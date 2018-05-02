package org.uma.jmetal.runner.multiobjective;

import interfaces.jMetalDinamicValues;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.cellde.CellDE45;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.*;
import org.uma.jmetal.util.archive.impl.CrowdingDistanceArchive;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.util.neighborhood.impl.C9;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

/**
 * Class to configure and run the MOCell algorithm
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class CellDERunner extends AbstractAlgorithmRunner implements jMetalDinamicValues {
    private static HashMap<String, Integer> intHmapProperty = new HashMap<String, Integer>();
    private static HashMap<String, Double> doubleHmapProperty = new HashMap<String, Double>();

    @Override
    public HashMap<String, Double> getDoubleHmapProperty() {
        return doubleHmapProperty;
    }

    public CellDERunner() {
        intHmapProperty.put("maxSize", 100);
        intHmapProperty.put("maxEvaluations", 50000);
        intHmapProperty.put("populationSize", 100);
    }

    /**
     * @param args Command line arguments.
     * @throws JMetalException
     * @throws FileNotFoundException Invoking command:
     *                               java org.uma.jmetal.runner.multiobjective.MOCellRunner problemName [referenceFront]
     */
    public static void main(String[] args) throws JMetalException, FileNotFoundException {
        Problem<DoubleSolution> problem;
        Algorithm<List<DoubleSolution>> algorithm;
        SelectionOperator<List<DoubleSolution>, DoubleSolution> selection;
        DifferentialEvolutionCrossover crossover;
        String referenceParetoFront = "";

        String problemName;
        if (args.length == 1) {
            problemName = args[0];
        } else if (args.length == 2) {
            problemName = args[0];
            referenceParetoFront = args[1];
        } else {
            problemName = "org.uma.jmetal.problem.multiobjective.dtlz.DTLZ1";
            referenceParetoFront = "jmetal-problem/src/test/resources/pareto_fronts/DTLZ1.3D.pf";
        }

        problem = ProblemUtils.<DoubleSolution>loadProblem(problemName);

        double cr = 0.5;
        double f = 0.5;

        crossover = new DifferentialEvolutionCrossover(cr, f, "rand/1/bin");

        selection = new BinaryTournamentSelection<DoubleSolution>(new RankingAndCrowdingDistanceComparator<DoubleSolution>());

        algorithm = new CellDE45(
                problem,
                intHmapProperty.get("maxEvaluations"),
                intHmapProperty.get("populationSize"),
                new CrowdingDistanceArchive<DoubleSolution>(intHmapProperty.get("maxSize")),
                new C9<DoubleSolution>((int) Math.sqrt(100), (int) Math.sqrt(100)),
                selection,
                crossover,
                20,
                new SequentialSolutionListEvaluator<DoubleSolution>()
        );

        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        List<DoubleSolution> population = algorithm.getResult();
        long computingTime = algorithmRunner.getComputingTime();

        JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

        printFinalSolutionSet(population);
        if (!referenceParetoFront.equals("")) {
            printQualityIndicators(population, referenceParetoFront);
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
    public void setDoubleHmapProperty(HashMap<String, Double> hmapProperty) {

    }

    public HashMap<String, Integer> getIntHmapProperty() {
        return this.intHmapProperty;
    }
}
