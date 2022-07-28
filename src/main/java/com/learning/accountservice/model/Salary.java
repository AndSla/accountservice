package com.learning.accountservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Entity
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @NotNull
    @Pattern(regexp = "\\w+@acme\\.com", message = "Wrong email!")
    private String employee;

    @Pattern(regexp = "(0[1-9]|1[0-2])-\\d\\d\\d\\d", message = "Wrong date!")
    private String period;

    @Positive(message = "Salary must be non negative!")
    private Long salary;

    @ManyToOne
    @JsonIgnore
    private User0 user0;

    public Salary() {
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User0 getUser0() {
        return user0;
    }

    public void setUser0(User0 user0) {
        this.user0 = user0;
    }
}
