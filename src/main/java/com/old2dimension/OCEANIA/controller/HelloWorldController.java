package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.bl.GraphCaculate;
import com.old2dimension.OCEANIA.bl.GraphCalculateImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @Autowired
    GraphCalculateImp graphCalculateImp;
    //Hello World
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        graphCalculateImp.initializeGraph();
        System.out.println("dasdsa");
        return String.format("Hello %s!", name);
    }

}
