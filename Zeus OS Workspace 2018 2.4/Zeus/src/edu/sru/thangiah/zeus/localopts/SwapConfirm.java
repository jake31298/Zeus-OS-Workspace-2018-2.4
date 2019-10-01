package edu.sru.thangiah.zeus.localopts;

/**
 * Abstract class that any optimization confirmation class must extend off of.
 * <p>Title: SwapConfirm</p>
 * <p>Description: Abstract class that any optimization confirmation class must
 * extend off of.  This allows you to create a new class to define a new way
 * of deciding when to make a swap in the optimizations, without touching the
 * optimization code.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public abstract class SwapConfirm {
  /**
   * Defines if the current swap is worth making
   * @param costDifference cost change from optimization
   * @return true- make change, false- don't make change
   */
  public abstract boolean isGoodSwap(double costDifference);

  /**
   * Defines if the current swap is worth making
   * @param unit OptimizationUnit - the optimization
   * @return boolean true-make swap, false- dont make it
   */
  public abstract boolean isGoodSwap(OptimizationUnit unit);
}
