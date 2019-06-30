package pl.edu.wat.wcy.jfk.lab2.gui.popupMenu;

import pl.edu.wat.wcy.jfk.lab2.controler.CtClassControler;
import pl.edu.wat.wcy.jfk.lab2.controler.PackageControler;
import pl.edu.wat.wcy.jfk.lab2.gui.tree.Node;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfacePopupMenu extends JPopupMenu {
    private final JTree tree;
    private final TreePath path;
    private final JMenuItem addMItem;
    private JMenuItem deleteIItem;
    private JTextArea textArea;

    private class DirPopupActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addMItem) {
                CtClassControler classControler = new CtClassControler(path);
                textArea = new JTextArea();
                MyMenuItem.actionItem(classControler, tree, getPanel(), "Method declaration:", Node.METHODDECLARATION, textArea);
            } else if (e.getSource() == deleteIItem) {
                PackageControler packageControler = new PackageControler(tree);
                packageControler.deleteClass(tree.getSelectionPath().getPath());

                CtClassControler.removeSelected(tree);
            }
        }

        private JScrollPane getPanel() {
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(700, 100));
            return scrollPane;
        }
    }

    public InterfacePopupMenu(boolean isMy, TreePath path, JTree tree) {
        super();
        this.path = path;
        this.tree = tree;
        DirPopupActionListener actionListener = new DirPopupActionListener();

        addMItem = MyMenuItem.getItem("Add method declaration", actionListener, this);
        if (isMy) {
            deleteIItem = MyMenuItem.getItem("Delete", actionListener, this);
        }
    }
}
