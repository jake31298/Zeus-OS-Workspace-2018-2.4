package edu.sru.thangiah.zeus.top.topgui.topcheckboxtree;

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

public class TOPJCheckTree extends JTree {
    public TOPJCheckTree(TreeNode root) {
        super(root);
        super.setCellRenderer(new TOPCheckTreeCellRenderer(this));
        setCellEditor(new TOPCheckTreeCellEditor(this));
        setEditable(true);
    }

    public void setCellRenderer(TreeCellRenderer renderer) {
        super.setCellRenderer(new TOPCheckTreeCellRenderer(this, renderer));
    }

    public void setEditorRenderer(TreeCellRenderer renderer) {
        setCellEditor(new TOPCheckTreeCellEditor(this, renderer));
    }
}
