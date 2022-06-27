package com.learning.accountservice.utils;

import com.learning.accountservice.model.NeatUser;

public class Utils {

    public boolean isUserValid(NeatUser neatUser) {
        if (neatUser.getUsername() == null ||
                neatUser.getLastname() == null ||
                neatUser.getEmail() == null ||
                neatUser.getPassword() == null) {
            return false;

        }
        return neatUser.getUsername().matches("[a-zA-Z]+") &&
                neatUser.getLastname().matches("[a-zA-Z]+") &&
                neatUser.getEmail().matches("\\w+@acme\\.com") &&
                neatUser.getPassword().matches("\\S+");
    }

}
