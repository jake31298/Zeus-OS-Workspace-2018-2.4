package edu.sru.thangiah.zeus.top.topgui;

import java.awt.event.*;
import javax.swing.*;

/**
 * Holds the menu bar and all its functionality seen in the main window
 * <p>Title: DesktopMenuBar</p>
 * <p>Description: Holds the menu bar and all its functionality seen in the main
 * window</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class TOPDesktopMenuBar {
  private JMenuBar jMenuBar1 = new JMenuBar();
  private JMenu jMenuFile = new JMenu();
  private JMenuItem jMenuFileNew = new JMenuItem();
  private JMenuItem jMenuFileOpen = new JMenuItem();
  private JMenuItem jMenuFileClose = new JMenuItem();
  private JMenuItem jMenuFileSave = new JMenuItem();
  private JMenuItem jMenuFileSaveAs = new JMenuItem();
  private JMenuItem jMenuFileExit = new JMenuItem();
  private JMenu jMenuHelp = new JMenu();
  private JMenuItem jMenuHelpAbout = new JMenuItem();
  private JMenu jMenuWindow = new JMenu();
  private JCheckBoxMenuItem jMenuWindowDepot = new JCheckBoxMenuItem(TOPGuiInfo.depotPaneTitle, TOPGuiInfo.showDepotPane);
  private JCheckBoxMenuItem jMenuWindowShipment = new JCheckBoxMenuItem(TOPGuiInfo.shipmentPaneTitle, TOPGuiInfo.showShipmentPane);
  private JCheckBoxMenuItem jMenuWindowProcess = new JCheckBoxMenuItem(TOPGuiInfo.infoPaneTitle, TOPGuiInfo.showInfoPane);
  private JCheckBoxMenuItem jMenuWindowFeature = new JCheckBoxMenuItem(TOPGuiInfo.featurePaneTitle, TOPGuiInfo.showFeaturePane);
  private JMenu jMenuView = new JMenu("View");
  private JCheckBoxMenuItem jMenuFeatureShow = new JCheckBoxMenuItem(TOPGuiInfo.showFeatureMenuTitle, TOPGuiInfo.showFeatureOverlay);

  /**
   * Constructor. Create the menu bar
   */
  public TOPDesktopMenuBar() {
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
    jMenuWindow.setText("Window");
    jMenuWindowDepot.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TOPGuiInfo.showDepotPane = jMenuWindowDepot.getState();
        showPane(TOPGuiInfo.depotPaneTitle, TOPGuiInfo.showDepotPane);

      }
    });
    jMenuWindowShipment.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TOPGuiInfo.showShipmentPane = jMenuWindowShipment.getState();
        showPane(TOPGuiInfo.shipmentPaneTitle, TOPGuiInfo.showShipmentPane);

      }
    });
    jMenuWindowProcess.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TOPGuiInfo.showInfoPane = jMenuWindowProcess.getState();
        showPane(TOPGuiInfo.infoPaneTitle, TOPGuiInfo.showInfoPane);
      }
    });
    jMenuWindowFeature.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TOPGuiInfo.showFeaturePane = jMenuWindowFeature.getState();
        showPane(TOPGuiInfo.featurePaneTitle, TOPGuiInfo.showFeaturePane);
      }
    });

    jMenuFeatureShow.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
       if(e.getActionCommand() == TOPGuiInfo.showFeatureMenuTitle) {
         TOPGuiInfo.showFeatureOverlay = jMenuFeatureShow.getState();
         JInternalFrame[] j = TOPGuiInfo.mainDesktop.getAllFrames();
         for(int i = 0; i < j.length; i++){
           if(j[i].getTitle() == TOPGuiInfo.routeDisplayTitle)
             ((TOPRouteDisplayFrame)j[i]).displayRepaint();
         }
       }
      }
    });
    jMenuHelp.setText("Help");
    jMenuHelpAbout.setText("About");
    jMenuHelpAbout.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TOPGuiInfo.showAboutWindow = !TOPGuiInfo.showAboutWindow;
        showPane(TOPGuiInfo.aboutWindowTitle, TOPGuiInfo.showAboutWindow);
      }
    });
    jMenuFileNew.setEnabled(false);
    jMenuFileSave.setEnabled(false);
    jMenuFileSaveAs.setEnabled(false);
    jMenuFileOpen.setEnabled(false);
    jMenuFileClose.setEnabled(false);
    jMenuHelpAbout.setEnabled(true);
    jMenuFile.add(jMenuFileNew);
    jMenuFile.add(jMenuFileOpen);
    jMenuFile.add(jMenuFileClose);
    jMenuFile.add(jMenuFileSave);
    jMenuFile.add(jMenuFileSaveAs);
    jMenuFile.addSeparator();
    jMenuFile.add(jMenuFileExit);
    jMenuWindow.add(jMenuWindowDepot);
    jMenuWindow.add(jMenuWindowShipment);
    jMenuWindow.add(jMenuWindowProcess);
    jMenuWindow.add(jMenuWindowFeature);
    jMenuView.add(jMenuFeatureShow);
    jMenuHelp.add(jMenuHelpAbout);
    jMenuBar1.add(jMenuFile);
    jMenuBar1.add(jMenuWindow);
    jMenuBar1.add(jMenuView);
    jMenuBar1.add(jMenuHelp);
  }

  public JMenuBar getJMenuBar() {
    return jMenuBar1;
  }

  private void showPane(String title, boolean toShow) {
    JInternalFrame[] j = TOPGuiInfo.mainDesktop.getAllFrames();
         for(int i = 0; i < j.length; i++){
           if(j[i].getTitle() == title){
             j[i].setVisible(toShow);
             j[i].toFront();
           }
         }

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
   * toggles the depot frame on and off
   * @param e event
   */
  public void jMenuWindowDepot_actionPerformed(ActionEvent e) {
  }

  /**
   * toggles the shipment frame on and off
   * @param e event
   */
  public void jMenuWindowShipment_actionPerformed(ActionEvent e) {
  }

  /**
   * togggles the process event on and off
   * @param e
   */
  public void jMenuWindowProcess_actionPerformed(ActionEvent e) {
  }

  /**
   * executed when the about item in the help menu is selected.
   * @param e event
   */
  public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
  }

  /**
   * exits the program
   * @param e window event
   */
  protected void processWindowEvent(WindowEvent e) {
  }
}
