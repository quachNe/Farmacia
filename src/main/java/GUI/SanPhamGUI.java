package GUI;
import java.awt.Image;
import java.awt.Dimension;
import javax.swing.*;  
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import BUS.DuocSiBUS;
import BUS.SanPhamBUS;
import DTO.RoundedBorder;
import DTO.SanPham;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SanPhamGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private DefaultTableModel model = new DefaultTableModel(
			new Object[][] {},
			new String[] {"Mã SP","Tên SP", "Loại SP", "Nhà SX", "Quy cách" ,"Xuất xứ", "Kê toa", "Trạng thái"}
			);
	DuocSiBUS bus = new DuocSiBUS();
	private static SanPhamBUS sanphamBUS = new SanPhamBUS();
	private JTextField txtTimKiem;
	private JComboBox<String> cbbTrangthai;
	private JComboBox<String> cbbKeToa;
	private static JTable table;
	private SanPhamBUS sanphambus = new SanPhamBUS();
	public static Map<String, Boolean> sanPhamBiKhoa = new HashMap<>();
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
	public void updateTable(ArrayList<SanPham> filteredNCC) {
	    model.setRowCount(0);
	    for (SanPham sp : filteredNCC) {
	        String trangThaiKeToa = sp.isCanKeToa() ? "Có" : "Không";
	        String trangThaiSanPham = sp.isTrangThai() ? "Còn kinh doanh" : "Ngừng kinh doanh";
	        model.addRow(new Object[]{
	            sp.getMa(),
	            sp.getTen(),
	            sp.getLoai(),
	            sp.getNhaSanXuat(),
	            sp.getQuyCach(),
	            sp.getXuatXu(),
	            trangThaiKeToa,
	            trangThaiSanPham
	        });
	    }
	}

	public SanPhamGUI() {
		UIManager.put("Button.arc", 50);
		setMinimumSize(new Dimension(50, 100));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setPreferredSize(new Dimension(1280, 728));
		setLayout(null);
			
		txtTimKiem = new JTextField();
		txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		RoundedBorder.setPlaceholder(txtTimKiem, " Tìm Kiếm...");
		txtTimKiem.setBorder(new RoundedBorder(50));
		txtTimKiem.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				if (txtTimKiem.getText().isEmpty()) {
					loadDataSp();
				}
			}
		});
		txtTimKiem.setBounds(10, 20, 517, 31);
		add(txtTimKiem);
		txtTimKiem.setColumns(10);
		
		JButton btnTimKiem = new JButton("");
		btnTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		btnTimKiem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String chuoi = txtTimKiem.getText();
			    ArrayList<SanPham> arrSP = sanphambus.find(chuoi);
			    if (txtTimKiem.getText().equals(" Tìm Kiếm...")) {
					ThongBao.baoLoi("Vui lòng nhập nội dung tìm kiếm");
					return;
				}
			    if (arrSP == null || arrSP.isEmpty()) {
			    	
			        ThongBao.baoLoi("Bảng không tồn tại dữ liệu");
			        txtTimKiem.setText("");
			        txtTimKiem.requestFocusInWindow();
			        return;
			    }
			    DefaultTableModel model = (DefaultTableModel) table.getModel();
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
		txtTimKiem.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		            btnTimKiem.doClick(); // Gọi sự kiện click cho nút btnTimKiem
		        }
		    }
		});
		btnTimKiem.setBounds(537, 20, 35, 31);
		btnTimKiem.setIcon(new ImageIcon(new ImageIcon("img/Icon/Search.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		add(btnTimKiem);
		
		
		JButton btnThem = new JButton("Thêm");
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ThemSanPhamGUI().setVisible(true);
			}
		});	
		btnThem.setBounds(1090, 20, 110, 31);
		btnThem.setForeground(new Color(255, 255, 255));
		btnThem.setBackground(new Color(11, 101, 140));
		btnThem.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnThem.setBorderPainted(false);
		btnThem.setFocusPainted(false);
		btnThem.setIcon(new ImageIcon(new ImageIcon("img/Icon/Add.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		add(btnThem);
	
		cbbKeToa = new JComboBox<>();
		cbbKeToa.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		cbbKeToa.setModel(new DefaultComboBoxModel<>(new String[] {"Tất cả", "Có", "Không"}));
		   cbbKeToa.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		            timKiemTheoDieuKien();
		        }
		    });
		cbbKeToa.setBounds(120, 61, 105, 30);
		cbbKeToa.setBorder(new RoundedBorder(50));
		add(cbbKeToa);

		cbbTrangthai = new JComboBox<String>();
		cbbTrangthai.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		cbbTrangthai.setModel(new DefaultComboBoxModel<>(new String[] {"Tất cả", "Còn kinh doanh", "Ngừng kinh doanh"}));
		 cbbTrangthai.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		            timKiemTheoDieuKien();
		        }
		    });	
		cbbTrangthai.setBounds(332, 61, 195, 30);
		cbbTrangthai.setBorder(new RoundedBorder(50));
		add(cbbTrangthai);
		
		JLabel lblNewLabel_1 = new JLabel("Cần kê toa:");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(10, 61, 100, 30);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Trạng thái:");
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblNewLabel_1_1.setBounds(235, 61, 87, 30);
		add(lblNewLabel_1_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 113, 1260, 710);
		add(scrollPane);
		
		table = new JTable(model);
		table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		setColorCell(table);
		table.getTableHeader().setBackground((Color.decode("#B9B9B9")));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    handleUp();
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(510);
		table.getColumnModel().getColumn(2).setPreferredWidth(10);
		table.getColumnModel().getColumn(3).setPreferredWidth(10);
		table.getColumnModel().getColumn(4).setPreferredWidth(50);
		table.getColumnModel().getColumn(5).setPreferredWidth(30);
		table.getColumnModel().getColumn(6).setPreferredWidth(30);
		table.getColumnModel().getColumn(7).setPreferredWidth(30);
		JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.ITALIC, 18));
		scrollPane.setViewportView(table);
		
		JButton btnLamMoi = new JButton("");
		btnLamMoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadDataSp();
				txtTimKiem.setText("");
				cbbKeToa.setSelectedIndex(0);
				cbbTrangthai.setSelectedIndex(0);
				RoundedBorder.setPlaceholder(txtTimKiem," Tìm Kiếm...");
			}
		});
		btnLamMoi.setForeground(new Color(255, 255, 255));
		btnLamMoi.setBackground(new Color(11, 101, 140));
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 12));
	
		btnLamMoi.setFocusPainted(false);
		btnLamMoi.setBounds(1220, 20, 50, 31);
		btnLamMoi.setIcon(new ImageIcon(new ImageIcon("img/Icon/Reset.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		add(btnLamMoi);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 101, 1260, 2);
		add(separator);
		
		loadDataSp();
		repainting();
	}
	void repainting() {
		setBackground(Theme.LIGHT);
	}
	private void timKiemTheoDieuKien() {
	    String selectedKeToa = (String) cbbKeToa.getSelectedItem();
	    String selectedTrangThai = (String) cbbTrangthai.getSelectedItem();

	    ArrayList<SanPham> arrSP = sanphambus.getAllSp();
	    ArrayList<SanPham> filteredNCC = new ArrayList<>();

	    for (SanPham sp : arrSP) {
	        boolean matchKeToa = true;
	        boolean matchTrangThai = true;

	        // Điều kiện lọc theo Ke Toa
	        if (selectedKeToa.equals("Có")) {
	            matchKeToa = sp.isCanKeToa();
	        } else if (selectedKeToa.equals("Không")) {
	            matchKeToa = !sp.isCanKeToa();
	        }

	        // Điều kiện lọc theo Trạng Thái
	        if (selectedTrangThai.equals("Còn kinh doanh")) {
	            matchTrangThai = sp.isTrangThai();
	        } else if (selectedTrangThai.equals("Ngừng kinh doanh")) {
	            matchTrangThai = !sp.isTrangThai();
	        }

	        // Thêm vào danh sách nếu thỏa cả hai điều kiện
	        if (matchKeToa && matchTrangThai) {
	            filteredNCC.add(sp);
	        }
	    }

	    updateTable(filteredNCC);
	}
	public void loadDataCBB(int check,int data) {
		ArrayList<SanPham> arrSP = null;
		if (check == 1) {
			arrSP = sanphambus.findCanKeToa(data);
		}else if(check == 2) {
			arrSP = sanphambus.findTrangThai(data);
		}
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
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
	public void handleCbbKeToa() {
		String data = (String) cbbKeToa.getSelectedItem();
        if (data != null) {
        	if (data.equals("Có")) {
    			loadDataCBB(1, 1);
    		}else if (data.equals("Không")) {
    			loadDataCBB(1, 0);
    		}else {
    			loadDataSp();
    		}
		}
	}
	
	public void handleCbbTrangThai() {
		String data = (String) cbbTrangthai.getSelectedItem();
        if (data != null) {
        	if (data.equals("Còn kinh doanh")) {
    			loadDataCBB(2, 1);
    		}else if (data.equals("Ngừng kinh doanh")) {
    
				loadDataCBB(2, 0);
    		}else {
    			loadDataSp();
    		}
		}
	}
	
	public static void loadDataSp() {
	    ArrayList<SanPham> arrSP = sanphamBUS.getAllSp();
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
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
	public void handleUp() {
		int row = table.getSelectedRow();
		if (row != -1) {
			String masp = (String) table.getValueAt(row, 0);
			String tensp = (String) table.getValueAt(row, 1); 
			String loaisp = (String) table.getValueAt(row, 2);
			String nhasx = (String) table.getValueAt(row, 3);
			String quycach = (String) table.getValueAt(row, 4);
			String xuatxu = (String) table.getValueAt(row, 5);
			boolean canketoa = ((String) table.getValueAt(row, 6)).contains("C");
			boolean trangthai = ((String) table.getValueAt(row, 7)).equals("Còn kinh doanh");
			
			SanPham sp = new SanPham(masp, tensp, loaisp, nhasx, quycach, xuatxu, canketoa, trangthai);
			SuaSanPham suasp = new SuaSanPham(masp, trangthai);
			suasp.setData(sp,true);
			suasp.setVisible(true);
			suasp.setLocationRelativeTo(null);
		} else {
	        ThongBao.baoLoi("Vui lòng chọn một sản phẩm để sửa");
	    }
	}
}