package com.smartagri.dto;

import com.smartagri.model.Role;

public class UserDto {
    private Long id;
    private String fullName;
    private String email;
    private Role role;

    public UserDto() {
    }

    public UserDto(Long id, String fullName, String email, Role role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    public static UserDtoBuilder builder() {
        return new UserDtoBuilder();
    }

    public static class UserDtoBuilder {
        private Long id;
        private String fullName;
        private String email;
        private Role role;

        public UserDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserDtoBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public UserDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserDtoBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public UserDto build() {
            return new UserDto(id, fullName, email, role);
        }
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
