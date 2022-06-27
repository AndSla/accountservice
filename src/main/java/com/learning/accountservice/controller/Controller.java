package com.learning.accountservice.controller;

import com.learning.accountservice.exception.BadUserException;
import com.learning.accountservice.model.NeatUser;
import com.learning.accountservice.utils.Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    Utils utils = new Utils();

    @PostMapping("api/auth/signup")
    public NeatUser signUp(@RequestBody NeatUser neatUser) {
        if (utils.isUserValid(neatUser)) {
            NeatUser newNeatUser = new NeatUser();
            newNeatUser.setUsername(neatUser.getUsername());
            newNeatUser.setLastname(neatUser.getLastname());
            newNeatUser.setEmail(neatUser.getEmail());
            newNeatUser.setPassword(neatUser.getPassword());
            return newNeatUser;
        } else {
            throw new BadUserException();
        }
    }



}
