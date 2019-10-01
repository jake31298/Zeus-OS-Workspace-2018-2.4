package edu.sru.thangiah.zeus.top;

//import the parent class
import edu.sru.thangiah.zeus.core.TruckLinkedList;
import edu.sru.thangiah.zeus.core.Settings;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * Modified to handle TOPs by Peter Schallot.
 * @version 2.0
 */
public class TOPTruckLinkedList
    extends TruckLinkedList implements java.io.Serializable, java.lang.Cloneable {
  public static final long serialVersionUID = 1;
private TOPTruck head;
private TOPTruck tail;

  /**
   * Constructor
   */
  public TOPTruckLinkedList() {
    //Assign the attributes
    setAttributes(new TOPAttributes());
  }

  /**
   * Returns the first truck in the linked list
   * @return first truck
   */
  public TOPTruck getTOPHead() {
    return (TOPTruck)getHead();
  }

  /**
   * Returns the last truck in the linked list
   * @return last truck
   */
  public TOPTruck getTOPTail() {
    return (TOPTruck)getTail();
  }

  /**
   * Sets the head truck in the linked list
   * @param head TOPTruck
   */
  public void setTOPHead( TOPTruck head){
    this.head = head;
  }

  /**
   * Sets the last truck in the linked list
   * @param tail TOPTruck
   */
  public void setTOPTail(TOPTruck tail){
    this.tail = tail;
  }

  /**
   * Returns the truck linked lists's attributes
   * @return TOPAttributes
   */
  public TOPAttributes getTOPAttributes() {
    return (TOPAttributes)getAttributes();
  }

  /* Will insert a truck into the truck's linked list
   * @param truck the truck to insert
   * @return if it was inserted or not
   */
  public boolean insertTruck(TOPTruck truck) {
    TOPTruck currentTruck = getTOPHead();

    truck.setPrev(null);
    truck.setNext(null);

    if (head == null) {
      //no buses exist in list, insert at head
      tail = head = truck;

      return true;
    }
    else {
      //at least head and last buses exist, loop to find insertion
      currentTruck = this.getTOPHead().getTOPNext();

      while (currentTruck != null) {
	if (truck.getTruckNum() < currentTruck.getTruckNum()) {
	  currentTruck.getTOPPrev().setNext(truck);
	  truck.setPrev(currentTruck.getTOPPrev());
	  truck.setNext(currentTruck);
	  currentTruck.setPrev(truck);

	  return true;
	}
	currentTruck = currentTruck.getTOPNext();
      }
    }
    //put the bus at the end
    tail.setNext(truck);
    truck.setPrev(tail);
    tail = truck;

    return true; //bus is always inserted
  }

  /**
   * Attempts to insert the shipment into one of the trucks
   * @param theShipment TOPShipment
   * @return boolean
   */
  public boolean insertShipment(TOPShipment theShipment) {
    boolean status;
    TOPTruck currentTruck, newTruck;

    status = false;
    currentTruck = getTOPHead();
    while ((currentTruck != null) && (status == false)) {
      status = currentTruck.getTOPMainNodes().insertShipment(theShipment);

      if (status == false) {
	currentTruck = currentTruck.getTOPNext();
      }
    }

    //can we create new trucks?
    if ((status == false) && (Settings.lockTrucks == false)) {
      newTruck = new TOPTruck((TOPTruckType)TOPProblemInfo.truckTypes.get(0), TOPProblemInfo.startXCoord,
                              TOPProblemInfo.startYCoord, TOPProblemInfo.endXCoord, TOPProblemInfo.endYCoord);

      //attempt to put this shipment into the new truck
      status = newTruck.getTOPMainNodes().insertShipment(theShipment);

      if (status == true) {
        this.insertTruck(newTruck);
      }
    }

    return status;
  }

  /**
   * Returns a clone of this object
   * @return Object clone
   */
  public Object clone() {
    TOPTruckLinkedList clonedTruckLinkedList = new TOPTruckLinkedList();

    clonedTruckLinkedList.setAttributes((TOPAttributes)this.getTOPAttributes().clone());
    if (this.getTOPHead() != null) {
      clonedTruckLinkedList.head = (TOPTruck)this.getTOPHead().clone();

      if (this.head != this.tail) {
        TOPTruck currentTruck = clonedTruckLinkedList.getTOPHead();
        TOPTruck nextTruck = this.getTOPHead().getTOPNext();

        while (nextTruck != null) {
          currentTruck.setNext( (TOPTruck)nextTruck.clone()); //create the next depot
          currentTruck.getTOPNext().setPrev(currentTruck); //set the next depot's prev
          currentTruck = currentTruck.getTOPNext();
          nextTruck = nextTruck.getTOPNext();

          //once next is null, we have found the tail of the list
          if (nextTruck == null) {
            clonedTruckLinkedList.tail = currentTruck;
            currentTruck.setNext(null);
          }
        }
      }
      else { //only 1 truck
        clonedTruckLinkedList.tail = clonedTruckLinkedList.head;
      }
    }
    else {
      clonedTruckLinkedList.head = null;
      clonedTruckLinkedList.tail = null;
    }

    return clonedTruckLinkedList;
  }
}
