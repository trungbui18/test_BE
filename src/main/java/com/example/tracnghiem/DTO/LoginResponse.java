package com.example.tracnghiem.DTO;

import com.example.tracnghiem.Model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class LoginResponse {
    private int id;
    private String username;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

    public LoginResponse(int id, String username, String email, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public LoginResponse() {
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
