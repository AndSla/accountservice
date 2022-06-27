package com.learning.accountservice.controller;

import com.learning.accountservice.exception.BadUserException;
import com.learning.accountservice.model.User0;
import com.learning.accountservice.utils.Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    Utils utils = new Utils();

    @PostMapping("api/auth/signup")
    public User0 signUp(@RequestBody User0 user0) {
        if (utils.isUserValid(user0)) {
            User0 newUser0 = new User0();
            newUser0.setName(user0.getName());
            newUser0.setLastname(user0.getLastname());
            newUser0.setEmail(user0.getEmail());
            newUser0.setPassword(user0.getPassword());
            return newUser0;
        } else {
            throw new BadUserException();
        }
    }

}
