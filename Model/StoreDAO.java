package Model;

import Controller.ConnectSQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StoreDAO {
    private Connection conn;
    private static final String INSERT_STORE = "INSERT INTO sales.stores (store_name, phone, email, street, city, state, zip_code) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_STORE = "UPDATE sales.stores SET store_name=?, phone=?, email=?, street=?, city=?, state=?, zip_code=? WHERE store_id=?";
    private static final String DELETE_STORE = "DELETE FROM sales.stores WHERE store_id=?";
    private static final String SELECT_ALL_STORES = "SELECT * FROM sales.stores";
    private static final String SELECT_STORE_BY_ID = "SELECT * FROM sales.stores WHERE store_id=?";

    public StoreDAO() {
        this.conn = ConnectSQL.getConnection();
    }

    public List<Store> getAllStores() {
        List<Store> stores = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_STORES)) {
            
            while (rs.next()) {
                stores.add(mapResultSetToStore(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách cửa hàng: " + e.getMessage());
        }
        return stores;
    }

    public boolean insertStore(Store store) {
        try (PreparedStatement pstmt = conn.prepareStatement(INSERT_STORE)) {
            setStoreParameters(pstmt, store);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm cửa hàng: " + e.getMessage());
            return false;
        }
    }

    public boolean updateStore(Store store) {
        try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_STORE)) {
            setStoreParameters(pstmt, store);
            pstmt.setInt(8, store.getStoreId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật cửa hàng: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteStore(int storeId) {
        try (PreparedStatement pstmt = conn.prepareStatement(DELETE_STORE)) {
            pstmt.setInt(1, storeId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa cửa hàng: " + e.getMessage());
            return false;
        }
    }

    public Store getStoreById(int storeId) {
        try (PreparedStatement pstmt = conn.prepareStatement(SELECT_STORE_BY_ID)) {
            pstmt.setInt(1, storeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToStore(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm cửa hàng: " + e.getMessage());
        }
        return null;
    }

    // Helper method để map ResultSet sang đối tượng Store
    private Store mapResultSetToStore(ResultSet rs) throws SQLException {
        Store store = new Store();
        store.setStoreId(rs.getInt("store_id"));
        store.setStoreName(rs.getString("store_name"));
        store.setPhone(rs.getString("phone"));
        store.setEmail(rs.getString("email"));
        store.setStreet(rs.getString("street"));
        store.setCity(rs.getString("city"));
        store.setState(rs.getString("state"));
        store.setZipCode(rs.getString("zip_code"));
        return store;
    }

    // Helper method để set các tham số cho PreparedStatement
    private void setStoreParameters(PreparedStatement pstmt, Store store) throws SQLException {
        pstmt.setString(1, store.getStoreName());
        pstmt.setString(2, store.getPhone());
        pstmt.setString(3, store.getEmail());
        pstmt.setString(4, store.getStreet());
        pstmt.setString(5, store.getCity());
        pstmt.setString(6, store.getState());
        pstmt.setString(7, store.getZipCode());
    }

    // Đóng kết nối khi không cần thiết
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
        }
    }
}