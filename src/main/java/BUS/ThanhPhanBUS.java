package BUS;

import java.util.ArrayList;

import DAO.ThanhPhanDAO;
import DTO.ThanhPhan;

public class ThanhPhanBUS {
	ThanhPhanDAO tpDAO = new ThanhPhanDAO();
	
	public boolean addThanhPhan(ThanhPhan tp) {
		return tpDAO.addThanhPhan(tp);
	}

	public boolean deleteThanhPhan(String mathuoc) {
		return tpDAO.deleteThanhPhan(mathuoc);
	}
	
	public ArrayList<String> getThanhPhanByMaThuoc(String mathuoc) {
		return tpDAO.getThanhPhanByMaThuoc(mathuoc);
	}
}
