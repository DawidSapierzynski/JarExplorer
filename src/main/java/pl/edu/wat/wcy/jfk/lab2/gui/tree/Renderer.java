package pl.edu.wat.wcy.jfk.lab2.gui.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.tree.TreeCellRenderer;

class Renderer extends JPanel implements TreeCellRenderer {

    private final TreeLabel label;

    Renderer() {
        setLayout(null);
        add(label = new TreeLabel());
        label.setForeground(UIManager.getColor("Tree.textForeground"));
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean isSelected, boolean expanded,
                                                  boolean leaf, int row,
                                                  boolean hasFocus) {
        String stringValue = tree.convertValueToText(value, isSelected,
                expanded, leaf, row, hasFocus);
        setEnabled(tree.isEnabled());
        label.setFont(tree.getFont());
        label.setText(stringValue);
        label.setSelected(isSelected);
        label.setFocus(hasFocus);

        if (((Node) value).getType() == Node.DIR) {
            if (expanded) {
                label.setIcon(UIManager.getIcon("Tree.openIcon"));
            } else {
                label.setIcon(UIManager.getIcon("Tree.closedIcon"));
            }
        } else {
            ImageIcon icon = new ImageIcon("src/main/resources/icon/" +
                    ((Node) value).getType() + ".png");
            label.setIcon(icon);
        }

        return this;
    }

    public Dimension getPreferredSize() {
        Dimension d_label = label.getPreferredSize();
        return new Dimension(d_label.width, d_label.height);
    }

    public void doLayout() {
        Dimension d_label = label.getPreferredSize();

        label.setLocation(0, 0);
        label.setBounds(0, 0, d_label.width, d_label.height);
    }

    public void setBackground(Color color) {
        if (color instanceof ColorUIResource) {
            color = null;
        }
        super.setBackground(color);
    }
}
