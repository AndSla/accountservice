package com.learning.accountservice.repository;

import com.learning.accountservice.model.User0;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface User0Repository extends JpaRepository<User0, Long> {

    boolean existsByUsernameIgnoreCase(String username);

    Optional<User0> findByUsernameIgnoreCase(String username);

}
