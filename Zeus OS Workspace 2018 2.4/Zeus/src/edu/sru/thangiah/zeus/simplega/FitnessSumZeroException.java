package edu.sru.thangiah.zeus.simplega;

public class FitnessSumZeroException extends SelectionException {
   FitnessSumZeroException() {
      super("ProportionalSelection: population fitness sums to zero");
   }
}
