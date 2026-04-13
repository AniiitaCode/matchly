package org.example.user.model.dating;

public enum RelationshipStatus {

    SINGLE("Необвързан/а"),
    IN_RELATIONSHIP("Във връзка"),
    OPEN_RELATIONSHIP("В отворена връзка"),
    MARRIED("Женен/а"),
    DIVORCED("Разведен/а"),
    NOT_SPECIFIED("Не споделям");

    private String displayName;

    RelationshipStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
