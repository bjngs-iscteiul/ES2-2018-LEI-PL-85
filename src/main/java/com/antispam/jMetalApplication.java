
package com.antispam;
import java.io.*;

public class jmetalapplication {

    public jmetalapplication(){}

    public file[] getalgorithmslist(){
        file folder = new file("jmetal.algorithms");
        file[] listoffiles = folder.listfiles();
        for(int i=0;i!=listoffiles.length;i++){
            if(listoffiles[i].isdirectory()){
                system.out.println("directory " + listoffiles[i].getname();
            }
        }
        return listoffiles;
    }

}
