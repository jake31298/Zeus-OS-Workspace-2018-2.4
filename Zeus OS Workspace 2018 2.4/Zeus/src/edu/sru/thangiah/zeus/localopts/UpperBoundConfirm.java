package edu.sru.thangiah.zeus.localopts;

/**
 * A simple upper bounded swap confirmation class for the optimizations.
 * <p>Title: SwapConfirm</p>
 * <p>Description: A simple upper bounded swap confirmation class for the
 * optimizations. Will set an upper bound for whether the swap
 * should be made or not.
 * Ex.
 * If the bound is set to 100, all swaps that decrease the cost, or increase the
 * cost by less than 100 will be accepted.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class UpperBoundConfirm
    extends SwapConfirm {
  private double upperBound;

  /**
   * Constructor. Sets the exclusive upper bound for the swap cost (ie cost must
   * be less than, not less than or equal to)
   * @param bound the upper bound
   */
  public UpperBoundConfirm(int bound) {
    upperBound = bound;
  }

  /**
   * Determines if the swap should be made
   * @param costDifference the change is cost
   * @return true- make swap, false- don't swap
   */
  public boolean isGoodSwap(double costDifference) {
    return (costDifference < upperBound);
  }

  public boolean isGoodSwap(OptimizationUnit optUnit) {
	
  boolean isDiagnostic=true;
  if (isDiagnostic)
  {
		if (optUnit.getCostDifference() < upperBound)
		{
			System.out.println("CostDifference ="+optUnit.getCostDifference());
			System.out.println("upperbound ="+upperBound);
		}
  }
	
    return optUnit.getCostDifference() < upperBound;
  }

  /**
   * Sets the upper bound
   * @param bound new upper bound
   */
  public void setUpperBound(double bound) {
    upperBound = bound;
  }

  /**
   * Returns the upper bound
   * @return upper bound
   */
  public double getUpperBound() {
    return upperBound;
  }
}
