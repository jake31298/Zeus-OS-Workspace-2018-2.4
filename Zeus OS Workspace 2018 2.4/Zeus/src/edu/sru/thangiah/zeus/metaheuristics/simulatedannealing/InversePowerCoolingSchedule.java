package edu.sru.thangiah.zeus.metaheuristics.simulatedannealing;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class InversePowerCoolingSchedule
    extends CoolingSchedule {
  private int maxI;
  private double initT;
  private double finalT;

  public InversePowerCoolingSchedule(int I, double iT, double fT) {
    maxI = I;
    initT = iT;
    finalT = fT;
  }

  public double cool(int i) {
    return initT * Math.pow( (finalT / initT), (i / maxI));
  }
}
