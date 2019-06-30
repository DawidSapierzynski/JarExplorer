package pl.edu.wat.wcy.jfk.lab2.gui.popupMenu;

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import pl.edu.wat.wcy.jfk.lab2.controler.CtClassControler;
import pl.edu.wat.wcy.jfk.lab2.controler.PackageControler;
import pl.edu.wat.wcy.jfk.lab2.gui.tree.Node;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionListener;

class MyMenuItem {

    static void actionItem(Object classOrPackageControler, JTree tree, JScrollPane jPanel, String title, int typeNode, JTextArea textArea) {
        Node newNode;
        CtClass ctClass;
        CtMethod ctMethod;
        int selection = JOptionPane.showConfirmDialog(
                null, jPanel, title
                , JOptionPane.OK_CANCEL_OPTION
                , JOptionPane.PLAIN_MESSAGE);
        if (selection == JOptionPane.OK_OPTION) {
            switch (typeNode) {
                case Node.METHOD:
                case Node.METHODDECLARATION:
                    ctMethod = ((CtClassControler) classOrPackageControler).addMethod(textArea.getText());
                    newNode = new Node(ctMethod.getName(), typeNode, true, ctMethod);
                    break;

                case Node.ATTRIBUTE:
                    CtField ctField = ((CtClassControler) classOrPackageControler).addField(textArea.getText());
                    newNode = new Node(ctField.getName(), Node.ATTRIBUTE, true, ctField);
                    break;

                case Node.CONSTRUCTOR:
                    CtConstructor ctConstructor = ((CtClassControler) classOrPackageControler).addConstructor(textArea.getText());
                    newNode = new Node(ctConstructor.getName(), Node.CONSTRUCTOR, true, ctConstructor);
                    break;

                case Node.DIR:
                    newNode = new Node(textArea.getText(), Node.DIR, true, null);
                    break;

                case Node.INTERFACE:
                    ctClass = ((PackageControler) classOrPackageControler).addInterface(textArea.getText());
                    newNode = new Node(textArea.getText(), Node.INTERFACE, true, ctClass);
                    break;

                case Node.CLASS:
                    ctClass = ((PackageControler) classOrPackageControler).addClass(textArea.getText());
                    newNode = new Node(textArea.getText(), Node.CLASS, true, ctClass);
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + typeNode);
            }


            Node selectedNode = (Node) tree.getSelectionPath().getLastPathComponent();
            selectedNode.add(newNode);

            DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
            model.reload(selectedNode);
        }
    }

    public static JMenuItem getItem(String name, ActionListener actionListener, JPopupMenu popupMenu) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(actionListener);
        popupMenu.add(item);
        return item;
    }
}
