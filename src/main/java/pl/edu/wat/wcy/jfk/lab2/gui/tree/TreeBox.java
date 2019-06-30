package pl.edu.wat.wcy.jfk.lab2.gui.tree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javassist.*;
import pl.edu.wat.wcy.jfk.lab2.InfoJar;

import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TreeBox {
    private static final Logger logger = LoggerFactory.getLogger(TreeBox.class);

    private final List<Node> nodes = new ArrayList<>();
    private JScrollPane sp = new JScrollPane(new JLabel("Open jar file"));

    public TreeBox() {
        sp.setPreferredSize(new Dimension(300, 300));
    }

    public TreeBox(List<String> classes, String jarName) {
        Node last = new Node(jarName, Node.PARENT, -1);
        for (String c : classes) {
            try {
                InfoJar pool = InfoJar.getInstance();
                CtClass ctClass = pool.getPool().getCtClass(c);
                CtField[] ctFields = ctClass.getDeclaredFields();
                CtConstructor[] ctConstructors = ctClass.getDeclaredConstructors();
                CtMethod[] ctMethods = ctClass.getDeclaredMethods();

                String[] strings = c.split("\\.");
                Node[] classNode = builtTreeClass(strings, ctClass);
                last.add(classNode[0]);
                if (ctFields != null) {
                    addFieldsToClass(classNode[1], ctFields);
                }

                if (ctConstructors != null) {
                    addConstructorsToClass(classNode[1], ctConstructors);
                }

                if (ctMethods != null) {
                    addMethodsToClass(classNode[1], ctMethods);
                }

            } catch (NotFoundException e) {
                logger.error(e.getMessage());
            }
        }

        JTree tree = new JTree(last);
        tree.setCellRenderer(new Renderer());
        tree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.SINGLE_TREE_SELECTION
        );
        tree.putClientProperty("JTree.lineStyle", "Angled");
        tree.addMouseListener(new NodeSelectionListener(tree));
        sp = new JScrollPane(tree);
        sp.setPreferredSize(new Dimension(300, 300));
    }

    private void addMethodsToClass(Node last, CtMethod[] ctMethods) {
        for (CtMethod m : ctMethods) {
            Node field = new Node(m.getName(), Node.METHOD, m);
            nodes.add(field);
            last.add(field);
        }
    }

    private void addConstructorsToClass(Node last, CtConstructor[] ctConstructors) {
        for (CtConstructor c : ctConstructors) {
            Node field = new Node(c.getName(), Node.CONSTRUCTOR, c);
            nodes.add(field);
            last.add(field);
        }
    }

    private void addFieldsToClass(Node last, CtField[] ctFields) {
        for (CtField f : ctFields) {
            Node field = new Node(f.getName(), Node.ATTRIBUTE, f);
            nodes.add(field);
            last.add(field);
        }

    }

    private Node[] builtTreeClass(String[] strings, CtClass ctClass) {
        Node now;
        Node[] n = new Node[2];
        for (int i = 0; i < strings.length; i++) {
            String id = ((n[0] != null) ? n[0] : "null").toString() + i;
            if ((now = searchDir(strings[i], id)) == null) {
                nodes.add(now = new Node(strings[i], Node.DIR, id));
            }

            if (i == 0) {
                n[0] = now;
            }

            if (n[1] != null) {
                n[1].add(now);
            }
            n[1] = now;

        }
        n[1].setType(Node.CLASS);
        n[1].setContent(ctClass);

        return n;
    }

    private Node searchDir(String s, String id) {
        for (Node node : nodes) {
            if (node.getType() == Node.DIR && node.toString().equals(s) && node.getContent().equals(id)) {
                return node;
            }
        }

        return null;
    }

    public JScrollPane getSp() {
        return sp;
    }

}

  

