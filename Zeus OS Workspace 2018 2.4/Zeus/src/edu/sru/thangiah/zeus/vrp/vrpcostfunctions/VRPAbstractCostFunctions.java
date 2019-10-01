package edu.sru.thangiah.zeus.vrp.vrpcostfunctions;

import edu.sru.thangiah.zeus.core.*;

abstract public class VRPAbstractCostFunctions implements CostFunctions {

  /** Methods not used by VRP in the computation of cost can be declared
   * here with dummy methods. Then have the cost functions in the VRP
   * inherit this class instead of the CostFunctions class.
   */

  public float getTotalScore(Object o) {
    return 0;
  }

  public void setTotalScore(Object o) {

  }
  
  public void setTotalDays(Object o) {

  }
  
  public int getTotalDays(Object o) {
	  
	  	return 0;
  }

 
}
