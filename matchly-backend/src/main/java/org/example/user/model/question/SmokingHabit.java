package org.example.user.model.question;

public enum SmokingHabit {

    YES("Да"),
    NO("Не"),
    RARELY("Рядко"),
    ONLY_IN_COMPANY("Само в компания");

    private final String displayName;

    SmokingHabit(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
