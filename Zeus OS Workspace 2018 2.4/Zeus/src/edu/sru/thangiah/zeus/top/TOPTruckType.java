package edu.sru.thangiah.zeus.top;

//import the parent class
import edu.sru.thangiah.zeus.core.TruckType;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class TOPTruckType
    extends TruckType
    implements java.io.Serializable, java.lang.Cloneable {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public TOPTruckType() {
  }

  /**
   * Constructor
   * @param N type number
   * @param D max duration
   * @param Q max capacity
   * @param s type of customers the truck can service
   */
  public TOPTruckType(int N, float D, float Q, String s) {
    setTruckNo(N);
    setServiceType(s);

    if (D == 0) {
      setMaxDuration(Integer.MAX_VALUE);
    }
    else {
    	setMaxDuration(D);
    }

    if (Q == 0) {
      setMaxCapacity(Integer.MAX_VALUE);
    }
    else {
      setMaxCapacity(Q);
    }

    setFixedCost(getMaxCapacity());
    setVariableCost((double) getMaxCapacity() / 1000);
  }

  public Object clone() {
	    TOPTruckType clonedTruckType = new TOPTruckType();
	    clonedTruckType.setFixedCost(this.getFixedCost());
	    clonedTruckType.setMaxCapacity(this.getMaxCapacity());
	    clonedTruckType.setMaxDuration(this.getMaxDuration());
	    clonedTruckType.setServiceType(this.getServiceType());
	    clonedTruckType.setTruckNo(this.getTruckNo());
	    clonedTruckType.setVariableCost(this.getVariableCost());

	    return clonedTruckType;
	  }
  
}
