package com.desarrollo.v1.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.desarrollo.v1.model.Opcion;
import com.desarrollo.v1.service.ItemCarritoService;
import com.desarrollo.v1.service.OpcionService;
import com.desarrollo.v1.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@Controller
@RequestMapping("/opciones")
@Tag(name = "Options", description = "Gestión de opciones/productos del menú")
public class OpcionController {
    @Autowired
    OpcionService opcionService;

    @Autowired
    UserService userService;

    @Autowired
    ItemCarritoService itemCarritoService;

    @GetMapping("/listar")
    @Operation(summary = "Listar opciones", description = "Muestra la vista con todas las opciones")
    public String listarOpciones(Model model) {
        model.addAttribute("opciones", opcionService.obtenerTodasLasOpciones());
        return "opciones/listar";
    }

    @GetMapping("/categoria")
    @Operation(summary = "Listar por categoría", description = "Lista las opciones filtradas por categoría")
    public String listarPorCategoria(@RequestParam String categoria, Model model) {
        model.addAttribute("opciones", opcionService.obtenerOpcionesPorCategoria(categoria));
        model.addAttribute("categoria", categoria);
        return "opciones/listar";
    }

    @PostMapping("/crear")
    @Operation(summary = "Crear opción", description = "Crea una nueva opción/producto a partir del formulario")
    public String crearOpcion(@RequestParam String nombre,
                             @RequestParam String descripcion,
                             @RequestParam Double precio,
                             @RequestParam String categoria,
                             @RequestParam Boolean disponible,
                             RedirectAttributes redirectAttributes) {
        
        try {
            opcionService.crearOpcion(nombre, descripcion, precio, categoria, disponible);
            redirectAttributes.addFlashAttribute("exito", "Opción creada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear la opción");
        }
        
        return "redirect:/opciones/listar";
    }

    @GetMapping("/editar")
    @Operation(summary = "Editar opción (vista)", description = "Carga la vista de edición para una opción existente")
    public String editarOpcion(@RequestParam Long id, Model model) {
        Optional<Opcion> opcion = opcionService.obtenerOpcionPorId(id);
        if (opcion.isPresent()) {
            model.addAttribute("opcion", opcion.get());
            return "opciones/editar";
        }
        return "redirect:/opciones/listar";
    }

    @PostMapping("/actualizar")
    @Operation(summary = "Actualizar opción", description = "Actualiza los datos de una opción existente")
    public String actualizarOpcion(@RequestParam Long id,
                                  @RequestParam String nombre,
                                  @RequestParam String descripcion,
                                  @RequestParam Double precio,
                                  @RequestParam String categoria,
                                  @RequestParam Boolean disponible,
                                  RedirectAttributes redirectAttributes) {
        
        try {
            opcionService.actualizarOpcion(id, nombre, descripcion, precio, categoria, disponible);
            redirectAttributes.addFlashAttribute("exito", "Opción actualizada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar la opción");
        }
        
        return "redirect:/opciones/listar";
    }

    @GetMapping("/eliminar")
    @Operation(summary = "Eliminar opción", description = "Elimina una opción por su id")
    public String eliminarOpcion(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            if (opcionService.eliminarOpcion(id)) {
                redirectAttributes.addFlashAttribute("exito", "Opción eliminada exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("error", "Error al eliminar la opción");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la opción");
        }
        
        return "redirect:/opciones/listar";
    }
}
