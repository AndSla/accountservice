package com.learning.accountservice.controller;

import com.learning.accountservice.exception.*;
import com.learning.accountservice.model.*;
import com.learning.accountservice.model.enums.EventMsg;
import com.learning.accountservice.model.enums.Operation;
import com.learning.accountservice.model.enums.Role;
import com.learning.accountservice.model.response.*;
import com.learning.accountservice.repository.SalaryRepository;
import com.learning.accountservice.repository.User0Repository;
import com.learning.accountservice.service.LogService;
import com.learning.accountservice.utils.SortByPeriod;
import com.learning.accountservice.utils.Utils;
import com.learning.accountservice.utils.ValidList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    LogService logService;

    @PostMapping("api/auth/signup")
    public User0 signUp(@Valid @RequestBody User0 user0, HttpServletRequest request) {

        if (utils.isOnBreachedPasswordsList(user0.getPassword())) {
            throw new BreachedPasswordException();
        }

        if (user0Repository.existsByUsernameIgnoreCase(user0.getUsername())) {
            throw new UserExistsException();
        }

        User0 newUser0 = new User0();
        newUser0.setName(user0.getName());
        newUser0.setLastname(user0.getLastname());
        newUser0.setUsername(user0.getUsername());
        newUser0.setPassword(encoder.encode(user0.getPassword()));
        if (user0Repository.count() == 0) {
            newUser0.grantRole(Role.ROLE_ADMINISTRATOR);
        } else {
            newUser0.grantRole(Role.ROLE_USER);
        }
        user0Repository.save(newUser0);

        logService.log(EventMsg.CREATE_USER.name(),
                "Anonymous",
                newUser0.getUsername(),
                request.getServletPath());

        return newUser0;

    }

    @GetMapping("api/empl/payment")
    public Object getPaymentInfo(Authentication auth, @RequestParam(required = false) String period) {

        Optional<User0> user0Optional = user0Repository.findByUsernameIgnoreCase(auth.getName());
        User0 user0;

        if (user0Optional.isPresent()) {
            user0 = user0Optional.get();
        } else {
            throw new UserNotFoundException(HttpStatus.BAD_REQUEST);
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
            Authentication auth, HttpServletRequest request) {
        String newPassword = changePass.getNewPassword();
        ChangePassResponse changePassResponse = new ChangePassResponse();

        if (auth != null) {
            Optional<User0> user0Optional = user0Repository.findByUsernameIgnoreCase(auth.getName());

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

                logService.log(EventMsg.CHANGE_PASSWORD.name(),
                        user0.getUsername(),
                        user0.getUsername(),
                        request.getServletPath());

                changePassResponse.setEmail(user0.getUsername());
                changePassResponse.setStatus("The password has been updated successfully");
            } else {
                throw new UserNotFoundException(HttpStatus.BAD_REQUEST);
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

                Optional<User0> user0Optional = user0Repository.findByUsernameIgnoreCase(employee);
                User0 user0;

                if (user0Optional.isPresent()) {
                    user0 = user0Optional.get();
                } else {
                    throw new UserNotFoundException(HttpStatus.BAD_REQUEST);
                }

                salary.setUser0(user0);

                List<Salary> user0Salaries = user0.getSalaries();
                user0Salaries.add(salary);
                user0.setSalaries(user0Salaries);

                user0Repository.save(user0);

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

    @PutMapping("api/admin/user/role")
    public User0 setRoles(@RequestBody SetRole setRole,
                          HttpServletRequest request) {

        String username = setRole.getUser();
        Role role = setRole.getRole();
        Operation operation = setRole.getOperation();

        Optional<User0> user0Optional = user0Repository.findByUsernameIgnoreCase(username);
        User0 user0;

        if (user0Optional.isPresent()) {
            user0 = user0Optional.get();
        } else {
            throw new UserNotFoundException(HttpStatus.NOT_FOUND);
        }

        switch (operation) {
            case GRANT:
                for (Role user0Role : user0.getRoles()) {
                    if (!user0Role.getGroup().equals(role.getGroup())) {
                        throw new CombineAdminAndBusinessRolesException();
                    }
                }
                if (!user0.getRoles().contains(role)) {
                    user0.grantRole(role);

                    logService.log(EventMsg.GRANT_ROLE.name(),
                            request.getRemoteUser(),
                            "Grant role " + role.name().substring(5) + " to " + user0.getUsername(),
                            request.getServletPath());

                }
                break;
            case REMOVE:
                if (role == Role.ROLE_ADMINISTRATOR) {
                    throw new DeleteAdminAttemptException();
                }
                if (user0.getRoles().contains(role)) {

                    if (user0.getRoles().size() <= 1) {
                        throw new LastSingleRoleRemovalAttemptException();
                    }

                    user0.removeRole(role);

                    logService.log(EventMsg.REMOVE_ROLE.name(),
                            request.getRemoteUser(),
                            "Remove role " + role.name().substring(5) + " from " + user0.getUsername(),
                            request.getServletPath());

                    break;
                } else {
                    throw new NoSuchRoleException();
                }
        }

        user0Repository.save(user0);

        return user0;
    }

    @GetMapping("api/admin/user")
    public List<User0> getUsers() {

        return user0Repository.findAll();

    }

    @DeleteMapping("api/admin/user/{user}")
    public DeleteUserResponse deleteUser(@PathVariable String user,
                                         HttpServletRequest request) {

        DeleteUserResponse deleteUserResponse = new DeleteUserResponse();

        Optional<User0> user0Optional = user0Repository.findByUsernameIgnoreCase(user);
        User0 user0;

        if (user0Optional.isPresent()) {
            user0 = user0Optional.get();
        } else {
            throw new UserNotFoundException(HttpStatus.NOT_FOUND);
        }

        if (user0.getRoles().contains(Role.ROLE_ADMINISTRATOR)) {
            throw new DeleteAdminAttemptException();
        }

        user0Repository.delete(user0);
        deleteUserResponse.setUser(user0.getUsername());
        deleteUserResponse.setStatus("Deleted successfully!");

        logService.log(EventMsg.DELETE_USER.name(),
                request.getRemoteUser(),
                user0.getUsername(),
                request.getServletPath());

        return deleteUserResponse;

    }

    @PutMapping("api/admin/user/access")
    public SetAccessResponse setAccess(@RequestBody SetAccess setAccess,
                                       HttpServletRequest request) {

        String username = setAccess.getUser();
        Operation operation = setAccess.getOperation();

        Optional<User0> user0Optional = user0Repository.findByUsernameIgnoreCase(username);
        User0 user0;

        if (user0Optional.isPresent()) {
            user0 = user0Optional.get();
            username = user0.getUsername();
        } else {
            throw new UserNotFoundException(HttpStatus.NOT_FOUND);
        }

        switch (operation) {
            case LOCK:
                if (user0.getRoles().contains(Role.ROLE_ADMINISTRATOR)) {
                    throw new LockAdminAttemptException();
                }
                user0.setAccountNonLocked(false);

                logService.log(EventMsg.LOCK_USER.name(),
                        request.getRemoteUser(),
                        "Lock user " + username,
                        request.getServletPath());

                break;

            case UNLOCK:
                user0.setAccountNonLocked(true);
                user0.setFailedLoginAttempts(0);

                logService.log(EventMsg.UNLOCK_USER.name(),
                        request.getRemoteUser(),
                        "Unlock user " + username,
                        request.getServletPath());

        }

        user0Repository.save(user0);

        return new SetAccessResponse(username, operation);

    }

    @GetMapping("api/security/events")
    public List<LogEvent> getLog() {
        return logService.getLogEventRepository().findAll();
    }

}
