package edu.sru.thangiah.zeus.top.topgui.topcheckboxtree;

import edu.sru.thangiah.zeus.gui.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.*;





/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class TOPCheckTreeCellRenderer
    extends JPanel
    implements TreeCellRenderer {
  protected TOPCheckTreeNode node;
  protected TreeCellRenderer renderer;
  protected JCheckBox check;

  public TOPCheckTreeCellRenderer(JTree tree) {
    this(tree, new DefaultTreeCellRenderer());
  }

  public TOPCheckTreeCellRenderer(JTree tree, TreeCellRenderer renderer) {
    setOpaque(false);
    setLayout(new BorderLayout());

    this.renderer = renderer;
    add(BorderLayout.CENTER,
        renderer.getTreeCellRendererComponent(tree, "", true, true, true,
                                              0, true));

    check = new JCheckBox();
    check.setMargin(new Insets(0, 0, 0, 0));
    check.setBorderPaintedFlat(true);
    check.setOpaque(false);
    add(BorderLayout.WEST, check);
  }

  public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                boolean selected,
                                                boolean expanded, boolean leaf,
                                                int row,
                                                boolean hasFocus) {
    if (value instanceof TOPCheckTreeNode) {
      node = (TOPCheckTreeNode) value;
      check.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          node.setSelected(check.isSelected());

          if (node.getActionListener() != null) {
            node.getActionListener().actionPerformed(e);
          }
        }
      });
      check.setSelected(node.isSelected());
      check.setForeground(node.getColor());
      value = node.getUserObject();
    }

    renderer.getTreeCellRendererComponent(tree, value, selected, expanded,
                                          leaf, row, hasFocus);

    return this;
  }
}
