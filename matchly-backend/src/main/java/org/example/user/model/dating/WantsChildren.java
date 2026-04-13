package org.example.user.model.dating;

public enum WantsChildren {

    YES("Искам деца"),
    NO("Не искам деца"),
    NOT_SURE("Не съм сигурен/на");

    private final String displayName;

    WantsChildren(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
