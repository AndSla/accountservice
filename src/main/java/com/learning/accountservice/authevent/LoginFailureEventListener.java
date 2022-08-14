package com.learning.accountservice.authevent;

import com.learning.accountservice.model.enums.LoginAttempt;
import com.learning.accountservice.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class LoginFailureEventListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    LoginAttemptService loginAttemptService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String username = event.getAuthentication().getName();
        LoginAttempt loginAttempt = LoginAttempt.FAILURE;
        loginAttemptService.updateLoginAttempts(username, loginAttempt);
    }

}
