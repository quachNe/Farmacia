package BUS;

import DTO.NhaCungCap;
import DAO.NhaCungCapDAO;
import java.util.ArrayList;

public class NhaCungCapBUS {
	private NhaCungCapDAO nccDAO = new NhaCungCapDAO();
	
	public ArrayList<NhaCungCap> getAllNCC() {
		return nccDAO.getAllNCC();
	}
	
	public boolean addNhaCungCap (NhaCungCap ncc) {
		return nccDAO.addNhaCungCap(ncc);
	}
	
	public boolean editNhaCungCap (NhaCungCap ncc) {
		return nccDAO.editNhaCungCap(ncc);
	}
	
	public boolean changeStatusNhaCungCap (String ma, boolean trangThai) {
		return nccDAO.changeStatusNhaCungCap(ma, trangThai);
	}

	public String getLastMaNCC () {
		return nccDAO.getLastMaNCC();
	}
	
	public ArrayList<NhaCungCap> find(String chuoi) {
		return nccDAO.find(chuoi);
	}
	
	public ArrayList<NhaCungCap> findTrangThais(int num) {
		return nccDAO.findTrangThai(num);
	}
}
