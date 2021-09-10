package com.musevarg.nordvpn.ui;

import com.musevarg.nordvpn.util.CountryLocales;
import com.musevarg.nordvpn.vpn.NordVPN;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.util.Objects;

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
    private JPanel leftCardLayout;
    private CardLayout lcl = (CardLayout) (leftCardLayout.getLayout());
    private JPanel defaultLeftPanel;
    private JPanel countryDetailLeftPanel;
    private JList<String> cityList;
    private JButton connectCityButton;
    private JButton backButton;
    private JLabel flagLabelLeft;
    private JLabel countryLabelLeft;
    private JLabel pickCityLabel;
    private JLabel logoLabel;
    private JLabel loadingLabel;

    public Client(){
        initComponents();
        this.setTitle("Pôle - A NordVPN UI for Linux");
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
        countryList.addListSelectionListener(e -> {
            if(e.getValueIsAdjusting()){
                showCountryDetails();
            }
        });
        cityList.addListSelectionListener(e -> {
            if(e.getValueIsAdjusting()){
                updateCity();
            }
        });
        connectCityButton.addActionListener(e -> connect(cityList.getSelectedValue()));
        backButton.addActionListener(e -> showDefaultButtons());

        // Extra init to make the whole thing pretty
        pickCityLabel.setText("<html><body><p style=\"margin-top:3px;\">Pick City:</p></body></html>");
        logoLabel.setIcon(getLogo());
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

    // Connect (quick connect, country connect or city connect)
    private void connect(String country){
        connectingUI();
        if (country == null){
            new Thread(() -> {
                runAndLog(nordVPN.connect());
                updateUiComponents();
            }).start();
        } else {
            new Thread(() -> {
                runAndLog(nordVPN.connect(country));
                updateUiComponents();
            }).start();
        }
    }

    // Prevent user from triggering multiple connect commands
    // and change UI accordingly
    private void connectingUI(){
        loadingLabel.setIcon(getLoaderGif());
        connectButton.setEnabled(false);
        toggleButton.setEnabled(false);
        connectButton.setText("Connecting...");
        statusText.setText("Status: Connecting...");
        if (isCountryListShowing){
            toggleRightPanel();
            showDefaultButtons();
        }
    }

    // Prevent panels from toggling
    private boolean isCountryPanelShowing = false;

    // Toggle country detail (left cardlayout)
    private void showCountryDetails(){
        if (!isCountryPanelShowing){
            lcl.next(leftCardLayout);
            isCountryPanelShowing = true;
        }
        fetchCountryDetails();
    }
    
    // Get country details
    private void fetchCountryDetails(){
        getCityList(countries[countryList.getSelectedIndex()]);
        String countryCode = CountryLocales.getCountryCode(countryList.getSelectedValue());
        ImageIcon flag = CountryCellRenderer.loadFlag(countryCode.toLowerCase(), 45);
        flagLabelLeft.setIcon(flag);
        countryLabelLeft.setText(countryList.getSelectedValue());
        connectCityButton.setText("Connect to " + cityList.getSelectedValue());
    }

    // Fetch city list in a separate thread
    private void getCityList(String country){
        new Thread(() -> {
            String[] cities = nordVPN.cities(country);
            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (String c : cities) {
                String city = c.replace("_", " ");
                listModel.addElement(city);
            }
            cityList.setModel(listModel);
            cityList.setSelectedIndex(0);
        }).start();
    }

    // Change button text when selecting different city
    private void updateCity(){
        connectCityButton.setText("Connect to " + cityList.getSelectedValue());
    }

    // Toggle default buttons (left cardlayout)
    private void showDefaultButtons(){
        if(isCountryPanelShowing){
            lcl.next(leftCardLayout);
            isCountryPanelShowing = false;
            if(isCountryListShowing) {
                toggleRightPanel();
            }
        }
    }

    // Disconnect
    private void disconnect(){
        if (nordVPN.isConnected)
            new Thread(() -> {
                runAndLog(nordVPN.disconnect());
                updateUiComponents();
            }).start();
    }


    private boolean isCountryListShowing = false;

    // Handle the toggling of the right panel and the button label
    private void toggleRightPanel(){
        if (isCountryListShowing){
            cl.next(rightPanel);
            isCountryListShowing = false;
        } else {
            cl.next(rightPanel);
            isCountryListShowing = true;
            if(countryList.getSelectedIndex() != -1){
                showCountryDetails();
            } else {
                countryList.setSelectedIndex(0);
                showCountryDetails();
            }
        }
    }

    // Update UI after connecting or disconnecting
    private void updateUiComponents(){
        loadingLabel.setIcon(null);
        String status = nordVPN.status();
        nordVPN.isConnected = status.contains("Connected");
        statusText.setText(status);

        if(nordVPN.isConnected){
            connectButton.setText("Disconnect");
        } else {
            connectButton.setText("Quick Connect");
        }
        connectButton.setEnabled(true);
        toggleButton.setEnabled(true);
    }

    // Clean response from CLI
    private void runAndLog(String response){
        String[] responses = response.split("\n");
        for (String r : responses){
            System.out.println(r);
        }
    }

    // Load main logo
    private ImageIcon getLogo(){
        ImageIcon logo = new ImageIcon(Objects.requireNonNull(CountryCellRenderer.class.getClassLoader().getResource("res/img/pole-logo.png")));
        Image tempImage = logo.getImage().getScaledInstance(150, 60,  java.awt.Image.SCALE_SMOOTH); // Resize image
        logo = new ImageIcon(tempImage);
        return logo;
    }

    // Load loader GIF
    private ImageIcon getLoaderGif(){
        return new ImageIcon(Objects.requireNonNull(CountryCellRenderer.class.getClassLoader().getResource("res/img/loader.gif")));
    }
}