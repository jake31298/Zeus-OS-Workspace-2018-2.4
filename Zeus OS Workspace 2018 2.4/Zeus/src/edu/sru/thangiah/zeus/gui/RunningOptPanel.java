package edu.sru.thangiah.zeus.gui;

import com.borland.jbcl.layout.*;
//import edu.sru.thangiah.zeus.localopts.*;
import java.awt.*;
import java.util.Vector;
import javax.swing.*;

/**
 * Panel that shows the list of opts
 * <p>Title: Mixed Fleet</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class RunningOptPanel
    extends JComponent {
  Vector opts;
  Vector icon = new Vector();
  Vector names = new Vector();
  int selectedIndex = 0;
  private XYLayout xYLayout1 = new XYLayout();
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();

  public RunningOptPanel(Vector optimizations) {
    opts = optimizations;

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setBackground(Color.white);
    xYLayout1.setWidth(219);
    xYLayout1.setHeight(380);
    this.setLayout(xYLayout1);

    int x = 3;

    for (int i = 0; i < opts.size(); i++) {
      //SearchStrategy o = (SearchStrategy) opts.elementAt(i);
      //names.add(new JLabel(o.toString()));
      icon.add(new JLabel());
      this.add( (JLabel) icon.elementAt(i), new XYConstraints(3, x, 23, 21));
      this.add( (JLabel) names.elementAt(i),
               new XYConstraints(29, x, 170, 21));
      x += 23;
    }
  }

  /**
   * Will put an arrow next to the selected optimization
   * @param index which opt to put the arrow next to
   */
  public void setSelected(int index) {
    //remove the arrow from the previously selected optimization
    JLabel oldLabel = (JLabel) icon.elementAt(selectedIndex);
    oldLabel.setIcon(null);

    //put the arrow next to the newly selected optimization
    selectedIndex = index;

    ImageIcon image = new ImageIcon("img/arrow.gif");
    JLabel label = (JLabel) icon.elementAt(selectedIndex);
    label.setIcon(image);
  }
}
