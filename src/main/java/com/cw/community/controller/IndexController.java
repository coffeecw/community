package com.cw.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {
    //主页
    @GetMapping("/")
    public String index() {
        return "index";
    }

}



