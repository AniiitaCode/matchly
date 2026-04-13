package org.example.user.model.question;

public enum RelationshipSecretsPolicy {

    NO_SECRETS("Никакви тайни"),
    SMALL_PERSONAL_SECRETS("Малки лични тайни"),
    RIGHT_TO_PRIVACY("Всеки има право на личен живот");

    private final String displayName;

    RelationshipSecretsPolicy(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
