package com.learning.accountservice.utils;

import com.learning.accountservice.model.User0;

public class Utils {

    public boolean isUserValid(User0 user0) {
        if (user0.getName() == null ||
                user0.getLastname() == null ||
                user0.getEmail() == null ||
                user0.getPassword() == null) {
            return false;

        }
        return user0.getName().matches("[a-zA-Z]+") &&
                user0.getLastname().matches("[a-zA-Z]+") &&
                user0.getEmail().matches("\\w+@acme\\.com") &&
                user0.getPassword().matches("\\S+");
    }

}
