package com.musevarg.nordvpn.commands;

/*
* This class is a singleton. It is used to run NordVPN commands in the background.
* We do not need to instantiate it more than once.
*/

public class NordVpnCommands {

    // Static variable reference of single_instance
    // of type NordVpnCommands
    private static NordVpnCommands single_instance = null;

    // Constructor of the singleton
    private NordVpnCommands() {}

    // Static method to create instance of Singleton class
    public static NordVpnCommands getInstance()
    {
        if (single_instance == null)
            single_instance = new NordVpnCommands();
        return single_instance;
    }
}
