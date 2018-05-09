package org.uma.jmetal.runner.multiobjective;

import interfaces.jMetalDinamicValues;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.abyss.ABYSSBuilder;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AbstractAlgorithmRunner;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.ProblemUtils;
import org.uma.jmetal.util.archive.Archive;
import org.uma.jmetal.util.archive.impl.CrowdingDistanceArchive;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

/**
 * This class is the main program used to configure and run AbYSS, a
 * multiobjective scatter search metaheuristics, which is described in:
 *   A.J. Nebro, F. Luna, E. Alba, B. Dorronsoro, J.J. Durillo, A. Beham
 *   "AbYSS: Adapting Scatter Search to Multiobjective Optimization."
 *   IEEE Transactions on Evolutionary Computation. Vol. 12,
 *   No. 4 (August 2008), pp. 439-457
 *
 *   @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class ABYSS extends AbstractAlgorithmRunner implements jMetalDinamicValues {
  private static HashMap<String,Integer> intHmapProperty = new HashMap<String,Integer>();
  private static HashMap<String, Double> doubleHmapProperty = new HashMap<String, Double>();

  @Override
  public HashMap<String, Double> getDoubleHmapProperty() {
    return doubleHmapProperty;
  }

  public ABYSS() {
    //FALTA DEFINIR VALORES DEFAULT
    intHmapProperty.put("maxSize",0);
    intHmapProperty.put("MaxEvaluations",0);
  }

  /**
   * @param args Command line arguments.
   * @throws JMetalException
   * @throws FileNotFoundException
   * Invoking command:
  java org.uma.jmetal.runner.multiobjective.AbYSSRunner problemName [referenceFront]
   */
  public static void main(String[] args) throws Exception {
    DoubleProblem problem;
    Algorithm<List<DoubleSolution>> algorithm;
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

    //CRIAR UMA NOVA INSTANCIA DE DOUBLE PROBLEM E USAR COMO SET DA VARIAVEL PROBLEM
    //DOUBLE PROBLEM É UMA CLASS CUSTOMIZADA

    problem = (DoubleProblem) ProblemUtils.<DoubleSolution> loadProblem(problemName);

    Archive<DoubleSolution> archive = new CrowdingDistanceArchive<DoubleSolution>(intHmapProperty.get("maxSize")) ;

    algorithm = new ABYSSBuilder(problem, archive)
        .setMaxEvaluations(intHmapProperty.get("MaxEvaluations"))
        .build();

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
  public void setIntHmapProperty(HashMap<String, Integer> hmapProperty) {
    if(hmapProperty.size() == hmapProperty.size()){
      this.intHmapProperty = hmapProperty;
    }else{
      throw new IllegalArgumentException();
    }
  }


  //Hash not necessary
  @Override
  public void setDoubleHmapProperty(HashMap<String, Double> hmapProperty) {}

  public HashMap<String,Integer> getIntHmapProperty(){
    return this.intHmapProperty;
  }
}
