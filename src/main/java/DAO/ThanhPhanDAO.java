package DAO;
import java.sql.*;
import java.util.ArrayList;
import DTO.ThanhPhan;

public class ThanhPhanDAO {
    public boolean addThanhPhan(ThanhPhan tp) {
        boolean result = false;
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
            try {
                String sql = "INSERT INTO thanhphan (mathuoc, thanhphan) VALUES (?, ?)";
                PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
                pstmt.setString(1, tp.getMathuoc());
                pstmt.setString(2, tp.getThanhphan());
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

    public boolean deleteThanhPhan(String maThanhPhan) {
        boolean result = false;
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
            try {
                String sql = "DELETE FROM thanhphan WHERE mathuoc = ?";
                PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
                pstmt.setString(1, maThanhPhan);
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
    
    public ArrayList<String> getThanhPhanByMaThuoc(String maThuoc) {
        ArrayList<String> arr = new ArrayList<>();
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
            try {
                String sql = "SELECT thanhphan FROM thanhphan WHERE mathuoc = ?";
                PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
                pstmt.setString(1, maThuoc);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    arr.add(rs.getString("thanhphan"));
                }
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return arr;
    }
}
