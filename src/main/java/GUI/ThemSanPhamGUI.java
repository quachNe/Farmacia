package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import BUS.AnhBUS;
import BUS.ChuyBUS;
import BUS.DieuTriBUS;
import BUS.LoaiSanPhamBUS;
import BUS.NhaSanXuatBUS;
import BUS.SanPhamBUS;
import BUS.ThanhPhanBUS;
import DTO.DieuTri;
import DTO.SanPham;
import DTO.ThanhPhan;
import javax.swing.JTextArea;

public class ThemSanPhamGUI extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtMaSP;
	private JTextField txtTenSP;
	private JTextField txtQuyCach;
	private JTextArea textThanhPhan;
	private JTextArea textLuuY;
	private LoaiSanPhamBUS loaisanphambus = new LoaiSanPhamBUS();
	private NhaSanXuatBUS nhasanxuatbus = new NhaSanXuatBUS();
	private SanPhamBUS sanphambus = new SanPhamBUS();
	private ThanhPhanBUS thanhphanbus = new ThanhPhanBUS();
	private DieuTriBUS dieutribus = new DieuTriBUS();
	private static JComboBox<String> cbbLoai;
	private static String[] arrLspArray;
	private static String[] arrNsxArray;
	private static JLabel lblLoaiSP;
	private static JLabel lblNhaSX;
	private JTextArea textDieuTri;
	private static String mspUP;
	private static JComboBox<String> cbbNhaSX;
	private static JComboBox<String> cbbXuatXu;
	private static JComboBox<String> cbbKeToa;
	String[] arrKetoa = {"Cần kê toa", "Không cần kê toa"};
	DefaultComboBoxModel<String> defauKetoa = new DefaultComboBoxModel<>(arrKetoa);
	private JLabel lblHinhAnh;
	private String  tenAnh;
	private String currentDirectory = System.getProperty("user.dir");
	private JFileChooser f = new JFileChooser(currentDirectory + "/img/HinhSanPham");
	private File ftenanh = f.getSelectedFile();
	private String imageName = "";
	public void taiAnh(String anh) {
		String thuMucChuaAnh = getFolderPath() + "/img/HinhSanPham/";
		String duongdananh = thuMucChuaAnh + anh;
		changeImage(duongdananh);
		imageName=anh;
	}
	
	public void themanh() {
		currentDirectory = getFolderPath() + "/img";
		JFileChooser f = new JFileChooser(currentDirectory);
		FileNameExtensionFilter filer = new FileNameExtensionFilter("All Pic", "png", "jpg", "jpeg", "gef");
		f.addChoosableFileFilter(filer);
		f.setDialogTitle("Mở file");
		int result = f.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = f.getSelectedFile();
			tenAnh= selectedFile.getName();
			taiAnh(tenAnh);
		}
	}
	public String getFolderPath() {
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

	public void changeImage(String duongdananh) {
		try {
			ImageIcon resizedImage = ResizeImage(duongdananh);
			if (resizedImage != null) {
				lblHinhAnh.setIcon(resizedImage);
	        } else {
	        	System.out.println("Không thể tải ảnh.");
	        }
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public ImageIcon ResizeImage(String imagePath) {
		ImageIcon resizedImage = null;
		try {
			File file = new File(imagePath);
			if (!file.exists()) {
				System.out.println("Tệp ảnh không tồn tại: " + imagePath);
				return null;
			}
			BufferedImage originalImage = ImageIO.read(file);
			if (originalImage == null) {
				System.out.println("Không thể đọc ảnh từ tệp.");
				return null;
			}
			Image img = originalImage.getScaledInstance(lblHinhAnh.getWidth(), lblHinhAnh.getHeight(), Image.SCALE_SMOOTH);
			resizedImage = new ImageIcon(img);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resizedImage;
	}
	public ThemSanPhamGUI() {
		setTitle("Thêm sản phẩm");
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon("img/icon.png").getImage());
	    setModal(true);
	    setResizable(false);
		setBounds(100, 100, 1090, 440);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 0, 0);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		
		JLabel lblNewLabel = new JLabel("THÊM SẢN PHẨM");
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 10, 1067, 34);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Mã SP :");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(230, 70, 80, 25);
		getContentPane().add(lblNewLabel_1);
		
		String msp = sanphambus.getLatestProductId();
		String numberPart = msp.substring(2);
        int number = Integer.parseInt(numberPart);
        number++;
        String paddedNumber = String.format("%04d", number);
        String newProductId = "SP" + paddedNumber;
		txtMaSP = new JTextField();
		txtMaSP.setText(newProductId);
		txtMaSP.setEnabled(false);
		txtMaSP.setBounds(330, 70, 200, 25);
		getContentPane().add(txtMaSP);
		txtMaSP.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Tên SP :");
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(230, 125, 80, 25);
		getContentPane().add(lblNewLabel_1_1);
		
		txtTenSP = new JTextField();
		txtTenSP.setColumns(10);
		txtTenSP.setBounds(330, 127, 200, 25);
		getContentPane().add(txtTenSP);
		
		JLabel lblNewLabel_1_2 = new JLabel("Loại SP :");
		lblNewLabel_1_2.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_2.setBounds(230, 169, 80, 25);
		getContentPane().add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Nhà SX :");
		lblNewLabel_1_3.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_3.setBounds(230, 231, 80, 25);
		getContentPane().add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("Quy cách :");
		lblNewLabel_1_4.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_4.setBounds(230, 301, 103, 25);
		getContentPane().add(lblNewLabel_1_4);
		
		txtQuyCach = new JTextField();
		txtQuyCach.setColumns(10);
		txtQuyCach.setBounds(330, 303, 200, 25);
		getContentPane().add(txtQuyCach);
		
		lblLoaiSP = new JLabel("");
		lblLoaiSP.setBounds(330, 198, 200, 25);
		getContentPane().add(lblLoaiSP);
		
		lblNhaSX = new JLabel("");
		lblNhaSX.setBounds(330, 268, 200, 25);
		getContentPane().add(lblNhaSX);
		
		ArrayList<String> arrLsp = loaisanphambus.getAllMaLoai();
		arrLspArray = arrLsp.toArray(new String[arrLsp.size()]);
		DefaultComboBoxModel<String> defauLsp = new DefaultComboBoxModel<>(arrLspArray);
		cbbLoai = new JComboBox<>();
		cbbLoai.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String maloai = (String) cbbLoai.getSelectedItem();
                if (maloai != null) {
                	lblLoaiSP.setText(loaisanphambus.getTenLoaiByMaloai(cbbLoai.getSelectedItem() + ""));
				}
            }
        });
		cbbLoai.setModel(defauLsp);
		cbbLoai.setBounds(330, 169, 200, 25);
		getContentPane().add(cbbLoai);
		lblLoaiSP.setText(loaisanphambus.getTenLoaiByMaloai(cbbLoai.getItemAt(0) + ""));
		
		cbbNhaSX = new JComboBox<>();
		ArrayList<String> arrNsx = nhasanxuatbus.getAllMaNSX();
		arrNsxArray = arrNsx.toArray(new String[arrLsp.size()]);
		DefaultComboBoxModel<String> defauNsx = new DefaultComboBoxModel<>(arrNsxArray);
		cbbNhaSX.setModel(defauNsx);
		cbbNhaSX.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String mansx = (String) cbbNhaSX.getSelectedItem();
                if (mansx != null) {
                	lblNhaSX.setText(nhasanxuatbus.getTenNsxByMansx(cbbNhaSX.getSelectedItem() + ""));
				}
            }
        });
		cbbNhaSX.setBounds(330, 233, 200, 25);
		getContentPane().add(cbbNhaSX);
		lblNhaSX.setText(nhasanxuatbus.getTenNsxByMansx(cbbNhaSX.getItemAt(0) + ""));
		
		ArrayList<String> countries = sanphambus.loaddataQuocGia();
		cbbXuatXu = new JComboBox<>(countries.toArray(new String[0]));
		cbbXuatXu.setBounds(680, 70, 127, 25);
		getContentPane().add(cbbXuatXu);
		
		JLabel lblNewLabel_1_5 = new JLabel("Xuất xứ :");
		lblNewLabel_1_5.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_5.setBounds(560, 70, 80, 25);
		getContentPane().add(lblNewLabel_1_5);
		
		JLabel lblNewLabel_1_5_1 = new JLabel("Cần kê toa :");
		lblNewLabel_1_5_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_5_1.setBounds(820, 71, 110, 25);
		getContentPane().add(lblNewLabel_1_5_1);
		
		cbbKeToa = new JComboBox<>();
		cbbKeToa.setModel(defauKetoa);
		cbbKeToa.setSelectedIndex(0);
		cbbKeToa.setBounds(940, 70, 127, 25);
		getContentPane().add(cbbKeToa);
		
		JLabel lblNewLabel_1_5_1_1 = new JLabel("Thành phần :");
		lblNewLabel_1_5_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_5_1_1.setBounds(560, 126, 120, 25);
		getContentPane().add(lblNewLabel_1_5_1_1);
		
		JLabel lblNewLabel_1_4_1 = new JLabel("Điều trị :");
		lblNewLabel_1_4_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_4_1.setBounds(560, 198, 80, 25);
		getContentPane().add(lblNewLabel_1_4_1);
		
		JLabel lblNewLabel_1_4_1_1 = new JLabel("Lưu ý :");
		lblNewLabel_1_4_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_4_1_1.setBounds(560, 272, 80, 25);
		getContentPane().add(lblNewLabel_1_4_1_1);
		
		JButton btnLuu = new JButton("Lưu");
		btnLuu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String anhSPName = imageName;
				if (txtTenSP.getText().isEmpty() || txtQuyCach.getText().isEmpty() || textThanhPhan.getText().isEmpty() || 
						textDieuTri.getText().isEmpty() || cbbLoai.getSelectedItem() == null || cbbNhaSX.getSelectedItem() == null || cbbKeToa.getSelectedItem() == null
						|| textLuuY.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin");
					return;
				}else if (anhSPName == "") {
					ThongBao.baoLoi("Vui lòng chọn ảnh của sản phẩm!");
					return;
				}else {
					String ma = txtMaSP.getText();
					String tensp = txtTenSP.getText();
					String maloai = (String) cbbLoai.getSelectedItem();
					String mansx = (String) cbbNhaSX.getSelectedItem();
					String quycach = txtQuyCach.getText();
					String xuatxu = (String) cbbXuatXu.getSelectedItem();
					String canketoa = (String) cbbKeToa.getSelectedItem();
					boolean ketoa = canketoa.equals("true");
					String thanhPhans = textThanhPhan.getText();
					String[] arrtp = thanhPhans.split(",");
					String dieuTris = textDieuTri.getText();
					String[] arrdieutri = dieuTris.split(",");
					String note = textLuuY.getText();
					boolean tt = true;
					boolean result = sanphambus.add(new SanPham(ma, tensp, maloai,mansx, quycach, xuatxu, ketoa, tt));
					boolean result_1 = new AnhBUS().addAnh(ma, anhSPName);
					boolean result_2 = new ChuyBUS().addChuy(ma, note);
					if (result == true && result_1 == true && result_2 == true) {
						for (String s : arrtp) {
							ThanhPhan tp = new ThanhPhan(ma, s);
							thanhphanbus.addThanhPhan(tp);
						}
						for (String s : arrdieutri) {
							DieuTri dt = new DieuTri(ma, s);
							dieutribus.addBenhDieuTri(dt);
						}
						JOptionPane.showMessageDialog(null, "Thêm thành công");
						dispose();
						SanPhamGUI.loadDataSp();
					}
				}
			}
		});
		btnLuu.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnLuu.setForeground(new Color(255, 255, 255));
		btnLuu.setBackground(new Color(11, 101, 140));
		btnLuu.setBorderPainted(false);
		btnLuu.setFocusPainted(false);
		btnLuu.setIcon(new ImageIcon(new ImageIcon("img/Icon/Save.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		btnLuu.setBounds(820, 336, 110, 31);
		getContentPane().add(btnLuu);
		
		JButton btnHuy = new JButton("Hủy");
		btnHuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnHuy.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnHuy.setForeground(new Color(255, 255, 255));
		btnHuy.setBackground(new Color(11, 101, 140));
		btnHuy.setBorderPainted(false);
		btnHuy.setFocusPainted(false);
		btnHuy.setIcon(new ImageIcon(new ImageIcon("img/Icon/Cancel.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		btnHuy.setBounds(957, 336, 110, 31);
		getContentPane().add(btnHuy);
		
		JButton btnThemAnh = new JButton("Thêm ảnh");
		btnThemAnh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				themanh();
			}
		});
		btnThemAnh.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnThemAnh.setForeground(new Color(255, 255, 255));
		btnThemAnh.setBackground(new Color(11, 101, 140));
		btnThemAnh.setBounds(36, 335, 150, 31);
		btnThemAnh.setBorderPainted(false);
		btnThemAnh.setFocusPainted(false);
		btnThemAnh.setIcon(new ImageIcon(new ImageIcon("img/Icon/Picture.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		getContentPane().add(btnThemAnh);
		
		lblHinhAnh = new JLabel("");
		lblHinhAnh.setBackground(Color.WHITE);
		lblHinhAnh.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lblHinhAnh.setForeground(new Color(255, 255, 255));
		lblHinhAnh.setBounds(10, 70, 210, 256);
		getContentPane().add(lblHinhAnh);
		
		textThanhPhan = new JTextArea();
		textThanhPhan.setBounds(680, 125, 387, 69);
//		getContentPane().add(textThanhPhan);
		textThanhPhan.setLineWrap(true); // Bật xuống dòng tự động
		textThanhPhan.setWrapStyleWord(true); // Chỉ xuống dòng tại vị trí từ
		textThanhPhan.setEditable(true); // Nếu bạn không muốn người dùng chỉnh sửa

		// Bọc JTextArea trong JScrollPane
		JScrollPane scrollPaneThanhPhan = new JScrollPane(textThanhPhan);
		scrollPaneThanhPhan.setBounds(680, 118, 386, 76); // Đặt vị trí và kích thước cho JScrollPane

		// Thêm JScrollPane vào panel
		getContentPane().add(scrollPaneThanhPhan);
		
		textDieuTri = new JTextArea();
		textDieuTri.setLineWrap(true);
		textDieuTri.setWrapStyleWord(true);
		textDieuTri.setEditable(true);

		JScrollPane scrollPaneDieuTri = new JScrollPane(textDieuTri);
		scrollPaneDieuTri.setBounds(680, 201, 386, 57);
		getContentPane().add(scrollPaneDieuTri);
		
		textLuuY = new JTextArea();
		textLuuY.setLineWrap(true); // Bật xuống dòng tự động
		textLuuY.setWrapStyleWord(true); // Chỉ xuống dòng tại vị trí từ
		textLuuY.setEditable(true); // Nếu bạn không muốn người dùng chỉnh sửa
		JScrollPane scrollPaneLuuY = new JScrollPane(textLuuY);
		scrollPaneLuuY.setBounds(680, 271, 386, 55); // Đặt vị trí và kích thước cho JScrollPane
		getContentPane().add(scrollPaneLuuY);
		
		repainting();
	}
	
	void repainting() {
		Theme.setTheme(this);
		getRootPane().putClientProperty("JRootPane.titleBarBackground", Theme.DARK);
		getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.WHITE);
		contentPanel.setBackground(Theme.LIGHT);
		contentPanel.setLayout(null);
	}
}
