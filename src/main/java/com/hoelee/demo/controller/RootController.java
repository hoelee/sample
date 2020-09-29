package com.hoelee.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/admin")
public class RootController {
    
    @ResponseBody
    @GetMapping(value = {"","/"})
    public String index(){
        return "Hello world";
    }
}
