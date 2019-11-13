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

  /* public double getTotalConstraintCost(Object o) {
     TOPTruckLinkedList tLL = (TOPTruckLinkedList) o;
     setTotalConstraintCost(o);

     return tLL.getAttributes().totalConstraintCost;
   }*/

  /*public double getTotalCrossRoadPenaltyCost(Object o) {
    TOPTruckLinkedList tLL = (TOPTruckLinkedList) o;
    setTotalCrossRoadPenaltyCost(o);

    return tLL.getAttributes().totalCrossRoadPenaltyCost;
     }*/

  /*public double getTotalTurnAroundPenaltyCost(Object o) {
    TOPTruckLinkedList tLL = (TOPTruckLinkedList) o;
    setTotalTurnAroundPenaltyCost(o);

    return tLL.getAttributes().totalTurnAroundPenaltyCost;
     }*/

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
    truckLL.getAttributes().setTotalCost(0);

    Truck t = truckLL.getHead();

    while (t != truckLL.getTail()) {
      if (!t.isEmptyMainNodes()) {
        truckLL.getAttributes().setTotalCost(truckLL.getAttributes().getTotalCost() + ZeusProblemInfo.getTruckLevelCostF().
            getTotalCost(t));
      }

      t = t.getNext();
    }
  }

  /*public void setTotalConstraintCost(Object o) {
    TOPTruckLinkedList truckLL = (TOPTruckLinkedList) o;
    truckLL.getAttributes().totalConstraintCost = 0;

    Truck t = truckLL.getHead();

    while (t != getTail()) {
      if (!t.isEmpty()) {
        truckLL.getAttributes().totalConstraintCost += ZeusProblemInfo.truckLevelCostF.
            getTotalConstraintCost(t);
      }

      t = t.getNext();
    }
     }*/

  /*public void setTotalCrossRoadPenaltyCost(Object o) {
    TOPTruckLinkedList truckLL = (TOPTruckLinkedList) o;
    truckLL.getAttributes().totalCrossRoadPenaltyCost = 0;

    Truck t = truckLL.getHead();

    while (t != getTail()) {
      if (!t.isEmpty()) {
        truckLL.getAttributes().totalCrossRoadPenaltyCost += ZeusProblemInfo.
            truckLevelCostF.getTotalCrossRoadPenaltyCost(t);
      }

      t = t.getNext();
    }
     }*/

  /* public void setTotalTurnAroundPenaltyCost(Object o) {
     TOPTruckLinkedList truckLL = (TOPTruckLinkedList) o;
     truckLL.getAttributes().totalTurnAroundPenaltyCost = 0;

     Truck t = truckLL.getHead();

     while (t != getTail()) {
       if (!t.isEmpty()) {
         truckLL.getAttributes().totalTurnAroundPenaltyCost += ZeusProblemInfo.
             truckLevelCostF.getTotalTurnAroundPenaltyCost(t);
       }

       t = t.getNext();
     }
   }*/

  public void setTotalDemand(Object o) {
    TOPTruckLinkedList truckLL = (TOPTruckLinkedList) o;
    truckLL.getAttributes().setTotalDemand(0);

    TOPTruck t = truckLL.getHead();

    while (t != truckLL.getTail()) {
      if (!t.isEmptyMainNodes()) {
        truckLL.getAttributes().setTotalDemand(truckLL.getAttributes().getTotalDemand() + ZeusProblemInfo.getTruckLevelCostF().
            getTotalDemand(t));
      }

      t = t.getTOPNext();
    }
  }

  public void setTotalDistance(Object o) {
    TOPTruckLinkedList truckLL = (TOPTruckLinkedList) o;
    truckLL.getAttributes().getTotalDistance();

    TOPTruck t = truckLL.getHead();

    while (t != truckLL.getTail()) {
      if (!t.isEmptyMainNodes()) {
        truckLL.getAttributes().setTotalDistance(truckLL.getAttributes().getTotalDistance() + ZeusProblemInfo.getTruckLevelCostF().
            getTotalDistance(t));
      }

      t = t.getTOPNext();
    }
  }

  public void setTotalTravelTime(Object o) {
    TOPTruckLinkedList truckLL = (TOPTruckLinkedList) o;
    truckLL.getAttributes().setTotalTravelTime(0);

    TOPTruck t = truckLL.getHead();

    while (t != truckLL.getTail()) {
      if (!t.isEmptyMainNodes()) {
        truckLL.getAttributes().setTotalTravelTime(truckLL.getAttributes().getTotalTravelTime() + ZeusProblemInfo.getTruckLevelCostF().
            getTotalTravelTime(t));
      }

      t = t.getTOPNext();
    }
  }

  public void setMaxTravelTime(Object o) {
    double max = 0;
    TOPTruckLinkedList truckLL = (TOPTruckLinkedList) o;
    TOPTruck t = truckLL.getHead();

    while (t != truckLL.getTail()) {
      if (!t.isEmptyMainNodes()) {
        if (ZeusProblemInfo.getTruckLevelCostF().getMaxTravelTime(t) > max) {
          max = ZeusProblemInfo.getTruckLevelCostF().getMaxTravelTime(t);
        }
      }

      t = t.getTOPNext();
    }

    truckLL.getAttributes().setMaxTravelTime(max);
  }

  public void setAvgTravelTime(Object o) {
    double avg = 0;
    TOPTruckLinkedList truckLL = (TOPTruckLinkedList) o;
    TOPTruck t = truckLL.getHead();

    if ( (truckLL.getSize() != 0) &&
        (ZeusProblemInfo.getTruckLLLevelCostF().getTotalDemand(truckLL) != 0)) {
      while (t != truckLL.getTail()) {
        if (!t.isEmptyMainNodes()) {
          avg +=
              (ZeusProblemInfo.getTruckLevelCostF().getAvgTravelTime(t) *
               ZeusProblemInfo.getTruckLevelCostF().getTotalDemand(t));
        }

        t = t.getTOPNext();
      }

      truckLL.getAttributes().setAvgTravelTime(avg /
          ZeusProblemInfo.getTruckLLLevelCostF().getTotalDemand(truckLL));
    }
    else {
      truckLL.getAttributes().setAvgTravelTime(0);
    }
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
