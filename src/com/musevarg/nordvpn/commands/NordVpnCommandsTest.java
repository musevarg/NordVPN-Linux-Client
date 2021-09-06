package com.musevarg.nordvpn.commands;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NordVpnCommandsTest {

    @Test //Ensure that NordVpnCommands can only be instantiated once
    public void singletonTest() {
        NordVpnCommands commands1 = NordVpnCommands.getInstance();
        NordVpnCommands commands2 = NordVpnCommands.getInstance();
        assertEquals(commands1.hashCode(), commands2.hashCode());
    }

    @Test //Run quick connect command
    public void runCommandTest(){
        NordVpnCommands commands = NordVpnCommands.getInstance();
        String response = commands.connect();
        assertTrue(isConnected(response));
    }

    // Check if response contains "you are connected"
    private Boolean isConnected(String response){
        return response.toLowerCase().contains("you are connected");
    }

}