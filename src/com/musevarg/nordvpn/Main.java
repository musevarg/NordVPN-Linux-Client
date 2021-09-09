package com.musevarg.nordvpn;

import com.musevarg.nordvpn.ui.Client;

import javax.swing.*;

public class Main {

    public static void main (String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Gives UI a native look and feel
        SwingUtilities.invokeLater(Client::new);
    }
}
