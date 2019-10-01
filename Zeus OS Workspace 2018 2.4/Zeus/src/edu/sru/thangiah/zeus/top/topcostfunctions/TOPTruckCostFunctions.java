package edu.sru.thangiah.opsys.top.topcostfunctions;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.opsys.top.TOPTruck;

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

    return truck.attributes.totalCost;
  }

  public float getTotalDemand(Object o) {
    TOPTruck truck = (TOPTruck) o;
    setTotalDemand(o);

    return (int) truck.attributes.totalDemand;
  }

  public double getTotalDistance(Object o) {
    TOPTruck truck = (TOPTruck) o;
    setTotalDistance(o);

    return truck.attributes.totalDistance;
  }

  public double getTotalTravelTime(Object o) {
    TOPTruck truck = (TOPTruck) o;
    setTotalTravelTime(o);

    return truck.attributes.totalTravelTime;
  }

  public double getMaxTravelTime(Object o) {
    TOPTruck truck = (TOPTruck) o;
    setMaxTravelTime(o);

    return truck.attributes.maxTravelTime;
  }

  public double getAvgTravelTime(Object o) {
    TOPTruck truck = (TOPTruck) o;
    setAvgTravelTime(o);

    return truck.attributes.avgTravelTime;
  }

  public void setTotalCost(Object o) {
    TOPTruck truck = (TOPTruck) o;
    truck.attributes.totalCost = ProblemInfo.nodesLLLevelCostF.getTotalCost(truck.getMainNodes());
  }

  public void setTotalDemand(Object o) {
    TOPTruck truck = (TOPTruck) o;
    truck.attributes.totalDemand = ProblemInfo.nodesLLLevelCostF.getTotalDemand(truck.getMainNodes());
  }

  public void setTotalDistance(Object o) {
    TOPTruck truck = (TOPTruck) o;
    truck.attributes.totalDistance = ProblemInfo.nodesLLLevelCostF.
        getTotalDistance(truck.getMainNodes());
  }

  public void setTotalTravelTime(Object o) {
    TOPTruck truck = (TOPTruck) o;
    truck.attributes.totalTravelTime = ProblemInfo.nodesLLLevelCostF.
        getTotalTravelTime(truck.getMainNodes());
  }

  public void setMaxTravelTime(Object o) {
    TOPTruck truck = (TOPTruck) o;
    truck.attributes.maxTravelTime = ProblemInfo.nodesLLLevelCostF.
        getMaxTravelTime(truck.getMainNodes());
  }

  public void setAvgTravelTime(Object o) {
    TOPTruck truck = (TOPTruck) o;
    truck.attributes.avgTravelTime = ProblemInfo.nodesLLLevelCostF.
        getAvgTravelTime(truck.getMainNodes());
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
