package DAO;

import DTO.NhaCungCap;
import java.sql.*;
import java.util.ArrayList;

public class NhaCungCapDAO {
	public ArrayList<NhaCungCap> getAllNCC() {
		ArrayList<NhaCungCap> arr = new ArrayList<NhaCungCap>();
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
			try {
				String sql = "select * from nhacungcap";
				Statement stmt = DBConnect.getConnection().createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					NhaCungCap ncc = new NhaCungCap();
					ncc.setMa(rs.getString(1)); 
					ncc.setTen(rs.getString(2));
					ncc.setDiaChi(rs.getString(3));
					ncc.setSoDT(rs.getString(4));
					ncc.setEmail(rs.getString(5));
					ncc.setTrangThai(rs.getBoolean(6));
					arr.add(ncc);
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e + "\nLoi lay danh sach Person tu DB");
			}
		}
		return arr;
	}
	
	public boolean addNhaCungCap (NhaCungCap ncc) {
		boolean result = false;
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
			try {
				String sql = "INSERT INTO nhacungcap(mancc, tenncc, diachi, sodt, email, trangthai) VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
				
				stmt.setString(1, ncc.getMa());
				stmt.setString(2, ncc.getTen());
				stmt.setString(3, ncc.getDiaChi());
				stmt.setString(4, ncc.getSoDT());
				stmt.setString(5, ncc.getEmail());
				stmt.setBoolean(6, ncc.isTrangThai());
				if (stmt.executeUpdate() >= 1) {
					result = true;
				}
				con.close();
				
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return result;
	}
	
	public boolean editNhaCungCap(NhaCungCap ncc) {
		boolean result = false;
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
			try {
				String sql = "UPDATE nhacungcap set tenncc=?, diachi=?, sodt=?, email=?, trangthai=? WHERE mancc=?";
				PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
				stmt.setString(1, ncc.getTen());
				stmt.setString(2, ncc.getDiaChi());
				stmt.setString(3, ncc.getSoDT());
				stmt.setString(4, ncc.getEmail());
				stmt.setString(6, ncc.getMa());
				stmt.setBoolean(5, ncc.isTrangThai());
				int check = stmt.executeUpdate();
				if(check > 0) {
					result = true;
				}
				con.close();
			} catch(Exception e) {
				System.out.println(e);
			}
		}
		return result;
	}
	
	public boolean changeStatusNhaCungCap (String ma, boolean trangThai) {
		boolean result = false;
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
			try {
				String sql = "UPDATE nhacungcap SET trangThai = ? WHERE mancc=?";
				PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
				stmt.setBoolean(1, trangThai);
				stmt.setString(2, ma);
				int rowAffected = stmt.executeUpdate();
				if (rowAffected == 1) {
					result = true;
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return result;
	}
	
	public String getLastMaNCC() {
	    String lastMaNCC = "";
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "SELECT MAX(MaNCC) FROM nhacungcap";
	            Statement stmt = DBConnect.getConnection().createStatement();
	            ResultSet rs = stmt.executeQuery(sql);
	            if (rs.next()) {
	                lastMaNCC = rs.getString(1);
	            }
	            con.close();
	        } catch (Exception e) {
	            System.out.println(e + "\nLỗi lấy mã nhà cung cấp cuối cùng từ DB");
	        }
	    }
	    return lastMaNCC;
	}

	public ArrayList<NhaCungCap> find(String chuoi) {
	    ArrayList<NhaCungCap> arr = new ArrayList<NhaCungCap>();
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "SELECT * FROM nhacungcap WHERE mancc LIKE ? OR tenncc LIKE ? OR diaChi LIKE ? OR sodt LIKE ? OR email LIKE ?";
	            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
	            String param = "%" + chuoi + "%";
	            for (int i = 1; i <= 5; i++) {
	                pstmt.setString(i, param);
	            }
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                NhaCungCap ncc = new NhaCungCap();
	                ncc.setMa(rs.getString(1));
	                ncc.setTen(rs.getString(2));
	                ncc.setDiaChi(rs.getString(3));
	                ncc.setSoDT(rs.getString(4));
	                ncc.setEmail(rs.getString(5));
	                ncc.setTrangThai(rs.getBoolean(6));
	                arr.add(ncc);
	            }
	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    return arr;
	}

	public ArrayList<NhaCungCap> findTrangThai(int num) {
	    ArrayList<NhaCungCap> arr = new ArrayList<NhaCungCap>();
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
	        try {
	            String sql = "SELECT * FROM nhacungcap WHERE trangthai = ?";
	            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
	            pstmt.setInt(1, num);
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                NhaCungCap ncc = new NhaCungCap();
	                ncc.setMa(rs.getString(1));
	                ncc.setTen(rs.getString(2));
	                ncc.setDiaChi(rs.getString(3));
	                ncc.setSoDT(rs.getString(4));
	                ncc.setEmail(rs.getString(5));
	                ncc.setTrangThai(rs.getBoolean(6));
	                arr.add(ncc);
	            }
	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    return arr;
	}	
}
