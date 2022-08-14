package com.learning.accountservice.authevent;

import com.learning.accountservice.model.enums.LoginAttempt;
import com.learning.accountservice.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    LoginAttemptService loginAttemptService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        LoginAttempt loginAttempt = LoginAttempt.SUCCESS;
        loginAttemptService.updateLoginAttempts(username, loginAttempt);
    }

}
