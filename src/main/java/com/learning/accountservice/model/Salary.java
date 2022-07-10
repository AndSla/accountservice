package com.learning.accountservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.YearMonth;

public class Salary {

    private String employee;
    private YearMonth period;
    private Long salary;

    public Salary() {
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-yyyy")
    public YearMonth getPeriod() {
        return period;
    }

    public void setPeriod(YearMonth period) {
        this.period = period;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }
}
