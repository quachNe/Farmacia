package DTO;

public class ThanhPhan {
	private String mathuoc;
	private String thanhphan;
	
	public ThanhPhan(String mathuoc, String thanhphan) {
		super();
		this.mathuoc = mathuoc;
		this.thanhphan = thanhphan;
	}

	public ThanhPhan() {
		super();
	}

	public String getMathuoc() {
		return mathuoc;
	}

	public void setMathuoc(String mathuoc) {
		this.mathuoc = mathuoc;
	}

	public String getThanhphan() {
		return thanhphan;
	}

	public void setThanhphan(String thanhphan) {
		this.thanhphan = thanhphan;
	}
}
