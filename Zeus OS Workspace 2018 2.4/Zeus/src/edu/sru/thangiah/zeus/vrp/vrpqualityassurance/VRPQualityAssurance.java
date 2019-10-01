package edu.sru.thangiah.zeus.vrp.vrpqualityassurance;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.vrp.*;
import edu.sru.thangiah.zeus.qualityassurance.*;
import java.io.*;
import java.util.*;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */
/** @todo Need to document the variables and the parameters */
public class VRPQualityAssurance
    extends QualityAssurance {
  VRPDepotLinkedList mainDepots;
  VRPShipmentLinkedList mainShipments;
  VRPQADepotLinkedList vrpQADepots;
  VRPQAShipmentLinkedList vrpQAShipments;

  File shipFile;
  File solFile;

  public VRPQualityAssurance() {
  }

  public VRPQualityAssurance(VRPDepotLinkedList md, VRPShipmentLinkedList ms) {
    //used for writing out the files
    mainDepots = md;
    mainShipments = ms;
    //used for reading in the information
    vrpQAShipments = new VRPQAShipmentLinkedList();
    vrpQADepots = new VRPQADepotLinkedList();

    //Write out the information that is in the data structures. This does not read the original file
    //Might need another function that reads in the original files and checks if they are correct
    writeFiles();
    readShipmentFile();
    readSolutionFile();
  }

  public boolean runQA() {
    boolean isGood = true;
/** @todo  CHeck the integrity of the data. That is, are there shipments that are larger than then largest capacity
     * available trucks */
    //Area all the customer being serviced and are they serviced only once
    System.out.print("Check on all customers being serviced and serviced no more than once:");
    isGood = vrpQAShipments.customerServicedOnlyOnce(vrpQADepots);
    if (isGood) {
      System.out.println("Passed");
    }
    else {
      System.out.println("Failed");
    }

    //Check on maximum travel time of truck
    System.out.print("Check on maximum travel time of trucks:");
    isGood = isGood && vrpQADepots.checkDistanceConstraint();
    /** @todo Need a check to ensure that the constraints of the routes are met - maximum distance and capacity*/
    if (isGood) {
      System.out.println("Passed");
    }
    else {
      System.out.println("Failed");
    }

    //Check on maximum demand of a truck
    System.out.print("Check on maximum demand of trucks:");

    isGood = isGood && vrpQADepots.checkCapacityConstraint();
    /** @todo Need a check to ensure that the constraints of the routes are met - maximum distance and capacity*/
    if (isGood) {
      System.out.println("Passed");
    }
    else {
      System.out.println("Failed");
    }

    return isGood;
  }

  public void writeFiles() {
    PrintStream out = null;
    try {
      //write the shipment information - C:/temp/ship.txt
      shipFile = new File(ZeusProblemInfo.getTempFileLocation()+ "/ship.txt");
      out = new PrintStream(new FileOutputStream(shipFile));
      mainShipments.writeShipments(out);

      //write the current solution - C:/temp/sol.txt
      solFile = new File(ZeusProblemInfo.getTempFileLocation() + "/sol.txt");
      out = new PrintStream(new FileOutputStream(solFile));
      //mainDepots.expandAllRoutes(); //not needed for the VRP
      mainDepots.printDepotLinkedList(out);
    }
    catch (IOException ioe) {
      ioe.printStackTrace();
    }
    finally {
      try {
        if (out != null) {
          out.close();
        }
      }
      catch (Exception ioe) {
        ioe.printStackTrace();
      }
    }
  }

  public void readShipmentFile() {
    BufferedReader br = null;
    try {
      StringTokenizer st;
      br = new BufferedReader(new FileReader(shipFile));
      int numships = Integer.parseInt(br.readLine().trim());

      for (int i = 0; i < numships; i++) {
        st = new StringTokenizer(br.readLine());
        VRPQAShipment s = new VRPQAShipment();
        s.setIndex(Integer.parseInt(st.nextToken().trim()));
        s.setType(st.nextToken().trim());
        s.setDemand(Double.parseDouble(st.nextToken().trim()));
        s.setX(Float.parseFloat(st.nextToken().trim()));
        s.setY(Float.parseFloat(st.nextToken().trim()));
        //s.setPup(st.nextToken());
        //Add to the auality assurance shipment linked list
        vrpQAShipments.getShipments().add(s);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      try {
        if (br != null) {
          br.close();
        }
      }
      catch (Exception ioe) {
        ioe.printStackTrace();
      }
    }
  }

  public void readSolutionFile() {
    BufferedReader br = null;
    try {
      StringTokenizer st;
      br = new BufferedReader(new FileReader(solFile));

      int depots = Integer.parseInt(br.readLine().trim());
      for (int i = 0; i < depots; i++) {
        st = new StringTokenizer(br.readLine());
        //Add to quality assurance depot
        VRPQADepot d = new VRPQADepot();
        d.setIndex(Integer.parseInt(st.nextToken().trim()));
        d.setX(Float.parseFloat(st.nextToken().trim()));
        d.setY(Float.parseFloat(st.nextToken().trim()));
        d.setDemand(Double.parseDouble(st.nextToken().trim()));
        d.setDistance(Double.parseDouble(st.nextToken().trim()));

        int numTrucks = Integer.parseInt(st.nextToken().trim());
        for (int j = 0; j < numTrucks; j++) {
          st = new StringTokenizer(br.readLine());
          //Add to quality assurance truck
          VRPQATruck t = new VRPQATruck();
          t.setIndex(Integer.parseInt(st.nextToken().trim()));
          t.setType(st.nextToken().trim());
          t.setDemand(Double.parseDouble(st.nextToken().trim()));
          t.setDistance(Double.parseDouble(st.nextToken().trim()));
          t.setMaxDemand(Double.parseDouble(st.nextToken().trim()));
          t.setMaxDistance(Double.parseDouble(st.nextToken().trim()));

          int numNodes = Integer.parseInt(st.nextToken().trim());
          //the first node is the depot node
          VRPQANode n = new VRPQANode();
          n.setIndex(0);
          n.setDemand(0);
          n.setX(d.getX());
          n.setY(d.getY());
          t.getNodes().add(n); //add the node
          for (int k = 1; k < numNodes+1; k++) {
            st = new StringTokenizer(br.readLine());
            //Add to quality assurance node
            n = new VRPQANode();
            n.setIndex(Integer.parseInt(st.nextToken().trim()));
            n.setDemand(Double.parseDouble(st.nextToken().trim()));
            n.setX(Float.parseFloat(st.nextToken().trim()));
            n.setY(Float.parseFloat(st.nextToken().trim()));
            n.setType(st.nextToken().trim());
            t.getNodes().add(n);
          }
          d.getTrucks().add(t);
        }
        vrpQADepots.getDepots().add(d);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      try {
        if (br != null) {
          br.close();
        }
      }
      catch (Exception ioe) {
        ioe.printStackTrace();
      }
    }
  }
}
