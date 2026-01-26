package com.smartagri.dto;

import com.smartagri.model.Role;

public class AuthenticationResponse {

    private String token;
    private Role role;
    private Long id;
    private String fullName;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String token, Role role, Long id, String fullName) {
        this.token = token;
        this.role = role;
        this.id = id;
        this.fullName = fullName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
