package DTO;

public class NhaSanXuat {
	private String ma;
	private String ten;
	private boolean trangThai;
	
	public NhaSanXuat() {}
	
	public NhaSanXuat(String ma, String ten, boolean tt) {
		this.ma = ma;
		this.ten = ten;
		this.trangThai = tt;
	}
	
	public String getMa() {
		return ma;
	}
	
	public String getTen() {
		return ten;
	}
	
	public void setMa(String ma) {
		this.ma = ma;
	}
	
	public void setTen(String ten) {
		this.ten = ten;
	}
	
	public boolean getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangthai) {
		this.trangThai = trangthai;
	}

	public String toString() {
		return ma + " - " + ten;
	}
}