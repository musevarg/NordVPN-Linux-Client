package com.musevarg.nordvpn.ui;

import com.musevarg.nordvpn.vpn.NordVPN;

import javax.swing.*;

public class MainWindowLogic {

    NordVPN nordVPN = NordVPN.getInstance();
    String[] countries;

    public MainWindowLogic(){
        this.countries = nordVPN.countries();
    }

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
            countryConnectBtn.setText("Connect to " + cityList.getSelectedValue());
        }).start();
    }

}
