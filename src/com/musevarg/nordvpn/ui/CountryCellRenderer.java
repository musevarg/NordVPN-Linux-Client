package com.musevarg.nordvpn.ui;

import javax.swing.*;
import java.awt.*;
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