package BUS;

import DTO.SanPham;

import DAO.SanPhamDAO;

import java.util.ArrayList;

public class SanPhamBUS {
	SanPhamDAO spDAO = new SanPhamDAO();

	public ArrayList<SanPham> getAllSp() {
		return spDAO.getAllSp();
	}
	public ArrayList<String> loaddataQuocGia(){
		return spDAO.loaddataQuocGia();
	}
	public boolean add(SanPham sp) {
		return spDAO.add(sp);
	}
	
	public boolean update(SanPham sp) {
		return spDAO.update(sp);
	}
	
	public boolean changeStatus(String masp, boolean trangThai) {
		return spDAO.changeStatus(masp, trangThai);
	}

	public ArrayList<SanPham> find(String chuoi) {
		return spDAO.find(chuoi);
	}
	
	public ArrayList<SanPham> findCanKeToa(int num) {
		return spDAO.findCanKeToa(num);
	}
	
	public ArrayList<SanPham> findTrangThai(int num) {
		return spDAO.findTrangThai(num);
	}
	
	public String getLatestProductId() {
		return spDAO.getLatestProductId();
	}
}