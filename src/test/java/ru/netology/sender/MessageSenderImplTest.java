package ru.netology.sender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Location;
import ru.netology.entity.Country;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageSenderImplTest {

    private GeoService geoServiceMock;
    private LocalizationService localizationServiceMock;
    private MessageSenderImpl messageSender;
    private LocalizationServiceImpl localizationService;

    @BeforeEach
    public void setUp() {
        geoServiceMock = Mockito.mock(GeoService.class);
        localizationServiceMock = Mockito.mock(LocalizationService.class);
        messageSender = new MessageSenderImpl(geoServiceMock, localizationServiceMock);
        localizationService = new LocalizationServiceImpl(); // Инициализация реального экземпляра
    }

    static Stream<Arguments> provideIpAddressesForTesting() {
        return Stream.of(
                Arguments.of("176.100.100.1", Country.RUSSIA, "Привет"),
                Arguments.of("192.0.2.1", Country.USA, "Hello"),
                Arguments.of("1.1.1.1", Country.BRAZIL, "Welcome"),
                Arguments.of("0.0.0.0", Country.GERMANY, "Hallo")
        );
    }

    @ParameterizedTest
    @MethodSource("provideIpAddressesForTesting")
    public void testSendMessage(String ipAddress, Country country, String expectedMessage) {
        Mockito.when(geoServiceMock.byIp(ipAddress))
                .thenReturn(new Location("Unknown", country, "Unknown", 0));
        Mockito.when(localizationServiceMock.locale(country))
                .thenReturn(expectedMessage);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ipAddress);

        String result = messageSender.send(headers);

        assertEquals(expectedMessage, result);
    }

    // Новый тест для метода byIp()
    @ParameterizedTest
    @MethodSource("provideIpAddressesForTesting")
    public void testByIp(String ipAddress, Country country, String expectedMessage) {
        // Подготовка данных
        Location expectedLocation = new Location("Unknown", country, "Unknown", 0);

        // Настройка мока
        Mockito.when(geoServiceMock.byIp(ipAddress)).thenReturn(expectedLocation);

        // Вызов метода и получение результата
        Location actualLocation = geoServiceMock.byIp(ipAddress);

        // Проверка результатов
        assertEquals(expectedLocation.getCountry(), actualLocation.getCountry(), "Country should match for the given IP.");

        assertEquals(expectedLocation.getCity(), actualLocation.getCity(), "City should match for 'Unknown'");
    }

    @Test
    public void testLocaleForRussia() {
        String result = localizationService.locale(Country.RUSSIA);
        assertEquals("Добро пожаловать", result, "Localization for Russia should be 'Добро пожаловать'");
    }

    @Test
    public void testLocaleForOtherCountries() {
        String resultUSA = localizationService.locale(Country.USA);
        assertEquals("Welcome", resultUSA, "Localization for USA should be 'Welcome'");

        String resultGermany = localizationService.locale(Country.GERMANY);
        assertEquals("Welcome", resultGermany, "Localization for Germany should be 'Welcome'");

        String resultBrazil = localizationService.locale(Country.BRAZIL);
        assertEquals("Welcome", resultBrazil, "Localization for Brazil should be 'Welcome'");
    }
}
