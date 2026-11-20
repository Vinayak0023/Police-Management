package com.police.ui;

import com.police.data.DatabaseManager;
import com.police.model.CrimeRecord;
import com.police.model.Criminal;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDate;

public class Page3AddCriminal extends JPanel {
    private final MainFrame mainFrame;

    // Fields
    private JTextField txtName;
    private JTextField txtId;
    private JTextField txtAge;
    private JTextField txtHeight;
    private JTextArea txtAddress;
    private JTextField txtLocationCrime;
    private JComboBox<String> cmbOfficerRank;
    private JTextField txtOfficerName;

    // Initial Crime details
    private JTextField txtReasonForArrest;
    private JTextField txtImplementedSection;
    private JTextField txtGangName;
    private JTextField txtPartnerNames;
    private JTextField txtImprisonmentFrom;
    private JTextField txtImprisonmentTo;
    private JComboBox<String> cmbCustodyStatus;

    // Photo
    private JLabel lblPhotoPreview;
    private String selectedPhotoPath = "";
    private JComboBox<String> cmbAvailablePhotos;

    private static final String[] OFFICER_RANKS = {
            "PI (Police Inspector)",
            "PSI (Police Sub-Inspector)",
            "SI (Sub-Inspector)",
            "DSP (Deputy Superintendent)",
            "SP (Superintendent of Police)",
            "SSP (Senior Superintendent)",
            "IGP (Inspector General)",
            "ADGP (Addl Director General)",
            "DGP (Director General of Police)"
    };

    public Page3AddCriminal(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(12, 12));
        setBackground(PoliceTheme.BG_DARK);
        setBorder(new EmptyBorder(12, 20, 12, 20));

        initUI();
    }

    private void initUI() {
        // 1. TOP HEADER with Photo on Top Right
        JPanel topContainer = new JPanel(new BorderLayout(10, 10));
        topContainer.setOpaque(false);

        // Header Title Box
        JPanel headerBox = new JPanel(new BorderLayout());
        headerBox.setBackground(PoliceTheme.NAVY_DARK);
        headerBox.setBorder(new CompoundBorder(
                new LineBorder(PoliceTheme.GOLD, 2, true),
                new EmptyBorder(10, 16, 10, 16)
        ));

        JLabel lblTitle = new JLabel("NEW CRIMINAL REGISTRATION (FIRST-TIME OFFENDER)", SwingConstants.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(PoliceTheme.GOLD);

        JLabel lblSub = new JLabel("Rajkot Crime Branch • Central Identification & Booking Portal", SwingConstants.LEFT);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(PoliceTheme.TEXT_MUTED);

        JPanel titleStack = new JPanel(new GridLayout(2, 1, 0, 2));
        titleStack.setOpaque(false);
        titleStack.add(lblTitle);
        titleStack.add(lblSub);

        headerBox.add(titleStack, BorderLayout.WEST);

        JButton btnCancelTop = PoliceTheme.createSecondaryButton("⬅ Back to Search");
        btnCancelTop.addActionListener(e -> mainFrame.showPage1Search());
        headerBox.add(btnCancelTop, BorderLayout.EAST);

        topContainer.add(headerBox, BorderLayout.CENTER);
        add(topContainer, BorderLayout.NORTH);

        // 2. CENTER CONTENT (Split into Form on Left and Photo Insert on Right)
        JPanel centerPanel = new JPanel(new BorderLayout(16, 0));
        centerPanel.setOpaque(false);

        // --- LEFT FORM: CRIMINAL DETAILS ---
        JPanel formContainer = new JPanel();
        formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
        formContainer.setOpaque(false);

        // Section A: Personal & Crime Information
        JPanel personalCard = new JPanel(new GridBagLayout());
        personalCard.setBackground(PoliceTheme.PANEL_BG);
        personalCard.setBorder(new CompoundBorder(
                PoliceTheme.createCardBorder("CRIMINAL DOSSIER ENTRY"),
                new EmptyBorder(10, 14, 12, 14)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 6, 5, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: Name (Mandatory) and ID
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.20;
        personalCard.add(PoliceTheme.createRequiredFieldLabel("Criminal Name:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.30;
        txtName = PoliceTheme.createTextField();
        personalCard.add(txtName, gbc);

        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0.20;
        personalCard.add(PoliceTheme.createRequiredFieldLabel("Criminal ID:"), gbc);
        gbc.gridx = 3; gbc.gridy = 0; gbc.weightx = 0.30;
        txtId = PoliceTheme.createTextField();
        personalCard.add(txtId, gbc);

        // Row 1: Age and Height
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.20;
        personalCard.add(PoliceTheme.createFieldLabel("Age:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.30;
        txtAge = PoliceTheme.createTextField();
        personalCard.add(txtAge, gbc);

        gbc.gridx = 2; gbc.gridy = 1; gbc.weightx = 0.20;
        personalCard.add(PoliceTheme.createFieldLabel("Height:"), gbc);
        gbc.gridx = 3; gbc.gridy = 1; gbc.weightx = 0.30;
        txtHeight = PoliceTheme.createTextField();
        personalCard.add(txtHeight, gbc);

        // Row 2: Address
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.20;
        personalCard.add(PoliceTheme.createFieldLabel("Address:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 3; gbc.weightx = 0.80;
        txtAddress = PoliceTheme.createTextArea(2, 30);
        JScrollPane addrScroll = new JScrollPane(txtAddress);
        addrScroll.setPreferredSize(new Dimension(addrScroll.getPreferredSize().width, 48));
        personalCard.add(addrScroll, gbc);
        gbc.gridwidth = 1;

        // Row 3: Location's Crime
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.20;
        personalCard.add(PoliceTheme.createRequiredFieldLabel("Location's Crime:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 3; gbc.weightx = 0.80;
        txtLocationCrime = PoliceTheme.createTextField();
        txtLocationCrime.setToolTipText("Exact crime occurrence spot / police station jurisdiction");
        personalCard.add(txtLocationCrime, gbc);
        gbc.gridwidth = 1;

        // Row 4: Incharge Officer (Post + Name)
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.20;
        personalCard.add(PoliceTheme.createRequiredFieldLabel("Incharge Officer:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 0.30;
        cmbOfficerRank = PoliceTheme.createComboBox(OFFICER_RANKS);
        personalCard.add(cmbOfficerRank, gbc);

        gbc.gridx = 2; gbc.gridy = 4; gbc.weightx = 0.20;
        personalCard.add(PoliceTheme.createFieldLabel("Officer Name:"), gbc);
        gbc.gridx = 3; gbc.gridy = 4; gbc.weightx = 0.30;
        txtOfficerName = PoliceTheme.createTextField();
        personalCard.add(txtOfficerName, gbc);

        formContainer.add(personalCard);
        formContainer.add(Box.createVerticalStrut(10));

        // Section B: Offense Details
        JPanel offenseCard = new JPanel(new GridBagLayout());
        offenseCard.setBackground(PoliceTheme.PANEL_BG);
        offenseCard.setBorder(new CompoundBorder(
                PoliceTheme.createCardBorder("FIRST-TIME CRIME & LEGAL SECTIONS"),
                new EmptyBorder(10, 14, 12, 14)
        ));

        GridBagConstraints oGbc = new GridBagConstraints();
        oGbc.insets = new Insets(5, 6, 5, 6);
        oGbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: Reason for Arrest & Implemented Section
        oGbc.gridx = 0; oGbc.gridy = 0; oGbc.weightx = 0.20;
        offenseCard.add(PoliceTheme.createFieldLabel("Reason for Arrest:"), oGbc);
        oGbc.gridx = 1; oGbc.gridy = 0; oGbc.weightx = 0.30;
        txtReasonForArrest = PoliceTheme.createTextField();
        offenseCard.add(txtReasonForArrest, oGbc);

        oGbc.gridx = 2; oGbc.gridy = 0; oGbc.weightx = 0.20;
        offenseCard.add(PoliceTheme.createFieldLabel("Implemented Section:"), oGbc);
        oGbc.gridx = 3; oGbc.gridy = 0; oGbc.weightx = 0.30;
        txtImplementedSection = PoliceTheme.createTextField();
        txtImplementedSection.setToolTipText("e.g. IPC 392, 420, 302 / Bharatiya Nyaya Sanhita");
        offenseCard.add(txtImplementedSection, oGbc);

        // Row 1: Gang Name & Partner Names
        oGbc.gridx = 0; oGbc.gridy = 1; oGbc.weightx = 0.20;
        offenseCard.add(PoliceTheme.createFieldLabel("Gang Name (if group):"), oGbc);
        oGbc.gridx = 1; oGbc.gridy = 1; oGbc.weightx = 0.30;
        txtGangName = PoliceTheme.createTextField();
        offenseCard.add(txtGangName, oGbc);

        oGbc.gridx = 2; oGbc.gridy = 1; oGbc.weightx = 0.20;
        offenseCard.add(PoliceTheme.createFieldLabel("Partner(s) Involved:"), oGbc);
        oGbc.gridx = 3; oGbc.gridy = 1; oGbc.weightx = 0.30;
        txtPartnerNames = PoliceTheme.createTextField();
        offenseCard.add(txtPartnerNames, oGbc);

        // Row 2: Imprisonment From & To
        oGbc.gridx = 0; oGbc.gridy = 2; oGbc.weightx = 0.20;
        offenseCard.add(PoliceTheme.createFieldLabel("Imprisonment (From):"), oGbc);
        oGbc.gridx = 1; oGbc.gridy = 2; oGbc.weightx = 0.30;
        txtImprisonmentFrom = PoliceTheme.createTextField();
        txtImprisonmentFrom.setToolTipText("DD/MM/YYYY");
        offenseCard.add(txtImprisonmentFrom, oGbc);

        oGbc.gridx = 2; oGbc.gridy = 2; oGbc.weightx = 0.20;
        offenseCard.add(PoliceTheme.createFieldLabel("Imprisonment (To):"), oGbc);
        oGbc.gridx = 3; oGbc.gridy = 2; oGbc.weightx = 0.30;
        txtImprisonmentTo = PoliceTheme.createTextField();
        txtImprisonmentTo.setToolTipText("DD/MM/YYYY");
        offenseCard.add(txtImprisonmentTo, oGbc);

        // Row 3: Custody Status
        oGbc.gridx = 0; oGbc.gridy = 3; oGbc.weightx = 0.20;
        offenseCard.add(PoliceTheme.createFieldLabel("Custody Status:"), oGbc);
        oGbc.gridx = 1; oGbc.gridy = 3; oGbc.gridwidth = 3; oGbc.weightx = 0.80;
        cmbCustodyStatus = PoliceTheme.createComboBox(new String[]{"In Judicial Custody", "Released"});
        offenseCard.add(cmbCustodyStatus, oGbc);
        oGbc.gridwidth = 1;

        formContainer.add(offenseCard);
        centerPanel.add(formContainer, BorderLayout.CENTER);

        // --- TOP RIGHT SIDE: PHOTO INSERTION AREA ---
        JPanel rightPhotoPanel = new JPanel();
        rightPhotoPanel.setLayout(new BoxLayout(rightPhotoPanel, BoxLayout.Y_AXIS));
        rightPhotoPanel.setBackground(PoliceTheme.PANEL_BG);
        rightPhotoPanel.setPreferredSize(new Dimension(300, 520));
        rightPhotoPanel.setBorder(new CompoundBorder(
                PoliceTheme.createCardBorder("INSERT PHOTO (TOP RIGHT)"),
                new EmptyBorder(10, 14, 14, 14)
        ));

        lblPhotoPreview = new JLabel("", SwingConstants.CENTER);
        lblPhotoPreview.setPreferredSize(new Dimension(230, 230));
        lblPhotoPreview.setMaximumSize(new Dimension(230, 230));
        lblPhotoPreview.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPhotoPreview.setBorder(new LineBorder(PoliceTheme.GOLD, 2));
        lblPhotoPreview.setBackground(new Color(15, 23, 42));
        lblPhotoPreview.setOpaque(true);

        // Quick photo dropdown from directory + Browse button
        JLabel lblQuickPhoto = new JLabel("Select Photo from Folder:", SwingConstants.CENTER);
        lblQuickPhoto.setFont(PoliceTheme.FONT_LABEL);
        lblQuickPhoto.setForeground(PoliceTheme.TEXT_MUTED);
        lblQuickPhoto.setAlignmentX(Component.CENTER_ALIGNMENT);

        cmbAvailablePhotos = PoliceTheme.createComboBox(getAvailablePhotosList());
        cmbAvailablePhotos.setAlignmentX(Component.CENTER_ALIGNMENT);
        cmbAvailablePhotos.setMaximumSize(new Dimension(260, 32));
        cmbAvailablePhotos.addActionListener(e -> {
            String selected = (String) cmbAvailablePhotos.getSelectedItem();
            if (selected != null && !selected.startsWith("--")) {
                selectedPhotoPath = selected;
                previewPhoto(selected);
            }
        });

        JButton btnBrowsePhoto = PoliceTheme.createGoldButton("📁 Browse Photo File...");
        btnBrowsePhoto.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBrowsePhoto.setMaximumSize(new Dimension(240, 36));
        btnBrowsePhoto.addActionListener(e -> browsePhotoFile());

        JLabel lblNotice = new JLabel("<html><center><font color='#FEFE90'>Mugshot Image Insertion</font><br><font color='#94A3B8'>Standard biometric portrait photograph for police crime records database</font></center></html>", SwingConstants.CENTER);
        lblNotice.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblNotice.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblNotice.setBorder(new EmptyBorder(8, 0, 0, 0));

        rightPhotoPanel.add(lblPhotoPreview);
        rightPhotoPanel.add(Box.createVerticalStrut(10));
        rightPhotoPanel.add(lblQuickPhoto);
        rightPhotoPanel.add(Box.createVerticalStrut(4));
        rightPhotoPanel.add(cmbAvailablePhotos);
        rightPhotoPanel.add(Box.createVerticalStrut(8));
        rightPhotoPanel.add(btnBrowsePhoto);
        rightPhotoPanel.add(Box.createVerticalStrut(8));
        rightPhotoPanel.add(lblNotice);

        centerPanel.add(rightPhotoPanel, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);

        // 3. BOTTOM ACTION BAR: Save, Clear, Cancel
        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 8));
        bottomBar.setOpaque(false);

        JButton btnSave = PoliceTheme.createGoldButton("💾 Save Criminal Record");
        btnSave.setPreferredSize(new Dimension(240, 42));
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.addActionListener(e -> saveNewCriminal());

        JButton btnClear = PoliceTheme.createSecondaryButton("🧹 Clear Form");
        btnClear.setPreferredSize(new Dimension(140, 42));
        btnClear.addActionListener(e -> resetForm());

        JButton btnCancel = PoliceTheme.createSecondaryButton("⬅ Back to Search");
        btnCancel.setPreferredSize(new Dimension(160, 42));
        btnCancel.addActionListener(e -> mainFrame.showPage1Search());

        bottomBar.add(btnSave);
        bottomBar.add(btnClear);
        bottomBar.add(btnCancel);

        add(bottomBar, BorderLayout.SOUTH);

        resetForm();
    }

    private String[] getAvailablePhotosList() {
        File dir = new File(".");
        File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".jpeg"));
        if (files == null || files.length == 0) {
            return new String[]{"-- No image files found --"};
        }
        String[] names = new String[files.length + 1];
        names[0] = "-- Choose mugshot file --";
        for (int i = 0; i < files.length; i++) {
            names[i + 1] = files[i].getName();
        }
        return names;
    }

    public void resetForm() {
        String nextId = DatabaseManager.getInstance().generateNextCriminalId();
        txtId.setText(nextId);
        txtName.setText("");
        txtAge.setText("");
        txtHeight.setText("");
        txtAddress.setText("");
        txtLocationCrime.setText("");
        txtOfficerName.setText("");
        txtReasonForArrest.setText("");
        txtImplementedSection.setText("");
        txtGangName.setText("");
        txtPartnerNames.setText("");

        // Default dates
        LocalDate today = LocalDate.now();
        txtImprisonmentFrom.setText(today.format(CrimeRecord.DATE_FORMATTER));
        txtImprisonmentTo.setText(today.plusYears(3).format(CrimeRecord.DATE_FORMATTER));

        cmbOfficerRank.setSelectedIndex(0);
        cmbCustodyStatus.setSelectedItem("In Judicial Custody");

        // Refresh photo choices
        cmbAvailablePhotos.setModel(new DefaultComboBoxModel<>(getAvailablePhotosList()));
        if (cmbAvailablePhotos.getItemCount() > 1) {
            cmbAvailablePhotos.setSelectedIndex(1);
            selectedPhotoPath = (String) cmbAvailablePhotos.getItemAt(1);
            previewPhoto(selectedPhotoPath);
        } else {
            selectedPhotoPath = "";
            previewPhoto("");
        }

        txtName.requestFocus();
    }

    private void browsePhotoFile() {
        JFileChooser chooser = new JFileChooser(new File("."));
        chooser.setDialogTitle("Select Mugshot Image");
        int res = chooser.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            selectedPhotoPath = f.getName();
            previewPhoto(f.getAbsolutePath());
        }
    }

    private void previewPhoto(String path) {
        if (path == null || path.trim().isEmpty() || path.startsWith("--")) {
            lblPhotoPreview.setIcon(null);
            lblPhotoPreview.setText("<html><center>NO PHOTO SELECTED<br><font size='2'>Click Browse to insert photo</font></center></html>");
            lblPhotoPreview.setForeground(PoliceTheme.TEXT_MUTED);
            return;
        }

        File f = new File(path);
        if (!f.exists()) {
            f = new File(".", path);
        }

        if (f.exists()) {
            try {
                BufferedImage img = ImageIO.read(f);
                if (img != null) {
                    Image scaled = img.getScaledInstance(226, 226, Image.SCALE_SMOOTH);
                    lblPhotoPreview.setIcon(new ImageIcon(scaled));
                    lblPhotoPreview.setText("");
                    return;
                }
            } catch (Exception e) {
                System.err.println("Error reading preview: " + e.getMessage());
            }
        }

        lblPhotoPreview.setIcon(null);
        lblPhotoPreview.setText("<html><center>Photo: " + path + "</center></html>");
        lblPhotoPreview.setForeground(PoliceTheme.GOLD);
    }

    private void saveNewCriminal() {
        String name = txtName.getText().trim();
        String id = txtId.getText().trim();
        String locationCrime = txtLocationCrime.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Criminal Name is mandatory!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            txtName.requestFocus();
            return;
        }

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Criminal ID is mandatory!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            txtId.requestFocus();
            return;
        }

        if (DatabaseManager.getInstance().getCriminalById(id) != null) {
            JOptionPane.showMessageDialog(this, "Criminal ID \"" + id + "\" already exists! Please use a unique ID.", "Duplicate ID Error", JOptionPane.ERROR_MESSAGE);
            txtId.requestFocus();
            return;
        }

        // Create Criminal object
        Criminal newCriminal = new Criminal(
                id,
                name,
                txtAge.getText().trim(),
                txtHeight.getText().trim(),
                txtAddress.getText().trim(),
                selectedPhotoPath
        );

        // Extract rank code
        String selectedRank = (String) cmbOfficerRank.getSelectedItem();
        String rankCode = selectedRank != null ? selectedRank.split(" ")[0] : "PI";

        // Create first CrimeRecord
        CrimeRecord record = new CrimeRecord(
                "CR-" + System.currentTimeMillis() % 100000,
                txtReasonForArrest.getText().trim().isEmpty() ? "Arrested at " + locationCrime : txtReasonForArrest.getText().trim(),
                txtImplementedSection.getText().trim(),
                locationCrime,
                txtPartnerNames.getText().trim(),
                txtGangName.getText().trim(),
                txtImprisonmentFrom.getText().trim(),
                txtImprisonmentTo.getText().trim(),
                rankCode,
                txtOfficerName.getText().trim(),
                (String) cmbCustodyStatus.getSelectedItem()
        );

        newCriminal.addCrimeRecord(record);

        // Save to Database
        DatabaseManager.getInstance().saveCriminal(newCriminal);

        JOptionPane.showMessageDialog(this,
                "New Criminal Registered Successfully!\n\nName: " + name + "\nID: " + id + "\nCrime Location: " + locationCrime + "\nOfficer: " + rankCode + " " + txtOfficerName.getText().trim(),
                "Registration Successful",
                JOptionPane.INFORMATION_MESSAGE);

        // Open Page 2 with the newly created criminal
        mainFrame.showPage2CriminalDetail(newCriminal);
    }
}
