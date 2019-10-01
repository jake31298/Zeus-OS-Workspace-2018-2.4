package edu.sru.thangiah.zeus.qualityassurance;

import edu.sru.thangiah.zeus.core.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */
/** @todo Need to document the variables and the parameters */
public class QANode {

  private int index;
  private double demand;
  private float x;
  private float y;
  private String type;

  protected QANode() {
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


  public void setIndex(int ind) {
    index = ind;
  }

  public void setDemand(double dmd) {
    demand = dmd;
  }

  public void setX(float xx) {
    x = xx;
  }

  public void setY(float yy) {
    y = yy;
  }

  public void setType(String ty) {
    type = ty;
  }

public String getType() {
	return type;
}
}
