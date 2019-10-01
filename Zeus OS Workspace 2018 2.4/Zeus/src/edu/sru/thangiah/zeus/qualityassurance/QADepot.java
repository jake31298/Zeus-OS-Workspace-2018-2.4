package edu.sru.thangiah.zeus.qualityassurance;

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
public class QADepot {

  private Vector trucks;
  private int index;
  private float x;
  private float y;
  private double demand;
  private double distance;

  
  public QADepot() {
    trucks = new Vector();
  }

  public int getIndex() {
	return index;
}

public float getX() {
	return x;
}

public float getY() {
	return y;
}

public double getDemand() {
	return demand;
}

public double getDistance() {
	return distance;
}

public void setTrucks(Vector trucks) {
	this.trucks = trucks;
}

public void setIndex(int ind) {
    index = ind;
  }

  public void setX(float xx) {
    x = xx;
  }

  public void setY(float yy) {
    y = yy;
  }

  public void setDemand(double dmd) {
    demand = dmd;
  }

  public void setDistance(double dist) {
    distance = dist;
  }

public Vector getTrucks() {
	return trucks;
}

  /*public boolean checkSiteDependance() {
    boolean status = true;
    for (int i = 0; i < trucks.size(); i++) {
      QATruck truck = (QATruck) trucks.elementAt(i);
      truck.checkSiteDependance();
    }
    return status;
     }*/

}
