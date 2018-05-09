package Tests;


import com.antispam.Services.JMetal.JMetalConnection;
import org.junit.jupiter.api.Test;
import org.uma.jmetal.runner.multiobjective.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JMetalConnectionTests {

    @Test
    public void testAlgorithmName(){

        //GET DATA TO TEST
        JMetalConnection jm = new JMetalConnection();
        String[] data = jm.AlgorithmName();
        List<String> listdata=Arrays.asList(data);


        //GET REAL DATA
        File folder = null;
        File[] paths;
        folder = new File("src/main/java/org/uma/jmetal/runner/multiobjective");
        paths = folder.listFiles();
        int max = paths.length;



        //THIS METHOD MUST RETURN JUST ALGORITHM NAMES PRESENT ON "folder" IN HARDCODE
        for (int  i = 0 ; i!= max; i ++){
            String algorithmName = paths[i].getName().replace(".java","");
            assert(listdata.contains(algorithmName));
        }

        assert(listdata.contains("ABYSS"));

    }
}
