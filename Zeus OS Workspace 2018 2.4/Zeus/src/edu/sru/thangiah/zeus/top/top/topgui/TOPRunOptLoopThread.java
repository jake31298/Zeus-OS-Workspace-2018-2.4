package edu.sru.thangiah.zeus.top.topgui;

import edu.sru.thangiah.zeus.core.*;
//import edu.sru.thangiah.zeus.localopts.*;
import java.awt.Font;
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

public class TOPRunOptLoopThread
    extends Thread {
  DepotLinkedList mainDepots;
  //OptLoopConfig config;
  JLabel[] labels;
  JProgressBar bar;
  //RunningOptPanel runningOptPanel;
  //RunOptPanel runOptPanel;
  //MainFrame mainFrame;
  JDialog dialog;

  /*public RunOptLoopThread(DepotLinkedList mainD, OptLoopConfig c, JLabel[] l,
                          JProgressBar b, RunningOptPanel optPanel,
                          RunOptPanel rop,
                          MainFrame main, JDialog d) {
    mainDepots = mainD;
    config = c;
    labels = l;
    bar = b;
    runningOptPanel = optPanel;
    runOptPanel = rop;
    mainFrame = main;
    dialog = d;
  }

  public void run() {
    int maxLoops;
    boolean globalChange = false;

    //determine how many times to run throught the opt list
    if (config.isLoopUntilNoChange()) {
      //if running until no more changes take place, set maxLoops to a
      //very high value
      maxLoops = Integer.MAX_VALUE;
      labels[1].setText("????");
    }
    else {
      //if running for only a predetermined number of loops, set that value
      maxLoops = config.getMaxLoops();
      labels[1].setText(new Integer(maxLoops).toString());
      bar.setMaximum(maxLoops * config.getOpts().size());
    }

    labels[2].setText("0");

    Vector currentLoop;

    for (int j = 0; j < maxLoops; j++) {
      OptInfo info;
      currentLoop = (Vector) config.getOpts().clone();

      //loop for all the opts
      for (int i = 0; i < config.getOpts().size(); i++) {
        SearchStrategy opt = (SearchStrategy) config.getOpts().elementAt(i);
        labels[0].setText(opt.toString());
        System.out.println(opt.toString());
        runningOptPanel.setSelected(i);
        info = opt.run(mainDepots);

        //System.out.println("Did change: " + info.didChange + " isLoopUntilNoChange " + config.isLoopUntilNoChange() + " global change " + globalChange);
        //update progress bar
        bar.setValue(bar.getValue() + 1);

        //update current values
        //labels[3].setText(new Double(info.newCost).toString());
        //labels[4].setText(new Double(info.newDist).toString());
        //labels[5].setText(new Integer(info.newNumTruck).toString());

        /*
                //make the opt's name bold if it has changed.
                if(info.didChange){
                  labels[0].setFont(new Font("Dialog",1,10));
                }
         */

        //if you should break on change, and there was a change, go back to first opt
    /*    if (info.didChange && config.isBreakOnChange()) {
          i = 0;
        }

        //set global change to true, if it was already true, or the opt worked
        globalChange = globalChange || info.didChange;
      }

      //if your dont know when to stop, set value of progress bar to zero
      if (config.isLoopUntilNoChange()) {
        bar.setValue(0);
      }

      //check to see if any changes were made in the loop.
      //if changes were made, and you are looping until there are no changes made
      //keep looping.
      if (globalChange && config.isLoopUntilNoChange()) {
        int num = Integer.parseInt(labels[2].getText());
        num += 1;
        labels[2].setText(new Integer(num).toString());
        j = 0;
      }
      else {
        labels[2].setText(new Integer(j).toString());
        mainFrame.refreshIt(mainDepots, "Optimization Loop");
        dialog.dispose();

        return;
      }

      //set the global change back to false
      globalChange = false;
    }

    mainFrame.refreshIt(mainDepots, "Optimization Loop");
    dialog.dispose();
  }*/
}
