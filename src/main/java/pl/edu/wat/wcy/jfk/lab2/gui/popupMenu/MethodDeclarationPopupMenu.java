package pl.edu.wat.wcy.jfk.lab2.gui.popupMenu;

import pl.edu.wat.wcy.jfk.lab2.controler.CtClassControler;
import pl.edu.wat.wcy.jfk.lab2.controler.PackageControler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MethodDeclarationPopupMenu extends JPopupMenu {
    private final JTree tree;
    private JMenuItem deleteIItem;

    private class DirPopupActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == deleteIItem) {
                PackageControler packageControler = new PackageControler(tree);
                packageControler.deleteClass(tree.getSelectionPath().getPath());

                CtClassControler.removeSelected(tree);
            }
        }
    }

    public MethodDeclarationPopupMenu(boolean isMy, JTree tree) {
        super();
        this.tree = tree;
        DirPopupActionListener actionListener = new DirPopupActionListener();

        if (isMy) {
            deleteIItem = MyMenuItem.getItem("Delete", actionListener, this);
        }
    }
}
