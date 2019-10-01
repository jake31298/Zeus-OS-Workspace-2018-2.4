package edu.sru.thangiah.zeus.localopts;

import java.util.Vector;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Joshua J. Petry
 * @author Sam R. Thangiah
 * @version 2.0
 */

public abstract class OptimizationUnit {
  public OptimizationUnit() {
  }

  public abstract double getCostDifference();

  public abstract void execute();

  public abstract void undo();

  public abstract String toString();

  public abstract String[] getRoutes();

  public abstract int[] getNodeIndexes();

  public abstract int[] getLeftNeighborIndexes();

  public abstract int[] getRightNeighborIndexes();

}
