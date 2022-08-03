package com.learning.accountservice.controller;

import com.learning.accountservice.exception.*;
import com.learning.accountservice.model.ChangePass;
import com.learning.accountservice.model.Role;
import com.learning.accountservice.model.Salary;
import com.learning.accountservice.model.User0;
import com.learning.accountservice.model.response.ChangePassResponse;
import com.learning.accountservice.model.response.ChangeSalaryResponse;
import com.learning.accountservice.model.response.PaymentResponse;
import com.learning.accountservice.model.response.UpdatePayrollsResponse;
import com.learning.accountservice.repository.SalaryRepository;
import com.learning.accountservice.repository.User0Repository;
import com.learning.accountservice.utils.SortByPeriod;
import com.learning.accountservice.utils.Utils;
import com.learning.accountservice.utils.ValidList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class Controller {

    Utils utils = new Utils();

    @Autowired
    User0Repository user0Repository;

    @Autowired
    SalaryRepository salaryRepository;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("api/auth/signup")
    public User0 signUp(@Valid @RequestBody User0 user0) {

        if (utils.isOnBreachedPasswordsList(user0.getPassword())) {
            throw new BreachedPasswordException();
        }

        User0 newUser0 = new User0();
        newUser0.setName(user0.getName());
        newUser0.setLastname(user0.getLastname());
        newUser0.setUsername(user0.getUsername());
        newUser0.setPassword(encoder.encode(user0.getPassword()));
        newUser0.grantRole(Role.ROLE_USER);

        if (user0Repository.existsByUsername(user0.getUsername().toLowerCase())) {
            throw new UserExistsException();
        } else {
            user0Repository.save(newUser0);
            return newUser0;
        }

    }

    @GetMapping("api/empl/payment")
    public Object getPaymentInfo(Authentication auth, @RequestParam(required = false) String period) {

        Optional<User0> user0Optional = user0Repository.findByUsername(auth.getName());
        User0 user0;

        if (user0Optional.isPresent()) {
            user0 = user0Optional.get();
        } else {
            throw new UserNotFoundException();
        }

        if (period != null) {

            Optional<Salary> salaryOptional = salaryRepository.findByEmployeeAndPeriod(auth.getName(), period);

            if (salaryOptional.isPresent()) {

                Salary salary = salaryOptional.get();
                PaymentResponse paymentResponse = new PaymentResponse();
                paymentResponse.setName(user0.getName());
                paymentResponse.setLastname(user0.getLastname());
                paymentResponse.setPeriod(salary.getPeriod());
                paymentResponse.setSalary(salary.getSalary());
                return paymentResponse;

            } else {

                throw new UserOrPeriodNotFoundException();

            }

        }

        List<Salary> salaryList = user0.getSalaries();
        salaryList.sort(new SortByPeriod());

        List<PaymentResponse> paymentResponseList = new ArrayList<>();

        for (Salary salary : salaryList) {
            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setName(user0.getName());
            paymentResponse.setLastname(user0.getLastname());
            paymentResponse.setPeriod(salary.getPeriod());
            paymentResponse.setSalary(salary.getSalary());
            paymentResponseList.add(paymentResponse);
        }

        return paymentResponseList;

    }

    @PostMapping("api/auth/changepass")
    public ChangePassResponse changePassword(
            @Valid @RequestBody ChangePass changePass,
            Authentication auth) {
        String newPassword = changePass.getNewPassword();
        ChangePassResponse changePassResponse = new ChangePassResponse();

        if (auth != null) {
            Optional<User0> user0Optional = user0Repository.findByUsername(auth.getName());

            if (user0Optional.isPresent()) {
                User0 user0 = user0Optional.get();
                String oldPassword = user0.getPassword();

                if (utils.isNewPasswordSameAsOldPassword(newPassword, oldPassword)) {
                    throw new SamePasswordException();
                }

                if (utils.isOnBreachedPasswordsList(newPassword)) {
                    throw new BreachedPasswordException();
                }

                user0.setPassword(encoder.encode(newPassword));
                user0Repository.save(user0);
                changePassResponse.setEmail(user0.getUsername());
                changePassResponse.setStatus("The password has been updated successfully");
            } else {
                throw new UserNotFoundException();
            }

        } else {
            throw new AuthenticationServiceException("Authentication Error");
        }

        return changePassResponse;

    }

    @Transactional
    @PostMapping("api/acct/payments")
    public UpdatePayrollsResponse updatePayrollsResponse(@Valid @RequestBody ValidList<Salary> listOfSalaries) {

        UpdatePayrollsResponse updatePayrollsResponse = new UpdatePayrollsResponse();

        for (Salary salary : listOfSalaries.getPayments()) {
            String employee = salary.getEmployee();
            String period = salary.getPeriod();

            if (!salaryRepository.existsByEmployeeAndPeriod(employee, period)) {

                Optional<User0> user0Optional = user0Repository.findByUsername(employee);
                User0 user0;

                if (user0Optional.isPresent()) {
                    user0 = user0Optional.get();
                } else {
                    throw new UserNotFoundException();
                }

                salary.setUser0(user0);

                List<Salary> user0Salaries = user0.getSalaries();
                user0Salaries.add(salary);
                user0.setSalaries(user0Salaries);

                user0Repository.save(user0);
                salaryRepository.save(salary);

                updatePayrollsResponse.setStatus("Added successfully!");

            } else {

                throw new DuplicatePeriodException();

            }

        }

        return updatePayrollsResponse;
    }

    @PutMapping("api/acct/payments")
    public ChangeSalaryResponse changeSalaryResponse(@Valid @RequestBody Salary salary) {

        ChangeSalaryResponse changeSalaryResponse = new ChangeSalaryResponse();

        String employee = salary.getEmployee();
        String period = salary.getPeriod();
        Long newSalary = salary.getSalary();

        Optional<Salary> salaryOptional = salaryRepository.findByEmployeeAndPeriod(employee, period);

        if (salaryOptional.isPresent()) {

            Salary updateSalary = salaryOptional.get();
            updateSalary.setSalary(newSalary);
            salaryRepository.save(updateSalary);
            changeSalaryResponse.setStatus("Updated successfully!");

        } else {

            throw new UserOrPeriodNotFoundException();

        }

        return changeSalaryResponse;

    }

}
