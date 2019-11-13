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

  public static final long serialVersionUID = 1;

  public TOPNodes() {
    super();
    setNext(null);
    setPrev(null);

  }

  /**
   * Constructor
   * @param s shipment conatining this cells information
   */
  public TOPNodes(TOPShipment s) {
    super();
    TOPShipment theShipment = s;
    setNext(null);
    setPrev(null);
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
    return (TOPShipment)getTOPShipment();
  }

  /**
   * Creates a copy of this node and returns it. It will not create the next
   * and prev links, because this would cause infinate recursion. These are
   * set in the nodes linked list clone() function.
   * @return Object node clone
   */
  public Object clone() {
    TOPNodes clonedNode = new TOPNodes();

    //clonedNode.theShipment = (TOPShipment)this.getTOPShipment().clone(); Original
    clonedNode.setShipment((TOPShipment) this.getTOPShipment().clone()); //Added 30 SEP 2019
    clonedNode.setNext(this.getTOPNext());
    clonedNode.setPrev(this.getTOPPrev());

    return clonedNode;
  }

}
