package com.learning.accountservice.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.validation.Valid;
import java.util.List;

// Class for wrap list of salaries - payments - in order to validate each object in the list

public class ValidList<E> {

    @Valid
    private List<E> payments;

    public ValidList() {
    }

    @JsonCreator
    public ValidList(List<E> payments) {
        this.payments = payments;
    }

    @JsonValue
    public List<E> getPayments() {
        return payments;
    }

    public void setPayments(List<E> payments) {
        this.payments = payments;
    }
}
