package org.example.user.model.user;

public enum GenderType {

    MALE("MЪЖ"),
    FEMALE("ЖЕНА");

    private final String displayName;

    GenderType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
