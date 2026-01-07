package DAO;
import java.sql.*;

public class TaiKhoanDAO {	
	public String[] dangNhap(String username, String password) {
		String[] result = null;
		DBConnect.open();
		Connection con = DBConnect.getConnection();
		
		if (con != null) {
			try {
				PreparedStatement st = con.prepareStatement("Select maDS, username, hoTen, quyen, sodt, email  from DuocSi D join TaiKhoan T on D.maDS = T.maTK where username = ? and password = ? and trangthai = true");
				st.setString(1, username);
				st.setString(2, password);
				
				ResultSet rs = st.executeQuery();
				while (rs.next()) result = new String[] { rs.getString("maDS"), rs.getString("username"), rs.getString("hoTen"), rs.getString("quyen"), rs.getString("sodt"), rs.getString("email") };
				con.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean usernameExists(String username) {
	    DBConnect.open();
	    Connection con = DBConnect.getConnection();
	    if (con != null) {
	        try {
	        	ResultSet rs = con.createStatement().executeQuery("Select username from TaiKhoan where username = '" + username + "'");
	            while (rs.next()) return true;
	            con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return false;
	}

	public boolean createAccount(String mads) {
	    DBConnect.open();
	    Connection con = DBConnect.getConnection();
	    boolean result = false;
	    if (con != null) {
	        try {
	            PreparedStatement st = con.prepareStatement("INSERT INTO taikhoan VALUES(?,?,?,?)");
	            st.setString(1, mads);
	            st.setString(2, mads);
	            st.setString(3, "123456");
	            st.setBoolean(4, false);
	            result = st.executeUpdate() >0;
	            con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return result;
	}
	
	public boolean updateMatKhau(String maDS, String username, String password_new) {
		DBConnect.open();
	    Connection con = DBConnect.getConnection();
	    boolean result = false;
	    if (con != null) {
	    	try {
	            PreparedStatement st = con.prepareStatement("UPDATE taikhoan SET username=?, password = ? WHERE maTK = ?");
	            st.setString(1, username);
	            st.setString(2, password_new);
	            st.setString(3, maDS);
	            result = st.executeUpdate() >0;
	            con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return result;
	}
	
	public boolean updateQuyen(String maDS, int quyen) {
		DBConnect.open();
	    Connection con = DBConnect.getConnection();
	    boolean result = false;
	    if (con != null) {
	    	try {
	            PreparedStatement st = con.prepareStatement("UPDATE taikhoan SET quyen = ? WHERE maTK=?");
	            st.setInt(1, quyen);
	            st.setString(2, maDS);
	            result = st.executeUpdate() >0;
	            con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return result;
	}
	
	public int getQuyen(String maDS) {
	    DBConnect.open();
	    int quyen = 0;
	    Connection con = DBConnect.getConnection();
	    if (con != null) {
	        try {
	        	ResultSet rs = con.createStatement().executeQuery("Select quyen from TaiKhoan where maTK = '" + maDS + "'");
	            while (rs.next()) quyen = rs.getInt(1);
	            con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return quyen;		
	}
}