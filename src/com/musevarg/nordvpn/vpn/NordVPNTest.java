package com.musevarg.nordvpn.vpn;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NordVPNTest {

    NordVPNCommands nordVpnCommands = NordVPNCommands.getInstance();

    @Test //Ensure that the NordVPN class can only be instantiated once
    public void singletonTest() {
        NordVPNCommands nordVPN2 = NordVPNCommands.getInstance();
        assertEquals(nordVpnCommands.hashCode(), nordVPN2.hashCode());
    }

    /*@Test // Run quick connect command
    public void connect(){
        String response = nordVpnCommands.connect();
        assertTrue(isCorrectResponse(response, "you are connected"));
        assertTrue(nordVpnCommands.isConnected);
        response = nordVpnCommands.status();
        assertTrue(isCorrectResponse(response, "connected"));
    }

    @Test // Disconnect from VPN
    public void disconnect(){
        String response = nordVpnCommands.disconnect();
        assertTrue(isCorrectResponse(response, "you are disconnected"));
        assertFalse(nordVpnCommands.isConnected);
        response = nordVpnCommands.status();
        assertTrue(isCorrectResponse(response, "disconnected"));
    }

    @Test // connect to france
    public void connectToFrance(){
        String response = nordVpnCommands.connect("France");
        assertTrue(isCorrectResponse(response, "you are connected"));
    }*/

    @Test // Fetch country list
    public void getCountries(){
        String[] response = nordVpnCommands.getCountries();
        assertTrue(response.length > 0);
    }

    @Test // Fetch city list (at time of test france has only 2 cities)
    public void getCitiesFrance(){
        String[] response = nordVpnCommands.getCities("France");
        assertEquals(2, response.length);
    }

    @Test // Fetch city list (at time of test Belgium has only 1 city)
    public void getCitiesBelgium(){
        String[] response = nordVpnCommands.getCities("Belgium");
        assertEquals(1, response.length);
    }

    // Check if response contains the expected String
    private Boolean isCorrectResponse(String response, String expected){
        return response.toLowerCase().contains(expected);
    }

}