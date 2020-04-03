package com.old2dimension.OCEANIA.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PageController {

    @RequestMapping("/index")
    public String index() {
        return "/html/index.html";
    }

    @RequestMapping("/graphpage")
    public String test() {
        return "/html/graphpage.html";
    }

    @RequestMapping("/cy")
    public String cy() {
        return "/html/cytotest.html";
    }

    @RequestMapping("/login")
    public String login() {
        return "/html/login.html";
    }
}
