/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Controller.ConnectSQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author REMMY
 */
public class BrandDAO {
    private Connection conn;
    private static final String INSERT_BRAND = "INSERT INTO production.brands (brand_name) VALUES (?)";
    private static final String UPDATE_BRAND = "UPDATE production.brands SET brand_name=? WHERE brand_id=?";
    private static final String DELETE_BRAND = "DELETE FROM production.brands WHERE brand_id=?";
    private static final String SELECT_ALL_BRANDS = "SELECT * FROM production.brands ORDER BY brand_id";
    private static final String SELECT_BRAND_BY_ID = "SELECT * FROM production.brands WHERE brand_id=?";
    private static final String CHECK_BRAND_IN_USE = "SELECT COUNT(*) FROM production.products WHERE brand_id=?";

    public BrandDAO() {
        this.conn = ConnectSQL.getConnection();
    }

    public List<Brand> getAllBrands() {
        List<Brand> brands = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_BRANDS)) {
            
            while (rs.next()) {
                brands.add(mapResultSetToBrand(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách nhãn hàng: " + e.getMessage());
        }
        return brands;
    }

    public boolean insertBrand(Brand brand) {
        try (PreparedStatement pstmt = conn.prepareStatement(INSERT_BRAND, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, brand.getBrandName());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        brand.setBrandId(rs.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            if (e.getMessage().contains("unique")) {
                System.err.println("Tên nhãn hàng đã tồn tại!");
            } else {
                System.err.println("Lỗi khi thêm nhãn hàng: " + e.getMessage());
            }
            return false;
        }
    }

    public boolean updateBrand(Brand brand) {
        try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_BRAND)) {
            pstmt.setString(1, brand.getBrandName());
            pstmt.setInt(2, brand.getBrandId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            if (e.getMessage().contains("unique")) {
                System.err.println("Tên nhãn hàng đã tồn tại!");
            } else {
                System.err.println("Lỗi khi cập nhật nhãn hàng: " + e.getMessage());
            }
            return false;
        }
    }

    public boolean deleteBrand(int brandId) {
        // Kiểm tra xem brand có đang được sử dụng không
        if (isBrandInUse(brandId)) {
            System.err.println("Không thể xóa nhãn hàng đang được sử dụng trong sản phẩm!");
            return false;
        }

        try (PreparedStatement pstmt = conn.prepareStatement(DELETE_BRAND)) {
            pstmt.setInt(1, brandId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa nhãn hàng: " + e.getMessage());
            return false;
        }
    }

    public Brand getBrandById(int brandId) {
        try (PreparedStatement pstmt = conn.prepareStatement(SELECT_BRAND_BY_ID)) {
            pstmt.setInt(1, brandId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBrand(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm nhãn hàng: " + e.getMessage());
        }
        return null;
    }

    private boolean isBrandInUse(int brandId) {
        try (PreparedStatement pstmt = conn.prepareStatement(CHECK_BRAND_IN_USE)) {
            pstmt.setInt(1, brandId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra nhãn hàng: " + e.getMessage());
        }
        return true; // Trả về true để đảm bảo an toàn
    }

    private Brand mapResultSetToBrand(ResultSet rs) throws SQLException {
        Brand brand = new Brand();
        brand.setBrandId(rs.getInt("brand_id"));
        brand.setBrandName(rs.getString("brand_name"));
        return brand;
    }

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
