package edu.sru.thangiah.zeus.top;

//import the parent class
import edu.sru.thangiah.zeus.core.Nodes;
import edu.sru.thangiah.zeus.core.Shipment;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class TOPNodes
    extends Nodes
    implements java.io.Serializable, java.lang.Cloneable {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public TOPNodes() {
  }

  /**
   * Constructor
   * @param s shipment conatining this cells information
   */
  public TOPNodes(TOPShipment s) {
    //super(s);
    this.setShipment(s);
  }

  /**
   * Returns the next point cell in the linked list
   * @return next point cell
   */
  public TOPNodes getTOPNext() {
    return (TOPNodes) getNext();
  }
  public TOPNodes getTOPPrev() {
	    return (TOPNodes) getPrev();
	  }

	  public TOPShipment getTOPShipment() {
	    return (TOPShipment)this.getShipment();
	  }
  /**
   * Creates a copy of this node and returns it. It will not create the next
   * and prev links, because this would cause infinate recursion. These are
   * set in the nodes linked list clone() function.
   * @return Object node clone
   */
  
  public Object clone() {
	    TOPNodes clonedNode = new TOPNodes();
	    
	    if(this.getShipment() != null) {
	    	clonedNode.setShipment((TOPShipment)this.getTOPShipment().clone());
	    }
	    else
	    	clonedNode.setShipment(new TOPShipment());

	    return clonedNode;
	  }

}
