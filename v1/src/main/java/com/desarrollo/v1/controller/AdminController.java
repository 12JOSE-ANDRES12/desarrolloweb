package com.desarrollo.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.desarrollo.v1.model.usermodel;
import com.desarrollo.v1.model.UserRole;
import com.desarrollo.v1.model.Pedido;
import com.desarrollo.v1.service.UserService;
import com.desarrollo.v1.service.PedidoService;
import jakarta.servlet.http.HttpSession;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@Controller
@RequestMapping("/admin")
@Tag(name = "Admin", description = "Operaciones administrativas del sistema")
public class AdminController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    PedidoService pedidoService;
    
    /**
     * Verifica si el usuario es administrador
     */
    private boolean esAdmin(usermodel usuario) {
        return usuario != null && usuario.getRole() == UserRole.ADMIN;
    }
    
    @GetMapping("/dashboard")
    @Operation(summary = "Panel de administración", description = "Muestra el dashboard con usuarios y pedidos (solo para admin)")
    public String dashboard(HttpSession session, Model model) {
        // Verificar si el usuario está autenticado
        usermodel usuario = (usermodel) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/";
        }
        
        // Verificar si es administrador
        if (!esAdmin(usuario)) {
            return "redirect:/menu";
        }
        
        // Obtener todos los usuarios
        List<usermodel> usuarios = userService.getAllUsers();
        
        // Obtener todos los pedidos
        List<Pedido> pedidos = pedidoService.obtenerTodosPedidos();
        
        // Contar estadísticas
        long totalUsuarios = usuarios.size();
        long totalPedidos = pedidos.size();
        double totalIngresos = pedidos.stream().mapToDouble(Pedido::getTotal).sum();
        long pedidosPendientes = pedidos.stream().filter(p -> "PENDIENTE".equals(p.getEstado())).count();
        
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("totalUsuarios", totalUsuarios);
        model.addAttribute("totalPedidos", totalPedidos);
        model.addAttribute("totalIngresos", totalIngresos);
        model.addAttribute("pedidosPendientes", pedidosPendientes);
        
        return "admin/dashboard";
    }
    
    @GetMapping("/usuarios")
    @Operation(summary = "Listar usuarios", description = "Muestra lista de todos los usuarios registrados")
    public String listarUsuarios(HttpSession session, Model model) {
        usermodel usuario = (usermodel) session.getAttribute("usuarioLogueado");
        if (usuario == null || !esAdmin(usuario)) {
            return "redirect:/";
        }
        
        List<usermodel> usuarios = userService.getAllUsers();
        model.addAttribute("usuarios", usuarios);
        return "admin/usuarios";
    }
    
    @GetMapping("/pedidos")
    @Operation(summary = "Listar pedidos", description = "Muestra lista de todos los pedidos del sistema")
    public String listarPedidos(HttpSession session, Model model) {
        usermodel usuario = (usermodel) session.getAttribute("usuarioLogueado");
        if (usuario == null || !esAdmin(usuario)) {
            return "redirect:/";
        }
        
        List<Pedido> pedidos = pedidoService.obtenerTodosPedidos();
        model.addAttribute("pedidos", pedidos);
        return "admin/pedidos";
    }
}
