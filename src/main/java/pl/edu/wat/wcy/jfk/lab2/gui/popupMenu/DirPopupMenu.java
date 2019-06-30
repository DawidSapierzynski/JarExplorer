package pl.edu.wat.wcy.jfk.lab2.gui.popupMenu;

import pl.edu.wat.wcy.jfk.lab2.controler.CtClassControler;
import pl.edu.wat.wcy.jfk.lab2.controler.PackageControler;
import pl.edu.wat.wcy.jfk.lab2.gui.tree.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DirPopupMenu extends JPopupMenu {
    private final JTree tree;
    private final JMenuItem addPItem;
    private final JMenuItem addIItem;
    private final JMenuItem addCItem;
    private JMenuItem deleteItem;
    private JTextArea textArea;

    private class DirPopupActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            PackageControler packageControler = new PackageControler(tree);
            textArea = new JTextArea();

            if (e.getSource() == addPItem) {
                MyMenuItem.actionItem(packageControler, tree, getPanel(), "Name package:", Node.DIR, textArea);

            } else if (e.getSource() == addIItem) {
                MyMenuItem.actionItem(packageControler, tree, getPanel(), "Name Interface:", Node.INTERFACE, textArea);

            } else if (e.getSource() == addCItem) {
                MyMenuItem.actionItem(packageControler, tree, getPanel(), "Name class:", Node.CLASS, textArea);

            } else if (e.getSource() == deleteItem) {
                packageControler.deleteDir((Node) tree.getSelectionPath().getLastPathComponent());
                CtClassControler.removeSelected(tree);
            }
        }

        private JScrollPane getPanel() {
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(100, 8));
            return scrollPane;
        }
    }

    public DirPopupMenu(boolean isMy, JTree tree) {
        super();
        this.tree = tree;
        DirPopupActionListener actionListener = new DirPopupActionListener();

        addPItem = MyMenuItem.getItem("Add package", actionListener, this);
        addIItem = MyMenuItem.getItem("Add class interface", actionListener, this);
        addCItem = MyMenuItem.getItem("Add class", actionListener, this);
        if (isMy) {
            deleteItem = MyMenuItem.getItem("Delete", actionListener, this);
        }

    }

}
