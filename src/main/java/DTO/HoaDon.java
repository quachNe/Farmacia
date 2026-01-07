package DTO;
import java.time.LocalDate;

public class HoaDon {
	private String ma;
	private LocalDate ngayLap;
	private String nguoiLap;
	private int khachHang;
	private String toaThuoc; //nen doi thanh kieu du lieu khac
	private int diemTichLuy;
	private long tongTien;
	
	private KhachHang thongTinKhach = new KhachHang();
	
	public HoaDon() {}
	
	public HoaDon(String ma, LocalDate ngayLap, String nguoiLap, int khachHang, String toaThuoc, int diemTichLuy, long tongTien) {
		this.ma = ma;
		this.ngayLap = ngayLap;
		this.nguoiLap = nguoiLap;
		this.khachHang = khachHang;
		this.toaThuoc = toaThuoc;
		this.diemTichLuy = diemTichLuy;
		this.tongTien = tongTien;
	}
	
	public String getMa() {
		return ma;
	}
	
	public LocalDate getNgayLap() {
		return ngayLap;
	}
	
	public String getNguoiLap() {
		return nguoiLap;
	}
	
	public int getKhachHang() {
		return khachHang;
	}
	
	public String getToaThuoc() {
		return toaThuoc;
	}
	
	public int getDiemTichLuy() {
		return diemTichLuy;
	}
	
	public long getTongTien() {
		return tongTien;
	}
	
	public KhachHang getThongTinKhach() {
		return thongTinKhach;
	}
	
	public void setThongTinKhach(KhachHang kh) {
		thongTinKhach = kh;
	}
	
	public void setMa(String ma) {
		this.ma = ma;
	}

	public void setNgayLap(LocalDate ngayLap) {
		this.ngayLap = ngayLap;
	}

	public void setNguoiLap(String nguoiLap) {
		this.nguoiLap = nguoiLap;
	}

	public void setKhachHang(int khachHang) {
		this.khachHang = khachHang;
	}

	public void setToaThuoc(String toaThuoc) {
		this.toaThuoc = toaThuoc;
	}

	public void setDiemTichLuy(int diemTichLuy) {
		this.diemTichLuy = diemTichLuy;
	}

	public void setTongTien(long tongTien) {
		this.tongTien = tongTien;
	}
}