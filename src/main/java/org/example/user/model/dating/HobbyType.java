package org.example.user.model.dating;

public enum HobbyType {

    SPORT("Спорт"),
    TRAVEL("Пътуване"),
    MUSIC("Музика"),
    CINEMA("Кино"),
    READING("Четене"),
    COOKING("Готвене"),
    DANCING("Танци"),
    PHOTOGRAPHY("Фотография"),
    GAMING("Игри"),
    FITNESS("Фитнес"),
    HIKING("Планинарство"),
    TECHNOLOGY("Технологии"),
    ART("Изкуство"),
    THEATER("Театър"),
    VOLUNTEERING("Доброволчество"),
    YOGA("Йога"),
    SWIMMING("Плуване"),
    RUNNING("Бягане"),
    CYCLING("Колоездене"),
    BOARD_GAMES("Настолни игри"),
    GARDENING("Градинарство"),
    LANGUAGES("Езици"),
    FASHION("Мода"),
    TRIVIA("Общи знания"),
    MEDITATION("Медитация");

    private final String displayName;

    HobbyType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
