package edu.sru.thangiah.opsys.top.topgui;

import javax.swing.*;
import java.awt.*;
import edu.sru.thangiah.opsys.top.*;

public class TOPGuiInfo {
  public static JDesktopPane mainDesktop; // The instance of the mainDesktop
  public static Dimension screenSize; // The dimenstions of the current screen
  public static boolean showMouseXY = true; // Should we display the mouse X and Y coordinates in the GUI?

  public static TOPDepotLinkedList mainDepots; // The DepotLinkedList passed into the GUI
  public static TOPShipmentLinkedList mainShipments; // The ShipmentLinkedList passed into the GUI

  // Pane Titles
  public static String depotPaneTitle = "Depots";
  public static String shipmentPaneTitle = "Shipments";
  public static String infoPaneTitle = "Solution";
  public static String featurePaneTitle = "Algorithm";
  public static String routeDisplayTitle = "Route Display";
  public static String aboutWindowTitle = "About ZeusGui";

  // Menu Options
  public static String showFeatureMenuTitle = "Show Algorithm Overlay"; // Title for the menu option.
  public static boolean showDepotPane = true; // Show the Depot pane in the GUI
  public static boolean showShipmentPane = true; // Show the shipment pane in the GUI
  public static boolean showInfoPane = true; // Show the information pane in the GUI
  public static boolean showFeaturePane = true; // Show the feature pane in the GUI
  public static boolean showFeatureOverlay = true; // Show the feature overlay in the display window
  public static boolean showAboutWindow = false; // By default our about window is hidden.

  // Labels for use in the about window.
  public static String AboutLabel1 = "ZeusGui: A Visual Interface for the Zeus VRP Program.";
  public static String AboutLabel2 = "Original Concept: Dr. Sam Thangiah";
  public static String AboutLabel3 = " ";
  public static String AboutLabel4 = "Additions/Modifications By:";
  public static String AboutLabel5 = "Andrew Beers(2005)";
  public static String AboutLabel6 = "Sam R. Thangiah(2006)";

}
