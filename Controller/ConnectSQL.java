/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author REMMY
 */
public class ConnectSQL {
    public static Connection getConnection() {
        try {
            String dbURL = "jdbc:sqlserver://localhost:1433;"
                         + "databaseName=QLGV;user=sa;password=sa;";
            return DriverManager.getConnection(dbURL);
        } catch (SQLException ex) {
            System.err.println("Cannot connect database, " + ex);
            return null;
        }
    }
}