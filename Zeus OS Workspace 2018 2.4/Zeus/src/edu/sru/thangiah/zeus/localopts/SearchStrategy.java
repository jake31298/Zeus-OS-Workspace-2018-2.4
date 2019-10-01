package edu.sru.thangiah.zeus.localopts;

import edu.sru.thangiah.zeus.core.*;

/**
 * This is the parent class for all optimizations.
 * <p>Title: Optimization</p>
 * <p>Description: This is the parent class for all optimizations.
 * By overloading the run method, this allows all optimization classes
 * to be called using the same function name.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public abstract class SearchStrategy {
  protected boolean loopUntilNoChanges = true;
  protected Optimization optimization = null;

  public abstract OptInfo run(DepotLinkedList depots);

  public abstract String toString();

  public abstract String getShortName();
  /**
   * Sets whether the optimization will run until there are no more changes
   * @param loop the status of the looping
   */
  public void setLoopUntilNoChanges(boolean loop) {
    loopUntilNoChanges = loop;
  }

  /**
   * Returns the loop until no changes setting
   * @return boolean
   */
  public boolean getLoopUntilNoChanges() {
    return loopUntilNoChanges;
  }

  public abstract Optimization getOptimization();
}
