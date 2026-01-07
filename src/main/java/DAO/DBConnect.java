package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DBConnect {
    private static Connection con;

    // Mở kết nối
 
        private static String URL = "jdbc:mysql://localhost:3306/QuanLyNhaThuoc";
        private static String user = "root";
        private static String pass = "141516";

        public static Connection getConnection() {
            try {
                return DriverManager.getConnection(URL, user, pass);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    public static boolean open() {
        if (con != null) {
            return true; // Nếu đã mở, không cần mở lại
        }
        
        																																																
        try {
         
            
            con = DriverManager.getConnection(URL, user, pass);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Đóng kết nối
    public static void close() {
        if (con != null) {
            try {
                con.close();
                con = null; // Đặt lại biến kết nối
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

   
    
    // Kiểm tra kết nối
    public static boolean isConnected() {
        try {
            return con != null && !con.isClosed(); // Kiểm tra xem kết nối có mở không
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
