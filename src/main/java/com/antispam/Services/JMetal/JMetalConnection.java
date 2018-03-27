package com.antispam.Services.JMetal;

import java.io.File;

public class JMetalConnection {

    public File[] GetAlgorithmsList(){

        File folder = new File(
                "resources.jMetalSourceCode.jmetal-alghorithm.src.main.java.org.uma.jmetal.algorithm.multiobjective");
        File[] listoffiles = folder.listFiles();
        for(int i=0;i!=listoffiles.length;i++){
            if(listoffiles[i].isDirectory()){
                System.out.println("directory " + listoffiles[i].getName());
            }
        }
        return listoffiles;
    }

    public String[] TestMethod (){
        String[] list = new String[3];
        list[0] = "teste";
        list[1] = "bananas";
        list[2] = "verde";
        return  list;
    }

}
