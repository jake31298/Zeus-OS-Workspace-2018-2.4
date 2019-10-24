package edu.sru.thangiah.zeus.top.topqualityassurance;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.top.*;
import edu.sru.thangiah.zeus.qualityassurance.*;
import edu.sru.thangiah.zeus.top.TOPDepotLinkedList;
import edu.sru.thangiah.zeus.top.TOPShipmentLinkedList;
//import edu.sru.thangiah.zeus.core.ZeusProblemInfo;

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
public class TOPQualityAssurance
    extends QualityAssurance {
  TOPDepotLinkedList mainDepots;
  TOPShipmentLinkedList mainShipments;
  TOPQADepotLinkedList TOPQADepots;
  TOPQAShipmentLinkedList TOPQAShipments;

  File shipFile;
  File solFile;

  public TOPQualityAssurance() {
  }

  public TOPQualityAssurance(TOPDepotLinkedList mainDepots2, TOPShipmentLinkedList mainShipments2) {
    //used for writing out the files
    mainDepots = mainDepots2;
    mainShipments = mainShipments2;
    //used for reading in the information
    TOPQAShipments = new TOPQAShipmentLinkedList();
    TOPQADepots = new TOPQADepotLinkedList();

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
    isGood = TOPQAShipments.customerServicedOnlyOnce(TOPQADepots);
    if (isGood) {
      System.out.println("Passed");
    }
    else {
      System.out.println("Failed");
    }

    //Check on maximum travel time of truck
    System.out.print("Check on maximum travel time of trucks:");
    isGood = isGood && TOPQADepots.checkDistanceConstraint();
    /** @todo Need a check to ensure that the constraints of the routes are met - maximum distance and capacity*/
    if (isGood) {
      System.out.println("Passed");
    }
    else {
      System.out.println("Failed");
    }

    //Check on maximum demand of a truck
    System.out.print("Check on maximum demand of trucks:");

    isGood = isGood && TOPQADepots.checkCapacityConstraint();
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
      //shipFile = new File(ProblemInfo.tempFileLocation + "/ship.txt"); // Original
      shipFile = new File(ZeusProblemInfo.getTempFileLocation() + "/ship.txt");
      out = new PrintStream(new FileOutputStream(shipFile));
      mainShipments.writeShipments(out);

      //write the current solution - C:/temp/sol.txt
      //solFile = new File(ProblemInfo.tempFileLocation + "/sol.txt"); // Original
      solFile = new File(ZeusProblemInfo.getTempFileLocation() + "/sol.txt");
      out = new PrintStream(new FileOutputStream(solFile));
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
        TOPQAShipment s = new TOPQAShipment();
        s.setIndex(Integer.parseInt(st.nextToken().trim()));
        s.setType(st.nextToken().trim());
        s.setDemand(Double.parseDouble(st.nextToken().trim()));
        s.setX(Float.parseFloat(st.nextToken().trim()));
        s.setY(Float.parseFloat(st.nextToken().trim()));
        //s.setPup(st.nextToken());
        //Add to the quality assurance shipment linked list
        TOPQAShipments.getShipments().add(s);
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


  /**
   * Modified by Pete - Caution.
   */
  public void readSolutionFile() {
    BufferedReader br = null;
    try {
      StringTokenizer st;
      br = new BufferedReader(new FileReader(solFile));

      String ssss = solFile.getName();
      String str = br.readLine();

      int depots = Integer.parseInt(str.trim());
      depots =  1; // Set to 2 for the TOP problem.
      for (int i = 0; i < depots; i++) {
        st = new StringTokenizer(br.readLine());
        //Add to quality assurance depot
        TOPQADepot d = new TOPQADepot();
        d.setIndex(Integer.parseInt(st.nextToken().trim()));
        d.setX(Float.parseFloat(st.nextToken().trim()));
        d.setY(Float.parseFloat(st.nextToken().trim()));
        d.setDemand(Double.parseDouble(st.nextToken().trim()));
        d.setDistance(Double.parseDouble(st.nextToken().trim()));

        int numTrucks = Integer.parseInt(st.nextToken().trim());
        for (int j = 0; j < numTrucks; j++) {
          st = new StringTokenizer(br.readLine());
          //Add to quality assurance truck
          TOPQATruck t = new TOPQATruck();
          t.setIndex(Integer.parseInt(st.nextToken().trim()));
          t.setType(st.nextToken().trim());
          t.setDemand(Double.parseDouble(st.nextToken().trim()));
          t.setDistance(Double.parseDouble(st.nextToken().trim()));
          t.setMaxDemand(Double.parseDouble(st.nextToken().trim()));
          t.setMaxDistance(Double.parseDouble(st.nextToken().trim()));

          int numNodes = Integer.parseInt(st.nextToken().trim());
          for (int k = 0; k < numNodes; k++) {
            st = new StringTokenizer(br.readLine());
            //Add to quality assurance node
            TOPQANode n = new TOPQANode();
            n.setIndex(Integer.parseInt(st.nextToken().trim()));
            n.setDemand(Double.parseDouble(st.nextToken().trim()));
            n.setX(Float.parseFloat(st.nextToken().trim()));
            n.setY(Float.parseFloat(st.nextToken().trim()));
            n.setType(st.nextToken().trim());

            t.getNodes().add(n);
          }
          d.getTrucks().add(t);
        }
        TOPQADepots.getDepots().add(d);
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
