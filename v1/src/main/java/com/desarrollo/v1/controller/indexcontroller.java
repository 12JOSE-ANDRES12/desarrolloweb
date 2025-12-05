package com.desarrollo.v1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class indexcontroller {
    
    @GetMapping("/")
    public String home(){
        return "index";
    }
    
    @GetMapping("/index")
    public String index(){
        return "index";
    }
    
    @GetMapping("/vision")
    public String vision(){
        return "vision";
    }
    
    @GetMapping("/mision")
    public String mision(){
        return "users/mision";
    }
}
