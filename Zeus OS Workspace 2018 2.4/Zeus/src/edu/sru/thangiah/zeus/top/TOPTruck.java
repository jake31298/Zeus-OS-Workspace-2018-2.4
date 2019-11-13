package edu.sru.thangiah.zeus.top;

import edu.sru.thangiah.zeus.core.Nodes;
import edu.sru.thangiah.zeus.core.NodesLinkedList;
import edu.sru.thangiah.zeus.core.ZeusProblemInfo;
import edu.sru.thangiah.zeus.core.Attributes;
//import the parent class
import edu.sru.thangiah.zeus.core.Truck;
import edu.sru.thangiah.zeus.core.TruckType;

public class TOPTruck
    extends Truck implements java.io.Serializable, java.lang.Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean routeToOriginal;
	private double secondDepotX;
	private double secondDepotY;
	private int secondDepotID;
	private boolean isUsed;
	private boolean isTeamMember;
	private Object endDepotX;
	private Object endDepotY;
	
	public TOPTruck() {
	
	  //Assign the nodes linkes list
	 setMainNodes(new TOPNodesLinkedList());
	  
    //Assign the attributes
    setAttributes(new TOPAttributes());
  }

  /**
   * Constructor
   * @param tt truck type
   * @param depotX depot's x coordinate
   * @param depotY depot's y coordinate
   */
  public TOPTruck(TOPTruckType tt, double depX, double depY) {
    //super(tt, depX, depY);
    setAttributes(new TOPAttributes());
    setDepotX(depX);
    setDepotY(depY);
    setTruckNum(ZeusProblemInfo.getNumTrucks() + 1);
    setTruckType(tt);
    setRouteToOriginal(true);
	setIsUsed(true);
    
    setMainNodes(new TOPNodesLinkedList(tt, depX, depY, getTruckNum()));

  }
  
  /**
   * New constructor
   * @param tt
   * @param xCoord
   * @param yCoord
   * @param xCoord2ID
   * @param xCoord2
   * @param yCoord2
   */
  public TOPTruck(TOPTruckType tt, double xCoord, double yCoord, int xCoord2ID,
			double xCoord2, double yCoord2) {
		    setAttributes(new TOPAttributes());
		    setDepotX(xCoord);
		    setDepotY(yCoord);
		    setTruckNum(ZeusProblemInfo.getNumTrucks());
		    setTruckType(tt);
		    setRouteToOriginal(false);
			setSecondDepotX(xCoord2);
			setSecondDepotY(yCoord2);
			setIsUsed(true);
			
		    setMainNodes(new TOPNodesLinkedList(tt, xCoord, yCoord, xCoord2ID, xCoord2, yCoord2, getTruckNum()));
	}

  public TOPTruck(TOPTruckType tt, float startDepX, float startDepY, float endDepX, float endDepY) {
	    setAttributes(new TOPAttributes());
	    setDepotX(startDepX);
	    setDepotY(startDepY);
	    endDepotX = endDepX;
	    endDepotY = endDepY;
	    setTruckNum(TOPProblemInfo.getNumTrucks() + 1);
	    setTruckType(tt);
	    isTeamMember = false;

	    setMainNodes(new TOPNodesLinkedList(tt, startDepX, startDepY, endDepX, endDepY, getTruckNum()));
	  }
/**
   * Returns the visit nodes linked list (route)
   * @return route
   */
  public TOPNodesLinkedList getTOPMainNodes() {
    return (TOPNodesLinkedList) getMainNodes();
  }

  /**
   * Returns the next depot in the linked list
   * @return next depot
   */
  public TOPTruck getTOPNext() {
    return (TOPTruck) getNext();
  }
  
  /**
   * Returns truck demand 
   * Used in TOP for total scores for teams
   * @param currTruck
   * @return
   */
  public int getTruckDemand(Truck currTruck)
  {
	  int truckDemand = 0;
	  Nodes currNode = currTruck.getMainNodes().getHead().getNext();
	  
	  while(currNode != currTruck.getMainNodes().getTail())
	  {
		  truckDemand += currNode.getDemand();
		  currNode = currNode.getNext();		  
	  }
	  
	  return(truckDemand);
  }
  
  public void setIsTeamMember(boolean b) {
	  this.isTeamMember = b;
  }
  
  public boolean getIsTeamMember() {
	  return this.isTeamMember;
  }
  
  public double getSecondDepotX()
  {
	  return secondDepotX;
  }
  
  public void setSecondDepotX(double xLoc)
  {
	  secondDepotX = xLoc;
  }
  
  
  public boolean getIsUsed()
  {
	  return isUsed;
  }
  
  public void setIsUsed(boolean flag)
  {
	  isUsed = flag;
  }
  
  
  public double getSecondDepotY()
  {
	  return secondDepotY;
  }
  
  public void setSecondDepotY(double yLoc)
  {
	  secondDepotY = yLoc;
  }
  
  public boolean getRouteToOriginal()
  {
	  return routeToOriginal;
  }
  
  public void setRouteToOriginal(boolean route)
  {
	  routeToOriginal = route;
  }
  
  

  /**
   * Creates a clone of the current trucks. Does not create the next and prev
   * links, that is the responsibility of the truck linked list clone() function
   * @return Object truck clone
   */
  public Object clone() {
	    TOPTruck clonedTruck = new TOPTruck();

	    clonedTruck.setAttributes((TOPAttributes)this.getAttributes().clone());
	    clonedTruck.setDepotX(this.getDepotX());
	    clonedTruck.setDepotY(this.getDepotY());
	    clonedTruck.endDepotX = this.endDepotX;
	    clonedTruck.endDepotY = this.endDepotY;
	    clonedTruck.setMainNodes((TOPNodesLinkedList)this.getTOPMainNodes().clone());
	    clonedTruck.setTruckNum(this.getTruckNum());
	    clonedTruck.isTeamMember = this.isTeamMember;
	    if(this.getTruckType() != null) {
	    	clonedTruck.setTruckType((TOPTruckType)this.getTruckType().clone());
	    }
	    else {
	    	clonedTruck.setTruckType(new TOPTruckType());
	    }
	    return clonedTruck;
	  }

public int getSecondDepotID() {
	return secondDepotID;
}

}
