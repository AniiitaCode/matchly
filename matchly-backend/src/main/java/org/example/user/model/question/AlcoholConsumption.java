package org.example.user.model.question;

public enum AlcoholConsumption {

    YES("Да"),
    NO("Не"),
    RARELY("Рядко"),
    ONLY_IN_COMPANY("Само в компания");

    private final String displayName;

    AlcoholConsumption(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
