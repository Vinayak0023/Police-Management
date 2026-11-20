package com.police.ui;

import com.police.data.DatabaseManager;
import com.police.model.CrimeRecord;
import com.police.model.Criminal;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class Page2CriminalDetail extends JPanel {
    private final MainFrame mainFrame;
    private Criminal currentCriminal;
    private CrimeRecord currentRecord;
    private boolean isEditMode = false;

    // UI Fields - Personal
    private JTextField txtName;
    private JTextField txtId;
    private JTextField txtAge;
    private JTextField txtHeight;
    private JTextArea txtAddress;

    // UI Fields - Crime Details
    private JComboBox<String> cmbCrimeSelector;
    private JTextField txtReasonForArrest;
    private JTextField txtImplementedSection;
    private JTextField txtCrimeLocation;
    private JTextField txtPartnerNames;
    private JTextField txtGangName;
    private JTextField txtImprisonmentFrom;
    private JTextField txtImprisonmentTo;
    private JComboBox<String> cmbOfficerRank;
    private JTextField txtOfficerName;
    private JComboBox<String> cmbCustodyStatus;

    // Right Side Photo & Status
    private JLabel lblPhoto;
    private JPanel statusBadgePanel;
    private JLabel lblStatusBadge;
    private JLabel lblStatusSub;
    private JButton btnChangePhoto;
    private String currentPhotoPath;

    // Bottom Action Buttons
    private JButton btnEdit;
    private JButton btnAddNewCrime;
    private JButton btnSave;
    private JButton btnBack;

    private static final String[] OFFICER_RANKS = {
            "DGP (Director General of Police)",
            "ADGP (Addl Director General)",
            "IGP (Inspector General)",
            "SSP (Senior Superintendent)",
            "SP (Superintendent of Police)",
            "DSP (Deputy Superintendent)",
            "PI (Police Inspector)",
            "PSI (Police Sub-Inspector)",
            "SI (Sub-Inspector)",
            "ASI (Assistant Sub-Inspector)"
    };

    public Page2CriminalDetail(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(12, 12));
        setBackground(PoliceTheme.BG_DARK);
        setBorder(new EmptyBorder(12, 18, 12, 18));

        initUI();
    }

    private void initUI() {
        // 1. TOP HEADER & NAVIGATION
        JPanel topHeader = new JPanel(new BorderLayout(10, 10));
        topHeader.setOpaque(false);

        JPanel headerBox = new JPanel(new BorderLayout());
        headerBox.setBackground(PoliceTheme.NAVY_DARK);
        headerBox.setBorder(new CompoundBorder(
                new LineBorder(PoliceTheme.GOLD, 2, true),
                new EmptyBorder(8, 16, 8, 16)
        ));

        JLabel lblTitle = new JLabel("CRIMINAL PROFILE & JUDICIAL DOSSIER", SwingConstants.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(PoliceTheme.GOLD);

        JLabel lblSub = new JLabel("Rajkot Crime Branch • Case History & Law Enforcement Record", SwingConstants.LEFT);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(PoliceTheme.TEXT_MUTED);

        JPanel titleStack = new JPanel(new GridLayout(2, 1, 0, 2));
        titleStack.setOpaque(false);
        titleStack.add(lblTitle);
        titleStack.add(lblSub);

        headerBox.add(titleStack, BorderLayout.WEST);

        // Top Navigation button
        JButton btnTopBack = PoliceTheme.createSecondaryButton("⬅ Back to Search (Page 1)");
        btnTopBack.addActionListener(e -> mainFrame.showPage1Search());
        headerBox.add(btnTopBack, BorderLayout.EAST);

        topHeader.add(headerBox, BorderLayout.CENTER);
        add(topHeader, BorderLayout.NORTH);

        // 2. MAIN CENTER CONTENT (Split into Left/Center Details & Right Photo/Status)
        JPanel mainContentPanel = new JPanel(new BorderLayout(16, 0));
        mainContentPanel.setOpaque(false);

        // --- LEFT / CENTER: CRIMINAL DETAILS & CRIME RECORDS ---
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);

        // Card 1: Personal Profile
        JPanel personalCard = new JPanel(new GridBagLayout());
        personalCard.setBackground(PoliceTheme.PANEL_BG);
        personalCard.setBorder(new CompoundBorder(
                PoliceTheme.createCardBorder("1. PERSONAL DETAILS & IDENTIFICATION"),
                new EmptyBorder(8, 12, 10, 12)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: Name and ID
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.15;
        personalCard.add(PoliceTheme.createFieldLabel("Criminal Name:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.35;
        txtName = PoliceTheme.createTextField();
        personalCard.add(txtName, gbc);

        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0.15;
        personalCard.add(PoliceTheme.createFieldLabel("Criminal ID:"), gbc);
        gbc.gridx = 3; gbc.gridy = 0; gbc.weightx = 0.35;
        txtId = PoliceTheme.createTextField();
        txtId.setEditable(false);
        txtId.setBackground(new Color(20, 30, 45));
        personalCard.add(txtId, gbc);

        // Row 1: Age and Height
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.15;
        personalCard.add(PoliceTheme.createFieldLabel("Age:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.35;
        txtAge = PoliceTheme.createTextField();
        personalCard.add(txtAge, gbc);

        gbc.gridx = 2; gbc.gridy = 1; gbc.weightx = 0.15;
        personalCard.add(PoliceTheme.createFieldLabel("Height:"), gbc);
        gbc.gridx = 3; gbc.gridy = 1; gbc.weightx = 0.35;
        txtHeight = PoliceTheme.createTextField();
        personalCard.add(txtHeight, gbc);

        // Row 2: Address
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.15;
        personalCard.add(PoliceTheme.createFieldLabel("Address:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 3; gbc.weightx = 0.85;
        txtAddress = PoliceTheme.createTextArea(2, 30);
        JScrollPane addrScroll = new JScrollPane(txtAddress);
        addrScroll.setPreferredSize(new Dimension(addrScroll.getPreferredSize().width, 50));
        personalCard.add(addrScroll, gbc);
        gbc.gridwidth = 1;

        leftPanel.add(personalCard);
        leftPanel.add(Box.createVerticalStrut(10));

        // Card 2: Crime Details & Multiple Incidents
        JPanel crimeCard = new JPanel(new BorderLayout(8, 8));
        crimeCard.setBackground(PoliceTheme.PANEL_BG);
        crimeCard.setBorder(new CompoundBorder(
                PoliceTheme.createCardBorder("2. CRIME RECORD & JUDICIAL SECTIONS"),
                new EmptyBorder(8, 12, 10, 12)
        ));

        // Incident selector header
        JPanel incidentSelectorBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        incidentSelectorBar.setOpaque(false);
        JLabel lblSelectCrime = PoliceTheme.createFieldLabel("Select Crime Incident:");
        lblSelectCrime.setForeground(PoliceTheme.GOLD);
        cmbCrimeSelector = PoliceTheme.createComboBox(new String[]{"Incident #1 (Initial Record)"});
        cmbCrimeSelector.setPreferredSize(new Dimension(280, 30));
        cmbCrimeSelector.addActionListener(e -> onCrimeSelected());

        incidentSelectorBar.add(lblSelectCrime);
        incidentSelectorBar.add(cmbCrimeSelector);
        crimeCard.add(incidentSelectorBar, BorderLayout.NORTH);

        // Crime form fields
        JPanel crimeFormGrid = new JPanel(new GridBagLayout());
        crimeFormGrid.setOpaque(false);
        GridBagConstraints cGbc = new GridBagConstraints();
        cGbc.insets = new Insets(4, 6, 4, 6);
        cGbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: Reason for Arrest
        cGbc.gridx = 0; cGbc.gridy = 0; cGbc.weightx = 0.22;
        crimeFormGrid.add(PoliceTheme.createFieldLabel("Reason for Arrest:"), cGbc);
        cGbc.gridx = 1; cGbc.gridy = 0; cGbc.gridwidth = 3; cGbc.weightx = 0.78;
        txtReasonForArrest = PoliceTheme.createTextField();
        crimeFormGrid.add(txtReasonForArrest, cGbc);
        cGbc.gridwidth = 1;

        // Row 1: Implemented Section (Law used by court)
        cGbc.gridx = 0; cGbc.gridy = 1; cGbc.weightx = 0.22;
        crimeFormGrid.add(PoliceTheme.createFieldLabel("Implemented Section (Law):"), cGbc);
        cGbc.gridx = 1; cGbc.gridy = 1; cGbc.gridwidth = 3; cGbc.weightx = 0.78;
        txtImplementedSection = PoliceTheme.createTextField();
        txtImplementedSection.setToolTipText("e.g. IPC Section 302, IPC 392, IPC 420 / Bharatiya Nyaya Sanhita");
        crimeFormGrid.add(txtImplementedSection, cGbc);
        cGbc.gridwidth = 1;

        // Row 2: Location for Crime & Gang Name
        cGbc.gridx = 0; cGbc.gridy = 2; cGbc.weightx = 0.22;
        crimeFormGrid.add(PoliceTheme.createFieldLabel("Location for Crime:"), cGbc);
        cGbc.gridx = 1; cGbc.gridy = 2; cGbc.weightx = 0.28;
        txtCrimeLocation = PoliceTheme.createTextField();
        crimeFormGrid.add(txtCrimeLocation, cGbc);

        cGbc.gridx = 2; cGbc.gridy = 2; cGbc.weightx = 0.22;
        crimeFormGrid.add(PoliceTheme.createFieldLabel("Gang Name (if group):"), cGbc);
        cGbc.gridx = 3; cGbc.gridy = 2; cGbc.weightx = 0.28;
        txtGangName = PoliceTheme.createTextField();
        crimeFormGrid.add(txtGangName, cGbc);

        // Row 3: Partner Name who involved
        cGbc.gridx = 0; cGbc.gridy = 3; cGbc.weightx = 0.22;
        crimeFormGrid.add(PoliceTheme.createFieldLabel("Partner(s) Involved:"), cGbc);
        cGbc.gridx = 1; cGbc.gridy = 3; cGbc.gridwidth = 3; cGbc.weightx = 0.78;
        txtPartnerNames = PoliceTheme.createTextField();
        crimeFormGrid.add(txtPartnerNames, cGbc);
        cGbc.gridwidth = 1;

        // Row 4: Imprisonment (From DD/MM/YYYY to DD/MM/YYYY)
        cGbc.gridx = 0; cGbc.gridy = 4; cGbc.weightx = 0.22;
        crimeFormGrid.add(PoliceTheme.createFieldLabel("Imprisonment (From):"), cGbc);
        cGbc.gridx = 1; cGbc.gridy = 4; cGbc.weightx = 0.28;
        txtImprisonmentFrom = PoliceTheme.createTextField();
        txtImprisonmentFrom.setToolTipText("DD/MM/YYYY");
        crimeFormGrid.add(txtImprisonmentFrom, cGbc);

        cGbc.gridx = 2; cGbc.gridy = 4; cGbc.weightx = 0.22;
        crimeFormGrid.add(PoliceTheme.createFieldLabel("Imprisonment (To):"), cGbc);
        cGbc.gridx = 3; cGbc.gridy = 4; cGbc.weightx = 0.28;
        txtImprisonmentTo = PoliceTheme.createTextField();
        txtImprisonmentTo.setToolTipText("DD/MM/YYYY");
        txtImprisonmentTo.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                updateCustodyBadgeFromInput();
            }
        });
        crimeFormGrid.add(txtImprisonmentTo, cGbc);

        // Row 5: Officer in charge (Post & Name)
        cGbc.gridx = 0; cGbc.gridy = 5; cGbc.weightx = 0.22;
        crimeFormGrid.add(PoliceTheme.createFieldLabel("Officer Rank:"), cGbc);
        cGbc.gridx = 1; cGbc.gridy = 5; cGbc.weightx = 0.28;
        cmbOfficerRank = PoliceTheme.createComboBox(OFFICER_RANKS);
        crimeFormGrid.add(cmbOfficerRank, cGbc);

        cGbc.gridx = 2; cGbc.gridy = 5; cGbc.weightx = 0.22;
        crimeFormGrid.add(PoliceTheme.createFieldLabel("Officer Name:"), cGbc);
        cGbc.gridx = 3; cGbc.gridy = 5; cGbc.weightx = 0.28;
        txtOfficerName = PoliceTheme.createTextField();
        crimeFormGrid.add(txtOfficerName, cGbc);

        // Row 6: Manual Custody Status Override
        cGbc.gridx = 0; cGbc.gridy = 6; cGbc.weightx = 0.22;
        crimeFormGrid.add(PoliceTheme.createFieldLabel("Custody Status:"), cGbc);
        cGbc.gridx = 1; cGbc.gridy = 6; cGbc.gridwidth = 3; cGbc.weightx = 0.78;
        cmbCustodyStatus = PoliceTheme.createComboBox(new String[]{"In Judicial Custody", "Released"});
        cmbCustodyStatus.addActionListener(e -> updateStatusBadgeDisplay());
        crimeFormGrid.add(cmbCustodyStatus, cGbc);
        cGbc.gridwidth = 1;

        crimeCard.add(crimeFormGrid, BorderLayout.CENTER);
        leftPanel.add(crimeCard);

        mainContentPanel.add(leftPanel, BorderLayout.CENTER);

        // --- RIGHT SIDE: PHOTO & STATUS DISPLAY ---
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(PoliceTheme.PANEL_BG);
        rightPanel.setPreferredSize(new Dimension(310, 520));
        rightPanel.setBorder(new CompoundBorder(
                PoliceTheme.createCardBorder("MUGSHOT & STATUS"),
                new EmptyBorder(10, 15, 12, 15)
        ));

        // Photo Frame
        lblPhoto = new JLabel("", SwingConstants.CENTER);
        lblPhoto.setPreferredSize(new Dimension(240, 240));
        lblPhoto.setMaximumSize(new Dimension(240, 240));
        lblPhoto.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPhoto.setBorder(new LineBorder(PoliceTheme.GOLD, 2));
        lblPhoto.setBackground(new Color(15, 23, 42));
        lblPhoto.setOpaque(true);

        btnChangePhoto = PoliceTheme.createSecondaryButton("📷 Change Photo");
        btnChangePhoto.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnChangePhoto.setMaximumSize(new Dimension(200, 30));
        btnChangePhoto.addActionListener(e -> selectNewPhoto());
        btnChangePhoto.setEnabled(false);

        // Status Card
        statusBadgePanel = new JPanel(new BorderLayout(5, 5));
        statusBadgePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusBadgePanel.setMaximumSize(new Dimension(270, 95));
        statusBadgePanel.setBorder(new CompoundBorder(
                new LineBorder(PoliceTheme.BORDER_COLOR, 1, true),
                new EmptyBorder(10, 10, 10, 10)
        ));

        lblStatusBadge = new JLabel("STATUS", SwingConstants.CENTER);
        lblStatusBadge.setFont(new Font("Segoe UI", Font.BOLD, 18));

        lblStatusSub = new JLabel("Imprisonment Status Indicator", SwingConstants.CENTER);
        lblStatusSub.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        statusBadgePanel.add(lblStatusBadge, BorderLayout.CENTER);
        statusBadgePanel.add(lblStatusSub, BorderLayout.SOUTH);

        // Security watermark text
        JLabel lblSecureSeal = new JLabel("<html><center><font color='#D4AF37'>● OFFICIAL POLICE RECORD ●</font><br><font color='#94A3B8'>Rajkot Crime Branch Archives</font></center></html>", SwingConstants.CENTER);
        lblSecureSeal.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSecureSeal.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSecureSeal.setBorder(new EmptyBorder(10, 0, 0, 0));

        rightPanel.add(lblPhoto);
        rightPanel.add(Box.createVerticalStrut(8));
        rightPanel.add(btnChangePhoto);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(statusBadgePanel);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(lblSecureSeal);

        mainContentPanel.add(rightPanel, BorderLayout.EAST);
        add(mainContentPanel, BorderLayout.CENTER);

        // 3. BOTTOM ACTION BUTTONS
        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 6));
        bottomBar.setOpaque(false);
        bottomBar.setBorder(new EmptyBorder(5, 0, 0, 0));

        btnEdit = PoliceTheme.createGoldButton("✏ Edit Details");
        btnEdit.setPreferredSize(new Dimension(140, 38));
        btnEdit.addActionListener(e -> toggleEditMode(true));

        btnAddNewCrime = PoliceTheme.createPrimaryButton("➕ Add New Crime for this Criminal");
        btnAddNewCrime.setPreferredSize(new Dimension(280, 38));
        btnAddNewCrime.addActionListener(e -> addNewCrimeIncident());

        btnSave = PoliceTheme.createGoldButton("💾 Save");
        btnSave.setPreferredSize(new Dimension(130, 38));
        btnSave.setEnabled(false);
        btnSave.addActionListener(e -> saveCriminalChanges());

        btnBack = PoliceTheme.createSecondaryButton("⬅ Back to Search");
        btnBack.setPreferredSize(new Dimension(150, 38));
        btnBack.addActionListener(e -> mainFrame.showPage1Search());

        bottomBar.add(btnEdit);
        bottomBar.add(btnAddNewCrime);
        bottomBar.add(btnSave);
        bottomBar.add(btnBack);

        add(bottomBar, BorderLayout.SOUTH);

        setFieldsEditable(false);
    }

    public void setCriminal(Criminal criminal) {
        this.currentCriminal = criminal;
        this.currentPhotoPath = criminal.getPhotoPath();

        // Populate personal details
        txtName.setText(criminal.getName());
        txtId.setText(criminal.getCriminalId());
        txtAge.setText(criminal.getAge());
        txtHeight.setText(criminal.getHeight());
        txtAddress.setText(criminal.getAddress());

        // Populate photo
        loadMugshot(criminal.getPhotoPath());

        // Populate Crime Incidents Selector
        List<CrimeRecord> records = criminal.getCrimeRecords();
        cmbCrimeSelector.removeAllItems();
        if (records.isEmpty()) {
            CrimeRecord initial = new CrimeRecord();
            criminal.addCrimeRecord(initial);
        }

        for (int i = 0; i < criminal.getCrimeRecords().size(); i++) {
            CrimeRecord r = criminal.getCrimeRecords().get(i);
            String label = "Incident #" + (i + 1) + ": " + (r.getReasonForArrest() == null || r.getReasonForArrest().isEmpty() ? "Crime Record" : r.getReasonForArrest());
            if (label.length() > 38) label = label.substring(0, 35) + "...";
            cmbCrimeSelector.addItem(label);
        }

        cmbCrimeSelector.setSelectedIndex(criminal.getCrimeRecords().size() - 1);
        displayCrimeRecord(criminal.getLatestCrime());

        toggleEditMode(false);
    }

    private void onCrimeSelected() {
        int idx = cmbCrimeSelector.getSelectedIndex();
        if (currentCriminal != null && idx >= 0 && idx < currentCriminal.getCrimeRecords().size()) {
            displayCrimeRecord(currentCriminal.getCrimeRecords().get(idx));
        }
    }

    private void displayCrimeRecord(CrimeRecord record) {
        this.currentRecord = record;
        if (record == null) return;

        txtReasonForArrest.setText(record.getReasonForArrest() == null ? "" : record.getReasonForArrest());
        txtImplementedSection.setText(record.getImplementedSection() == null ? "" : record.getImplementedSection());
        txtCrimeLocation.setText(record.getCrimeLocation() == null ? "" : record.getCrimeLocation());
        txtPartnerNames.setText(record.getPartnerNames() == null ? "" : record.getPartnerNames());
        txtGangName.setText(record.getGangName() == null ? "" : record.getGangName());
        txtImprisonmentFrom.setText(record.getImprisonmentFrom() == null ? "" : record.getImprisonmentFrom());
        txtImprisonmentTo.setText(record.getImprisonmentTo() == null ? "" : record.getImprisonmentTo());
        txtOfficerName.setText(record.getOfficerName() == null ? "" : record.getOfficerName());

        // Match officer rank in dropdown
        String rank = record.getOfficerRank();
        boolean matched = false;
        for (int i = 0; i < OFFICER_RANKS.length; i++) {
            if (rank != null && (OFFICER_RANKS[i].startsWith(rank) || OFFICER_RANKS[i].contains(rank))) {
                cmbOfficerRank.setSelectedIndex(i);
                matched = true;
                break;
            }
        }
        if (!matched) cmbOfficerRank.setSelectedIndex(6); // Default PI

        // Custody status
        record.autoUpdateCustodyStatus();
        if (record.isReleased()) {
            cmbCustodyStatus.setSelectedItem("Released");
        } else {
            cmbCustodyStatus.setSelectedItem("In Judicial Custody");
        }

        updateStatusBadgeDisplay();
    }

    private void updateCustodyBadgeFromInput() {
        String toDateStr = txtImprisonmentTo.getText().trim();
        if (!toDateStr.isEmpty()) {
            try {
                LocalDate toDate = LocalDate.parse(toDateStr, CrimeRecord.DATE_FORMATTER);
                if (toDate.isBefore(LocalDate.now())) {
                    cmbCustodyStatus.setSelectedItem("Released");
                } else {
                    cmbCustodyStatus.setSelectedItem("In Judicial Custody");
                }
            } catch (Exception ignored) {
            }
        }
        updateStatusBadgeDisplay();
    }

    private void updateStatusBadgeDisplay() {
        String status = (String) cmbCustodyStatus.getSelectedItem();
        boolean isReleased = "Released".equalsIgnoreCase(status);

        if (isReleased) {
            statusBadgePanel.setBackground(PoliceTheme.STATUS_RELEASED_BG);
            statusBadgePanel.setBorder(new LineBorder(PoliceTheme.STATUS_RELEASED_FG, 2, true));
            lblStatusBadge.setForeground(PoliceTheme.STATUS_RELEASED_FG);
            lblStatusBadge.setText("🟢 RELEASED");
            lblStatusSub.setForeground(PoliceTheme.STATUS_RELEASED_FG);
            lblStatusSub.setText("Imprisonment Period Completed");
        } else {
            statusBadgePanel.setBackground(PoliceTheme.STATUS_CUSTODY_BG);
            statusBadgePanel.setBorder(new LineBorder(PoliceTheme.STATUS_CUSTODY_FG, 2, true));
            lblStatusBadge.setForeground(PoliceTheme.STATUS_CUSTODY_FG);
            lblStatusBadge.setText("🔴 IN JUDICIAL CUSTODY");
            lblStatusSub.setForeground(PoliceTheme.STATUS_CUSTODY_FG);
            lblStatusSub.setText("Under Active Police / Jail Custody");
        }
        statusBadgePanel.repaint();
    }

    private void toggleEditMode(boolean enable) {
        this.isEditMode = enable;
        setFieldsEditable(enable);
        btnSave.setEnabled(enable);
        btnChangePhoto.setEnabled(enable);
        btnEdit.setEnabled(!enable);

        if (enable) {
            txtName.requestFocus();
            JOptionPane.showMessageDialog(this,
                    "Edit Mode Enabled.\nYou can now modify personal details, crime records, dates, or add a new crime incident.",
                    "Edit Mode Active",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void setFieldsEditable(boolean editable) {
        txtName.setEditable(editable);
        txtAge.setEditable(editable);
        txtHeight.setEditable(editable);
        txtAddress.setEditable(editable);

        txtReasonForArrest.setEditable(editable);
        txtImplementedSection.setEditable(editable);
        txtCrimeLocation.setEditable(editable);
        txtPartnerNames.setEditable(editable);
        txtGangName.setEditable(editable);
        txtImprisonmentFrom.setEditable(editable);
        txtImprisonmentTo.setEditable(editable);
        txtOfficerName.setEditable(editable);

        cmbOfficerRank.setEnabled(editable);
        cmbCustodyStatus.setEnabled(editable);

        Color fieldBg = editable ? PoliceTheme.CARD_BG : new Color(20, 30, 45);
        txtName.setBackground(fieldBg);
        txtAge.setBackground(fieldBg);
        txtHeight.setBackground(fieldBg);
        txtAddress.setBackground(fieldBg);

        txtReasonForArrest.setBackground(fieldBg);
        txtImplementedSection.setBackground(fieldBg);
        txtCrimeLocation.setBackground(fieldBg);
        txtPartnerNames.setBackground(fieldBg);
        txtGangName.setBackground(fieldBg);
        txtImprisonmentFrom.setBackground(fieldBg);
        txtImprisonmentTo.setBackground(fieldBg);
        txtOfficerName.setBackground(fieldBg);
    }

    private void addNewCrimeIncident() {
        if (currentCriminal == null) return;

        int confirm = JOptionPane.showConfirmDialog(this,
                "Do you want to add a NEW CRIME record for " + currentCriminal.getName() + " (" + currentCriminal.getCriminalId() + ")?",
                "Add New Crime Incident",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            CrimeRecord newCrime = new CrimeRecord();
            newCrime.setReasonForArrest("New Offense Incident");
            newCrime.setImplementedSection("IPC Section ");
            newCrime.setCrimeLocation("Rajkot");
            newCrime.setCustodyStatus("In Judicial Custody");
            currentCriminal.addCrimeRecord(newCrime);

            int newIdx = currentCriminal.getCrimeRecords().size();
            cmbCrimeSelector.addItem("Incident #" + newIdx + ": New Offense Incident");
            cmbCrimeSelector.setSelectedIndex(newIdx - 1);

            toggleEditMode(true);
            txtReasonForArrest.requestFocus();
            txtReasonForArrest.selectAll();
        }
    }

    private void saveCriminalChanges() {
        if (currentCriminal == null) return;

        String name = txtName.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Criminal Name cannot be empty!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            txtName.requestFocus();
            return;
        }

        // Save personal details
        currentCriminal.setName(name);
        currentCriminal.setAge(txtAge.getText().trim());
        currentCriminal.setHeight(txtHeight.getText().trim());
        currentCriminal.setAddress(txtAddress.getText().trim());
        currentCriminal.setPhotoPath(currentPhotoPath);

        // Save current crime record details
        if (currentRecord != null) {
            currentRecord.setReasonForArrest(txtReasonForArrest.getText().trim());
            currentRecord.setImplementedSection(txtImplementedSection.getText().trim());
            currentRecord.setCrimeLocation(txtCrimeLocation.getText().trim());
            currentRecord.setPartnerNames(txtPartnerNames.getText().trim());
            currentRecord.setGangName(txtGangName.getText().trim());
            currentRecord.setImprisonmentFrom(txtImprisonmentFrom.getText().trim());
            currentRecord.setImprisonmentTo(txtImprisonmentTo.getText().trim());

            String selectedRankFull = (String) cmbOfficerRank.getSelectedItem();
            String rankCode = selectedRankFull != null ? selectedRankFull.split(" ")[0] : "PI";
            currentRecord.setOfficerRank(rankCode);
            currentRecord.setOfficerName(txtOfficerName.getText().trim());
            currentRecord.setCustodyStatus((String) cmbCustodyStatus.getSelectedItem());
        }

        // Persist to Database
        DatabaseManager.getInstance().saveCriminal(currentCriminal);

        toggleEditMode(false);
        JOptionPane.showMessageDialog(this,
                "Criminal Dossier for " + currentCriminal.getName() + " (" + currentCriminal.getCriminalId() + ")\nsuccessfully saved to Rajkot Crime Branch Database!",
                "Dossier Saved Successfully",
                JOptionPane.INFORMATION_MESSAGE);

        // Refresh selector names
        int selectedIdx = cmbCrimeSelector.getSelectedIndex();
        cmbCrimeSelector.removeAllItems();
        for (int i = 0; i < currentCriminal.getCrimeRecords().size(); i++) {
            CrimeRecord r = currentCriminal.getCrimeRecords().get(i);
            String label = "Incident #" + (i + 1) + ": " + (r.getReasonForArrest() == null || r.getReasonForArrest().isEmpty() ? "Crime Record" : r.getReasonForArrest());
            if (label.length() > 38) label = label.substring(0, 35) + "...";
            cmbCrimeSelector.addItem(label);
        }
        if (selectedIdx >= 0 && selectedIdx < cmbCrimeSelector.getItemCount()) {
            cmbCrimeSelector.setSelectedIndex(selectedIdx);
        }
    }

    private void selectNewPhoto() {
        JFileChooser chooser = new JFileChooser(new File("."));
        chooser.setDialogTitle("Select Criminal Mugshot Photo");
        int res = chooser.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            currentPhotoPath = file.getName();
            loadMugshot(file.getAbsolutePath());
        }
    }

    private void loadMugshot(String path) {
        if (path == null || path.trim().isEmpty()) {
            lblPhoto.setIcon(null);
            lblPhoto.setText("NO PHOTO AVAILABLE");
            lblPhoto.setForeground(PoliceTheme.TEXT_MUTED);
            return;
        }

        File imgFile = new File(path);
        if (!imgFile.exists()) {
            // Try relative to current directory
            imgFile = new File(".", path);
        }

        if (imgFile.exists()) {
            try {
                BufferedImage orig = ImageIO.read(imgFile);
                if (orig != null) {
                    Image scaled = orig.getScaledInstance(236, 236, Image.SCALE_SMOOTH);
                    lblPhoto.setIcon(new ImageIcon(scaled));
                    lblPhoto.setText("");
                    return;
                }
            } catch (Exception e) {
                System.err.println("Failed to read image: " + e.getMessage());
            }
        }

        lblPhoto.setIcon(null);
        lblPhoto.setText("<html><center>MUGSHOT<br><font size='3'>" + path + "</font></center></html>");
        lblPhoto.setForeground(PoliceTheme.GOLD);
    }
}
