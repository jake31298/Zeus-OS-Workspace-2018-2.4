package edu.sru.thangiah.zeus;

import edu.sru.thangiah.zeus.top.TOPRoot;
import  edu.sru.thangiah.zeus.vrp.VRPRoot;


import javax.swing.*;

/**
 * Calls the main functions of Zeus.
 * Title: Zeus
 * Description: This class calls the main root method for the problem to be solved
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class Zeus {
  /**
   * Main function for Zeus
   * @param args command line arguments (not used)
   */
  public static void main(String[] args) {
	  
	  
    //Solve the TOP Problem
    //VRPRoot theRoot = new VRPRoot();
     TOPRoot theRoot = new TOPRoot();

	System.gc();

  }
}
