package org.example.user.model.dating;

public enum RelationshipType {

    SERIOUS_RELATIONSHIP("Сериозна връзка"),
    DATING("Срещи"),
    FRIENDSHIP("Приятелство"),
    CHAT("Чат"),
    CASUAL_DATING("Нещо неангажиращо");

    private final String displayName;

    RelationshipType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
