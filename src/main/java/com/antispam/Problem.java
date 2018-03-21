package com.antispam;

public class Problem extends AbstractDoubleProblem {

    private LinkedHashMap<String, ArrayList<String>> ham;
    private LinkedHashMap<String, ArrayList<String>> spam;
    private double countFP = 0.0;
    private double countFN = 0.0;
    private final double algorithmLimit = 5.0;
    private static final long serialVersionUID = -8036102488474183100L;
    private String pathHam;
    private String pathSpam;
    private String pathrules;

    private LinkedHashMap <String,Double> rules = FileLoader.getInstance().getRulesMap();

    public double getCountFP() {
        return countFP;
    }


    public double getCountFN() {
        return countFN;
    }



    public Problem() {
        // 10 variables (anti-spam filter rules) by default
        //TODO getNumberOfRules file rules
        //FileLoader.getInstance().manualStart(pathrules, pathHam, pathSpam);
        this(FileLoader.getInstance().getRulesMap().size());

        //TODO Create GetPath's to Rules, Ham, Spam of GUI
        Debug.getInstance();
        Debug.IN("AntiSpamFilterProblem [Constructor]");
        Debug.OUT("AntiSpamFilterProblem [Constructor]");
    }


    //definimos o lower limit e upper limit
    /**
     * Constructor.
     * Creates a new instance of the AntiSpamFilter Problem.
     *
     * @param numberOfVariables Number of variables of the problem
     */
    public Problem(Integer numberOfVariables) {
        Debug.IN("AntiSpamFilterProblem [Constructor(INTEGER)]");

        //TODO
        ham = FileLoader.getInstance().getHamRulesMap();
        spam= FileLoader.getInstance().getSpamRulesMap();


        setNumberOfVariables(numberOfVariables);
        setNumberOfObjectives(2);// FN & FP
        setName("AntiSpamFilterProblem");

        List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
        List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;
        int y = getNumberOfVariables();
        for (int i = 0; i < getNumberOfVariables(); i++) {
            lowerLimit.add(-5.0);
            upperLimit.add(5.0);
        }

        setLowerLimit(lowerLimit);
        setUpperLimit(upperLimit);

        Debug.OUT("AntiSpamFilterProblem [Constructor(INTEGER)]");
    }

    //ver metodo rules a entrar vazias.
    /** Evaluate() method */
    public void evaluate(DoubleSolution solution){
        Debug.IN("AntiSpamFilterProblem [evaluate(DoubleSolution)]");

        //tratamento de dados
        int iterator = 0;
        for (Entry<String, Double> rule : rules.entrySet()){
            rule.setValue(solution.getVariableValue(iterator));
            iterator++;
        }


        Debug.msg("Call evaluate(rules)");
        double [] fx = evaluate(rules);

        solution.setObjective(0, fx[0]); //objective 0 fx[0] will be subst by FN
        solution.setObjective(1, fx[1]); //objective 1 fx[1] will be subst by FP
        Debug.OUT("AntiSpamFilterProblem [evaluate(DoubleSolution)]");
    }


    public double[] evaluate(LinkedHashMap <String,Double> rules){
       //CALL JAR
        trow NotImplementedException;
        double [] temp = {0.0};
        return temp;
    }


    public LinkedHashMap<String, Double> getRules() {
        return rules;
    }


    public LinkedHashMap<String, ArrayList<String>> getHam() {
        return ham;
    }


    public LinkedHashMap<String, ArrayList<String>> getSpam() {
        return spam;
    }

    //Manual calculation of FN and FP
    public int[] getManualFN_FP(){
        int[] result = new int[2];
        return result;
    }

    public boolean validLists() {
        return !rules.isEmpty() && !ham.isEmpty() && !spam.isEmpty();
    }


    public void setPathHam(String pathHam) {
        this.pathHam = pathHam;
    }


    public void setPathSpam(String pathSpam) {
        this.pathSpam = pathSpam;
    }


    public void setPathrules(String pathrules) {
        this.pathrules = pathrules;
    }

}
