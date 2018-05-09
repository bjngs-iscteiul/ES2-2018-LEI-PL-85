package org.uma.jmetal.problems;


import interfaces.jMetalDinamicValues;
import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.impl.DefaultBinarySolution;
import org.uma.jmetal.util.JMetalException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.BitSet;
import java.util.HashMap;

/* Implementa��o de um problema do tipo Binary que executa o .jar externo
   OneZeroMax.jar e pode ser usado como um dos problema de teste indicados 
   no encunciado do trabalho */

@SuppressWarnings("serial")
public class MyProblemBinaryExternalViaJAR extends AbstractBinaryProblem implements jMetalDinamicValues {
  	private int bits ;

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



	public MyProblemBinaryExternalViaJAR() throws JMetalException {
		intHmapProperty.put("numberOfBits",10);
		intHmapProperty.put("numberOfVariables",1);
		intHmapProperty.put("numberOfObjectives",2);
	}

	public void init(){
		start(intHmapProperty.get("numberOfBits"));
	}

	public void start(Integer numberOfBits) throws JMetalException {
		setNumberOfVariables(intHmapProperty.get("numberOfVariables"));
		setNumberOfObjectives(intHmapProperty.get("numberOfObjectives"));
		setName("MyProblemBinaryExternalViaJAR");
		bits = numberOfBits ;
	}
	  
	  @Override
	  protected int getBitsPerVariable(int index) {
	  	if (index != 0) {
	  		throw new JMetalException("Problem MyBinaryProblem has only a variable. Index = " + index) ;
	  	}
	  	return bits ;
	  }

	  @Override
	  public BinarySolution createSolution() {
	    return new DefaultBinarySolution(this) ;
	  }

	  @Override
	  public void evaluate(BinarySolution solution){

	    String solutionString ="";
	    String evaluationResultString ="";
	    BitSet bitset = solution.getVariableValue(0) ;
	    solutionString = bitset.toString();
	    try {
			String line;
	    	Process p = Runtime.getRuntime().exec("java -jar c:\\OneZeroMax.jar" + " " + solutionString);
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
	    	solution.setObjective(i, Double.parseDouble(individualEvaluationCriteria[i]));
	    }	    	    
	  
	  }
	}
