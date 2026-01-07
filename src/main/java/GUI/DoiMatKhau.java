package GUI;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BUS.TaiKhoanBUS;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class DoiMatKhau extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtTenTaiKhoan;
	private TaiKhoanBUS bus = new TaiKhoanBUS();
	private JPasswordField txtMatKhauCu;
	private JPasswordField txtMatKhauMoi;
	private JPasswordField txtXacNhanMatKhau;

	public DoiMatKhau(String maDS, String username, HomeGUI home) {
		setIconImage(new ImageIcon("img/icon.png").getImage());
		setBounds(100, 100, 531, 335);
		setResizable(false);
		setTitle("Đổi mật khẩu");
		setModalityType(DEFAULT_MODALITY_TYPE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setOpaque(false);
			panel.setPreferredSize(new Dimension(10, 40));
			contentPanel.add(panel, BorderLayout.NORTH);
			panel.setLayout(new GridLayout(1, 1, 0, 0));
			{
				JLabel lblNewLabel = new JLabel("ĐỔI MẬT KHẨU");
				lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				panel.add(lblNewLabel);
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setOpaque(false);
			panel.setPreferredSize(new Dimension(100, 60));
			contentPanel.add(panel, BorderLayout.SOUTH);
			panel.setLayout(null);
			JButton btnLuu = new JButton("Lưu");
			btnLuu.addActionListener(new ActionListener() {
			    @SuppressWarnings("deprecation")
			    public void actionPerformed(ActionEvent e) {
			        if (txtMatKhauCu.getText().isEmpty() || txtMatKhauMoi.getText().isEmpty() || txtXacNhanMatKhau.getText().isEmpty()) {
			            ThongBao.baoLoi("Không được để trống thông tin!!!");
			            return;
			        } else {
			            if (!txtTenTaiKhoan.getText().equals(username) && bus.usernameExists(txtTenTaiKhoan.getText())) {
			                ThongBao.baoLoi("Username đã tồn tại. Vui lòng chọn username khác");
			                return;							
			            } else if (bus.dangNhap(username, txtMatKhauCu.getText()) == null) {
			                ThongBao.baoLoi("Vui lòng nhập đúng mật khẩu cũ");
			                return;							
			            } else if (txtMatKhauCu.getText().equals(txtMatKhauMoi.getText())) {
			                ThongBao.baoLoi("Mật khẩu mới không được trùng với mật khẩu cũ!!!");
			                return;
			            } else if (txtMatKhauMoi.getText().length() < 6) {
			                ThongBao.baoLoi("Mật khẩu mới phải có tối thiểu 6 kí tự!!!");
			                return;
			            } else if (!txtMatKhauMoi.getText().matches("[a-zA-Z0-9]+")) {
			                ThongBao.baoLoi("Mật khẩu mới không được chứa kí tự đặc biệt!!!");
			                return;
			            } else if (!txtMatKhauMoi.getText().equals(txtXacNhanMatKhau.getText())) {
			                ThongBao.baoLoi("Mật khẩu xác nhận phải trùng với mật khẩu mới!!!");
			                return;
			            } else {
			                boolean result = bus.updateMatKhau(maDS, txtTenTaiKhoan.getText(), txtMatKhauMoi.getText());
			                if (result) {
			                    ThongBao.thongBao("Đổi mật khẩu thành công. Vui lòng đăng nhập lại");
			                    setVisible(false);
			                    home.setVisible(false);
			                    new DangNhapGUI(1).setVisible(true);
			                } else {
			                    ThongBao.thongBao("Đã có lỗi xảy ra. Đổi mật khẩu thất bại.");
			                }
			            }
			        }
			    }
			});
			btnLuu.setBounds(60, 10, 120, 40);
			btnLuu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			btnLuu.setIcon(new ImageIcon(new ImageIcon("img/Save.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
			panel.add(btnLuu);
			
			JButton btnHuy = new JButton("Hủy");
			btnHuy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}
			});
			btnHuy.setBounds(325, 10, 120, 40);
			btnHuy.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			btnHuy.setIcon(new ImageIcon(new ImageIcon("img/cancel.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
			panel.add(btnHuy);
		}
		{
			JPanel panel = new JPanel();
			panel.setOpaque(false);
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JPanel panel_1 = new JPanel();
				panel_1.setOpaque(false);
				panel_1.setPreferredSize(new Dimension(200, 10));
				panel.add(panel_1, BorderLayout.WEST);
				panel_1.setLayout(null);
				
				JLabel lblNewLabel_1 = new JLabel("Mật khẩu cũ :");
				lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
				lblNewLabel_1.setBounds(10, 55, 180, 30);
				panel_1.add(lblNewLabel_1);
				
				JLabel lblNewLabel_2 = new JLabel("Mật khẩu mới :");
				lblNewLabel_2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
				lblNewLabel_2.setBounds(10, 100, 180, 30);
				panel_1.add(lblNewLabel_2);
				
				JLabel lblNewLabel_3 = new JLabel("Nhập lại mật khẩu mới :");
				lblNewLabel_3.setFont(new Font("Segoe UI", Font.PLAIN, 16));
				lblNewLabel_3.setBounds(10, 145, 180, 30);
				panel_1.add(lblNewLabel_3);
				
				JLabel lblNewLabel_4 = new JLabel("Tên tài khoản :");
				lblNewLabel_4.setFont(new Font("Segoe UI", Font.PLAIN, 16));
				lblNewLabel_4.setBounds(10, 10, 180, 30);
				panel_1.add(lblNewLabel_4);
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setOpaque(false);
				panel.add(panel_1, BorderLayout.CENTER);
				panel_1.setLayout(null);
				
				txtTenTaiKhoan = new JTextField();
				txtTenTaiKhoan.setEnabled(false);
				txtTenTaiKhoan.setFont(new Font("Segoe UI", Font.PLAIN, 16));
				txtTenTaiKhoan.setText(username);
				txtTenTaiKhoan.setBounds(10, 10, 285, 30);
				panel_1.add(txtTenTaiKhoan);
				txtTenTaiKhoan.setColumns(10);
				
				txtMatKhauCu = new JPasswordField();
				txtMatKhauCu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
				txtMatKhauCu.setBounds(10, 55, 242, 30);
				panel_1.add(txtMatKhauCu);
				
				txtMatKhauMoi = new JPasswordField();
				txtMatKhauMoi.setFont(new Font("Segoe UI", Font.PLAIN, 16));
				txtMatKhauMoi.setBounds(10, 100, 242, 30);
				panel_1.add(txtMatKhauMoi);
				
				txtXacNhanMatKhau = new JPasswordField();
				txtXacNhanMatKhau.setFont(new Font("Segoe UI", Font.PLAIN, 16));
				txtXacNhanMatKhau.setBounds(10, 145, 242, 30);
				panel_1.add(txtXacNhanMatKhau);
				
				JButton btn_passold = new JButton();
				btn_passold.setIcon(new ImageIcon("img/eye_open_" + Theme.THEME + ".png"));
				btn_passold.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						txtMatKhauCu.setEchoChar('\0');
						btn_passold.setIcon(new ImageIcon("img/eye_close_" + Theme.THEME + ".png"));
					}			

					public void mouseReleased(MouseEvent e) {
						txtMatKhauCu.setEchoChar('•');
						btn_passold.setIcon(new ImageIcon("img/eye_open_" + Theme.THEME + ".png"));
					}
				});
				btn_passold.setBounds(250, 55, 45, 30);
				panel_1.add(btn_passold);
				
				JButton btn_passnew = new JButton();
				btn_passnew.setIcon(new ImageIcon("img/eye_open_" + Theme.THEME + ".png"));
				btn_passnew.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						txtMatKhauMoi.setEchoChar('\0');
						btn_passnew.setIcon(new ImageIcon("img/eye_close_" + Theme.THEME + ".png"));
					}			

					public void mouseReleased(MouseEvent e) {
						txtMatKhauMoi.setEchoChar('•');
						btn_passnew.setIcon(new ImageIcon("img/eye_open_" + Theme.THEME + ".png"));
					}
				});
				btn_passnew.setBounds(250, 100, 45, 30);
				panel_1.add(btn_passnew);
				
				JButton btn_passnew1 = new JButton();
				btn_passnew1.setIcon(new ImageIcon("img/eye_open_" + Theme.THEME + ".png"));
				btn_passnew1.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						txtXacNhanMatKhau.setEchoChar('\0');
						btn_passnew1.setIcon(new ImageIcon("img/eye_close_" + Theme.THEME + ".png"));
					}			

					public void mouseReleased(MouseEvent e) {
						txtXacNhanMatKhau.setEchoChar('•');
						btn_passnew1.setIcon(new ImageIcon("img/eye_open_" + Theme.THEME + ".png"));
					}
				});
				btn_passnew1.setBounds(250, 145, 45, 30);
				panel_1.add(btn_passnew1);
			}
		}
		repainting();
	}
	
	void repainting() {
		Theme.setTheme(this);
		getRootPane().putClientProperty("JRootPane.titleBarBackground", Theme.DARK);
		getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.WHITE);
		contentPanel.setBackground(Theme.LIGHT);
	}
}
