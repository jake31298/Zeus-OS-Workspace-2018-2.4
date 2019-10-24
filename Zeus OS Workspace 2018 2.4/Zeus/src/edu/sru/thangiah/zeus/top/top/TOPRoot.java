package edu.sru.thangiah.zeus.top;

import edu.sru.thangiah.zeus.top.topcostfunctions.*;
import edu.sru.thangiah.zeus.simplega.sGA;
//import edu.sru.thangiah.zeus.simplega.*;
import edu.sru.thangiah.zeus.core.Settings;
import java.io.*;
import java.util.*;

public class TOPRoot {

  /**
   * Constructor. Runs the TOP and calculates the total CPU time
   * Modified by Pete Schallot and David Crissman
   */
  public TOPRoot() {

    //Settings for the ProblemInfo class
    //Problem info consists of a set of static values that are used by a number
    //of different classes. The following has to be set in order for the program
    //to function correctly.
    TOPProblemInfo.nodesLLLevelCostF = new TOPNodesLLCostFunctions();
    TOPProblemInfo.truckLevelCostF = new TOPTruckCostFunctions();
    TOPProblemInfo.truckLLLevelCostF = new TOPTruckLLCostFunctions();
    TOPProblemInfo.depotLevelCostF = new TOPDepotCostFunctions();
    TOPProblemInfo.depotLLLevelCostF = new TOPDepotLLCostFunctions();

    //Paths for temporary, input and output files
    TOPProblemInfo.tempFileLocation = TOPProblemInfo.workingDirectory + "\\temp";
    TOPProblemInfo.inputPath = TOPProblemInfo.workingDirectory + "\\data\\top\\problems\\";
    TOPProblemInfo.outputPath = TOPProblemInfo.workingDirectory + "\\data\\top\\results\\";
    TOPProblemInfo.scriptFileLocation = TOPProblemInfo.workingDirectory + "\\data\\top\\scripts\\";

    Settings.debugLevel = Settings.OFF;

    //Choose the problem-solving parameters (if the GA is enabled, these values will be overwritten)
    TOPProblemInfo.numTargetAreas = 25;
    TOPProblemInfo.targetPointDistanceFactor = 0.0;
    TOPProblemInfo.targetEllipseSizeFactor = 1.0;
    TOPProblemInfo.maxDistanceBuffer = 1.2;
    TOPProblemInfo.overMaxDistanceCostPenalty = 1.2;
    TOPProblemInfo.costType = TOPProblemInfo.TOPCostType.SCORE_INVERSE;
    TOPProblemInfo.initialAngle = 0.0;

    //Choose the parameters of the genetic algorithm (these numbers will not be used if the GA is not enabled)
    TOPProblemInfo.gaPopulationSize = 20;
    TOPProblemInfo.gaNumGenerations = 5;
    TOPProblemInfo.gaNumCrossoverPts = 3;
    TOPProblemInfo.gaCrossoverRate = 0.01;
    TOPProblemInfo.gaMutationRate = 0.005;

    //Solve the problem(s)
    runSingleProblem("PROB7-4-t.PRN", true, true, true); //Problem 7-4-t, with GA, optimizations, and GUI enabled
    //runScriptFile("TOPScript.zsf");
  }

  /**
   * Generates a solution to a single problem file
   * @param fileName String
   * @param gui boolean
   * @param opt boolean
   * @param ga boolean
   * Added by David Crissman
   */
  public void runSingleProblem(String fileName, boolean gui, boolean opt, boolean ga) {
    //Enables or disables the GUI, optimizations, and genetic algorithms
    TOPProblemInfo.enableGUI = gui;
    TOPProblemInfo.enableOptimizations = opt;
    TOPProblemInfo.enableGA = ga;

    //Run the problem, with or without the GA
    if (TOPProblemInfo.enableGA == true) {
      new sGA(fileName);
    }
    else {
      new TOP(fileName);
    }

  }

  /**
   * Reads and executes a ZSF (Zeus Script File) file to automatically solve multiple problems
   * @param fileName String
   * Added by David Crissman
   */
  public void runScriptFile(String fileName) {
    BufferedReader inFile;
    StringTokenizer strToken;
    String readLn, token;

    //Disable the GUI, optimizations, and genetic algorithms by default
    TOPProblemInfo.enableGUI = false;
    TOPProblemInfo.enableOptimizations = false;
    TOPProblemInfo.enableGA = false;

    try {
      //Open the script file
      inFile = new BufferedReader(new FileReader(TOPProblemInfo.scriptFileLocation + fileName));

      //Read each line of the file
      readLn = inFile.readLine();
      while (readLn != null) {
        //Ignore empty lines and commented lines
        if ((readLn.length() > 0) && (readLn.charAt(0) != ';')) {
          strToken = new StringTokenizer(readLn);

          //Perform actions according to the commands in the file
          token = strToken.nextToken();
          if (token.compareToIgnoreCase("RunProblem") == 0) {
            if (strToken.hasMoreTokens() == true) {
              //The next token is the problem filename
              token = strToken.nextToken();

              //Run the problem, with or without the GA
              if (TOPProblemInfo.enableGA == true) {
		new sGA(token);
	      }
              else {
                new TOP(token);
              }
            }
            else {
              Settings.printDebug(Settings.ERROR, "No problem filename found after RunProblem command in script file " + fileName);
            }
          }
          else if (token.compareToIgnoreCase("EnableGUI") == 0) {
            TOPProblemInfo.enableGUI = true;
          }
          else if (token.compareToIgnoreCase("DisableGUI") == 0) {
            TOPProblemInfo.enableGUI = false;
          }
          else if (token.compareToIgnoreCase("EnableGA") == 0) {
            TOPProblemInfo.enableGA = true;

            if (strToken.hasMoreTokens() == true) {
              //The second token is the population size
              token = strToken.nextToken();
              TOPProblemInfo.gaPopulationSize = Integer.parseInt(token);

              if (strToken.hasMoreTokens() == true) {
                //The third token is the number of generations
                token = strToken.nextToken();
                TOPProblemInfo.gaNumGenerations = Integer.parseInt(token);

                if (strToken.hasMoreTokens() == true) {
                  //The fourth token is the number of crossover points
                  token = strToken.nextToken();
                  TOPProblemInfo.gaNumCrossoverPts = Integer.parseInt(token);

                  if (strToken.hasMoreTokens() == true) {
                    //The fifth token is the crossover rate
                    token = strToken.nextToken();
                    TOPProblemInfo.gaCrossoverRate = Double.parseDouble(token);

                    if (strToken.hasMoreTokens() == true) {
                      //The sixth token is the mutation rate
                      token = strToken.nextToken();
                      TOPProblemInfo.gaMutationRate = Double.parseDouble(token);
                    }
                    else {
                      TOPProblemInfo.enableGA = false;
                      Settings.printDebug(Settings.ERROR, "No mutation rate found after EnableGA command in script file " + fileName);
                    }
                  }
                  else {
                    TOPProblemInfo.enableGA = false;
                    Settings.printDebug(Settings.ERROR, "No crossover rate found after EnableGA command in script file " + fileName);
                  }
                }
                else {
                  TOPProblemInfo.enableGA = false;
                  Settings.printDebug(Settings.ERROR, "No number of crossover points found after EnableGA command in script file " + fileName);
                }
              }
              else {
                TOPProblemInfo.enableGA = false;
                Settings.printDebug(Settings.ERROR, "No number of generations found after EnableGA command in script file " + fileName);
              }
            }
            else {
              TOPProblemInfo.enableGA = false;
              Settings.printDebug(Settings.ERROR, "No population size found after EnableGA command in script file " + fileName);
            }
          }
          else if (token.compareToIgnoreCase("DisableGA") == 0) {
            TOPProblemInfo.enableGA = false;
          }
          else if (token.compareToIgnoreCase("EnableOpt") == 0) {
            TOPProblemInfo.enableOptimizations = true;
          }
          else if (token.compareToIgnoreCase("DisableOpt") == 0) {
            TOPProblemInfo.enableOptimizations = false;
          }
          else {
            //Indicate to the user that there was an invalid command
            Settings.printDebug(Settings.ERROR, "Invalid command " + token + " found in script file " + fileName);
          }
	}

        readLn = inFile.readLine();
      }

      //Compiles the solution data into a spreadsheet. Currently, this is specific to the
      //TOP problem set, so future groups will need to modify this or remove it
      Runtime.getRuntime().exec("\"" + TOPProblemInfo.workingDirectory + "\\data\\top\\" +
                                "CompileResults.exe" + "\"");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
