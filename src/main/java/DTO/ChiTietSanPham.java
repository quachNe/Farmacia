package DTO;

import java.time.LocalDate;

public class ChiTietSanPham {
    private String maSanPham; // Giữ lại maSanPham
    private String mavach; // Thêm thuộc tính mavach
    private String loSanXuat;
    private LocalDate ngaySanXuat;
    private LocalDate hanSuDung;
    private long phantram;
    private int soLuong;
    private SanPham sanPham= new SanPham();

   

    public ChiTietSanPham() {}

    public ChiTietSanPham(String maSanPham, String mavach, String loSanXuat, LocalDate hanSuDung) {
        this.maSanPham = maSanPham;
        this.loSanXuat = loSanXuat;
        this.hanSuDung = hanSuDung;
    }

    public ChiTietSanPham(String maSanPham, String mavach, String loSanXuat, LocalDate ngaySanXuat, LocalDate hanSuDung, long phantram, int soLuong) {
        this.maSanPham = maSanPham;
        this.mavach = mavach; // Khởi tạo mavach
        this.loSanXuat = loSanXuat;
        this.ngaySanXuat = ngaySanXuat;
        this.hanSuDung = hanSuDung;
        this.phantram = phantram;
        this.soLuong = soLuong;
    }

    // Không cần maChiTiet nữa, nên bỏ qua hàm liên quan

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getMavach() { // Thêm getter cho mavach
        return mavach;
    }

    public void setMavach(String mavach) { // Thêm setter cho mavach
        this.mavach = mavach;
    }

    public String getLoSanXuat() {
        return loSanXuat;
    }

    public void setLoSanXuat(String loSanXuat) {
        this.loSanXuat = loSanXuat;
    }

    public LocalDate getNgaySanXuat() {
        return ngaySanXuat;
    }

    public void setNgaySanXuat(LocalDate ngaySanXuat) {
        this.ngaySanXuat = ngaySanXuat;
    }

    public LocalDate getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(LocalDate hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    public long getphantram() {
        return phantram;
    }

    public void setphantram(long phantram) {
        this.phantram = phantram;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(String ma, String ten) {
        sanPham.setMa(ma);
        sanPham.setTen(ten);
    }
}