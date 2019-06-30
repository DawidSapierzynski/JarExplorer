package pl.edu.wat.wcy.jfk.lab2.gui.tree;

import pl.edu.wat.wcy.jfk.lab2.gui.popupMenu.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import javax.swing.tree.TreePath;

class NodeSelectionListener extends MouseAdapter {

    private final JTree tree;
    private JPopupMenu popupMenu;

    NodeSelectionListener(JTree tree) {
        this.tree = tree;
    }

    public void mouseClicked(MouseEvent e) {

        if (SwingUtilities.isRightMouseButton(e)) {

            int row = tree.getClosestRowForLocation(e.getX(), e.getY());
            tree.setSelectionRow(row);
            TreePath path = tree.getPathForRow(row);
            if (path != null) {
                Node node = (Node) path.getLastPathComponent();
                if (node.getType() == Node.DIR || node.getType() == Node.PARENT) {
                    popupMenu = new DirPopupMenu(node.isMy(), tree);
                } else if (node.getType() == Node.INTERFACE) {
                    popupMenu = new InterfacePopupMenu(node.isMy(), path, tree);
                } else if (node.getType() == Node.CLASS) {
                    popupMenu = new ClassPopupMenu(node.isMy(), path, tree);
                } else if (node.getType() == Node.ATTRIBUTE) {
                    popupMenu = new AttributePopupMenu(path, tree);
                } else if (node.getType() == Node.CONSTRUCTOR) {
                    popupMenu = new ConstructorPopupMenu(path, tree);
                } else if (node.getType() == Node.METHOD) {
                    popupMenu = new MethodPopupMenu(path, tree);
                } else if (node.getType() == Node.METHODDECLARATION) {
                    popupMenu = new MethodDeclarationPopupMenu(node.isMy(), tree);
                }

                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }

        }
    }
}
