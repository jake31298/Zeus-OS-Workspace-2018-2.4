package edu.sru.thangiah.zeus.metaheuristics.simulatedannealing;

import java.util.*;
import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.localopts.*;
import edu.sru.thangiah.zeus.localopts.OptInfo;


/**
 * Implementation of the Simulated Annealing Meta-Heuristic
 * <p>Title: SimulatedAnnealing</p>
 * <p>Description: Implementation of the Simulated Annealing Meta-Heuristic
 * See http://members.aol.com/btluke/simann1.htm for more information</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class SimulatedAnnealing {
  protected double initTemperature;
  protected double finalTemperature;
  protected int maxIterations;
  protected int iterationsPerTemp;

  /**
   * Describes the rate at which the system cools
   */
  protected CoolingSchedule coolingSchedule;

  /**
   * Constructor, defaults to a linear cooling schedule.
   * @param initTemp initial temperature of the system
   * @param finalTemp final temperature of the system
   * @param iterAtTemp iterations per temperature
   * @param maxIter the number of different temperatures to try
   */
  public SimulatedAnnealing(double initTemp, double finalTemp,
                            int iterAtTemp, int maxIter) {
    this(initTemp, finalTemp, iterAtTemp, maxIter,
         new LinearCoolingSchedule(maxIter,
                                   initTemp, finalTemp));

  }

  /**
   * Constructor
   * @param initTemp initial temperature of the system
   * @param finalTemp final temperature of the system
   * @param iterAtTemp iterations per temperature
   * @param maxIter max total iterations
   * @param cooling the cooling schedule
   */
  public SimulatedAnnealing(double initTemp, double finalTemp,
                            int iterAtTemp, int maxIter,
                            CoolingSchedule cooling) {
    initTemperature = initTemp;
    finalTemperature = finalTemp;
    iterationsPerTemp = iterAtTemp;
    maxIterations = maxIter;
    coolingSchedule = cooling;
  }

  /**
   * Will run the simulated annealing algorithm
   * @param mainDepots initial solution
   * @param opt the optimization to use for simualted annealing
   * @return best found solution
   * @deprecated use the anneal(DepotLinkedList mainDepots, Vector opts) implementation instead
   */
  public DepotLinkedList anneal(DepotLinkedList mainDepots, SearchStrategy opt) {
    double temperature = initTemperature;

    for (int i = 0; i <= maxIterations; i++) {
      temperature = coolingSchedule.cool(i);
      System.out.println("temperature = " + temperature);
      opt.getOptimization().setConfirm(new MetropolisMonteCarloConfirm(
          temperature));
      opt.setLoopUntilNoChanges(false);

      for (int j = 0; j < iterationsPerTemp; j++) {
        //System.out.print(j + " ");
        opt.run(mainDepots);
      }

      MetropolisMonteCarloConfirm mmcc = (MetropolisMonteCarloConfirm) opt.
          getOptimization().getConfirm();
      System.out.println("Total Cost $" +
                         mainDepots.getAttributes().getTotalCost() + " GG=" +
                         mmcc.goodGood +
                         " BG=" + mmcc.badGood + " BB=" + mmcc.badBad);
    }

    return mainDepots;
  }

  /**
   * Runs the simulated annealing algorithm on the depot linked list using a
   * series of optimizations. Will run the optimizations consecutively, in the
   * order that they are found in the array, for each iteration at each
   * temperature
   * @param mainDepots the depot linked list
   * @param opts the optimizations to run
   * @return the updated depot linked list
   */
  public OptInfo anneal(DepotLinkedList mainDepots, Vector opts) {
    OptInfo optInfo = new OptInfo();
    optInfo.setOldAttributes(mainDepots.getAttributes());
    double temperature = initTemperature;

    for (int i = 0; i <= maxIterations; i++) {
      temperature = coolingSchedule.cool(i);
      System.out.println("temperature = " + temperature);

      for (int j = 0; j < opts.size(); j++) {
        SearchStrategy opt = (SearchStrategy) opts.elementAt(j);
        opt.getOptimization().setConfirm(new MetropolisMonteCarloConfirm(
            temperature));
        System.out.println("Confirm = " +
                           opt.getOptimization().getConfirm().getClass().
                           toString());
        opt.setLoopUntilNoChanges(false);
      }

      for (int k = 0; k < iterationsPerTemp; k++) {
        for (int l = 0; l < opts.size(); l++) {
          SearchStrategy opt = (SearchStrategy) opts.elementAt(l);
          optInfo.numChanges += opt.run(mainDepots).numChanges;
        }
      }

      System.out.println("Total Cost $" +
                         mainDepots.getAttributes().getTotalCost());

      for (int l = 0; l < opts.size(); l++) {
        SearchStrategy opt = (SearchStrategy) opts.elementAt(l);
        MetropolisMonteCarloConfirm mmcc = (MetropolisMonteCarloConfirm) opt.
            getOptimization().getConfirm();
        System.out.println("\t" + opt + " GG=" + mmcc.goodGood +
                           " BG=" + mmcc.badGood + " BB=" + mmcc.badBad);
      }
    }

    optInfo.setNewAtributes(mainDepots.getAttributes());
    return optInfo;
  }

  public CoolingSchedule getCoolingSchedule() {
    return coolingSchedule;
  }

  public void setCoolingSchedule(CoolingSchedule coolingSchedule) {
    this.coolingSchedule = coolingSchedule;
  }

  public double getFinalTemperature() {
    return finalTemperature;
  }

  public void setFinalTemperature(double finalTemperature) {
    this.finalTemperature = finalTemperature;
  }

  public double getInitTemperature() {
    return initTemperature;
  }

  public void setInitTemperature(double initTemperature) {
    this.initTemperature = initTemperature;
  }

  public int getIterationsPerTemp() {
    return iterationsPerTemp;
  }

  public void setIterationsPerTemp(int iterationsPerTemp) {
    this.iterationsPerTemp = iterationsPerTemp;
  }

  public int getMaxIterations() {
    return maxIterations;
  }

  public void setMaxIterations(int maxIterations) {
    this.maxIterations = maxIterations;
  }

}
