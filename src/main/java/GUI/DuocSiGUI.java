package GUI;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import javax.swing.*;  
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import BUS.AnhAvtBUS;
import BUS.DuocSiBUS;
import BUS.TaiKhoanBUS;
import DTO.DuocSi;
import DTO.RoundedBorder;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DuocSiGUI extends JPanel {
	private RoundedBorder border;
	private static final long serialVersionUID = 1L;
	private JButton btnTimKiem = new JButton("");
	private DefaultTableModel model = new DefaultTableModel(
			new Object[][] {},
			new String[] {"STT","Mã DS", "Họ Tên", "SDT", "Email" ,"Tài khoản", "Trạng thái"}
			);
	DuocSiBUS bus = new DuocSiBUS();
	private JTextField txtTimKiem;
	private JTable table;
	private JComboBox<String> cbbTimKiem;
	public void setColorCell(JTable table) {
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (!isSelected) {
					if (row % 2 == 1) {
						cell.setBackground(Color.decode("#E6E6E6"));
					} else {
						cell.setBackground(Color.WHITE);
					}
				} else {
					cell.setBackground(table.getSelectionBackground());
				}
				return cell;
			}
		};
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
	}
	public DuocSiGUI() {
		UIManager.put("Button.arc", 20);
		load(bus.getDuocSi());
		load(bus.getDuocSi());
		setMinimumSize(new Dimension(50, 100));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setPreferredSize(new Dimension(1280, 728));
		setLayout(null);
			
		txtTimKiem = new JTextField(20);
		txtTimKiem.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) { // Kiểm tra nếu phím là Enter
                    btnTimKiem.doClick(); // Giả lập nhấn nút Tìm Kiếm
                }
			}
		});
		txtTimKiem.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				if (txtTimKiem.getText().equals("")) {
					load(bus.getDuocSi());
				}
			}
		});
		txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtTimKiem.setBounds(158, 21, 290, 30);
		add(txtTimKiem);
		txtTimKiem.setColumns(10);
		RoundedBorder.setPlaceholder(txtTimKiem," Tìm Kiếm...");
		txtTimKiem.setBorder(new RoundedBorder(50));
		
		
		btnTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		btnTimKiem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtTimKiem.getText().equals(" Tìm Kiếm...") || txtTimKiem.getText().equals("")) {
					ThongBao.baoLoi("Vui lòng nhập nội dung tìm kiếm");
					return;
				} else {
				    String key = txtTimKiem.getText();
				    String option = cbbTimKiem.getSelectedItem().toString();
				    if (!key.trim().isEmpty()) { 
				        ArrayList<DuocSi> duocSiList = bus.findDuocSi(option, key);
				        if (duocSiList==null || duocSiList.isEmpty()) {
				        	ThongBao.baoLoi("Bảng không tồn tại dữ liệu");
					        txtTimKiem.setText("");
					        txtTimKiem.requestFocus();
					        return;
				        }
				        load(duocSiList);
				    }
				}
			}
		});
		btnTimKiem.setBounds(460, 21, 35, 30);
		btnTimKiem.setIcon(new ImageIcon(new ImageIcon("img/Icon/Search.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		add(btnTimKiem);
		
		
		JButton btnXuatExcel = new JButton("Xuất file");
		btnXuatExcel.setHorizontalAlignment(SwingConstants.LEFT);
		btnXuatExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser saveFileDialog = new JFileChooser(System.getProperty("user.dir"));
				saveFileDialog.setDialogTitle("Chọn vị trí cần xuất");
				saveFileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Excel Workbook (.xlsx)", "xlsx"));
				saveFileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Excel 97-2003 Workbook (.xls)", "xls"));
				saveFileDialog.setAcceptAllFileFilterUsed(false);
				if (saveFileDialog.showSaveDialog(DuocSiGUI.this) == JFileChooser.APPROVE_OPTION) {
				    String path = saveFileDialog.getSelectedFile().getAbsolutePath();
				    String desc = saveFileDialog.getFileFilter().getDescription();
				    if (!path.endsWith(".xlsx") && (!path.endsWith(".xls")))
				    	path += (desc.equals("Excel Workbook (.xlsx)")) ? ".xlsx" : ".xls";
				    if (bus.xuatExcel(model, path)) ThongBao.thongBao("Xuất file Excel thành công");
				    else ThongBao.baoLoi("Xuất file Excel thất bại");
				}
			}
		});
		btnXuatExcel.setForeground(new Color(255, 255, 255));
		btnXuatExcel.setBackground(new Color(11, 101, 140));
		btnXuatExcel.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnXuatExcel.setBounds(1080, 21, 140, 32);
		btnXuatExcel.setIcon(new ImageIcon(new ImageIcon("img/Icon/Export.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		add(btnXuatExcel);
		
		JButton btnNhapExcel = new JButton("Nhập file");
		btnNhapExcel.setHorizontalAlignment(SwingConstants.LEFT);
		btnNhapExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser openFileDialog = new JFileChooser(System.getProperty("user.dir"));
		        openFileDialog.setDialogTitle("Chọn vị trí cần nhập");
		        openFileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Excel Workbook (.xlsx)", "xlsx"));
		        openFileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Excel 97-2003 Workbook (.xls)", "xls"));
		        openFileDialog.setAcceptAllFileFilterUsed(false);
		        
		        int result = openFileDialog.showOpenDialog(DuocSiGUI.this); // Sửa từ showSaveDialog thành showOpenDialog
		        if (result == JFileChooser.APPROVE_OPTION) {
		            String filePath = openFileDialog.getSelectedFile().getAbsolutePath();
		            List<DuocSi> duocSiList = bus.nhapExcel(filePath);
		            for (DuocSi ds : duocSiList) {
		                bus.addDuocSi(ds);
		                new TaiKhoanBUS().createAccount(ds.getMa());
		            }
		            ThongBao.thongBao("Nhập tất cả dược sĩ thành công");
		            load(bus.getDuocSi());
		        }
			}
		});
		btnNhapExcel.setForeground(new Color(255, 255, 255));
		btnNhapExcel.setBackground(new Color(11, 101, 140));
		btnNhapExcel.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnNhapExcel.setBounds(920, 20, 150, 32);
		btnNhapExcel.setIcon(new ImageIcon(new ImageIcon("img/Icon/Import.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		add(btnNhapExcel);
		
		JButton btnThem = new JButton("Dược sĩ");
		btnThem.setHorizontalAlignment(SwingConstants.LEFT);
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mads = bus.generateMaDS();
				new FormThemDuocSi(mads).setVisible(true);
			}
		});	
		btnThem.setBounds(770, 20, 140, 33);
		btnThem.setForeground(new Color(255, 255, 255));
		btnThem.setBackground(new Color(11, 101, 140));
		btnThem.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnThem.setFocusPainted(false);
		btnThem.setIcon(new ImageIcon(new ImageIcon("img/Icon/Add.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		add(btnThem);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 80, 1260, 750);
		add(scrollPane);
		
		table = new JTable(model);
		table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(90);
		table.getColumnModel().getColumn(2).setPreferredWidth(160);
		table.getColumnModel().getColumn(3).setPreferredWidth(130);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(6).setPreferredWidth(85);
		table.getTableHeader().setBackground(Color.decode("#B9B9B9"));
		setColorCell(table);
		table.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        int row = table.getSelectedRow();
		        if (row != -1) {
		            String maDS = model.getValueAt(row, 1) + "";
		            AnhAvtBUS avt = new AnhAvtBUS();
		            String anhavt = avt.getAnhAvtByMads(maDS);
		            if (maDS.startsWith("DS")) {
		            	String hoten= model.getValueAt(row, 2) + "";
		            	String sdt = model.getValueAt(row, 3) + "";
		            	String email = model.getValueAt(row, 4) + "";
		            	String trangthai = model.getValueAt(row, 6) + "";
		                new FormSuaDuocSi(maDS, hoten, sdt, email,anhavt,trangthai).setVisible(true);
		                
		            } else {
		                System.out.println("Selected maDS starts with 'DS'.");
		            }
		        } else {
		            System.out.println("No row selected.");
		        }
		    }
		});
		JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.ITALIC, 18));
		scrollPane.setViewportView(table);
	
		cbbTimKiem = new JComboBox<>();
		cbbTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		cbbTimKiem.setModel(new DefaultComboBoxModel<>(new String[] {"Mã", "Họ Tên", "SDT", "Email"}));
		cbbTimKiem.setBounds(10, 21, 138, 30);
		cbbTimKiem.setBorder(new RoundedBorder(50));
		add(cbbTimKiem);
		
		JButton btnLamMoi = new JButton("");
		btnLamMoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RoundedBorder.setPlaceholder(txtTimKiem, " Tìm Kiếm...");
				load(bus.getDuocSi());
			}
		});
		btnLamMoi.setBounds(1230, 21, 40, 30);
		btnLamMoi.setForeground(new Color(255, 255, 255));
		btnLamMoi.setIcon(new ImageIcon(new ImageIcon("img/Icon/Reset.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		btnLamMoi.setBackground(new Color(11, 101, 140));
		btnLamMoi.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		add(btnLamMoi);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 62, 1260, 2);
		add(separator);
		repainting();
	}
	void repainting() {
		setBackground(Theme.LIGHT);
	}
	public void load(ArrayList<DuocSi> listDS) {
		int i =1;
		if (listDS == null) {
			ThongBao.baoLoi("Bảng không có dữ liệu hoặc dữ liệu không hợp lệ");
			return;
		}
		model.setRowCount(0);
		for (DuocSi ds: listDS) {
			Object[] data = new Object[] {i++, ds.getMa(), ds.getTen(),ds.getSoDT(),ds.getEmail() ,ds.getTaiKhoan().getUsername(),"" };
			data[6] = ds.getTrangThai() ? "Còn làm" : "Nghỉ làm";
			model.addRow(data);
		}
	}
	
	
	public class FormSuaDuocSi extends JDialog {
		private static final long serialVersionUID = 1L;
		private final JPanel contentPanel = new JPanel();
		private JTextField txtMa;
		private JTextField txtHoTen;
		private JTextField txtSDT;
		private JTextField txtEmail;
		private JLabel lblHinhAnh;
		private String  tenAnh;
		private String currentDirectory = System.getProperty("user.dir");
		private JFileChooser f = new JFileChooser(currentDirectory + "/img/HinhAvt");
		private File ftenanh = f.getSelectedFile();
		private String imageName = "";
		public void taiAnh(String anh) {
			String thuMucChuaAnh = getFolderPath() + "/img/HinhAvt/";
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
		public FormSuaDuocSi(String mads, String hoten, String sdt, String email,String anh,String trangthai) {
			setTitle("Sửa thông tin dược sĩ");
			setResizable(false);
			setIconImage(new ImageIcon("img/icon.png").getImage());
		    setModal(true);
		    setBounds(100, 100, 706, 400);
		    setLocationRelativeTo(null); 
		    getContentPane().setLayout(new BorderLayout());
		    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		    getContentPane().add(contentPanel, BorderLayout.CENTER);
		    contentPanel.setLayout(null);
		    
		    JLabel lblNewLabel = new JLabel("THÔNG TIN DƯỢC SĨ");
		    lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 25));
		    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		    lblNewLabel.setBounds(10, 10, 670, 30);
		    contentPanel.add(lblNewLabel);
		    
		    lblHinhAnh = new JLabel("");
		    lblHinhAnh.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		    lblHinhAnh.setBounds(10, 50, 230, 240);
		    contentPanel.add(lblHinhAnh);
		     
		    JLabel lblNewLabel_2 = new JLabel("Mã DS :");
		    lblNewLabel_2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		    lblNewLabel_2.setBounds(250, 50, 120, 30);
		    contentPanel.add(lblNewLabel_2);
		    
		    txtMa = new JTextField();
		    txtMa.setBounds(380, 50, 290, 30);
		    txtMa.setEnabled(false);
		    txtMa.setText(mads);
		    contentPanel.add(txtMa);
		    txtMa.setColumns(10);
		    
		    JLabel lblTen = new JLabel("Họ và Tên :");
		    lblTen.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		    lblTen.setBounds(250, 108, 120, 30);
		    contentPanel.add(lblTen);
		    
		    txtHoTen = new JTextField();
		    txtHoTen.setEnabled(false);
		    txtHoTen.setColumns(10);
		    txtHoTen.setBounds(380, 108, 290, 30);
		    txtHoTen.setText(hoten);
		    contentPanel.add(txtHoTen);
		    
		    JLabel lblNewLabel_2_1_1 = new JLabel("Số điện thoại :");
		    lblNewLabel_2_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		    lblNewLabel_2_1_1.setBounds(250, 168, 126, 30);
		    contentPanel.add(lblNewLabel_2_1_1);
		    
		    txtSDT = new JTextField();
		    txtSDT.setColumns(10);
		    txtSDT.setBounds(380, 168, 290, 30);
		    txtSDT.setText(sdt);
		    contentPanel.add(txtSDT);
		    
		    JLabel lblNewLabel_2_1_2 = new JLabel("Email :");
		    lblNewLabel_2_1_2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		    lblNewLabel_2_1_2.setBounds(250, 237, 120, 30);
		    contentPanel.add(lblNewLabel_2_1_2);
		    
		    txtEmail = new JTextField();
		    txtEmail.setColumns(10);
		    txtEmail.setBounds(380, 237, 290, 30);
		    txtEmail.setText(email);
		    contentPanel.add(txtEmail);
		    
		    JButton btnThemAnh = new JButton("Thêm ảnh");
		    btnThemAnh.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		themanh();
		    	}
		    });
		    btnThemAnh.setForeground(new Color(255, 255, 255));
		    btnThemAnh.setBackground(new Color(11, 101, 140));
		    btnThemAnh.setFont(new Font("Segoe UI", Font.BOLD, 18));
		    btnThemAnh.setBounds(55, 313, 150, 30);
		    btnThemAnh.setBorderPainted(false);
		    btnThemAnh.setFocusPainted(false);
		    btnThemAnh.setIcon(new ImageIcon(new ImageIcon("img/Icon/Picture.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		    contentPanel.add(btnThemAnh);
		    
		    JButton btnQuyen = new JButton("Quyền");
		    btnQuyen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setModal(false);
					PhanQuyenGUI pq = new PhanQuyenGUI(mads);
					pq.setQuyen(new TaiKhoanBUS().getQuyen(mads));
					pq.setVisible(true);
				}
			});
		    btnQuyen.setForeground(new Color(255, 255, 255));
		    btnQuyen.setBackground(new Color(11, 101, 140));
		    btnQuyen.setFont(new Font("Segoe UI", Font.BOLD, 18));
		    btnQuyen.setBounds(400, 313, 120, 30);
		    btnQuyen.setBorderPainted(false);
		    btnQuyen.setFocusPainted(false);
		    btnQuyen.setIcon(new ImageIcon(new ImageIcon("img/Icon/Medium Priority.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		    contentPanel.add(btnQuyen);
		    
		    JButton btnLock = new JButton("");
		    if (trangthai.equals("Còn làm")) {
		        btnLock.setText("Khóa");
		        btnLock.setIcon(new ImageIcon(new ImageIcon("img/Icon/Lock.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		    } else {
		        btnLock.setText("Mở khóa");
		        btnLock.setIcon(new ImageIcon(new ImageIcon("img/Icon/Unlock.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		    }
		    btnLock.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		if (trangthai.equals("Còn làm")) {
		    			if (ThongBao.cauHoi("Bạn có muốn khóa dược sĩ này?") == JOptionPane.NO_OPTION) return;
						boolean result = bus.lockDuocSi(mads);
						if (result) {
							ThongBao.thongBao("Khóa dược sĩ thành công");
							load(bus.getDuocSi());
							dispose();
						}
						else ThongBao.baoLoi("Khóa dược sĩ thất bại");
		    		}else {
		    			if (ThongBao.cauHoi("Bạn có muốn mở khóa dược sĩ này?") == JOptionPane.NO_OPTION) return;
						boolean result = bus.unlockDuocSi(mads);
						if (result) {
							ThongBao.thongBao("Mở khóa dược sĩ thành công");
							load(bus.getDuocSi());
							dispose();
						}
						else ThongBao.baoLoi("Mở khóa dược sĩ thất bại");
		    		}
		    	}
		    });
		    btnLock.setForeground(new Color(255, 255, 255));
		    btnLock.setBackground(new Color(11, 101, 140));
		    btnLock.setFont(new Font("Segoe UI", Font.BOLD, 18));
		    btnLock.setBounds(530, 313, 140, 30);
		    btnLock.setBorderPainted(false);
		    btnLock.setFocusPainted(false);
		    contentPanel.add(btnLock);
		    
		    JButton btnCapNhat = new JButton("Cập nhật");
		    btnCapNhat.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (ThongBao.cauHoi("Bạn có muốn cập nhật thông tin dược sĩ này?") == JOptionPane.NO_OPTION) return;
					if (txtHoTen.getText().equals("") || txtSDT.getText().equals("") || txtEmail.getText().equals("")) {
						ThongBao.baoLoi("Không được để trống thông tin!!");
						return;
					}
					if (!bus.isValidPhoneNumber(txtSDT.getText())) {
						ThongBao.baoLoi("Số điện thoại không hợp lệ!");
						return;
					}
					if (!bus.isValidEmail(txtEmail.getText())) {
						ThongBao.baoLoi("Email không hợp lệ!");
						return;
					}
					if (check_trungDT(txtSDT.getText(), txtMa.getText())) {
					    ThongBao.baoLoi("Số điện thoại đã tồn tại!");
					    return;
					}
					if (check_trungEmail(txtEmail.getText(), txtMa.getText())) {
					    ThongBao.baoLoi("Email đã tồn tại!");
					    return;
					}
					String anhAvtName = imageName;
					boolean result = bus.updateDuocSi(new DuocSi(
						txtMa.getText(), txtHoTen.getText(), txtSDT.getText(), txtEmail.getText()
					));
					boolean result_1=new AnhAvtBUS().UpdateAnhAvt(txtMa.getText(), anhAvtName);
					if (result == true && result_1 == true) {
						ThongBao.thongBao("Cập nhật thông tin dược sĩ thành công");
						load(bus.getDuocSi());
						dispose();
//						clear();
					}
					else ThongBao.baoLoi("Cập nhật thông tin dược sĩ thất bại");
				}
			});
		    btnCapNhat.setForeground(Color.WHITE);
		    btnCapNhat.setFont(new Font("Segoe UI", Font.BOLD, 18));
		    btnCapNhat.setFocusPainted(false);
		    btnCapNhat.setBorderPainted(false);
		    btnCapNhat.setBackground(new Color(11, 101, 140));
		    btnCapNhat.setBounds(250, 313, 140, 30);
		    btnCapNhat.setIcon(new ImageIcon(new ImageIcon("img/Icon/Synchronize.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		    contentPanel.add(btnCapNhat);
		    taiAnh(anh);
		    repainting();
		}
		
		void repainting() {
			Theme.setTheme(this);
			getRootPane().putClientProperty("JRootPane.titleBarBackground", Theme.DARK);
			getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.WHITE);
			getContentPane().setBackground(Theme.LIGHT);
		}
		public boolean check_trungDT(String sdt, String mads) {
		    for (DuocSi ds : bus.getDuocSi()) {
		        if (ds.getSoDT() != null && ds.getSoDT().equals(sdt) && !ds.getMa().equals(mads)) {
		            return true;
		        }
		    }
		    return false;
		}

		public boolean check_trungEmail(String email, String mads) {
		    for (DuocSi ds : bus.getDuocSi()) {
		        if (ds.getEmail() != null && ds.getEmail().equals(email) && !ds.getMa().equals(mads)) {
		            return true;
		        }
		    }
		    return false;
		}
	}
	public class FormThemDuocSi extends JDialog {
		private static final long serialVersionUID = 1L;
		private final JPanel contentPanel = new JPanel();
		private JTextField txtMa;
		private JTextField txtHoTen;
		private JTextField txtSDT;
		private JTextField txtEmail;
		private JLabel lblHinhAnh;
		private String  tenAnh;
		private String currentDirectory = System.getProperty("user.dir");
		private JFileChooser f = new JFileChooser(currentDirectory + "/img/HinhAvt");
		private File ftenanh = f.getSelectedFile();
		private String imageName = "";
		private DuocSiBUS bus = new DuocSiBUS();
		public void taiAnh(String anh) {
			String thuMucChuaAnh = getFolderPath() + "/img/HinhAvt/";
			String duongdananh = thuMucChuaAnh + anh;
			changeImage(duongdananh);
			imageName=anh;
		}
		TaiKhoanBUS taikhoanBus= new TaiKhoanBUS();
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
		public FormThemDuocSi(String mads) {
			setTitle("Thêm dược sĩ");
			setIconImage(new ImageIcon("img/icon.png").getImage());
		    setModal(true);
		    setBounds(100, 100, 640, 400);
		    setLocationRelativeTo(null);
		    setResizable(false);
		    getContentPane().setLayout(new BorderLayout());
		    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		    getContentPane().add(contentPanel, BorderLayout.CENTER);
		    contentPanel.setLayout(null);
		    
		    JLabel lblNewLabel = new JLabel("THÊM DƯỢC SĨ");
		    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		    lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 25));
		    lblNewLabel.setBounds(10, 10, 600, 30);
		    contentPanel.add(lblNewLabel);
		    
		    lblHinhAnh = new JLabel("");
		    lblHinhAnh.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		    lblHinhAnh.setBounds(10, 50, 230, 240);
		    contentPanel.add(lblHinhAnh);
		     
		    JLabel lblNewLabel_2 = new JLabel("Mã DS :");
		    lblNewLabel_2.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		    lblNewLabel_2.setBounds(250, 50, 120, 30);
		    contentPanel.add(lblNewLabel_2);
		    
		    txtMa = new JTextField();
		    txtMa.setBounds(380, 50, 230, 30);
		    txtMa.setEnabled(false);
		    txtMa.setText(mads);
		    contentPanel.add(txtMa);
		    txtMa.setColumns(10);
		    
		    JLabel lblTen = new JLabel("Họ và Tên :");
		    lblTen.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		    lblTen.setBounds(250, 108, 120, 30);
		    contentPanel.add(lblTen);
		    
		    txtHoTen = new JTextField();
		    txtHoTen.setColumns(10);
		    txtHoTen.setBounds(380, 108, 230, 30);
		    contentPanel.add(txtHoTen);
		    
		    JLabel lblNewLabel_2_1_1 = new JLabel("Số điện thoại :");
		    lblNewLabel_2_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		    lblNewLabel_2_1_1.setBounds(250, 168, 126, 30);
		    contentPanel.add(lblNewLabel_2_1_1);
		    
		    txtSDT = new JTextField();
		    txtSDT.setColumns(10);
		    txtSDT.setBounds(380, 168, 230, 30);
		    contentPanel.add(txtSDT);
		    
		    JLabel lblNewLabel_2_1_2 = new JLabel("Email :");
		    lblNewLabel_2_1_2.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		    lblNewLabel_2_1_2.setBounds(250, 237, 120, 30);
		    contentPanel.add(lblNewLabel_2_1_2);
		    
		    txtEmail = new JTextField();
		    txtEmail.setColumns(10);
		    txtEmail.setBounds(380, 237, 230, 30);
		    contentPanel.add(txtEmail);
		    
		    JButton btnThemAnh = new JButton("Thêm ảnh");
		    btnThemAnh.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		themanh();
		    	}
		    });
		    btnThemAnh.setForeground(new Color(255, 255, 255));
		    btnThemAnh.setBackground(new Color(11, 101, 140));
		    btnThemAnh.setFont(new Font("Segoe UI", Font.BOLD, 18));
		    btnThemAnh.setBounds(55, 313, 150, 30);
		    btnThemAnh.setFocusPainted(false);
		    btnThemAnh.setIcon(new ImageIcon(new ImageIcon("img/Icon/Picture.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		    contentPanel.add(btnThemAnh);
		    
		    JButton btnLuu = new JButton("Lưu");
		    btnLuu.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		if (ThongBao.cauHoi("Bạn có muốn thêm dược sĩ này?") == JOptionPane.NO_OPTION) return;
					if (txtHoTen.getText().equals("") || txtSDT.getText().equals("") || txtEmail.getText().equals("") || txtMa.getText().equals("")) {
						ThongBao.baoLoi("Không được để trống thông tin!!");
						return;
					}
					String anhAvtName = imageName;
					if (anhAvtName == "") {
						ThongBao.baoLoi("Vui lòng chọn ảnh của nhân viên!");
						return;
					}
					if (!txtHoTen.getText().matches("^[a-zA-ZÀ-ỹ\\s]+$")) {
					    ThongBao.baoLoi("Tên không được chứa số hoặc ký tự đặc biệt!");
					    return;
					}
					if (txtHoTen.getText().startsWith(" ") || txtHoTen.getText().endsWith(" ") || txtHoTen.getText().contains("  ")) {
						ThongBao.baoLoi("Tên không được bắt đầu, kết thúc hoặc chứa hai dấu cách liên tiếp!");
					    return;
					}
					if (!anhAvtName.endsWith(".png") && !anhAvtName.endsWith(".jpg") &&
						    !anhAvtName.endsWith(".jpeg") && !anhAvtName.endsWith(".gif")) {
						    ThongBao.baoLoi("Vui lòng chọn ảnh có định dạng hợp lệ (png, jpg, jpeg, gif)!");
						    return;
					}
					if (!bus.isValidPhoneNumber(txtSDT.getText())) {
						ThongBao.baoLoi("Số điện thoại không hợp lệ!");
						return;
					}
					if (!bus.isValidEmail(txtEmail.getText())) {
						ThongBao.baoLoi("Email không hợp lệ!");
						return;
					}
					if (bus.check_trungDT(txtSDT.getText())) {
						ThongBao.baoLoi("Số điện thoại đã tồn tại!");
						return;
					}
					if (bus.check_trungEmail(txtEmail.getText())) {
						ThongBao.baoLoi("Email đã tồn tại!");
						return;
					}
					boolean result = bus.addDuocSi(new DuocSi(
							txtMa.getText(), txtHoTen.getText(), txtSDT.getText(), txtEmail.getText()
					));
					if (result) {
						ThongBao.thongBao("Thêm dược sĩ thành công");
						taikhoanBus.createAccount(txtMa.getText());
						new AnhAvtBUS().addAnhAvt(txtMa.getText(), anhAvtName);
						taikhoanBus.updateQuyen(txtMa.getText(),4);
						new DuocSiGUI().load(bus.getDuocSi());
//						clear();
					}
					else ThongBao.baoLoi("Thêm dược sĩ thất bại");
					dispose();
		    	}
		    });
		    btnLuu.setForeground(new Color(255, 255, 255));
		    btnLuu.setBackground(new Color(11, 101, 140));
		    btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 18));
		    btnLuu.setBounds(372, 313, 114, 30);
		    btnLuu.setIcon(new ImageIcon(new ImageIcon("img/Icon/Save.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		    contentPanel.add(btnLuu);
		    
		    JButton btnHuy = new JButton("Hủy");
		    btnHuy.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		setVisible(false);
		    	}
		    });
		    btnHuy.setForeground(new Color(255, 255, 255));
		    btnHuy.setBackground(new Color(11, 101, 140));
		    btnHuy.setFont(new Font("Segoe UI", Font.BOLD, 18));
		    btnHuy.setBounds(496, 313, 114, 30);
		    btnHuy.setFocusPainted(false);
		    btnHuy.setIcon(new ImageIcon(new ImageIcon("img/Icon/Cancel.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		    contentPanel.add(btnHuy);
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