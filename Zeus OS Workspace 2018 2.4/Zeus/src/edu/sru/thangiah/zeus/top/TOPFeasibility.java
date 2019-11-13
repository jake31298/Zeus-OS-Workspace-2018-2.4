package edu.sru.thangiah.zeus.top;

//import the parent class
import edu.sru.thangiah.zeus.core.Feasibility;
import edu.sru.thangiah.zeus.core.ZeusProblemInfo;

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
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public TOPFeasibility() {
  }

  /**
   * Constructor for the feasibilty, will send the constants as well as a
   * pointer to the route that will be checked
   * @param maxd max duration
   * @param maxq max capacity
   * @param thisR the route
   */
  public TOPFeasibility(double maxd, float maxq, TOPNodesLinkedList thisR) {
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
    
    System.out.println("Current Distance ="+ currentDistance);
    System.out.println("Current max distance ="+ getMaxDuration());
    //System.out.println("Current Demand ="+ currentDemand);
    

    if ( (currentDistance <= getMaxDuration()) && (currentDemand <= getMaxCapacity())) {
      return true;
    }
    else {
      return false;
    }
  }
  public boolean isPostOptFeasible() {
	    double currentDistance;
	    double currentDemand;

	    currentDistance = TOPProblemInfo.getNodesLLLevelCostF().getTotalDistance(getRoute());
	    currentDemand = TOPProblemInfo.getNodesLLLevelCostF().getTotalDemand(getRoute());

	    if ( (currentDistance <= getMaxDuration()) && (currentDemand <= getMaxCapacity())) {
	      return true;
	    }
	    else {
	      return false;
	    }
	  }
  
  public Object clone() {
		TOPFeasibility clonedFeasibility = new TOPFeasibility();

		clonedFeasibility.setMaxCapacity(this.getMaxCapacity());
		clonedFeasibility.setMaxDuration(this.getMaxDuration());

		return clonedFeasibility;
	}
}
