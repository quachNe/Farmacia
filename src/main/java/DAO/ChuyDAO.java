package DAO;

import java.sql.*;

public class ChuyDAO {
    public boolean addChuy(String mathuoc, String chuy) {
        boolean result = false;
        DBConnect.open();
        Connection con = DBConnect.getConnection();
        if (con != null) {
            try {
                String sql = "INSERT INTO chuy (mathuoc, chuy) VALUES (?, ?)";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, mathuoc);
                pstmt.setString(2, chuy);
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
    
    public boolean UpdateChuy(String mathuoc, String chuy) {
        boolean result = false;
        DBConnect.open();
        Connection con = DBConnect.getConnection();
        if (con != null) {
            try {
                String sql = "UPDATE chuy SET chuy=? WHERE mathuoc=?";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, chuy);
                pstmt.setString(2, mathuoc);
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
    
    public boolean deleteChuy(String maChuy) {
        boolean result = false;
        DBConnect.open();
        Connection con = DBConnect.getConnection();
        
        if (con != null) {
            try {
                String sql = "DELETE FROM chuy WHERE machuy = ?";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, maChuy);
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

    // Lấy tên chuy theo mã chuy
    public String getTenchuyByMaChuy(String maThuoc) {
        String chuy = null;
        DBConnect.open();
        Connection con = DBConnect.getConnection();
        
        if (con != null) {
            try {
                String sql = "SELECT chuy FROM chuy WHERE mathuoc = ?";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, maThuoc);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                   chuy = rs.getString("chuy");
                }
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return chuy;
    }
}
