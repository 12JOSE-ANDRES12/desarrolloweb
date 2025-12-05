package com.desarrollo.v1.service;

import org.springframework.stereotype.Service;

import com.desarrollo.v1.repository.userRepository;
import com.desarrollo.v1.model.usermodel;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
@Service
public class UserService {
    @Autowired
    userRepository userRepository;  

    public List<usermodel> getAllUsers(){
        return userRepository.findAll();
    }
}
