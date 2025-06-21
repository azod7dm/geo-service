package ru.netology.i18n;

import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;

import static org.junit.jupiter.api.Assertions.*;

class LocalizationServiceImplTest {

    private final LocalizationServiceImpl localizationService = new LocalizationServiceImpl();

    @Test
    void locale_WhenCountryIsRussia_ReturnsWelcomeMessageInRussian() {
        String result = localizationService.locale(Country.RUSSIA);
        assertEquals("Добро пожаловать", result);
    }

    @Test
    void locale_WhenCountryIsUSA_ReturnsWelcomeMessageInEnglish() {
        String result = localizationService.locale(Country.USA);
        assertEquals("Welcome", result);
    }

    @Test
    void locale_WhenCountryIsCanada_ReturnsWelcomeMessageInFrench() {
        String result = localizationService.locale(Country.CANADA);
        assertEquals("Bienvenue", result);
    }

    @Test
    void locale_WhenCountryIsGermany_ReturnsWelcomeMessageInGerman() {
        String result = localizationService.locale(Country.GERMANY);
        assertEquals("Willkommen", result);
    }

    @Test
    void locale_WhenCountryIsFrance_ReturnsWelcomeMessageInFrench() {
        String result = localizationService.locale(Country.FRANCE);
        assertEquals("Bienvenue", result);
    }
}
