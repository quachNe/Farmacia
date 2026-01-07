package BUS;
import DAO.AnhAvtDAO;

public class AnhAvtBUS {
	
    private AnhAvtDAO anhAvtDAO = new AnhAvtDAO();
    
    public boolean addAnhAvt(String mads, String anhavt) {
        return anhAvtDAO.addAnhAvt(mads, anhavt);
    }

    public boolean deleteAnhAvtByMads(String mads) {
        return anhAvtDAO.deleteAnhAvt(mads);
    }
    
    public String getAnhAvtByMads(String mads) {
        return anhAvtDAO.getAnhAvtByMads(mads);
    }
    public boolean UpdateAnhAvt(String txtMaDS, String anh) {
        return anhAvtDAO.UpdateAnhAvt(txtMaDS, anh);
    }
}
