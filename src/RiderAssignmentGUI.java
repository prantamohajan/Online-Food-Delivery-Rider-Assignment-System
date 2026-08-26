
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class RiderAssignmentGUI extends JFrame {

    private DeliveryManager manager;

    // GUI Components
    private JTable riderTable;
    private JTable orderTable;
    private DefaultTableModel riderTableModel;
    private DefaultTableModel orderTableModel;

    private JTextField tfRiderName, tfRiderId, tfRiderPhone, tfExtraAttr;
    private JComboBox<String> cbVehicleType;

    private JTextField tfOrderId, tfCustomer, tfRestaurant, tfAddress, tfDistance;

    public RiderAssignmentGUI() {
        manager = new DeliveryManager();
        initUI();
        seedInitialData();
        refreshTables();
    }

    private void initUI() {
        setTitle("Online Food Delivery Rider Assignment System");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(33, 150, 243));
        JLabel lblTitle = new JLabel("Food Delivery Dispatch & Rider Management Dashboard");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle);
        add(headerPanel, BorderLayout.NORTH);

        // Center Panel: Split into Left (Forms) & Right (Tables)
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Left Panel: Form Controls
        JPanel formsPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        // Form 1: Add Rider
        JPanel addRiderPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        addRiderPanel.setBorder(BorderFactory.createTitledBorder("Register New Rider"));

        tfRiderName = new JTextField();
        tfRiderId = new JTextField();
        tfRiderPhone = new JTextField();
        tfExtraAttr = new JTextField();
        cbVehicleType = new JComboBox<>(new String[]{"Bike", "Cycle"});

        addRiderPanel.add(new JLabel("Rider ID:"));
        addRiderPanel.add(tfRiderId);
        addRiderPanel.add(new JLabel("Name:"));
        addRiderPanel.add(tfRiderName);
        addRiderPanel.add(new JLabel("Phone:"));
        addRiderPanel.add(tfRiderPhone);
        addRiderPanel.add(new JLabel("Vehicle:"));
        addRiderPanel.add(cbVehicleType);
        addRiderPanel.add(new JLabel("Fuel/MaxLoad:"));
        addRiderPanel.add(tfExtraAttr);

        JButton btnAddRider = new JButton("Register Rider");
        btnAddRider.setBackground(new Color(76, 175, 80));
        btnAddRider.setForeground(Color.WHITE);
        btnAddRider.addActionListener(e -> registerRiderAction());
        addRiderPanel.add(new JLabel(""));
        addRiderPanel.add(btnAddRider);

        formsPanel.add(addRiderPanel);

        // Form 2: Create Order
        JPanel createOrderPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        createOrderPanel.setBorder(BorderFactory.createTitledBorder("Create & Assign Order"));

        tfOrderId = new JTextField();
        tfCustomer = new JTextField();
        tfRestaurant = new JTextField();
        tfAddress = new JTextField();
        tfDistance = new JTextField();

        createOrderPanel.add(new JLabel("Order ID:"));
        createOrderPanel.add(tfOrderId);
        createOrderPanel.add(new JLabel("Customer Name:"));
        createOrderPanel.add(tfCustomer);
        createOrderPanel.add(new JLabel("Restaurant:"));
        createOrderPanel.add(tfRestaurant);
        createOrderPanel.add(new JLabel("Delivery Address:"));
        createOrderPanel.add(tfAddress);
        createOrderPanel.add(new JLabel("Distance (km):"));
        createOrderPanel.add(tfDistance);

        JButton btnCreateOrder = new JButton("Assign Order");
        btnCreateOrder.setBackground(new Color(255, 152, 0));
        btnCreateOrder.setForeground(Color.WHITE);
        btnCreateOrder.addActionListener(e -> createOrderAction());

        JButton btnCompleteOrder = new JButton("Complete Delivery");
        btnCompleteOrder.setBackground(new Color(33, 150, 243));
        btnCompleteOrder.setForeground(Color.WHITE);
        btnCompleteOrder.addActionListener(e -> completeDeliveryAction());

        createOrderPanel.add(btnCreateOrder);
        createOrderPanel.add(btnCompleteOrder);

        formsPanel.add(createOrderPanel);
        mainPanel.add(formsPanel);

        // Right Panel: Tables
        JPanel tablesPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        // Rider Table
        riderTableModel = new DefaultTableModel(new String[]{"ID", "Name", "Vehicle", "Status", "Details"}, 0);
        riderTable = new JTable(riderTableModel);
        JScrollPane riderScroll = new JScrollPane(riderTable);
        riderScroll.setBorder(BorderFactory.createTitledBorder("Registered Riders Status"));
        tablesPanel.add(riderScroll);

        // Order Table
        orderTableModel = new DefaultTableModel(new String[]{"Order ID", "Customer", "Restaurant", "Status"}, 0);
        orderTable = new JTable(orderTableModel);
        JScrollPane orderScroll = new JScrollPane(orderTable);
        orderScroll.setBorder(BorderFactory.createTitledBorder("Live Order Board"));
        tablesPanel.add(orderScroll);

        mainPanel.add(tablesPanel);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void seedInitialData() {
        manager.addRider(new BikeRider("Rahim", "R1", "01700000001", 80.0, 15.0));
        manager.addRider(new CycleRider("Karim", "R2", "01700000002", 10.0));
    }

    private void registerRiderAction() {
        try {
            String id = tfRiderId.getText().trim();
            String name = tfRiderName.getText().trim();
            String phone = tfRiderPhone.getText().trim();
            String vehicle = (String) cbVehicleType.getSelectedItem();
            double extra = Double.parseDouble(tfExtraAttr.getText().trim());

            if (id.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter all rider fields!", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if ("Bike".equals(vehicle)) {
                manager.addRider(new BikeRider(name, id, phone, extra, 20.0));
            } else {
                manager.addRider(new CycleRider(name, id, phone, extra));
            }

            refreshTables();
            JOptionPane.showMessageDialog(this, "Rider registered successfully!");
            tfRiderId.setText("");
            tfRiderName.setText("");
            tfRiderPhone.setText("");
            tfExtraAttr.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Fuel/Load must be numeric!", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createOrderAction() {
        try {
            String orderId = tfOrderId.getText().trim();
            String customer = tfCustomer.getText().trim();
            String restaurant = tfRestaurant.getText().trim();
            String address = tfAddress.getText().trim();
            double distance = Double.parseDouble(tfDistance.getText().trim());

            if (orderId.isEmpty() || customer.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all order details!", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Order newOrder = new Order(orderId, customer, restaurant, address);
            manager.addOrder(newOrder);

            // Polymorphic time calculation & exception check
            Rider assignedRider = manager.assignRider(newOrder);
            double time = assignedRider.calculateDeliveryTime(distance);

            refreshTables();
            JOptionPane.showMessageDialog(this,
                    "Assigned to: " + assignedRider.getName() + "\nEstimated Time: " + String.format("%.2f", time) + " hours",
                    "Assignment Success", JOptionPane.INFORMATION_MESSAGE);

            tfOrderId.setText("");
            tfCustomer.setText("");
            tfRestaurant.setText("");
            tfAddress.setText("");
            tfDistance.setText("");

        } catch (NoRiderAvailableException ex) {
            refreshTables();
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Dispatch Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Distance must be a valid number!", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void completeDeliveryAction() {
        int selectedRow = riderTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a rider from the table to complete their delivery.", "Select Rider", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String riderId = (String) riderTableModel.getValueAt(selectedRow, 0);
        boolean success = manager.completeOrder(riderId);

        if (success) {
            refreshTables();
            JOptionPane.showMessageDialog(this, "Order marked as Delivered. Rider is now Available!");
        } else {
            JOptionPane.showMessageDialog(this, "Selected rider has no active order.", "Notice", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void refreshTables() {
        // Refresh Rider Table
        riderTableModel.setRowCount(0);
        for (Rider r : manager.getRiderMap().values()) {
            riderTableModel.addRow(new Object[]{
                r.getId(),
                r.getName(),
                r.getVehicleType(),
                r.isAvailable() ? "Available" : "On Delivery",
                r.getRoleDescription()
            });
        }

        // Refresh Order Table
        orderTableModel.setRowCount(0);
        for (Order o : manager.getOrderList()) {
            orderTableModel.addRow(new Object[]{
                o.getOrderId(),
                o.getCustomerName(),
                o.getRestaurant(),
                o.getStatus()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RiderAssignmentGUI().setVisible(true));
    }
}
