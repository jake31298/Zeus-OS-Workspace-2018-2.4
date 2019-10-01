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
/** @todo Need to document the variables and the parameters */
public class QADepotLinkedList {

  private int demand;
  private double distance;
  private Vector depots;

  public QADepotLinkedList() {
    depots = new Vector();
  }

  public Vector getDepots() {
    return depots;
  }

public int getDemand() {
	return demand;
}

public void setDemand(int demand) {
	this.demand = demand;
}

public double getDistance() {
	return distance;
}

public void setDistance(double distance) {
	this.distance = distance;
}

public void setDepots(Vector depots) {
	this.depots = depots;
}

  /*public boolean checkSiteDependance() {
    boolean status = true;
    for (int i = 0; i < depots.size(); i++) {
      QADepot depot = (QADepot) depots.elementAt(i);
      status = status && depot.checkSiteDependance();
    }
    return status;
     }*/

}
