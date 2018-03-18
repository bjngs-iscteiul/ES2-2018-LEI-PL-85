package com.antispam.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;


public class JMetalController {


    // inject via application.properties
    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        jmetalapplication jps = new jmetalapplication();
        model.put("Algorithms", jps.getalgorithmslist());
        return "index";
    }

}
