package com.learning.accountservice.repository;

import com.learning.accountservice.model.User0;
import org.springframework.data.jpa.repository.JpaRepository;

public interface User0Repository extends JpaRepository<User0, Long> {

    boolean existsByEmail(String email);

}
