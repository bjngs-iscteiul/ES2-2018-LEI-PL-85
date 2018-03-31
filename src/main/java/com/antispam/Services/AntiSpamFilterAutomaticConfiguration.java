package com.antispam.Services;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;


import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.zdt.*;
import org.uma.jmetal.qualityindicator.impl.*;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.experiment.Experiment;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.component.*;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootApplication
public class AntiSpamFilterAutomaticConfiguration {
    private final int INDEPENDENT_RUNS = 5 ;
    private Problem problem ;
    private static String AlgorithmName;




    public AntiSpamFilterAutomaticConfiguration(Problem problem){
        this.problem = problem;
    }


    public void INIT(String AlgorithmName) throws IOException {
        String experimentBaseDirectory = "experimentBaseDirectory";

        List<ExperimentProblem<DoubleSolution>> problemList = new ArrayList<ExperimentProblem<DoubleSolution>>();


        problemList.add(new ExperimentProblem<>(problem, "Problem"));

        List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithmList =
                configureAlgorithmList(problemList);


        try{
            Experiment<DoubleSolution, List<DoubleSolution>> experiment =
                    new ExperimentBuilder<DoubleSolution, List<DoubleSolution>>("AntiSpamStudy")
                            .setAlgorithmList(algorithmList)
                            .setProblemList(problemList)
                            .setExperimentBaseDirectory(experimentBaseDirectory)
                            .setOutputParetoFrontFileName("FUN")
                            .setOutputParetoSetFileName("VAR")
                            .setReferenceFrontDirectory(experimentBaseDirectory+"/referenceFronts")
                            .setIndicatorList(Arrays.asList(
                                    new PISAHypervolume<DoubleSolution>()
                            ))
                            .setIndependentRuns(INDEPENDENT_RUNS)
                            .setNumberOfCores(8)
                            .build();


            //Debug.getInstance().msg("experiment"+experiment.toString());

            new ExecuteAlgorithms<>(experiment).run();
            new GenerateReferenceParetoSetAndFrontFromDoubleSolutions(experiment).run();
            new ComputeQualityIndicators<>(experiment).run() ;
            new GenerateLatexTablesWithStatistics(experiment).run() ;
            new GenerateBoxplotsWithR<>(experiment).setRows(1).setColumns(1).run() ;


        } catch (Exception e) {
            System.out.println("Erro no experiment jmetal");
            //System.out.println("Experiment" + experiment);
        }
       
    }

    static List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> configureAlgorithmList(
            List<ExperimentProblem<DoubleSolution>> problemList) {

        List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithms = new ArrayList<>();

        for (int i = 0; i < problemList.size(); i++) {
            Algorithm<List<DoubleSolution>> algorithm =
                    new NSGAIIBuilder<>(
                            problemList.get(i).getProblem(),
                            new SBXCrossover(1.0, 5),
                            new PolynomialMutation(1.0 / problemList.get(i).getProblem().getNumberOfVariables(), 10.0)
                    ).setMaxEvaluations(25000)
                            .setPopulationSize(100)
                            .build();
            algorithms.add(new ExperimentAlgorithm<>(algorithm, AlgorithmName, problemList.get(i).getTag()));
        }
        return algorithms;
    }

    public Problem getProblem() {
        return this.problem;
    }

}
