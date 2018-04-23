package com.antispam.Controllers;

import com.antispam.Services.JMetal.JMetalConnection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JMetalController {

    @GetMapping("/algorithms")
    public String listAlgorithms(Model model) {
        JMetalConnection jMetalConnection = new JMetalConnection();
        model.addAttribute("algorithms", jMetalConnection.TestMethod());
        //model.addAttribute("algorithmsList", jMetalConnection.AlgorithmName());
       // jMetalConnection.startJMetalAlgorithm("NSGAII");
        return "algorithmList";
    }

}
