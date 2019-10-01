package edu.sru.thangiah.zeus.metaheuristics.simulatedannealing;

import edu.sru.thangiah.zeus.localopts.*;

/**
 * Implementation of the Metropolis Monte Carlo Simulation as an extention of
 * the SwapConfirm class for the optimizations.
 * <p>Title: MetropolisMonteCarloConfirm</p>
 * <p>Description: Implementation of the Metropolis Monte Carlo Simulation. Will
 * accept all costs less than zero. Will also accept exchanges where the cost
 * difference is small enough that is is probabilistically accepted.
 * See http://members.aol.com/btluke/metro01.htm for further explination.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class MetropolisMonteCarloConfirm
    extends SwapConfirm {
  public static int badGood = 0;
  public static int goodGood = 0;
  public static int badBad = 0;
  private double currentTemperature;

  /**
   * Constructor, sets current temperature of the system
   * @param currentTemp the current temperature
   */
  public MetropolisMonteCarloConfirm(double currentTemp) {
    currentTemperature = currentTemp;
    badGood = 0;
    goodGood = 0;
    badBad = 0;
  }

  /**
   * Implementation of the Metropolis Monte Carlo Simulation. Will accept all
   * costs less than zero. Will also accept exchanges where the cost difference
   * is small enough that is is probabilistically accepted.
   * See http://members.aol.com/btluke/metro01.htm for further explination.
   * @param cost the cost difference
   * @return whether to make the exchange or not
   */
  public boolean isGoodSwap(double cost) {
    if (cost < 0) {
      goodGood++;

      return true;
    }
    else {
      double random = Math.random();
      double diff = Math.pow(Math.E, - (cost / currentTemperature));

      if (diff > random) {
        badGood++;

        return true;
      }
      else {
        badBad++;

        return false;
      }
    }
  }

  public boolean isGoodSwap(OptimizationUnit optUnit) {
    double cost = optUnit.getCostDifference();

    if (cost < 0) {
      goodGood++;

      return true;
    }
    else {
      double random = Math.random();
      double diff = Math.pow(Math.E, - (cost / currentTemperature));

      if (diff > random) {
        badGood++;

        return true;
      }
      else {
        badBad++;

        return false;
      }
    }

  }

}
