package pl.edu.wat.wcy.jfk.lab2.gui;

import javax.swing.*;
import java.awt.*;

class WorkPanel extends JPanel {

    WorkPanel() {
        this.setLayout(new GridLayout(1, 1));

        TextArea bodyClass = new TextArea();
        bodyClass.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollBodyClass = new JScrollPane(bodyClass);

        add(scrollBodyClass);
    }
}
