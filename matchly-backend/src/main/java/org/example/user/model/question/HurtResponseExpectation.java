package org.example.user.model.question;

public enum HurtResponseExpectation {

    APOLOGY("Извинение"),
    CHANGE_OF_BEHAVIOR("Промяна на поведението"),
    TIME("Време"),
    CONVERSATION("Разговор");

    private final String displayName;

    HurtResponseExpectation(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
