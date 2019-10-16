package edu.sru.thangiah.zeus.top.topcostfunctions;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.top.TOPTruck;
import edu.sru.thangiah.zeus.top.*;

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

    //return truck.attributes.totalCost;
    return truck.getAttributes().getTotalCost();
  }

  public float getTotalDemand(Object o) {
    TOPTruck truck = (TOPTruck) o;
    setTotalDemand(o);

    //return (int) truck.attributes.totalDemand;
    return (int) truck.getAttributes().getTotalDemand();
  }

  public double getTotalDistance(Object o) {
    TOPTruck truck = (TOPTruck) o;
    setTotalDistance(o);

    //return truck.attributes.totalDistance;
    return truck.getAttributes().getTotalDistance();
  }

  public double getTotalTravelTime(Object o) {
    TOPTruck truck = (TOPTruck) o;
    setTotalTravelTime(o);

    //return truck.attributes.totalTravelTime;
    return truck.getAttributes().getTotalTravelTime();
  }

  public double getMaxTravelTime(Object o) {
    TOPTruck truck = (TOPTruck) o;
    setMaxTravelTime(o);

    //return truck.attributes.maxTravelTime;
    return truck.getAttributes().getMaxTravelTime();

  }

  public double getAvgTravelTime(Object o) {
    TOPTruck truck = (TOPTruck) o;
    setAvgTravelTime(o);

    //return truck.attributes.avgTravelTime;
    return truck.getAttributes().getAvgTravelTime();

  }

  public void setTotalCost(Object o) {
    TOPTruck truck = (TOPTruck) o;
    //truck.attributes.totalCost = ProblemInfo.nodesLLLevelCostF.getTotalCost(truck.getMainNodes());
    truck.getAttributes().setTotalCost(ZeusProblemInfo.getNodesLLLevelCostF().getTotalCost(truck.getMainNodes()));

  }

  public void setTotalDemand(Object o) {
    TOPTruck truck = (TOPTruck) o;
    //truck.attributes.totalDemand = ProblemInfo.nodesLLLevelCostF.getTotalDemand(truck.getMainNodes());
    truck.getAttributes().setTotalDemand(ZeusProblemInfo.getNodesLLLevelCostF().getTotalDemand(truck.getMainNodes()));

  }

  public void setTotalDistance(Object o) {
    TOPTruck truck = (TOPTruck) o;
    //truck.attributes.totalDistance = ProblemInfo.nodesLLLevelCostF.
    truck.getAttributes().setTotalDistance(ZeusProblemInfo.getNodesLLLevelCostF().getTotalDistance(truck.getMainNodes()));
  }

  public void setTotalTravelTime(Object o) {
    TOPTruck truck = (TOPTruck) o;
    //truck.attributes.totalTravelTime = ProblemInfo.nodesLLLevelCostF.
    truck.getAttributes().setTotalTravelTime(ZeusProblemInfo.getNodesLLLevelCostF().getTotalTravelTime(truck.getMainNodes()));
  }

  public void setMaxTravelTime(Object o) {
    TOPTruck truck = (TOPTruck) o;
    //truck.attributes.maxTravelTime = ProblemInfo.nodesLLLevelCostF.
    truck.getAttributes().setMaxTravelTime(ZeusProblemInfo.getNodesLLLevelCostF().getMaxTravelTime(truck.getMainNodes()));
  }

  public void setAvgTravelTime(Object o) {
    TOPTruck truck = (TOPTruck) o;
    //truck.attributes.avgTravelTime = ProblemInfo.nodesLLLevelCostF.
    truck.getAttributes().setAvgTravelTime(ZeusProblemInfo.getNodesLLLevelCostF().getAvgTravelTime(truck.getMainNodes()));
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
