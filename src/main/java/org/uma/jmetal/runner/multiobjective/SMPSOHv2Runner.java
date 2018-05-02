package org.uma.jmetal.runner.multiobjective;

import interfaces.jMetalDinamicValues;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.smpso.SMPSO;
import org.uma.jmetal.algorithm.multiobjective.smpso.SMPSOBuilder;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.qualityindicator.impl.hypervolume.WFGHypervolume;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AbstractAlgorithmRunner;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.ProblemUtils;
import org.uma.jmetal.util.archive.BoundedArchive;
import org.uma.jmetal.util.archive.impl.HypervolumeArchive;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.util.pseudorandom.impl.MersenneTwisterGenerator;

import java.util.HashMap;
import java.util.List;

/**
 * Class for configuring and running the SMPSO algorithm using an HypervolumeArchive, i.e, the
 * SMPSOhv algorithm described in:
 * A.J Nebro, J.J. Durillo, C.A. Coello Coello. Analysis of Leader Selection Strategies in a
 * Multi-Objective Particle Swarm Optimizer. 2013 IEEE Congress on Evolutionary Computation. June 2013
 * DOI: 10.1109/CEC.2013.6557955
 *
 * This is a variant using the WFG Hypervolume implementation
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class SMPSOHv2Runner extends AbstractAlgorithmRunner implements jMetalDinamicValues {
  private static HashMap<String, Double> doubleHmapProperty = new HashMap<String, Double>();
  private static HashMap<String, Integer> intHmapProperty = new HashMap<String, Integer>();

  public SMPSOHv2Runner (){
    doubleHmapProperty.put("mutationDistributionIndex",20.0);

    intHmapProperty.put("HypervolumeArchive_maxSize",100);
    intHmapProperty.put("MaxIterations",250);
    intHmapProperty.put("SwarmSize",100);
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
   * @param args Command line arguments. The first (optional) argument specifies
   *             the problem to solve.
   * @throws org.uma.jmetal.util.JMetalException
   * @throws java.io.IOException
   * @throws SecurityException
   * Invoking command:
  java org.uma.jmetal.runner.multiobjective.SMPSOHvRunner problemName [referenceFront]
   */
  public static void main(String[] args) throws Exception {
    DoubleProblem problem;
    Algorithm<List<DoubleSolution>> algorithm;
    MutationOperator<DoubleSolution> mutation;

    String referenceParetoFront = "" ;

    String problemName ;
    if (args.length == 1) {
      problemName = args[0];
    } else if (args.length == 2) {
      problemName = args[0] ;
      referenceParetoFront = args[1] ;
    } else {
      //problemName = "org.uma.jmetal.problem.multiobjective.dtlz.DTLZ1";
      //referenceParetoFront = "jmetal-problem/src/test/resources/pareto_fronts/DTLZ1.3D.pf" ;
      problemName = "org.uma.jmetal.problem.multiobjective.zdt.ZDT4";
      referenceParetoFront = "jmetal-problem/src/test/resources/pareto_fronts/ZDT4.pf" ;
    }

    problem = (DoubleProblem) ProblemUtils.<DoubleSolution> loadProblem(problemName);

    BoundedArchive<DoubleSolution> archive =
        new HypervolumeArchive<DoubleSolution>(intHmapProperty.get("HypervolumeArchive_maxSize"), new WFGHypervolume<DoubleSolution>()) ;

    double mutationProbability = 1.0 / problem.getNumberOfVariables() ;
    double mutationDistributionIndex = doubleHmapProperty.get("mutationDistributionIndex") ;
    mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex) ;

    algorithm = new SMPSOBuilder(problem, archive)
        .setMutation(mutation)
        .setMaxIterations(intHmapProperty.get("MaxIterations"))
        .setSwarmSize(intHmapProperty.get("SwarmSize"))
        .setRandomGenerator(new MersenneTwisterGenerator())
        .setSolutionListEvaluator(new SequentialSolutionListEvaluator<DoubleSolution>())
        .build();

    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
        .execute();

    List<DoubleSolution> population = ((SMPSO)algorithm).getResult();
    long computingTime = algorithmRunner.getComputingTime();

    JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

    printFinalSolutionSet(population);
    if (!referenceParetoFront.equals("")) {
      printQualityIndicators(population, referenceParetoFront) ;
    }
  }
}
