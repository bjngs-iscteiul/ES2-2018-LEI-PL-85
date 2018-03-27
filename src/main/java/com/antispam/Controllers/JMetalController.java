package com.antispam.Controllers;

import com.antispam.Services.JMetal.JMetalConnection;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.antispam.Services.JMetal.JMetalConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class JMetalController {

    @GetMapping("/algorithms")
    public String listAlgorithms(Model model) {
        JMetalConnection jMetalConnection = new JMetalConnection();
        model.addAttribute("algorithms", jMetalConnection.TestMethod());
        return "algorithmList";
    }

}
