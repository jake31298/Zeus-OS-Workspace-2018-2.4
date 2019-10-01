package edu.sru.thangiah.zeus.top.topgui;

import java.awt.*;
import java.awt.geom.CubicCurve2D;

import javax.swing.*;

import edu.sru.thangiah.zeus.top.topgui.topcheckboxtree.*;
import edu.sru.thangiah.zeus.top.*;

/**
 * <p>Title: TOPRouteDisplay</p>
 * <p>Description: Tour Orienteering Route Display Panel</p>
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: Slippery Rock University</p>
 *
 * @author Sam R. Thangiah
 * @version 1.0
 */
public class TOPRouteDisplay extends JPanel {
  private TOPJCheckTree jCheckTree;
  private TOPShipmentLinkedList mainShipments;
  private int bigX;
  private int smallX;
  private int bigY;
  private int smallY;

  /**
   * Create a new display
   * @param jct JCheckTree
   */
  public TOPRouteDisplay(TOPJCheckTree jct, TOPShipmentLinkedList ship) {
    jCheckTree = jct;
    mainShipments = ship;

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Create the visual elements of the display
   * @throws Exception something went wrong
   */
  private void jbInit() throws Exception {
    this.setBackground(Color.white);
    this.setSize(new Dimension(400, 400));
  }

  /**
   * Paint the routes
   * @param g graphics class
   * Modified by David Crissman
   */
  public void paint(Graphics g) {
    TOPShipment currentShipment;

    g.setColor(Color.white);
    g.fillRect(2, 2, this.getWidth() - 4, this.getHeight() - 4);

    //Loop through the shipment linked list and find the biggest x and y
    bigX = 0;
    bigY = 0;
    smallX = Integer.MAX_VALUE;
    smallY = Integer.MAX_VALUE;

    TOPCheckTreeNode root = (TOPCheckTreeNode) jCheckTree.getModel().getRoot();
    TOPDepotLinkedList mainDepots = (TOPDepotLinkedList) root.getUserObject();

    currentShipment = mainShipments.getTOPHead();
    while (currentShipment != null) {
      if (currentShipment.getXCoord() > bigX) {
        bigX = (int)currentShipment.getXCoord();
      }

      if (currentShipment.getYCoord() > bigY) {
        bigY = (int)currentShipment.getYCoord();
      }

      if (currentShipment.getXCoord() < smallX) {
        smallX = (int)currentShipment.getXCoord();
      }

      if (currentShipment.getYCoord() < smallY) {
        smallY = (int)currentShipment.getYCoord();
      }


      currentShipment = currentShipment.getTOPNext();
    }

    //loop through the depot linked list again and paint the points
    for (int i = 0; i < root.getChildCount(); i++) {
      TOPCheckTreeNode dnode = (TOPCheckTreeNode) root.getChildAt(i);

      if (dnode.isSelected()) {
        TOPDepot depot = (TOPDepot) dnode.getUserObject();
        int x = transX( (int) depot.getXCoord());
        int y = transY( (int) depot.getYCoord());
        int[] xs = {
            x, x + 7, x - 7};
        int[] ys = {
            y - 7, y, y};

        //draw a triangle for the depot
        g.setColor(Color.black);
        g.fillPolygon(new Polygon(xs, ys, 3));

        for (int j = 0; j < dnode.getChildCount(); j++) {
          TOPCheckTreeNode tnode = (TOPCheckTreeNode) dnode.getChildAt(j);
          TOPTruck truck = (TOPTruck) tnode.getUserObject();

          if (tnode.isSelected()) {
            TOPNodes cell = truck.getTOPMainNodes().getTOPHead();

            g.setColor(tnode.getColor());

            while (cell != null) {
              //dont redraw the cell again if its a depot
              if (cell.getIndex() > 0) {
                x = transX( (int) cell.getShipment().getXCoord());
                y = transY( (int) cell.getShipment().getYCoord());
                g.fillOval(x - 4, y - 4, 8, 8);
              }

              TOPNodes next = cell.getTOPNext();

              if (next != null) {
                //draw the path
                g.drawLine(transX( (int) cell.getShipment().getXCoord()),
                           transY( (int) cell.getShipment().getYCoord()),
                           transX( (int) next.getShipment().getXCoord()),
                           transY( (int) next.getShipment().getYCoord()));
              }

              cell = next;
            }
          }

          //Draw an X to represent each unreachable shipment
	  g.setColor(Color.black);
	  currentShipment = mainShipments.getTOPHead();
	  while (currentShipment != null) {
	    if (currentShipment.getUnreachable() == true) {
	      g.drawLine(transX( (int)currentShipment.getXCoord()) - 4,
			 transY( (int)currentShipment.getYCoord()) - 4,
			 transX( (int)currentShipment.getXCoord()) + 4,
			 transY( (int)currentShipment.getYCoord()) + 4);

	      g.drawLine(transX( (int)currentShipment.getXCoord()) + 4,
			 transY( (int)currentShipment.getYCoord()) - 4,
			 transX( (int)currentShipment.getXCoord()) - 4,
			 transY( (int)currentShipment.getYCoord()) + 4);

	    }
	    currentShipment = currentShipment.getTOPNext();
	  }
        }
      }
    }
  }

  /**
   * will translate the X coordinate from Zeus into the X coordinate for the
   * display
   * @param x x coordinate to translate
   * @return x coordinate to draw
   */
  private int transX(int x) {
    float negOffset = 0;
    if(smallX < 0){
      negOffset = smallX;
    }
    return (int)(( (x-negOffset) * (this.getWidth()-10)) / (bigX-negOffset));
  }


  /**
   * Will translate the y coordinate from Zeus into the X coordinate for the
   * display.
   * @param y y coordinate to translate
   * @return y coordinate to draw
   */
  private int transY(int y) {
    float negOffset = 0;
    //if(smallY < 0){
      negOffset = smallY;
    //}
    return (int)(((y-negOffset) * (this.getHeight()-10)) / (bigY-negOffset));
  }

  public int checkMouseLocation(int x, int y){
    int currX, currY;
    TOPCheckTreeNode root = (TOPCheckTreeNode) jCheckTree.getModel().getRoot();
    //loop through the depot linked list and check each node
    for (int i = 0; i < root.getChildCount(); i++) {
      TOPCheckTreeNode dnode = (TOPCheckTreeNode) root.getChildAt(i);

      if (dnode.isSelected()) {
        TOPDepot depot = (TOPDepot) dnode.getUserObject();
        for (int j = 0; j < dnode.getChildCount(); j++) {
          TOPCheckTreeNode tnode = (TOPCheckTreeNode) dnode.getChildAt(j);
          TOPTruck truck = (TOPTruck) tnode.getUserObject();
          if (tnode.isSelected()) {
            TOPNodes cell = truck.getTOPMainNodes().getTOPHead();
            while (cell != null) {
              //dont redraw the cell again if its a depot
              if (cell.getIndex() > 0) {
                currX = transX( (int) cell.getShipment().getXCoord());
                currY = transY( (int) cell.getShipment().getYCoord());
                if((currX-4 <= x &&
                    currX+4 >= x) &&
                   (currY-4 <= y &&
                    currY+4 >= y))
                  return cell.getShipment().getIndex();
              }
              TOPNodes next = cell.getTOPNext();
              cell = next;
            }
          }
        }
      }
    }
    return -1;
  }
}
