package com.musevarg.nordvpn.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NordVpnCommandsTest {

    @Test //Ensure that NordVpnCommands can only be instantiated once
    public void singletonTest() {
        NordVpnCommands commands1 = NordVpnCommands.getInstance();
        NordVpnCommands commands2 = NordVpnCommands.getInstance();
        assertEquals(commands1.hashCode(), commands2.hashCode());
    }

}