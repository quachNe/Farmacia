package DTO;

public class DieuTri {
	private String mathuoc;
	private String benhdieutri;
	
	public DieuTri(String mathuoc, String benhdieutri) {
		super();
		this.mathuoc = mathuoc;
		this.benhdieutri = benhdieutri;
	}

	public DieuTri() {
		super();
	}

	public String getMathuoc() {
		return mathuoc;
	}

	public void setMathuoc(String mathuoc) {
		this.mathuoc = mathuoc;
	}

	public String getBenhdieutri() {
		return benhdieutri;
	}

	public void setBenhdieutri(String benhdieutri) {
		this.benhdieutri = benhdieutri;
	}
}
