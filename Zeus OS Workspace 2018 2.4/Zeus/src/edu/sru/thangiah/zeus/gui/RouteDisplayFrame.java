package edu.sru.thangiah.zeus.gui;

import com.borland.jbcl.layout.*;

import edu.sru.thangiah.zeus.core.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.tree.*;

import com.brunchboy.util.swing.relativelayout.*;

import java.io.File;

import edu.sru.thangiah.zeus.gui.checkboxtree.*;
import edu.sru.thangiah.zeus.top.*;

import javax.swing.border.*;

/**
 * <p>Title: A redesign of the route display, to incorporate a fluid gui system.</p>
 * <p>Description: A redesign of the route display, to incorporate a fluid gui system.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Slippery Rock University</p>
 * @author Sam R. Thangiah
 * @version 1.0
 */
public class RouteDisplayFrame
    extends JInternalFrame{

  //Zeus Variables
  private DepotLinkedList mainDepots;

  //Interface Variables
  private JPanel RDFPanel = new JPanel();
  private JToolBar toolbar = new JToolBar();
  private ZeusRouteDisplay routeDisplay;
  private JCheckTree rdfTrucks;
  private RelativeLayout layout = new RelativeLayout(),
      panelLayout = new RelativeLayout();
  private JLabel xyPos;
  private JSplitPane jSP;
  private CheckTreeNode node;
  private JLabel jW = new JLabel("Initial");
  private JInternalFrame t;

  /**
   * Constructor which creates a new RouteDisplayFrame.
   */
  public RouteDisplayFrame() {
    super(ZeusGuiInfo.routeDisplayTitle, true, true, true, false);
    t = this;

    try {
      jbInit();
      constraintInit();
    } catch (Exception e) {
      e.printStackTrace();
    }

    jSP.setDividerLocation(600);
  }

  /**
   * Initialize graphical objects
   * @throws Exception error
   */
  private void jbInit() throws Exception {
    //this.setLayout(layout); -- Works on JBuilder 2006
     this.getContentPane().setLayout(layout);
    rdfTrucks = createTree((TOPDepotLinkedList) ZeusGuiInfo.mainDepots);
    jSP = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, RDFPanel, rdfTrucks);
    this.getContentPane().add(jSP, "JSP");
    //this.getContentPane().add(RDFPanel, "RDFPanel");
    RDFPanel.setLayout(panelLayout);

    routeDisplay = new ZeusRouteDisplay(rdfTrucks);
    routeDisplay.setLayout(new XYLayout());

    RDFPanel.add(jW, "JW");
    RDFPanel.add(routeDisplay, "routeDisplay");
    jW.setSize(150, 40);
    jW.setBackground(Color.WHITE);
    jW.setBorder(new LineBorder(Color.BLACK));
    jW.setVisible(false);

    routeDisplay.addMouseMotionListener(new ZRDMouseMotionListener());
    if(ZeusGuiInfo.showMouseXY) {
      xyPos = new JLabel("Mouse");
      RDFPanel.add(xyPos, "xyPosLabel");
    }

    this.setSize(1000, 600);
    this.show();
  }

  /**
   * Repaint the display.
   */
  public void displayRepaint(){
    RDFPanel.repaint();
  }

  /**
   *
   * <p>Title: </p>
   *
   * <p>Description: </p>
   *
   * <p>Copyright: Copyright (c) 2005</p>
   *
   * <p>Company: Slippery Rock University</p>
   *
   * @author Sam R. Thangiah
   * @version 1.0
   */
  public class ZRDMouseMotionListener
       extends MouseMotionAdapter {
      public void mouseMoved(MouseEvent evt) {
        // System.out.println(evt.getPoint().toString());
        int shipIndex = routeDisplay.checkMouseLocation((int)evt.getPoint().getX(), (int)evt.getPoint().getY());
        if(shipIndex != -1) {
          //System.out.println(System.currentTimeMillis() + ": Found shipment " + shipIndex);
          jW.setText("#" + shipIndex + " Demand: " + getShipDemand(shipIndex));
          System.out.println("Demand is "+getShipDemand(shipIndex));
          jW.setBounds((int)evt.getPoint().getX(), (int)evt.getPoint().getY(), 150, 40);
          jW.setVisible(true);
          RDFPanel.add(jW, "JW", 0);
          jW.setOpaque(true);
          jW.setBackground(Color.LIGHT_GRAY);
          panelLayout.addConstraint("JW", AttributeType.TOP, new AttributeConstraint(DependencyManager.ROOT_NAME, AttributeType.TOP, (int) evt.getPoint().getY() + 10));
          panelLayout.addConstraint("JW", AttributeType.LEFT, new AttributeConstraint(DependencyManager.ROOT_NAME, AttributeType.LEFT, (int) evt.getPoint().getX()));
          RDFPanel.repaint();
        }
        if(shipIndex == -1) {
          RDFPanel.remove(jW);
          RDFPanel.repaint();
        }
        if(ZeusGuiInfo.showMouseXY)
          xyPos.setText("(" + evt.getPoint().getX() + ", " + evt.getPoint().getY() + ")");
      }

    /**
     * getShipDemand
     *
     * @param shipIndex int
     * @return double
     */
    private double getShipDemand(int shipIndex) {
      Shipment s = ZeusGuiInfo.mainShipments.getHead().getNext();
      while(s != ZeusGuiInfo.mainShipments.getTail()) {
        if(s.getIndex() == shipIndex)
          return s.getDemand();
        s = s.getNext();
      }
      return -1.0;
    }
  }


  /**
   * Will create a check tree from the depot linked list. This check tree will
   * allow the selecting and deselecting of trucks to be shown in the display.
   * (De)selecting a depot will (de)select all trucks in that depot
   * @param mainDepots depot linked list to show
   * @return the jtree
   */
  private JCheckTree createTree(TOPDepotLinkedList mainDepots) {
    CheckTreeNode root = new CheckTreeNode(mainDepots, true);

    TOPDepot depot = mainDepots.getTOPHead().getNext();
    int colorCounter = 0;

    while (depot != mainDepots.getTail()) {
      CheckTreeNode dnode = new CheckTreeNode(depot, true, true);
      node = dnode; // for the action listener
      dnode.setActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          RDFPanel.repaint();
        }
      });

      TOPTruck truck = depot.getMainTrucks().getHead().getTOPNext();
      while (truck != depot.getMainTrucks().getTail()) {
        CheckTreeNode tnode = new CheckTreeNode(truck, true);
        Color truckColor = new Color(Color.HSBtoRGB(
            (float) colorCounter++ / (float) mainDepots.getNumTrucksUsed(),
            1, 1));
        tnode.setColor(truckColor);
        tnode.setSelected(truck.getIsUsed());
        tnode.setActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            RDFPanel.repaint();
          }
        });
        dnode.add(tnode);
        truck = truck.getTOPNext();
      }

      root.add(dnode);
      depot = depot.getNext();
    }

    return new JCheckTree(root);
  }


  private void constraintInit(){
    layout.addConstraint("JSP", AttributeType.TOP, new AttributeConstraint(DependencyManager.ROOT_NAME, AttributeType.TOP, 3));
    layout.addConstraint("JSP", AttributeType.BOTTOM, new AttributeConstraint(DependencyManager.ROOT_NAME, AttributeType.BOTTOM, -3));
    layout.addConstraint("JSP", AttributeType.RIGHT, new AttributeConstraint(DependencyManager.ROOT_NAME, AttributeType.RIGHT, -3));
    layout.addConstraint("JSP", AttributeType.LEFT, new AttributeConstraint(DependencyManager.ROOT_NAME, AttributeType.LEFT, 3));


    layout.addConstraint("RDFPanel", AttributeType.TOP, new AttributeConstraint(DependencyManager.ROOT_NAME, AttributeType.TOP, 3));
    layout.addConstraint("RDFPanel", AttributeType.BOTTOM, new AttributeConstraint(DependencyManager.ROOT_NAME, AttributeType.BOTTOM, -3));
    layout.addConstraint("RDFPanel", AttributeType.RIGHT, new AttributeConstraint(DependencyManager.ROOT_NAME, AttributeType.RIGHT, -3));
    layout.addConstraint("RDFPanel", AttributeType.LEFT, new AttributeConstraint(DependencyManager.ROOT_NAME, AttributeType.LEFT, 3));

    // Read the layout constraints
    XmlConstraintBuilder db_XMLBuilderLayout = new XmlConstraintBuilder();
    try {
      db_XMLBuilderLayout.addConstraints(new File("etc\\RouteDisplayFrameLayout.xml"), panelLayout);   // Read the contraints from the layout XML file.
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
