package org.example.user.model.question;

public enum CheatingForgiveness {

    NEVER("Никога"),
    SOMETIMES("Понякога"),
    DEPENDS_ON_SITUATION("Зависи от ситуацията");

    private final String displayName;

    CheatingForgiveness(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
