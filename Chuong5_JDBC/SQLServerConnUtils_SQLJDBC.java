/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chuong5_JDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author ASUS
 */
public class SQLServerConnUtils_SQLJDBC {
    // Kết nối vào SQLServer.
    // (Sử dụng thư viện điều khiển SQLJDBC)
    public static Connection getSQLServerConnection()
            throws SQLException, ClassNotFoundException {
        String hostName = "localhost";
        String sqlInstanceName = "SQLEXPRESS";
        String database = "LTM";
        String userName = "sa";
        String password = "Ltm@123";

        return getSQLServerConnection(hostName, sqlInstanceName,
                database, userName, password);
    }
    // Trường hợp sử dụng SQLServer.
 // Và thư viện SQLJDBC.
 public static Connection getSQLServerConnection(String hostName,String sqlInstanceName, String database, String userName,String password) throws ClassNotFoundException, SQLException {
     // Khai báo class Driver cho DB SQLServer
     Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
 
     // Cấu trúc URL Connection dành cho SQLServer
     // Ví dụ:
     // jdbc:sqlserver://ServerIp:1433/SQLEXPRESS;databaseName=LTM
     String connectionURL = "jdbc:sqlserver://" + hostName + ":1433" + ";instance=" + sqlInstanceName + ";databaseName=" + database;
     //System.out.println(connectionURL);
     Connection conn = DriverManager.getConnection(connectionURL, userName, password);
     return conn;
 }
}
