package com.learning.accountservice.service;

import com.learning.accountservice.exception.UserNotFoundException;
import com.learning.accountservice.model.User0;
import com.learning.accountservice.model.enums.LoginAttempt;
import com.learning.accountservice.repository.User0Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginAttemptService {
    private final int MAX_LOGIN_ATTEMPTS = 5;

    @Autowired
    User0Repository user0Repository;

    public LoginAttemptService() {
    }

    public void updateLoginAttempts(String username, LoginAttempt loginAttempt) {

        Optional<User0> user0Optional = user0Repository.findByUsername(username);
        User0 user0;
        int attempts = 0;

        if (user0Optional.isPresent()) {
            user0 = user0Optional.get();
        } else {
            throw new UserNotFoundException(HttpStatus.NOT_FOUND);
        }

        if (loginAttempt == LoginAttempt.FAILURE) {
            attempts = user0.getFailedLoginAttempts();
            attempts++;
        }

        if (attempts > MAX_LOGIN_ATTEMPTS) {
            user0.setAccountNonLocked(false);
        }

        user0.setFailedLoginAttempts(attempts);
        user0Repository.save(user0);

    }

}
