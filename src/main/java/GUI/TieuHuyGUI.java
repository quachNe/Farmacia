package GUI;
import java.awt.Image;
import java.awt.Dimension;
import javax.swing.*;  
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import BUS.TieuHuyBUS;
import DTO.TieuHuy;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import com.toedter.calendar.JDateChooser;
import java.util.Date;

public class TieuHuyGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private static DefaultTableModel model = new DefaultTableModel(
			new Object[][] {},
			new String[] {"Mã SP","Lô SX", "Ngày lập", "Người lập", "Lý do" ,"Thiệt hại"}
			);
	private static JTable table;
	private JButton btnExcel;
	private static TieuHuyBUS tieuhuybus = new TieuHuyBUS();
	public static Map<String, Boolean> sanPhamBiKhoa = new HashMap<>();
	private String maDS;
	private JDateChooser dcNgayBatDau;
	private JDateChooser dcNgayKetThuc;
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
	public TieuHuyGUI(String maDS) {
		this.maDS=maDS;
		btnExcel = new JButton("Xuất file");
		loadTH(tieuhuybus.getTieuHuy());
		UIManager.put("Button.arc", 50);
		setMinimumSize(new Dimension(50, 100));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setPreferredSize(new Dimension(1280, 728));
		setLayout(null);
		
		JButton btnThem = new JButton("Thêm");
		btnThem.setHorizontalAlignment(SwingConstants.LEFT);
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LapPhieuTieuHuy(maDS).setVisible(true);
				
			}
		});	
		btnThem.setBounds(940, 23, 120, 30);
		btnThem.setForeground(new Color(255, 255, 255));
		btnThem.setBackground(new Color(11, 101, 140));
		btnThem.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnThem.setBorderPainted(false);
		btnThem.setFocusPainted(false);
		btnThem.setIcon(new ImageIcon(new ImageIcon("img/Icon/Add.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		add(btnThem);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 101, 1260, 730);
		add(scrollPane);
		
		table = new JTable(model);
		table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		table.getTableHeader().setBackground((Color.decode("#B9B9B9")));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(10);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(350);
		table.getColumnModel().getColumn(5).setPreferredWidth(30);
		setColorCell(table);
		JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.ITALIC, 18));
		scrollPane.setViewportView(table);
		
		btnExcel.setHorizontalAlignment(SwingConstants.LEFT);
		btnExcel.setIcon(new ImageIcon(new ImageIcon("img/Icon/Export.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		btnExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser saveFileDialog = new JFileChooser(System.getProperty("user.dir"));
				saveFileDialog.setDialogTitle("Chọn vị trí cần xuất");
				saveFileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Excel Workbook (.xlsx)", "xlsx"));
				saveFileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Excel 97-2003 Workbook (.xls)", "xls"));
				saveFileDialog.setAcceptAllFileFilterUsed(false);
				
				if (saveFileDialog.showSaveDialog(TieuHuyGUI.this) == JFileChooser.APPROVE_OPTION) {
				    String path = saveFileDialog.getSelectedFile().getAbsolutePath();
				    String desc = saveFileDialog.getFileFilter().getDescription();
				    if (!path.endsWith(".xlsx") && (!path.endsWith(".xls")))
				    	path += (desc.equals("Excel Workbook (.xlsx)")) ? ".xlsx" : ".xls";
				    if (tieuhuybus.xuatChiTietExcel(model, path)) ThongBao.thongBao("Xuất file Excel thành công");
				    else ThongBao.baoLoi("Xuất file Excel thất bại");
				}
			}
		});
		btnExcel.setForeground(new Color(255, 255, 255));
		btnExcel.setBackground(new Color(11, 101, 140));
		btnExcel.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnExcel.setFocusPainted(false);
		btnExcel.setBounds(1070, 23, 140, 30);
		add(btnExcel);
		
		JButton btnLamMoi = new JButton("");
		btnLamMoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadTH(tieuhuybus.getTieuHuy());
				dcNgayBatDau.setDate(null);
		        dcNgayKetThuc.setDate(null);
			}
		});
		btnLamMoi.setForeground(new Color(255, 255, 255));
		btnLamMoi.setBackground(new Color(11, 101, 140));
		btnLamMoi.setFont(new Font("Segoe UI", Font.BOLD, 18));
	
		btnLamMoi.setFocusPainted(false);
		btnLamMoi.setBounds(1220, 23, 50, 30);
		btnLamMoi.setIcon(new ImageIcon(new ImageIcon("img/Icon/Reset.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		add(btnLamMoi);
		
		dcNgayBatDau = new JDateChooser((Date) null, "yyyy-MM-dd");
		dcNgayBatDau.getCalendarButton().setFont(new Font("Segoe UI", Font.PLAIN, 18));
		dcNgayBatDau.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		dcNgayBatDau.setBounds(108, 23, 146, 30);
		add(dcNgayBatDau);
		
		dcNgayKetThuc = new JDateChooser((Date) null, "yyyy-MM-dd");
		dcNgayKetThuc.getCalendarButton().setFont(new Font("Segoe UI", Font.PLAIN, 18));
		dcNgayKetThuc.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		dcNgayKetThuc.setBounds(298, 23, 146, 30);
		add(dcNgayKetThuc);
		
		JLabel lblNewLabel = new JLabel("Thời gian:");
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 23, 88, 30);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("-");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		lblNewLabel_1.setBounds(264, 22, 24, 30);
		add(lblNewLabel_1);
		
		JButton btnTimKiem = new JButton("");
		btnTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		btnTimKiem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        Date ngayBatDau = dcNgayBatDau.getDate();
		        Date ngayKetThuc = dcNgayKetThuc.getDate();
		        Date ngayHienTai = new Date();

		        if (ngayBatDau == null || ngayKetThuc == null) {
		            StringBuilder errorMsg = new StringBuilder("Vui lòng chọn ");
		            if (ngayBatDau == null) {
		                errorMsg.append("ngày bắt đầu");
		            }
		            if (ngayKetThuc == null) {
		                errorMsg.append(ngayBatDau == null ? " và " : "").append("ngày kết thúc");
		            }
		            ThongBao.baoLoi(errorMsg.toString());
		            return;
		        }

		        if (ngayBatDau.after(ngayHienTai)) {
		            ThongBao.baoLoi("Ngày bắt đầu không được lớn hơn ngày hiện tại");
		            return;
		        }

		        if (ngayKetThuc.after(ngayHienTai)) {
		            ThongBao.baoLoi("Ngày kết thúc không được lớn hơn ngày hiện tại");
		            return;
		        }

		        if (ngayBatDau.after(ngayKetThuc)) {
		            ThongBao.baoLoi("Ngày bắt đầu không được lớn hơn ngày kết thúc");
		            return;
		        }

		        LocalDate localNgayBatDau = ngayBatDau.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		        LocalDate localNgayKetThuc = ngayKetThuc.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		        ArrayList<TieuHuy> tieuHuyList = tieuhuybus.timTieuHuy(localNgayBatDau, localNgayKetThuc);
		        loadTH(tieuHuyList);
		    }
		});

		btnTimKiem.setBounds(454, 23, 35, 30);
		btnTimKiem.setIcon(new ImageIcon(new ImageIcon("img/Icon/Search.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		add(btnTimKiem);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.GRAY);
		separator.setBounds(10, 75, 1260, 7);
		add(separator);
		
		repainting();
	}
	void repainting() {
		setBackground(Theme.LIGHT);
	}
	public static void loadTH(ArrayList<TieuHuy> listTH) {
		model.setRowCount(0);
		if (listTH == null || listTH.size() == 0) return;
		for (TieuHuy th: listTH) {
			Object[] data = new Object[] { th.getMaSanPham(), th.getLoSanXuat(), th.getNgayTieuHuy(), th.getNguoiLap(), th.getLyDo(), th.getThietHai() };
			model.addRow(data);
		}
	}
}