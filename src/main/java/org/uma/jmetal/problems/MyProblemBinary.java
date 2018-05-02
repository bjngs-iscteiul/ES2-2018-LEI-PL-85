package org.uma.jmetal.problems;

import interfaces.jMetalDinamicValues;
import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.impl.DefaultBinarySolution;
import org.uma.jmetal.util.JMetalException;
import java.util.BitSet;
import java.util.HashMap;

public class MyProblemBinary extends AbstractBinaryProblem implements jMetalDinamicValues {
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



	public MyProblemBinary() throws JMetalException {
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
	    setName("MyProblemBinary");
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
	    int counterOnes;
	    int counterZeroes;
	    counterOnes = 0;
	    counterZeroes = 0;

	    BitSet bitset = solution.getVariableValue(0) ;
	    for (int i = 0; i < bitset.length(); i++) {
	      if (bitset.get(i)) {
	        counterOnes++;
	      } else {
	        counterZeroes++;
	      }
	    }
	    // OneZeroMax is a maximization problem: multiply by -1 to minimize
	    solution.setObjective(0, -1.0 * counterOnes);
	    solution.setObjective(1, -1.0 * counterZeroes);		  
	  }
  
	}
