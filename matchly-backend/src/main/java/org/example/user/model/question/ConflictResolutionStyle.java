package org.example.user.model.question;

public enum ConflictResolutionStyle {

    IMMEDIATELY("Веднага"),
    AFTER_EMOTIONS_CALM("След като емоциите спаднат"),
    AVOID_CONFLICTS("Избягвам конфликти");

    private final String displayName;

    ConflictResolutionStyle(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
