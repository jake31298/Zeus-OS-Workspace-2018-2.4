package edu.sru.thangiah.zeus.localopts;

import edu.sru.thangiah.zeus.core.*;
import java.io.*;

/**
 * Class that contains information about an optimization. Records the effectiveness
 * of the optimization.
 * <p>Title: OptInfo</p>
 * <p>Description: Class that contains information about an optimization.
 * Records the effectiveness of the optimization.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class OptInfo
    implements Serializable {
  public boolean didChange = false;
  public int numChanges = 0;
  private Attributes oldAttributes;
  private Attributes newAttributes;

  /**
   * Constructor
   */
  public OptInfo() {
    oldAttributes = new Attributes();
    newAttributes = new Attributes();
  }

  /**
   * sets the new attributes
   * @param nA new attributes
   */
  public void setNewAtributes(Attributes nA) {
    newAttributes = nA;
  }

  /**
   * gets the new attributes
   * @return new attributes
   */
  public Attributes getNewAttributes() {
    return newAttributes;
  }

  /**
   * sets the old attributes
   * @param oA old attributes
   */
  public void setOldAttributes(Attributes oA) {
    //oA is the pointer that is pointing to the old attributes
    //oldAttributes now points to the old set of values
    oldAttributes = oA;
  }

  /**
   * gets the old attributes
   * @return old attributes
   */
  public Attributes getOldAttributes() {
    return oldAttributes;
  }

  /**
   * Returns the OptInfo in a string form
   * @return opt info string
   */
  public String toString() {
    return numChanges + " changes " + newAttributes.toString();
  }
}
