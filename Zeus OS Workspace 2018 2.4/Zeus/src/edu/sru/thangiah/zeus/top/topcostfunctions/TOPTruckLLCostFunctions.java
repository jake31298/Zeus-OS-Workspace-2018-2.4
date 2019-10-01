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

    //return truckLL.attributes.totalTravelTime;
    return truckLL.getAttributes().getTotalTravelTime();

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
    //truckLL.getAttributes().totalCost = 0;
    truckLL.getAttributes().setTotalCost(0);


    Truck t = truckLL.getHead();

    while (t != null) {
      //if (!t.isEmpty()) { // Original
      if (!((TOPTruck) t).isEmpty()) {
  
        //truckLL.getAttributes().totalCost += ProblemInfo.truckLevelCostF.
        truckLL.getAttributes().setTotalCost((ZeusProblemInfo.getTruckLevelCostF().getTotalCost(t)) 
        		+ truckLL.getAttributes().getTotalCost());
      }

      t = t.getNext();
    }
  }

  public void setTotalDemand(Object o) {
    TOPTruckLinkedList truckLL = (TOPTruckLinkedList) o;
    //truckLL.getAttributes().totalDemand = 0;
    truckLL.getAttributes().setTotalDemand(0);

    TOPTruck t = (TOPTruck) truckLL.getHead();
    TOPTruck temp = t;

    while (t != null) {
      if (!t.isEmpty())
      {
        //truckLL.getAttributes().totalDemand += ProblemInfo.truckLevelCostF.getTotalDemand(t);
        truckLL.getAttributes().setTotalDemand((ZeusProblemInfo.getTruckLevelCostF().getTotalDemand(t)) 
        		+ truckLL.getAttributes().getTotalDemand());

      }

      t = (TOPTruck) t.getNext();
      if(t == temp){
        break;  // Break if the list loops back on itself instead of ending in a null.
      }
    }
  }

  public void setTotalDistance(Object o) {
    TOPTruckLinkedList truckLL = (TOPTruckLinkedList) o;
    //truckLL.getAttributes().totalDistance = 0;
    truckLL.getAttributes().setTotalDistance(0);


    Truck t = truckLL.getHead();

    while (t != null) {
      //if (!t.isEmpty()) {
      if (!((TOPTruck) t).isEmpty()) {

        //truckLL.getAttributes().totalDistance += ProblemInfo.truckLevelCostF.getTotalDistance(t);
        truckLL.getAttributes().setTotalDistance((ZeusProblemInfo.getTruckLevelCostF().getTotalDistance(t)) 
        		+ truckLL.getAttributes().getTotalDistance());

      }

      t = t.getNext();
    }
  }

  public void setTotalTravelTime(Object o) {
    TOPTruckLinkedList truckLL = (TOPTruckLinkedList) o;
    //truckLL.getAttributes().totalTravelTime = 0;
    truckLL.getAttributes().setTotalTravelTime(0);


    Truck t = truckLL.getHead();

    while (t != null) {
      //if (!t.isEmpty()) {
      if (!((TOPTruck) t).isEmpty()) {

        //truckLL.getAttributes().totalTravelTime += ProblemInfo.truckLevelCostF.getTotalTravelTime(t);
        truckLL.getAttributes().setTotalTravelTime((ZeusProblemInfo.getTruckLevelCostF().getTotalTravelTime(t)) 
        		+ truckLL.getAttributes().getTotalTravelTime());

      }

      t = t.getNext();
    }
  }

  public void setMaxTravelTime(Object o) {
    double max = 0;
    TOPTruckLinkedList truckLL = (TOPTruckLinkedList) o;
    Truck t = truckLL.getHead();

    while (t != null) {
      //if (!t.isEmpty()) {
      if (!((TOPTruck) t).isEmpty()) {

        //if (ProblemInfo.truckLevelCostF.getMaxTravelTime(t) > max) {
        if (ZeusProblemInfo.getTruckLevelCostF().getMaxTravelTime(t) > max) {

          //max = ProblemInfo.truckLevelCostF.getMaxTravelTime(t);
          max = ZeusProblemInfo.getTruckLevelCostF().getMaxTravelTime(t);

        }
      }

      t = t.getNext();
    }

    //truckLL.getAttributes().maxTravelTime = max;
    truckLL.getAttributes().setMaxTravelTime(max);

  }

  public void setAvgTravelTime(Object o) {
    double avg = 0;
    TOPTruckLinkedList truckLL = (TOPTruckLinkedList) o;
    Truck t = truckLL.getHead();

    if ( (truckLL.getSize() != 0) &&
        //(ProblemInfo.truckLLLevelCostF.getTotalDemand(truckLL) != 0)) {
        (ZeusProblemInfo.getTruckLLLevelCostF().getTotalDemand(truckLL) != 0)) {

      while (t != null) {
        //if (!t.isEmpty()) {
        if (!((TOPTruck) t).isEmpty()) {

          avg +=
              //(ProblemInfo.truckLevelCostF.getAvgTravelTime(t) * ProblemInfo.truckLevelCostF.getTotalDemand(t));
              (ZeusProblemInfo.getTruckLevelCostF().getAvgTravelTime(t) 
            		  * ZeusProblemInfo.getTruckLevelCostF().getTotalDemand(t));

        }

        t = t.getNext();
      }

      //truckLL.getAttributes().avgTravelTime = avg / ProblemInfo.truckLLLevelCostF.getTotalDemand(truckLL);
      truckLL.getAttributes().setAvgTravelTime(avg / (ZeusProblemInfo.getTruckLLLevelCostF().getTotalDemand(truckLL)));

    }
    else {
      //truckLL.getAttributes().avgTravelTime = 0;
      truckLL.getAttributes().setAvgTravelTime(0);

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

// Added 1 OCT 2019
@Override
public int getTotalDays(Object arg0) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public int getTotalStops(Object arg0) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public void setTotalDays(Object arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void setTotalStops(Object arg0) {
	// TODO Auto-generated method stub
	
}
}
