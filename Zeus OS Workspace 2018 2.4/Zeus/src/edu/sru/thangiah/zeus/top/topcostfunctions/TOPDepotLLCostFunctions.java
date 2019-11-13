package edu.sru.thangiah.zeus.top.topcostfunctions;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.top.TOPDepot;
import edu.sru.thangiah.zeus.top.TOPDepotLinkedList;
import edu.sru.thangiah.zeus.top.TOPProblemInfo;
import edu.sru.thangiah.zeus.top.TOPTruck;

/**
 * Cost functions specific to Depot LinkedList level
 * <p>Title: Zeus - A Unified Object Oriented Model for TOP's</p>
 * <p>Description: cost functions specific to Depot LinkedList level</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */
public class TOPDepotLLCostFunctions
    extends TOPAbstractCostFunctions implements
    java.io.Serializable {

  public double getTotalCost(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    setTotalCost(o);

    return depotLL.getAttributes().getTotalCost();
  }

  /* public double getTotalConstraintCost(Object o) {
     TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
     setTotalConstraintCost(o);

     return depotLL.getAttributes().totalConstraintCost;
   }*/

  /*public double getTotalCrossRoadPenaltyCost(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    setTotalCrossRoadPenaltyCost(o);

    return depotLL.getAttributes().totalCrossRoadPenaltyCost;
     }*/

  /*public double getTotalTurnAroundPenaltyCost(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    setTotalTurnAroundPenaltyCost(o);

    return depotLL.getAttributes().totalTurnAroundPenaltyCost;
     }*/

  public float getTotalDemand(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    setTotalDemand(o);

    return (int) depotLL.getAttributes().getTotalDemand();
  }

  public double getTotalDistance(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    setTotalDistance(o);

    return depotLL.getAttributes().getTotalDistance();
  }

  public double getTotalTravelTime(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    setTotalTravelTime(o);

    return depotLL.getAttributes().getTotalTravelTime();
  }

  public double getMaxTravelTime(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    setMaxTravelTime(o);

    return depotLL.getAttributes().getMaxTravelTime();
  }

  public double getAvgTravelTime(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    setAvgTravelTime(o);

    return depotLL.getAttributes().getAvgTravelTime();
  }

  public void setTotalCost(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    double cost = 0;

    Depot d = depotLL.getHead();

    while (d != depotLL.getTail()) {
      cost += ZeusProblemInfo.getDepotLevelCostF().getTotalCost(d);
      d = d.getNext();
    }

    depotLL.getAttributes().setTotalCost(cost);
  }

  /*public void setTotalConstraintCost(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    double cost = 0;

    Depot d = depotLL.getHead();

    while (d != depotLL.getTail()) {
      cost += ZeusProblemInfo.depotLevelCostF.getTotalConstraintCost(d);
      d = d.getNext();
    }

    depotLL.getAttributes().totalConstraintCost = cost;
     }*/

  /*public void setTotalCrossRoadPenaltyCost(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    depotLL.getAttributes().totalCrossRoadPenaltyCost = 0;

    Depot d = depotLL.getHead();

    while (d != depotLL.getTail()) {
      depotLL.getAttributes().totalCrossRoadPenaltyCost += ZeusProblemInfo.
          depotLevelCostF.getTotalCrossRoadPenaltyCost(d);
      d = d.getNext();
    }
     }*/

  /*public void setTotalTurnAroundPenaltyCost(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    depotLL.getAttributes().totalTurnAroundPenaltyCost = 0;

    Depot d = depotLL.getHead();

    while (d != depotLL.getTail()) {
      depotLL.getAttributes().totalTurnAroundPenaltyCost += ZeusProblemInfo.
          depotLevelCostF.getTotalTurnAroundPenaltyCost(d);
      d = d.getNext();
    }
     }*/

  public void setTotalDemand(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    depotLL.getAttributes().setTotalDemand(0);

    Depot d = depotLL.getHead();

    while (d != depotLL.getTail()) {
      depotLL.getAttributes().setTotalDemand(depotLL.getAttributes().getTotalDemand() + (float) ZeusProblemInfo.getDepotLevelCostF().
          getTotalDemand(d));
      d = d.getNext();
    }
  }
 
	/**
	   * Chooses the highest-scoring trucks and marks them as team members, up to the maximum number of team members
	   * @param o Object
	   * Added by David Crissman
	   */
	  public void markTeamMembers(Object o) {
	    TOPDepotLinkedList depotLL = (TOPDepotLinkedList)o;
	    TOPTruck currentTruck, bestTruck;
	    double currentDemand, bestDemand;

	    //Update the total demand for all the trucks
	    setTotalDemand(o);

	    //Clear the current team members so that they can be re-evaluated
	    currentTruck = depotLL.getStartingDepot().getMainTrucks().getHead();
	    while (currentTruck != null) {
	      currentTruck.setIsTeamMember(false);
	      currentTruck = currentTruck.getTOPNext();
	    }

	    for (int i = 0; i < TOPProblemInfo.getNumTeamMembers(); i++) {
	      bestTruck = null;
	      bestDemand = 0.0;
	      currentTruck = depotLL.getStartingDepot().getMainTrucks().getHead();
	      while (currentTruck != null) {
	        if (currentTruck.getIsTeamMember() == false) {
	          currentDemand = TOPProblemInfo.getTruckLevelCostF().getTotalDemand(currentTruck);
	          if (currentDemand > bestDemand) {
	            bestTruck = currentTruck;
	            bestDemand = currentDemand;
	          }
	        }
	        currentTruck = currentTruck.getTOPNext();
	      }

	      if (bestTruck != null) {
	        bestTruck.setIsTeamMember(true);
	      }
	    }
	  }
  public void setTotalDistance(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    depotLL.getAttributes().setTotalDistance(0);

    Depot d = depotLL.getHead();

    while (d != depotLL.getTail()) {
      depotLL.getAttributes().setTotalDistance(depotLL.getAttributes().getTotalDistance()  + (float) ZeusProblemInfo.getDepotLevelCostF().
          getTotalDistance(d));
      d = d.getNext();
    }
  }

  public void setTotalTravelTime(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
     depotLL.getAttributes().setTotalTravelTime(0);

    Depot d = depotLL.getHead();

    while (d != depotLL.getTail()) {
      depotLL.getAttributes().setTotalTravelTime(depotLL.getAttributes().getTotalTravelTime() + ZeusProblemInfo.getDepotLevelCostF().
          getTotalTravelTime(d));
      d = d.getNext();
    }
  }

  public void setMaxTravelTime(Object o) {
    double max = 0;
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    Depot d = depotLL.getHead();

    while (d != depotLL.getTail()) {
      if (ZeusProblemInfo.getDepotLevelCostF().getMaxTravelTime(d) > max) {
        max = ZeusProblemInfo.getDepotLevelCostF().getMaxTravelTime(d);
      }

      d = d.getNext();
    }

    depotLL.getAttributes().setMaxTravelTime(max);
  }

  public void setAvgTravelTime(Object o) {
    double avg = 0;
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    Depot d = depotLL.getHead();

    if (ZeusProblemInfo.getDepotLLLevelCostF().getTotalDemand(depotLL) != 0) {
      while (d != depotLL.getTail()) {
        avg +=
            (ZeusProblemInfo.getDepotLevelCostF().getAvgTravelTime(d) *
             ZeusProblemInfo.getDepotLevelCostF().getTotalDemand(d));
        d = d.getNext();
      }

      depotLL.getAttributes().setAvgTravelTime(avg /
          ZeusProblemInfo.getDepotLLLevelCostF().getTotalDemand(depotLL));
    }
    else {
      depotLL.getAttributes().setAvgTravelTime(0);
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
