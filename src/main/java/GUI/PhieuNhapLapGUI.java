package GUI;

import java.awt.Font;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import DTO.*;
import BUS.NhaCungCapBUS;
import BUS.PhieuNhapBUS;
import BUS.SanPhamBUS;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import com.toedter.calendar.*;

public class PhieuNhapLapGUI extends JPanel {
	private PhieuNhapBUS bus = new PhieuNhapBUS();
	private static final long serialVersionUID = 1L;
	private JPanel pnInfo;
	private JTextField txtNgayLap;
	private JTextField txtNguoiLap;
	private JComboBox<SanPham> cbbMaSP;
	private JComboBox<NhaCungCap> cbbMaNCC;
	private JTextField txtLoSX;
	private JDateChooser dcNgaySX;
	private JDateChooser dcHanSD;
	private JTextField txtGiaNhap;
	private JTextField txtGiaBan;
	
	private JLabel lblErrorLoSX;
	private JLabel lblErrorNgaySX;
	private JLabel lblErrorHanSD;
	private JLabel lblErrorGiaNhap;
	private JLabel lblErrorGiaBan;
	private JLabel lblErorSoLuong;
	
	private JTable tableSanPham;
	private DefaultTableModel modelSanPham = new DefaultTableModel(
			new Object[][] {}, new String[] {"Mã SP", "Lô SX", "Ngày SX", "Hạn SD", "Giá nhập", "Phần Trăm", "Số lượng","Seri"}
	);
	private String maDS;
	
	private JLabel lblTongTien;
	private long tongTien = 0;
	private JButton btnLap;
	private JTextField txtSeriBD;
	private JTextField txtSoLuong;
	private JTextField txtSeriKT;
	private JTextField txtPhanTram;
	private JLabel lblErrorPhanTram;
	private JLabel lblErrorSeri;
	private JButton btnMore;
	/**
	 * Create the panel.
	 */
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
	public PhieuNhapLapGUI(String maDS) {
		bus.getPhieuNhap();
		this.maDS = maDS;
		setBounds(0,0,1000,550);
		
		JScrollPane scrollInfo = new JScrollPane();
		scrollInfo.setBorder(null);
		scrollInfo.setBounds(100, 100, 500, 550);
		scrollInfo.getVerticalScrollBar().setUnitIncrement(16);
		setLayout(new GridLayout(0, 1, 0, 0));
		add(scrollInfo);
		
		pnInfo = new JPanel();
		pnInfo.setBorder(new EmptyBorder(10, 10, 10, 10));
		scrollInfo.setViewportView(pnInfo);
		GridBagLayout gbl_pnInfo = new GridBagLayout();
		gbl_pnInfo.columnWidths = new int[] {0, 0, 0, 0, 0, 0};
		gbl_pnInfo.rowHeights = new int[] {48, 39, 0, 30, 30};
		gbl_pnInfo.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		gbl_pnInfo.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0};
		pnInfo.setLayout(gbl_pnInfo);
		
		JLabel lblNhaCungCap = new JLabel("Nhà cung cấp:");
		lblNhaCungCap.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNhaCungCap = new GridBagConstraints();
		gbc_lblNhaCungCap.fill = GridBagConstraints.VERTICAL;
		gbc_lblNhaCungCap.anchor = GridBagConstraints.WEST;
		gbc_lblNhaCungCap.insets = new Insets(0, 0, 15, 15);
		gbc_lblNhaCungCap.gridx = 4;
		gbc_lblNhaCungCap.gridy = 1;
		pnInfo.add(lblNhaCungCap, gbc_lblNhaCungCap);
		
		JLabel lblTitle = new JLabel("THÔNG TIN PHIẾU NHẬP");
		lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.fill = GridBagConstraints.BOTH;
		gbc_lblTitle.insets = new Insets(0, 0, 15, 0);
		gbc_lblTitle.gridwidth = 6;
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		pnInfo.add(lblTitle, gbc_lblTitle);
		
		txtNgayLap = new JTextField(LocalDate.now().toString());
		txtNgayLap.setEnabled(false);
		txtNgayLap.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_txtNgayLap = new GridBagConstraints();
		gbc_txtNgayLap.fill = GridBagConstraints.BOTH;
		gbc_txtNgayLap.insets = new Insets(0, 0, 15, 15);
		gbc_txtNgayLap.gridx = 1;
		gbc_txtNgayLap.gridy = 1;
		pnInfo.add(txtNgayLap, gbc_txtNgayLap);
		txtNgayLap.setColumns(10);
		
		JLabel lblNguoiLap = new JLabel("Người lập:");
		lblNguoiLap.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNguoiLap = new GridBagConstraints();
		gbc_lblNguoiLap.anchor = GridBagConstraints.WEST;
		gbc_lblNguoiLap.fill = GridBagConstraints.VERTICAL;
		gbc_lblNguoiLap.insets = new Insets(0, 0, 15, 15);
		gbc_lblNguoiLap.gridx = 2;
		gbc_lblNguoiLap.gridy = 1;
		pnInfo.add(lblNguoiLap, gbc_lblNguoiLap);
		
		txtNguoiLap = new JTextField(maDS);
		txtNguoiLap.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtNguoiLap.setEnabled(false);
		txtNguoiLap.setColumns(10);
		GridBagConstraints gbc_txtNguoiLap = new GridBagConstraints();
		gbc_txtNguoiLap.fill = GridBagConstraints.BOTH;
		gbc_txtNguoiLap.insets = new Insets(0, 0, 15, 15);
		gbc_txtNguoiLap.gridx = 3;
		gbc_txtNguoiLap.gridy = 1;
		pnInfo.add(txtNguoiLap, gbc_txtNguoiLap);
		
		JLabel lblNgayLap = new JLabel("Ngày lập:");
		lblNgayLap.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNgayLap = new GridBagConstraints();
		gbc_lblNgayLap.anchor = GridBagConstraints.WEST;
		gbc_lblNgayLap.fill = GridBagConstraints.VERTICAL;
		gbc_lblNgayLap.insets = new Insets(0, 0, 15, 15);
		gbc_lblNgayLap.gridx = 0;
		gbc_lblNgayLap.gridy = 1;
		pnInfo.add(lblNgayLap, gbc_lblNgayLap);
		
		cbbMaNCC = new JComboBox<>();
		cbbMaNCC.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_cbbMaNCC = new GridBagConstraints();
		gbc_cbbMaNCC.insets = new Insets(0, 0, 15, 0);
		gbc_cbbMaNCC.fill = GridBagConstraints.BOTH;
		gbc_cbbMaNCC.gridx = 5;
		gbc_cbbMaNCC.gridy = 1;
		pnInfo.add(cbbMaNCC, gbc_cbbMaNCC);
		
		JPanel pnSanPham = new JPanel();
		pnSanPham.setOpaque(false);
		pnSanPham.setBorder(new CompoundBorder(
				new TitledBorder(new LineBorder(Color.GRAY, 1), "Thông tin sản phẩm", 0, 0, new Font("Segoe UI", Font.ITALIC, 16)),
				new EmptyBorder(10,10,10,10)
		));
		GridBagConstraints gbc_pnSanPham = new GridBagConstraints();
		gbc_pnSanPham.insets = new Insets(0, 0, 5, 0);
		gbc_pnSanPham.fill = GridBagConstraints.BOTH;
		gbc_pnSanPham.gridwidth = 6;
		gbc_pnSanPham.gridx = 0;
		gbc_pnSanPham.gridy = 2;
		pnInfo.add(pnSanPham, gbc_pnSanPham);
		GridBagLayout gbl_pnSanPham = new GridBagLayout();
		gbl_pnSanPham.columnWidths = new int[] {30, 30, 30, 30, 30};
		gbl_pnSanPham.rowHeights = new int[] {39, 24, 20, 20, 10, 20};
		gbl_pnSanPham.columnWeights = new double[]{0.0, 0.5, 0.0, 0.0, 0.5, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0};
		gbl_pnSanPham.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		pnSanPham.setLayout(gbl_pnSanPham);
		
		JLabel lblMaSP = new JLabel("Mã SP:");
		lblMaSP.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblMaSP = new GridBagConstraints();
		gbc_lblMaSP.anchor = GridBagConstraints.WEST;
		gbc_lblMaSP.insets = new Insets(0, 0, 22, 15);
		gbc_lblMaSP.gridx = 0;
		gbc_lblMaSP.gridy = 0;
		pnSanPham.add(lblMaSP, gbc_lblMaSP);
		
		cbbMaSP = new JComboBox<>();
		cbbMaSP.setPreferredSize(new Dimension(this.getWidth()/2,30));
		cbbMaSP.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_cbbMaSP = new GridBagConstraints();
		gbc_cbbMaSP.gridwidth = 13;
		gbc_cbbMaSP.insets = new Insets(0, 0, 22, 5);
		gbc_cbbMaSP.fill = GridBagConstraints.BOTH;
		gbc_cbbMaSP.gridx = 1;
		gbc_cbbMaSP.gridy = 0;
		pnSanPham.add(cbbMaSP, gbc_cbbMaSP);
		
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
							
							for (int i = 0; i < cbbMaSP.getItemCount(); i++) {
								SanPham existingProduct = cbbMaSP.getItemAt(i);
								if (existingProduct.getMa().equals(sanPhamDaChon.getMa())) {
									
									oldIndex = i;
									cbbMaSP.removeItemAt(i);
									break;
								}
							}
							
							
							if (oldIndex != -1) {
							
								cbbMaSP.insertItemAt(sanPhamDaChon, oldIndex);
								cbbMaSP.setSelectedItem(sanPhamDaChon);
							}
//							CustomComboBoxRenderer renderer = new CustomComboBoxRenderer();
//							renderer.setSanPhamDaChon(sanPhamDaChon);
//							cbbMaSP.setRenderer(renderer);
//							cbbMaSP.showPopup(); 
						} 
						
					}
				});
		
				formTimSanPham.setVisible(true);
			}
		});
		
		GridBagConstraints gbc_btnMore = new GridBagConstraints();
		gbc_btnMore.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnMore.insets = new Insets(0, 0, 5, 5);
		gbc_btnMore.gridx = 14;
		gbc_btnMore.gridy = 0;
		pnSanPham.add(btnMore, gbc_btnMore);


		
		JLabel lblLoSX = new JLabel("Lô SX:");
		lblLoSX.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblLoSX = new GridBagConstraints();
		gbc_lblLoSX.anchor = GridBagConstraints.WEST;
		gbc_lblLoSX.fill = GridBagConstraints.VERTICAL;
		gbc_lblLoSX.insets = new Insets(0, 0, 5, 15);
		gbc_lblLoSX.gridx = 0;
		gbc_lblLoSX.gridy = 1;
		pnSanPham.add(lblLoSX, gbc_lblLoSX);
		
		txtLoSX = new JTextField();
		txtLoSX.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtLoSX.setColumns(10);
		GridBagConstraints gbc_txtLoSX = new GridBagConstraints();
		gbc_txtLoSX.fill = GridBagConstraints.BOTH;
		gbc_txtLoSX.insets = new Insets(0, 0, 5, 15);
		gbc_txtLoSX.gridx = 1;
		gbc_txtLoSX.gridy = 1;
		pnSanPham.add(txtLoSX, gbc_txtLoSX);
		
		dcNgaySX = new JDateChooser(new java.util.Date(), "yyyy-MM-dd");
		dcNgaySX.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_dcNgaySX = new GridBagConstraints();
		gbc_dcNgaySX.insets = new Insets(0, 0, 5, 15);
		gbc_dcNgaySX.fill = GridBagConstraints.BOTH;
		gbc_dcNgaySX.gridx = 3;
		gbc_dcNgaySX.gridy = 1;
		pnSanPham.add(dcNgaySX, gbc_dcNgaySX);
		
		JLabel lblHanSD = new JLabel("HSD:");
		lblHanSD.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblHanSD = new GridBagConstraints();
		gbc_lblHanSD.gridwidth = 2;
		gbc_lblHanSD.anchor = GridBagConstraints.WEST;
		gbc_lblHanSD.insets = new Insets(0, 0, 5, 5);
		gbc_lblHanSD.gridx = 4;
		gbc_lblHanSD.gridy = 1;
		pnSanPham.add(lblHanSD, gbc_lblHanSD);
		
		
		dcHanSD = new JDateChooser(new java.util.Date(), "yyyy-MM-dd");
		dcHanSD.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(dcHanSD.getDate()); 
		calendar.add(Calendar.YEAR, 1);
		dcHanSD.setDate(calendar.getTime());
		GridBagConstraints gbc_dcHanSD = new GridBagConstraints();
		gbc_dcHanSD.gridwidth = 3;
		gbc_dcHanSD.insets = new Insets(0, 0, 5, 5);
		gbc_dcHanSD.fill = GridBagConstraints.BOTH;
		gbc_dcHanSD.gridx = 6;
		gbc_dcHanSD.gridy = 1;
		pnSanPham.add(dcHanSD, gbc_dcHanSD);
		
		JLabel lblSoLuong = new JLabel("Số lượng:");
		lblSoLuong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblSoLuong = new GridBagConstraints();
		gbc_lblSoLuong.anchor = GridBagConstraints.WEST;
		gbc_lblSoLuong.fill = GridBagConstraints.VERTICAL;
		gbc_lblSoLuong.insets = new Insets(0, 0, 5, 5);
		gbc_lblSoLuong.gridx = 10;
		gbc_lblSoLuong.gridy = 1;
		pnSanPham.add(lblSoLuong, gbc_lblSoLuong);
		
		txtSoLuong = new JTextField();
		txtSoLuong.setColumns(10);
		GridBagConstraints gbc_txtSoLuong = new GridBagConstraints();
		gbc_txtSoLuong.gridwidth = 5;
		gbc_txtSoLuong.insets = new Insets(0, 0, 5, 0);
		gbc_txtSoLuong.fill = GridBagConstraints.BOTH;
		gbc_txtSoLuong.gridx = 11;
		gbc_txtSoLuong.gridy = 1;
		pnSanPham.add(txtSoLuong, gbc_txtSoLuong);
		
		lblErrorLoSX = new JLabel(" ");
		lblErrorLoSX.setFont(new Font("Segoe UI", Font.ITALIC, 14));
		lblErrorLoSX.setForeground(new Color(255, 0, 0));
		GridBagConstraints gbc_lblErrorLoSX = new GridBagConstraints();
		gbc_lblErrorLoSX.anchor = GridBagConstraints.WEST;
		gbc_lblErrorLoSX.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorLoSX.gridx = 1;
		gbc_lblErrorLoSX.gridy = 2;
		pnSanPham.add(lblErrorLoSX, gbc_lblErrorLoSX);
		
		JLabel lblNgaySX = new JLabel("NSX:");
		lblNgaySX.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNgaySX = new GridBagConstraints();
		gbc_lblNgaySX.anchor = GridBagConstraints.WEST;
		gbc_lblNgaySX.insets = new Insets(0, 0, 5, 5);
		gbc_lblNgaySX.gridx = 2;
		gbc_lblNgaySX.gridy = 1;
		pnSanPham.add(lblNgaySX, gbc_lblNgaySX);
		
		lblErrorNgaySX = new JLabel(" ");
		lblErrorNgaySX.setFont(new Font("Segoe UI", Font.ITALIC, 14));
		lblErrorNgaySX.setForeground(new Color(255, 0, 0));
		GridBagConstraints gbc_lblErrorNgaySX = new GridBagConstraints();
		gbc_lblErrorNgaySX.anchor = GridBagConstraints.WEST;
		gbc_lblErrorNgaySX.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorNgaySX.gridx = 3;
		gbc_lblErrorNgaySX.gridy = 2;
		pnSanPham.add(lblErrorNgaySX, gbc_lblErrorNgaySX);
		
		lblErrorHanSD = new JLabel(" ");
		lblErrorHanSD.setFont(new Font("Segoe UI", Font.ITALIC, 14));
		lblErrorHanSD.setForeground(new Color(255, 0, 0));
		GridBagConstraints gbc_lblErrorHanSD = new GridBagConstraints();
		gbc_lblErrorHanSD.gridwidth = 3;
		gbc_lblErrorHanSD.anchor = GridBagConstraints.WEST;
		gbc_lblErrorHanSD.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorHanSD.gridx = 6;
		gbc_lblErrorHanSD.gridy = 2;
		pnSanPham.add(lblErrorHanSD, gbc_lblErrorHanSD);
		
		lblErorSoLuong = new JLabel(" ");
		GridBagConstraints gbc_lblErorSoLuong = new GridBagConstraints();
		lblErorSoLuong.setFont(new Font("Segoe UI", Font.ITALIC, 14));
		lblErorSoLuong.setForeground(new Color(255, 0, 0));
		gbc_lblErorSoLuong.anchor = GridBagConstraints.WEST;
		gbc_lblErorSoLuong.gridwidth = 5;
		gbc_lblErorSoLuong.insets = new Insets(0, 0, 5, 0);
		gbc_lblErorSoLuong.gridx = 11;
		gbc_lblErorSoLuong.gridy = 2;
		pnSanPham.add(lblErorSoLuong, gbc_lblErorSoLuong);
		
		JLabel lblGiaNhap = new JLabel("Giá nhập:");
		lblGiaNhap.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblGiaNhap = new GridBagConstraints();
		gbc_lblGiaNhap.anchor = GridBagConstraints.WEST;
		gbc_lblGiaNhap.fill = GridBagConstraints.VERTICAL;
		gbc_lblGiaNhap.insets = new Insets(0, 0, 5, 15);
		gbc_lblGiaNhap.gridx = 0;
		gbc_lblGiaNhap.gridy = 3;
		pnSanPham.add(lblGiaNhap, gbc_lblGiaNhap);
		
		txtGiaNhap = new JTextField();
	

		txtGiaNhap.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtGiaNhap.setColumns(10);
		GridBagConstraints gbc_txtGiaNhap = new GridBagConstraints();
		gbc_txtGiaNhap.fill = GridBagConstraints.BOTH;
		gbc_txtGiaNhap.insets = new Insets(0, 0, 5, 15);
		gbc_txtGiaNhap.gridx = 1;
		gbc_txtGiaNhap.gridy = 3;
		pnSanPham.add(txtGiaNhap, gbc_txtGiaNhap);
		
		txtGiaBan = new JTextField();
		txtGiaBan.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtGiaBan.setColumns(10);
		txtGiaBan.setEnabled(false);
		GridBagConstraints gbc_txtGiaBan = new GridBagConstraints();
		gbc_txtGiaBan.insets = new Insets(0, 0, 5, 15);
		gbc_txtGiaBan.fill = GridBagConstraints.BOTH;
		gbc_txtGiaBan.gridx = 3;
		gbc_txtGiaBan.gridy = 3;
		pnSanPham.add(txtGiaBan, gbc_txtGiaBan);
		
		JLabel lblSeri = new JLabel("Seri :");
		lblSeri.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblSeri = new GridBagConstraints();
		gbc_lblSeri.gridwidth = 2;
		gbc_lblSeri.anchor = GridBagConstraints.WEST;
		gbc_lblSeri.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeri.gridx = 4;
		gbc_lblSeri.gridy = 3;
		pnSanPham.add(lblSeri, gbc_lblSeri);
		
		txtSeriBD = new JTextField();
		GridBagConstraints gbc_txtSeriBD = new GridBagConstraints();
		gbc_txtSeriBD.insets = new Insets(0, 0, 5, 5);
		gbc_txtSeriBD.fill = GridBagConstraints.BOTH;
		gbc_txtSeriBD.gridx = 6;
		gbc_txtSeriBD.gridy = 3;
		pnSanPham.add(txtSeriBD, gbc_txtSeriBD);
		txtSeriBD.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("-");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 7;
		gbc_lblNewLabel.gridy = 3;
		pnSanPham.add(lblNewLabel, gbc_lblNewLabel);
		
		txtSeriKT = new JTextField();
		txtSeriKT.setColumns(10);
		GridBagConstraints gbc_txtSeriKT = new GridBagConstraints();
		gbc_txtSeriKT.anchor = GridBagConstraints.WEST;
		gbc_txtSeriKT.insets = new Insets(0, 0, 5, 5);
		gbc_txtSeriKT.fill = GridBagConstraints.VERTICAL;
		gbc_txtSeriKT.gridx = 8;
		gbc_txtSeriKT.gridy = 3;
		pnSanPham.add(txtSeriKT, gbc_txtSeriKT);
		
		JLabel lblPhanTram = new JLabel("Phần trăm \r\n\r\n");
		lblPhanTram.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblPhanTram = new GridBagConstraints();
		gbc_lblPhanTram.anchor = GridBagConstraints.EAST;
		gbc_lblPhanTram.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhanTram.gridx = 10;
		gbc_lblPhanTram.gridy = 3;
		pnSanPham.add(lblPhanTram, gbc_lblPhanTram);
		
		txtPhanTram = new JTextField();
		txtPhanTram.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyReleased(KeyEvent e) {
		        try {
		            String giaNhapText = txtGiaNhap.getText();
		            if (giaNhapText == null || giaNhapText.trim().isEmpty()) {
		                txtGiaBan.setText("");
		                return;
		            }
		            long gianhap = Integer.parseInt(giaNhapText);
		            String phanTramText = txtPhanTram.getText();
		            if (phanTramText == null || phanTramText.trim().isEmpty()) {
		                txtGiaBan.setText("");
		                return;
		            }
		            long giaban = gianhap + gianhap * Integer.parseInt(phanTramText) / 100;
		            txtGiaBan.setText(String.valueOf(giaban));
		        } catch (NumberFormatException ex) {
		            System.out.println("Lỗi định dạng số: " + ex.getMessage());
		            txtGiaBan.setText("");
		        }
		    }
		});

		GridBagConstraints gbc_txtPhanTram = new GridBagConstraints();
		gbc_txtPhanTram.gridwidth = 5;
		gbc_txtPhanTram.insets = new Insets(0, 0, 5, 0);
		gbc_txtPhanTram.fill = GridBagConstraints.BOTH;
		gbc_txtPhanTram.gridx = 11;
		gbc_txtPhanTram.gridy = 3;
		pnSanPham.add(txtPhanTram, gbc_txtPhanTram);
		txtPhanTram.setColumns(10);
		 
		lblErrorSeri = new JLabel("");
		GridBagConstraints gbc_lblErrorSeri = new GridBagConstraints();
		lblErrorSeri.setFont(new Font("Segoe UI", Font.ITALIC, 14));
		lblErrorSeri.setForeground(new Color(255, 0, 0));
		gbc_lblErrorSeri.anchor = GridBagConstraints.WEST;
		gbc_lblErrorSeri.gridwidth = 4;
		gbc_lblErrorSeri.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorSeri.gridx = 5;
		gbc_lblErrorSeri.gridy = 4;
		pnSanPham.add(lblErrorSeri, gbc_lblErrorSeri);
		
		lblErrorPhanTram = new JLabel(" ");
		GridBagConstraints gbc_lblErrorPhanTram = new GridBagConstraints();
		gbc_lblErrorPhanTram.anchor = GridBagConstraints.WEST;
		gbc_lblErrorPhanTram.gridwidth = 5;
		gbc_lblErrorPhanTram.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorPhanTram.gridx = 11;
		gbc_lblErrorPhanTram.gridy = 4;
		lblErrorPhanTram.setFont(new Font("Segoe UI", Font.ITALIC, 14));
		lblErrorPhanTram.setForeground(new Color(255, 0, 0));
		pnSanPham.add(lblErrorPhanTram, gbc_lblErrorPhanTram);
			
		lblErrorGiaNhap = new JLabel(" ");
		lblErrorGiaNhap.setFont(new Font("Segoe UI", Font.ITALIC, 14));
		lblErrorGiaNhap.setForeground(new Color(255, 0, 0));
		GridBagConstraints gbc_lblErrorGiaNhap = new GridBagConstraints();
		gbc_lblErrorGiaNhap.anchor = GridBagConstraints.WEST;
		gbc_lblErrorGiaNhap.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorGiaNhap.gridx = 1;
		gbc_lblErrorGiaNhap.gridy = 4;
		pnSanPham.add(lblErrorGiaNhap, gbc_lblErrorGiaNhap);
			
		JLabel lblGiaBan = new JLabel("Giá bán:");
		lblGiaBan.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblGiaBan = new GridBagConstraints();
		gbc_lblGiaBan.anchor = GridBagConstraints.WEST;
		gbc_lblGiaBan.insets = new Insets(0, 0, 5, 20);
		gbc_lblGiaBan.gridx = 2;
		gbc_lblGiaBan.gridy = 3;
			
		pnSanPham.add(lblGiaBan, gbc_lblGiaBan);
			
		lblErrorGiaBan = new JLabel(" ");
		lblErrorGiaBan.setFont(new Font("Segoe UI", Font.ITALIC, 14));
		lblErrorGiaBan.setForeground(new Color(255, 0, 0));
		GridBagConstraints gbc_lblErrorGiaBan = new GridBagConstraints();
		gbc_lblErrorGiaBan.anchor = GridBagConstraints.WEST;
		gbc_lblErrorGiaBan.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorGiaBan.gridx = 3;
		gbc_lblErrorGiaBan.gridy = 4;
		pnSanPham.add(lblErrorGiaBan, gbc_lblErrorGiaBan);
			
		JPanel pnButtonSP = new JPanel();
		pnButtonSP.setOpaque(false);
		GridBagConstraints gbc_pnButtonSP = new GridBagConstraints();
		gbc_pnButtonSP.gridheight = 2;
		gbc_pnButtonSP.fill = GridBagConstraints.BOTH;
		gbc_pnButtonSP.gridwidth = 16;
		gbc_pnButtonSP.insets = new Insets(0, 0, 5, 0);
		gbc_pnButtonSP.gridx = 0;
		gbc_pnButtonSP.gridy = 6;
		pnSanPham.add(pnButtonSP, gbc_pnButtonSP);
		pnButtonSP.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
			
		JButton btnLuuSP = new JButton("Lưu");
		pnButtonSP.add(btnLuuSP);
		btnLuuSP.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (checkLoi()) return;
		        
		        String lo = txtLoSX.getText().trim();
		        String nhap = txtGiaNhap.getText();
		        String sp = ((SanPham) cbbMaSP.getSelectedItem()).getMa();
		        String seriBD = txtSeriBD.getText().trim();
		        String seriKT = txtSeriKT.getText().trim();

		        if (!lo.matches("^LO\\d{3}$")) {
		            JOptionPane.showMessageDialog(null, "Số lô phải có định dạng 'LO' theo sau là 3 chữ số. Ví dụ: LO123", "Lỗi", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

//		        int bd = Integer.parseInt(seriBD);
//		        int kt = Integer.parseInt(seriKT);
//		        int soLuong = Integer.parseInt(txtSoLuong.getText());
//		        
//		        if ((kt - bd) != soLuong) {
//		            JOptionPane.showMessageDialog(null, "Hiệu số giữa seriBD và seriKT phải bằng số lượng!");
//		            return;
//		        }
//		        
//		        BarCode barcode = new BarCode();
//		      
//		        	barcode.createBarcodes(Integer.parseInt(seriBD),Integer.parseInt(seriKT));
//		        	
//		        	
		        	
		        	
		   
		        if (!bus.check_losp(sp, lo)) {
		            ThongBao.baoLoi("Lô này đã tồn tại!");
		            return;
		        }

		        for (int i = 0; i < tableSanPham.getRowCount(); i++) {
		            String matb = (String) tableSanPham.getValueAt(i, 0);
		            String lotb = (String) tableSanPham.getValueAt(i, 1);
		            String nhaptb = (String) tableSanPham.getValueAt(i, 4);
		            String seriTb = (String) tableSanPham.getValueAt(i, 7);
		            
		            String[] seriRange = seriTb.split("-");
		            String seriKtTb = seriRange[1];
		            
		            if (matb.equals(sp) && lotb.equals(lo) && !nhaptb.equals(nhap)) {
		                ThongBao.thongBao("Lô này hiện tại đã tồn tại dưới bảng!");
		                return;
		            }
		            
		            // Kiểm tra nếu sản phẩm có cùng số lô và sản phẩm với khoảng seri liền nhau
		            if (matb.equals(sp) && lotb.equals(lo) && seriKtTb.equals(seriBD)) {
		                int existingQuantity = Integer.parseInt((String) modelSanPham.getValueAt(i, 6));
		                int newQuantity = Integer.parseInt(txtSoLuong.getText());
		                int updatedQuantity = existingQuantity + newQuantity;

		                // Cập nhật hàng hiện có với số lượng và seri mới
		                modelSanPham.setValueAt(String.valueOf(updatedQuantity), i, 6);
		                modelSanPham.setValueAt(seriRange[0] + "-" + seriKT, i, 7);
		                
		                cbbMaSP.setSelectedIndex(0);
		                clearSP();
		                tinhTongTien();
		                lblTongTien.setText("Tổng tiền: " + tongTien);
		                return;
		            }
		        }

		        int row = timSP(sp, lo);
		        String Seri = seriBD + "-" + seriKT;
		        
		        if (row == -1) {
		            modelSanPham.addRow(new Object[]{sp, lo, Time.toString(dcNgaySX.getDate()), Time.toString(dcHanSD.getDate()), txtGiaNhap.getText(), 
		                txtPhanTram.getText(), txtSoLuong.getText(), Seri
		            });
		        } else {
		            int existingQuantity = Integer.parseInt((String) modelSanPham.getValueAt(row, 6));
		            int newQuantity = Integer.parseInt(txtSoLuong.getText());
		            int updatedQuantity = existingQuantity + newQuantity;

		            modelSanPham.removeRow(row);
		            modelSanPham.insertRow(row, new Object[]{sp, lo, Time.toString(dcNgaySX.getDate()), Time.toString(dcHanSD.getDate()), txtGiaNhap.getText(),
		                txtPhanTram.getText(), String.valueOf(updatedQuantity), Seri
		            });
		        }
		        
		        cbbMaSP.setSelectedIndex(0);
		        clearSP();
		        tinhTongTien();
		        lblTongTien.setText("Tổng tiền: " + tongTien);
		    }
		});

			btnLuuSP.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			JButton btnCapNhat = new JButton("Cập nhật");
			btnCapNhat.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int row = tableSanPham.getSelectedRow();
					if (row == -1) {
						ThongBao.baoLoi("Vui lòng chọn 1 sản phẩm");
						return;
					}
					if (checkLoi()) return;
					modelSanPham.removeRow(row);
					modelSanPham.addRow(new Object[] {((SanPham)cbbMaSP.getSelectedItem()).getMa(), txtLoSX.getText(), 
							Time.toString(dcNgaySX.getDate()), Time.toString(dcHanSD.getDate()), txtGiaNhap.getText(), 
							txtPhanTram.getText(), txtSoLuong.getText()});
					cbbMaSP.setEnabled(true);
					txtLoSX.setText("");
					txtGiaNhap.setText("");
					txtSoLuong.setText("");
					loadSP();
					tinhTongTien();
					lblTongTien.setText("Tổng tiền: " + tongTien);
					txtLoSX.setEnabled(true);
					btnLuuSP.setEnabled(true);
					txtLoSX.setEnabled(true);
				}
			});
			btnCapNhat.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			pnButtonSP.add(btnCapNhat);
		
			JButton btnXoaSP = new JButton("Xóa");
			pnButtonSP.add(btnXoaSP);
			btnXoaSP.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int row = tableSanPham.getSelectedRow();
					if (row == -1) {
						ThongBao.baoLoi("Vui lòng chọn 1 sản phẩm");
						return;
					}
					else {
						modelSanPham.removeRow(row);
						cbbMaSP.setSelectedIndex(0);
						loadSP();
						cbbMaSP.setEnabled(true);
						txtLoSX.setText("");
						txtGiaNhap.setText("");
						txtSoLuong.setText("");
						txtPhanTram.setText("");
						tinhTongTien();
						lblTongTien.setText("Tổng tiền: " + tongTien);
						txtLoSX.setEnabled(true);
						btnLuuSP.setEnabled(true);
						txtLoSX.setEnabled(true);
					}
				}
			});
			btnXoaSP.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		
			JButton btnNhapExcel = new JButton("Nhập Excel");
			btnNhapExcel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser openFileDialog = new JFileChooser(System.getProperty("user.dir"));
					openFileDialog.setDialogTitle("Chọn vị trí cần nhập");
					openFileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Excel Workbook (.xlsx)","xlsx"));
					openFileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Excel 97-2003 Workbook (.xls)","xls"));
					openFileDialog.setAcceptAllFileFilterUsed(false);
					
					if (openFileDialog.showOpenDialog(PhieuNhapLapGUI.this) == JFileChooser.APPROVE_OPTION) {
						loadCTSP(bus.nhapSPExcel(openFileDialog.getSelectedFile().getAbsolutePath()));
						tinhTongTien();
						lblTongTien.setText("Tổng tiền: " + tongTien);
					}
				}
			});
			
			JButton btnXuatBieuMau = new JButton("Xuất biểu mẫu");
			btnXuatBieuMau.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser saveFileDialog = new JFileChooser(System.getProperty("user.dir"));
					saveFileDialog.setDialogTitle("Chọn vị trí cần xuất");
					saveFileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Excel Workbook (.xlsx)", "xlsx"));
					saveFileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Excel 97-2003 Workbook (.xls)", "xls"));
					saveFileDialog.setAcceptAllFileFilterUsed(false);
					
					if (saveFileDialog.showSaveDialog(PhieuNhapLapGUI.this) == JFileChooser.APPROVE_OPTION) {
					    String path = saveFileDialog.getSelectedFile().getAbsolutePath();
					    String desc = saveFileDialog.getFileFilter().getDescription();
					    if (!path.endsWith(".xlsx") && (!path.endsWith(".xls")))
					    	path += (desc.equals("Excel Workbook (.xlsx)")) ? ".xlsx" : ".xls";
					    if (bus.xuatMauSPNhap(modelSanPham, path)) ThongBao.thongBao("Xuất biểu mẫu thành công");
					    else ThongBao.baoLoi("Xuất biểu mẫu thất bại");
					}
				}
			});
			btnXuatBieuMau.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			pnButtonSP.add(btnXuatBieuMau);
			btnNhapExcel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			pnButtonSP.add(btnNhapExcel);
		
			JScrollPane scrollSanPham = new JScrollPane();
			scrollSanPham.setPreferredSize(new Dimension(0,150));
			GridBagConstraints gbc_scrollSanPham = new GridBagConstraints();
			gbc_scrollSanPham.fill = GridBagConstraints.BOTH;
			gbc_scrollSanPham.gridwidth = 16;
			gbc_scrollSanPham.gridx = 0;
			gbc_scrollSanPham.gridy = 8;
			pnSanPham.add(scrollSanPham, gbc_scrollSanPham);
			
			tableSanPham = new JTable(modelSanPham);
			setColorCell(tableSanPham)	;
			  tableSanPham.getTableHeader().setBackground(Color.decode("#B9B9B9"));
			tableSanPham.setFocusable(false);
			tableSanPham.setColumnSelectionAllowed(true);
			tableSanPham.setCellSelectionEnabled(false);
			tableSanPham.setRowSelectionAllowed(true);
			tableSanPham.setDefaultEditor(Object.class, null);
			tableSanPham.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableSanPham.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int row = tableSanPham.getSelectedRow();
					cbbMaSP.setSelectedItem(modelSanPham.getValueAt(row, 0));
					txtLoSX.setText(modelSanPham.getValueAt(row, 1) + "");
					dcNgaySX.setDate(Time.parseDate(modelSanPham.getValueAt(row, 2) + ""));
					dcHanSD.setDate(Time.parseDate(modelSanPham.getValueAt(row, 3) + ""));
					txtGiaNhap.setText(modelSanPham.getValueAt(row, 4) + "");
					txtPhanTram.setText(modelSanPham.getValueAt(row, 5) + "");
					txtSoLuong.setText(modelSanPham.getValueAt(row, 6) + "");
					String Seri = (String) modelSanPham.getValueAt(row, 7);
	
					// Tách chuỗi Seri tại dấu '-'
					String[] parts = Seri.split("-");
	
					// Gán seriBD và seriKT
					String seriBD = parts[0].trim(); // Phần trước dấu '-'
	
					// Kiểm tra xem có phần sau hay không để tránh ArrayIndexOutOfBoundsException
					String seriKT = parts.length > 1 ? parts[1].trim() : ""; // Phần sau dấu '-', nếu không có thì gán là chuỗi rỗng
					txtSeriBD.setText(seriBD);
					txtSeriKT.setText(seriKT);
					
					
					btnLuuSP.setEnabled(false);
				}
			});
			tableSanPham.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN | Font.ITALIC, 18));
			tableSanPham.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			scrollSanPham.setViewportView(tableSanPham);
			
			lblTongTien = new JLabel("Tổng tiền: " + tongTien);
			lblTongTien.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 16));
			GridBagConstraints gbc_lblTongTien = new GridBagConstraints();
			gbc_lblTongTien.insets = new Insets(0, 0, 5, 10);
			gbc_lblTongTien.gridwidth = 6;
			gbc_lblTongTien.anchor = GridBagConstraints.EAST;
			gbc_lblTongTien.gridx = 0;
			gbc_lblTongTien.gridy = 3;
			pnInfo.add(lblTongTien, gbc_lblTongTien);
			
			JPanel pnButtonInfo = new JPanel();
			pnButtonInfo.setOpaque(false);
			GridBagConstraints gbc_pnButtonInfo = new GridBagConstraints();
			gbc_pnButtonInfo.fill = GridBagConstraints.BOTH;
			gbc_pnButtonInfo.gridwidth = 6;
			gbc_pnButtonInfo.gridx = 0;
			gbc_pnButtonInfo.gridy = 4;
			pnInfo.add(pnButtonInfo, gbc_pnButtonInfo);
			pnButtonInfo.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 0));
			
			btnLap = new JButton("Lập phiếu");
			btnLap.setIcon(new ImageIcon(new ImageIcon("img/icon/Save.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
			btnLap.setBackground(new Color(11, 101, 140));;
			btnLap.setForeground(new Color(255, 255, 255));
			btnLap.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			        if (modelSanPham.getRowCount() == 0) {
			            ThongBao.baoLoi("Vui lòng nhập sản phẩm nhập");
			            return;
			        }
	
			        String unavailable = "";
	
			        if (ThongBao.cauHoiNghiemTrong("Bạn có muốn lưu phiếu nhập? Một khi đồng ý thì hệ thống không thể khôi phục.") == JOptionPane.YES_OPTION) {
			            ArrayList<ChiTietPhieuNhap> spList = new ArrayList<>();
			            for (int i = 0; i < modelSanPham.getRowCount(); i++) {
			                String maSanPham = modelSanPham.getValueAt(i, 0) + "";
			                if (!bus.kiemTraSP(maSanPham)) {
			                    unavailable += maSanPham + ", ";
			                } else {
			                    LocalDate ngaySX = LocalDate.parse(modelSanPham.getValueAt(i, 2) + "");
			                    LocalDate hanSD = LocalDate.parse(modelSanPham.getValueAt(i, 3) + "");
			                    long giaNhap = Long.parseLong(modelSanPham.getValueAt(i, 4) + "");
			                    long phantram = Long.parseLong(modelSanPham.getValueAt(i, 5) + "");
			                    int soLuong = Integer.parseInt(modelSanPham.getValueAt(i, 6) + "");
			                    String loSX = modelSanPham.getValueAt(i, 1) + "";
			                    String seri = (String) modelSanPham.getValueAt(i, 7);
			                    spList.add(new ChiTietPhieuNhap(maSanPham, seri, loSX, ngaySX, hanSD, giaNhap, phantram, soLuong));
			                 
			                }
			            }
			            
			            if (!unavailable.isEmpty()) {
			                ThongBao.baoLoi("Lập phiếu nhập thất bại\nCác mã sau không tồn tại: " + unavailable);
			                return;
			            }

			            PhieuNhap pn = new PhieuNhap("", LocalDate.parse(txtNgayLap.getText()), maDS, ((NhaCungCap) cbbMaNCC.getSelectedItem()).getMa(), tongTien);
			            if (bus.addPhieuNhap(pn, spList)) {
			            	
			                ThongBao.thongBao("Lập phiếu nhập thành công");
			                if (ThongBao.cauHoi("Bạn có muốn xuất phiếu nhập ra file PDF ngay bây giờ?") == JOptionPane.YES_OPTION) {
			                    JFileChooser saveFileDialog = new JFileChooser(System.getProperty("user.dir"));
			                    saveFileDialog.setDialogTitle("Chọn vị trí cần xuất");
			                    saveFileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Portable Document Format (.pdf)", "pdf"));
			                    saveFileDialog.setAcceptAllFileFilterUsed(false);
			                    if (saveFileDialog.showSaveDialog(PhieuNhapLapGUI.this) == JFileChooser.APPROVE_OPTION) {
			                        String path = saveFileDialog.getSelectedFile().getAbsolutePath();
			                        if (!path.endsWith(".pdf")) path += ".pdf";
			                        if (bus.xuatPDF(pn, spList, path)) {
			                            ThongBao.thongBao("Xuất phiếu nhập ra PDF thành công");
			                            try {
			                                Desktop.getDesktop().open(new java.io.File(path));
			                            } catch (Exception ex) {
			                                ex.printStackTrace();
			                            }
			                        } else {
			                            ThongBao.baoLoi("Xuất phiếu nhập ra PDF thất bại");
			                        }
			                    }
			                }
			                bus.getPhieuNhap();
			                clear();
			            } else {
			                ThongBao.baoLoi("Lập phiếu nhập thất bại");
			            }
			        }
			    }
			});

			pnButtonInfo.add(btnLap);
			btnLap.setFont(new Font("Segoe UI", Font.PLAIN, 18));
			
			JButton btnHuy = new JButton("Hủy bỏ");
			btnHuy.setIcon(new ImageIcon(new ImageIcon("img/icon/Cancel.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
			btnHuy.setBackground(new Color(11, 101, 140));;
			btnHuy.setForeground(new Color(255, 255, 255));
			btnHuy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					clear();
				}
			});
			pnButtonInfo.add(btnHuy);
			btnHuy.setFont(new Font("Segoe UI", Font.PLAIN, 18));
			loadNCC();
			loadSP();
			repainting();
		}
		
		private int timSP(String maSP, String loSX) {
			for (int i=0; i<modelSanPham.getRowCount(); i++)
				if (modelSanPham.getValueAt(i, 0).equals(maSP) && modelSanPham.getValueAt(i, 1).equals(loSX))
					return i;
			return -1;
		}
		
		private void clearSP() {
			txtNgayLap.setText(LocalDate.now().toString());
			txtNguoiLap.setText(maDS);
			txtLoSX.setText("");
			dcNgaySX.setDate(new java.util.Date());
			Calendar calendar = Calendar.getInstance();
	        calendar.setTime(dcHanSD.getDate());
	        calendar.add(Calendar.YEAR, 1);
			txtGiaNhap.setText("");
			txtGiaBan.setText("");
			txtPhanTram.setText("");
			txtSoLuong.setText("");
			lblErrorLoSX.setText("");
			lblErrorNgaySX.setText("");
			lblErrorHanSD.setText("");
			lblErrorGiaNhap.setText("");
			lblErrorGiaBan.setText("");
			lblErorSoLuong.setText("");
			txtSeriBD.setText("");
			txtSeriKT.setText("");
			lblErrorPhanTram.setText("");
			lblErrorSeri.setText("");
		}
		
		private void clear() {
			loadNCC();
			loadSP();
			clearSP();
			tongTien = 0;
			lblTongTien.setText("Tổng tiền: " + tongTien);
			modelSanPham.setRowCount(0);
		}
		
		private void loadSP() {
			ArrayList<SanPham> sanPham = new SanPhamBUS().getAllSp();
			cbbMaSP.removeAllItems();
			for (SanPham sp: sanPham) {
				if (sp.isTrangThai())
				cbbMaSP.addItem(sp);
			}
		}
		
		private void loadNCC() {
			ArrayList<NhaCungCap> nhaCungCap = new NhaCungCapBUS().getAllNCC();
			cbbMaNCC.removeAllItems();
			for (NhaCungCap ncc: nhaCungCap) {
				if (ncc.isTrangThai())
				cbbMaNCC.addItem(ncc);
			}
		}
	
	private boolean checkLoi() {
		boolean loi = false, loiNSX = false, loiHSD = false;
		long giaNhap = -1;
		if (txtLoSX.getText().isEmpty()) {
			lblErrorLoSX.setText("Vui lòng nhập số lô SX");
			loi = true;
		}
		else lblErrorLoSX.setText("");
		
		try {
			if (dcNgaySX.getDate().compareTo(new java.util.Date()) >= 0) {
				lblErrorNgaySX.setText("NSX phải nhỏ hơn hiện tại");
				loi = true;			
			}
			else lblErrorNgaySX.setText("");
		}
		catch (Exception e) {
			lblErrorNgaySX.setText("Vui lòng nhập NSX hợp lệ");
			loi = true; loiNSX = true;
		}
		
		try {
			if (dcHanSD.getDate().compareTo(new java.util.Date()) <= 0) {
				lblErrorHanSD.setText("HSD phải lớn hơn hiện tại");
				loi = true;			
			}
			else lblErrorHanSD.setText("");
		}
		catch (Exception e) {
			lblErrorHanSD.setText("Vui lòng nhập HSD hợp lệ");
			loi = true; loiHSD = true;
		}
		
		if (!lblErrorNgaySX.getText().equals("")) {
			if (!loiNSX && !loiHSD) {
				if (dcNgaySX.getDate().compareTo(dcHanSD.getDate()) >= 0) {
					lblErrorNgaySX.setText("NSX phải nhỏ hơn HSD");
					loi = true;
				}
				else lblErrorNgaySX.setText("");
			}
		}
		
		if (txtGiaNhap.getText().isEmpty()) {
			lblErrorGiaNhap.setText("Vui lòng nhập giá nhập");
			loi = true;
		}
		if (txtPhanTram.getText().isEmpty()) {
			lblErrorPhanTram.setText("Vui lòng nhập phần trăm");
			loi = true;
		}
		if (txtSoLuong.getText().isEmpty()) {
			lblErorSoLuong.setText("Vui lòng nhập số lượng");
			loi = true;
		}
		else {			
			try {
				giaNhap = Long.parseLong(txtGiaNhap.getText());
				if (giaNhap <= 0) throw new Exception();
				else if (giaNhap % 100 != 0) {
					lblErrorGiaNhap.setText("Giá nhập phải chia hết cho 100");
					loi = true;
				}
				else lblErrorGiaNhap.setText("");
			}
			catch (Exception ex) {
				lblErrorGiaNhap.setText("Giá nhập phải là số dương");
				loi = true;
			}		
		}
		if (txtPhanTram.getText().isEmpty()) {
		    lblErrorPhanTram.setText("Vui lòng nhập phần trăm");
		    loi = true;
		} else {
		    try {
		        int phanTram = Integer.parseInt(txtPhanTram.getText());
		        if (phanTram <= 0 || phanTram > 100) throw new Exception();
		        else lblErrorPhanTram.setText("");
		    } catch (Exception ex) {
		        lblErrorPhanTram.setText("Phần trăm phải là số dương từ 1 đến 100");
		        loi = true;
		    }
		}
		if (txtSeriBD.getText().isEmpty() && txtSeriKT.getText().isEmpty()) {
		    lblErrorSeri.setText("Số seri không được bỏ trống");
		    loi = true;
		} else {
		    if (txtSeriBD.getText().isEmpty()) {
		    	lblErrorSeri.setText("Vui lòng nhập số seri bắt đầu");
		        loi = true;
		    } else {
		        try {
		            Long.parseLong(txtSeriBD.getText());
		            lblErrorSeri.setText("");
		        } catch (NumberFormatException e) {
		        	lblErrorSeri.setText("Số seri bắt đầu phải là số");
		            loi = true;
		        }
		    }
		    if (txtSeriKT.getText().isEmpty()) {
		    	lblErrorSeri.setText("Vui lòng nhập số seri kết thúc");
		        loi = true;
		    } else {
		        try {
		            Long.parseLong(txtSeriKT.getText());
		            lblErrorSeri.setText("");
		        } catch (NumberFormatException e) {
		        	lblErrorSeri.setText("Số seri kết thúc phải là số");
		            loi = true;
		        }
		    }
		}
		return loi;
	}
	
	private void loadCTSP(ArrayList<ChiTietPhieuNhap> ctpn) {
		for (ChiTietPhieuNhap pn: ctpn ) {
			int row = timSP(pn.getChiTiet().getMaSanPham(), pn.getChiTiet().getLoSanXuat());
			
			if (row == -1) {
				Object[] data = new Object[] { pn.getChiTiet().getMaSanPham(), pn.getChiTiet().getLoSanXuat(), pn.getChiTiet().getNgaySanXuat(), pn.getChiTiet().getHanSuDung(), pn.getGiaNhap(), pn.getChiTiet().getphantram(), pn.getChiTiet().getSoLuong(),pn.getChiTiet().getMavach() };
				modelSanPham.addRow(data);
			}
			else {
				int soLuong = Integer.parseInt(modelSanPham.getValueAt(row, 6) + "");
				modelSanPham.removeRow(row);
				modelSanPham.insertRow(row, new Object[] { pn.getChiTiet().getMaSanPham(), pn.getChiTiet().getLoSanXuat(), pn.getChiTiet().getNgaySanXuat(), pn.getChiTiet().getHanSuDung(), pn.getGiaNhap(), pn.getChiTiet().getphantram(), soLuong + pn.getChiTiet().getSoLuong(),pn.getChiTiet().getMavach() });				
			}
		}
	}
	
	private void tinhTongTien() {
		long tong = 0;
		for (int i=0; i<modelSanPham.getRowCount(); i++)
			tong = tong + Long.parseLong(modelSanPham.getValueAt(i, 4) + "") * Integer.parseInt(modelSanPham.getValueAt(i, 6) + "");
		tongTien = tong;
	}
	
	void repainting() {
		setBackground(Theme.LIGHT);
		pnInfo.setBackground(Theme.LIGHT);
		Time.filledDateEditor(dcNgaySX);
		Time.filledDateEditor(dcHanSD);
	}
}
