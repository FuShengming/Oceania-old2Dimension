package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.blImpl.GraphCalculateImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @Autowired
    GraphCalculateImpl graphCalculateImpl;
    //Hello World
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        graphCalculateImpl.initializeGraph("call_dependencies_update.txt");

        return String.format("Hello %s!", name);
    }

}
