package com.musevarg.nordvpn.ui;

import com.musevarg.nordvpn.vpn.NordVPN;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client extends JFrame {

    private JButton connectButton;
    private JLabel statusLabel;
    private JPanel mainPanel;
    private NordVPN nordVPN = NordVPN.getInstance();

    public Client(){
        initComponents();
        this.setSize(175, 250);
        this.setContentPane(mainPanel);
        this.setVisible(true);
    }

    private void initComponents(){
        updateUiComponents();

        connectButton.addActionListener(e -> {
            if (nordVPN.isConnected){
                nordVPN.disconnect();
            } else {
                nordVPN.connect();
            }
            updateUiComponents();
        });
    }

    private void updateUiComponents(){
        statusLabel.setText("Status: " + nordVPN.status());
        nordVPN.isConnected = nordVPN.status().equals("Connected");
        if(nordVPN.isConnected){
            connectButton.setText("Disconnect");
        } else {
            connectButton.setText("Connect");
        }
    }


}
