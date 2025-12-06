package com.desarrollo.v1.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.desarrollo.v1.model.ItemCarrito;
import com.desarrollo.v1.model.Opcion;
import com.desarrollo.v1.model.usermodel;
import com.desarrollo.v1.repository.OpcionRepository;
import com.desarrollo.v1.service.ItemCarritoService;
import com.desarrollo.v1.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/carrito")
public class CarritoController {
    @Autowired
    ItemCarritoService itemCarritoService;

    @Autowired
    UserService userService;

    @Autowired
    OpcionRepository opcionRepository;

    @GetMapping("/ver")
    public String verCarrito(HttpSession session, Model model) {
        // Verificar si el usuario está autenticado
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/";
        }

        usermodel usuario = (usermodel) session.getAttribute("usuarioLogueado");
        List<ItemCarrito> items = itemCarritoService.obtenerCarritoDelUsuario(usuario);
        
        Double subtotal = itemCarritoService.calcularSubtotalCarrito(usuario);
        Double impuestos = subtotal * 0.10;
        Double envio = subtotal > 50 ? 0.0 : 5.0;
        Double total = subtotal + impuestos + envio;

        model.addAttribute("items", items);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("impuestos", impuestos);
        model.addAttribute("envio", envio);
        model.addAttribute("total", total);

        return "carrito/ver";
    }

    @PostMapping("/agregar")
    public String agregarAlCarrito(@RequestParam Long opcionId,
                                  @RequestParam(defaultValue = "1") Integer cantidad,
                                  @RequestParam(defaultValue = "") String notas,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        
        // Verificar si el usuario está autenticado
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/";
        }

        try {
            usermodel usuario = (usermodel) session.getAttribute("usuarioLogueado");
            itemCarritoService.agregarAlCarrito(usuario, opcionId, cantidad, notas);
            redirectAttributes.addFlashAttribute("exito", "Producto agregado al carrito");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al agregar el producto al carrito");
        }

        return "redirect:/carrito/ver";
    }

    @GetMapping("/eliminar")
    public String eliminarDelCarrito(@RequestParam Long itemId,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes) {
        
        // Verificar si el usuario está autenticado
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/";
        }

        try {
            if (itemCarritoService.eliminarDelCarrito(itemId)) {
                redirectAttributes.addFlashAttribute("exito", "Producto eliminado del carrito");
            } else {
                redirectAttributes.addFlashAttribute("error", "Error al eliminar el producto");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el producto");
        }

        return "redirect:/carrito/ver";
    }

    @PostMapping("/actualizar")
    public String actualizarCantidad(@RequestParam Long itemId,
                                    @RequestParam Integer cantidad,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes) {
        
        // Verificar si el usuario está autenticado
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/";
        }

        try {
            if (cantidad < 1) {
                redirectAttributes.addFlashAttribute("error", "La cantidad debe ser mayor a 0");
            } else {
                itemCarritoService.actualizarCantidad(itemId, cantidad);
                redirectAttributes.addFlashAttribute("exito", "Cantidad actualizada");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar la cantidad");
        }

        return "redirect:/carrito/ver";
    }

    @GetMapping("/vaciar")
    public String vaciarCarrito(HttpSession session, RedirectAttributes redirectAttributes) {
        // Verificar si el usuario está autenticado
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/";
        }

        try {
            usermodel usuario = (usermodel) session.getAttribute("usuarioLogueado");
            itemCarritoService.vaciarCarrito(usuario);
            redirectAttributes.addFlashAttribute("exito", "Carrito vaciado");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al vaciar el carrito");
        }

        return "redirect:/carrito/ver";
    }

    @PostMapping("/agregar-ajax")
    @ResponseBody
    public String agregarAlCarritoAjax(@RequestParam String nombre,
                                      @RequestParam Double precio,
                                      @RequestParam(defaultValue = "1") Integer cantidad,
                                      HttpSession session) {
        
        // Verificar si el usuario está autenticado
        if (session.getAttribute("usuarioLogueado") == null) {
            return "{\"exito\": false, \"mensaje\": \"No autenticado\"}";
        }

        try {
            usermodel usuario = (usermodel) session.getAttribute("usuarioLogueado");
            
            // Buscamos si existe una opción con ese nombre y precio
            Optional<Opcion> opcionOptional = opcionRepository.findByNombreAndPrecio(nombre, precio);
            
            Long opcionId;
            if (opcionOptional.isPresent()) {
                opcionId = opcionOptional.get().getId();
            } else {
                // Si no existe, crear una opción nueva
                Opcion nuevaOpcion = new Opcion();
                nuevaOpcion.setNombre(nombre);
                nuevaOpcion.setDescripcion("");
                nuevaOpcion.setPrecio(precio);
                nuevaOpcion.setCategoria("General");
                nuevaOpcion.setDisponible(true);
                nuevaOpcion.setCreatedAt(java.time.LocalDateTime.now());
                nuevaOpcion.setUpdatedAt(java.time.LocalDateTime.now());
                nuevaOpcion = opcionRepository.save(nuevaOpcion);
                opcionId = nuevaOpcion.getId();
            }
            
            // Agregar al carrito
            itemCarritoService.agregarAlCarrito(usuario, opcionId, cantidad, "");
            return "{\"exito\": true, \"mensaje\": \"Producto agregado al carrito\"}";
            
        } catch (Exception e) {
            return "{\"exito\": false, \"mensaje\": \"Error al agregar el producto\"}";
        }
    }

    @GetMapping("/obtener-items-ajax")
    @ResponseBody
    public String obtenerItemsCarritoAjax(HttpSession session) {
        // Verificar si el usuario está autenticado
        if (session.getAttribute("usuarioLogueado") == null) {
            return "{\"exito\": false, \"items\": [], \"mensaje\": \"No autenticado\"}";
        }

        try {
            usermodel usuario = (usermodel) session.getAttribute("usuarioLogueado");
            List<ItemCarrito> items = itemCarritoService.obtenerCarritoDelUsuario(usuario);
            
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < items.size(); i++) {
                ItemCarrito item = items.get(i);
                json.append("{")
                    .append("\"id\": ").append(item.getId()).append(",")
                    .append("\"nombre\": \"").append(item.getOpcion().getNombre()).append("\",")
                    .append("\"precio\": ").append(item.getPrecioUnitario()).append(",")
                    .append("\"cantidad\": ").append(item.getCantidad()).append(",")
                    .append("\"subtotal\": ").append(item.getSubtotal())
                    .append("}");
                if (i < items.size() - 1) json.append(",");
            }
            json.append("]");
            
            return json.toString();
        } catch (Exception e) {
            return "[]";
        }
    }
}

