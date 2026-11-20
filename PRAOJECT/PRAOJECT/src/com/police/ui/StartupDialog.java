package com.police.ui;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class StartupDialog extends JDialog {

    public StartupDialog(JFrame parent) {
        super(parent, "Rajkot Crime Branch - Police Management System", true);
        setUndecorated(true);
        setSize(650, 420);
        setLocationRelativeTo(parent);
        setResizable(false);

        // Custom rounded panel
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient Background
                GradientPaint gp = new GradientPaint(0, 0, PoliceTheme.NAVY_DARK, 0, getHeight(), new Color(7, 15, 28));
                g2.setPaint(gp);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));

                // Golden Outer Border
                g2.setColor(PoliceTheme.GOLD);
                g2.setStroke(new BasicStroke(3));
                g2.draw(new RoundRectangle2D.Double(1, 1, getWidth() - 3, getHeight() - 3, 20, 20));

                // Inner Accent Line
                g2.setColor(new Color(212, 175, 55, 60));
                g2.setStroke(new BasicStroke(1));
                g2.draw(new RoundRectangle2D.Double(8, 8, getWidth() - 17, getHeight() - 17, 16, 16));

                g2.dispose();
            }
        };
        mainPanel.setLayout(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(25, 30, 25, 30));
        mainPanel.setOpaque(false);

        // Center Content Panel
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Police Badge Canvas
        JPanel badgeCanvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int cx = getWidth() / 2;
                int cy = getHeight() / 2;

                // Outer Gold Circle
                g2.setColor(PoliceTheme.GOLD);
                g2.fillOval(cx - 36, cy - 36, 72, 72);

                // Inner Navy Shield Circle
                g2.setColor(PoliceTheme.NAVY_MID);
                g2.fillOval(cx - 31, cy - 31, 62, 62);

                // Star / Shield Graphic
                g2.setColor(PoliceTheme.GOLD_LIGHT);
                int[] xPoints = {cx, cx + 8, cx + 24, cx + 12, cx + 16, cx, cx - 16, cx - 12, cx - 24, cx - 8};
                int[] yPoints = {cy - 22, cy - 8, cy - 8, cy + 2, cy + 18, cy + 8, cy + 18, cy + 2, cy - 8, cy - 8};
                g2.fillPolygon(xPoints, yPoints, 10);

                // Center emblem
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                FontMetrics fm = g2.getFontMetrics();
                String starTxt = "RCB";
                g2.drawString(starTxt, cx - fm.stringWidth(starTxt) / 2, cy + 4);

                g2.dispose();
            }
        };
        badgeCanvas.setPreferredSize(new Dimension(80, 80));
        badgeCanvas.setMaximumSize(new Dimension(80, 80));
        badgeCanvas.setAlignmentX(Component.CENTER_ALIGNMENT);
        badgeCanvas.setOpaque(false);

        // Subtitle: Police Management System
        JLabel lblSys = new JLabel("POLICE MANAGEMENT SYSTEM", SwingConstants.CENTER);
        lblSys.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblSys.setForeground(PoliceTheme.GOLD);
        lblSys.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSys.setBorder(new EmptyBorder(10, 0, 5, 0));

        // Big Main Title: RAJKOT CRIME BRANCH
        JLabel lblMain = new JLabel("RAJKOT CRIME BRANCH", SwingConstants.CENTER);
        lblMain.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblMain.setForeground(Color.WHITE);
        lblMain.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Department Info
        JLabel lblDept = new JLabel("Government of Gujarat • Police Department HQ", SwingConstants.CENTER);
        lblDept.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDept.setForeground(PoliceTheme.TEXT_MUTED);
        lblDept.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblDept.setBorder(new EmptyBorder(4, 0, 15, 0));

        // Separator line
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(450, 2));
        sep.setForeground(PoliceTheme.BORDER_COLOR);
        sep.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Security Notice
        JLabel lblNotice = new JLabel("<html><center>Confidential Criminal Dossier & Judicial Records Management System<br><font color='#FEFE90'>Authorized Police Personnel Access Only</font></center></html>", SwingConstants.CENTER);
        lblNotice.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblNotice.setForeground(PoliceTheme.TEXT_WHITE);
        lblNotice.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblNotice.setBorder(new EmptyBorder(12, 0, 15, 0));

        centerPanel.add(badgeCanvas);
        centerPanel.add(lblSys);
        centerPanel.add(lblMain);
        centerPanel.add(lblDept);
        centerPanel.add(sep);
        centerPanel.add(lblNotice);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom Action Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);

        JButton btnEnter = PoliceTheme.createGoldButton("ENTER POLICE MANAGEMENT SYSTEM   ➔");
        btnEnter.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEnter.setPreferredSize(new Dimension(360, 42));
        btnEnter.addActionListener(e -> dispose());

        bottomPanel.add(btnEnter);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        getRootPane().setDefaultButton(btnEnter);
    }
}
