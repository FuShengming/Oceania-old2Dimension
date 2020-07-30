package com.old2dimension.OCEANIA.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PageController {
    @RequestMapping(value = {"/index", "/"})
    public String index() {
        return "/html/index.html";
    }

    @RequestMapping("/workspace")
    public String workspace() {
        return "/html/workspace.html";
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

    @RequestMapping("/notification")
    public String notification() {
        return "/html/notification.html";
    }

    @RequestMapping("/tasks")
    public String tasks() {
        return "/html/tasks.html";
    }

    @RequestMapping("/groups")
    public String groups() {
        return "/html/groups.html";
    }
    @RequestMapping("/test")
    public String cy() {
        return "/html/test.html";
    }
}
