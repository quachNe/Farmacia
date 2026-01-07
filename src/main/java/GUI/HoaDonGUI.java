package GUI;

import javax.swing.*;


import DTO.*;
import BUS.*;
import com.toedter.calendar.*;
import javax.swing.event.CaretListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.CaretEvent;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.EmptyBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class HoaDonGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	JComboBox<KhachHang> cbbKhachHang;
	private JLabel lblNguoiLap;
	private JLabel lblNgayLap;
	private JLabel lblKhachHang;
	private JRadioButton rdThanhVien;
	private JRadioButton rdVangLai;
	private JButton btnTVMoi;
	private JLabel lblTitle;
	private JPanel panelSanPham;
	private JTextField txtSoLuong;
	private JLabel lblSoLuong;
	private JLabel lblDonGia;
	private JLabel lblSanPham;
	private JComboBox<SanPham> cbbSanPham;
	private JPanel panelButtonSP;
	private JButton btnThemSP;
	private JButton btnSuaSP;
	private JButton btnXoaSP;
	private JButton btnHuyBoSP;
	private JLabel lblTong;
	private JLabel lblDiemTichLuy;
	private JTable tableHoaDon;
	private JTable tableSanPham;
	private JButton btnLapDon;
	private JScrollPane scrollPaneHoaDon;
	private static String mavachtam;
	private DefaultTableModel modelSanPham = new DefaultTableModel(new Object[][] {},
			new String[] { "Mã SP", "Tên SP", "Lô SX", "Giá tiền", "Số Lượng" });
	private DefaultTableModel modelHoaDon = new DefaultTableModel(new Object[][] {},
			new String[] { "Mã", "Ngày Lập", "Người Lập ", "Khách Hàng", "Tổng tiền" });

	private JComboBox<String> comboBoxTimKiem;
	private JTextField txtTimKiem;
	private HoaDonBUS hdBUS = new HoaDonBUS();
	private JLabel lblLoSX;
	private JTextField txtNguoiLap;

	private ThemKhachHang themKH = new ThemKhachHang(this);

	private int diemTichLuy = 0;
	private long tongTien = 0;
	private JCheckBox chkSuDungDiem;
	private JTextField txtNgayLap;
	private JPanel panelLeft;
	private JPanel panelRight;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lblRightTitle;
	private JButton btnXuatExcel;
	private JPanel panelButtonHD;
	private JButton btnPDF;
	private ChiTietSanPham spHienTai = new ChiTietSanPham();
	private ArrayList<ChiTietHoaDon> spList = new ArrayList<>();
	private JLabel lblConLai;
	private JTextField txtLoSX;
	public JTextField txtSeri;
	private JLabel lblSeri;
	private JTextField txtGiaTien;
	private JButton btnSeri;
	private JButton btnMore;
	private JLabel lblTichDiemHienTai;
	int diemConLai = 0;
	private JTextField txtConLai;

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

	public HoaDonGUI(String maDS) {
		setBounds(0, 0, 1623, 760);
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new GridLayout(0, 2, 10, 0));

		JScrollPane scrollInfo = new JScrollPane();
		scrollInfo.setBorder(null);
		add(scrollInfo);
		panelLeft = new JPanel();
		panelLeft.setPreferredSize(new Dimension(500, 0));
		scrollInfo.setViewportView(panelLeft);
		panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));

		JPanel panelInfo = new JPanel();
		panelLeft.add(panelInfo);
		panelInfo.setOpaque(false);
		GridBagLayout gbl_panelInfo = new GridBagLayout();
		gbl_panelInfo.columnWidths = new int[] { 102, 133, 56, 33, 64, 0 };
		gbl_panelInfo.rowHeights = new int[] { 50, 35, 35, 35, 35 };
		gbl_panelInfo.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelInfo.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
		panelInfo.setLayout(gbl_panelInfo);

		rdVangLai = new JRadioButton("Vãng lai");
		rdVangLai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cbbKhachHang.setSelectedIndex(-1);
				cbbKhachHang.setEnabled(false);
				btnTVMoi.setEnabled(false);
				chkSuDungDiem.setEnabled(false);
				chkSuDungDiem.setSelected(false);
				lblTichDiemHienTai.setText("");
				tinhTongTien();
				lblDiemTichLuy.setText("Điểm Tích Lũy: " + diemTichLuy);
			}
		});

		rdVangLai.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		lblNguoiLap = new JLabel("Người lập:");
		lblNguoiLap.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNguoiLap = new GridBagConstraints();
		gbc_lblNguoiLap.fill = GridBagConstraints.BOTH;
		gbc_lblNguoiLap.insets = new Insets(0, 0, 5, 5);
		gbc_lblNguoiLap.gridx = 0;
		gbc_lblNguoiLap.gridy = 1;
		panelInfo.add(lblNguoiLap, gbc_lblNguoiLap);

		lblTitle = new JLabel("HÓA ĐƠN");
		lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.fill = GridBagConstraints.BOTH;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridwidth = 5;
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		panelInfo.add(lblTitle, gbc_lblTitle);

		txtNguoiLap = new JTextField(maDS);
		txtNguoiLap.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtNguoiLap.setEditable(false);
		GridBagConstraints gbc_txtNguoiLap = new GridBagConstraints();
		gbc_txtNguoiLap.fill = GridBagConstraints.BOTH;
		gbc_txtNguoiLap.gridwidth = 4;
		gbc_txtNguoiLap.insets = new Insets(0, 0, 5, 0);
		gbc_txtNguoiLap.gridx = 1;
		gbc_txtNguoiLap.gridy = 1;
		panelInfo.add(txtNguoiLap, gbc_txtNguoiLap);
		txtNguoiLap.setColumns(10);

		lblNgayLap = new JLabel("Ngày lập:");
		lblNgayLap.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblNgayLap.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNgayLap = new GridBagConstraints();
		gbc_lblNgayLap.fill = GridBagConstraints.BOTH;
		gbc_lblNgayLap.insets = new Insets(0, 0, 5, 5);
		gbc_lblNgayLap.gridx = 0;
		gbc_lblNgayLap.gridy = 2;
		panelInfo.add(lblNgayLap, gbc_lblNgayLap);

		txtNgayLap = new JTextField(LocalDate.now().toString());
		txtNgayLap.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtNgayLap.setEditable(false);
		txtNgayLap.setColumns(10);
		GridBagConstraints gbc_txtNgayLap = new GridBagConstraints();
		gbc_txtNgayLap.gridwidth = 4;
		gbc_txtNgayLap.fill = GridBagConstraints.BOTH;
		gbc_txtNgayLap.insets = new Insets(0, 0, 5, 0);
		gbc_txtNgayLap.gridx = 1;
		gbc_txtNgayLap.gridy = 2;
		panelInfo.add(txtNgayLap, gbc_txtNgayLap);

		lblKhachHang = new JLabel("Khách hàng:");
		lblKhachHang.setHorizontalAlignment(SwingConstants.LEFT);
		lblKhachHang.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblKhachHang = new GridBagConstraints();
		gbc_lblKhachHang.anchor = GridBagConstraints.WEST;
		gbc_lblKhachHang.fill = GridBagConstraints.VERTICAL;
		gbc_lblKhachHang.insets = new Insets(0, 0, 5, 5);
		gbc_lblKhachHang.gridx = 0;
		gbc_lblKhachHang.gridy = 3;
		panelInfo.add(lblKhachHang, gbc_lblKhachHang);

		rdThanhVien = new JRadioButton("Thành viên", true);
		rdThanhVien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnTVMoi.setEnabled(false);
				chkSuDungDiem.setEnabled(true);
				lblTichDiemHienTai.setText("Tích Điểm Hiện Tại : ");
//				chkSuDungDiem.setSelected(true);
				cbbKhachHang.setEnabled(rdThanhVien.isSelected());
				btnTVMoi.setEnabled(rdThanhVien.isSelected());
				if (cbbKhachHang.getSelectedIndex() > -1)
					chkSuDungDiem.setEnabled(true);
				
				tinhTongTien();
				lblDiemTichLuy.setText("Điểm Tích Lũy: " + diemTichLuy);
			}
		});

		rdThanhVien.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		buttonGroup.add(rdThanhVien);
		GridBagConstraints gbc_rdThanhVien = new GridBagConstraints();
		gbc_rdThanhVien.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdThanhVien.insets = new Insets(0, 0, 5, 5);
		gbc_rdThanhVien.gridx = 1;
		gbc_rdThanhVien.gridy = 3;
		panelInfo.add(rdThanhVien, gbc_rdThanhVien);
		buttonGroup.add(rdVangLai);
		GridBagConstraints gbc_rdVangLai = new GridBagConstraints();
		gbc_rdVangLai.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdVangLai.insets = new Insets(0, 0, 5, 0);
		gbc_rdVangLai.gridwidth = 3;
		gbc_rdVangLai.gridx = 2;
		gbc_rdVangLai.gridy = 3;
		panelInfo.add(rdVangLai, gbc_rdVangLai);

		cbbKhachHang = new JComboBox<>();
		cbbKhachHang.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        int selectedIndex = cbbKhachHang.getSelectedIndex();
		    	txtGiaTien.setText(" ");
				txtLoSX.setText(" ");
				txtConLai.setText("0");
				txtSeri.setText("");
				txtSoLuong.setText("");
		        
		        // Chỉ thực thi khi người dùng chọn mục hợp lệ (không phải -1)
		        if (selectedIndex >= 0) {
		            int makh = selectedIndex + 1;
		            ArrayList<KhachHang> khachHang = hdBUS.getKhachHang();
		            
		            for (KhachHang kh : khachHang) {
		                if (kh.getMa() == makh) {
		                    lblTichDiemHienTai.setText("Tích Điểm Hiện Tại: " + kh.getDiem());
		                    break; // Thoát khỏi vòng lặp sau khi tìm thấy khách hàng phù hợp
		                }
		            }
		        } else {
		            // Nếu chưa chọn mục nào, xóa thông tin hoặc hiển thị mặc định
		            lblTichDiemHienTai.setText("Tích Điểm Hiện Tại: ");
		        }
		    }
		});


		cbbKhachHang.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_cbbKhachHang = new GridBagConstraints();
		gbc_cbbKhachHang.fill = GridBagConstraints.BOTH;
		gbc_cbbKhachHang.insets = new Insets(0, 0, 0, 5);
		gbc_cbbKhachHang.gridwidth = 3;
		gbc_cbbKhachHang.gridx = 1;
		gbc_cbbKhachHang.gridy = 4;
		panelInfo.add(cbbKhachHang, gbc_cbbKhachHang);

		btnTVMoi = new JButton("Mới");
		btnTVMoi.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnTVMoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				themKH.setVisible(true);
				 loadKH();
			}
		});
		GridBagConstraints gbc_btnTVMoi = new GridBagConstraints();
		gbc_btnTVMoi.fill = GridBagConstraints.BOTH;
		gbc_btnTVMoi.gridx = 4;
		gbc_btnTVMoi.gridy = 4;
		panelInfo.add(btnTVMoi, gbc_btnTVMoi);

		panelSanPham = new JPanel();
		panelSanPham.setOpaque(false);
		panelLeft.add(panelSanPham);
		panelSanPham
				.setBorder(new CompoundBorder(
						new TitledBorder(
								new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255),
										new Color(160, 160, 160)),
								"Sản phẩm", TitledBorder.LEADING, TitledBorder.TOP,
								new Font("Segoe UI", Font.ITALIC, 16), new Color(0, 0, 0)),
						new EmptyBorder(10, 10, 10, 10)));
		GridBagLayout gbl_panelSanPham = new GridBagLayout();
		gbl_panelSanPham.columnWidths = new int[] { 80, 140, 80, 100, 50, 0, 130, 0, 50 };
		gbl_panelSanPham.rowHeights = new int[] { 35, 35, 0, 40, 0 };
		gbl_panelSanPham.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 1.0 };
		gbl_panelSanPham.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0 };
		panelSanPham.setLayout(gbl_panelSanPham);

		cbbSanPham = new JComboBox<>();
		cbbSanPham.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		cbbSanPham.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				txtGiaTien.setText(" ");
				txtLoSX.setText(" ");
			txtConLai.setText("0");
			txtSeri.setText("");
			txtSoLuong.setText("");
				if (e.getStateChange() == ItemEvent.SELECTED) {
				SanPham selectedSP = (SanPham) cbbSanPham.getSelectedItem();
//					ChiTietPhieuNhap Ctpn =  new ChiTietPhieuNhap();
//					PhieuNhapBUS pnBus= new PhieuNhapBUS();
//					ChiTietSanPham ctsp= new ChiTietSanPham();
//					ChiTietSanPhamBUS ctspBus= new ChiTietSanPhamBUS();
//					ctsp=ctspBus.timSanPhamTheoMaSP(selectedSP.getMa());
//					Ctpn=pnBus.timChiTietPhieuNhapTheoMaVach(ctsp.getMavach());

					if (selectedSP != null) {
						// Lấy lô sản xuất đầu tiên có số lượng lớn hơn 0
						String loSanXuat = layLoSanXuatConSoLuong(selectedSP.getMa());
						System.out.print(selectedSP.getMa());
					//	txtLoSX.setText(loSanXuat);

						if (loSanXuat != null) {
							spHienTai = hdBUS.getChiTietSP(selectedSP.getMa(), loSanXuat);
							if (spHienTai != null) {
//								txtGiaTien.setText(String.valueOf(Ctpn.getGiaNhap()*ctsp.getphantram()+Ctpn.getGiaNhap()));
//								txtLoSX.setText(loSanXuat);
//								txtConLai.setText(spHienTai.getSoLuong() + "");

							} else {
//								txtGiaTien.setText("Dữ liệu sản phẩm không hợp lệ");
//								txtLoSX.setText("Không có dữ liệu lô sản xuất");
//								txtConLai.setText("0");
							}
						} else {
//							txtGiaTien.setText(" hết hàng ");
//							txtLoSX.setText("Hết hàng ");
//							txtConLai.setText("0");
					}
					} else {
//						txtGiaTien.setText("Sản phẩm không hợp lệ");
//						txtLoSX.setText("Không có lô sản xuất");
//						txtConLai.setText("0");
					}
				}
			}
		});

		lblSanPham = new JLabel("Sản phẩm:");
		lblSanPham.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblSanPham = new GridBagConstraints();
		gbc_lblSanPham.fill = GridBagConstraints.BOTH;
		gbc_lblSanPham.insets = new Insets(0, 0, 5, 5);
		gbc_lblSanPham.gridx = 0;
		gbc_lblSanPham.gridy = 0;
		panelSanPham.add(lblSanPham, gbc_lblSanPham);
		GridBagConstraints gbc_cbbSanPham = new GridBagConstraints();
		gbc_cbbSanPham.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbbSanPham.insets = new Insets(0, 0, 5, 5);
		gbc_cbbSanPham.gridwidth = 7;
		gbc_cbbSanPham.gridx = 1;
		gbc_cbbSanPham.gridy = 0;
		panelSanPham.add(cbbSanPham, gbc_cbbSanPham);
		
		btnMore = new JButton("....");
		btnMore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			TimSanPham formTimSanPham = new TimSanPham();
				
			
				formTimSanPham.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						SanPham sanPhamDaChon = formTimSanPham.getSanPhamDaChon();
						if (sanPhamDaChon != null && sanPhamDaChon.isTrangThai()) {
							int oldIndex = -1; 
							
							for (int i = 0; i < cbbSanPham.getItemCount(); i++) {
								SanPham existingProduct = cbbSanPham.getItemAt(i);
								if (existingProduct.getMa().equals(sanPhamDaChon.getMa())) {
									
									oldIndex = i;
									cbbSanPham.removeItemAt(i);
									break;
								}
							}
							
							if (oldIndex != -1) {
							
								cbbSanPham.insertItemAt(sanPhamDaChon, oldIndex);
								cbbSanPham.setSelectedItem(sanPhamDaChon);
							}
							CustomComboBoxRenderer renderer = new CustomComboBoxRenderer();
							renderer.setSanPhamDaChon(sanPhamDaChon);
							cbbSanPham.setRenderer(renderer);
						
						} 
					}
				});
		
				formTimSanPham.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnMore = new GridBagConstraints();
		gbc_btnMore.insets = new Insets(0, 0, 5, 0);
		gbc_btnMore.gridx = 8;
		gbc_btnMore.gridy = 0;
		panelSanPham.add(btnMore, gbc_btnMore);

		lblLoSX = new JLabel("Lô sản xuất:");
		lblLoSX.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblLoSX = new GridBagConstraints();
		gbc_lblLoSX.anchor = GridBagConstraints.EAST;
		gbc_lblLoSX.fill = GridBagConstraints.VERTICAL;
		gbc_lblLoSX.insets = new Insets(0, 0, 5, 5);
		gbc_lblLoSX.gridx = 0;
		gbc_lblLoSX.gridy = 1;
		panelSanPham.add(lblLoSX, gbc_lblLoSX);

		txtLoSX = new JTextField();
		txtLoSX.setEnabled(false);
		GridBagConstraints gbc_txtLoSX = new GridBagConstraints();
		gbc_txtLoSX.insets = new Insets(0, 0, 5, 5);
		gbc_txtLoSX.fill = GridBagConstraints.BOTH;
		gbc_txtLoSX.gridx = 1;
		gbc_txtLoSX.gridy = 1;
		panelSanPham.add(txtLoSX, gbc_txtLoSX);
		txtLoSX.setColumns(10);

		lblDonGia = new JLabel("Đơn giá:");
		lblDonGia.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblDonGia = new GridBagConstraints();
		gbc_lblDonGia.anchor = GridBagConstraints.EAST;
		gbc_lblDonGia.fill = GridBagConstraints.VERTICAL;
		gbc_lblDonGia.insets = new Insets(0, 0, 5, 5);
		gbc_lblDonGia.gridx = 2;
		gbc_lblDonGia.gridy = 1;
		panelSanPham.add(lblDonGia, gbc_lblDonGia);

		txtGiaTien = new JTextField();
		txtGiaTien.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtGiaTien.setEnabled(false);
		txtGiaTien.setColumns(10);
		GridBagConstraints gbc_txtGiaTien = new GridBagConstraints();
		gbc_txtGiaTien.gridwidth = 3;
		gbc_txtGiaTien.insets = new Insets(0, 0, 5, 5);
		gbc_txtGiaTien.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtGiaTien.gridx = 3;
		gbc_txtGiaTien.gridy = 1;
		panelSanPham.add(txtGiaTien, gbc_txtGiaTien);
				
						lblConLai = new JLabel("Còn lại :");
						lblConLai.setFont(new Font("Segoe UI", Font.PLAIN, 16));
						GridBagConstraints gbc_lblConLai = new GridBagConstraints();
						gbc_lblConLai.anchor = GridBagConstraints.EAST;
						gbc_lblConLai.insets = new Insets(0, 0, 5, 5);
						gbc_lblConLai.gridx = 6;
						gbc_lblConLai.gridy = 1;
						panelSanPham.add(lblConLai, gbc_lblConLai);
		
		txtConLai = new JTextField();
		txtConLai.setEditable(false);
		GridBagConstraints gbc_txtConLai = new GridBagConstraints();
		gbc_txtConLai.gridwidth = 2;
		gbc_txtConLai.insets = new Insets(0, 0, 5, 5);
		gbc_txtConLai.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtConLai.gridx = 7;
		gbc_txtConLai.gridy = 1;
		panelSanPham.add(txtConLai, gbc_txtConLai);
		txtConLai.setColumns(10);

		lblSoLuong = new JLabel("Số Lượng:");
		GridBagConstraints gbc_lblSoLuong = new GridBagConstraints();
		gbc_lblSoLuong.insets = new Insets(0, 0, 5, 5);
		gbc_lblSoLuong.gridx = 0;
		gbc_lblSoLuong.gridy = 2;
		panelSanPham.add(lblSoLuong, gbc_lblSoLuong);
		lblSoLuong.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		txtSoLuong = new JTextField();
		GridBagConstraints gbc_txtSoLuong = new GridBagConstraints();
		gbc_txtSoLuong.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSoLuong.insets = new Insets(0, 0, 5, 5);
		gbc_txtSoLuong.gridx = 1;
		gbc_txtSoLuong.gridy = 2;
		panelSanPham.add(txtSoLuong, gbc_txtSoLuong);
		txtSoLuong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtSoLuong.setColumns(10);

		lblSeri = new JLabel("Seri:");
		lblSeri.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblSeri = new GridBagConstraints();
		gbc_lblSeri.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeri.anchor = GridBagConstraints.EAST;
		gbc_lblSeri.gridx = 2;
		gbc_lblSeri.gridy = 2;
		panelSanPham.add(lblSeri, gbc_lblSeri);

		txtSeri = new JTextField();
		txtSeri.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		txtSeri.setColumns(10);
		txtSeri.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyReleased(KeyEvent e) {  
		        // Sử dụng sự kiện keyReleased để xử lý chung
		        handleSeriInput(e);
		    }

		    @Override
		    public void keyPressed(KeyEvent e) {  
		        // Sử dụng sự kiện keyPressed để xử lý phím Enter
		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		            handleSeriInput(e);
		        }
		    }
		});

		GridBagConstraints gbc_txtSeri = new GridBagConstraints();
		gbc_txtSeri.gridwidth = 3;
		gbc_txtSeri.insets = new Insets(0, 0, 5, 5);
		gbc_txtSeri.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSeri.gridx = 3;
		gbc_txtSeri.gridy = 2;
		panelSanPham.add(txtSeri, gbc_txtSeri);
		
		btnSeri = new JButton("\r\n");
//		btnSeri.setIcon(new ImageIcon("C:\\Users\\Nguyen Tan Dat\\Documents\\Zalo Received Files\\QuanLyTiemThuoc\\QuanLyTiemThuoc\\img\\icon\\barcode.png"));
		btnSeri.setIcon(new ImageIcon(new ImageIcon("img/icon/barcode.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		btnSeri.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        SanPham selectedSP = (SanPham) cbbSanPham.getSelectedItem();

		        if (selectedSP == null) {
		            txtGiaTien.setText("");
		            txtConLai.setText("0");
		            return; // Thoát khỏi hàm nếu không có sản phẩm được chọn
		        }

		      
		        BarCode barcode = new BarCode();
		        barcode.ScanBarCode(txtSeri); // Giả sử mã vạch được quét và nhập vào txtSeri

		        
		        
		    }
		});
		

		btnSeri.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnSeri = new GridBagConstraints();
		gbc_btnSeri.anchor = GridBagConstraints.WEST;
		gbc_btnSeri.insets = new Insets(0, 0, 5, 5);
		gbc_btnSeri.gridx = 6;
		gbc_btnSeri.gridy = 2;
		panelSanPham.add(btnSeri, gbc_btnSeri);

		panelButtonSP = new JPanel();
		panelButtonSP.setOpaque(false);
		GridBagConstraints gbc_panelButtonSP = new GridBagConstraints();
		gbc_panelButtonSP.fill = GridBagConstraints.BOTH;
		gbc_panelButtonSP.insets = new Insets(0, 0, 5, 0);
		gbc_panelButtonSP.gridwidth = 9;
		gbc_panelButtonSP.gridx = 0;
		gbc_panelButtonSP.gridy = 3;
		panelSanPham.add(panelButtonSP, gbc_panelButtonSP);

		btnThemSP = new JButton("Thêm");
		
		btnThemSP.setPreferredSize(new Dimension(80, 35));
		btnThemSP.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		
//		btnThemSP.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if (txtGiaTien.getText().isEmpty() || txtSoLuong.getText().isEmpty()) {
//					ThongBao.baoLoi("Không được để trống thông tin");
//					return;
//				}
//				long giaBan; int soLuong;
//				try {
//					giaBan = Integer.parseInt(txtGiaTien.getText());
//					if (giaBan < 0) throw new Exception();
//				}
//				catch (Exception ex) {
//					ThongBao.baoLoi("Vui lòng nhập giá bán số lớn hơn 0");
//					return;
//				}
//				try {
//					soLuong = Integer.parseInt(txtSoLuong.getText());
//					if (soLuong < 0 || soLuong > spHienTai.getSoLuong()) throw new Exception();
//				}
//				catch (Exception ex) {
//					ThongBao.baoLoi("Vui lòng nhập số lượng lớn hơn 0, nhỏ hơn " + spHienTai.getSoLuong());
//					return;
//				}
//				
//				ChiTietHoaDon ct = new ChiTietHoaDon(spHienTai.getMaChiTiet(), giaBan, soLuong);
//				ct.setChiTiet(spHienTai);
//				String masp = ((SanPham) cbbSanPham.getSelectedItem()).getMa();
//				String tensp = ((SanPham) cbbSanPham.getSelectedItem()).getTen();
//				String losx =  cbbLoSX.getSelectedItem() + "";
//				
//				if (!kiemTraSoLuong(masp, losx, soLuong)) {
//					ThongBao.baoLoi("Tổng sản phẩm bán ra của sản phẩm vượt quá tồn kho (" + spHienTai.getSoLuong() + ")");
//					return;
//				}
//				int row = timSP(masp, losx);
//				if (row == -1) {
//					Object [] data = new Object [] {masp,tensp, losx, giaBan, soLuong};
//					modelSanPham.addRow(data);
//					spList.add(ct);
//				}					
//				else {
//					int soLuongCu = spList.get(row).getSoLuong();
//					modelSanPham.removeRow(row);
//					modelSanPham.insertRow(row, new Object [] {masp, tensp, losx, giaBan, soLuong + soLuongCu} );
//					spList.get(row).setSoLuong(soLuong + soLuongCu);
//				}
//				
//				tinhTongTien();
//				lblTong.setText("TỔNG: " + tongTien);
//				lblDiemTichLuy.setText("Điểm Tích Lũy: " + diemTichLuy);
//				
//				clear();
//			}
//		});
		btnThemSP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Kiểm tra nếu các trường thông tin trống
				if (txtGiaTien.getText().isEmpty() || txtSoLuong.getText().isEmpty()) {
					ThongBao.baoLoi("Không được để trống thông tin");
					return;
				}

				long giaBan;
				int soLuong;
				try {
					giaBan = Integer.parseInt(txtGiaTien.getText());
					if (giaBan < 0)
						throw new Exception();
				} catch (Exception ex) {
					//ThongBao.baoLoi("Vui lòng nhập giá bán là số lớn hơn 0");
					return;
				}

				try {
					soLuong = Integer.parseInt(txtSoLuong.getText());
					if(soLuong>0 && soLuong<=Integer.parseInt(txtConLai.getText())) {
						
						
					}
					
					else {
						ThongBao.baoLoi("Vui lòng nhập số lượng hợp lệ");
						txtSoLuong.setText("");
						return;
					}
					
				} catch (Exception ex) {
					ThongBao.baoLoi("Vui lòng nhập số lượng hợp lệ");
					return;
				}
				SanPham sanPhamChon = (SanPham) cbbSanPham.getSelectedItem();
				if (sanPhamChon == null) {
				    ThongBao.baoLoi("Vui lòng chọn sản phẩm từ danh sách.");
				    return;
				}

				String masp = sanPhamChon.getMa();
				String tensp = sanPhamChon.getTen();

				if (spHienTai == null || spHienTai.getMavach() == null) {
				    ThongBao.baoLoi("Thông tin sản phẩm hiện tại không hợp lệ.");
				    return;
				}
			
				
				System.out.println(mavachtam);
				ChiTietHoaDon ct = new ChiTietHoaDon(mavachtam, giaBan, soLuong);
				
				
				
				
				ct.setChiTiet(spHienTai);
				String losx = txtLoSX.getText().trim();
				System.out.println(losx);
				ct.getChiTiet().setLoSanXuat(losx);

				// Kiểm tra nếu tổng số lượng bán vượt quá tồn kho
//				if (!kiemTraSoLuong(masp, losx, soLuong)) {
//			
//					return;
//				}

				int row = timSP(masp, losx);

				// Nếu sản phẩm chưa có trong danh sách, thêm mới
				if (row == -1) {
					Object[] data = new Object[] { masp, tensp, losx, giaBan, soLuong };
					modelSanPham.addRow(data);
					spList.add(ct);
					

				} else {
					int soLuongCu = spList.get(row).getSoLuong();
				//	System.out.println(soLuongCu);
					modelSanPham.removeRow(row);
					modelSanPham.insertRow(row, new Object[] { masp, tensp, losx, giaBan, soLuong + soLuongCu });
					spList.get(row).setSoLuong(soLuong + soLuongCu);
				}

				// Cập nhật số lượng sản phẩm hiện tại
				spHienTai.setSoLuong(spHienTai.getSoLuong() - soLuong);

				// Tính lại tổng tiền và cập nhật các nhãn
				tinhTongTien();
				lblTong.setText("TỔNG: " + tongTien);
			lblDiemTichLuy.setText("Điểm Tích Lũy: " + diemTichLuy);
				

				// Kiểm tra lại lô sản xuất sau khi cập nhật số lượng sản phẩm
//				checkSpTrongLo();
			}
		});


		panelButtonSP.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
		panelButtonSP.add(btnThemSP);

		btnSuaSP = new JButton("Sửa");
		btnSuaSP.setPreferredSize(new Dimension(80, 35));
		btnSuaSP.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnSuaSP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tableSanPham.getSelectedRow();

				if (selectedRow != -1) {
					String tensp = ((SanPham) cbbSanPham.getSelectedItem()).getTen();
					String losx = txtLoSX.getText() + "";

					long giaBan;
					int soLuong;
					try {
						giaBan = Integer.parseInt(txtGiaTien.getText());
						if (giaBan < 0)
							throw new Exception();
					} catch (Exception ex) {
						//ThongBao.baoLoi("Vui lòng nhập giá bán số lớn hơn 0");
						return;
					}
					try {
						soLuong = Integer.parseInt(txtSoLuong.getText());
						if (soLuong < 0 || soLuong > Integer.parseInt(txtConLai.getText()))
							throw new Exception();
					} catch (Exception ex) {
						ThongBao.baoLoi("Vui lòng nhập số lượng lớn hơn 0, nhỏ hơn " + Integer.parseInt(txtConLai.getText()));
						return;
					}

					// cap nhat lai cac bang dc chon
//					tableSanPham.setValueAt(masp, selectedRow, 0);
					tableSanPham.setValueAt(tensp, selectedRow, 1);
					tableSanPham.setValueAt(losx, selectedRow, 2);
					tableSanPham.setValueAt(giaBan, selectedRow, 3);
					tableSanPham.setValueAt(soLuong, selectedRow, 4);

					spList.remove(selectedRow);
					ChiTietHoaDon ct = new ChiTietHoaDon(spHienTai.getMavach(), giaBan, soLuong);
					ct.setChiTiet(spHienTai);
					spList.add(ct);

					clear();
					tinhTongTien();
					lblTong.setText("TỔNG: " + tongTien);
					lblDiemTichLuy.setText("Điểm Tích Lũy: " + diemTichLuy);
				} else {
					ThongBao.baoLoi("Vui lòng chọn một dòng sản phẩm để sửa");
				}
			}
		});
		panelButtonSP.add(btnSuaSP);

		btnXoaSP = new JButton("Xóa");
		btnXoaSP.setPreferredSize(new Dimension(80, 35));
		btnXoaSP.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnXoaSP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// lay chi so cua hang
				int selectedRow = tableSanPham.getSelectedRow();
				if (selectedRow != -1) {
					modelSanPham.removeRow(selectedRow);
					tinhTongTien();
					lblTong.setText("TỔNG: " + tongTien);
					lblDiemTichLuy.setText("Điểm Tích Lũy: " + diemTichLuy);
					spList.remove(selectedRow);
				} else {
					ThongBao.baoLoi("Vui lòng chọn dòng sản phẩm cần xóa");
				}

			}
		});
		panelButtonSP.add(btnXoaSP);

		btnHuyBoSP = new JButton("Hủy Bỏ");
		btnHuyBoSP.setPreferredSize(new Dimension(100, 35));
		btnHuyBoSP.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnHuyBoSP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modelSanPham.setRowCount(0);
				clear();
				loadKH();
				tinhTongTien();
				lblTong.setText("TỔNG: " + tongTien);
				lblDiemTichLuy.setText("Điểm Tích Lũy: " + diemTichLuy);
				modelSanPham.setRowCount(0);
			}
		});
		
		panelButtonSP.add(btnHuyBoSP);
		tableSanPham = new JTable();
		setColorCell(tableSanPham);
		  tableSanPham.getTableHeader().setBackground(Color.decode("#B9B9B9"));
			tableSanPham.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN | Font.ITALIC, 18));
			tableSanPham.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		// Thêm ActionListener cho bảng
		tableSanPham.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				// Kiểm tra xem có phải sự kiện chọn hàng không
				if (!event.getValueIsAdjusting()) {
					int selectedRow = tableSanPham.getSelectedRow();
					if (selectedRow != -1) {
						// Lấy dữ liệu từ hàng được chọn
						String masp = tableSanPham.getValueAt(selectedRow, 0).toString();
						String tensp = tableSanPham.getValueAt(selectedRow, 1).toString();
						String losx = tableSanPham.getValueAt(selectedRow, 2).toString();

						Long giaBan = Long.parseLong(tableSanPham.getValueAt(selectedRow, 3).toString());
						int soLuong = Integer.parseInt(tableSanPham.getValueAt(selectedRow, 4).toString());

						// Hiển thị dữ liệu trong các trường nhập liệu để chỉnh sửa
						txtLoSX.setText(losx);
						cbbSanPham.setSelectedItem(tensp);
						txtGiaTien.setText(String.valueOf(giaBan));
						txtSoLuong.setText(String.valueOf(soLuong));
					}
				}
			}
		});
//		tableSanPham.getColumnModel().getColumn(0).setPreferredWidth(50);
//		tableSanPham.getColumnModel().getColumn(1).setPreferredWidth(250);
//		tableSanPham.getColumnModel().getColumn(2).setPreferredWidth(40);
//		tableSanPham.getColumnModel().getColumn(3).setPreferredWidth(50);
//		tableSanPham.getColumnModel().getColumn(4).setPreferredWidth(10);
		tableSanPham.setModel(modelSanPham);

		JScrollPane scrollPane = new JScrollPane(tableSanPham);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 9;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 4;
		panelSanPham.add(scrollPane, gbc_scrollPane);

		JPanel panelTotal = new JPanel();
		panelTotal.setOpaque(false);
		panelLeft.add(panelTotal);
		GridBagLayout gbl_panelTotal = new GridBagLayout();
		gbl_panelTotal.columnWidths = new int[] { 348, 53, 150, 0 };
		gbl_panelTotal.rowHeights = new int[] { 25, 30, 35, 0 };
		gbl_panelTotal.columnWeights = new double[] { 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelTotal.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelTotal.setLayout(gbl_panelTotal);

		chkSuDungDiem = new JCheckBox("Sử dụng điểm", false);
		// Biến tích điểm còn lại
	

		chkSuDungDiem.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		        if (chkSuDungDiem.isSelected() && cbbKhachHang.getSelectedIndex() > -1) {
		            // Lấy số điểm của khách hàng
		            long diemKhachHang = ((KhachHang) cbbKhachHang.getSelectedItem()).getDiem();
		            // Tính tổng tiền sau khi trừ điểm
		            long tongTienSauKhiTruDiem = tongTien - diemKhachHang;
		            
		            if (tongTienSauKhiTruDiem < 0) {
		                lblTong.setText("TỔNG: 0"); // Hiển thị tổng tiền bằng 0 nếu âm
		                diemConLai = (int)Math.abs(tongTienSauKhiTruDiem); // Số điểm dư ra
		            } else {
		                lblTong.setText("TỔNG: " + tongTienSauKhiTruDiem); // Hiển thị tổng tiền sau khi trừ
		                diemConLai = 0; // Không có điểm dư nếu tổng tiền không âm
		            }
		        } else {
		            lblTong.setText("TỔNG: " + tongTien); // Hiển thị tổng tiền ban đầu nếu không dùng điểm
		            diemConLai = 0; // Không có điểm dư nếu không dùng điểm
		        }

		        // In ra số điểm còn lại để kiểm tra (hoặc sử dụng diemConLai ở phần khác trong chương trình)
		        System.out.println("Điểm còn lại: " + diemConLai);
		    }
		});


		chkSuDungDiem.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_chkSuDungDiem = new GridBagConstraints();
		gbc_chkSuDungDiem.anchor = GridBagConstraints.WEST;
		gbc_chkSuDungDiem.fill = GridBagConstraints.VERTICAL;
		gbc_chkSuDungDiem.insets = new Insets(0, 0, 5, 5);
		gbc_chkSuDungDiem.gridx = 0;
		gbc_chkSuDungDiem.gridy = 0;
		panelTotal.add(chkSuDungDiem, gbc_chkSuDungDiem);

		lblTong = new JLabel("TỔNG: " + tongTien);
		GridBagConstraints gbc_lblTong = new GridBagConstraints();
		gbc_lblTong.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblTong.insets = new Insets(0, 0, 5, 10);
		gbc_lblTong.gridx = 2;
		gbc_lblTong.gridy = 0;
		panelTotal.add(lblTong, gbc_lblTong);
		lblTong.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
		lblTichDiemHienTai = new JLabel("");
	
		lblTichDiemHienTai.setFont(new Font("Segoe UI", Font.BOLD, 16));
		GridBagConstraints gbc_lblTichDiemHienTai = new GridBagConstraints();
		gbc_lblTichDiemHienTai.anchor = GridBagConstraints.WEST;
		gbc_lblTichDiemHienTai.insets = new Insets(0, 0, 5, 5);
		gbc_lblTichDiemHienTai.gridx = 0;
		gbc_lblTichDiemHienTai.gridy = 1;
		panelTotal.add(lblTichDiemHienTai, gbc_lblTichDiemHienTai);

		lblDiemTichLuy = new JLabel("Điểm Tích Lũy  : 0");
		GridBagConstraints gbc_lblDiemTichLuy = new GridBagConstraints();
		gbc_lblDiemTichLuy.anchor = GridBagConstraints.EAST;
		gbc_lblDiemTichLuy.fill = GridBagConstraints.VERTICAL;
		gbc_lblDiemTichLuy.insets = new Insets(0, 0, 5, 10);
		gbc_lblDiemTichLuy.gridx = 2;
		gbc_lblDiemTichLuy.gridy = 1;
		panelTotal.add(lblDiemTichLuy, gbc_lblDiemTichLuy);
		lblDiemTichLuy.setFont(new Font("Segoe UI", Font.BOLD, 16));
		panelButtonHD = new JPanel();
		panelButtonHD.setOpaque(false);
		panelButtonHD.setLayout(null);
		GridBagConstraints gbc_panelButtonHD = new GridBagConstraints();
		gbc_panelButtonHD.gridwidth = 3;
		gbc_panelButtonHD.fill = GridBagConstraints.BOTH;
		gbc_panelButtonHD.gridx = 0;
		gbc_panelButtonHD.gridy = 2;
		panelTotal.add(panelButtonHD, gbc_panelButtonHD);

		btnLapDon = new JButton("Lập");
//		btnLapDon.setIcon(new ImageIcon("C:\\Users\\Nguyen Tan Dat\\Documents\\Zalo Received Files\\QuanLyTiemThuoc\\QuanLyTiemThuoc\\img\\icon\\Save.png"));
		btnLapDon.setIcon(new ImageIcon(new ImageIcon("img/icon/Save.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		btnLapDon.setBounds(220, 0, 100, 35);
		btnLapDon.setBackground(new Color(11, 101, 140));;
		btnLapDon.setForeground(new Color(255, 255, 255));
		
		btnLapDon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (rdThanhVien.isSelected() && cbbKhachHang.getSelectedIndex() == -1) {
					ThongBao.baoLoi("Vui lòng chọn khách hàng");
					return;
				}
				
				if (modelSanPham.getRowCount() == 0) {
					ThongBao.baoLoi("Hóa đơn chưa có sản phẩm");
					return;
				}
				if (ThongBao.cauHoiNghiemTrong(
						"Bạn có muốn lưu hoá đơn? Một khi đồng ý thì hệ thống không thể khôi phục.") == JOptionPane.YES_OPTION) {
					HoaDon hd;
					KhachHang kh = null;
					String text = lblTong.getText();
					String[] parts = text.split(":");
					long tong = Long.parseLong(parts[1].trim());
					if (rdThanhVien.isSelected()) {
						kh = (KhachHang) cbbKhachHang.getSelectedItem();
						hd = new HoaDon("", LocalDate.parse(txtNgayLap.getText()), maDS, 1, null, diemTichLuy,
								tong);

						hd.setThongTinKhach(kh);
					} else {
						hd = new HoaDon("", LocalDate.parse(txtNgayLap.getText()), maDS, -1, null, diemTichLuy,
								tong);
						hd.setThongTinKhach(kh);
					}
					
					System.out.println("tu hoa don gui 1005"+spList.get(0).getMavach());
					
					
					
					if (hdBUS.addHoaDon(hd, spList, kh, chkSuDungDiem.isSelected(),diemConLai)) {
						ThongBao.thongBao("Lập hóa đơn thành công");
						DefaultTableModel model = (DefaultTableModel) tableSanPham.getModel();
						JFileChooser saveFileDialog = new JFileChooser(System.getProperty("user.dir"));
						saveFileDialog.setDialogTitle("Chọn vị trí cần xuất");
						saveFileDialog.addChoosableFileFilter(
								new FileNameExtensionFilter("Portable Document Format (.pdf)", "pdf"));
						saveFileDialog.setAcceptAllFileFilterUsed(false);

						if (saveFileDialog.showSaveDialog(HoaDonGUI.this) == JFileChooser.APPROVE_OPTION) {
							String path = saveFileDialog.getSelectedFile().getAbsolutePath();
							if (!path.endsWith(".pdf"))
								path += ".pdf";
							System.out.println(spList);  // Kiểm tra danh sách chi tiết hóa đơn
							for (ChiTietHoaDon chiTiet : spList) {
							    System.out.println(chiTiet.getChiTiet());  // Kiểm tra chi tiết sản phẩm
							    System.out.println(chiTiet.getChiTiet().getSanPham().getTen());  // Kiểm tra tên sản phẩm
							}

							if (hdBUS.xuatPDF(hd, spList,kh, path)) {
								ThongBao.thongBao("Xuất hóa đơn ra PDF thành công");
								try {
									Desktop.getDesktop().open(new java.io.File(path));
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							} else {
								ThongBao.baoLoi("Xuất hóa đơn ra PDF thất bại");
							}
						}
						clear();
						lblTong.setText("TỔNG: " + 0);
						lblDiemTichLuy.setText("Điểm Tích Lũy: " + 0);
						model.setRowCount(0);
						spList.removeAll(spList);
						loadHoaDon(hdBUS.getHoaDon());
					}
				}
			}
		});

		btnLapDon.setPreferredSize(new Dimension(80, 35));
		panelButtonHD.add(btnLapDon);
		btnLapDon.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		btnPDF = new JButton("Xuất PDF");
//		btnPDF.setIcon(new ImageIcon("C:\\Users\\Nguyen Tan Dat\\Documents\\Zalo Received Files\\QuanLyTiemThuoc\\QuanLyTiemThuoc\\img\\icon\\Export.png"));
		btnPDF.setIcon(new ImageIcon(new ImageIcon("img/icon/Export.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		btnPDF.setBounds(335, 0, 140, 35);
		btnPDF.setBackground(new Color(11, 101, 140));;
		btnPDF.setForeground(new Color(255, 255, 255));
		btnPDF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tableHoaDon.getSelectedRow() == -1) {
					ThongBao.baoLoi("Vui lòng chọn 1 hóa đơn");
					return;
				} else {
					String maHD = modelHoaDon.getValueAt(tableHoaDon.getSelectedRow(), 0).toString();
					JFileChooser saveFileDialog = new JFileChooser(System.getProperty("user.dir"));
					saveFileDialog.setDialogTitle("Chọn vị trí cần xuất");
					saveFileDialog.addChoosableFileFilter(
							new FileNameExtensionFilter("Portable Document Format (.pdf)", "pdf"));
					saveFileDialog.setAcceptAllFileFilterUsed(false);

					if (saveFileDialog.showSaveDialog(HoaDonGUI.this) == JFileChooser.APPROVE_OPTION) {
						String path = saveFileDialog.getSelectedFile().getAbsolutePath();
						if (!path.endsWith(".pdf"))
							path += ".pdf";
						KhachHang kh = (KhachHang) cbbKhachHang.getSelectedItem();
						if (hdBUS.xuatPDF(hdBUS.getHoaDon(maHD), spList,kh, path)) {
							ThongBao.thongBao("Xuất hóa đơn ra PDF thành công");
							try {
								Desktop.getDesktop().open(new java.io.File(path));
							} catch (Exception ex) {
							}
						} else
							ThongBao.baoLoi("Xuất hóa đơn ra PDF thất bại");
					}
				}
			}
		});
		btnPDF.setPreferredSize(new Dimension(120, 35));
		btnPDF.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		panelButtonHD.add(btnPDF);

		JButton btnHuyDon = new JButton("Hủy");
//		btnHuyDon.setIcon(new ImageIcon("C:\\Users\\Nguyen Tan Dat\\Documents\\Zalo Received Files\\QuanLyTiemThuoc\\QuanLyTiemThuoc\\img\\icon\\Cancel.png"));
		btnHuyDon.setIcon(new ImageIcon(new ImageIcon("img/icon/Cancel.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		btnHuyDon.setBounds(495, 0, 100, 35);
		btnHuyDon.setPreferredSize(new Dimension(80, 35));
		btnHuyDon.setBackground(new Color(11, 101, 140));;
		btnHuyDon.setForeground(new Color(255, 255, 255));
		panelButtonHD.add(btnHuyDon);
		btnHuyDon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modelSanPham.setRowCount(0);
				spList.clear();
				rdThanhVien.setSelected(true);
				chkSuDungDiem.setEnabled(false);
				lblTichDiemHienTai.setText("");
				clear();
				loadKH();
			}
			
		});
		btnHuyDon.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		panelRight = new JPanel();
		panelRight.setOpaque(false);
		add(panelRight);
		GridBagLayout gbl_panelRight = new GridBagLayout();
		gbl_panelRight.columnWidths = new int[] { 80, 0, 120 };
		gbl_panelRight.rowHeights = new int[] { 60, 40, 0, 40 };
		gbl_panelRight.columnWeights = new double[] { 0.0, 1.0, 0.0 };
		gbl_panelRight.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0 };
		panelRight.setLayout(gbl_panelRight);
		JLabel lblTimKiem = new JLabel("Tìm kiếm:");
		GridBagConstraints gbc_lblTimKiem = new GridBagConstraints();
		gbc_lblTimKiem.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTimKiem.insets = new Insets(0, 0, 5, 5);
		gbc_lblTimKiem.gridx = 0;
		gbc_lblTimKiem.gridy = 1;
		panelRight.add(lblTimKiem, gbc_lblTimKiem);
		lblTimKiem.setHorizontalAlignment(SwingConstants.LEFT);
		lblTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		txtTimKiem = new JTextField();
		txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_txtTimKiem = new GridBagConstraints();
		gbc_txtTimKiem.fill = GridBagConstraints.BOTH;
		gbc_txtTimKiem.insets = new Insets(0, 0, 5, 5);
		gbc_txtTimKiem.gridx = 1;
		gbc_txtTimKiem.gridy = 1;
		panelRight.add(txtTimKiem, gbc_txtTimKiem);
		txtTimKiem.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				String key = txtTimKiem.getText().toString();
				String option = comboBoxTimKiem.getSelectedItem().toString();

				if (key != "") {
					ArrayList<HoaDon> hd = hdBUS.searchHoaDon(option, key);
					loadHoaDon(hd);
				}
			}
		});
		txtTimKiem.setColumns(10);

		comboBoxTimKiem = new JComboBox<>();
		comboBoxTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_comboBoxTimKiem = new GridBagConstraints();
		gbc_comboBoxTimKiem.fill = GridBagConstraints.BOTH;
		gbc_comboBoxTimKiem.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxTimKiem.gridx = 2;
		gbc_comboBoxTimKiem.gridy = 1;
		panelRight.add(comboBoxTimKiem, gbc_comboBoxTimKiem);
		comboBoxTimKiem.setModel(new DefaultComboBoxModel<>(new String[] { "Mã hóa đơn", "Ngày", "Người Lập" }));

		lblRightTitle = new JLabel("DANH SÁCH HÓA ĐƠN");
		lblRightTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
		GridBagConstraints gbc_lblRightTitle = new GridBagConstraints();
		gbc_lblRightTitle.gridwidth = 3;
		gbc_lblRightTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblRightTitle.gridx = 0;
		gbc_lblRightTitle.gridy = 0;
		panelRight.add(lblRightTitle, gbc_lblRightTitle);

		btnXuatExcel = new JButton("Xuất Excel");
//		btnXuatExcel.setIcon(new ImageIcon("C:\\Users\\Nguyen Tan Dat\\Documents\\Zalo Received Files\\QuanLyTiemThuoc\\QuanLyTiemThuoc\\img\\icon\\Export.png"));
		btnXuatExcel.setIcon(new ImageIcon(new ImageIcon("img/icon/Export.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		btnXuatExcel.setBackground(new Color(11, 101, 140));;
		btnXuatExcel.setForeground(new Color(255, 255, 255));
		btnXuatExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser saveFileDialog = new JFileChooser(System.getProperty("user.dir"));
				saveFileDialog.setDialogTitle("Chọn vị trí cần xuất");
				saveFileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Excel Workbook (.xlsx)", "xlsx"));
				saveFileDialog
						.addChoosableFileFilter(new FileNameExtensionFilter("Excel 97-2003 Workbook (.xls)", "xls"));
				saveFileDialog.setAcceptAllFileFilterUsed(false);

				if (saveFileDialog.showSaveDialog(HoaDonGUI.this) == JFileChooser.APPROVE_OPTION) {
					String path = saveFileDialog.getSelectedFile().getAbsolutePath();
					String desc = saveFileDialog.getFileFilter().getDescription();
					if (!path.endsWith(".xlsx") && (!path.endsWith(".xls")))
						path += (desc.equals("Excel Workbook (.xlsx)")) ? ".xlsx" : ".xls";
					if (hdBUS.xuatChiTietExcel(modelHoaDon, path))
						ThongBao.thongBao("Xuất file Excel thành công");
					else
						ThongBao.baoLoi("Xuất file Excel thất bại");
				}
			}
		});
		btnXuatExcel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnXuatExcel = new GridBagConstraints();
		gbc_btnXuatExcel.gridwidth = 3;
		gbc_btnXuatExcel.fill = GridBagConstraints.VERTICAL;
		gbc_btnXuatExcel.gridx = 0;
		gbc_btnXuatExcel.gridy = 3;
		panelRight.add(btnXuatExcel, gbc_btnXuatExcel);

		scrollPaneHoaDon = new JScrollPane();
		GridBagConstraints gbc_scrollPaneHoaDon = new GridBagConstraints();
		gbc_scrollPaneHoaDon.insets = new Insets(0, 0, 20, 0);
		gbc_scrollPaneHoaDon.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneHoaDon.gridwidth = 3;
		gbc_scrollPaneHoaDon.gridx = 0;
		gbc_scrollPaneHoaDon.gridy = 2;
		panelRight.add(scrollPaneHoaDon, gbc_scrollPaneHoaDon);

		tableHoaDon = new JTable();
		setColorCell(tableHoaDon);
		  tableHoaDon.getTableHeader().setBackground(Color.decode("#B9B9B9"));
			tableHoaDon.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN | Font.ITALIC, 18));
		tableHoaDon.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tableHoaDon.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        spList = hdBUS.getChiTietHD(modelHoaDon.getValueAt(tableHoaDon.getSelectedRow(), 0) + "");
		        
		        HoaDon hd = hdBUS.getHoaDon(modelHoaDon.getValueAt(tableHoaDon.getSelectedRow(), 0) + "");

		        cbbKhachHang.setSelectedItem(hd.getThongTinKhach());
		     
		        lblTong.setText("TỔNG: " + hd.getTongTien());
		        
		   		 
		
		        lblDiemTichLuy.setText("Điểm Tích Lũy: " + hd.getThongTinKhach().getDiem()+hd.getDiemTichLuy());
		        
		        modelSanPham.setRowCount(0);
		        selectedMode(true, hd.getKhachHang());

		        PhieuNhapBUS pnBus = new PhieuNhapBUS(); // Khởi tạo PhieuNhapBUS chỉ một lần

		        for (ChiTietHoaDon ct : spList) {
		           
		            	int giaBan = (int)ct.getgiaban(); // Get the percentage markup
		 
		            	System.out.println(" gia ban "+giaBan);

		            	// Calculate total selling price based on the quantity
		            	int giaTien = (int) (giaBan*ct.getSoLuong()); // Total selling price
		            	lblTong.setText("TỔNG: " + hd.getTongTien());
						lblDiemTichLuy.setText("Điểm Tích Lũy: " + hd.getDiemTichLuy());
						DecimalFormat formatter = new DecimalFormat("#,###");
		            	String formattedGiaBan = formatter.format(giaBan);
		                // Thêm dòng vào model
		                modelSanPham.addRow(new Object[] {
		                		ct.getChiTiet().getSanPham().getMa(), ct.getChiTiet().getSanPham().getTen(),
		                		ct.getChiTiet().getLoSanXuat(),giaBan,ct.getSoLuong()
		                });
		            
		            }
		    }
		    
		});
	
		tableHoaDon.setModel(modelHoaDon);
		scrollPaneHoaDon.setViewportView(tableHoaDon);
		// clear();
		loadHoaDon(hdBUS.getHoaDon());
		loadSP();
		loadKH();
		repainting();
	}

	public String layLoSanXuatTiepTheo(String loSanXuatHienTai) {
		if (loSanXuatHienTai == null || loSanXuatHienTai.isEmpty()) {
			return "Lô sản xuất không hợp lệ!";
		}

		String prefix = loSanXuatHienTai.substring(0, 2); // "LO"
		String numberPart = loSanXuatHienTai.substring(2); // "001"

		try {
			// Tăng giá trị số trong lô sản xuất
			int nextNumber = Integer.parseInt(numberPart) + 1;

			// Định dạng số với 3 chữ số
			String nextLoSanXuat = String.format("%03d", nextNumber);

			// Kiểm tra xem lô sản xuất tiếp theo có tồn tại trong database
			boolean loSanXuatTonTai = kiemTraLoSanXuat(prefix + nextLoSanXuat);

			if (loSanXuatTonTai) {
				return prefix + nextLoSanXuat;
			} else {
				return null; // Trả về null nếu lô tiếp theo không tồn tại
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "Lô sản xuất không hợp lệ!";
		}
	}

	public String layLoSanXuatConSoLuong(String masp) {
		ChiTietSanPhamBUS ctspBus = new ChiTietSanPhamBUS();
		ArrayList<ChiTietSanPham> arrCtsp = (ArrayList<ChiTietSanPham>) ctspBus.hienThiTatCaSanPham();

		// Duyệt qua danh sách chi tiết sản phẩm để tìm lô sản xuất đầu tiên có số lượng
		// lớn hơn 0
		for (ChiTietSanPham ctsp : arrCtsp) {
			if (ctsp.getMaSanPham() != null && masp != null && ctsp.getMaSanPham().equals(masp)
					&& ctsp.getSoLuong() > 0) {

				return ctsp.getLoSanXuat(); // Trả về lô sản xuất đầu tiên có số lượng lớn hơn 0
			}
		}

		return null; // Trả về null nếu không có lô nào có số lượng lớn hơn 0
	}

	private boolean kiemTraLoSanXuat(String loSanXuat) {
		// Tạo một instance của ChiTietSanPhamBUS để tương tác với dữ liệu
		ChiTietSanPhamBUS ctspBus = new ChiTietSanPhamBUS();

		// Lấy danh sách tất cả các chi tiết sản phẩm
		ArrayList<ChiTietSanPham> danhSachSP = ctspBus.hienThiTatCaSanPham();

		// Kiểm tra nếu danh sách sản phẩm là null hoặc rỗng
		if (danhSachSP == null || danhSachSP.isEmpty()) {
			return false;
		}

		// Duyệt qua danh sách sản phẩm và kiểm tra lô sản xuất
		for (ChiTietSanPham sp : danhSachSP) {
			// Kiểm tra nếu lô sản xuất không null và trùng khớp với lô sản xuất cần tìm
			if (sp.getLoSanXuat() != null && sp.getLoSanXuat().equals(loSanXuat)) {
				return true;
			}
		}

		// Nếu không tìm thấy lô sản xuất nào khớp, trả về false
		return false;
	}

//	public void checkSpTrongLo() {
//		ChiTietSanPhamBUS ctspBus = new ChiTietSanPhamBUS();
//		ArrayList<ChiTietSanPham> arrCtsp = (ArrayList<ChiTietSanPham>) ctspBus.hienThiTatCaSanPham();
//
//		boolean loSanXuatCapNhat = false;
//
//		// Duyệt qua tất cả các chi tiết sản phẩm
//		for (ChiTietSanPham ctsp : arrCtsp) {
//			// Kiểm tra mã sản phẩm và lô sản xuất
//			if (ctsp.getMaSanPham() != null && spHienTai.getMaSanPham() != null
//					&& ctsp.getMaSanPham().equals(spHienTai.getMaSanPham())) {
//
//				// Nếu số lượng của lô hiện tại bằng 0, chuyển sang lô sản xuất tiếp theo
//				if (ctsp.getSoLuong() == 0) {
//					String loSanXuatTiepTheo = layLoSanXuatTiepTheo(ctsp.getLoSanXuat());
//
//					if (loSanXuatTiepTheo != null) {
//						// Cập nhật sản phẩm hiện tại với lô sản xuất tiếp theo
//						spHienTai = hdBUS.getChiTietSP(ctsp.getMaSanPham(), loSanXuatTiepTheo);
//
//						// Cập nhật lô sản xuất và các thông tin liên quan lên giao diện
//						txt_loSX.setText(loSanXuatTiepTheo);
//						txtGiaTien.setText(spHienTai.getGiaBan() + "");
//						txtConLai.setText(spHienTai.getSoLuong() + "");
//
//						loSanXuatCapNhat = true;
//						break; // Thoát khỏi vòng lặp sau khi tìm thấy lô mới
//					} else {
//						// Nếu không có lô sản xuất tiếp theo, thông báo hết hàng
//						txt_loSX.setText("Sản phẩm đã hết hàng.");
//						txtGiaTien.setText("N/A");
//						txtSoLuong.setText("0");
//
//						loSanXuatCapNhat = true;
//						break; // Không cần duyệt thêm
//					}
//				} else {
//					// Nếu lô sản xuất hiện tại còn hàng, hiển thị thông tin của lô hiện tại
//					spHienTai = ctsp;
//					txt_loSX.setText(ctsp.getLoSanXuat());
//					txtGiaTien.setText(ctsp.getGiaBan() + "");
//					txtSoLuong.setText(ctsp.getSoLuong() + "");
//
//					loSanXuatCapNhat = true;
//					break; // Thoát khỏi vòng lặp sau khi cập nhật lô hiện tại
//				}
//			}
//		}
//
//		// Nếu không có lô nào được cập nhật, hiển thị thông báo
//		if (!loSanXuatCapNhat) {
//			txt_loSX.setText("Không có thông tin lô sản xuất.");
//			txtGiaTien.setText("N/A");
//			txtSoLuong.setText("0");
//		}
//	}
//
//	
	private class CustomComboBoxRenderer extends DefaultListCellRenderer {
		private SanPham sanPhamDaChon;

		public void setSanPhamDaChon(SanPham sanPham) {
			this.sanPhamDaChon = sanPham;
		}

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof SanPham) {
				SanPham sp = (SanPham) value;
				if (sp.equals(sanPhamDaChon)) {
					component.setBackground(Color.LIGHT_GRAY); 
				} else {
					component.setBackground(isSelected ? Color.LIGHT_GRAY : Color.WHITE);
				}
			}
			return component;
		}
	}
	private void loadKH() {
		ArrayList<KhachHang> khachHang = hdBUS.getKhachHang();
		cbbKhachHang.removeAllItems();
		for (KhachHang kh : khachHang)
			cbbKhachHang.addItem(kh);
		cbbKhachHang.setSelectedIndex(-1);
	}

	private void loadHoaDon(ArrayList<HoaDon> list) {
		modelHoaDon.setRowCount(0);
		for (HoaDon hd : list) {
			Object[] data = new Object[] { hd.getMa(), hd.getNgayLap(), hd.getNguoiLap(),
					(hd.getKhachHang() != 0) ? "Thành viên" : "Vãng lai", hd.getTongTien() };
			modelHoaDon.addRow(data);
		}
	}

	private void loadLSX(String maSP) {

		ArrayList<String> loSX = hdBUS.getLoSX(maSP);
		txtLoSX.setText("");
		for (String lo : loSX)
			txtLoSX.setText(lo);
	}

	private void tinhTongTien() {
		long tong = 0;
		for (int i = 0; i < modelSanPham.getRowCount(); i++)
			tong = tong + Long.parseLong(modelSanPham.getValueAt(i, 3) + "")
					* Integer.parseInt(modelSanPham.getValueAt(i, 4) + "");
		tongTien = tong;
		diemTichLuy = rdThanhVien.isSelected() ? (int) (tong / 1000) : 0;
		
	}

	private void clear() {
		if (cbbKhachHang.getItemCount() < -1) {
			cbbKhachHang.setSelectedIndex(0);
		}
		txtLoSX.setText("");
		cbbSanPham.setSelectedIndex(0);
		txtGiaTien.setText("");
		txtSoLuong.setText("");
		selectedMode(false, -1);
		chkSuDungDiem.setSelected(false);
		lblTichDiemHienTai.setText("");
		loadSP();
	}

	private void selectedMode(boolean select, int maKH) {
		btnLapDon.setEnabled(!select);
		btnPDF.setEnabled(select);
		btnThemSP.setEnabled(!select);
		btnSuaSP.setEnabled(!select);
		btnXoaSP.setEnabled(!select);
		btnHuyBoSP.setEnabled(!select);
		cbbKhachHang.setEnabled(!select);
		cbbKhachHang.setSelectedIndex(maKH > -1 ? maKH - 1 : -1);
		chkSuDungDiem.setEnabled(!select);
		btnTVMoi.setEnabled(!select);
	}
	private void TimLoSanPham(JTextField txtSeri, String masp) {
	    // Lấy giá trị từ ô nhập liệu
	    String seriInput = txtSeri.getText();
	    SanPham selectedSP = (SanPham) cbbSanPham.getSelectedItem();

	    // Kiểm tra xem sản phẩm được chọn có null không
	    if (selectedSP == null) {
	        JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm.", "Thông báo", JOptionPane.WARNING_MESSAGE);
	        return; // Thoát khỏi hàm nếu không có sản phẩm được chọn
	    }

	    // Tạo đối tượng BUS
	    PhieuNhapBUS pnBus = new PhieuNhapBUS();
	    ChiTietSanPhamBUS ctspBus = new ChiTietSanPhamBUS();
	    
	    // Chuyển đổi giá trị seri thành số nguyên
	    int serinum;
	    try {
	        serinum = Integer.parseInt(seriInput);
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Giá trị seri không hợp lệ.", "Thông báo", JOptionPane.WARNING_MESSAGE);
	        return; // Thoát khỏi hàm nếu giá trị seri không hợp lệ
	    }

	    // Lấy danh sách tất cả chi tiết sản phẩm
	    ArrayList<ChiTietSanPham> ctspList = ctspBus.hienThiTatCaSanPham();
	    boolean found = false; // Biến để kiểm tra xem sản phẩm có được tìm thấy không

	    for (ChiTietSanPham ctsp : ctspList) {
	        // Kiểm tra mã sản phẩm
	        if (ctsp.getMaSanPham() != null && ctsp.getMaSanPham().equals(masp)) {
	            String seri = ctsp.getMavach(); // Lấy mã vạch
	            
	            // Tách chuỗi Seri thành hai phần
	            String[] parts = seri.split("-");
	            // Kiểm tra xem chuỗi có ít nhất 2 phần không
	            if (parts.length < 2) {
	                JOptionPane.showMessageDialog(null, "Mã vạch không hợp lệ.", "Thông báo", JOptionPane.WARNING_MESSAGE);
	                txtConLai.setText("0");
	                return; // Thoát khỏi hàm nếu mã vạch không hợp lệ
	            }
	            
	            // Tìm chi tiết phiếu nhập theo mã vạch
	            ChiTietPhieuNhap Ctpn = pnBus.timChiTietPhieuNhapTheoMaVach(ctsp.getMavach());
	            
	            
	            
	           //gan lay ma vach 
	            
	            mavachtam=ctsp.getMavach();

	            // Kiểm tra xem Ctpn có null không
	            if (Ctpn == null) {
	                JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin chi tiết phiếu nhập cho mã vạch: " + ctsp.getMavach(), "Thông báo", JOptionPane.WARNING_MESSAGE);
	                continue; // Tiếp tục vòng lặp nếu không tìm thấy chi tiết phiếu nhập
	            }

	            // Lấy giá trị Seri bắt đầu và kết thúc
	            int seriBD1 = Integer.parseInt(parts[0]); // Phần đầu tiên trước dấu '-'
	            int seriKT1 = Integer.parseInt(parts[1]); // Phần thứ hai sau dấu '-'

	            // Kiểm tra xem seri có nằm trong khoảng không
	            if (serinum >= seriBD1 && serinum <= seriKT1) {
	                txtLoSX.setText(ctsp.getLoSanXuat());
	                txtGiaTien.setText(String.valueOf(Ctpn.getGiaNhap() * ctsp.getphantram() / 100 + Ctpn.getGiaNhap()));
	             
	                txtConLai.setText(String.valueOf(ctsp.getSoLuong()));
	                found = true; // Đánh dấu là đã tìm thấy sản phẩm
	                return; // Nếu tìm thấy sản phẩm, thoát khỏi hàm
	            }
	        }
	    }

	    // Nếu không tìm thấy sản phẩm trong khoảng seri
	    if (!found) {
	        JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm trong khoảng seri.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	    }
	}

//
	private int timSP(String maSP, String loSX) {
	for (int i = 0; i < spList.size(); i++) {
			ChiTietHoaDon sp = spList.get(i);
			
			System.out.println(sp.getChiTiet().getMaSanPham()+"lo o ham tim Sp " +sp.getChiTiet().getLoSanXuat());
			if (sp.getChiTiet().getMaSanPham().equals(maSP) && sp.getChiTiet().getLoSanXuat().equals(loSX))
				return i;
		}
		return -1;
	}
//
	private boolean kiemTraSoLuong(String maSP, String loSX, int soLuong) {
	    int tong = 0;

	    for (ChiTietHoaDon sp : spList) {
	        // Kiểm tra xem sản phẩm và lô sản xuất có null không trước khi gọi phương thức
	        if (sp.getChiTiet() != null && sp.getChiTiet().getSanPham() != null) {
	            SanPham sanPham = sp.getChiTiet().getSanPham();

	            // Kiểm tra xem mã sản phẩm và lô sản xuất có null không
	            if (sanPham.getMa() != null && sp.getChiTiet().getLoSanXuat() != null &&
	                sanPham.getMa().equals(maSP) && sp.getChiTiet().getLoSanXuat().equals(loSX)) {
	                tong += sp.getSoLuong();
	            }
	        }
	    }

	    // Kiểm tra số lượng còn lại
	    return (spHienTai != null && spHienTai.getSoLuong() - tong - soLuong >= 0);
	}
	private void handleSeriInput(KeyEvent e) {
	    // Lấy giá trị từ txtSeri
	    String seriInput = txtSeri.getText().trim();

	    // Kiểm tra nếu chuỗi không rỗng và phải là số
	    if (!seriInput.isEmpty() && seriInput.matches("\\d+")) {
	        // Lấy sản phẩm được chọn từ combobox
	        SanPham selectedSP = (SanPham) cbbSanPham.getSelectedItem();
	        
	        if (selectedSP != null) { 
	            // Gọi phương thức TimLoSanPham nếu dữ liệu hợp lệ
	            TimLoSanPham(txtSeri, selectedSP.getMa());
	        } else {
	            txtLoSX.setText("");
	            txtGiaTien.setText("0");
	            txtConLai.setText("0");
	        }
	    } else {
	        // Hiển thị thông báo nếu seri không hợp lệ
	        txtLoSX.setText("");
	        txtGiaTien.setText("0");
	        txtConLai.setText("0");
	    }
	}

	void repainting() {
		setBackground(Theme.LIGHT);
		panelLeft.setBackground(Theme.LIGHT);
		themKH.repainting();
	}
	
	private void loadSP() {
		ArrayList<SanPham> sanPham = new SanPhamBUS().getAllSp();
		cbbSanPham.removeAllItems(); 
		for (SanPham sp : sanPham) {
			if (sp.isTrangThai()) {
				cbbSanPham.addItem(sp); 
			}
		}}
	}




