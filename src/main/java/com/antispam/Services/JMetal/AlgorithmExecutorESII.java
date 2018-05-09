package com.antispam.Services.JMetal;


import org.uma.jmetal.runner.multiobjective.ABYSS;

import java.util.Iterator;
import java.util.Map;

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
                System.out.println("É necessario inserir as seguintes propriedades: ");
                //Iterator it = ABYSS.getHmapProperty().entrySet().iterator();
//                while (it.hasnext()) {
//                    map.entry pair = (map.entry)it.next();
//                    system.out.println(pair.getkey());
//                    it.remove(); // avoids a concurrentmodificationexception
//                }


                //ABYSS.getHmapProperty();
                //inserir propriedades
                //ABYSS.setIntHmapProperty();

                try {
                    //ABYSS.main(args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


        }

    }
}
