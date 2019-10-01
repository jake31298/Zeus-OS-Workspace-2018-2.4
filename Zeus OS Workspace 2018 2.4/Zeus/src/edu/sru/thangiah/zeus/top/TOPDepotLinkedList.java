package edu.sru.thangiah.zeus.top;

//import the parent class
import edu.sru.thangiah.zeus.core.DepotLinkedList;
import edu.sru.thangiah.zeus.core.Settings;

/**
 * <p>Title: TOPDepotLinkedList</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 * Modified by Pete Schallot and David Crissman to handle tour orienteering problems
 */
public class TOPDepotLinkedList
    extends DepotLinkedList
    implements java.io.Serializable, java.lang.Cloneable {

  public static final long serialVersionUID = 1;
  private int startDepotNum;     //Index of the starting depot
  private int endDepotNum;       //Index of the ending depot

  /**
   * Constructor
   * Modified by Pete Schallot and David Crissman
   */
  public TOPDepotLinkedList() {
    setAttributes(new TOPAttributes());

    setHead(null);
    setTail(null);

    //When the list is created, it doesn't have a starting or ending depot yet
    startDepotNum = -1;
    endDepotNum = -1;
  }

  /**
   * Returns the first depot in the linked list
   * @return first depot
   */
  public TOPDepot getTOPHead() {
    return (TOPDepot) getHead();
  }

  /**
   * Returns the last depot in the linked list
   * @return last depot
   */
  public TOPDepot getTOPTail() {
    return (TOPDepot)getTail();
  }

  /**
   * Returns the attributes of this depot linked list
   * @return TOPAttributes
   */
  public TOPAttributes getTOPAttributes() {
    return (TOPAttributes)getAttributes();
  }

  /**
   * Returns the depot that is the starting point
   * @return TOPDepot
   * Added by David Crissman
   */
  public TOPDepot getStartDepot() {
    return (TOPDepot)find(startDepotNum);
  }

  /**
   * Returns the depot that is the ending point
   * @return TOPDepot
   * Added by David Crissman
   */
  public TOPDepot getEndDepot() {
    return (TOPDepot)find(endDepotNum);
  }

  /**
   * Returns the elliptical area which determines which shipments can be reached without exceeding the trucks' maximum travel time
   * @return TOPEllipse
   * Added by David Crissman
   */
  public TOPEllipse getReachableArea() {
    TOPEllipse reachableArea;

    //Only continue if starting and ending depots have been specified
    if ((startDepotNum > 0) && (endDepotNum > 0)) {
      //Recalculate the ellipse to ensure that it is up to date
      reachableArea = new TOPEllipse(find(startDepotNum).getXCoord(), find(startDepotNum).getYCoord(),
				   find(endDepotNum).getXCoord(), find(endDepotNum).getYCoord(),
				   TOPProblemInfo.truckMaxTravelTime);
    }
    else {
      reachableArea = null;

      Settings.printDebug(Settings.COMMENT, "Starting and ending depots have not been defined");
    }

    return reachableArea;
  }

  /**
   * Returns a solution string which only counts trucks that are team members
   * @return String
   * Added by David Crissman
   */
  public String getTeamSolutionString() {
    String solutionString = "";
    TOPTruck currentTruck;
    int trucksUsed = 0;

    TOPProblemInfo.getTOPDepotLLCostFunctions().markTeamMembers(this);
    currentTruck = this.getStartDepot().getTOPMainTrucks().getTOPHead();
    while (currentTruck != null) {
      if (currentTruck.getIsTeamMember() == true) {
        trucksUsed++;
      }
      currentTruck = currentTruck.getTOPNext();
    }

    solutionString += "Trucks Used = " + trucksUsed + " | ";
    solutionString += "Total Demand = " + TOPProblemInfo.getTOPDepotLLCostFunctions().getTeamTotalDemand(this) + "| ";
    solutionString += "Total Distance = " + TOPProblemInfo.getTOPDepotLLCostFunctions().getTeamTotalDistance(this) + "| ";
    solutionString += "Total TravelTime = " + TOPProblemInfo.getTOPDepotLLCostFunctions().getTeamTotalTravelTime(this) + "| ";
    solutionString += "Total Cost = " + TOPProblemInfo.getTOPDepotLLCostFunctions().getTeamTotalCost(this) + "|";

    return solutionString;
  }

  /**
   * Specifies which depot is the starting point
   * @param depotNum int
   * Added by David Crissman
   */
  public void setStartDepot(int depotNum) {
    //Only set the starting depot if the number corresponds to an existing depot
    if (find(depotNum) != null) {
      startDepotNum = depotNum;
    }
    else {
      Settings.printDebug(Settings.COMMENT, "The specified depot (" + depotNum + ") does not exist.");
    }
  }

  /**
   * Specifies which depot is the ending point
   * @param depotNum int
   * Added by David Crissman
   */
  public void setEndDepot(int depotNum) {
    //Only set the ending depot if the number corresponds to an existing depot
    if (find(depotNum) != null) {
      endDepotNum = depotNum;
    }
    else {
      Settings.printDebug(Settings.COMMENT, "The specified depot (" + depotNum + ") does not exist.");
    }
  }

  /**
   * Sets a flag in each shipment to specify whether or not it is within the reachable area
   * @param mainShipments TOPShipmentLinkedList
   * Added by David Crissman
   */
  public void markUnreachableShipments(TOPShipmentLinkedList mainShipments) {
    TOPShipment currentShipment;
    TOPEllipse reachableArea;

    //Only continue if the starting and ending depots have been specified
    if ((startDepotNum > 0) && (endDepotNum > 0)) {
      //Get the ellipse representing the area that the trucks can reach
      reachableArea = getReachableArea();

      //Set the flag in each shipment according to whether or not it is in the reachable area
      currentShipment = mainShipments.getTOPHead();
      while (currentShipment != null) {
	if (reachableArea.pointInsideEllipse(currentShipment.getXCoord(), currentShipment.getYCoord()) == true) {
	  currentShipment.setUnreachable(false);
	}
	else {
          Settings.printDebug(Settings.COMMENT, "Shipment #" + currentShipment.getIndex() + " cannot be reached.");
	  currentShipment.setUnreachable(true);
	}

	currentShipment = currentShipment.getTOPNext();
      }
    }
    else {
      Settings.printDebug(Settings.COMMENT, "Starting and ending depots have not been defined");
    }
  }

  /**
   * Inserts depot as last node in the depot linked list
   * @param depot the specified depot to be inserted
   */
  public void insertLastDepot(TOPDepot depot) {
    TOPDepot prevDepot;

    if (getHead() == null) {
      setHead(depot);
      setTail(depot);
      depot.setPrev(null);
      depot.setNext(null);
    }
    else {
      getTail().setNext(depot);
      depot.setPrev(getTail());
      setTail(depot);
      depot.setNext(null);
    }
  }

  /**
   * Attempts to insert the shipment into the depot linked list. It adds the
   * shipment to the depot which was set as the starting point
   * @param theShipment the shipment to route
   * @return true if shipment serviced, false if not.
   * Modified by David Crissman and Pete Schallot
   */
  public boolean insertShipment(TOPShipment theShipment) {
    boolean status = false;

    //Only continue if the starting and ending depots have been specified
    if ((startDepotNum != -1) && (endDepotNum != -1)) {
      status = getStartDepot().getTOPMainTrucks().insertShipment(theShipment);
    }

    return status;
  }

  /**
   * Returns an exact copy of the depot linked list
   * @return Object depot linked list copy
   * Modified by David Crissman and Pete Schallot
   */
  public Object clone() {
    TOPDepotLinkedList clonedDepotLinkedList = new TOPDepotLinkedList();

    clonedDepotLinkedList.setAttributes((TOPAttributes)this.getTOPAttributes().clone());
    clonedDepotLinkedList.setHead((TOPDepot)this.getTOPHead().clone());

    //must establish the links at this level to avoid recursing out of control
    clonedDepotLinkedList.getHead().setPrev(null);

    if (this.getHead() != this.getTail()) {
      TOPDepot currentDepot = clonedDepotLinkedList.getTOPHead();
      TOPDepot nextDepot = this.getTOPHead().getTOPNext();

      while (nextDepot != null) {
        currentDepot.setNext((TOPDepot)nextDepot.clone()); //create the next depot
        currentDepot.getTOPNext().setPrev(currentDepot); //set the next depot's prev
        currentDepot = currentDepot.getTOPNext();
        nextDepot = nextDepot.getTOPNext();

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

    //Copy over the variables that are unique to TOPDepotLinkedList
    clonedDepotLinkedList.setStartDepot(this.startDepotNum);
    clonedDepotLinkedList.setEndDepot(this.endDepotNum);


    return clonedDepotLinkedList;

  }
}
