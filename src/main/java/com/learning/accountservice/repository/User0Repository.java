package com.learning.accountservice.repository;

import com.learning.accountservice.model.User0;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface User0Repository extends JpaRepository<User0, Long> {

    boolean existsByEmail(String email);

    Optional<User0> findByUsername(String username);

}
