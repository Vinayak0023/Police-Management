package com.police.ui;

import com.police.data.DatabaseManager;
import com.police.model.CrimeRecord;
import com.police.model.Criminal;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Page1Search extends JPanel {
    private final MainFrame mainFrame;
    private JTextField txtSearchName;
    private JTextField txtSearchId;
    private JTable tblRecords;
    private DefaultTableModel tableModel;
    private JLabel lblResultCount;

    public Page1Search(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(15, 15));
        setBackground(PoliceTheme.BG_DARK);
        setBorder(new EmptyBorder(15, 20, 15, 20));

        initUI();
    }

    private void initUI() {
        // 1. UPPER AREA: [ Criminal Records ] Box + Action Bar
        JPanel topContainer = new JPanel(new BorderLayout(10, 10));
        topContainer.setOpaque(false);

        // Boxed Header: (Criminal Records)
        JPanel headerBox = new JPanel(new BorderLayout());
        headerBox.setBackground(PoliceTheme.NAVY_DARK);
        headerBox.setBorder(new CompoundBorder(
                new LineBorder(PoliceTheme.GOLD, 2, true),
                new EmptyBorder(12, 20, 12, 20)
        ));

        JLabel lblBoxTitle = new JLabel("CRIMINAL RECORDS", SwingConstants.CENTER);
        lblBoxTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblBoxTitle.setForeground(PoliceTheme.GOLD);

        JLabel lblSub = new JLabel("RAJKOT CRIME BRANCH • CENTRAL INVESTIGATION DATABASE", SwingConstants.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(PoliceTheme.TEXT_MUTED);

        JPanel titleStack = new JPanel(new GridLayout(2, 1, 0, 4));
        titleStack.setOpaque(false);
        titleStack.add(lblBoxTitle);
        titleStack.add(lblSub);

        headerBox.add(titleStack, BorderLayout.CENTER);

        // Top Action Buttons Bar
        JPanel topButtonsBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        topButtonsBar.setOpaque(false);

        JButton btnTopAdd = PoliceTheme.createGoldButton("+ Add Criminal");
        btnTopAdd.addActionListener(e -> mainFrame.showPage3AddCriminal());

        JButton btnTopSearch = PoliceTheme.createPrimaryButton("🔍 Search Records");
        btnTopSearch.addActionListener(e -> executeSearch());

        JButton btnTopClear = PoliceTheme.createSecondaryButton("🧹 Clear");
        btnTopClear.addActionListener(e -> clearSearchFields());

        JButton btnTopAll = PoliceTheme.createSecondaryButton("📋 View All");
        btnTopAll.addActionListener(e -> {
            clearSearchFields();
            refreshRecordsTable();
        });

        topButtonsBar.add(btnTopAdd);
        topButtonsBar.add(btnTopSearch);
        topButtonsBar.add(btnTopClear);
        topButtonsBar.add(btnTopAll);

        topContainer.add(headerBox, BorderLayout.NORTH);
        topContainer.add(topButtonsBar, BorderLayout.SOUTH);

        add(topContainer, BorderLayout.NORTH);

        // 2. CENTER AREA: Search Form in Center of screen + Records Table
        JPanel centerContainer = new JPanel(new BorderLayout(15, 15));
        centerContainer.setOpaque(false);

        // Center Search Box
        JPanel searchCardWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchCardWrapper.setOpaque(false);

        JPanel searchCard = new JPanel();
        searchCard.setLayout(new BoxLayout(searchCard, BoxLayout.Y_AXIS));
        searchCard.setBackground(PoliceTheme.PANEL_BG);
        searchCard.setBorder(new CompoundBorder(
                new LineBorder(PoliceTheme.BORDER_COLOR, 1, true),
                new EmptyBorder(15, 25, 15, 25)
        ));
        searchCard.setPreferredSize(new Dimension(680, 180));

        JLabel lblSearchHeading = new JLabel("SEARCH CRIMINAL DOSSIER");
        lblSearchHeading.setFont(PoliceTheme.FONT_SECTION);
        lblSearchHeading.setForeground(PoliceTheme.GOLD);
        lblSearchHeading.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Fields Grid
        JPanel formGrid = new JPanel(new GridBagLayout());
        formGrid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: Criminal Name (Mandatory)
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        JLabel lblName = PoliceTheme.createRequiredFieldLabel("Criminal Name");
        lblName.setToolTipText("Mandatory field for name search");
        formGrid.add(lblName, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.7;
        txtSearchName = PoliceTheme.createTextField();
        txtSearchName.setColumns(20);
        formGrid.add(txtSearchName, gbc);

        // Row 1: Criminal ID
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        JLabel lblId = PoliceTheme.createFieldLabel("Criminal ID");
        formGrid.add(lblId, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.7;
        txtSearchId = PoliceTheme.createTextField();
        txtSearchId.setColumns(20);
        formGrid.add(txtSearchId, gbc);

        // Row 2: Search, Clear, Add Buttons in center
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 8));
        btnRow.setOpaque(false);

        JButton btnSearch = PoliceTheme.createGoldButton("🔍 Search");
        btnSearch.setPreferredSize(new Dimension(130, 36));
        btnSearch.addActionListener(e -> executeSearch());

        JButton btnClear = PoliceTheme.createSecondaryButton("Clear");
        btnClear.setPreferredSize(new Dimension(110, 36));
        btnClear.addActionListener(e -> clearSearchFields());

        JButton btnAddCriminal = PoliceTheme.createPrimaryButton("+ Add Criminal");
        btnAddCriminal.setPreferredSize(new Dimension(140, 36));
        btnAddCriminal.addActionListener(e -> mainFrame.showPage3AddCriminal());

        btnRow.add(btnSearch);
        btnRow.add(btnClear);
        btnRow.add(btnAddCriminal);

        searchCard.add(lblSearchHeading);
        searchCard.add(Box.createVerticalStrut(8));
        searchCard.add(formGrid);
        searchCard.add(Box.createVerticalStrut(6));
        searchCard.add(btnRow);

        searchCardWrapper.add(searchCard);
        centerContainer.add(searchCardWrapper, BorderLayout.NORTH);

        // 3. Lower Center: Records Table
        JPanel tablePanel = new JPanel(new BorderLayout(5, 5));
        tablePanel.setOpaque(false);
        tablePanel.setBorder(new CompoundBorder(
                PoliceTheme.createCardBorder("REGISTERED CRIMINAL RECORDS & LIVE DOSSIERS"),
                new EmptyBorder(5, 10, 10, 10)
        ));

        // Status / Count Bar
        JPanel tableInfoBar = new JPanel(new BorderLayout());
        tableInfoBar.setOpaque(false);
        lblResultCount = new JLabel("Showing all criminal records");
        lblResultCount.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblResultCount.setForeground(PoliceTheme.TEXT_MUTED);

        JLabel lblHint = new JLabel("Double-click any record to open full dossier (Page 2) ➔");
        lblHint.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblHint.setForeground(PoliceTheme.TEXT_GOLD);

        tableInfoBar.add(lblResultCount, BorderLayout.WEST);
        tableInfoBar.add(lblHint, BorderLayout.EAST);
        tableInfoBar.setBorder(new EmptyBorder(0, 0, 5, 0));

        tablePanel.add(tableInfoBar, BorderLayout.NORTH);

        // Table Model & Setup
        String[] columns = {"Criminal ID", "Criminal Name", "Age", "Height", "Implemented Section", "Crime Location", "Officer In Charge", "Custody Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblRecords = new JTable(tableModel);
        tblRecords.setFont(PoliceTheme.FONT_BODY);
        tblRecords.setRowHeight(32);
        tblRecords.setBackground(PoliceTheme.CARD_BG);
        tblRecords.setForeground(PoliceTheme.TEXT_WHITE);
        tblRecords.setGridColor(PoliceTheme.BORDER_COLOR);
        tblRecords.setSelectionBackground(PoliceTheme.NAVY_MID);
        tblRecords.setSelectionForeground(Color.WHITE);
        tblRecords.setShowGrid(true);

        // Header Styling
        JTableHeader header = tblRecords.getTableHeader();
        header.setFont(PoliceTheme.FONT_BUTTON);
        header.setBackground(PoliceTheme.NAVY_DARK);
        header.setForeground(PoliceTheme.GOLD);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));

        // Custom Cell Renderer for Status Column
        tblRecords.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
                String status = value == null ? "" : value.toString();
                if ("Released".equalsIgnoreCase(status)) {
                    lbl.setBackground(PoliceTheme.STATUS_RELEASED_BG);
                    lbl.setForeground(PoliceTheme.STATUS_RELEASED_FG);
                    lbl.setText("● RELEASED");
                } else {
                    lbl.setBackground(PoliceTheme.STATUS_CUSTODY_BG);
                    lbl.setForeground(PoliceTheme.STATUS_CUSTODY_FG);
                    lbl.setText("■ IN JUDICIAL CUSTODY");
                }
                lbl.setOpaque(true);
                return lbl;
            }
        });

        // Double click listener to open Page 2
        tblRecords.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    openSelectedCriminal();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblRecords);
        scrollPane.getViewport().setBackground(PoliceTheme.CARD_BG);
        scrollPane.setBorder(new LineBorder(PoliceTheme.BORDER_COLOR, 1));

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        centerContainer.add(tablePanel, BorderLayout.CENTER);

        add(centerContainer, BorderLayout.CENTER);

        refreshRecordsTable();
    }

    public void clearSearchFields() {
        txtSearchName.setText("");
        txtSearchId.setText("");
        txtSearchName.requestFocus();
        refreshRecordsTable();
    }

    public void refreshRecordsTable() {
        tableModel.setRowCount(0);
        List<Criminal> criminals = DatabaseManager.getInstance().getAllCriminals();
        for (Criminal c : criminals) {
            CrimeRecord latest = c.getLatestCrime();
            tableModel.addRow(new Object[]{
                    c.getCriminalId(),
                    c.getName(),
                    c.getAge(),
                    c.getHeight(),
                    latest != null ? latest.getImplementedSection() : "N/A",
                    latest != null ? latest.getCrimeLocation() : "N/A",
                    latest != null ? latest.getFullOfficerInCharge() : "N/A",
                    latest != null ? (latest.isReleased() ? "Released" : "In Judicial Custody") : "Unknown"
            });
        }
        lblResultCount.setText("Showing all " + criminals.size() + " registered criminals");
    }

    private void executeSearch() {
        String name = txtSearchName.getText().trim();
        String id = txtSearchId.getText().trim();

        // If table has a selected row and search inputs are empty, open selected
        int selectedRow = tblRecords.getSelectedRow();
        if (name.isEmpty() && id.isEmpty()) {
            if (selectedRow != -1) {
                openSelectedCriminal();
                return;
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please enter Criminal Name (Mandatory) or Criminal ID to search,\nor select a record from the table below.",
                        "Search Input Required",
                        JOptionPane.WARNING_MESSAGE);
                txtSearchName.requestFocus();
                return;
            }
        }

        List<Criminal> results = DatabaseManager.getInstance().searchCriminals(name, id);
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No criminal records found matching:\nName: \"" + name + "\"\nID: \"" + id + "\"",
                    "No Records Found",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // If exactly one match found, directly navigate to Page 2
        if (results.size() == 1) {
            mainFrame.showPage2CriminalDetail(results.get(0));
        } else {
            // Update table to show filtered results and select first
            tableModel.setRowCount(0);
            for (Criminal c : results) {
                CrimeRecord latest = c.getLatestCrime();
                tableModel.addRow(new Object[]{
                        c.getCriminalId(),
                        c.getName(),
                        c.getAge(),
                        c.getHeight(),
                        latest != null ? latest.getImplementedSection() : "N/A",
                        latest != null ? latest.getCrimeLocation() : "N/A",
                        latest != null ? latest.getFullOfficerInCharge() : "N/A",
                        latest != null ? (latest.isReleased() ? "Released" : "In Judicial Custody") : "Unknown"
                });
            }
            lblResultCount.setText("Found " + results.size() + " matching records. Double-click or select and press Search.");
            tblRecords.setRowSelectionInterval(0, 0);
            
            // Ask user if they want to view the first record or browse the table
            int choice = JOptionPane.showConfirmDialog(this,
                    "Found " + results.size() + " matching records.\nOpen profile of: " + results.get(0).getName() + " (" + results.get(0).getCriminalId() + ")?",
                    "Multiple Records Found",
                    JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                mainFrame.showPage2CriminalDetail(results.get(0));
            }
        }
    }

    private void openSelectedCriminal() {
        int row = tblRecords.getSelectedRow();
        if (row != -1) {
            String criminalId = (String) tblRecords.getValueAt(row, 0);
            Criminal criminal = DatabaseManager.getInstance().getCriminalById(criminalId);
            if (criminal != null) {
                mainFrame.showPage2CriminalDetail(criminal);
            }
        }
    }
}
