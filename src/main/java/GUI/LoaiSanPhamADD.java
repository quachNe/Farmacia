package GUI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import BUS.LoaiSanPhamBUS;
import DTO.LoaiSanPham;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoaiSanPhamADD extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static boolean isCheck = false;
	private static String maLoaiUp;
	private static boolean isCloseWindow;
	private static LoaiSanPhamBUS loaisanphambus = new LoaiSanPhamBUS();
	private static JTextField txtTenLoai;
	public LoaiSanPhamADD(String title) {
		setBounds(100, 100, 545, 260);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setTitle(title);
		setLocationRelativeTo(null);
		
		JButton btnLuu = new JButton("Lưu");
		btnLuu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isCloseWindow = false;
				handleAdd();
				LoaiSanPhamGUI.loadDataLSP();
				if (isCloseWindow) {
					dispose();
				}
			}
		});
		btnLuu.setForeground(Color.WHITE);
		btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnLuu.setFocusPainted(false);
		btnLuu.setBorderPainted(false);
		btnLuu.setBackground(new Color(11, 101, 140));
		btnLuu.setIcon(new ImageIcon(new ImageIcon("img/Icon/Save.png").getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
		btnLuu.setBounds(205, 171, 113, 30);
		contentPanel.add(btnLuu);
		
		JButton btnHuy = new JButton("Hủy");
		btnHuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnHuy.setForeground(Color.WHITE);
		btnHuy.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnHuy.setFocusPainted(false);
		btnHuy.setBorderPainted(false);
		btnHuy.setBackground(new Color(11, 101, 140));
		btnHuy.setIcon(new ImageIcon(new ImageIcon("img/Icon/Cancel.png").getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
		btnHuy.setBounds(328, 171, 113, 30);
		contentPanel.add(btnHuy);
		
		txtTenLoai = new JTextField();
		txtTenLoai.setColumns(10);
		txtTenLoai.setBounds(205, 102, 316, 30);
		contentPanel.add(txtTenLoai);
		
		JLabel lblThmLoiSn = new JLabel(title);
		lblThmLoiSn.setHorizontalAlignment(SwingConstants.CENTER);
		lblThmLoiSn.setFont(new Font("Segoe UI", Font.PLAIN, 25));
		lblThmLoiSn.setBounds(10, 10, 511, 40);
		contentPanel.add(lblThmLoiSn);
		
		JLabel lblNewLabel = new JLabel("Tên loại sản phẩm :");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 102, 185, 29);
		contentPanel.add(lblNewLabel);
		setModal(true);
		setResizable(false);
		setIconImage(new ImageIcon("img/icon.png").getImage());
		repainting();
	}
	public static void setData(LoaiSanPham lsp,boolean check1) {
		isCheck = check1;
		maLoaiUp = lsp.getMa();
		txtTenLoai.setText(lsp.getTen());
	}
	
	public static void handleAdd() {
		if (txtTenLoai.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin");
		}else {
			boolean trangThaiMacDinh = true;
			
			String maloai = loaisanphambus.getLatestMaLoai();
			String numberPart = maloai.substring(3);
	        int number = Integer.parseInt(numberPart);
	        number++;
	        String paddedNumber = String.format("%04d", number);
	        String newMaLoai = "LSP" + paddedNumber;
			
			String tenLoai = txtTenLoai.getText();
			boolean tt = trangThaiMacDinh;
			if (!txtTenLoai.getText().equals(txtTenLoai.getText().trim())) {
	            ThongBao.baoLoi("Tên loại sản phẩm không được bắt đầu hoặc kết thúc bằng khoảng trắng");
	            return;
	        }

	        if (txtTenLoai.getText().contains("  ")) {
	            ThongBao.baoLoi("Tên loại sản phẩm không được chứa hơn một khoảng trắng giữa các từ");
	            return;
	        }
			if (isCheck) {
				LoaiSanPham lsp = new LoaiSanPham(maLoaiUp, tenLoai, tt);
				for(LoaiSanPham LSP : loaisanphambus.getAllLSP()) {
					if (LSP.getTen().equals(tenLoai.trim()) && !LSP.getMa().equals(maLoaiUp)){
						ThongBao.baoLoi("Tên loại sản phẩm đã tồn tại!");
						return;
					}
				}
				if (loaisanphambus.updateLoaiSanPham(lsp)) {
					JOptionPane.showMessageDialog(null, "Cập nhật thành công");
					isCheck = false;
					isCloseWindow = true;
				}
			}else {
				for(LoaiSanPham LSP : loaisanphambus.getAllLSP()) {
					if (LSP.getTen().equals(tenLoai.trim())){
						ThongBao.baoLoi("Tên loại sản phẩm đã tồn tại!");
						return;
					}
				}
				LoaiSanPham lsp = new LoaiSanPham(newMaLoai, tenLoai, tt);
				if (loaisanphambus.addLoaiSanPham(lsp)) {
					JOptionPane.showMessageDialog(null, "Thêm thành công");
					isCheck = false;
					isCloseWindow = true;
				}
			}
		}
	} 	void repainting() {
		Theme.setTheme(this);
		getRootPane().putClientProperty("JRootPane.titleBarBackground", Theme.DARK);
		getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.WHITE);
		contentPanel.setBackground(Theme.LIGHT);		
	}
}