package com.musevarg.nordvpn.vpn;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NordVPNTest {

    NordVPN nordVPN = NordVPN.getInstance();

    @Test //Ensure that the NordVPN class can only be instantiated once
    public void singletonTest() {
        NordVPN nordVPN2 = NordVPN.getInstance();
        assertEquals(nordVPN.hashCode(), nordVPN2.hashCode());
    }

    @Test // Run quick connect command
    public void connect(){
        String response = nordVPN.connect();
        assertTrue(isCorrectResponse(response, "you are connected"));
        assertTrue(nordVPN.isConnected);
        response = nordVPN.status();
        assertTrue(isCorrectResponse(response, "status: connected"));
    }

    @Test // Disconnect from VPN
    public void disconnect(){
        String response = nordVPN.disconnect();
        assertTrue(isCorrectResponse(response, "you are disconnected"));
        assertFalse(nordVPN.isConnected);
        response = nordVPN.status();
        assertTrue(isCorrectResponse(response, "status: disconnected"));
    }

    // Check if response contains the expected String
    private Boolean isCorrectResponse(String response, String expected){
        return response.toLowerCase().contains(expected);
    }

}