package com.desarrollo.v1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

@RequestMapping("/index")
public class indexcontroller {
    @GetMapping("/home")
    public String home(){
        return "Hola Mundo Spring Boot";
    }
}
