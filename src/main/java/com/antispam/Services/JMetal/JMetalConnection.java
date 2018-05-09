package com.antispam.Services.JMetal;


import com.antispam.Services.AntiSpamFilterAutomaticConfiguration;
import com.antispam.Services.AntiSpamFilterProblem;
import fileLoader.FileLoader;
import fileLoader.FileSearch;

import java.io.File;
import java.io.IOException;

public class JMetalConnection {

    public Tools.Debug debug = Tools.Debug.getInstance();


    public String[] AlgorithmName() {
        debug.IN("AlgorithmName_Test");
        String[] returnList = new String[0];
        File folder = null;
        File[] paths;

        try {
            folder = new File("src/main/java/org/uma/jmetal/runner/multiobjective");
            paths = folder.listFiles();
            returnList = new String[paths.length];

            int i = 0;
            for (File file : paths) {
                returnList[i] = file.getName().replace(".java","");

                if (file.isDirectory()) {
                    returnList[i] = file.getName();
                }
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i != returnList.length; i++) {

            Tools.Debug.msg("ReturnList [" + i + "]: " + returnList[i]);
        }

        Tools.Debug.OUT("AlgorithmName_Test");
        return returnList;
    }

    public String[] TestMethod() {
       String[] list = new String[3];
       String[] testelist = null;
        list[0] = "teste";
        list[1] = "bananas";
        list[2] = "verde";

       new AlgorithmExecutorESII().testRun("ABYSS");

        //FileSearch.getInstance().createXMLRunnerByProblem();


        return testelist;
    }

    public void startJMetalAlgorithm(String AlgorithmName) {
        Tools.Debug.IN("startJMetalAlgorithm");

        //GET FILE PATHS
        String pathRules    = "/Users/pedro/Documents/EngenhariaInformatica/ESII/Repositiorio/ES2-2018-LEI-PL-85_/Files/rules.cf";
        String pathHam      = "/Users/pedro/Documents/EngenhariaInformatica/ESII/Repositiorio/ES2-2018-LEI-PL-85_/Files/ham.log";
        String pathSpam     = "/Users/pedro/Documents/EngenhariaInformatica/ESII/Repositiorio/ES2-2018-LEI-PL-85_/Files/spam.log";



        manualStart(
                pathRules,
                pathHam,
                pathSpam
        );


        AntiSpamFilterProblem antiSpamFilterProblem = new AntiSpamFilterProblem();
        Tools.Debug.msg("Rules size=["+antiSpamFilterProblem.getRules().size()+"]");
        antiSpamFilterProblem = new AntiSpamFilterProblem(antiSpamFilterProblem.getRules().size());
        AntiSpamFilterAutomaticConfiguration AntiSpamFilterAutomaticConfiguration = new AntiSpamFilterAutomaticConfiguration(antiSpamFilterProblem);


        //INIT

        try {

            AntiSpamFilterAutomaticConfiguration.INIT(AlgorithmName);
            Tools.Debug.msg("FP["+AntiSpamFilterAutomaticConfiguration.getAntiSpamFilterProblem().getCountFP()+"]");
            Tools.Debug.msg("FN["+AntiSpamFilterAutomaticConfiguration.getAntiSpamFilterProblem().getCountFN()+"]");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Tools.Debug.OUT("startJMetalAlgorithm");
    }

    public void readRules(String path) {
        FileLoader.readRules(path);
    }

    public void readHam(String path) {
        FileLoader.readLogFile(path);
    }

    public void readSpam(String path) {
        FileLoader.readLogFile(path);
    }

    public void manualStart(String pathRules, String pathHam, String pathSpam) {
        //Os Path's não podem entrar vazios
        if (!pathRules.isEmpty() && !pathHam.isEmpty() && !pathSpam.isEmpty()) {
            readRules(pathRules);
            readHam(pathHam);
            readSpam(pathSpam);
        }
    }



}
