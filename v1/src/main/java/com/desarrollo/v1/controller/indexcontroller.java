package com.desarrollo.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.desarrollo.v1.service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class indexcontroller {
    
    @Autowired
    UserService userService;
    
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

    @GetMapping("/menu")
    public String menu(Model model, HttpSession session){
        // Verificar si el usuario está autenticado
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/";
        }
        model.addAttribute("listarusuarios", userService.getAllUsers());
        return "users/list";
    }

    @GetMapping("/pedidos")
    public String pedidos(HttpSession session){
        // Verificar si el usuario está autenticado
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/";
        }
        return "pedidos";
    }
}
