package com.learning.accountservice.model.response;

import com.learning.accountservice.model.enums.Operation;

public class SetAccessResponse {

    private String status;


    public SetAccessResponse() {
    }

    public SetAccessResponse(String user, Operation operation) {
        String operationString = "";

        switch (operation) {
            case LOCK:
                operationString = "locked";
                break;
            case UNLOCK:
                operationString = "unlocked";
        }

        status = "User " + user + " " + operationString + "!";

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
