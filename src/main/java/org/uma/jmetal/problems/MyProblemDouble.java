package org.uma.jmetal.problems;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import interfaces.jMetalDinamicValues;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

public class MyProblemDouble extends AbstractDoubleProblem implements jMetalDinamicValues {
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

		
	  public MyProblemDouble() {
	    // 10 variables (anti-spam filter rules) by default
		  intHmapProperty.put("numberOfVariables",10);
		  intHmapProperty.put("numberOfObjectives",2);
		  doubleHmapProperty.put("lowerLimit",-5.0);
		  doubleHmapProperty.put("upperLimit",5.0);
	  }

	  public void init(){
		start(intHmapProperty.get("numberOfVariables"));
	  }

	  public void start (Integer numberOfVariables) {
	    setNumberOfVariables(numberOfVariables);
	    setNumberOfObjectives(intHmapProperty.get("NumberOfObjectives"));
	    setName("MyProblemDouble");

	    List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
	    List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

	    for (int i = 0; i < getNumberOfVariables(); i++) {
	      lowerLimit.add(doubleHmapProperty.get("lowerLimit"));
	      upperLimit.add(doubleHmapProperty.get("upperLimit"));
	    }

	    setLowerLimit(lowerLimit);
	    setUpperLimit(upperLimit);
	  }

	  public void evaluate(DoubleSolution solution){

	    double[] fx = new double[getNumberOfObjectives()];
	    double[] x = new double[getNumberOfVariables()];
	    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
	      x[i] = solution.getVariableValue(i) ;
	    }

	    fx[0] = 0.0;
	    for (int var = 0; var < solution.getNumberOfVariables() - 1; var++) {
		  fx[0] += Math.abs(x[0]); // Example for testing
	    }
	    
	    fx[1] = 0.0;
	    for (int var = 0; var < solution.getNumberOfVariables(); var++) {
	    	fx[1] += Math.abs(x[1]); // Example for testing
	    }

	    solution.setObjective(0, fx[0]);
	    solution.setObjective(1, fx[1]);
	  }
 	  	  
	}
