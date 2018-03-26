
package com.antispam;

import java.io.File;

public class jmetalapplication {

    public jmetalapplication(){}

    public File[] getalgorithmslist(){
        File folder = new File("jmetal.algorithms");
        File[] listoffiles = folder.listFiles();
        for(int i=0;i!=listoffiles.length;i++){
            if(listoffiles[i].isDirectory()){
                System.out.println("directory " + listoffiles[i].getName());
            }
        }
        return listoffiles;
    }

}
