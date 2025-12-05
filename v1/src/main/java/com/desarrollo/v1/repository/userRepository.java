package com.desarrollo.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desarrollo.v1.model.usermodel;

@Repository
public interface userRepository extends JpaRepository<usermodel, Long> {
    
}
