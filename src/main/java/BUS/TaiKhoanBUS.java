package BUS;
import DAO.TaiKhoanDAO;

public class TaiKhoanBUS {
	TaiKhoanDAO dao = new TaiKhoanDAO();
	
	public boolean isServerOnline() {
		return DAO.DBConnect.open();
	}

	public boolean usernameExists(String username) {
		return dao.usernameExists(username);
	}
	
	public String[] dangNhap(String username, String password) {
		return dao.dangNhap(username, password);
	}
	public boolean createAccount(String mads) {
	    return dao.createAccount(mads);
	}
	
	public boolean updateMatKhau(String maDS, String username, String password_new) {
		return dao.updateMatKhau(maDS, username, password_new);
	}
	
	public int getQuyen(String maDS) {
		return dao.getQuyen(maDS);
	}
	
	public boolean updateQuyen(String maDS, int quyen) {
		return dao.updateQuyen(maDS, quyen);
	}
}