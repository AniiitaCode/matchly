package org.example.user.model.question;

public enum RelationshipPriority {

    LOVE("Любов"),
    TRUST("Доверие"),
    RESPECT("Уважение"),
    STABILITY("Стабилност"),
    FREEDOM("Свобода");

    private final String displayName;

    RelationshipPriority(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
