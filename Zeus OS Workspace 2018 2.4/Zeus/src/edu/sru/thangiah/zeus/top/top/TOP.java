package edu.sru.thangiah.zeus.top;

import java.io.*;
import java.util.*;
import edu.sru.thangiah.zeus.top.topgui.*;
import edu.sru.thangiah.zeus.top.topqualityassurance.*;
import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.localopts.*;
import edu.sru.thangiah.zeus.localopts.interopts.FirstFirstInterSearch;
import edu.sru.thangiah.zeus.localopts.intraopts.*;
import edu.sru.thangiah.zeus.top.*;
import edu.sru.thangiah.zeus.top.TOPProblemInfo.TOPCostType;
import edu.sru.thangiah.zeus.localopts.interopts.*;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
//For Excel file
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



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
 
  TOPData data = new TOPData(0, 0, 0, 0, 0, new Vector(), new Vector(),
			new TOPShipmentLinkedList(), new TOPDepotLinkedList());
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

    int dataSet = 7;
    int truckCount = 4;
    char constraint = 't';
    boolean routeToOriginal = false;

    String outFile = "TOP" + dataSet + "." + truckCount + "." + constraint;
    String constraintFile = "constraints\\TOP" + dataSet + "." + truckCount + "People.xlsx";
    dataFile = fileName;
    mainOpts = new Vector();
    optInformation = new Vector<String>();
    mainShipments = new TOPShipmentLinkedList();
    mainDepots = new TOPDepotLinkedList();

    //read in the TOP data
    //readDataFromFile(TOPProblemInfo.inputPath + dataFile);
    readShipmentsDataFromExcelFile(ZeusProblemInfo.getInputPath() + dataFile, ZeusProblemInfo.getInputPath() + constraintFile, constraint, truckCount, routeToOriginal);
    Settings.printDebug(Settings.COMMENT, "Read Data File: " + TOPProblemInfo.inputPath + dataFile);
    //writeDataFile(dataFile.substring(dataFile.lastIndexOf("/") + 1));
    writeDataToFileExcel(outFile);

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
      if (TOPProblemInfo.enableOptimizations == true) {
        //mainOpts.add(new TOPOptEntry(new FirstFirstInterSearch(new Exchange01()), TOPProblemInfo.TOPCostType.SCORE_INVERSE));
        //mainOpts.add(new TOPOptEntry(new FirstFirstInterSearch(new Exchange01()), TOPCostType.SCORE_INVERSE));

        //mainOpts.add(new TOPOptEntry(new FirstFirstIntraSearch(new TwoOpt()), TOPProblemInfo.TOPCostType.DISTANCE_PLUS_DISTANCE_TIMES_SCORE_INVERSE));
       // mainOpts.add(new TOPOptEntry(new FirstFirstIntraSearch(new TwoOpt()), TOPProblemInfo.TOPCostType.DISTANCE_PLUS_DISTANCE_TIMES_SCORE_INVERSE));
       // mainOpts.add(new TOPOptEntry(new FirstFirstIntraSearch(new TwoOpt()), TOPProblemInfo.TOPCostType.DISTANCE_PLUS_DISTANCE_TIMES_SCORE_INVERSE));
        //mainOpts.add(new TOPOptEntry(new TOPReduction(), TOPProblemInfo.TOPCostType.DISTANCE_PLUS_DISTANCE_TIMES_SCORE_INVERSE));
      //  mainOpts.add(new TOPOptEntry(new TOPReduction(), TOPProblemInfo.TOPCostType.DISTANCE_PLUS_DISTANCE_TIMES_SCORE_INVERSE));
      //  mainOpts.add(new TOPOptEntry(new FirstFirstIntraSearch(new OneOpt()), TOPProblemInfo.TOPCostType.DISTANCE_PLUS_DISTANCE_TIMES_SCORE_INVERSE));
      //  mainOpts.add(new TOPOptEntry(new FirstFirstIntraSearch(new TwoOpt()), TOPProblemInfo.TOPCostType.DISTANCE_PLUS_DISTANCE_TIMES_SCORE_INVERSE));

      }

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
      //writeLongSolution(dataFile.substring(dataFile.lastIndexOf("/") + 1));
      //writeShortSolution(dataFile.substring(dataFile.lastIndexOf("/") + 1));
      
      //Write solution files with Excel
      writeLongSolutionExcel(outFile);
      writeShortSolutionExcel(outFile);
      writeComparisonExcel(dataSet, truckCount, constraint);

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

  /**
   * Write out the data file that was read in
   * @param file
   */

  public void writeDataFile(String file) {
    try {
      PrintStream ps = new PrintStream(new FileOutputStream(file +
	  "_students.txt"));
      mainShipments.writeTOPShipments(ps);
    }
    catch (IOException ioex) {
      ioex.printStackTrace();
    }
  }
  
  public void writeDataToFileExcel(String file){

		data.mainShipments.writeTOPShipmentsExcel(ZeusProblemInfo.getOutputPath() +file +"_students.xlsx");

	}
  
	/**
	 * Writes out comparison file using POI implementation
	 * @param dataSet
	 * @param truckCount
	 * @param constraint
	 */
	public void writeComparisonExcel(int dataSet, int truckCount, int constraint) 
	{
		String selectShipType = ZeusProblemInfo.getSelectShipType().toString();
		selectShipType = selectShipType.substring(selectShipType.lastIndexOf('.')+1, selectShipType.lastIndexOf('@'));
		System.out.println("Problem Type " + selectShipType);
		
		String file = ZeusProblemInfo.getOutputPath() + "TOP_comparison_" + selectShipType + ".xlsx";		
		try
		{
			
			
			
			FileInputStream dataInputStream = new FileInputStream(file);
			XSSFWorkbook dataWorkbook = new XSSFWorkbook(dataInputStream);
			XSSFSheet dataWorksheet = dataWorkbook.getSheetAt(0);
			
			
			Cell cell = dataWorksheet.getRow(((dataSet-1)*3) + truckCount-1).getCell(Character.getNumericValue(constraint) - 9);
			cell.setCellValue(data.mainDepots.getTOPHead().getNext().getAttributes().getTotalDemand());
			
			
			FileOutputStream fileOut = new FileOutputStream(file);
			dataWorkbook.write(fileOut);	
			
			
			
			//workbook.write(fileOut);
			System.out.println("Long Excel File Outputed");
		}
		catch (FileNotFoundException  e) 
		{
			//Write file starter
			try
			{
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet worksheet1 = workbook.createSheet("Timings");
				XSSFSheet worksheet2 = workbook.createSheet("Best_Known");
				XSSFSheet worksheet3 = workbook.createSheet("Comparison");

				int k = 0;
				for(int i = 0; i < 22; i++)
				{
					Row row = worksheet1.createRow(i);
					if(i == 0)
					{
						for(int j = 0; j < 26; j++)
						{
							row.createCell(j).setCellValue(String.valueOf((char)(j + 96)));
						}
					}
					else
					{
						if((i-1)%3 == 0)
						{
							k++;
						}
						for(int j = 0; j < 26; j++)
						{
							if(j == 0)
							{
								row.createCell(j).setCellValue("Data Set " + k + "." + (((i-1)%3) + 2));
							}
							else
							{
								row.createCell(j).setCellValue("Not Run");
							}
						}
						
					}
				}
				
				k = 0;
				for(int i = 0; i < 22; i++)
				{
					Row row = worksheet2.createRow(i);
					if(i == 0)
					{
						for(int j = 0; j < 26; j++)
						{
							row.createCell(j).setCellValue(String.valueOf((char)(j + 96)));
						}
					}
					else
					{
						if((i-1)%3 == 0)
						{
							k++;
						}
						for(int j = 0; j < 26; j++)
						{
							if(j == 0)
							{
								row.createCell(j).setCellValue("Data Set " + k + "." + (((i-1)%3) + 2));
							}
							else
							{
								try
								{

									String knownBestFile = ZeusProblemInfo.getInputPath() + "knownBest.xlsx";							
										
									FileInputStream knownBestFileStream = new FileInputStream(knownBestFile);
									XSSFWorkbook knownBestFileWorkbook = new XSSFWorkbook(knownBestFileStream);
									XSSFSheet knownBestFileWorksheet = knownBestFileWorkbook.getSheetAt(0);
									

									row.createCell(j).setCellValue(knownBestFileWorksheet.getRow(i).getCell(j).toString());
									
								}
								catch (Exception exception)
								{
									exception.printStackTrace();
								}
							}
						}
						
					}
				}
				
				k = 0;
				for(int i = 0; i < 22; i++)
				{
					Row row = worksheet3.createRow(i);
					if(i == 0)
					{
						for(int j = 0; j < 26; j++)
						{
							if(j == 0)
							{
								row.createCell(j).setCellValue("All Values in %");
							}
							else
							{
								row.createCell(j).setCellValue(String.valueOf((char)(j + 96)));
							}
						}
					}
					else
					{
						if((i-1)%3 == 0)
						{
							k++;
						}
						for(int j = 0; j < 26; j++)
						{
							if(j == 0)
							{
								row.createCell(j).setCellValue("Data Set " + k + "." + (((i-1)%3) + 2));
							}
							else
							{
								String cellLocation = String.valueOf((char)(j + 97)) + (i + 1);
								row.createCell(j).setCellType(Cell.CELL_TYPE_FORMULA);
								row.createCell(j).setCellFormula("(Timings!" + cellLocation + "/Best_Known!" + cellLocation +")*100");
								//=(Timings!B2/Best_Known!B2)*100
							}
						}
						
					}
				}
				
				FileOutputStream fileOut = new FileOutputStream(file);
				workbook.write(fileOut);	
				
				writeComparisonExcel(dataSet, truckCount, constraint);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
	}
	}
  

  /**
   * Will write a long detailed solution for the problem
   * @param file - Name of the file to write to
   */
  public void writeLongSolution(String file) {
    try {
      PrintStream ps = new PrintStream(new FileOutputStream(
	  TOPProblemInfo.outputPath + file + "_long.txt"));
      mainDepots.printDepotLinkedList(ps);
    }
    catch (IOException ioex) {
      ioex.printStackTrace();
    }
  }
  
	/**
	 * Writes out long solution excel file using POI
	 * @param file
	 */
	public void writeLongSolutionExcel(String file) 
	{

		String selectShipType = ZeusProblemInfo.getSelectShipType().toString();
		selectShipType = selectShipType.substring(selectShipType.lastIndexOf('.')+1, selectShipType.lastIndexOf('@'));
		System.out.println("Problem Type " + selectShipType);
		
		String insertShipType = ZeusProblemInfo.getInsertShipType().toString();
		insertShipType = insertShipType.substring(insertShipType.lastIndexOf('.')+1, insertShipType.lastIndexOf('@'));
		System.out.println("Problem Type " + insertShipType);
		

		
		try {
			//create our POI objects
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Sheet1");
			
			
			int i = 0;
			Row row = worksheet.createRow(i);
			row.createCell(0).setCellValue(ZeusProblemInfo.getNumDepots());

			//export data to excel file
			Depot depot = data.mainDepots.getHead().getNext();
			while (depot != data.mainDepots.getTail()) {
				i++;
				row = worksheet.createRow(i);
				
				//export depot data
				row.createCell(0).setCellValue("Depot #" + depot.getDepotNum());
				row.createCell(1).setCellValue("Depot X: " + depot.getXCoord());
				row.createCell(2).setCellValue("Depot Y: " + depot.getYCoord());
				row.createCell(3).setCellValue("Depot Total Demand: " + depot.getAttributes().getTotalDemand());
				row.createCell(4).setCellValue("Depot Total Distance: " + depot.getAttributes().getTotalDistance());
				row.createCell(5).setCellValue("Depot Total Trucks: " + depot.getMainTrucks().getSize());

				Truck truck = depot.getMainTrucks().getHead().getNext();
				Truck truckTail = depot.getMainTrucks().getTail();

				while (truck != truckTail) {
					i++;
					row = worksheet.createRow(i);
					
					//export truck data
					row.createCell(0).setCellValue("Truck #" + truck.getTruckNum());
					row.createCell(1).setCellValue("Truck Max Distance: " + truck.getTruckType().getMaxDuration());
					row.createCell(2).setCellValue("Truck Node Count: " + (truck.getMainNodes().getSize()));
					row.createCell(3).setCellValue("Truck Total Demand: " + truck.getAttributes().getTotalDemand());
					row.createCell(4).setCellValue("Truck Total Distance: " + truck.getAttributes().getTotalDistance());


					Nodes cell = truck.getMainNodes().getHead().getNext();
					Nodes cellTail = truck.getMainNodes().getTail();

					i++;
					row = worksheet.createRow(i);
					
					//export shipment data
					row.createCell(0).setCellValue("Shipment #");
					row.createCell(1).setCellValue("Shipment Demand: ");
					row.createCell(2).setCellValue("Shipment X: ");
					row.createCell(3).setCellValue("Shipment Y: ");
					
					while (cell != cellTail) {
						i++;
						row = worksheet.createRow(i);
						
						//export shipment data
						row.createCell(0).setCellValue(cell.getIndex());
						row.createCell(1).setCellValue(cell.getDemand());
						row.createCell(2).setCellValue(cell.getShipment().getXCoord());
						row.createCell(3).setCellValue(cell.getShipment().getYCoord());
						cell = cell.getNext();
					}

					truck = truck.getNext();
				}

				depot = depot.getNext();
			}

			file = ZeusProblemInfo.getOutputPath() +file +"_long_" + selectShipType + ".xlsx";
			
			FileOutputStream fileOut = new FileOutputStream(file);
			workbook.write(fileOut);
			System.out.println("Long Excel File Outputed");
		}
		catch (Exception e) {
			System.out.println("Error in printDepotLinkedList"+e);
			e.printStackTrace();
		}


	}
  

  /**
   * Write out both the long and short solution files.
   */
  public void writeAllSolutionFiles() {
    writeDataFile(this.dataFile);
    writeLongSolution(this.dataFile);
    writeShortSolution(this.dataFile);
  }

  /**
   * Will write a short solution for the problem
   * @param file - Name of the file to write to
   */
  public void writeShortSolution(String file) {
    double totalScore = TOPProblemInfo.getTOPDepotLLCostFunctions().getTeamTotalDemand(mainDepots); //getTeamScore();
    try {
      PrintStream ps = new PrintStream(new FileOutputStream(
	  TOPProblemInfo.outputPath + file + "_short.txt"));
      TOPDepot d = (TOPDepot)mainDepots.getHead();

      ps.println("File: " + file + " Num Depots: "
		 + TOPProblemInfo.numDepots + " Num Pick Up Points: "
		 + TOPProblemInfo.numCustomers + " Num Trucks: "
		 + TOPProblemInfo.numTrucks + " Truck Max Travel Time: "
		 + TOPProblemInfo.truckMaxTravelTime + " Processing Time: "
		 + (endTime - startTime) / 1000 + " seconds");
      ps.println(mainDepots.getAttributes().toDetailedString());
      ps.println("Total Score: " + totalScore);

      ps.println();

      while (d != null) {
	TOPTruck t = (TOPTruck)d.getMainTrucks().getHead();

	while (t != null) {
	  ps.print("Truck #" + t.getTruckNum() + " MaxCap: "
		   + t.getTruckType().getMaxCapacity() + " Demand: "
		   + t.getAttributes().getTotalDemand() + " ROUTE:");
	  Nodes p = t.getMainNodes().getHead();

	  while (p != null) {
	    ps.print(p.getIndex() + " ");
	    p = p.getNext();
	  }

	  if (t.getIsTeamMember()) {
	    ps.print(" (TEAM MEMBER)");
	  }
	  else {
	    ps.print(" (NOT ON TEAM)");
	  }
	  ps.println();
	  t = (TOPTruck)t.getNext();
	}

	ps.println();
	ps.println();
	d = (TOPDepot)d.getNext();
      }
      for (int i = 0; i < optInformation.size(); i++) {
	ps.println(optInformation.elementAt(i));
      }
    }
    catch (IOException ioex) {
      ioex.printStackTrace();
    }
  }
  
  /**
   * Writes out short solution to excel file using POI
   * @param file
   */
  public void writeShortSolutionExcel(String file) {
  	//PrintStream ps = new PrintStream(new FileOutputStream(ProblemInfo.
  	//outputPath + "/" + file + "_short.txt"));

  	try {
  		XSSFWorkbook workbook = new XSSFWorkbook();
  		XSSFSheet worksheet = workbook.createSheet("Sheet1");
  		
  		

  		String selectShipType = ZeusProblemInfo.getSelectShipType().toString();
  		selectShipType = selectShipType.substring(selectShipType.lastIndexOf('.')+1, selectShipType.lastIndexOf('@'));
  		System.out.println("Problem Type " + selectShipType);
  		
  		String insertShipType = ZeusProblemInfo.getInsertShipType().toString();
  		insertShipType = insertShipType.substring(insertShipType.lastIndexOf('.')+1, insertShipType.lastIndexOf('@'));
  		System.out.println("Problem Type " + insertShipType);
  		
  		
  		int i = 0;
  		Row row = worksheet.createRow(i);

  		row.createCell(0).setCellValue("File: ");
  		row.createCell(1).setCellValue(file);
  		i++;
  		row = worksheet.createRow(i);
  		
  		row.createCell(0).setCellValue("Selection Type: ");
  		row.createCell(1).setCellValue(selectShipType);
  		i++;
  		row = worksheet.createRow(i);
  		
  		row.createCell(0).setCellValue("Insertion Type: ");
  		row.createCell(1).setCellValue(insertShipType);		
  		i++;
  		row = worksheet.createRow(i);
  		i++;
  		row = worksheet.createRow(i);
  		
  		row.createCell(0).setCellValue("Truck");
  		row.createCell(1).setCellValue("Distance");
  		row.createCell(2).setCellValue("Demand");
  		row.createCell(3).setCellValue("Used");
  		row.createCell(4).setCellValue("Route");
  		
  		
  		TOPDepot depotHead = data.mainDepots.getTOPHead();
  		Depot depotTail = data.mainDepots.getTail();
  		while (depotHead != depotTail) {
  			i++;
  			row = worksheet.createRow(i);
  			//TOPTruck truckHead = depotHead.getMainTrucks().getHead().getTOPNext();
  			TOPTruck truckHead = (TOPTruck) depotHead.getMainTrucks().getHead().getNext();
  			//TOPTruck truckTail = depotHead.getMainTrucks().getTail();
  			TOPTruck truckTail = (TOPTruck) depotHead.getMainTrucks().getTail();

  			while (truckHead != truckTail) 
  			{
  				row = worksheet.createRow(i);
  				
  				row.createCell(0).setCellValue(truckHead.getTruckNum());
  				row.createCell(1).setCellValue(truckHead.getAttributes().getTotalDistance());
  				row.createCell(2).setCellValue(truckHead.getAttributes().getTotalDemand());
  				//row.createCell(3).setCellValue(truckHead.getIsUsed());
  				row.createCell(3).setCellValue(truckHead.getIsUsed());
  				
  				Nodes nodesHead = truckHead.getMainNodes().getHead();
  				Nodes nodesTail = truckHead.getMainNodes().getTail().getNext();

  				int j = 0;
  				while (nodesHead != nodesTail) {
  					row.createCell(j+4).setCellValue(nodesHead.getIndex());
  					nodesHead = nodesHead.getNext();
  					j++;
  				}

  				i++;
  				truckHead = truckHead.getTOPNext();
  			}

  			//depotHead = depotHead.getNext();
  			depotHead = (TOPDepot) depotHead.getNext();
  		}
  		i++;
  		row = worksheet.createRow(i);
  		for (int s = 0; s < data.optInformation.size(); s++) {
  			row.createCell(s).setCellValue(data.optInformation.elementAt(s).toString());
  		}
  		
  		file = ZeusProblemInfo.getOutputPath() + file + "_short_" + selectShipType + ".xlsx";
  		FileOutputStream fileOut = new FileOutputStream(file);
  		workbook.write(fileOut);
  		System.out.println("Short Excel File Outputed");
  	}
  	catch (IOException ioex) {
  		ioex.printStackTrace();
  	}
  }

  /**
   * Runs optmizations inserted into the mainOpts vector
   */
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

			
			Vector custTypes = new Vector();
			//Obtain the different customer types
			for (int ct = 0; ct < 1; ct++) {
				custTypes.add(new Integer(1));
			}	 

			FileInputStream dataInputStream = new FileInputStream(dataFile);
			XSSFWorkbook dataWorkbook = new XSSFWorkbook(dataInputStream);
			XSSFSheet dataWworksheet = dataWorkbook.getSheetAt(0);
			
			for (Iterator<Row> rowIt = dataWworksheet.rowIterator(); rowIt.hasNext(); )
			{
				try
				{
				Row row = rowIt.next();
				Cell cellA1 = row.getCell(0);
				int i = (int)cellA1.getNumericCellValue();
				Cell cellB1 = row.getCell(1);
				float  x = (float)cellB1.getNumericCellValue();
				Cell cellC1 = row.getCell(2);
				float y = (float)cellC1.getNumericCellValue();
				Cell cellD1 = row.getCell(3);
				int s = (int)cellD1.getNumericCellValue();
				
				
				System.out.print(i + " " + x + " " + y + " " + s + "\n");

				int list[] = new int[ZeusProblemInfo.getMaxCombinations()];		    
				int currentComb[][] = new int[ZeusProblemInfo.getMaxHorizon()][ZeusProblemInfo.getMaxCombinations()];	

				Integer custType = (Integer) custTypes.elementAt(0);
				if(i != 0 && s != 0) //May not need s check 
				{
					data.mainShipments.insertShipment(i, x, y, s, custType.toString(),
							list, currentComb);		
				}
				else
				{
					if(firstDepot == null)
					{
						firstDepot = new TOPDepot(i, x, y); //n is the number of customers
						data.mainDepots.insertDepotLast(firstDepot);
					}
					else if (secondDepot == null && !routeToOriginal)
					{
						secondDepot = new TOPDepot(i, x, y); //n is the number of customers
						data.mainDepots.insertDepotLast(secondDepot);
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

			FileInputStream constraintInputStream = new FileInputStream(constraintFile);
			XSSFWorkbook constraintWorkbook = new XSSFWorkbook(constraintInputStream);
			XSSFSheet constraintWorksheet = constraintWorkbook.getSheetAt(0);

			Row dataRow = constraintWorksheet.getRow(Character.getNumericValue(constraint) - charOffset); //Get what number of the alphabet it is
			Cell dataCell = dataRow.getCell(1);
			data.D = (float)dataCell.getNumericCellValue();
			//data.D = (int)(data.L*1.714);
			System.out.println(data.L);

			constraintInputStream.close();

			if(firstDepot == null)
			{
				firstDepot = new TOPDepot(0, 0, 0); //n is the number of customers
				data.mainDepots.insertDepotLast(firstDepot);
			}
			if(vehicleCount == 0)
			{
				vehicleCount = 2;
			}
			if (data.Q == 0) { //if there is no maximum capacity, set it to a very large number
				data.Q = 10000000;
			}


			//********************************
			//@TODO: SET TO BE BASED ON TIME
			//********************************
			if (data.D == 0) { //if there is no travel time, set it to a very large number
				data.D = 10000000; //if there is not maximum distance, set it to a very large number
				//ProblemInfo.maxCapacity = Q;  //maximum capacity of a vehicle
				//ProblemInfo.maxDistance = D;  //maximum distance of a vehicle
			}


			//Put the problem information into the ProblemInfo class
			//set the problem info for the problem
			ZeusProblemInfo.setNumDepots(1); //Set the number of depots to 1 for this problem
			TOPProblemInfo.fileName = dataFile; //name of the file being read in
			ZeusProblemInfo.setProbType(0); //problem type
			ZeusProblemInfo.setNoOfVehs(vehicleCount); //number of vehicles
			ZeusProblemInfo.setNoOfShips(numLocations); //number of locations

			/** @todo  There three variables need to be defined at the beginning of
			 * the method */
			float maxCapacity = data.Q; //maximum capacity of a vehicle
			float maxDistance = data.D; //maximum distance of a vehicle

			String serviceType = "1";
			TOPTruckType truckType = new TOPTruckType(1, maxDistance,
					maxCapacity, serviceType);
			for(int t = 0; t < truckCount; t++)
			{
				ZeusProblemInfo.addTruckTypes(truckType);
			}
			//Each depot has a mainTrucks. The different truck types available are
			//inserted into the mainTrucks type. For the TOP, there is only one truck type
			firstDepot = (TOPDepot) data.mainDepots.getHead().getNext();
			secondDepot = (TOPDepot) firstDepot.getNext();
			
			for (int i = 0; i < ZeusProblemInfo.getTruckTypesSize(); i++) {
				TOPTruckType ttype = (TOPTruckType) ZeusProblemInfo.getTruckTypesAt(i);
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
		}  
	}

	/**
	 * Print  out the data to the console
	 */

	public void printDataToConsole() {
		try {
			//data.mainShipments.printTOPShipmentsToConsole();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
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
