package edu.sru.thangiah.zeus.metaheuristics.simulatedannealing;

/**
 * Implementation of a linear cooling schedule
 * <p>Title: LinearCoolingSchedule</p>
 * <p>Description: Will return the new current temperature for the anneal.
 * Uses a simple slope equation to solve for the new temperature
 * newTemp = initTemp - iteration * (initTemp - finalTemp) / maxIterations
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class LinearCoolingSchedule
    extends CoolingSchedule {
  /**
   * Constructor, sets max iterations and initial temperature
   * @param maxIter max iterations
   * @param initTemp initial temperature
   * @param finalTemp the ending temperature
   */
  public LinearCoolingSchedule(int maxIter, double initTemp, double finalTemp) {
    super.maxIterations = maxIter;
    super.initTemperature = initTemp;
    super.finalTemperature = finalTemp;
  }

  /**
   * Will return the new current temperature for the anneal. Uses a simple slope
   * equation to solve for the new temperature
   * newTemp = initTemp - iteration * (initTemp - finalTemp) / maxIterations
   * @param currentIteration iteration number
   * @return the new current temperature
   */
  public double cool(int currentIteration) {
    //save vars in temps to make them smaller
    double T0 = initTemperature;
    double TN = finalTemperature;
    int i = currentIteration;
    int N = maxIterations;

    return T0 - ( (i * (T0 - TN)) / (double) N);
  }
}
