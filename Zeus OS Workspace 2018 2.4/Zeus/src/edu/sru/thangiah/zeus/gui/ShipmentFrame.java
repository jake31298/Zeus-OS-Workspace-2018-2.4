package edu.sru.thangiah.zeus.gui;

import com.borland.jbcl.layout.*;
import edu.sru.thangiah.zeus.core.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;

/**
 * Frame that holds all the shipment information
 * <p>Title: ShipmentFrame</p>
 * <p>Description: Frame that holds all the shipment information</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class ShipmentFrame
    extends JInternalFrame {
  //Zeus Variables
  private ShipmentLinkedList mainShipments;

  //Interface Variables
  private DefaultMutableTreeNode root = null;
  private JTree tree = null;
  private JScrollPane treeScrollPane;

  /**
   * Constructor
   * @param mS the shipment linked list to display
   */
  public ShipmentFrame(ShipmentLinkedList mS) {
    super(ZeusGuiInfo.shipmentPaneTitle, true, false, false, true);
    mainShipments = mS;

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Initialize graphical objects
   * @throws Exception error
   */
  private void jbInit() throws Exception {
    tree = makeTree(mainShipments);
    treeScrollPane = new JScrollPane(tree);
    this.getContentPane().add(treeScrollPane, BorderLayout.CENTER);
    this.setSize(400, 600);
  }

  /**
   * Makes a tree from the shipment linked list
   * @param s the shipment linked list
   * @return a tree of the depot linked list
   */
  private JTree makeTree(ShipmentLinkedList s) {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode(
        "The Shipment Linked List");
    Shipment shipment = s.getHead().getNext();
    String shipInfo = "";
    int nextIndex = 0, prevIndex = 0;

    while (shipment != s.getTail()) {
      if(shipment.getPrev() == null)
        prevIndex = -1;
      else if(shipment.getNext() == null)
        nextIndex = -1;
      else {
        nextIndex = shipment.getNext().getIndex();
        prevIndex = shipment.getPrev().getIndex();
      }

      shipInfo = "#" + shipment.getIndex() + "(" + shipment.getXCoord() + "," +
          shipment.getYCoord() + ") | Demand: " +
          shipment.getDemand() + " | Prev: " + prevIndex +
          " | Next: " + nextIndex;
      DefaultMutableTreeNode ship = new DefaultMutableTreeNode(shipInfo);
      root.add(ship);
      shipment = shipment.getNext();
    }

    JTree tree = new JTree(root);

    //tree.setCellRenderer(new CustomCellRenderer());
    return tree;
  }
}
