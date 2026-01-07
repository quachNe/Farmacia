package GUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import BUS.*;
import DTO.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;
import java.util.Date;
import java.util.HashSet;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PhieuNhapXemGUI extends JPanel {
	private PhieuNhapBUS bus = new PhieuNhapBUS();
	private ChonNhaCungCap nccForm = new ChonNhaCungCap();
	private ChonSanPham spForm = new ChonSanPham();
	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelDS = new DefaultTableModel(
			new Object[][] {}, new String[] {"Mã PN", "Ngày nhập", "Người nhập", "NCC", "Tổng"}
	);
	private String maDS;
	private JTextField txtTimKiem;
	private JTable tableDS;
	private JTextField txtMaPN;
	private JTextField txtNgayLap;
	private JTextField txtNguoiLap;
	private JTextField txtNhaCungCap;
	private JTextField txtMaSP;
	private JTextField txtLoSX;
	private JTextField txtNgaySX;
	private JTextField txtGiaNhap;
	private JTextField txtSoLuong;
	private JTextField txtHanSD;
	private JTextField txtGiaBan;
	private JTextField txtSeri;
	private JTable tableSanPham;
	private JCheckBox chkMaPN;
	private JCheckBox chkNgayLap;
	private JCheckBox chkNhaCungCap;
	private JCheckBox chkSanPham;
	private JLabel lblTongTien;
	private JDateChooser dcDateFrom;
	private JDateChooser dcDateTo;
	private JButton btnNhaCungCap;
	private JButton btnSanPham;
	private JButton btnTimKiem;
	private DefaultTableModel modelSanPham = new DefaultTableModel(
			new Object[][] {}, new String[] {"Mã SP", "Lô SX", "Ngày SX", "Hạn SD", "Giá nhập", "Giá bán", "Số lượng","Seri"}
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
	public PhieuNhapXemGUI() {
		setBounds(0,0,1280,850);
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 640, 850);
		add(panel);
		panel.setLayout(null);   
		panel.setBackground(Theme.LIGHT);
		
		JLabel lblDSPhieuNhap = new JLabel("DANH SÁCH PHIẾU NHẬP");
		lblDSPhieuNhap.setHorizontalAlignment(SwingConstants.CENTER);
		lblDSPhieuNhap.setFont(new Font("Segoe UI", Font.PLAIN, 25));
		lblDSPhieuNhap.setBounds(10, 10, 620, 30);
		panel.add(lblDSPhieuNhap);
		
		JPanel panel_2 = new JPanel();
	    panel_2.setBackground(Theme.LIGHT);
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Ti\u00EAu ch\u00ED t\u00ECm ki\u1EBFm", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.ITALIC, 18), new Color(0, 0, 0)));
		panel_2.setBounds(10, 50, 620, 80);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		
		chkMaPN = new JCheckBox("Mã phiếu",true);
		chkMaPN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkMaPN.isSelected()) {
		            chkNgayLap.setSelected(false);
		            chkNhaCungCap.setSelected(false);
		            chkSanPham.setSelected(false);
		        }
			}
		});
		chkMaPN.setOpaque(false);
		chkMaPN.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		chkMaPN.setBounds(70, 37, 103, 25);
		panel_2.add(chkMaPN);
		
		chkNgayLap = new JCheckBox("Ngày lập");
		chkNgayLap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkNgayLap.isSelected()) {
		            chkMaPN.setSelected(false);
		            chkNhaCungCap.setSelected(false);
		            chkSanPham.setSelected(false);
		        }
			}
		});
		chkNgayLap.setOpaque(false);
		chkNgayLap.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		chkNgayLap.setBounds(175, 37, 103, 25);
		panel_2.add(chkNgayLap);
		
		chkNhaCungCap = new JCheckBox("Nhà cung cấp");
		chkNhaCungCap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkNhaCungCap.isSelected()) {
		            chkMaPN.setSelected(false);
		            chkNgayLap.setSelected(false);
		            chkSanPham.setSelected(false);
		        }
			}
		});
		chkNhaCungCap.setOpaque(false);
		chkNhaCungCap.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		chkNhaCungCap.setBounds(280, 37, 139, 25);
		panel_2.add(chkNhaCungCap);
		
		chkSanPham = new JCheckBox("Sản phẩm");
		chkSanPham.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkSanPham.isSelected()) {
		            chkMaPN.setSelected(false);
		            chkNgayLap.setSelected(false);
		            chkNhaCungCap.setSelected(false);
		        }
			}
		});
		chkSanPham.setOpaque(false);
		chkSanPham.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		chkSanPham.setBounds(421, 37, 130, 25);
		panel_2.add(chkSanPham);
		
		JPanel panel_2_1 = new JPanel();
	    panel_2_1.setBackground(Theme.LIGHT);
		panel_2_1.setLayout(null);
		panel_2_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Th\u00F4ng tin t\u00ECm ki\u1EBFm", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.ITALIC, 18), new Color(0, 0, 0)));
		panel_2_1.setBounds(10, 140, 620, 160);
		panel.add(panel_2_1);
		
		JLabel lblTimKiem = new JLabel("Tìm kiếm :\r\n");
		lblTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblTimKiem.setBounds(10, 35, 85, 25);
		panel_2_1.add(lblTimKiem);
		
		txtTimKiem = new JTextField();
		txtTimKiem.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        // Kiểm tra nếu phím Enter được nhấn
		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		            // Lấy nội dung trong txtTimKiem
		            String input = txtTimKiem.getText().trim();
		            // Kiểm tra nếu có dữ liệu thì gọi btnTimKiem
		            if (!input.isEmpty()) {
		                btnTimKiem.doClick(); 
		            } else {
		                System.out.println("Vui lòng nhập dữ liệu trước khi tìm kiếm.");
		            }
		        }
		    }
		});

		txtTimKiem.setBounds(100, 35, 190, 25);
		panel_2_1.add(txtTimKiem);
		txtTimKiem.setColumns(10);
		
		JLabel lblThoiGian = new JLabel("Thời gian:");
		lblThoiGian.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblThoiGian.setBounds(10, 80, 85, 25);
		panel_2_1.add(lblThoiGian);
		
		dcDateFrom = new JDateChooser((Date) null, "yyyy-MM-dd");
		dcDateFrom.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		dcDateFrom.setBounds(100, 80, 190, 25);
		panel_2_1.add(dcDateFrom);
		
		dcDateTo = new JDateChooser((Date) null, "yyyy-MM-dd");
		dcDateTo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		dcDateTo.setBounds(331, 80, 190, 25);
		panel_2_1.add(dcDateTo);
		
		JLabel lblNewLabel_2 = new JLabel("-");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.PLAIN, 50));
		lblNewLabel_2.setBounds(301, 72, 20, 26);
		panel_2_1.add(lblNewLabel_2);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chkMaPN.setSelected(true);
				chkNgayLap.setSelected(false);
				chkNhaCungCap.setSelected(false);
				chkSanPham.setSelected(false);
				txtMaPN.setText("");
				txtNgayLap.setText("");
				txtNguoiLap.setText("");
				txtNhaCungCap.setText("");
				lblTongTien.setText("Tổng tiền: 0");
				loadDS(bus.getPhieuNhap());
				nccForm = new ChonNhaCungCap();
				spForm = new ChonSanPham();
				txtMaSP.setText("");
				txtLoSX.setText("");
				txtNgaySX.setText("");
				txtHanSD.setText("");
				txtGiaBan.setText("");
				txtGiaNhap.setText("");
				modelSanPham.setRowCount(0);  // This will clear all rows in the table
				txtSoLuong.setText("");
				txtSeri.setText("");
				lblTongTien.setText("Tổng tiền: 0");
			}
		});
		btnReset.setForeground(new Color(255, 255, 255));
		btnReset.setBackground(new Color(11, 101, 140));
		btnReset.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		btnReset.setBounds(100, 120, 85, 30);
		panel_2_1.add(btnReset);
		
		btnNhaCungCap = new JButton("Chọn nhà cung cấp\r\n");
		btnNhaCungCap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nccForm.setVisible(true);
			}
		});
		btnNhaCungCap.setForeground(new Color(255, 255, 255));
		btnNhaCungCap.setBackground(new Color(11, 101, 140));
		btnNhaCungCap.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		btnNhaCungCap.setBounds(195, 120, 190, 30);
		panel_2_1.add(btnNhaCungCap);
		
		btnSanPham = new JButton("Chọn sản phẩm");
		btnSanPham.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spForm.setVisible(true);
			}
		});
		btnSanPham.setForeground(new Color(255, 255, 255));
		btnSanPham.setBackground(new Color(11, 101, 140));
		btnSanPham.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		btnSanPham.setBounds(395, 120, 190, 30);
		panel_2_1.add(btnSanPham);
		
		 btnTimKiem = new JButton("");
		btnTimKiem.setIcon(new ImageIcon(new ImageIcon("img/icon/Search.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		
		btnTimKiem.addActionListener(new ActionListener() {
		    @SuppressWarnings("serial")
		    public void actionPerformed(ActionEvent e) {
		    	if (!chkMaPN.isSelected() && !chkNhaCungCap.isSelected() && !chkSanPham.isSelected() && !chkNgayLap.isSelected()) {
		            ThongBao.baoLoi("Vui lòng chọn một tiêu chí tìm kiếm");
		            return;
		        }
		        if (chkMaPN.isSelected()) {
		            if (txtTimKiem.getText().isEmpty()) {
		                ThongBao.baoLoi("Vui lòng nhập phiếu nhập");
		            } else {
		                findDS(new ArrayList<PhieuNhap>() {
		                    { add(bus.timPhieuNhap(txtTimKiem.getText())); }
		                });
		            }
		            return;
		        }

		        ArrayList<String> nhaCungCap = chkNhaCungCap.isSelected() ? nccForm.nccChon : null;
		        ArrayList<String> sanPham = chkSanPham.isSelected() ? spForm.spChon : null; // Get the selected products

		        LocalDate dateFrom = null;
		        LocalDate dateTo = null;
		        if (chkNgayLap.isSelected()) {
		            // Check if dates are selected when 'Ngày lập' is checked
		            if (dcDateFrom.getDate() == null || dcDateTo.getDate() == null) {
		                ThongBao.baoLoi("Vui lòng chọn cả ngày bắt đầu và ngày kết thúc");
		                return;
		            }

		            try {
		                if (dcDateFrom.getDate().compareTo(new java.util.Date()) > 0) {
		                    ThongBao.baoLoi("Ngày bên trái phải nhỏ hơn hoặc bằng hiện tại");
		                    return;
		                }
		            } catch (Exception ex) {
		                ThongBao.baoLoi("Ngày bên trái không hợp lệ");
		                return;
		            }

		            try {
		                if (dcDateTo.getDate().compareTo(new java.util.Date()) > 0) {
		                    ThongBao.baoLoi("Ngày bên phải phải nhỏ hơn hoặc bằng hiện tại");
		                    return;
		                }
		            } catch (Exception ex) {
		                ThongBao.baoLoi("Ngày bên phải không hợp lệ");
		                return;
		            }

		            if (dcDateFrom.getDate().compareTo(dcDateTo.getDate()) > 0) {
		                ThongBao.baoLoi("Ngày bên trái phải nhỏ hơn hoặc bằng ngày bên phải");
		                return;
		            }

		            dateFrom = Time.toLocalDate(dcDateFrom.getDate());
		            dateTo = Time.toLocalDate(dcDateTo.getDate());
		        }

		        // Perform the search using the selected suppliers, products, and date range
		        findDS(bus.timPhieuNhap(nhaCungCap, sanPham, dateFrom, dateTo));
		    }
		});

		btnTimKiem.setBounds(48, 120, 42, 30);
		panel_2_1.add(btnTimKiem);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 297, 620, 410);
		panel.add(scrollPane);
		
		tableDS = new JTable(modelDS);
		   tableDS.getTableHeader().setBackground(Color.decode("#B9B9B9"));
		setColorCell(tableDS);
		tableDS.setFocusable(false);
		tableDS.setColumnSelectionAllowed(true);
		tableDS.setCellSelectionEnabled(false);
		tableDS.setRowSelectionAllowed(true);
		tableDS.setDefaultEditor(Object.class, null);
		tableDS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableDS.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN | Font.ITALIC, 18));
		tableDS.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tableDS.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tableDS.getSelectedRow();
				txtMaPN.setText(modelDS.getValueAt(row, 0) + "");
				txtNgayLap.setText(modelDS.getValueAt(row, 1) + "");
				txtNguoiLap.setText(modelDS.getValueAt(row, 2) + "");
				txtNhaCungCap.setText(modelDS.getValueAt(row, 3) + "");
				lblTongTien.setText("Tổng tiền: " + modelDS.getValueAt(row, 4));
				loadCTSP(modelDS.getValueAt(row, 0) + "");
			}
		});
		scrollPane.setViewportView(tableDS);
		
		JButton btnXuatDSPhieu = new JButton("Xuất file");
		btnXuatDSPhieu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser saveFileDialog = new JFileChooser(System.getProperty("user.dir"));
				saveFileDialog.setDialogTitle("Chọn vị trí cần xuất");
				saveFileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Excel Workbook (.xlsx)", "xlsx"));
				saveFileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Excel 97-2003 Workbook (.xls)", "xls"));
				saveFileDialog.setAcceptAllFileFilterUsed(false);
				
				if (saveFileDialog.showSaveDialog(PhieuNhapXemGUI.this) == JFileChooser.APPROVE_OPTION) {
				    String path = saveFileDialog.getSelectedFile().getAbsolutePath();
				    String desc = saveFileDialog.getFileFilter().getDescription();
				    if (!path.endsWith(".xlsx") && (!path.endsWith(".xls")))
				    	path += (desc.equals("Excel Workbook (.xlsx)")) ? ".xlsx" : ".xls";
				    if (bus.xuatChiTietExcel(modelDS, path)) ThongBao.thongBao("Xuất file Excel thành công");
				    else ThongBao.baoLoi("Xuất file Excel thất bại");
				}
			}
		});
		btnXuatDSPhieu.setForeground(new Color(255, 255, 255));
		btnXuatDSPhieu.setBackground(new Color(11, 101, 140));
		btnXuatDSPhieu.setIcon(new ImageIcon(new ImageIcon("img/Icon/Export.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		btnXuatDSPhieu.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		btnXuatDSPhieu.setBounds(200, 728, 150, 30);
		panel.add(btnXuatDSPhieu);
		
		JPanel panel_1 = new JPanel();
		   panel_1.setBackground(Theme.LIGHT);
		panel_1.setBounds(640, 0, 640, 850);
		add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblTTPhieuNhap = new JLabel("THÔNG TIN  PHIẾU NHẬP");
		lblTTPhieuNhap.setHorizontalAlignment(SwingConstants.CENTER);
		lblTTPhieuNhap.setFont(new Font("Segoe UI", Font.PLAIN, 25));
		lblTTPhieuNhap.setBounds(10, 10, 620, 30);
		panel_1.add(lblTTPhieuNhap);
		
		JLabel lblMaPhieu = new JLabel("Mã phiếu:");
		lblMaPhieu.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblMaPhieu.setBounds(10, 60, 109, 25);
		panel_1.add(lblMaPhieu);
		
		txtMaPN = new JTextField();
		txtMaPN.setBounds(129, 60, 501, 25);
		txtMaPN.setEnabled(false);
		panel_1.add(txtMaPN);
		txtMaPN.setColumns(10);
		
		JLabel lblNgayLap = new JLabel("Ngày lập:");
		lblNgayLap.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblNgayLap.setBounds(10, 100, 109, 25);
		panel_1.add(lblNgayLap);
		
		JLabel lblNguoiLap = new JLabel("Người lập:");
		lblNguoiLap.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblNguoiLap.setBounds(10, 140, 109, 25);
		panel_1.add(lblNguoiLap);
		
		JLabel lblNhaCungCap = new JLabel("Nhà cung cấp:\r\n");
		lblNhaCungCap.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblNhaCungCap.setBounds(10, 180, 120, 25);
		panel_1.add(lblNhaCungCap);
		
		txtNgayLap = new JTextField();
		txtNgayLap.setColumns(10);
		txtNgayLap.setEnabled(false);
		txtNgayLap.setBounds(129, 100, 501, 25);
		panel_1.add(txtNgayLap);
		
		txtNguoiLap = new JTextField(maDS);
		txtNguoiLap.setColumns(10);
		txtNguoiLap.setEnabled(false);
		txtNguoiLap.setBounds(129, 140, 501, 25);
		panel_1.add(txtNguoiLap);
		
		txtNhaCungCap = new JTextField();
		txtNhaCungCap.setColumns(10);
		txtNhaCungCap.setEnabled(false);
		txtNhaCungCap.setBounds(129, 180, 501, 25);
		panel_1.add(txtNhaCungCap);
		
		JPanel panel_3 = new JPanel();
		
		
		
		   panel_3.setBackground(Theme.LIGHT);
		panel_3.setBorder(new TitledBorder(null, "Th\u00F4ng tin t\u00ECm ki\u1EBFm", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.ITALIC, 18), new Color(0, 0, 0)));
		panel_3.setBounds(10, 215, 620, 485);
		panel_1.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblMaSP = new JLabel("Mã SP:");
		lblMaSP.setBounds(10, 25, 82, 25);
		lblMaSP.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		panel_3.add(lblMaSP);
		
		JLabel lblNSX = new JLabel("NSX:");
		lblNSX.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblNSX.setBounds(10, 65, 82, 25);
		panel_3.add(lblNSX);
		
		JLabel lblGiaNhap = new JLabel("Giá nhập:");
		lblGiaNhap.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblGiaNhap.setBounds(10, 105, 82, 25);
		panel_3.add(lblGiaNhap);
		
		JLabel lblSoLuong = new JLabel("Số lượng:");
		lblSoLuong.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblSoLuong.setBounds(10, 150, 82, 25);
		panel_3.add(lblSoLuong);
		
		JLabel lblLOSX = new JLabel("Lô SX:");
		lblLOSX.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblLOSX.setBounds(318, 25, 82, 25);
		panel_3.add(lblLOSX);
		
		JLabel lblHSD = new JLabel("HSD:");
		lblHSD.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblHSD.setBounds(318, 65, 82, 25);
		panel_3.add(lblHSD);
		
		JLabel lblGiaBan = new JLabel("Giá bán:");
		lblGiaBan.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblGiaBan.setBounds(318, 105, 82, 25);
		panel_3.add(lblGiaBan);
		
		JLabel lblSeri = new JLabel("Seri:");
		lblSeri.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblSeri.setBounds(318, 150, 82, 25);
		panel_3.add(lblSeri);
		
		txtMaSP = new JTextField();
		txtMaSP.setColumns(10);
		txtMaSP.setEnabled(false);
		txtMaSP.setBounds(102, 25, 200, 25);
		panel_3.add(txtMaSP);
		
		txtLoSX = new JTextField();
		txtLoSX.setColumns(10);
		txtLoSX.setEnabled(false);
		txtLoSX.setBounds(410, 25, 200, 25);
		panel_3.add(txtLoSX);
		
		txtNgaySX = new JTextField();
		txtNgaySX.setColumns(10);
		txtNgaySX.setEnabled(false);
		txtNgaySX.setBounds(102, 65, 200, 25);
		panel_3.add(txtNgaySX);
		
		txtGiaNhap = new JTextField();
		txtGiaNhap.setColumns(10);
		txtGiaNhap.setEnabled(false);
		txtGiaNhap.setBounds(102, 109, 200, 25);
		panel_3.add(txtGiaNhap);
		
		txtSoLuong = new JTextField();
		txtSoLuong.setColumns(10);
		txtSoLuong.setEnabled(false);
		txtSoLuong.setBounds(102, 150, 200, 25);
		panel_3.add(txtSoLuong);
		
		txtHanSD = new JTextField();
		txtHanSD.setColumns(10);
		txtHanSD.setEnabled(false);
		txtHanSD.setBounds(410, 65, 200, 25);
		panel_3.add(txtHanSD);
		
		txtGiaBan = new JTextField();
		txtGiaBan.setColumns(10);
		txtGiaBan.setEnabled(false);
		txtGiaBan.setBounds(410, 105, 200, 25);
		panel_3.add(txtGiaBan);
		
		txtSeri = new JTextField();
		txtSeri.setColumns(10);
		txtSeri.setEnabled(false);
		txtSeri.setBounds(410, 150, 200, 25);
		panel_3.add(txtSeri);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 186, 600, 290);
		panel_3.add(scrollPane_1);
		
		tableSanPham = new JTable(modelSanPham);
		   tableSanPham.getTableHeader().setBackground(Color.decode("#B9B9B9"));
		   setColorCell(tableSanPham);
		tableSanPham.setFocusable(false);
		tableSanPham.setColumnSelectionAllowed(true);
		tableSanPham.setCellSelectionEnabled(false);
		tableSanPham.setRowSelectionAllowed(true);
		tableSanPham.setDefaultEditor(Object.class, null);tableDS.setFocusable(false);
		tableSanPham.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN | Font.ITALIC, 16));
		tableSanPham.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tableSanPham.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableSanPham.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tableSanPham.getSelectedRow();
				txtMaSP.setText(modelSanPham.getValueAt(row, 0) + "");
				txtLoSX.setText(modelSanPham.getValueAt(row, 1) + "");
				txtNgaySX.setText(modelSanPham.getValueAt(row, 2) + "");
				txtHanSD.setText(modelSanPham.getValueAt(row, 3) + "");
				txtGiaNhap.setText(modelSanPham.getValueAt(row, 4) + "");
				txtGiaBan.setText(modelSanPham.getValueAt(row, 5) + "");
				txtSoLuong.setText(modelSanPham.getValueAt(row, 6) + "");
				txtSeri.setText((String) modelSanPham.getValueAt(row, 7));
			}
		});
		scrollPane_1.setViewportView(tableSanPham);
		
		JButton btnXuatPN = new JButton("Xuất file");
		btnXuatPN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtMaPN.getText().isEmpty()) {
					ThongBao.baoLoi("Vui lòng chọn 1 phiếu nhập");
					return;
				}
				else {
					JFileChooser saveFileDialog = new JFileChooser(System.getProperty("user.dir"));
					saveFileDialog.setDialogTitle("Chọn vị trí cần xuất");
					saveFileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Portable Document Format (.pdf)", "pdf"));
					saveFileDialog.setAcceptAllFileFilterUsed(false);
					
					if (saveFileDialog.showSaveDialog(PhieuNhapXemGUI.this) == JFileChooser.APPROVE_OPTION) {
					    String path = saveFileDialog.getSelectedFile().getAbsolutePath();
					    if (!path.endsWith(".pdf")) path += ".pdf";
					    if (bus.xuatPDF(bus.timPhieuNhap(txtMaPN.getText()), bus.getSanPhamNhap(txtMaPN.getText()), path)) {
							ThongBao.thongBao("Xuất phiếu nhập ra PDF thành công");
							try { Desktop.getDesktop().open(new java.io.File(path)); }
							catch (Exception ex) {}
					    }
						else
							ThongBao.baoLoi("Xuất phiếu nhập ra PDF thất bại");
					}
				}
			}
		});
		btnXuatPN.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		btnXuatPN.setForeground(new Color(255, 255, 255));
		btnXuatPN.setBackground(new Color(11, 101, 140));
		btnXuatPN.setIcon(new ImageIcon(new ImageIcon("img/Icon/Export.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		btnXuatPN.setBounds(268, 728, 150, 30);
		panel_1.add(btnXuatPN);
		
		lblTongTien = new JLabel("Tổng tiền: 0");
		lblTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblTongTien.setBounds(445, 710, 185, 21);
		panel_1.add(lblTongTien);
		loadDS(bus.getPhieuNhap());
		
		repainting();
//		if (chkMaPN.isSelected()) {
//            chkNgayLap.setSelected(false);
//            chkNhaCungCap.setSelected(false);
//            chkSanPham.setSelected(false);
//            txtMaPNTim.setEnabled(true);
//            dcDateFrom.setEnabled(false);
//            dcDateTo.setEnabled(false);
//            btnSanPham.setEnabled(false);
//            btnNhaCungCap.setEnabled(false);
//        }
	}
	
	private void findDS(ArrayList<PhieuNhap> listPN) {
		if (listPN == null || listPN.size() == 0) {
//			ThongBao.baoLoi("Bảng không có dữ liệu hoặc dữ liệu không hợp lệ");
//			return;
			modelDS.setRowCount(0);
		}
		else 
			loadDS(listPN);
	}
	
	private void loadDS(ArrayList<PhieuNhap> listPN) {
		if (listPN == null || listPN.size() == 0) return;		
		modelDS.setRowCount(0);
		for (PhieuNhap pn: listPN) {
			Object[] data = new Object[] { pn.getMa(), pn.getNgayLap(), pn.getNguoiLap(), pn.getNhaCungCap(), pn.getTongTien() };
			modelDS.addRow(data);
		}
	}
	
	private void loadCTSP(String maPN) {
		ArrayList<ChiTietPhieuNhap> listSP = bus.getSanPhamNhap(maPN);
		modelSanPham.setRowCount(0);
		for (ChiTietPhieuNhap sp: listSP) {
			long giaNhap = sp.getGiaNhap();
			int phanTram = (int) sp.getphantram();
			long giaBan = giaNhap + (giaNhap * phanTram / 100);

			Object[] data = new Object[] {sp.getChiTiet().getMaSanPham(), sp.getChiTiet().getLoSanXuat(), 
					sp.getChiTiet().getNgaySanXuat(), sp.getChiTiet().getHanSuDung(), sp.getGiaNhap(), 
					giaBan, sp.getSoLuong(),sp.getChiTiet().getMavach()};
			modelSanPham.addRow(data);
		}
	}

	private class ChonNhaCungCap extends JDialog {
		private static final long serialVersionUID = 1L;
		ArrayList<String> nccChon =new ArrayList<>();
		private JPanel pnListNCC;
		private JCheckBox chkAll;
		
		ChonNhaCungCap() {
			setIconImage(new ImageIcon("img/icon.png").getImage());
			setTitle("Chọn nhà cung cấp");
			setModalityType(ModalityType.APPLICATION_MODAL);
			setResizable(false);
			setBounds(100, 100, 400, 500);
			setLocationRelativeTo(null);
			getContentPane().setLayout(null);
			
			JLabel lblTitle = new JLabel("CHỌN NHÀ CUNG CẤP");
			lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
			lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
			lblTitle.setBounds(0, 10, 384, 40);
			getContentPane().add(lblTitle);
			
			chkAll = new JCheckBox("Chọn tất cả", true);
			chkAll.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (Component c: pnListNCC.getComponents())
						((JCheckBox)c).setSelected(chkAll.isSelected());
				}
			});
			chkAll.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			chkAll.setBounds(20, 60, 344, 25);
			getContentPane().add(chkAll);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(20, 92, 344, 295);
			getContentPane().add(scrollPane);
			
			pnListNCC = new JPanel();
			scrollPane.setViewportView(pnListNCC);
			pnListNCC.setLayout(new BoxLayout(pnListNCC, BoxLayout.Y_AXIS));
			
			JButton btnOK = new JButton("OK");
			btnOK.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			        nccChon.clear();
			        for (Component c : pnListNCC.getComponents()) {
			            JCheckBox checkBox = (JCheckBox) c;
			            if (checkBox.isSelected()) {
			                String ncc = checkBox.getText();
			                nccChon.add(ncc.substring(0, ncc.indexOf("-")).trim());
			              
			            }
			        }
			        if (nccChon.size() == 0) {
			            ThongBao.baoLoi("Vui lòng chọn ít nhất 1 nhà cung cấp");
			        } else {
			            dispose();
			            btnTimKiem.doClick();
			        }
			    }
			});
			btnOK.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			btnOK.setBounds(61, 405, 100, 40);
			getContentPane().add(btnOK);
			
			JButton btnHuy = new JButton("Hủy bỏ");
			btnHuy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					for (Component c: pnListNCC.getComponents()) {
						JCheckBox chk = (JCheckBox)c;
						if (chk.isSelected() != (nccChon.indexOf(chk.getText()) != -1)) chk.doClick();
					}
				}
			});
			btnHuy.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			btnHuy.setBounds(223, 405, 100, 40);
			getContentPane().add(btnHuy);
			loadNCC();
			repainting();
		}
		
		private void loadNCC() {
			ArrayList<NhaCungCap> nhaCungCap = new NhaCungCapBUS().getAllNCC();
			for (NhaCungCap ncc: nhaCungCap) {
				nccChon.add(ncc.getMa());
				JCheckBox chk = new JCheckBox(ncc.toString(), true);
				chk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						for (Component c: pnListNCC.getComponents()) {
							if (((JCheckBox)c).isSelected() == false) {
								chkAll.setSelected(false);
								break;
							}
							chkAll.setSelected(true);
						}
					}
				});
				chk.setFont(new Font("Segoe UI", Font.PLAIN, 16));
				pnListNCC.add(chk);
			}
		}
		
		void repainting() {
			Theme.setTheme(this);
			getContentPane().setBackground(Theme.LIGHT);
			pnListNCC.setBackground(Theme.LIGHT);		
		}
	}
	
	private class ChonSanPham extends JDialog {
		private static final long serialVersionUID = 1L;
		ArrayList<String> spChon = new ArrayList<>();
		private JPanel pnListSP;
		private JCheckBox chkAll;
		
		ChonSanPham() {
			setIconImage(new ImageIcon("img/icon.png").getImage());
			setTitle("Chọn sản phẩm");
			setModalityType(ModalityType.APPLICATION_MODAL);
			setResizable(false);
			setBounds(100, 100, 400, 500);
			setLocationRelativeTo(null);
			getContentPane().setLayout(null);
			
			JLabel lblTitle = new JLabel("CHỌN SẢN PHẨM");
			lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
			lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
			lblTitle.setBounds(0, 10, 384, 40);
			getContentPane().add(lblTitle);
			
			chkAll = new JCheckBox("Chọn tất cả", true);
			chkAll.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (Component c: pnListSP.getComponents())
						((JCheckBox)c).setSelected(chkAll.isSelected());
				}
			});
			chkAll.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			chkAll.setBounds(20, 60, 344, 25);
			getContentPane().add(chkAll);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(20, 92, 344, 295);
			getContentPane().add(scrollPane);
			
			pnListSP = new JPanel();
			scrollPane.setViewportView(pnListSP);
			pnListSP.setLayout(new BoxLayout(pnListSP, BoxLayout.Y_AXIS));
			
			JButton btnOK = new JButton("OK");
			btnOK.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			        spChon = new ArrayList<>();
			        for (Component c : pnListSP.getComponents()) {
			            if (c instanceof JCheckBox) {
			                JCheckBox chk = (JCheckBox) c;
			                if (chk.isSelected()) {
			                    String sp = chk.getText();
			                    String maSP = sp.substring(0, sp.indexOf("-")).trim();
			                    spChon.add(maSP);
			                   
			                }
			            }
			        }
			        if (spChon.isEmpty()) {
			            ThongBao.baoLoi("Vui lòng chọn ít nhất 1 sản phẩm");
			        } else {
			            dispose();
			            btnTimKiem.doClick();
			        }
			    }
			});
			btnOK.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			btnOK.setBounds(61, 405, 100, 40);
			getContentPane().add(btnOK);
			
			JButton btnHuy = new JButton("Hủy bỏ");
			btnHuy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					for (Component c: pnListSP.getComponents()) {
						JCheckBox chk = (JCheckBox)c;
						if (chk.isSelected() != (spChon.indexOf(chk.getText()) != -1)) chk.doClick();
					}
				}
			});
			btnHuy.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			btnHuy.setBounds(223, 405, 100, 40);
			getContentPane().add(btnHuy);
			loadSP();
		}
		
		private void loadSP() {
			ArrayList<SanPham> sanPham = new SanPhamBUS().getAllSp();
			for (SanPham sp: sanPham) {
				spChon.add(sp.getMa());
				JCheckBox chk = new JCheckBox(sp + "", true);
				chk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						for (Component c: pnListSP.getComponents()) {
							if (((JCheckBox)c).isSelected() == false) {
								chkAll.setSelected(false);
								break;
							}
							chkAll.setSelected(true);
						}
					}
				});
				chk.setFont(new Font("Segoe UI", Font.PLAIN, 16));
				pnListSP.add(chk);
			}
		}
		
		void repainting() {
			Theme.setTheme(this);
			getContentPane().setBackground(Theme.LIGHT);
			pnListSP.setBackground(Theme.LIGHT);
		}
	}
	
	void repainting() {
		setBackground(Theme.LIGHT);
		
		nccForm.repainting();
		spForm.repainting();
	}
}
