package pl.edu.wat.wcy.jfk.lab2.gui.popupMenu;

import pl.edu.wat.wcy.jfk.lab2.controler.CtClassControler;
import pl.edu.wat.wcy.jfk.lab2.controler.PackageControler;
import pl.edu.wat.wcy.jfk.lab2.gui.tree.Node;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClassPopupMenu extends JPopupMenu {
    private final JTree tree;
    private final TreePath path;
    private final JMenuItem addMItem;
    private final JMenuItem addAItem;
    private final JMenuItem addCItem;
    private final JMenuItem addIItem;
    private JMenuItem deleteCItem;
    private JTextArea textArea;

    private class ClassPopupActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            textArea = new JTextArea();
            CtClassControler classControler = new CtClassControler(path);
            if (e.getSource() == addMItem) {
                MyMenuItem.actionItem(classControler, tree, getPanel(700), "Method:", Node.METHOD, textArea);

            } else if (e.getSource() == addAItem) {
                MyMenuItem.actionItem(classControler, tree, getPanel(700), "Field:", Node.ATTRIBUTE, textArea);

            } else if (e.getSource() == addCItem) {
                MyMenuItem.actionItem(classControler, tree, getPanel(700), "Constructor:", Node.CONSTRUCTOR, textArea);

            } else if (e.getSource() == deleteCItem) {
                PackageControler packageControler = new PackageControler(tree);
                packageControler.deleteClass(tree.getSelectionPath().getPath());

                CtClassControler.removeSelected(tree);

            } else if (e.getSource() == addIItem) {
                int selection = JOptionPane.showConfirmDialog(
                        null, getPanel(16), "Path (full name) interface:"
                        , JOptionPane.OK_CANCEL_OPTION
                        , JOptionPane.PLAIN_MESSAGE);
                if (selection == JOptionPane.OK_OPTION) {
                    classControler.setInterface(textArea.getText());
                }
            }
        }
    }

    private JScrollPane getPanel(int height) {
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(700, height));
        return scrollPane;
    }


    public ClassPopupMenu(boolean isMy, TreePath path, JTree tree) {
        super();
        this.path = path;
        this.tree = tree;
        ClassPopupActionListener actionListener = new ClassPopupActionListener();

        addMItem = MyMenuItem.getItem("Add method", actionListener, this);
        addAItem = MyMenuItem.getItem("Add attribute", actionListener, this);
        addCItem = MyMenuItem.getItem("Add constructor", actionListener, this);
        addIItem = MyMenuItem.getItem("Add interface", actionListener, this);
        if (isMy) {
            deleteCItem = MyMenuItem.getItem("Delete", actionListener, this);
        }
    }
}

