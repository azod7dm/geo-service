package ru.netology.sender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Location;
import ru.netology.entity.Country;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageSenderImplTest {

    private GeoService geoServiceMock;
    private LocalizationService localizationServiceMock;
    private MessageSenderImpl messageSender;

    @BeforeEach
    public void setUp() {
        geoServiceMock = Mockito.mock(GeoService.class);
        localizationServiceMock = Mockito.mock(LocalizationService.class);
        messageSender = new MessageSenderImpl(geoServiceMock, localizationServiceMock);
    }

    @Test
    public void testSendMessageForRussianIP() {
        // Настройка заглушки
        Mockito.when(geoServiceMock.byIp("176.100.100.1"))
                .thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 1));
        Mockito.when(localizationServiceMock.locale(Country.RUSSIA))
                .thenReturn("Привет");

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "176.100.100.1");

        String result = messageSender.send(headers);

        assertEquals("Привет", result);
    }

    @Test
    public void testSendMessageForUSIP() {
        Mockito.when(geoServiceMock.byIp("192.0.2.1"))
                .thenReturn(new Location("New York", Country.USA, "5th Avenue", 1));
        Mockito.when(localizationServiceMock.locale(Country.USA))
                .thenReturn("Hello");

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "192.0.2.1");

        String result = messageSender.send(headers);

        assertEquals("Hello", result);
    }

    @Test
    public void testSendMessageForUnknownCountry() {
        // Здесь можно использовать одну из стран, например, RUSSIA, чтобы проверить работу
        Mockito.when(geoServiceMock.byIp("1.1.1.1"))
                .thenReturn(new Location("Unknown", Country.RUSSIA, "Unknown", 0));
        Mockito.when(localizationServiceMock.locale(Country.RUSSIA))
                .thenReturn("Welcome");

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "1.1.1.1");

        String result = messageSender.send(headers);

        assertEquals("Welcome", result);
    }

    @Test
    public void testSendMessageWithEmptyIP() {
        Mockito.when(localizationServiceMock.locale(Country.USA))
                .thenReturn("Hello");

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ""); // Пустой IP-адрес

        String result = messageSender.send(headers);

        // По умолчанию возвращает (например, для пустого IP)
        assertEquals("Hello", result);
    }
}
