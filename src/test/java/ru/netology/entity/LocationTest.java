package ru.netology.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    void testGetCity() {
        // Создаем объект Location с заданными параметрами
        Location location = new Location("Moscow", Country.RUSSIA, "Red Square", 1);

        // Проверяем, что метод getCity возвращает правильное значение
        assertEquals("Moscow", location.getCity());
    }

    @Test
    void testGetCountry() {
        // Создаем объект Location с заданными параметрами
        Location location = new Location("Moscow", Country.RUSSIA, "Red Square", 1);

        // Проверяем, что метод getCountry возвращает правильное значение
        assertEquals(Country.RUSSIA, location.getCountry());
    }

    @Test
    void testGetStreet() {
        // Создаем объект Location с заданными параметрами
        Location location = new Location("Moscow", Country.RUSSIA, "Red Square", 1);

        // Проверяем, что метод getStreet возвращает правильное значение
        assertEquals("Red Square", location.getStreet());
    }

    @Test
    void testGetBuilding() {
        // Создаем объект Location с заданными параметрами
        Location location = new Location("Moscow", Country.RUSSIA, "Red Square", 1);

        // Проверяем, что метод getBuiling возвращает правильное значение
        assertEquals(1, location.getBuiling());
    }
}
