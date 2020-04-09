package com.old2dimension.OCEANIA.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PageController {

    @RequestMapping("/index")
    public String index() {
        return "/html/index.html";
    }

    @RequestMapping("/graph")
    public String graph() {
        return "/html/graph.html";
    }

    @RequestMapping("/statistics")
    public String statistics() {
        return "/html/statistics.html";
    }

    @RequestMapping("/login")
    public String login() {
        return "/html/login.html";
    }

    @RequestMapping("/register")
    public String register() {
        return "/html/register.html";
    }
//    @RequestMapping("/cy")
//    public String cy() {
//        return "/html/cytotest.html";
//    }
}
