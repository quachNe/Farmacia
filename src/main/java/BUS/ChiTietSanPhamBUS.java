package BUS;

import DAO.ChiTietSanPhamDAO;
import DTO.ChiTietSanPham;

import java.time.LocalDate;
import java.util.ArrayList;

public class ChiTietSanPhamBUS {
    private ChiTietSanPhamDAO chiTietSanPhamDAO;

    public ChiTietSanPhamBUS() {
        chiTietSanPhamDAO = new ChiTietSanPhamDAO();
    }

    // Thêm sản phẩm mới
    public void themSanPham(String maSanPham, String mavach, String loSanXuat, LocalDate ngaySanXuat, LocalDate hanSuDung, long giaBan, int soLuong) {
        // Kiểm tra logic nghiệp vụ: các trường không được rỗng, giá bán > 0, số lượng > 0, ngày sản xuất trước hạn sử dụng
        if (ngaySanXuat.isBefore(hanSuDung) && giaBan > 0 && soLuong > 0) {
            ChiTietSanPham sp = new ChiTietSanPham(maSanPham, mavach, loSanXuat, ngaySanXuat, hanSuDung, giaBan, soLuong);
            chiTietSanPhamDAO.themSanPham(sp);
            System.out.println("Đã thêm sản phẩm.");
        } else {
            System.out.println("Dữ liệu không hợp lệ. Vui lòng kiểm tra lại.");
        }
    }

    // Cập nhật sản phẩm
    public void capNhatSanPham(String mavach, String maSanPham, String loSanXuat, LocalDate ngaySanXuat, LocalDate hanSuDung, long giaBan, int soLuong) {
        // Kiểm tra logic nghiệp vụ tương tự
        if (ngaySanXuat.isBefore(hanSuDung) && giaBan > 0 && soLuong > 0) {
            ChiTietSanPham sp = new ChiTietSanPham(maSanPham, mavach, loSanXuat, ngaySanXuat, hanSuDung, giaBan, soLuong);
            chiTietSanPhamDAO.capNhatSanPham(sp);
            System.out.println("Đã cập nhật sản phẩm.");
        } else {
            System.out.println("Dữ liệu không hợp lệ. Vui lòng kiểm tra lại.");
        }
    }

    // Xóa sản phẩm theo mã vạch
    public void xoaSanPham(String mavach) {
        if (mavach != null && !mavach.isEmpty()) {
            chiTietSanPhamDAO.xoaSanPhamTheoMavach(mavach);
            System.out.println("Đã xóa sản phẩm có mã vạch: " + mavach);
        } else {
            System.out.println("Mã vạch không hợp lệ.");
        }
    }

    // Tìm sản phẩm theo mã vạch
    public ChiTietSanPham timSanPhamTheoMaVach(String mavach) {
        if (mavach != null && !mavach.isEmpty()) {
            ChiTietSanPham sp = chiTietSanPhamDAO.timSanPhamTheoMavach(mavach);
            if (sp != null) {
                System.out.println("Đã tìm thấy sản phẩm có mã vạch: " + mavach);
            } else {
                System.out.println("Không tìm thấy sản phẩm.");
            }
            return sp;
        } else {
            System.out.println("Mã vạch không hợp lệ.");
            return null;
        }
    }
    public ChiTietSanPham timSanPhamTheoMaSP(String masp) {
        if (masp != null && !masp.isEmpty()) {
            ChiTietSanPham sp = chiTietSanPhamDAO.timSanPhamTheoMaSP(masp);
            if (sp != null) {
                System.out.println("Đã tìm thấy sản phẩm có mã sản phẩm: " + masp);
            } else {
                System.out.println("Không tìm thấy sản phẩm.");
            }
            return sp;
        } else {
            System.out.println("Mã sản phẩm không hợp lệ.");
            return null;
        }
    }

    // Hiển thị tất cả sản phẩm
    public ArrayList<ChiTietSanPham> hienThiTatCaSanPham() {
        ArrayList<ChiTietSanPham> danhSachSanPham = chiTietSanPhamDAO.layTatCaSanPham();
        return danhSachSanPham;
    }
}
