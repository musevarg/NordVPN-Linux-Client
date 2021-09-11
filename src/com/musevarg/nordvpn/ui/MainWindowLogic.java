package com.musevarg.nordvpn.ui;

import com.musevarg.nordvpn.util.CountryLocales;
import com.musevarg.nordvpn.vpn.NordVPN;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class MainWindowLogic {

    public NordVPN nordVPN;
    public String[] countries;
    public ResourceBundle rb;
    private final MainWindow mainWindow;

    public MainWindowLogic(MainWindow mainWindow, ResourceBundle rb){
        this.mainWindow = mainWindow;
        this.rb = rb;
        this.nordVPN = NordVPN.getInstance();
        this.countries = nordVPN.countries();
    }

    /*
     *  THE METHODS BELOW ARE REUSABLE
     */

    // Set connectBtn txt
    public void setConnectBtnText(JButton connectBtn) { connectBtn.setText(rb.getString("connectTo")); }

    // Set connectBtn txt and add place it will connect to
    public void setConnectBtnText(JButton connectBtn, String place) { connectBtn.setText(rb.getString("connectTo") + " " + place); }

    // Set backBtn txt
    public void backButtonText(JButton backButton){
        backButton.setText(rb.getString("back"));
    }

    /*
     * THE METHODS BELOW ARE USED TO CREATE ELEMENTS IN THE COUNTRY CARD
     */

    // Populate country list in a separate thread and add list selection listener
    public void createCountryAndCityList(JList<String> countryList, JList<String> cityList){
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String c : countries) {
            String country = c.replace("_", " ");
            listModel.addElement(country);
        }
        countryList.setModel(listModel);
        countryList.setCellRenderer(new CountryCellRenderer());
    }

    // Populate city list in a separate thread
    public void createCityList(JList<String> cityList, int countryIndex, JButton countryConnectBtn){
        new Thread(() ->{
            String[] cities = nordVPN.cities(countries[countryIndex]);
            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (String c : cities) {
                String city = c.replace("_", " ");
                listModel.addElement(city);
            }
            cityList.setModel(listModel);
            cityList.setSelectedIndex(0);
            setConnectBtnText(countryConnectBtn, cityList.getSelectedValue());
        }).start();
    }

    // get flag and country name to display on the left side
    public void getFlagAndCountryName(JLabel countryFlagLabel, JLabel countryNameLabel, String country){
        String countryCode = CountryLocales.getCountryCode(country);
        ImageIcon flag = CountryCellRenderer.loadFlag(countryCode.toLowerCase(), 45);
        countryFlagLabel.setIcon(flag);
        countryNameLabel.setText(country);
    }


    /*
     * THE METHODS BELOW ARE USED TO CREATE ELEMENTS IN THE SETTINGS CARD
     */

    // Generate settings list from strings in the resource bundle
    public void generateSettingsList(ResourceBundle rb, JList<String> settingsList){
        String[] settings = new String[]{rb.getString("about"), rb.getString("log")};
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String c : settings) {
            listModel.addElement(c);
        }
        settingsList.setModel(listModel);
    }

    // Update the log before displaying it to the user
    public void updateSettingsLog(JTextArea settingsLogTextArea){
        settingsLogTextArea.setText("");
        for(String s : nordVPN.getLog()){
            settingsLogTextArea.append(s + "\n");
        }
    }


}
