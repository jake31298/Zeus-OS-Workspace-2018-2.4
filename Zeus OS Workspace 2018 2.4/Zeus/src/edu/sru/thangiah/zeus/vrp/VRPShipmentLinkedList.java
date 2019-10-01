package edu.sru.thangiah.zeus.vrp;

import java.io.PrintStream;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */


//import the parent class
import edu.sru.thangiah.zeus.core.ShipmentLinkedList;
import edu.sru.thangiah.zeus.core.Shipment;
import edu.sru.thangiah.zeus.core.ZeusProblemInfo;

public class VRPShipmentLinkedList
    extends ShipmentLinkedList
    implements java.io.Serializable, java.lang.Cloneable {


  /**
   * Constructor for the shipment linked list
   * Define it as below
   */
  public VRPShipmentLinkedList() 
  {
      setHead(new VRPShipment()); //header node for head
      setTail(new VRPShipment()); //tail node for tail
      linkHeadTail();			  //point head and tail to each other
      setNumShipments(0);
  }
  
  
  
  public void insertShipment(int num, float x, float y, int q, int d, int e, String type) {    
		VRPShipment thisShip = new VRPShipment(num, x, y, d, q, e, type);
		//add the instance to the linked list - in this case it is added at the end of the list
		//the total number of shipments is incremented in the insert
		insertLast(thisShip);	
	}

	

/**
   * insert the shipment into the linked list
   * Constructor
   * @param ind index
   * @param x x-coordinate
   * @param y y-coordinate
   * @param q demand
   * @param d service time
   * @param e frequency
   * @param comb number of combination
   * @param vComb list of combinations (vector)
   * @param cuComb number of combinations (matrix)
   */

  public void insertShipment(int num, float x, float y, int q, int d, int e,
                             int comb, String type,
                             int[] vComb, int[][] cuComb) {
	  
	if (vComb.length <= ZeusProblemInfo.getMaxCombinations()) {
    //if (vComb.length <= ProblemInfo1.MAX_COMBINATIONS) {
      //create an instance of the Shipment
      VRPShipment thisShip = new VRPShipment(num, x, y, d, q, e, comb, type,
                                             vComb, cuComb);
      //add the instance to the linked list - in this case it is added at the end of the list
      //the total number of shipments is incremented in the insert
      insertLast(thisShip);
    }
    else {
      System.out.println(
          "VRPShipmentLinkedList: Maximum number of combinations exceeded");
    }
  }

  /**
   * Returns the first shipment in the linked list
   * @return first shipment
   */
  public VRPShipment getVRPHead() {
    return (VRPShipment) getHead();
  }
  
  /**
   * Returns the tail shipment in the linked list
   * @return first shipment
   */
  public VRPShipment getVRPTail() {
    return (VRPShipment) getTail();
  }


  /**
   * This method will get the next shipment that is to be inserted based on the
   * type of shipment selection that has been defined in the main method
   * for the variable ProblemInfo.selectShipType
   * @param currDepot current depot being considered for the shipment
   * @param currDepotLL Depot linked list of the problem being solved
   * @param currShipmentLL shipment linked list from which the shipment to be
   * insertion is to be selected
   * @return VRPShipment the shipment to be inserted
   */

  public VRPShipment getNextInsertShipment(VRPDepotLinkedList currDepotLL,
		  VRPDepot currDepot,
		  VRPShipmentLinkedList currShipmentLL,
		  VRPShipment currShip) {
	  
	  VRPShipmentLinkedList selectShip = (VRPShipmentLinkedList) ZeusProblemInfo.getSelectShipType();
	  //VRPShipmentLinkedList selectShip = (VRPShipmentLinkedList) ProblemInfo1.
	  //		  selectShipType;
	  return selectShip.getSelectShipment(currDepotLL, currDepot, currShipmentLL,currShip);
  }

  /**
   * This is a stub - Leave it as it is
   * The concrere getSelectShipment will be declared by the class inheriting this
   * class and implementing the actual insertion of shipment  *
   * @param currShipmentLL shipment linked list from which the shipment to be
   * insertion is to be selected
   * @return VRPShipment the shipment to be inserted
   */

  public VRPShipment getSelectShipment(VRPDepotLinkedList currDepotLL,
		  VRPDepot currDepot,
		  VRPShipmentLinkedList currShipmentLL,
		  VRPShipment currShip) {
	  return null;
  }



  /**
   * Print out the shipment linked list to the console
   * @param out PrintStream stream to output to
   */
  public void printVRPShipmentsToConsole() {
	  System.out.println(this.getNumShipments());

	  Shipment ship = super.getHead();
	  VRPShipment vrpShip;
	  while (ship != getTail())
	  {
		  vrpShip = (VRPShipment)ship ;
		  System.out.println(vrpShip.getIndex() + " " + vrpShip.getTruckTypeNeeded() + " " +
				  vrpShip.getDemand() + " " + vrpShip.getXCoord() + " " +
				  //ship.getYCoord() + " " + ship.getPickUpPointName() +
				  vrpShip.getYCoord() + " " +
				  vrpShip.getExtraVariable());
		  ship = ship.getNext();
	  }
  }



  /**
   * Writes the shipments to the print stream
   * @param out PrintStream stream to output to
   */
  public void writeVRPShipments(PrintStream out) {
	  out.println(this.getNumShipments());


	  Shipment ship = super.getHead();
	  VRPShipment vrpShip;
	  while (ship != getTail())
	  {
		  vrpShip = (VRPShipment)ship ;
		  out.println(vrpShip.getIndex() + " " + vrpShip.getTruckTypeNeeded() + " " +
				  vrpShip.getDemand() + " " + vrpShip.getXCoord() + " " +
				  //ship.getYCoord() + " " + ship.getPickUpPointName() +
				  vrpShip.getYCoord() + " " +
				  vrpShip.getExtraVariable());
		  ship = ship.getNext();
	  }
  }
}

  //Select the shipment with the shortest distance to the depot
  class ClosestEuclideanDistToDepot
  extends VRPShipmentLinkedList {
	  public VRPShipment getSelectShipment(VRPDepotLinkedList currDepotLL,
			  VRPDepot currDepot,
                                       VRPShipmentLinkedList currShipLL,
                                       VRPShipment currShip) {
    //currDepotLL is the depot linked list of the problem
    //currDepot is the depot under consideration
    //currShipLL is the set of avaialble shipments
    boolean isDiagnostic = false;
    //VRPShipment temp = (VRPShipment) getHead(); //point to the first shipment
    VRPShipment temp = (VRPShipment) currShipLL.getVRPHead().getNext(); //point to the first shipment
    VRPShipment foundShipment = null; //the shipment found with the criteria
    //double angle;
    //double foundAngle = 360; //initial value
    double distance;
    double foundDistance = 200; //initial distance
    double depotX, depotY;

    //Get the X and Y coordinate of the depot
    depotX = currDepot.getXCoord();
    depotY = currDepot.getYCoord();

    while (temp != currShipLL.getVRPTail()) {
      if (isDiagnostic) {
        System.out.print("Shipment " + temp.getIndex() + " ");

        if ( ( (temp.getXCoord() - depotX) >= 0) &&
            ( (temp.getYCoord() - depotX) >= 0)) {
          System.out.print("Quadrant I ");
        }
        else if ( ( (temp.getXCoord() - depotX) <= 0) &&
                 ( (temp.getYCoord() - depotY) >= 0)) {
          System.out.print("Quadrant II ");
        }
        else if ( ( (temp.getXCoord()) <= (0 - depotX)) &&
                 ( (temp.getYCoord() - depotY) <= 0)) {
          System.out.print("Quadrant III ");
        }
        else if ( ( (temp.getXCoord() - depotX) >= 0) &&
                 ( (temp.getYCoord() - depotY) <= 0)) {
          System.out.print("Quadrant VI ");
        }
        else {
          System.out.print("No Quadrant");
        }
      }

      //if the shipment is assigned, skip it
      if (temp.getIsAssigned()) {
        if (isDiagnostic) {
          System.out.println("has been assigned");
        }

        temp = (VRPShipment) temp.getNext();

        continue;
      }
/** @todo Associate the quadrant with the distance to get the correct shipment.
       * Set up another insertion that takes the smallest angle and the smallest distance */
      distance = calcDist(depotX, temp.getXCoord(), depotY, temp.getYCoord());

      if (isDiagnostic) {
        System.out.println("  " + distance);
      }

      //check if this shipment should be tracked
      if (foundShipment == null) { //this is the first shipment being checked
        foundShipment = temp;
        foundDistance = distance;
      }
      else {
        if (distance < foundDistance) { //found an angle that is less
          foundShipment = temp;
          foundDistance = distance;
        }
      }
      temp = (VRPShipment) temp.getNext();
    }
    return foundShipment; //stub
  }

  //The WhoAmI methods gives the id of the assigned object
  //It is a static method so that it can be accessed without creating an object
 public static String WhoAmI() {
   return("Selection Type: Closest euclidean distance to depot");
 }

}


//Select the shipment with the smallest polar coordinate angle to the depot
class SmallestPolarAngleToDepot
    extends VRPShipmentLinkedList {
  public VRPShipment getSelectShipment(VRPDepotLinkedList currDepotLL,
                                       VRPDepot currDepot,
                                       VRPShipmentLinkedList currShipLL,
                                       VRPShipment currShip) {
    //currDepotLL is the depot linked list of the problem
    //currDepot is the depot under consideration
    //currShipLL is the set of avaialble shipments
    boolean isDiagnostic = false;
    //VRPShipment temp = (VRPShipment) getHead(); //point to the first shipment
    VRPShipment temp = (VRPShipment) currShipLL.getVRPHead().getNext(); //point to the first shipment
   
    VRPShipment foundShipment = null; //the shipment found with the criteria
    double angle;
    double foundAngle = 360; //initial value
    //double distance;
    //double foundDistance = 200; //initial distance
    double depotX, depotY;
    int type = 2;

    //Get the X and Y coordinate of the depot
    depotX = currDepot.getXCoord();
    depotY = currDepot.getYCoord();

    while (temp != currShipLL.getVRPTail()) {

      if (isDiagnostic) {
    	  System.out.println("Temp is "+temp);
    	  System.out.println("Tail is "+getTail());
    	  System.out.print("Shipment " + temp.getIndex() + " ");

    	  if ( ( (temp.getXCoord() - depotX) >= 0) &&
    			  ( (temp.getYCoord() - depotX) >= 0)) {
    		  System.out.print("Quadrant I ");
    	  }
    	  else if ( ( (temp.getXCoord() - depotX) <= 0) &&
    			  ( (temp.getYCoord() - depotY) >= 0)) {
    		  System.out.print("Quadrant II ");
    	  }
    	  else if ( ( (temp.getXCoord()) <= (0 - depotX)) &&
    			  ( (temp.getYCoord() - depotY) <= 0)) {
    		  System.out.print("Quadrant III ");
    	  }
    	  else if ( ( (temp.getXCoord() - depotX) >= 0) &&
    			  ( (temp.getYCoord() - depotY) <= 0)) {
    		  System.out.print("Quadrant VI ");
    	  }
    	  else {
    		  System.out.print("No Quadrant");
    	  }
      }

      //if the shipment is assigned, skip it
      if (temp.getIsAssigned()) {
    	  if (isDiagnostic) {
    		  System.out.println("has been assigned");
    	  }

    	  temp = (VRPShipment) temp.getNext();

    	  continue;
      }

      angle = calcPolarAngle(depotX, depotX, temp.getXCoord(),
    		  temp.getYCoord());

      if (isDiagnostic) {
    	  System.out.println("  " + angle);
      }

      //check if this shipment should be tracked
      if (foundShipment == null) { //this is the first shipment being checked
    	  foundShipment = temp;
    	  foundAngle = angle;
      }
      else {
    	  if (angle < foundAngle) { //found an angle that is less
    		  foundShipment = temp;
    		  foundAngle = angle;
    	  }
      }
      temp =  (VRPShipment) temp.getNext();
    }
    return foundShipment; //stub
  }

  //The WhoAmI methods gives the id of the assigned object
  //It is a static method so that it can be accessed without creating an object
 public static String WhoAmI() {
   return("Selection Type: Smallest polar angle to the depot");
 }
}

//Select the shipment with the smallest polar coordinate angle and the
//shortest distance to the depot
class SmallestPolarAngleShortestDistToDepot
    extends VRPShipmentLinkedList {
  public VRPShipment getSelectShipment(VRPDepotLinkedList currDepotLL,
                                       VRPDepot currDepot,
                                       VRPShipmentLinkedList currShipLL,
                                       VRPShipment currShip) {
    //currDepotLL is the depot linked list of the problem
    //currDepot is the depot under consideration
    //currShipLL is the set of avaialble shipments
    boolean isDiagnostic = false;
    //VRPShipment temp = (VRPShipment) getHead(); //point to the first shipment
    VRPShipment temp = (VRPShipment)currShipLL.getVRPHead().getNext(); //point to the first shipment
    VRPShipment foundShipment = null; //the shipment found with the criteria
    double angle;
    double foundAngle = 360; //initial value
    double distance;
    double foundDistance = 200; //initial distance
    double depotX, depotY;
    int type = 2;

    //Get the X and Y coordinate of the depot
    depotX = currDepot.getXCoord();
    depotY = currDepot.getYCoord();

    while (temp != currShipLL.getVRPTail()) {
      if (isDiagnostic) {
        System.out.print("Shipment " + temp.getIndex() + " ");

        if ( ( (temp.getXCoord() - depotX) >= 0) &&
            ( (temp.getYCoord() - depotX) >= 0)) {
          System.out.print("Quadrant I ");
        }
        else if ( ( (temp.getXCoord() - depotX) <= 0) &&
                 ( (temp.getYCoord() - depotY) >= 0)) {
          System.out.print("Quadrant II ");
        }
        else if ( ( (temp.getXCoord()) <= (0 - depotX)) &&
                 ( (temp.getYCoord() - depotY) <= 0)) {
          System.out.print("Quadrant III ");
        }
        else if ( ( (temp.getXCoord() - depotX) >= 0) &&
                 ( (temp.getYCoord() - depotY) <= 0)) {
          System.out.print("Quadrant VI ");
        }
        else {
          System.out.print("No Quadrant");
        }
      }

      //if the shipment is assigned, skip it
      if (temp.getIsAssigned()) {
        if (isDiagnostic) {
          System.out.println("has been assigned");
        }

        temp = (VRPShipment) temp.getNext();

        continue;
      }

      distance = calcDist(depotX, temp.getXCoord(), depotY, temp.getYCoord());
      angle = calcPolarAngle(depotX, depotX, temp.getXCoord(),
                             temp.getYCoord());

      if (isDiagnostic) {
        System.out.println("  " + angle);
      }

      //check if this shipment should be tracked
      if (foundShipment == null) { //this is the first shipment being checked
        foundShipment = temp;
        foundAngle = angle;
        foundDistance = distance;
      }
      else {
        //if angle and disnace are smaller than what had been found
        //if (angle <= foundAngle && distance <= foundDistance) {
        if (angle+ distance  <= foundAngle + foundDistance) {
        //if ((angle*.90)+ (distance * 0.1)  <= (foundAngle*0.9) + (foundDistance*0.1)) {
          foundShipment = temp;
          foundAngle = angle;
           foundDistance = distance;
        }
      }
      temp = (VRPShipment) temp.getNext();
    }
    return foundShipment; //stub
  }

  //The WhoAmI methods gives the id of the assigned object
  //It is a static method so that it can be accessed without creating an object
 public static String WhoAmI() {
   return("Selection Type: Smallest polar angle to the depot");
 }

}

