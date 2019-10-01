package edu.sru.thangiah.zeus.vrp;

//import the parent class
import edu.sru.thangiah.zeus.core.TruckLinkedList;
import edu.sru.thangiah.zeus.core.ZeusProblemInfo;
import edu.sru.thangiah.zeus.core.Settings;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class VRPTruckLinkedList
    extends TruckLinkedList
    implements java.io.Serializable, java.lang.Cloneable {

	/**
	 * Constructor
	 */
	public VRPTruckLinkedList()
	{
		//Housekeeping for the linked list
		setHead(new VRPTruck()); //header node for head
		setTail(new VRPTruck()); //tail node for tail
		linkHeadTail();			  //point head and tail to each other
		
		//Assign the attributes	
		setAttributes(new VRPAttributes());
	}

	/**
   * Returns the first truck in the linked list
   * @return first truck
   */
  public VRPTruck getHead() {
    return (VRPTruck) super.getHead();
  }
  
  /**
   * Returns the tail node truck in the linked list
   * @return first truck
   */
  public VRPTruck getTail() {
    return (VRPTruck) super.getTail();
  }

  /* Will insert a truck into the truck's linked list
   * @param truck the truck to insert
   * @return if it was inserted or not
   */
 /* public boolean insertTruck(VRPTruck truck) {
    VRPTruck currentTruck = (VRPTruck)super.getHead();

    truck.setPrev(null);
    truck.setNext(null);

    if (getHead() == null) {
      //no buses exist in list, insert at head
      setTail(truck);
      setHead(truck);

      return true;
    }
    else {
      //at least head and last buses exist, loop to find insertion
      currentTruck = (VRPTruck) getHead().getNext();

      while (currentTruck != null) {
        if (truck.getTruckNum() < currentTruck.getTruckNum()) {
          currentTruck.getPrev().setNext(truck);
          truck.setPrev(currentTruck.getPrev());
          truck.setNext(currentTruck);
          currentTruck.setPrev(truck);

          return true;
        }
        currentTruck = (VRPTruck) currentTruck.getNext();
      }
    }
    //put the bus at the end
    getTail().setNext(truck);
    truck.setPrev(getTail());
    setTail(truck);

    return true; //bus is always inserted
  }
*/
  /**
   * Attempts to insert a shipment into the Truck linked list. Will attempt
   * to insert the shipment into each truck until one is found that can
   * accomodate the shipment
   * @param theShipment the shipment to insert
   * @return true if shipment serviced by a truck, false if not.
   */
  public boolean insertShipment(VRPShipment theShipment) {
    boolean status = false;
    //int loopCount=0;

    VRPTruck truck = (VRPTruck)this.getHead().getNext();

    //loopCount = 1;
    while (truck != this.getTail()) {
      if (truck.compatableWith(theShipment)) {
        status = truck.getVRPMainNodes().insertShipment(theShipment);

        // break out of the loop if a good truck is found.
        if (status) {
          break;
        }
      }

      truck = (VRPTruck) truck.getNext();
      //System.out.println("Loop count in insert "+loopCount++);
    }

    //can we create new trucks?
    if ( (status == false) && (Settings.lockTrucks == false)) {
    /** @todo  Is this really needed */
     //create a pointer to the last truck for reference
      VRPTruck last = (VRPTruck)this.getTail().getPrev();
      VRPTruck first = (VRPTruck)this.getHead();

      //loop to find the correct truck type for this customer
      for (int i = 0; i < ZeusProblemInfo.getTruckTypesSize(); i++) {
        VRPTruckType type = (VRPTruckType) ZeusProblemInfo.getTruckTypesAt(i);

        if (type.getServiceType().equals(theShipment.getTruckTypeNeeded())) {
          //create a new truck with the truck number of the last + 1, the matching truck type
          //and use the first customer in the last truck (the depot) to get the depot x,y
        	//If it is set to null, it w
          //VRPTruck newTruck = null;
        	/*VRPTruck newTruck    = new VRPTruck(type,
                                           last.getMainNodes().getHead().getNext().
                                           getShipment()
                                           .getXCoord(),
                                           last.getMainNodes().getHead().getNext().
                                           getShipment()
                                           .getYCoord());*/
          
        	
        	
        	VRPTruck newTruck    = new VRPTruck(type,
                    first.getNext().getDepotX(),
                    first.getNext().getDepotY());
        	

          //attempt to put this shipment into the new truck
          status = newTruck.getVRPMainNodes().insertShipment(theShipment);

          if (status == true) {
            //the customer was inserted to the new truck, so insert the new truck to the linked list
        	 System.out.println("** Inserting new Truck");
        	 System.out.println("Depot x and y is:"+ first.getNext().getDepotX()+" "+first.getNext().getDepotY());
            this.insertTruckLast(newTruck);

            return status;
          }
          else {
            //customer could not be inserted into the new truck so return false
            //and dont insert the new truck into the linked list (garbage collector
            //will delete it)
            return status;
          }
        }
      }
    }

    return status;
  }

  /**
   * Returns a clone of this object
   * @return Object clone
   */
  public Object clone() {
    VRPTruckLinkedList clonedTruckLinkedList = new VRPTruckLinkedList();

    clonedTruckLinkedList.setAttributes((VRPAttributes)this.getAttributes().clone());
    clonedTruckLinkedList.setHead((VRPTruck)this.getHead().clone());

    if (this.getHead() != this.getTail()) {
      VRPTruck currentTruck = (VRPTruck) clonedTruckLinkedList.getHead();
      VRPTruck nextTruck = (VRPTruck)this.getHead().getNext();

      while (nextTruck != null) {
        currentTruck.setNext( (VRPTruck) nextTruck.clone()); //create the next depot
        currentTruck.getNext().setPrev(currentTruck); //set the next depot's prev
        currentTruck = (VRPTruck) currentTruck.getNext();
        nextTruck = (VRPTruck) nextTruck.getNext();

        //once next is null, we have found the tail of the list
        if (nextTruck == null) {
          clonedTruckLinkedList.setTail(currentTruck);
          currentTruck.setNext(null);
        }

      }
    }
    else { //only 1 depot
      clonedTruckLinkedList.setTail(clonedTruckLinkedList.getHead());
    }
    return clonedTruckLinkedList;
  }

}
