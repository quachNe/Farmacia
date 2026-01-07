package DAO;

import DTO.TieuHuy;
import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDate;

public class TieuHuyDAO {
	public ArrayList<TieuHuy> getTieuHuy() {
		ArrayList<TieuHuy> result = null;
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
			try {
				ResultSet rs = con.prepareStatement("Select * from TieuHuy T join ChiTietSanPham C on T.mavach = C.mavach").executeQuery();				
				result = new ArrayList<>();
				while (rs.next())
					result.add(new TieuHuy(rs.getString("maSP"), rs.getString("loSX"), rs.getDate("ngayTieuHuy").toLocalDate(),
						rs.getString("nguoiLap"), rs.getString("lyDo"), rs.getLong("thietHai")));
				con.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public ArrayList<String> getSanPham() {
		ArrayList<String> result = null;
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
			try {
				ResultSet rs = con.prepareStatement("Select distinct maSP from ChiTietSanPham where soLuong > 0").executeQuery();				
				result = new ArrayList<>();
				while (rs.next()) result.add(rs.getString("maSP"));
				con.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public ArrayList<String> getSanXuat(String maSP) {
		ArrayList<String> result = null;
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
			try {
				PreparedStatement st = con.prepareStatement("Select distinct loSX from ChiTietSanPham where maSP = ? and soLuong > 0");
				st.setString(1, maSP);
				ResultSet rs = st.executeQuery();
				result = new ArrayList<>();
				while (rs.next()) result.add(rs.getString("loSX"));
				con.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public long getThietHai(String maSP, String loSX) {
	    long tong = 0;
	    DBConnect.open();
	    Connection con = DBConnect.getConnection();
	    
	    if (con != null) {
	        try {
	            // Chỉ rõ tên cột loSX trong điều kiện WHERE
	            PreparedStatement st = con.prepareStatement("SELECT DISTINCT giaNhap, S.soLuong FROM ChiTietSanPham S JOIN ChiTietPhieuNhap C ON S.mavach = C.mavach WHERE S.maSP = ? AND S.loSX = ? AND S.soLuong > 0");
	            st.setString(1, maSP);
	            st.setString(2, loSX);
	            ResultSet rs = st.executeQuery();
	            while (rs.next()) {
	                tong += rs.getLong("giaNhap") * rs.getInt("soLuong");
	            }
	            con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return tong;
	}

	
//	public ArrayList<TieuHuy> timTieuHuy(LocalDate dateFrom, LocalDate dateTo) {
//		ArrayList<TieuHuy> result = null;
//		DBConnect.open();
//		Connection con = DBConnect.getConnection();
//		
//		if (con != null) {
//			try {
//				PreparedStatement st = con.prepareStatement("SELECT th.*, ctp.* FROM TieuHuy th JOIN ChiTietSanPham ctp ON th.mact = ctp.mact WHERE th.ngayTieuHuy >= ? AND th.ngayTieuHuy <= ?");
//				st.setDate(1, Date.valueOf(dateFrom));
//				st.setDate(2, Date.valueOf(dateTo));
//				ResultSet rs = st.executeQuery();			
//				result = new ArrayList<>();
//				while (rs.next())
//					result.add(new TieuHuy(rs.getString("maSP"), rs.getString("loSX"), rs.getDate("ngayTieuHuy").toLocalDate(),
//							rs.getString("nguoiLap"), rs.getString("lyDo"), rs.getLong("thietHai")));
//				con.close();
//			}
//			catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return result;		
//	}
	public ArrayList<TieuHuy> timTieuHuy(LocalDate dateFrom, LocalDate dateTo) {
	    ArrayList<TieuHuy> result = new ArrayList<>();
	    DBConnect.open();
	    Connection con = DBConnect.getConnection();
	    
	    if (con != null) {
	        try {
	            PreparedStatement st = con.prepareStatement(
	                "SELECT ctp.masp,ctp.losx, th.ngaytieuhuy, th.nguoilap, th.lydo, th.thiethai FROM tieuhuy th " +
	                "JOIN chitietsanpham ctp ON th.mavach = ctp.mavach " +
	                "WHERE th.ngaytieuhuy >= ? AND th.ngaytieuhuy <= ?"
	            );
	            st.setDate(1, Date.valueOf(dateFrom));
	            st.setDate(2, Date.valueOf(dateTo));
	            
	            ResultSet rs = st.executeQuery();			
	            while (rs.next()) {
	                String maSP = rs.getString("masp");
	                String loSX = rs.getString("losx");
	                LocalDate ngayTieuHuy = rs.getDate("ngaytieuhuy").toLocalDate();
	                String nguoiLap = rs.getString("nguoilap");
	                String lyDo = rs.getString("lydo");
	                long thietHai = rs.getLong("thiethai");
	                result.add(new TieuHuy(maSP, loSX, ngayTieuHuy, nguoiLap, lyDo, thietHai));
	            }
	            con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return result;
	}


	public boolean addTieuHuy(TieuHuy th) {
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
			try {
				CallableStatement st = con.prepareCall("{call insertTH(?,?,?,?,?,?)}");
				st.setString(1, th.getMaSanPham());
				st.setString(2, th.getLoSanXuat());
				st.setDate(3, Date.valueOf(th.getNgayTieuHuy()));
				st.setString(4, th.getNguoiLap());
				st.setString(5, th.getLyDo());
				st.setLong(6, th.getThietHai());
				st.execute();
				
				con.close();
				return true;
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;		
	}
}
