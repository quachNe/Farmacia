package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import DTO.LoaiSanPham;
import DTO.NhaSanXuat;
import DTO.RoundedBorder;
import BUS.DuocSiBUS;
import BUS.LoaiSanPhamBUS;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoaiSanPhamGUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private static JTable tableLoaiSP;
	private static LoaiSanPhamBUS lspBUS = new LoaiSanPhamBUS();
	private static JComboBox cbb_trangthai;
	private DefaultTableModel model = new DefaultTableModel(
			new Object[][] {},
			new String[] {"Mã loại","Tên loại","Trạng thái"}
			);
	DuocSiBUS bus = new DuocSiBUS();
	private  static JTextField txtTimKiem;
	private JComboBox<String> cbbTrangthai;
	private static JTable table;
	private JButton btnLock;
	private LoaiSanPhamBUS loaisanphambus = new LoaiSanPhamBUS();
	public static Map<String, Boolean> loaisanPhamBiKhoa = new HashMap<>();
	
	public static void loadDataLSP() {
		LoaiSanPhamBUS lspBUS = new LoaiSanPhamBUS();
	    ArrayList<LoaiSanPham> arrLSP = lspBUS.getAllLSP();

	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    while (model.getRowCount() != 0) {
			model.removeRow(0);
		}

		for (LoaiSanPham lsp : arrLSP) {
			model.addRow(new Object[] {
					lsp.getMa(),
		            lsp.getTen(),
		            (lsp.isTrangThai() ? "Còn kinh doanh" : "Ngừng kinh doanh")
		        });
		}
	}
	
	public static void searchDataLSP(String searchTxt) {
		LoaiSanPhamBUS lspBUS = new LoaiSanPhamBUS();
	    ArrayList<LoaiSanPham> arrLSP = lspBUS.searchLSP(searchTxt);
	    
	    if(arrLSP == null) {	 
	    	ThongBao.baoLoi(" Thông báo không có dữ liệu cần tìm Vui lòng nhập lại !");
	    	txtTimKiem.requestFocusInWindow();

	    }
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    while (model.getRowCount() != 0) {
			model.removeRow(0);
		}
	    
		for (LoaiSanPham lsp : arrLSP) {
			model.addRow(new Object[] {
					lsp.getMa(),
		            lsp.getTen(),
		            (lsp.isTrangThai() ? "Còn kinh doanh" : "Ngừng kinh doanh")
		        });
		}
		
	}
	
	public static void handleCbbTrangThai() {
		String data = (String) cbb_trangthai.getSelectedItem();
        if (data != null) {
        	if (data.equals("Còn kinh doanh")) {
    			loadDataCBB(1);
    		}else if (data.equals("Ngừng kinh doanh")) {
    			loadDataCBB(0);
    		}else {
    			loadDataLSP();
    		}
		}
	}
	
	public static void loadDataCBB(int data) {
		ArrayList<LoaiSanPham> arrLSP = null;
		
		arrLSP = lspBUS.findTrangThai(data);

	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    while (model.getRowCount() != 0) {
			model.removeRow(0);
		}

		for (LoaiSanPham lsp : arrLSP) {
			model.addRow(new Object[] {
					lsp.getMa(),
		            lsp.getTen(),
		            (lsp.isTrangThai() ? "Còn kinh doanh" : "Ngừng kinh doanh")
		        });
		}
	}
	
	public static void handleAdd() {
		LoaiSanPhamADD addsp = new LoaiSanPhamADD("Thêm loại loại sản phẩm");
		addsp.setVisible(true);
		addsp.setLocationRelativeTo(null);
	}
	
	public void updateTable(ArrayList<LoaiSanPham> filteredNSX) {
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    model.setRowCount(0);
	    for (LoaiSanPham lsp : filteredNSX) {
	        String trangThai = lsp.isTrangThai() ? "Còn kinh doanh" : "Ngừng kinh doanh";
	        model.addRow(new Object[]{
	        		lsp.getMa(),
	        		lsp.getTen(),
	        		trangThai
	        });
	    }
	}
	public static void handleUp() {
		int row = table.getSelectedRow();
		if (row != -1) {
			String maLoai = (String) table.getValueAt(row, 0);
			String tenLoai = (String) table.getValueAt(row, 1); 
			Object trangthai = table.getValueAt(row, 2);
			boolean trangThai;
			if (trangthai instanceof String) {
			
			    trangThai = Boolean.parseBoolean((String) trangthai);
			} else if (trangthai instanceof Boolean) {
			    trangThai = (Boolean) trangthai;
			} else {
			    trangThai = false;
			}
			LoaiSanPham lsp = new LoaiSanPham(maLoai, tenLoai, trangThai);
			LoaiSanPhamADD addlsp = new LoaiSanPhamADD("Sửa thông tin loại sản phẩm");
			addlsp.setData(lsp,true);
			addlsp.setVisible(true);
			addlsp.setLocationRelativeTo(null);
		} else {
	        JOptionPane.showMessageDialog(null, "Vui lòng chọn một loại sản phẩm để sửa.");
	    }
	}
	
	public static void handleDelete() {
		int row = tableLoaiSP.getSelectedRow();
		if (row != -1) {
			String malsp = (String) tableLoaiSP.getValueAt(row, 0);
			boolean trangThai = (boolean) tableLoaiSP.getValueAt(row, 2).toString().equals("Còn kinh doanh");
			int reponse = ThongBao.cauHoi("Bạn có chắc muốn " + (trangThai ? "khóa" : "mở khóa") + " Loại Sản Phẩm có Mã Loại = " + malsp + "?");
			if (reponse == JOptionPane.YES_OPTION) {
				if (lspBUS.changeStatusLoaiSanPham(malsp, !trangThai)) {
					ThongBao.thongBao("Thay đổi trạng thái thành công");
					loadDataLSP();
				} else {
					ThongBao.baoLoi("Thay đổi trạng thái thất bại. Vui lòng kiểm tra lại");
				}
			}
		} else {
	        JOptionPane.showMessageDialog(null, "Vui lòng chọn một loại sản phẩm.");
	    }
	}
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
	public LoaiSanPhamGUI() {
		btnLock = new JButton("Khóa");

		setMinimumSize(new Dimension(50, 100));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setPreferredSize(new Dimension(1280, 728));
		setLayout(null);
			
		txtTimKiem = new JTextField();
		txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtTimKiem.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				if (txtTimKiem.getText().isEmpty()) {
					loadDataLSP();
				}
			}
		});
		txtTimKiem.setBounds(10, 21, 382, 32);
		add(txtTimKiem);
		RoundedBorder.setPlaceholder(txtTimKiem," Tìm Kiếm...");
		txtTimKiem.setBorder(new RoundedBorder(50));
		txtTimKiem.setColumns(10);
		
		JButton btnTimKiem = new JButton("");
		btnTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		btnTimKiem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtTimKiem.getText().equals(" Tìm Kiếm...")) {
					ThongBao.baoLoi("Vui lòng nhập nội dung tìm kiếm");
					return;
				}
				String chuoi = txtTimKiem.getText();
			    ArrayList<LoaiSanPham> arrSP = loaisanphambus.searchLSP(chuoi);
			    if (arrSP == null || arrSP.isEmpty()) {
			        ThongBao.baoLoi("Bảng không tồn tại dữ liệu");
			        txtTimKiem.setText("");
			        txtTimKiem.requestFocus();
			        return;
			    }
			    DefaultTableModel model = (DefaultTableModel) table.getModel();
			    while (model.getRowCount() != 0) {
					model.removeRow(0);
				}
				for (LoaiSanPham sp : arrSP) {
					model.addRow(new Object[] {
							sp.getMa(),
				            sp.getTen(),
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
		btnTimKiem.setBounds(402, 21, 35, 32);
		btnTimKiem.setIcon(new ImageIcon(new ImageIcon("img/Icon/Search.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		add(btnTimKiem);
		
		
		JButton btnThem = new JButton("Thêm");
		btnThem.setHorizontalAlignment(SwingConstants.LEFT);
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LoaiSanPhamADD("Thêm loại sản phẩm").setVisible(true);
			}
		});	
		btnThem.setBounds(790, 20, 120, 32);
		btnThem.setForeground(new Color(255, 255, 255));
		btnThem.setBackground(new Color(11, 101, 140));
		btnThem.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnThem.setBorderPainted(false);
		btnThem.setFocusPainted(false);
		btnThem.setIcon(new ImageIcon(new ImageIcon("img/Icon/Add.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		add(btnThem);

		cbbTrangthai = new JComboBox<String>();
		cbbTrangthai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedStatus = (String) cbbTrangthai.getSelectedItem();
		        ArrayList<LoaiSanPham> arrLSP = loaisanphambus.getAllLSP();
		        ArrayList<LoaiSanPham> filteredNSX = new ArrayList<>();

		        if (selectedStatus.equals("Còn kinh doanh")) {
		            for (LoaiSanPham lsp : arrLSP) {
		                if (lsp.isTrangThai()) {
		                    filteredNSX.add(lsp);
		                }
		            }
		        } else if (selectedStatus.equals("Ngừng kinh doanh")) {
		            for (LoaiSanPham lsp : arrLSP) {
		                if (!lsp.isTrangThai()) {
		                    filteredNSX.add(lsp);
		                }
		            }
		        } else {
		            filteredNSX.addAll(arrLSP);
		        }
		        updateTable(filteredNSX);
			}
		});
		cbbTrangthai.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		cbbTrangthai.setModel(new DefaultComboBoxModel<>(new String[] {"Tất cả", "Còn kinh doanh", "Ngừng kinh doanh"}));
		cbbTrangthai.setBounds(100, 64, 195, 32);
		cbbTrangthai.setBorder(new RoundedBorder(50));
		add(cbbTrangthai);
		
		JLabel lblNewLabel_1_1 = new JLabel("Trạng thái:");
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblNewLabel_1_1.setBounds(10, 64, 100, 32);
		add(lblNewLabel_1_1);
		
		JButton btnCapNhat = new JButton("Cập nhật");
		btnCapNhat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleUp();
			}
		});
		btnCapNhat.setIcon(new ImageIcon(new ImageIcon("img/Icon/Synchronize.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		btnCapNhat.setHorizontalAlignment(SwingConstants.LEFT);
		btnCapNhat.setForeground(Color.WHITE);
		btnCapNhat.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnCapNhat.setFocusPainted(false);
		btnCapNhat.setBorderPainted(false);
		btnCapNhat.setBackground(new Color(11, 101, 140));
		btnCapNhat.setBounds(920, 20, 140, 32);
		add(btnCapNhat);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 116, 1260, 710);
		add(scrollPane);
		
		table = new JTable(model);
		table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		table.getTableHeader().setBackground((Color.decode("#B9B9B9")));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    ImageIcon iconLock = new ImageIcon(new ImageIcon("img/Icon/Lock.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
			    ImageIcon iconUnlock = new ImageIcon(new ImageIcon("img/Icon/Unlock.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
			    String status = table.getModel().getValueAt(table.getSelectedRow(), 2).toString();
			    if (status.equals("Còn kinh doanh")) {
			        btnLock.setText("Khóa");
			        btnLock.setIcon(iconLock);
			    } else {
			        btnLock.setText("Mở khóa");
			        btnLock.setIcon(iconUnlock);
			    }
			}
		});
		scrollPane.setViewportView(table);
		JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        table.getColumnModel().getColumn(0).setPreferredWidth(10);
        table.getColumnModel().getColumn(1).setPreferredWidth(600);
        table.getColumnModel().getColumn(2).setPreferredWidth(40);
		setColorCell(table);
		
		btnLock.setHorizontalAlignment(SwingConstants.LEFT);
		btnLock.setIcon(new ImageIcon(new ImageIcon("img/Icon/Lock.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		btnLock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
			    if (row != -1) {
			        String malsp = (String) table.getValueAt(row, 0);
			        boolean trangthai = ((String) table.getValueAt(row, 2)).equals("Còn kinh doanh");

			        int reponse = ThongBao.cauHoi("Bạn có chắc muốn " + (trangthai ? "khóa" : "mở khóa") + " Loại sản phẩm có Mã LSP = " + malsp + "?");

			        if (reponse == JOptionPane.YES_OPTION) {
			            if (loaisanPhamBiKhoa.containsKey(malsp) && loaisanPhamBiKhoa.get(malsp)) {
			                if (loaisanphambus.changeStatusLoaiSanPham(malsp, true)) {
			                    ThongBao.thongBao("Mở khóa thành công cho sản phẩm Mã SP: " + malsp);
			                    loaisanPhamBiKhoa.put(malsp, false);
			                } else {
			                    ThongBao.baoLoi("Mở khóa thất bại cho sản phẩm Mã SP: " + malsp);
			                }
			            } else {
			                if (loaisanphambus.changeStatusLoaiSanPham(malsp, false)) {
			                    ThongBao.thongBao("Khóa thành công cho sản phẩm Mã SP: " + malsp);
			                    loaisanPhamBiKhoa.put(malsp, true);
			                } else {
			                    ThongBao.baoLoi("Khóa thất bại cho sản phẩm Mã SP: " + malsp);
			                }
			            }
			            loadDataLSP();
			        }
			    } else {
			        ThongBao.baoLoi("Vui lòng chọn một sản phẩm");
			    }
			}
		});
		btnLock.setForeground(new Color(255, 255, 255));
		btnLock.setBackground(new Color(11, 101, 140));
		btnLock.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnLock.setBorderPainted(false);
		btnLock.setFocusPainted(false);
		btnLock.setBounds(1070, 21, 140, 32);
		add(btnLock);
		
		JButton btnLamMoi = new JButton("");
		btnLamMoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadDataLSP();
				txtTimKiem.setText("");
				cbbTrangthai.setSelectedIndex(0);
				btnLock.setText("Khóa");
				btnLock.setIcon(new ImageIcon(new ImageIcon("img/Icon/Lock.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
				RoundedBorder.setPlaceholder(txtTimKiem," Tìm Kiếm...");
			}
		});
		btnLamMoi.setForeground(new Color(255, 255, 255));
		btnLamMoi.setBackground(new Color(11, 101, 140));
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnLamMoi.setBorderPainted(false);
		btnLamMoi.setFocusPainted(false);
		btnLamMoi.setBounds(1220, 21, 50, 32);
		btnLamMoi.setIcon(new ImageIcon(new ImageIcon("img/Icon/Reset.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		add(btnLamMoi);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 104, 1260, 2);
		add(separator);
		loadDataLSP();
		repainting();
	}
	
	void repainting() {
		setBackground(Theme.LIGHT);
	}	
}