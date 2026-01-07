package DAO;

import DTO.SanPham;
import DAO.SanPhamDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SanPhamDAO {

	public ArrayList<SanPham> getAllSp() {
		ArrayList<SanPham> arr = new ArrayList<SanPham>();
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
			try {
				String sql = "select * from sanpham";
				Statement stmt = DBConnect.getConnection().createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					SanPham sp = new SanPham();
					sp.setMa(rs.getString(1));
					sp.setTen(rs.getString(2));
					sp.setLoai(rs.getString(3));
					sp.setNhaSanXuat(rs.getString(4));
					sp.setQuyCach(rs.getString(5));
					sp.setXuatXu(rs.getString(6));
					sp.setCanKeToa(rs.getBoolean(7));
					sp.setTrangThai(rs.getBoolean(8));
					arr.add(sp);
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return arr;
	}
	
	public boolean add(SanPham sp) {
	    boolean result = false;
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "INSERT INTO sanpham (masp, tensp, loaisp, nhasanxuat, quycach, xuatxu, canketoa, trangthai) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
	            pstmt.setString(1, sp.getMa());
	            pstmt.setString(2, sp.getTen());
	            pstmt.setString(3, sp.getLoai());
	            pstmt.setString(4, sp.getNhaSanXuat());
	            pstmt.setString(5, sp.getQuyCach());
	            pstmt.setString(6, sp.getXuatXu());
	            pstmt.setBoolean(7, sp.isCanKeToa());
	            pstmt.setBoolean(8, sp.isTrangThai());
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
	
	public String getLatestProductId() {
	    String latestProductId = null;
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "SELECT MASP FROM sanpham ORDER BY MASP DESC LIMIT 1";
	            Statement stmt = DBConnect.getConnection().createStatement();
	            ResultSet rs = stmt.executeQuery(sql);
	            if (rs.next()) {
	                latestProductId = rs.getString(1);
	            }
	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    return latestProductId;
	}

	
	public boolean update(SanPham sp) {
	    boolean result = false;
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "UPDATE sanpham SET tensp = ?, loaisp = ?, nhasanxuat = ?, quycach = ?, xuatxu = ?, canketoa = ? WHERE masp = ?";
	            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
	            pstmt.setString(1, sp.getTen());
	            pstmt.setString(2, sp.getLoai());
	            pstmt.setString(3, sp.getNhaSanXuat());
	            pstmt.setString(4, sp.getQuyCach());
	            pstmt.setString(5, sp.getXuatXu());
	            pstmt.setBoolean(6, sp.isCanKeToa());
	            pstmt.setString(7, sp.getMa());
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

	public boolean changeStatus(String maSanPham, boolean trangThai) {
	    boolean result = false;
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "UPDATE sanpham SET trangthai = ? WHERE masp = ?";
	            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
	            pstmt.setBoolean(1, trangThai);
	            pstmt.setString(2, maSanPham);
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

	public ArrayList<SanPham> find(String chuoi) {
	    ArrayList<SanPham> arr = new ArrayList<SanPham>();
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "SELECT * FROM sanpham WHERE masp like ? or tensp like ? or loaisp like ? or nhasanxuat like ? or "
	                    + "quycach like ? or xuatxu like ?";
	            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
	            String param = "%" + chuoi + "%";
	            for (int i = 1; i <= 6; i++) {
	                pstmt.setString(i, param);
	            }
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                SanPham sp = new SanPham();
	                sp.setMa(rs.getString(1));
	                sp.setTen(rs.getString(2));
	                sp.setLoai(rs.getString(3));
	                sp.setNhaSanXuat(rs.getString(4));
	                sp.setQuyCach(rs.getString(5));
	                sp.setXuatXu(rs.getString(6));
	                sp.setCanKeToa(rs.getBoolean(7));
	                sp.setTrangThai(rs.getBoolean(8));
	                arr.add(sp);
	            }
	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    return arr;
	}
	
	public ArrayList<SanPham> findCanKeToa(int num) {
	    ArrayList<SanPham> arr = new ArrayList<SanPham>();
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "SELECT * FROM sanpham WHERE canketoa = ?";
	            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
	            pstmt.setInt(1, num);
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                SanPham sp = new SanPham();
	                sp.setMa(rs.getString(1));
	                sp.setTen(rs.getString(2));
	                sp.setLoai(rs.getString(3));
	                sp.setNhaSanXuat(rs.getString(4));
	                sp.setQuyCach(rs.getString(5));
	                sp.setXuatXu(rs.getString(6));
	                sp.setCanKeToa(rs.getBoolean(7));
	                sp.setTrangThai(rs.getBoolean(8));
	                arr.add(sp);
	            }
	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    return arr;
	}
	
	public ArrayList<SanPham> findTrangThai(int num) {
	    ArrayList<SanPham> arr = new ArrayList<SanPham>();
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "SELECT * FROM sanpham WHERE trangthai = ?";
	            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
	            pstmt.setInt(1, num);
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                SanPham sp = new SanPham();
	                sp.setMa(rs.getString(1));
	                sp.setTen(rs.getString(2));
	                sp.setLoai(rs.getString(3));
	                sp.setNhaSanXuat(rs.getString(4));
	                sp.setQuyCach(rs.getString(5));
	                sp.setXuatXu(rs.getString(6));
	                sp.setCanKeToa(rs.getBoolean(7));
	                sp.setTrangThai(rs.getBoolean(8));
	                arr.add(sp);
	            }
	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    return arr;
	}
	
	public ArrayList<String> loaddataQuocGia() {
	    ArrayList<String> arr = new ArrayList<String>();
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "SELECT country_name FROM countries";
	            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                arr.add(rs.getString(1));
	            }
	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    return arr;
	}
}