package com.antispam.Services;

import com.antispam.Services.JMetal.Factory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.experiment.Experiment;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.component.*;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@SpringBootApplication
public class AntiSpamFilterAutomaticConfiguration {
    private static String AlgorithmName;
    private final int INDEPENDENT_RUNS = 5;
    private static AntiSpamFilterProblem AntiSpamFilterProblem = new AntiSpamFilterProblem();
    public AntiSpamFilterAutomaticConfiguration (){}
    public AntiSpamFilterAutomaticConfiguration (AntiSpamFilterProblem AntiSpamFilterProblem){this.AntiSpamFilterProblem = AntiSpamFilterProblem;}


    public void INIT(String algorithmName) throws IOException {
        Tools.Debug.IN("INIT algorithmName=[" + algorithmName);
        String experimentBaseDirectory = "experimentBaseDirectory";

        List<ExperimentProblem<DoubleSolution>> problemList = new ArrayList<>();

        //TODO Criar uma nova instância da class "algorithmName" usando Srting como nome.
        // exemplo: Criar uma class que permita dar o nome do algo
        //   ritmo

        problemList.add(new ExperimentProblem<>(AntiSpamFilterProblem, "AntiSpamFilterProblem"));

        List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithmList =
                configureAlgorithmList(problemList);


        try {
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

            //TODO usar o nome de cada algoritmo como nome de Class. (POO Object name)
            new ExecuteAlgorithms<>(experiment).run();
            new GenerateReferenceParetoSetAndFrontFromDoubleSolutions(experiment).run();
            new ComputeQualityIndicators<>(experiment).run();
            new GenerateLatexTablesWithStatistics(experiment).run();
            new GenerateBoxplotsWithR<>(experiment).setRows(1).setColumns(1).run();


        } catch (Exception e) {
            System.out.println("Erro no experiment jmetal erro=" + e.toString());
        }
        Tools.Debug.OUT("INIT");
    }


    static List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> configureAlgorithmList(
            List<ExperimentProblem<DoubleSolution>> problemList) {
        Tools.Debug.IN("configureAlgorithmList");

        for (ExperimentProblem<DoubleSolution> problem: problemList){
           Tools.Debug.msg("problem=["+problem.getTag()+"]");
        }

        List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithms = new ArrayList<>();
        Tools.Debug.msg("Algoritmo usado: " + AlgorithmName);
        for (int i = 0; i < problemList.size(); i++) {
            //TODO O factory deve estar preparado para receber dados por contexto.
            Algorithm<List<DoubleSolution>> algorithm = Factory.getAlgorithm("pesa2",problemList.get(i));

            //algorithms.add(new ExperimentAlgorithm<>(algorithm, AlgorithmName, problemList.get(i).getTag()));
            algorithms.add(new ExperimentAlgorithm<>(algorithm, "pesa2", problemList.get(i).getTag()));
        }
        Tools.Debug.OUT("configureAlgorithmList");
        return algorithms;
    }

    public AntiSpamFilterProblem getAntiSpamFilterProblem() {
        return AntiSpamFilterProblem;
    }


}
