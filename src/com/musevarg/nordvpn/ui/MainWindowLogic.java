package com.musevarg.nordvpn.ui;

import com.musevarg.nordvpn.vpn.NordVPN;

import javax.swing.*;

public class MainWindowLogic {

    NordVPN nordVPN = NordVPN.getInstance();

    public MainWindowLogic(){}

    public void createCountryList(JList<String> countryList){
        String[] countries = nordVPN.countries();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String c : countries) {
            String country = c.replace("_", " ");
            listModel.addElement(country);
        }
        countryList.setModel(listModel);
        countryList.setCellRenderer(new CountryCellRenderer());
    }

}
