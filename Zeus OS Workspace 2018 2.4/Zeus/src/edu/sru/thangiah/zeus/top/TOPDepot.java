package edu.sru.thangiah.zeus.top;

//import the parent class
import edu.sru.thangiah.zeus.core.Depot;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */
public class TOPDepot
    extends Depot implements java.io.Serializable, java.lang.Cloneable {
  public static final long serialVersionUID = 1;
  public double maxCost = -1;
  public double minCost = -1;

  public TOPDepot() {
    setAttributes(new TOPAttributes());
    setMainTrucks(new TOPTruckLinkedList());
  }

  /**
   * Constructor. Creates the depot
   * @param d depot number
   * @param e x-coordinate
   * @param f y-coordinate
   */
  public TOPDepot(int d, double e, double f) {
    setDepotNum(d);
    setxCoord(e);
    setyCoord(f);

    setAttributes(new TOPAttributes());
    setMainTrucks(new TOPTruckLinkedList());
  }

  /**
   * Returns the truck linked list
   * @return main trucks
   */
  public TOPTruckLinkedList getTOPMainTrucks() {
    return (TOPTruckLinkedList)getMainTrucks();
  }

  /**
   * Returns the next depot in the linked list
   * @return next depot
   */
  public TOPDepot getTOPNext() {
    return (TOPDepot)getNext();
  }

  public TOPAttributes getTOPAttributes() {
    return (TOPAttributes)getAttributes();
  }

  /**
   * Returns an exact copy of the depot object
   * @return Object
   */
  public Object clone() {
    TOPDepot clonedDepot = new TOPDepot(this.getDepotNum(), this.getxCoord(), this.getyCoord());

    clonedDepot.setAttributes((TOPAttributes)this.getTOPAttributes().clone());
    clonedDepot.setMainTrucks((TOPTruckLinkedList)this.getTOPMainTrucks().clone());

    return clonedDepot;
  }
}
