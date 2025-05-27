/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;


import Model.Customer;
import Controller.ConnectSQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    // Lấy danh sách khách hàng
    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM sales.customers";
        try (Connection conn = ConnectSQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getInt("customer_id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("Last_name"));
                customer.setStreet(rs.getString("street"));
                customer.setPhone(rs.getString("phone"));
                customer.setEmail(rs.getString("email"));
                customer.setCity(rs.getString("city"));
                customer.setState(rs.getString("state"));
                customer.setZipCode(rs.getInt("zip_code"));
                list.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm khách hàng mới
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO sales.customers (customer_id, street, city, state, zip_code) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, customer.getCustomerID());
            pstmt.setString(2, customer.getStreet());
            pstmt.setString(3, customer.getCity());
            pstmt.setString(4, customer.getState());
            pstmt.setInt(5, customer.getZipCode());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật thông tin khách hàng
    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE sales.customers SET street=?, city=?, state=?, zip_code=? WHERE customer_id=?";
        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, customer.getStreet());
            pstmt.setString(2, customer.getCity());
            pstmt.setString(3, customer.getState());
            pstmt.setInt(4, customer.getZipCode());
            pstmt.setInt(5, customer.getCustomerID());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa khách hàng
    public boolean deleteCustomer(int customerID) {
        String sql = "DELETE FROM sales.customers WHERE customer_id=?";
        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, customerID);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
