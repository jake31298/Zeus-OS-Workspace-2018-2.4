package edu.sru.thangiah.zeus.gui.checkboxtree;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class JCheckTree extends JTree {
    public JCheckTree(TreeNode root) {
        super(root);
        super.setCellRenderer(new CheckTreeCellRenderer(this));
        setCellEditor(new CheckTreeCellEditor(this));
        setEditable(true);
    }

    public void setCellRenderer(TreeCellRenderer renderer) {
        super.setCellRenderer(new CheckTreeCellRenderer(this, renderer));
    }

    public void setEditorRenderer(TreeCellRenderer renderer) {
        setCellEditor(new CheckTreeCellEditor(this, renderer));
    }
}
