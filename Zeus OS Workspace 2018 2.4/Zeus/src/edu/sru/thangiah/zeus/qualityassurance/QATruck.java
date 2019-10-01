package edu.sru.thangiah.zeus.qualityassurance;

import edu.sru.thangiah.zeus.core.*;
import java.util.Vector;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class QATruck {

  private Vector nodes;
  private int index;
  private String type;
  private double demand;
  private double distance;
  private double maxDemand;
  private double maxDistance;

  /** @todo Need to document the variables and the parameters */
  public QATruck() {
    nodes = new Vector();
  }

//  public int getIndex() {
//    return index;
//  }

  public Vector getNodes(){
   return nodes;
 }

  public void setIndex(int ind) {
    index = ind;
  }

  public int getIndex() {
	return index;
}

public String getType() {
	return type;
}

public double getDemand() {
	return demand;
}

public double getDistance() {
	return distance;
}

public double getMaxDemand() {
	return maxDemand;
}

public double getMaxDistance() {
	return maxDistance;
}

public void setNodes(Vector nodes) {
	this.nodes = nodes;
}

public void setType(String ty) {
    type = ty;
  }

  public void setDemand(double dmd) {
    demand = dmd;
  }

  public void setDistance(double dist) {
    distance = dist;
  }

  public void setMaxDemand(double mDmd) {
    maxDemand = mDmd;
  }

  public void setMaxDistance(double mDist) {
    maxDistance = mDist;
  }

  /*public boolean checkSiteDependance() {
    boolean status = true;
    for (int i = 0; i < nodes.size(); i++) {
      QANode node = (QANode) nodes.elementAt(i);
      if (!node.type.equals(type) && !node.type.equals("D")) {
        Settings.printDebug(Settings.ERROR,
                            "Customer " + node.index + "(" + node.type +
                            ") cannot be serviced by truck " + index + "(" +
                            type + ")");
        status = false;
      }
    }
    return status;
     }*/

}
