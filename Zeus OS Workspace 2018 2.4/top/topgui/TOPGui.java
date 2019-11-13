package edu.sru.thangiah.zeus.top.topgui;

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

public class TOPGui
    extends JFrame {

  //to create optimization frame or not
  boolean createOptFrame = false;

  //Interface variables
  protected JDesktopPane desktop;
  protected JToolBar toolbar = new JToolBar();
  protected TOPDesktopMenuBar desktopMenuBar;
  protected TOPDepotFrame depotFrame;
  protected TOPShipmentFrame shipmentFrame;
  protected TOPInfoFrame infoFrame;
  protected TOPAboutFrame aboutFrame;

  /**
  * Default constructor, Will create the Zeus Graphical user interface
  */
  public TOPGui() {
    createOptFrame = false;

    //this allows for layering of windows.
    desktop = new JDesktopPane();
    this.setContentPane(desktop);

    //make dragging faster by using the outline
    desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
  }

  /**
   * Constructor, Will create the Zeus Graphical user interface
   * @param mainDepots DepotLinkedList
   * @param mainShipments ShipmentLinkedList
   */
  public TOPGui(TOPDepotLinkedList mainDepots, TOPShipmentLinkedList mainShipments) {
    TOPGuiInfo.mainDepots = mainDepots;
    TOPGuiInfo.mainShipments = mainShipments;

    createOptFrame = false;

    //this allows for layering of windows.
    desktop = new JDesktopPane();
    this.setContentPane(desktop);
    TOPGuiInfo.mainDesktop = desktop;

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

    TOPGuiInfo.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.setSize(TOPGuiInfo.screenSize);

    //create the menubar
    desktopMenuBar = new TOPDesktopMenuBar();
    this.setJMenuBar(desktopMenuBar.getJMenuBar());

    //create the toolbar
    createButtons();

    //create the depot frame and place it in the display
    depotFrame = new TOPDepotFrame(TOPGuiInfo.mainDepots, TOPGuiInfo.mainShipments);
    depotFrame.setVisible(TOPGuiInfo.showDepotPane);
    depotFrame.setBounds(0, 0, depotFrame.getWidth(), depotFrame.getHeight());
    desktop.add(depotFrame);

    //create the shipment frame and place in the display
    shipmentFrame = new TOPShipmentFrame(TOPGuiInfo.mainShipments);
    shipmentFrame.setVisible(TOPGuiInfo.showShipmentPane);
    shipmentFrame.setBounds(depotFrame.getWidth(), 0,
                            shipmentFrame.getWidth(), shipmentFrame.getHeight());
    desktop.add(shipmentFrame);

      //create the info frame and place it in the display
      infoFrame = new TOPInfoFrame(TOPGuiInfo.mainDepots.getTeamSolutionString());
      infoFrame.setVisible(TOPGuiInfo.showInfoPane);
      infoFrame.setBounds(this.getWidth() - infoFrame.getWidth(), 0,
                          infoFrame.getWidth(), infoFrame.getHeight());
      desktop.add(infoFrame);

    aboutFrame = new TOPAboutFrame();
    aboutFrame.setVisible(TOPGuiInfo.showAboutWindow);
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
}
