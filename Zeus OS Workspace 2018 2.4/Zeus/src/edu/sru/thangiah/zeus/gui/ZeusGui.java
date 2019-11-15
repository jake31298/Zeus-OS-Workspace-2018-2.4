package edu.sru.thangiah.zeus.gui;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.top.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * The Main class for the Zeus Interface
 * <p>Title: ZeusGui</p>
 * <p>Description: The Main class for the Zeus Interface</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class ZeusGui
    extends JFrame {

  //to create optimization frame or not
  boolean createOptFrame = false;

  //Interface variables
  protected JDesktopPane desktop;
  protected JToolBar toolbar = new JToolBar();
  protected DesktopMenuBar desktopMenuBar;
  protected DepotFrame depotFrame;
  protected ShipmentFrame shipmentFrame;
  protected InfoFrame infoFrame;
  protected FeatureFrame featureFrame;
  protected AboutFrame aboutFrame;

  /**
     * Default constructor, Will create the Zeus Graphical user interface
     */
    public ZeusGui() {
      //run the JFrame constructor
      //super("Zeus - Unified Model for Vehicle Routing Problems");

      //mainDepots = mD;
      //mainShipments = mS;

      createOptFrame = false;

      //this allows for layering of windows.
      desktop = new JDesktopPane();
      this.setContentPane(desktop);

      //make dragging faster by using the outline
      desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

      /*try {
        jbInit();
      }
      catch (Exception e) {
        e.printStackTrace();
      }*/

    }

  /**
   * Constructor, Will create the Zeus Graphical user interface
   * @param mD DepotLinkedList
   * @param mS ShipmentLinkedList
   */
  public ZeusGui(DepotLinkedList mD, ShipmentLinkedList mS) {
    //run the JFrame constructor
    //super("Zeus - Unified Model for Vehicle Routing Problems");

    ZeusGuiInfo.mainDepots = mD;
    ZeusGuiInfo.mainShipments = mS;

    createOptFrame = false;

    //this allows for layering of windows.
    desktop = new JDesktopPane();
    this.setContentPane(desktop);
    ZeusGuiInfo.mainDesktop = desktop;

    //make dragging faster by using the outline
    desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * This constructor will create a new GUI with space filling curves enabled.
   * @param mD DepotLinkedList
   * @param mS ShipmentLinkedList
   * @param SFC double[]
   * @param info String
   */
  public ZeusGui(DepotLinkedList mD, ShipmentLinkedList mS, double[] SFC, String info) {
    //run the JFrame constructor
    super("Zeus - Unified Model for Vehicle Routing Problems");

    ZeusGuiInfo.useSpaceFillingCurves = true;

    ZeusGuiInfo.mainDepots = mD;
    ZeusGuiInfo.mainShipments = mS;
    ZeusGuiInfo.mainFeatures = SFC;
    ZeusGuiInfo.mainFeaturesInfo = info;

    //this allows for layering of windows.
    desktop = new JDesktopPane();
    this.setContentPane(desktop);
    ZeusGuiInfo.mainDesktop = desktop;

    //make dragging faster by using the outline
    desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Initialize graphical objects
   * @throws Exception error
   */
  private void jbInit() throws Exception {
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.pack();

    ZeusGuiInfo.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.setSize(ZeusGuiInfo.screenSize);

    //create the menubar
    desktopMenuBar = new DesktopMenuBar();
    this.setJMenuBar(desktopMenuBar.getJMenuBar());

    //create the toolbar
    createButtons();

    //create the depot frame and place it in the display
    depotFrame = new DepotFrame(ZeusGuiInfo.mainDepots);
    depotFrame.setVisible(ZeusGuiInfo.showDepotPane);
    depotFrame.setBounds(0, 0, depotFrame.getWidth(), depotFrame.getHeight());
    desktop.add(depotFrame);

    //create the shipment frame and place in the display
    shipmentFrame = new ShipmentFrame(ZeusGuiInfo.mainShipments);
    shipmentFrame.setVisible(ZeusGuiInfo.showShipmentPane);
    shipmentFrame.setBounds(depotFrame.getWidth(), 0,
                            shipmentFrame.getWidth(), shipmentFrame.getHeight());
    desktop.add(shipmentFrame);

      //create the info frame and place it in the display
      infoFrame = new InfoFrame(ZeusGuiInfo.mainDepots.getSolutionString());
      infoFrame.setVisible(ZeusGuiInfo.showInfoPane);
      infoFrame.setBounds(this.getWidth() - infoFrame.getWidth(), 0,
                          infoFrame.getWidth(), infoFrame.getHeight());
      desktop.add(infoFrame);


    if(ZeusGuiInfo.useSpaceFillingCurves) {
      featureFrame = new FeatureFrame(ZeusGuiInfo.mainFeaturesInfo);
      featureFrame.setVisible(ZeusGuiInfo.showFeaturePane);
      featureFrame.setBounds(0, depotFrame.getHeight(),
                             featureFrame.getWidth(), featureFrame.getHeight());
      desktop.add(featureFrame);
    }
    aboutFrame = new AboutFrame();
    aboutFrame.setVisible(ZeusGuiInfo.showAboutWindow);
    aboutFrame.setBounds(this.getWidth() / 2 - 200, this.getHeight() / 2 - 300, aboutFrame.getWidth(),
                         aboutFrame.getHeight());
    desktop.add(aboutFrame);

    this.setVisible(true);
  }

  /**
   * Creates the buttons in the toolbar
   */
  private void createButtons() {
    for (int i = 0; i < 8; i++) {
      toolbar.add(new JButton(new Integer(i).toString()));
    }

    desktop.add(toolbar, BorderLayout.CENTER);
  }

  /**
   * Set the title for the GUI
   */
 public void setGUITitle(String title) {
     for (int i = 0; i < 8; i++) {
       toolbar.add(new JButton(new Integer(i).toString()));
     }

     desktop.add(toolbar, BorderLayout.CENTER);
   }


  /**
   * exits the program
   * @param e window event
   */
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);

    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    }
  }

  /**
   * Refreshes all frames in the display
   */
  /*public void refreshAll() {
    depotFrame.refreshDisplay(mainDepots);
    infoFrame.refreshIt(mainDepots.getSolutionString());
  }*/

  /**
   * Main class for testing... not in use anymore
   * @param args command line arguments
   */
  /*public static void main(String[] args) {
    new ZeusGui(null, null, null);
  }*/
}
