package com.police;

import com.police.ui.MainFrame;
import com.police.ui.StartupDialog;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();

            // Show Opening PopUp as requested
            StartupDialog startupDialog = new StartupDialog(mainFrame);
            startupDialog.setVisible(true);

            // Display Main Window
            mainFrame.setVisible(true);
        });
    }
}
