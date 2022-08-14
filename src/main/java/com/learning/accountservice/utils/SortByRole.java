package com.learning.accountservice.utils;

import com.learning.accountservice.model.Role;

import java.util.Comparator;

public class SortByRole implements Comparator<Role> {

    @Override
    public int compare(Role role1, Role role2) {
        return role1.toString().compareTo(role2.toString());
    }

}
