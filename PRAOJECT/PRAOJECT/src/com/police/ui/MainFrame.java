package com.police.ui;

import com.police.data.DatabaseManager;
import com.police.model.Criminal;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainFrame extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel cardsPanel;

    private Page1Search page1Search;
    private Page2CriminalDetail page2CriminalDetail;
    private Page3AddCriminal page3AddCriminal;
    private Page4FIRManagement page4FIRManagement;

    private JLabel lblClock;
    private JLabel lblFooterStatus;

    public static final String PAGE_1_SEARCH = "PAGE_1_SEARCH";
    public static final String PAGE_2_DETAIL = "PAGE_2_DETAIL";
    public static final String PAGE_3_ADD = "PAGE_3_ADD";
    public static final String PAGE_4_FIR = "PAGE_4_FIR";

    public MainFrame() {
        super("Police Management System - Rajkot Crime Branch");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1180, 800);
        setMinimumSize(new Dimension(1000, 700));
        setLocationRelativeTo(null);

        // Main Layout
        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(PoliceTheme.BG_DARK);

        // 1. TOP HEADER / APP BAR
        JPanel appHeader = createHeaderPanel();
        rootPanel.add(appHeader, BorderLayout.NORTH);

        // 2. CENTER CARDS CONTAINER
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);
        cardsPanel.setBackground(PoliceTheme.BG_DARK);

        page1Search = new Page1Search(this);
        page2CriminalDetail = new Page2CriminalDetail(this);
        page3AddCriminal = new Page3AddCriminal(this);
        page4FIRManagement = new Page4FIRManagement(this);

        cardsPanel.add(page1Search, PAGE_1_SEARCH);
        cardsPanel.add(page2CriminalDetail, PAGE_2_DETAIL);
        cardsPanel.add(page3AddCriminal, PAGE_3_ADD);
        cardsPanel.add(page4FIRManagement, PAGE_4_FIR);

        rootPanel.add(cardsPanel, BorderLayout.CENTER);

        // 3. BOTTOM FOOTER / STATUS BAR
        JPanel footer = createFooterPanel();
        rootPanel.add(footer, BorderLayout.SOUTH);

        setContentPane(rootPanel);

        // Start Live Clock
        Timer clockTimer = new Timer(1000, e -> updateClock());
        clockTimer.start();
        updateClock();
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout(15, 0));
        header.setBackground(PoliceTheme.NAVY_DARK);
        header.setBorder(new CompoundBorder(
                new LineBorder(PoliceTheme.BORDER_COLOR, 1),
                new EmptyBorder(10, 20, 10, 20)
        ));

        // Left Branding
        JPanel brandPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        brandPanel.setOpaque(false);

        // Mini Badge
        JPanel miniBadge = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(PoliceTheme.GOLD);
                g2.fillOval(2, 2, 36, 36);
                g2.setColor(PoliceTheme.NAVY_MID);
                g2.fillOval(5, 5, 30, 30);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 10));
                g2.drawString("RCB", 9, 23);
                g2.dispose();
            }
        };
        miniBadge.setPreferredSize(new Dimension(40, 40));
        miniBadge.setOpaque(false);

        JPanel titleStack = new JPanel(new GridLayout(2, 1, 0, 1));
        titleStack.setOpaque(false);

        JLabel lblApp = new JLabel("POLICE MANAGEMENT SYSTEM");
        lblApp.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblApp.setForeground(PoliceTheme.GOLD);

        JLabel lblBranch = new JLabel("RAJKOT CRIME BRANCH");
        lblBranch.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblBranch.setForeground(Color.WHITE);

        titleStack.add(lblApp);
        titleStack.add(lblBranch);

        brandPanel.add(miniBadge);
        brandPanel.add(titleStack);

        header.add(brandPanel, BorderLayout.WEST);

        // Right Navigation & Live Clock
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 4));
        navPanel.setOpaque(false);

        JButton btnNavHome = PoliceTheme.createSecondaryButton("🏠 Criminal Records");
        btnNavHome.addActionListener(e -> showPage1Search());

        JButton btnNavFIR = PoliceTheme.createSecondaryButton("📑 FIR Records");
        btnNavFIR.addActionListener(e -> showPage4FIR());

        JButton btnNavAdd = PoliceTheme.createGoldButton("+ New Criminal");
        btnNavAdd.addActionListener(e -> showPage3AddCriminal());

        lblClock = new JLabel("2026-08-30 00:00:00");
        lblClock.setFont(new Font("Consolas", Font.BOLD, 13));
        lblClock.setForeground(PoliceTheme.TEXT_GOLD);
        lblClock.setBorder(new CompoundBorder(
                new LineBorder(PoliceTheme.BORDER_COLOR, 1, true),
                new EmptyBorder(6, 12, 6, 12)
        ));

        navPanel.add(btnNavHome);
        navPanel.add(btnNavFIR);
        navPanel.add(btnNavAdd);
        navPanel.add(Box.createHorizontalStrut(10));
        navPanel.add(lblClock);

        header.add(navPanel, BorderLayout.EAST);
        return header;
    }

    private JPanel createFooterPanel() {
        JPanel footer = new JPanel(new BorderLayout(10, 0));
        footer.setBackground(PoliceTheme.NAVY_DARK);
        footer.setBorder(new CompoundBorder(
                new LineBorder(PoliceTheme.BORDER_COLOR, 1),
                new EmptyBorder(6, 15, 6, 15)
        ));

        lblFooterStatus = new JLabel("System Ready • Database Connected: police_records.json • Total Records: " + DatabaseManager.getInstance().getAllCriminals().size());
        lblFooterStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblFooterStatus.setForeground(PoliceTheme.TEXT_MUTED);

        JLabel lblSec = new JLabel("RESTRICTED ACCESS • GUJARAT POLICE CRIME BRANCH INTELLIGENCE");
        lblSec.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblSec.setForeground(PoliceTheme.GOLD);

        footer.add(lblFooterStatus, BorderLayout.WEST);
        footer.add(lblSec, BorderLayout.EAST);
        return footer;
    }

    private void updateClock() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MMM-yyyy | HH:mm:ss");
        lblClock.setText("🕒 " + now.format(fmt));
    }

    public void updateFooterCount() {
        int count = DatabaseManager.getInstance().getAllCriminals().size();
        lblFooterStatus.setText("System Ready • Database Connected: police_records.json • Total Criminals: " + count);
    }

    public void showPage1Search() {
        page1Search.refreshRecordsTable();
        cardLayout.show(cardsPanel, PAGE_1_SEARCH);
        updateFooterCount();
    }

    public void showPage2CriminalDetail(Criminal criminal) {
        if (criminal != null) {
            page2CriminalDetail.setCriminal(criminal);
            cardLayout.show(cardsPanel, PAGE_2_DETAIL);
            updateFooterCount();
        }
    }

    public void showPage3AddCriminal() {
        page3AddCriminal.resetForm();
        cardLayout.show(cardsPanel, PAGE_3_ADD);
        updateFooterCount();
    }

    public void showPage4FIR() {
        page4FIRManagement.refreshTable();
        cardLayout.show(cardsPanel, PAGE_4_FIR);
        int firCount = DatabaseManager.getInstance().getAllFIRs().size();
        lblFooterStatus.setText("System Ready • Database Connected • FIR Records: " + firCount + " | Criminals: " + DatabaseManager.getInstance().getAllCriminals().size());
    }
}
