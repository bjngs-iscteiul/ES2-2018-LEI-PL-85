package org.uma.jmetal.runner.multiobjective;

import interfaces.jMetalDinamicValues;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIIIBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.*;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

import java.util.HashMap;
import java.util.List;

/**
 * Class to configure and run the NSGA-III algorithm
 */
public class NSGAIIIRunner extends AbstractAlgorithmRunner implements jMetalDinamicValues {
    private static HashMap<String, Double> doubleHmapProperty = new HashMap<String, Double>();
    private static HashMap<String, Integer> intHmapProperty = new HashMap<String, Integer>();

    public NSGAIIIRunner(){
        doubleHmapProperty.put("crossoverProbability",0.9);
        doubleHmapProperty.put("crossoverDistributionIndex",30.0);
        doubleHmapProperty.put("mutationDistributionIndex",20.0);

        intHmapProperty.put("MaxIterations",300);
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
   * @throws java.io.IOException
   * @throws SecurityException
   * @throws ClassNotFoundException
   * Usage: three options
   *        - org.uma.jmetal.runner.multiobjective.NSGAIIIRunner
   *        - org.uma.jmetal.runner.multiobjective.NSGAIIIRunner problemName
   *        - org.uma.jmetal.runner.multiobjective.NSGAIIIRunner problemName paretoFrontFile
   */
  public static void main(String[] args) throws JMetalException {
	    Problem<DoubleSolution> problem;
	    Algorithm<List<DoubleSolution>> algorithm;
	    CrossoverOperator<DoubleSolution> crossover;
	    MutationOperator<DoubleSolution> mutation;
	    SelectionOperator<List<DoubleSolution>, DoubleSolution> selection;

    String problemName = "org.uma.jmetal.problem.multiobjective.dtlz.DTLZ1" ;

    problem = ProblemUtils.loadProblem(problemName);
    
    double crossoverProbability = doubleHmapProperty.get("crossoverProbability");
    double crossoverDistributionIndex = doubleHmapProperty.get("crossoverDistributionIndex");
    crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex) ;

    double mutationProbability = 1.0 / problem.getNumberOfVariables() ;
    double mutationDistributionIndex = doubleHmapProperty.get("mutationDistributionIndex");
    mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex) ;
    
    selection = new BinaryTournamentSelection<DoubleSolution>();
    
    algorithm = new NSGAIIIBuilder<>(problem)
            .setCrossoverOperator(crossover)
            .setMutationOperator(mutation)
            .setSelectionOperator(selection)
            .setMaxIterations(intHmapProperty.get("MaxIterations"))
            .build() ;

    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
            .execute() ;

    List<DoubleSolution> population = algorithm.getResult() ;
    long computingTime = algorithmRunner.getComputingTime() ;

    new SolutionListOutput(population)
            .setSeparator("\t")
            .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
            .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv"))
            .print() ;

    JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
    JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
    JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");
  }
}
