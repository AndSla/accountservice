package com.learning.accountservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Size;

public class ChangePass {

    @JsonProperty("new_password")
    @Size(min = 12, message = "Password length must be 12 chars minimum!")
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
