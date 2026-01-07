package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import com.toedter.calendar.*;

import BUS.HoaDonBUS;
import DTO.KhachHang;
import javax.swing.ButtonGroup;

public class ThemKhachHang extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTextField txtHoTen;
	private JDateChooser dcNgaySinh;
	private JTextField txtSoDT;
	KhachHang kh = new KhachHang();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private HoaDonBUS bus = new HoaDonBUS();
	/**
	 * Create the dialog.
	 */
	public ThemKhachHang(HoaDonGUI gui) {
		setIconImage(new ImageIcon("img/icon.png").getImage());		
		setTitle("Thêm thành viên mới");
		setModalityType(DEFAULT_MODALITY_TYPE);
		setResizable(false);
		setBounds(100, 100, 400, 400);
		setLocationRelativeTo(null);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {80, 40, 0};
		gridBagLayout.rowHeights = new int[] {80, 40, 53, 40, 40, 40};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		getContentPane().setLayout(gridBagLayout);
		((JComponent) getContentPane()).setBorder(new EmptyBorder(10,10,10,10));
		
		JLabel lblNewLabel = new JLabel("TẠO THÀNH VIÊN MỚI");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 20, 0);
		gbc_lblNewLabel.gridwidth = 3;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblHoTen = new JLabel("Họ tên:");
		lblHoTen.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblHoTen = new GridBagConstraints();
		gbc_lblHoTen.fill = GridBagConstraints.BOTH;
		gbc_lblHoTen.insets = new Insets(0, 0, 20, 5);
		gbc_lblHoTen.gridx = 0;
		gbc_lblHoTen.gridy = 1;
		getContentPane().add(lblHoTen, gbc_lblHoTen);
		
		txtHoTen = new JTextField();
		txtHoTen.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_txtHoTen = new GridBagConstraints();
		gbc_txtHoTen.anchor = GridBagConstraints.NORTH;
		gbc_txtHoTen.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtHoTen.insets = new Insets(0, 0, 20, 0);
		gbc_txtHoTen.gridwidth = 2;
		gbc_txtHoTen.gridx = 1;
		gbc_txtHoTen.gridy = 1;
		getContentPane().add(txtHoTen, gbc_txtHoTen);
		txtHoTen.setColumns(10);
		
		JLabel lblNgaySinh = new JLabel("Ngày sinh:");
		lblNgaySinh.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNgaySinh = new GridBagConstraints();
		gbc_lblNgaySinh.fill = GridBagConstraints.BOTH;
		gbc_lblNgaySinh.insets = new Insets(0, 0, 20, 5);
		gbc_lblNgaySinh.gridx = 0;
		gbc_lblNgaySinh.gridy = 2;
		getContentPane().add(lblNgaySinh, gbc_lblNgaySinh);
		
		dcNgaySinh = new JDateChooser(new java.util.Date());
		dcNgaySinh.setDateFormatString("yyyy-MM-dd");
		dcNgaySinh.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_dcNgaySinh = new GridBagConstraints();
		gbc_dcNgaySinh.fill = GridBagConstraints.BOTH;
		gbc_dcNgaySinh.insets = new Insets(0, 0, 20, 0);
		gbc_dcNgaySinh.gridwidth = 2;
		gbc_dcNgaySinh.gridx = 1;
		gbc_dcNgaySinh.gridy = 2;
		getContentPane().add(dcNgaySinh, gbc_dcNgaySinh);
		
		JLabel lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblGioiTinh = new GridBagConstraints();
		gbc_lblGioiTinh.fill = GridBagConstraints.BOTH;
		gbc_lblGioiTinh.insets = new Insets(0, 0, 20, 5);
		gbc_lblGioiTinh.gridx = 0;
		gbc_lblGioiTinh.gridy = 3;
		getContentPane().add(lblGioiTinh, gbc_lblGioiTinh);
		
		JRadioButton rdNam = new JRadioButton("Nam", true);
		buttonGroup.add(rdNam);
		rdNam.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_rdNam = new GridBagConstraints();
		gbc_rdNam.fill = GridBagConstraints.BOTH;
		gbc_rdNam.insets = new Insets(0, 0, 20, 5);
		gbc_rdNam.gridx = 1;
		gbc_rdNam.gridy = 3;
		getContentPane().add(rdNam, gbc_rdNam);
		
		JRadioButton rdNu = new JRadioButton("Nữ");
		buttonGroup.add(rdNu);
		rdNu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_rdNu = new GridBagConstraints();
		gbc_rdNu.anchor = GridBagConstraints.WEST;
		gbc_rdNu.fill = GridBagConstraints.VERTICAL;
		gbc_rdNu.insets = new Insets(0, 0, 20, 0);
		gbc_rdNu.gridx = 2;
		gbc_rdNu.gridy = 3;
		getContentPane().add(rdNu, gbc_rdNu);
		
		JLabel lblSoDT = new JLabel("Số điện thoại:");
		lblSoDT.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblSoDT = new GridBagConstraints();
		gbc_lblSoDT.fill = GridBagConstraints.BOTH;
		gbc_lblSoDT.insets = new Insets(0, 0, 20, 5);
		gbc_lblSoDT.gridx = 0;
		gbc_lblSoDT.gridy = 4;
		getContentPane().add(lblSoDT, gbc_lblSoDT);
		
		txtSoDT = new JTextField();
		txtSoDT.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_txtSoDT = new GridBagConstraints();
		gbc_txtSoDT.fill = GridBagConstraints.BOTH;
		gbc_txtSoDT.insets = new Insets(0, 0, 20, 0);
		gbc_txtSoDT.gridwidth = 2;
		gbc_txtSoDT.gridx = 1;
		gbc_txtSoDT.gridy = 4;
		getContentPane().add(txtSoDT, gbc_txtSoDT);
		txtSoDT.setColumns(10);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
		gbc_buttonPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonPanel.gridwidth = 3;
		gbc_buttonPanel.gridx = 0;
		gbc_buttonPanel.gridy = 5;
		getContentPane().add(buttonPanel, gbc_buttonPanel);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 0));
		
		JButton btnLuu = new JButton("Lưu");
		btnLuu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtHoTen.getText().isEmpty() || txtSoDT.getText().isEmpty() || dcNgaySinh.getDate()==null) {
					ThongBao.baoLoi("Vui lòng nhập đầy đủ thông tin");
					return;
				}
				if (txtHoTen.getText().length() > 50) {
					ThongBao.baoLoi("Tên không được dài hơn 50 ký tự");
					return;
				}
				if (txtHoTen.getText().startsWith(" ")) {
					ThongBao.baoLoi("Tên không được bắt đầu bằng khoảng trắng");
					return;
				}
				if (!txtHoTen.getText().matches("^[a-zA-ZÀ-ỹ\\s]+$")) {
					ThongBao.baoLoi("Tên không được chứa số và ký tự đặc biệt");
					return;
				}
				if (!txtSoDT.getText().matches("^0\\d{9}$")) {
					ThongBao.baoLoi("Vui lòng nhập đúng cú pháp số điện thoại");
					return;
				}
				for (KhachHang kh:bus.getKhachHang()) {
					if (txtSoDT.getText().equals(kh.getSoDT())) {
						ThongBao.baoLoi("Số điện thoại đã tồn tại");
						return;
					}
				}
				LocalDate dateOfBirth = LocalDate.ofInstant(dcNgaySinh.getDate().toInstant(), java.time.ZoneId.systemDefault());
				if (dateOfBirth.isAfter(LocalDate.now())) {
					ThongBao.baoLoi("Ngày sinh không được lớn hơn ngày hiện tại");
					return;
				}
				java.sql.Date sqlDate = new java.sql.Date(dcNgaySinh.getDate().getTime());
				kh = new KhachHang(txtHoTen.getText(), dateOfBirth, (rdNam.isSelected() ? "Nam" : "Nữ"), txtSoDT.getText());
				boolean kq = bus.addKhachHang(txtHoTen.getText(), sqlDate, (rdNam.isSelected() ? "Nam" : "Nữ"), txtSoDT.getText(), 0);
				if (kq) {
					gui.cbbKhachHang.addItem(kh);
					ThongBao.thongBao("Thêm khách hàng thành công");
					txtHoTen.setText("");
					txtSoDT.setText("");
					rdNam.setSelected(true);
					dcNgaySinh.setDate(new java.util.Date());
					dispose();
				} else {
					ThongBao.thongBao("Thêm khách hàng thất bại");
					return;
				}
			}
		});
		btnLuu.setPreferredSize(new Dimension(120, 40));
		buttonPanel.add(btnLuu);
		btnLuu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		
		JButton btnHuy = new JButton("Hủy");
		btnHuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtHoTen.setText(kh.getHoTen());
				if (kh.getGioiTinh().equals("Nam")) rdNam.setSelected(true); else rdNu.setSelected(true);
				txtSoDT.setText(kh.getSoDT());
				dispose();
			}
		});
		btnHuy.setPreferredSize(new Dimension(120, 40));
		buttonPanel.add(btnHuy);
		btnHuy.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		repainting();
	}
	
	void repainting() {
		Theme.setTheme(this);
		getRootPane().putClientProperty("JRootPane.titleBarBackground", Theme.DARK);
		getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.WHITE);
		getContentPane().setBackground(Theme.LIGHT);
		Time.filledDateEditor(dcNgaySinh);
	}
}
