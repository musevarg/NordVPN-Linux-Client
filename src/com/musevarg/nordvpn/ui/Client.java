package com.musevarg.nordvpn.ui;

import com.musevarg.nordvpn.vpn.NordVPN;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client extends JFrame {

    private NordVPN nordVPN = NordVPN.getInstance();
    private String[] countries = nordVPN.countries();
    private JButton connectButton;
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private CardLayout cl = (CardLayout)(rightPanel.getLayout());
    private JPanel statusPanel;
    private JPanel countryPanel;
    private JButton countryButton;
    private JTextArea statusText;
    private JList<String> countryList;
    private JList<String> commandsList;

    public Client(){
        initComponents();
        this.setSize(600, 400);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);
        this.setVisible(true);
    }

    private void initComponents(){
        updateUiComponents();
        initCountryList();

        connectButton.addActionListener(e -> {
            if (nordVPN.isConnected){
                nordVPN.disconnect();
            } else {
                nordVPN.connect();
            }
            updateUiComponents();
        });

        countryButton.addActionListener(e -> cl.next(rightPanel));
    }

    private void initCountryList(){
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String c : countries) {
            String country = c.replace("_", " ");
            listModel.addElement(country);
        }
        countryList.setModel(listModel);
        countryList.setCellRenderer(new CountryListElement());
    }

    private void updateUiComponents(){
        nordVPN.isConnected = nordVPN.status().equals("Connected");
        String status = nordVPN.isConnected ? "Connected" : "Disconnected";
        statusText.setText("Status: " + status);

        if(nordVPN.isConnected){
            connectButton.setText("Disconnect");
        } else {
            connectButton.setText("Connect");
        }
    }
}