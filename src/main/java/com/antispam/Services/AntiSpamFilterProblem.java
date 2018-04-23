package com.antispam.Services;

import fileLoader.FileLoader;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static fileLoader.FileLoader.getInstance;

@SpringBootApplication
public class AntiSpamFilterProblem extends AbstractDoubleProblem {


    private LinkedHashMap<String, ArrayList<String>> ham;
    private LinkedHashMap<String, ArrayList<String>> spam;
    private double countFP = 0.0;
    private double countFN = 0.0;
    private final double algorithmLimit = 5.0;
    private static final long serialVersionUID = -8036102488474183100L;
    private String pathHam;
    private String pathSpam;
    private String pathrules;

    private LinkedHashMap <String,Double> rules =getInstance().getRulesMap();

    //private LinkedHashMap <String,Double> rules = FileLoader.getInstance().getRulesMap();

    public double getCountFP() {
        return countFP;
    }


    public double getCountFN() {
        return countFN;
    }



    public AntiSpamFilterProblem() {

        // 10 variables (anti-spam filter rules) by default
        //TODO getNumberOfRules file rules
        //FileLoader.getInstance().manualStart(pathrules, pathHam, pathSpam);

        //TODO LER RULES
        this(FileLoader.getInstance().getRulesMap().size());
        Tools.Debug.IN("AntiSpamFilterProblem()");
        Tools.Debug.msg("Rules size: " + FileLoader.getInstance().getRulesMap().size());

        //TODO Create GetPath's to Rules, Ham, Spam of GUI
        Tools.Debug.OUT("AntiSpamFilterProblem()");
    }


    //definimos o lower limit e upper limit
    /**
     * Constructor.
     * Creates a new instance of the AntiSpamFilter Problem.
     *
     * @param numberOfVariables Number of variables of the problem
     */
    public AntiSpamFilterProblem(Integer numberOfVariables) {
        Tools.Debug.IN("AntiSpamFilterProblem");
        //TODO get rules
        ham = FileLoader.getInstance().getHamRulesMap();
        spam = FileLoader.getInstance().getSpamRulesMap();

        Tools.Debug.msg("Numero de variaveis: " + numberOfVariables);
        setNumberOfVariables(numberOfVariables);
        setNumberOfObjectives(2);// FN & FP
        setName("AntiSpamFilterProblem");


        //
        List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
        List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

        int y = getNumberOfVariables();
        Tools.Debug.msg("Numero de variaveis: [getNumberOfVariables()]" + y);
        for (int i = 0; i < getNumberOfVariables(); i++) {
            lowerLimit.add(-5.0);
            upperLimit.add(5.0);
        }


        //SET LIMITS

        setLowerLimit(lowerLimit);
        setUpperLimit(upperLimit);
        Tools.Debug.OUT("AntiSpamFilterProblem");
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



    public void setPathHam(String pathHam) {
        this.pathHam = pathHam;
    }


    public void setPathSpam(String pathSpam) {
        this.pathSpam = pathSpam;
    }


    public void setPathrules(String pathrules) {
        this.pathrules = pathrules;
    }

    @Override
    //tb pode receber BinarySolution -> ver em jmetal solution
   // doubleSolution
    //IntegerSolution
    //permutation
    public void evaluate(DoubleSolution solution) {

        //TODO Fazer tratamento de dados se necessario.
        //tratamento de dados
//        int iterator = 0;
//        for (entry<string, double> rule : rules.entryset()){
//            rule.setvalue(solution.getvariablevalue(iterator));
//            iterator++;
//        }
        //TODO Aqui deverá ser chamado o evaluate dado atraves de JAR!
        //double [] fx = evaluate(rules);

        //TODO SET OBJECTIVE NOVAMENTE PARA O EVALUATE
        //solution.setObjective(0, fx[0]); //objective 0 fx[0] will be subst by FN
        //solution.setObjective(1, fx[1]); //objective 1 fx[1] will be subst by FP



        Tools.Debug.IN("AntiSpamFilterProblem [evaluate(DoubleSolution)]");

        //tratamento de dados
        int iterator = 0;
        for (Map.Entry<String, Double> rule : rules.entrySet()){
            rule.setValue(solution.getVariableValue(iterator));
            iterator++;
        }


        Tools.Debug.msg("Call evaluate(rules)");
        double [] fx = evaluate(rules);

        solution.setObjective(0, fx[0]); //objective 0 fx[0] will be subst by FN
        solution.setObjective(1, fx[1]); //objective 1 fx[1] will be subst by FP
        Tools.Debug.OUT("AntiSpamFilterProblem [evaluate(DoubleSolution)]");



    }

    public double[] evaluate(LinkedHashMap <String,Double> rules){
        Tools.Debug.IN("Evaluate");
        double[] fx = new double[getNumberOfObjectives()];

        fx[0] = 0.0;
        for (Map.Entry<String, ArrayList<String>> hamRule : ham.entrySet()) {
            double count = 0.0;
            for (String hamRules : hamRule.getValue()) {
                if (rules.containsKey(hamRules)) {
                    count += rules.get(hamRules);
                }
            }
            if (count > algorithmLimit) {
                fx[0]++;
            }
        }


        fx[1] = 0.0;
        for (Map.Entry<String, ArrayList<String>> spamRule : spam.entrySet()) {
            double count = 0.0;
            for (String spamRules : spamRule.getValue()) {
                if (rules.containsKey(spamRules)) {
                    count += rules.get(spamRules);
                }
            }
            if (count < algorithmLimit) {
                fx[1]++;
            }
        }
        Tools.Debug.msg("Return fx=["+fx[0]+"|"+fx[1]+"]");

        Tools.Debug.OUT("Evaluate");
        return fx;
    }

    public LinkedHashMap<String, Double> getRules() {
        return rules;
    }
}
