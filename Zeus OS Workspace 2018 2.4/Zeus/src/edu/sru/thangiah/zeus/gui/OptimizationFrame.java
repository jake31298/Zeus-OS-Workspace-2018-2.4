package edu.sru.thangiah.zeus.gui;

import com.borland.jbcl.layout.*;
import edu.sru.thangiah.zeus.core.*;
//import edu.sru.thangiah.zeus.localopts.*;
import java.awt.*;
import java.awt.event.*;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.*;

/**
 * Frame that holds the optimization, and a log of what they did
 * <p>Title: OptimizationFrame</p>
 * <p>Description: Frame that holds the optimization, and a log of what they did</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class OptimizationFrame
    extends JInternalFrame {
  private DepotLinkedList mainDepots;
  private Vector optimizations;
  private Vector log = new Vector();

  //interface variables
  //private ZeusGui zeusGui;
  private JToolBar optBar = new JToolBar();
  private JScrollPane logPane = new JScrollPane();
  private JTextArea optLog = new JTextArea();
  private JPanel panel = new JPanel();
  private XYLayout xYLayout1 = new XYLayout();

  /*public OptimizationFrame(Vector opts, DepotLinkedList mD, ZeusGui zgui) {
    super("Optimization", true, false, false, true);
    optimizations = opts;
    mainDepots = mD;
    zeusGui = zgui;

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }*/

  /**
   * Initialize graphical objects
   * @throws Exception error
   */
  private void jbInit() throws Exception {
    for (int i = 0; i < optimizations.size(); i++) {
     // SearchStrategy opt = (SearchStrategy) optimizations.elementAt(i);
      //JButton button = new JButton(opt.getShortName());
      //button.addActionListener(new OptButtonListener(opt, log, optLog));
      //optBar.add(button);
    }

    panel.setLayout(xYLayout1);
    logPane = new JScrollPane(optLog);

    panel.add(logPane, new XYConstraints(0, 34, 589, 234));
    panel.add(optBar, new XYConstraints(0, 1, 597, 32));
    this.getContentPane().add(panel, BorderLayout.CENTER);
    this.setSize(600, 300);
  }

  /**
   * Listener for the buttons in the optimization class
   * <p>Title: OptButtonListener</p>
   * <p>Description: Listener for the buttons in the optimization class</p>
   * <p>Copyright: Copyright (c) 2005</p>
   * <p>Company: </p>
   * @author Sam R. Thangiah
   * @version 2.0
   */
  private class OptButtonListener
      implements ActionListener {
    //SearchStrategy theOpt;
    Vector log;
    JTextArea optLog;

    /**
     * Constructor
     * @param o the optimization
     * @param l the vector holding the log
     * @param ol text area to write the log info to
     */
    /*public OptButtonListener(SearchStrategy o, Vector l, JTextArea ol) {
      theOpt = o;
      log = l;
      optLog = ol;
    }*/

    /**
     * Execute the optimzation
     * @param e this button was clicked
     */
    public void actionPerformed(ActionEvent e) {
      long startCPUTime = 0;
      long endCPUTime = 0;

      startCPUTime = System.currentTimeMillis();

      //OptInfo info = theOpt.run(mainDepots);
      endCPUTime = System.currentTimeMillis();

      /// Sam zeusGui.refreshAll();
      //log.add(info);
      //optLog.append(theOpt + " = " + info + " CPU Time = " +
        //            ( (endCPUTime - startCPUTime) / 1000D) + " sec\n");
    }
  }
}
