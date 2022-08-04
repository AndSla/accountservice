package com.learning.accountservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.learning.accountservice.utils.RoleDeserializer;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class SetRole {
    private String user;

    @Enumerated(EnumType.STRING)
    @JsonDeserialize(using = RoleDeserializer.class)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Operation operation;

    public SetRole() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
