package com.musevarg.nordvpn.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import com.musevarg.nordvpn.util.CountryLocales;

// Custom cell renderer for the country list (allows for flag + label)
public class CountryCellRenderer extends DefaultListCellRenderer {

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

// This renderer is used to provide text wrap in the JList that acts as a log for CLI commands and responses
class CommandsCellRenderer extends DefaultListCellRenderer {
    public static final String HTML_1 = "<html><body style='width: ";
    public static final String HTML_2 = "px'>";
    public static final String HTML_3 = "</html>";
    private int width;

    public CommandsCellRenderer(int width) {
        this.width = width;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        String text = HTML_1 + width + HTML_2 + value.toString() + HTML_3;
        return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
    }

}