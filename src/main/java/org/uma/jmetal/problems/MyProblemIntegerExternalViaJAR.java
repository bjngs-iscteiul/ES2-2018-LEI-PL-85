package org.uma.jmetal.problems;


import interfaces.jMetalDinamicValues;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.JMetalException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* Implementa��o de um problema do tipo Integer que executa o .jar externo
   NMMin.jar e pode ser usado como um dos problema de teste indicados 
   no encunciado do trabalho */

@SuppressWarnings("serial")
public class MyProblemIntegerExternalViaJAR extends AbstractIntegerProblem implements jMetalDinamicValues {
	private static HashMap<String,Integer> intHmapProperty = new HashMap<String,Integer>();
	private static HashMap<String, Double> doubleHmapProperty = new HashMap<String, Double>();

	@Override
	public void setDoubleHmapProperty(HashMap<String, Double> hmapProperty) {
		if (hmapProperty.size() == hmapProperty.size()) {
			this.doubleHmapProperty = hmapProperty;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void setIntHmapProperty(HashMap<String, Integer> hmapProperty) {
		if (hmapProperty.size() == hmapProperty.size()) {
			this.intHmapProperty = hmapProperty;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public HashMap<String, Integer> getIntHmapProperty() {
		return intHmapProperty;
	}

	@Override
	public HashMap<String, Double> getDoubleHmapProperty() {
		return doubleHmapProperty;
	}


	public MyProblemIntegerExternalViaJAR() throws JMetalException {
		// 10 decision variables by default
		intHmapProperty.put("numberOfVariables",10);
		intHmapProperty.put("numberOfObjectives",2);
		intHmapProperty.put("lowerLimit",-1000);
		intHmapProperty.put("upperLimit",1000);
	}
	public void init(){
		start(intHmapProperty.get("numberOfVariables"));
	}


	public void start (Integer numberOfVariables) throws JMetalException {
		setNumberOfVariables(numberOfVariables);
		setNumberOfObjectives(intHmapProperty.get("numberOfObjectives"););
		setName("MyProblemIntegerExternalViaJAR");

		List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
		List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

		for (int i = 0; i < getNumberOfVariables(); i++) {
			lowerLimit.add(intHmapProperty.get("lowerLimit"));
			upperLimit.add(intHmapProperty.get("upperLimit"));
		}

		setLowerLimit(lowerLimit);
		setUpperLimit(upperLimit);

	}
	  public void evaluate(IntegerSolution solution){
	    String solutionString ="";
	    String evaluationResultString ="";
	    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
	      solutionString = solutionString + " " + solution.getVariableValue(i);  
	    }
	    try {
			String line;
	    	Process p = Runtime.getRuntime().exec("java -jar c:\\NMMin.jar" + " " + solutionString);
	    	BufferedReader brinput = new BufferedReader(new InputStreamReader(p.getInputStream()));
	    	while ((line = brinput.readLine()) != null) 
	    		{evaluationResultString+=line;}
	    	brinput.close();
	        p.waitFor();
	      }
	      catch (Exception err) { err.printStackTrace(); }
	    
   		String[] individualEvaluationCriteria = evaluationResultString.split("\\s+");
	    // It is assumed that all evaluated criteria are returned in the same result string
	    for (int i = 0; i < solution.getNumberOfObjectives(); i++) {
		    solution.setObjective(i, Integer.parseInt(individualEvaluationCriteria[i]));    
	    }	    
	  }	  
	}
