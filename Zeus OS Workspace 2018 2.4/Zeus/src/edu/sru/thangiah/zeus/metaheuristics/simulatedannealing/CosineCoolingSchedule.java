package edu.sru.thangiah.zeus.metaheuristics.simulatedannealing;

/**
 * Implimentation of a cosine related cooling schedule.
 * <p>Title: CosineCoolingSchedule</p>
 * <p>Description:
 * See http://members.aol.com/btluke/simanf1.htm for more information
 * </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class CosineCoolingSchedule
    extends CoolingSchedule {
  /**
   * Constructor
   * @param maxIter max iterations
   * @param initTemp initial temperature
   * @param finalTemp final temperature
   */
  public CosineCoolingSchedule(int maxIter, double initTemp, double finalTemp) {
    super.maxIterations = maxIter;
    super.initTemperature = initTemp;
    super.finalTemperature = finalTemp;
  }

  /**
   * Cooling schedule for cosine cooling.
   * @param currentIteration the current iteration
   * @return the new temperature
   */
  public double cool(int currentIteration) {
    //save vars in temps to make them smaller
    double T0 = initTemperature;
    double TN = finalTemperature;
    int i = currentIteration;
    int N = maxIterations;

    return ( (1 / 2) * (T0 - TN) * (1 +
                                    Math.cos( (i * Math.PI) / (double) N))) +
        TN;
  }
}
