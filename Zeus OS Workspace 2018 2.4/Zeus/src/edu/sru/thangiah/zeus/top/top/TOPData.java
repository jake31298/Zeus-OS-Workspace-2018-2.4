package edu.sru.thangiah.zeus.top;

import java.util.Vector;

import edu.sru.thangiah.zeus.top.topqualityassurance.TOPQualityAssurance;

public class TOPData {
	public int m;
	public int n;
	public int t;
	public float L;
	public float D;
	public int Q;
	public long startTime;
	public long endTime;
	public Vector mainOpts;
	public Vector optInformation;
	public TOPShipmentLinkedList mainShipments;
	public TOPDepotLinkedList mainDepots;
	public TOPQualityAssurance topQA;

	public TOPData(int m, int n, int t, float d, int q, Vector mainOpts,
			Vector optInformation, TOPShipmentLinkedList mainShipments,
			TOPDepotLinkedList mainDepots) {
		this.m = m;
		this.n = n;
		this.t = t;
		D = d;
		Q = q;
		this.mainOpts = mainOpts;
		this.optInformation = optInformation;
		this.mainShipments = mainShipments;
		this.mainDepots = mainDepots;
	}
}