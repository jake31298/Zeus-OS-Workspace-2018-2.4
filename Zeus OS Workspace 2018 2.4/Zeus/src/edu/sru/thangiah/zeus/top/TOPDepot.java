package edu.sru.thangiah.zeus.top;

import edu.sru.thangiah.zeus.core.Attributes;
//import the parent class
import edu.sru.thangiah.zeus.core.Depot;
import edu.sru.thangiah.zeus.core.TruckLinkedList;

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
    extends Depot
    implements java.io.Serializable, java.lang.Cloneable {
	
  public TOPDepot() {
    //set the attributes and the truck linked list
    setAttributes (new TOPAttributes());
    setMainTrucks(new TOPTruckLinkedList());
  }

  /**
   * Constructor. Creates the depot
   * @param d depot number
   * @param x x-coordinate
   * @param y y-coordinate
   */
  public TOPDepot(int d, float x, float y) {
    //The x,y and d (index number)
    //super(d, x, y);
    setDepotNum(d);
    setXCoord(x);
    setYCoord(y);

    setAttributes(new TOPAttributes());
    setMainTrucks(new TOPTruckLinkedList());
  }
  
  /**
   * Returns the truck linked list
   * @return main trucks
   */
  public TOPTruckLinkedList getMainTrucks() {
    return (TOPTruckLinkedList)super.getMainTrucks();
  }
  public TOPTruckLinkedList getTOPMainTrucks() {
	    return (TOPTruckLinkedList)getMainTrucks();
	  }
  public TOPAttributes getTOPAttributes() {
	    return (TOPAttributes)getAttributes();
	  }
  /**
 * Returns the next depot in the linked list
 * @return next depot
 */
public TOPDepot getNext() {
  return (TOPDepot)super.getNext();
}

public Object clone() {
    TOPDepot clonedDepot = new TOPDepot((int)this.getDepotNum(), (float)this.getxCoord(), (float)this.getyCoord());

    clonedDepot.setAttributes((Attributes)this.getTOPAttributes().clone());
    clonedDepot.setMainTrucks((TruckLinkedList)this.getMainTrucks().clone());

    return clonedDepot;
  }
}
