package DTO;

public class SanPham {
	private String ma;
	private String ten;
	private String loai;
	private String nhaSanXuat;
	private String quyCach;
	private String xuatXu;
	private boolean canKeToa;
	private boolean trangThai;
	private ChiTietSanPham chitiet;
public SanPham() {}
	
	public SanPham(String ma, String ten, String loai, String nhaSanXuat, String quyCach, String xuatXu,
			boolean canKeToa, boolean trangThai) {
		this.ma = ma;
		this.ten = ten;
		this.loai = loai;
		this.nhaSanXuat = nhaSanXuat;
		this.quyCach = quyCach;
		this.xuatXu = xuatXu;
		this.canKeToa = canKeToa;
		this.trangThai = trangThai;
	}
	
	public SanPham(String ma, String ten, String loai, String nhaSanXuat, String quyCach, String xuatXu,
			boolean canKeToa, boolean trangThai, ChiTietSanPham ct) {
		this.ma = ma;
		this.ten = ten;
		this.loai = loai;
		this.nhaSanXuat = nhaSanXuat;
		this.quyCach = quyCach;
		this.xuatXu = xuatXu;
		this.canKeToa = canKeToa;
		this.trangThai = trangThai;
		this.chitiet=ct;
	}
	public SanPham(String ma,String ten){
		
	}
	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getLoai() {
		return loai;
	}

	public void setLoai(String loai) {
		this.loai = loai;
	}

	public String getNhaSanXuat() {
		return nhaSanXuat;
	}

	public void setNhaSanXuat(String nhaSanXuat) {
		this.nhaSanXuat = nhaSanXuat;
	}

	public String getQuyCach() {
		return quyCach;
	}

	public void setQuyCach(String quyCach) {
		this.quyCach = quyCach;
	}

	public String getXuatXu() {
		return xuatXu;
	}

	public void setXuatXu(String xuatXu) {
		this.xuatXu = xuatXu;
	}

	public boolean isCanKeToa() {
		return canKeToa;
	}

	public void setCanKeToa(boolean canKeToa) {
		this.canKeToa = canKeToa;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}
	
	public ChiTietSanPham getChiTiet() {
		return chitiet;
	}

	public void setTaiKhoan(ChiTietSanPham chitiet) {
		this.chitiet = chitiet;
	}

	public String toString() {
		return ma + " - " + ten;
	}
}