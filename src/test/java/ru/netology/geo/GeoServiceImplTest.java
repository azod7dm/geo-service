package ru.netology.geo;

import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import static org.junit.jupiter.api.Assertions.*;

class GeoServiceImplTest {

    private final GeoServiceImpl geoService = new GeoServiceImpl();

    @Test
    void byIp_Localhost_ReturnsNullLocation() {
        Location location = geoService.byIp(GeoServiceImpl.LOCALHOST);
        assertNotNull(location);
        assertNull(location.getCity());
        assertNull(location.getCountry());
        assertNull(location.getStreet());
        assertEquals(0, location.getBuiling());
    }

    @Test
    void byIp_Moscow_ReturnsMoscowLocation() {
        Location location = geoService.byIp(GeoServiceImpl.MOSCOW_IP);
        assertNotNull(location);
        assertEquals("Moscow", location.getCity());
        assertEquals(Country.RUSSIA, location.getCountry());
        assertEquals("Lenina", location.getStreet());
        assertEquals(15, location.getBuiling());
    }

    @Test
    void byIp_NewYork_ReturnsNewYorkLocation() {
        Location location = geoService.byIp(GeoServiceImpl.NEW_YORK_IP);
        assertNotNull(location);
        assertEquals("New York", location.getCity());
        assertEquals(Country.USA, location.getCountry());
        assertEquals(" 10th Avenue", location.getStreet());
        assertEquals(32, location.getBuiling());
    }

    @Test
    void byIp_OtherMoscowIP_ReturnsMoscowLocationWithNullStreet() {
        Location location = geoService.byIp("172.0.25.10");
        assertNotNull(location);
        assertEquals("Moscow", location.getCity());
        assertEquals(Country.RUSSIA, location.getCountry());
        assertNull(location.getStreet());
        assertEquals(0, location.getBuiling());
    }

    @Test
    void byIp_OtherNewYorkIP_ReturnsNewYorkLocationWithNullStreet() {
        Location location = geoService.byIp("96.44.100.100");
        assertNotNull(location);
        assertEquals("New York", location.getCity());
        assertEquals(Country.USA, location.getCountry());
        assertNull(location.getStreet());
        assertEquals(0, location.getBuiling());
    }

    @Test
    void byIp_InvalidIP_ReturnsNull() {
        Location location = geoService.byIp("123.45.67.89");
        assertNull(location);
    }
}
