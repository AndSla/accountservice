package com.learning.accountservice.model.response;

public class ChangePassResponse {

    private String email;
    private String status;

    public ChangePassResponse() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
