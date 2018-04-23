package com.antispam.Services.JMetal;

import com.sun.xml.internal.bind.v2.TODO;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.abyss.ABYSS;
import org.uma.jmetal.algorithm.multiobjective.abyss.ABYSSBuilder;
import org.uma.jmetal.algorithm.multiobjective.mocell.MOCellBuilder;
import org.uma.jmetal.algorithm.multiobjective.mombi.MOMBI;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.paes.PAESBuilder;
import org.uma.jmetal.algorithm.multiobjective.pesa2.PESA2Builder;
import org.uma.jmetal.algorithm.multiobjective.randomsearch.RandomSearchBuilder;
import org.uma.jmetal.algorithm.multiobjective.rnsgaii.RNSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.smsemoa.SMSEMOABuilder;
import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2Builder;
import org.uma.jmetal.algorithm.multiobjective.wasfga.WASFGA;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

import java.util.List;

public class Factory {
    public static Algorithm<List<DoubleSolution>> getAlgorithm(String algorithmName, ExperimentProblem<DoubleSolution> experimentProblem) {

        switch (algorithmName) {
            case "nsgaii":
                return new NSGAIIBuilder<>(
                        experimentProblem.getProblem(),
                        new SBXCrossover(1.0, 5),
                        new PolynomialMutation(1.0 / experimentProblem.getProblem().getNumberOfVariables(), 10.0)
                ).setMaxEvaluations(1000)
                        .setPopulationSize(100)
                        .build();

            case "pesa2":
                //TODO cast's errados
                //este cosntrutor n retorna o mesmo que o outro
                return new PESA2Builder<>(experimentProblem.getProblem(),
                        new SBXCrossover(1.0, 5),
                        new PolynomialMutation(1.0 / experimentProblem.getProblem().getNumberOfVariables(), 10.0)
                ).setMaxEvaluations(1000)
                        .setMaxEvaluations(100)
                        .build();
            case "mocell":
                return new MOCellBuilder<>(experimentProblem.getProblem(),
                        new SBXCrossover(1.0, 5),
                        new PolynomialMutation(1.0 / experimentProblem.getProblem().getNumberOfVariables(), 10.0)
                ).setMaxEvaluations(1000)
                        .setPopulationSize(100)
                        .build();
            case "mombi":
                //TODO ver null's & a falta de build
                return new MOMBI<>(experimentProblem.getProblem(),
                        1000,
                        new SBXCrossover(1.0, 5),
                        new PolynomialMutation(1.0 / experimentProblem.getProblem().getNumberOfVariables(), 10.0),
                        null,
                        null,
                        null
                );
            case "nsgaiii":
                //TODO ver falta de Crossover & PolynomialMutation
                return new NSGAIIIBuilder<>(experimentProblem.getProblem()
                ).setPopulationSize(100)
                        .build();
            case "pass":
                //TODO ver falta de Crossover & PolynomialMutation
                return new PAESBuilder<>(experimentProblem.getProblem()
                ).setMaxEvaluations(1000)
                        .build();
            case "randomsearch":
                //TODO ver falta de Crossover & PolynomialMutation & setPopulationSize
                return new RandomSearchBuilder<>(experimentProblem.getProblem()
                ).setMaxEvaluations(1000)
                        .build();
            case "rnsgaii":
                //TODO ver falta de interestPoint & epsilon
                return new RNSGAIIBuilder<>(experimentProblem.getProblem(),
                        new SBXCrossover(1.0, 5),
                        new PolynomialMutation(1.0 / experimentProblem.getProblem().getNumberOfVariables(), 10.0),
                        null,
                        0.0
                ).setMaxEvaluations(1000)
                        .setPopulationSize(100)
                        .build();
            case "smsemoa":
                return new SMSEMOABuilder<>(experimentProblem.getProblem(),
                        new SBXCrossover(1.0, 5),
                        new PolynomialMutation(1.0 / experimentProblem.getProblem().getNumberOfVariables(), 10.0)
                ).setMaxEvaluations(1000)
                        .setPopulationSize(100)
                        .build();
            case "spea2":
                //TODO ver falta setMaxEvaluations
                return new SPEA2Builder<>(experimentProblem.getProblem(),
                        new SBXCrossover(1.0, 5),
                        new PolynomialMutation(1.0 / experimentProblem.getProblem().getNumberOfVariables(), 10.0)
                ).setPopulationSize(100)
                        .build();

                //TODO ver falta de build
/*            case "wasfga":
                //TODO ver falta de selectionOperator, evaluator , epsilon, referencePoint, weightVectorsFileName
                return new WASFGA<>(experimentProblem.getProblem(),
            100,
            1000,
                        new SBXCrossover(1.0, 5),
                        new PolynomialMutation(1.0 / experimentProblem.getProblem().getNumberOfVariables(), 10.0),
            null,
                        null,
           null,
             0.0,
            null,
            null);*/

            default:
                return null;
        }
    }
}
