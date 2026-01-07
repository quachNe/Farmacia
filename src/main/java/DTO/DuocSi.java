package DTO;

public class DuocSi {
	private String ma;
	private String ten;
	private String soDT;
	private String email;
	private boolean trangThai;
	private TaiKhoan taiKhoan;
	public DuocSi() {}
	
	public DuocSi(String ma, String ten, String soDT, String email,TaiKhoan taiKhoan,boolean trangThai) {
		this.ma = ma;
		this.ten = ten;
		this.soDT = soDT;
		this.email = email;
		this.taiKhoan = taiKhoan;
		this.trangThai = trangThai;
	}

	public DuocSi(String ma, String ten, String soDT, String email) {
		super();
		this.ma = ma;
		this.ten = ten;
		this.soDT = soDT;
		this.email = email;
	}

	public String getMa() {
		return ma;
	}

	public String getTen() {
		return ten;
	}

	public String getSoDT() {
		return soDT;
	}

	public String getEmail() {
		return email;
	}

	public boolean getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}
	
	public String toString() {
		return ma + " - " + ten;
	}	
}