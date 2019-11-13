package edu.sru.thangiah.zeus.top;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */





import java.util.Iterator;


//import the parent class
import edu.sru.thangiah.zeus.core.ShipmentLinkedList;
import edu.sru.thangiah.zeus.core.Shipment;
import edu.sru.thangiah.zeus.core.ZeusProblemInfo;

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

public class TOPShipmentLinkedList
extends ShipmentLinkedList
implements java.io.Serializable, java.lang.Cloneable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the shipment linked list
	 * Define it as below
	 */
	public TOPShipmentLinkedList() 
	{
		setHead(new TOPShipment()); //header node for head
		setTail(new TOPShipment()); //tail node for tail
		linkHeadTail();			  //point head and tail to each other
		setNumShipments(0);
	}


	public Object clone() {
	    TOPShipmentLinkedList clonedShipmentLinkedList = new TOPShipmentLinkedList();

	    if (this.getHead().getNext() != this.getTail()) {
	      TOPShipment nextShipment = this.getTOPHead().getTOPNext();

	      while (nextShipment != this.getTail()) {
	    	clonedShipmentLinkedList.insertLast((Shipment) nextShipment.clone());
	        nextShipment = nextShipment.getTOPNext();

	        
	      }
	    }
	    

	    return clonedShipmentLinkedList;
	  }

	/*public TOPShipmentLinkedList clone() {
		TOPShipmentLinkedList list = new TOPShipmentLinkedList();
		list.setNumShipments(this.getNumShipments());
		list.setTotalDemand(this.getTotalDemand());
		list.setTotalDist(this.getTotalDist());
		list.setTotalStops(this.getTotalStops());
		TOPShipment theShip = (TOPShipment) this.getHead().getNext();
		while(theShip.getNext() != null) {
			System.out.println(theShip.getIndex());
			list.insertLast(theShip);
			theShip= (TOPShipment) theShip.getNext();
		}
		return null;
		
	}*/
	
	/**
	 * insert the shipment into the linked list
	 * Constructor
	 * @param ind index
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param q demand
	 * @param d service time
	 * @param e frequency
	 * @param comb number of combination
	 * @param vComb list of combinations (vector)
	 * @param cuComb number of combinations (matrix)
	 */
	public void insertShipment(int num, float x, float y, int s, String type) {
		
			TOPShipment thisShip = new TOPShipment(num,x,y,s,type);
					//add the instance to the linked list - in this case it is added at the end of the list
			//the total number of shipments is incremented in the insert
			insertLast(thisShip);
		
	}

	/**
	 * Returns the first shipment in the linked list
	 * @return first shipment
	 */
	public TOPShipment getTOPHead() {
		return (TOPShipment) this.getHead();
	}
	
	

	
	public boolean isAllShipsAssignedOrUnreachable() {
	    boolean retValue = true;
	    TOPShipment currentShipment = (TOPShipment) getTOPHead();

	    while (currentShipment != this.getTail()) {
	      if ( (currentShipment.getIsAssigned() == false) && (currentShipment.getUnreachable() == false)) {
		retValue = false;
	      }

	      currentShipment = (TOPShipment) currentShipment.getNext();
	    }

	    return retValue;
	  }
	/**
	 * Returns the tail shipment in the linked list
	 * @return first shipment
	 */
	public TOPShipment getTOPTail() {
		return (TOPShipment) getTail();
	}
	
	


	/**
	 * This method will get the next shipment that is to be inserted based on the
	 * type of shipment selection that has been defined in the main method
	 * for the variable ProblemInfo.selectShipType
	 * @param currDepot current depot being considered for the shipment
	 * @param currDepotLL Depot linked list of the problem being solved
	 * @param currShipmentLL shipment linked list from which the shipment to be
	 * insertion is to be selected
	 * @return TOPShipment the shipment to be inserted
	 */

	public TOPShipment getNextInsertShipment(TOPDepotLinkedList currDepotLL,
			TOPDepot currDepot,
			TOPShipmentLinkedList currShipmentLL,
			TOPShipment currShip) {

		TOPShipmentLinkedList selectShip = (TOPShipmentLinkedList) ZeusProblemInfo.getSelectShipType();
		return selectShip.getSelectShipment(currDepotLL, currDepot, currShipmentLL,currShip);
	}

	/**
	 * This is a stub - Leave it as it is
	 * The concrere getSelectShipment will be declared by the class inheriting this
	 * class and implementing the actual insertion of shipment  *
	 * @param currShipmentLL shipment linked list from which the shipment to be
	 * insertion is to be selected
	 * @return TOPShipment the shipment to be inserted
	 */

	public TOPShipment getSelectShipment(TOPDepotLinkedList currDepotLL,
			TOPDepot currDepot,
			TOPShipmentLinkedList currShipmentLL,
			TOPShipment currShip) {
		return null;
	}



	/**
	 * Print out the shipment linked list to the console
	 * @param out PrintStream stream to output to
	 */
	public void printTOPShipmentsToConsole() {
		System.out.println(this.getNumShipments());

		Shipment ship = super.getHead();
		TOPShipment topShip;
		while (ship != getTail())
		{
			topShip = (TOPShipment)ship ;
			System.out.println(topShip.getIndex() + " " + topShip.getTruckTypeNeeded() + " " +
					topShip.getDemand() + " " + topShip.getXCoord() + " " +
					//ship.getYCoord() + " " + ship.getPickUpPointName() +
					topShip.getYCoord() + " " +
					topShip.getExtraVariable());
			ship = ship.getNext();
		}
	}



	/**
	 * Writes the shipments to the print stream
	 * @param out PrintStream stream to output to
	 */
	public void writeTOPShipments(PrintStream out) {
		out.println(this.getNumShipments());


		Shipment ship = super.getHead();
		TOPShipment topShip;
		while (ship != getTail())
		{
			topShip = (TOPShipment)ship ;
			out.println(topShip.getIndex() + " " + topShip.getTruckTypeNeeded() + " " +
					topShip.getDemand() + " " + topShip.getXCoord() + " " +
					//ship.getYCoord() + " " + ship.getPickUpPointName() +
					topShip.getYCoord() + " " +
					topShip.getExtraVariable());
			ship = ship.getNext();
		}
	}
	
	/**
	 * Write out Excel file for data checking
	 */
	public void writeTOPShipmentsExcel(String file) {
		//@todo

		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Sheet1");
			System.out.print(this.getNumShipments());
			//for (int i = 0; i < this.getNumShipments(); i++)
			//{
			int i = 0;

			Shipment ship = super.getHead();
			TOPShipment topShip;
			while (ship != getTail())
			{
				Row row = worksheet.createRow(i);
				topShip = (TOPShipment)ship ;


				row.createCell(0).setCellValue(topShip.getIndex());
				row.createCell(1).setCellValue(topShip.getXCoord());
				row.createCell(2).setCellValue(topShip.getYCoord());
				row.createCell(3).setCellValue(topShip.getDemand());

				i++;
				ship = ship.getNext();
			}	  
			//}

			FileOutputStream fileOut = new FileOutputStream(file);
			workbook.write(fileOut);


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}


	public static String WhoAmI() {
		return null;
	}
}

//Select the shipment with the shortest distance to the depot
/**
*
* <p>Title: HighestDemandInReachableArea</p>
* <p>Description: This is a selection algorithm which chooses the shipment with the highest demand.</p>
* <p>Copyright: Copyright (c) 2007</p>
* <p>Company: Slippery Rock University</p>
*
* @author David Crissman
* @version 1.0
*/

/**
*
* <p>Title: EllipticalTargetArea</p>
* <p>Description: This is a selection algorithm which only chooses shipments from a within the bounds
* of two ellipses, the first defined by the starting point and a target point, and the second defined
* by the target point and the ending point. The shipments are selected using a secondary selection
* algorithm.</p>
* <p>Copyright: Copyright (c) 2007</p>
* <p>Company: Slippery Rock University</p>
* @author David Crissman
* @version 1.0
*/
class EllipticalTargetArea
   extends TOPShipmentLinkedList {
 private static TOPEllipse targetAreaA, targetAreaB;                //The two elliptical target areas
 private static TOPShipmentLinkedList secondarySelectionAlgorithm;  //Selection algorithm used to select
                                                                    //shipments from within the target ares

 /**
  * Constructor
  */
 public EllipticalTargetArea() {
   super();

   targetAreaA = new TOPEllipse(TOPProblemInfo.getStartXCoord(), TOPProblemInfo.getStartYCoord(),
                                    TOPProblemInfo.getEndXCoord(), TOPProblemInfo.getEndYCoord(),
                                    TOPProblemInfo.getTruckMaxTravelTime());
   targetAreaB = targetAreaA;
   secondarySelectionAlgorithm = null;
 }

 /**
  * Constructor
  * @param xCoord double
  * @param yCoord double
  * @param dist double
  * @param alg TOPShipmentLinkedList
  */
 public EllipticalTargetArea(double xCoord, double yCoord, double dist, TOPShipmentLinkedList alg) {
   targetAreaA = new TOPEllipse(TOPProblemInfo.getStartXCoord(), TOPProblemInfo.getStartYCoord(),
                                xCoord, yCoord, dist);
   targetAreaB = new TOPEllipse(TOPProblemInfo.getEndXCoord(), TOPProblemInfo.getEndYCoord(),
                                xCoord, yCoord, dist);
   secondarySelectionAlgorithm = alg;
 }

 /**
  * Sets the secondary selection algorithm
  * @param alg TOPShipmentLinkedList
  */
 public static void setSecondaryAlgorithm(TOPShipmentLinkedList alg) {
   secondarySelectionAlgorithm = alg;
 }

 /**
  * Chooses a shipment from within the target area, using the secondary selection algorithm
  * @param currDepotLL TOPDepotLinkedList
  * @param currDepot TOPDepot
  * @param currShipLL TOPShipmentLinkedList
  * @param currShip TOPShipment
  * @return TOPShipment
  */
 public TOPShipment getSelectShipment(TOPDepotLinkedList currDepotLL, TOPDepot currDepot,
                                      TOPShipmentLinkedList currShipLL, TOPShipment currShip) {
   TOPShipment currentShipment;
   boolean status, isDiagnostic = false;

   //Clear the 'checked' flags on all the shipments
   currentShipment = this.getTOPHead();
   while (currentShipment != this.getTail()) {
     currentShipment.setChecked(false);
     currentShipment = currentShipment.getTOPNext();
   }

   //Call the secondary algorithm until it returns a shipment which is in the target area
   status = false;
   currentShipment = null;
   while (status == false) {
     currentShipment = secondarySelectionAlgorithm.getSelectShipment(currDepotLL, currDepot, currShipLL, currShip);

     if (currentShipment != null) {
	if ((targetAreaA.pointInsideEllipse(currentShipment.getXCoord(), currentShipment.getYCoord()) == true) ||
          (targetAreaB.pointInsideEllipse(currentShipment.getXCoord(), currentShipment.getYCoord()) == true)) {
         status = true;
	}
	else {
         //This shipment is not in the target area, so mark it as checked, and move on to a different one
	  currentShipment.setChecked(true);
	}
     }
     else {
       status = true;
     }
   }

   return currentShipment;
 }


 public static String WhoAmI() {
   return ("Selection Type: Selects shipments from a sweeping elliptical area");
 }

}

/**
*
* <p>Title: HighestDemandInReachableArea</p>
* <p>Description: This is a selection algorithm which chooses the shipment with the highest demand.</p>
* <p>Copyright: Copyright (c) 2007</p>
* <p>Company: Slippery Rock University</p>
*
* @author David Crissman
* @version 1.0
*/
class HighestDemandInReachableArea
   extends TOPShipmentLinkedList {
 /**
  * Returns the next available shipment
  * @param currDepotLL TOPDepotLinkedList
  * @param currDepot TOPDepot
  * @param currShipLL TOPShipmentLinkedList
  * @param currShip TOPShipment
  * @return TOPShipment
  */
 public TOPShipment getSelectShipment(TOPDepotLinkedList currDepotLL, TOPDepot currDepot,
				       TOPShipmentLinkedList currShipLL, TOPShipment currShip) {
   TOPShipment currentShipment, bestShipment = null;
   int highestDemand, demand;
   boolean isDiagnostic = false;

   highestDemand = -1;
   currentShipment = (TOPShipment) currShipLL.getTOPHead().getNext(); // Pete changed this because 'head' was null.
   while (currentShipment != null) {
	   //System.out.print(currentShipment);
	   //System.out.println("     " + this.getTail());
     if (isDiagnostic) {
	System.out.print("Shipment " + currentShipment.getIndex() + " ");
     }

     if (currentShipment.getIsAssigned()) {
	if (isDiagnostic) {
	  System.out.println("has been assigned");
	}
     }
     else if (currentShipment.getUnreachable() == true) {
	if (isDiagnostic) {
	  System.out.println("is not reachable");
	}
     }
     else if (currentShipment.getChecked() == true) {
       if (isDiagnostic) {
         System.out.println("is outside the target area");
       }
     }
     else {
	demand = currentShipment.getDemand();
	if (demand > highestDemand) {
	  bestShipment = currentShipment;
	  highestDemand = currentShipment.getDemand();
	}

	if (isDiagnostic) {
	  System.out.println("  " + demand);
	}
     }

     currentShipment = (TOPShipment) currentShipment.getNext();
   }

   return bestShipment;
 }

 public static String WhoAmI() {
   return ("Selection Type: Highest demand within the trucks' reachable area");
 }
}
class ClosestEuclideanDistToDepot
extends TOPShipmentLinkedList {
	public TOPShipment getSelectShipment(TOPDepotLinkedList currDepotLL,
			TOPDepot currDepot,
			TOPShipmentLinkedList currShipLL,
			TOPShipment currShip) {
		//currDepotLL is the depot linked list of the problem
		//currDepot is the depot under consideration
		//currShipLL is the set of avaialble shipments
		boolean isDiagnostic = false;
		//TOPShipment temp = (TOPShipment) getHead(); //point to the first shipment
		TOPShipment temp = (TOPShipment) currShipLL.getTOPHead().getNext(); //point to the first shipment
		TOPShipment foundShipment = null; //the shipment found with the criteria
		//double angle;
		//double foundAngle = 360; //initial value
		double distance;
		double foundDistance = 200; //initial distance
		double depotX, depotY;

		//Get the X and Y coordinate of the depot
		depotX = currDepot.getXCoord();
		depotY = currDepot.getYCoord();

		while (temp != currShipLL.getTOPTail()) {
			if (isDiagnostic) {
				System.out.print("Shipment " + temp.getIndex() + " ");

				if ( ( (temp.getXCoord() - depotX) >= 0) &&
						( (temp.getYCoord() - depotX) >= 0)) {
					System.out.print("Quadrant I ");
				}
				else if ( ( (temp.getXCoord() - depotX) <= 0) &&
						( (temp.getYCoord() - depotY) >= 0)) {
					System.out.print("Quadrant II ");
				}
				else if ( ( (temp.getXCoord()) <= (0 - depotX)) &&
						( (temp.getYCoord() - depotY) <= 0)) {
					System.out.print("Quadrant III ");
				}
				else if ( ( (temp.getXCoord() - depotX) >= 0) &&
						( (temp.getYCoord() - depotY) <= 0)) {
					System.out.print("Quadrant VI ");
				}
				else {
					System.out.print("No Quadrant");
				}
			}

			//if the shipment is assigned, skip it
			if (temp.getIsAssigned()) {
				if (isDiagnostic) {
					System.out.println("has been assigned");
				}

				temp = (TOPShipment) temp.getNext();

				continue;
			}
			/** @todo Associate the quadrant with the distance to get the correct shipment.
			 * Set up another insertion that takes the smallest angle and the smallest distance */
			distance = calcDist(depotX, temp.getXCoord(), depotY, temp.getYCoord());

			if (isDiagnostic) {
				System.out.println("  " + distance);
			}

			//check if this shipment should be tracked
			if (foundShipment == null) { //this is the first shipment being checked
				foundShipment = temp;
				foundDistance = distance;
			}
			else {
				if (distance < foundDistance) { //found an angle that is less
					foundShipment = temp;
					foundDistance = distance;
				}
			}
			temp = (TOPShipment) temp.getNext();
		}
		return foundShipment; //stub
	}

	//The WhoAmI methods gives the id of the assigned object
	//It is a static method so that it can be accessed without creating an object
	public static String WhoAmI() {
		return("Selection Type: Closest euclidean distance to depot");
	}
	
	//Similar to WhoAmI but an abbreviation, for file output
	public static String stringAbbreviation() {
		return ("ClosestEuclideanDist");
	}

}


//Select the shipment with the smallest polar coordinate angle to the depot
class SmallestPolarAngleToDepot
extends TOPShipmentLinkedList {
	public TOPShipment getSelectShipment(TOPDepotLinkedList currDepotLL,
			TOPDepot currDepot,
			TOPShipmentLinkedList currShipLL,
			TOPShipment currShip) {
		//currDepotLL is the depot linked list of the problem
		//currDepot is the depot under consideration
		//currShipLL is the set of avaialble shipments
		boolean isDiagnostic = false;
		//TOPShipment temp = (TOPShipment) getHead(); //point to the first shipment
		TOPShipment temp = (TOPShipment) currShipLL.getTOPHead().getNext(); //point to the first shipment

		TOPShipment foundShipment = null; //the shipment found with the criteria
		double angle;
		double foundAngle = 360; //initial value
		//double distance;
		//double foundDistance = 200; //initial distance
		double depotX, depotY;
		int type = 2;

		//Get the X and Y coordinate of the depot
		depotX = currDepot.getXCoord();
		depotY = currDepot.getYCoord();

		while (temp != currShipLL.getTOPTail()) {

			if (isDiagnostic) {
				System.out.println("Temp is "+temp);
				System.out.println("Tail is "+getTail());
				System.out.print("Shipment " + temp.getIndex() + " ");

				if ( ( (temp.getXCoord() - depotX) >= 0) &&
						( (temp.getYCoord() - depotX) >= 0)) {
					System.out.print("Quadrant I ");
				}
				else if ( ( (temp.getXCoord() - depotX) <= 0) &&
						( (temp.getYCoord() - depotY) >= 0)) {
					System.out.print("Quadrant II ");
				}
				else if ( ( (temp.getXCoord()) <= (0 - depotX)) &&
						( (temp.getYCoord() - depotY) <= 0)) {
					System.out.print("Quadrant III ");
				}
				else if ( ( (temp.getXCoord() - depotX) >= 0) &&
						( (temp.getYCoord() - depotY) <= 0)) {
					System.out.print("Quadrant VI ");
				}
				else {
					System.out.print("No Quadrant");
				}
			}

			//if the shipment is assigned, skip it
			if (temp.getIsAssigned()) {
				if (isDiagnostic) {
					System.out.println("has been assigned");
				}

				temp = (TOPShipment) temp.getNext();

				continue;
			}

			angle = calcPolarAngle(depotX, depotX, temp.getXCoord(),
					temp.getYCoord());

			if (isDiagnostic) {
				System.out.println("  " + angle);
			}

			//check if this shipment should be tracked
			if (foundShipment == null) { //this is the first shipment being checked
				foundShipment = temp;
				foundAngle = angle;
			}
			else {
				if (angle < foundAngle) { //found an angle that is less
					foundShipment = temp;
					foundAngle = angle;
				}
			}
			temp =  (TOPShipment) temp.getNext();
		}
		return foundShipment; //stub
	}

	//The WhoAmI methods gives the id of the assigned object
	//It is a static method so that it can be accessed without creating an object
	public static String WhoAmI() {
		return("Selection Type: Smallest polar angle to the depot");
	}
	
	//Similar to WhoAmI but an abbreviation, for file output
		public static String stringAbbreviation() {
			return ("SmallestPolarAngle");
	}
}

/**
 * Attempt at a new routing solution based on routing
 * to the point with the greatest score/distance ratio
 * with hopes of better results.  Better results were
 * not had.
 * @author nrb1001
 *
 */
class ScoreToDistDepot
extends TOPShipmentLinkedList {
	public TOPShipment getSelectShipment(TOPDepotLinkedList currDepotLL,
			TOPDepot currDepot,
			TOPShipmentLinkedList currShipLL,
			TOPShipment currShip) {
		//currDepotLL is the depot linked list of the problem
		//currDepot is the depot under consideration
		//currShipLL is the set of avaialble shipments

		boolean isDiagnostic = false;
		//TOPShipment temp = (TOPShipment) getHead(); //point to the first shipment
		TOPShipment temp = (TOPShipment)currShipLL.getTOPHead().getNext(); //point to the first shipment
		TOPShipment foundShipment = null; //the shipment found with the criteria
		double angle;
		double foundAngle = 360; //initial value
		double distance;
		double foundDistance = 200; //initial distance
		double score = 0;
		double foundScore = 0;
		double depotX, depotY;
		int type = 2;
		//Get the X and Y coordinate of the depot
		depotX = currDepot.getXCoord();
		depotY = currDepot.getYCoord();

		while (temp != currShipLL.getTOPTail()) 
		{
			if (isDiagnostic) {
				System.out.print("Shipment " + temp.getIndex() + " ");

				if ( ( (temp.getXCoord() - depotX) >= 0) &&
						( (temp.getYCoord() - depotX) >= 0)) {
					System.out.print("Quadrant I ");
				}
				else if ( ( (temp.getXCoord() - depotX) <= 0) &&
						( (temp.getYCoord() - depotY) >= 0)) {
					System.out.print("Quadrant II ");
				}
				else if ( ( (temp.getXCoord()) <= (0 - depotX)) &&
						( (temp.getYCoord() - depotY) <= 0)) {
					System.out.print("Quadrant III ");
				}
				else if ( ( (temp.getXCoord() - depotX) >= 0) &&
						( (temp.getYCoord() - depotY) <= 0)) {
					System.out.print("Quadrant VI ");
				}
				else {
					System.out.print("No Quadrant");
				}
			}

			
			//currDepotLL.getAttributes();
			//currShipLL.;
			//currShip;
			
			//if the shipment is assigned, skip it
			if (temp.getIsAssigned()) 
			{
				if (isDiagnostic) {
					System.out.println("has been assigned");
				}

				temp = (TOPShipment) temp.getNext();

				continue;
			}

			distance = calcDist(depotX, temp.getXCoord(), depotY, temp.getYCoord());
			angle = calcPolarAngle(depotX, depotX, temp.getXCoord(),
					temp.getYCoord());
			score = temp.getDemand();
			if (isDiagnostic) 
			{
				System.out.println("  " + angle);
			}
			//if(temp.getTravelTime() < currDepot.getMainTrucks().getAttributes().getMaxTravelTime())
				
			//check if this shipment should be tracked
			if (foundShipment == null) { //this is the first shipment being checked
				foundShipment = temp;
				foundAngle = angle;
				foundDistance = distance;
				foundScore = score;
			}
			else 
			{
				//if angle and disnace are smaller than what had been found
				//if (angle <= foundAngle && distance <= foundDistance) {
				if (score/(angle+ distance)  <= foundScore/(foundAngle + foundDistance)) 
				{
					//if ((angle*.90)+ (distance * 0.1)  <= (foundAngle*0.9) + (foundDistance*0.1)) {
					foundShipment = temp;
					foundAngle = angle;
					foundDistance = distance;
					foundScore = score;
				}
			}
			
			temp = (TOPShipment) temp.getNext();
		}
		
		return foundShipment; //stub

	}
	public static String WhoAmI() {
		return("Selection Type: Smallest score over distance to the depot");
	}
	
	//Similar to WhoAmI but an abbreviation, for file output
			public static String stringAbbreviation() {
				return ("ScoreOverDist");
	}
}
//Select the shipment with the smallest polar coordinate angle and the
//shortest distance to the depot
class SmallestPolarAngleShortestDistToDepot
extends TOPShipmentLinkedList {
	public TOPShipment getSelectShipment(TOPDepotLinkedList currDepotLL,
			TOPDepot currDepot,
			TOPShipmentLinkedList currShipLL,
			TOPShipment currShip) {
		//currDepotLL is the depot linked list of the problem
		//currDepot is the depot under consideration
		//currShipLL is the set of avaialble shipments
		boolean isDiagnostic = false;
		//TOPShipment temp = (TOPShipment) getHead(); //point to the first shipment
		TOPShipment temp = (TOPShipment)currShipLL.getTOPHead().getNext(); //point to the first shipment
		TOPShipment foundShipment = null; //the shipment found with the criteria
		double angle;
		double foundAngle = 360; //initial value
		double distance;
		double foundDistance = 200; //initial distance
		double depotX, depotY;
		int type = 2;

		//Get the X and Y coordinate of the depot
		depotX = currDepot.getXCoord();
		depotY = currDepot.getYCoord();

		while (temp != currShipLL.getTOPTail()) {
			if (isDiagnostic) {
				System.out.print("Shipment " + temp.getIndex() + " ");

				if ( ( (temp.getXCoord() - depotX) >= 0) &&
						( (temp.getYCoord() - depotX) >= 0)) {
					System.out.print("Quadrant I ");
				}
				else if ( ( (temp.getXCoord() - depotX) <= 0) &&
						( (temp.getYCoord() - depotY) >= 0)) {
					System.out.print("Quadrant II ");
				}
				else if ( ( (temp.getXCoord()) <= (0 - depotX)) &&
						( (temp.getYCoord() - depotY) <= 0)) {
					System.out.print("Quadrant III ");
				}
				else if ( ( (temp.getXCoord() - depotX) >= 0) &&
						( (temp.getYCoord() - depotY) <= 0)) {
					System.out.print("Quadrant VI ");
				}
				else {
					System.out.print("No Quadrant");
				}
			}

			//if the shipment is assigned, skip it
			if (temp.getIsAssigned()) {
				if (isDiagnostic) {
					System.out.println("has been assigned");
				}

				temp = (TOPShipment) temp.getNext();

				continue;
			}

			distance = calcDist(depotX, temp.getXCoord(), depotY, temp.getYCoord());
			angle = calcPolarAngle(depotX, depotX, temp.getXCoord(),
					temp.getYCoord());

			if (isDiagnostic) {
				System.out.println("  " + angle);
			}

			//check if this shipment should be tracked
			if (foundShipment == null) { //this is the first shipment being checked
				foundShipment = temp;
				foundAngle = angle;
				foundDistance = distance;
			}
			else {
				//if angle and disnace are smaller than what had been found
				//if (angle <= foundAngle && distance <= foundDistance) {
				if (angle+ distance  <= foundAngle + foundDistance) {
					//if ((angle*.90)+ (distance * 0.1)  <= (foundAngle*0.9) + (foundDistance*0.1)) {
					foundShipment = temp;
					foundAngle = angle;
					foundDistance = distance;
				}
			}
			temp = (TOPShipment) temp.getNext();
		}
		return foundShipment; //stub
	}

	//The WhoAmI methods gives the id of the assigned object
	//It is a static method so that it can be accessed without creating an object
	public static String WhoAmI() {
		return("Selection Type: Smallest polar angle to the depot");
	}
	
	//Similar to WhoAmI but an abbreviation, for file output
	public static String stringAbbreviation() {
		return ("SmallestPolarAngleShortestDist");
	}

}

