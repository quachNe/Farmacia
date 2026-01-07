package BUS;
import DAO.AnhDAO;

public class AnhBUS {
    private AnhDAO anhDAO = new AnhDAO();
    
    public boolean addAnh(String maThuoc, String anh) {
        return anhDAO.addAnh(maThuoc, anh);
    }
    
    public boolean deleteAnhByMaThuoc(String maThuoc) {
        return anhDAO.deleteAnh(maThuoc);
    }
    
    public boolean UpdateAnh(String mathuoc, String anh) {
        return anhDAO.UpdateAnh(mathuoc, anh);
    }
    
    public String getAnhByMaThuoc(String maThuoc) {
        return anhDAO.getAnhByMaThuoc(maThuoc);
    }
}