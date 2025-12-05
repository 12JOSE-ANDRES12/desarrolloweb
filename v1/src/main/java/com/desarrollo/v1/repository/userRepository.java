package com.desarrollo.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desarrollo.v1.model.usermodel;
import java.util.Optional;

@Repository
public interface userRepository extends JpaRepository<usermodel, Long> {
    Optional<usermodel> findByEmail(String email);
    boolean existsByEmail(String email);
}
