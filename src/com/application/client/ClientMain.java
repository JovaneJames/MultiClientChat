package com.application.client;

import javax.swing.*;

public class ClientMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientGUI().createWindowFrame());
    }
}