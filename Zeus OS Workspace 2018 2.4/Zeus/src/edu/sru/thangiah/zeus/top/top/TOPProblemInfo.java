package edu.sru.thangiah.zeus.top;

import java.util.Vector;
import edu.sru.thangiah.zeus.top.topcostfunctions.*;
import edu.sru.thangiah.zeus.core.ZeusProblemInfo;
//import edu.sru.thangiah.zeus.core.ProblemInfo;


/**
 * Holds global information about a TOP problem
 * @author Peter Schallot
 */
public class TOPProblemInfo // extends ProblemInfo 
{
  /**
   * Select the type of cost function to be used in TOPNodesLLCostFuntions.
   * Used for changing the cost type for optimizations.
   */
  public enum TOPCostType 
  {
    SCORE_INVERSE, DISTANCE, DISTANCE_PLUS_SCORE_INVERSE, DISTANCE_PLUS_DISTANCE_TIMES_SCORE_INVERSE
  }

  public static final long serialVersionUID = 1;

  public static int truckMaxCapacity;                 //As all trucks have the same capacity, we only need to store it once.
  public static float truckMaxTravelTime;             //Again, all trucks are the same, so they all have the same maximum travel time.
  public static float startXCoord, startYCoord;       //Coords of the starting depot
  public static float endXCoord, endYCoord;           //Coords of the ending depot
  public static int numTeamMembers;                   //Number of trucks that will ultimately be used to calculate the score.

  public static TOPCostType costType;                 //Determines which cost function is to be used
  public static int numTargetAreas;                   //Number of elliptical target areas which will be generated
  public static double targetPointDistanceFactor;     //Determines the distance between the foci of each target area
  public static double targetEllipseSizeFactor;       //Determines the overall size of each elliptical target area
  public static double maxDistanceBuffer;             //Percentage over the maximum travel distance that trucks will be allowed to go over before optimization
  public static double overMaxDistanceCostPenalty;    //Penalty to the score of trucks which go over the maximum travel distance
  public static double initialAngle;                  //Angle at which the first target area will be generated

  public static String scriptFileLocation;            //Directory where Zeus Script Files will be placed
  public static boolean enableGUI;                    //Controls whether the GUI is displayed after a solution is found
  public static boolean enableOptimizations;          //Controls whether or not optimizations are used
  public static boolean enableGA;                     //Controls whether or not genetic algorithms are used

  public static int gaPopulationSize;                 //Population size of each GA generation
  public static int gaNumGenerations;                 //Number of GA generations
  public static int gaNumCrossoverPts;                //Number of GA crossover points
  public static double gaCrossoverRate;               //Probability of a crossover in the GA
  public static double gaMutationRate;                //Probability of a mutation in the GA

  /*FIX
   * ADD WHAT THEY DO
   * |
   * |
   * |
   * V
   * */
public static TOPNodesLLCostFunctions nodesLLLevelCostF;
public static TOPTruckCostFunctions truckLevelCostF;
public static TOPTruckLLCostFunctions truckLLLevelCostF;
public static TOPDepotCostFunctions depotLevelCostF;
public static TOPDepotLLCostFunctions depotLLLevelCostF;
public static Object tempFileLocation;
public static String workingDirectory;
public static String inputPath;
public static String outputPath;
public static LinearGreedyInsertShipment insertShipType;
public static TOPShipmentLinkedList selectShipType;
public static int numCustomers;
public static int numTrucks;
public static String fileName;
public static Vector truckTypes;
public static int probType;
public static int noOfVehs;
public static String numDepots;
/* ^
 * |
 * |
 * |
 * |
 * FIX
 * ADD WHAT THEY DO
 * */
  /**
   * Constructor
   */
  public TOPProblemInfo() 
  {
  
  }

  /**
   * Returns the depot linked list cost function
   * @return TOPDepotLLCostFunctions
   */
  public static TOPDepotLLCostFunctions getTOPDepotLLCostFunctions() 
  {
    TOPDepotLLCostFunctions depotLLLevelCostF = null;
	return (TOPDepotLLCostFunctions)depotLLLevelCostF;
  }

}
