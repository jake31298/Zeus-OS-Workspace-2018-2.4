package edu.sru.thangiah.zeus.gui;

/**
 * The frame to hold information about the currently implemented space filling curve.
 * <p>Title: FeatureFrame</p>
 * <p>Description: The frame to hold information about the currently implemented space filling curve</p>
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Slippery Rock University</p>
 *
 * @author Sam R. Thangiah
 * @version 1.0
 */

import com.borland.jbcl.layout.*;
import java.awt.*;
import java.awt.event.*;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.*;

public class FeatureFrame
    extends JInternalFrame {
  //String containing the attributes of the feature
  String infoString;

  //elements in the gui
  Vector labels = new Vector();
  JPanel panel = new JPanel();
  private XYLayout xYLayout1 = new XYLayout();
  private JLabel header = new JLabel();


  public FeatureFrame(String str) {
    super(ZeusGuiInfo.featurePaneTitle, true, false, false, true);
    infoString = str;

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
    //parse through the attribute string and create a label
    //for each attribute, add the labels to the content pane
    panel.setLayout(xYLayout1);
    header.setText("Feature Data:");
    this.getContentPane().add(panel, BorderLayout.CENTER);
    panel.add(header, new XYConstraints(5, 5, header.getWidth(), 15));

    StringTokenizer info = new StringTokenizer(infoString);
    int height = 20;

    while (info.hasMoreTokens()) {
      String att = info.nextToken("|").trim();
      JLabel label = new JLabel(att);
      panel.add(label, new XYConstraints(10, height, label.getWidth(), 15));
      height += 18;
      labels.add(label);
    }
    this.setSize(400, 100);
  }

}
