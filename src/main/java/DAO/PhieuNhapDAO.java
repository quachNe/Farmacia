package DAO;

import DTO.PhieuNhap;
import DTO.ChiTietPhieuNhap;
import DTO.ChiTietSanPham;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class PhieuNhapDAO {
    
    public boolean kiemTraSP(String maSP) {
        boolean result = false;
        String sql = "SELECT maSP FROM SanPham WHERE maSP = ?";

        try (Connection con = DBConnect.getConnection(); 
             PreparedStatement st = con.prepareStatement(sql)) {
             
            st.setString(1, maSP);
            try (ResultSet rs = st.executeQuery()) {
                result = rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<String> getNhaCungCap() {
        ArrayList<String> result = new ArrayList<>();
        String sql = "SELECT maNCC FROM NhaCungCap";

        try (Connection con = DBConnect.getConnection(); 
             PreparedStatement pst = con.prepareStatement(sql); 
             ResultSet rs = pst.executeQuery()) {
             
            while (rs.next()) {
                result.add(rs.getString("maNCC"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<PhieuNhap> getPhieuNhap() {
        ArrayList<PhieuNhap> result = new ArrayList<>();
        String sql = "SELECT * FROM PhieuNhap";

        try (Connection con = DBConnect.getConnection(); 
             PreparedStatement pst = con.prepareStatement(sql); 
             ResultSet rs = pst.executeQuery()) {
             
            while (rs.next()) {
                result.add(new PhieuNhap(rs.getString("maPN"), 
                                          rs.getDate("ngayLap").toLocalDate(),
                                          rs.getString("nguoiLap"), 
                                          rs.getString("nhaCungCap"), 
                                          rs.getLong("tongTien")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<ChiTietPhieuNhap> getSanPhamNhap(String maPN) {
        ArrayList<ChiTietPhieuNhap> result = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietPhieuNhap P JOIN chitietsanpham S ON P.mavach = S.mavach WHERE maPN = ?";

        try (Connection con = DBConnect.getConnection(); 
             PreparedStatement st = con.prepareStatement(sql)) {
             
            st.setString(1, maPN);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuNhap temp = new ChiTietPhieuNhap(rs.getString("mavach"), 
                                                                  rs.getLong("giaNhap"), 
                                                                  rs.getInt("P.soLuong"));
                    temp.setChiTiet(new ChiTietSanPham(rs.getString("masp"), 
                                                        rs.getString("mavach"), 
                                                        rs.getString("losx"),
                                                        rs.getDate("ngaysx").toLocalDate(), 
                                                        rs.getDate("hansd").toLocalDate(), 
                                                        rs.getLong("phantram"), 
                                                        rs.getInt("S.soLuong")));
                    result.add(temp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public PhieuNhap timPhieuNhap(String ma) {
        PhieuNhap result = null;
        String sql = "SELECT * FROM PhieuNhap WHERE maPN = ?";

        try (Connection con = DBConnect.getConnection(); 
             PreparedStatement st = con.prepareStatement(sql)) {
             
            st.setString(1, ma);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    result = new PhieuNhap(rs.getString("maPN"), 
                                           rs.getDate("ngayLap").toLocalDate(),
                                           rs.getString("nguoiLap"), 
                                           rs.getString("nhaCungCap"), 
                                           rs.getLong("tongTien"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<PhieuNhap> timPhieuNhap(ArrayList<String> ncc, ArrayList<String> sp, LocalDate dateFrom, LocalDate dateTo) {
        ArrayList<PhieuNhap> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM PhieuNhap WHERE 1=1");

        if (ncc != null && !ncc.isEmpty()) {
            sql.append(" AND nhaCungCap IN (");
            for (int i = 0; i < ncc.size(); i++) {
                sql.append("'").append(ncc.get(i)).append("'");
                if (i < ncc.size() - 1) sql.append(", ");
            }
            sql.append(")");
        }

        if (sp != null && !sp.isEmpty()) {
            sql.append(" AND maPN IN (SELECT maPN FROM ChiTietPhieuNhap P JOIN chitietsanpham S ON P.mavach = S.mavach WHERE masp IN (");
            for (int i = 0; i < sp.size(); i++) {
                sql.append("'").append(sp.get(i)).append("'");
                if (i < sp.size() - 1) sql.append(", ");
            }
            sql.append("))");
        }

        if (dateFrom != null && dateTo != null) {
            sql.append(" AND ngayLap BETWEEN ? AND ?");
        }

        try (Connection con = DBConnect.getConnection(); 
             PreparedStatement st = con.prepareStatement(sql.toString())) {
             
            int index = 1;
            if (dateFrom != null && dateTo != null) {
                st.setDate(index++, Date.valueOf(dateFrom));
                st.setDate(index++, Date.valueOf(dateTo));
            }

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    result.add(new PhieuNhap(rs.getString("maPN"), 
                                              rs.getDate("ngayLap").toLocalDate(),
                                              rs.getString("nguoiLap"), 
                                              rs.getString("nhaCungCap"), 
                                              rs.getLong("tongTien")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean addPhieuNhap(PhieuNhap pn, ArrayList<ChiTietPhieuNhap> spList) {
        boolean result = false;
        String sqlPN = "INSERT INTO PhieuNhap VALUES (?, ?, ?, ?, ?)";
        String sqlCTPN = "{call insertPN(?,?,?,?,?,?,?,?,?)}";

        try (Connection con = DBConnect.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement stPN = con.prepareStatement(sqlPN)) {
                stPN.setString(1, pn.getMa());
                stPN.setDate(2, Date.valueOf(pn.getNgayLap()));
                stPN.setString(3, pn.getNguoiLap());
                stPN.setString(4, pn.getNhaCungCap());
                stPN.setLong(5, pn.getTongTien());
                result = stPN.executeUpdate() > 0;
            }
            try (CallableStatement stCTPN = con.prepareCall(sqlCTPN)) {
                for (ChiTietPhieuNhap sp : spList) {
                    stCTPN.setString(1, pn.getMa());
                    stCTPN.setString(2, sp.getChiTiet().getMaSanPham());
                    stCTPN.setString(3, sp.getChiTiet().getMavach()); // Thêm mã vạch
                    stCTPN.setString(4, sp.getChiTiet().getLoSanXuat());
                    stCTPN.setDate(5, Date.valueOf(sp.getChiTiet().getNgaySanXuat()));
                    stCTPN.setDate(6, Date.valueOf(sp.getChiTiet().getHanSuDung()));
                    stCTPN.setLong(7, sp.getGiaNhap());
                    stCTPN.setLong(8, sp.getChiTiet().getphantram());
                    stCTPN.setInt(9, sp.getSoLuong());
                    stCTPN.addBatch();
                }

                int[] batchResults = stCTPN.executeBatch();
                boolean batchSuccess = Arrays.stream(batchResults).allMatch(res -> res >= 0);
                if (batchSuccess) {
                    con.commit();
                } else {
                    con.rollback();
                    result = false;
                }
            }
        } catch (SQLException e) { 
            e.printStackTrace();
        }
        return result;
    }
    public static ChiTietPhieuNhap timChiTietPhieuNhap(String mavach) {
        ChiTietPhieuNhap chiTietPhieuNhap = null;
        String sql = "SELECT P.mapn, P.mavach, P.losx, P.gianhap, P.soluong, " +
                     "S.masp, S.ngaysx, S.hansd, S.phantram " +
                     "FROM ChiTietPhieuNhap P " +
                     "JOIN ChiTietSanPham S ON P.mavach = S.mavach " +
                     "WHERE P.mavach = ?";

        try (Connection con = DBConnect.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, mavach);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    String maSanPham = rs.getString("masp");
                    String loSanXuat = rs.getString("losx");
                    LocalDate ngaySanXuat = rs.getDate("ngaysx").toLocalDate();
                    LocalDate hanSuDung = rs.getDate("hansd").toLocalDate();
                    long giaNhap = rs.getLong("gianhap");
                    long phantram = rs.getLong("phantram");
                    int soLuong = rs.getInt("soluong");
                    chiTietPhieuNhap = new ChiTietPhieuNhap(maSanPham, mavach, loSanXuat, ngaySanXuat, hanSuDung, giaNhap, phantram, soLuong);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chiTietPhieuNhap;
    }


    public boolean check_losp(String masp, String losx) {
        boolean result = false;
        String sql = "SELECT COUNT(*) FROM chitietsanpham WHERE masp = ? AND losx = ?";
        try (Connection con = DBConnect.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(sql)) {
             
            checkStmt.setString(1, masp);
            checkStmt.setString(2, losx);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    result = rs.getInt(1) == 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}