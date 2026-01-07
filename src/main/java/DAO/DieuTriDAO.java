package DAO;
import java.sql.*;
import java.util.ArrayList;
import DTO.DieuTri;

public class DieuTriDAO {
    public boolean addBenhDieuTri(DieuTri tp) {
        boolean result = false;
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
            try {
                String sql = "INSERT INTO dieutri (mathuoc, benhdieutri) VALUES (?, ?)";
                PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
                pstmt.setString(1, tp.getMathuoc());
                pstmt.setString(2, tp.getBenhdieutri());
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

    public boolean deleteBenhDieuTri(String maThuoc) {
        boolean result = false;
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
            try {
                String sql = "DELETE FROM dieutri WHERE mathuoc = ?";
                PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
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
    
    public ArrayList<String> getBenhDieuTriByMaThuoc(String maThuoc) {
        ArrayList<String> arr = new ArrayList<>();
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
            try {
                String sql = "SELECT benhdieutri FROM dieutri WHERE mathuoc = ?";
                PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
                pstmt.setString(1, maThuoc);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    arr.add(rs.getString("benhdieutri"));
                }
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return arr;
    }
}
