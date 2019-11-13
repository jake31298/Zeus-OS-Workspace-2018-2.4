package edu.sru.thangiah.zeus.top;

//import the parent class
import java.text.DecimalFormat;

import edu.sru.thangiah.zeus.core.Attributes;
import edu.sru.thangiah.zeus.core.DepotLinkedList;
import edu.sru.thangiah.zeus.core.Settings;
import edu.sru.thangiah.zeus.core.ZeusProblemInfo;

//For Excel file
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
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

/**
 *
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class TOPDepotLinkedList extends DepotLinkedList implements java.io.Serializable, java.lang.Cloneable {

	/**
	 * 
	 */
	private TOPDepot startingDepot;
	private TOPDepot endingDepot;
	// Index of the ending depot
	private static final long serialVersionUID = 1L;

	public TOPDepotLinkedList() {
		// Housekeeping for the linked list
		setHead(new TOPDepot()); // header node for head
		setTail(new TOPDepot()); // tail node for tail
		linkHeadTail(); // point head and tail to each other

		// Assign the attributes
		setAttributes(new TOPAttributes());
	}

	/**
	 * Sets the starting depot
	 */
	public void setStartingDepot(TOPDepot d) {
		this.startingDepot = d;
	}

	/**
	 * gets the starting depot
	 */
	public TOPDepot getStartingDepot() {
		return this.startingDepot;
	}

	/**
	 * Sets the ending depot
	 */
	public void setEndingDepot(TOPDepot d) {
		this.endingDepot = d;
	}

	/**
	 * gets the ending depot
	 */
	public TOPDepot getEndingDepot() {
		return this.endingDepot;
	}

	/**
	 * Attempts to insert the shipment into the depot linked list. Will loop through
	 * the available depots until a depot is found that can accommodate the shipment
	 * 
	 * @param theShipment the shipment to route
	 * @return true if shipment serviced, false if not.
	 */
	public boolean insertShipment(TOPShipment theShipment) {
		boolean status = false;

		TOPDepot depot = (TOPDepot) super.getHead().getNext();
		TOPTruckLinkedList truckLL = (TOPTruckLinkedList) depot.getMainTrucks();

		while (depot != this.getTail()) {
			// Get truck to insert the shipment
			truckLL = (TOPTruckLinkedList) depot.getMainTrucks();
			// status = depot.getMainTrucks().insertShipment(theShipment);
			status = truckLL.insertShipment(theShipment);

			if (status) {
				break;
			}
			depot = (TOPDepot) depot.getNext();
		}

		return status;
	}

	/**
	 * Returns the first depot in the linked list
	 * 
	 * @return first depot
	 */

	public TOPDepot getTOPHead() {
		return (TOPDepot) getHead();
	}

	/**
	 * Returns the elliptical area which determines which shipments can be reached
	 * without exceeding the trucks' maximum travel time
	 * 
	 * @return TOPEllipse Added by David Crissman
	 */
	public TOPEllipse getReachableArea() {
		TOPEllipse reachableArea;

		// Only continue if starting and ending depots have been specified
		if ((startingDepot != null) && (endingDepot != null)) {
			// Recalculate the ellipse to ensure that it is up to date
			System.out.println(TOPProblemInfo.getTruckMaxTravelTime());

			reachableArea = new TOPEllipse(startingDepot.getXCoord(), startingDepot.getYCoord(),
					endingDepot.getXCoord(), endingDepot.getYCoord(), TOPProblemInfo.getTruckMaxTravelTime());
		} else {
			reachableArea = null;

			Settings.printDebug(Settings.COMMENT, "Starting and ending depots have not been defined");
		}

		return reachableArea;
	}

	public String getSolutionString() {

		return "Trucks Used = " + getTotalNumTrucksSelected() + " | " + getAttributes().toDetailedString();
	}

	public Attributes getTOPAttributes() {
		return getAttributes();
	}

	public void markUnreachableShipments(TOPShipmentLinkedList mainShipments) {
		TOPShipment currentShipment;
		TOPEllipse reachableArea;

		// Only continue if the starting and ending depots have been specified
		if ((startingDepot != null) && (endingDepot != null)) {
			// Get the ellipse representing the area that the trucks can reach
			reachableArea = getReachableArea();

			// Set the flag in each shipment according to whether or not it is in the
			// reachable area
			currentShipment = mainShipments.getTOPHead();
			while (currentShipment != mainShipments.getTail()) {
				if (reachableArea.pointInsideEllipse(currentShipment.getXCoord(),
						currentShipment.getYCoord()) == true) {
					currentShipment.setUnreachable(false);
				} else {
					Settings.printDebug(Settings.COMMENT,
							"Shipment #" + currentShipment.getIndex() + " cannot be reached.");
					currentShipment.setUnreachable(true);
				}

				currentShipment = (TOPShipment) currentShipment.getNext();
			}
		} else {
			Settings.printDebug(Settings.COMMENT, "Starting and ending depots have not been defined");
		}
	}

	public int getTotalNumTrucksSelected() {
		TOPDepot currentDepot = getTOPHead().getNext();
		TOPTruck currentTruck, currentTruckTail;
		int numBusesUsed = 0;

		// loop through all depots
		while (currentDepot != getTail()) {
			currentTruck = currentDepot.getMainTrucks().getHead();
			currentTruckTail = currentDepot.getMainTrucks().getTail();

			// loop through all buses
			while (currentTruck != currentTruckTail) {
				// if bus is not empty, then increment number of used buses
				if (!currentTruck.isEmptyMainNodes() && currentTruck.getIsUsed()) {
					numBusesUsed++;
				}

				currentTruck = currentTruck.getTOPNext();
			}

			currentDepot = currentDepot.getNext();
		}

		return numBusesUsed;
	}

	/**
	 * Returns an exact copy of the depot linked list
	 * 
	 * @return Object depot linked list copy
	 */
	public Object clone() {
		TOPDepotLinkedList clonedDepotLinkedList = new TOPDepotLinkedList();

		clonedDepotLinkedList.setAttributes((TOPAttributes) this.getTOPAttributes().clone());
		clonedDepotLinkedList.setHead((TOPDepot) this.getTOPHead().clone());
		clonedDepotLinkedList.setNoDepots(this.getNoDepots());
		// must establish the links at this level to avoid recursing out of control
		clonedDepotLinkedList.getHead().setPrev(null);

		if (this.getHead() != this.getTail()) {
			TOPDepot currentDepot = (TOPDepot) clonedDepotLinkedList.getTOPHead();
			TOPDepot nextDepot = (TOPDepot) this.getTOPHead().getNext();

			while (nextDepot != null) {
				currentDepot.setNext((TOPDepot) nextDepot.clone()); // create the next depot
				currentDepot.getNext().setPrev(currentDepot); // set the next depot's prev
				currentDepot = (TOPDepot) currentDepot.getNext();
				nextDepot = (TOPDepot) nextDepot.getNext();

				// once next is null, we have found the tail of the list
				if (nextDepot == null) {
					clonedDepotLinkedList.setTail(currentDepot);
					currentDepot.setNext(null);
				}

			}
		} else { // only 1 depot
			clonedDepotLinkedList.setTail(clonedDepotLinkedList.getHead());
		}

		// Copy over the variables that are unique to TOPDepotLinkedList
		clonedDepotLinkedList.setStartingDepot(this.getStartingDepot());
		clonedDepotLinkedList.setEndingDepot(this.getEndingDepot());

		return clonedDepotLinkedList;

	}

}
