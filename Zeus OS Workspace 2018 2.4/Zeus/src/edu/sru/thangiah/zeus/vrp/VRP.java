package edu.sru.thangiah.zeus.vrp;

import java.io.*;
import java.util.*;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.localopts.*;
import edu.sru.thangiah.zeus.localopts.interopts.*;
import edu.sru.thangiah.zeus.localopts.intraopts.*;

import edu.sru.thangiah.zeus.vrp.vrpqualityassurance.*;
import edu.sru.thangiah.zeus.gui.*;
import edu.sru.thangiah.zeus.localopts.OptInfo;
import edu.sru.thangiah.zeus.localopts.*;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


//For the Simulated Annealing metaheuristic
import edu.sru.thangiah.zeus.metaheuristics.simulatedannealing.*;

//import edu.sru.thangiah.zeus.metaheuristics.simulatedannealing.*;
//import edu.sru.thangiah.zeus.metaheuristics.tabu.*;
//import edu.sru.thangiah.zeus.qualityassurance.*;
//import edu.sru.thangiah.zeus.localopts.mixedfleet.*;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 * Changes: 9/6/2017 - Addition of readDataFromExcelFile() method
 */

public class VRP {

	int m = 0, //number of vehicles
			n = 0, //number of customers
			t = 0, //number of days(or depots)
			D = 0, //maximum duration of route
			Q = 0; //maximum capacity of vehicle

	long startTime, endTime; //track the CPU processing time
	private Vector mainOpts = new Vector(); //contains the collections of optimizations
	@SuppressWarnings("rawtypes")
	private Vector optInformation = new Vector(); //contains information about routes
	private VRPShipmentLinkedList mainShipments = new VRPShipmentLinkedList(); //customers read in from a file or database that are available
	private VRPDepotLinkedList mainDepots = new VRPDepotLinkedList(); //depots linked list for the VRP problem
	/* This needs to be looked at in how it defines the entire structure and how to clean out lists for DepotLL, TruckLL and NodesLL */
	private VRPQualityAssurance vrpQA; //check the integrity and quality of the solution

	//constructor for the class
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public VRP(String dataFile) {

		//Truck types are placed into a vector
		//AccessProblemInfo.setTruckTypes(new Vector()); //defined in AccessProblemInfo
		//ProblemInfo1.truckTypes = new Vector();

		//Type of shipment insterion to be performed
		//ProblemInfo.insertShipType = new Object();

		boolean isDiagnostic = false;
		Shipment tempShip;
		Depot thisDepot;
		int type;
		int depotNo;
		int countAssignLoop;
		boolean status;
		String outputFileName;

		/** @todo  Need to put in a VRP file and read in VRP data. The readfile method will have to be changed to match the format of the
		 * vrp file*/
		//read in the MDVRP data
		//readDataFromTextFile(ProblemInfo.inputPath + dataFile);
		readDataFromExcelFile(ZeusProblemInfo.getInputPath() + dataFile);
		//readDataFromExcelFile(ProblemInfo1.inputPath + dataFile);
		Settings.printDebug(Settings.COMMENT,
				"Read Data File: " +ZeusProblemInfo.getInputPath()+ dataFile);
		//Settings.printDebug(Settings.COMMENT,
		//		"Read Data File: " + ProblemInfo1.inputPath + dataFile);
		printDataToConsole();
		writeDataFile(dataFile.substring(dataFile.lastIndexOf("/") + 1));

		//Ensure that the shipment linked list has been loaded with the data
		if (mainShipments.getVRPHead() == null) {
			Settings.printDebug(Settings.ERROR,
					"VRP: Shipment linked list is empty");
		}

		//Set up the shipment selection type
		//ProblemInfo.selectShipType = new ClosestEuclideanDistToDepot();
		//Settings.printDebug(Settings.COMMENT,ClosestEuclideanDistToDepot.WhoAmI());
		ZeusProblemInfo.setSelectShipType(new SmallestPolarAngleToDepot());
		//ProblemInfo1.selectShipType = new SmallestPolarAngleToDepot();
		Settings.printDebug(Settings.COMMENT, SmallestPolarAngleToDepot.WhoAmI());
		//ProblemInfo.selectShipType = new SmallestPolarAngleShortestDistToDepot();
		//Settings.printDebug(Settings.COMMENT,SmallestPolarAngleShortestDistToDepot.WhoAmI());

		//set up the shipment insertion type
		ZeusProblemInfo.setInsertShipType(new LinearGreedyInsertShipment());
		//ProblemInfo1.insertShipType = new LinearGreedyInsertShipment();
		Settings.printDebug(Settings.COMMENT, LinearGreedyInsertShipment.WhoAmI());
		
		// Set up the distance matrix
		//ProblemInfo.distanceMatrix =
		
		//Set up the similarity matrix
		//ProblemInfo.similarityMatrix =

		//Capture the CPU time required for solving the problem
		startTime = System.currentTimeMillis();
		// captures the initial information on solving the problem
		// returns the total customer and total distance after the initial solution
		optInformation.add("Inital Solution " + createInitialRoutes());
		System.out.println("Completed initial routes");

		//Get the initial solution
		//Depending on the Settings status, display information on the routes
		//Trucks used, total demand, dist, travel time and cost
		Settings.printDebug(Settings.COMMENT, "Created Initial Routes ");
		Settings.printDebug(Settings.COMMENT,
				"Initial Stats: " + mainDepots.getSolutionString());
		//At this point all shipments have been assigned
		writeLongSolutionTxt(dataFile.substring(dataFile.lastIndexOf("/") + 1));
		//writeShortSolution(dataFile.substring(dataFile.lastIndexOf("/") + 1));

		//create a vector of search strategy/optimizations to execute
		mainOpts = new Vector(1); //vector capacity of 1
		//sets the upperbound in LocalOneOpt and
		//sets the name of the search to Best
		//mainOpts.add(new FirstFirstIntraSearch(new ThreeOpt()));
		//mainOpts.add(new FirstFirstIntraSearch(new OneOpt()));
		//mainOpts.add(new FirstBestIntraSearch(new OneOpt(false)));
		//mainOpts.add(new BestBestIntraSearch(new OneOpt()));
		//mainOpts.add(new FirstFirstInterSearch(new Exchange01()));
		//mainOpts.add(new FirstFirstInterSearch(new Exchange10()));
		//mainOpts.add(new FirstFirstInterSearch(new Exchange11(false)));
		//mainOpts.add(new FirstBestInterSearch(new Exchange01()));
		//mainOpts.add(new FirstFirstInterSearch(new Exchange22()));
		//mainOpts.add(new FirstFirstInterSearch(new Exchange11()));
		//mainOpts.add(new FirstFirstInterSearch(new Exchange11()));
		//mainOpts.add(new FirstBestInterSearch(new Exchange11()));
		//mainOpts.add(new BestBestInterSearch(new Exchange11()));
		//mainOpts.add(new Combination1(ProblemInfo.truckTypes));
		//runOptimizations();
		

		//create a vector of search strategy/optimizations to execute
		//mainOpts = new Vector(1);
		//add a first-first local 1-opt
		//simAnnealOpts.add(new FirstBestIntraSearch(new LocalOneOpt()));
		//add an intra and inter-opt search strategy
		//mainOpts.add(new FirstBestIntraSearch(new OneOpt(false)));
		//mainOpts.add(new FirstFirstInterSearch(new Exchange11(false)));
		
		
		////////////////////////////////////////////////////////////////////////////
		///// This is an implementation of a simulated annealing metaheuristic /////
		////////////////////////////////////////////////////////////////////////////
		int initialTemp = 1000;                        //the inital temperature
		int finalTemp   = 0;                           //the final temperature
		int iterationsAtEachTemp = 50;                 //iterations at each temperature
		int numTemps = 100;                            //number of temperatures to look at

		//create a simulated annealing instance
		SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(initialTemp,
				finalTemp, iterationsAtEachTemp, numTemps);
		//set the cooling schedule you would like to use, in this case linear
		simulatedAnnealing.setCoolingSchedule(new LinearCoolingSchedule(
				simulatedAnnealing.getMaxIterations(),
				simulatedAnnealing.getInitTemperature(),
				simulatedAnnealing.getFinalTemperature()));

		System.out.println("Simulated Annealing Initiated");
		//now pass the opts and the depot linked list at it will be annealed the
		//return will be an optInfo class that contains the beginning and ending
		//stats
		OptInfo simAnnealResults = simulatedAnnealing.anneal(mainDepots,mainOpts);
		//print results
		System.out.println("Simulated Annealing Results: " +
				simAnnealResults.toString());

		
		
		
		
		
		
		
		//Write the long solution
		writeLongSolutionTxt(dataFile.substring(dataFile.lastIndexOf("/") + 2));

		//Check for the quality and integrity of the solution
		System.out.println("Starting QA");
		vrpQA = new VRPQualityAssurance(mainDepots, mainShipments);
		if (vrpQA.runQA() == false) {
			Settings.printDebug(Settings.ERROR, "QA FAILED!");
		}
		else {
			Settings.printDebug(Settings.COMMENT, "QA succeeded");


		}
		
		//Write the solution out to an excel file
		
		/** @todo  GUI still needs to be implemented */
		//Call to the graphical user inter face
		//Vector emptyVector = new Vector(0);
		//VRPZeusGui gui = new VRPZeusGui(mainDepots, mainShipments, emptyVector);

		ZeusGui guiPost = new ZeusGui(mainDepots, mainShipments);

	} 

	/**
	 * Creates the initial solution for the problem
	 */
	public OptInfo createInitialRoutes() {
		//OptInfo has old and new attributes
		OptInfo info = new OptInfo();
		VRPDepot currDepot = null; //current depot
		VRPShipment currShip = null; //current shipment
		//int countLoop=0;

		//check if selection and insertion type methods have been selected
		if (ZeusProblemInfo.getSelectShipType() == null) {
			Settings.printDebug(Settings.ERROR,
					"No selection shipment type has been assigned");

		}
		if (ZeusProblemInfo.getInsertShipType()== null) {
			Settings.printDebug(Settings.ERROR,
					"No insertion shipment type has been assigned");
		}

		//capture the old attributes
		info.setOldAttributes(mainDepots.getAttributes());
		/* Get the shipment that is closest to a depot with respect to a criteria
     The method for assigning customers to the depots is as follows. As there are
     a fixed number of depots, start with the first depot and find closest customer
     to the depot and insert that customer into a truck allocated to the depot if
     it does not exceed the constraints.  If it does, then create another truck
     from the same depot and add the customer to it.
     The next step is to go to the next depot and allocate the a customer closest
     to that depot to a truck. This process will loop through all the depots and
     sequentially allocate customers to the depot until all customers have been
     assigned to a depot.

     The class assignment to ProblemInfo.selectShipType determines the type of
     shipment insertion to be executed.
		 */

		//countLoop=1;
		while (!mainShipments.isAllShipsAssigned()) {
			double x, y;
			int i = 0;
			//Get the x an y coordinate of the depot
			//Then use those to get the customer, that has not been allocated,
			// that is closest to the depot
			currDepot = (VRPDepot) mainDepots.getVRPHead().getNext();
			x = currDepot.getXCoord();
			y = currDepot.getYCoord();
			//Send the entire mainDepots and mainShipments to get the next shipment
			//to be inserted including the current depot
			VRPShipment theShipment = mainShipments.getNextInsertShipment(mainDepots,
					currDepot, mainShipments, currShip);

			if (theShipment == null) { //shipment is null, print error message
				Settings.printDebug(Settings.COMMENT, "No shipment was selected");
			}
			//The selected shipment will be inserted into the route
			if (!mainDepots.insertShipment(theShipment)) {
				Settings.printDebug(Settings.COMMENT, "The Shipment: <" + theShipment.getIndex() +
						"> cannot be routed");
			}
			else {
				Settings.printDebug(Settings.COMMENT,
						"The Shipment: <" + theShipment.getIndex() +// " " + theShipment +
						"> was routed");
				//tag the shipment as being routed
				theShipment.setIsAssigned(true);
			}
			//System.out.println("Loop is "+countLoop++);
			//get the next shipment
			//theShipment = (VRPShipment) theShipment.getNext();? - Not needed
		}

		ZeusProblemInfo.getDepotLLLevelCostF().calculateTotalsStats(mainDepots);
		//ProblemInfo1.depotLLLevelCostF.calculateTotalsStats(mainDepots);
		info.setNewAtributes(mainDepots.getAttributes());
		return info;
	}

	//read in the data from the requested file in token format from a text file
	public int readDataFromTextFile(String VRPFileName) {
		// read in the MDVRP data from the listed file and load the information
		// into the availShipments linked list

		//type = 0 (MDVRP)
		//     = 1 (PTSP)
		//     = 2 (PVRP)
		char ch;
		String temp = "";
		int index = 0,
				j = 0,
				type = 0; //type
		//m        = 0,                           //number of vehicles
		//n        = 0,                           //number of customers
		//t        = 0,                           //number of days(or depots)
		//D        = 0,                           //maximum duration of route
		//Q        = 0;                           //maximum load of vehicle
		int p = 3; //Np neighborhood size

		int depotIndex;

		//Open the requested file
		FileInputStream fis;
		InputStreamReader isr;
		BufferedReader br;
		try {
			fis = new FileInputStream(VRPFileName);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
		}
		catch (Exception e) {
			System.out.println("File is not present");
			return 0;
		}

		//InitData             currentProb = new InitData();    //Class for initializing data

		//This section will get the initial information from the data file
		//Read in the first line from the file
		String readLn;
		StringTokenizer st;

		//This reads in the first line that is used to determine the total
		//numer of customers and type of problem
		//typePvrp is + type);
		//numVeh is         + m; //not really used in an MDVRP
		//numCust is        + n;
		//Days is            + t; //Depots in the case of MDVRP
		//Depot duration is + D;
		//capacity is       + Q;

		//read in the first line
		try {
			readLn = br.readLine();
			//print out the line that was read
			//System.out.println("This is s:" + s);

			st = new StringTokenizer(readLn);
			while (st.hasMoreTokens()) { //while there are more tokens
				//int shValue =  Integer.parseInt(st.nextToken());
				switch (index) {
				case 0:
					type = Integer.parseInt(st.nextToken());
					break;
				case 1:
					m = Integer.parseInt(st.nextToken());
					break;
				case 2:
					n = Integer.parseInt(st.nextToken());
					break;
				case 3:
					t = Integer.parseInt(st.nextToken());
					break;
				case 4:
					D = Integer.parseInt(st.nextToken());
					break;
				case 5:
					Q = Integer.parseInt(st.nextToken());
					break;
				} //end switch
				index += 1;
			} //end while
		}
		catch (Exception e) {
			System.out.println("Line could not be read in");
		}

		//Put the problem information into the ProblemInfo class
		//set the problem info for the problem
		ZeusProblemInfo.setNumDepots(1); 			  //Set the number of depots to 1 for this problem
		ZeusProblemInfo.setProbFileName(VRPFileName);   //name of the problem file being read in
		ZeusProblemInfo.setProbType(type);		  //problem type
		ZeusProblemInfo.setNoOfVehs(m);			  //number of vehicles
		ZeusProblemInfo.setNoOfShips(n);			  //number of shipments
		ZeusProblemInfo.setNoOfDays(t);			  //number of days (horizon) or number of depots for MDVRP
		
		//ProblemInfo1.numDepots = 1; //Set the number of depots to 1 for this problem
		//ProblemInfo1.fileName = VRPFileName; //name of the file being read in
		//ProblemInfo1.probType = type; //problem type
		//ProblemInfo1.noOfVehs = m; //number of vehicles
		//ProblemInfo1.noOfShips = n; //number of shipments
		//ProblemInfo1.noOfDays = t; //number of days (horizon) or number of depots for MDVRP
		if (Q == 0) { //if there is no maximum capacity, set it to a very large number
			Q = 999999999;
		}
		if (D == 0) { //if there is no travel time, set it to a very large number
			D = 999999999; //if there is not maximum distance, set it to a very large number
			//ProblemInfo.maxCapacity = Q;  //maximum capacity of a vehicle
			//ProblemInfo.maxDistance = D;  //maximum distance of a vehicle
		}
		/** @todo  There three variables need to be defined at the beginning of
		 * the method */
		float maxCapacity = Q; //maximum capacity of a vehicle
		float maxDistance = D; //maximum distance of a vehicle

		String serviceType = "1"; //serviceType is the trucktype. Should match with
		//required truck type
		//In some problems, different truck types might be present to solve
		//the problem. For this problem, we assume that there is only one
		//truck type that is available.
		//loop through each truck type and store each one in the vector
		int numTruckTypes = 1;
		for (int i = 0; i < numTruckTypes; i++) {
			VRPTruckType truckType = new VRPTruckType(i, maxDistance,
					maxCapacity, serviceType);
			ZeusProblemInfo.addTruckTypes(truckType);
			//ProblemInfo1.truckTypes.add(truckType);
		}

		//Some problems tend to have different customer types. In this problem
		//there is only one customter type. The integer value for the customer type
		//should match with the integer value for the truck type for the compatibiliy
		//check to work
		//read in the different customer types
		//Vector custTypes = new Vector();
		//Obtain the different customer types
		for (int ct = 0; ct < 1; ct++) {
			ZeusProblemInfo.addCustTypes(new Integer(1));
			//custTypes.add(new Integer(1));
		}

		//place the number of depots and number of shipments in the linked list instance
		//These no longer seem to be needed for the shipment linked list. The total number of
		//shipments are tallied when they are inserted into the linked list
		//mainShipments.numShipments = n;
		//mainShipments.noDepots = t;
		//mainShipments.maxCapacity = Q;
		//mainShipments.maxDuration = D ;

		//display the information from the first line
		//System.out.println("typePvrp is       " + type);
		//System.out.println("numVeh is         " + m);
		//System.out.println("numCust is        " + n);
		//System.out.println("days is           " + t);
		//System.out.println("Depot duration is " + D);
		//System.out.println("capacity is       " + Q);

		if (type != 0) { //then it is not an MDVRP problem
			System.out.println("Problem is not an MDVRP problem");
			return 0;
		}

		//This section will get the depot x and y for the PVRP and the PTSP.
		float x = 0, //x coordinate
				y = 0; //y coordinate
		int i = 0, //customer number
				d = 0, //service duration
				q = 0, //demand
				f = 0, //frequency of visit
				a = 0, //number of combinations allowed
				vIndex = 1,
				custCnt = 0;
		int runTimes;

		//Use 1 less the maximum as the 0 index is not used
		//declare the total number of combinations
		int list[] = new int[ZeusProblemInfo.getMaxCombinations()];
		//int list[] = new int[ProblemInfo1.MAX_COMBINATIONS];
		//array of 0'1 and 1's for the combinations
		int currentComb[][] = new int[ZeusProblemInfo.getMaxHorizon()][ZeusProblemInfo.getMaxCombinations()];
		//int currentComb[][] = new int[ProblemInfo1.MAX_HORIZON][ProblemInfo1.
		//                                                       MAX_COMBINATIONS];
		//if MDVRP problem, readn in n+t lines
		if (type == 0) {
			runTimes = n + t;

		}
		//if  PVRP/PTSP, read in n+1 lines
		else {
			runTimes = n + 1;
			//This section will get the customers/depots and related information
		}

		try {
			readLn = br.readLine();
			//print out the line that was read in
			//System.out.println("This is s:" + s);

			//The first for loop runtimes dependent upon how many lines are to be read
			//in
			//The next for loop reads the line into s.  Then the entire string in s
			//is processed until the the entire line is processed and there are no
			//more characters are to be processed. There is a case for each index
			//except for the combinations.  The combinations are processed
			//until the last character in s is processed

			for (int k = 0; k < runTimes; k++) {
				index = 0;
				temp = "";
				vIndex = 0;
				custCnt++;
				st = new StringTokenizer(readLn);
				if (k < n) { //it is a shipment
					while (st.hasMoreElements()) {
						switch (index) {
						case 0:
							i = Integer.parseInt(st.nextToken());
							//System.out.println("custNum is " + custNum);
							break;
						case 1: //x = Double.parseDouble(temp);
							x = Integer.parseInt(st.nextToken());
							//System.out.println("x is " + vertexX);
							break;
						case 2:
							y = Integer.parseInt(st.nextToken());
							//y = Double.parseDouble(temp);
							//System.out.println("y is " + vertexY);
							break;
						case 3:
							d = Integer.parseInt(st.nextToken());
							//System.out.println("duration is " + duration);
							break;
						case 4:
							q = Integer.parseInt(st.nextToken());
							//System.out.println("demand is " + demand);
							break;
						case 5:
							f = Integer.parseInt(st.nextToken());
							//System.out.println("frequency is " + frequency);
							break;
						case 6:
							a = Integer.parseInt(st.nextToken());
							//System.out.println("number of comb is " + numComb);
							break;
						default:
							list[vIndex] = Integer.parseInt(st.nextToken());
							//System.out.println("visitComb[" + vIndex +"] is " + visitComb[vIndex]);
							vIndex++;
							break;
						} //end switch
						index += 1;
					} //end while
					//Each combination gets its own set of 0 and 1 combinations
					//a = number of Combinations, list = [] of comb as ints,
					//l=index of combination to be decoded,
					//t = days in planning horizon or #depots
					for (int l = 0; l < a; l++) {
						currentComb[l] = mainShipments.getCurrentComb(list, l, t); // current visit comb

						//insert the customer data into the linked list
					}
					Integer custType = (Integer) ZeusProblemInfo.getCustTypesAt(0);
					//Integer custType = (Integer) custTypes.elementAt(0);
					mainShipments.insertShipment(i, x, y, q, d, f, a, custType.toString(),
							list, currentComb);

					//  type = (Integer) custTypes.elementAt(0);
					//       shipment = new Shipment(mainShipments.getNumShipments() +
					//                               1, x, y, 1, d, type.toString(), "" + i);

				}
				else { //it is a depot - add it to the depot linked list
					while (st.hasMoreElements() && index < 3) { //No more than three values are there for depot information
						switch (index) {
						case 0:
							i = Integer.parseInt(st.nextToken());
							//System.out.println("custNum is " + custNum);
							break;
						case 1: //x = Double.parseDouble(temp);
							x = Integer.parseInt(st.nextToken());
							//System.out.println("x is " + vertexX);
							break;
						case 2:
							y = Integer.parseInt(st.nextToken());
							//y = Double.parseDouble(temp);
							//System.out.println("y is " + vertexY);
							break;
						default:
							System.out.println("Default in reading the file was initiated");
							vIndex++;
						} //end switch
						index += 1;
					} //while

					//insert the depot into the depot linked list
					//insert only one depot for the VRP
					if (mainDepots.getHead().getNext() == mainDepots.getTail() && mainDepots.getNoDepots() <= 1)
					{
						VRPDepot depot = new VRPDepot(i - n, x, y); //n is the number of customers
						mainDepots.insertDepotLast(depot);

						//Each depot has a mainTrucks. The different truck types available are
						//inserted into the mainTrucks type. For the VRP, there is only one truck type
						depot = (VRPDepot) mainDepots.getHead().getNext();
						for (i = 0; i < ZeusProblemInfo.getTruckTypesSize(); i++) {
						//for (i = 0; i < ProblemInfo1.truckTypes.size(); i++) {
							
							VRPTruckType ttype = (VRPTruckType)ZeusProblemInfo.getTruckTypesAt(i);
							//VRPTruckType ttype = (VRPTruckType) AccessProblemInfo.getTruckTypes()
							//		elementAt(i);
							depot.getMainTrucks().insertTruckLast(new VRPTruck(ttype,
									depot.getXCoord(), depot.getYCoord()));
						}
					}
				} //else
				//read the next line from the file
				try {
					readLn = br.readLine();
				}
				catch (Exception e) {
					e.printStackTrace();
					System.out.println("Reading in the next line");
				} //try
				//System.out.println("This is s:" + s);
			} //end for
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Reading the line");
		}

		//print out the shipment numbers on command line
		//  mainShipments.printShipNos();
		//call method to send the data to file
		try {
			//availShipments.outputMDVRPShipData(type, t, MDVRPFileName, "outCust.txt");   //problem type, #days or depots
			//outputMDVRPShipData(type, t, MDVRPFileName, "outCust.txt");   //problem type, #days or depots
		}
		catch (Exception e) {
			System.out.println("Shipment information could not be sent to the file");
		}
		return 1;
	}
	
	/* 
	 * Method used to read data from the specified Excel file 
	 */
	public int readDataFromExcelFile(String VRPFileName) {
		// read in the MDVRP data from the listed file and load the information
		// into the availShipments linked list

		//type = 0 (MDVRP)
		//     = 1 (PTSP)
		//     = 2 (PVRP)
		char ch;
		String temp = "";
		int index = 0,
				j = 0,
				type = 0, //type
				m        = 0,                           //number of vehicles
				n        = 0,                           //number of customers
				t        = 0,                           //number of days(or depots)
				D        = 0,                           //maximum duration of route
				Q        = 0;                           //maximum load of vehicle
		int p = 3; //Np neighborhood size

		int depotIndex;

		//Open the requested file
		XSSFWorkbook workbook = new XSSFWorkbook();    
		FileInputStream fis;
		XSSFSheet sheet;
		XSSFRow curRow;
		int rowCounter = 0; //initial the row counter

		//    FileInputStream fis;
		//    InputStreamReader isr;
		//    BufferedReader br;
		try {
			fis = new FileInputStream(VRPFileName);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			curRow = sheet.getRow(rowCounter+1); // the 2nd row is the problem data

			//      fis = new FileInputStream(VRPFileName);
			//      isr = new InputStreamReader(fis);
			//      br = new BufferedReader(isr);
		}
		catch (Exception e) {
			System.out.println("readDataFromExcelFile - "+VRPFileName+" File is not present");
			return 0;
		}

		//InitData             currentProb = new InitData();    //Class for initializing data

		//This section will get the initial information from the data file
		//Read in the first line from the file
		//String readLn;
		//StringTokenizer st;

		//This reads in the first line that is used to determine the total
		//numer of customers and type of problem
		//typePvrp is + type);
		//numVeh is         + m; //not really used in an MDVRP
		//numCust is        + n;
		//Days is           + t; //Depots in the case of MDVRP
		//Depot duration is + D;
		//capacity is       + Q;

		//read in the first line
		try {
			type = (int)curRow.getCell(0).getNumericCellValue();
			m = (int)curRow.getCell(1).getNumericCellValue();
			n = (int)curRow.getCell(2).getNumericCellValue();
			t = (int)curRow.getCell(3).getNumericCellValue();
			D = (int)curRow.getCell(4).getNumericCellValue();
			Q = (int)curRow.getCell(5).getNumericCellValue();
		}
		catch (Exception e) {
			System.out.println("Line could not be read in");
		}

		//Put the problem information into the ProblemInfo class
		//set the problem info for the problem
		ZeusProblemInfo.setNumDepots(1); 			  //Set the number of depots to 1 for this problem
		ZeusProblemInfo.setProbFileName(VRPFileName);   //name of the problem file being read in
		ZeusProblemInfo.setProbType(type);		  //problem type
		ZeusProblemInfo.setNoOfVehs(m);			  //number of vehicles
		ZeusProblemInfo.setNoOfShips(n);			  //number of shipments
		ZeusProblemInfo.setNoOfDays(t);			  //number of days (horizon) or number of depots for MDVRP
		
		//ProblemInfo1.numDepots = t; //Set the number of depots to 1 for this problem
		//ProblemInfo1.fileName = VRPFileName; //name of the file being read in
		//ProblemInfo1.probType = type; //problem type
		//ProblemInfo1.noOfVehs = m; //number of vehicles
		//ProblemInfo1.noOfShips = n; //number of shipments
		//ProblemInfo1.noOfDays = t; //number of days (horizon) or number of depots for MDVRP

		if (Q == 0) { //if there is no maximum capacity, set it to a very large number
			Q = 999999999;
		}
		if (D == 0) { //if there is no travel time, set it to a very large number
			D = 999999999; //if there is not maximum distance, set it to a very large number
			//ProblemInfo.maxCapacity = Q;  //maximum capacity of a vehicle
			//ProblemInfo.maxDistance = D;  //maximum distance of a vehicle
		}
		/** @todo  There three variables need to be defined at the beginning of
		 * the method */
		float maxCapacity = Q; //maximum capacity of a vehicle
		float maxDistance = D; //maximum distance of a vehicle

		String serviceType = "1"; //serviceType is the trucktype. Should match with
		//required truck type
		//In some problems, different truck types might be present to solve
		//the problem. For this problem, we assume that there is only one
		//truck type that is available.
		//loop through each truck type and store each one in the vector
		int numTruckTypes = 1;
		for (int i = 0; i < numTruckTypes; i++) {
			VRPTruckType truckType = new VRPTruckType(i, maxDistance,
					maxCapacity, serviceType);
			ZeusProblemInfo.addTruckTypes(truckType);
			//ProblemInfo1.truckTypes.add(truckType);
		}

		//Some problems tend to have different customer types. In this problem
		//there is only one customter type. The integer value for the customer type
		//should match with the integer value for the truck type for the compatibiliy
		//check to work
		//read in the different customer types
		Vector custTypes = new Vector();
		//Obtain the different customer types
		for (int ct = 0; ct < 1; ct++) {
			custTypes.add(new Integer(1));
		}

		//place the number of depots and number of shipments in the linked list instance
		//These no longer seem to be needed for the shipment linked list. The total number of
		//shipments are tallied when they are inserted into the linked list
		//mainShipments.numShipments = n;
		//mainShipments.noDepots = t;
		//mainShipments.maxCapacity = Q;
		//mainShipments.maxDuration = D ;

		//display the information from the first line
		//System.out.println("typePvrp is       " + type);
		//System.out.println("numVeh is         " + m);
		//System.out.println("numCust is        " + n);
		//System.out.println("days is           " + t);
		//System.out.println("Depot duration is " + D);
		//System.out.println("capacity is       " + Q);

		if (type != 0) { //then it is not an MDVRP problem
			System.out.println("Problem is not an MDVRP problem");
			return 0;
		}

		//This section will get the depot x and y for the PVRP and the PTSP.
		float x = 0, //x coordinate
				y = 0; //y coordinate
		int i = 0, //customer number
				d = 0, //service duration
				q = 0, //demand
				f = 0, //frequency of visit
				a = 0; //number of combinations allowed
		int runTimes;

		//Use 1 less the maximum as the 0 index is not used
		//declare the total number of combinations
		//int list[] = new int[ProblemInfo.MAX_COMBINATIONS];
		//array of 0'1 and 1's for the combinations
		//int currentComb[][] = new int[ProblemInfo.MAX_HORIZON][ProblemInfo.MAX_COMBINATIONS];
		//TODO Max_horizon = 7 it's too small

		//if MDVRP problem, readn in n+t lines
		if (type == 0) {
			runTimes = n;

		}
		//if  PVRP/PTSP, read in n+1 lines
		else {
			runTimes = n + 1;
			//This section will get the customers/depots and related information
		}

		try {
			//The first for loop runtimes dependent upon how many lines are to be read in
			//The next for loop reads the line into s.  Then the entire string in s
			//is processed until the the entire line is processed and there are no
			//more characters are to be processed. There is a case for each index
			//except for the combinations.  The combinations are processed
			//until the last character in s is processed

			rowCounter = 3; //set the rowCounter, customer data begin from the 4th row

			for (int k = 0; k < runTimes; k++) {
				index = 0;
				temp = "";
				curRow = sheet.getRow(rowCounter);
				try { // read the current row
					i = (int)curRow.getCell(0).getNumericCellValue();
					x = (int)curRow.getCell(1).getNumericCellValue();
					y = (int)curRow.getCell(2).getNumericCellValue();
					d = (int)curRow.getCell(3).getNumericCellValue();
					q = (int)curRow.getCell(4).getNumericCellValue();
					f = (int)curRow.getCell(5).getNumericCellValue();
					//							a = (int)curRow.getCell(6).getNumericCellValue();
					//							for(int indexComb=0; indexComb<a; indexComb++){
					//								list[indexComb] = (int)curRow.getCell(indexComb+7).getNumericCellValue();
					//							}
				}
				catch (Exception e) {
					System.out.println("Line could not be read in line 474");
				}
				//Each combination gets its own set of 0 and 1 combinations
				//a = number of Combinations, list = [] of comb as ints,
				//l=index of combination to be decoded,
				//t = days in planning horizon or #depots
				//						for (int l = 0; l < a; l++) {
				//							currentComb[l] = mainShipments.getCurrentComb(list, l, t); // current visit comb
				//
				//							//insert the customer data into the linked list
				//						}
				Integer custType = (Integer) custTypes.elementAt(0);
				mainShipments.insertShipment(i, x, y, q, d, f, custType.toString());
				
				//mainShipments.insertShipment(i, x, y, q, d, f, a, custType.toString(),
				//		list, currentComb);

				//						mainShipments.insertShipment(i, x, y, q, d, f, a, custType.toString(),
				//								list, currentComb);

				//  type = (Integer) custTypes.elementAt(0);
				//       shipment = new Shipment(mainShipments.getNumShipments() +
				//                               1, x, y, 1, d, type.toString(), "" + i);
				rowCounter++; //go to next row          
			}// end for

			rowCounter = 3+n+1; //set the rowCounter, depot data begin from the (3+n+1)th row
			for (int k = 0; k < t; k++) { //add it to the depot linked list
				curRow = sheet.getRow(rowCounter);

				try { // read the current row
					i =  (int)curRow.getCell(0).getNumericCellValue();
					x =  (int)curRow.getCell(1).getNumericCellValue();
					y =  (int)curRow.getCell(2).getNumericCellValue();
				}
				catch (Exception e) {
					System.out.println("Line could not be read in line 505");
				}

				//insert the depot into the depot linked list
				VRPDepot depot = new VRPDepot(i - n, x, y); //n is the number of customers
				mainDepots.insertDepotLast(depot);

				//Each depot has a mainTrucks. The different truck types available are
				//inserted into the mainTrucks type. For the VRP, there is only one truck type
				depot = (VRPDepot) mainDepots.getHead().getNext();
				for (i = 0; i < ZeusProblemInfo.getTruckTypesSize(); i++) {
					VRPTruckType ttype = (VRPTruckType) ZeusProblemInfo.getTruckTypesAt(i);
				//for (i = 0; i < ProblemInfo1.truckTypes.size(); i++) {
				//		VRPTruckType ttype = (VRPTruckType) ProblemInfo1.truckTypes.
			//					elementAt(i);
					depot.getMainTrucks().insertTruckLast(new VRPTruck(ttype,
							depot.getXCoord(), depot.getYCoord()));                   
				}

				rowCounter++; //go to next row 
			} //end for
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Reading the line");
		}

		//print out the shipment numbers on command line
		//  mainShipments.printShipNos();
		//call method to send the data to file
		try {
			//availShipments.outputMDVRPShipData(type, t, MDVRPFileName, "outCust.txt");   //problem type, #days or depots
			//outputMDVRPShipData(type, t, MDVRPFileName, "outCust.txt");   //problem type, #days or depots
		}
		catch (Exception e) {
			System.out.println("Shipment information could not be sent to the file");
		}
		return 1;
	}


	/**
	 * Print  out the data to the console
	 */

	public void printDataToConsole() {
		try {
			mainShipments.printVRPShipmentsToConsole();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write out the data file that was read in
	 * @param file name of file used for generating the data
	 */

	public void writeDataFile(String file) {
		try {
			PrintStream ps = new PrintStream(new FileOutputStream(ZeusProblemInfo.getOutputPath()+file +"_students.txt"));
			//PrintStream ps = new PrintStream(new FileOutputStream(ProblemInfo1.
			//		outputPath +file +"_students.txt"));
			mainShipments.writeVRPShipments(ps);
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
	
	
	/*
	 * Create a excel file to put in the final solutions
	 */
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
			row2.createCell(0).setCellValue(mainDepots.getVRPHead().getNext().getNext().getDepotNum()); 
			row2.createCell(1).setCellValue(mainDepots.getVRPHead().getNext().getXCoord()); // depot location x
			row2.createCell(2).setCellValue(mainDepots.getVRPHead().getNext().getYCoord()); // depot location y
			row2.createCell(3).setCellValue(mainDepots.getAttributes().getTotalDemand());
			row2.createCell(4).setCellValue(mainDepots.getAttributes().getTotalDistance());
			row2.createCell(5).setCellValue(ZeusProblemInfo.getNoOfVehs());
			//row2.createCell(5).setCellValue(ProblemInfo1.noOfVehs);
			
			// row 3 information, truck information(?)
			XSSFRow row3 = sheet.createRow(2);
			row3.createCell(0).setCellValue(mainDepots.getVRPHead().getNext().getMainTrucks().getHead().getNext().getTruckNum()); // what is this
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
	

	/**
	 * Will write a long detailed solution for the problem
	 * @param file name of the file to write to
	 */
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

	/**
	 * Will write a short solution for the problem
	 * @param file name of the file to write to
	 */
	public void writeShortSolutionTxt(String file) {
		try {
			//PrintStream ps = new PrintStream(new FileOutputStream(ProblemInfo.
			//outputPath + "/" + file + "_short.txt"));
			PrintStream ps = new PrintStream(new FileOutputStream(ZeusProblemInfo.getOutputPath()
					+ file + "_short.txt"));
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
	
	/*
	 * Create a excel file to put in the final solutions
	 */
	
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
		OptInfo info = new OptInfo();
		for (int i = 0; i < mainOpts.size(); i++) {
			//Extract the operator in the vector and convert it into a
			//SearchStrategy type. The object that is loaded into the
			//SearchStrategy type is not SearchStrategy as it is an
			//abstract class but a class that inherits off of
			//SearchStrategy such as FirstBestIntraOpt, FirstBestInterOpt
			//and so on. the opt.run method runs the run method
			//from the inheriting class such as FirstBestIntraOpt.
			SearchStrategy opt = (SearchStrategy) mainOpts.elementAt(i);
			Settings.printDebug(Settings.COMMENT, "Running " + opt);

			//The opt.run method called is dependent on the object that
			//was created in the mainOpts. If the object was BestBestIntraSearch
			//then the run method in BestBestIntraSearch is called.
			optInformation.add(opt.toString() + " " + opt.run(mainDepots));
			Settings.printDebug(Settings.COMMENT,
					opt.toString() + " Stats: " +
							mainDepots.getSolutionString());
		}
		//Calculate the total stats
		//ProblemInfo.depotLLLevelCostF.calculateTotalsStats(mainDepots);
		//info.setNewAtributes(mainDepots.getAttributes());
	}

} //End of VRP file
