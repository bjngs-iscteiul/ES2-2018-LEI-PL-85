package com.antispam.Services.JMetal;


import org.uma.jmetal.runner.multiobjective.ABYSSRunner;

public class AlgorithmExecutorESII {



    public void testRun(String algorithmName){
        String[] args = {};
        switch(algorithmName){
            case"NSGAIII":

                org.uma.jmetal.runner.multiobjective.NSGAIIIRunner.main(args);

                break;
            case"ABYSS":
                args = new String[]{};

                ABYSSRunner ABYSS = new ABYSSRunner();
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
