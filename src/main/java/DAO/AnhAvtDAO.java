package DAO;

import java.sql.*;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AnhAvtDAO {
    public boolean addAnhAvt(String maDS, String anh) {
        boolean result = false;

        // Mở kết nối
        try (Connection con = DBConnect.getConnection()) { // Sử dụng getConnection để lấy kết nối
            if (con != null) {
                String sql = "INSERT INTO anhavt (mads, anhavt) VALUES (?, ?)";
                try (PreparedStatement pstmt = con.prepareStatement(sql)) { // Sử dụng try-with-resources để tự động đóng PreparedStatement
                    pstmt.setString(1, maDS);
                    pstmt.setString(2, anh);
                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        result = true;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm ảnh đại diện: " + e.getMessage()); // In ra thông báo lỗi
        }

        return result;
    
}

    public boolean UpdateAnhAvt(String maDS, String anh) {
        boolean result = false;
        DBConnect.open();
        Connection con = DBConnect.getConnection();

        if (con != null) {
            try {
                String sql = "UPDATE anhavt SET anhavt=? WHERE maDS=?";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, anh);
                pstmt.setString(2, maDS);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    result = true;
                }
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return result;
    }
    public boolean deleteAnhAvt(String mads) {
        boolean result = false;
        DBConnect.open();
        Connection con = DBConnect.getConnection();

        if (con != null) {
            try {
                String sql = "DELETE FROM anhavt WHERE mads = ?";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, mads);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    result = true;
                }
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return result;
    }
    public String getAnhAvtByMads(String mads) {
        String anhavt = null;
        DBConnect.open();
        Connection con = DBConnect.getConnection();

        if (con != null) {
            try {
                String sql = "SELECT anhavt FROM anhavt WHERE mads = ?";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, mads);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    anhavt = rs.getString("anhavt");
                }
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return anhavt;
    }
}
