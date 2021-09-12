package com.musevarg.nordvpn.ui;

import com.musevarg.nordvpn.util.CountryLocales;
import com.musevarg.nordvpn.vpn.NordVPN;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class MainWindowLogic {

    public NordVPN nordVPN;
    public String[] countries;
    public String[] groups;
    public ResourceBundle rb;
    private final MainWindow mainWindow;

    public MainWindowLogic(MainWindow mainWindow, ResourceBundle rb){
        this.mainWindow = mainWindow;
        this.rb = rb;
        this.nordVPN = NordVPN.getInstance();
        this.countries = nordVPN.getCountries();
        this.groups = nordVPN.getServerGroups();
    }

    /*
     * THE METHODS BELOW HANDLE CONNECTIONS TO THE VPN
     */

    public void quickConnectButtonPressed(JButton quickConnectBtn, JButton serverCountryBtn, JButton serverGroupsBtn, JLabel statusLabel){
        new Thread(() -> {
            if (nordVPN.isConnected){
                updateUiWhileConnecting(false, quickConnectBtn, serverCountryBtn, serverGroupsBtn, statusLabel);
                nordVPN.disconnect();
                setQuickConnectBtnText(quickConnectBtn, rb.getString("quickConnect"));
                enableButtons(quickConnectBtn, serverCountryBtn, serverGroupsBtn);
            } else {
                updateUiWhileConnecting(true, quickConnectBtn, serverCountryBtn, serverGroupsBtn, statusLabel);
                nordVPN.connect();
                setQuickConnectBtnText(quickConnectBtn, rb.getString("disconnect"));
                enableButtons(quickConnectBtn, serverCountryBtn, serverGroupsBtn);
            }
        }).start();
    }



    /*
     *  THE METHODS BELOW ARE REUSABLE
     */

    // Set quickConnectBtn txt
    public void setQuickConnectBtnText(JButton quickConnectBtn, String text) { quickConnectBtn.setText(text); }

    // Set connectBtn txt (country and group cards)
    public void setConnectBtnText(JButton connectBtn) { connectBtn.setText(rb.getString("connectTo")); }

    // Set connectBtn txt and add place it will connect to (country and group cards)
    public void setConnectBtnText(JButton connectBtn, String place) { connectBtn.setText(rb.getString("connectTo") + " " + place); }

    // Set backBtn txt
    public void backButtonText(JButton backButton){
        backButton.setText(rb.getString("back"));
    }

    // Update UI while connecting
    private void updateUiWhileConnecting(boolean isConnecting, JButton quickConnectBtn, JButton serverCountryBtn, JButton serverGroupsBtn, JLabel statusLabel){
        disableButtons(quickConnectBtn, serverCountryBtn, serverGroupsBtn);
        if (isConnecting){
            quickConnectBtn.setText(rb.getString("connecting"));
            statusLabel.setText(rb.getString("connecting"));
        } else {
            statusLabel.setText(rb.getString("disconnecting"));
            quickConnectBtn.setText(rb.getString("disconnecting"));
        }
        mainWindow.mainCardLayout.show(mainWindow.mainCardLayoutPanel, "defaultCard");
    }

    // Disable default panel buttons
    private void disableButtons(JButton quickConnectBtn, JButton serverCountryBtn, JButton serverGroupsBtn) {
        quickConnectBtn.setEnabled(false);
        serverCountryBtn.setEnabled(false);
        serverGroupsBtn.setEnabled(false);
    }

    // Enable default panel buttons
    private void enableButtons(JButton quickConnectBtn, JButton serverCountryBtn, JButton serverGroupsBtn) {
        quickConnectBtn.setEnabled(true);
        serverCountryBtn.setEnabled(true);
        serverGroupsBtn.setEnabled(true);
    }

    /*
     * THE METHODS BELOW ARE USED TO CREATE ELEMENTS IN THE DEFAULT CARD
     */

    public void assignStatusLabels(JLabel[] statusLabels){
        statusLabels[0].setText(rb.getString("statusReceivedLabel"));
        statusLabels[1].setText(rb.getString("statusSentLabel"));
        statusLabels[2].setText(rb.getString("sServerLabel"));
        statusLabels[3].setText(rb.getString("sCountryLabel"));
        statusLabels[4].setText(rb.getString("sCityLabel"));
        statusLabels[5].setText(rb.getString("sIpLabel"));
        statusLabels[6].setText(rb.getString("sTechLabel"));
        statusLabels[7].setText(rb.getString("sProtocolLabel"));
        statusLabels[8].setText(rb.getString("sUptimeLabel"));
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
            String[] cities = nordVPN.getCities(countries[countryIndex]);
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
        countryFlagLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        countryNameLabel.setText(country);
    }


    /*
     * THE METHODS BELOW ARE USED CREATE ELEMENTS IN THE SERVER GROUPS CARD
     */

    // Populate group list
    public void createServerGroupsList(JList<String> groupList){
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String g : groups) {
            String group = g.replace("_", " ");
            listModel.addElement(group);
        }
        groupList.setModel(listModel);
        groupList.setCellRenderer(new ServerGroupCellRenderer());
    }

    // get icon and group name to display on the left side
    public void getIconAndGroupName(JLabel groupIconLabel, JLabel groupNameLabel, String group){
        String iconName = ServerGroupCellRenderer.groupIcons().get(group.trim());
        ImageIcon icon = ServerGroupCellRenderer.loadIcon(iconName, 40);
        groupIconLabel.setIcon(icon);
        groupNameLabel.setText(group);
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
