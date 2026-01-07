package DTO;

public class NhaCungCap {
	private String ma;
	private String ten;
	private String diaChi;
	private String soDT;
	private String email;
	private boolean trangThai;
	
	public NhaCungCap() {}
	
	public NhaCungCap(String ma, String ten, String diaChi, String soDT, String email, boolean trangthai) {
		this.ma = ma;
		this.ten = ten;
		this.diaChi = diaChi;
		this.soDT = soDT;
		this.email = email;
		this.trangThai = trangthai;
	}

	public String getMa() {
		return ma;
	}

	public String getTen() {
		return ten;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	public String getSoDT() {
		return soDT;
	}

	public String getEmail() {
		return email;
	}
	
	public void setMa(String ma) {
		this.ma = ma;
	}
	
	public void setTen(String ten) {
		this.ten = ten;
	}
	
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	
	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String toString() {
		return ma + " - " + ten;
	}
}