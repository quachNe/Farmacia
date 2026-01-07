package BUS;

import DTO.NhaSanXuat;
import DAO.NhaSanXuatDAO;
import java.util.ArrayList;

public class NhaSanXuatBUS {
	private NhaSanXuatDAO nsxDAO = new NhaSanXuatDAO();
	
	public ArrayList<NhaSanXuat> getAllNsx() {
		return nsxDAO.getAllNsx();
	}
	
	public boolean addNhaSanXuat(NhaSanXuat nsx) {
		return nsxDAO.addNhaSanXuat(nsx);
	}
	
	public boolean updateNhaSanXuat(NhaSanXuat nsx) {
		return nsxDAO.updateNhaSanXuat(nsx);
	}
	
	public boolean deleteNhaSanXuat(String ma, boolean tt) {
		return nsxDAO.deleteNhaSanXuat(ma,tt);
	}
	
	public String getLatestMaNSX() {
		return nsxDAO.getLatestMaNSX();
	}
	
	public ArrayList<NhaSanXuat> find(String chuoi) {
		return nsxDAO.find(chuoi);
	}
	
	public ArrayList<NhaSanXuat> findTrangThai(int num) {
		return nsxDAO.findTrangThai(num);
	}
	
	public ArrayList<String> getAllMaNSX() {
		return nsxDAO.getAllMaNSX();
	}
	
	public String getTenNsxByMansx(String mansx) {
		return nsxDAO.getTenNsxByMansx(mansx);
	}
}