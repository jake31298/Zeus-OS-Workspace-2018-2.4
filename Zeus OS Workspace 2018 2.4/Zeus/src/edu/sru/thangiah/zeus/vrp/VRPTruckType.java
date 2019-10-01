package edu.sru.thangiah.zeus.vrp;

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

public class VRPTruckType
    extends TruckType
    implements java.io.Serializable, java.lang.Cloneable {
  public VRPTruckType() {
  }

  /**
   * Constructor
   * @param N type number
   * @param D max duration
   * @param Q max capacity
   * @param s type of customers the truck can service
   */
  public VRPTruckType(int N, float D, float Q, String s) {
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
	    VRPTruckType clonedTruckType = new VRPTruckType();
	    clonedTruckType.setFixedCost(this.getFixedCost());
	    clonedTruckType.setMaxCapacity(this.getMaxCapacity());
	    clonedTruckType.setMaxDuration(this.getMaxDuration());
	    clonedTruckType.setServiceType(this.getServiceType());
	    clonedTruckType.setTruckNo(this.getTruckNo());
	    clonedTruckType.setVariableCost(this.getVariableCost());

	    return clonedTruckType;
	  }
}
