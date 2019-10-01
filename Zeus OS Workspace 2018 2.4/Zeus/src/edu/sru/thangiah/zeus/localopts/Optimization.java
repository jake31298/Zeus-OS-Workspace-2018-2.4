package edu.sru.thangiah.zeus.localopts;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public abstract class Optimization {
  protected SwapConfirm confirm = new UpperBoundConfirm(0);

  public abstract String toString();

  public abstract String getShortName();

  public SwapConfirm getConfirm() {
    return confirm;
  }

  public void setConfirm(SwapConfirm confirm) {
    this.confirm = confirm;
  }
}
