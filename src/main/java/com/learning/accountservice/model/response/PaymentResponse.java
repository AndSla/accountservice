package com.learning.accountservice.model.response;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class PaymentResponse {
    private String name;
    private String lastName;
    private String period;
    private String salary;

    public PaymentResponse() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        DateTimeFormatter formatterIn = DateTimeFormatter.ofPattern("MM-yyyy");
        DateTimeFormatter formatterOut = DateTimeFormatter.ofPattern("MMMM-yyyy");
        YearMonth monthYear = YearMonth.parse(period, formatterIn);
        this.period = monthYear.format(formatterOut);
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        BigDecimal bd = new BigDecimal(salary).movePointLeft(2);
        String[] amount = bd.toString().split("\\.");
        String dollars = amount[0];
        String cents = amount[1];
        this.salary = dollars + " dollar(s) " + cents + " cent(s)";
    }
}
