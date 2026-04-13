package org.example.user.model.question;

public enum RelationshipRoles {

    EQUAL_ROLES("Равни роли"),
    TRADITIONAL_ROLES("Традиционни роли"),
    SITUATION_DEPENDENT("Според ситуацията");

    private final String displayName;

    RelationshipRoles(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
