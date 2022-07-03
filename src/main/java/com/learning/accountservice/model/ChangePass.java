package com.learning.accountservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangePass {

    @JsonProperty("new_password")
    private String newPassword;

    public ChangePass() {
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
