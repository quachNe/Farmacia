package DAO;

import DTO.DuocSi;
import DTO.TaiKhoan;

import java.sql.*;
import java.util.ArrayList;

public class DuocSiDAO {
	public ArrayList<DuocSi> getDuocSi() {
		ArrayList<DuocSi> result = null;
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		if (con != null) {
			try {
				ResultSet rs = con.prepareStatement("select ds.mads,ds.hoten,ds.sodt,ds.email,tk.username,ds.trangthai from duocsi ds left join taikhoan tk on mads = matk").executeQuery();				
				result = new ArrayList<>();
				while (rs.next()) {
					TaiKhoan taiKhoan = new TaiKhoan(rs.getString("username"));
					result.add(new DuocSi(rs.getString("MaDS"), rs.getString("HoTen"),rs.getString("SoDT"), rs.getString("Email"),taiKhoan,rs.getBoolean("trangThai")));
				}
				con.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public boolean addDuocSi(DuocSi ds) {
	    DBConnect.open();
	    Connection con =  DBConnect.getConnection();
	    boolean result = false;   
	    if (con != null) {
	        try {
	            PreparedStatement st=con.prepareStatement("INSERT INTO DuocSi VALUES (?, ?, ?, ?, ?)");
	            st.setString(1, ds.getMa());
	            st.setString(2, ds.getTen());
	            st.setString(3, ds.getSoDT());
	            st.setString(4, ds.getEmail());
	            st.setBoolean(5, true);
	            result = st.executeUpdate() > 0;
	            con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return result;     
	}
	public boolean updateDuocSi(DuocSi ds) {
	    DBConnect.open();
	    Connection con =  DBConnect.getConnection();
	    boolean result = false;
	    if (con != null) {
	        try {
	            PreparedStatement st = con.prepareStatement("UPDATE DuocSi SET HoTen=?, SoDT=?, Email=? WHERE MaDS=?");
	                st.setString(1, ds.getTen());
	                st.setString(2, ds.getSoDT());
	                st.setString(3, ds.getEmail());
	                st.setString(4, ds.getMa());
	                result = st.executeUpdate() > 0; 
	            con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return result;
	}
	public boolean lockDuocSi(String ma) {
		DBConnect.open();
		Connection con =  DBConnect.getConnection();
		boolean result = false;
		
		if (con != null) {
			try {
				PreparedStatement st = con.prepareStatement("Update DuocSi set TrangThai=false where MaDS=?");
				st.setString(1, ma);
				result = st.executeUpdate() > 0;				
				con.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;		
	}
	public boolean unlockDuocSi(String ma) {
		DBConnect.open();
		Connection con =  DBConnect.getConnection();
		boolean result = false;
		if (con != null) {
			try {
				PreparedStatement st = con.prepareStatement("Update DuocSi set TrangThai=true where MaDS=?");
				st.setString(1, ma);
				
				result = st.executeUpdate() > 0;				
				con.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;		
	}
	public ArrayList<DuocSi> findDuocSi(String option, String key) {
	    ArrayList<DuocSi> resultList = new ArrayList<>();
	    DBConnect.open();
	    Connection con =  DBConnect.getConnection();
	    String query = "";
	    try {
	        if (con != null) {
	            if (option.equalsIgnoreCase("Mã")) {
	                query = "SELECT ds.mads,ds.hoten,ds.sodt,ds.email,tk.username,ds.trangthai FROM DuocSi ds, taikhoan tk WHERE mads = matk  AND MaDS LIKE ?";
	            } else if (option.equalsIgnoreCase("Họ tên")) {
	                query = "SELECT ds.mads,ds.hoten,ds.sodt,ds.email,tk.username,ds.trangthai FROM DuocSi ds, taikhoan tk WHERE mads = matk  AND HoTen LIKE ?";
	            } else if (option.equalsIgnoreCase("SDT")) {
	                query = "SELECT ds.mads,ds.hoten,ds.sodt,ds.email,tk.username,ds.trangthai FROM DuocSi ds, taikhoan tk WHERE mads = matk  AND SoDT LIKE ?";
	            } else if (option.equalsIgnoreCase("Email")) {
	                query = "SELECT ds.mads,ds.hoten,ds.sodt,ds.email,tk.username,ds.trangthai FROM DuocSi ds, taikhoan tk WHERE mads = matk  AND Email LIKE ?";
	            } 
	            PreparedStatement st = con.prepareStatement(query);
	            st.setString(1, "%" + key + "%");
	            ResultSet rs = st.executeQuery();
	            while (rs.next()) {
	            	TaiKhoan taiKhoan = new TaiKhoan(rs.getString("username"));
	                DuocSi duocSi = new DuocSi();
	                duocSi.setMa(rs.getString("MaDS"));
	                duocSi.setTen(rs.getString("HoTen"));
	                duocSi.setSoDT(rs.getString("SoDT"));
	                duocSi.setEmail(rs.getString("Email"));
	                duocSi.setTaiKhoan(taiKhoan);
	                duocSi.setTrangThai(rs.getBoolean("TrangThai"));
	                resultList.add(duocSi);
	            }
	            con.close();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return resultList;
	}
	public int countDS(String DS) {
	    DBConnect.open();
	    Connection con =  DBConnect.getConnection();
	    int count = 0;
	    if (con != null) {
	        try {
	            PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM DuocSi WHERE MaDS LIKE ?");
	            st.setString(1, DS + "%");
	            ResultSet rs = st.executeQuery();
	            if (rs.next()) {
	                count = rs.getInt(1);
	            }
//	            rs.close();
	            con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return count;
	}
	
	public int countQL(String QL) {
	    DBConnect.open();
	    Connection con =  DBConnect.getConnection();
	    int count = 0;
	    if (con != null) {
	        try {
	            PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM DuocSi WHERE MaDS LIKE ?");
	            st.setString(1, QL + "%");
	            ResultSet rs = st.executeQuery();
	            if (rs.next()) {
	                count = rs.getInt(1);
	            }
//	            rs.close();
	            con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return count;
	}
}