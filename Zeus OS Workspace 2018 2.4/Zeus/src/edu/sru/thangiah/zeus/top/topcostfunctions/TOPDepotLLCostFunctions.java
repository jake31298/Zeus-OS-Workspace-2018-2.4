package edu.sru.thangiah.zeus.top.topcostfunctions;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.top.TOPDepotLinkedList;
import edu.sru.thangiah.zeus.top.*;

/**
 * Cost functions specific to Depot LinkedList level
 * <p>Title: Zeus - A Unified Object Oriented Model for TOP's</p>
 * <p>Description: cost functions specific to Depot LinkedList level</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */
public class TOPDepotLLCostFunctions extends TOPAbstractCostFunctions implements java.io.Serializable {

  public double getTotalCost(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    setTotalCost(o);

    return depotLL.getTOPAttributes().getTotalCost();
  }

  public float getTotalDemand(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    setTotalDemand(o);

    return (int)depotLL.getTOPAttributes().getTotalDemand();
  }

  public double getTotalDistance(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    setTotalDistance(o);

    return depotLL.getTOPAttributes().getTotalDistance();
  }

  public double getTotalTravelTime(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    setTotalTravelTime(o);

    return depotLL.getTOPAttributes().getTotalTravelTime();
  }

  public double getMaxTravelTime(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    setMaxTravelTime(o);

    return depotLL.getTOPAttributes().getMaxTravelTime();
  }

  public double getAvgTravelTime(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    setAvgTravelTime(o);

    return depotLL.getTOPAttributes().getAvgTravelTime();
  }

  /**
   * Returns the overall cost for the trucks that are team members
   * @param o Object
   * @return double
   * Added by David Crissman
   */
  public double getTeamTotalCost(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    TOPTruck currentTruck;
    double teamTotalCost = 0.0;

    //Set the cost of all trucks
    setTotalCost(o);

    //Find the highest-scoring trucks and mark them as team members
    markTeamMembers(o);

    //Look at each truck, and if it is a team member, add its cost
    currentTruck = depotLL.getStartDepot().getTOPMainTrucks().getTOPHead();
    while (currentTruck != null) {
      if (currentTruck.getIsTeamMember() == true) {
        teamTotalCost += TOPProblemInfo.truckLevelCostF.getTotalCost(currentTruck);
      }
      currentTruck = currentTruck.getTOPNext();
    }


    return teamTotalCost;
  }

  /**
   * Returns the overall demand for the trucks that are team members
   * @param o Object
   * @return float
   * Added by David Crissman
   */
  public float getTeamTotalDemand(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    TOPTruck currentTruck;
    double teamTotalDemand = 0.0;

    //Set the demand of all trucks
    setTotalDemand(o);

    //Find the highest-scoring trucks and mark them as team members
    markTeamMembers(o);

    //Look at each truck, and if it is a team member, add its demand
    currentTruck = depotLL.getStartDepot().getTOPMainTrucks().getTOPHead();
    while (currentTruck != null) {
      if (currentTruck.getIsTeamMember() == true) {
	teamTotalDemand += TOPProblemInfo.truckLevelCostF.getTotalDemand(currentTruck);
      }
      currentTruck = currentTruck.getTOPNext();
    }

    return (int) teamTotalDemand;
  }

  /**
   * Returns the total distance travelled by all the team members
   * @param o Object
   * @return double
   * Added by David Crissman
   */
  public double getTeamTotalDistance(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    TOPTruck currentTruck;
    double teamTotalDistance = 0.0;

    //Set the total distance of all the trucks
    setTotalDistance(o);

    //Find the highest-scoring trucks and mark them as team members
    markTeamMembers(o);

    //Look at each truck, and if it is a team member, add its demand
    currentTruck = depotLL.getStartDepot().getTOPMainTrucks().getTOPHead();
    while (currentTruck != null) {
      if (currentTruck.getIsTeamMember() == true) {
        teamTotalDistance += TOPProblemInfo.truckLevelCostF.getTotalDistance(currentTruck);
      }
      currentTruck = currentTruck.getTOPNext();
    }

    return teamTotalDistance;
  }

  /**
   * Returns the longest travel time of all the team members
   * @param o Object
   * @return double
   * Added by David Crissman
   */
  public double getTeamMaxTravelTime(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    TOPTruck currentTruck;
    double teamMaxTravelTime = 0.0;

    //Set the total travel time of all trucks
    setTotalTravelTime(o);

    //Find the highest-scoring trucks, and mark them as team members
    markTeamMembers(o);

    //Look at each truck, and pick out the highest travel time of all the team members
    currentTruck = depotLL.getStartDepot().getTOPMainTrucks().getTOPHead();
    while (currentTruck != null) {
      if (currentTruck.getIsTeamMember() == true) {
        if (TOPProblemInfo.truckLevelCostF.getMaxTravelTime(currentTruck) > teamMaxTravelTime) {
	  teamMaxTravelTime = TOPProblemInfo.truckLevelCostF.getMaxTravelTime(currentTruck);
	}
      }
      currentTruck = currentTruck.getTOPNext();
    }


    return teamMaxTravelTime;
  }

  /**
   * Returns the total travel time of all the team members
   * @param o Object
   * @return double
   * Added by David Crissman
   */
  public double getTeamTotalTravelTime(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    TOPTruck currentTruck;
    double teamTotalTravelTime = 0.0;

    //Set the total travel time of all the trucks
    setTotalTravelTime(o);

    //Find the highest-scoring trucks and mark them as team members
    markTeamMembers(o);

    //Look at each truck, and if it is a team member, add its travel time
    currentTruck = depotLL.getStartDepot().getTOPMainTrucks().getTOPHead();
    while (currentTruck != null) {
      if (currentTruck.getIsTeamMember() == true) {
        teamTotalTravelTime += TOPProblemInfo.truckLevelCostF.getTotalTravelTime(currentTruck);
      }
      currentTruck = currentTruck.getTOPNext();
    }


    return teamTotalTravelTime;
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
    currentTruck = depotLL.getStartDepot().getTOPMainTrucks().getTOPHead();
    while (currentTruck != null) {
      currentTruck.setIsTeamMember(false);
      currentTruck = currentTruck.getTOPNext();
    }

    for (int i = 0; i < TOPProblemInfo.numTeamMembers; i++) {
      bestTruck = null;
      bestDemand = 0.0;
      currentTruck = depotLL.getStartDepot().getTOPMainTrucks().getTOPHead();
      while (currentTruck != null) {
        if (currentTruck.getIsTeamMember() == false) {
          currentDemand = TOPProblemInfo.truckLevelCostF.getTotalDemand(currentTruck);
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

  public void setTotalCost(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    double cost = 0;

    TOPDepot d = depotLL.getTOPHead();
    TOPDepot temp = d;
    while (d != null) {
      cost += TOPProblemInfo.depotLevelCostF.getTotalCost(d);
      d = d.getTOPNext();
      if(d == temp){
        Settings.printDebug(Settings.COMMENT, "Possible error: TOPDepotLinkedList loops back on itself");
        break;  // Get out because we're back to the original depot- Something could be wrong with the DepotLinkedList
      }
    }

    depotLL.getTOPAttributes().totalCost = cost;
  }

  public void setTotalDemand(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    depotLL.getTOPAttributes().totalDemand = 0;

    TOPDepot d = depotLL.getTOPHead();
    TOPDepot tempd = d;

    while (d != null) {
      depotLL.getTOPAttributes().totalDemand += (float) TOPProblemInfo.depotLevelCostF.getTotalDemand(d);
      d = d.getTOPNext();
      if(d == tempd){
        break; // Break if the list loops back on itself instead of ending in a null.
      }
    }
  }

  public void setTotalDistance(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    depotLL.getTOPAttributes().totalDistance = 0;

    TOPDepot d = depotLL.getTOPHead();

    while (d != null) {
      depotLL.getTOPAttributes().totalDistance += (float) TOPProblemInfo.depotLevelCostF.getTotalDistance(d);
      d = d.getTOPNext();
    }
  }

  public void setTotalTravelTime(Object o) {
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    depotLL.getTOPAttributes().totalTravelTime = 0;

    TOPDepot d = depotLL.getTOPHead();

    while (d != null) {
      depotLL.getTOPAttributes().totalTravelTime += TOPProblemInfo.depotLevelCostF.
          getTotalTravelTime(d);
      d = d.getTOPNext();
    }
  }

  public void setMaxTravelTime(Object o) {
    double max = 0;
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    TOPDepot d = depotLL.getTOPHead();

    while (d != null) {
      if (TOPProblemInfo.depotLevelCostF.getMaxTravelTime(d) > max) {
        max = TOPProblemInfo.depotLevelCostF.getMaxTravelTime(d);
      }

      d = d.getTOPNext();
    }

    depotLL.getTOPAttributes().maxTravelTime = max;
  }

  public void setAvgTravelTime(Object o) {
    double avg = 0;
    TOPDepotLinkedList depotLL = (TOPDepotLinkedList) o;
    TOPDepot d = depotLL.getTOPHead();

    if (TOPProblemInfo.depotLLLevelCostF.getTotalDemand(depotLL) != 0) {
      while (d != null) {
        avg +=
            (TOPProblemInfo.depotLevelCostF.getAvgTravelTime(d) *
            		TOPProblemInfo.depotLevelCostF.getTotalDemand(d));
        d = d.getTOPNext();
      }

      depotLL.getTOPAttributes().avgTravelTime = avg /
      TOPProblemInfo.depotLLLevelCostF.getTotalDemand(depotLL);
    }
    else {
      depotLL.getTOPAttributes().avgTravelTime = 0;
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
