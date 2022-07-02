package com.learning.accountservice.service;

import com.learning.accountservice.model.User0;
import com.learning.accountservice.repository.User0Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    User0Repository user0Repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User0> user0Optional = user0Repository.findByUsername(username);

        if (user0Optional.isPresent()) {
            return user0Optional.get();
        } else {
            throw new UsernameNotFoundException(username);
        }

    }
}
