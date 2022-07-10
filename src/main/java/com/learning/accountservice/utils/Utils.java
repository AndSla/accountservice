package com.learning.accountservice.utils;

import com.learning.accountservice.config.BreachedPasswords;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public class Utils {

    BreachedPasswords breachedPasswords = new BreachedPasswords();

    public boolean isNewPasswordSameAsOldPassword(String newPass, String oldPass) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.matches(newPass, oldPass);
    }

    public boolean isOnBreachedPasswordsList(String newPass) {
        List<String> breachedPasswordsList = breachedPasswords.getListOfBreachedPasswords();

        return breachedPasswordsList.contains(newPass);
    }

}
