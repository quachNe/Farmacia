package GUI;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import BUS.AnhBUS;
import BUS.SanPhamBUS;
import DTO.RoundedBorder;
import DTO.SanPham;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TimSanPham extends JDialog {
	   private static final long serialVersionUID = 1L;
	   private JTextField txt_TimKiem;
	   private JTable tableSanPham;
	    private SanPham sanPhamDaChon;
	    static private JLabel lblAnh ;
	    static private JButton btnTimKiem;
	    private boolean isCanceled = false;
	   private SanPhamBUS spBUS = new SanPhamBUS();
	   static private DefaultTableModel model = new DefaultTableModel(
				new Object[][] {},
				new String[] {"Mã Sản Phẩm ","Tên Sản Phẩm "}
				);
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
	public TimSanPham() {
		setTitle("Tìm Kiếm Sản Phẩm ");
        setIconImage(new ImageIcon("img/icon.png").getImage());
        setBounds(100, 100, 700, 546);
        setResizable(false);
        setModalityType(DEFAULT_MODALITY_TYPE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().setBackground(Theme.LIGHT);
        JPanel panel = new JPanel();
        panel.setBounds(0, 10, 976, 50);
        getContentPane().add(panel);
        panel.setLayout(null);
        panel.setBackground(Theme.LIGHT);
        
        txt_TimKiem = new JTextField();
        RoundedBorder rb= new RoundedBorder(1);
        rb.setPlaceholder(txt_TimKiem," Tìm Kiếm... ");
        txt_TimKiem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) { // Kiểm tra nếu phím là Enter
                    btnTimKiem.doClick(); // Giả lập nhấn nút Tìm Kiếm
                }
            }
        });
        txt_TimKiem.setBounds(10, 14, 594, 30);
        panel.add(txt_TimKiem);
        txt_TimKiem.setColumns(10);
        
       btnTimKiem = new JButton("");
//       btnTimKiem.setBackground(new Color(11, 101, 140));
       btnTimKiem.setIcon(new ImageIcon(new ImageIcon("img/Icon/Search.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
       btnTimKiem.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			SanPhamBUS sanphambus = new SanPhamBUS();
			String chuoi = txt_TimKiem.getText();
		    ArrayList<SanPham> arrSP = sanphambus.find(chuoi);
		    if (txt_TimKiem.getText().equals(" Tìm Kiếm...")) {
				ThongBao.baoLoi("Vui lòng nhập nội dung tìm kiếm");
				return;
			}
		    if (arrSP == null || arrSP.isEmpty()) {
		        ThongBao.baoLoi("Bảng không tồn tại dữ liệu");
		        return;
		    }
		    DefaultTableModel model = (DefaultTableModel) tableSanPham.getModel();
		    while (model.getRowCount() != 0) {
				model.removeRow(0);
			}
			for (SanPham sp : arrSP) {
				model.addRow(new Object[] {
						sp.getMa(),
			            sp.getTen(),
			            sp.getLoai(),
			            sp.getNhaSanXuat(),
			            sp.getQuyCach(),
			            sp.getXuatXu(),
			            (sp.isCanKeToa() ? "Có": "Không"),
			            (sp.isTrangThai() ? "Còn kinh doanh" : "Ngừng kinh doanh")
				});
			}
		}
	});
        btnTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnTimKiem.setBounds(614, 14, 73, 30);
//        btnTimKiem.setForeground(new Color(255, 255, 255));
        panel.add(btnTimKiem);
        
        JPanel panel_TableSanPham = new JPanel();
        panel_TableSanPham.setBounds(10, 70, 592, 483);
        getContentPane().add(panel_TableSanPham);
        panel_TableSanPham.setLayout(null);
        panel_TableSanPham.setBackground(Theme.LIGHT);
     // Tạo bảng và cài đặt các thuộc tính
        tableSanPham = new JTable();
        tableSanPham.getTableHeader().setBackground(Color.decode("#B9B9B9"));
        tableSanPham.setModel(model);
        tableSanPham.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JTableHeader header = tableSanPham.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        tableSanPham.setRowHeight(25); // Đặt chiều cao hàng
        tableSanPham.getColumnModel().getColumn(0).setPreferredWidth(100);
        tableSanPham.getColumnModel().getColumn(1).setPreferredWidth(450);

        // Đặt JTable vào JScrollPane để hiển thị header
        JScrollPane scrollPane = new JScrollPane(tableSanPham);
        scrollPane.setBounds(0, 0, 592, 483); // Cài đặt kích thước cho JScrollPane
        panel_TableSanPham.add(scrollPane); // Thêm JScrollPane vào panel

        // Lắng nghe sự kiện click chuột trên bảng
        tableSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = tableSanPham.getSelectedRow();
                if (selectedRow != -1) {
                    String maSP = (String) tableSanPham.getValueAt(selectedRow, 0); 
                    AnhBUS anhBus = new AnhBUS();
                    String anh = anhBus.getAnhByMaThuoc(maSP);
                    taiAnh(anh);
                }
            }
        });
        setColorCell(tableSanPham);
        loadDataTable();
        panel_TableSanPham.add( scrollPane);
        setColorCell(tableSanPham);
        JPanel panel_Anh = new JPanel();
        
        panel_Anh.setBounds(612, 70, 364, 402);
        getContentPane().add(panel_Anh);
        panel_Anh.setLayout(null);
        panel_Anh.setBackground(Theme.LIGHT);
        
        lblAnh = new JLabel("\r\n");
        lblAnh.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        lblAnh.setBounds(0, 0, 364, 401);
        panel_Anh.add(lblAnh);
        
        JPanel panel_nut = new JPanel();
        panel_nut.setBounds(612, 471, 364, 70);
        getContentPane().add(panel_nut);
        panel_nut.setLayout(null);
        panel_nut.setBackground(Theme.LIGHT);
        
        JButton btnChon = new JButton("Chọn\r\n");
        btnChon.setForeground(new Color(255, 255, 255));
        btnChon.setIcon(new ImageIcon(new ImageIcon("img/Icon/Save.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
        btnChon.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		chonSanPham();
        		}
        });
        btnChon.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnChon.setBounds(106, 28, 121, 36);
        btnChon.setBackground(new Color(11, 101, 140));
        panel_nut.add(btnChon);
        
        JButton btnHuy = new JButton("Hủy\r\n");
        btnHuy.setForeground(new Color(255, 255, 255));
        btnHuy.setBackground(new Color(11, 101, 140));
        btnHuy.setIcon(new ImageIcon(new ImageIcon("img/Icon/Cancel.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
        btnHuy.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		  isCanceled = true;
        		dispose();
        	}
        });
        btnHuy.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnHuy.setBounds(237, 28, 121, 36);
        panel_nut.add(btnHuy);
        repainting();
       
		}
	  private void loadDataTable() {
	        ArrayList<SanPham> arrSP = spBUS.getAllSp();
	        model.setRowCount(0);
	        for (SanPham sp : arrSP) {
	           
	            model.addRow(new Object[]{sp.getMa(), sp.getTen()});
	        }
	    }

	    private void loadDataFind() {
	        String chuoi = txt_TimKiem.getText();
	        ArrayList<SanPham> arrSP = spBUS.find(chuoi);
	        model.setRowCount(0);

	        for (SanPham sp : arrSP) {
	            String thuMucChuaAnh = getFolderPath() + "/img/HinhSanPham/";
	            
	            ImageIcon imageIcon = ResizeImage(thuMucChuaAnh);
	            if (imageIcon == null) {
	                imageIcon = new ImageIcon(getFolderPath() + "/img/default.png");
	            }
	            model.addRow(new Object[]{sp.getMa(), sp.getTen(), imageIcon});
	        }
	    }

	    private void chonSanPham() {
	        if (tableSanPham.getSelectedRow() != -1) {
	            String maSP = (String) tableSanPham.getValueAt(tableSanPham.getSelectedRow(), 0);
	            String tenSP = (String) tableSanPham.getValueAt(tableSanPham.getSelectedRow(), 1);
	            sanPhamDaChon = new SanPham(maSP, tenSP);
	            // Đặt isCanceled thành false khi chọn sản phẩm
	            isCanceled = false;
	            dispose(); // Đóng cửa sổ sau khi chọn sản phẩm
	        } else {
	            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm!");
	        }
	    }
	    
	    

	    public static void taiAnh(String anh) {
	        String thuMucChuaAnh = getFolderPath() + "/img/HinhSanPham/";
	        String duongdananh = thuMucChuaAnh + anh;
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
	            lblAnh.setIcon(resizedImage);
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
	            Image newImg = img.getScaledInstance(lblAnh.getWidth(), lblAnh.getHeight(), Image.SCALE_SMOOTH);
	            resizedImage = new ImageIcon(newImg);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        return resizedImage;
	    }
	    
	    public void addProductSelectionListener(JComboBox<SanPham> cbbSanPham) {
	    	this.addWindowListener(new WindowAdapter() {
	    		@Override
	    		public void windowClosed(WindowEvent windowEvent) {
	    			SanPham sanPhamDaChon = getSanPhamDaChon();
	    			if (sanPhamDaChon != null) {
	    				boolean isProductAlreadyInComboBox = false;
	    				for (int i = 0; i < cbbSanPham.getItemCount(); i++) {
	    					if (cbbSanPham.getItemAt(i).getMa().equals(sanPhamDaChon.getMa())) {
	    						isProductAlreadyInComboBox = true;
	    						break;
	    					}
	    				}
	    				if (!isProductAlreadyInComboBox) {
	    					cbbSanPham.addItem(sanPhamDaChon);
	    				}
	    				cbbSanPham.setSelectedItem(sanPhamDaChon);
	    			}
	    		}
	    	});
	    	
	    	cbbSanPham.addActionListener(actionEvent -> {
	    		SanPham selectedProduct = (SanPham) cbbSanPham.getSelectedItem();
	            if (selectedProduct != null) {
	            	System.out.println("Sản phẩm đã chọn: " + selectedProduct.getTen());
	            }
	        });
	    }
	    
	    public void adddProductSelectionListener(JComboBox<SanPham> cbbMaSP) {
	        this.addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosed(WindowEvent e) {
	                SanPham sanPhamDaChon = getSanPhamDaChon(); 
	                if (sanPhamDaChon != null) {
	                    boolean isProductAlreadyInComboBox = false;
	                
	                    for (int i = 0; i < cbbMaSP.getItemCount(); i++) {
	                        if (((SanPham) cbbMaSP.getItemAt(i)).getMa().equals(sanPhamDaChon.getMa())) {
	                            isProductAlreadyInComboBox = true;
	                            break;
	                        }
	                    }
	                
	                    if (!isProductAlreadyInComboBox) {
	                        cbbMaSP.addItem(sanPhamDaChon); 
	                    }
	                 
	                    cbbMaSP.setSelectedItem(sanPhamDaChon);
	                }
	                
	            }
	        });
	    }
	    public SanPham getSanPhamDaChon() {
	        if (isCanceled) {
	            return null;
	        }
	        int selectedRow = tableSanPham.getSelectedRow();
	        if (selectedRow != -1) {
	            String maSP = (String) tableSanPham.getValueAt(selectedRow, 0);
	            return spBUS.getAllSp().stream()
	                    .filter(sp -> sp.getMa().equals(maSP))
	                    .findFirst()
	                    .orElse(null);
	        }
	        return null;
	    }
	    void repainting() {
	    	Theme.setTheme(this);
	    	getRootPane().putClientProperty("JRootPane.titleBarBackground", Theme.DARK);
	    	getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.WHITE);
	    	
	    }
}