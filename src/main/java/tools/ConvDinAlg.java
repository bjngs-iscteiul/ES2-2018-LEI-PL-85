package tools;

import fileLoader.FileSearch;
import org.uma.jmetal.util.AbstractAlgorithmRunner;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConvDinAlg {

    private String textSearch = "public class ";

    public static void main(String[] args){
        converToDinamicAlgorithm("/Users/pedro/Documents/EngenhariaInformatica/ESII/Repositiorio/ES2-2018-LEI-PL-85_/src/main/java/org/uma/jmetal/runner/multiobjective");
    }

    public static void converToDinamicAlgorithm(String folderPath) {
        ArrayList<String> putList = new ArrayList<String>();

        File folder = null;
        File[] paths;

        //Folder search
        folder = new File(folderPath);
        paths = folder.listFiles();

        //searching file by file
        for (File file : paths) {
            if (!file.isDirectory()) {
                //This is a file
                String fileText = null;
                try {
                    fileText = FileSearch.getInstance().readFileToString(file).toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //if (fileText.contains(textSearch)) {
                    if (fileText.contains("")) {
                    String[] lines = fileText.split(";");

                    for (int i = 0; i != lines.length; i++) {

                        try {

                            if (lines[i].contains("double")) {
                                lines[i].indexOf("=");
                                String Number = lines[i].substring(lines[i].indexOf("=") + 1).replace(";", ""); //tem de ser number

                                try {
                                    double d = Double.parseDouble(Number);

                                    String[] instruction = lines[i].substring(0, lines[i].indexOf("=") - 1).split(" ");
                                    String nameProperty = instruction[instruction.length - 1]; //é o nome da propriedade


                                    if (nameProperty.length() > 2) {
                                        lines[i] = instruction + " = " + "doubleHmapProperty.get(\"" + nameProperty + "\");";
                                        putList.add("doubleHmapProperty.put(\"" + nameProperty + "\",\" " + Number + "\");");
                                    }


                                } catch (NumberFormatException nfe) {
                                    throw new NumberFormatException();
                                }

                            }
                        } catch (NumberFormatException nfe) {
                        }

                        try {
                            if (lines[i].contains(".set")) {
                                String[] sets = lines[i].split(".");
                                for (String set : sets) {
                                    if (set.contains("set")) {
                                        String toReplace = set;
                                        set.indexOf("(");

                                        String Number = set.substring(set.indexOf("("));
                                        Number = Number.replace(")", "");
                                        Number = Number.replace(";", ""); //tem de ser number

                                        try {
                                            double d = Double.parseDouble(Number);

                                            String instruction = set.substring(0,set.indexOf("(")-1);
                                            String nameProperty = instruction.replace("set","");

                                            String newInstruction = instruction + "(doubleHmapProperty.get(\"" + nameProperty + "\");";
                                            lines[i].replace(toReplace,newInstruction);
                                            putList.add("doubleHmapProperty.put(\"" + nameProperty + "\",\" " + Number + "\");");
                                        } catch (NumberFormatException nfe) {

                                            throw new NumberFormatException();
                                        }
                                    }
                                }
                            }
                        } catch (NumberFormatException nfe) {
                            //É porque nao é double
                        }

                        try {
                            if (lines[i].contains(".set")) {
                                String[] sets = lines[i].split(".");
                                for (String set : sets) {
                                    if (set.contains("set")) {
                                        String toReplace = set;
                                        set.indexOf("(");

                                        String Number = set.substring(set.indexOf("("));
                                        Number = Number.replace(")", "");
                                        Number = Number.replace(";", "");

                                        try {
                                            int d = Integer.parseInt(Number);

                                            String instruction = set.substring(0,set.indexOf("(")-1);
                                            String nameProperty = instruction.replace("set","");

                                            String newInstruction = instruction + "(intHmapProperty.get(\"" + nameProperty + "\");";
                                            lines[i].replace(toReplace,newInstruction);
                                            putList.add("intHmapProperty.put(\"" + nameProperty + "\",\" " + Number + "\");");
                                        } catch (NumberFormatException nfe) {

                                            throw new NumberFormatException();
                                        }
                                    }
                                }
                            }
                        } catch (NumberFormatException nfe) {
                            //É porque nao é Inteiro
                        }


                    }


                    for (int i = 0; i != lines.length; i++) {

                        if (lines[i].contains("public class")) {

                            String declares = lines[i].substring(lines[i].indexOf("{")+1);
                            String oldDeclares=declares;

                            String[] temp = lines[i].substring(0,lines[i].indexOf("{")-1).split(" ");

                            String nameClass="";

                            for(int j = 0 ; j!= temp.length ; j++){
                                if(temp[j].contains("class")){
                                    nameClass = temp[j+1];

                                }
                            }


                            declares = "public " + nameClass + "(){" +
                                    putList.toString()                  //coloca os put double e int
                                    + "}" + declares;

                            lines[i].replace(oldDeclares,declares);

                        }



                        try {
                            //Path fileW = Paths.get(folderPath + file.getName());
                            Path fileW = Paths.get("temp.class");
                            Files.write(fileW, Arrays.asList(lines), Charset.forName("UTF-8"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }


                }
            }
        }

    }
}
