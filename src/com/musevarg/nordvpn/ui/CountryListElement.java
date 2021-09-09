package com.musevarg.nordvpn.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import com.musevarg.nordvpn.util.CountryLocales;

public class CountryListElement extends DefaultListCellRenderer {

        // Load flag from the res folder, if error load the flag of Taured
        private ImageIcon loadFlag(String countryCode){
            try{
                ImageIcon flag = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("res/flags/" + countryCode + ".png")));
                Image tempImage = flag.getImage().getScaledInstance(30, 20,  java.awt.Image.SCALE_SMOOTH); // Resize image
                flag = new ImageIcon(tempImage);
                return flag;
            } catch (Exception e) {
                System.out.println(countryCode + " - " + e);
                ImageIcon flag = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("res/flags/ad.png")));
                Image tempImage = flag.getImage().getScaledInstance(30, 20,  java.awt.Image.SCALE_SMOOTH); // Resize image
                flag = new ImageIcon(tempImage);
                return flag;
            }

        }

        @Override // Create custom label for the country list, including a flag and a label
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            String countryCode = CountryLocales.getCountryCode(label.getText());
            label.setIcon(loadFlag(countryCode.toLowerCase()));
            label.setHorizontalTextPosition(JLabel.RIGHT);
            return label;

        }
}
