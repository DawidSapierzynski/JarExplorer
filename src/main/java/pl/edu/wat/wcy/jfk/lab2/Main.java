package pl.edu.wat.wcy.jfk.lab2;

import pl.edu.wat.wcy.jfk.lab2.gui.ExplorerJFrame;

import javax.swing.*;
import java.awt.*;

class Main {
    public static void main(String[] args) {
        ExplorerJFrame jFrame = new ExplorerJFrame("JarExplorer");

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setMinimumSize(new Dimension(1000, 700));
        jFrame.setVisible(true);
    }
}
