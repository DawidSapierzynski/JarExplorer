package pl.edu.wat.wcy.jfk.lab2.gui.tree;

import javax.swing.tree.DefaultMutableTreeNode;

public class Node extends DefaultMutableTreeNode {
    public static final int DIR = 1;
    public static final int INTERFACE = 2;
    public static final int CLASS = 3;
    public static final int ATTRIBUTE = 4;
    public static final int CONSTRUCTOR = 5;
    public static final int METHOD = 6;
    public static final int METHODDECLARATION = 7;
    public static final int PARENT = 8;

    private int type;
    private final boolean isMy;
    private Object content;

    public Node(Object userObject, int type, Object content) {
        this(userObject, false, type, content);
    }

    public Node(Object userObject, int type, boolean isMy, Object content) {
        this(userObject, isMy, type, content);
    }

    private Node(Object userObject,
                 boolean isMy, int type, Object content) {
        super(userObject, true);
        this.isMy = isMy;
        this.type = type;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isMy() {
        return isMy;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
