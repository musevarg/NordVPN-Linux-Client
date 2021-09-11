package com.musevarg.nordvpn.ui;

import com.musevarg.nordvpn.util.LanguageLocales;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainWindow extends JFrame {

    // Locale and Resource Bundle
    private Locale currentLocale;
    private ResourceBundle rb;

    // Main Window Logic
    MainWindowLogic mwl = new MainWindowLogic();

    // UI elements
    private JPanel mainCardLayoutPanel;
    private CardLayout mainCardLayout = (CardLayout)(mainCardLayoutPanel.getLayout());
    private JPanel defaultCard;
    private JButton quickConnBtn;
    private JButton serverCountriesBtn;
    private JButton serverGroupsBtn;
    private JLabel logoLabel;
    private JLabel statusLabel;
    private JPanel countryCard;
    private JList<String> countryList;
    private JList<String> cityList;
    private JButton countryConnectBtn;
    private JButton countryBackBtn;
    private JLabel countryFlagLabel;
    private JLabel countryNameLabel;
    private JLabel pickCityLabel;

    public MainWindow(){
        initComponents();
        this.setSize(600, 400);
        this.setContentPane(mainCardLayoutPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);
        this.setVisible(true);
        this.setResizable(false);
    }

    // Init the components that are part of the form
    private void initComponents(){
        // Init locale
        initLocale(LanguageLocales.enLocale);

        // Button Action Listeners
        initDefaultPanelButtonActions();
        initCountryPanelButtonActions();

        // Remaining elements to be generated
        generateCountryAndCityList();

        // Show main panel
        initCards();
    }

    // Init the locale
    private void initLocale(Locale locale){
        currentLocale = locale;
        rb = ResourceBundle.getBundle("language", currentLocale);

        this.setTitle(rb.getString("mainTitle"));
        initDefaultPanelText();
        initCountryPanelText();
    }

    /*
     * SET THE TEXT FROM THE RESOURCE BUNDLE IN THE METHODS BELOW
     */

    // Init the text of the default panel
    private void initDefaultPanelText(){
        quickConnBtn.setText(rb.getString("quickConnect"));
        serverCountriesBtn.setText(rb.getString("chooseServerCountry"));
        serverGroupsBtn.setText(rb.getString("chooseServerGroup"));
    }

    // Init the text in the country panel
    private void initCountryPanelText(){
        pickCityLabel.setText(rb.getString("city"));
        countryConnectBtn.setText(rb.getString("connectCity"));
        countryBackBtn.setText(rb.getString("back"));
    }

    /*
     * SET THE ACTIONS OF THE BUTTONS IN THE METHODS BELOW
     */

    // Init button actions of the default panel
    private void initDefaultPanelButtonActions(){
        serverCountriesBtn.addActionListener(e -> showCountryCard());
    }

    // Init button actions of the country panel
    private void initCountryPanelButtonActions(){
        countryBackBtn.addActionListener(e -> showDefaultCard());
    }

    /*
     * THE METHODS BELOW ARE USED TO GENERATE EVERYTHING ELSE
     * THAT COULDN'T BE DONE BY THE OTHER ONES
     */

    // Generate country list with flags and labels
    private void generateCountryAndCityList(){
        mwl.createCountryAndCityList(countryList, cityList);

        // Action listener on country list element click
        countryList.addListSelectionListener(e -> {
            if(e.getValueIsAdjusting()){
                mwl.createCityList(cityList, countryList.getSelectedIndex(), countryConnectBtn);
                mwl.getFlagAndCountryName(countryFlagLabel, countryNameLabel, countryList.getSelectedValue());
            }
        });

        // Action listener on city list element click
        cityList.addListSelectionListener(e -> {
            if(e.getValueIsAdjusting()){
                mwl.setCityConnectBtnText(countryConnectBtn, cityList.getSelectedValue());
            }
        });
    }

    /*
     * THE METHODS BELOW ARE USED TO RECREATE THE CARD LAYOUT
     * AND SHOW A SPECIFIC PANEL (CARD)
     */

    private void initCards(){
        mainCardLayoutPanel.removeAll();
        mainCardLayoutPanel.add("defaultCard", defaultCard);
        mainCardLayoutPanel.add("countryCard", countryCard);
        mainCardLayout.show(mainCardLayoutPanel, "defaultCard");
    }

    // Display the default panel
    private void showDefaultCard(){
        mainCardLayout.show(mainCardLayoutPanel, "defaultCard");
    }

    // Display the country panel
    private void showCountryCard(){
        mainCardLayout.show(mainCardLayoutPanel, "countryCard");
    }

}