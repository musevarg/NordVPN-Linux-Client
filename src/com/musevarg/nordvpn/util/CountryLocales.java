package com.musevarg.nordvpn.util;

import java.util.Locale;

// This class loads a list of country codes and country names.
// It only uses the Locale Java library.
// It is used to fetch the correct flag to display in the UI list of countries.

public class CountryLocales {

    public static String getCountryCode(String country){

        String[] locales = Locale.getISOCountries();

        for (String countryCode : locales) {

            Locale obj = new Locale("", countryCode);

            if (obj.getDisplayCountry().equalsIgnoreCase(country)){
                return countryCode;
            }

        }

        return "ERR";
    }

}
