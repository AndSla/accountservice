package com.learning.accountservice.model;

import com.learning.accountservice.model.enums.Operation;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class SetAccess {

    private String user;

    @Enumerated(EnumType.STRING)
    private Operation operation;

    public SetAccess() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
