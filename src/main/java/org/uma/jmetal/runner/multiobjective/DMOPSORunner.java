package org.uma.jmetal.runner.multiobjective;

import interfaces.jMetalDinamicValues;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.dmopso.DMOPSO;
import org.uma.jmetal.algorithm.multiobjective.dmopso.DMOPSO.FunctionType;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AbstractAlgorithmRunner;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.ProblemUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Class for configuring and running the DMOPSO algorithm
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */

public class DMOPSORunner extends AbstractAlgorithmRunner implements jMetalDinamicValues {

  private static HashMap<String, Double> doubleHmapProperty = new HashMap<String, Double>();
  private static HashMap<String, Integer> intHmapProperty = new HashMap<String, Integer>();

  public DMOPSORunner() {
    intHmapProperty.put("swarmSize",100);
    intHmapProperty.put("maxIterations",150);
    doubleHmapProperty.put("r1Min",0.0);
    doubleHmapProperty.put("r1Max",0.1);
    doubleHmapProperty.put("r2Min",0.0);
    doubleHmapProperty.put("r2Max",1.0);
    doubleHmapProperty.put("c1Min",1.5);
    doubleHmapProperty.put("c1Max",2.5);
    doubleHmapProperty.put("c2Min",1.5);
    doubleHmapProperty.put("c2Max",2.5);
    doubleHmapProperty.put("weightMin",0.1);
    doubleHmapProperty.put("weightMax",0.4);
    doubleHmapProperty.put("changeVelocity1",-1.0);
    doubleHmapProperty.put("changeVelocity2",-1.0);
    intHmapProperty.put("maxAge",2);
  }

  /**
   * @param args Command line arguments.
   * @throws org.uma.jmetal.util.JMetalException
   * @throws java.io.IOException
   * @throws SecurityException
   * Invoking command:
  java org.uma.jmetal.runner.multiobjective.DMOPSORunner problemName [referenceFront]
   */
  public static void main(String[] args) throws Exception {
    DoubleProblem problem;
    Algorithm<List<DoubleSolution>> algorithm;

    String referenceParetoFront = "" ;

    String problemName ;
    if (args.length == 1) {
      problemName = args[0];
    } else if (args.length == 2) {
      problemName = args[0] ;
      referenceParetoFront = args[1] ;
    } else {
      problemName = "org.uma.jmetal.problem.multiobjective.zdt.ZDT1";
      referenceParetoFront = "../jmetal-problem/src/test/resources/pareto_fronts/ZDT1.pf" ;
    }

    problem = (DoubleProblem) ProblemUtils.<DoubleSolution> loadProblem(problemName);

    algorithm = new DMOPSO(problem,
            intHmapProperty.get("swarmSize"),
            intHmapProperty.get("maxIterations"),
            doubleHmapProperty.get("r1Min"),
            doubleHmapProperty.get("r1Max"),
            doubleHmapProperty.get("r2Min"),
            doubleHmapProperty.get("r2Max"),
            doubleHmapProperty.get("c1Min"),
            doubleHmapProperty.get("c1Max"),
            doubleHmapProperty.get("c2Min"),
            doubleHmapProperty.get("c2Max"),
            doubleHmapProperty.get("weightMin"),
            doubleHmapProperty.get("weightMax"),
            doubleHmapProperty.get("changeVelocity1"),
            doubleHmapProperty.get("changeVelocity2"),
            FunctionType.TCHE, "MOEAD_Weights", intHmapProperty.get("maxAge"));

    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
        .execute();

    List<DoubleSolution> population = algorithm.getResult();
    long computingTime = algorithmRunner.getComputingTime();

    JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

    printFinalSolutionSet(population);
    if (!referenceParetoFront.equals("")) {
      printQualityIndicators(population, referenceParetoFront) ;
    }
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

}
