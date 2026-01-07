package BUS;
import DAO.ChuyDAO;

public class ChuyBUS {
    private ChuyDAO chuyDAO = new ChuyDAO();

    public boolean addChuy(String machuy, String tenchuy) {
        return chuyDAO.addChuy(machuy, tenchuy);
    }

    public boolean deleteChuy(String machuy) {
        return chuyDAO.deleteChuy(machuy);
    }
    
    public boolean UpdateChuy(String mathuoc, String chuy) {
        return chuyDAO.UpdateChuy(mathuoc, chuy);
    }
    
    public String getTenchuyByMaChuy(String machuy) {
        return chuyDAO.getTenchuyByMaChuy(machuy);
    }
}
