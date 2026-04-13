package org.example.user.model.question;

public enum ApologyMethod {

    SAY_SORRY("Съжалявам"),
    ADMIT_FAULT("Признаване на вина"),
    REAL_CHANGE("Реална промяна"),
    ACTION("Действие");

    private final String displayName;

    ApologyMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
