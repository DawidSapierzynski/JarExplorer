package pl.edu.wat.wcy.jfk.lab2.gui.popupMenu;


import javassist.CtConstructor;
import pl.edu.wat.wcy.jfk.lab2.controler.CtClassControler;
import pl.edu.wat.wcy.jfk.lab2.gui.tree.Node;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConstructorPopupMenu extends JPopupMenu {
    private final JTree tree;
    private final TreePath path;
    private final JMenuItem deleteCItem;
    private final JMenuItem overwriteCItem;
    private JTextArea textArea;

    private class ConstructorPopupActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            CtClassControler classControler = new CtClassControler(path);
            if (e.getSource() == deleteCItem) {
                classControler.deleteConstructor(tree);
                CtClassControler.removeSelected(tree);

            } else if (e.getSource() == overwriteCItem) {
                textArea = new JTextArea();
                int selection = JOptionPane.showConfirmDialog(
                        null, getPanel(), "Body overwrite constructor:"
                        , JOptionPane.OK_CANCEL_OPTION
                        , JOptionPane.PLAIN_MESSAGE);
                if (selection == JOptionPane.OK_OPTION) {
                    Node selectedNode = (Node) tree.getSelectionPath().getLastPathComponent();
                    classControler.overwriteConstructor(textArea.getText(), (CtConstructor) selectedNode.getContent());
                }
            }
        }

        private JScrollPane getPanel() {
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(700, 700));
            return scrollPane;
        }
    }


    public ConstructorPopupMenu(TreePath path, JTree tree) {
        super();
        this.path = path;
        this.tree = tree;
        ConstructorPopupActionListener actionListener = new ConstructorPopupActionListener();

        overwriteCItem = MyMenuItem.getItem("Overwrite", actionListener, this);
        deleteCItem = MyMenuItem.getItem("Delete", actionListener, this);
    }
}
