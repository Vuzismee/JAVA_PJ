package Controller;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Test {
    public static void main(String[] args) {
        try {
            String dbURL = "jdbc:sqlserver://localhost:1433;"
                         + "databaseName=QLGV;user=sa;password=sa;";
            Connection conn = DriverManager.getConnection(dbURL);
            if (conn != null) {
                System.out.println("Kết nối thành công!");
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Tên driver: " + dm.getDriverName());
                System.out.println("Phiên bản driver: " + dm.getDriverVersion());
                System.out.println("Tên sản phẩm: " + dm.getDatabaseProductName());
                System.out.println("Phiên bản sản phẩm: " + dm.getDatabaseProductVersion());
            }
        } catch (SQLException ex) {
            System.err.println("Kết nối thất bại: " + ex);
        }
    }
}