package com.police.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PoliceTheme {
    // Colors
    public static final Color NAVY_DARK = new Color(11, 25, 44);      // #0B192C
    public static final Color NAVY_MID = new Color(30, 62, 98);       // #1E3E62
    public static final Color NAVY_LIGHT = new Color(48, 85, 128);    // #305580
    public static final Color GOLD = new Color(212, 175, 55);         // #D4AF37
    public static final Color GOLD_LIGHT = new Color(241, 196, 15);   // #F1C40F
    public static final Color BG_DARK = new Color(15, 23, 42);        // #0F172A
    public static final Color PANEL_BG = new Color(26, 38, 57);       // #1A2639
    public static final Color CARD_BG = new Color(34, 49, 73);        // #223149
    public static final Color TEXT_WHITE = new Color(248, 250, 252);
    public static final Color TEXT_MUTED = new Color(148, 163, 184);
    public static final Color TEXT_GOLD = new Color(254, 224, 102);
    public static final Color BORDER_COLOR = new Color(51, 71, 98);

    // Status Colors
    public static final Color STATUS_RELEASED_BG = new Color(20, 83, 45); // Dark Green
    public static final Color STATUS_RELEASED_FG = new Color(74, 222, 128); // Bright Green
    public static final Color STATUS_CUSTODY_BG = new Color(127, 29, 29);  // Dark Red
    public static final Color STATUS_CUSTODY_FG = new Color(248, 113, 113); // Bright Red

    // Fonts
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_HEADER_BIG = new Font("Segoe UI", Font.BOLD, 26);
    public static final Font FONT_SECTION = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BODY_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_BADGE = new Font("Segoe UI", Font.BOLD, 14);

    public static JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setForeground(new Color(56, 189, 248)); // Vibrant Blue
        btn.setBackground(NAVY_MID);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new CompoundBorder(
                new LineBorder(GOLD, 1, true),
                new EmptyBorder(8, 18, 8, 18)
        ));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(NAVY_LIGHT);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(NAVY_MID);
            }
        });
        return btn;
    }

    public static JButton createGoldButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setForeground(Color.BLACK); // Black for special button
        btn.setBackground(GOLD);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new CompoundBorder(
                new LineBorder(GOLD_LIGHT, 1, true),
                new EmptyBorder(8, 20, 8, 20)
        ));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(GOLD_LIGHT);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(GOLD);
            }
        });
        return btn;
    }

    public static JButton createSecondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setForeground(new Color(56, 189, 248)); // Vibrant Blue
        btn.setBackground(new Color(51, 65, 85));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(8, 16, 8, 16)
        ));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(71, 85, 105));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(51, 65, 85));
            }
        });
        return btn;
    }

    public static JButton createDangerButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setForeground(new Color(56, 189, 248)); // Vibrant Blue
        btn.setBackground(new Color(185, 28, 28));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new CompoundBorder(
                new LineBorder(new Color(239, 68, 68), 1, true),
                new EmptyBorder(8, 16, 8, 16)
        ));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(220, 38, 38));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(185, 28, 28));
            }
        });
        return btn;
    }

    public static JTextField createTextField() {
        JTextField tf = new JTextField();
        tf.setFont(FONT_BODY);
        tf.setForeground(TEXT_WHITE);
        tf.setBackground(CARD_BG);
        tf.setCaretColor(GOLD);
        tf.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(6, 10, 6, 10)
        ));
        return tf;
    }

    public static JTextArea createTextArea(int rows, int cols) {
        JTextArea ta = new JTextArea(rows, cols);
        ta.setFont(FONT_BODY);
        ta.setForeground(TEXT_WHITE);
        ta.setBackground(CARD_BG);
        ta.setCaretColor(GOLD);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(6, 8, 6, 8)
        ));
        return ta;
    }

    public static JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(FONT_BODY);
        cb.setForeground(TEXT_WHITE);
        cb.setBackground(CARD_BG);
        cb.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(4, 6, 4, 6)
        ));
        return cb;
    }

    public static JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_LABEL);
        label.setForeground(TEXT_WHITE);
        return label;
    }

    public static JLabel createRequiredFieldLabel(String text) {
        JLabel label = new JLabel("<html>" + text + " <font color='#FF5252'>*</font></html>");
        label.setFont(FONT_LABEL);
        label.setForeground(TEXT_WHITE);
        return label;
    }

    public static JPanel createSectionHeader(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(6, 0, 10, 0));

        JLabel lbl = new JLabel(title);
        lbl.setFont(FONT_SECTION);
        lbl.setForeground(GOLD);

        panel.add(lbl, BorderLayout.WEST);

        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER_COLOR);
        panel.add(sep, BorderLayout.SOUTH);

        return panel;
    }

    public static Border createCardBorder(String title) {
        return BorderFactory.createTitledBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                " " + title + " ",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                FONT_LABEL,
                GOLD
        );
    }
}
