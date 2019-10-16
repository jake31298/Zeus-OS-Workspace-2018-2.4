package edu.sru.thangiah.zeus.top;

import edu.sru.thangiah.zeus.localopts.*;
import edu.sru.thangiah.zeus.localopts.interopts.FirstFirstInterSearch;
import edu.sru.thangiah.zeus.top.TOPProblemInfo.TOPCostType;




public class TOPOptEntry {
  public static final long serialVersionUID = 1;
  private SearchStrategy opt;
  private TOPProblemInfo.TOPCostType costType;

  
  public TOPOptEntry(SearchStrategy o, TOPCostType c) {
    opt = o;
    costType = c;
  }
  
  
  
  public TOPOptEntry(FirstFirstInterSearch firstFirstInterSearch, TOPCostType c) {
	  opt = firstFirstInterSearch;
	  costType = c;
  }



public TOPOptEntry(Object firstFirstInterSearch, TOPProblemInfo.TOPCostType scoreInverse) {
	// TODO Auto-generated constructor stub
}


public OptInfo run(TOPDepotLinkedList mainDepots) {
    TOPProblemInfo.costType = costType;

    return opt.run(mainDepots);
  }

  public String toString() {
    return opt.toString();
  }
}
