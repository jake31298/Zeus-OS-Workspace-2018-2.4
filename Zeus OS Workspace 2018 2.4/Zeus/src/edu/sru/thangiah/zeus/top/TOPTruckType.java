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
  public static final long serialVersionUID = 1;

  public TOPTruckType() {
  }

  /**
   * Constructor
   * @param N type number
   * @param d max duration
   * @param Q max capacity
   * @param s type of customers the truck can service
   */
  public TOPTruckType(int N, double d, float Q, String s) {
    setTruckNo(N);
    setServiceType(s);

    if (d == 0) {
      setMaxDuration(Integer.MAX_VALUE);
    }
    else {
      setMaxDuration(d);
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

  /**
   * Returns an exact copy of the truck type object
   * @return Object
   */
  public Object clone() {
    TOPTruckType clonedTruckType = new TOPTruckType(this.getTruckNo(), this.getMaxDuration(), this.getMaxCapacity(), this.getServiceType());

    return clonedTruckType;
  }
}
