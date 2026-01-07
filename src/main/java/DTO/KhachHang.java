package DTO;
import java.time.LocalDate;

public class KhachHang {
	private int ma;
	private String hoTen;
	private LocalDate ngaySinh;
	private String gioiTinh;
	private String soDT;
	private int diem;
	
	public KhachHang() {
		this.gioiTinh = "Nam";
	}
	
	public KhachHang(String hoTen, LocalDate ngaySinh, String gioiTinh, String soDT) {
		this.hoTen = hoTen;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.soDT = soDT;
		this.diem = 0;
	}
	
	public KhachHang(int ma, String hoTen, LocalDate ngaySinh, String gioiTinh, String soDT, int diem) {
		this.ma = ma;
		this.hoTen = hoTen;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.soDT = soDT;
		this.diem = diem;
	}
	
	public int getMa() {
		return ma;
	}
	
	public String getHoTen() {
		return hoTen;
	}
	
	public LocalDate getNgaySinh() {
		return ngaySinh;
	}
	
	public String getGioiTinh() {
		return gioiTinh;
	}
	
	public String getSoDT() {
		return soDT;
	}
	
	public int getDiem() {
		return diem;
	}
	
	public String toString() {
		return soDT + " - " + hoTen;
	}
}