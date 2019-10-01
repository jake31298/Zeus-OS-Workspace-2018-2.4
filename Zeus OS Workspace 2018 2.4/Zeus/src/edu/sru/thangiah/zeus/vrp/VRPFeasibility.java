package edu.sru.thangiah.zeus.vrp;

import edu.sru.thangiah.zeus.core.ZeusProblemInfo;
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

public class VRPFeasibility
    extends Feasibility
    implements java.io.Serializable, java.lang.Cloneable {
  public VRPFeasibility() {
  }

  /**
   * Constructor for the feasibilty, will send the constants as well as a
   * pointer to the route that will be checked
   * @param maxd max duration
   * @param maxq max capacity
   * @param thisR the route
   */
  public VRPFeasibility(double maxd, float maxq, VRPNodesLinkedList thisR) {
    super(thisR);
    
    setMaxDuration(maxd);
    setMaxCapacity(maxq);
    //thisRoute = thisR;
  }

  /**
   * This function checks the feasibility of the route.
   * @return true if feasible, false if not.
   */
  public boolean isFeasible() {
    double currentDistance;
    double currentDemand;
    
    currentDistance = ZeusProblemInfo.getNodesLLLevelCostF().getTotalDistance(getRoute());
    currentDemand = ZeusProblemInfo.getNodesLLLevelCostF().getTotalDemand(getRoute());
 
    //currentDistance = ProblemInfo1.nodesLLLevelCostF.getTotalDistance(getRoute());
    //currentDemand = ProblemInfo1.nodesLLLevelCostF.getTotalDemand(getRoute());
    
    //System.out.println("Current Distance ="+ currentDistance);
    //System.out.println("Current Demand ="+ currentDemand);
    

    if ( (currentDistance <= getMaxDuration()) && (currentDemand <= getMaxCapacity())) {
      return true;
    }
    else {
      return false;
    }
  }
  

	public Object clone() {
		VRPFeasibility clonedFeasibility = new VRPFeasibility();

		clonedFeasibility.setMaxCapacity(this.getMaxCapacity());
		clonedFeasibility.setMaxDuration(this.getMaxDuration());

		return clonedFeasibility;
	}
  
}
