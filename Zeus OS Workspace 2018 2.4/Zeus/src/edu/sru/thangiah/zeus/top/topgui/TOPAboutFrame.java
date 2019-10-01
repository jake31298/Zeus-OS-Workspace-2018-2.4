package edu.sru.thangiah.zeus.top.topgui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.brunchboy.util.swing.relativelayout.*;
import java.io.File;

/**
 * A redesign of the route display, to incorporate a fluid gui system.
 * <p>Title: AboutFrame</p>
 * <p>Description: An about box for displaying general information about the GUI devs.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Slippery Rock University</p>
 * @author Sam R. Thangiah
 * @version 1.0
 */
public class TOPAboutFrame
    extends JInternalFrame {

  private RelativeLayout layout = new RelativeLayout();
  private JLabel label_1 = new JLabel(TOPGuiInfo.AboutLabel1, JLabel.CENTER);
  private JLabel label_2 = new JLabel(TOPGuiInfo.AboutLabel2, JLabel.CENTER);
  private JLabel label_3 = new JLabel(TOPGuiInfo.AboutLabel3, JLabel.CENTER);
  private JLabel label_4 = new JLabel(TOPGuiInfo.AboutLabel4, JLabel.CENTER);
  private JLabel label_5 = new JLabel(TOPGuiInfo.AboutLabel5, JLabel.CENTER);
  private JLabel label_6 = new JLabel(TOPGuiInfo.AboutLabel6, JLabel.CENTER);
  private JButton closeButton = new JButton("OK");

  /**
   * AboutFrame Constructor
   */
  public TOPAboutFrame() {
    super(TOPGuiInfo.aboutWindowTitle, false, false, false, false);
    this.getContentPane().setLayout(layout);
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Initialize graphical objects in the AboutFrame
   * These objects are defined in the ZeusGuiInfo class
   * @throws Exception error
   */
  private void jbInit() throws Exception {
    this.getContentPane().add(label_1, "label_1");
    this.getContentPane().add(label_2, "label_2");
    this.getContentPane().add(label_3, "label_3");
    this.getContentPane().add(label_4, "label_4");
    this.getContentPane().add(label_5, "label_5");
    this.getContentPane().add(label_6, "label_6");
    this.getContentPane().add(closeButton, "closeButton");

    closeButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TOPGuiInfo.showAboutWindow = !TOPGuiInfo.showAboutWindow;
        JInternalFrame[] j = TOPGuiInfo.mainDesktop.getAllFrames();
         for(int i = 0; i < j.length; i++){
           if(j[i].getTitle() == TOPGuiInfo.aboutWindowTitle){
             j[i].setVisible(TOPGuiInfo.showAboutWindow);
             j[i].toFront();
           }
         }
      }
    });
    readConstraints();
    this.setSize(400, 210);
  }


  /**
   * readConstraints
   * Will read the constraints from the AboutWindowLayout.xml file.
   * These constraints will determine the layout of the objects in the AboutFrame.
   */
  public void readConstraints() {
    // Read the layout constraints
    XmlConstraintBuilder db_XMLBuilderLayout = new XmlConstraintBuilder();
    try {
      db_XMLBuilderLayout.addConstraints(new File("etc\\AboutWindowLayout.xml"), layout);   // Read the contraints from the layout XML file.
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

  }


}
