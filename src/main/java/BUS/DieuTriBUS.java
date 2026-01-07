package BUS;

import java.util.ArrayList;

import DAO.DieuTriDAO;
import DTO.DieuTri;

public class DieuTriBUS {
	DieuTriDAO dtDAO = new DieuTriDAO();
	
	public boolean addBenhDieuTri(DieuTri dt) {
		return dtDAO.addBenhDieuTri(dt);
	}

	public boolean deleteBenhDieuTri(String mathuoc) {
		return dtDAO.deleteBenhDieuTri(mathuoc);
	}
	
	public ArrayList<String> getBenhDieuTriByMaThuoc(String mathuoc) {
		return dtDAO.getBenhDieuTriByMaThuoc(mathuoc);
	}
}
