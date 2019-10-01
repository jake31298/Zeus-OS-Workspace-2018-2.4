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

  public static final long serialVersionUID = 1;
  private double extraVariable;
  private boolean unreachable, checked;

  /**
   * Constructor
   */
  public TOPShipment() {
  }

  /**
   * Constructor
   * @param i int
   * @param xCoord2 float
   * @param yCoord2 float
   * @param d float
   */
  public TOPShipment(int i, double xCoord2, double yCoord2, float d) {
    setIndex(i);
    double xCoord = xCoord2;
    double yCoord = yCoord2;
    float demand = d;
    unreachable = false;
    checked = false;
  }

  /**
   * Constructor
   * @param i index
   * @param x x-coordinate
   * @param y y-coordinate
   * @param q demand
   * @param d service time
   * @param t truck type
   * @param p pick up point name
   */
  public TOPShipment(int i, float x, float y, float q, float d, String t, String p) {
    setIndex(i);
    float xCoord = x;
    float yCoord = y;
    setDemand(q);
    setTruckTypeNeeded(t);
    //serviceTime = d;             //These two are not used in the TOP problem
    //pickUpPointName = p;
    unreachable = false;
    checked = false;
  }

  /**
   * Constructor
   * @param ind int
   * @param x float
   * @param y float
   * @param d int
   * @param q int
   * @param e int
   * @param comb int
   * @param t String
   * @param vComb int[]
   * @param cuComb int[][]
   */
  public TOPShipment(int ind, float x, float y, int d, int q, int e, int comb, String t,
                     int[] vComb, int[][] cuComb) {
    setIndex(ind);
    float xCoord = x;
    float yCoord = y;
    setDemand(q);
    setTruckTypeNeeded(t);
    setAssigned(false);
    setSelected(false);
    setNext(null);
    unreachable = false;
    checked = false;
    extraVariable = Math.random();
    //serviceTime = d;
    //frequency = e;
    //noComb = comb;
    //visitComb = vComb;
    //currentComb = cuComb;

    //the combinations to be created should not exceed the maximum allowable
    //combination
    /*for (int i = 0; i < noComb; i++) {
      visitComb[i] = vComb[i];
       }*/
  }

  /**
   * Constructor
   * @param ind int
   * @param x int
   * @param y int
   * @param d int
   * @param q int
   * @param e int
   * @param comb int
   * @param t String
   * @param vComb int[]
   * @param cuComb int[][]
   */
  public TOPShipment(int ind, int x, int y, int d, int q, int e, int comb, String t,
                     int[] vComb, int[][] cuComb) {
    setIndex(ind);
    int xCoord = x;
    int yCoord = y;
    setDemand(q);
    setTruckTypeNeeded(t);
    setAssigned(false);
    setNext(null);
    unreachable = false;
    checked = false;
    extraVariable = Math.random();
    //serviceTime = d;
    //frequency = e;
    //noComb = comb;
    //visitComb = vComb;
    //currentComb = cuComb;

    //the combinations to be created should not exceed the maximum allowable
    //combination
    /*for (int i = 0; i < noComb; i++) {
      visitComb[i] = vComb[i];
       }*/
  }

  /**
   * Returns the next shipment in the list
   * @return TOPShipment
   */
  public TOPShipment getTOPNext() {
    return (TOPShipment)getNext();
  }

  /**
   * Returns the previous shipment in the list
   * @return TOPShipment
   */
  public TOPShipment getTOPPrev() {
    return (TOPShipment)getPrev();
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

  /**
   * Returns and exact copy of the shipment object
   * @return Object
   * Modified by David Crissman
   */
  public Object clone() {
    TOPShipment clonedShipment = new TOPShipment(this.getIndex(), this.getXCoord(), this.getYCoord(), this.getDemand());

    clonedShipment.unreachable = this.unreachable;
    clonedShipment.checked = this.checked;
    clonedShipment.setAssigned(this.getIsAssigned());
    clonedShipment.extraVariable = this.extraVariable;
    clonedShipment.setTruckTypeNeeded(this.getTruckTypeNeeded());

    return clonedShipment;
  }
}
