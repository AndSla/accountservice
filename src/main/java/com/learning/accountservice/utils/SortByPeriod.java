package com.learning.accountservice.utils;

import com.learning.accountservice.model.Salary;

import java.util.Comparator;

public class SortByPeriod implements Comparator<Salary> {

    @Override
    public int compare(Salary a, Salary b) {
        return b.getPeriod().compareTo(a.getPeriod());
    }

}
