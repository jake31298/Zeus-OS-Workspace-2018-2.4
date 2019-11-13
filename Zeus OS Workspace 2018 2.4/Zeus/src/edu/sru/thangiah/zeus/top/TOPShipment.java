package edu.sru.thangiah.zeus.top;

//import the parent class
import edu.sru.thangiah.zeus.core.Shipment;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class TOPShipment
    extends Shipment
    implements java.io.Serializable, java.lang.Cloneable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private double extraVariable;
private boolean checked;
private boolean unreachable;

  public TOPShipment() {
  }

  /**
   * Constructor
   * @param i index
   * @param x x-coordinate
   * @param y y-coordinate
   * @param q demand
   * @param type service time
   * @param vComb truck type
   * @param cuComb pick up point name
   */
  public TOPShipment(int i, double x, double y, int s, String t,
          String p) {
//super(i, x, y, q, d, t, p);
setIndex(i);
setXCoord(x);
setYCoord(y);
setDemand(s);
//serviceTime = d;
//setTruckTypeNeeded(t); //Does not appear to be needed with TOP
//pickUpPointName = p;

}
  

  //calls the super in Shipment
  public TOPShipment(int i, float x, float y, int s,
                     String t,
                     int[] vComb, int[][] cuComb) {
    //super(ind, x, y, d, q, e, comb, t, vComb, cuComb);
	  setIndex(i);
	    setXCoord(x);
	    setYCoord(y);
	    setDemand(s);
    //serviceTime = d;

   // frequency = e;
   // noComb = comb;
	    setTruckTypeNeeded(t);
    //visitComb = vComb;
    //currentComb = cuComb;
    setIsAssigned(false);
    setIsSelected(false);
    setNext(null);

    //the combinations to be created should not exceed the maximum allowable
    //combination
    /*for (int i = 0; i < noComb; i++) {
      visitComb[i] = vComb[i];
    }*/

    extraVariable = Math.random();
  }

  public TOPShipment getTOPNext() {
		return (TOPShipment) this.getNext();
	}
  
  //calls the super in shipment
  public TOPShipment(int i, int x, int y, int s,
		  String t,
		  int[] vComb, int[][] cuComb) {
	  //super(ind, x, y, d, q, e, comb, t, vComb, cuComb);
	  //serviceTime = d;
	  //frequency = e;
	  //noComb = comb;
	  //visitComb = vComb;
	  //currentComb = cuComb;

	  setIndex(i);
	  setXCoord(x);
	  setYCoord(y);
	  setDemand(s);
	  //serviceTime = d;

	  // frequency = e;
	  // noComb = comb;
	  setTruckTypeNeeded(t);
	  //visitComb = vComb;
	  //currentComb = cuComb;
	  setIsAssigned(false);

	  setNext(null);

	  //the combinations to be created should not exceed the maximum allowable
    //combination
    /*for (int i = 0; i < noComb; i++) {
      visitComb[i] = vComb[i];
    }*/

    extraVariable = Math.random();
  }
  
  public TOPShipment(int i, float x, float y, int s,String t) {
	  //super(ind, x, y, d, q, e, comb, t, vComb, cuComb);
	  //serviceTime = d;
	  //frequency = e;
	  //noComb = comb;
	  //visitComb = vComb;
	  //currentComb = cuComb;

	  setIndex(i);
	  setXCoord(x);
	  setYCoord(y);
	  setDemand(s);
	  //serviceTime = d;

	  // frequency = e;
	  // noComb = comb;
	  setTruckTypeNeeded(t);
	  //visitComb = vComb;
	  //currentComb = cuComb;
	  setIsAssigned(false);

	  setNext(null);

	  //the combinations to be created should not exceed the maximum allowable
    //combination
    /*for (int i = 0; i < noComb; i++) {
      visitComb[i] = vComb[i];
    }*/

    extraVariable = Math.random();
  }

  /**
   * Returns the value of extraVariable
   * @return extraVariable int
   */
  public double getExtraVariable() {
    //get the next shipment from the shipment linked list
    //but return it as a TOPShipment
    return extraVariable;
  }
  
  /**
   * Marks the shipment as being outside the trucks' reachable area
   * @param val boolean
   * Added by David Crissman
   */
  public void setUnreachable(boolean val) {
    unreachable = val;
  }

  /**
   * Returns whether or not the shipment is within the trucks' reachable area
   * @return boolean
   * Added by David Crissman
   */
  public boolean getUnreachable() {
    return unreachable;
  }

  /**
   * Marks the shipment as having been checked on the current pass
   * @param val boolean
   * Added by David Crissman
   */
  public void setChecked(boolean val) {
    checked = val;
  }

  /**
   * Returns whether or not the shipment has been checked on the current pass
   * @return boolean
   * Added by David Crissman
   */
  public boolean getChecked() {
    return checked;
  }
  
  public Object clone() {
		TOPShipment clonedShipment = new TOPShipment();

		clonedShipment.setDemand(this.getDemand());
		clonedShipment.setIndex(this.getIndex());
		clonedShipment.setIsAssigned(this.getIsAssigned());
		//clonedShipment.pickUpPointName = this.pickUpPointName;
		//clonedShipment.serviceTime = this.serviceTime;
		clonedShipment.setTruckTypeNeeded(this.getTruckTypeNeeded());
		clonedShipment.setXCoord(this.getXCoord());
		clonedShipment.setYCoord(this.getYCoord());
		clonedShipment.setChecked(this.getChecked());
		clonedShipment.setUnreachable(this.getUnreachable());
		return clonedShipment;
	}

}
