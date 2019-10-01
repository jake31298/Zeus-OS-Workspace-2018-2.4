package edu.sru.thangiah.zeus.qualityassurance;

import java.util.Vector;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */
/** @todo Need to document the variables and the parameters */
public class QAShipmentLinkedList {
 private Vector shipments;

  public QAShipmentLinkedList() {
    shipments = new Vector();
  }

  public Vector getShipments(){
    return shipments;
  }

 /* public boolean customerServicedOnlyOnce(QADepotLinkedList qaDepots) {
    //loop through all the shipments and mark which are serviced
    for (int i = 0; i < qaDepots.depots.size(); i++) {
      QADepot d = (QADepot) qaDepots.depots.elementAt(i);
      for (int j = 0; j < d.trucks.size(); j++) {
        QATruck t = (QATruck) d.trucks.elementAt(j);
        for (int k = 0; k < t.nodes.size(); k++) {
          QANode n = (QANode) t.nodes.elementAt(k);
          for (int l = 0; l < shipments.size(); l++) {
            QAShipment s = (QAShipment) shipments.elementAt(l);
            if (s.index == n.index) {
              s.servCount++;
              break;
            }
          }
        }
      }
    }

    boolean ret = true;
    //loop through shipments and look for anomolies
    for (int l = 0; l < shipments.size(); l++) {
      QAShipment s = (QAShipment) shipments.elementAt(l);
      if (s.servCount != 1) {
        edu.sru.thangiah.zeus.core.Settings.printDebug(edu.sru.thangiah.zeus.
            core.Settings.ERROR,
            "Shipment " + s.index + " is serviced " + s.servCount + " time(s)");
        ret = false;
      }
    }

    return ret;
  }
*/
}
