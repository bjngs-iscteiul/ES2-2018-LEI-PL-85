package com.antispam.Services.JMetal;


import org.uma.jmetal.runner.multiobjective.ABYSS;

public class AlgorithmExecutorESII {



    public void testRun(String algorithmName){
        String[] args = {};
        switch(algorithmName){
            case"NSGAIII":

                org.uma.jmetal.runner.multiobjective.NSGAIIIRunner.main(args);

                break;
            case"ABYSS":
                args = new String[]{};

                ABYSS ABYSS = new ABYSS();
                //pedir propriedades
                //ABYSS.getHmapProperty();
                //inserir propriedades
                //ABYSS.setHmapProperty();

                try {
                    ABYSS.main(args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


        }

    }
}
