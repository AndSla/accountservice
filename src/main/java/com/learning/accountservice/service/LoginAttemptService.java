package com.learning.accountservice.service;

import com.learning.accountservice.model.User0;
import com.learning.accountservice.model.enums.EventMsg;
import com.learning.accountservice.model.enums.LoginAttempt;
import com.learning.accountservice.repository.User0Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginAttemptService {
    private final int MAX_LOGIN_ATTEMPTS = 5;

    @Autowired
    User0Repository user0Repository;

    @Autowired
    LogService logService;

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

                if (attempts > MAX_LOGIN_ATTEMPTS) {
                    user0.setAccountNonLocked(false);
                    logService.log(EventMsg.BRUTE_FORCE.getMessage(), "", "", "");
                    logService.log(EventMsg.LOCK_USER.getMessage(), "", "", "");
                    return;
                }

                logService.log(EventMsg.LOGIN_FAILED.getMessage(), "", "", "");
            }

            user0.setFailedLoginAttempts(attempts);
            user0Repository.save(user0);

        } else {
            logService.log(EventMsg.LOGIN_FAILED.getMessage(), "", "", "");
        }

    }

}
