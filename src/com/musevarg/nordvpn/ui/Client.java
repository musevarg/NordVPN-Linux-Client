package com.musevarg.nordvpn.ui;

import com.musevarg.nordvpn.vpn.NordVPN;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

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
    private JButton toggleButton;
    private JTextArea statusText;
    private JList<String> countryList;
    private JList<String> commandsList;
    private JPanel leftCardLayout;
    private CardLayout lcl = (CardLayout) (leftCardLayout.getLayout());
    private JPanel defaultLeftPanel;
    private JPanel countryDetailLeftPanel;
    private JList<String> cityList;
    private JButton connectToButton;
    private JButton backButton;
    private JLabel flagLabelLeft;
    private JLabel countryLabelLeft;
    DefaultListModel<String> commandsListModel = new DefaultListModel<>();
    private boolean isConnecting = false;

    public Client(){
        initComponents();
        this.setTitle("NordVPN Linux Client");
        this.setSize(600, 400);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);
        this.setVisible(true);
        this.setResizable(false);
    }

    private void initComponents(){
        updateUiComponents();
        initCountryList();

        // Assign action listeners
        connectButton.addActionListener(e -> toggleConnection());
        toggleButton.addActionListener(e -> toggleRightPanel());
        countryList.addListSelectionListener(e -> showCountryDetails());
        backButton.addActionListener(e -> showDefaultButtons());

        // Set style of command JList
        commandsList.setModel(commandsListModel);
        commandsList.setCellRenderer(new CommandsCellRenderer(220));
    }

    // Build custom country list
    private void initCountryList(){
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String c : countries) {
            String country = c.replace("_", " ");
            listModel.addElement(country);
        }
        countryList.setModel(listModel);
        countryList.setCellRenderer(new CountryCellRenderer());
    }

    // Quick connect / disconnect
    private void toggleConnection(){
        new Thread(() -> {
            if (nordVPN.isConnected){
                disconnect();
            } else {
                connect(null);
            }
        }).start();
    }

    private void connect(String country){
        connectButton.setText("Connecting...");
        connectButton.setEnabled(false);
        if (country == null){
            isConnecting = true;
            runAndLog(nordVPN.connect());
        } else {
            runAndLog(nordVPN.connect(country));
        }
        updateUiComponents();
    }

    // Prevent panels from toggling
    private boolean isCountryPanelShowing = false;

    // Toggle country detail (left cardlayout)
    private void showCountryDetails(){
        if (!isCountryPanelShowing){
            lcl.next(leftCardLayout);
            isCountryPanelShowing = true;
        }
    }

    // Toggle default buttons (left cardlayout)
    private void showDefaultButtons(){
        if(isCountryPanelShowing){
            lcl.next(leftCardLayout);
            isCountryPanelShowing = false;
            if(isCountryListShowing)
                toggleRightPanel();
        }
    }

    private void connectCountry(){
        if (!isConnecting){
            connectButton.setText("Connecting...");
            connectButton.setEnabled(false);
            isConnecting = true;
            new Thread(() -> connect(countries[countryList.getSelectedIndex()])).start();
        }
    }

    private void disconnect(){
        if (nordVPN.isConnected)
            runAndLog(nordVPN.disconnect());
        updateUiComponents();
    }


    private boolean isCountryListShowing = false;

    // Handle the toggling of the right panel and the button label
    private void toggleRightPanel(){
        cl.next(rightPanel);
        if (toggleButton.getText().equals("Choose Country")){
            toggleButton.setText("View Status");
            isCountryListShowing = true;
        } else {
            toggleButton.setText("Choose Country");
            isCountryListShowing = false;
        }
    }

    // Update UI after connecting or disconnecting
    private void updateUiComponents(){
        String status = nordVPN.status();
        nordVPN.isConnected = status.contains("Connected");
        statusText.setText(status);

        if(nordVPN.isConnected){
            connectButton.setText("Disconnect");
        } else {
            connectButton.setText("Connect");
        }
        connectButton.setEnabled(true);
        isConnecting = false;
    }

    // Clean response from CLI
    private void runAndLog(String response){
        String[] responses = response.split("\n");
        for (String r : responses){
            if (!r.contains("nordvpn rate [1-5]"))
                commandsListModel.addElement(r);
        }
    }
}