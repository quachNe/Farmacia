package DAO;

import DTO.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import com.itextpdf.text.List;

import BUS.ChiTietSanPhamBUS;

public class HoaDonDAO {

    public ArrayList<HoaDon> getHoaDon() {
        ArrayList<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM hoadon";

        try ( Connection con = DBConnect.getConnection();
                // Tự động đóng kết nối
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String maHD = rs.getString(1);
                LocalDate ngaylap = LocalDate.parse(rs.getString(2));
                String nguoilap = rs.getString(3);
                int khachhang = rs.getInt(4);
                String toathuoc = rs.getString(5);
                int diemtichluy = rs.getInt(6);
                long tongtien = rs.getLong(7);

                HoaDon hd = new HoaDon(maHD, ngaylap, nguoilap, khachhang, toathuoc, diemtichluy, tongtien);
                list.add(hd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
	public ArrayList<HoaDon> searchHoaDon(String option, String key) {
	    ArrayList<HoaDon> resultList = new ArrayList<>();
	    DBConnect.open();
	    Connection con = DBConnect.getConnection();
	    String sql = "";

	    try {
	        if (con != null) {
	            if (option.equalsIgnoreCase("Mã hóa đơn")) {
	                sql = "SELECT * FROM hoadon WHERE maHD LIKE ?";
                } else if (option.equalsIgnoreCase("Ngày")) {
	                sql = "SELECT * FROM hoadon WHERE ngaylap LIKE ?";
	            } else if (option.equalsIgnoreCase("Người Lập")) {
	                sql = "SELECT * FROM hoadon WHERE nguoilap LIKE ?";
	            }

	            PreparedStatement pst = con.prepareStatement(sql);
	            pst.setString(1, "%" + key + "%");
	            ResultSet rs = pst.executeQuery();
	            
	            while (rs.next()) {
	                HoaDon hoaDon = new HoaDon();
	                hoaDon.setMa(rs.getString("maHD"));
	                LocalDate ngayLap = LocalDate.parse(rs.getString("ngaylap"));
	                hoaDon.setNgayLap(ngayLap);
	                hoaDon.setNguoiLap(rs.getString("nguoilap"));
	                hoaDon.setKhachHang(rs.getInt("khachhang"));
	                hoaDon.setToaThuoc(rs.getString("toathuoc"));
	                hoaDon.setDiemTichLuy(rs.getInt("diemtichluy"));
	                hoaDon.setTongTien(rs.getLong("tongtien"));
	                resultList.add(hoaDon);
	            }
	            con.close();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return resultList;
	}
	
    public ChiTietSanPham getChiTietSP(String maSP, String loSX) {
        ChiTietSanPham ct = null; // Khởi tạo là null
        String sql = "SELECT mavach, masp, losx, ngaysx, hansd, phantram, soLuong FROM chitietsanpham WHERE masp = ? AND losx = ?";

        try ( Connection con = DBConnect.getConnection();
                
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, maSP);
            pstmt.setString(2, loSX);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                ct = new ChiTietSanPham();
                ct.setMavach(rs.getString("mavach")); // Chỉnh sửa theo tên cột trong bảng
                ct.setMaSanPham(rs.getString("masp"));
                ct.setLoSanXuat(rs.getString("losx"));
                ct.setphantram(rs.getLong("phantram"));
                ct.setHanSuDung(rs.getDate("hansd").toLocalDate());
                ct.setSoLuong(rs.getInt("soLuong"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ct; // Trả về null nếu không tìm thấy
    }

    public ArrayList<ChiTietHoaDon> getChiTietHD(String maHD) {
        ArrayList<ChiTietHoaDon> result = new ArrayList<>();
        String sql = "SELECT C.mavach, C.masp, tenSP, losx, hansd, H.giaban, H.soLuong " +
                     "FROM chitietsanpham C " +
                     "JOIN ChiTietHoaDon H ON C.mavach = H.mavach " +
                     "JOIN SanPham S ON C.masp = S.masp " +
                     "WHERE maHD = ?";

        try ( Connection con = DBConnect.getConnection();
                
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, maHD);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ChiTietHoaDon cthd = new ChiTietHoaDon(rs.getString("mavach"), rs.getLong("H.giaban"), rs.getInt("H.soLuong"));
                ChiTietSanPham ctsp = new ChiTietSanPham(rs.getString("mavach"), rs.getString("masp"), rs.getString("losx"), rs.getDate("hansd").toLocalDate());
                ctsp.setSanPham(rs.getString("masp"), rs.getString("tenSP"));
                cthd.setChiTiet(ctsp);
                result.add(cthd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<KhachHang> getKhachHang() {
        ArrayList<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM khachhang";

        try ( Connection con = DBConnect.getConnection();
                
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                list.add(new KhachHang(rs.getInt("makh"), rs.getString("hoten"), rs.getDate("ngaysinh").toLocalDate(), rs.getString("gioiTinh"), rs.getString("soDT"), rs.getInt("diem")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addKhachHang(String hoten, Date ngaysinh, String gioitinh, String sdt, int diem) {
        boolean result = false;
        String sql = "INSERT INTO khachhang(hoten, ngaysinh, gioitinh, sodt, diem) VALUES (?, ?, ?, ?, ?)";

        try ( Connection con = DBConnect.getConnection();
                
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, hoten);
            pst.setDate(2, ngaysinh);
            pst.setString(3, gioitinh);
            pst.setString(4, sdt);
            pst.setInt(5, diem);
            result = pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public ArrayList<KhachHang> getAllKhachHang() {
        ArrayList<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM khachhang";
        try (Connection con = DBConnect.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                KhachHang kh = new KhachHang(
                    rs.getInt("makh"),
                    rs.getString("hoTen"),
                    rs.getDate("ngaySinh").toLocalDate(),
                    rs.getString("gioiTinh"),
                    rs.getString("soDT"),
                    rs.getInt("diem")
                );
                list.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateDiem(int makh, int diem) {
        boolean result = false;
        String sql = "UPDATE khachhang SET diem = ? WHERE makh = ?";

        try (Connection con = DBConnect.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, diem);
            pst.setInt(2, makh);
            result = pst.executeUpdate() > 0; // Trả về true nếu có dòng được cập nhật

        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật điểm khách hàng: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

 // Phương thức trong lớp DAO
    public int getDiem(int makh) {
        int result = -1; // Giá trị mặc định khi không tìm thấy
        String sql = "SELECT diem FROM khachhang WHERE makh = ?";

        try (Connection con = DBConnect.getConnection(); // Đảm bảo mở kết nối
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, makh);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                result = rs.getInt("diem");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<String> getLoSX(String maSP) {
        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT losx FROM chitietsanpham WHERE masp = ? ORDER BY hansd";

        try ( Connection con = DBConnect.getConnection();
                
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, maSP);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("losx"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

//    public boolean updateSoLuong(String masp, String losx, int soLuongBanRa) {
//        boolean result = false;
//        String sql = "UPDATE chitietsanpham SET soLuong = soLuong - ? WHERE masp = ? AND losx = ?";
//
//        try ( Connection con = DBConnect.getConnection();
//                
//             PreparedStatement pst = con.prepareStatement(sql)) {
//
//            pst.setInt(1, soLuongBanRa);
//            pst.setString(2, masp);
//            pst.setString(3, losx);
//            result = pst.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

    public HoaDon getHoaDon(String maHD) {
        HoaDon hoaDon = null;
        String sql = "SELECT * FROM hoadon H LEFT JOIN khachhang K ON H.khachHang = K.maKH WHERE maHD = ?";

        try ( Connection con = DBConnect.getConnection();
                
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, maHD);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                hoaDon = new HoaDon();
                hoaDon.setMa(rs.getString("maHD"));
                LocalDate ngayLap = LocalDate.parse(rs.getString("ngaylap"));
                hoaDon.setNgayLap(ngayLap);
                hoaDon.setNguoiLap(rs.getString("nguoilap"));
                hoaDon.setKhachHang(rs.getInt("khachhang"));
                hoaDon.setToaThuoc(rs.getString("toathuoc"));
                hoaDon.setDiemTichLuy(rs.getInt("diemtichluy"));
                hoaDon.setTongTien(rs.getLong("tongtien"));
            }
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hoaDon;
    }
    public boolean updateSoLuong(String mavach, int soluong, String losx, String masp) {
        ChiTietSanPhamBUS ctsp = new ChiTietSanPhamBUS();
        ArrayList<ChiTietSanPham> list = ctsp.hienThiTatCaSanPham();
        ChiTietSanPham sp = null;

        // Tìm ChiTietSanPham có cùng masp và losx
        for (ChiTietSanPham item : list) {
            if (item.getMaSanPham().equals(masp) && item.getLoSanXuat().equals(losx)) {
                sp = item;
                break;
            }
        }

        // Kiểm tra nếu không tìm thấy sản phẩm phù hợp
        if (sp == null) {
            System.out.println("Sản phẩm không tìm thấy với masp: " + masp + " và losx: " + losx);
            return false;
        }

        int soluongMoi = sp.getSoLuong() - soluong;
        if (soluongMoi < 0) {
            System.out.println("Số lượng mới không hợp lệ (âm): " + soluongMoi);
            return false;
        }

        String sql = "UPDATE chitietsanpham SET soLuong = ? WHERE masp = ? AND losx = ? ";
        try (Connection con = DBConnect.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, soluongMoi);
            pstmt.setString(2, masp);
            pstmt.setString(3, losx);
           

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Cập nhật số lượng thành công cho mã vạch: " + mavach);
                return true;
            } else {
                System.out.println("Không tìm thấy sản phẩm cần cập nhật trong cơ sở dữ liệu.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addHoaDon(HoaDon hd, ArrayList<ChiTietHoaDon> spList, KhachHang kh, boolean useDiem, int diemConLai) {
        boolean result = false;
        Connection con = null;
        CallableStatement st = null;

        try {
            DBConnect.open();
            con = DBConnect.getConnection();

            if (con != null) {
                if (useDiem && kh != null) {
                    result = updateDiem(kh.getMa(), diemConLai);
                    if (!result) {
                        System.out.println("Lỗi khi cập nhật điểm còn lại của khách hàng.");
                        return false;
                    }
                }

                // Thêm hóa đơn
                st = con.prepareCall("INSERT INTO HoaDon (mahd, ngayLap, nguoiLap, khachHang, toaThuoc, diemTichLuy, tongTien) VALUES (?, ?, ?, ?, ?, ?, ?)");
                st.setString(1, hd.getMa());
                st.setDate(2, Date.valueOf(hd.getNgayLap()));
                st.setString(3, hd.getNguoiLap());
                st.setString(4, (kh != null ? String.valueOf(kh.getMa()) : null));
                st.setString(5, hd.getToaThuoc());
                st.setInt(6, hd.getDiemTichLuy());
                st.setLong(7, hd.getTongTien());
                result = st.executeUpdate() > 0;
                if (!result) {
                    System.out.println("Lỗi khi thêm hóa đơn vào cơ sở dữ liệu.");
                    return false;
                }

                // Thêm chi tiết hóa đơn cho từng sản phẩm
                String insertCTHDQuery = "INSERT INTO chitiethoadon (maHD, mavach, giaBan, soLuong) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt = con.prepareStatement(insertCTHDQuery)) {
                    for (ChiTietHoaDon sp : spList) {
                        pstmt.setString(1, hd.getMa());
                        pstmt.setString(2, sp.getChiTiet().getMavach());
                        pstmt.setDouble(3, sp.getgiaban());
                        pstmt.setInt(4, sp.getSoLuong());
                        pstmt.executeUpdate();
                    }
                }

                // Cập nhật số lượng cho các sản phẩm sau khi đã thêm vào hóa đơn
                for (ChiTietHoaDon sp : spList) {
                    boolean updated = updateSoLuong(sp.getChiTiet().getMavach(), sp.getSoLuong(), sp.getChiTiet().getLoSanXuat(), sp.getChiTiet().getMaSanPham());
                    if (!updated) {
                        System.out.println("Lỗi khi cập nhật số lượng sản phẩm.");
                        return false;
                    }
                }

                // Cập nhật điểm tích lũy mới cho khách hàng
                if (kh != null) {
                    if (useDiem) {
                        result = updateDiem(kh.getMa(), diemConLai);
                        if (!result) {
                            System.out.println("Lỗi khi cập nhật điểm còn lại của khách hàng.");
                            return false;
                        }
                    }
                    // Thêm điểm tích lũy mới
                    result = updateDiem(kh.getMa(), getDiem(kh.getMa()) + hd.getDiemTichLuy());
                    if (!result) {
                        System.out.println("Lỗi khi cập nhật điểm tích lũy mới cho khách hàng.");
                        return false;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        } finally {
            try {
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Lỗi khi đóng kết nối: " + e.getMessage());
            }
        }

        return result;
    }



}