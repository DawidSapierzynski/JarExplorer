package pl.edu.wat.wcy.jfk.lab2.controler;

import javassist.CtClass;
import pl.edu.wat.wcy.jfk.lab2.InfoJar;
import pl.edu.wat.wcy.jfk.lab2.gui.tree.Node;

import javax.swing.*;

public class PackageControler {
    private final InfoJar pool = InfoJar.getInstance();
    private final JTree tree;

    public PackageControler(JTree tree) {
        this.tree = tree;

    }

    public CtClass addInterface(String name) {

        String stringPathInterface = getStringPath(name);
        CtClass cc = pool.getPool().makeInterface(stringPathInterface);
        pool.getCtClasses().add(cc);

        return cc;
    }

    private String getStringPath(String name) {
        Object[] nodes = tree.getSelectionPath().getPath();
        StringBuilder stringBuilder = new StringBuilder();
        String stringPathInterface;

        for (int i = 1; i < nodes.length; i++) {
            stringBuilder.append(nodes[i])
                    .append(".");
        }
        stringBuilder.append(name);
        stringPathInterface = stringBuilder.toString();

        return stringPathInterface;
    }

    public CtClass addClass(String name) {
        String stringPathClass = getStringPath(name);
        CtClass cc = pool.getPool().makeClass(stringPathClass);
        pool.getCtClasses().add(cc);

        return cc;
    }

    public void deleteDir(Node selectedNode) {
        for (int i = 0; i < selectedNode.getChildCount(); i++) {
            Node childNode = (Node) selectedNode.getChildAt(i);
            if (childNode.getType() == Node.CLASS) {
                deleteClass(childNode.getPath());
            } else if (childNode.getType() == Node.DIR) {
                deleteDir(childNode);
            }
        }
    }

    public void deleteClass(Object[] nodes) {
        StringBuilder stringBuilder = new StringBuilder();
        String stringPathClass;
        CtClass cc;

        for (int i = 1; i < nodes.length; i++) {
            stringBuilder.append(nodes[i]);
            if (i != nodes.length - 1)
                stringBuilder.append(".");

        }
        stringPathClass = stringBuilder.toString();

        if ((cc = pool.searchCtClass(stringPathClass)) != null) {
            pool.getCtClasses().remove(cc);
        }
    }
}
