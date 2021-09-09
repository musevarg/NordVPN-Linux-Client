package com.musevarg.nordvpn.ui;

import com.musevarg.nordvpn.vpn.NordVPN;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client extends JFrame {

    private JButton connectButton;
    //private JLabel statusLabel;
    private JPanel mainPanel;
    private JPanel rightPanel;
    private JPanel leftPanel;
    private JButton countryButton;
    private NordVPN nordVPN = NordVPN.getInstance();

    public Client(){
        initComponents();
        this.setSize(500, 300);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);
        this.setVisible(true);
    }

    private void initComponents(){
        //updateUiComponents();

        connectButton.addActionListener(e -> {
            if (nordVPN.isConnected){
                nordVPN.disconnect();
            } else {
                nordVPN.connect();
            }
            //updateUiComponents();
        });
    }

    /*private void updateUiComponents(){
        nordVPN.isConnected = nordVPN.status().equals("Connected");
        String status = nordVPN.isConnected ? "Connected" : "Disconnected";
        statusLabel.setText("Status: " + status);

        if(nordVPN.isConnected){
            connectButton.setText("Disconnect");
        } else {
            connectButton.setText("Connect");
        }
    }*/


}
