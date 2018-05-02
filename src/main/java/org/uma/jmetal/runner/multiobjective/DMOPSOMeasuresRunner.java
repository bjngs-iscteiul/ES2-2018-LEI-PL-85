package org.uma.jmetal.runner.multiobjective;

import interfaces.jMetalDinamicValues;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.uma.jmetal.algorithm.multiobjective.dmopso.DMOPSO.FunctionType;
import org.uma.jmetal.algorithm.multiobjective.dmopso.DMOPSOMeasures;
import org.uma.jmetal.measure.MeasureListener;
import org.uma.jmetal.measure.MeasureManager;
import org.uma.jmetal.measure.impl.BasicMeasure;
import org.uma.jmetal.measure.impl.CountingMeasure;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AbstractAlgorithmRunner;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.ProblemUtils;
import org.uma.jmetal.util.chartcontainer.ChartContainer;
import org.uma.jmetal.util.front.imp.ArrayFront;

import java.util.HashMap;
import java.util.List;

/**
 * Class for configuring and running the DMOPSO algorithm
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */

public class DMOPSOMeasuresRunner extends AbstractAlgorithmRunner implements jMetalDinamicValues {
    private static HashMap<String, Double> doubleHmapProperty = new HashMap<String, Double>();
    private static HashMap<String, Integer> intHmapProperty = new HashMap<String, Integer>();

    public DMOPSOMeasuresRunner() {
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
     * @throws SecurityException Invoking command: java org.uma.jmetal.runner.multiobjective.DMOPSORunner
     *                           problemName [referenceFront]
     */
    public static void main(String[] args) throws Exception {
        DoubleProblem problem;
        DMOPSOMeasures algorithm;

        String referenceParetoFront = "";

        String problemName;
        if (args.length == 1) {
            problemName = args[0];
        } else if (args.length == 2) {
            problemName = args[0];
            referenceParetoFront = args[1];
        } else {
            problemName = "org.uma.jmetal.problem.multiobjective.zdt.ZDT4";
            referenceParetoFront = "jmetal-problem/src/test/resources/pareto_fronts/ZDT1.pf";
        }

        problem = (DoubleProblem) ProblemUtils.<DoubleSolution>loadProblem(problemName);

        algorithm = new DMOPSOMeasures(problem,
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

        algorithm.setReferenceFront(new ArrayFront(referenceParetoFront));

        /* Measure management */
        MeasureManager measureManager = ((DMOPSOMeasures) algorithm).getMeasureManager();

        BasicMeasure<List<DoubleSolution>> solutionListMeasure = (BasicMeasure<List<DoubleSolution>>) measureManager
                .<List<DoubleSolution>>getPushMeasure("currentPopulation");
        CountingMeasure iterationMeasure = (CountingMeasure) measureManager.<Long>getPushMeasure("currentEvaluation");
        BasicMeasure<Double> hypervolumeMeasure = (BasicMeasure<Double>) measureManager
                .<Double>getPushMeasure("hypervolume");
        BasicMeasure<Double> epsilonMeasure = (BasicMeasure<Double>) measureManager.<Double>getPushMeasure("epsilon");

        ChartContainer chart = new ChartContainer(algorithm.getName(), 200);
        chart.setFrontChart(0, 1, referenceParetoFront);
        chart.setVarChart(0, 1);
        chart.addIndicatorChart("Hypervolume");
        chart.addIndicatorChart("Epsilon");
        chart.initChart();

        solutionListMeasure.register(new ChartListener(chart));
        iterationMeasure.register(new IterationListener(chart));
        hypervolumeMeasure.register(new IndicatorListener("Hypervolume", chart));
        epsilonMeasure.register(new IndicatorListener("Epsilon", chart));

        /* End of measure management */

        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
        chart.saveChart("./chart", BitmapFormat.PNG);

        List<DoubleSolution> population = algorithm.getResult();
        long computingTime = algorithmRunner.getComputingTime();

        JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

        printFinalSolutionSet(population);
        if (!referenceParetoFront.equals("")) {
            printQualityIndicators(population, referenceParetoFront);
        }

    }

    private static class ChartListener implements MeasureListener<List<DoubleSolution>> {
        private ChartContainer chart;
        private int iteration = 0;

        public ChartListener(ChartContainer chart) {
            this.chart = chart;
            this.chart.getFrontChart().setTitle("Iteration: " + this.iteration);
        }

        private void refreshChart(List<DoubleSolution> solutionList) {
            if (this.chart != null) {
                iteration++;
                this.chart.getFrontChart().setTitle("Iteration: " + this.iteration);
                this.chart.updateFrontCharts(solutionList);
                this.chart.refreshCharts();
            }
        }

        @Override
        synchronized public void measureGenerated(List<DoubleSolution> solutions) {
            refreshChart(solutions);
        }
    }

    private static class IterationListener implements MeasureListener<Long> {
        ChartContainer chart;

        public IterationListener(ChartContainer chart) {
            this.chart = chart;
            this.chart.getFrontChart().setTitle("Iteration: " + 0);
        }

        @Override
        synchronized public void measureGenerated(Long iteration) {
            if (this.chart != null) {
                this.chart.getFrontChart().setTitle("Iteration: " + iteration);
            }
        }
    }

    private static class IndicatorListener implements MeasureListener<Double> {
        ChartContainer chart;
        String indicator;

        public IndicatorListener(String indicator, ChartContainer chart) {
            this.chart = chart;
            this.indicator = indicator;
        }

        @Override
        synchronized public void measureGenerated(Double value) {
            if (this.chart != null) {
                this.chart.updateIndicatorChart(this.indicator, value);
                this.chart.refreshCharts(0);
            }
        }
    }
}
