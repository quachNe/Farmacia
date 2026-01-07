package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ThongKeDAO {
	private HashMap<String, Object> init(LocalDate dateFrom, LocalDate dateTo) {
		HashMap<String, Object> dic = new LinkedHashMap<>();
		while (dateFrom.isBefore(dateTo)) {
			dic.put(dateFrom.format(DateTimeFormatter.ofPattern("yyyy-MM")), null);
			dateFrom = dateFrom.plusMonths(1);
		}
		return dic;
	}
	
	public HashMap<String, Object> sanPhamNhap(String maSP, String maNCC, LocalDate dateFrom, LocalDate dateTo, int numberFrom, int numberTo, String tieuChi) {
		HashMap<String, Object> result = init(dateFrom, dateTo);
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
			try {
				PreparedStatement st;
				if (tieuChi.equals("Theo giá nhập"))
					st = con.prepareStatement
						("Select year(ngayLap), month(ngayLap), sum(giaNhap*C.soLuong) from chiTietSanPham S join chiTietPhieuNhap C on S.mavach = C.mavach join phieuNhap P on C.maPN = P.maPN"
							+ " where maSP = ? and ngayLap >= ? and ngayLap <= ? "
							+ (!maNCC.equals("Tất cả") ? "and nhaCungCap = '" + maNCC + "'" : "")
							+ " group by year(ngayLap), month(ngayLap)"
							+ " having sum(giaNhap*C.soLuong) >= ? and sum(giaNhap*C.soLuong) <= ?"
							+ " order by year(ngayLap), month(ngayLap)");
				else
					st = con.prepareStatement
						("Select year(ngayLap), month(ngayLap), sum(C.soLuong) from chiTietSanPham S join chiTietPhieuNhap C on S.mavach = C.mavach join phieuNhap P on C.maPN = P.maPN"
							+ " where maSP = ? and ngayLap >= ? and ngayLap <= ? "
							+ (!maNCC.equals("Tất cả") ? " and nhaCungCap = '" + maNCC + "'" : "")
							+ " group by year(ngayLap), month(ngayLap)"
							+ " having sum(C.soLuong) >= ? and sum(C.soLuong) <= ?"
							+ " order by year(ngayLap), month(ngayLap)");
								
				st.setString(1, maSP);
				st.setDate(2, Date.valueOf(dateFrom));
				st.setDate(3, Date.valueOf(dateTo));
				st.setInt(4, numberFrom);
				st.setInt(5, numberTo);
				
				ResultSet rs = st.executeQuery();
				while (rs.next())
					result.put(LocalDate.of(rs.getInt(1), rs.getInt(2), 1).format(DateTimeFormatter.ofPattern("yyyy-MM")), rs.getInt(3));
				
				for (String date: result.keySet())
					if (result.get(date) == null) result.put(date, 0);
				con.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public HashMap<String, Object> nhaCungCapNhap(String maNCC, LocalDate dateFrom, LocalDate dateTo, int numberFrom, int numberTo, String tieuChi) {
		HashMap<String, Object> result = init(dateFrom, dateTo);
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
			try {
				PreparedStatement st;
				if (tieuChi.equals("Theo giá nhập"))
					st = con.prepareStatement
						("Select year(ngayLap), month(ngayLap), sum(giaNhap*soLuong) from chiTietPhieuNhap C join phieuNhap P on C.maPN = P.maPN"
							+ " where nhaCungCap = ? and ngayLap >= ? and ngayLap <= ? "
							+ " group by year(ngayLap), month(ngayLap)"
							+ " having sum(giaNhap*soLuong) >= ? and sum(giaNhap*soLuong) <= ?"
							+ " order by year(ngayLap), month(ngayLap)");
				else
					st = con.prepareStatement
						("Select year(ngayLap), month(ngayLap), sum(soLuong) from chiTietPhieuNhap C join phieuNhap P on C.maPN = P.maPN"
							+ " where nhaCungCap = ? and ngayLap >= ? and ngayLap <= ? "
							+ " group by year(ngayLap), month(ngayLap)"
							+ " having sum(soLuong) >= ? and sum(soLuong) <= ?"
							+ " order by year(ngayLap), month(ngayLap)");
								
				st.setString(1, maNCC);
				st.setDate(2, Date.valueOf(dateFrom));
				st.setDate(3, Date.valueOf(dateTo));
				st.setInt(4, numberFrom);
				st.setInt(5, numberTo);
				
				ResultSet rs = st.executeQuery();
				while (rs.next())
					result.put(LocalDate.of(rs.getInt(1), rs.getInt(2), 1).format(DateTimeFormatter.ofPattern("yyyy-MM")), rs.getInt(3));
				
				for (String date: result.keySet())
					if (result.get(date) == null) result.put(date, 0);
				
				con.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public ArrayList<Object[]> sanPhamTheoNCC(String maNCC, LocalDate dateFrom, LocalDate dateTo, int numberFrom, int numberTo, String tieuChi) {
		ArrayList<Object[]> result = null;
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
			try {
				PreparedStatement st;
				if (tieuChi.equals("Theo giá nhập"))
					st = con.prepareStatement
							("Select maSP, sum(giaNhap*C.soLuong) from chiTietSanPham S join chiTietPhieuNhap C on S.mavach = C.mavach join phieuNhap P on C.maPN = P.maPN"
								+ " where nhaCungCap = ? and ngayLap >= ? and ngayLap <= ? "
								+ " group by maSP having sum(giaNhap*C.soLuong) >= ? and sum(giaNhap*C.soLuong) <= ?");
				else
					st = con.prepareStatement
							("Select maSP, sum(C.soLuong) from chiTietSanPham S join chiTietPhieuNhap C on S.mavach = C.mavach join phieuNhap P on C.maPN = P.maPN"
									+ " where nhaCungCap = ? and ngayLap >= ? and ngayLap <= ? "
									+ " group by maSP having sum(C.soLuong) >= ? and sum(C.soLuong) <= ?");
								
				st.setString(1, maNCC);
				st.setDate(2, Date.valueOf(dateFrom));
				st.setDate(3, Date.valueOf(dateTo));
				st.setInt(4, numberFrom);
				st.setInt(5, numberTo);
				ResultSet rs = st.executeQuery();
				result = new ArrayList<>();
				while (rs.next()) result.add(new Object[] {rs.getString(1), rs.getLong(2)});
				con.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public HashMap<String, Object> sanPhamBan(String maSP, LocalDate dateFrom, LocalDate dateTo, int numberFrom, int numberTo, String tieuChi) {
		HashMap<String, Object> result = init(dateFrom, dateTo);
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
			try {
				PreparedStatement st;
				if (tieuChi.equals("Theo giá bán"))
					st = con.prepareStatement
						("Select year(ngayLap), month(ngayLap), sum(C.giaBan*C.soLuong) from chiTietSanPham S join chiTietHoaDon C on S.mavach = C.mavach join hoaDon H on C.maHD = H.maHD"
							+ " where maSP = ? and ngayLap >= ? and ngayLap <= ? "
							+ " group by year(ngayLap), month(ngayLap)"
							+ " having sum(C.giaBan*C.soLuong) >= ? and sum(C.giaBan*C.soLuong) <= ?"
							+ " order by year(ngayLap), month(ngayLap)");
				else
					st = con.prepareStatement
						("Select year(ngayLap), month(ngayLap), sum(C.soLuong) from chiTietSanPham S join chiTietHoaDon C on S.mavach = C.mavach join hoaDon H on C.maHD = H.maHD"
							+ " where maSP = ? and ngayLap >= ? and ngayLap <= ? "
							+ " group by year(ngayLap), month(ngayLap)"
							+ " having sum(C.soLuong) >= ? and sum(C.soLuong) <= ?"
							+ " order by year(ngayLap), month(ngayLap)");
								
				st.setString(1, maSP);
				st.setDate(2, Date.valueOf(dateFrom));
				st.setDate(3, Date.valueOf(dateTo));
				st.setInt(4, numberFrom);
				st.setInt(5, numberTo);
				
				ResultSet rs = st.executeQuery();
				while (rs.next())
					result.put(LocalDate.of(rs.getInt(1), rs.getInt(2), 1).format(DateTimeFormatter.ofPattern("yyyy-MM")), rs.getInt(3));
				
				for (String date: result.keySet())
					if (result.get(date) == null) result.put(date, 0);
				con.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public HashMap<String, long[]> doanhThu(LocalDate dateFrom, LocalDate dateTo) {		
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		HashMap<String, long[]> result = new LinkedHashMap<>();
		
		if (con != null) {
			try {
				LocalDate temp = dateFrom;
				while (temp.isBefore(dateTo)) {
					result.put(temp.format(DateTimeFormatter.ofPattern("yyyy-MM")), new long[] {0,0});
					temp = temp.plusMonths(1);
				}
				
				PreparedStatement st = con.prepareStatement("select year(ngayLap), month(ngayLap), sum(tongTien) from phieuNhap where ngayLap >= ? and ngayLap <= ? group by year(ngayLap), month(ngayLap)");
				st.setDate(1, Date.valueOf(dateFrom));
				st.setDate(2, Date.valueOf(dateTo));
				ResultSet rs = st.executeQuery();
				while (rs.next()) {
					String key = LocalDate.of(rs.getInt(1), rs.getInt(2), 1).format(DateTimeFormatter.ofPattern("yyyy-MM"));
					result.get(key)[0] = rs.getLong(3);
				}
				
				st = con.prepareStatement("select year(ngayLap), month(ngayLap), sum(tongTien) from hoaDon where ngayLap >= ? and ngayLap <= ? group by year(ngayLap), month(ngayLap)");
				st.setDate(1, Date.valueOf(dateFrom));
				st.setDate(2, Date.valueOf(dateTo));
				
				rs = st.executeQuery();
				while (rs.next()) {
					String key = LocalDate.of(rs.getInt(1), rs.getInt(2), 1).format(DateTimeFormatter.ofPattern("yyyy-MM"));
					result.get(key)[1] = rs.getLong(3);
				}
				
				con.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	
	public ArrayList<Object[]> sanPhamBanTheoThang(int thang, int nam) {
	    ArrayList<Object[]> result = new ArrayList<>();
	    DBConnect.open();
	    Connection con = DBConnect.getConnection();

	    if (con != null) {
	        try {
	            String query = "SELECT sp.masp, sp.tensp, SUM(cthd.soLuong) AS tongSoLuongBan, "
	                         + "SUM(cthd.soLuong * cthd.giaban) AS doanhThu "
	                         + "FROM hoadon hd "
	                         + "JOIN chitiethoadon cthd ON hd.mahd = cthd.mahd "
	                         + "JOIN chitietsanpham ctsp ON cthd.mavach = ctsp.mavach "
	                         + "JOIN sanpham sp ON ctsp.masp = sp.masp "
	                         + "WHERE MONTH(hd.ngaylap) = ? AND YEAR(hd.ngaylap) = ? "
	                         + "AND DAY(hd.ngaylap) <= 28 " 
	                         + "GROUP BY sp.masp, sp.tensp "
	                         + "ORDER BY tongSoLuongBan DESC";
	                         
	            PreparedStatement st = con.prepareStatement(query);
	            st.setInt(1, thang); 
	            st.setInt(2, nam);   
	            
	            ResultSet rs = st.executeQuery();
	            
	            int soThuTu = 1; 
	            while (rs.next()) {
	                String maSanPham = rs.getString("masp");
	                String tenSanPham = rs.getString("tensp");
	                int tongSoLuongBan = rs.getInt("tongSoLuongBan");
	                double doanhThu = rs.getDouble("doanhThu");
	                
	                result.add(new Object[] {soThuTu, maSanPham, tenSanPham, tongSoLuongBan, doanhThu});
	                soThuTu++;  
	            }
	            
	            con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    return result;
	}

	
	
	public HashMap<String, Object[]> laySanPhamBanChayNhatTheoThang(int month, int year) {
	    DBConnect.open();
	    Connection con = DBConnect.getConnection();
	    HashMap<String, Object[]> sanPhamBanChay = new HashMap<>();
	    
	    if (con != null) {
	        try {
	            PreparedStatement st = con.prepareStatement(
	                "SELECT sp.masp, sp.tensp, a.anh, SUM(hd.soLuong) AS tongSoLuong " +
	                "FROM sanpham sp " +
	                "JOIN chitietsanpham ctsp ON sp.masp = ctsp.masp " +
	                "JOIN chitiethoadon hd ON ctsp.mact = hd.mact " +
	                "JOIN hoadon h ON hd.mahd = h.mahd " +
	                "JOIN anh a ON sp.masp = a.mathuoc " +
	                "WHERE MONTH(h.ngayLap) = ? AND YEAR(h.ngayLap) = ? " +
	                "GROUP BY sp.masp, sp.tensp, a.anh " +
	                "ORDER BY tongSoLuong DESC " +
	                "LIMIT 1"
	            );
	            st.setInt(1, month);
	            st.setInt(2, year);
	            
	            ResultSet rs = st.executeQuery();
	            if (rs.next()) {
	                String masp = rs.getString(1);  // Product ID
	                String tensp = rs.getString(2);  // Product Name
	                String hinhAnh = rs.getString(3); // Product Image
	                int tongSoLuong = rs.getInt(4);  // Total quantity sold
	                sanPhamBanChay.put(masp + " - " + tensp, new Object[]{tongSoLuong, hinhAnh});
	            }

	            con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return sanPhamBanChay;
	}

}
