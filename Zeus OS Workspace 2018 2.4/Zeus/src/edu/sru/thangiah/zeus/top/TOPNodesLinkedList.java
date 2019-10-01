package edu.sru.thangiah.zeus.top;

import edu.sru.thangiah.zeus.core.NodesLinkedList;
import edu.sru.thangiah.zeus.core.Nodes;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */
public class TOPNodesLinkedList
    extends NodesLinkedList
    implements java.io.Serializable, java.lang.Cloneable {

  public static final long serialVersionUID = 1;

  /**
   * Constructor
   */
  public TOPNodesLinkedList() {
    setHead(new TOPNodes(new TOPShipment(0, TOPProblemInfo.startXCoord, TOPProblemInfo.startYCoord, 0, 0, "D","D")));
    setTail(new TOPNodes(new TOPShipment( -1, TOPProblemInfo.endXCoord, TOPProblemInfo.endYCoord, 0, 0, "D","D")));
  }

  /**
   * Constructor
   * @param tT truck type
   * @param depotX depot x-coordinate
   * @param depotY depot y-coordinate
   */
  public TOPNodesLinkedList(TOPTruckType tT, float depotX, float depotY, int tN) {
    setTruckType(tT);
    setTruckNum(tN);
    setFeasibility(new TOPFeasibility(getTruckType().getMaxDuration(), getTruckType().getMaxCapacity(), this));
    setHead(new TOPNodes(new TOPShipment(0, depotX, depotY, 0, 0, "D", "D")));
    setTail(new TOPNodes(new TOPShipment( -1, TOPProblemInfo.endXCoord, TOPProblemInfo.endYCoord, 0, 0, "D",
					"D")));
    getHead().setNext(getTail());
    getTail().setPrev(getHead());

    //assign the attributes
    setAttributes(new TOPAttributes());
  }

  /**
   * Constructor
   * @param tT truck type
   * @param depotX depot x-coordinate
   * @param depotY depot y-coordinate
   */
  public TOPNodesLinkedList(TOPTruckType tT, float depotX, float depotY, float endDepotX, float endDepotY,
			    int tN) {
    setTruckType(tT);
    setTruckNum(tN);
    setFeasibility(new TOPFeasibility(truckType.getMaxDuration(), getTruckType().getMaxCapacity(), this));
    setHead(new TOPNodes(new TOPShipment(0, depotX, depotY, 0, 0, "D", "D")));
    setTail(new TOPNodes(new TOPShipment( -1, endDepotX, endDepotY, 0, 0, "D", "D")));
    getHead().setNext(getTail());
    getTail().setPrev(getHead());

    //assign the TOP attributes
    setAttributes(new TOPAttributes());
  }

  /**
   * Returns the first cell in the linked list
   * @return first cell
   */
  public TOPNodes getTOPHead() {
    return (TOPNodes)getHead();
  }

  /**
   * Returns the last cell in the linked list
   * @return TOPNodes
   */
  public TOPNodes getTOPTail() {
    return (TOPNodes)getTail();
  }

  /**
   * Returns the attributes of the linked list
   * @return TOPAttributes
   */
  public TOPAttributes getTOPAttributes() {
    return (TOPAttributes)getAttributes();
  }

  /**
   * Returns the feasibility of the linked list
   * @return TOPFeasibility
   */
  public TOPFeasibility getTOPFeasibility() {
    return (TOPFeasibility)getFeasibility();
  }

  /**
   * Returns the type of truck associated with this linked list
   * @return TOPTruckType
   */
  public TOPTruckType getTOPTruckType() {
    return (TOPTruckType)getTruckType();
  }

  /**
   * Removes the node with the lowest demand from the list
   * Added by David Crissman
   */
  public void removeLowestScoringShipment() {
    float lowScore = Float.MAX_VALUE;
    TOPNodes currentNode, lowestNode;

    //Find the node with the lowest demand
    lowestNode = null;
    currentNode = getTOPHead();
    while (currentNode != null) {

      if (currentNode.getShipment().getTruckTypeNeeded() != "D") {
	if (currentNode.getTOPShipment().getDemand() < lowScore) {
	  lowestNode = currentNode;
	  lowScore = currentNode.getTOPShipment().getDemand();
	}
      }

      currentNode = currentNode.getTOPNext();
    }

    //Remove the lowest scoring node from the list
    if (lowestNode != null) {
      if (lowestNode.getTOPPrev() != null) {
	lowestNode.getTOPPrev().setNext(lowestNode.getTOPNext());
	if (lowestNode.getTOPNext() != null) {
	  lowestNode.getTOPNext().setPrev(lowestNode.getTOPPrev());
	}
	else {
	  this.setTail(lowestNode.getTOPPrev());
	}
      }
      else {
	this.setHead(lowestNode.getTOPNext());
	if (lowestNode.getTOPNext() != null) {
	  lowestNode.getTOPNext().setPrev(lowestNode.getTOPPrev());
	}
	else {
	  this.setTail(lowestNode.getTOPPrev());
	}
      }
    }
  }

  /**
   * This is a stub - Leave it as it is
   * The concrete getInsertShipment will be declared by the class inheriting this
   * class and implementing the actual insertion of shipment
   * @param currNodesLL current nodes linked list
   * @param theShipment shipment to be inserted
   * @return true if inserted, false if not
   */

  public boolean getInsertShipment(TOPNodesLinkedList currNodeLL, TOPShipment theShipment) {
    return false;
  }

  public boolean getInsertShipment(TOPNodesLinkedList currNodeLL, TOPShipment theShipment,
				   TOPShipmentLinkedList mainShipments) {
    return false;
  }

  /**
   * Inserts a shipment into the route, creating a new Nodes for the
   * Shipment. Will attempt to put the newly created point cell into every
   * possible location in the route in an attempt to find the best possible
   * initial solution.
   * @param theShipment the shipment to insert
   * @return true if inserted, false if not
   */
  public boolean insertShipment(TOPShipment theShipment) {
    TOPNodesLinkedList status; //Method for inserting the shipment into a truck
    boolean stat; //Indicates whether or not the insertion was successful

    status = (TOPNodesLinkedList)TOPProblemInfo.insertShipType;
    stat = status.getInsertShipment(this, theShipment);

    return stat;
  }

  /**
   * Inserts a shipment into the route, creating a new Nodes for the
   * Shipment. Will attempt to put the newly created point cell into every
   * possible location in the route in an attempt to find the best possible
   * initial solution.
   * @param theShipment the shipment to insert
   * @return true if inserted, false if not
   */
  public boolean insertShipment(TOPShipment theShipment, TOPShipmentLinkedList mainShipments) {
    TOPNodesLinkedList status; //Method for inserting the shipment into a truck
    boolean stat; //Indicates whether or not the insertion was successful

    status = (TOPNodesLinkedList)TOPProblemInfo.insertShipType;
    stat = status.getInsertShipment(this, theShipment, mainShipments);

    return stat;
  }

  /**
   * Same as insertShipment except the insertion parameter must be specified
   * as a point cell and also the previous point cell is returned. This method
   * is used by the local optimization methods
   * @param insertNode Nodes that is to be inserted into the route
   * @return Nodes that is previous to the inserted Nodes.
   */
  public Nodes insertNodes(Nodes insertNodeIn) {
    TOPNodes insertNode = (TOPNodes)insertNodeIn;
    boolean isDiagnostic = false;
    TOPNodes pcBeforeInsertNode = null;
    TOPShipment theShipment = insertNode.getTOPShipment();

    if (isDiagnostic) {
      System.out.println("========================================");
      System.out.println("In InsertNodes in Nodeslinked list");
      System.out.println("Nodes to be inserted " + theShipment.getIndex());
      System.out.println("Route to be inserted " + this.getRouteString());
      System.out.println("Cost before insertion " + this.getCost());
    }

    //The route is empty
    if (getTOPHead().getTOPNext() == getTOPTail()) {
      getTOPHead().setNext(insertNode);
      getTOPTail().setPrev(insertNode);
      insertNode.setPrev(getTOPHead());
      insertNode.setNext(getTOPTail());

      pcBeforeInsertNode = getTOPHead(); //return head depot

      if (isDiagnostic) {
	System.out.println("Route is empty");
	System.out.println("After inserting the node " + this.getRouteString());
      }

      if (getTOPFeasibility().isFeasible()) {
	this.removeNodes(insertNode); //route is infeasible, remove this cell
	if (isDiagnostic) {
	  System.out.println("Insertion infeasible - Returning null");
	  System.out.println("Returning to original route " + this.getRouteString());
	  System.out.println("================== Exiting insertNodes ");
	}
	return null;
      }
    }
    //The route is not empty
    else {
      double cost = Double.MAX_VALUE;
      TOPNodes costCell = null; //cell after which the new cell was inserted to achieve cost
      TOPNodes prevCell = getTOPHead();
      TOPNodes nextCell = getTOPHead().getTOPNext();

      if (isDiagnostic) {
	System.out.println("Route is not empty");
      }

      //Loop through all the cells looking for the cheapest place to put the new cell.
      while (nextCell != null) {
	//Insert the cell after current prevCell
	prevCell.setNext(insertNode);
	insertNode.setPrev(prevCell);
	insertNode.setNext(nextCell);
	nextCell.setPrev(insertNode);

	//Calculate the cost
	double tempCost = TOPProblemInfo.nodesLLLevelCostF.getTotalCost(this);
	if (isDiagnostic) {
	  System.out.println("After inserting node " + this.getRouteString());
	  System.out.println("Cost after insertion " + tempCost);
	}

	//Check to see if the new route exceeds the maximum distance allowed
	if (getTOPFeasibility().isFeasible()) {
	  //decide if this cell should be saved
	  if (tempCost < cost) {
	    cost = tempCost;
	    costCell = prevCell;
	  }
	  if (isDiagnostic) {
	    System.out.println("Insertion is feasible ");
	    System.out.println("Cost before and after insertion " + cost + " " +
			       tempCost);
	  }
	}

	//Remove the new cell
	prevCell.setNext(nextCell);
	nextCell.setPrev(prevCell);
	insertNode.setNext(null);
	insertNode.setPrev(null);
	if (isDiagnostic) {
	  System.out.println("After removing the node from the route " +
			     this.getRouteString());
	}

	//Set prevCell and nextCell to the next cells in linked list
	prevCell = nextCell;
	nextCell = prevCell.getTOPNext();
      }

      if (costCell != null) {
	//Put the cell in the cheapest place you found
	prevCell = costCell;
	nextCell = prevCell.getTOPNext();
	prevCell.setNext(insertNode);
	insertNode.setPrev(prevCell);
	insertNode.setNext(nextCell);
	nextCell.setPrev(insertNode);

	pcBeforeInsertNode = prevCell;
	if (isDiagnostic) {
	  System.out.println("Cost is not null");
	  System.out.println(
	      "After inserting the cell in the cheapest place found" +
	      this.getRouteString());
	}
      }
      else {
	if (isDiagnostic) {
	  System.out.println("Cost is null");
	}
	return null;
      }
    }
    theShipment.setAssigned(true);
    return pcBeforeInsertNode;
  }

  /**
   * Returns and exact copy of the nodes linked list object
   * @return Object
   */
  public Object clone() {
    TOPNodesLinkedList clonedNodesLinkedList = new TOPNodesLinkedList();

    clonedNodesLinkedList.setAttributes((TOPAttributes)this.getTOPAttributes().clone());
    clonedNodesLinkedList.setFeasibility( (TOPFeasibility)this.getTOPFeasibility().clone());
    clonedNodesLinkedList.setTruckType( (TOPTruckType)this.getTOPTruckType().clone());
    clonedNodesLinkedList.setTruckNum(this.getTruckNum());
    clonedNodesLinkedList.setHead( (TOPNodes)this.getTOPHead().clone());

    this.expandRoute();

    //If the list is not empty, add each node to the clone
    if (this.getTOPHead() != this.getTOPTail()) {
      TOPNodes currentNodes = clonedNodesLinkedList.getTOPHead();
      TOPNodes nextNodes = this.getTOPHead().getTOPNext();

      while (nextNodes != null) {
	currentNodes.setNext( (TOPNodes)nextNodes.clone()); //create the next depot
	currentNodes.getTOPNext().setPrev(currentNodes); //set the next depot's prev
	currentNodes = currentNodes.getTOPNext();
	nextNodes = nextNodes.getTOPNext();

	//Once next is null, we have found the tail of the list
	if (nextNodes == null) {
	  clonedNodesLinkedList.setTail(currentNodes);
	  currentNodes.setNext(null);
	}
      }
    }
    else {
      clonedNodesLinkedList.setTail(clonedNodesLinkedList.getHead());
    }

    //Set the route for the feasibility
    clonedNodesLinkedList.getTOPFeasibility().setRoute(clonedNodesLinkedList);

    return clonedNodesLinkedList;
  }
}

class LinearGreedyInsertShipment
    extends TOPNodesLinkedList {

  /**
   * Inserts the shipment into the nodes linked list
   * @param currNodeLL TOPNodesLinkedList
   * @param theShipment TOPShipment
   * @return boolean
   */
  public boolean getInsertShipment(TOPNodesLinkedList currNodeLL, TOPShipment theShipment) {
    //currNodeLL is the reference to the current node linked list being considered for insertion
    //theShipment is the shipment to be inserted
    boolean retValue = true;
    TOPNodes bestCell, nextCell, prevCell, tempCell, theCell;
    double bestCost, currentCost;

    theCell = new TOPNodes(theShipment);
    // the route is empty
    if (currNodeLL.getTOPHead().getTOPNext() == currNodeLL.getTOPTail()) {
      currNodeLL.getTOPHead().setNext(theCell);
      currNodeLL.getTOPTail().setPrev(theCell);
      theCell.setPrev(currNodeLL.getTOPHead());
      theCell.setNext(currNodeLL.getTOPTail());

      //if its not feasible, return route to what it was and return false
      if (!currNodeLL.getTOPFeasibility().isFeasible()) {
	currNodeLL.getTOPHead().setNext(currNodeLL.getTOPHead());
	currNodeLL.getTOPTail().setPrev(currNodeLL.getTOPTail());

	retValue = false;
      }
    }
    // the route is not empty
    else {
      bestCost = Double.MAX_VALUE;
      bestCell = null; //cell after which the new cell was inserted to achieve cost

      prevCell = currNodeLL.getTOPHead();
      nextCell = currNodeLL.getTOPHead().getTOPNext();
      tempCell = nextCell;
      while (nextCell != null) {
	//insert the cell after current prevCell
	prevCell.setNext(theCell);
	theCell.setPrev(prevCell);
	theCell.setNext(nextCell);
	nextCell.setPrev(theCell);

	//check to see if the new route is feasible
	if (currNodeLL.getTOPFeasibility().isFeasible()) {
	  //calculate the cost
	  currentCost = TOPProblemInfo.nodesLLLevelCostF.getTotalCost(currNodeLL);

	  //decide if this cell should be saved
	  if (currentCost < bestCost) {
	    bestCost = currentCost;
	    bestCell = prevCell;
	  }
	}
	//remove the new cell
	prevCell.setNext(nextCell);
	nextCell.setPrev(prevCell);
	theCell.setNext(null);
	theCell.setPrev(null);

	//set prevCell and nextCell to the next cells in linked list
	prevCell = nextCell;
	nextCell = prevCell.getTOPNext();
	if (nextCell == tempCell) {
	  break; // Stop looping if the final cell is not null
	}
      }

      if (bestCell != null) {
	prevCell = bestCell;
	nextCell = prevCell.getTOPNext();
	prevCell.setNext(theCell);
	theCell.setPrev(prevCell);
	theCell.setNext(nextCell);
	nextCell.setPrev(theCell);
      }
      else {
	retValue = false;
      }
    }

    if (retValue == true) {
      theShipment.setAssigned(true);
    }
    TOPProblemInfo.nodesLLLevelCostF.calculateTotalsStats(currNodeLL);
    return retValue;
  }

  //The WhoAmI methods gives the id of the assigned object
  //It is a static method so that it can be accessed without creating an object
  public static String WhoAmI() {
    return ("Insertion Type: Linear greedy insertion heuristic");
  }

  public Nodes insertNodes(Nodes nodes) {
    System.out.println("HELLO: InsertNodes was called in from NodesLinkedList");
    return null;
  }
}
