package DAO;

import DTO.ChiTietSanPham;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ChiTietSanPhamDAO {

    public void themSanPham(ChiTietSanPham sp) {
        DBConnect.open();
        Connection connection = DBConnect.getConnection();
        String sql = "INSERT INTO chitietsanpham (mavach, masp, losx, ngaysx, hansd, phantram, soLuong) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, sp.getMavach()); // Use mavach as the first parameter
            ps.setString(2, sp.getMaSanPham()); // Foreign key
            ps.setString(3, sp.getLoSanXuat());
            ps.setDate(4, Date.valueOf(sp.getNgaySanXuat()));
            ps.setDate(5, Date.valueOf(sp.getHanSuDung()));
            ps.setLong(6, sp.getphantram());
            ps.setInt(7, sp.getSoLuong());
            ps.executeUpdate();
            System.out.println("Đã thêm sản phẩm vào cơ sở dữ liệu.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<ChiTietSanPham> layTatCaSanPham() {
        ArrayList<ChiTietSanPham> danhSachSanPham = new ArrayList<>();
        String sql = "SELECT * FROM chitietsanpham";

        try (Connection connection = DBConnect.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                LocalDate ngaySanXuat = null;
                LocalDate hanSuDung = null;

                if (rs.getDate("ngaysx") != null) {
                    ngaySanXuat = rs.getDate("ngaysx").toLocalDate();
                }

                if (rs.getDate("hansd") != null) {
                    hanSuDung = rs.getDate("hansd").toLocalDate();
                }

                ChiTietSanPham sp = new ChiTietSanPham(
                    rs.getString("masp"),       // Mã sản phẩm
                    rs.getString("mavach"),     // Mã vạch
                    rs.getString("losx"),       // Lô sản xuất
                    ngaySanXuat,                // Ngày sản xuất
                    hanSuDung,                  // Hạn sử dụng
                    rs.getLong("phantram"),       // Giá bán
                    rs.getInt("soLuong")        // Số lượng
                );

                danhSachSanPham.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Có thể thông báo cho người dùng hoặc ghi log ở đây
        }

        return danhSachSanPham;
    }

    // Tìm sản phẩm theo mã vạch
    public ChiTietSanPham timSanPhamTheoMavach(String mavach) {
        DBConnect.open();
        Connection connection = DBConnect.getConnection();
        String sql = "SELECT * FROM chitietsanpham WHERE mavach = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, mavach); // Use mavach
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ChiTietSanPham(
                    rs.getString("masp"),       // Mã sản phẩm
                    rs.getString("mavach"),     // Mã vạch
                    rs.getString("losx"),       // Lô sản xuất
                    rs.getDate("ngaysx") != null ? rs.getDate("ngaysx").toLocalDate() : null,
                    rs.getDate("hansd") != null ? rs.getDate("hansd").toLocalDate() : null,
                    rs.getLong("phantram"),       // Giá bán
                    rs.getInt("soLuong")        // Số lượng
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Xóa sản phẩm theo mã vạch
    public void xoaSanPhamTheoMavach(String mavach) {
        DBConnect.open();
        Connection connection = DBConnect.getConnection();
        String sql = "DELETE FROM chitietsanpham WHERE mavach = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, mavach); // Use mavach
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Đã xóa sản phẩm có mã vạch: " + mavach);
            } else {
                System.out.println("Không tìm thấy sản phẩm có mã vạch: " + mavach);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Cập nhật thông tin sản phẩm
    public void capNhatSanPham(ChiTietSanPham sp) {
        DBConnect.open();
        Connection connection = DBConnect.getConnection();
        String sql = "UPDATE chitietsanpham SET masp = ?, losx = ?, ngaysx = ?, hansd = ?, phantram = ?, soLuong = ? WHERE mavach = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, sp.getMaSanPham()); // Foreign key
            ps.setString(2, sp.getLoSanXuat());
            ps.setDate(3, Date.valueOf(sp.getNgaySanXuat()));
            ps.setDate(4, Date.valueOf(sp.getHanSuDung()));
            ps.setLong(5, sp.getphantram());
            ps.setInt(6, sp.getSoLuong());
            ps.setString(7, sp.getMavach()); // Use mavach
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Đã cập nhật sản phẩm có mã vạch: " + sp.getMavach());
            } else {
                System.out.println("Không tìm thấy sản phẩm có mã vạch: " + sp.getMavach());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public ChiTietSanPham timSanPhamTheoMaSP(String masp) {
        ChiTietSanPham sp = null;
        String sql = "SELECT * FROM chitietsanpham WHERE masp = ?";
        
        try (Connection con = DBConnect.getConnection(); 
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, masp);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    sp = new ChiTietSanPham(rs.getString("masp"), 
                                            rs.getString("mavach"),
                                            rs.getString("losx"), 
                                            rs.getDate("ngaysx").toLocalDate(), 
                                            rs.getDate("hansd").toLocalDate(),
                                            rs.getLong("giaban"),
                                            rs.getInt("soLuong"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sp;
    }
}
