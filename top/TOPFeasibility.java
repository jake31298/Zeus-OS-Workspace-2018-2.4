package edu.sru.thangiah.zeus.top;

//import the parent class
import edu.sru.thangiah.zeus.core.Feasibility;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class TOPFeasibility
    extends Feasibility
    implements java.io.Serializable, java.lang.Cloneable {
  public static final long serialVersionUID = 1;
private TOPNodesLinkedList thisRoute;

  public TOPFeasibility() {
  }

  /**
   * Constructor for the feasibilty, will send the constants as well as a
   * pointer to the route that will be checked
   * @param d max duration
   * @param e max capacity
   * @param thisR the route
   */
  TOPFeasibility(double d, double e, TOPNodesLinkedList thisR) {
    super(thisR);
    setMaxDuration(d);
    setMaxCapacity(e);
    //thisRoute = thisR;
  }

  /**
   * This function checks the feasibility of the route.
   * @return true if feasible, false if not.
   */
  public boolean isFeasible() {
    double currentDistance;
    double currentDemand;

    currentDistance = TOPProblemInfo.nodesLLLevelCostF.getTotalDistance(getRoute());
    currentDemand = TOPProblemInfo.nodesLLLevelCostF.getTotalDemand(getRoute());

    if ( (currentDistance <= (getMaxDuration() * TOPProblemInfo.maxDistanceBuffer)) && (currentDemand <= getMaxCapacity())) {
      return true;
    }
    else {
      return false;
    }
  }

  public boolean isPostOptFeasible() {
    double currentDistance;
    double currentDemand;

    currentDistance = TOPProblemInfo.nodesLLLevelCostF.getTotalDistance(getRoute());
    currentDemand = TOPProblemInfo.nodesLLLevelCostF.getTotalDemand(getRoute());

    if ( (currentDistance <= getMaxDuration()) && (currentDemand <= getMaxCapacity())) {
      return true;
    }
    else {
      return false;
    }
  }

  public Object clone() {
    TOPFeasibility clonedFeasibility = new TOPFeasibility(this.getMaxCapacity(), this.getMaxDuration(), (TOPNodesLinkedList)this.thisRoute);

    return clonedFeasibility;
  }
}
