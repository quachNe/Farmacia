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
import DTO.NhaCungCap;
import DTO.RoundedBorder;
import BUS.DuocSiBUS;
import BUS.NhaCungCapBUS;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class NhaCungCapGUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private static JComboBox<String> cbb_find;
	private static NhaCungCapBUS nccbus = new NhaCungCapBUS();
	
	private DefaultTableModel model = new DefaultTableModel(
			new Object[][] {},
			new String[] {"Mã NCC","Tên NCC","Địa chỉ","SDT","Email","Trạng thái"}
			);
	DuocSiBUS bus = new DuocSiBUS();
	private JTextField txtTimKiem;
	private JComboBox<String> cbbTrangthai;
	private static JTable table;
	private JButton btnLock;
	public static Map<String, Boolean> NhaCungCapBiKhoa = new HashMap<>();
	private static JButton btnTimKiem = new JButton("");
	public static void loadDataNCC() {
	    ArrayList<NhaCungCap> arrNCC = nccbus.getAllNCC();
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    while (model.getRowCount() != 0) {
			model.removeRow(0);
		}

		for (NhaCungCap ncc : arrNCC) {
			model.addRow(new Object[] {
					ncc.getMa(),
					ncc.getTen(),
					ncc.getDiaChi(),
					ncc.getSoDT(),
					ncc.getEmail(),
					(ncc.isTrangThai() ? "Còn hợp tác" : "Hết hợp tác")
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
	public void loadDataFind() {
		String chuoi = txtTimKiem.getText();
	    ArrayList<NhaCungCap> arrNCC = nccbus.find(chuoi);
	    if (arrNCC == null || arrNCC.isEmpty()) {
	        ThongBao.baoLoi("Bảng không tồn tại dữ liệu");
	        txtTimKiem.setText("");
	        txtTimKiem.requestFocus();
	        return;
	    }
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    while (model.getRowCount() != 0) {
			model.removeRow(0);
		}

	    for (NhaCungCap ncc : arrNCC) {
			model.addRow(new Object[] {
					ncc.getMa(),
					ncc.getTen(),
					ncc.getDiaChi(),
					ncc.getSoDT(),
					ncc.getEmail(),
					(ncc.isTrangThai() ? "Còn hợp tác" : "Hết hợp tác")
		    });
		}
	} 
	public void loadDataCBB(int num) {
		ArrayList<NhaCungCap> arrNCC = nccbus.findTrangThais(num);
	    
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    while (model.getRowCount() != 0) {
			model.removeRow(0);
		}

	    for (NhaCungCap ncc : arrNCC) {
			model.addRow(new Object[] {
					ncc.getMa(),
					ncc.getTen(),
					ncc.getDiaChi(),
					ncc.getSoDT(),
					ncc.getEmail(),
					(ncc.isTrangThai() ? "Còn hợp tác" : "Hết hợp tác")
		    });
		}
	}
	
	public void handleAdd() {
		ThemNhaCungCap ncc = new ThemNhaCungCap("Thêm nhà cung cấp");
		ncc.setVisible(true);
		ncc.setLocationRelativeTo(null);
	}
	
	public void handleUp() {
		int row = table.getSelectedRow();
		if (row != -1) {
			String mancc = (String) table.getValueAt(row, 0);
			String tenncc = (String) table.getValueAt(row, 1); 
			String diachi = (String) table.getValueAt(row, 2);
			String sdt = (String) table.getValueAt(row, 3);
			String email = (String) table.getValueAt(row, 4);
			boolean trangthai = ((String) table.getValueAt(row, 5)).equals("Còn hợp tác");
			String tt = (String) table.getValueAt(row, 5);
			NhaCungCap ncc = new NhaCungCap(mancc, tenncc, diachi, sdt, email, trangthai);
			
			SuaNhaCungCap nccUp = new SuaNhaCungCap("Cập nhật nhà cung cấp",tenncc,tt);
			nccUp.setData(ncc,true);
			nccUp.setVisible(true);
			nccUp.setLocationRelativeTo(null);
			nccUp.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					loadDataNCC();
				}
			});
		}
		else ThongBao.baoLoi("Vui lòng chọn 1 nhà cung cấp");
	}
	
	public void handleDelete() {
		int row = table.getSelectedRow();
		if (row != -1) {
			String mancc = (String) table.getValueAt(row, 0);
			boolean trangthai = ((String) table.getValueAt(row, 5)).equals("Còn hợp tác");
			int reponse = ThongBao.cauHoi("Bạn có chắc muốn " + (trangthai ? "khóa" : "mở khóa") + " NCC có Mã NCC = " + mancc + "?");
			if (reponse == JOptionPane.YES_OPTION) {
				if (nccbus.changeStatusNhaCungCap(mancc, !trangthai)) {
					ThongBao.thongBao("Thay đổi trạng thái thành công");
					loadDataNCC();
				} else {
					ThongBao.baoLoi("Thay đổi trạng thái thất bại. Vui lòng kiểm tra lại");
				}
			}
		}
	}
	
	public void handleCbbTrangThai() {
		String data = (String) cbb_find.getSelectedItem();
        if (data != null) {
        	if (data.equals("Còn hợp tác")) {
    			loadDataCBB(1);
    		}else if (data.equals("Hết hợp tác")) {
    			loadDataCBB(0);
    		}else {
    			loadDataNCC();
    		}
		}
	}
	public void updateTable(ArrayList<NhaCungCap> filteredNCC) {
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    model.setRowCount(0);
	    for (NhaCungCap ncc : filteredNCC) {
	        String trangThai = ncc.isTrangThai() ? "Còn hợp tác" : "Hết hợp tác";
	        model.addRow(new Object[]{
	        		ncc.getMa(),
	        		ncc.getTen(),
	        		ncc.getDiaChi(),
	        		ncc.getSoDT(),
	        		ncc.getEmail(),
	        		trangThai
	        });
	    }
	}
	public NhaCungCapGUI() {
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
					loadDataNCC();
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
		RoundedBorder.setPlaceholder(txtTimKiem," Tìm Kiếm...");
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
		add(btnTimKiem);
		
		
		JButton btnThem = new JButton("Thêm");
		btnThem.setHorizontalAlignment(SwingConstants.LEFT);
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ThemNhaCungCap("Thêm nhà cung cấp").setVisible(true);
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
		        ArrayList<NhaCungCap> arrNCC = nccbus.getAllNCC();
		        ArrayList<NhaCungCap> filteredNCC = new ArrayList<>();

		        if (selectedStatus.equals("Còn hợp tác")) {
		            for (NhaCungCap ncc : arrNCC) {
		                if (ncc.isTrangThai()) {
		                	filteredNCC.add(ncc);
		                }
		            }
		        } else if (selectedStatus.equals("Hết hợp tác")) {
		            for (NhaCungCap ncc : arrNCC) {
		                if (!ncc.isTrangThai()) {
		                	filteredNCC.add(ncc);
		                }
		            }
		        } else {
		        	filteredNCC.addAll(arrNCC);
		        }
		        updateTable(filteredNCC);
		    }
		});
		cbbTrangthai.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		cbbTrangthai.setModel(new DefaultComboBoxModel<>(new String[] {"Tất cả", "Còn hợp tác", "Hết hợp tác"}));
		cbbTrangthai.setBounds(107, 64, 160, 30);
		cbbTrangthai.setBorder(new RoundedBorder(50));
		add(cbbTrangthai);
		
		JLabel lblNewLabel_1_1 = new JLabel("Trạng thái:");
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblNewLabel_1_1.setBounds(10, 64, 87, 30);
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
			    String status = table.getModel().getValueAt(table.getSelectedRow(), 5).toString();
			    if (status.equals("Còn hợp tác")) {
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
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(300);
		table.getColumnModel().getColumn(2).setPreferredWidth(190);
		table.getColumnModel().getColumn(3).setPreferredWidth(10);
		table.getColumnModel().getColumn(4).setPreferredWidth(50);
		table.getColumnModel().getColumn(5).setPreferredWidth(10);
		JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.ITALIC, 18));
		
		btnLock.setHorizontalAlignment(SwingConstants.LEFT);
		btnLock.setIcon(new ImageIcon(new ImageIcon("img/Icon/Lock.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		btnLock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
			    if (row != -1) {
			        String maNCC = (String) table.getValueAt(row, 0);
			        boolean trangthai = ((String) table.getValueAt(row, 2)).equals("Còn hợp tác");

			        int reponse = ThongBao.cauHoi("Bạn có chắc muốn " + (trangthai ? "khóa" : "mở khóa") + " Nhà cung cấp có mã nhà cung cấp = " + maNCC + "?");
			        if (reponse == JOptionPane.YES_OPTION) {
			            if (NhaCungCapBiKhoa.containsKey(maNCC) && NhaCungCapBiKhoa.get(maNCC)) {
			                if (nccbus.changeStatusNhaCungCap(maNCC, true)) {
			                    ThongBao.thongBao("Mở khóa thành công cho nhà cung cấp Mã NCC: " + maNCC);
			                    NhaCungCapBiKhoa.put(maNCC, false);
			                } else {
			                    ThongBao.baoLoi("Mở khóa thất bại cho nhà cung cấp Mã NCC: " + maNCC);
			                }
			            } else {
			                if (nccbus.changeStatusNhaCungCap(maNCC, false)) {
			                    ThongBao.thongBao("Khóa thành công cho nhà cung cấp xuất Mã NCC: " + maNCC);
			                    NhaCungCapBiKhoa.put(maNCC, true);
			                } else {
			                    ThongBao.baoLoi("Khóa thất bại cho nhà cung cấp Mã NCC: " + maNCC);
			                }
			            }
			            loadDataNCC();
			        }
			    } else {
			        ThongBao.baoLoi("Vui lòng chọn một nhà cung cấp");
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
				loadDataNCC();
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
		separator.setBounds(10, 104, 1260, 2);
		add(separator);
		loadDataNCC();
		repainting();
	}
	
	void repainting() {
		setBackground(Theme.LIGHT);
	}	
}