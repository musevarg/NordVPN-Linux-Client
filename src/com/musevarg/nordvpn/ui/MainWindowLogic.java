package com.musevarg.nordvpn.ui;

import com.musevarg.nordvpn.util.CountryLocales;
import com.musevarg.nordvpn.vpn.NordVPN;

import javax.swing.*;

public class MainWindowLogic {

    public NordVPN nordVPN;
    public String[] countries;

    public MainWindowLogic(){
        this.nordVPN = NordVPN.getInstance();
        this.countries = nordVPN.countries();
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
            setCityConnectBtnText(countryConnectBtn, cityList.getSelectedValue());
        }).start();
    }

    // Change cityConnectBtn txt
    public void setCityConnectBtnText(JButton countryConnectBtn, String city){
        countryConnectBtn.setText("Connect to " + city);
    }

    // get flag and country name to display on the left side
    public void getFlagAndCountryName(JLabel countryFlagLabel, JLabel countryNameLabel, String country){
        String countryCode = CountryLocales.getCountryCode(country);
        ImageIcon flag = CountryCellRenderer.loadFlag(countryCode.toLowerCase(), 45);
        countryFlagLabel.setIcon(flag);
        countryNameLabel.setText(country);
    }

}
