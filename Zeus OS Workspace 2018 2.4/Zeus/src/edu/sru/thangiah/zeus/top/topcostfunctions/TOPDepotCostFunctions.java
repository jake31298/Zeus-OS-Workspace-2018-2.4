package edu.sru.thangiah.zeus.top.topcostfunctions;

import edu.sru.thangiah.zeus.top.TOPDepot;
import edu.sru.thangiah.zeus.top.TOPProblemInfo;

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

    return depot.getAttributes().totalCost;
  }

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
    depot.attributes.totalCost = TOPProblemInfo.truckLLLevelCostF.getTotalCost(
        depot.getMainTrucks());
  }

  public void setTotalDemand(Object o) {
    TOPDepot depot = (TOPDepot) o;
    depot.getAttributes().totalDemand = (int) TOPProblemInfo.truckLLLevelCostF.
        getTotalDemand(depot.getMainTrucks());
  }

  public void setTotalDistance(Object o) {
    TOPDepot depot = (TOPDepot) o;
    depot.getAttributes().totalDistance = (float) TOPProblemInfo.truckLLLevelCostF.
        getTotalDistance(depot.getMainTrucks());
  }

  public void setTotalTravelTime(Object o) {
    TOPDepot depot = (TOPDepot) o;
    depot.getAttributes().totalTravelTime = TOPProblemInfo.truckLLLevelCostF.
        getTotalTravelTime(depot.getMainTrucks());
  }

  public void setMaxTravelTime(Object o) {
    TOPDepot depot = (TOPDepot) o;
    depot.getAttributes().maxTravelTime = TOPProblemInfo.truckLLLevelCostF.
        getMaxTravelTime(depot.getMainTrucks());
  }

  public void setAvgTravelTime(Object o) {
    TOPDepot depot = (TOPDepot) o;
    depot.getAttributes().avgTravelTime = TOPProblemInfo.truckLLLevelCostF.
        getAvgTravelTime(depot.getMainTrucks());
  }

  /** @todo Might not need CrossRoadPenaltyCost and TurnAroundPenaltyCost */
  public void calculateTotalsStats(Object o) {
    setTotalDemand(o);
    setTotalDistance(o);
    setTotalTravelTime(o);
    setMaxTravelTime(o);
    setAvgTravelTime(o);
    setTotalCost(o);
  }
}
