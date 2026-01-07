package DTO;
import java.time.LocalDate;
import java.util.Objects;

public class PhieuNhap {
	private String ma;
	private LocalDate ngayLap;
	private String nguoiLap;
	private String nhaCungCap;
	private long tongTien;
	
	public PhieuNhap() {}
	
	public PhieuNhap(String ma, LocalDate ngayLap, String nguoiLap, String nhaCungCap, long tongTien) {
		this.ma = ma;
		this.ngayLap = ngayLap;
		this.nguoiLap = nguoiLap;
		this.nhaCungCap = nhaCungCap;
		this.tongTien = tongTien;
	}

	public String getMa() {
		return ma;
	}
	
	public void setMa(String ma) {
		this.ma = ma;
	}

	public LocalDate getNgayLap() {
		return ngayLap;
	}

	public String getNguoiLap() {
		return nguoiLap;
	}

	public String getNhaCungCap() {
		return nhaCungCap;
	}

	public long getTongTien() {
		return tongTien;
	}
	   @Override
	    public boolean equals(Object o) {
	        if (this == o) return true; // So sánh tham chiếu nếu giống nhau.
	        if (o == null || getClass() != o.getClass()) return false; // Kiểm tra null và kiểu đối tượng.
	        PhieuNhap that = (PhieuNhap) o;
	        return Objects.equals(ma, that.ma); // So sánh dựa trên mã phiếu nhập.
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(ma); // Tạo giá trị băm dựa trên mã phiếu nhập.
	    }
}