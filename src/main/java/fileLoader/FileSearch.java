package fileLoader;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.IntegerSolution;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;

public class FileSearch {
    private static final FileSearch INSTANCE = new FileSearch();

    public static FileSearch getInstance() {
        return INSTANCE;
    }

    //Search String on path with files and return their names
    public ArrayList<String> fileContainsString(String folderPath, String textSearch) throws IOException {
        ArrayList<String> returnList = new ArrayList<String>();
        File folder = null;
        File[] paths;


        //Folder search
        folder = new File(folderPath);
        paths = folder.listFiles();

        //searching file by file
        for (File file : paths) {
            if (!file.isDirectory()) {
                //This is a file
                String fileText = readFileToString(file).toString();
                if (fileText.contains(textSearch)) {
                    returnList.add(file.getName());

                }
            }
        }

        return returnList;
    }


    //Read File to StringBuilder
    private StringBuilder readFileToString(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
        StringBuilder sb = new StringBuilder();
        try {
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        return sb;
    }

    public void createXMLRunnerByProblem() {
        String[] algorithList = null;
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("root");
            doc.appendChild(rootElement);



            //FOR PROBLEM
            Element typeProblem = doc.createElement("Problem");
            rootElement.appendChild(typeProblem);

            try {
                algorithList =
                        FileSearch.getInstance()
                                .fileContainsString
                                        ("/Users/pedro/Documents/EngenhariaInformatica/ESII/Repositiorio/ES2-2018-LEI-PL-85_/src/main/java/org/uma/jmetal/runner/multiobjective",
                                                "import org.uma.jmetal.problem.Problem;").toArray(new String[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Element firstname = null;

            for (String algorithmName : algorithList) {
                firstname = doc.createElement("AlgorithmRunner");
                firstname.appendChild(doc.createTextNode(algorithmName));
                typeProblem.appendChild(firstname);
            }


            //FOR DOUBLEPROBLEM
            typeProblem = doc.createElement("DoubleProblem");
            rootElement.appendChild(typeProblem);

            try {
                algorithList =
                        FileSearch.getInstance()
                                .fileContainsString
                                        ("/Users/pedro/Documents/EngenhariaInformatica/ESII/Repositiorio/ES2-2018-LEI-PL-85_/src/main/java/org/uma/jmetal/runner/multiobjective",
                                                "import org.uma.jmetal.problem.DoubleProblem;").toArray(new String[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

             firstname = null;

            for (String algorithmName : algorithList) {
                firstname = doc.createElement("AlgorithmRunner");
                firstname.appendChild(doc.createTextNode(algorithmName));
                typeProblem.appendChild(firstname);
            }

            //FOR BINARYPROBLEM
            typeProblem = doc.createElement("BinaryProblem");
            rootElement.appendChild(typeProblem);

            try {
                algorithList =
                        FileSearch.getInstance()
                                .fileContainsString
                                        ("/Users/pedro/Documents/EngenhariaInformatica/ESII/Repositiorio/ES2-2018-LEI-PL-85_/src/main/java/org/uma/jmetal/runner/multiobjective",
                                                "import org.uma.jmetal.problem.BinaryProblem;").toArray(new String[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            firstname = null;

            for (String algorithmName : algorithList) {
                firstname = doc.createElement("AlgorithmRunner");
                firstname.appendChild(doc.createTextNode(algorithmName));
                typeProblem.appendChild(firstname);
            }

            //FOR Integer
            typeProblem = doc.createElement("Integer");
            rootElement.appendChild(typeProblem);

            try {
                algorithList =
                        FileSearch.getInstance()
                                .fileContainsString
                                        ("/Users/pedro/Documents/EngenhariaInformatica/ESII/Repositiorio/ES2-2018-LEI-PL-85_/src/main/java/org/uma/jmetal/runner/multiobjective",
                                                "Problem<IntegerSolution> problem;").toArray(new String[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            firstname = null;

            for (String algorithmName : algorithList) {
                firstname = doc.createElement("AlgorithmRunner");
                firstname.appendChild(doc.createTextNode(algorithmName));
                typeProblem.appendChild(firstname);
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("RunnerProblemType.xml"));
            //StreamResult result =  new StreamResult(System.out);
            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public void createXMLRunnerBySolution(){


        String[] algorithList = null;
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("root");
            doc.appendChild(rootElement);



            //FOR DOUBLESOLUTION
            Element typeProblem = doc.createElement("DoubleSolution");
            rootElement.appendChild(typeProblem);

            try {
                algorithList =
                        FileSearch.getInstance()
                                .fileContainsString
                                        ("/Users/pedro/Documents/EngenhariaInformatica/ESII/Repositiorio/ES2-2018-LEI-PL-85_/src/main/java/org/uma/jmetal/runner/multiobjective",
                                                "import org.uma.jmetal.solution.DoubleSolution;").toArray(new String[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Element firstname = null;

            for (String algorithmName : algorithList) {
                firstname = doc.createElement("AlgorithmRunner");
                firstname.appendChild(doc.createTextNode(algorithmName));
                typeProblem.appendChild(firstname);
            }


            //FOR BinarySolution
            typeProblem = doc.createElement("BinarySolution");
            rootElement.appendChild(typeProblem);

            try {
                algorithList =
                        FileSearch.getInstance()
                                .fileContainsString
                                        ("/Users/pedro/Documents/EngenhariaInformatica/ESII/Repositiorio/ES2-2018-LEI-PL-85_/src/main/java/org/uma/jmetal/runner/multiobjective",
                                                "import org.uma.jmetal.solution.BinarySolution;").toArray(new String[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }


            for (String algorithmName : algorithList) {
                firstname = doc.createElement("AlgorithmRunner");
                firstname.appendChild(doc.createTextNode(algorithmName));
                typeProblem.appendChild(firstname);
            }




            //FOR IntegerSolution
            typeProblem = doc.createElement("IntegerSolution");
            rootElement.appendChild(typeProblem);

            try {
                algorithList =
                        FileSearch.getInstance()
                                .fileContainsString
                                        ("/Users/pedro/Documents/EngenhariaInformatica/ESII/Repositiorio/ES2-2018-LEI-PL-85_/src/main/java/org/uma/jmetal/runner/multiobjective",
                                                "import org.uma.jmetal.solution.IntegerSolution;").toArray(new String[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }


            for (String algorithmName : algorithList) {
                firstname = doc.createElement("AlgorithmRunner");
                firstname.appendChild(doc.createTextNode(algorithmName));
                typeProblem.appendChild(firstname);
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("RunnerSOLUTIONType.xml"));
            //StreamResult result =  new StreamResult(System.out);
            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);
            System.out.println("File saved!");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        FileSearch.getInstance().createXMLRunnerByProblem();
        FileSearch.getInstance().createXMLRunnerBySolution();
    }
}
