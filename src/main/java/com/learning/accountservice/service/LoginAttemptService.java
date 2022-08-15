package com.learning.accountservice.service;

import com.learning.accountservice.model.User0;
import com.learning.accountservice.model.enums.EventMsg;
import com.learning.accountservice.model.enums.LoginAttempt;
import com.learning.accountservice.repository.User0Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginAttemptService {
    private final int MAX_LOGIN_ATTEMPTS = 5;
    private final Logger logger = LoggerFactory.getLogger(LoginAttemptService.class);

    @Autowired
    User0Repository user0Repository;

    public LoginAttemptService() {
    }

    public void updateLoginAttempts(String username, LoginAttempt loginAttempt) {

        Optional<User0> user0Optional = user0Repository.findByUsername(username);
        int attempts = 0;

        if (user0Optional.isPresent()) {
            User0 user0 = user0Optional.get();

            if (loginAttempt == LoginAttempt.FAILURE) {
                attempts = user0.getFailedLoginAttempts();
                attempts++;
                logger.info(EventMsg.LOGIN_FAILED.getMessage());
            }

            if (attempts > MAX_LOGIN_ATTEMPTS) {
                user0.setAccountNonLocked(false);
                logger.info(EventMsg.BRUTE_FORCE.getMessage());
                logger.info(EventMsg.LOCK_USER.getMessage());
            }

            user0.setFailedLoginAttempts(attempts);
            user0Repository.save(user0);

        } else {
            logger.info(EventMsg.LOGIN_FAILED.getMessage());
        }

    }

}
