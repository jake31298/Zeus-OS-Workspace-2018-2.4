package edu.sru.thangiah.zeus.gui;

import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 * <p>Title: Mixed Fleet</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class ZeusGraph
    extends JComponent {
  Vector data;

  public ZeusGraph(Vector d) {
    super();
    data = d;
  }

  public void setData(Vector d) {
    data = d;
  }

  public void paint(Graphics g) {
    int width = this.getWidth();
    int height = this.getHeight();
    g.drawLine(3, 3, 3, height - 3);
    g.drawLine(3, height - 3, width - 3, height - 3);
    g.drawString("Hello", 20, 20);
  }

  private double getMaxValueInData() {
    double max = 0;

    for (int i = 0; i < data.size(); i++) {
      Double x = (Double) data.elementAt(i);
      max = Math.max(max, x.doubleValue());
    }

    return max;
  }
}
