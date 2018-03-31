package com.antispam.controllers;

import com.antispam.Services.JMetal.JMetalConnection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class JMetalController {

    @GetMapping("/algorithms")
    public String listAlgorithms(Model model) {
        JMetalConnection jMetalConnection = new JMetalConnection();
        model.addAttribute("algorithms", jMetalConnection.TestMethod());
        return "algorithmList";
    }

}
