package com.learning.accountservice.model;

public enum Role {
    ROLE_ADMIN(RoleGroup.ADMINS),
    ROLE_USER(RoleGroup.USERS),
    ROLE_ACCOUNTANT(RoleGroup.USERS);

    RoleGroup group;

    Role(RoleGroup group) {
        this.group = group;
    }

    public RoleGroup getGroup() {
        return group;
    }

}
