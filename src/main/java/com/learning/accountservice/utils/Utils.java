package com.learning.accountservice.utils;

import com.learning.accountservice.model.User;

public class Utils {

    public boolean isUserValid(User user) {
        if (user.getName() == null ||
                user.getLastname() == null ||
                user.getEmail() == null ||
                user.getPassword() == null) {
            return false;

        }
        return user.getName().matches("[a-zA-Z]+") &&
                user.getLastname().matches("[a-zA-Z]+") &&
                user.getEmail().matches("\\w+@acme\\.com") &&
                user.getPassword().matches("\\S+");
    }

}
