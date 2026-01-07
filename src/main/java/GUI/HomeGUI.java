package GUI;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BUS.AnhAvtBUS;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import java.awt.Font;

import java.util.ArrayList;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Insets;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class HomeGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final String tenTiem = "Tiệm thuốc Farmacia - ";
	private JLabel lblLogo;
	private ArrayList<JButton> menu = new ArrayList<>();
	private JPanel pnLogo;
	private JPanel pnButton;
	private JPanel pnForm;
	private JPanel pnUser;
	private JButton btnCurrent= new JButton();
	private JButton btnDangXuat;
	private int mau;
	
	private JButton btnSanPham;
	private JButton btnLoaiSanPham;
	private JButton btnNhaCungCap;
	private JButton btnNhaSanXuat;
	private JButton btnDuocSi;
	private JButton btnPhieuNhap;
	private JButton btnHoaDon;
	private JButton btnTieuHuy;
	private JButton btnThongKe;
	private JButton btnGioiThieu;
	private JButton btnMauSac;
	private GioiThieuGUI gt = new GioiThieuGUI();
	private JScrollPane scrollPane;
	private JLabel lblTaiKhoan;
	
	public HomeGUI(String maDS, String username, String tenDS, int quyen, String sodt, String email, int m) {
		mau = m;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (ThongBao.cauHoi("Bạn có muốn thoát ứng dụng?") == JOptionPane.YES_OPTION)
					System.exit(1);
				else
					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			}
		});
		setIconImage(new ImageIcon("img/icon.png").getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {250, 0};
		gbl_contentPane.rowHeights = new int[] {50, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 0.0};
		contentPane.setLayout(gbl_contentPane);
		
		pnLogo = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnLogo.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		GridBagConstraints gbc_pnLogo = new GridBagConstraints();
		gbc_pnLogo.fill = GridBagConstraints.BOTH;
		gbc_pnLogo.gridx = 0;
		gbc_pnLogo.gridy = 0;
		contentPane.add(pnLogo, gbc_pnLogo);
		
		lblLogo = new JLabel("");
		lblLogo.setBorder(new EmptyBorder(10, 10, 10, 10));
		lblLogo.setIcon(new ImageIcon("img/logo_dark.png"));
		pnLogo.add(lblLogo);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(new EmptyBorder(0,0,0,0));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		pnButton = new JPanel();
		scrollPane.setViewportView(pnButton);
		pnButton.setBorder(null);
		pnButton.setBackground(new Color(23, 107, 135));
		pnButton.setLayout(new BoxLayout(pnButton, BoxLayout.PAGE_AXIS));
		
		btnSanPham = new JButton("SẢN PHẨM");
		btnSanPham.setHorizontalAlignment(SwingConstants.LEFT);
		btnSanPham.setIcon(new ImageIcon("img/sp.png"));
		btnSanPham.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnSanPham.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnSanPham.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		btnSanPham.setBorder(new EmptyBorder(0, 35, 0, 0));
		btnSanPham.setFocusable(false);
		btnSanPham.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrent(btnSanPham);
				setTitle(tenTiem + "Quản lý sản phẩm");
				pnForm.removeAll();
				pnForm.add(new SanPhamGUI());
				pnForm.validate();
				pnForm.repaint();
			}
		});
		btnSanPham.setMaximumSize(new Dimension(270, 50));
		
		btnLoaiSanPham = new JButton("LOẠI SẢN PHẨM");
		btnLoaiSanPham.setHorizontalAlignment(SwingConstants.LEFT);
		btnLoaiSanPham.setIcon(new ImageIcon("img/lsp.png"));
		btnLoaiSanPham.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnLoaiSanPham.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnLoaiSanPham.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		btnLoaiSanPham.setBorder(new EmptyBorder(0, 35, 0, 0));
		btnLoaiSanPham.setFocusable(false);
		btnLoaiSanPham.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrent(btnLoaiSanPham);
				setTitle(tenTiem + "Quản lý loại sản phẩm");
				pnForm.removeAll();
				pnForm.add(new LoaiSanPhamGUI());
				pnForm.validate();
				pnForm.repaint();
			}
		});
		btnLoaiSanPham.setMaximumSize(new Dimension(270, 50));
		
		btnNhaCungCap = new JButton("NHÀ CUNG CẤP");
		btnNhaCungCap.setHorizontalAlignment(SwingConstants.LEFT);
		btnNhaCungCap.setIcon(new ImageIcon("img/ncc.png"));
		btnNhaCungCap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNhaCungCap.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNhaCungCap.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		btnNhaCungCap.setBorder(new EmptyBorder(0, 35, 0, 0));
		btnNhaCungCap.setFocusable(false);
		btnNhaCungCap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrent(btnNhaCungCap);
				setTitle(tenTiem + "Quản lý nhà cung cấp");
				pnForm.removeAll();
				pnForm.add(new NhaCungCapGUI());
				pnForm.validate();
				pnForm.repaint();
			}
		});
		btnNhaCungCap.setMaximumSize(new Dimension(270, 50));
		
		btnNhaSanXuat = new JButton("NHÀ SẢN XUẤT");
		btnNhaSanXuat.setHorizontalAlignment(SwingConstants.LEFT);
		btnNhaSanXuat.setIcon(new ImageIcon("img/nsx.png"));
		btnNhaSanXuat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNhaSanXuat.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNhaSanXuat.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		btnNhaSanXuat.setBorder(new EmptyBorder(0, 35, 0, 0));
		btnNhaSanXuat.setFocusable(false);
		btnNhaSanXuat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrent(btnNhaSanXuat);
				setTitle(tenTiem + "Quản lý nhà sản xuất");
				pnForm.removeAll();
				pnForm.add(new NhaSanXuatGUI());
				pnForm.validate();
				pnForm.repaint();
			}
		});
		btnNhaSanXuat.setMaximumSize(new Dimension(270, 50));
		
		btnDuocSi = new JButton("DƯỢC SĨ");
		btnDuocSi.setHorizontalAlignment(SwingConstants.LEFT);
		btnDuocSi.setIcon(new ImageIcon("img/ds.png"));
		btnDuocSi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnDuocSi.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnDuocSi.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		btnDuocSi.setBorder(new EmptyBorder(0, 35, 0, 0));
		btnDuocSi.setFocusable(false);
		btnDuocSi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrent(btnDuocSi);
				setTitle(tenTiem + "Quản lý dược sĩ");
				pnForm.removeAll();
				pnForm.add(new DuocSiGUI());
				pnForm.validate();
				pnForm.repaint();
			}
		});
		btnDuocSi.setMaximumSize(new Dimension(270, 50));
		
		btnPhieuNhap = new JButton("PHIẾU NHẬP");
		btnPhieuNhap.setHorizontalAlignment(SwingConstants.LEFT);
		btnPhieuNhap.setIcon(new ImageIcon("img/pn.png"));
		btnPhieuNhap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnPhieuNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnPhieuNhap.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		btnPhieuNhap.setBorder(new EmptyBorder(0, 35, 0, 0));
		btnPhieuNhap.setFocusable(false);
		btnPhieuNhap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrent(btnPhieuNhap);
				setTitle(tenTiem + "Quản lý phiếu nhập");
				pnForm.removeAll();
				pnForm.add(new PhieuNhapGUI(maDS));
				pnForm.validate();
				pnForm.repaint();
			}
		});
		btnPhieuNhap.setMaximumSize(new Dimension(270, 50));
		
		btnHoaDon = new JButton("HÓA ĐƠN");
		btnHoaDon.setHorizontalAlignment(SwingConstants.LEFT);
		btnHoaDon.setIcon(new ImageIcon("img/hd.png"));
		btnHoaDon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnHoaDon.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnHoaDon.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		btnHoaDon.setBorder(new EmptyBorder(0, 35, 0, 0));
		btnHoaDon.setFocusable(false);
		btnHoaDon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrent(btnHoaDon);
				setTitle(tenTiem + "Quản lý hóa đơn");
				pnForm.removeAll();
				pnForm.add(new HoaDonGUI(maDS));
				pnForm.validate();
				pnForm.repaint();
			}
		});
		btnHoaDon.setMaximumSize(new Dimension(270, 50));
		
		btnTieuHuy = new JButton("TIÊU HỦY");
		btnTieuHuy.setHorizontalAlignment(SwingConstants.LEFT);
		btnTieuHuy.setIcon(new ImageIcon("img/th.png"));
		btnTieuHuy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnTieuHuy.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnTieuHuy.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		btnTieuHuy.setBorder(new EmptyBorder(0, 35, 0, 0));
		btnTieuHuy.setFocusable(false);
		btnTieuHuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrent(btnTieuHuy);
				setTitle(tenTiem + "Quản lý tiêu hủy");
				pnForm.removeAll();
				pnForm.add(new TieuHuyGUI(maDS));
				pnForm.validate();
				pnForm.repaint();
			}
		});
		btnTieuHuy.setMaximumSize(new Dimension(270, 50));
		
		btnThongKe = new JButton("THỐNG KÊ");
		btnThongKe.setHorizontalAlignment(SwingConstants.LEFT);
		btnThongKe.setIcon(new ImageIcon("img/tk.png"));
		btnThongKe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnThongKe.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnThongKe.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		btnThongKe.setBorder(new EmptyBorder(0, 35, 0, 0));
		btnThongKe.setFocusable(false);
		btnThongKe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrent(btnThongKe);
				setTitle(tenTiem + "Thống kê");
				pnForm.removeAll();
				pnForm.add(new ThongKeGUI(maDS));
				pnForm.validate();
				pnForm.repaint();
			}
		});
		btnThongKe.setMaximumSize(new Dimension(270, 50));
		
		btnGioiThieu = new JButton("GIỚI THIỆU");
		btnGioiThieu.setHorizontalAlignment(SwingConstants.LEFT);
		btnGioiThieu.setIcon(new ImageIcon("img/gt.png"));
		btnGioiThieu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnGioiThieu.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnGioiThieu.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		btnGioiThieu.setBorder(new EmptyBorder(0, 35, 0, 0));
		btnGioiThieu.setFocusable(false);
		btnGioiThieu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gt.setVisible(true);
			}
		});
		btnGioiThieu.setMaximumSize(new Dimension(270, 50));
		
		btnMauSac = new JButton("ĐỔI THEME");
		btnMauSac.setHorizontalAlignment(SwingConstants.LEFT);
		btnMauSac.setIcon(new ImageIcon("img/ms.png"));
		btnMauSac.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnMauSac.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnMauSac.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		btnMauSac.setBorder(new EmptyBorder(0, 35, 0, 0));
		btnMauSac.setFocusable(false);
		btnMauSac.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mau = (mau + 1 > Theme.TOTAL) ? 1 : mau + 1;
				repainting();
			}
		});
		btnMauSac.setMaximumSize(new Dimension(270, 50));
		
		pnUser = new JPanel();
		pnUser.setBackground(new Color(23, 107, 135));
		GridBagConstraints gbc_pnUser = new GridBagConstraints();
		gbc_pnUser.fill = GridBagConstraints.BOTH;
		gbc_pnUser.gridx = 0;
		contentPane.add(pnUser, gbc_pnUser);
		GridBagLayout gbl_pnUser = new GridBagLayout();
		gbl_pnUser.columnWidths = new int[] {65, 175, 0};
		gbl_pnUser.rowHeights = new int[] {30, 30, 30};
		gbl_pnUser.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_pnUser.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnUser.setLayout(gbl_pnUser);
		
		JLabel lblAvatar = new JLabel();
		lblAvatar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblAvatar.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblAvatar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
//				openDoiMatKhau(maDS, username);
//				mn.setVisible(true);
				Menu_nho mn = new Menu_nho(maDS, tenDS, quyen, sodt, email);
				mn.setVisible(true);
			}
		});
		lblAvatar.setIcon(new ImageIcon("img/avatar.png"));
		GridBagConstraints gbc_lblAvatar = new GridBagConstraints();
		gbc_lblAvatar.fill = GridBagConstraints.BOTH;
		gbc_lblAvatar.insets = new Insets(5, 5, 0, 5);
		gbc_lblAvatar.gridheight = 4;
		gbc_lblAvatar.gridx = 0;
		gbc_lblAvatar.gridy = 0;
		pnUser.add(lblAvatar, gbc_lblAvatar);
		
		JLabel lblXinChao = new JLabel(tenDS);
		lblXinChao.setForeground(Color.white);
		lblXinChao.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 17));
		lblXinChao.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblXinChao = new GridBagConstraints();
		gbc_lblXinChao.insets = new Insets(0, 0, 5, 0);
		gbc_lblXinChao.fill = GridBagConstraints.BOTH;
		gbc_lblXinChao.gridx = 1;
		gbc_lblXinChao.gridy = 0;
		pnUser.add(lblXinChao, gbc_lblXinChao);
		
		btnDangXuat = new JButton("Đăng xuất");
		btnDangXuat.setHorizontalAlignment(SwingConstants.LEFT);
		btnDangXuat.setBackground(new Color(23, 107, 135));
		btnDangXuat.setForeground(Color.white);
		btnDangXuat.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnDangXuat.setFocusable(false);
		btnDangXuat.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		btnDangXuat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnDangXuat.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnDangXuat.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		btnDangXuat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Menu_nho(maDS, username, quyen, sodt, email).setVisible(false);
				if (ThongBao.cauHoi("Bạn có muốn đăng xuất?") == JOptionPane.YES_OPTION) {
					setVisible(false);
					new DangNhapGUI(mau).setVisible(true);
				}
			}
		});
		
		
		lblTaiKhoan = new JLabel("New label");
		lblTaiKhoan.setForeground(new Color(255, 255, 255));
		lblTaiKhoan.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblTaiKhoan = new GridBagConstraints();
		gbc_lblTaiKhoan.anchor = GridBagConstraints.WEST;
		gbc_lblTaiKhoan.insets = new Insets(0, 0, 5, 0);
		gbc_lblTaiKhoan.gridx = 1;
		gbc_lblTaiKhoan.gridy = 1;
		pnUser.add(lblTaiKhoan, gbc_lblTaiKhoan);
		if (quyen == 15) {
			lblTaiKhoan.setText("Quản trị viên");
		}
		if (quyen == 2) {
			lblTaiKhoan.setText("Quản lý");
		}
		if (quyen == 4) {
			lblTaiKhoan.setText("Nhân viên bán hàng");
		}
		if (quyen == 8) {
			lblTaiKhoan.setText("Nhân viên nhập hàng");
		}
		GridBagConstraints gbc_btnDangXuat = new GridBagConstraints();
		gbc_btnDangXuat.insets = new Insets(0, 0, 5, 0);
		gbc_btnDangXuat.fill = GridBagConstraints.BOTH;
		gbc_btnDangXuat.gridx = 1;
		gbc_btnDangXuat.gridy = 2;
		pnUser.add(btnDangXuat, gbc_btnDangXuat);
		
		pnForm = new JPanel();
		GridBagConstraints gbc_pnForm = new GridBagConstraints();
		gbc_pnForm.gridheight = 3;
		gbc_pnForm.fill = GridBagConstraints.BOTH;
		gbc_pnForm.gridx = 1;
		gbc_pnForm.gridy = 0;
		contentPane.add(pnForm, gbc_pnForm);
		setMinimumSize(new Dimension(1216, 639));
		setLocationRelativeTo(null);
		pnForm.setLayout(new CardLayout(0, 0));
		
		init(quyen);
		menu.get(0).doClick();
		repainting();
	}
	
	private void init(int quyen) {
		if (quyen - Math.pow(2,3) >= 0) {
			pnButton.add(btnPhieuNhap, 0);
			menu.add(0, btnPhieuNhap);
			quyen -= Math.pow(2,3);
		}
		if (quyen - Math.pow(2,2) >= 0) {
			pnButton.add(btnHoaDon, 0);
			menu.add(0, btnHoaDon);
			quyen -= Math.pow(2,2);
		}
		if (quyen - Math.pow(2,1) >= 0) {
			pnButton.add(btnSanPham, 0);
			menu.add(0, btnSanPham);
			pnButton.add(btnLoaiSanPham, 1);
			menu.add(1, btnLoaiSanPham);
			pnButton.add(btnNhaCungCap, 2);
			menu.add(2, btnNhaCungCap);
			pnButton.add(btnNhaSanXuat, 3);
			menu.add(3, btnNhaSanXuat);
			pnButton.add(btnTieuHuy, 4);
			menu.add(4, btnTieuHuy);
			pnButton.add(btnPhieuNhap, 5);
			menu.add(5, btnPhieuNhap);
			pnButton.add(btnHoaDon, 6);
			menu.add(6, btnHoaDon);
			pnButton.add(btnThongKe, 7);
			menu.add(7, btnThongKe);
			quyen -= Math.pow(2,1);
		}
		if (quyen == 1) {
			pnButton.add(btnDuocSi, 0);
			menu.add(0, btnDuocSi);
		}
		menu.add(btnGioiThieu);
		pnButton.add(btnGioiThieu);
		
		pnButton.add(btnMauSac);
		menu.add(btnMauSac);		
		
	}
	
	private void openDoiMatKhau(String maDS, String username) {
		new DoiMatKhau(maDS, username, this).setVisible(true);
	}
	private void openThongtin(String maDS, String tends,int quyen,String sdt,String email) {
		new ThongTinCaNhanGUI(maDS, tends,quyen,sdt,email).setVisible(true);
	}
	private void setCurrent(JButton btnCurrent) {
		for (JButton btn: menu) {
			btn.setBackground(Theme.DARK);
			btn.setForeground(Color.white);
			btn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		}
		this.btnCurrent = btnCurrent;
		this.btnCurrent.setBackground(Theme.DARK_CUR);
		this.btnCurrent.setFont(new Font("Segoe UI", Font.BOLD, 18));
	}
	
	private void repainting() {
		Theme.setTheme(mau, this);
		pnLogo.setBackground(Theme.DARK);
		pnButton.setBackground(Theme.DARK);
		pnUser.setBackground(Theme.DARK);
		btnDangXuat.setBackground(Theme.DARK);
		getRootPane().putClientProperty("JRootPane.titleBarBackground", Theme.DARK);

		if (btnCurrent == btnSanPham)
			((SanPhamGUI)pnForm.getComponent(0)).repainting();
		else if (btnCurrent == btnLoaiSanPham)
			((LoaiSanPhamGUI)pnForm.getComponent(0)).repainting();
		else if (btnCurrent == btnNhaCungCap)
			((NhaCungCapGUI)pnForm.getComponent(0)).repainting();
		else if (btnCurrent == btnNhaSanXuat)
			((NhaSanXuatGUI)pnForm.getComponent(0)).repainting();
		else if (btnCurrent == btnDuocSi)
			((DuocSiGUI)pnForm.getComponent(0)).repainting();
		else if (btnCurrent == btnPhieuNhap)
			((PhieuNhapGUI)pnForm.getComponent(0)).repainting();
		else if (btnCurrent == btnHoaDon)
			((HoaDonGUI)pnForm.getComponent(0)).repainting();
		else if (btnCurrent == btnTieuHuy)
			((TieuHuyGUI)pnForm.getComponent(0)).repainting();
		else if (btnCurrent == btnThongKe)
			((ThongKeGUI)pnForm.getComponent(0)).repainting();
		
		setCurrent(btnCurrent);
		gt.repainting();
	}
	
	private class GioiThieuGUI extends JDialog {
		private static final long serialVersionUID = 1L;
		private JLabel lblLogo;
		private JPanel contentPane;
		private JLabel lblNhom8;
		private JLabel lblHongLinh;
		private JLabel lblHoangnhat;
		private JLabel lblXuanCanh;
		private JLabel lblTanDat;
		private JLabel lblThanhCong;
		private JLabel lblThanhLuan;
		private JLabel lblYenThuy;
		private JLabel lblDucAnh;
		private JLabel lblYear;
		
		GioiThieuGUI() {
			setTitle("Giới thiệu phần mềm");
			setModalityType(ModalityType.APPLICATION_MODAL);
			setIconImage(new ImageIcon("img/icon.png").getImage());		
			setBounds(100, 100, 438, 620);
			setLocationRelativeTo(null);
			setResizable(false);
			
			contentPane = new JPanel();		
			contentPane.setLayout(null);
			getContentPane().add(contentPane);
			
			lblLogo = new JLabel("");
			lblLogo.setVerticalAlignment(SwingConstants.BOTTOM);
			lblLogo.setIcon(new ImageIcon("img/logo_green.png"));
			lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
			lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 24));
			lblLogo.setBounds(0, 0, 422, 84);
			contentPane.add(lblLogo);
			
			JButton btnOK = new JButton("OK");
			btnOK.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnOK.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			btnOK.setBounds(161, 500, 100, 35);
			contentPane.add(btnOK);
			
			lblNhom8 = new JLabel("Nhóm 10 - Công nghệ phần mềm");
			lblNhom8.setHorizontalAlignment(SwingConstants.CENTER);
			lblNhom8.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 20));
			lblNhom8.setBounds(25, 105, 360, 35);
			contentPane.add(lblNhom8);
			
			lblHongLinh = new JLabel("Quách Hồng Linh_3122410212");
			lblHongLinh.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			lblHongLinh.setBounds(25, 150, 250, 25);
			contentPane.add(lblHongLinh);
			
			lblHoangnhat = new JLabel("Nguyễn Hoàng Nhật_3122410278");
			lblHoangnhat.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			lblHoangnhat.setBounds(25, 185, 250, 25);
			contentPane.add(lblHoangnhat);
			
			lblXuanCanh = new JLabel("Trương Xuân Cảnh_3122410038");
			lblXuanCanh.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			lblXuanCanh.setBounds(25, 220, 250, 25);
			contentPane.add(lblXuanCanh);
			
			lblTanDat = new JLabel("Nguyễn Tấn Đạt_3122410072");
			lblTanDat.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			lblTanDat.setBounds(25, 255, 250, 25);
			contentPane.add(lblTanDat);
			
			lblThanhLuan = new JLabel("Dương Thanh Luận_3122410224");
			lblThanhLuan.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			lblThanhLuan.setBounds(25, 290, 250, 25);
			contentPane.add(lblThanhLuan);
			
			lblYenThuy = new JLabel("Bùi Thành Công_312241004");
			lblYenThuy.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			lblYenThuy.setBounds(25, 325, 250, 25);
			contentPane.add(lblYenThuy);
			
			lblThanhCong = new JLabel("Võ Thị Yến Thùy_3122410406");
			lblThanhCong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			lblThanhCong.setBounds(25, 360, 250, 25);
			contentPane.add(lblThanhCong);
			
			lblDucAnh = new JLabel("Vũ Lê Đức Anh_3122560002");
			lblDucAnh.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			lblDucAnh.setBounds(25, 395, 250, 25);
			contentPane.add(lblDucAnh);
			
			lblYear = new JLabel("© 2024 Nhóm 10 - Công nghệ phần mềm 2024 - 2025");
			lblYear.setHorizontalAlignment(SwingConstants.CENTER);
			lblYear.setFont(new Font("Segoe UI", Font.ITALIC, 14));
			lblYear.setBounds(0, 455, 422, 20);
			contentPane.add(lblYear);
			repainting();
		}
		
		void repainting() {
			Theme.setTheme(this);
			getRootPane().putClientProperty("JRootPane.titleBarBackground", Theme.DARK);
			getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.WHITE);
			contentPane.setBackground(Theme.LIGHT);
			lblLogo.setIcon(new ImageIcon("img/logo_" + Theme.THEME + ".png"));
			lblNhom8.setForeground(Theme.TITLE);
			lblHongLinh.setForeground(Theme.TITLE);
			lblHoangnhat.setForeground(Theme.TITLE);
			lblTanDat.setForeground(Theme.TITLE);
			lblXuanCanh.setForeground(Theme.TITLE);
			lblYenThuy.setForeground(Theme.TITLE);
			lblThanhCong.setForeground(Theme.TITLE);
			lblThanhLuan.setForeground(Theme.TITLE);
			lblDucAnh.setForeground(Theme.TITLE);
			lblYear.setForeground(Theme.TITLE);
		}
	}
	private class Menu_nho extends JDialog {

		private static final long serialVersionUID = 1L;
		private final JPanel contentPanel = new JPanel();

		Menu_nho(String maDS, String username, int quyen, String sodt, String email) {
			setModalityType(ModalityType.APPLICATION_MODAL);
//			setVisible(true);
			setVisible(false);
			setTitle("Tính năng");
			setBounds(0, 675, 230, 120);
			setResizable(false);
			getContentPane().setLayout(new BorderLayout());
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			contentPanel.setLayout(null);
			
			JButton btnNewButton = new JButton("Thông tin cá nhân");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					openThongtin(maDS, username, quyen, sodt, email);
				}
			});
			btnNewButton.setBounds(10, 10, 196, 27);
			contentPanel.add(btnNewButton);
			
			JButton btnNewButton_1 = new JButton("Đổi mật khẩu");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					openDoiMatKhau(maDS, maDS);
				}
			});
			btnNewButton_1.setBounds(10, 47, 196, 27);
			contentPanel.add(btnNewButton_1);
		}
	}
	
	private static class ThongTinCaNhanGUI extends JDialog {
		private static final long serialVersionUID = 1L;
		private JTextField txtMa;
		private JTextField txtHoTen;
		private JTextField txtChucVu;
		private JTextField txtSDT;
		private JTextField txtEmail;
		private static JLabel lblavatar  = new JLabel("");
		public static  void taiAnh(String anh ) {
	    	  String thuMucChuaAnh = getFolderPath() + "/img/HinhAvt/";
	          String duongdananh = thuMucChuaAnh +anh;
	       System.out.print(duongdananh);
	          changeImage(duongdananh);
	    }
	    public static String getFolderPath() {
	        String folderPath = "";
	        try {
	            String appFolderPath = System.getProperty("user.dir");
	            if (appFolderPath.endsWith("bin") || appFolderPath.endsWith("dist")) {
	                folderPath = new File(appFolderPath).getParent();
	            } else {
	                folderPath = appFolderPath;
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        return folderPath;
	    }
	    public static void changeImage(String duongdananh) {
	        try {
	            ImageIcon resizedImage = ResizeImage(duongdananh);
	            lblavatar.setIcon(resizedImage);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	     
	        }
	    }
	    public static ImageIcon ResizeImage(String imagePath) {
	        ImageIcon resizedImage = null;
	        try {
	            File file = new File(imagePath);
	            if (!file.exists()) {
	                return null;
	            }
	            ImageIcon originalImage = new ImageIcon(imagePath);
	            Image img = originalImage.getImage();
	            Image newImg = img.getScaledInstance(  lblavatar.getWidth(),   lblavatar.getHeight(), Image.SCALE_SMOOTH);
	            resizedImage = new ImageIcon(newImg);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        return resizedImage;
	    }
		
	    public ThongTinCaNhanGUI(String mads, String hoten, int quyen, String sodt, String email) {
	        setModalityType(ModalityType.APPLICATION_MODAL);
	        setIconImage(new ImageIcon("img/icon.png").getImage());
	        setTitle("Thông tin cá nhân");
	        setBounds(100, 100, 600, 350);
	        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	        setResizable(false);

	        setLocationRelativeTo(null);
	        
	        getContentPane().setLayout(null);

	        lblavatar = new JLabel();
	        lblavatar.setBounds(30, 79, 160, 194);
	        AnhAvtBUS avt = new AnhAvtBUS();
	        String anhavt = avt.getAnhAvtByMads(mads);
	        taiAnh(anhavt);
	        getContentPane().add(lblavatar);

	        JLabel lblHoTen = new JLabel("Họ và Tên :");
	        lblHoTen.setFont(new Font("Segoe UI", Font.PLAIN, 20));
	        lblHoTen.setBounds(197, 112, 100, 20);
	        getContentPane().add(lblHoTen);

	        txtMa = new JTextField();
	        txtMa.setBounds(329, 78, 220, 20);
	        getContentPane().add(txtMa);
	        txtMa.setColumns(10);
	        txtMa.setEnabled(false);
	        txtMa.setText(mads);

	        JLabel lblThongTinCaNhan = new JLabel("Thông tin cá nhân");
	        lblThongTinCaNhan.setHorizontalAlignment(SwingConstants.CENTER);
	        lblThongTinCaNhan.setFont(new Font("Segoe UI", Font.PLAIN, 25));
	        lblThongTinCaNhan.setBounds(10, 0, 606, 60);
	        getContentPane().add(lblThongTinCaNhan);

	        JLabel lblSDT = new JLabel("SĐT :");
	        lblSDT.setFont(new Font("Segoe UI", Font.PLAIN, 20));
	        lblSDT.setBounds(197, 203, 70, 20);
	        getContentPane().add(lblSDT);

	        txtHoTen = new JTextField();
	        txtHoTen.setColumns(10);
	        txtHoTen.setBounds(329, 115, 220, 20);
	        txtHoTen.setEnabled(false);
	        getContentPane().add(txtHoTen);
	        txtHoTen.setText(hoten);

	        JLabel lblemail = new JLabel("Email :");
	        lblemail.setFont(new Font("Tahoma", Font.PLAIN, 20));
	        lblemail.setBounds(197, 244, 70, 20);
	        getContentPane().add(lblemail);

	        txtChucVu = new JTextField();
	        txtChucVu.setColumns(10);
	        txtChucVu.setBounds(329, 158, 220, 20);
	        txtChucVu.setEnabled(false);
	        if (quyen == 15) {
	            txtChucVu.setText("Quản trị viên");
	        } else if (quyen == 2) {
	            txtChucVu.setText("Quản lý");
	        } else if (quyen == 4) {
	            txtChucVu.setText("Nhân viên bán hàng");
	        } else if (quyen == 8) {
	            txtChucVu.setText("Nhân viên nhập hàng");
	        }
	        getContentPane().add(txtChucVu);

	        JLabel lblChucVu = new JLabel("Chức vụ :");
	        lblChucVu.setFont(new Font("Segoe UI", Font.PLAIN, 20));
	        lblChucVu.setBounds(197, 154, 100, 20);
	        getContentPane().add(lblChucVu);

	        txtSDT = new JTextField();
	        txtSDT.setColumns(10);
	        txtSDT.setBounds(329, 203, 220, 20);
	        txtSDT.setEnabled(false);
	        if (quyen == 15) {
	            txtSDT.setText("Không có");
	        } else {
	            txtSDT.setText(sodt);
	        }
	        getContentPane().add(txtSDT);

	        JLabel lblMa = new JLabel("Mã nhân viên :");
	        lblMa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
	        lblMa.setBounds(197, 78, 130, 20);
	        getContentPane().add(lblMa);

	        txtEmail = new JTextField();
	        txtEmail.setColumns(10);
	        txtEmail.setBounds(329, 247, 220, 20);
	        txtEmail.setEnabled(false);
	        if (quyen == 15) {
	            txtEmail.setText("Không có");
	        } else {
	            txtEmail.setText(email);
	        }
	        getContentPane().add(txtEmail);

	        repainting();
	    }

	    void repainting() {
	        Theme.setTheme(this);
	        getRootPane().putClientProperty("JRootPane.titleBarBackground", Theme.DARK);
	        getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.WHITE);
	        getContentPane().setBackground(Theme.LIGHT);
	    }
	}
}