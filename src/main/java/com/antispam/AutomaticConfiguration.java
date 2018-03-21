package com.antispam;

public class AutomaticConfiguration {
    private final int INDEPENDENT_RUNS = 5 ;
    private Problem problem ;
    private String AlgorithmName;


    public AutomaticConfiguration(Problem problem){
        this.problem = problem;
    }


    public void INIT(String AlgorithmName) throws IOException {
        String experimentBaseDirectory = "experimentBaseDirectory";

        List<ExperimentProblem<DoubleSolution>> problemList = new ArrayList<>();

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
        Debug.OUT("AntiSpamFilterAutomaticConfiguration [MAIN]");
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
