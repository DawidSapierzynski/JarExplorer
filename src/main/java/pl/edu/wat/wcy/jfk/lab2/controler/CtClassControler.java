package pl.edu.wat.wcy.jfk.lab2.controler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javassist.*;
import pl.edu.wat.wcy.jfk.lab2.InfoJar;
import pl.edu.wat.wcy.jfk.lab2.gui.tree.Node;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class CtClassControler {

    private static final Logger logger = LoggerFactory.getLogger(CtClassControler.class);

    private final InfoJar pool = InfoJar.getInstance();
    private CtClass ctClass;
    private final String name;
    private boolean classInList = true;

    public CtClassControler(TreePath path) {
        StringBuilder stringBuilder = new StringBuilder();
        Object[] nodes = path.getPath();
        name = path.getLastPathComponent().toString();

        int length;
        if (((Node) nodes[nodes.length - 1]).getType() == Node.CLASS || ((Node) nodes[nodes.length - 1]).getType() == Node.INTERFACE) {
            length = nodes.length;
        } else {
            length = nodes.length - 1;
        }

        for (int i = 1; i < length; i++) {
            stringBuilder.append(nodes[i]);
            if (i != length - 1)
                stringBuilder.append(".");

        }
        String stringPathClass = stringBuilder.toString();
        try {
            if ((ctClass = pool.searchCtClass(stringPathClass)) == null) {
                ctClass = pool.getPool().get(stringPathClass);
                classInList = false;
            }

        } catch (NotFoundException e) {
            logger.error(e.getMessage());
        }
    }

    public void deleteField() {
        try {
            CtField field = ctClass.getField(name);
            ctClass.removeField(field);
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            return;
        }

        if (!classInList) {
            pool.getCtClasses().add(ctClass);
        }

    }

    public void deleteConstructor(JTree tree) {
        try {
            CtConstructor constructor = (CtConstructor) ((Node) tree.getSelectionPath().getLastPathComponent()).getContent();
            ctClass.removeConstructor(constructor);
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            return;
        }

        if (!classInList) {
            pool.getCtClasses().add(ctClass);
        }

    }

    public void deleteMethod(JTree tree) {
        try {
            CtMethod method = (CtMethod) ((Node) tree.getSelectionPath().getLastPathComponent()).getContent();
            ctClass.removeMethod(method);
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            return;
        }

        if (!classInList) {
            pool.getCtClasses().add(ctClass);
        }

    }

    public void overwriteConstructor(String body, CtConstructor constructor) {
        try {
            constructor.setBody("{" + body + "}");
        } catch (CannotCompileException e) {
            logger.error(e.getMessage());

        }
        if (!classInList) {
            pool.getCtClasses().add(ctClass);
        }
    }

    public static void removeSelected(JTree tree) {
        MutableTreeNode selectedNode = (MutableTreeNode) tree.getSelectionPath().getLastPathComponent();
        TreeNode parent = selectedNode.getParent();
        DefaultTreeModel defaultTreeModel = (DefaultTreeModel) tree.getModel();
        defaultTreeModel.removeNodeFromParent(selectedNode);
        defaultTreeModel.reload(parent);
    }

    public CtMethod addMethod(String text) {
        try {
            CtMethod newMethod = CtNewMethod.make(text, ctClass);
            ctClass.addMethod(newMethod);
            if (!classInList) {
                pool.getCtClasses().add(ctClass);
            }
            return newMethod;
        } catch (CannotCompileException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public CtField addField(String text) {
        try {
            CtField newField = CtField.make(text, ctClass);
            ctClass.addField(newField);
            if (!classInList) {
                pool.getCtClasses().add(ctClass);
            }
            return newField;
        } catch (CannotCompileException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public CtConstructor addConstructor(String text) {
        try {
            CtConstructor newConstructor = CtNewConstructor.make(text, ctClass);
            ctClass.addConstructor(newConstructor);
            if (!classInList) {
                pool.getCtClasses().add(ctClass);
            }
            return newConstructor;
        } catch (CannotCompileException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public void insertBeforeMethod(String text, CtMethod method) {
        try {
            method.insertBefore(text);
            if (!classInList) {
                pool.getCtClasses().add(ctClass);
            }
        } catch (CannotCompileException e) {
            logger.error(e.getMessage());
        }
    }

    public void insertAfterMethod(String text, CtMethod method) {
        try {
            method.insertAfter(text);
            if (!classInList) {
                pool.getCtClasses().add(ctClass);
            }
        } catch (CannotCompileException e) {
            logger.error(e.getMessage());
        }
    }

    public void overwriteMethod(String text, CtMethod content) {
        try {
            content.setBody("{" + text + "}");
            if (!classInList) {
                pool.getCtClasses().add(ctClass);
            }
        } catch (CannotCompileException e) {
            logger.error(e.getMessage());
        }

    }

    public void setInterface(String path) {
        CtClass c;
        if ((c = pool.searchCtClass(path)) == null) {
            try {
                c = pool.getPool().get(path);
            } catch (NotFoundException e) {
                logger.error(e.getMessage());
            }
        }

        ctClass.addInterface(c);
    }
}
