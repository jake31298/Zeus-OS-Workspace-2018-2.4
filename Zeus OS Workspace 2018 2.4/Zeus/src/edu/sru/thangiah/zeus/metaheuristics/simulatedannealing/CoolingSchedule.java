package edu.sru.thangiah.zeus.metaheuristics.simulatedannealing;

/**
 * Interface class for a cooling schedule intended for the SimulatedAnnealing
 * class
 * <p>Title: CoolingSchedule</p>
 * <p>Description: Interface class for a cooling schedule intended for the
 * SimulatedAnnealing class. The cooling schedule will determine at what rate
 * the temperature in the system drops.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public abstract class CoolingSchedule {
  protected int maxIterations;
  protected double initTemperature;
  protected double finalTemperature;

  /**
   * Returns the new temperature based upon the iteration
   * @param currentIteration the current iteration
   * @return new temperature
   */
  abstract public double cool(int currentIteration);
}
