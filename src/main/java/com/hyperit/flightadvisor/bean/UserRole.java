package com.hyperit.flightadvisor.bean;

public enum UserRole {
    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER");

    private final String value;

    public String getValue() {
        return value;
    }

    UserRole(String value) {
        this.value = value;
    }
}
