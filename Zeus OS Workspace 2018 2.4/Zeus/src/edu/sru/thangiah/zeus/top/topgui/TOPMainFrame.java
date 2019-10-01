package edu.sru.thangiah.zeus.top.topgui;

import com.borland.jbcl.layout.*;
import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.tree.*;
import edu.sru.thangiah.zeus.localopts.OptInfo;

/**
 * The frame seen in the graphical user interface
 * <p>Title: Mixed Fleet</p>
 * <p>Description: Frame for the Graphical User Interface</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class TOPMainFrame
    extends JFrame {
  private DepotLinkedList mainDepots;
  private Vector optimizations = new Vector();
  private TOPOptLoopConfig optConfig = new TOPOptLoopConfig(optimizations, false,
      true, 0);
  private DefaultMutableTreeNode root = null;
  private JTree tree = null;
  private JPanel contentPane;
  private XYLayout xYLayout1 = new XYLayout();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JLabel jLabel3 = new JLabel();
  private JLabel jLabel4 = new JLabel();
  private JLabel jLabel5 = new JLabel();
  private JLabel jLabel6 = new JLabel();
  private JLabel jLabel7 = new JLabel();
  private JLabel jLabel8 = new JLabel();
  private JLabel jLabel9 = new JLabel();
  private JLabel jLabel10 = new JLabel();
  private JLabel jLabel11 = new JLabel();
  private JLabel jLabel12 = new JLabel();
  private JLabel jLabel13 = new JLabel();
  private JLabel jLabel14 = new JLabel();
  private JLabel jLabel15 = new JLabel();
  private JLabel jLabel16 = new JLabel();
  private JLabel jLabel17 = new JLabel();
  private JLabel jLabel18 = new JLabel();
  private JLabel jLabel19 = new JLabel();
  private JLabel jLabel20 = new JLabel();
  private JLabel jLabel21 = new JLabel();
  private JLabel jLabel22 = new JLabel();
  private JLabel jLabel23 = new JLabel();
  private JLabel jLabel24 = new JLabel();
  private JLabel jLabel25 = new JLabel();
  private JLabel jLabel26 = new JLabel();
  private JLabel jLabel27 = new JLabel();
  private JLabel jLabel28 = new JLabel();
  private JLabel jLabel29 = new JLabel();
  private JLabel jLabel30 = new JLabel();
  private JLabel jLabel31 = new JLabel();
  private JLabel jLabel32 = new JLabel();
  private JLabel jLabel33 = new JLabel();
  private JLabel jLabel34 = new JLabel();

  //variables for the menu bar
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenuFile = new JMenu();
  JMenuItem jMenuFileNew = new JMenuItem();
  JMenuItem jMenuFileOpen = new JMenuItem();
  JMenuItem jMenuFileClose = new JMenuItem();
  JMenuItem jMenuFileSave = new JMenuItem();
  JMenuItem jMenuFileSaveAs = new JMenuItem();
  JMenuItem jMenuFileExit = new JMenuItem();
  JMenu jMenuOpt = new JMenu();
  JMenuItem jMenuOptConfigure = new JMenuItem();
  JMenuItem jMenuOptRunOptLoop = new JMenuItem();
  JMenu jMenuHelp = new JMenu();
  JMenuItem jMenuHelpAbout = new JMenuItem();

  //variable for tool bar
  private JToolBar jToolBar1 = new JToolBar();

  /**
   * Construct the frame
   * @param md depot linked list to show
   */
  public TOPMainFrame(DepotLinkedList md, Vector opt) {
    mainDepots = md;
    optimizations = opt;
    optConfig = new TOPOptLoopConfig(optimizations, false, true, 0);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Initialize the visual components of the frame
   * @throws Exception
   */
  private void jbInit() throws Exception {
    this.setIconImage(new ImageIcon("img/bus.gif").getImage());
    contentPane = (JPanel)this.getContentPane();
    contentPane.setLayout(xYLayout1);
    this.setSize(new Dimension(863, 799));
    this.setTitle("Rural School Bus Routing");
    jLabel1.setFont(new java.awt.Font("Serif", 1, 12));
    jLabel1.setText("Total Demand:");
    jLabel2.setFont(new java.awt.Font("Serif", 1, 12));
    jLabel2.setText("Total Buses:");
    jLabel3.setFont(new java.awt.Font("Serif", 1, 12));
    jLabel3.setText("Total Distance");
    jLabel4.setText("jLabel4");
    jLabel5.setText("jLabel5");
    jLabel6.setText("jLabel6");
    jLabel7.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel7.setText("Travel Time:");
    jLabel8.setFont(new java.awt.Font("Serif", 1, 12));
    jLabel8.setText("Max:");
    jLabel9.setFont(new java.awt.Font("Serif", 1, 12));
    jLabel9.setText("Avg:");
    jLabel10.setFont(new java.awt.Font("Serif", 1, 12));
    jLabel10.setText("Total:");
    jLabel11.setText("jLabel11");
    jLabel12.setText("jLabel12");
    jLabel13.setText("jLabel13");
    jLabel14.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel14.setText("Cost:");
    jLabel15.setFont(new java.awt.Font("Serif", 1, 12));
    jLabel15.setText("Turn Around Penalty:");
    jLabel16.setFont(new java.awt.Font("Serif", 1, 12));
    jLabel16.setText("Cross Road Penalty:");
    jLabel17.setFont(new java.awt.Font("Serif", 1, 12));
    jLabel17.setText("Route Cost:");
    jLabel18.setFont(new java.awt.Font("Serif", 1, 12));
    jLabel18.setText("Total Constraint:");
    jLabel19.setText("jLabel19");
    jLabel20.setText("jLabel20");
    jLabel21.setText("jLabel21");
    jLabel22.setText("jLabel22");
    setLabels();
    tree = makeTree(mainDepots);
    jScrollPane1 = new JScrollPane(tree);
    jLabel23.setText("Recently Run:");
    jLabel24.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel24.setText("Changes:");
    jLabel25.setFont(new java.awt.Font("Serif", 1, 12));
    jLabel25.setText("Changed:");
    jLabel27.setFont(new java.awt.Font("Serif", 1, 12));
    jLabel27.setText("Num Changes:");
    jLabel29.setFont(new java.awt.Font("Serif", 1, 12));
    jLabel29.setText("Cost decrease%");
    jLabel30.setFont(new java.awt.Font("Serif", 1, 12));
    jLabel30.setText("Dist decrease%");
    jLabel31.setFont(new java.awt.Font("Serif", 1, 12));
    jLabel31.setText("Bus decrease:");

    //buttons for toolbar
    contentPane.add(jLabel19, new XYConstraints(681, 24, 156, 27));
    contentPane.add(jLabel3, new XYConstraints(3, 74, 85, 27));
    contentPane.add(jLabel2, new XYConstraints(3, 39, 82, 27));
    contentPane.add(jLabel1, new XYConstraints(3, 8, 89, 27));
    contentPane.add(jLabel4, new XYConstraints(117, 8, 135, 27));
    contentPane.add(jLabel5, new XYConstraints(117, 39, 129, 27));
    contentPane.add(jLabel6, new XYConstraints(117, 74, 129, 27));
    contentPane.add(jLabel7, new XYConstraints(270, 0, 119, 27));
    contentPane.add(jLabel8, new XYConstraints(304, 33, 28, 27));
    contentPane.add(jLabel9, new XYConstraints(304, 64, 46, 27));
    contentPane.add(jLabel10, new XYConstraints(304, 95, 60, 27));
    contentPane.add(jLabel13, new XYConstraints(340, 94, 156, 27));
    contentPane.add(jLabel12, new XYConstraints(340, 64, 156, 27));
    contentPane.add(jLabel11, new XYConstraints(340, 33, 156, 27));
    contentPane.add(jLabel14, new XYConstraints(525, 0, 79, 27));
    contentPane.add(jLabel15, new XYConstraints(540, 24, 133, 27));
    contentPane.add(jLabel16, new XYConstraints(540, 47, 133, 27));
    contentPane.add(jLabel17, new XYConstraints(540, 71, 133, 27));
    contentPane.add(jLabel18, new XYConstraints(540, 95, 133, 27));
    contentPane.add(jLabel22, new XYConstraints(681, 95, 156, 27));
    contentPane.add(jLabel21, new XYConstraints(681, 71, 156, 27));
    contentPane.add(jLabel20, new XYConstraints(681, 47, 156, 27));
    contentPane.add(jLabel29, new XYConstraints(583, 646, 134, 21));
    contentPane.add(jLabel23, new XYConstraints(7, 687, 354, 26));
    contentPane.add(jLabel24, new XYConstraints(373, 640, 80, 27));
    contentPane.add(jLabel25, new XYConstraints(387, 672, 78, 21));
    contentPane.add(jLabel27, new XYConstraints(387, 696, 87, 18));
    contentPane.add(jLabel31, new XYConstraints(583, 694, 124, 20));
    contentPane.add(jLabel30, new XYConstraints(583, 667, 123, 26));
    jToolBar1.setFloatable(false);

    createMenuBar();
    contentPane.add(jScrollPane1, new XYConstraints(4, 154, 844, 481));
    contentPane.add(jLabel28, new XYConstraints(4, 744, 49, 21));
    contentPane.add(jLabel26, new XYConstraints(4, 723, 77, 21));
    contentPane.add(jLabel34, new XYConstraints(4, 747, 126, 18));
    contentPane.add(jLabel33, new XYConstraints(4, 720, 117, 24));
    contentPane.add(jLabel32, new XYConstraints(4, 694, 122, 24));
    contentPane.add(jToolBar1, new XYConstraints(4, 121, 842, -1));
  }

  /**
   * Makes a tree from the depot linked list
   * @param d the depot linked list
   * @return a tree of the depot linked list
   */
  private JTree makeTree(DepotLinkedList d) {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode(
        "The Depot Linked List");
    Depot tempDepot = d.getHead();

    while (tempDepot != null) {
      DefaultMutableTreeNode tempDNode = new DefaultMutableTreeNode(tempDepot.
          toString());
      Truck tempTruck = tempDepot.getMainTrucks().getHead();

      while (tempTruck != null) {
        //if (!tempTruck.isEmpty()) { // Original
    	  if (!((List) tempTruck).isEmpty()) { // Added 1 OCT 2019
          DefaultMutableTreeNode tempTNode = new DefaultMutableTreeNode(
              tempTruck.toString());
          Nodes point = tempTruck.getMainNodes().getHead();

          while (point != null) {
            DefaultMutableTreeNode tempPNode = new DefaultMutableTreeNode(point.
                toString());
            tempTNode.add(tempPNode);
            point = point.getNext();
          }

          tempDNode.add(tempTNode);
        }

        tempTruck = tempTruck.getNext();
      }

      root.add(tempDNode);
      tempDepot = tempDepot.getNext();
    }

    JTree tree = new JTree(root);
    tree.setCellRenderer(new TOPCustomCellRenderer());

    return tree;
  }

  /**
   * Sets the main labels to their proper values
   */
  private void setLabels() {
    /*
             jLabel4.setText(new Integer(mainDepots.totalDemand).toString());
     jLabel5.setText(new Integer(mainDepots.getTotalNumBusesUsed()).toString());
             jLabel6.setText(new Double(mainDepots.totalDistance).toString());
             jLabel11.setText(new Double(mainDepots.maxTravelTime).toString());
             jLabel12.setText(new Double(mainDepots.avgTravelTime).toString());
     jLabel13.setText(new Double(mainDepots.totalTravelTime).toString());
     jLabel19.setText(new Double(mainDepots.totalTurnAroundPenaltyCost).toString());
     jLabel20.setText(new Double(mainDepots.totalCrossRoadPenaltyCost).toString());
             jLabel21.setText(new Double(mainDepots.totalCost).toString());
     jLabel22.setText(new Double(mainDepots.totalConstraintCost).toString());
     */
  }

  /**
   * Refreshes the frame with new information
   * @param d new depot linked list
   * @param label string describing what was just done
   */
  public void refreshIt(DepotLinkedList d, String label) {
    /*
             SBVRPData.mainDepots = mainDepots = d;
             tree = makeTree(d);
             jScrollPane1.setViewportView(tree);
             setLabels();
             jLabel23.setText("Recently Run: " + label);
     */
  }

  /**
   * Refreshes the frame with new information
   * @param d new depot linked list
   * @param label string describing what was just done
   * @param optInfo information about was was jsut done
   */
  public void refreshIt(DepotLinkedList d, String label, OptInfo optInfo) {
    /*
             SBVRPData.mainDepots = mainDepots = d;
             tree = makeTree(d);
             jScrollPane1.setViewportView(tree);
             setLabels();
             jLabel26.setText(new Boolean(optInfo.didChange).toString());
             jLabel28.setText(new Integer(optInfo.numChanges).toString());
             jLabel32.setText(new Double(SBVRPConstants.roundToDecPlaces(optInfo.costDecrease,5)).toString());
             jLabel33.setText(new Double(SBVRPConstants.roundToDecPlaces(optInfo.distDecrease,5)).toString());
             jLabel34.setText(new Integer(optInfo.truckDecrease).toString());
             jLabel23.setText("Recently Run: " + label);
     */
  }

  /**
   * Macro to create the menu bar and all the menu items in it
   */
  private void createMenuBar() {
    jMenuFile.setText("File");
    jMenuFileNew.setText("New");
    jMenuFileSave.setText("Save");
    jMenuFileSave.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuFileSave_actionPerformed(e);
      }
    });
    jMenuFileSaveAs.setText("Save As...");
    jMenuFileSaveAs.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuFileSaveAs_actionPerformed(e);
      }
    });
    jMenuFileOpen.setText("Open");
    jMenuFileOpen.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuFileOpen_actionPerformed(e);
      }
    });
    jMenuFileClose.setText("Close");
    jMenuFileClose.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuFileClose_actionPerformed(e);
      }
    });

    jMenuFileExit.setText("Exit");
    jMenuFileExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuFileExit_actionPerformed(e);
      }
    });

    jMenuOpt.setText("Optimization");
    jMenuOptConfigure.setText("Configure");
    jMenuOptConfigure.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuOptConfigure_actionPerformed(e);
      }
    });
    jMenuOptRunOptLoop.setText("Run Opt Loop");
    jMenuOptRunOptLoop.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuOptRunOptLoop_actionPerformed(e);
      }
    });
    jMenuHelp.setText("Help");
    jMenuHelpAbout.setText("About");
    jMenuHelpAbout.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuHelpAbout_actionPerformed(e);
      }
    });
    jMenuFileNew.setEnabled(false);
    jMenuFileSave.setEnabled(false);
    jMenuFileSaveAs.setEnabled(false);
    jMenuHelpAbout.setEnabled(false);
    jMenuFileClose.setEnabled(false);
    jMenuFile.add(jMenuFileNew);
    jMenuFile.add(jMenuFileOpen);
    jMenuFile.add(jMenuFileClose);
    jMenuFile.add(jMenuFileSave);
    jMenuFile.add(jMenuFileSaveAs);
    jMenuFile.addSeparator();
    jMenuFile.add(jMenuFileExit);
    jMenuOpt.add(jMenuOptConfigure);
    jMenuOpt.add(jMenuOptRunOptLoop);
    jMenuHelp.add(jMenuHelpAbout);
    jMenuBar1.add(jMenuFile);
    jMenuBar1.add(jMenuOpt);
    jMenuBar1.add(jMenuHelp);
    this.setJMenuBar(jMenuBar1);
  }

  /**
   * Executes when the Open item in the file menu is clicked. Opens a new file
   * @param e event
   */
  public void jMenuFileOpen_actionPerformed(ActionEvent e) {
  }

  /**
   * Executed when the Save item in the File menu is selected. Not implemented.
   * @param e event
   */
  public void jMenuFileSave_actionPerformed(ActionEvent e) {
  }

  /**
   * Executed whtn the Save As.. item in the file meny is selected. Not implemented
   * @param e event
   */
  public void jMenuFileSaveAs_actionPerformed(ActionEvent e) {
    /*        fileChooser.showSaveDialog(this);
            File file = fileChooser.getSelectedFile();
            System.out.println("saving file to " + file);*/
  }

  /**
   * Executed when the Close item in the file menu is selected.
   * staus - not working correctly
   * @param e event
   */
  public void jMenuFileClose_actionPerformed(ActionEvent e) {
  }

  /**
   * Executed when the Exit item in the File menu is selected
   * @param e event
   */
  public void jMenuFileExit_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

  /**
   * Executed when the configure item in the Opt menu is selected
   * @param e event
   */
  public void jMenuOptConfigure_actionPerformed(ActionEvent e) {
    JDialog dialog = new JDialog(this, "Optimization", true);
    TOPOptimizationPanel panel = new TOPOptimizationPanel( (Vector) optConfig.getOpts()
        .clone(),
        optimizations, dialog);
    Container dialogContentPane = dialog.getContentPane();

    //add the dialog's contents
    dialogContentPane.add(panel, BorderLayout.CENTER);
    dialog.pack();

    //open the dialog
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);

    //once the dialog is closed, if it was not canceled, get the new
    //optimization configuration
    if (panel.getExitStatus() == TOPOptimizationPanel.OK) {
      optConfig = panel.getConfiguration();
    }
  }

  /**
   * Executed when the configure item in the Opt menu is selected
   * @param e event
   */
  public void jMenuOptRunOptLoop_actionPerformed(ActionEvent e) {
    JDialog dialog = new JDialog(this, "Run Optimizations", true);
    //TOPRunOptPanel panel = new TOPRunOptPanel(mainDepots, optConfig, this, dialog);
    Container dialogContentPane = dialog.getContentPane();

    //add the dialog's contents
    //dialogContentPane.add(panel, BorderLayout.CENTER);
    dialog.pack();

    //open the dialog
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
  }

  /**
   * executed when the about item in the help menu is selected.
   * @param e event
   */
  public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
  }

  //Overridden so we can exit when window is closed
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);

    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    }
  }
}
