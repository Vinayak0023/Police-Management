package com.police.ui;

import com.police.data.DatabaseManager;
import com.police.model.FIR;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class Page4FIRManagement extends JPanel {
    private final MainFrame mainFrame;
    private JTable firTable;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private JComboBox<String> cmbStatusFilter;
    private JLabel lblTotalFIRs;

    public Page4FIRManagement(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(15, 15));
        setBackground(PoliceTheme.BG_DARK);
        setBorder(new EmptyBorder(15, 20, 15, 20));

        // Top Control Panel
        add(createTopPanel(), BorderLayout.NORTH);

        // Center Table Panel
        add(createTablePanel(), BorderLayout.CENTER);

        // Bottom Action Bar
        add(createBottomPanel(), BorderLayout.SOUTH);

        refreshTable();
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);

        // Header Title
        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 2));
        titlePanel.setOpaque(false);

        JLabel lblTitle = new JLabel("FIRST INFORMATION REPORT (FIR) MANAGEMENT");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(PoliceTheme.GOLD);

        lblTotalFIRs = new JLabel("Official Police Station FIR Repository & Investigation Records");
        lblTotalFIRs.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTotalFIRs.setForeground(PoliceTheme.TEXT_MUTED);

        titlePanel.add(lblTitle);
        titlePanel.add(lblTotalFIRs);

        // Search & Filter Bar
        JPanel filterBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        filterBar.setOpaque(false);

        JLabel lblSearch = new JLabel("Search:");
        lblSearch.setForeground(Color.WHITE);
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 12));

        txtSearch = new JTextField(15);
        txtSearch.setBackground(PoliceTheme.NAVY_MID);
        txtSearch.setForeground(Color.WHITE);
        txtSearch.setCaretColor(PoliceTheme.GOLD);
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PoliceTheme.BORDER_COLOR),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));

        cmbStatusFilter = new JComboBox<>(new String[]{"All Statuses", "Under Investigation", "Chargesheeted", "Closed"});
        cmbStatusFilter.setBackground(PoliceTheme.NAVY_MID);
        cmbStatusFilter.setForeground(Color.WHITE);

        JButton btnSearch = PoliceTheme.createSecondaryButton("🔍 Filter");
        btnSearch.addActionListener(e -> applyFilter());

        JButton btnClear = PoliceTheme.createSecondaryButton("Reset");
        btnClear.addActionListener(e -> {
            txtSearch.setText("");
            cmbStatusFilter.setSelectedIndex(0);
            refreshTable();
        });

        filterBar.add(lblSearch);
        filterBar.add(txtSearch);
        filterBar.add(cmbStatusFilter);
        filterBar.add(btnSearch);
        filterBar.add(btnClear);

        panel.add(titlePanel, BorderLayout.WEST);
        panel.add(filterBar, BorderLayout.EAST);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PoliceTheme.NAVY_DARK);
        panel.setBorder(BorderFactory.createLineBorder(PoliceTheme.BORDER_COLOR, 1));

        String[] cols = {"FIR No.", "Complainant", "Phone", "Crime Category", "IPC Section", "Location", "Inv. Officer", "Status", "Date"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        firTable = new JTable(tableModel);
        firTable.setBackground(PoliceTheme.NAVY_MID);
        firTable.setForeground(Color.WHITE);
        firTable.setGridColor(PoliceTheme.BORDER_COLOR);
        firTable.setRowHeight(32);
        firTable.setSelectionBackground(PoliceTheme.NAVY_LIGHT);
        firTable.setSelectionForeground(PoliceTheme.GOLD);
        firTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JTableHeader header = firTable.getTableHeader();
        header.setBackground(PoliceTheme.NAVY_DARK);
        header.setForeground(PoliceTheme.GOLD);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, PoliceTheme.GOLD));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        firTable.getColumnModel().getColumn(0).setCellRenderer(center);
        firTable.getColumnModel().getColumn(4).setCellRenderer(center);
        firTable.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                l.setHorizontalAlignment(SwingConstants.CENTER);
                String val = (value != null) ? value.toString() : "";
                if ("Closed".equalsIgnoreCase(val)) {
                    l.setForeground(new Color(100, 255, 120));
                } else if ("Chargesheeted".equalsIgnoreCase(val)) {
                    l.setForeground(new Color(255, 215, 0));
                } else {
                    l.setForeground(new Color(255, 130, 130));
                }
                return l;
            }
        });

        JScrollPane scroll = new JScrollPane(firTable);
        scroll.getViewport().setBackground(PoliceTheme.NAVY_MID);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
        panel.setOpaque(false);

        JButton btnNewFIR = PoliceTheme.createGoldButton("+ Register New FIR");
        btnNewFIR.addActionListener(e -> showRegisterFIRDialog());

        JButton btnUpdateStatus = PoliceTheme.createSecondaryButton("📝 Update Investigation Status");
        btnUpdateStatus.addActionListener(e -> updateSelectedFIRStatus());

        JButton btnViewDetails = PoliceTheme.createSecondaryButton("📋 View Full FIR Dossier");
        btnViewDetails.addActionListener(e -> viewSelectedFIRDetails());

        panel.add(btnViewDetails);
        panel.add(btnUpdateStatus);
        panel.add(btnNewFIR);

        return panel;
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        List<FIR> firs = DatabaseManager.getInstance().getAllFIRs();
        for (FIR f : firs) {
            tableModel.addRow(new Object[]{
                    f.getFirId(),
                    f.getComplainantName(),
                    f.getComplainantPhone(),
                    f.getCrimeCategory(),
                    f.getIpcSection(),
                    f.getIncidentLocation(),
                    f.getInvestigatingOfficer(),
                    f.getStatus(),
                    f.getRegistrationDate()
            });
        }
        lblTotalFIRs.setText("Official Police Station FIR Repository • Total Active Case Files: " + firs.size());
    }

    private void applyFilter() {
        String q = txtSearch.getText().trim();
        String st = (String) cmbStatusFilter.getSelectedItem();
        if ("All Statuses".equalsIgnoreCase(st)) st = "";

        List<FIR> results = DatabaseManager.getInstance().searchFIRs(q, st);
        tableModel.setRowCount(0);
        for (FIR f : results) {
            tableModel.addRow(new Object[]{
                    f.getFirId(),
                    f.getComplainantName(),
                    f.getComplainantPhone(),
                    f.getCrimeCategory(),
                    f.getIpcSection(),
                    f.getIncidentLocation(),
                    f.getInvestigatingOfficer(),
                    f.getStatus(),
                    f.getRegistrationDate()
            });
        }
    }

    private void showRegisterFIRDialog() {
        JDialog dlg = new JDialog(mainFrame, "Register New First Information Report (FIR)", true);
        dlg.setSize(600, 580);
        dlg.setLocationRelativeTo(mainFrame);
        dlg.setLayout(new BorderLayout(10, 10));
        dlg.getContentPane().setBackground(PoliceTheme.BG_DARK);

        JPanel formPanel = new JPanel(new GridLayout(10, 2, 10, 8));
        formPanel.setBackground(PoliceTheme.BG_DARK);
        formPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        String nextId = DatabaseManager.getInstance().generateNextFIRId();

        JTextField txtId = createStyledField(nextId, false);
        JTextField txtComp = createStyledField("", true);
        JTextField txtPhone = createStyledField("", true);
        JTextField txtAddr = createStyledField("", true);
        JComboBox<String> cmbCat = new JComboBox<>(new String[]{
                "Theft & Burglary", "Armed Robbery & Dacoity", "Assault & Grievous Hurt",
                "Cyber Crime & Financial Fraud", "Extortion & Criminal Intimidation", "Homicide & Attempt to Murder"
        });
        cmbCat.setBackground(PoliceTheme.NAVY_MID);
        cmbCat.setForeground(Color.WHITE);

        JTextField txtIpc = createStyledField("IPC 379 / IPC 420", true);
        JTextField txtLoc = createStyledField("Rajkot", true);
        JTextField txtOfficer = createStyledField("PI M. S. Gohil", true);
        JTextField txtDesc = createStyledField("Complaint incident details...", true);
        JComboBox<String> cmbStatus = new JComboBox<>(new String[]{"Under Investigation", "Chargesheeted", "Closed"});
        cmbStatus.setBackground(PoliceTheme.NAVY_MID);
        cmbStatus.setForeground(Color.WHITE);

        formPanel.add(createFormLabel("FIR Number:"));
        formPanel.add(txtId);
        formPanel.add(createFormLabel("Complainant Name:"));
        formPanel.add(txtComp);
        formPanel.add(createFormLabel("Contact Phone:"));
        formPanel.add(txtPhone);
        formPanel.add(createFormLabel("Complainant Address:"));
        formPanel.add(txtAddr);
        formPanel.add(createFormLabel("Crime Category:"));
        formPanel.add(cmbCat);
        formPanel.add(createFormLabel("IPC Section(s):"));
        formPanel.add(txtIpc);
        formPanel.add(createFormLabel("Incident Location:"));
        formPanel.add(txtLoc);
        formPanel.add(createFormLabel("Investigating Officer:"));
        formPanel.add(txtOfficer);
        formPanel.add(createFormLabel("Brief Description:"));
        formPanel.add(txtDesc);
        formPanel.add(createFormLabel("Initial Status:"));
        formPanel.add(cmbStatus);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnPanel.setOpaque(false);

        JButton btnSave = PoliceTheme.createGoldButton("Submit FIR");
        btnSave.addActionListener(e -> {
            String compName = txtComp.getText().trim();
            if (compName.isEmpty()) {
                JOptionPane.showMessageDialog(dlg, "Complainant Name is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            FIR newFir = new FIR(
                    nextId,
                    compName,
                    txtPhone.getText().trim(),
                    txtAddr.getText().trim(),
                    (String) cmbCat.getSelectedItem(),
                    txtIpc.getText().trim(),
                    "Today",
                    txtLoc.getText().trim(),
                    "Under probe",
                    txtDesc.getText().trim(),
                    "Rajkot Crime Branch HQ",
                    txtOfficer.getText().trim(),
                    (String) cmbStatus.getSelectedItem()
            );

            DatabaseManager.getInstance().saveFIR(newFir);
            refreshTable();
            dlg.dispose();
            JOptionPane.showMessageDialog(mainFrame, "FIR " + nextId + " logged successfully into system records!", "FIR Registered", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton btnCancel = PoliceTheme.createSecondaryButton("Cancel");
        btnCancel.addActionListener(e -> dlg.dispose());

        btnPanel.add(btnCancel);
        btnPanel.add(btnSave);

        dlg.add(formPanel, BorderLayout.CENTER);
        dlg.add(btnPanel, BorderLayout.SOUTH);
        dlg.setVisible(true);
    }

    private void updateSelectedFIRStatus() {
        int row = firTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an FIR from the table to update status.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String firId = (String) tableModel.getValueAt(row, 0);
        FIR fir = DatabaseManager.getInstance().getFIRById(firId);
        if (fir == null) return;

        String[] statuses = {"Under Investigation", "Chargesheeted", "Closed"};
        String chosen = (String) JOptionPane.showInputDialog(
                this,
                "Select updated investigation status for FIR: " + firId,
                "Update FIR Status",
                JOptionPane.QUESTION_MESSAGE,
                null,
                statuses,
                fir.getStatus()
        );

        if (chosen != null && !chosen.isEmpty()) {
            fir.setStatus(chosen);
            DatabaseManager.getInstance().saveFIR(fir);
            refreshTable();
            JOptionPane.showMessageDialog(this, "FIR " + firId + " status updated to: " + chosen, "Status Updated", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void viewSelectedFIRDetails() {
        int row = firTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an FIR from the table to view dossier.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String firId = (String) tableModel.getValueAt(row, 0);
        FIR fir = DatabaseManager.getInstance().getFIRById(firId);
        if (fir == null) return;

        String details = "========================================================\n" +
                "       RAJKOT CRIME BRANCH - CASE DOSSIER\n" +
                "========================================================\n\n" +
                "FIR Number          : " + fir.getFirId() + "\n" +
                "Police Station      : " + fir.getPoliceStation() + "\n" +
                "Registration Date   : " + fir.getRegistrationDate() + "\n" +
                "Status              : " + fir.getStatus() + "\n\n" +
                "Complainant Name    : " + fir.getComplainantName() + "\n" +
                "Contact Phone       : " + fir.getComplainantPhone() + "\n" +
                "Address             : " + fir.getComplainantAddress() + "\n\n" +
                "Crime Category      : " + fir.getCrimeCategory() + "\n" +
                "IPC Section(s)      : " + fir.getIpcSection() + "\n" +
                "Incident Date       : " + fir.getIncidentDate() + "\n" +
                "Incident Location   : " + fir.getIncidentLocation() + "\n" +
                "Investigating IO    : " + fir.getInvestigatingOfficer() + "\n\n" +
                "Suspect Details     : " + fir.getSuspectDetails() + "\n\n" +
                "Detailed Incident Description:\n" + fir.getDescription() + "\n";

        JTextArea txt = new JTextArea(details);
        txt.setBackground(PoliceTheme.NAVY_MID);
        txt.setForeground(Color.WHITE);
        txt.setFont(new Font("Consolas", Font.PLAIN, 13));
        txt.setEditable(false);
        txt.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane sp = new JScrollPane(txt);
        sp.setPreferredSize(new Dimension(550, 420));

        JOptionPane.showMessageDialog(this, sp, "FIR Details: " + fir.getFirId(), JOptionPane.PLAIN_MESSAGE);
    }

    private JLabel createFormLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(PoliceTheme.GOLD);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return l;
    }

    private JTextField createStyledField(String text, boolean editable) {
        JTextField tf = new JTextField(text);
        tf.setEditable(editable);
        tf.setBackground(PoliceTheme.NAVY_MID);
        tf.setForeground(editable ? Color.WHITE : PoliceTheme.TEXT_MUTED);
        tf.setCaretColor(PoliceTheme.GOLD);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PoliceTheme.BORDER_COLOR),
                BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        return tf;
    }
}
