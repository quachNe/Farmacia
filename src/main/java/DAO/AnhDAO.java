package DAO;

import java.sql.*;

public class AnhDAO {
    public boolean addAnh(String mathuoc, String anh) {
        boolean result = false;
        DBConnect.open();
        Connection con = DBConnect.getConnection();
        
        if (con != null) {
            try {
                String sql = "INSERT INTO anh (mathuoc, anh) VALUES (?, ?)";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, mathuoc);
                pstmt.setString(2, anh);
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
    
    public boolean UpdateAnh(String mathuoc, String anh) {
        boolean result = false;
        DBConnect.open();
        Connection con = DBConnect.getConnection();
        
        if (con != null) {
            try {
                String sql = "UPDATE anh SET anh=? WHERE mathuoc=?";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, anh);
                pstmt.setString(2, mathuoc);
                result = pstmt.executeUpdate()>0;
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return result;
    }
    
    public boolean deleteAnh(String maThuoc) {
        boolean result = false;
        DBConnect.open();
        Connection con = DBConnect.getConnection();
        
        if (con != null) {
            try {
                String sql = "DELETE FROM anh WHERE mathuoc = ?";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, maThuoc);
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

    public String getAnhByMaThuoc(String maThuoc) {
        String anh  = null;
        DBConnect.open();
        Connection con = DBConnect.getConnection();
        
        if (con != null) {
            try {
                String sql = "SELECT anh  FROM anh  WHERE mathuoc = ?";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, maThuoc);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                   anh = rs.getString("anh");
                }
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return anh;
    }
}
