package BUS;

import DAO.ThongKeDAO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class ThongKeBUS {
private ThongKeDAO dao = new ThongKeDAO();
public HashMap<String, Object> sanPhamNhap(String maSP, String maNCC, LocalDate dateFrom, LocalDate dateTo, int numberFrom, int numberTo, String tieuChi) {
return dao.sanPhamNhap(maSP, maNCC, dateFrom, dateTo, numberFrom, numberTo, tieuChi);
}
public HashMap<String, Object> nhaCungCapNhap(String maNCC, LocalDate dateFrom, LocalDate dateTo, int numberFrom, int numberTo, String tieuChi) {
return dao.nhaCungCapNhap(maNCC, dateFrom, dateTo, numberFrom, numberTo, tieuChi);
}
public ArrayList<Object[]> sanPhamTheoNCC(String maNCC, LocalDate dateFrom, LocalDate dateTo, int numberFrom, int numberTo, String tieuChi) {
return dao.sanPhamTheoNCC(maNCC, dateFrom, dateTo, numberFrom, numberTo, tieuChi);
}
public HashMap<String, Object> sanPhamBan(String maSP, LocalDate dateFrom, LocalDate dateTo, int numberFrom, int numberTo, String tieuChi) {
return dao.sanPhamBan(maSP, dateFrom, dateTo, numberFrom, numberTo, tieuChi);
}
public HashMap<String, long[]> doanhThu(LocalDate dateFrom, LocalDate dateTo) {
return dao.doanhThu(dateFrom, dateTo); 
}
public HashMap<String, Object[]> laySanPhamBanChay(int month, int year) {
return dao.laySanPhamBanChayNhatTheoThang(month, year);
}
public ArrayList<Object[]> sanPhamBanTheoThang(int thang, int nam) {
return dao.sanPhamBanTheoThang(thang, nam); 
}

}

