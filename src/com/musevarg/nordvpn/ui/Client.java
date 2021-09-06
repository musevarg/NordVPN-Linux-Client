package com.musevarg.nordvpn.ui;

import com.musevarg.nordvpn.vpn.NordVPN;

import javax.swing.*;

public class Client extends javax.swing.JPanel {

    private JButton connectButton;
    private JLabel statusLabel;
    private NordVPN nordVPN = NordVPN.getInstance();

    public Client(){
        initComponents();
    }

    private void initComponents(){

    }
}
