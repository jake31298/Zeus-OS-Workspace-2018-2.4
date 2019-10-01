package edu.sru.thangiah.zeus.vrp;

//import the parent class
import edu.sru.thangiah.zeus.core.Nodes;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class VRPNodes
    extends Nodes
    implements java.io.Serializable, java.lang.Cloneable {
  public VRPNodes() {
  }

  /**
   * Constructor
   * @param s shipment conatining this cells information
   */
  public VRPNodes(VRPShipment s) {
    //super(s);
    setShipment(s);
  }

  /**
   * Returns the next point cell in the linked list
   * @return next point cell
   */
  public VRPNodes getVRPNext() {
    return (VRPNodes) getNext();
  }

  /**
   * Creates a copy of this node and returns it. It will not create the next
   * and prev links, because this would cause infinite recursion. These are
   * set in the nodes linked list clone() function.
   * @return Object node clone
   */
  public Object clone() {
    VRPNodes clonedNode = new VRPNodes();

    
    if (this.getShipment() != null)
    	clonedNode.setShipment((VRPShipment)this.getShipment().clone());
    	//clonedNode.theShipment = (VRPShipment)this.theShipment.clone();
    else
    	clonedNode.setShipment(new VRPShipment());
    
    return clonedNode;
  }

}
