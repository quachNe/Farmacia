package GUI;
import javax.swing.JOptionPane;

abstract class ThongBao {
	static int cauHoi(String cauHoi) {
		return JOptionPane.showConfirmDialog(null, cauHoi, "Câu hỏi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
	
	static int cauHoiNghiemTrong(String cauHoi) {
		return JOptionPane.showConfirmDialog(null, cauHoi, "Câu hỏi", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
	}
	
	static void thongBao(String thongBao) {
		JOptionPane.showMessageDialog(null, thongBao, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	}
	
	static void baoLoi(String loi) {
		JOptionPane.showMessageDialog(null, loi, "Lỗi", JOptionPane.ERROR_MESSAGE);		
	}
}