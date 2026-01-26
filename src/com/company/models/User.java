package com.company.models;

import com.company.models.enums.Role;

public class User {
    private int id;
    private String email;
    private String password;
    private Role role;

    public User() {
    }

    public User(int id, String email, String password, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role == null ? Role.USER : role;
    }

    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role == null ? Role.USER : role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role == null ? Role.USER : role;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", email='" + email + "', role=" + role + "}";
    }
}
