package com.musevarg.nordvpn.vpn;

/* This class is a singleton */

import java.util.ArrayList;

public class NordVPN {

    /*
     * SINGLETON SETUP
     */

    // Static variable reference of single_instance
    private static NordVPN single_instance = null;

    // Constructor of the singleton
    private NordVPN() {}

    // Static method to create instance of Singleton class
    public static NordVPN getInstance()
    {
        if (single_instance == null) {
            single_instance = new NordVPN();
        }
        return single_instance;
    }

    /*
     * GET INSTANCE OF THE COMMANDS SINGLETON
     */

    private NordVPNCommands vpnCommands = NordVPNCommands.getInstance();

    /*
     * RUN VPN COMMANDS IN BACKGROUND THREADS
     */

    // Quick Connect
    public void quickConnect(){
        new Thread(() -> vpnCommands.connect()).start();
    }

    // Connect to specific country, city or group
    public void connect(String server){
        new Thread(() -> vpnCommands.connect(server)).start();
    }

    // Disconnect
    public void disconnect(){
        new Thread(() -> vpnCommands.disconnect());
    }

    // Fetch connection status
    public void getStatus(){
        new Thread(() -> vpnCommands.status()).start();
    }

    // Fetch country list
    public String[] countries(){
        return vpnCommands.getCountries();
    }

    // Fetch city list
    public String[] cities(String country){
        return vpnCommands.getCities(country);
    }

    /*
     * GET COMMANDS AND RESPONSE LOG
     */

    public ArrayList<String> getLog(){
        return vpnCommands.getLog();
    }


}
