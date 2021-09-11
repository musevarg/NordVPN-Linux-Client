package com.musevarg.nordvpn.ui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import com.musevarg.nordvpn.util.CountryLocales;

// Custom cell renderer for the country list (allows for flag + label)
public class CountryCellRenderer extends DefaultListCellRenderer {

        // Load flag from the res folder, if error load the flag of Taured
        public static ImageIcon loadFlag(String countryCode, int width){
            int height = width - (width/3);
            try{
                ImageIcon flag = new ImageIcon(Objects.requireNonNull(CountryCellRenderer.class.getClassLoader().getResource("res/flags/" + countryCode + ".png")));
                Image tempImage = flag.getImage().getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH); // Resize image
                flag = new ImageIcon(tempImage);
                return flag;
            } catch (Exception e) {
                System.out.println(countryCode + " - " + e);
                ImageIcon flag = new ImageIcon(Objects.requireNonNull(CountryCellRenderer.class.getClassLoader().getResource("res/flags/ad.png")));
                Image tempImage = flag.getImage().getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH); // Resize image
                flag = new ImageIcon(tempImage);
                return flag;
            }

        }

        @Override // Create custom label for the country list, including a flag and a label
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            String countryCode = CountryLocales.getCountryCode(label.getText());
            label.setIcon(loadFlag(countryCode.toLowerCase(), 30));
            label.setHorizontalTextPosition(JLabel.RIGHT);
            return label;

        }
}


// Custom cell renderer for the group list (allows for icon + label)
class ServerGroupCellRenderer extends DefaultListCellRenderer {

    // Load flag from the res folder, if error load the flag of Taured
    public static ImageIcon loadIcon(String iconName, int size){
        try{
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(CountryCellRenderer.class.getClassLoader().getResource("res/groups/" + iconName)));
            Image tempImage = icon.getImage().getScaledInstance(size, size,  java.awt.Image.SCALE_SMOOTH); // Resize image
            icon = new ImageIcon(tempImage);
            return icon;
        } catch (Exception e) {
            System.out.println(iconName + " - " + e);
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(CountryCellRenderer.class.getClassLoader().getResource("res/groups/vpn.png")));
            Image tempImage = icon.getImage().getScaledInstance(size, size,  java.awt.Image.SCALE_SMOOTH); // Resize image
            icon = new ImageIcon(tempImage);
            return icon;
        }

    }

    public static Map<String, String> groupIcons(){
        Map<String, String> groupIcons = new HashMap<>();
        groupIcons.put("Africa The Middle East And India", "africa.png");
        groupIcons.put("Asia Pacific", "asia.png");
        groupIcons.put("Dedicated IP", "ip.png");
        groupIcons.put("Double VPN", "vpn2.png");
        groupIcons.put("Europe", "europe.png");
        groupIcons.put("Onion Over VPN", "onion.png");
        groupIcons.put("P2P", "p2p.png");
        groupIcons.put("Standard VPN Servers", "vpn.png");
        groupIcons.put("The Americas", "america.png");
        return groupIcons;
    }

    @Override // Create custom label for the country list, including an icon and a label
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        String icon = groupIcons().get(label.getText().trim());
        label.setIcon(loadIcon(icon, 25));
        label.setHorizontalTextPosition(JLabel.RIGHT);
        return label;

    }
}