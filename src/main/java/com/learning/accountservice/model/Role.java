package com.learning.accountservice.model;

public enum Role {
    ROLE_ADMIN(RoleGroup.ADMINS),
    ROLE_USER(RoleGroup.USERS),
    ROLE_ACCOUNTANT(RoleGroup.USERS);

    Role(RoleGroup group) {
    }

    public static boolean existsByName(String name) {
        boolean result = false;

        for (Role role : Role.values()) {
            if (role.name().equals(name)) {
                result = true;
                break;
            }
        }

        return result;
    }

}
