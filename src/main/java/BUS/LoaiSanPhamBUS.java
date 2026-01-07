package BUS;

import DTO.LoaiSanPham;
import DAO.LoaiSanPhamDAO;

import java.util.ArrayList;

public class LoaiSanPhamBUS {
	LoaiSanPhamDAO lspDAO = new LoaiSanPhamDAO();

	public ArrayList<LoaiSanPham> getAllLSP() {
		return lspDAO.getAllLSP();
	}
	
	public boolean addLoaiSanPham(LoaiSanPham lsp) {
		return lspDAO.addLoaiSanPham(lsp);
	}
	
	public boolean updateLoaiSanPham(LoaiSanPham lsp) {
		return lspDAO.updateLoaiSanPham(lsp);
	}
	
	public boolean changeStatusLoaiSanPham(String ma, boolean trangThai) {
		return lspDAO.changeStatusLoaiSanPham(ma, trangThai);
	}
	
	public ArrayList<LoaiSanPham> searchLSP(String searchTxt) {
		return lspDAO.searchLSP(searchTxt);
	}
	
	public ArrayList<String> getAllMaLoai() {
		return lspDAO.getAllMaLoai();
	}
	
	public String getTenLoaiByMaloai(String maloai) {
		return lspDAO.getTenLoaiByMaloai(maloai);
	}
	
	public ArrayList<LoaiSanPham> findTrangThai(int num) {
		return lspDAO.findTrangThai(num);
	}
	
	public String getLatestMaLoai() {
		return lspDAO.getLatestMaLoai();
	}
}
