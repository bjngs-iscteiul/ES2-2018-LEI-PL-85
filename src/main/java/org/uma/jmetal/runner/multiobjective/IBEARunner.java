package org.uma.jmetal.runner.multiobjective;

import interfaces.jMetalDinamicValues;
import org.uma.jmetal.algorithm.multiobjective.ibea.IBEA;
import org.uma.jmetal.algorithm.multiobjective.ibea.IBEABuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AbstractAlgorithmRunner;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.ProblemUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Class for configuring and running the IBEA algorithm
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class IBEARunner extends AbstractAlgorithmRunner implements jMetalDinamicValues {

  private static HashMap<String, Double> doubleHmapProperty = new HashMap<String, Double>();
  private static HashMap<String, Integer> intHmapProperty = new HashMap<String, Integer>();

  public IBEARunner() {
    doubleHmapProperty.put("crossoverProbability",0.9);
    doubleHmapProperty.put("crossoverDistributionIndex",20.0);
    doubleHmapProperty.put("mutationDistributionIndex",20.0);
    intHmapProperty.put("setArchiveSize",100);
    intHmapProperty.put("setPopulationSize",100);
    intHmapProperty.put("setMaxEvaluations",25000);
  }

  @Override
  public void setDoubleHmapProperty(HashMap<String, Double> hmapProperty) {
    if (hmapProperty.size() == hmapProperty.size()) {
      this.doubleHmapProperty = hmapProperty;
    } else {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public void setIntHmapProperty(HashMap<String, Integer> hmapProperty) {
    if (hmapProperty.size() == hmapProperty.size()) {
      this.intHmapProperty = hmapProperty;
    } else {
      throw new IllegalArgumentException();
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
   * @throws java.io.IOException
   * @throws SecurityException
   * @throws ClassNotFoundException
   * Invoking command:
  java org.uma.jmetal.runner.multiobjective.IBEARunner problemName [referenceFront]
   */
  public static void main(String[] args) throws Exception {
    Problem<DoubleSolution> problem;
    IBEA<DoubleSolution> algorithm;
    CrossoverOperator<DoubleSolution> crossover;
    MutationOperator<DoubleSolution> mutation;
    SelectionOperator<List<DoubleSolution>, DoubleSolution> selection;

    String problemName ;
    String referenceParetoFront = "" ;
    if (args.length == 1) {
      problemName = args[0];
    } else if (args.length == 2) {
      problemName = args[0] ;
      referenceParetoFront = args[1] ;
    } else {
      problemName = "org.uma.jmetal.problem.multiobjective.zdt.ZDT1";
      referenceParetoFront = "jmetal-problem/src/test/resources/pareto_fronts/ZDT1.pf" ;
    }

    problem = ProblemUtils.loadProblem(problemName);

    double crossoverProbability = doubleHmapProperty.get("crossoverProbability") ;

    double crossoverDistributionIndex = doubleHmapProperty.get("crossoverDistributionIndex") ;


    crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex) ;

    double mutationProbability = 1.0 / problem.getNumberOfVariables() ;
    double mutationDistributionIndex = doubleHmapProperty.get("mutationDistributionIndex"); ;


    mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex) ;

    selection = new BinaryTournamentSelection<DoubleSolution>() ;

    algorithm = new IBEABuilder(problem)
      .setArchiveSize(intHmapProperty.get("setArchiveSize"))
      .setPopulationSize(intHmapProperty.get("setPopulationSize"))
      .setMaxEvaluations(intHmapProperty.get("setMaxEvaluations"))
      .setCrossover(crossover)
      .setMutation(mutation)
      .setSelection(selection)
      .build() ;

    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
      .execute() ;

    List<DoubleSolution> population = algorithm.getResult() ;
    long computingTime = algorithmRunner.getComputingTime() ;

    JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

    printFinalSolutionSet(population);
    if (!referenceParetoFront.equals("")) {
      printQualityIndicators(population, referenceParetoFront) ;
    }
  }
}
