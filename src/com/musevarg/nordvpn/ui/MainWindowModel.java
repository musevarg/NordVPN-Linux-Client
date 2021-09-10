package com.musevarg.nordvpn.ui;

import com.musevarg.nordvpn.vpn.NordVPNCommands;

public class MainWindowModel {

    MainWindow mainWindow;
    NordVPNCommands nordVPN = NordVPNCommands.getInstance();

    public MainWindowModel(MainWindow mw){
        this.mainWindow = mw;
    }

}
