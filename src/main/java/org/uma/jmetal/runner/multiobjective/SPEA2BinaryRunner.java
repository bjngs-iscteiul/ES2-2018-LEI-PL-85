package org.uma.jmetal.runner.multiobjective;

import interfaces.jMetalDinamicValues;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2Builder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.AbstractAlgorithmRunner;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.ProblemUtils;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

/**
 * Class for configuring and running the SPEA2 algorithm (binary encoding)
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */

public class SPEA2BinaryRunner extends AbstractAlgorithmRunner implements jMetalDinamicValues {
  private static HashMap<String, Double> doubleHmapProperty = new HashMap<String, Double>();
  private static HashMap<String, Integer> intHmapProperty = new HashMap<String, Integer>();

  public SPEA2BinaryRunner(){
    doubleHmapProperty.put("crossoverProbability",0.9);


    intHmapProperty.put("maxEvaluations",250);
    intHmapProperty.put("populationSize",100);
  }

  @Override
  public void setDoubleHmapProperty(HashMap<String, Double> hmapProperty) {
    if (hmapProperty.size() == hmapProperty.size()) {
      this.doubleHmapProperty = hmapProperty;
    } else {
      throw new IllegalArgumentException;
    }
  }

  @Override
  public void setIntHmapProperty(HashMap<String, Integer> hmapProperty) {
    if (hmapProperty.size() == hmapProperty.size()) {
      this.intHmapProperty = hmapProperty;
    } else {
      throw new IllegalArgumentException;
    }
  }

  @Override
  public HashMap<String, Integer> getIntHmapProperty() {
    return intHmapProperty;
  }

  @Override
  public HashMap<String, Double> getDoubleHmapProperty() {
    return doubleHmapProperty;
  }

  /**
   * @param args Command line arguments.
   * @throws SecurityException
   * Invoking command:
  java org.uma.jmetal.runner.multiobjective.SPEA2BinaryRunner problemName [referenceFront]
   */
  public static void main(String[] args) throws JMetalException, FileNotFoundException {
    BinaryProblem problem;
    Algorithm<List<BinarySolution>> algorithm;
    CrossoverOperator<BinarySolution> crossover;
    MutationOperator<BinarySolution> mutation;
    SelectionOperator<List<BinarySolution>, BinarySolution> selection;

    String referenceParetoFront = "" ;

    String problemName ;
    if (args.length == 1) {
      problemName = args[0];
    } else if (args.length == 2) {
      problemName = args[0] ;
      referenceParetoFront = args[1] ;
    } else {
      problemName = "org.uma.jmetal.problem.multiobjective.OneZeroMax";
      referenceParetoFront = "" ;
    }

    problem = (BinaryProblem)ProblemUtils.<BinarySolution> loadProblem(problemName);

    double crossoverProbability = doubleHmapProperty.get("crossoverProbability") ;
    crossover = new SinglePointCrossover(crossoverProbability) ;

    double mutationProbability = 1.0 / problem.getTotalNumberOfBits() ;
    mutation = new BitFlipMutation(mutationProbability) ;

    selection = new BinaryTournamentSelection<BinarySolution>(new RankingAndCrowdingDistanceComparator<BinarySolution>());

    algorithm = new SPEA2Builder<>(problem, crossover, mutation)
        .setSelectionOperator(selection)
        .setMaxIterations(intHmapProperty.get("maxEvaluations"))
        .setPopulationSize(intHmapProperty.get("populationSize"))
        .build() ;

    new AlgorithmRunner.Executor(algorithm).execute() ;

    List<BinarySolution> population = algorithm.getResult();

    printFinalSolutionSet(population);
    if (!referenceParetoFront.equals("")) {
      printQualityIndicators(population, referenceParetoFront) ;
    }
  }
}
