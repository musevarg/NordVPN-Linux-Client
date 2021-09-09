package com.musevarg.nordvpn.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class CountryListElement extends DefaultListCellRenderer {

        //Font font = new Font("helvitica", Font.BOLD, 24);

        private ImageIcon loadFlag(String countryCode){
            ImageIcon flag = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("res/flags/" + countryCode + ".png")));
            Image tempImage = flag.getImage().getScaledInstance(40, 26,  java.awt.Image.SCALE_SMOOTH); // Resize image
            flag = new ImageIcon(tempImage);
            return flag;
        }

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);

            label.setIcon(loadFlag("ad"));
            label.setHorizontalTextPosition(JLabel.RIGHT);
            //label.setFont(font);
            return label;
        }
}
