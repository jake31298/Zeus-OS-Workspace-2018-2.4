package edu.sru.thangiah.zeus.top.topcostfunctions;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.top.*;

/**
 * Cost functions specific to TOP Truck Linked List level
 * <p>Title: Zeus - A Unified Object Oriented Model for TOP's</p>
 * <p>Description: cost functions specific to TOP Truck Linked List level</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */
public class TOPTruckLLCostFunctions
    extends TOPAbstractCostFunctions implements
    java.io.Serializable  {

  public double getTotalCost(Object o) {
    TOPTruckLinkedList tLL = (TOPTruckLinkedList) o;
    setTotalCost(o);

    return tLL.getAttributes().getTotalCost();
  }

  public float getTotalDemand(Object o) {
    TOPTruckLinkedList truckLL = (TOPTruckLinkedList) o;
    setTotalDemand(o);

    return (int) truckLL.getAttributes().getTotalDemand();
  }

  public double getTotalDistance(Object o) {
    TOPTruckLinkedList truckLL = (TOPTruckLinkedList) o;
    setTotalDistance(o);

    return truckLL.getAttributes().getTotalDistance();
  }

  public double getTotalTravelTime(Object o) {
    TOPTruckLinkedList truckLL = (TOPTruckLinkedList) o;
    setTotalTravelTime(o);

    return truckLL.attributes.totalTravelTime;
  }

  public double getMaxTravelTime(Object o) {
    TOPTruckLinkedList truckLL = (TOPTruckLinkedList) o;
    setMaxTravelTime(o);

    return truckLL.getAttributes().getMaxTravelTime();
  }

  public double getAvgTravelTime(Object o) {
    TOPTruckLinkedList truckLL = (TOPTruckLinkedList) o;
    setAvgTravelTime(o);

    return truckLL.getAttributes().getAvgTravelTime();
  }

  public void setTotalCost(Object o) {
    TOPTruckLinkedList truckLL = (TOPTruckLinkedList) o;
    truckLL.getAttributes().totalCost = 0;

    Truck t = truckLL.getHead();

    while (t != null) {
      if (!t.isEmpty()) {
        truckLL.getAttributes().totalCost += ProblemInfo.truckLevelCostF.
            getTotalCost(t);
      }

      t = t.getNext();
    }
  }

  public void setTotalDemand(Object o) {
    TOPTruckLinkedList truckLL = (TOPTruckLinkedList) o;
    truckLL.getAttributes().totalDemand = 0;

    TOPTruck t = (TOPTruck) truckLL.getHead();
    TOPTruck temp = t;

    while (t != null) {
      if (!t.isEmpty())
      {
        truckLL.getAttributes().totalDemand += ProblemInfo.truckLevelCostF.getTotalDemand(t);
      }

      t = (TOPTruck) t.getNext();
      if(t == temp){
        break;  // Break if the list loops back on itself instead of ending in a null.
      }
    }
  }

  public void setTotalDistance(Object o) {
    TOPTruckLinkedList truckLL = (TOPTruckLinkedList) o;
    truckLL.getAttributes().totalDistance = 0;

    Truck t = truckLL.getHead();

    while (t != null) {
      if (!t.isEmpty()) {
        truckLL.getAttributes().totalDistance += ProblemInfo.truckLevelCostF.getTotalDistance(t);
      }

      t = t.getNext();
    }
  }

  public void setTotalTravelTime(Object o) {
    TOPTruckLinkedList truckLL = (TOPTruckLinkedList) o;
    truckLL.getAttributes().totalTravelTime = 0;

    Truck t = truckLL.getHead();

    while (t != null) {
      if (!t.isEmpty()) {
        truckLL.getAttributes().totalTravelTime += ProblemInfo.truckLevelCostF.
            getTotalTravelTime(t);
      }

      t = t.getNext();
    }
  }

  public void setMaxTravelTime(Object o) {
    double max = 0;
    TOPTruckLinkedList truckLL = (TOPTruckLinkedList) o;
    Truck t = truckLL.getHead();

    while (t != null) {
      if (!t.isEmpty()) {
        if (ProblemInfo.truckLevelCostF.getMaxTravelTime(t) > max) {
          max = ProblemInfo.truckLevelCostF.getMaxTravelTime(t);
        }
      }

      t = t.getNext();
    }

    truckLL.getAttributes().maxTravelTime = max;
  }

  public void setAvgTravelTime(Object o) {
    double avg = 0;
    TOPTruckLinkedList truckLL = (TOPTruckLinkedList) o;
    Truck t = truckLL.getHead();

    if ( (truckLL.getSize() != 0) &&
        (ProblemInfo.truckLLLevelCostF.getTotalDemand(truckLL) != 0)) {
      while (t != null) {
        if (!t.isEmpty()) {
          avg +=
              (ProblemInfo.truckLevelCostF.getAvgTravelTime(t) * ProblemInfo.truckLevelCostF.getTotalDemand(t));
        }

        t = t.getNext();
      }

      truckLL.getAttributes().avgTravelTime = avg / ProblemInfo.truckLLLevelCostF.getTotalDemand(truckLL);
    }
    else {
      truckLL.getAttributes().avgTravelTime = 0;
    }
  }

  public void calculateTotalsStats(Object o) {
    setTotalDemand(o);
    setTotalDistance(o);
    setTotalTravelTime(o);
    setMaxTravelTime(o);
    setAvgTravelTime(o);
    setTotalCost(o);
  }
}
