package GUI;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import com.formdev.flatlaf.*;

import BUS.TaiKhoanBUS;
import DTO.RoundedBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DangNhapGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private TaiKhoanBUS bus = new TaiKhoanBUS();
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JLabel lblLogo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(new FlatLightLaf());
					DangNhapGUI frame = new DangNhapGUI(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DangNhapGUI(int mau) {
		UIManager.put("Button.arc", 20);
		Theme.setTheme(mau, this);
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
		setTitle("Đăng nhập");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Theme.LIGHT);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnDangNhap = new JButton("Đăng nhập");
		btnDangNhap.setBackground(new Color(11, 101, 140));;
		btnDangNhap.setForeground(new Color(255, 255, 255));
		
		
		

//		btnDangNhap.setIcon(new ImageIcon(new ImageIcon("img/login.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		getRootPane().setDefaultButton(btnDangNhap);
		btnDangNhap.setMnemonic(KeyEvent.VK_ENTER);
	//	btnDangNhap.setBorder(new EmptyBorder(10, 10, 10, 10));
		btnDangNhap.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		btnDangNhap.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if (!bus.isServerOnline()) {
					ThongBao.baoLoi("Hệ quản trị CSDL hiện tại đang chưa được mở");
					return;
				}
				
				if (txtUsername.getText().isEmpty()) {
					ThongBao.baoLoi("Vui lòng nhập username của tài khoản");
					return;
				}				
				
				if (txtPassword.getText().isEmpty()) {
					ThongBao.baoLoi("Vui lòng nhập mật khẩu của tài khoản");
					return;
				}				
				
				String[] chiTiet = bus.dangNhap(txtUsername.getText(), txtPassword.getText());
				if (chiTiet == null) {
					ThongBao.baoLoi("Vui lòng kiểm tra username hoặc mật khẩu");
					return;
				}
				ThongBao.thongBao("Đăng nhập thành công");
				setVisible(false);
				HomeGUI home = new HomeGUI(chiTiet[0], chiTiet[1], chiTiet[2], Integer.parseInt(chiTiet[3]), chiTiet[4], chiTiet[5], mau);
				
				home.getRootPane().putClientProperty("JRootPane.titleBarBackground", Theme.DARK);
				home.getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.white);
				home.setExtendedState(JFrame.MAXIMIZED_BOTH);
				home.setVisible(true);
			}
		});
		btnDangNhap.setBounds(40, 297, 165, 40);
		contentPane.add(btnDangNhap);
		
		lblLogo = new JLabel("");
		lblLogo.setVerticalAlignment(SwingConstants.BOTTOM);
		lblLogo.setIcon(new ImageIcon("img/logo_" + Theme.THEME + ".png"));
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblLogo.setBounds(0, 0, 434, 84);
		contentPane.add(lblLogo);
		
		JLabel lblDangNhap = new JLabel("ĐĂNG NHẬP");
		lblDangNhap.setHorizontalAlignment(SwingConstants.CENTER);
		lblDangNhap.setFont(new Font("Segoe UI", Font.BOLD, 30));
		lblDangNhap.setBounds(0, 95, 434, 49);
		contentPane.add(lblDangNhap);
		
		JLabel lblUser = new JLabel("Username:");
		lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblUser.setBounds(10, 164, 94, 32);
		contentPane.add(lblUser);
		
		txtUsername = new JTextField("Admin");
		txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtUsername.setBounds(114, 165, 310, 32);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblPassword.setBounds(10, 225, 94, 32);
		contentPane.add(lblPassword);
		
		txtPassword = new JPasswordField("123456");
		txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtPassword.setBounds(114, 225, 278, 32);
		contentPane.add(txtPassword);
		
		JButton btnThoat = new JButton("Thoát");
		btnThoat.setBackground(new Color(11, 101, 140));;
		btnThoat.setForeground(new Color(255, 255, 255));
		btnThoat.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		
//		btnThoat.setIcon(new ImageIcon(new ImageIcon("img/thoat.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		btnThoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ThongBao.cauHoi("Bạn có muốn thoát ứng dụng?") == JOptionPane.YES_OPTION)
					System.exit(1);
				else
					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			}
		});
		btnThoat.setBounds(245, 297, 165, 40);
		contentPane.add(btnThoat);
		
		JButton btnEye = new JButton();
		btnEye.setIcon(new ImageIcon("img/eye_open_" + Theme.THEME + ".png"));
		btnEye.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				txtPassword.setEchoChar('\0');
				btnEye.setIcon(new ImageIcon("img/eye_close_" + Theme.THEME + ".png"));
			}			

			public void mouseReleased(MouseEvent e) {
				txtPassword.setEchoChar('•');
				btnEye.setIcon(new ImageIcon("img/eye_open_" + Theme.THEME + ".png"));
			}
		});
		btnEye.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		btnEye.setBounds(392, 225, 32, 32);
		contentPane.add(btnEye);
		
		setLocationRelativeTo(null);
		setResizable(false);
		
		getRootPane().putClientProperty("JRootPane.titleBarBackground", Theme.DARK);
		getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.white);
	}
}
