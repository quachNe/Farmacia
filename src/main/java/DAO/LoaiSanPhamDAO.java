package DAO;

import DTO.LoaiSanPham;
import GUI.SanPhamGUI;

import java.sql.*;
import java.util.ArrayList;

public class LoaiSanPhamDAO {

	public ArrayList<LoaiSanPham> getAllLSP() {
		ArrayList<LoaiSanPham> arrLSP = new ArrayList<LoaiSanPham>();
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
			try {
				String sql = "select * from loaisanpham";
				Statement stmt = DBConnect.getConnection().createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					LoaiSanPham lsp = new LoaiSanPham();
					lsp.setMa(rs.getString(1));
					lsp.setTen(rs.getString(2));
					lsp.setTrangThai(rs.getBoolean(3));
					arrLSP.add(lsp);
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return arrLSP;
	}
	
	public boolean addLoaiSanPham(LoaiSanPham lsp) {
	    boolean success = false;
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "INSERT INTO loaisanpham (maloai, tenloai , trangthai) VALUES (?, ?, ?)";
	            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
	            pstmt.setString(1, lsp.getMa());
	            pstmt.setString(2, lsp.getTen());
	            pstmt.setBoolean(3, lsp.isTrangThai());
	            int rowsAffected = pstmt.executeUpdate();
	            success = rowsAffected > 0;
	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    return success;
	}

	public boolean updateLoaiSanPham(LoaiSanPham lsp) {
	    boolean success = false;
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "UPDATE loaisanpham SET tenloai=?, trangthai=? WHERE maloai=?";
	            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
	            pstmt.setString(1, lsp.getTen());
	            pstmt.setBoolean(2, lsp.isTrangThai());
	            pstmt.setString(3, lsp.getMa());
	            int rowsAffected = pstmt.executeUpdate();
	            success = rowsAffected > 0;
	            
	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    return success;
	}

	public boolean changeStatusLoaiSanPham(String maloai, boolean trangThai) {
	    boolean success = false;
	    DBConnect.open();
	    Connection con = DBConnect.getConnection();

	    if (con != null) {
	        try {
	            String sql = "UPDATE loaisanpham SET trangThai = ? WHERE maloai=?";
	            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
	            pstmt.setBoolean(1, trangThai);
	            pstmt.setString(2, maloai);
	            int rowsAffected = pstmt.executeUpdate();
	            success = rowsAffected > 0;

	            SanPhamGUI sanPhamGui = new SanPhamGUI();
	            boolean hasLockedProducts = false;

	            for (String masp : sanPhamGui.sanPhamBiKhoa.keySet()) {
	                Boolean TrangThai = sanPhamGui.sanPhamBiKhoa.get(masp);
	                System.out.print(" ma"+masp+" trang thai"+TrangThai);
	                if (TrangThai == true) {
	                    changeStatusSanPhamKhac(maloai, trangThai, masp);
	                    hasLockedProducts = true; // Đánh dấu rằng đã có sản phẩm bị khóa
	                }
	            }

	            // Nếu không có sản phẩm nào bị khóa, khóa tất cả sản phẩm trong loại
	            if (!hasLockedProducts) {
	                changeStatusAllSanPham(maloai, trangThai);
	            }

	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    return success;
	}

	public boolean changeStatusAllSanPham(String maLoaiSP, boolean trangThai) {
	    boolean success = false;
	    DBConnect.open();
	    Connection con = DBConnect.getConnection();

	    if (con != null) {
	        try {
	            // Cập nhật trạng thái của tất cả sản phẩm thuộc loại 'maLoaiSP'
	            String sql = "UPDATE sanpham SET trangThai = ? WHERE loaisp = ?";
	            PreparedStatement pstmt = con.prepareStatement(sql);
	            pstmt.setBoolean(1, trangThai);
	            pstmt.setString(2, maLoaiSP);
	            int rowsAffected = pstmt.executeUpdate();
	            success = rowsAffected > 0;

	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    return success;
	}

	public boolean changeStatusSanPhamKhac(String maLoaiSP, boolean trangThai, String masp) {
	    boolean success = false;
	    DBConnect.open();
	    Connection con = DBConnect.getConnection();

	    if (con != null) {
	        try {
	            // Cập nhật trạng thái của các sản phẩm khác ngoài sản phẩm thuộc loại 'maLoaiSP'
	            String sql = "UPDATE sanpham SET trangThai = ? WHERE loaisp = ? AND masp != ?";
	            PreparedStatement pstmt = con.prepareStatement(sql);
	            pstmt.setBoolean(1, trangThai);
	            pstmt.setString(2, maLoaiSP);
	            pstmt.setString(3, masp); // Mã sản phẩm cần loại trừ
	            int rowsAffected = pstmt.executeUpdate();
	            success = rowsAffected > 0;

	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    return success;
	}

	public ArrayList<LoaiSanPham> searchLSP(String searchTxt) {
		ArrayList<LoaiSanPham> arrLSP = new ArrayList<LoaiSanPham>();
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
			try {
				String sql = "select * from loaisanpham where tenloai like '%" + searchTxt + "%'" + " or maloai like '%" + searchTxt + "%'" ;
				Statement stmt = DBConnect.getConnection().createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					LoaiSanPham lsp = new LoaiSanPham();
					lsp.setMa(rs.getString(1));
					lsp.setTen(rs.getString(2));
					lsp.setTrangThai(rs.getBoolean(3));
					arrLSP.add(lsp);
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return arrLSP;
	}
	
	public ArrayList<String> getAllMaLoai() {
	    ArrayList<String> arrMaLoai = new ArrayList<String>();
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "select maloai from loaisanpham";
	            Statement stmt = DBConnect.getConnection().createStatement();
	            ResultSet rs = stmt.executeQuery(sql);
	            while (rs.next()) {
	                arrMaLoai.add(rs.getString("maloai"));
	            }
	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    return arrMaLoai;
	}
	
	public String getTenLoaiByMaloai(String maloai) {
	    String tenloaisp = null;
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "SELECT tenloai FROM loaisanpham WHERE maloai = ?";
	            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
	            pstmt.setString(1, maloai);
	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next()) {
	                tenloaisp = rs.getString("tenloai");
	            }
	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    return tenloaisp;
	}

	public ArrayList<LoaiSanPham> findTrangThai(int num) {
	    ArrayList<LoaiSanPham> arr = new ArrayList<LoaiSanPham>();
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "SELECT * FROM loaisanpham WHERE trangthai = ?";
	            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
	            pstmt.setInt(1, num);
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                LoaiSanPham lsp = new LoaiSanPham();
	                lsp.setMa(rs.getString(1));
	                lsp.setTen(rs.getString(2));
	                lsp.setTrangThai(rs.getBoolean(3));
	                arr.add(lsp);
	            }
	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    return arr;
	}

	public String getLatestMaLoai() {
	    String latestMaLoai = null;
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "SELECT MAX(maloai) FROM loaisanpham";
	            Statement stmt = DBConnect.getConnection().createStatement();
	            ResultSet rs = stmt.executeQuery(sql);
	            if (rs.next()) {
	                latestMaLoai = rs.getString(1);
	            }
	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    return latestMaLoai;
	}
}
