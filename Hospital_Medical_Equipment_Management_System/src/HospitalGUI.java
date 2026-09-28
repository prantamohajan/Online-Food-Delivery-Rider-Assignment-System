import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HospitalGUI extends JFrame {
    private Hospital hospital;
    private MaintenanceScheduler scheduler;
    private JTable equipmentTable;
    private DefaultTableModel tableModel;
    private JTextArea logArea;
    private JLabel totalUnitsLabel;
    private JLabel operationalLabel;
    private JLabel maintenanceLabel;

    public HospitalGUI() {
        setTitle("Hospital Medical Equipment Management System - CSE 1115");
        setSize(1150, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initBackend();
        initComponents();
        refreshTable();
        appendLog("System initialized. Connected to Premier Central Hospital Inventory.");
    }

    private void initBackend() {
        hospital = new Hospital("Premier Central Hospital");
        scheduler = new MaintenanceScheduler();

        PatientMonitor pm = new PatientMonitor("EQ-PM-01", "ICU Bedside Monitor", "Philips", "IntelliVue MX800");
        Ventilator vn = new Ventilator("EQ-VN-02", "Critical Care Ventilator", "Dräger", "Evita V800");
        InfusionPump ip = new InfusionPump("EQ-IP-03", "Smart Infusion Pump", "Baxter", "Sigma Spectrum");
        ECGMachine ecg = new ECGMachine("EQ-ECG-04", "12-Lead Diagnostic ECG", "GE Healthcare", "MAC 2000", 12);
        PortableUltrasound us = new PortableUltrasound("EQ-US-05", "Point-of-Care Ultrasound", "Mindray", "M9", "Convex");

        hospital.addEquipment(pm);
        hospital.addEquipment(vn);
        hospital.addEquipment(ip);
        hospital.addEquipment(ecg);
        hospital.addEquipment(us);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        mainPanel.setBackground(new Color(245, 247, 250));

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(new Color(24, 76, 120));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("HOSPITAL MEDICAL EQUIPMENT MANAGEMENT SYSTEM");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        JLabel subTitleLabel = new JLabel("Object-Oriented Logistics & Safety Dashboard | Dept. of CSE, Premier University");
        subTitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subTitleLabel.setForeground(new Color(210, 230, 250));

        JPanel titleContainer = new JPanel(new GridLayout(2, 1));
        titleContainer.setOpaque(false);
        titleContainer.add(titleLabel);
        titleContainer.add(subTitleLabel);

        // Quick Stats Badges
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        statsPanel.setOpaque(false);

        totalUnitsLabel = createStatBadge("Total Devices: 5", new Color(41, 128, 185));
        operationalLabel = createStatBadge("Operational: 5", new Color(39, 174, 96));
        maintenanceLabel = createStatBadge("Maintenance: 0", new Color(211, 84, 0));

        statsPanel.add(totalUnitsLabel);
        statsPanel.add(operationalLabel);
        statsPanel.add(maintenanceLabel);

        headerPanel.add(titleContainer, BorderLayout.WEST);
        headerPanel.add(statsPanel, BorderLayout.EAST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Center Table
        String[] columns = {"Equipment ID", "Device Name", "Category", "Manufacturer & Model", "Status", "Last Service"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        equipmentTable = new JTable(tableModel);
        equipmentTable.setRowHeight(28);
        equipmentTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        equipmentTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        equipmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Color coding for status column
        equipmentTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                if (value != null) {
                    String status = value.toString();
                    if ("OPERATIONAL".equalsIgnoreCase(status)) {
                        c.setForeground(new Color(39, 174, 96));
                        setFont(getFont().deriveFont(Font.BOLD));
                    } else if ("UNDER_MAINTENANCE".equalsIgnoreCase(status)) {
                        c.setForeground(new Color(211, 84, 0));
                        setFont(getFont().deriveFont(Font.BOLD));
                    } else {
                        c.setForeground(Color.RED);
                    }
                }
                return c;
            }
        });

        JScrollPane tableScroll = new JScrollPane(equipmentTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Live Equipment Inventory"));

        // Action Toolbar
        JPanel actionToolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        actionToolbar.setBackground(Color.WHITE);
        actionToolbar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(5, 5, 5, 5)));

        JButton btnStart = createStyledButton("▶ Start Operation", new Color(46, 204, 113));
        JButton btnStop = createStyledButton("⏹ Stop Operation", new Color(231, 76, 60));
        JButton btnAction = createStyledButton("⚙ Device Action", new Color(52, 152, 219));
        JButton btnSchedule = createStyledButton("⏱ Schedule Maintenance", new Color(243, 156, 18));
        JButton btnService = createStyledButton("✔ Perform Service", new Color(26, 188, 156));
        JButton btnLog = createStyledButton("📜 View Service History", new Color(142, 68, 173));
        JButton btnAdd = createStyledButton("➕ Add Device", new Color(52, 73, 94));

        actionToolbar.add(btnStart);
        actionToolbar.add(btnStop);
        actionToolbar.add(btnAction);
        actionToolbar.add(btnSchedule);
        actionToolbar.add(btnService);
        actionToolbar.add(btnLog);
        actionToolbar.add(btnAdd);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 8));
        centerPanel.setOpaque(false);
        centerPanel.add(tableScroll, BorderLayout.CENTER);
        centerPanel.add(actionToolbar, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom Console Log
        logArea = new JTextArea(7, 20);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        logArea.setEditable(false);
        logArea.setBackground(new Color(30, 30, 30));
        logArea.setForeground(new Color(80, 250, 123));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY), "System Telemetry & Audit Logs"));

        mainPanel.add(logScroll, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        // Event Listeners
        btnStart.addActionListener(e -> handleStart());
        btnStop.addActionListener(e -> handleStop());
        btnAction.addActionListener(e -> handleDeviceAction());
        btnSchedule.addActionListener(e -> handleSchedule());
        btnService.addActionListener(e -> handleService());
        btnLog.addActionListener(e -> handleViewLog());
        btnAdd.addActionListener(e -> handleAddDevice());
    }

    private JLabel createStatBadge(String text, Color bg) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setOpaque(true);
        lbl.setBackground(bg);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setBorder(new EmptyBorder(5, 12, 5, 12));
        return lbl;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private MedicalEquipment getSelectedEquipment() {
        int row = equipmentTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an equipment from the table first.", "Selection Needed", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        String id = (String) tableModel.getValueAt(row, 0);
        for (MedicalEquipment eq : hospital.getEquipmentList()) {
            if (eq.getEquipmentId().equals(id)) return eq;
        }
        return null;
    }

    private void handleStart() {
        MedicalEquipment eq = getSelectedEquipment();
        if (eq == null) return;
        eq.startOperation();
        refreshTable();
        appendLog("[Polymorphism] startOperation() invoked on " + eq.getEquipmentId() + " (" + eq.getClass().getSimpleName() + ")");
    }

    private void handleStop() {
        MedicalEquipment eq = getSelectedEquipment();
        if (eq == null) return;
        eq.stopOperation();
        eq.setStatus(EquipmentStatus.OUT_OF_SERVICE);
        refreshTable();
        appendLog("[Polymorphism] stopOperation() invoked on " + eq.getEquipmentId() + " (" + eq.getClass().getSimpleName() + ")");
    }

    private void handleDeviceAction() {
        MedicalEquipment eq = getSelectedEquipment();
        if (eq == null) return;

        if (eq instanceof Ventilator) {
            String val = JOptionPane.showInputDialog(this, "Enter target oxygen level (%):", "Ventilator Controls", JOptionPane.PLAIN_MESSAGE);
            if (val != null && !val.trim().isEmpty()) {
                double level = Double.parseDouble(val.trim());
                ((Ventilator) eq).adjustOxygenLevel(level);
                appendLog("[Ventilator] Oxygen level adjusted to: " + level + "%");
            }
        } else if (eq instanceof PatientMonitor) {
            ((PatientMonitor) eq).updateVitals(78, "125/82");
            ((PatientMonitor) eq).displayVitals();
            appendLog("[PatientMonitor] Vitals updated: Heart Rate: 78 bpm, BP: 125/82");
        } else if (eq instanceof InfusionPump) {
            String val = JOptionPane.showInputDialog(this, "Enter flow rate (mL/h):", "Pump Controls", JOptionPane.PLAIN_MESSAGE);
            if (val != null && !val.trim().isEmpty()) {
                double rate = Double.parseDouble(val.trim());
                ((InfusionPump) eq).setFlowRate(rate);
                appendLog("[InfusionPump] Flow rate calibrated to: " + rate + " mL/h");
            }
        } else if (eq instanceof ECGMachine) {
            ((ECGMachine) eq).recordECG();
            appendLog("[ECG Machine] Electrocardiogram waveform captured successfully.");
        } else if (eq instanceof PortableUltrasound) {
            ((PortableUltrasound) eq).captureImage();
            appendLog("[Ultrasound] Acoustic image captured and saved.");
        }
    }

    private void handleSchedule() {
        MedicalEquipment eq = getSelectedEquipment();
        if (eq == null) return;

        String tech = JOptionPane.showInputDialog(this, "Enter technician name:", "Maintenance Assignment", JOptionPane.PLAIN_MESSAGE);
        if (tech == null || tech.trim().isEmpty()) tech = "Staff Tech";

        Date schedDate = new Date(System.currentTimeMillis() + 86400000L * 7); // 7 days later
        scheduler.scheduleTask(eq, tech, "Bi-annual compliance calibration", schedDate);
        eq.setStatus(EquipmentStatus.UNDER_MAINTENANCE);
        refreshTable();
        appendLog("[Scheduler] Equipment " + eq.getEquipmentId() + " scheduled for service by " + tech);
    }

    private void handleService() {
        MedicalEquipment eq = getSelectedEquipment();
        if (eq == null) return;

        eq.performMaintenance();
        refreshTable();
        appendLog("[Maintenance] Maintenance completed for " + eq.getEquipmentId() + ". Restored to OPERATIONAL.");
    }

    private void handleViewLog() {
        MedicalEquipment eq = getSelectedEquipment();
        if (eq == null) return;

        StringBuilder sb = new StringBuilder();
        sb.append("=== Maintenance History for ").append(eq.getName()).append(" (").append(eq.getEquipmentId()).append(") ===\n\n");
        List<MaintenanceRecord> records = eq.getMaintenanceHistory();
        if (records.isEmpty()) {
            sb.append("No service records found. Device is running on factory calibration.");
        } else {
            for (MaintenanceRecord r : records) {
                sb.append(r.getSummary()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Audit History (Composition)", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleAddDevice() {
        String[] types = {"Patient Monitor", "Ventilator", "Infusion Pump", "ECG Machine", "Portable Ultrasound"};
        String chosen = (String) JOptionPane.showInputDialog(this, "Select Device Type:", "Add Equipment",
                JOptionPane.QUESTION_MESSAGE, null, types, types[0]);
        if (chosen == null) return;

        String name = JOptionPane.showInputDialog(this, "Device Name:");
        if (name == null || name.trim().isEmpty()) return;

        String id = "EQ-NEW-" + (hospital.getEquipmentList().size() + 1);
        MedicalEquipment newEq;

        switch (chosen) {
            case "Ventilator":
                newEq = new Ventilator(id, name, "MedTech", "V-Next");
                break;
            case "Infusion Pump":
                newEq = new InfusionPump(id, name, "Baxter", "Flo-Pro");
                break;
            case "ECG Machine":
                newEq = new ECGMachine(id, name, "GE Health", "CardioCare", 12);
                break;
            case "Portable Ultrasound":
                newEq = new PortableUltrasound(id, name, "SonoScape", "S-Mobile", "Linear");
                break;
            default:
                newEq = new PatientMonitor(id, name, "Philips", "M-Pulse");
                break;
        }

        hospital.addEquipment(newEq);
        refreshTable();
        appendLog("[Inventory] Registered new device: " + newEq.getEquipmentInfo());
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        int total = 0, op = 0, maint = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (MedicalEquipment eq : hospital.getEquipmentList()) {
            total++;
            if (eq.checkStatus() == EquipmentStatus.OPERATIONAL) op++;
            else if (eq.checkStatus() == EquipmentStatus.UNDER_MAINTENANCE) maint++;

            String serviceDate = (eq.getLastMaintenanceDate() != null) ? sdf.format(eq.getLastMaintenanceDate()) : "Factory Tested";

            tableModel.addRow(new Object[]{
                    eq.getEquipmentId(),
                    eq.getName(),
                    eq.getClass().getSimpleName(),
                    eq.getManufacturer() + " (" + eq.getModel() + ")",
                    eq.checkStatus().toString(),
                    serviceDate
            });
        }

        totalUnitsLabel.setText("Total Devices: " + total);
        operationalLabel.setText("Operational: " + op);
        maintenanceLabel.setText("Maintenance: " + maint);
    }

    private void appendLog(String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        logArea.append("[" + sdf.format(new Date()) + "] " + message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new HospitalGUI().setVisible(true);
        });
    }
}
