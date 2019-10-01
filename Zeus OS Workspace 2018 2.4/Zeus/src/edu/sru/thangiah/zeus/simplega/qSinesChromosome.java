package edu.sru.thangiah.zeus.simplega;

//import GAutilities.*;

/*
 * This chromosome class implements encoding the problem of maximizing
 * 21.5+x1*sin(4*PIx1)+x2*sin(20*PI*x2)+x3 for x1 in [-3.0,12.1],
 * x2 in [4.1,5.8], and x3 in [0.0, 1.0].
 */
public
class qSinesChromosome extends DoubleChromosome {// this user-defined class
                                               // implements evalChromosome,
   protected qSinesChromosome() {              // toPhenotype;
      super(file,parent);                               // defines chromosomeLength,
   }                                        // knownSolutionFitness, and
                                           // solutionFitness
   static {
      chromosomeLength = 1;
      Globals.stdout.println("SinesChromosome: chromosome length is "
         + chromosomeLength);
      knownSolutionFitness = false;
      solutionFitness = -1;
      // compiler forbids this :-( geneLowerBound = {-3.0, 4.1, 0.0};
      double[] lower = {-5}; geneLowerBound = lower;
      double[] upper = {5}; geneUpperBound = upper;
   }

   protected double evalChromosome() {
      double fitness = gene[0]*gene[0];
      if (fitness <= 0) {
         System.err.println("fitness of " + fitness + " at x1=" + gene[0] + " is <= 0");
         System.exit(1);
      }
      return fitness;
   }

   public String toPhenotype() {
      String phenotype = gene[0] + "*" + gene[0];
      return phenotype;
   }
}
