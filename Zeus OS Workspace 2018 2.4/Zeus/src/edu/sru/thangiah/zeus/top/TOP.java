package edu.sru.thangiah.zeus.top;

import java.io.*;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.sru.thangiah.zeus.top.topgui.*;
import edu.sru.thangiah.zeus.top.topqualityassurance.*;
import edu.sru.thangiah.zeus.vrp.VRPDepot;
import edu.sru.thangiah.zeus.vrp.VRPTruck;
import edu.sru.thangiah.zeus.vrp.VRPTruckType;
import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.localopts.*;
import edu.sru.thangiah.zeus.localopts.interopts.FirstFirstInterSearch;
import edu.sru.thangiah.zeus.localopts.intraopts.*;
import edu.sru.thangiah.zeus.top.*;
import edu.sru.thangiah.zeus.top.TOPProblemInfo.TOPCostType;
import edu.sru.thangiah.zeus.localopts.interopts.*;
import edu.sru.thangiah.zeus.core.ZeusProblemInfo.*;
import edu.sru.thangiah.zeus.core.Depot.*;
import edu.sru.thangiah.zeus.core.CostFunctions.*;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;

 



//import edu.sru.thangiah.zeus.localopts.*;

/**
 * <p>Title:</p> TOP in Zeus
 * <p>Description:</p> Solve Team Orienteering Problems.
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company:</p>
 * @author Sam R. Thangiah
 * Modified by Peter Schallot and David Crissman.
 * @version 2.0
 */
public class TOP {
  private String dataFile;                      //Filename of the problem to be solved
  private long startTime, endTime;              //Track the CPU processing time
  private Vector<TOPOptEntry> mainOpts;         //Contains the collections of optimizations
  private Vector<String> optInformation;        //Contains information about routes
  private TOPShipmentLinkedList mainShipments;  //Customers read in from a file or database that are available
  private TOPDepotLinkedList mainDepots;        //Depots linked list for the TOP problem
  private TOPQualityAssurance TOPQA;            //Check the integrity and quality of the solution
  private TOPShipmentLinkedList primarySelectType, secondarySelectType;  //Used to hold the primary and secondary algorithms
                                                                         //so that we can switch between them
  /**
   * Constructor
   * @param fileName String
   * Modified by Pete Schallot and David Crissman
   */
  public TOP(String fileName) {
    float bestScore;                                                     //Score of the best solution so far
    TOPShipmentLinkedList resetMainShipments, bestMainShipments;         //Shipment linked list of the best solution
    TOPDepotLinkedList resetMainDepots, bestMainDepots;                  //Depot linked list of the best solution
    double angleBetweenPasses, targetPointDistance, targetEllipseSize;   //Information about the target elliptical area
    double targetX, targetY, tempX;                                      //Coordinates of the target point
    OptInfo opInfo;
    
    // ---------- TEMPORARY FIX --------------//
    
    String dataFile = "data\\TOP" + 7 + ".xlsx";
	//String constraintFile = "\\TOP" + 7 + "." + 4 + "People.xlsx";
    String constraintFile = "\\TOP\\PROB1-2-a.PRN.xlsx";
	String outFile = "TOP" + 7 + "." + 4 + "." + 't';
    
    dataFile = fileName;
    mainOpts = new Vector();
    optInformation = new Vector<String>();
    mainShipments = new TOPShipmentLinkedList();
    mainDepots = new TOPDepotLinkedList();

    //read in the TOP data
    //readDataFromFile(TOPProblemInfo.inputPath + dataFile);
    //readDataFromExcelFile(TOPProblemInfo.inputPath + dataFile);
    //readShipmentsDataFromExcelFile(TOPProblemInfo.inputPath + dataFile, fileName, 0, 0, false);
    
    readShipmentsDataFromExcelFile(TOPProblemInfo.inputPath + dataFile, TOPProblemInfo.inputPath + constraintFile, 't', 4, false);
    
    Settings.printDebug(Settings.COMMENT, "Read Data File: " + TOPProblemInfo.inputPath + dataFile);
    writeDataFile(dataFile.substring(dataFile.lastIndexOf("/") + 1));

    //Ensure that the shipment linked list has been loaded with the data
    if (mainShipments.getTOPHead() == null) {
      Settings.printDebug(Settings.ERROR, "TOP: Shipment linked list is empty");
    }
    else if (mainDepots.getStartDepot() == null) {
      Settings.printDebug(Settings.ERROR, "TOP: Starting depot has not been specified");
    }
    else if (mainDepots.getEndDepot() == null) {
      Settings.printDebug(Settings.ERROR, "TOP: Ending depot has not been specified");
    }
    else {
      //If optimizations are enabled, create a list of them to be run
/*
    	if (TOPProblemInfo.enableOptimizations == true) {
        //mainOpts.add(new TOPOptEntry(new FirstFirstInterSearch(new Exchange01()), TOPProblemInfo.TOPCostType.SCORE_INVERSE));
        mainOpts.add(new TOPOptEntry(new FirstFirstInterSearch(new Exchange01()), TOPCostType.SCORE_INVERSE));
        mainOpts.add(new TOPOptEntry(new FirstFirstIntraSearch(new TwoOpt()), TOPProblemInfo.TOPCostType.DISTANCE_PLUS_DISTANCE_TIMES_SCORE_INVERSE));
        mainOpts.add(new TOPOptEntry(new FirstFirstIntraSearch(new TwoOpt()), TOPProblemInfo.TOPCostType.DISTANCE_PLUS_DISTANCE_TIMES_SCORE_INVERSE));
        mainOpts.add(new TOPOptEntry(new FirstFirstIntraSearch(new TwoOpt()), TOPProblemInfo.TOPCostType.DISTANCE_PLUS_DISTANCE_TIMES_SCORE_INVERSE));
        //mainOpts.add(new TOPOptEntry(new TOPReduction(), TOPProblemInfo.TOPCostType.DISTANCE_PLUS_DISTANCE_TIMES_SCORE_INVERSE));
        mainOpts.add(new TOPOptEntry(new TOPReduction(), TOPProblemInfo.TOPCostType.DISTANCE_PLUS_DISTANCE_TIMES_SCORE_INVERSE));
        mainOpts.add(new TOPOptEntry(new FirstFirstIntraSearch(new OneOpt()), TOPProblemInfo.TOPCostType.DISTANCE_PLUS_DISTANCE_TIMES_SCORE_INVERSE));
        mainOpts.add(new TOPOptEntry(new FirstFirstIntraSearch(new TwoOpt()), TOPProblemInfo.TOPCostType.DISTANCE_PLUS_DISTANCE_TIMES_SCORE_INVERSE));

      }
*/
      //Set up the shipment insertion type
      TOPProblemInfo.insertShipType = new LinearGreedyInsertShipment();
      Settings.printDebug(Settings.COMMENT, LinearGreedyInsertShipment.WhoAmI());

      //Capture the CPU time required for solving the problem
      startTime = System.currentTimeMillis();

      //Calculate the rest of the information about the initial target area
      angleBetweenPasses = (2.0 * Math.PI) / TOPProblemInfo.numTargetAreas;
      targetPointDistance = TOPProblemInfo.truckMaxTravelTime * TOPProblemInfo.targetPointDistanceFactor;
      targetEllipseSize = TOPProblemInfo.truckMaxTravelTime * TOPProblemInfo.targetEllipseSizeFactor;
      targetX = mainDepots.getStartDepot().getXCoord() + (Math.cos(TOPProblemInfo.initialAngle) * targetPointDistance);
      targetY = mainDepots.getStartDepot().getYCoord() + (Math.sin(TOPProblemInfo.initialAngle) * targetPointDistance);

      //Determine which shipments can and cannot be added to truck routes due to distance
      mainDepots.markUnreachableShipments(mainShipments);

      //Generate solutions for each target area, keeping track of the overall best solution
      bestMainShipments = new TOPShipmentLinkedList();
      resetMainShipments = (TOPShipmentLinkedList)mainShipments.clone();
      bestMainDepots = new TOPDepotLinkedList();
      resetMainDepots = (TOPDepotLinkedList)mainDepots.clone();
      bestScore = 0.0f;
      for (int i = 0; i < TOPProblemInfo.numTargetAreas; i++) {
        //Set up the shipment selection type
        secondarySelectType = new HighestDemandInReachableArea();
        primarySelectType = new EllipticalTargetArea(targetX, targetY, targetEllipseSize, secondarySelectType);
        TOPProblemInfo.selectShipType = primarySelectType;
        Settings.printDebug(Settings.COMMENT, EllipticalTargetArea.WhoAmI());

        //Captures the initial information on solving the problem, and
        //returns the total customer and total distance after the initial solution
        opInfo = createInitialRoutes();
        optInformation.add("Inital Solution " + opInfo);

        //Get the initial solution
        //Depending on the Settings status, display information on the routes
        //Trucks used, total demand, dist, travel time and cost
        Settings.printDebug(Settings.COMMENT, "Created Initial Routes ");
        Settings.printDebug(Settings.COMMENT, "Initial Stats: " + mainDepots.getTeamSolutionString());

        //Optimize the solution
        runOptimizations();

        //Ensure that the solution is feasible
        maintainFeasibility();

        //If this is the best solution so far, save it
        if ( (TOPProblemInfo.getTOPDepotLLCostFunctions().getTeamTotalDemand(mainDepots) > bestScore) ||
            (bestScore == 0.0)) {
          bestScore = TOPProblemInfo.getTOPDepotLLCostFunctions().getTeamTotalDemand(mainDepots);
          bestMainDepots = (TOPDepotLinkedList)mainDepots.clone();
          bestMainShipments = (TOPShipmentLinkedList)mainShipments.clone();
        }

        //Move the target area for the next pass
        tempX = (float) ( (targetX * Math.cos(angleBetweenPasses)) - (targetY * Math.sin(angleBetweenPasses))) -
            TOPProblemInfo.startXCoord;
        targetY = (float) ( (targetX * Math.sin(angleBetweenPasses)) + (targetY * Math.cos(angleBetweenPasses))) -
            TOPProblemInfo.startYCoord;
        targetX = tempX;

        //Reset mainDepots and mainShipments for the next pass
        mainDepots = (TOPDepotLinkedList)resetMainDepots.clone();
        mainShipments = (TOPShipmentLinkedList)resetMainShipments.clone();
      }

      //Use the best solution as the final answer
      mainDepots = (TOPDepotLinkedList)bestMainDepots.clone();
      mainShipments = (TOPShipmentLinkedList)bestMainShipments.clone();
    }

    //Capture the CPU time required for solving the problem
    endTime = System.currentTimeMillis();

    //Only perform these steps if the GA is disabled. If it is enabled, these steps will
    //be performed at the very end by the GA class
    if (TOPProblemInfo.enableGA == false) {
      //Run quality assurance on the solution
      runQA();

      //Write the solution files
      writeLongSolutionToExcel(dataFile.substring(dataFile.lastIndexOf("/") + 1));
      writeShortSolutionExcel(dataFile.substring(dataFile.lastIndexOf("/") + 1));

      //Display the score
      Settings.printDebug(Settings.COMMENT, "Total Score: " + getTotalScore());

      if (TOPProblemInfo.enableGUI == true) {
        displayGUI();
      }
    }
  }

  /**
   * Runs the quality assurance package to ensure that the solution is within the constraints of the problem
   * @return boolean
   */
  public boolean runQA(){
    TOPQA = new TOPQualityAssurance(mainDepots, mainShipments);

    return TOPQA.runQA();
  }

  //public void displayGU

  /**
   * Creates the initial solution for the problem
   * Modified by Peter Schallot and David Crissman
   */
  public OptInfo createInitialRoutes() {
    OptInfo info = new OptInfo();     //OptInfo has old and new attributes
    TOPDepot startDepot, endDepot;    //Depot to which shipments are currently being added
    TOPShipment currentShipment;      //Shipment currently being inserted

    //Check if selection and insertion type methods have been selected
    if (TOPProblemInfo.selectShipType == null) {
      Settings.printDebug(Settings.ERROR, "No selection shipment type has been assigned");
    }
    if (TOPProblemInfo.insertShipType == null) {
      Settings.printDebug(Settings.ERROR, "No insertion shipment type has been assigned");
    }

    //Capture the old attributes
    info.setOldAttributes(mainDepots.getAttributes());

    //Assign as many shipments as possible to trucks using the primary selection algorithm
    TOPProblemInfo.selectShipType = primarySelectType;
    while (mainShipments.isAllShipsAssignedOrUnreachable() == false) {
      currentShipment = mainShipments.getNextInsertShipment(mainDepots, mainDepots.getStartDepot(),
	  mainShipments, null);

      //If the shipment is null, no more shipments can be obtained using the primary algorithm
      if (currentShipment == null) {
	break;
      }
      else {
	//The selected shipment will be inserted into the route
	if (!mainDepots.insertShipment(currentShipment)) {
	  Settings.printDebug(Settings.COMMENT,
			      "The Shipment: <" + currentShipment.getIndex() + "> cannot be routed");
	  currentShipment.setChecked(true);
	}
	else {
	  Settings.printDebug(Settings.COMMENT,
			      "The Shipment: <" + currentShipment.getIndex() + "> was routed");
	  currentShipment.setAssigned(true);
	}
      }
    }

    //Clear the "checked" flags on all shipments
    currentShipment = mainShipments.getTOPHead();
    while (currentShipment != null) {
      currentShipment.setChecked(false);
      currentShipment = currentShipment.getTOPNext();
    }

    //Assign the remaining shipments to trucks using the secondary selection algorithm
    TOPProblemInfo.selectShipType = secondarySelectType;
    while (mainShipments.isAllShipsAssignedOrUnreachable() == false) {
      currentShipment = mainShipments.getNextInsertShipment(mainDepots, mainDepots.getStartDepot(),
          mainShipments, null);

      //If the shipment is null, print error message
      if (currentShipment == null) {
        Settings.printDebug(Settings.COMMENT, "No shipment was selected");
        break;
      }
      else {
        //The selected shipment will be inserted into the route
        if (!mainDepots.insertShipment(currentShipment)) {
          Settings.printDebug(Settings.COMMENT,
                              "The Shipment: <" + currentShipment.getIndex() + "> cannot be routed");
        }
        else {
          Settings.printDebug(Settings.COMMENT,
                              "The Shipment: <" + currentShipment.getIndex() + "> was routed");
          currentShipment.setAssigned(true);
        }
      }

    }

    //Calculate information about the solution that was generated
    TOPProblemInfo.depotLLLevelCostF.calculateTotalsStats(mainDepots);
    info.setNewAtributes(mainDepots.getAttributes());

    return info;
  }

  /**
   * Reads information about a problem from a file
   * @param TOPFileName String
   * @return int
   * Modified by David Crissman
   */
  public int readDataFromFile(String TOPFileName) {
    BufferedReader inFile;                  //File reader
    StringTokenizer strToken;               //Used to tokenize the lines of the file
    String readLn;                          //Current line of text from the file
    int retValue = 1;                       //Returned to indicate success (1) or failure (0)
    int problemType = 0;
    int numVehicles = 0; //Number of trucks
    int startNo, endNo, stopNo, demand;
    float xCoord, yCoord;
    TOPTruckType truckType;
    TOPDepot newDepot;

    try {
      inFile = new BufferedReader(new FileReader(TOPFileName));

      //Read global information about the problem from the first line
      readLn = inFile.readLine();
      strToken = new StringTokenizer(readLn);
      TOPProblemInfo.numCustomers = Integer.parseInt(strToken.nextToken()); //First token is the number of customers
      TOPProblemInfo.numTrucks = Integer.parseInt(strToken.nextToken()); //Second token is the number of trucks
      TOPProblemInfo.numTeamMembers = TOPProblemInfo.numTrucks;
      TOPProblemInfo.truckMaxTravelTime = Float.parseFloat(strToken.nextToken()); //Third token is the trucks' maximum travel time

      //Maximum time of 0 indicates no limit, so make it a very large number
      if (TOPProblemInfo.truckMaxTravelTime == 0) {
	TOPProblemInfo.truckMaxTravelTime = Integer.MAX_VALUE;
      }

      //Set some of the other global information
      TOPProblemInfo.fileName = TOPFileName;
      TOPProblemInfo.probType = problemType;
      TOPProblemInfo.noOfVehs = numVehicles;

      // We're assuming that each truck has essentially infinite capacity.
      TOPProblemInfo.truckMaxCapacity = Integer.MAX_VALUE;

      //There is only one type of truck for this problem, so create it
      truckType = new TOPTruckType(0, TOPProblemInfo.truckMaxTravelTime, TOPProblemInfo.truckMaxCapacity, "1");
      TOPProblemInfo.truckTypes = new Vector();
      TOPProblemInfo.truckTypes.add(truckType);

      //Ignore the second line
      readLn = inFile.readLine();

      //The third line contains information about the starting depot
      readLn = inFile.readLine();
      strToken = new StringTokenizer(readLn);
      startNo = Integer.parseInt(strToken.nextToken());
      TOPProblemInfo.startXCoord = Float.parseFloat(strToken.nextToken());
      TOPProblemInfo.startYCoord = Float.parseFloat(strToken.nextToken());

      //Read the information for each stop and add each one to mainShipments
      for (int i = 0; i < (TOPProblemInfo.numCustomers - 2); i++) {
	readLn = inFile.readLine();
	strToken = new StringTokenizer(readLn);
	stopNo = Integer.parseInt(strToken.nextToken());
	xCoord = Float.parseFloat(strToken.nextToken());
	yCoord = Float.parseFloat(strToken.nextToken());
	demand = Integer.parseInt(strToken.nextToken());
	mainShipments.insertShipment(stopNo, xCoord, yCoord, demand);
      }
      //The last line contains information about the ending depot
      readLn = inFile.readLine();
      strToken = new StringTokenizer(readLn);
      endNo = Integer.parseInt(strToken.nextToken());
      TOPProblemInfo.endXCoord = Float.parseFloat(strToken.nextToken());
      TOPProblemInfo.endYCoord = Float.parseFloat(strToken.nextToken());

      //Create the starting depot
      newDepot = new TOPDepot(startNo, TOPProblemInfo.startXCoord, TOPProblemInfo.startYCoord);
      mainDepots.insertLastDepot(newDepot);
      mainDepots.setStartDepot(startNo);

      //Create an initial truck and add it to the starting depot
      newDepot.getMainTrucks().insertTruckLast(new TOPTruck(truckType, TOPProblemInfo.startXCoord,
	  TOPProblemInfo.startYCoord, TOPProblemInfo.endXCoord, TOPProblemInfo.endYCoord));

      //Create the ending depot
      newDepot = new TOPDepot(endNo, TOPProblemInfo.endXCoord, TOPProblemInfo.endYCoord);
      mainDepots.insertLastDepot(newDepot);
      mainDepots.setEndDepot(endNo);

      //Close the file
      inFile.close();
    }
    catch (Exception e) {
      e.printStackTrace();
      retValue = 0;
    }

    return retValue;
  }


  //sGA.java needs this
  public void writeAllSolutionFiles() {
	    writeDataFile(this.dataFile);
	    writeLongSolutionToExcel(this.dataFile);
	    writeShortSolutionExcel(this.dataFile);
	  }
  
  
///EXECL FILE STUFF
  
  
	
	///////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////
	//   TEST SECTION	TEST SECTION	TEST SECTION	TEST SECTION //
	///////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////
	
  /**
	 * Reads in shipments data from an excel file 
	 * @param dataFile
	 * @param constraintFile
	 * @param constraint
	 * @param truckCount
	 * @param routeToOriginal
	 */
	public void readShipmentsDataFromExcelFile(String dataFile, String constraintFile, char constraint, int truckCount, boolean routeToOriginal) {
		try {
			int vehicleCount = 0;
			int numLocations = 0;
			int charOffset = 10; //Since letters in getNumericValue start at 10
			TOPDepot firstDepot = null;
			TOPDepot secondDepot = null;
			
			//Cortlin's Test
			int 	index 	 = 0,
					j 		 = 0,
					type 	 = 0, 							//type
					m        = 0,                           //number of vehicles
					n        = 0,                           //number of customers
					//t        = 0,                           //number of days(or depots)
					//D        = 0,                           //maximum duration of route
					Q        = 0;                           //maximum load of vehicle
			float D = 0;
			//End Cortlin's Test
			
			Vector custTypes = new Vector();
			//Obtain the different customer types
			for (int ct = 0; ct < 1; ct++) {
				custTypes.add(new Integer(1));
			}	 

			FileInputStream dataInputStream = new FileInputStream(".\\/data/TOP/Problems/PROB1-2-a.PRN.xlsx");//dataFile);
			XSSFWorkbook dataWorkbook = new XSSFWorkbook(dataInputStream);
			XSSFSheet dataWworksheet = dataWorkbook.getSheetAt(0);
			
			Iterator<Row> rowIt = dataWworksheet.rowIterator();
			Row row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			row = rowIt.next();
			//System.out.println(row);

		
		
			while ( rowIt.hasNext() )
			{
				
				try
				{
				row = rowIt.next();
				
				Cell cellA1 = row.getCell(0); //System.out.println(cellA1);
				int i = (int)cellA1.getNumericCellValue(); System.out.println(i);
				Cell cellB1 = row.getCell(1); //System.out.println(cellB1);
				float  x = (float)cellB1.getNumericCellValue();
				Cell cellC1 = row.getCell(2); //System.out.println(cellC1);
				float y = (float)cellC1.getNumericCellValue();
				Cell cellD1 = row.getCell(3); //System.out.println(cellD1);
				int s = (int)cellD1.getNumericCellValue();
				
				
				System.out.print(i + " " + x + " " + y + " " + s + "\n");
				//edu.sru.thangiah.zeus.core.ZeusProlemInfo;
				int list[] = new int[ZeusProblemInfo.getMaxCombinations()];		    
				int currentComb[][] = new int[ZeusProblemInfo.getMaxHorizon()][ZeusProblemInfo.getMaxCombinations()];	

				Integer custType = (Integer) custTypes.elementAt(0);
				if(i != 0 && s != 0) //May not need s check 
				{
					mainShipments.insertShipment(i, x, y, s, custType.toString(),
							list, currentComb);		
				}
				else
				{
					if(firstDepot == null)
					{
						firstDepot = new TOPDepot(i, x, y); //n is the number of customers
						mainDepots.insertDepotLast(firstDepot);
					}
					else if (secondDepot == null && !routeToOriginal)
					{
						secondDepot = new TOPDepot(i, x, y); //n is the number of customers
						mainDepots.insertDepotLast(secondDepot);
					}
					
					
					//depotMade = true;
				}
				if(numLocations < i)
				{
					numLocations = i;
				}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			dataInputStream.close();

			FileInputStream constraintInputStream = new FileInputStream(".\\/data/TOP/Problems/TOP7.4People.xlsx");//constraintFile);
			XSSFWorkbook constraintWorkbook = new XSSFWorkbook(constraintInputStream);
			XSSFSheet constraintWorksheet = constraintWorkbook.getSheetAt(0);

			Row dataRow = constraintWorksheet.getRow(Character.getNumericValue(constraint) - charOffset); //Get what number of the alphabet it is
			Cell dataCell = dataRow.getCell(1);
			D = (float)dataCell.getNumericCellValue();
			//D = (int)(L*1.714);
			//System.out.println(L);

			constraintInputStream.close();

			if(firstDepot == null)
			{
				firstDepot = new TOPDepot(0, 0, 0); //n is the number of customers
				mainDepots.insertDepotLast(firstDepot);
			}
			if(vehicleCount == 0)
			{
				vehicleCount = 2;
			}
			if (Q == 0) { //if there is no maximum capacity, set it to a very large number
				Q = 10000000;
			}


			//********************************
			//@TODO: SET TO BE BASED ON TIME
			//********************************
			if (D == 0) { //if there is no travel time, set it to a very large number
				D = 10000000; //if there is not maximum distance, set it to a very large number
				//ProblemInfo.maxCapacity = Q;  //maximum capacity of a vehicle
				//ProblemInfo.maxDistance = D;  //maximum distance of a vehicle
			}


			//Put the problem information into the ProblemInfo class
			//set the problem info for the problem
			ZeusProblemInfo.setNumDepots(1); //Set the number of depots to 1 for this problem
			ZeusProblemInfo.setProbFileName(dataFile); //name of the file being read in
			ZeusProblemInfo.setProbType(0); //problem type
			ZeusProblemInfo.setNoOfVehs(vehicleCount); //number of vehicles
			ZeusProblemInfo.setNoOfShips(numLocations); //number of locations

			/** @todo  There three variables need to be defined at the beginning of
			 * the method */
			float maxCapacity = Q; //maximum capacity of a vehicle
			float maxDistance = D; //maximum distance of a vehicle

			String serviceType = "1";
			TOPTruckType truckType = new TOPTruckType(1, maxDistance,
					maxCapacity, serviceType);
			
			for(int t = 0; t < truckCount; t++)
			{
			
				TOPProblemInfo.truckTypes.add(truckType); // Originally These are all ProblemInfo.xyz
				
			}
			//Each depot has a mainTrucks. The different truck types available are
			//inserted into the mainTrucks type. For the TOP, there is only one truck type
			firstDepot = (TOPDepot) mainDepots.getHead().getNext();
			secondDepot = (TOPDepot) firstDepot.getNext();
			
			 for (int i = 0; i < TOPProblemInfo.truckTypes.size(); i++) {
				TOPTruckType ttype = (TOPTruckType) TOPProblemInfo.truckTypes.
						elementAt(i);
				if(routeToOriginal)
				{
					firstDepot.getMainTrucks().insertTruckLast(new TOPTruck(ttype,
							firstDepot.getXCoord(), firstDepot.getYCoord()));
				}
				else
				{
					firstDepot.getMainTrucks().insertTruckLast(new TOPTruck(ttype,
							firstDepot.getXCoord(), firstDepot.getYCoord(), secondDepot.getDepotNum(), secondDepot.getXCoord(), secondDepot.getYCoord()));
				}
			}
			Settings.lockTrucks = false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  catch (org.apache.poi.POIXMLException e)
		{
			e.printStackTrace();
		} 
	}
	
	///////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////
	//   TEST SECTION	TEST SECTION	TEST SECTION	TEST SECTION //
	///////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////
  
  public void writeDataFile(String file) {
		try {
			PrintStream ps = new PrintStream(new FileOutputStream(/*ZeusProblemInfo.getOutputPath()+*/file));
			//PrintStream ps = new PrintStream(new FileOutputStream(ProblemInfo1.
			//		outputPath +file +"_students.txt"));
			mainShipments.writeTOPShipments(ps);
		}
		catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}
	
	public void writeShortSolutionExcel(String file) 
	{
		
		//setup excel file
		int rowCounter = 0;
		XSSFWorkbook workbook = new XSSFWorkbook(); // create a book
		XSSFSheet sheet = workbook.createSheet("Sheet1");// create a sheet
		XSSFRow curRow = sheet.createRow(rowCounter); // create a row
		
		//Problem info
		curRow.createCell(0).setCellValue("File: ");
		curRow.createCell(1).setCellValue(file);
		curRow.createCell(2).setCellValue("Num Depots: ");
		curRow.createCell(3).setCellValue(ZeusProblemInfo.getNumDepots());
		//curRow.createCell(3).setCellValue(ProblemInfo1.numDepots);
		curRow.createCell(4).setCellValue("Num Pick Up Points: ");
		curRow.createCell(5).setCellValue(ZeusProblemInfo.getNumCustomers());
		//curRow.createCell(5).setCellValue(ProblemInfo1.numCustomers);
		curRow.createCell(6).setCellValue("Num Trucks: ");
		curRow.createCell(7).setCellValue(ZeusProblemInfo.getNumTrucks());
		//curRow.createCell(7).setCellValue(ProblemInfo1.numTrucks);
		curRow.createCell(8).setCellValue("Processing Time: ");
		curRow.createCell(9).setCellValue((endTime - startTime) / 1000);
		curRow.createCell(10).setCellValue("seconds");
		
		//next row
		rowCounter++;
		curRow = sheet.createRow(rowCounter);
		
		
		curRow.createCell(0).setCellValue("Total Demand =");
		curRow.createCell(1).setCellValue(mainDepots.getAttributes().getTotalDemand());
		curRow.createCell(2).setCellValue("Total Distance =");
		curRow.createCell(3).setCellValue(mainDepots.getAttributes().getTotalDistance());
		curRow.createCell(4).setCellValue("Total Travel Time =");
		curRow.createCell(5).setCellValue(mainDepots.getAttributes().getTotalTravelTime());
		curRow.createCell(6).setCellValue("Total Cost = ");
		curRow.createCell(7).setCellValue(Math.round(mainDepots.getAttributes().getTotalCost()*100.0)/100.0);
			
		rowCounter++;
		curRow = sheet.createRow(rowCounter);
			
		Depot depotHead = mainDepots.getHead();
		Depot depotTail = mainDepots.getTail();
		
		//Truck header info
		curRow.createCell(0).setCellValue("Truck #");
		curRow.createCell(1).setCellValue("MaxCap:");
		curRow.createCell(2).setCellValue("Demand:");
		
		//loop through Depots, trucks, nodes
		while (depotHead != depotTail) 
		{
			Truck truckHead = depotHead.getMainTrucks().getHead();
			Truck truckTail = depotHead.getMainTrucks().getTail();
			
			//print truck data
			while (truckHead != truckTail) 
			{
				try 
				{
					rowCounter++;
					curRow = sheet.createRow(rowCounter);
					
					
					curRow.createCell(1).setCellValue(truckHead.getTruckType().getMaxCapacity());
					curRow.createCell(0).setCellValue(truckHead.getTruckNum());
					curRow.createCell(2).setCellValue(truckHead.getAttributes().getTotalDemand());
					
					
					Nodes nodesHead = truckHead.getMainNodes().getHead().getNext();
					Nodes nodesTail = truckHead.getMainNodes().getTail();
					
					rowCounter++;
					curRow = sheet.createRow(rowCounter);
					
					curRow.createCell(0).setCellValue("ROUTE:");
					int cellCount = 1;
					
					//print rout data
					while (nodesHead != nodesTail) 
					{
						curRow.createCell(cellCount).setCellValue(nodesHead.getIndex());

						cellCount++;
						nodesHead = nodesHead.getNext();
					}
					
					cellCount = 0;
				}
				catch(NullPointerException ex)
				{
						System.out.println("Null truck types detected");
						rowCounter--;
				}
					truckHead = truckHead.getNext();
			}
			depotHead = depotHead.getNext();
		}
			 
			rowCounter +=2;
			curRow = sheet.createRow(rowCounter);
			
			curRow.createCell(0).setCellValue("optimization Info");
			
			rowCounter ++;
			curRow = sheet.createRow(rowCounter);
			
			//print Optimization information
			for (int i = 0; i < optInformation.size(); i++) 
			{
				curRow.createCell(i).setCellValue(optInformation.elementAt(i).toString());
			}
			
			try 
		    {
				FileOutputStream fout = new FileOutputStream(new File(ZeusProblemInfo.getOutputPath() + file + "_short.xlsx"));
				//FileOutputStream fout = new FileOutputStream(new File(ProblemInfo1.outputPath + file + "_short.xlsx"));
		    	workbook.write(fout); 
		        fout.close();
		    } 
		    catch (Exception e) 
		    { 
		       e.printStackTrace(); 
		    } 
	}
	
	
	
	// Create a excel file to put in the final solutions
	 
	public void writeLongSolutionToExcel(String file) {
		try {
			// setting up workbook, row 1 information
			XSSFWorkbook workbook = new XSSFWorkbook();
			PrintStream fos = new PrintStream(new FileOutputStream(ZeusProblemInfo.getOutputPath()+ file + "_long.xlsx"));
			//PrintStream fos = new PrintStream(new FileOutputStream(ProblemInfo1.outputPath + file + "_long.xlsx"));
			XSSFSheet sheet = workbook.createSheet("Sheet1");
			XSSFRow row1 = sheet.createRow(0);
			row1.createCell(0).setCellValue(ZeusProblemInfo.getNumDepots()); 
			//row1.createCell(0).setCellValue(ProblemInfo1.numDepots); 
			
			// row 2 information, depot information(?)
			XSSFRow row2 = sheet.createRow(1);
			row2.createCell(0).setCellValue(mainDepots.getTOPHead().getNext().getNext().getDepotNum()); 
			row2.createCell(1).setCellValue(mainDepots.getTOPHead().getNext().getXCoord()); // depot location x
			row2.createCell(2).setCellValue(mainDepots.getTOPHead().getNext().getYCoord()); // depot location y
			row2.createCell(3).setCellValue(mainDepots.getAttributes().getTotalDemand());
			row2.createCell(4).setCellValue(mainDepots.getAttributes().getTotalDistance());
			row2.createCell(5).setCellValue(ZeusProblemInfo.getNoOfVehs());
			//row2.createCell(5).setCellValue(ProblemInfo1.noOfVehs);
			
			// row 3 information, truck information(?)
			XSSFRow row3 = sheet.createRow(2);
			row3.createCell(0).setCellValue(mainDepots.getTOPHead().getNext().getMainTrucks().getHead().getNext().getTruckNum()); // what is this
			row3.createCell(1).setCellValue(mainDepots.getNumTrucksUsed());
			row3.createCell(2).setCellValue(mainDepots.getHead().getNext().getMainTrucks().getHead().getNext().getAttributes().getTotalDemand());
			row3.createCell(3).setCellValue(mainDepots.getAttributes().getTotalCost());
			row3.createCell(4).setCellValue(mainDepots.getHead().getNext().getMainTrucks().getHead().getNext().getTruckType().getMaxDuration());
			row3.createCell(5).setCellValue(mainDepots.getHead().getNext().getMainTrucks().getHead().getNext().getTruckType().getMaxCapacity());
			row3.createCell(6).setCellValue(ZeusProblemInfo.getNoOfShips());
			//row3.createCell(6).setCellValue(ProblemInfo1.noOfShips);
			
			int curRow = 3;
			Depot depotHead = mainDepots.getHead();
			Depot depotTail = mainDepots.getTail();
			
			// depots
			while (depotHead != depotTail) {
				Truck truckHead = depotHead.getMainTrucks().getHead();
				Truck truckTail = depotHead.getMainTrucks().getTail();
				
				// trucks
				while (truckHead != truckTail) {
					Nodes nodesHead = truckHead.getMainNodes().getHead().getNext();
					Nodes nodesTail = truckHead.getMainNodes().getTail();
					
					// nodes
					while (nodesHead != nodesTail) {
						XSSFRow newRow = sheet.createRow(curRow);
						int isAssigned = (nodesHead) != null ? 1 : 0;
						newRow.createCell(0).setCellValue(nodesHead.getIndex());
						newRow.createCell(1).setCellValue(nodesHead.getShipment().getDemand());
						newRow.createCell(2).setCellValue(nodesHead.getShipment().getXCoord());
						newRow.createCell(3).setCellValue(nodesHead.getShipment().getYCoord());
						newRow.createCell(4).setCellValue(isAssigned);
						curRow++;
						nodesHead = nodesHead.getNext();
					}
				truckHead = truckHead.getNext();
				}
			depotHead = depotHead.getNext();
			}
		workbook.write(fos);
		fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}	
	}
	

	
	//  Will write a long detailed solution for the problem
	//  @param file name of the file to write to
	 
	public void writeLongSolutionTxt(String file) {
		try {
			PrintStream ps = new PrintStream(new FileOutputStream(ZeusProblemInfo.getOutputPath() + file + "_long.txt"));
			//PrintStream ps = new PrintStream(new FileOutputStream(ProblemInfo1.
			//		outputPath + file + "_long.txt"));
			mainDepots.printDepotLinkedList(ps);
		}
		catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}


	// Will write a short solution for the problem
	// @param file name of the file to write to
	 
	public void writeShortSolutionTxt(String file) {
		try {
			//PrintStream ps = new PrintStream(new FileOutputStream(ProblemInfo.
			//outputPath + "/" + file + "_short.txt"));
			PrintStream ps = new PrintStream(new FileOutputStream(ZeusProblemInfo.getOutputPath() + file + "_short.txt"));
			//PrintStream ps = new PrintStream(new FileOutputStream(ProblemInfo1.
					//outputPath + file + "_short.txt"));

			ps.println("File: " + file + " Num Depots: " +
					ZeusProblemInfo.getNumDepots() + " Num Pick Up Points: " +
					ZeusProblemInfo.getNumCustomers() + " Num Trucks: " +
					ZeusProblemInfo.getNumTrucks()+ " Processing Time: " +
					//ProblemInfo1.numDepots + " Num Pick Up Points: " +
					//ProblemInfo1.numCustomers + " Num Trucks: " +
					//ProblemInfo1.numTrucks + " Processing Time: " +
					(endTime - startTime) / 1000 + " seconds");
			ps.println(mainDepots.getAttributes().toDetailedString());
			ps.println();

			Depot depotHead = mainDepots.getHead();
			Depot depotTail = mainDepots.getTail();

			while (depotHead != depotTail) {
				Truck truckHead = depotHead.getMainTrucks().getHead();
				Truck truckTail = depotHead.getMainTrucks().getTail();

				while (truckHead != truckTail) {
					ps.print("Truck #" + truckHead.getTruckNum() + " MaxCap: " +
							truckHead.getTruckType().getMaxCapacity() + " Demand: " +
							truckHead.getAttributes().getTotalDemand() + " ROUTE:");

					Nodes nodesHead = truckHead.getMainNodes().getHead();
					Nodes nodesTail = truckHead.getMainNodes().getTail();

					while (nodesHead != nodesTail) {
						ps.print(nodesHead.getIndex() + " ");
						nodesHead = nodesHead.getNext();
					}

					ps.println();
					truckHead = truckHead.getNext();
				}

				ps.println();
				ps.println();
				depotHead = depotHead.getNext();
			}
			for (int i = 0; i < optInformation.size(); i++) {
				ps.println(optInformation.elementAt(i));
			}
		}
		catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}
	
	
	 // Create a excel file to put in the final solutions
	 
	
	public static void createFinalExcelFile(int numSolution){
		  
		  
		  int rowCounter = 0;
		  int curCol = 0;	//column counter
		  XSSFWorkbook workbook = new XSSFWorkbook(); // create a book
		  XSSFSheet sheet = workbook.createSheet("Sheet1");// create a sheet
		  XSSFRow curRow = sheet.createRow(rowCounter++); // create a row
		  
		  //fill the first row of excel, Solution numbers
		  curCol = 2;
		  for(int i=0; i<numSolution; i++){
			  curRow.createCell(curCol).setCellValue("Solution " + (i));
			  curCol = curCol+4;		  
		  }
		  
		  curRow = sheet.createRow(rowCounter++); // create a row
		  curCol = 0;
		  curRow.createCell(curCol++).setCellValue("Problem");
		  curRow.createCell(curCol++).setCellValue("Best Known Solution");	  
		  for(int i=0; i<numSolution; i++){
			  curRow.createCell(curCol++).setCellValue("Num of Vehicle");
			  curRow.createCell(curCol++).setCellValue("Distance");
			  curRow.createCell(curCol++).setCellValue("Cost");
			  curRow.createCell(curCol++).setCellValue("Percentage");		  	  
		  }
		  	  
	      //save the file
	      try 
	      {
	    	  FileOutputStream fout = new FileOutputStream(new File(ZeusProblemInfo.getOutputPath() + "Comparison.xlsx"));
	    	  //FileOutputStream fout = new FileOutputStream(new File(ProblemInfo1.outputPath + "Comparison.xlsx"));
	    	  workbook.write(fout); 
	          fout.close();
	      } 
	      catch (Exception e) 
	      { 
	          e.printStackTrace(); 
	      } 
		  
	  }
  

  /**
   * Runs optmizations inserted into the mainOpts vector
   */
  public void runOptimizations() {
    TOPOptEntry opt;

    for (int i = 0; i < mainOpts.size(); i++) {
      opt = mainOpts.get(i);
      Settings.printDebug(Settings.COMMENT, "Running " + opt);

      optInformation.add(opt.toString() + " " + opt.run(mainDepots));

      Settings.printDebug(Settings.COMMENT, opt.toString() + " Stats: " + mainDepots.getTeamSolutionString());
    }
  }

  /**
   * Runs a series of tests to ensure that the solution is feasible
   */
  public void maintainFeasibility() {

    TOPTruck currentTruck = mainDepots.getStartDepot().getTOPMainTrucks().getTOPHead();

    TOPNodesLinkedList nList = null;
    TOPNodes n1 = null;
    TOPNodes n2 = null;
    TOPNodes n3 = null;

    // If the depots are further apart than the maximum travel distance, the
    // single truck will have a scoreless, yet unfeasible route.  In this case,
    // we merely loop through the trucks.
    while (currentTruck != null) {
      nList = currentTruck.getTOPMainNodes();
      if (nList != null) {
	n1 = nList.getTOPHead();
	n2 = nList.getTOPTail();
	n3 = n1.getTOPNext();

	if (n2 == null || n3 == null) {
	  currentTruck = currentTruck.getTOPNext();
	}
	else if (n2 == n3) {
	  currentTruck = currentTruck.getTOPNext();
	}
	else {
	  break;
	}
      }
    }

    while (currentTruck != null) {
      while (currentTruck.getTOPMainNodes().getTOPFeasibility().isPostOptFeasible() == false) {
	currentTruck.getTOPMainNodes().removeLowestScoringShipment();
	if (currentTruck.getTOPMainNodes() == null) {
	  break;
	}
	if (currentTruck.getTOPMainNodes().getTOPFeasibility() == null) {
	  break;
	}
      }

      currentTruck = currentTruck.getTOPNext();
    }
  }

  /**
   * Return the fitness for the GA.
   * @return double
   * Added by Pete Schallot
   */
  public double getTotalFitness() {
    double demand = TOPProblemInfo.getTOPDepotLLCostFunctions().getTeamTotalDemand(this.mainDepots);
    double distance = TOPProblemInfo.getTOPDepotLLCostFunctions().getTeamTotalDistance(this.mainDepots);

    if (distance == 0 || demand == 0) {
      return 0;
    }
    else {
      return demand + demand / (distance + demand);
    }
  }

  /**
   * Return the total score.
   * @return double
   */
  public double getTotalScore() {
    return TOPProblemInfo.getTOPDepotLLCostFunctions().getTeamTotalDemand(this.mainDepots);
  }

  /**
   * Opens a Zeus GUI window, displaying the current problem
   * Added by David Crissman
   */
  public void displayGUI() {
    TOPGui guiPost = new TOPGui(mainDepots, mainShipments);
  }
  
  

  
  
} //End of TOP file
