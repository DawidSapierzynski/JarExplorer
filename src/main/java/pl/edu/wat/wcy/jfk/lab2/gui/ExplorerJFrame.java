package pl.edu.wat.wcy.jfk.lab2.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javassist.ClassPool;
import javassist.NotFoundException;
import pl.edu.wat.wcy.jfk.lab2.InfoJar;
import pl.edu.wat.wcy.jfk.lab2.gui.tree.TreeBox;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ExplorerJFrame extends JFrame {
    private static final Logger logger = LoggerFactory.getLogger(ExplorerJFrame.class);

    private final InfoJar infoJar = InfoJar.getInstance();
    private TreeBox treeBox;
    private final WorkPanel workPanel;
    private JMenuItem openItem, saveItem, closeItem;

    private class MenuActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == openItem) {
                JFileChooser fc = new JFileChooser();
                fc.setAcceptAllFileFilterUsed(false);

                FileFilter filter = new FileNameExtensionFilter("JAR File", "jar");
                fc.addChoosableFileFilter(filter);

                int returnVal = fc.showDialog(ExplorerJFrame.this, "Open file");
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    treeBox = getNewTreeBox(file.getPath(), file.getName());
                    infoJar.setJarPath(file.getPath());
                    getContentPane().removeAll();
                    getContentPane().add(treeBox.getSp(), BorderLayout.WEST);
                    getContentPane().add(workPanel, BorderLayout.CENTER);
                    revalidate();
                    repaint();
                }
            } else if (e.getSource() == saveItem) {
                if (infoJar.getPool() != null) {
                    JFileChooser fc = new JFileChooser();
                    int returnVal = fc.showDialog(ExplorerJFrame.this, "Save");
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        String path = fc.getSelectedFile().getPath();
                        if (!path.endsWith(".jar")) {
                            path += ".jar";
                        }
                        infoJar.save(path);
                    }
                }
            } else if (e.getSource() == closeItem) {
                System.exit(0);
            }
        }
    }

    public ExplorerJFrame(String name) {
        super(name);

        setMenu();
        treeBox = new TreeBox();
        workPanel = new WorkPanel();
        getContentPane().add(treeBox.getSp(), BorderLayout.WEST);
        getContentPane().add(workPanel, BorderLayout.CENTER);
    }

    private void setMenu() {
        MenuActionListener menuActionListener = new MenuActionListener();
        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        menuBar.add(file);

        openItem = getMenuItem("Open", "Open jar file", menuActionListener, file);
        saveItem = getMenuItem("Save", "Save jar file", menuActionListener, file);
        closeItem = getMenuItem("Close", "Close application", menuActionListener, file);

        this.setJMenuBar(menuBar);
    }

    private JMenuItem getMenuItem(String name, String description, MenuActionListener menuActionListener, JMenu file) {
        JMenuItem item = new JMenuItem(name);
        item.getAccessibleContext().setAccessibleDescription(description);
        item.addActionListener(menuActionListener);
        file.add(item);
        return item;
    }

    private TreeBox getNewTreeBox(String jarPath, String jarName) {
        List<String> classList = null;
        ClassPool pool = ClassPool.getDefault();
        infoJar.setPool(pool);

        try {
            pool.insertClassPath(jarPath);
            classList = getClassList(jarPath);
        } catch (IOException | NotFoundException e) {
            logger.error(e.getMessage());
        }

        assert classList != null;
        return new TreeBox(classList, jarName);
    }

    private List<String> getClassList(String jarPath) throws IOException {
        File file = new File(jarPath);

        JarInputStream in = new JarInputStream(new FileInputStream(file));
        JarEntry jarEntry;
        List<String> list = new ArrayList<>();
        while ((jarEntry = in.getNextJarEntry()) != null) {
            String name = jarEntry.getName();
            if (!name.endsWith(".class")) continue;
            String className = jarEntry.getName().substring(0, jarEntry.getName().length() - 6);
            className = className.replace('/', '.');
            list.add(className);
        }
        in.close();
        return list;
    }
}
