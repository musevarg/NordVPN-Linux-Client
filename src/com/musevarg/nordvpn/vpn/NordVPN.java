package com.musevarg.nordvpn.vpn;

public class NordVPN {

    NordVPNCommands vpnCommands = NordVPNCommands.getInstance();

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
    public void countries(){
        new Thread(() -> vpnCommands.getCountries()).start();
    }

    // Fetch city list
    public void cities(String country){
        new Thread(() -> vpnCommands.getCities(country)).start();
    }

}
