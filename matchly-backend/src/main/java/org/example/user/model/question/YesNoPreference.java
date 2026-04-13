package org.example.user.model.question;

public enum YesNoPreference {

    YES("ДА"),
    NO("НЕ");

    private final String displayName;

    YesNoPreference(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
