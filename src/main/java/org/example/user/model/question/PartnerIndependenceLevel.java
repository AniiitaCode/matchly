package org.example.user.model.question;

public enum PartnerIndependenceLevel {

    ALMOST_EVERYTHING_TOGETHER("Почти всичко заедно"),
    BALANCED("Баланс"),
    COMPLETELY_SEPARATE_LIVES("Напълно отделни животи");

    private final String displayName;

    PartnerIndependenceLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
