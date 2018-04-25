package interfaces;

import java.util.HashMap;

public interface jMetalAlgorithmDinamic {


    /**
     * Permite fazer set das propriedades com valores Integer
     * @param hmapProperty HashMap<String,Integer>
     */
    public void setIntHmapProperty(HashMap<String,Integer> hmapProperty);

    /**
     * Permite fazer set das propriedades com valores Double
     * @param hmapProperty HashMap<String,Double>
     */
    public void setDoubleHmapProperty(HashMap<String,Double> hmapProperty);

    /**
     * Permite obter o hash com os valores inteiros default e nome das propriedades (Keys) do algoritmo
     * @return null if don't have hash
     */
    public HashMap<String,Integer> getIntHmapProperty();

    /**
     * Permite obter o hash com os valores Double default e nome das propriedades (Keys) do algoritmo
     * @return null if don't have hash
     */
    public HashMap<String,Double> getDoubleHmapProperty();
}
