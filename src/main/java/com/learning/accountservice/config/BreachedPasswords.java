package com.learning.accountservice.config;

import java.util.List;

public class BreachedPasswords {
    private final List<String> listOfBreachedPasswords;

    public BreachedPasswords() {
        listOfBreachedPasswords = List.of("PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
                "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
                "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember");
    }

    public List<String> getListOfBreachedPasswords() {
        return listOfBreachedPasswords;
    }

}
