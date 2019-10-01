package edu.sru.thangiah.zeus.top.topgui;

import com.borland.jbcl.layout.*;
import edu.sru.thangiah.zeus.core.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;

/**
 * This panel allows for the configuration of the opt looping tool.
 * <p>Title: Zeus - A Unified Object Oriented Model for VRP's</p>
 * <p>Description: This panel allows for the configuration of the opt looping tool.
 * All changes made in this panel are saved in a class called OptLoopConfig.
 * The configuration can be obtained by calling the getConfiguration() method.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class TOPOptimizationPanel
    extends JPanel {
  public static final int CANCEL = 0;
  public static final int OK = 1;
  private JDialog dialog; //dialog this panel is displayed in
  private Vector executeString = new Vector(); //vector of opts to do
  private Vector optimizations = new Vector(); //vector of all opts
  private XYLayout xYLayout1 = new XYLayout();
  private JList allMethodList = new JList();
  private JButton addButton = new JButton();
  private JButton addAllButton = new JButton();
  private JButton removeButton = new JButton();
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JButton upButton = new JButton();
  private JButton downButton = new JButton();
  private JScrollPane jScrollPane2 = new JScrollPane();
  private JButton okButton = new JButton();
  private JButton cancelButton = new JButton();
  private int exitStatus;
  private JCheckBox jCheckBox1 = new JCheckBox();
  private ButtonGroup buttonGroup1 = new ButtonGroup();
  private JRadioButton jRadioButton1 = new JRadioButton();
  private JRadioButton jRadioButton2 = new JRadioButton();
  private JTextField jTextField1 = new JTextField();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JList methodUsedList = new JList();
  private JButton removeButton1 = new JButton();

  /**
   * Constuctor
   * @param execString the current list of opts used
   * @param opt all the opts possible
   * @param d the dialog box the panel is in
   */
  public TOPOptimizationPanel(Vector execString, Vector opt, JDialog d) {
    dialog = d;
    optimizations = opt;
    executeString = execString;
    methodUsedList = new JList(executeString);
    allMethodList = new JList(optimizations);

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * This method initilizes the visual components of the class.
   * @throws Exception for exceptions
   */
  private void jbInit() throws Exception {
    this.setLayout(xYLayout1);
    addButton.setText("add");
    addButton.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        addButton_mouseClicked(e);
      }
    });
    addAllButton.setText("add all");
    addAllButton.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        addAllButton_mouseClicked(e);
      }
    });
    removeButton.setText("remove");
    removeButton.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        removeButton_mouseClicked(e);
      }
    });
    xYLayout1.setWidth(388);
    xYLayout1.setHeight(467);
    jLabel1.setText("Optimization Methods:");
    jLabel2.setText("Methods Used:");
    upButton.setText("up");
    upButton.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        upButton_mouseClicked(e);
      }
    });
    downButton.setText("down");
    downButton.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        downButton_mouseClicked(e);
      }
    });
    allMethodList.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        allMethodList_focusGained(e);
      }
    });
    okButton.setText("OK");
    okButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okButton_actionPerformed(e);
      }
    });
    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelButton_actionPerformed(e);
      }
    });
    jCheckBox1.setToolTipText(
        "This will start the optimization loop back at the beginning each " +
        "time there is a change made in the optimization");
    jCheckBox1.setText("Break On Change");
    jRadioButton1.setSelected(true);
    jRadioButton1.setText("loop until no more changes");
    jRadioButton2.setText("loop until reached max of:");

    jTextField1.setText("0");
    methodUsedList.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        methodUsedList_focusGained(e);
      }
    });
    removeButton1.setText("rem all");
    removeButton1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        removeButton1_mouseClicked(e);
      }
    });

    buttonGroup1.add(jRadioButton1);
    buttonGroup1.add(jRadioButton2);
    this.add(addButton, new XYConstraints(156, 56, 80, 37));
    this.add(jLabel1, new XYConstraints(11, 17, 129, 23));
    this.add(jLabel2, new XYConstraints(229, 16, 141, 24));
    this.add(addAllButton, new XYConstraints(156, 95, 80, 37));
    this.add(removeButton, new XYConstraints(156, 134, 80, 37));
    this.add(jScrollPane2, new XYConstraints(7, 45, 134, 244));
    this.add(jCheckBox1, new XYConstraints(50, 301, 176, 23));
    this.add(cancelButton, new XYConstraints(201, 388, 96, 35));
    this.add(okButton, new XYConstraints(72, 389, 96, 35));
    this.add(jRadioButton1, new XYConstraints(50, 327, 176, 23));
    this.add(jRadioButton2, new XYConstraints(50, 350, 166, 23));
    this.add(jTextField1, new XYConstraints(216, 351, 72, -1));
    this.add(jScrollPane1, new XYConstraints(241, 42, 138, 255));
    this.add(removeButton1, new XYConstraints(156, 170, 80, 37));
    this.add(upButton, new XYConstraints(156, 207, 80, 37));
    this.add(downButton, new XYConstraints(156, 245, 80, 37));
    jScrollPane1.getViewport().add(methodUsedList, null);
    jScrollPane2.getViewport().add(allMethodList, null);
  }

  /**
   * Will return the configuration settings for the opt loop
   * @return the configuration settings.
   */
  public TOPOptLoopConfig getConfiguration() {
    int numLoops;

    try {
      numLoops = Integer.parseInt(jTextField1.getText());
    }
    catch (NumberFormatException nfe) {
      numLoops = 0;
    }

    return new TOPOptLoopConfig(executeString, jCheckBox1.isSelected(),
                             jRadioButton1.isSelected(), numLoops);
  }

  /**
   * Method for the clicking of the Add button. Will add a method
   * to the list of opts to execute.
   * @param e add button clicked
   */
  void addButton_mouseClicked(MouseEvent e) {
    //add method
    if (allMethodList.getSelectedIndex() >= 0) {
      executeString.add(optimizations.elementAt(
          allMethodList.getSelectedIndex()));
      methodUsedList.setListData(executeString);
    }
  }

  /**
   * Method for clicking the add all button. Will add all the methods
   * to the list of opts to execute.
   * @param e add all button clicked
   */
  void addAllButton_mouseClicked(MouseEvent e) {
    //add all methods
    executeString = optimizations;
    methodUsedList.setListData(executeString);
  }

  /**
   * Method executed after clicking the remove button. Will remove
   * an opt from the list of opts to execute
   * @param e remove button clicked
   */
  void removeButton_mouseClicked(MouseEvent e) {
    //remove method
    if (methodUsedList.getSelectedIndex() >= 0) {
      executeString.remove(methodUsedList.getSelectedIndex());
      methodUsedList.setListData(executeString);
    }
  }

  /**
   * Method executed when the focus is in the all method list.
   * This will disable the buttons that dont work for that list,
   * and enable the ones that do.
   * @param e allMethodList has focus
   */
  void allMethodList_focusGained(FocusEvent e) {
    removeButton.setEnabled(false);
    removeButton1.setEnabled(false);
    upButton.setEnabled(false);
    downButton.setEnabled(false);
    addButton.setEnabled(true);
    addAllButton.setEnabled(true);
  }

  /**
   * Method executed when the methodUsedList has focus. This
   * will disable the buttons that dont work for that list, and
   * enable the ones that do
   * @param e methodUsedList has focus
   */
  void methodUsedList_focusGained(FocusEvent e) {
    removeButton.setEnabled(true);
    removeButton1.setEnabled(true);
    upButton.setEnabled(true);
    downButton.setEnabled(true);
    addButton.setEnabled(false);
    addAllButton.setEnabled(false);
  }

  /**
   * Method executed when the up button is clicked. This will
   * shift an opt in the methodsUsedList up one space.
   * @param e up button clicked
   */
  void upButton_mouseClicked(MouseEvent e) {
    int index = methodUsedList.getSelectedIndex();

    if (index > 0) {
      Object o = executeString.remove(index);
      executeString.insertElementAt(o, index - 1);
      methodUsedList.setListData(executeString);
      methodUsedList.setSelectedIndex(index - 1);
    }
  }

  /**
   * Method executed when the down button is clicked. This
   * will shift an opt in the methodUsedList down one space.
   * @param e down button clicked
   */
  void downButton_mouseClicked(MouseEvent e) {
    int index = methodUsedList.getSelectedIndex();

    if ( (index < (executeString.size() - 1)) && (index > -1)) {
      Object o = executeString.remove(index);
      executeString.insertElementAt(o, index + 1);
      methodUsedList.setListData(executeString);
      methodUsedList.setSelectedIndex(index + 1);
    }
  }

  /**
   * Method called when the OK button is clicked. This will
   * close the dialog
   * @param e OK button clicked
   */
  void okButton_actionPerformed(ActionEvent e) {
    this.exitStatus = OK;
    dialog.dispose();
  }

  /**
   * Method called when the cancel button is clicked. This will
   * close the dialog.
   * @param e Cancel button is clicked
   */
  void cancelButton_actionPerformed(ActionEvent e) {
    this.exitStatus = CANCEL;
    dialog.dispose();
  }

  /**
   * This method returns whether the dialog was closed using the OK
   * or Cancel buttons
   * @return exit status.
   */
  public int getExitStatus() {
    return exitStatus;
  }

  /**
   * This method executes when the rem all button is clicked. The
   * methodsUsedList is cleared
   * @param e rem all button pressed.
   */
  void removeButton1_mouseClicked(MouseEvent e) {
    executeString.removeAllElements();
    methodUsedList.setListData(executeString);
  }
}
