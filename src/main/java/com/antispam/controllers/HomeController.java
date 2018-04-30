package com.antispam.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    @GetMapping({"/","/home"})
    public String test() { return "index"; }

    @GetMapping({"/problemView"})
    public String test2() { return "problemView"; }

    @GetMapping({"/faqView"})
    public String test3() { return "faqView"; }

    @GetMapping({"/supportView"})
    public String test4() { return "supportView"; }
}
