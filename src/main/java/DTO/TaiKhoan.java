package DTO;

public class TaiKhoan {
	private String maNguoiDung;
	private String username;
	private int quyen;
	
	public TaiKhoan() {}

	public TaiKhoan(String maNguoiDung, String username, int quyen) {
		this.maNguoiDung = maNguoiDung;
		this.username = username;
		this.quyen = quyen;
	}
	
	public TaiKhoan(String username) {
		this.username = username;
	} 
	public String getMaNguoiDung() {
		return maNguoiDung;
	}

	public String getUsername() {
		return username;
	}
	
	public int getQuyen() {
		return quyen;
	}
}