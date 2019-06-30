package pl.edu.wat.wcy.jfk.lab2.gui.popupMenu;


import pl.edu.wat.wcy.jfk.lab2.controler.CtClassControler;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttributePopupMenu extends JPopupMenu {
    private final JTree tree;
    private final JMenuItem deleteAItem;
    private final TreePath path;

    private class AttributePopupActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == deleteAItem) {
                CtClassControler ctClassControler = new CtClassControler(path);
                ctClassControler.deleteField();

                CtClassControler.removeSelected(tree);
            }
        }
    }

    public AttributePopupMenu(TreePath path, JTree tree) {
        super();
        this.path = path;
        this.tree = tree;
        AttributePopupActionListener actionListener = new AttributePopupActionListener();

        deleteAItem = MyMenuItem.getItem("Delete", actionListener, this);
    }

}
