package com.musevarg.nordvpn.util;

import java.util.Locale;

// This class loads a list of country codes and country names.
// It only uses the Locale Java library.
// It is used to fetch the correct flag to display in the UI list of countries.

public class CountryLocales {

    public static String getCountryCode(String country){

        country = cleanUpNordLabel(country);

        String[] locales = Locale.getISOCountries();

        for (String countryCode : locales) {

            Locale obj = new Locale("", countryCode);
            String localeName = cleanUpJavaLocales(obj.getDisplayCountry());

            if (localeName.equalsIgnoreCase(country)){
                return countryCode;
            }

        }

        return "ERR";
    }

    // Print all countries from the Java Locales
    public static void printCountries(){
        String[] locales = Locale.getISOCountries();
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            System.out.println("Country: " + cleanUpJavaLocales(obj.getDisplayCountry()) + " - Code: " + countryCode);
        }
    }

    // Correct some of the discrepencies between the Java Locale and the NordVPN country list
    private static String cleanUpNordLabel(String country){
        country = country.replace("And", "&");

        if (country.toLowerCase().contains("czech"))
                country = "Czechia";

        if (country.toLowerCase().contains("hong kong"))
            country = "Hong Kong SAR China";

        if (country.toLowerCase().contains("macedonia"))
            country = "Macedonia";

        if (country.toLowerCase().contains("vietnam"))
            country = "Vietnam";

        return country;
    }

    // Remove extra info from country names returned by the Java Locale
    private static String cleanUpJavaLocales(String country){
        if (country.contains("-")){
            String[] c = country.split("-");
            country = c[0].trim();
        }
        return country;
    }

}
