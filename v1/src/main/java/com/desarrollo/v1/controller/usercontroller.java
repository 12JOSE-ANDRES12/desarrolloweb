package com.desarrollo.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.desarrollo.v1.service.UserService;
@Controller
@RequestMapping("/users")
public class usercontroller {
    @Autowired
    UserService userService;

    @GetMapping("/listar")
    public String listUsers(Model model){
        model.addAttribute("listarusuarios", userService.getAllUsers());
        return "users/list";
    }
}

