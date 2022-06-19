package com.learning.accountservice.authentication;

import com.learning.accountservice.exception.BadUserException;
import com.learning.accountservice.model.User;
import com.learning.accountservice.utils.Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    Utils utils = new Utils();

    @PostMapping("api/auth/signup")
    public User signUp(@RequestBody User user) {
        if (utils.isUserValid(user)) {
            User newUser = new User();
            newUser.setName(user.getName());
            newUser.setLastname(user.getLastname());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            return newUser;
        } else {
            throw new BadUserException();
        }
    }

}
