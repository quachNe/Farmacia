package DTO;

import java.time.LocalDate;

public class TieuHuy {
	private String maSanPham;
	private String loSanXuat;
	private LocalDate ngayTieuHuy;
	private String nguoiLap;
	private String lyDo;
	private long thietHai;
	
	public TieuHuy(String maSanPham, String loSanXuat, LocalDate ngayTieuHuy, String nguoiLap, String lyDo, long thietHai) {
		this.maSanPham = maSanPham;
		this.loSanXuat = loSanXuat;
		this.ngayTieuHuy = ngayTieuHuy;
		this.nguoiLap = nguoiLap;
		this.lyDo = lyDo;
		this.thietHai = thietHai;
	}
	
	public String getMaSanPham() {
		return maSanPham;
	}
	
	public String getLoSanXuat() {
		return loSanXuat;
	}
	
	public LocalDate getNgayTieuHuy() {
		return ngayTieuHuy;
	}

	public String getNguoiLap() {
		return nguoiLap;
	}

	public String getLyDo() {
		return lyDo;
	}
	
	public long getThietHai() {
		return thietHai;
	}
}