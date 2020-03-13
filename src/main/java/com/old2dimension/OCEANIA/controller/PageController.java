package com.old2dimension.OCEANIA.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PageController {

    @RequestMapping("/index")
    public String index() {
        return "/html/index.html";
    }

    @RequestMapping("/test")
    public String test() {
        return "/html/graphtest.html";
    }
}
