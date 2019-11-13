package edu.sru.thangiah.zeus.top.topcostfunctions;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.top.TOPTruck;

/**
 * Cost functions specific to TOPTruck level
 * <p>Title: Zeus - A Unified Object Oriented Model for TOP's</p>
 * <p>Description: cost functions specific to TOP Truck level</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */
public class TOPTruckCostFunctions
    extends TOPAbstractCostFunctions implements
    java.io.Serializable  {

  public double getTotalCost(Object o) {
    TOPTruck truck = (TOPTruck) o;
    setTotalCost(o);

    return truck.getAttributes().getTotalCost();
  }

  /* public double getTotalConstraintCost(Object o) {
     TOPTruck truck = (TOPTruck) o;
     setTotalConstraintCost(o);

     return truck.getAttributes().totalConstraintCost;
   }*/

  /*public double getTotalCrossRoadPenaltyCost(Object o) {
    TOPTruck truck = (TOPTruck) o;
    setTotalCrossRoadPenaltyCost(o);

    return truck.getAttributes().totalCrossRoadPenaltyCost;
     }*/

  /*public double getTotalTurnAroundPenaltyCost(Object o) {
    TOPTruck truck = (TOPTruck) o;
    setTotalTurnAroundPenaltyCost(o);

    return truck.getAttributes().totalTurnAroundPenaltyCost;
     }*/

  public float getTotalDemand(Object o) {
    TOPTruck truck = (TOPTruck) o;
    setTotalDemand(o);

    return (int) truck.getAttributes().getTotalDemand();
  }

  public double getTotalDistance(Object o) {
    TOPTruck truck = (TOPTruck) o;
    setTotalDistance(o);

    return truck.getAttributes().getTotalDistance();
  }

  public double getTotalTravelTime(Object o) {
    TOPTruck truck = (TOPTruck) o;
    setTotalTravelTime(o);

    return truck.getAttributes().getTotalTravelTime();
  }

  public double getMaxTravelTime(Object o) {
    TOPTruck truck = (TOPTruck) o;
    setMaxTravelTime(o);

    return truck.getAttributes().getMaxTravelTime();
  }

  public double getAvgTravelTime(Object o) {
    TOPTruck truck = (TOPTruck) o;
    setAvgTravelTime(o);

    return truck.getAttributes().getAvgTravelTime();
  }

  public void setTotalCost(Object o) {
    TOPTruck truck = (TOPTruck) o;
    truck.getAttributes().setTotalCost(ZeusProblemInfo.getNodesLLLevelCostF().getTotalCost(
        truck.getMainNodes()));
  }

  /*public void setTotalConstraintCost(Object o) {
    TOPTruck truck = (TOPTruck) o;
    truck.getAttributes().totalConstraintCost = ZeusProblemInfo.nodesLLLevelCostF.
        getTotalConstraintCost(truck.getMainNodes());
     }*/

  /*public void setTotalCrossRoadPenaltyCost(Object o) {
    TOPTruck truck = (TOPTruck) o;
    truck.getAttributes().totalCrossRoadPenaltyCost = ZeusProblemInfo.nodesLLLevelCostF.
        getTotalCrossRoadPenaltyCost(truck.getMainNodes());
     }*/

  /*public void setTotalTurnAroundPenaltyCost(Object o) {
    TOPTruck truck = (TOPTruck) o;
   truck.getAttributes().totalTurnAroundPenaltyCost = ZeusProblemInfo.nodesLLLevelCostF.
        getTotalTurnAroundPenaltyCost(truck.getMainNodes());
     }*/

  public void setTotalDemand(Object o) {
    TOPTruck truck = (TOPTruck) o;
    truck.getAttributes().setTotalDemand(ZeusProblemInfo.getNodesLLLevelCostF().getTotalDemand(
        truck.getMainNodes()));
  }

  public void setTotalDistance(Object o) {
    TOPTruck truck = (TOPTruck) o;
    truck.getAttributes().setTotalDistance(ZeusProblemInfo.getNodesLLLevelCostF().
        getTotalDistance(truck.getMainNodes()));
  }

  public void setTotalTravelTime(Object o) {
    TOPTruck truck = (TOPTruck) o;
    truck.getAttributes().setTotalTravelTime(ZeusProblemInfo.getNodesLLLevelCostF().
        getTotalTravelTime(truck.getMainNodes()));
  }

  public void setMaxTravelTime(Object o) {
    TOPTruck truck = (TOPTruck) o;
    truck.getAttributes().setMaxTravelTime(ZeusProblemInfo.getNodesLLLevelCostF().
        getMaxTravelTime(truck.getMainNodes()));
  }

  public void setAvgTravelTime(Object o) {
    TOPTruck truck = (TOPTruck) o;
    truck.getAttributes().setAvgTravelTime(ZeusProblemInfo.getNodesLLLevelCostF().
        getAvgTravelTime(truck.getMainNodes()));
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
