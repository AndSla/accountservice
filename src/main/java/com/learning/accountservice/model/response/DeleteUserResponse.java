package com.learning.accountservice.model.response;

public class DeleteUserResponse {
    private String user;
    private String status;

    public DeleteUserResponse(){
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
