package org.uma.jmetal.problems;


import interfaces.jMetalDinamicValues;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.JMetalException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyProblemInteger extends AbstractIntegerProblem implements jMetalDinamicValues {
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

	
	  public MyProblemInteger() throws JMetalException {
		// 10 decision variables by default
		  intHmapProperty.put("numberOfVariables",10);
		  intHmapProperty.put("numberOfObjectives",2);
		  intHmapProperty.put("lowerLimit",-5);
		  intHmapProperty.put("upperLimit",5);
	  }
	  public void init(){
		start(intHmapProperty.get("numberOfVariables"));
	  }


	  public void start (Integer numberOfVariables) throws JMetalException {
	    setNumberOfVariables(numberOfVariables);
	    setNumberOfObjectives(intHmapProperty.get("numberOfObjectives"););
	    setName("MyProblemInteger");

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
	    double[] fx = new double[getNumberOfObjectives()];
	    int[] x = new int[getNumberOfVariables()];
	    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
	      x[i] = solution.getVariableValue(i) ;
	    }

	    fx[0] = 0;
	    for (int var = 0; var < solution.getNumberOfVariables() - 1; var++) {
		  fx[0] += Math.abs(x[0]+Math.random()*10); // Example for testing
	    }
	    
	    fx[1] = 0;
	    for (int var = 0; var < solution.getNumberOfVariables(); var++) {
	    	fx[1] += Math.abs(x[1]+Math.random()*10); // Example for testing
	    }

	    solution.setObjective(0, fx[0]);
	    solution.setObjective(1, fx[1]);
	  }
	  	  
	}
