package com.learning.accountservice.repository;

import com.learning.accountservice.model.NeatUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NeatUserRepository extends CrudRepository<NeatUser, Long> {

    Optional<NeatUser> findByUsername(String username);

}
