package DTO;

import java.time.LocalDate;

public class ChiTietHoaDon {
    private String mavach; // Thay thế maChiTiet bằng mavach
    private double giaban;
    private int soLuong;
    private ChiTietSanPham chiTiet = new ChiTietSanPham(); // Đối tượng ChiTietSanPham

    // Constructor không tham số
    public ChiTietHoaDon() {}

    // Constructor cho các trường hợp chỉ có giá bán và số lượng (có thể dùng cho tích điểm)
    public ChiTietHoaDon(double giaban, int soLuong) {
        this.mavach = "-1"; // Dùng "-1" như mã tạm thời
        this.giaban = giaban;
        this.soLuong = soLuong;
    }

    // Constructor đầy đủ mã chi tiết, giá bán và số lượng
    public ChiTietHoaDon(String mavach, double giaban, int soLuong) {
        this.mavach = mavach;
        this.giaban = giaban;
        this.soLuong = soLuong;
    }

    // Constructor đầy đủ với thông tin về chi tiết sản phẩm
    public ChiTietHoaDon(String mavach, double giaban, int soLuong, String maSanPham, String loSanXuat, LocalDate ngaySanXuat, LocalDate hanSuDung, long giabanSanPham) {
        this.mavach = mavach; // Khởi tạo mavach
        this.giaban = giaban;
        this.soLuong = soLuong;
        this.chiTiet = new ChiTietSanPham(maSanPham, mavach, loSanXuat, ngaySanXuat, hanSuDung, giabanSanPham, soLuong);
    }

    // Getter cho giaban
    public double getgiaban() {
        return giaban;
    }

    // Setter cho giaban (nếu cần)
    public void setgiaban(double giaban) {
        this.giaban = giaban;
    }

    // Getter cho soLuong
    public int getSoLuong() {
        return soLuong;
    }

    // Setter cho soLuong
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    // Getter cho mavach
    public String getMavach() {
        return mavach;
    }

    // Getter cho chiTietSanPham
    public ChiTietSanPham getChiTiet() {
        return chiTiet;
    }

    // Setter cho chiTietSanPham
    public void setChiTiet(ChiTietSanPham chiTiet) {
        this.chiTiet = chiTiet;
    }
}
