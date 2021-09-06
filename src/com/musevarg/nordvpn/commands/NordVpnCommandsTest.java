package com.musevarg.nordvpn.commands;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NordVpnCommandsTest {

    private static boolean setUpIsDone = false;
    NordVpnCommands commands = NordVpnCommands.getInstance();

    @Test //Ensure that NordVpnCommands can only be instantiated once
    public void singletonTest() {
        NordVpnCommands commands2 = NordVpnCommands.getInstance();
        assertEquals(commands.hashCode(), commands2.hashCode());
    }

    @Test //Run quick connect command
    public void quickConnect(){
        String response = commands.connect();
        assertTrue(isCorrectResponse(response, "you are connected"));
    }

    @Test //Disconnect from VPN
    public void disconnect(){
        String response = commands.disconnect();
        assertTrue(isCorrectResponse(response, "nordvpn rate"));
    }

    // Check if response contains "you are connected"
    private Boolean isCorrectResponse(String response, String expected){
        return response.toLowerCase().contains(expected);
    }

}