package pl.edu.wat.wcy.jfk.lab2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javassist.ClassPool;
import javassist.CtClass;


import java.io.*;
import java.util.*;
import java.util.jar.*;

public class InfoJar {
    private static final Logger logger = LoggerFactory.getLogger(InfoJar.class);

    private static final InfoJar ourInstance = new InfoJar();
    private String jarPath;
    private ClassPool pool;
    private List<CtClass> ctClasses;

    public static InfoJar getInstance() {
        return ourInstance;
    }

    private InfoJar() {
    }

    public CtClass searchCtClass(String path) {
        for (CtClass c : ctClasses) {
            if (c.getName().equals(path)) {
                return c;
            }
        }
        return null;
    }

    public ClassPool getPool() {
        return pool;
    }

    public void setPool(ClassPool pool) {
        this.pool = pool;
        this.ctClasses = new ArrayList<>();
    }

    public List<CtClass> getCtClasses() {
        return ctClasses;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    public void save(String path) {
        try {
            saveJarFile(path, ctClasses);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String getFileName(String name) {
        name = name.replace('.', '/') + ".class";

        return name;
    }

    private void saveJarFile(String newJarPath, List<CtClass> ctClasses) throws IOException {
        File jarFile = new File(jarPath);
        File tempJarFile = new File(newJarPath);

        try (JarFile jar = new JarFile(jarFile)) {
            try (JarOutputStream tempJar = new JarOutputStream(new FileOutputStream(tempJarFile))) {
                byte[] buffer = new byte[1024];
                int bytesRead;

                try {
                    for (CtClass cc : ctClasses) {
                        JarEntry entry = new JarEntry(getFileName(cc.getName()));
                        tempJar.putNextEntry(entry);
                        tempJar.write(cc.toBytecode());
                    }
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                }


                InputStream entryStream = null;
                for (Enumeration entries = jar.entries(); entries.hasMoreElements(); ) {
                    JarEntry entry = (JarEntry) entries.nextElement();

                    if (!equalsName(entry.getName())) {
                        entryStream = jar.getInputStream(entry);
                        tempJar.putNextEntry(entry);

                        while ((bytesRead = entryStream.read(buffer)) != -1) {
                            tempJar.write(buffer, 0, bytesRead);
                        }
                    }
                }

                if (entryStream != null)
                    entryStream.close();
            } catch (Exception ex) {
                logger.error(ex.getMessage());
            }
        }
    }

    private boolean equalsName(String entryName) {
        for (CtClass cc : ctClasses) {
            if (getFileName(cc.getName()).equals(entryName)) {
                return true;
            }
        }
        return false;
    }

}
