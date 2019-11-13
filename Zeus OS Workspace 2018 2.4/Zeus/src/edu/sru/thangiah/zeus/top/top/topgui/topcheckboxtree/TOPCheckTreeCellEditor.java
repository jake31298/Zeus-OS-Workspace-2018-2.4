package edu.sru.thangiah.zeus.top.topgui.topcheckboxtree;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class TOPCheckTreeCellEditor
    extends TOPCheckTreeCellRenderer
    implements TreeCellEditor, ActionListener {
  protected CellEditorListener list;

  public TOPCheckTreeCellEditor(JTree tree) {
    this(tree, new DefaultTreeCellRenderer());
  }

  public TOPCheckTreeCellEditor(JTree tree, TreeCellRenderer renderer) {
    super(tree, renderer);
    check.addActionListener(this);
  }

  public Component getTreeCellEditorComponent(JTree tree, Object value,
                                              boolean selected,
                                              boolean expanded, boolean leaf,
                                              int row) {
    return getTreeCellRendererComponent(tree, value, true, expanded, leaf,
                                        row, true);
  }

  public boolean stopCellEditing() {
    return true;
  }

  public Object getCellEditorValue() {
    node.setSelected(check.isSelected());

    return node;
  }

  public boolean isCellEditable(EventObject event) {
    return true;
  }

  public boolean shouldSelectCell(EventObject event) {
    return true;
  }

  public void cancelCellEditing() {
    fireEditingCanceled();
  }

  public void addCellEditorListener(CellEditorListener listener) {
    list = TOPSwingEventMulticaster.add(list, listener);
  }

  public void removeCellEditorListener(CellEditorListener listener) {
    list = TOPSwingEventMulticaster.remove(list, listener);
  }

  protected void fireEditingStopped() {
    if (list != null) {
      list.editingStopped(new ChangeEvent(this));
    }
  }

  protected void fireEditingCanceled() {
    if (list != null) {
      list.editingCanceled(new ChangeEvent(this));
    }
  }

  public void actionPerformed(ActionEvent event) {
    fireEditingStopped();
  }
}
