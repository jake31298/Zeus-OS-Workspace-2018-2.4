package edu.sru.thangiah.zeus.top.topgui;

import com.borland.jbcl.layout.*;
import java.awt.*;
import java.awt.event.*;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.*;

/**
 * Has labels that holds the general information for the problem you are solving
 * <p>Title: InfoFrame</p>
 * <p>Description: Has labels that holds the general information for the problem
 * you are solving</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class TOPInfoFrame
    extends JInternalFrame {
  //String containing the attributes of the depot linked list
  String infoString;

  //elements in the gui
  Vector labels = new Vector();
  JPanel panel = new JPanel();
  private XYLayout xYLayout1 = new XYLayout();
  private JLabel header = new JLabel();

  public TOPInfoFrame(String info) {
    super(TOPGuiInfo.infoPaneTitle, true, false, false, true);
    infoString = info;
System.out.println(infoString);
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
    header.setText("Solution:");
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

    this.setSize(200, 300);
  }

  /**
   * Update the frame
   * @param s string with the new data
   */
  public void refreshIt(String s) {
    infoString = s;

    StringTokenizer info = new StringTokenizer(infoString);
    int count = 0;

    while (info.hasMoreTokens()) {
      String att = info.nextToken("|").trim();
      JLabel label = (JLabel) labels.elementAt(count);
      label.setText(att);
      count++;
    }
  }
}
