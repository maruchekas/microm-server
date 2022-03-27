package ru.maruchekas.micromessage.model.enumeration;

public enum Permission {
    USER("user:read"),
    ADMIN("user:write");
    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}
