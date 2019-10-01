package edu.sru.thangiah.zeus.top;

//import the parent class
import edu.sru.thangiah.zeus.core.Truck;

/**
 *
 * <p>Title: </p> TOPTruck
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Slippery Rock University</p>
 *
 * @author Sam R. Thangiah
 * @version 1.0
 */
public class TOPTruck
    extends Truck
    implements java.io.Serializable, java.lang.Cloneable {

  public static final long serialVersionUID = 1;
  private float endDepotX;      //X-coordinate of the depot where the truck will end its route
  private float endDepotY;      //Y-coordinate of the depot where the truck will end its route
  private boolean isTeamMember; //True if the truck is ultimately considered a member of the team.

  /**
   * Constructor
   * Modified by Pete Schallot
   */
  public TOPTruck() {
    //Assign the attributes
    setMainNodes(new TOPNodesLinkedList());
    setAttributes(new TOPAttributes());
    isTeamMember = false;
  }

  /**
   * Constructor
   * @param tt truck type
   * @param depotX depot's x coordinate
   * @param depotY depot's y coordinate
   * Modified by Pete Schallot
   */
  public TOPTruck(TOPTruckType tt, float depX, float depY) {
    setAttributes(new TOPAttributes());
    setDepotX(depX);
    setDepotY(depY);
    endDepotX = depX;
    endDepotY = depY;
    setTruckNum(TOPProblemInfo.numTrucks++);
    setTruckType(tt);
    isTeamMember = false;

    setMainNodes(new TOPNodesLinkedList(tt, depX, depY, depX, depY, getTruckNum()));
  }

  /**
   * Constructor
   * @param tt TOPTruckType
   * @param startDepX float
   * @param startDepY float
   * @param endDepX float
   * @param endDepY float
   * Added by Pete Schallot
   */
  public TOPTruck(TOPTruckType tt, float startDepX, float startDepY, float endDepX, float endDepY) {
    setAttributes(new TOPAttributes());
    setDepotX(startDepX);
    setDepotY(startDepY);
    endDepotX = endDepX;
    endDepotY = endDepY;
    setTruckNum(TOPProblemInfo.numTrucks++);
    setTruckType(tt);
    isTeamMember = false;

    setMainNodes(new TOPNodesLinkedList(tt, startDepX, startDepY, endDepX, endDepY, getTruckNum()));
  }

  /**
   * Returns the visit nodes linked list (route)
   * @return route
   */
  public TOPNodesLinkedList getTOPMainNodes() {
    return (TOPNodesLinkedList)getMainNodes();
  }

  /**
   * Returns the previous truck in the linked list
   * @return next depot
   */
  public TOPTruck getTOPPrev() {
    return (TOPTruck)getPrev();
  }

  /**
   * Returns the next depot in the linked list
   * @return next depot
   */
  public TOPTruck getTOPNext() {
    return (TOPTruck)getNext();
  }
  /**
   * Returns the attributes of the truck
   * @return TOPAttributes
   */
  public TOPAttributes getTOPAttributes() {
    return (TOPAttributes)getAttributes();
  }

  /**
   * Returns the truck's type
   * @return TOPTruckType
   */
  public TOPTruckType getTOPTruckType() {
    return (TOPTruckType)getTruckType();
  }

  /**
   * Specify whether or not this truck will be used as a member of the final team
   * @param b boolean
   */
  public void setIsTeamMember(boolean b) {
    this.isTeamMember = b;
  }

  /**
   * Returns whether or not this truck is a member of the final team
   * @return boolean
   */
  public boolean getIsTeamMember() {
    return this.isTeamMember;
  }


  /**
   * Creates a clone of the current trucks. Does not create the next and prev
   * links, that is the responsibility of the truck linked list clone() function
   * @return Object truck clone
   */
  public Object clone() {
    TOPTruck clonedTruck = new TOPTruck();

    clonedTruck.setAttributes((TOPAttributes)this.getTOPAttributes().clone());
    clonedTruck.setDepotX(this.getDepotX());
    clonedTruck.setDepotY(this.getDepotY());
    clonedTruck.endDepotX = this.endDepotX;
    clonedTruck.endDepotY = this.endDepotY;
    clonedTruck.setMainNodes((TOPNodesLinkedList)this.getTOPMainNodes().clone());
    clonedTruck.setTruckNum(this.getTruckNum());
    clonedTruck.isTeamMember = this.isTeamMember;
    clonedTruck.setTruckType((TOPTruckType)this.getTOPTruckType().clone());

    return clonedTruck;
  }

  /**
   * Returns whether or not the truck is empty
   * @return true - the bus is empty, false - the bus is not empty
   */
  public boolean isEmpty() {
    int size = 0;
    TOPNodes cell = this.getTOPMainNodes().getTOPHead();
    TOPNodes temp = cell;

    while (cell != null) {
      size++;
      cell = cell.getTOPNext();
      if (cell == temp) {
	break;
      }
    }

    if ( (getMainNodes() == null) || (size <= 2)) {
      return true;
    }
    else {
      return false;
    }
  }
}
