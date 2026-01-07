package GUI;

import javax.swing.*;
import java.awt.*;
import java.lang.Math;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import BUS.TaiKhoanBUS;
import javax.swing.border.EmptyBorder;

public class PhanQuyenGUI extends JDialog {
	private static final long serialVersionUID = 1L;
	private JCheckBox chkQuanLy;
	private JCheckBox chkBanHang;	
	private JCheckBox[] arrCheck = new JCheckBox[3];
	private int quyen;
	private JPanel panelForm;
	private JPanel panelButton;
	private JCheckBox chkNhapHang;
	
	public PhanQuyenGUI(String maDS) {
		setIconImage(new ImageIcon("img/icon.png").getImage());
		setModal(true);
		setBounds(100, 100, 381, 280);
		setLocationRelativeTo(null);
		setTitle("Phân quyền tài khoản");
		getContentPane().setLayout(new BorderLayout(0, 0));
		((JComponent) getContentPane()).setBorder(new EmptyBorder(10,10,10,10));
		
		JLabel lblTitle = new JLabel("PHÂN QUYỀN");
		lblTitle.setPreferredSize(new Dimension(0, 60));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 25));
		getContentPane().add(lblTitle, BorderLayout.NORTH);
		
		panelForm = new JPanel();
		panelForm.setOpaque(false);
		getContentPane().add(panelForm, BorderLayout.CENTER);
		panelForm.setLayout(new BoxLayout(panelForm, BoxLayout.Y_AXIS));
		
		chkQuanLy = new JCheckBox("Quản lí");
		chkQuanLy.setMaximumSize(new Dimension(200, 40));
		panelForm.add(chkQuanLy);
		chkQuanLy.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		
		chkBanHang = new JCheckBox("Nhân viên bán hàng");
		chkBanHang.setMaximumSize(new Dimension(200, 40));
		panelForm.add(chkBanHang);
		chkBanHang.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		
		chkNhapHang = new JCheckBox("Nhân viên nhập hàng");
		chkNhapHang.setMaximumSize(new Dimension(200, 40));
		chkNhapHang.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		panelForm.add(chkNhapHang);
		
		panelButton = new JPanel();
		panelButton.setOpaque(false);
		getContentPane().add(panelButton, BorderLayout.SOUTH);
		panelButton.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 0));
		
		JButton btnLuu = new JButton("LƯU");
		btnLuu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				if (new TaiKhoanBUS().updateQuyen(maDS, luuQuyen())) ThongBao.thongBao("Phân quyền thành công");
				else ThongBao.baoLoi("Lỗi hệ thống. Phân quyền thất bại");
			}
		});
		btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnLuu.setForeground(new Color(255, 255, 255));
		btnLuu.setBackground(new Color(11, 101, 140));
		btnLuu.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnLuu.setBounds(70, 313, 120, 30);
		btnLuu.setBorderPainted(false);
		btnLuu.setFocusPainted(false);
		btnLuu.setPreferredSize(new Dimension(100,40));
		btnLuu.setIcon(new ImageIcon(new ImageIcon("img/Icon/Save.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		panelButton.add(btnLuu);
		
		JButton btnHuy = new JButton("HỦY");
		btnHuy.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnHuy.setForeground(new Color(255, 255, 255));
		btnHuy.setBackground(new Color(11, 101, 140));
		btnHuy.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnHuy.setBounds(70, 313, 120, 30);
		btnHuy.setBorderPainted(false);
		btnHuy.setFocusPainted(false);
		btnHuy.setPreferredSize(new Dimension(100,40));
		btnHuy.setIcon(new ImageIcon(new ImageIcon("img/Icon/Cancel.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		panelButton.add(btnHuy);
		btnHuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		chkQuanLy.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            if (chkQuanLy.isSelected()) {
	                chkBanHang.setSelected(false);
	                chkNhapHang.setSelected(false);
	            }
	        }
	    });

	    chkBanHang.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            if (chkBanHang.isSelected()) {
	                chkQuanLy.setSelected(false);
	                chkNhapHang.setSelected(false);
	            }
	        }
	    });

	    chkNhapHang.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            if (chkNhapHang.isSelected()) {
	                chkQuanLy.setSelected(false);
	                chkBanHang.setSelected(false);
	            }
	        }
	    });
		repainting();
	}
	
	int getQuyen() {
		return quyen;
	}
	
	void setQuyen(int quyen) {
		this.quyen = quyen;
		init(quyen);
	}
	
	private int luuQuyen() {
		int tong = 0;
		for (int i=1; i<=arrCheck.length; i++)
			if (arrCheck[i-1].isSelected()) tong += Math.pow(2,i);
		return tong;
	}
	
	private void init(int quyen) {
		arrCheck[0] = chkQuanLy;
		arrCheck[1] = chkBanHang;	
		arrCheck[2] = chkNhapHang;	
		
		for (int i=arrCheck.length; i>0; i--)
			if (quyen - Math.pow(2,i) >= 0) {
				quyen -= Math.pow(2,i);
				arrCheck[i-1].setSelected(true);
			}
			else arrCheck[i-1].setSelected(false);
	}
	
	void repainting() {
		Theme.setTheme(this);
		getRootPane().putClientProperty("JRootPane.titleBarBackground", Theme.DARK);
		getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.WHITE);
		getContentPane().setBackground(Theme.LIGHT);
	}
}
