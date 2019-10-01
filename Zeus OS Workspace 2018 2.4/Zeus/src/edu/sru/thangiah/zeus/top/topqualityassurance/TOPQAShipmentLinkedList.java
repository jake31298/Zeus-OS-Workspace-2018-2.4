package edu.sru.thangiah.zeus.top.topqualityassurance;

import edu.sru.thangiah.zeus.qualityassurance.*;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */
public class TOPQAShipmentLinkedList
    extends QAShipmentLinkedList
    implements java.io.Serializable, java.lang.Cloneable {

  public TOPQAShipmentLinkedList() {
  }

  public boolean customerServicedOnlyOnce(TOPQADepotLinkedList qaDepots) {
    //loop through all the shipments and mark which are serviced and count the number of times
    //the customers are serviced. Each shipment should be serviced no more than once
    for (int i = 0; i < qaDepots.getDepots().size(); i++) {
      TOPQADepot d = (TOPQADepot) qaDepots.getDepots().elementAt(i);
      for (int j = 0; j < d.getTrucks().size(); j++) {
        TOPQATruck t = (TOPQATruck) d.getTrucks().elementAt(j);
        for (int k = 0; k < t.getNodes().size(); k++) {
          TOPQANode n = (TOPQANode) t.getNodes().elementAt(k);
          for (int l = 0; l < getShipments().size(); l++) {
            TOPQAShipment s = (TOPQAShipment) getShipments().elementAt(l);
            if (s.getIndex() == n.getIndex()) {
              s.setServecount(s.getServecount()+1);
              break;
            }
          }
        }
      }
    }

    boolean ret = true;
    //loop through shipments and look for anomolies.  No shipment should be serviced more than once.
    for (int l = 0; l < getShipments().size(); l++) {
      TOPQAShipment s = (TOPQAShipment) getShipments().elementAt(l);
      if (s.getServecount() > 1) {
        edu.sru.thangiah.zeus.core.Settings.printDebug(edu.sru.thangiah.zeus.
            core.Settings.ERROR,
            "Shipment " + s.getIndex() + " is serviced " + s.getServecount() + " time(s)");
        ret = false;
      }
    }

    return ret;
  }

}
