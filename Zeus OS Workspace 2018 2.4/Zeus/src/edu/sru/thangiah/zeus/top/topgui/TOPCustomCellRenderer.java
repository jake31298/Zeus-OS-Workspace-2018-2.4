package edu.sru.thangiah.zeus.top.topgui;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

/**
 * Class that produces the customized icons for the JTree in the interface
 * <p>Title: Mixed Fleet</p>
 * <p>Description: Class that produces the customized icons for the JTree in the interface</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class TOPCustomCellRenderer
    extends JLabel
    implements TreeCellRenderer {
  private ImageIcon truckImage;
  private ImageIcon emptyDepotImage;
  private ImageIcon fullDepotImage;
  private ImageIcon studentImage;
  private ImageIcon fullTruckImage;
  private boolean bSelected;

  public TOPCustomCellRenderer() {
    // Load the images
    truckImage = new ImageIcon("img/bus.gif");
    fullTruckImage = new ImageIcon("img/Fbus.gif");
    emptyDepotImage = new ImageIcon("img/warehouse.gif");
    fullDepotImage = new ImageIcon("img/depotFull.gif");
    studentImage = new ImageIcon("img/student.gif");
  }

  /**
   * Gets how to draw the tree cell
   * @param tree the tree
   * @param value which cell
   * @param bSelected is it selected
   * @param bExpanded is it expanded
   * @param bLeaf is it a leaf
   * @param iRow what row number
   * @param bHasFocus does it have focus
   * @return the cell drawn how we want
   */
  public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                boolean bSelected,
                                                boolean bExpanded,
                                                boolean bLeaf, int iRow,
                                                boolean bHasFocus) {
    // Find out which node we are rendering and get its text
    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
    String labelText = (String) node.getUserObject();

    this.bSelected = bSelected;

    // Set the correct foreground color
    if (!bSelected) {
      setForeground(Color.black);
    }
    else {
      setForeground(Color.white);
    }

    String s = labelText;

    switch (s.charAt(0)) {
      case 'D':

        //it is a depot
        if (node.getChildCount() == 0) {
          setIcon(emptyDepotImage);
        }
        else {
          setIcon(fullDepotImage);
        }

        break;

      case 'B':

        //it is a bus
        if (node.getChildCount() > 2) {
          setIcon(fullTruckImage);
        }
        else {
          setIcon(truckImage);
        }

        break;

      case 'S':

        //it is a student
        setIcon(studentImage);

        break;

      case 'T':

        //it is the depotLL
        setIcon(emptyDepotImage);

        break;
    }

    // Add the text to the cell
    setText(labelText);

    return this;
  }

  /**
   * This is a hack to paint the background.  Normally a JLabel can
   * paint its own background, but due to an apparent bug or
   * limitation in the TreeCellRenderer, the paint method is
   * required to handle this.
   * @param g
   */
  public void paint(Graphics g) {
    Color bColor;
    Icon currentI = getIcon();

    // Set the correct background color
    bColor = bSelected ? SystemColor.textHighlight : Color.white;
    g.setColor(bColor);

    // Draw a rectangle in the background of the cell
    g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);

    super.paint(g);
  }
}
