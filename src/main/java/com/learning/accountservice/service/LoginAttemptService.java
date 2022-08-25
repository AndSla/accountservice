package com.learning.accountservice.service;

import com.learning.accountservice.model.User0;
import com.learning.accountservice.model.enums.EventMsg;
import com.learning.accountservice.model.enums.LoginAttempt;
import com.learning.accountservice.repository.User0Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class LoginAttemptService {
    private final int MAX_LOGIN_ATTEMPTS = 5;

    @Autowired
    User0Repository user0Repository;

    @Autowired
    LogService logService;

    @Autowired
    HttpServletRequest request;

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

                logService.log(EventMsg.LOGIN_FAILED.name(),
                        username,
                        request.getServletPath(),
                        request.getServletPath());

                if (attempts >= MAX_LOGIN_ATTEMPTS) {
                    user0.setAccountNonLocked(false);
                    user0Repository.save(user0);

                    logService.log(EventMsg.BRUTE_FORCE.name(),
                            username,
                            request.getServletPath(),
                            request.getServletPath());

                    logService.log(EventMsg.LOCK_USER.name(),
                            username,
                            "Lock user " + username,
                            request.getServletPath());

                    return;

                }

            }

            user0.setFailedLoginAttempts(attempts);
            user0Repository.save(user0);

        } else {

            logService.log(EventMsg.LOGIN_FAILED.name(),
                    username,
                    request.getServletPath(),
                    request.getServletPath());

        }

    }

}

