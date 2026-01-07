package DAO;

import DTO.NhaSanXuat;

import java.sql.*;
import java.util.ArrayList;


public class NhaSanXuatDAO {
	public ArrayList<NhaSanXuat> getAllNsx() {
		ArrayList<NhaSanXuat> arr = new ArrayList<NhaSanXuat>();
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
			try {
				String sql = "SELECT * FROM nhasanxuat";
				Statement stmt = DBConnect.getConnection().createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					NhaSanXuat nsx = new NhaSanXuat();
					nsx.setMa(rs.getString(1));
					nsx.setTen(rs.getString(2));
					nsx.setTrangThai(rs.getBoolean(3));
					arr.add(nsx);
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return arr;
	}
	
	public boolean addNhaSanXuat(NhaSanXuat nsx) {
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "INSERT INTO nhasanxuat VALUES (?, ?, true)";
	            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
	            pstmt.setString(1, nsx.getMa());
	            pstmt.setString(2, nsx.getTen());
	            int rowCount = pstmt.executeUpdate();
	            con.close();
	            return rowCount > 0;
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    return false;
	}

	public boolean updateNhaSanXuat(NhaSanXuat nsx) {
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "UPDATE nhasanxuat SET tennsx = ? WHERE mansx = ?";
	            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
	            pstmt.setString(1, nsx.getTen());
//	            pstmt.setBoolean(2, nsx.getTrangThai());
	            pstmt.setString(2, nsx.getMa());
	            int rowCount = pstmt.executeUpdate();
	            con.close();
	            return rowCount > 0;
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    return false;
	}

	public boolean deleteNhaSanXuat(String mansx, boolean trangthai) {
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "UPDATE nhasanxuat SET trangThai = ? WHERE mansx = ?";
	            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
	            pstmt.setBoolean(1, trangthai);
	            pstmt.setString(2, mansx);
	            int rowCount = pstmt.executeUpdate();
	            con.close();
	            return rowCount > 0;
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    return false;
	}

	public String getLatestMaNSX() {
	    String latestMa = null;
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "SELECT MAX(mansx) FROM nhasanxuat";
	            Statement stmt = DBConnect.getConnection().createStatement();
	            ResultSet rs = stmt.executeQuery(sql);
	            if (rs.next()) {
	                latestMa = rs.getString(1);
	            }
	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    return latestMa;
	}
	
	public ArrayList<NhaSanXuat> find(String chuoi) {
	    ArrayList<NhaSanXuat> arr = new ArrayList<NhaSanXuat>();
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "SELECT * FROM nhasanxuat WHERE mansx LIKE ? OR tennsx LIKE ?";
	            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
	            String param = "%" + chuoi + "%";
	            for (int i = 1; i <= 2; i++) {
	                pstmt.setString(i, param);
	            }
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                NhaSanXuat nsx = new NhaSanXuat();
	                nsx.setMa(rs.getString(1));
	                nsx.setTen(rs.getString(2));
	                nsx.setTrangThai(rs.getBoolean(3));
	                arr.add(nsx);
	            }
	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    return arr;
	}

	public ArrayList<NhaSanXuat> findTrangThai(int num) {
	    ArrayList<NhaSanXuat> arr = new ArrayList<NhaSanXuat>();
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "SELECT * FROM nhasanxuat WHERE trangthai = ?";
	            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
	            pstmt.setInt(1, num);
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                NhaSanXuat nsx = new NhaSanXuat();
	                nsx.setMa(rs.getString(1));
	                nsx.setTen(rs.getString(2));
	                nsx.setTrangThai(rs.getBoolean(3));
	                arr.add(nsx);
	            }
	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    return arr;
	}
	
	public ArrayList<String> getAllMaNSX() {
	    ArrayList<String> arrMaNSX = new ArrayList<String>();
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "SELECT mansx FROM nhasanxuat";
	            Statement stmt = DBConnect.getConnection().createStatement();
	            ResultSet rs = stmt.executeQuery(sql);
	            while (rs.next()) {
	                arrMaNSX.add(rs.getString("mansx"));
	            }
	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    return arrMaNSX;
	}
	
	public String getTenNsxByMansx(String mansx) {
	    String tenNSX = null;
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "SELECT tennsx FROM nhasanxuat WHERE mansx = ?";
	            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
	            pstmt.setString(1, mansx);
	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next()) {
	                tenNSX = rs.getString("tennsx");
	            }
	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    return tenNSX;
	}
}
