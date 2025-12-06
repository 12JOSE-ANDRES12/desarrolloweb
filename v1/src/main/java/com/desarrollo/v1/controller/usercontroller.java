package com.desarrollo.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.desarrollo.v1.service.UserService;
import com.desarrollo.v1.model.usermodel;
import java.util.Optional;
import jakarta.servlet.http.HttpSession;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@Controller
@RequestMapping("/users")
@Tag(name = "Users", description = "Operaciones relacionadas con usuarios (registro, login, listado)")
public class usercontroller {
    @Autowired
    UserService userService;

    @GetMapping("/listar")
    @Operation(summary = "Listar usuarios", description = "Retorna la vista con la lista de usuarios")
    public String listUsers(Model model){
        model.addAttribute("listarusuarios", userService.getAllUsers());
        return "users/list";
    }
    
    @PostMapping("/registro")
    @Operation(summary = "Registro de usuario", description = "Registra un nuevo usuario a partir de los campos enviados por formulario")
    public String registro(@RequestParam String nombre, 
                          @RequestParam String email,
                          @RequestParam String password,
                          @RequestParam String confirmPassword,
                          RedirectAttributes redirectAttributes) {
        
        // Validar que las contraseñas coincidan
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Las contraseñas no coinciden");
            return "redirect:/";
        }
        
        // Validar campos vacíos
        if (nombre.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Todos los campos son requeridos");
            return "redirect:/";
        }
        
        // Intentar registrar
        boolean registrado = userService.registrarUsuario(nombre, email, password);
        
        if (registrado) {
            redirectAttributes.addFlashAttribute("exito", "Registro exitoso. Por favor inicia sesión");
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("error", "El correo electrónico ya está registrado");
            return "redirect:/";
        }
    }
    
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica al usuario y crea la sesión")
    public String login(@RequestParam String email,
                       @RequestParam String password,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        
        Optional<usermodel> usuario = userService.iniciarSesion(email, password);
        
        if (usuario.isPresent()) {
            session.setAttribute("usuarioLogueado", usuario.get());
            redirectAttributes.addFlashAttribute("exito", "Bienvenido " + usuario.get().getName());
            
            // Redirigir según rol
            if (usuario.get().getRole() != null && usuario.get().getRole().toString().equals("ADMIN")) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/menu";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Correo o contraseña incorrectos");
            return "redirect:/";
        }
    }
    
    @GetMapping("/logout")
    @Operation(summary = "Cerrar sesión", description = "Elimina la sesión del usuario autenticado")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.removeAttribute("usuarioLogueado");
        redirectAttributes.addFlashAttribute("exito", "Sesión cerrada correctamente");
        return "redirect:/";
    }
}

