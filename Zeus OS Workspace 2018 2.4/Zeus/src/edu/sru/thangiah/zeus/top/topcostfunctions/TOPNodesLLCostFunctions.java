package edu.sru.thangiah.zeus.top.topcostfunctions;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.opsys.top.TOPNodesLinkedList;
import edu.sru.thangiah.opsys.top.TOPProblemInfo;
import edu.sru.thangiah.opsys.top.TOPNodes;
//import edu.sru.thangiah.opsys.top.LinearGreedyInsertShipment;

/**
 * Cost functions specific to TOPNodesLinkedList level
 * <p>Title: Zeus - A Unified Object Oriented Model for TOP's</p>
 * <p>Description: cost functions specific to TOP Nodes LinkedList level</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */
public class TOPNodesLLCostFunctions
    extends TOPAbstractCostFunctions implements
    java.io.Serializable  {

  public double getTotalCost(Object o) {
    TOPNodesLinkedList vNodes = (TOPNodesLinkedList) o;
    setTotalCost(o);

    return vNodes.attributes.totalCost;
  }

  public float getTotalDemand(Object o) {
    TOPNodesLinkedList vNodes = (TOPNodesLinkedList) o;
    setTotalDemand(o);

    return (int) vNodes.attributes.totalDemand;
  }

  public double getTotalDistance(Object o) {
    TOPNodesLinkedList vNodes = (TOPNodesLinkedList) o;
    setTotalDistance(o);

    return vNodes.attributes.totalDistance;
  }

  public double getTotalTravelTime(Object o) {
    TOPNodesLinkedList vNodes = (TOPNodesLinkedList) o;
    setTotalTravelTime(o);

    return vNodes.attributes.totalTravelTime;
  }

  public double getMaxTravelTime(Object o) {
    TOPNodesLinkedList vNodes = (TOPNodesLinkedList) o;
    setMaxTravelTime(o);

    return vNodes.attributes.maxTravelTime;
  }

  public double getAvgTravelTime(Object o) {
    TOPNodesLinkedList vNodes = (TOPNodesLinkedList) o;
    setAvgTravelTime(o);

    return vNodes.attributes.avgTravelTime;
  }

  /**
   * Sets the overall cost of the nodes linked list
   * @param o Object
   * Modified by Pete Schallot
   */
  public void setTotalCost(Object o) {
    TOPNodesLinkedList vNodes = (TOPNodesLinkedList) o;
    double score = getTotalDemand(o);
    double dist = getTotalDistance(o);

    switch ( TOPProblemInfo.costType) {
      case DISTANCE_PLUS_SCORE_INVERSE:
        vNodes.attributes.totalCost = dist + 1000/(score+1);
        break;
      case DISTANCE_PLUS_DISTANCE_TIMES_SCORE_INVERSE:
        vNodes.attributes.totalCost = dist + dist/(score+1);
        break;
      case DISTANCE:
        vNodes.attributes.totalCost = dist;
        break;
      case SCORE_INVERSE:
        vNodes.attributes.totalCost = 1000/(score+1);
        break;
    }

    if (vNodes.getTOPFeasibility().isPostOptFeasible() == false) {
      double distanceOver = dist - TOPProblemInfo.truckMaxTravelTime;
      vNodes.attributes.totalCost  *=  Math.pow(Math.abs(distanceOver),Math.abs(TOPProblemInfo.overMaxDistanceCostPenalty));
    }

  }

  public void setTotalDemand(Object o) {
    TOPNodesLinkedList vNodes = (TOPNodesLinkedList) o;

    Nodes tempCell = vNodes.getHead();
    int tempD = 0;

    Nodes tempCell2 = tempCell;

    while (tempCell != null) {
      tempD += tempCell.getDemand();
      tempCell = tempCell.getNext();
      if(tempCell == tempCell2){
        break;  // Avoid an infinite loop if the end node is not a null.
      }
    }
    vNodes.attributes.totalDemand = tempD;
  }

  public void setTotalDistance(Object o) {
    TOPNodesLinkedList vNodes = (TOPNodesLinkedList) o;
    float distTravelled = 0;



    TOPNodes left = vNodes.getTOPHead();
    if(left == null){
      Settings.printDebug(Settings.WARNING, "SetTotalDistance() was passed an empty nodes linked list.");
       vNodes.attributes.totalDistance = 0;
      return;
    }
    TOPNodes right = vNodes.getTOPHead().getTOPNext();


    while (right != null) {
      try {
        distTravelled += (float) Math.sqrt( (double) (((left.getShipment().getXCoord() - right.getShipment().getXCoord()) *
                                                       (left.getShipment().getXCoord() - right.getShipment().getXCoord())) +
                                                      ((left.getShipment().getYCoord() - right.getShipment().getYCoord()) *
                                                       (left.getShipment().getYCoord() - right.getShipment().getYCoord()))));
      }
      catch (ArithmeticException e) {
        System.out.println(e);
      }

      left = right;
      right = right.getTOPNext();
    }
    vNodes.attributes.totalDistance = distTravelled;
  }

  public void setTotalTravelTime(Object o) {
    TOPNodesLinkedList vNodes = (TOPNodesLinkedList) o;

    double travTime = 0;
    double dist = getTotalDistance(o);
    double milesPerMinute = TOPProblemInfo.averageBusSpeed / 60;
    travTime = milesPerMinute * dist;

    Nodes theCell = vNodes.getHead().getNext(); //start at the first customer
    Nodes tempCell = theCell;

    while (theCell != vNodes.getTail() && theCell != null) {
        travTime += (TOPProblemInfo.R_PICK_UP_TIME * theCell.getDemand());
      theCell = theCell.getNext();
      if(theCell == tempCell){
        break; // Break if the list loops back on itself instead of ending in a null.
      }
    }

    vNodes.attributes.totalTravelTime = travTime;
  }

  public void setMaxTravelTime(Object o) {
    TOPNodesLinkedList vNodes = (TOPNodesLinkedList) o;
    double linehaul = 0;
    vNodes.attributes.maxTravelTime = linehaul;
  }

  public void setAvgTravelTime(Object o) {
    TOPNodesLinkedList vNodes = (TOPNodesLinkedList) o;
    double aTT = 0;
    vNodes.attributes.avgTravelTime = aTT;
  }

  /** @todo Might not need CrossRoadPenaltyCost and TurnAroundPenaltyCost */
  public void calculateTotalsStats(Object o) {
    setTotalDemand(o);
    setTotalDistance(o);
    setTotalTravelTime(o);
    setMaxTravelTime(o);
    setAvgTravelTime(o);
    //setTotalCrossRoadPenaltyCost(o);
    //setTotalTurnAroundPenaltyCost(o);
    setTotalCost(o);
    //setTotalConstraintCost(o);
  }
}
