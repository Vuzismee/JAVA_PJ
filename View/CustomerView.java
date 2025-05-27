/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

/**
 *
 * @author REMMY
 */

import Model.Customer;
import Model.CustomerDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerView extends JFrame {
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private CustomerDAO customerDAO;
    private JTextField txtCustomerID, txtStreet, txtCity, txtState, txtZipCode;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;

    public CustomerView() {
        customerDAO = new CustomerDAO();
        initComponents();
        loadCustomerData();
    }

    private void initComponents() {
        setTitle("Quản lý Khách hàng");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel nhập liệu
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        txtCustomerID = new JTextField();
        txtStreet = new JTextField();
        txtCity = new JTextField();
        txtState = new JTextField();
        txtZipCode = new JTextField();

        inputPanel.add(new JLabel("Customer ID:"));
        inputPanel.add(txtCustomerID);
        inputPanel.add(new JLabel("Street:"));
        inputPanel.add(txtStreet);
        inputPanel.add(new JLabel("City:"));
        inputPanel.add(txtCity);
        inputPanel.add(new JLabel("State:"));
        inputPanel.add(txtState);
        inputPanel.add(new JLabel("Zip Code:"));
        inputPanel.add(txtZipCode);

        // Panel nút bấm
        JPanel buttonPanel = new JPanel();
        btnAdd = new JButton("Thêm");
        btnUpdate = new JButton("Cập nhật");
        btnDelete = new JButton("Xóa");
        btnClear = new JButton("Làm mới");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        // Bảng hiển thị
        String[] columnNames = {"ID", "Street", "City", "State", "Zip Code"};
        tableModel = new DefaultTableModel(columnNames, 0);
        customerTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(customerTable);

        // Thêm các component vào frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Xử lý sự kiện
        btnAdd.addActionListener(e -> addCustomer());
        btnUpdate.addActionListener(e -> updateCustomer());
        btnDelete.addActionListener(e -> deleteCustomer());
        btnClear.addActionListener(e -> clearFields());
        
        customerTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = customerTable.getSelectedRow();
                if (row >= 0) {
                    displayCustomer(row);
                }
            }
        });

        pack();
        setLocationRelativeTo(null);
    }

    private void loadCustomerData() {
        tableModel.setRowCount(0);
        List<Customer> customers = customerDAO.getAllCustomers();
        for (Customer c : customers) {
            Object[] row = {
                c.getCustomerID(),
                c.getStreet(),
                c.getCity(),
                c.getState(),
                c.getZipCode()
            };
            tableModel.addRow(row);
        }
    }

    private void addCustomer() {
        try {
            Customer customer = new Customer();
            customer.setCustomerID(Integer.parseInt(txtCustomerID.getText()));
            customer.setStreet(txtStreet.getText());
            customer.setCity(txtCity.getText());
            customer.setState(txtState.getText());
            customer.setZipCode(Integer.parseInt(txtZipCode.getText()));

            if (customerDAO.addCustomer(customer)) {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
                loadCustomerData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!");
        }
    }

    private void updateCustomer() {
        try {
            Customer customer = new Customer();
            customer.setCustomerID(Integer.parseInt(txtCustomerID.getText()));
            customer.setStreet(txtStreet.getText());
            customer.setCity(txtCity.getText());
            customer.setState(txtState.getText());
            customer.setZipCode(Integer.parseInt(txtZipCode.getText()));

            if (customerDAO.updateCustomer(customer)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadCustomerData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!");
        }
    }

    private void deleteCustomer() {
        try {
            int customerID = Integer.parseInt(txtCustomerID.getText());
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc muốn xóa khách hàng này?", 
                "Xác nhận xóa", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (customerDAO.deleteCustomer(customerID)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    loadCustomerData();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!");
        }
    }

    private void clearFields() {
        txtCustomerID.setText("");
        txtStreet.setText("");
        txtCity.setText("");
        txtState.setText("");
        txtZipCode.setText("");
        customerTable.clearSelection();
    }

    private void displayCustomer(int row) {
        txtCustomerID.setText(tableModel.getValueAt(row, 0).toString());
        txtStreet.setText(tableModel.getValueAt(row, 1).toString());
        txtCity.setText(tableModel.getValueAt(row, 2).toString());
        txtState.setText(tableModel.getValueAt(row, 3).toString());
        txtZipCode.setText(tableModel.getValueAt(row, 4).toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CustomerView().setVisible(true);
        });
    }
}