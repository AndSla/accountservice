package com.learning.accountservice.model.enums;

public enum Role {
    ROLE_ADMINISTRATOR(RoleGroup.ADMINS),
    ROLE_USER(RoleGroup.USERS),
    ROLE_ACCOUNTANT(RoleGroup.USERS),
    ROLE_AUDITOR(RoleGroup.USERS);

    final RoleGroup group;

    Role(RoleGroup group) {
        this.group = group;
    }

    public RoleGroup getGroup() {
        return group;
    }

}
