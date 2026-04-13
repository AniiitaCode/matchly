package org.example.user.model.question;

public enum ZodiacSign {

    ARIES("Овен"),
    TAURUS("Телец"),
    GEMINI("Близнаци"),
    CANCER("Рак"),
    LEO("Лъв"),
    VIRGO("Дева"),
    LIBRA("Везни"),
    SCORPIO("Скорпион"),
    SAGITTARIUS("Стрелец"),
    CAPRICORN("Козирог"),
    AQUARIUS("Водолей"),
    PISCES("Риби");

    private final String displayName;

    ZodiacSign(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
