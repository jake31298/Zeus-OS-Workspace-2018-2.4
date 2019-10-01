package edu.sru.thangiah.zeus.qualityassurance;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */
/** @todo Need to document the variables and the parameters */
public class QAShipment {
	private int index;
	private String type;
	private double demand;
	private float x;
	private float y;
	// protected String pup;
	private int servCount;
	private int custId;
	private String pickupPointName;
	private int roadNo;
	private String roadName;

	public String getPickupPointName() {
		return pickupPointName;
	}

	public void setPickupPointName(String pickupPointName) {
		this.pickupPointName = pickupPointName;
	}

	public int getRoadNo() {
		return roadNo;
	}

	public void setRoadNo(int roadNo) {
		this.roadNo = roadNo;
	}

	public String getRoadName() {
		return roadName;
	}

	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}

	public QAShipment() {
		servCount = 0;
	}

	public int getIndex() {
		return index;
	}

	public int getServecount() {
		return servCount;
	}

	public int getCustId() {
		return custId;
	}

	public int getServCount() {
		return servCount;
	}

	public void setServCount(int servCount) {
		this.servCount = servCount;
	}

	public String getType() {
		return type;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public void setIndex(int ind) {
		index = ind;
	}

	public void setType(String ty) {
		type = ty;
	}

	public void setDemand(double dmd) {
		demand = dmd;
	}

	public double getDemand() {
		return demand;
	}

	public void setX(float xx) {
		x = xx;
	}

	public void setY(float yy) {
		y = yy;
	}

	/*
	 * public void setPup(String pp) { pup = pp; }
	 */

	public void setServecount(int sCount) {
		servCount = sCount;
	}
}
