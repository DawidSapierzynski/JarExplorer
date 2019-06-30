package pl.edu.wat.wcy.jfk.lab2.gui.popupMenu;

import javassist.CtMethod;
import pl.edu.wat.wcy.jfk.lab2.controler.CtClassControler;
import pl.edu.wat.wcy.jfk.lab2.gui.tree.Node;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MethodPopupMenu extends JPopupMenu {
    private final JTree tree;
    private final TreePath path;
    private final JMenuItem deleteMItem;
    private final JMenuItem overwriteMItem;
    private final JMenuItem insertBeforeMItem;
    private final JMenuItem insertAfterMItem;
    private JTextArea textArea;

    private class MethodPopupActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            CtClassControler classControler = new CtClassControler(path);
            textArea = new JTextArea();
            if (e.getSource() == deleteMItem) {
                classControler.deleteMethod(tree);

                CtClassControler.removeSelected(tree);
            } else if (e.getSource() == overwriteMItem) {
                int selection = JOptionPane.showConfirmDialog(
                        null, getPanel(), "Body overwrite method:"
                        , JOptionPane.OK_CANCEL_OPTION
                        , JOptionPane.PLAIN_MESSAGE);
                if (selection == JOptionPane.OK_OPTION) {
                    Node selectedNode = (Node) tree.getSelectionPath().getLastPathComponent();
                    classControler.overwriteMethod(textArea.getText(), (CtMethod) selectedNode.getContent());
                }

            } else if (e.getSource() == insertBeforeMItem) {
                int selection = JOptionPane.showConfirmDialog(
                        null, getPanel(), "Insert before method:"
                        , JOptionPane.OK_CANCEL_OPTION
                        , JOptionPane.PLAIN_MESSAGE);
                if (selection == JOptionPane.OK_OPTION) {
                    Node selectedNode = (Node) tree.getSelectionPath().getLastPathComponent();
                    classControler.insertBeforeMethod(textArea.getText(), (CtMethod) selectedNode.getContent());
                }

            } else if (e.getSource() == insertAfterMItem) {
                int selection = JOptionPane.showConfirmDialog(
                        null, getPanel(), "Insert after method:"
                        , JOptionPane.OK_CANCEL_OPTION
                        , JOptionPane.PLAIN_MESSAGE);
                if (selection == JOptionPane.OK_OPTION) {
                    Node selectedNode = (Node) tree.getSelectionPath().getLastPathComponent();
                    classControler.insertAfterMethod(textArea.getText(), (CtMethod) selectedNode.getContent());
                }
            }
        }

        private JScrollPane getPanel() {
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(700, 700));
            return scrollPane;
        }
    }

    public MethodPopupMenu(TreePath path, JTree tree) {
        super();
        this.path = path;
        this.tree = tree;
        MethodPopupActionListener actionListener = new MethodPopupActionListener();

        insertBeforeMItem = MyMenuItem.getItem("Insert before", actionListener, this);
        insertAfterMItem = MyMenuItem.getItem("Insert after", actionListener, this);
        overwriteMItem = MyMenuItem.getItem("Overwrite", actionListener, this);
        deleteMItem = MyMenuItem.getItem("Delete", actionListener, this);
    }
}
