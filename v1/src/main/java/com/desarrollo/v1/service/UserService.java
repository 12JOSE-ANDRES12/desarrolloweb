package com.desarrollo.v1.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desarrollo.v1.model.usermodel;
import com.desarrollo.v1.repository.userRepository;

@Service
public class UserService {
    @Autowired
    userRepository userRepository;  

    public List<usermodel> getAllUsers(){
        return userRepository.findAll();
    }
    
    public boolean registrarUsuario(String nombre, String email, String password) {
        // Validar que el email no exista
        if (userRepository.existsByEmail(email)) {
            return false;
        }
        
        usermodel nuevoUsuario = new usermodel();
        nuevoUsuario.setName(nombre);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setPassword(password);
        nuevoUsuario.setCreatedAt(LocalDateTime.now());
        
        userRepository.save(nuevoUsuario);
        return true;
    }
    
    public Optional<usermodel> iniciarSesion(String email, String password) {
        Optional<usermodel> usuario = userRepository.findByEmail(email);
        
        if (usuario.isPresent()) {
            // Aquí podrías implementar BCrypt para encriptar contraseñas
            if (usuario.get().getPassword().equals(password)) {
                return usuario;
            }
        }
        return Optional.empty();
    }
    
    public Optional<usermodel> obtenerUsuarioPorEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
