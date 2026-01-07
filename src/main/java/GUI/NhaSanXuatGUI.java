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

import DTO.NhaSanXuat;
import DTO.RoundedBorder;
import BUS.DuocSiBUS;
import BUS.NhaSanXuatBUS;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NhaSanXuatGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private static JComboBox<String> cbb_find;
	private static NhaSanXuatBUS nsxBUS = new NhaSanXuatBUS();
	private DefaultTableModel model = new DefaultTableModel(
			new Object[][] {},
			new String[] {"Mã NSX","Tên NSX","Trạng thái"}
			);
	DuocSiBUS bus = new DuocSiBUS();
	private static JTextField txtTimKiem;
	private JComboBox<String> cbbTrangthai;
	private static JTable table;
	private JButton btnLock;
	public static Map<String, Boolean> NhaSanXuatBiKhoa = new HashMap<>();
 private static	JButton btnTimKiem = new JButton("");
	public static void loadDataNSX() {
	    ArrayList<NhaSanXuat> arrNSX = nsxBUS.getAllNsx();
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    while (model.getRowCount() != 0) {
			model.removeRow(0);
		}
		for (NhaSanXuat nsx : arrNSX) {
			model.addRow(new Object[] {
					nsx.getMa(),
					nsx.getTen(),
					(nsx.getTrangThai() ? "Còn nhập hàng" : "Ngừng nhập hàng")
		    });
		}
	}
	public static void loadDataFind() {
		String chuoi = txtTimKiem.getText();
	    ArrayList<NhaSanXuat> arrNSX = nsxBUS.find(chuoi);
	    if (arrNSX == null || arrNSX.isEmpty()) {
	        ThongBao.baoLoi("Bảng không tồn tại dữ liệu");
	        txtTimKiem.setText("");
	        txtTimKiem.requestFocus();
	        return;
	    }
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    while (model.getRowCount() != 0) {
			model.removeRow(0);
		}

	    for (NhaSanXuat nsx : arrNSX) {
			model.addRow(new Object[] {
					nsx.getMa(),
					nsx.getTen(),
					(nsx.getTrangThai() ? "Còn nhập hàng" : "Ngừng nhập hàng")
		    });
		}
	} 
	public static void loadDataCBB(int num) {
		ArrayList<NhaSanXuat> arrNSX = nsxBUS.findTrangThai(num);
	    
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    while (model.getRowCount() != 0) {
			model.removeRow(0);
		}

	    for (NhaSanXuat nsx : arrNSX) {
			model.addRow(new Object[] {
					nsx.getMa(),
					nsx.getTen(),
					(nsx.getTrangThai() ? "Còn nhập hàng" : "Ngừng nhập hàng")
		    });
		}
	}
	
	public static void handleAdd() {
		ThemNhaSanXuat nsx = new ThemNhaSanXuat("Thêm nhà sản xuất");
		nsx.setVisible(true);
		nsx.setLocationRelativeTo(null);
	}
	
	public static void handleUp() {
		int row = table.getSelectedRow();
		if (row != -1) {
			String masx = (String) table.getValueAt(row, 0);
			String tennsx = (String) table.getValueAt(row, 1); 
			boolean trangthai = ((String) table.getValueAt(row, 2)).equals("Còn nhập hàng");
			
			NhaSanXuat nsx = new NhaSanXuat(masx, tennsx, trangthai);
			
			SuaNhaSanXuat nsxGui = new SuaNhaSanXuat("Cập nhật nhà sản xuất");
			nsxGui.setData(nsx);
			nsxGui.setVisible(true);
			nsxGui.setLocationRelativeTo(null);
		}
		else ThongBao.baoLoi("Vui lòng chọn 1 nhà sản xuất");
	}
	
	public static void handleDelete() {
		int row = table.getSelectedRow();
		if (row != -1) {
			String mansx = (String) table.getValueAt(row, 0);
			boolean trangthai = ((String) table.getValueAt(row, 2)).equals("Còn nhập hàng");
			int reponse = ThongBao.cauHoi("Bạn có chắc muốn " + (trangthai ? "khóa" : "mở khóa") + " NSX có Mã NSX  = " + mansx + "?");
			if (reponse == JOptionPane.YES_OPTION) {
				if (nsxBUS.deleteNhaSanXuat(mansx,!trangthai)) {
					ThongBao.thongBao("Thay đổi trạng thái thành công");
					loadDataNSX();
				} else {
					ThongBao.baoLoi("Xóa thất bại. Vui lòng kiểm tra lại");
				}
			}
		}
	}
	
	public static void handleCbbTrangThai() {
		String data = (String) cbb_find.getSelectedItem();
        if (data != null) {
        	if (data.equals("Còn nhập hàng")) {
    			loadDataCBB(1);
    		}else if (data.equals("Ngừng nhập hàng")) {
    			loadDataCBB(0);
    		}else {
    			loadDataNSX();
    		}
		}
	}
	public void updateTable(ArrayList<NhaSanXuat> filteredNSX) {
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    model.setRowCount(0);
	    for (NhaSanXuat nsx : filteredNSX) {
	        String trangThai = nsx.getTrangThai() ? "Còn nhập hàng" : "Ngừng nhập hàng";
	        model.addRow(new Object[]{
	            nsx.getMa(),
	            nsx.getTen(),
	            trangThai
	        });
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
	public NhaSanXuatGUI() {
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
					loadDataNSX();
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
		txtTimKiem.setBounds(10, 23, 382, 31);
		add(txtTimKiem);
		txtTimKiem.setColumns(10);
		txtTimKiem.setBorder(new RoundedBorder(50));
		

		btnTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		btnTimKiem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtTimKiem.getText().equals(" Tìm Kiếm...")) {
					ThongBao.baoLoi("Vui lòng nhập nội dung tìm kiếm");
					return;
				}
				loadDataFind();
			}
		});
		btnTimKiem.setBounds(402, 23, 35, 31);
		btnTimKiem.setIcon(new ImageIcon(new ImageIcon("img/Icon/Search.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		RoundedBorder.setPlaceholder(txtTimKiem," Tìm Kiếm...");
		add(btnTimKiem);
		
		
		JButton btnThem = new JButton("Thêm");
		btnThem.setHorizontalAlignment(SwingConstants.LEFT);
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ThemNhaSanXuat("Thêm nhà sản xuất").setVisible(true);
			}
		});	
		btnThem.setBounds(790, 23, 120, 31);
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
		        ArrayList<NhaSanXuat> arrNSX = nsxBUS.getAllNsx();
		        ArrayList<NhaSanXuat> filteredNSX = new ArrayList<>();

		        if (selectedStatus.equals("Còn nhập hàng")) {
		            for (NhaSanXuat nsx : arrNSX) {
		                if (nsx.getTrangThai()) {
		                    filteredNSX.add(nsx);
		                }
		            }
		        } else if (selectedStatus.equals("Ngừng nhập hàng")) {
		            for (NhaSanXuat nsx : arrNSX) {
		                if (!nsx.getTrangThai()) {
		                    filteredNSX.add(nsx);
		                }
		            }
		        } else {
		            filteredNSX.addAll(arrNSX);
		        }
		        updateTable(filteredNSX);
		    }
		});
		cbbTrangthai.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		cbbTrangthai.setModel(new DefaultComboBoxModel<>(new String[] {"Tất cả", "Còn nhập hàng", "Ngừng nhập hàng"}));
		cbbTrangthai.setBounds(116, 64, 195, 31);
		cbbTrangthai.setBorder(new RoundedBorder(50));
		add(cbbTrangthai);
		
		JLabel lblNewLabel_1_1 = new JLabel("Trạng thái:");
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblNewLabel_1_1.setBounds(10, 64, 96, 31);
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
		btnCapNhat.setBounds(920, 23, 140, 31);
		add(btnCapNhat);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 128, 1260, 700);
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
			    if (status.equals("Còn nhập hàng")) {
			        btnLock.setText("Khóa");
			        btnLock.setIcon(iconLock);
			    } else {
			        btnLock.setText("Mở khóa");
			        btnLock.setIcon(iconUnlock);
			    }
			}
		});
		scrollPane.setViewportView(table);
		setColorCell(table);
		JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.ITALIC, 18));
		
		btnLock.setHorizontalAlignment(SwingConstants.LEFT);
		btnLock.setIcon(new ImageIcon(new ImageIcon("img/Icon/Lock.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		btnLock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
			    if (row != -1) {
			        String mansx = (String) table.getValueAt(row, 0);
			        boolean trangthai = ((String) table.getValueAt(row, 2)).equals("Còn nhập hàng");

			        int reponse = ThongBao.cauHoi("Bạn có chắc muốn " + (trangthai ? "khóa" : "mở khóa") + " Nhà sản xuất có mã nhà sản xuất = " + mansx + "?");
			        if (reponse == JOptionPane.YES_OPTION) {
			            if (NhaSanXuatBiKhoa.containsKey(mansx) && NhaSanXuatBiKhoa.get(mansx)) {
			                if (nsxBUS.deleteNhaSanXuat(mansx, true)) {
			                    ThongBao.thongBao("Mở khóa thành công cho nhà sản xuẩ Mã NSX: " + mansx);
			                    NhaSanXuatBiKhoa.put(mansx, false);
			                } else {
			                    ThongBao.baoLoi("Mở khóa thất bại cho nhà sản xuất Mã NSX: " + mansx);
			                }
			            } else {
			                if (nsxBUS.deleteNhaSanXuat(mansx, false)) {
			                    ThongBao.thongBao("Khóa thành công cho nhà sản xuất Mã NSX: " + mansx);
			                    NhaSanXuatBiKhoa.put(mansx, true);
			                } else {
			                    ThongBao.baoLoi("Khóa thất bại cho nhà sản xuất Mã NSX: " + mansx);
			                }
			            }
			            loadDataNSX();
			        }
			    } else {
			        ThongBao.baoLoi("Vui lòng chọn một nhà sản xuất");
			    }
			}
		});
		btnLock.setForeground(new Color(255, 255, 255));
		btnLock.setBackground(new Color(11, 101, 140));
		btnLock.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnLock.setBorderPainted(false);
		btnLock.setFocusPainted(false);
		btnLock.setBounds(1070, 23, 140, 31);
		add(btnLock);
		
		JButton btnLamMoi = new JButton("");
		btnLamMoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadDataNSX();
				txtTimKiem.setText("");
				cbbTrangthai.setSelectedIndex(0);
				btnLock.setText("Khóa");
				btnLock.setIcon(new ImageIcon(new ImageIcon("img/Icon/Lock.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
				RoundedBorder.setPlaceholder(txtTimKiem," Tìm Kiếm...");
			}
		});
		btnLamMoi.setForeground(new Color(255, 255, 255));
		btnLamMoi.setBackground(new Color(11, 101, 140));
		btnLamMoi.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnLamMoi.setBorderPainted(false);
		btnLamMoi.setFocusPainted(false);
		btnLamMoi.setBounds(1220, 23, 50, 31);
		btnLamMoi.setIcon(new ImageIcon(new ImageIcon("img/Icon/Reset.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		add(btnLamMoi);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 111, 1260, 8);
		add(separator);
		loadDataNSX();
		repainting();
	}
	
	void repainting() {
		setBackground(Theme.LIGHT);
	}	
}