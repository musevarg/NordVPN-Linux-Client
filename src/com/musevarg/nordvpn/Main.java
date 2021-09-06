package com.musevarg.nordvpn;

import com.musevarg.nordvpn.ui.Client;

import javax.swing.*;

public class Main {

    public static void main (String[] args){
        SwingUtilities.invokeLater(Client::new);
    }
}
