package com.example.demoteb.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/index")
    public String mainPage(){

        log.info("index");
        return "index";
    }
}
