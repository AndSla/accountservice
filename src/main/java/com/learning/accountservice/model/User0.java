package com.learning.accountservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class User0 implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Pattern(regexp = "[a-zA-Z]+")
    private String name;

    @NotNull
    @Pattern(regexp = "[a-zA-Z]+")
    private String lastname;

    @JsonProperty(value = "email")
    @NotNull
    @Pattern(regexp = "\\w+@acme\\.com")
    private String username;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Enumerated(EnumType.STRING)                    // table column will be of enum type
    @ElementCollection(fetch = FetchType.EAGER)     // automatically creates one-to-many mapping
    private List<Role> roles;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 12, message = "Password length must be 12 chars minimum!")
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user0", cascade = CascadeType.ALL)
    private List<Salary> salaries;

    @JsonIgnore
    private boolean accountNonExpired;

    @JsonIgnore
    private boolean accountNonLocked;

    @JsonIgnore
    private boolean credentialsNonExpired;

    @JsonIgnore
    private boolean enabled;

    public User0() {
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void grantRole(Role role) {
        if (roles == null) roles = new ArrayList<>();
        roles.add(role);
    }

    public void removeRole(Role role) {
        roles.remove(role);
    }

    public List<Salary> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<Salary> salaries) {
        this.salaries = salaries;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.toString())));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
