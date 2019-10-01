package edu.sru.thangiah.zeus.top.topcostfunctions;

import edu.sru.thangiah.zeus.core.*;

abstract public class TOPAbstractCostFunctions implements CostFunctions {

  /** Methods not used by TOP in the computation of cost can be declared
   * here with dummy methods. Then have the cost functions in the TOP
   * inherit this class instead of the CostFunctions class.
   */

  public float getTotalScore(Object o) {
    return 0;
  }

  public void setTotalScore(Object o) {

  }
}
