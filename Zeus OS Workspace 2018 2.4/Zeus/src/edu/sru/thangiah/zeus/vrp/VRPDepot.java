package edu.sru.thangiah.zeus.vrp;

import edu.sru.thangiah.zeus.core.Attributes;
//import the parent class
import edu.sru.thangiah.zeus.core.Depot;
import edu.sru.thangiah.zeus.core.TruckLinkedList;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class VRPDepot
extends Depot
implements java.io.Serializable, java.lang.Cloneable {

	public VRPDepot() {
		//set the attributes and the truck linked list
		setAttributes (new VRPAttributes());
		setMainTrucks(new VRPTruckLinkedList());
	}

	/**
	 * Constructor. Creates the depot
	 * @param d depot number
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public VRPDepot(int d, float x, float y) {
		//The x,y and d (index number)
		//super(d, x, y);
		setDepotNum(d);
		setXCoord(x);
		setYCoord(y);

		setAttributes(new VRPAttributes());
		setMainTrucks(new VRPTruckLinkedList());
	}

	/**
	 * Returns the truck linked list
	 * @return main trucks
	 */
	public VRPTruckLinkedList getMainTrucks() {
		return (VRPTruckLinkedList)super.getMainTrucks();
	}

	/**
	 * Returns the next depot in the linked list
	 * @return next depot
	 */
	public VRPDepot getNext() {
		return (VRPDepot)super.getNext();
	}

	public Object clone() {
		VRPDepot clonedDepot = new VRPDepot();

		clonedDepot.setDepotNum(this.getDepotNum());
		clonedDepot.setxCoord(this.getxCoord());
		clonedDepot.setyCoord(this.getyCoord());
		clonedDepot.setAttributes((Attributes) this.getAttributes().clone());
		clonedDepot.setMainTrucks((TruckLinkedList)this.getMainTrucks().clone());

		return clonedDepot;
	}

}
