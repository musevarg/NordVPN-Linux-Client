package com.musevarg.nordvpn.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test // Get Belgium country code
    public void getBelgiumCountryCode(){
        String code = CountryLocales.getCountryCode("Belgium");
        assertEquals("BE", code);
    }

    @Test // Get US country code
    public void getUSCountryCode(){
        String code = CountryLocales.getCountryCode("United States");
        assertEquals("US", code);
    }

    @Test // Get South Africa country code
    public void getZACountryCode(){
        String code = CountryLocales.getCountryCode("South Africa");
        assertEquals("ZA", code);
    }

    /*@Test
    public void countryList(){
        CountryLocales.printCountries();
    }*/

}