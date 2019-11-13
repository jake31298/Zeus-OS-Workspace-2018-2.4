package edu.sru.thangiah.zeus.top.topgui;

import com.borland.jbcl.layout.*;
import edu.sru.thangiah.zeus.top.*;
import java.awt.*;
import java.util.Vector;
import javax.swing.*;

/**
 * <p>Title: Mixed Fleet</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */
public class TOPRunOptPanel
    extends JPanel {
  private TOPDepotLinkedList mainDepots;
  private TOPOptLoopConfig config;
  private TOPMainFrame mainFrame;
  private JDialog dialog;
  private TOPRunOptLoopThread runOpts;
  private Vector optInfo = new Vector();
  private XYLayout xYLayout1 = new XYLayout();
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JProgressBar jProgressBar1 = new JProgressBar();
  private JLabel jLabel3 = new JLabel();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JList jList1 = new JList();
  private JLabel jLabel4 = new JLabel();
  private JLabel jLabel5 = new JLabel();
  private JLabel jLabel6 = new JLabel();
  private JLabel jLabel7 = new JLabel();
  private JLabel jLabel8 = new JLabel();
  private TOPRunningOptPanel runningOptPanel;
  private JScrollPane jScrollPane2 = new JScrollPane();
  private JPanel jPanel2 = new JPanel();
  private JLabel jLabel9 = new JLabel();
  private JLabel jLabel10 = new JLabel();
  private JLabel jLabel11 = new JLabel();
  private JLabel jLabel12 = new JLabel();
  private XYLayout xYLayout2 = new XYLayout();
  private JLabel jLabel13 = new JLabel();
  private JLabel jLabel14 = new JLabel();
  private JLabel jLabel15 = new JLabel();
  private JLabel jLabel17 = new JLabel();
  private JLabel jLabel16 = new JLabel();
  private XYLayout xYLayout3 = new XYLayout();
  private JLabel jLabel111 = new JLabel();
  private JLabel jLabel110 = new JLabel();
  private JLabel jLabel19 = new JLabel();
  private JPanel jPanel3 = new JPanel();
  private JLabel jLabel20 = new JLabel();
  private JLabel jLabel18 = new JLabel();

  public TOPRunOptPanel(TOPDepotLinkedList mainD, TOPOptLoopConfig cfg,
                     TOPMainFrame main, JDialog d) {
    mainDepots = mainD;
    config = cfg;
    mainFrame = main;
    dialog = d;

    try {
      jbInit();

      JLabel[] labels = new JLabel[6];
      labels[0] = jLabel2;
      labels[1] = jLabel7;
      labels[2] = jLabel8;
      labels[3] = jLabel18;
      labels[4] = jLabel17;
      labels[5] = jLabel16;
      /*runOpts = new TOPRunOptLoopThread(mainDepots, config, labels,
                                     jProgressBar1, runningOptPanel, this,
                                     mainFrame, dialog);
      runOpts.start();*/
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    jLabel1.setText("Current Optimization:");
    this.setLayout(xYLayout1);
    jProgressBar1.setStringPainted(true);
    jProgressBar1.setMaximum(config.getOpts().size());
    jLabel3.setText("Progress");
    jLabel4.setText("Remaining Opts in this loop");
    jLabel5.setText("Remaining Loops:");
    jLabel6.setText("Total Loops Run:");
    jLabel7.setText("jLabel7");
    jLabel8.setText("jLabel8");
    //runningOptPanel = new RunningOptPanel(config.getOpts());
    jPanel2.setBorder(BorderFactory.createEtchedBorder());
    jPanel2.setLayout(xYLayout2);
    jLabel9.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel9.setText("Initial:");
    jLabel10.setText("Cost:");
    jLabel11.setText("Distance:");
    jLabel12.setText("Trucks:");

    //set initial values for the optimization
    //jLabel13.setText(new Double(TOPProblemInfo.depotLLLevelCostF.getTotalConstraintCost(mainDepots)).toString());
    jLabel14.setText(new Double(TOPProblemInfo.getDepotLLLevelCostF().getTotalDistance(mainDepots)).toString());
    jLabel15.setText(new Integer(mainDepots.getTotalNumTrucksUsed()).toString());
    jLabel111.setText("Cost:");
    jLabel110.setText("Distance:");
    jLabel19.setText("Trucks:");
    jPanel3.setLayout(xYLayout3);
    jPanel3.setBorder(BorderFactory.createEtchedBorder());
    jLabel20.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel20.setText("Current:");
    xYLayout1.setWidth(510);
    xYLayout1.setHeight(514);
    jPanel2.add(jLabel10, new XYConstraints(5, 27, 55, -1));
    jPanel2.add(jLabel11, new XYConstraints(5, 49, 53, -1));
    jPanel2.add(jLabel12, new XYConstraints(5, 71, 55, -1));
    jPanel2.add(jLabel13, new XYConstraints(61, 27, 108, 17));
    jPanel2.add(jLabel14, new XYConstraints(61, 49, 108, 17));
    jPanel2.add(jLabel15, new XYConstraints(61, 71, 108, -1));
    jPanel2.add(jLabel9, new XYConstraints(5, 6, 161, -1));
    this.add(jPanel3, new XYConstraints(317, 261, 175, 122));
    jPanel3.add(jLabel111, new XYConstraints(5, 27, 55, -1));
    jPanel3.add(jLabel110, new XYConstraints(5, 49, 53, -1));
    jPanel3.add(jLabel19, new XYConstraints(5, 71, 55, -1));
    jPanel3.add(jLabel18, new XYConstraints(61, 27, 108, 17));
    jPanel3.add(jLabel17, new XYConstraints(61, 49, 108, 17));
    jPanel3.add(jLabel16, new XYConstraints(61, 71, 108, -1));
    jPanel3.add(jLabel20, new XYConstraints(5, 5, 161, -1));
    this.add(jLabel3, new XYConstraints(235, 447, 58, 18));
    this.add(jProgressBar1, new XYConstraints(67, 470, 405, 27));
    this.add(jLabel1, new XYConstraints(11, 8, 128, 25));
    this.add(jLabel4, new XYConstraints(54, 40, 161, 26));
    this.add(jLabel5, new XYConstraints(309, 45, 112, 27));
    this.add(jLabel6, new XYConstraints(309, 74, 109, 23));
    this.add(jLabel7, new XYConstraints(431, 49, 100, 23));
    this.add(jLabel8, new XYConstraints(429, 74, 89, 21));
    this.add(jLabel2, new XYConstraints(148, 8, 178, 24));
    this.add(jScrollPane2, new XYConstraints(44, 92, 244, 343));
    this.add(jPanel2, new XYConstraints(315, 111, 175, 116));
    jScrollPane2.getViewport().add(runningOptPanel);
    jScrollPane1.getViewport().add(jList1, null);
  }
}
