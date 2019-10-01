package edu.sru.thangiah.zeus.top.topgui;

import com.borland.jbcl.layout.*;
import edu.sru.thangiah.zeus.top.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import com.brunchboy.util.swing.relativelayout.*;
import java.io.File;
import edu.sru.thangiah.zeus.top.topgui.topcheckboxtree.*;
import javax.swing.border.*;

/**
 * <p>Title: A redesign of the route display, to incorporate a fluid gui system.</p>
 * <p>Description: A redesign of the route display, to incorporate a fluid gui system.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Slippery Rock University</p>
 * @author Sam R. Thangiah
 * @version 1.0
 * Modified by David Crissman
 */
public class TOPRouteDisplayFrame
    extends JInternalFrame{

  //Zeus Variables

  private TOPShipmentLinkedList mainShipments;

  //Interface Variables
  private JPanel RDFPanel = new JPanel();
  private JToolBar toolbar = new JToolBar();
  private TOPRouteDisplay routeDisplay;
  private TOPJCheckTree rdfTrucks;
  private RelativeLayout layout = new RelativeLayout(),
      panelLayout = new RelativeLayout();
  private JLabel xyPos;
  private JSplitPane jSP;
  private TOPCheckTreeNode node;
  private JLabel jW = new JLabel("Initial");
  private JInternalFrame t;

  /**
   * Constructor which creates a new RouteDisplayFrame.
   */
  public TOPRouteDisplayFrame(TOPShipmentLinkedList ships) {
    super(TOPGuiInfo.routeDisplayTitle, true, true, true, false);
    t = this;

    mainShipments = ships;
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
    this.setLayout(layout);
    rdfTrucks = createTree(TOPGuiInfo.mainDepots);
    jSP = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, RDFPanel, rdfTrucks);
    this.getContentPane().add(jSP, "JSP");
    RDFPanel.setLayout(panelLayout);

    routeDisplay = new TOPRouteDisplay(rdfTrucks, mainShipments);
    routeDisplay.setLayout(new XYLayout());

    RDFPanel.add(jW, "JW");
    RDFPanel.add(routeDisplay, "routeDisplay");
    jW.setSize(150, 40);
    jW.setBackground(Color.WHITE);
    jW.setBorder(new LineBorder(Color.BLACK));
    jW.setVisible(false);

    routeDisplay.addMouseMotionListener(new ZRDMouseMotionListener());
    if(TOPGuiInfo.showMouseXY) {
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
        int shipIndex = routeDisplay.checkMouseLocation((int)evt.getPoint().getX(), (int)evt.getPoint().getY());
        if(shipIndex != -1) {
          jW.setText("<html>#" + shipIndex + " Demand: " + getShipDemand(shipIndex) + "<br>X:" +
                     getShipX(shipIndex) + "<br>Y: " + getShipY(shipIndex) + "</html>");
          jW.setBounds((int)evt.getPoint().getX(), (int)evt.getPoint().getY(), 150, 40);
          jW.setVisible(true);
          RDFPanel.add(jW, "JW", 0);
          jW.setOpaque(true);
          jW.setBackground(Color.LIGHT_GRAY);
          if ((evt.getPoint().getY() - 50) >= 0) {
            panelLayout.addConstraint("JW", AttributeType.TOP, new AttributeConstraint(DependencyManager.ROOT_NAME, AttributeType.TOP, (int) evt.getPoint().getY() - 50));
          }
          else {
            panelLayout.addConstraint("JW", AttributeType.TOP, new AttributeConstraint(DependencyManager.ROOT_NAME, AttributeType.TOP, (int) evt.getPoint().getY() + 30));
          }

          if ((evt.getPoint().getX() - 120) >= 0) {
            panelLayout.addConstraint("JW", AttributeType.LEFT, new AttributeConstraint(DependencyManager.ROOT_NAME, AttributeType.LEFT, (int) evt.getPoint().getX() - 120));
          }
          else {
            panelLayout.addConstraint("JW", AttributeType.LEFT, new AttributeConstraint(DependencyManager.ROOT_NAME, AttributeType.LEFT, (int) evt.getPoint().getX()));
          }


          RDFPanel.repaint();
        }
        if(shipIndex == -1) {
          RDFPanel.remove(jW);
          RDFPanel.repaint();
        }
        if(TOPGuiInfo.showMouseXY)
          xyPos.setText("(" + evt.getPoint().getX() + ", " + evt.getPoint().getY() + ")");
      }

    /**
     * getShipDemand
     *
     * @param shipIndex int
     * @return double
     */
    private double getShipDemand(int shipIndex) {
      TOPShipment s = TOPGuiInfo.mainShipments.getTOPHead();
      while(s != null) {
        if(s.getIndex() == shipIndex)
          return s.getDemand();
        s = s.getTOPNext();
      }
      return -1.0;
    }

    private double getShipX(int shipIndex) {
      TOPShipment s = TOPGuiInfo.mainShipments.getTOPHead();
      while(s != null) {
        if(s.getIndex() == shipIndex)
          return s.getXCoord();
        s = s.getTOPNext();
      }
      return -1.0;
    }

    private double getShipY(int shipIndex) {
      TOPShipment s = TOPGuiInfo.mainShipments.getTOPHead();
      while(s != null) {
        if(s.getIndex() == shipIndex)
          return s.getYCoord();
        s = s.getTOPNext();
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
   * Modified by David Crissman
   */
  private TOPJCheckTree createTree(TOPDepotLinkedList mainDepots) {
    TOPCheckTreeNode root = new TOPCheckTreeNode(mainDepots, true);

    TOPDepot depot = mainDepots.getTOPHead();
    int colorCounter = 0;

    while (depot != null) {
      TOPCheckTreeNode dnode = new TOPCheckTreeNode(depot, true, true);
      node = dnode; // for the action listener
      dnode.setActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          RDFPanel.repaint();
        }
      });

      TOPTruck truck = depot.getTOPMainTrucks().getTOPHead();

      while (truck != null) {
        TOPCheckTreeNode tnode = new TOPCheckTreeNode(truck, true);
        Color truckColor = new Color(Color.HSBtoRGB(
            (float) colorCounter++ / (float) mainDepots.getNumTrucksUsed(),
            1, 1));

        //Grey out the trucks which aren't team members
        if (truck.getIsTeamMember() == true) {
          tnode.setColor(truckColor);
        }
        else {
          tnode.setColor(Color.lightGray);
        }

        tnode.setActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            RDFPanel.repaint();
          }
        });
        dnode.add(tnode);
        truck = truck.getTOPNext();
      }

      root.add(dnode);
      depot = depot.getTOPNext();
    }

    return new TOPJCheckTree(root);
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
