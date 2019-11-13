package edu.sru.thangiah.zeus.top.topcostfunctions;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.top.TOPDepot;
import edu.sru.thangiah.zeus.top.TOPDepotLinkedList;
import edu.sru.thangiah.zeus.top.TOPProblemInfo;
import edu.sru.thangiah.zeus.top.TOPTruck;

/**
 * Cost Functions specific to the TOP Depot level.
 * <p>Title: Zeus - A Unified Object Oriented Model for TOP's</p>
 * <p>Description: cost functions specific to TOPDepot level</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0

 */
public class TOPDepotCostFunctions
    extends TOPAbstractCostFunctions implements
    java.io.Serializable {

  public double getTotalCost(Object o) {
    TOPDepot depot = (TOPDepot) o;
    setTotalCost(o);

    return depot.getAttributes().getTotalCost();
  }

  /*public double getTotalConstraintCost(Object o) {
    TOPDepot depot = (TOPDepot) o;
    setTotalConstraintCost(o);

    return depot.getAttributes().totalConstraintCost;
     }*/

  /*public double getTotalCrossRoadPenaltyCost(Object o) {
    TOPDepot depot = (TOPDepot) o;
    setTotalCrossRoadPenaltyCost(o);

    return depot.getAttributes().totalCrossRoadPenaltyCost;
     }*/

  /*public double getTotalTurnAroundPenaltyCost(Object o) {
    TOPDepot depot = (TOPDepot) o;
    setTotalTurnAroundPenaltyCost(o);

    return depot.getAttributes().totalTurnAroundPenaltyCost;
     }*/

  public float getTotalDemand(Object o) {
    TOPDepot depot = (TOPDepot) o;
    setTotalDemand(o);

    return (int) depot.getAttributes().getTotalDemand();
  }

  public double getTotalDistance(Object o) {
    TOPDepot depot = (TOPDepot) o;
    setTotalDistance(o);

    return depot.getAttributes().getTotalDistance();
  }

  public double getTotalTravelTime(Object o) {
    TOPDepot depot = (TOPDepot) o;
    setTotalTravelTime(o);

    return depot.getAttributes().getTotalTravelTime();
  }

  public double getMaxTravelTime(Object o) {
    TOPDepot depot = (TOPDepot) o;
    setMaxTravelTime(o);

    return depot.getAttributes().getMaxTravelTime();
  }

  public double getAvgTravelTime(Object o) {
    TOPDepot depot = (TOPDepot) o;
    setAvgTravelTime(o);

    return depot.getAttributes().getAvgTravelTime();
  }

  public void setTotalCost(Object o) {
    TOPDepot depot = (TOPDepot) o;
    depot.getAttributes().setTotalCost(ZeusProblemInfo.getTruckLLLevelCostF().getTotalCost(
        depot.getMainTrucks()));
  }

  /*public void setTotalConstraintCost(Object o) {
    TOPDepot depot = (TOPDepot) o;
    depot.getAttributes().totalConstraintCost = ZeusProblemInfo.truckLLLevelCostF.
        getTotalConstraintCost(depot.getMainTrucks());
     }*/

  /*public void setTotalCrossRoadPenaltyCost(Object o) {
    TOPDepot depot = (TOPDepot) o;
    depot.getAttributes().totalCrossRoadPenaltyCost = ZeusProblemInfo.truckLLLevelCostF.
        getTotalCrossRoadPenaltyCost(depot.getMainTrucks());
     }*/

  /* public void setTotalTurnAroundPenaltyCost(Object o) {
     TOPDepot depot = (TOPDepot) o;
   depot.getAttributes().totalTurnAroundPenaltyCost = ZeusProblemInfo.truckLLLevelCostF.
         getTotalTurnAroundPenaltyCost(depot.getMainTrucks());
   }*/

  public void setTotalDemand(Object o) {
    TOPDepot depot = (TOPDepot) o;
    depot.getAttributes().setTotalDemand((int) ZeusProblemInfo.getTruckLLLevelCostF().
        getTotalDemand(depot.getMainTrucks()));
  }

  public void setTotalDistance(Object o) {
    TOPDepot depot = (TOPDepot) o;
    depot.getAttributes().setTotalDistance((float) ZeusProblemInfo.getTruckLLLevelCostF().
        getTotalDistance(depot.getMainTrucks()));
  }
  
  public void setTotalTravelTime(Object o) {
    TOPDepot depot = (TOPDepot) o;
    depot.getAttributes().setTotalTravelTime( ZeusProblemInfo.getTruckLLLevelCostF().
        getTotalTravelTime(depot.getMainTrucks()));
  }

  public void setMaxTravelTime(Object o) {
    TOPDepot depot = (TOPDepot) o;
    depot.getAttributes().setMaxTravelTime(ZeusProblemInfo.getTruckLLLevelCostF().
        getMaxTravelTime(depot.getMainTrucks()));
  }

  public void setAvgTravelTime(Object o) {
    TOPDepot depot = (TOPDepot) o;
    depot.getAttributes().setAvgTravelTime(ZeusProblemInfo.getTruckLLLevelCostF().
        getAvgTravelTime(depot.getMainTrucks()));
  }

  /** @todo Might not need CrossRoadPenaltyCost and TurnAroundPenaltyCost */
  public void calculateTotalsStats(Object o) {
    setTotalDemand(o);
    setTotalDistance(o);
    setTotalTravelTime(o);
    setMaxTravelTime(o);
    setAvgTravelTime(o);
    //setTotalCrossRoadPenaltyCost(o);
    // setTotalTurnAroundPenaltyCost(o);
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
