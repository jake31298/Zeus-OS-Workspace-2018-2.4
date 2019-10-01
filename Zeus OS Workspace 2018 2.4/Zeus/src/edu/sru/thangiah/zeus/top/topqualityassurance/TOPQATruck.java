package edu.sru.thangiah.zeus.top.topqualityassurance;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.qualityassurance.*;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */
public class TOPQATruck
    extends QATruck
    implements java.io.Serializable, java.lang.Cloneable {

  public TOPQATruck() {
  }

  public boolean checkDistanceConstraint(TOPQATruck truck) {
    boolean isDiagnostic = false;
    boolean status = true;
    double totalDistance = 0;
    double distance = 0;
    TOPQANode node1;
    TOPQANode node2;

    //Used in the comparison of distances
    int intDistance1;
    int intDistance2;

    if (isDiagnostic) {
      System.out.println();
      System.out.println("Truck being evaluated is " + truck.getIndex() + " with " + getNodes().size() + " nodes");
      System.out.println("Sequence of the route is:");
      for (int i = 0; i < getNodes().size(); i++) {
        node1 = (TOPQANode) getNodes().elementAt(i);
        System.out.print(" " + node1.getIndex());
      }
      System.out.println();
    }

    if (getNodes().size() == 0) {
      return status;
    }
    node1 = (TOPQANode) getNodes().elementAt(0);
    //compute the total travel distance of the route
    for (int i = 1; i < getNodes().size() - 1; i++) {
      node2 = (TOPQANode) getNodes().elementAt(i);
      distance = (float) Math.sqrt( (double) (((node1.getX() - node2.getX()) * (node1.getX() - node2.getX())) + ( (node1.getY() - node2.getY()) * (node1.getY() - node2.getY()))));
      totalDistance += distance;
      if (isDiagnostic) {
        System.out.println("    Distance from " + node1.getIndex() + " to " +
                           node2.getIndex() + " = " + distance);
      }
      node1 = node2;
    }
    node2 = (TOPQANode) getNodes().elementAt(0);
    distance = (float) Math.sqrt( (double) (node1.getX() - node2.getX())  * (node1.getX() - node2.getX()) + ((node1.getY() - node2.getY()) * (node1.getY() - node2.getY())));
    totalDistance += distance;

    //Convert the distance to integer values for comparison. The convertion
    //takes up to a precison of 3 decimal places. Comapring floats can lead
    //to inaccurate results as errors start occuring after the 4th decimal
    //place
    intDistance1 = (int) (truck.getDistance() * 1000);
    intDistance2 = (int) (totalDistance * 1000);

    if (isDiagnostic) {
      System.out.println("    Distance from " + node1.getIndex() + " to " +
                         node2.getIndex() + " = " + truck.getDistance());
      System.out.println("   Truck distance " + truck.getDistance() + " : Computed distance " + totalDistance);

    }

    //Check if the computed distances are the same
    if (intDistance1 != intDistance2) {
      Settings.printDebug(Settings.ERROR, "Truck # " + truck.getIndex() + " distance does not match computed distance " + truck.getDistance() + " " + totalDistance);
      status = false;
      return status;
    }
    //check if it exceeds the maximum distance
    if (totalDistance > truck.getMaxDistance()) {
      Settings.printDebug(Settings.ERROR, "Truck # " + truck.getIndex() + "distance does exceeds maximum distance " + totalDistance + " " + getMaxDistance());
      status = false;
      return status;
    }
    return status;
  }

  public boolean checkCapacityConstraint(TOPQATruck truck) {
    boolean isDiagnostic = false;
    boolean status = true;
    double totalCapacity = 0;
    double capacity = 0;
    TOPQANode node;
    int intCap1, intCap2;



    if (isDiagnostic) {
      System.out.println();
      System.out.println("Truck being evaluated is " + truck.getIndex() + " with " + getNodes().size() + " nodes");
      System.out.println("Sequence of the route is:");
      for (int i = 0; i < getNodes().size(); i++) {
        node = (TOPQANode) getNodes().elementAt(i);
        System.out.print(" " + node.getIndex());
      }
      System.out.println();
    }

    if (getNodes().size() == 0) {
      return status;
    }
    //compute the total capacity of the route
    for (int i = 1; i < getNodes().size() - 1; i++) {
      node = (TOPQANode) getNodes().elementAt(i);
      capacity = node.getDemand();
      if (isDiagnostic) {
        System.out.println("    Capacity " + node.getIndex() + " is " + +capacity);
      }
      totalCapacity += capacity;
    }

    //Convert the capacity to integer values for comparison. The convertion
    //takes up to a precison of 3 decimal places. Comapring floats can lead
    //to inaccurate results as errors start occuring after the 4th decimal
    //place
    intCap1 = (int)(truck.getDemand() *1000);
    intCap2 = (int)(totalCapacity *1000);

    if (isDiagnostic) {
      System.out.println("   Truck capacity " + truck.getDemand() + " : Computed capacity " + totalCapacity);
    }
    //Check if the truck capacity is the same as the computed capacity
    if (intCap1 != intCap2) { //check up to 3 decimal placess
      Settings.printDebug(Settings.ERROR, "Truck # " + truck.getIndex() + " capacity does not match computed capacity " + truck.getDemand() + " " + totalCapacity);
      status = false;
      return status;
    }
    //check if it exceeds the maximum capacity
    if (totalCapacity > truck.getMaxDemand()) {
      Settings.printDebug(Settings.ERROR, "Truck # " + truck.getIndex() + "distance exceeds maximum capacity " + totalCapacity + " " + truck.getMaxDemand());
      status = false;
      return status;
    }
    return status;
  }
}
