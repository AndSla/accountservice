package com.learning.accountservice.repository;

import com.learning.accountservice.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary, Long> {

    boolean existsByEmployeeAndPeriod(String employee, String period);

}
