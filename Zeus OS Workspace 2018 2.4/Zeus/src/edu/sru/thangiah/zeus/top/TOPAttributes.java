package edu.sru.thangiah.zeus.top;

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
 * Modified by Pete Schallot and David Crissman to handle tour orienteering problems
 */
public class TOPAttributes extends Attributes implements java.io.Serializable, java.lang.Cloneable {
  public static final long serialVersionUID = 1;
  public double totalCost;		//TOPDepotLLCostFunctions.java ~line 268
  public int totalDemand;		//TOPDepotLLCostFunctions.java ~line 273
  public int totalDistance;		//TOPDepotLLCostFunctions.java ~line 289
  public int totalTravelTime;	//TOPDepotLLCostFunctions.java ~line 301
  public double maxTravelTime;	//TOPDepotLLCostFunctions.java ~line 325
  public double avgTravelTime;	//TOPDepotLLCostFunctions.java ~line 341

  /**
   * Constructor
   */
  public TOPAttributes() {
  }

  /**
   * Returns an exact copy of the attribute object
   * @return Object attribute object copy
   * Added by David Crissman and Pete Schallot
   */
  public Object clone() {
    TOPAttributes clonedAttributes = new TOPAttributes();

    clonedAttributes.setAvgTravelTime(this.getAvgTravelTime());
    clonedAttributes.setMaxTravelTime(this.getMaxTravelTime());
    clonedAttributes.setTotalCost(this.getTotalCost());
    clonedAttributes.setTotalDemand(this.getTotalDemand());
    clonedAttributes.setTotalDistance(this.getTotalDistance());
    clonedAttributes.setTotalTravelTime(this.getMaxTravelTime());

    return clonedAttributes;
  }
}
