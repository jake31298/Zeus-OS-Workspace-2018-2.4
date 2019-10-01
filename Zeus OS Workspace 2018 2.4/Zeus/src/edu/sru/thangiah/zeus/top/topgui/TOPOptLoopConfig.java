package edu.sru.thangiah.zeus.top.topgui;

import java.util.Vector;

/**
 * Configuration class for the Opt Loop
 * <p>Title: Mixed Fleet</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class TOPOptLoopConfig {
  private Vector optimizations;
  private boolean isBreakOnChange;
  private boolean isLoopUntilNoChange;
  private int maxLoops;

  /**
   * Constructor
   * @param opt opts used for loop
   * @param isBreak will break on change
   * @param isLoop will loop until no more changes
   * @param max max number of loops
   */
  public TOPOptLoopConfig(Vector opt, boolean isBreak, boolean isLoop, int max) {
    optimizations = opt;
    isBreakOnChange = isBreak;
    isLoopUntilNoChange = isLoop;
    maxLoops = max;
  }

  /**
   * gets the opts used
   * @return opts
   */
  public Vector getOpts() {
    return optimizations;
  }

  /**
   * returns whether set to break on change or not
   * @return will break on change
   */
  public boolean isBreakOnChange() {
    return isBreakOnChange;
  }

  /**
   * returns whether set to loop until no change
   * @return will loop until no change
   */
  public boolean isLoopUntilNoChange() {
    return isLoopUntilNoChange;
  }

  /**
   * gets the maximum number of loops
   * @return max loops
   */
  public int getMaxLoops() {
    return maxLoops;
  }
}
