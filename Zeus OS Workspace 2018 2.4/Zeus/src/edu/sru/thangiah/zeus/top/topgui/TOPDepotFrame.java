package edu.sru.thangiah.zeus.top.topgui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.util.*;
import com.brunchboy.util.swing.relativelayout.*;
import edu.sru.thangiah.zeus.top.*;

import java.io.File;


/**
 * This Frame holds the Tree with the depot information
 * <p>Title: DepotFrame</p>
 * <p>Description: This Frame holds the Tree with the depot information</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */
public class TOPDepotFrame extends JInternalFrame {
    //Zeus Variables
    private TOPDepotLinkedList mainDepots;
    private TOPShipmentLinkedList mainShipments;

    //Interface Variables
    private JPanel panel = new JPanel();
    private JToolBar toolbar = new JToolBar();
    private DefaultMutableTreeNode root = null;
    private JTree tree = null;
    private RelativeLayout layout = new RelativeLayout();
    private JScrollPane depotScrollPane = new JScrollPane();

    /**
     * DepotFrame Constructor.  Accepts a DepotLinkedList to be displayed in the frame.
     * @param mD DepotLinkedList
     */
    public TOPDepotFrame(TOPDepotLinkedList mD, TOPShipmentLinkedList mS) {
        super(TOPGuiInfo.depotPaneTitle, true, false, false, true);
        mainDepots = mD;
        mainShipments = mS;

        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize graphical objects
     * @throws Exception error
     */
    private void jbInit() throws Exception {
        this.getContentPane().setLayout(layout);

        JButton expand = new JButton("Expand");
        expand.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    expandAllRoutes(tree, true);
                }
            });
        toolbar.add(expand);

        JButton collapse = new JButton("Collapse");
        collapse.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
                    refreshDisplay(mainDepots);
                }
            });
        toolbar.add(collapse);

        JButton rdisplay = new JButton("Route Display");
        rdisplay.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                  TOPRouteDisplayFrame rdf = new TOPRouteDisplayFrame(mainShipments);
                  rdf.setVisible(true);
                  rdf.setBounds(0, 0, rdf.getWidth(), rdf.getHeight());
                  TOPGuiInfo.mainDesktop.add(rdf);
                  rdf.toFront();
                }
            });
        toolbar.add(rdisplay);

        tree = makeTree(mainDepots);
        depotScrollPane = new JScrollPane(tree);

        this.getContentPane().add(toolbar, "toolbar");
        this.getContentPane().add(depotScrollPane, "depotScrollPane");

        this.setSize(400, 600);
        readConstraints();
      }

  /**
   * readConstraints
   * Reads the constraints from the DepotFrameLayout.xml file to organize the layout of the objects in the depot frame.
   */
  public void readConstraints() {
    // Read the layout constraints
    XmlConstraintBuilder db_XMLBuilderLayout = new XmlConstraintBuilder();
    try {
      db_XMLBuilderLayout.addConstraints(new File("etc\\DepotFrameLayout.xml"), layout);   // Read the contraints from the layout XML file.
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

  }

  /**
     * Makes a tree from the depot linked list
     * @param d the depot linked list
     * @return a tree of the depot linked list
     */
    private JTree makeTree(TOPDepotLinkedList d) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(
                "The Depot Linked List");
        TOPDepot tempDepot = d.getTOPHead();
        String pointInfo = "";
        int nextIndex = 0, prevIndex = 0;


        while (tempDepot != null) {
            DefaultMutableTreeNode tempDNode = new DefaultMutableTreeNode(tempDepot.toString());
            TOPTruck tempTruck = tempDepot.getTOPMainTrucks().getTOPHead();

            while (tempTruck != null) {
                if ((tempTruck.isEmpty() == false) && (tempTruck.getIsTeamMember() == true)) {
                    DefaultMutableTreeNode tempTNode = new DefaultMutableTreeNode(tempTruck.toString());
                    TOPNodes point = tempTruck.getTOPMainNodes().getTOPHead();

                    while (point != null) {
                      if(point.getTOPPrev() == null)
                        prevIndex = -1;
                      else if(point.getTOPNext() == null)
                        nextIndex = -1;
                      else {
                        nextIndex = point.getTOPNext().getIndex();
                        prevIndex = point.getTOPPrev().getIndex();
                      }

                      pointInfo = "#" + point.getIndex() + " | Demand: " +
                          point.getDemand() + " | Prev: " + prevIndex +
                          " | Next: " + nextIndex;

                      DefaultMutableTreeNode tempPNode = new DefaultMutableTreeNode(pointInfo);
                      tempTNode.add(tempPNode);
                      point = point.getTOPNext();
                    }

                    tempDNode.add(tempTNode);
                }

                tempTruck = tempTruck.getTOPNext();
            }

            root.add(tempDNode);
            tempDepot = tempDepot.getTOPNext();
        }

        JTree tree = new JTree(root);

        return tree;
    }

    /**
     * Expands all the objects in the depot JTree.
     * @param t JTree
     * @param ex boolean
     */
    private void expandAllRoutes(JTree t, boolean ex) {
      TreeNode root = (TreeNode)t.getModel().getRoot();

        // Traverse tree from root
        expandAll(tree, new TreePath(root), ex);
    }

    /**
     * Generic method to expand the components of a JTree object.
     * @param t JTree
     * @param parent TreePath
     * @param expand boolean
     */
    private void expandAll(JTree t, TreePath parent, boolean expand) {
        // Traverse children
        TreeNode node = (TreeNode)parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (Enumeration e=node.children(); e.hasMoreElements(); ) {
                TreeNode n = (TreeNode)e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(t, path, expand);
            }
        }

        // Expansion or collapse must be done bottom-up
        if (expand) {
            t.expandPath(parent);
        } else {
            t.collapsePath(parent);
        }
    }

    /**
     * Refreshes the depot linked list tree
     * @param mD the depot linked list
     */
    public void refreshDisplay(TOPDepotLinkedList mD) {
        mainDepots = mD;
        tree = makeTree(mainDepots);
        depotScrollPane.setViewportView(tree);
    }
}
