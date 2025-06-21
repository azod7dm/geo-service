package ru.netology.i18n;

import ru.netology.entity.Country;

public class LocalizationServiceImpl implements LocalizationService {

    public String locale(Country country) {
        switch (country) {
            case RUSSIA:
                return "Добро пожаловать";
            case USA:
                return "Welcome";  // Обработка для США
            case CANADA:
                return "Bienvenue"; // Обработка для Канады
            case GERMANY:
                return "Willkommen"; // Обработка для Германии
            case FRANCE:
                return "Bienvenue"; // Обработка для Франции
            default:
                return "Welcome"; // Обработка для других стран
        }
    }
}
