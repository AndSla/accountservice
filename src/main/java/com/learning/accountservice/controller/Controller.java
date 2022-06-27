package com.learning.accountservice.controller;

import com.learning.accountservice.exception.BadUserException;
import com.learning.accountservice.exception.UserExistsException;
import com.learning.accountservice.model.User0;
import com.learning.accountservice.repository.User0Repository;
import com.learning.accountservice.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    Utils utils = new Utils();

    @Autowired
    User0Repository user0Repository;

    @PostMapping("api/auth/signup")
    public User0 signUp(@RequestBody User0 user0) {
        if (utils.isUserValid(user0)) {
            User0 newUser0 = new User0();
            newUser0.setName(user0.getName());
            newUser0.setLastname(user0.getLastname());
            newUser0.setEmail(user0.getEmail());
            newUser0.setPassword(user0.getPassword());
            if (user0Repository.existsByEmail(user0.getEmail())) {
                throw new UserExistsException();
            } else {
                user0Repository.save(newUser0);
                return newUser0;
            }
        } else {
            throw new BadUserException();
        }
    }

}
