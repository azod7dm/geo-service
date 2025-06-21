package ru.netology.sender;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageSenderImplTest {

    @Test
    void send_WithValidIp_ReturnsWelcomeMessage() {
        // Arrange
        GeoService geoServiceMock = mock(GeoService.class);
        LocalizationService localizationServiceMock = mock(LocalizationService.class);

        MessageSenderImpl messageSender = new MessageSenderImpl(geoServiceMock, localizationServiceMock);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.0.32.11");

        Location location = new Location("Moscow", Country.RUSSIA, "Lenina", 15);
        when(geoServiceMock.byIp("172.0.32.11")).thenReturn(location);
        when(localizationServiceMock.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");

        // Act
        String result = messageSender.send(headers);

        // Assert
        assertEquals("Добро пожаловать", result);

        // Проверяем, что метод был вызван именно с Country.RUSSIA
        ArgumentCaptor<Country> countryCaptor = ArgumentCaptor.forClass(Country.class);
        verify(localizationServiceMock, atLeast(1)).locale(countryCaptor.capture());

        // Проверяем, что был вызван именно Country.RUSSIA
        assertTrue(countryCaptor.getAllValues().contains(Country.RUSSIA));
    }

    @Test
    void send_WithInvalidIp_ReturnsDefaultMessage() {
        // Arrange
        GeoService geoServiceMock = mock(GeoService.class);
        LocalizationService localizationServiceMock = mock(LocalizationService.class);

        MessageSenderImpl messageSender = new MessageSenderImpl(geoServiceMock, localizationServiceMock);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "unknown-ip");

        // Здесь устанавливаем мок так, чтобы он возвращал null для неизвестного IP
        when(geoServiceMock.byIp("unknown-ip")).thenReturn(null);
        when(localizationServiceMock.locale(Country.USA)).thenReturn("Welcome");

        // Act
        String result = messageSender.send(headers);

        // Assert
        assertEquals("Welcome", result); // Проверка на возвращаемое значение
        verify(localizationServiceMock, times(1)).locale(Country.USA); // Ожидаем один вызов
    }

    @Test
    void send_WithoutIpAddress_ReturnsDefaultMessage() {
        // Arrange
        GeoService geoServiceMock = mock(GeoService.class);
        LocalizationService localizationServiceMock = mock(LocalizationService.class);

        MessageSenderImpl messageSender = new MessageSenderImpl(geoServiceMock, localizationServiceMock);

        Map<String, String> headers = new HashMap<>(); // Нет IP-адреса

        when(localizationServiceMock.locale(Country.USA)).thenReturn("Welcome");

        // Act
        String result = messageSender.send(headers);

        // Assert
        assertEquals("Welcome", result);
        verify(localizationServiceMock, times(1)).locale(Country.USA); // Ожидаем один вызов
    }
}
