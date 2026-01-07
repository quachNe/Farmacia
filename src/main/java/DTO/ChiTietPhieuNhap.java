package DTO;

import java.time.LocalDate;

public class ChiTietPhieuNhap {
    private String mavach; // Thay thế maChiTiet bằng mavach
    private long giaNhap;
    private int soLuong;

    private ChiTietSanPham chiTiet = new ChiTietSanPham(); // Khởi tạo đối tượng chi tiết sản phẩm

    public ChiTietPhieuNhap() {}

    public ChiTietPhieuNhap(String mavach, long giaNhap, int soLuong) {
        this.mavach = mavach; // Khởi tạo mavach
        this.giaNhap = giaNhap;
        this.soLuong = soLuong;
    }

    public ChiTietPhieuNhap(String maSanPham, String mavach, String loSanXuat, LocalDate ngaySanXuat, LocalDate hanSuDung, long giaNhap, long phantram, int soLuong) {
        this.mavach = mavach; // Khởi tạo mavach
        this.giaNhap = giaNhap;
        this.soLuong = soLuong;
        // Khởi tạo ChiTietSanPham với các tham số
        this.chiTiet = new ChiTietSanPham(maSanPham, mavach, loSanXuat, ngaySanXuat, hanSuDung, phantram, soLuong);
    }

    public ChiTietPhieuNhap(String mavach, long giaNhap, int soLuong, String maSanPham, String loSanXuat, LocalDate ngaySanXuat, LocalDate hanSuDung, long phantram) {
        this.mavach = mavach; // Khởi tạo mavach
        this.giaNhap = giaNhap;
        this.soLuong = soLuong;
        // Khởi tạo ChiTietSanPham với các tham số
        this.chiTiet = new ChiTietSanPham(maSanPham, mavach, loSanXuat, ngaySanXuat, hanSuDung, phantram, soLuong);
    }

    public long getGiaNhap() {
        return giaNhap;
    }
    public void  setGiaNhap( long GiaNhap) {
    	this.giaNhap=GiaNhap;
    	
    	
    	
    }
    public int getSoLuong() {
        return soLuong;
    }
    public void setSoLuong( int soLuong ){
    	this.soLuong=soLuong; 
    	
    	
    }

    public String getMavach() { // Getter cho mavach
        return mavach;
    }

    public ChiTietSanPham getChiTiet() {
        return chiTiet;
    }

    public void setChiTiet(ChiTietSanPham chiTiet) {
        this.chiTiet = chiTiet;
    }

    // Thêm phương thức getter cho phantram
    public long getphantram() {
        return chiTiet.getphantram();
    }

    public void setMavach(String mavach) { // Setter cho mavach
        this.mavach = mavach;
    }
}
