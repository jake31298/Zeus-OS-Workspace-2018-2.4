package edu.sru.thangiah.zeus.vrp;

//import the parent class
import edu.sru.thangiah.zeus.core.DepotLinkedList;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class VRPDepotLinkedList
    extends DepotLinkedList
    implements java.io.Serializable, java.lang.Cloneable {

  public VRPDepotLinkedList() {
	//Housekeeping for the linked list
	setHead(new VRPDepot()); //header node for head
	setTail(new VRPDepot()); //tail node for tail
	linkHeadTail();			  //point head and tail to each other
			
	//Assign the attributes	
    setAttributes(new VRPAttributes());
  }

 
  /**
   * Attempts to insert the shipment into the depot linked list. Will loop
   * through the available depots until a depot is found that can accommodate
   * the shipment
   * @param theShipment the shipment to route
   * @return true if shipment serviced, false if not.
   */
  public boolean insertShipment(VRPShipment theShipment) {
    boolean status = false;

    VRPDepot depot = (VRPDepot)super.getHead().getNext();
    VRPTruckLinkedList truckLL = (VRPTruckLinkedList)depot.getMainTrucks();

    while (depot != this.getTail()) {
      //Get truck to insert the shipment
      truckLL = (VRPTruckLinkedList)depot.getMainTrucks();
      //status = depot.getMainTrucks().insertShipment(theShipment);
      status = truckLL.insertShipment(theShipment);

      if (status) {
        break;
      }
      depot = (VRPDepot) depot.getNext();
    }

    return status;
  }

  /**
 * Returns the first depot in the linked list
 * @return first depot
 */

public VRPDepot getVRPHead() {
 return (VRPDepot) getHead();
}


  /**
   * Returns an exact copy of the depot linked list
   * @return Object depot linked list copy
   */
  public Object clone() {
    VRPDepotLinkedList clonedDepotLinkedList = new VRPDepotLinkedList();

    clonedDepotLinkedList.setAttributes((VRPAttributes)this.getAttributes().clone());
    clonedDepotLinkedList.setHead((VRPDepot)this.getHead().clone());

    //must establish the links at this level to avoid recursing out of control
    clonedDepotLinkedList.getHead().setPrev(null);

    if (this.getHead() != this.getTail()) {
      VRPDepot currentDepot = (VRPDepot) clonedDepotLinkedList.getHead();
      VRPDepot nextDepot = (VRPDepot)this.getHead().getNext();

      while (nextDepot != null) {
        currentDepot.setNext( (VRPDepot) nextDepot.clone()); //create the next depot
        currentDepot.getNext().setPrev(currentDepot); //set the next depot's prev
        currentDepot = (VRPDepot) currentDepot.getNext();
        nextDepot = (VRPDepot) nextDepot.getNext();

        //once next is null, we have found the tail of the list
        if (nextDepot == null) {
          clonedDepotLinkedList.setTail(currentDepot);
          currentDepot.setNext(null);
        }

      }
    }
    else { //only 1 depot
      clonedDepotLinkedList.setTail(clonedDepotLinkedList.getHead());
    }
    return clonedDepotLinkedList;
  }
}
