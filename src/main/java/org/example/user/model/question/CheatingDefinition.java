package org.example.user.model.question;

public enum CheatingDefinition {

    INTIMATE_CONTACT("Само интимен контакт"),
    KISS("Целувка"),
    EMOTIONAL_RELATIONSHIP("Емоционална връзка"),
    FLIRT_OR_CHATS("Флирт / чатове");

    private final String displayName;

    CheatingDefinition(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
