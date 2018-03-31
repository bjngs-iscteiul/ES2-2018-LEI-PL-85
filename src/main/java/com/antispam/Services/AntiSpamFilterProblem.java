package com.antispam.Services;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 */
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

    //private LinkedHashMap <String,Double> rules = FileLoader.getInstance().getRulesMap();

    /**
     * @return
     */
    public double getCountFP() {
        return countFP;
    }


    /**
     * @return
     */
    public double getCountFN() {
        return countFN;
    }


    /**
     *
     */
    public AntiSpamFilterProblem() {
        // 10 variables (anti-spam filter rules) by default
        //TODO getNumberOfRules file rules
        //FileLoader.getInstance().manualStart(pathrules, pathHam, pathSpam);

        //TODO LER RULES
        //this(FileLoader.getInstance().getRulesMap().size());

        //TODO Create GetPath's to Rules, Ham, Spam of GUI
    }


    //definimos o lower limit e upper limit
    /**
     * Constructor.
     * Creates a new instance of the AntiSpamFilter Problem.
     *
     * @param numberOfVariables Number of variables of the problem
     */
    public AntiSpamFilterProblem(Integer numberOfVariables) {

        //TODO get rules
        //ham = FileLoader.getInstance().getHamRulesMap();
        //spam= FileLoader.getInstance().getSpamRulesMap();


        setNumberOfVariables(numberOfVariables);
        setNumberOfObjectives(2);// FN & FP
        setName("AntiSpamFilterProblem");

        List<Double> lowerLimit = new ArrayList<Double>(getNumberOfVariables()) ;
        List<Double> upperLimit = new ArrayList<Double>(getNumberOfVariables()) ;

        int y = getNumberOfVariables();
        for (int i = 0; i < getNumberOfVariables(); i++) {
            lowerLimit.add(-5.0);
            upperLimit.add(5.0);
        }


        //SET LIMITS

        //setLowerLimit(lowerLimit);
        //setUpperLimit(upperLimit);

    }


    /**
     * @return
     */
    public LinkedHashMap<String, ArrayList<String>> getHam() {
        return ham;
    }


    /**
     * @return
     */
    public LinkedHashMap<String, ArrayList<String>> getSpam() {
        return spam;
    }

    /**
     * @return
     */
    //Manual calculation of FN and FP
    public int[] getManualFN_FP(){
        int[] result = new int[2];
        return result;
    }


    /**
     * @param pathHam
     */
    public void setPathHam(String pathHam) {
        this.pathHam = pathHam;
    }


    /**
     * @param pathSpam
     */
    public void setPathSpam(String pathSpam) {
        this.pathSpam = pathSpam;
    }


    /**
     * @param pathrules
     */
    public void setPathrules(String pathrules) {
        this.pathrules = pathrules;
    }

    /**
     * @param solution
     */
    @Override
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

    }
}
