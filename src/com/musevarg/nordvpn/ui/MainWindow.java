package com.musevarg.nordvpn.ui;

import com.musevarg.nordvpn.util.LanguageLocales;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainWindow extends JFrame {

    // Locale and Resource Bundle
    private Locale currentLocale;
    private ResourceBundle rb;

    // Main Window Logic
    private MainWindowLogic mwl;

    // UI elements
    public JPanel mainCardLayoutPanel;
    public CardLayout mainCardLayout = (CardLayout)(mainCardLayoutPanel.getLayout());
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
    private JButton settingsBtn;
    private JPanel groupCard;
    private JPanel settingsCard;
    private JPanel settingsPanel;
    private JPanel settingsCardLayoutPanel;
    private CardLayout settingsCardLayout = (CardLayout) (settingsCardLayoutPanel.getLayout());
    private JList<String> settingsList;
    private JPanel settingsAboutCard;
    private JPanel settingsLogCard;
    private JButton settingsBackBtn;
    private JLabel settingsAboutLabel;
    private JTextArea settingsLogTextArea;
    private JList<String> groupList;
    private JLabel groupLogoLabel;
    private JLabel groupNameLabel;
    private JButton groupConnectBtn;
    private JButton groupBackBtn;

    public MainWindow(){
        initComponents();
        setApplicationIcon();
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

        // Create instance of main window logic
        mwl = new MainWindowLogic(this, rb);

        // Init text of the UI
        initText();

        // Button Action Listeners
        initButtonsListeners();

        // Remaining elements to be generated
        generateCountryAndCityList();
        generateServerGroupsList();

        // Show main panel
        initCards();

        // Init settings card
        initSettingsCards();
    }

    // Init the locale
    private void initLocale(Locale locale){
        currentLocale = locale;
        rb = ResourceBundle.getBundle("language", currentLocale);
    }

    // Load app icon
    private void setApplicationIcon(){
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(CountryCellRenderer.class.getClassLoader().getResource("res/img/icon.png")));
        this.setIconImage(icon.getImage());
    }

    // Init the text based on locale language
    private void initText(){
        this.setTitle(rb.getString("mainTitle"));
        initDefaultPanelText();
        initCountryPanelText();
        initGroupPanelText();
        initSettingsPanelText();
    }

    // Init the buttons action listeners
    private void initButtonsListeners(){
        initDefaultPanelButtonActions();
        initCountryPanelButtonActions();
        initGroupPanelButtonsActions();
        initSettingsPanelButtonsActions();
    }

    /*
     * SET THE TEXT FROM THE RESOURCE BUNDLE IN THE METHODS BELOW
     */

    // Init the text of the default panel
    private void initDefaultPanelText(){
        quickConnBtn.setText(rb.getString("quickConnect"));
        serverCountriesBtn.setText(rb.getString("chooseServerCountry"));
        serverGroupsBtn.setText(rb.getString("chooseServerGroup"));
        settingsBtn.setText(rb.getString("settings"));
    }

    // Init the text in the country panel
    private void initCountryPanelText(){
        pickCityLabel.setText("<html><body><p style=\"margin-top:1px;\">"+rb.getString("city")+"</p></body></html>");
        mwl.setConnectBtnText(countryConnectBtn);
        mwl.backButtonText(countryBackBtn);
    }

    // Init the text in the group panel
    private void initGroupPanelText(){
        groupConnectBtn.setText(rb.getString("connectToGroup"));
        mwl.backButtonText(groupBackBtn);
    }

    // Init the text in the settings panel
    private void initSettingsPanelText(){
        mwl.generateSettingsList(rb, settingsList);
        mwl.backButtonText(settingsBackBtn);
        settingsAboutLabel.setText(rb.getString("aboutText"));
    }

    /*
     * SET THE ACTIONS OF THE BUTTONS IN THE METHODS BELOW
     */

    // Init button actions of the default panel
    private void initDefaultPanelButtonActions(){
        quickConnBtn.addActionListener(e -> mwl.quickConnectButtonPressed(quickConnBtn, serverCountriesBtn, serverGroupsBtn, statusLabel));
        serverCountriesBtn.addActionListener(e -> showCountryCard());
        serverGroupsBtn.addActionListener(e -> showGroupCard());
        settingsBtn.addActionListener(e -> showSettingsCard());
    }

    // Init button actions of the country panel
    private void initCountryPanelButtonActions(){
        countryBackBtn.addActionListener(e -> showDefaultCard());
    }

    // Init button actions of the server group panel
    private void initGroupPanelButtonsActions(){
        groupBackBtn.addActionListener(e -> showDefaultCard());
        groupList.addListSelectionListener( e -> {
            if(e.getValueIsAdjusting()) {
                mwl.getIconAndGroupName(groupLogoLabel, groupNameLabel, groupList.getSelectedValue());
            }
        });
    }

    // Init button actions of the settings panel
    private void initSettingsPanelButtonsActions(){
        settingsBackBtn.addActionListener(e -> {
            showSettingsAboutCard();
            settingsList.setSelectedIndex(0);
            showDefaultCard();
        });
        settingsList.addListSelectionListener( e -> {
            if(e.getValueIsAdjusting()) {
                settingsSelectionChanged(settingsList.getSelectedIndex());
            }
        });
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
                mwl.setConnectBtnText(countryConnectBtn, cityList.getSelectedValue());
            }
        });
    }

    private void generateServerGroupsList(){
        mwl.createServerGroupsList(groupList);
    }

    /*
     * THE METHODS BELOW ARE USED TO RECREATE THE CARD LAYOUT
     * AND SHOW A SPECIFIC PANEL (CARD)
     */

    private void initCards(){
        mainCardLayoutPanel.removeAll();
        mainCardLayoutPanel.add("defaultCard", defaultCard);
        mainCardLayoutPanel.add("countryCard", countryCard);
        mainCardLayoutPanel.add("groupCard", groupCard);
        mainCardLayoutPanel.add("settingsCard", settingsCard);
        mainCardLayout.show(mainCardLayoutPanel, "defaultCard");
    }

    // Display the default panel
    private void showDefaultCard(){
        mainCardLayout.show(mainCardLayoutPanel, "defaultCard");
    }

    // Display the country panel
    private void showCountryCard(){
        if (countryList.getSelectedIndex() == -1){
            countryList.setSelectedIndex(0);
            mwl.createCityList(cityList, countryList.getSelectedIndex(), countryConnectBtn);
            mwl.getFlagAndCountryName(countryFlagLabel, countryNameLabel, countryList.getSelectedValue());
        }
        mainCardLayout.show(mainCardLayoutPanel, "countryCard");
    }

    // Display the server group panel
    private void showGroupCard(){
        mainCardLayout.show(mainCardLayoutPanel, "groupCard");
    }

    // Display the settings panel
    private void showSettingsCard(){
        mainCardLayout.show(mainCardLayoutPanel, "settingsCard");
    }


    /*
     * THE METHODS BELOW ARE USED TO GENERATE THE SETTINGS CARDS
     */

    // Init the settings card layout
    private void initSettingsCards(){
        settingsCardLayoutPanel.removeAll();
        settingsCardLayoutPanel.add("settingsAboutCard", settingsAboutCard);
        settingsCardLayoutPanel.add("settingsLogCard", settingsLogCard);
        settingsCardLayout.show(settingsCardLayoutPanel, "settingsAboutCard");
        settingsList.setSelectedIndex(0);
    }

    // Display the about card
    private void showSettingsAboutCard() { settingsCardLayout.show(settingsCardLayoutPanel, "settingsAboutCard"); }

    // Display the log card
    private void showSettingsLogCard() {
        mwl.updateSettingsLog(settingsLogTextArea);
        settingsCardLayout.show(settingsCardLayoutPanel, "settingsLogCard");
    }

    // Change the settings card based on the user's selection
    private void settingsSelectionChanged(int settingsSelectionIndex){
        switch (settingsSelectionIndex){
            case 0:
                showSettingsAboutCard();
                break;
            case 1:
                showSettingsLogCard();
                break;
        }
    }



}
