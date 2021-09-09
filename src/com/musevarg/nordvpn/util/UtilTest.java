package com.musevarg.nordvpn.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test // Get Belgium country code
    public void getBelgiumCountryCode(){
        String belgiumCode = CountryLocales.getCountryCode("Belgium");
        assertEquals("BE", belgiumCode);
    }

    @Test // Get US country code
    public void getUSCountryCode(){
        String belgiumCode = CountryLocales.getCountryCode("United States");
        assertEquals("US", belgiumCode);
    }

    @Test // Get South Africa country code
    public void getZACountryCode(){
        String belgiumCode = CountryLocales.getCountryCode("South Africa");
        assertEquals("ZA", belgiumCode);
    }

}