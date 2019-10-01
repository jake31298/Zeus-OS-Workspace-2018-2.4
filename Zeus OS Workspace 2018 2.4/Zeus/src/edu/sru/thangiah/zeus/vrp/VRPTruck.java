package edu.sru.thangiah.zeus.vrp;


import edu.sru.thangiah.zeus.core.ZeusProblemInfo;
import edu.sru.thangiah.zeus.core.Attributes;
//import the parent class
import edu.sru.thangiah.zeus.core.Truck;

public class VRPTruck
    extends Truck implements java.io.Serializable, java.lang.Cloneable {
  public VRPTruck() {
	
	  //Assign the nodes linkes list
	 setMainNodes(new VRPNodesLinkedList());
	  
    //Assign the attributes
    setAttributes(new VRPAttributes());
  }

  /**
   * Constructor
   * @param tt truck type
   * @param depotX depot's x coordinate
   * @param depotY depot's y coordinate
   */
  public VRPTruck(VRPTruckType tt, double depX, double depY) {
    //super(tt, depX, depY);
    setAttributes(new VRPAttributes());
    setDepotX(depX);
    setDepotY(depY);
    ZeusProblemInfo.setNumTrucks(ZeusProblemInfo.getNumTrucks()+1);
    setTruckNum(ZeusProblemInfo.getNumTrucks());
    //setTruckNum(ProblemInfo1.numTrucks++);
    setTruckType(tt);

    setMainNodes(new VRPNodesLinkedList(tt, depX, depY, getTruckNum()));

    /*newShape().addShapesFromFile(ProblemInfo1.workingDirectory +
                                 "\\data\\shapes\\shape.txt");
    //Add shape to first irr polygon
    getShape().setCode(getShape().getRegularPolygonLimit() + 1);
    //Assign rand shape code that is an irregular polygon
    getShape().randCode(
        getShape().getRegularPolygonLimit() + 1,
        getShape().getRegularPolygonLimit() +
        getShape().getNumberofIrregularShapes()
        );
    //Change size of shape
    getShape().setSize(20);
    //Move center of the shape to the center of the depot
    //--- getShape().setXY(depX, depY); Needs to be changed to double
    //Rotate the shape a random amount
    getShape().randRot(360);
    //Shift the shape a random amount
    getShape().randXY(200);
    //Moves vertex number 2 by 20 units in the x direction and y direction
    //getShape().shiftVertex(2, getShape().getX(2) + 20, getShape().getY(2) + 20);
	*/
  }

  /**
   * Returns the visit nodes linked list (route)
   * @return route
   */
  public VRPNodesLinkedList getVRPMainNodes() {
    return (VRPNodesLinkedList) getMainNodes();
  }

  /**
   * Returns the next depot in the linked list
   * @return next depot
   */
  public VRPTruck getVRPNext() {
    return (VRPTruck) getNext();
  }

  /**
   * Creates a clone of the current trucks. Does not create the next and prev
   * links, that is the responsibility of the truck linked list clone() function
   * @return Object truck clone
   */
  public Object clone() {
    VRPTruck clonedTruck = new VRPTruck();

    clonedTruck.setAttributes((VRPAttributes)this.getAttributes().clone());
    clonedTruck.setDepotX(this.getDepotX());
    clonedTruck.setDepotY(this.getDepotY());
    clonedTruck.setMainNodes((VRPNodesLinkedList)this.getMainNodes().clone());
    clonedTruck.setTruckNum(this.getTruckNum());
    if (this.getTruckType() != null)
    	clonedTruck.setTruckType((VRPTruckType)this.getTruckType().clone());
    else
    	clonedTruck.setTruckType(new VRPTruckType());
    	
    return clonedTruck;
  }

}
