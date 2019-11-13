package edu.sru.thangiah.zeus.top;

import java.text.DecimalFormat;

//import the parent class
import edu.sru.thangiah.zeus.core.Attributes;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class TOPAttributes
    extends Attributes
    implements java.io.Serializable, java.lang.Cloneable {
  public TOPAttributes() {
  }
  

	/**
	   * Returns a detailed, pipe-delimited string. For use in the interface.
	   * NOTE: There must be a pipe after the final field!
	   * @return detailed string
	   */
	  public String toDetailedString() {
	    DecimalFormat df = new DecimalFormat("0.00");

	    return "Total Demand = " + df.format(getTotalDemand()) +
	        "| Total Distance = " + df.format(getTotalDistance()) +
	        "| Total Travel Time = " + df.format(getTotalTravelTime()) +
	        "| Total Cost = $" + df.format(getTotalCost()) + "|";
	  }
	  public Object clone() {
			TOPAttributes clonedAttributes = new TOPAttributes();

			clonedAttributes.setAvgTravelTime(this.getAvgTravelTime());
			clonedAttributes.setMaxTravelTime(this.getMaxTravelTime());
			//clonedAttributes.totalConstraintCost = this.totalConstraintCost;
			clonedAttributes.setTotalCost(this.getTotalCost());
			//clonedAttributes.totalCrossRoadPenaltyCost = this.totalCrossRoadPenaltyCost;
			clonedAttributes.setTotalDemand(this.getTotalDemand());
			clonedAttributes.setTotalDistance(this.getTotalDistance());
			clonedAttributes.setTotalTravelTime(this.getTotalTravelTime());
			//clonedAttributes.totalTurnAroundPenaltyCost = this.
			//    totalTurnAroundPenaltyCost;

			return clonedAttributes;
		}
}
