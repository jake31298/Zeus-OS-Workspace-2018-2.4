package edu.sru.thangiah.zeus.qualityassurance;

import edu.sru.thangiah.zeus.core.*;
import java.io.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class QualityAssurance {
  //DepotLinkedList mainDepots;
  //ShipmentLinkedList mainShipments;
  //QADepotLinkedList qaDepots;
  //QAShipmentLinkedList qaShipments;

  private File shipFile;
  private File solFile;
/** @todo Need to document the variables and the parameters */
  public QualityAssurance() {
  }
public File getShipFile() {
	return shipFile;
}
public void setShipFile(File shipFile) {
	this.shipFile = shipFile;
}
public File getSolFile() {
	return solFile;
}
public void setSolFile(File solFile) {
	this.solFile = solFile;
}

  /*public QualityAssurance(DepotLinkedList md, ShipmentLinkedList ms) {
    mainDepots = md;
    mainShipments = ms;
    qaShipments = new VRPQAShipmentLinkedList();
    qaDepots = new VRPQADepotLinkedList();

    writeFiles();
    readShipmentFile();
    readSolutionFile();
     }

     public boolean runQA() {
    boolean isGood = true;

    isGood = isGood && qaShipments.customerServicedOnlyOnce(qaDepots);
    isGood = isGood && qaDepots.checkSiteDependance();

    return isGood;
     }

     public void writeFiles() {
    PrintStream out = null;
    try {
      //write the shipment information
      shipFile = new File(ProblemInfo.tempFileLocation + "/ship.txt");
      out = new PrintStream(new FileOutputStream(shipFile));
      mainShipments.writeShipments(out);

      //write the current solution
      solFile = new File(ProblemInfo.tempFileLocation + "/sol.txt");
      out = new PrintStream(new FileOutputStream(solFile));
      mainDepots.expandAllRoutes();
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
        QAShipment s = new QAShipment();
        s.index = Integer.parseInt(st.nextToken().trim());
        s.type = st.nextToken().trim();
        s.demand = Double.parseDouble(st.nextToken().trim());
        s.x = Float.parseFloat(st.nextToken().trim());
        s.y = Float.parseFloat(st.nextToken().trim());
        s.pup = st.nextToken();
        qaShipments.shipments.add(s);
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
        QADepot d = new QADepot();
        d.index = Integer.parseInt(st.nextToken().trim());
        d.x = Float.parseFloat(st.nextToken().trim());
        d.y = Float.parseFloat(st.nextToken().trim());
        d.demand = Double.parseDouble(st.nextToken().trim());
        d.distance = Double.parseDouble(st.nextToken().trim());

        int numTrucks = Integer.parseInt(st.nextToken().trim());
        for (int j = 0; j < numTrucks; j++) {
          st = new StringTokenizer(br.readLine());
          QATruck t = new QATruck();
          t.index = Integer.parseInt(st.nextToken().trim());
          t.type = st.nextToken().trim();
          t.demand = Double.parseDouble(st.nextToken().trim());
          t.distance = Double.parseDouble(st.nextToken().trim());
          t.maxDemand = Double.parseDouble(st.nextToken().trim());
          t.maxDistance = Double.parseDouble(st.nextToken().trim());

          int numNodes = Integer.parseInt(st.nextToken().trim());
          for (int k = 0; k < numNodes; k++) {
            st = new StringTokenizer(br.readLine());
            QANode n = new QANode();
            n.index = Integer.parseInt(st.nextToken().trim());
            n.demand = Double.parseDouble(st.nextToken().trim());
            n.x = Float.parseFloat(st.nextToken().trim());
            n.y = Float.parseFloat(st.nextToken().trim());
            n.type = st.nextToken().trim();

            t.nodes.add(n);
          }
          d.trucks.add(t);
        }
        qaDepots.depots.add(d);
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
   */
}
