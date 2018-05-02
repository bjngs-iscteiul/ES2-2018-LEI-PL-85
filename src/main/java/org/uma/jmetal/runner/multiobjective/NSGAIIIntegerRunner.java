package org.uma.jmetal.runner.multiobjective;

import interfaces.jMetalDinamicValues;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.IntegerSBXCrossover;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.AbstractAlgorithmRunner;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.ProblemUtils;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

/**
 * Class for configuring and running the NSGA-II algorithm (integer encoding)
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */

public class NSGAIIIntegerRunner extends AbstractAlgorithmRunner implements jMetalDinamicValues {

  private static HashMap<String, Double> doubleHmapProperty = new HashMap<String, Double>();
  private static HashMap<String, Integer> intHmapProperty = new HashMap<String, Integer>();


  public NSGAIIIntegerRunner(){
    doubleHmapProperty.put("crossoverProbability",0.9);
    doubleHmapProperty.put("crossoverDistributionIndex",20.0);
    doubleHmapProperty.put("mutationDistributionIndex",20.0);

    intHmapProperty.put("maxEvaluations",25000);
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
   * @throws org.uma.jmetal.util.JMetalException
   * @throws java.io.IOException
   * @throws SecurityException
   * @throws ClassNotFoundException
   * Invoking command:
  java org.uma.jmetal.runner.multiobjective.NSGAIIIntegerRunner problemName [referenceFront]
   */
  public static void main(String[] args) throws FileNotFoundException {
    Problem<IntegerSolution> problem;
    Algorithm<List<IntegerSolution>> algorithm;
    CrossoverOperator<IntegerSolution> crossover;
    MutationOperator<IntegerSolution> mutation;
    SelectionOperator<List<IntegerSolution>, IntegerSolution> selection;
    
    String problemName ;

    String referenceParetoFront = "" ;
    if (args.length == 1) {
      problemName = args[0];
    } else if (args.length == 2) {
      problemName = args[0] ;
      referenceParetoFront = args[1] ;
    } else {
      problemName = "org.uma.jmetal.problem.multiobjective.NMMin" ;
      referenceParetoFront = "";
    }

    problem = ProblemUtils.<IntegerSolution> loadProblem(problemName);

    double crossoverProbability = doubleHmapProperty.get("crossoverProbability");
    double crossoverDistributionIndex = doubleHmapProperty.get("crossoverDistributionIndex");
    crossover = new IntegerSBXCrossover(crossoverProbability, crossoverDistributionIndex) ;

    double mutationProbability = 1.0 / problem.getNumberOfVariables() ;
    double mutationDistributionIndex = doubleHmapProperty.get("mutationDistributionIndex");
    mutation = new IntegerPolynomialMutation(mutationProbability, mutationDistributionIndex) ;

    selection = new BinaryTournamentSelection<IntegerSolution>() ;

    algorithm = new NSGAIIBuilder<IntegerSolution>(problem, crossover, mutation)
            .setSelectionOperator(selection)
            .setMaxEvaluations(intHmapProperty.get("maxEvaluations"),)
            .setPopulationSize(intHmapProperty.get("populationSize"),)
            .build() ;

    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
            .execute() ;

    List<IntegerSolution> population = algorithm.getResult() ;
    long computingTime = algorithmRunner.getComputingTime() ;

    JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

    printFinalSolutionSet(population);
    if (!referenceParetoFront.equals("")) {
      printQualityIndicators(population, referenceParetoFront) ;
    }
  }
}
