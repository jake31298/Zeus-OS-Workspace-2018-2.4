package edu.sru.thangiah.zeus.top.topgui;

import edu.sru.thangiah.zeus.top.*;
import java.awt.*;
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
public class TOPShipmentFrame
    extends JInternalFrame {
  //Zeus Variables
  private TOPShipmentLinkedList mainShipments;

  //Interface Variables
  private DefaultMutableTreeNode root = null;
  private JTree tree = null;
  private JScrollPane treeScrollPane;

  /**
   * Constructor
   * @param mS the shipment linked list to display
   */
  public TOPShipmentFrame(TOPShipmentLinkedList mS) {
    super(TOPGuiInfo.shipmentPaneTitle, true, false, false, true);
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
  private JTree makeTree(TOPShipmentLinkedList s) {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode(
        "The Shipment Linked List");
    TOPShipment shipment = s.getTOPHead();
    String shipInfo = "";
    int nextIndex = 0, prevIndex = 0;

    while (shipment != null) {
      if(shipment.getTOPPrev() == null)
        prevIndex = -1;
      else if(shipment.getTOPNext() == null)
        nextIndex = -1;
      else {
        nextIndex = shipment.getTOPNext().getIndex();
        prevIndex = shipment.getTOPPrev().getIndex();
      }

      shipInfo = "#" + shipment.getIndex() + "(" + shipment.getXCoord() + "," +
          shipment.getYCoord() + ") | Demand: " +
          shipment.getDemand() + " | Prev: " + prevIndex +
          " | Next: " + nextIndex;
      DefaultMutableTreeNode ship = new DefaultMutableTreeNode(shipInfo);
      root.add(ship);
      shipment = shipment.getTOPNext();
    }

    JTree tree = new JTree(root);

    return tree;
  }
}
