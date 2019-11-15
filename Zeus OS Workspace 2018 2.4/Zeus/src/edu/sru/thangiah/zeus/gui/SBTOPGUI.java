package edu.sru.thangiah.zeus.gui;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.localopts.OptInfo;
import edu.sru.thangiah.zeus.localopts.*;

import java.awt.*;
import java.util.*;

import javax.swing.UIManager;

import edu.sru.thangiah.zeus.gui.*;
//import edu.sru.thangiah.zeus.core.
import edu.sru.thangiah.zeus.localopts.*;
/**
 * The main class for the graphical user interface
 * <p>Title: Mixed Fleet</p>
 * <p>Description: The main class for the graphical user interface</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class SBTOPGUI {
  private boolean packFrame = false;
  private MainFrame frame;

  /**
   * Construct the applicaton
   */
  public SBTOPGUI(DepotLinkedList mainDepots, Vector opts) {
    frame = new MainFrame(mainDepots, opts);

    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame) {
      frame.pack();
    }
    else {
      frame.validate();
    }

    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();

    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }

    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }

    frame.setLocation( (screenSize.width - frameSize.width) / 2,
                      (screenSize.height - frameSize.height) / 2);
    frame.setVisible(true);
  }

  /**
   * Refresh the frame for new information
   * @param d new depot linked list
   * @param label what was just done
   */
  public void refresh(DepotLinkedList d, String label) {
    frame.refreshIt(d, label);
  }

  /**
   * Refreshes the frame for new information
   * @param d new depot linked list
   * @param label what was just done
   * @param optInfo information about what was just done
   */
  public void refresh(DepotLinkedList d, String label, OptInfo optInfo) {
    //frame.refreshIt(d, label, optInfo);
    frame.refreshIt(d, label);
  }

  /**
   * The main method
   * @param args arguments passed by the command line
   */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    //new SBTOPGUI();
  }
}
