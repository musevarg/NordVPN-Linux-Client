package com.musevarg.nordvpn.ui;

import com.musevarg.nordvpn.util.LanguageLocales;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class Client2 extends JFrame {

    private Locale currentLocale;
    ResourceBundle rb;

    private JPanel mainCardLayoutPanel;
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

    public Client2(){
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
        initLocale(LanguageLocales.enLocale);

        initDefaultPanelText();
        initDefaultPanelButtonActions();

        initCountryPanelText();
        initCountryPanelButtonActions();

        showDefaultPanel();
    }

    // Init the locale
    private void initLocale(Locale locale){
        currentLocale = locale;
        rb = ResourceBundle.getBundle("language", currentLocale);
        this.setTitle(rb.getString("mainTitle"));
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
        serverCountriesBtn.addActionListener(e -> showCountryPanel());
    }

    // Init button actions of the country panel
    private void initCountryPanelButtonActions(){
        countryBackBtn.addActionListener(e -> showDefaultPanel());
    }

    /*
     * THE METHODS BELOW ARE USED TO SHOW A SPECIFIC PANEL (CARD)
     */

    // Display the default panel
    private void showDefaultPanel(){
        mainCardLayoutPanel.removeAll();
        mainCardLayoutPanel.add(defaultCard);
        mainCardLayoutPanel.repaint();
        mainCardLayoutPanel.revalidate();
    }

    // Display the country panel
    private void showCountryPanel(){
        mainCardLayoutPanel.removeAll();
        mainCardLayoutPanel.add(countryCard);
        mainCardLayoutPanel.repaint();
        mainCardLayoutPanel.revalidate();
    }

}
