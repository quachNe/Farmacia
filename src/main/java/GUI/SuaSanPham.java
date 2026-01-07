package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JList;

public class SuaSanPham extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static JTextField txtMaSP;
	private static JTextField txtTenSP;
	private static JTextField txtQuyCach;
	private static JTextArea textDieuTri;
	private static JTextArea textThanhPhan;
	private static JTextArea textLuuY;
	private static Map<String, Boolean> sanPhamBiKhoa = new HashMap<>();
	private static LoaiSanPhamBUS loaisanphambus = new LoaiSanPhamBUS();
	private static NhaSanXuatBUS nhasanxuatbus = new NhaSanXuatBUS();
	private static SanPhamBUS sanphambus = new SanPhamBUS();
	private static ThanhPhanBUS thanhphanbus = new ThanhPhanBUS();
	private static DieuTriBUS dieutribus = new DieuTriBUS();
	private static ChuyBUS chuybus = new ChuyBUS();
	private static AnhBUS anhbus = new AnhBUS();
	private static JComboBox<String> cbbLoai;
	private static String[] arrLspArray;
	private static String[] arrNsxArray;
	private static JLabel lblLoaiSP;
	private static JLabel lblNhaSX;
	private static String mspUP;
	private static JComboBox<String> cbbNhaSX;
	private static JComboBox<String> cbbXuatXu;
	private static JComboBox<String> cbbKeToa;
	private static JButton btnLock;
	String[] arrKetoa = {"Có", "Không"};
	DefaultComboBoxModel<String> defauKetoa = new DefaultComboBoxModel<>(arrKetoa);
	private static JLabel lblHinhAnh;
	private String  tenAnh;
	private static ArrayList<String> arrQGArray;
	private String currentDirectory = System.getProperty("user.dir");
	private JFileChooser f = new JFileChooser(currentDirectory + "/img/HinhSanPham");
	private File ftenanh = f.getSelectedFile();
	private static String imageName = "";
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
	public SuaSanPham(String mathuoc, boolean trangthai) {
		
		setTitle("Thông tin sản phẩm");
		setIconImage(new ImageIcon("img/icon.png").getImage());
	    setModal(true);
	    setResizable(false);
		setBounds(100, 100, 1090, 450);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 0, 0);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		
		JLabel lblNewLabel = new JLabel("THÔNG TIN SẢN PHẨM");
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 10, 1066, 39);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Mã SP :");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(230, 70, 80, 25);
		getContentPane().add(lblNewLabel_1);
		
		txtMaSP = new JTextField();
		txtMaSP.setText(mathuoc);
		txtMaSP.setEnabled(false);
		txtMaSP.setBounds(330, 70, 200, 25);
		getContentPane().add(txtMaSP);
		txtMaSP.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Tên SP :");
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(230, 113, 80, 25);
		getContentPane().add(lblNewLabel_1_1);
		
		txtTenSP = new JTextField();
		txtTenSP.setEnabled(false);
		txtTenSP.setColumns(10);
		txtTenSP.setBounds(330, 115, 200, 25);
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
		lblNewLabel_1_4.setBounds(230, 301, 101, 25);
		getContentPane().add(lblNewLabel_1_4);
		
		txtQuyCach = new JTextField();
		txtQuyCach.setColumns(10);
		txtQuyCach.setEnabled(false);
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
		cbbLoai.setEnabled(false);
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
		cbbNhaSX.setEnabled(false);
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
		cbbXuatXu.setBounds(680, 70, 126, 25);
		cbbXuatXu.setEnabled(false);
		getContentPane().add(cbbXuatXu);
		
		JLabel lblNewLabel_1_5 = new JLabel("Xuất xứ :");
		lblNewLabel_1_5.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_5.setBounds(560, 70, 80, 25);
		getContentPane().add(lblNewLabel_1_5);
		
		JLabel lblNewLabel_1_5_1 = new JLabel("Cần kê toa :");
		lblNewLabel_1_5_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_5_1.setBounds(816, 70, 110, 25);
		getContentPane().add(lblNewLabel_1_5_1);
		
		cbbKeToa = new JComboBox<>();
		cbbKeToa.setModel(defauKetoa);
		cbbKeToa.setSelectedIndex(0);
		cbbKeToa.setBounds(936, 70, 130, 25);
		getContentPane().add(cbbKeToa);
		cbbKeToa.setEnabled(false);
		
		JLabel lblNewLabel_1_5_1_1 = new JLabel("Thành phần :");
		lblNewLabel_1_5_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_5_1_1.setBounds(560, 113, 120, 25);
		getContentPane().add(lblNewLabel_1_5_1_1);
		
		JLabel lblNewLabel_1_4_1 = new JLabel("Điều trị :");
		lblNewLabel_1_4_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_4_1.setBounds(560, 201, 80, 25);
		getContentPane().add(lblNewLabel_1_4_1);
		
		JLabel lblNewLabel_1_4_1_1 = new JLabel("Lưu ý :");
		lblNewLabel_1_4_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_4_1_1.setBounds(560, 268, 80, 25);
		getContentPane().add(lblNewLabel_1_4_1_1);
		
		JButton btnLuu = new JButton("Cập nhật");
		btnLuu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleUp(mathuoc);
				SanPhamGUI.loadDataSp();
				dispose();
			}
		});
		btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnLuu.setForeground(new Color(255, 255, 255));
		btnLuu.setBackground(new Color(11, 101, 140));
		btnLuu.setBorderPainted(false);
		btnLuu.setFocusPainted(false);
		btnLuu.setIcon(new ImageIcon(new ImageIcon("img/Icon/Synchronize.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		btnLuu.setBounds(666, 350, 140, 31);
		getContentPane().add(btnLuu);
		
		JButton btnHuy = new JButton("Hủy");
		btnHuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnHuy.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnHuy.setForeground(new Color(255, 255, 255));
		btnHuy.setBackground(new Color(11, 101, 140));
		btnHuy.setBorderPainted(false);
		btnHuy.setFocusPainted(false);
		btnHuy.setIcon(new ImageIcon(new ImageIcon("img/Icon/Cancel.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		btnHuy.setBounds(971, 350, 95, 31);
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
		btnThemAnh.setBounds(46, 350, 145, 31);
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
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.GRAY);
		separator.setForeground(Color.BLACK);
		separator.setBounds(550, 70, 0, 256);
		getContentPane().add(separator);
		
		textDieuTri = new JTextArea();
		textDieuTri.setLineWrap(true);
		textDieuTri.setWrapStyleWord(true);
		textDieuTri.setEditable(false);
		

		JScrollPane scrollPaneDieuTri = new JScrollPane(textDieuTri);
		scrollPaneDieuTri.setBounds(680, 201, 386, 57);
		getContentPane().add(scrollPaneDieuTri);
		
		textThanhPhan = new JTextArea();
		textThanhPhan.setLineWrap(true); // Bật xuống dòng tự động
		textThanhPhan.setWrapStyleWord(true); // Chỉ xuống dòng tại vị trí từ
		//textThanhPhan.setEditable(true); // Nếu bạn không muốn người dùng chỉnh sửa
		textThanhPhan.setEditable(false);

		// Bọc JTextArea trong JScrollPane
		JScrollPane scrollPaneThanhPhan = new JScrollPane(textThanhPhan);
		scrollPaneThanhPhan.setBounds(680, 118, 386, 76); // Đặt vị trí và kích thước cho JScrollPane

		// Thêm JScrollPane vào panel
		getContentPane().add(scrollPaneThanhPhan);
		
		// Tạo JTextArea cho lưu ý
		textLuuY = new JTextArea();
		textLuuY.setLineWrap(true); // Bật xuống dòng tự động
		textLuuY.setWrapStyleWord(true); // Chỉ xuống dòng tại vị trí từ
		textLuuY.setEditable(true); // Nếu bạn không muốn người dùng chỉnh sửa
		JScrollPane scrollPaneLuuY = new JScrollPane(textLuuY);
		scrollPaneLuuY.setBounds(680, 271, 386, 55); // Đặt vị trí và kích thước cho JScrollPane

		// Thêm JScrollPane vào panel
		getContentPane().add(scrollPaneLuuY);
		
		btnLock = new JButton("");
		btnLock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			        int reponse = ThongBao.cauHoi("Bạn có chắc muốn " + (trangthai ? "khóa" : "mở khóa") + " Sản Phẩm có Mã SP = " + mathuoc + "?");

			        if (reponse == JOptionPane.YES_OPTION) {
			            if (sanPhamBiKhoa.containsKey(mathuoc) && sanPhamBiKhoa.get(mathuoc)) {
			                if (sanphambus.changeStatus(mathuoc, true)) {
			                    ThongBao.thongBao("Mở khóa thành công cho sản phẩm Mã SP: " + mathuoc);
			                    sanPhamBiKhoa.put(mathuoc, false);
			                    dispose();
			                } else {
			                    ThongBao.baoLoi("Mở khóa thất bại cho sản phẩm Mã SP: " + mathuoc);
			                }
			            } else {
			                if (sanphambus.changeStatus(mathuoc, false)) {
			                    ThongBao.thongBao("Khóa thành công cho sản phẩm Mã SP: " + mathuoc);
			                    sanPhamBiKhoa.put(mathuoc, true);
			                    dispose();
			                } else {
			                    ThongBao.baoLoi("Khóa thất bại cho sản phẩm Mã SP: " + mathuoc);
			                }
			            }
						SanPhamGUI.loadDataSp();
			        }
			}
		});
		btnLock.setForeground(Color.WHITE);
		btnLock.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnLock.setFocusPainted(false);
		btnLock.setBackground(new Color(11, 101, 140));
		btnLock.setBounds(821, 350, 140, 31);
		getContentPane().add(btnLock);
		repainting();
		ImageIcon iconLock = new ImageIcon(new ImageIcon("img/Icon/Lock.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
	    ImageIcon iconUnlock = new ImageIcon(new ImageIcon("img/Icon/Unlock.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
	    String trangThaiString = trangthai ? "Còn kinh doanh" : "Ngừng kinh doanh";
	    if (trangThaiString.equals("Còn kinh doanh")) {
	        btnLock.setText("Khóa");
	        btnLock.setIcon(iconLock);
	    } else {
	        btnLock.setText("Mở khóa");
	        btnLock.setIcon(iconUnlock);
	    }
	}
	void repainting() {
		Theme.setTheme(this);
		getRootPane().putClientProperty("JRootPane.titleBarBackground", Theme.DARK);
		getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.WHITE);
		contentPanel.setBackground(Theme.LIGHT);		
	}
	public void setData(SanPham sp, boolean check) {
		mspUP = sp.getMa();
		txtTenSP.setText(sp.getTen());
		txtQuyCach.setText(sp.getQuyCach());
		int id_xx = -1;
		SanPhamBUS sanphamBus = new SanPhamBUS();
		
		arrQGArray= sanphamBus.loaddataQuocGia();
		for(int i = 0; i < arrQGArray.size();i++) {
			if(arrQGArray.get(i).equals(sp.getXuatXu())) {
				id_xx = i;
				break;
			}
		}
		cbbXuatXu.setSelectedIndex(id_xx);
		int id_lsp = -1;
		for(int i = 0; i < arrLspArray.length;i++) {
			if(arrLspArray[i].equals(sp.getLoai())) {
				id_lsp = i;
				break;
			}
		}
		cbbLoai.setSelectedIndex(id_lsp);
		int id_nsx = -1;
		for(int i = 0; i < arrNsxArray.length;i++) {
			if(arrNsxArray[i].equals(sp.getNhaSanXuat())) {
				id_nsx = i;
				break;
			}
		}
		cbbNhaSX.setSelectedIndex(id_nsx);
		
		boolean canketoa = sp.isCanKeToa();
		int ketoa = canketoa ? 0 : 1;
		cbbKeToa.setSelectedIndex(ketoa);
		
		ArrayList<String> tp = thanhphanbus.getThanhPhanByMaThuoc(mspUP);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tp.size(); i++) {
		    sb.append(tp.get(i));
		    if (i < tp.size() - 1) {
		        sb.append(",");
		    }
		}
		String thanhphan = sb.toString();
		textThanhPhan.setText(thanhphan);
		
		ArrayList<String> dt = dieutribus.getBenhDieuTriByMaThuoc(mspUP);
		StringBuilder sb1 = new StringBuilder();
		for (int i = 0; i < dt.size(); i++) {
			sb1.append(dt.get(i));
		    if (i < dt.size() - 1) {
		    	sb1.append(",");
		    }
		}
		String dieutri = sb1.toString();
		textDieuTri.setText(dieutri);
		
		String tenanh = anhbus.getAnhByMaThuoc(mspUP);
		taiAnh(tenanh);
		
		String luuy = chuybus.getTenchuyByMaChuy(mspUP);
		textLuuY.setText(luuy);
	}
	public static void handleUp(String ma) {
		String anhSPName = imageName;
	    if (txtTenSP.getText().isEmpty() || txtQuyCach.getText().isEmpty() || textThanhPhan.getText().isEmpty() || 
	    		textDieuTri.getText().isEmpty() || cbbLoai.getSelectedItem() == null || cbbNhaSX.getSelectedItem() == null || 
	            cbbKeToa.getSelectedItem() == null || cbbXuatXu.getSelectedItem() == null) {
	        JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin");
	        return;
	    }
	    String tensp = txtTenSP.getText();
	    String maloai = (String) cbbLoai.getSelectedItem();
	    String mansx = (String) cbbNhaSX.getSelectedItem();
	    String quycach = txtQuyCach.getText();
	    String xuatxu = (String) cbbXuatXu.getSelectedItem();
	    boolean ketoa = ((String) cbbKeToa.getSelectedItem()).equals("Có");
	    String[] arrtp = textThanhPhan.getText().split(",");
	    String[] arrdieutri = textDieuTri.getText().split(",");
	    String note = textLuuY.getText();
	    boolean trangThaiMacDinh = true;
	    thanhphanbus.deleteThanhPhan(ma);
	    dieutribus.deleteBenhDieuTri(ma);
	    boolean resultSP = sanphambus.update(new SanPham(ma, tensp, maloai, mansx, quycach, xuatxu, ketoa, trangThaiMacDinh));
	    boolean resultAnh = new AnhBUS().UpdateAnh(ma, anhSPName);
	    boolean resultNote = new ChuyBUS().UpdateChuy(ma, note);
	    if (resultSP && resultAnh && resultNote) {
	        for (String tp : arrtp) {
	            ThanhPhan thanhPhan = new ThanhPhan(ma, tp.trim());
	            thanhphanbus.addThanhPhan(thanhPhan);
	        }
	        for (String dt : arrdieutri) {
	            DieuTri dieuTri = new DieuTri(ma, dt.trim());
	            dieutribus.addBenhDieuTri(dieuTri);
	        }
	        JOptionPane.showMessageDialog(null, "Cập nhật thành công");
	    } else {
	        JOptionPane.showMessageDialog(null, "Cập nhật thất bại");
	    }
	}
}
