package GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.toedter.calendar.JDateChooser;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import com.toedter.calendar.JDateChooser;

import DTO.*;
import BUS.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.GridLayout;
import java.awt.Image;

public class ThongKeGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private JPanel pnButton;
	private JPanel pnForm;
	private JButton btnDoanhThu;
	private JButton btnSPNhap;
	private JButton btnSPBan;
	private JButton btnNCC;
	private JButton btnCurrent;
	private ArrayList<JButton> menu = new ArrayList<>();	
	private final java.util.Date THANG_DAU_TIEN = Time.parseDate(LocalDate.of(2024,1,1).toString());
	private JButton btnLoiNhuan;
	private JButton btnBanChay;
	
	public ThongKeGUI(String maDS) {
		setBounds(0,0,1000,550);
		setLayout(new BorderLayout(0, 0));
		
		pnButton = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnButton.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setHgap(0);
		flowLayout.setVgap(0);
		add(pnButton, BorderLayout.NORTH);
		
		pnForm = new JPanel();
		add(pnForm, BorderLayout.CENTER);
		pnForm.setLayout(new CardLayout(0, 0));
		
		btnDoanhThu = new JButton("Doanh thu");
		btnDoanhThu.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnDoanhThu.setFocusable(false);
		btnDoanhThu.setBackground(new Color(183, 228, 228));
		btnDoanhThu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrent(btnDoanhThu);
				pnForm.removeAll();
				pnForm.add(new ThongKeDoanhThu());
				pnForm.validate();
				pnForm.repaint();
				repainting();
			}
		});
		btnDoanhThu.setFont(new Font("Segoe UI", Font.ITALIC, 16));
		btnDoanhThu.setPreferredSize(new Dimension(145,35));
		pnButton.add(btnDoanhThu);
		
		btnSPNhap = new JButton("Sản phẩm nhập");
		btnSPNhap.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnSPNhap.setFocusable(false);
		btnSPNhap.setBackground(new Color(183, 228, 228));
		btnSPNhap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrent(btnSPNhap);
				pnForm.removeAll();
				pnForm.add(new ThongKeSanPhamNhap());
				pnForm.validate();
				pnForm.repaint();
				repainting();
			}
		});
		btnSPNhap.setFont(new Font("Segoe UI", Font.ITALIC, 16));
		btnSPNhap.setPreferredSize(new Dimension(145,35));
		pnButton.add(btnSPNhap);
		
		btnSPBan = new JButton("Sản phẩm bán");
		btnSPBan.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnSPBan.setFocusable(false);
		btnSPBan.setBackground(new Color(183, 228, 228));
		btnSPBan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrent(btnSPBan);
				pnForm.removeAll();
				pnForm.add(new ThongKeSanPhamBan());
				pnForm.validate();
				pnForm.repaint();
				repainting();
			}
		});
		btnSPBan.setFont(new Font("Segoe UI", Font.ITALIC, 16));
		btnSPBan.setPreferredSize(new Dimension(145, 35));
		pnButton.add(btnSPBan);
		
		btnNCC = new JButton("Nhà cung cấp");
		btnNCC.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnNCC.setFocusable(false);
		btnNCC.setBackground(new Color(183, 228, 228));
		btnNCC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrent(btnNCC);
				pnForm.removeAll();
				pnForm.add(new ThongKeNCC());
				pnForm.validate();
				pnForm.repaint();
				repainting();
			}
		});
		btnNCC.setFont(new Font("Segoe UI", Font.ITALIC, 16));
		btnNCC.setPreferredSize(new Dimension(145, 35));
		pnButton.add(btnNCC);
		btnLoiNhuan = new JButton("Lợi nhuận");
		
		
		btnLoiNhuan.setFocusable(false);
		btnLoiNhuan.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnLoiNhuan.setBackground(new Color(183, 228, 228));
		btnLoiNhuan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrent(btnLoiNhuan);
				pnForm.removeAll();
				pnForm.add(new ThongKeLoiNhuan());
				pnForm.validate();
				pnForm.repaint();
				repainting();
			}
		});
		btnLoiNhuan.setFont(new Font("Segoe UI", Font.ITALIC, 16));
		btnLoiNhuan.setPreferredSize(new Dimension(145, 35));
		pnButton.add(btnLoiNhuan);
		
		btnBanChay = new JButton("Sản phẩm bán chạy");		
		btnBanChay.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnBanChay.setFocusable(false);
		btnBanChay.setBackground(new Color(183, 228, 228));
		btnBanChay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrent(btnBanChay);
				pnForm.removeAll();
				pnForm.add(new ThongKeSPBC());
				pnForm.validate();
				pnForm.repaint();
				repainting();
			}
		});
		btnBanChay.setFont(new Font("Segoe UI", Font.ITALIC, 16));
		btnBanChay.setPreferredSize(new Dimension(145, 35));
		pnButton.add(btnBanChay);
		
		init();
		btnDoanhThu.doClick();
		repainting();
	}
	
	private void init() {
		menu.add(btnDoanhThu);
		menu.add(btnSPNhap);
		menu.add(btnSPBan);
		menu.add(btnNCC);
		menu.add(btnLoiNhuan);
		menu.add(btnBanChay);
	}
	
	private void setCurrent(JButton btnCurrent) {
		for (JButton btn: menu) {
			btn.setBackground(Theme.MID);
			btn.setBorder(new EmptyBorder(0, 0, 0, 0));
		}
		this.btnCurrent = btnCurrent;
		this.btnCurrent.setBorder(new MatteBorder(0, 0, 2, 0, Theme.DARK));
	}
	
	void repainting() {
		pnButton.setBackground(Theme.MID);
		setCurrent(btnCurrent);
		if (btnCurrent == btnDoanhThu) ((ThongKeDoanhThu)pnForm.getComponent(0)).repainting();
		else if (btnCurrent == btnSPNhap) ((ThongKeSanPhamNhap)pnForm.getComponent(0)).repainting();
		else if (btnCurrent == btnSPBan) ((ThongKeSanPhamBan)pnForm.getComponent(0)).repainting();
		else if (btnCurrent == btnNCC) ((ThongKeNCC)pnForm.getComponent(0)).repainting();
		else if (btnCurrent == btnLoiNhuan) ((ThongKeLoiNhuan)pnForm.getComponent(0)).repainting();
		else if (btnCurrent == btnBanChay) ((ThongKeSPBC)pnForm.getComponent(0)).repainting();
	}
	
	private void paintChart(JFreeChart chart) {
		if (chart != null) {
			chart.setBackgroundPaint(Theme.TABLE);
			chart.getLegend().setItemFont(new Font("Segoe UI", Font.ITALIC, 12));
			chart.getLegend().setItemPaint(Theme.TITLE);
			chart.getLegend().setBackgroundPaint(Theme.TABLE);
			chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 24));
			chart.getTitle().setPaint(Theme.TITLE);
			chart.getCategoryPlot().getRangeAxis().setLabelFont(new Font("Segoe UI", Font.PLAIN, 16));
			chart.getCategoryPlot().getRangeAxis().setLabelPaint(Theme.TITLE);
			chart.getCategoryPlot().getRangeAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 10));
			chart.getCategoryPlot().getRangeAxis().setTickLabelPaint(Theme.TITLE);
			chart.getCategoryPlot().getDomainAxis().setLabelFont(new Font("Segoe UI", Font.PLAIN, 10));
			
			chart.getCategoryPlot().getDomainAxis().setLabelPaint(Theme.TITLE);
			chart.getCategoryPlot().getDomainAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 16));
			chart.getCategoryPlot().getDomainAxis().setTickLabelPaint(Theme.TITLE);
			chart.getCategoryPlot().setBackgroundPaint(Theme.MID);
		}
	}
	
	private class ThongKeSanPhamNhap extends JPanel {
		private static final long serialVersionUID = 1L;
		private ThongKeBUS bus = new ThongKeBUS();
		private JDateChooser dcFrom;
		private JDateChooser dcTo;
		private JPanel pnInfo;
		private JComboBox<SanPham> cbbSanPham;
		private JComboBox<String> cbbNhaCungCap;
		private JRadioButton rdbSoLuong;
		private JTextField txtFrom;
		private JTextField txtTo;
		private final ButtonGroup buttonGroup = new ButtonGroup();
		private JFreeChart chart;
		private ChartPanel panelSP;
		private JTable table;
		private DefaultTableModel model = new DefaultTableModel(
			new Object[][] {}, new String[] {"Thời gian", "Chi tiết"}
		);
		
		ThongKeSanPhamNhap() {
			setBounds(0,0,1000,600);
			setBorder(new EmptyBorder(10, 10, 10, 10));
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[] {0};
			gridBagLayout.rowHeights = new int[] {271, 0};
			gridBagLayout.columnWeights = new double[]{1.0};
			gridBagLayout.rowWeights = new double[]{0.0, 1.0};
			setLayout(gridBagLayout);
			
			pnInfo = new JPanel();
			GridBagConstraints gbc_pnInfo = new GridBagConstraints();
			gbc_pnInfo.fill = GridBagConstraints.VERTICAL;
			gbc_pnInfo.insets = new Insets(0, 0, 20, 0);
			gbc_pnInfo.gridx = 0;
			gbc_pnInfo.gridy = 0;
			add(pnInfo, gbc_pnInfo);
			GridBagLayout gbl_pnInfo = new GridBagLayout();
			gbl_pnInfo.columnWidths = new int[] {60, 75, 60, 15, 110};
			gbl_pnInfo.rowHeights = new int[] {40, 30, 50, 30, 40};
			gbl_pnInfo.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
			gbl_pnInfo.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
			pnInfo.setLayout(gbl_pnInfo);
			
			JLabel lblTitle = new JLabel("THỐNG KÊ SẢN PHẨM NHẬP");
			GridBagConstraints gbc_lblTitle = new GridBagConstraints();
			gbc_lblTitle.gridwidth = 5;
			gbc_lblTitle.fill = GridBagConstraints.VERTICAL;
			gbc_lblTitle.insets = new Insets(0, 0, 20, 0);
			gbc_lblTitle.gridx = 0;
			gbc_lblTitle.gridy = 0;
			pnInfo.add(lblTitle, gbc_lblTitle);
			lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
			
			JLabel lblSanPham = new JLabel("Sản phẩm:");
			GridBagConstraints gbc_lblSanPham = new GridBagConstraints();
			gbc_lblSanPham.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblSanPham.insets = new Insets(0, 0, 25, 5);
			gbc_lblSanPham.gridx = 0;
			gbc_lblSanPham.gridy = 1;
			pnInfo.add(lblSanPham, gbc_lblSanPham);
			lblSanPham.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			cbbSanPham = new JComboBox<>();
			cbbSanPham.setPreferredSize(new Dimension(this.getWidth()/4,30));
			GridBagConstraints gbc_cbbSanPham = new GridBagConstraints();
			gbc_cbbSanPham.fill = GridBagConstraints.BOTH;
			gbc_cbbSanPham.insets = new Insets(0, 0, 25, 20);
			gbc_cbbSanPham.gridx = 1;
			gbc_cbbSanPham.gridy = 1;
			pnInfo.add(cbbSanPham, gbc_cbbSanPham);
			cbbSanPham.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			JLabel lblNhaCungCap = new JLabel("Nhà cung cấp:");
			GridBagConstraints gbc_lblNhaCungCap = new GridBagConstraints();
			gbc_lblNhaCungCap.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblNhaCungCap.insets = new Insets(0, 0, 25, 5);
			gbc_lblNhaCungCap.gridx = 2;
			gbc_lblNhaCungCap.gridy = 1;
			pnInfo.add(lblNhaCungCap, gbc_lblNhaCungCap);
			lblNhaCungCap.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			cbbNhaCungCap = new JComboBox<>(new String[] {"Tất cả"});
			cbbNhaCungCap.setPreferredSize(new Dimension(this.getWidth()/4,30));
			GridBagConstraints gbc_cbbNhaCungCap = new GridBagConstraints();
			gbc_cbbNhaCungCap.gridwidth = 2;
			gbc_cbbNhaCungCap.fill = GridBagConstraints.BOTH;
			gbc_cbbNhaCungCap.insets = new Insets(0, 0, 25, 0);
			gbc_cbbNhaCungCap.gridx = 3;
			gbc_cbbNhaCungCap.gridy = 1;
			pnInfo.add(cbbNhaCungCap, gbc_cbbNhaCungCap);
			cbbNhaCungCap.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			JLabel lblDateFrom = new JLabel("Ngày bắt đầu:");
			GridBagConstraints gbc_lblDateFrom = new GridBagConstraints();
			gbc_lblDateFrom.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblDateFrom.insets = new Insets(0, 0, 25, 5);
			gbc_lblDateFrom.gridx = 0;
			gbc_lblDateFrom.gridy = 2;
			pnInfo.add(lblDateFrom, gbc_lblDateFrom);
			lblDateFrom.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			dcFrom = new JDateChooser(THANG_DAU_TIEN, "yyyy-MM");
			GridBagConstraints gbc_dcFrom = new GridBagConstraints();
			gbc_dcFrom.fill = GridBagConstraints.BOTH;
			gbc_dcFrom.insets = new Insets(0, 0, 25, 20);
			gbc_dcFrom.gridx = 1;
			gbc_dcFrom.gridy = 2;
			gbc_dcFrom.gridwidth=1;
			pnInfo.add(dcFrom, gbc_dcFrom);
			dcFrom.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			JLabel lblDateTo = new JLabel("Ngày kết thúc:");
			GridBagConstraints gbc_lblDateTo = new GridBagConstraints();
			gbc_lblDateTo.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblDateTo.insets = new Insets(0, 0, 25, 5);
			gbc_lblDateTo.gridx = 2;
			gbc_lblDateTo.gridy = 2;
			pnInfo.add(lblDateTo, gbc_lblDateTo);
			lblDateTo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			dcTo = new JDateChooser(Time.parseDate(LocalDate.now().plusMonths(1).toString()), "yyyy-MM");
			GridBagConstraints gbc_dcTo = new GridBagConstraints();
			gbc_dcTo.gridwidth = 2;
			gbc_dcTo.fill = GridBagConstraints.BOTH;
			gbc_dcTo.insets = new Insets(0, 0, 25, 0);
			gbc_dcTo.gridx = 3;
			gbc_dcTo.gridy = 2;
			pnInfo.add(dcTo, gbc_dcTo);
			dcTo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			rdbSoLuong = new JRadioButton("Theo số lượng", true);
			GridBagConstraints gbc_rdbSoLuong = new GridBagConstraints();
			gbc_rdbSoLuong.fill = GridBagConstraints.HORIZONTAL;
			gbc_rdbSoLuong.insets = new Insets(0, 0, 25, 5);
			gbc_rdbSoLuong.gridx = 0;
			gbc_rdbSoLuong.gridy = 3;
			pnInfo.add(rdbSoLuong, gbc_rdbSoLuong);
			buttonGroup.add(rdbSoLuong);
			rdbSoLuong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			JRadioButton rdbGiaNhap = new JRadioButton("Theo giá nhập");
			GridBagConstraints gbc_rdbGiaNhap = new GridBagConstraints();
			gbc_rdbGiaNhap.insets = new Insets(0, 0, 25, 20);
			gbc_rdbGiaNhap.gridx = 1;
			gbc_rdbGiaNhap.gridy = 3;
			pnInfo.add(rdbGiaNhap, gbc_rdbGiaNhap);
			buttonGroup.add(rdbGiaNhap);
			rdbGiaNhap.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			txtFrom = new JTextField("0");
			GridBagConstraints gbc_txtFrom = new GridBagConstraints();
			gbc_txtFrom.fill = GridBagConstraints.BOTH;
			gbc_txtFrom.insets = new Insets(0, 0, 25, 5);
			gbc_txtFrom.gridx = 2;
			gbc_txtFrom.gridy = 3;
			pnInfo.add(txtFrom, gbc_txtFrom);
			txtFrom.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			txtFrom.setColumns(10);
			
			JLabel lblTo = new JLabel("-");
			GridBagConstraints gbc_lblTo = new GridBagConstraints();
			gbc_lblTo.fill = GridBagConstraints.BOTH;
			gbc_lblTo.insets = new Insets(0, 0, 25, 5);
			gbc_lblTo.gridx = 3;
			gbc_lblTo.gridy = 3;
			pnInfo.add(lblTo, gbc_lblTo);
			lblTo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			txtTo = new JTextField("0");
			GridBagConstraints gbc_txtTo = new GridBagConstraints();
			gbc_txtTo.fill = GridBagConstraints.BOTH;
			gbc_txtTo.insets = new Insets(0, 0, 25, 0);
			gbc_txtTo.gridx = 4;
			gbc_txtTo.gridy = 3;
			pnInfo.add(txtTo, gbc_txtTo);
			txtTo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			txtTo.setColumns(10);
			
			JButton btnThongKe = new JButton("Thống kê");
			GridBagConstraints gbc_btnThongKe = new GridBagConstraints();
			gbc_btnThongKe.fill = GridBagConstraints.VERTICAL;
			gbc_btnThongKe.gridwidth = 5;
			gbc_btnThongKe.gridx = 0;
			gbc_btnThongKe.gridy = 4;
			pnInfo.add(btnThongKe, gbc_btnThongKe);
			btnThongKe.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					LocalDate dateFrom, dateTo;
					int numFrom, numTo;
					try {
						dateFrom = Time.toLocalDate(dcFrom.getDate());
					}
					catch (Exception ex) {
						ThongBao.baoLoi("Vui lòng nhập ngày bắt đầu hợp lệ");
						return;
					}
					
					try {
						dateTo = Time.toLocalDate(dcTo.getDate());
						if (dateFrom.isAfter(dateTo))
							ThongBao.baoLoi("Vui lòng nhập ngày bắt đầu sớm hơn ngày kết thúc");
						if(dateFrom.plusMonths(8).isBefore(dateTo)) {
							
							ThongBao.baoLoi("Vui lòng nhập khoảng thời gian không quá 8 tháng ");
							
							
						}
					}
					catch (Exception ex) {
						ThongBao.baoLoi("Vui lòng nhập ngày kết thúc hợp lệ");
						return;
					}
					
					try {
						numFrom = Integer.parseInt(txtFrom.getText());
						if (numFrom < 0) {
							ThongBao.baoLoi("Vui lòng số bên trái lớn hơn 0");
							return;
						}
						numTo = Integer.parseInt(txtTo.getText());
						if (numTo < 0) {
							ThongBao.baoLoi("Vui lòng số bên phải lớn hơn 0");
							return;
						}
						else if (numFrom >= numTo) {
							ThongBao.baoLoi("Vui lòng số bên phải lớn hơn số bên trái");
							return;						
						}
					}
					catch (Exception ex) {
						ThongBao.baoLoi("Vui lòng nhập số liệu hợp lệ");
						return;
					}
									
					try {
						String maSP = ((SanPham)cbbSanPham.getSelectedItem()).getMa();
						String ncc = cbbNhaCungCap.getSelectedItem().toString();
						if (!ncc.equals("Tất cả")) ncc = ncc.substring(0, ncc.indexOf("-"));
						panelSP.setChart(spNhap(bus.sanPhamNhap(maSP, ncc, dateFrom, dateTo, numFrom, numTo, rdbSoLuong.isSelected() ? rdbSoLuong.getText() : rdbGiaNhap.getText())));
					}
					catch (Exception ex) {
						ex.printStackTrace();
						ThongBao.baoLoi("Vui lòng kiểm tra lại nhập liệu");
					}
				}
			});
			btnThongKe.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			JPanel pnResult = new JPanel();
			GridBagConstraints gbc_pnResult = new GridBagConstraints();
			gbc_pnResult.fill = GridBagConstraints.BOTH;
			gbc_pnResult.gridx = 0;
			gbc_pnResult.gridy = 1;
			add(pnResult, gbc_pnResult);
			pnResult.setLayout(new GridLayout(0, 2, 10, 0));
			pnResult.setOpaque(false);
			
			panelSP = new ChartPanel(null);
			panelSP.setBorder(new LineBorder(new Color(200,200,200), 1));
			pnResult.add(panelSP);
			
			JScrollPane scrollPane = new JScrollPane();
			pnResult.add(scrollPane);
			
			table = new JTable(model);
			table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 16));
			scrollPane.setViewportView(table);
			loadCBB();
		}
		
		private void loadCBB() {
			ArrayList<SanPham> sanPham = new SanPhamBUS().getAllSp();
			for (SanPham sp: sanPham) cbbSanPham.addItem(sp);		

			ArrayList<NhaCungCap> nhaCungCap = new NhaCungCapBUS().getAllNCC();
			for (NhaCungCap ncc: nhaCungCap) cbbNhaCungCap.addItem(ncc + "");
		}
		
		private JFreeChart spNhap(HashMap<String, Object> spNhap) {
			DefaultCategoryDataset spDataset = new DefaultCategoryDataset();
			model.setRowCount(0);
			
			for (String date: spNhap.keySet()) {
				Object soLuong = spNhap.get(date);
				spDataset.setValue((int)soLuong, rdbSoLuong.isSelected() ? "Số lượng" : "Tổng tiền", date.toString());
				model.addRow(new Object[] {date, soLuong});
			}
			
			JFreeChart chart = ChartFactory.createBarChart(
					"SẢN LƯỢNG NHẬP CỦA SẢN PHẨM " + cbbSanPham.getSelectedItem().toString(),
					"Thời gian", "Tiền (VND)", spDataset,
					PlotOrientation.VERTICAL, true, true, false);
			paintChart(chart);
			
			return chart;
		}
		
		void repainting() {
			setBackground(Theme.LIGHT);
			panelSP.setBackground(Theme.TABLE);
			pnInfo.setBackground(Theme.LIGHT);
			Time.filledDateEditor(dcFrom);
			Time.filledDateEditor(dcTo);
			paintChart(chart);
		}
	}
	
	private class ThongKeNCC extends JPanel {
		private static final long serialVersionUID = 1L;
		private ThongKeBUS bus = new ThongKeBUS();
		private JDateChooser dcFrom;
		private JDateChooser dcTo;
		private JPanel pnInfo;
		private JComboBox<NhaCungCap> cbbNhaCungCap;
		private JRadioButton rdbSoLuong;
		private JTextField txtFrom;
		private JTextField txtTo;
		private final ButtonGroup buttonGroup = new ButtonGroup();
		private JFreeChart chart;
		private ChartPanel panelNCC;
		private JTable table;
		private DefaultTableModel model = new DefaultTableModel(
			new Object[][] {}, new String[] {"Sản phẩm", "Chi tiết"}
		);
		
		ThongKeNCC() {
			setBounds(0,0,1000,600);
			setBorder(new EmptyBorder(10, 10, 10, 10));
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[] {0};
			gridBagLayout.rowHeights = new int[] {271, 0};
			gridBagLayout.columnWeights = new double[]{1.0};
			gridBagLayout.rowWeights = new double[]{0.0, 1.0};
			setLayout(gridBagLayout);
			
			pnInfo = new JPanel();
			pnInfo.setBackground(new Color(239, 249, 249));
			GridBagConstraints gbc_pnInfo = new GridBagConstraints();
			gbc_pnInfo.fill = GridBagConstraints.VERTICAL;
			gbc_pnInfo.insets = new Insets(0, 0, 20, 0);
			gbc_pnInfo.gridx = 0;
			gbc_pnInfo.gridy = 0;
			add(pnInfo, gbc_pnInfo);
			GridBagLayout gbl_pnInfo = new GridBagLayout();
			gbl_pnInfo.columnWidths = new int[] {60, 75, 60, 15, 110};
			gbl_pnInfo.rowHeights = new int[] {40, 30, 30, 30, 40};
			gbl_pnInfo.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
			gbl_pnInfo.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
			pnInfo.setLayout(gbl_pnInfo);
			
			JLabel lblTitle = new JLabel("THỐNG KÊ NHÀ CUNG CẤP");
			GridBagConstraints gbc_lblTitle = new GridBagConstraints();
			gbc_lblTitle.gridwidth = 5;
			gbc_lblTitle.fill = GridBagConstraints.VERTICAL;
			gbc_lblTitle.insets = new Insets(0, 0, 20, 0);
			gbc_lblTitle.gridx = 0;
			gbc_lblTitle.gridy = 0;
			pnInfo.add(lblTitle, gbc_lblTitle);
			lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
			
			JLabel lblNhaCungCap = new JLabel("Nhà cung cấp");
			GridBagConstraints gbc_lblNhaCungCap = new GridBagConstraints();
			gbc_lblNhaCungCap.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblNhaCungCap.insets = new Insets(0, 0, 25, 5);
			gbc_lblNhaCungCap.gridx = 0;
			gbc_lblNhaCungCap.gridy = 1;
			pnInfo.add(lblNhaCungCap, gbc_lblNhaCungCap);
			lblNhaCungCap.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			cbbNhaCungCap = new JComboBox<>();
			cbbNhaCungCap.setPreferredSize(new Dimension(this.getWidth()/4,30));
			GridBagConstraints gbc_cbbNhaCungCap = new GridBagConstraints();
			gbc_cbbNhaCungCap.gridwidth = 4;
			gbc_cbbNhaCungCap.fill = GridBagConstraints.BOTH;
			gbc_cbbNhaCungCap.insets = new Insets(0, 0, 25, 5);
			gbc_cbbNhaCungCap.gridx = 1;
			gbc_cbbNhaCungCap.gridy = 1;
			pnInfo.add(cbbNhaCungCap, gbc_cbbNhaCungCap);
			cbbNhaCungCap.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			JLabel lblDateFrom = new JLabel("Ngày bắt đầu:");
			GridBagConstraints gbc_lblDateFrom = new GridBagConstraints();
			gbc_lblDateFrom.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblDateFrom.insets = new Insets(0, 0, 25, 5);
			gbc_lblDateFrom.gridx = 0;
			gbc_lblDateFrom.gridy = 2;
			pnInfo.add(lblDateFrom, gbc_lblDateFrom);
			lblDateFrom.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			dcFrom = new JDateChooser(THANG_DAU_TIEN, "yyyy-MM");
			dcFrom.setPreferredSize(new Dimension(0,30));
			GridBagConstraints gbc_dcFrom = new GridBagConstraints();
			gbc_dcFrom.fill = GridBagConstraints.BOTH;
			gbc_dcFrom.insets = new Insets(0, 0, 25, 20);
			gbc_dcFrom.gridx = 1;
			gbc_dcFrom.gridy = 2;
			gbc_dcFrom.gridwidth=2;
			pnInfo.add(dcFrom, gbc_dcFrom);
			dcFrom.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			JLabel lblDateTo = new JLabel("Ngày kết thúc:");
			GridBagConstraints gbc_lblDateTo = new GridBagConstraints();
			gbc_lblDateTo.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblDateTo.insets = new Insets(0, 0, 25, 5);
			gbc_lblDateTo.gridx = 3;
			gbc_lblDateTo.gridy = 2;
			
			pnInfo.add(lblDateTo, gbc_lblDateTo);
			lblDateTo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			dcTo = new JDateChooser(Time.parseDate(LocalDate.now().plusMonths(1).toString()), "yyyy-MM");
			GridBagConstraints gbc_dcTo = new GridBagConstraints();
			gbc_dcTo.gridwidth = 2;
			gbc_dcTo.fill = GridBagConstraints.BOTH;
			gbc_dcTo.insets = new Insets(0, 0, 25, 0);
			gbc_dcTo.gridx = 4;
			gbc_dcTo.gridy = 2;
			pnInfo.add(dcTo, gbc_dcTo);
			dcTo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			rdbSoLuong = new JRadioButton("Theo số lượng", true);
			GridBagConstraints gbc_rdbSoLuong = new GridBagConstraints();
			gbc_rdbSoLuong.fill = GridBagConstraints.HORIZONTAL;
			gbc_rdbSoLuong.insets = new Insets(0, 0, 25, 5);
			gbc_rdbSoLuong.gridx = 0;
			gbc_rdbSoLuong.gridy = 3;
			pnInfo.add(rdbSoLuong, gbc_rdbSoLuong);
			buttonGroup.add(rdbSoLuong);
			rdbSoLuong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			JRadioButton rdbGiaNhap = new JRadioButton("Theo giá nhập");
			GridBagConstraints gbc_rdbGiaNhap = new GridBagConstraints();
			gbc_rdbGiaNhap.insets = new Insets(0, 0, 25, 20);
			gbc_rdbGiaNhap.gridx = 1;
			gbc_rdbGiaNhap.gridy = 3;
			pnInfo.add(rdbGiaNhap, gbc_rdbGiaNhap);
			buttonGroup.add(rdbGiaNhap);
			rdbGiaNhap.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			txtFrom = new JTextField("0");
			GridBagConstraints gbc_txtFrom = new GridBagConstraints();
			gbc_txtFrom.fill = GridBagConstraints.BOTH;
			gbc_txtFrom.insets = new Insets(0, 0, 25, 5);
			gbc_txtFrom.gridx = 2;
			gbc_txtFrom.gridy = 3;
			pnInfo.add(txtFrom, gbc_txtFrom);
			txtFrom.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			txtFrom.setColumns(10);
			
			JLabel lblTo = new JLabel("-");
			GridBagConstraints gbc_lblTo = new GridBagConstraints();
			gbc_lblTo.fill = GridBagConstraints.BOTH;
			gbc_lblTo.insets = new Insets(0, 0, 25, 5);
			gbc_lblTo.gridx = 3;
			gbc_lblTo.gridy = 3;
			pnInfo.add(lblTo, gbc_lblTo);
			lblTo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			txtTo = new JTextField("0");
			GridBagConstraints gbc_txtTo = new GridBagConstraints();
			gbc_txtTo.fill = GridBagConstraints.BOTH;
			gbc_txtTo.insets = new Insets(0, 0, 25, 0);
			gbc_txtTo.gridx = 4;
			gbc_txtTo.gridy = 3;
			pnInfo.add(txtTo, gbc_txtTo);
			txtTo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			txtTo.setColumns(10);
			
			JButton btnThongKe = new JButton("Thống kê");
			GridBagConstraints gbc_btnThongKe = new GridBagConstraints();
			gbc_btnThongKe.fill = GridBagConstraints.VERTICAL;
			gbc_btnThongKe.gridwidth = 5;
			gbc_btnThongKe.gridx = 0;
			gbc_btnThongKe.gridy = 4;
			pnInfo.add(btnThongKe, gbc_btnThongKe);
			btnThongKe.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					LocalDate dateFrom, dateTo;
					int numFrom, numTo;
					try {
						dateFrom = Time.toLocalDate(dcFrom.getDate());
					}
					catch (Exception ex) {
						ThongBao.baoLoi("Vui lòng nhập ngày bắt đầu hợp lệ");
						return;
					}
					
					try {
						dateTo = Time.toLocalDate(dcTo.getDate());
						if (dateFrom.isAfter(dateTo))
							ThongBao.baoLoi("Vui lòng nhập ngày bắt đầu sớm hơn ngày kết thúc");
					}
					catch (Exception ex) {
						ThongBao.baoLoi("Vui lòng nhập ngày kết thúc hợp lệ");
						return;
					}
					
					try {
						numFrom = Integer.parseInt(txtFrom.getText());
						if (numFrom < 0) {
							ThongBao.baoLoi("Vui lòng số bên trái lớn hơn 0");
							return;
						}
						numTo = Integer.parseInt(txtTo.getText());
						if (numTo < 0) {
							ThongBao.baoLoi("Vui lòng số bên phải lớn hơn 0");
							return;
						}
						else if (numFrom >= numTo) {
							ThongBao.baoLoi("Vui lòng số bên phải lớn hơn số bên trái");
							return;						
						}
					}
					catch (Exception ex) {
						ThongBao.baoLoi("Vui lòng nhập số liệu hợp lệ");
						return;
					}
									
					try {
						String maNCC = ((NhaCungCap)cbbNhaCungCap.getSelectedItem()).getMa();
						HashMap<String, Object> nccNhap = bus.nhaCungCapNhap(maNCC, dateFrom, dateTo, numFrom, numTo, rdbSoLuong.isSelected() ? rdbSoLuong.getText() : rdbGiaNhap.getText());
						ArrayList<Object[]> spNhap = bus.sanPhamTheoNCC(maNCC, dateFrom, dateTo, numFrom, numTo, rdbSoLuong.isSelected() ? rdbSoLuong.getText() : rdbGiaNhap.getText());
						panelNCC.setChart(load(nccNhap, spNhap));
					}
					catch (Exception ex) {
						ex.printStackTrace();
						ThongBao.baoLoi("Vui lòng kiểm tra lại nhập liệu");
					}
				}
			});
			btnThongKe.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			JPanel pnResult = new JPanel();
			pnResult.setOpaque(false);
			GridBagConstraints gbc_pnResult = new GridBagConstraints();
			gbc_pnResult.fill = GridBagConstraints.BOTH;
			gbc_pnResult.gridx = 0;
			gbc_pnResult.gridy = 1;
			add(pnResult, gbc_pnResult);
			pnResult.setLayout(new GridLayout(0, 2, 10, 0));
			
			panelNCC = new ChartPanel(null);
			panelNCC.setBackground(Color.white);
			panelNCC.setBorder(new LineBorder(new Color(200,200,200), 1));
			pnResult.add(panelNCC);
			
			JScrollPane scrollPane = new JScrollPane();
			pnResult.add(scrollPane);
			
			table = new JTable(model);
			table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 16));
			scrollPane.setViewportView(table);
			loadCBB();
		}
		
		private void loadCBB() {
			ArrayList<NhaCungCap> nhaCungCap = new NhaCungCapBUS().getAllNCC();
			for (NhaCungCap ncc: nhaCungCap) cbbNhaCungCap.addItem(ncc);
		}
		
		private JFreeChart load(HashMap<String, Object> nccNhap, ArrayList<Object[]> spNhap) {
			DefaultCategoryDataset nccDataset = new DefaultCategoryDataset();
			model.setRowCount(0);
			
			for (String date: nccNhap.keySet()) {
				Object soLuong = nccNhap.get(date);
				nccDataset.setValue((int)soLuong, rdbSoLuong.isSelected() ? "Số lượng" : "Tổng tiền", date.toString());
			}
			
			for (Object[] sp: spNhap) model.addRow(new Object[] { sp[0], sp[1] });
			
			chart = ChartFactory.createBarChart(
					"SẢN LƯỢNG NHẬP CỦA NHÀ CUNG CẤP " + cbbNhaCungCap.getSelectedItem().toString(),
					"Thời gian", "Tiền (VND)", nccDataset,
					PlotOrientation.VERTICAL, true, true, false);
			chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 24));
			chart.getCategoryPlot().getRangeAxis().setLabelFont(new Font("Segoe UI", Font.PLAIN, 16));
			chart.getCategoryPlot().getDomainAxis().setLabelFont(new Font("Segoe UI", Font.PLAIN, 16));
			paintChart(chart);
			
			return chart;
		}
		
		void repainting() {
			setBackground(Theme.LIGHT);
			panelNCC.setBackground(Theme.TABLE);
			pnInfo.setBackground(Theme.LIGHT);
			Time.filledDateEditor(dcFrom);
			Time.filledDateEditor(dcTo);
			paintChart(chart);
		}
	}
	
	private class ThongKeSanPhamBan extends JPanel {
		private static final long serialVersionUID = 1L;
		private JPanel pnInfo;
		private ThongKeBUS bus = new ThongKeBUS();
		private JDateChooser dcFrom;
		private JDateChooser dcTo;
		private JComboBox<SanPham> cbbSanPham;
		private JFreeChart chart;
		
		private JRadioButton rdbSoLuong;
		private JTextField txtFrom;
		private JTextField txtTo;
		private final ButtonGroup buttonGroup = new ButtonGroup();
		private ChartPanel panelSP;
		private JTable table;
		private DefaultTableModel model = new DefaultTableModel(
			new Object[][] {}, new String[] {"Thời gian", "Chi tiết"}
		);
		
		ThongKeSanPhamBan() {
			setBounds(0,0,1000,600);
			setBorder(new EmptyBorder(10, 10, 10, 10));
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[] {0};
			gridBagLayout.rowHeights = new int[] {271, 0};
			gridBagLayout.columnWeights = new double[]{1.0};
			gridBagLayout.rowWeights = new double[]{0.0, 1.0};
			setLayout(gridBagLayout);
			
			pnInfo = new JPanel();
			GridBagConstraints gbc_pnInfo = new GridBagConstraints();
			gbc_pnInfo.fill = GridBagConstraints.VERTICAL;
			gbc_pnInfo.insets = new Insets(0, 0, 20, 0);
			gbc_pnInfo.gridx = 0;
			gbc_pnInfo.gridy = 0;
			add(pnInfo, gbc_pnInfo);
			GridBagLayout gbl_pnInfo = new GridBagLayout();
			gbl_pnInfo.columnWidths = new int[] {60, 75, 60, 15, 110};
			gbl_pnInfo.rowHeights = new int[] {40, 30, 30, 30, 40};
			gbl_pnInfo.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
			gbl_pnInfo.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
			pnInfo.setLayout(gbl_pnInfo);
			
			JLabel lblTitle = new JLabel("THỐNG KÊ SẢN PHẨM BÁN");
			GridBagConstraints gbc_lblTitle = new GridBagConstraints();
			gbc_lblTitle.gridwidth = 5;
			gbc_lblTitle.fill = GridBagConstraints.VERTICAL;
			gbc_lblTitle.insets = new Insets(0, 0, 20, 0);
			gbc_lblTitle.gridx = 0;
			gbc_lblTitle.gridy = 0;
			pnInfo.add(lblTitle, gbc_lblTitle);
			lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
			
			JLabel lblSanPham = new JLabel("Sản phẩm:");
			GridBagConstraints gbc_lblSanPham = new GridBagConstraints();
			gbc_lblSanPham.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblSanPham.insets = new Insets(0, 0, 25, 5);
			gbc_lblSanPham.gridx = 0;
			gbc_lblSanPham.gridy = 1;
			pnInfo.add(lblSanPham, gbc_lblSanPham);
			lblSanPham.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			cbbSanPham = new JComboBox<>();
			cbbSanPham.setPreferredSize(new Dimension(this.getWidth()/4,30));
			GridBagConstraints gbc_cbbSanPham = new GridBagConstraints();
			gbc_cbbSanPham.gridwidth = 4;
			gbc_cbbSanPham.fill = GridBagConstraints.BOTH;
			gbc_cbbSanPham.insets = new Insets(0, 0, 25, 0);
			gbc_cbbSanPham.gridx = 1;
			gbc_cbbSanPham.gridy = 1;
			pnInfo.add(cbbSanPham, gbc_cbbSanPham);
			cbbSanPham.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			JLabel lblDateFrom = new JLabel("Ngày bắt đầu:");
			GridBagConstraints gbc_lblDateFrom = new GridBagConstraints();
			gbc_lblDateFrom.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblDateFrom.insets = new Insets(0, 0, 25, 5);
			gbc_lblDateFrom.gridx = 0;
			gbc_lblDateFrom.gridy = 2;
			pnInfo.add(lblDateFrom, gbc_lblDateFrom);
			lblDateFrom.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			dcFrom = new JDateChooser(THANG_DAU_TIEN, "yyyy-MM");
			dcFrom.setPreferredSize(new Dimension(0,30));
			GridBagConstraints gbc_dcFrom = new GridBagConstraints();
			gbc_dcFrom.fill = GridBagConstraints.BOTH;
			gbc_dcFrom.insets = new Insets(0, 0, 25, 20);
			gbc_dcFrom.gridx = 1;
			gbc_dcFrom.gridy = 2;
			pnInfo.add(dcFrom, gbc_dcFrom);
			dcFrom.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			JLabel lblDateTo = new JLabel("Ngày kết thúc:");
			GridBagConstraints gbc_lblDateTo = new GridBagConstraints();
			gbc_lblDateTo.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblDateTo.insets = new Insets(0, 0, 25, 5);
			gbc_lblDateTo.gridx = 2;
			gbc_lblDateTo.gridy = 2;
			pnInfo.add(lblDateTo, gbc_lblDateTo);
			lblDateTo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			dcTo = new JDateChooser(Time.parseDate(LocalDate.now().plusMonths(1).toString()), "yyyy-MM");
			GridBagConstraints gbc_dcTo = new GridBagConstraints();
			gbc_dcTo.gridwidth = 2;
			gbc_dcTo.fill = GridBagConstraints.BOTH;
			gbc_dcTo.insets = new Insets(0, 0, 25, 0);
			gbc_dcTo.gridx = 3;
			gbc_dcTo.gridy = 2;
			pnInfo.add(dcTo, gbc_dcTo);
			dcTo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			rdbSoLuong = new JRadioButton("Theo số lượng", true);
			GridBagConstraints gbc_rdbSoLuong = new GridBagConstraints();
			gbc_rdbSoLuong.fill = GridBagConstraints.HORIZONTAL;
			gbc_rdbSoLuong.insets = new Insets(0, 0, 25, 5);
			gbc_rdbSoLuong.gridx = 0;
			gbc_rdbSoLuong.gridy = 3;
			pnInfo.add(rdbSoLuong, gbc_rdbSoLuong);
			buttonGroup.add(rdbSoLuong);
			rdbSoLuong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			JRadioButton rdbGiaBan = new JRadioButton("Theo giá bán");
			GridBagConstraints gbc_rdbGiaBan = new GridBagConstraints();
			gbc_rdbGiaBan.insets = new Insets(0, 0, 25, 20);
			gbc_rdbGiaBan.gridx = 1;
			gbc_rdbGiaBan.gridy = 3;
			pnInfo.add(rdbGiaBan, gbc_rdbGiaBan);
			buttonGroup.add(rdbGiaBan);
			rdbGiaBan.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			txtFrom = new JTextField("0");
			GridBagConstraints gbc_txtFrom = new GridBagConstraints();
			gbc_txtFrom.fill = GridBagConstraints.BOTH;
			gbc_txtFrom.insets = new Insets(0, 0, 25, 5);
			gbc_txtFrom.gridx = 2;
			gbc_txtFrom.gridy = 3;
			pnInfo.add(txtFrom, gbc_txtFrom);
			txtFrom.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			txtFrom.setColumns(10);
			
			JLabel lblTo = new JLabel("-");
			GridBagConstraints gbc_lblTo = new GridBagConstraints();
			gbc_lblTo.fill = GridBagConstraints.BOTH;
			gbc_lblTo.insets = new Insets(0, 0, 25, 5);
			gbc_lblTo.gridx = 3;
			gbc_lblTo.gridy = 3;
			pnInfo.add(lblTo, gbc_lblTo);
			lblTo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			txtTo = new JTextField("0");
			GridBagConstraints gbc_txtTo = new GridBagConstraints();
			gbc_txtTo.fill = GridBagConstraints.BOTH;
			gbc_txtTo.insets = new Insets(0, 0, 25, 0);
			gbc_txtTo.gridx = 4;
			gbc_txtTo.gridy = 3;
			pnInfo.add(txtTo, gbc_txtTo);
			txtTo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			txtTo.setColumns(10);
			
			JButton btnThongKe = new JButton("Thống kê");
			GridBagConstraints gbc_btnThongKe = new GridBagConstraints();
			gbc_btnThongKe.fill = GridBagConstraints.VERTICAL;
			gbc_btnThongKe.gridwidth = 5;
			gbc_btnThongKe.gridx = 0;
			gbc_btnThongKe.gridy = 4;
			pnInfo.add(btnThongKe, gbc_btnThongKe);
			btnThongKe.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					LocalDate dateFrom, dateTo;
					int numFrom, numTo;
					try {
						dateFrom = Time.toLocalDate(dcFrom.getDate());
					}
					catch (Exception ex) {
						ThongBao.baoLoi("Vui lòng nhập ngày bắt đầu hợp lệ");
						return;
					}
					
					try {
						dateTo = Time.toLocalDate(dcTo.getDate());
						if (dateFrom.isAfter(dateTo))
							ThongBao.baoLoi("Vui lòng nhập ngày bắt đầu sớm hơn ngày kết thúc");
					}
					catch (Exception ex) {
						ThongBao.baoLoi("Vui lòng nhập ngày kết thúc hợp lệ");
						return;
					}
					
					try {
						numFrom = Integer.parseInt(txtFrom.getText());
						if (numFrom < 0) {
							ThongBao.baoLoi("Vui lòng số bên trái lớn hơn 0");
							return;
						}
						numTo = Integer.parseInt(txtTo.getText());
						if (numTo < 0) {
							ThongBao.baoLoi("Vui lòng số bên phải lớn hơn 0");
							return;
						}
						else if (numFrom >= numTo) {
							ThongBao.baoLoi("Vui lòng số bên phải lớn hơn số bên trái");
							return;						
						}
					}
					catch (Exception ex) {
						ThongBao.baoLoi("Vui lòng nhập số liệu hợp lệ");
						return;
					}
									
					try {
						String maSP = ((SanPham)cbbSanPham.getSelectedItem()).getMa();
						panelSP.setChart(spNhap(bus.sanPhamBan(maSP, dateFrom, dateTo, numFrom, numTo,
							rdbSoLuong.isSelected() ? rdbSoLuong.getText() : rdbGiaBan.getText())));
					}
					catch (Exception ex) {
						ex.printStackTrace();
						ThongBao.baoLoi("Vui lòng kiểm tra lại nhập liệu");
					}
				}
			});
			btnThongKe.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			JPanel pnResult = new JPanel();
			pnResult.setOpaque(false);
			GridBagConstraints gbc_pnResult = new GridBagConstraints();
			gbc_pnResult.fill = GridBagConstraints.BOTH;
			gbc_pnResult.gridx = 0;
			gbc_pnResult.gridy = 1;
			add(pnResult, gbc_pnResult);
			pnResult.setLayout(new GridLayout(0, 2, 10, 0));
			
			panelSP = new ChartPanel(null);
			panelSP.setBackground(Color.white);
			panelSP.setBorder(new LineBorder(new Color(200,200,200), 1));
			pnResult.add(panelSP);
			
			JScrollPane scrollPane = new JScrollPane();
			pnResult.add(scrollPane);
			
			table = new JTable(model);
			table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 16));
			scrollPane.setViewportView(table);
			loadCBB();
		}
		
		private void loadCBB() {
			ArrayList<SanPham> sanPham = new SanPhamBUS().getAllSp();
			for (SanPham sp: sanPham) cbbSanPham.addItem(sp);
		}
		
		private JFreeChart spNhap(HashMap<String, Object> spNhap) {
			DefaultCategoryDataset spDataset = new DefaultCategoryDataset();
			model.setRowCount(0);
			
			for (String date: spNhap.keySet()) {
				Object soLuong = spNhap.get(date);
				spDataset.setValue((int)soLuong, rdbSoLuong.isSelected() ? "Số lượng" : "Tổng tiền", date.toString());
				model.addRow(new Object[] {date, soLuong});
			}
			
			chart = ChartFactory.createBarChart(
					"SẢN LƯỢNG BÁN CỦA SẢN PHẨM " + cbbSanPham.getSelectedItem().toString(),
					"Thời gian", "Tiền (VND)", spDataset,
					PlotOrientation.VERTICAL, true, true, false);
			paintChart(chart);
			
			return chart;
		}
		
		void repainting() {
			setBackground(Theme.LIGHT);
			panelSP.setBackground(Theme.TABLE);
			pnInfo.setBackground(Theme.LIGHT);
			Time.filledDateEditor(dcFrom);
			Time.filledDateEditor(dcTo);
			paintChart(chart);
		}
	}
	
	private class ThongKeDoanhThu extends JPanel {
		private static final long serialVersionUID = 1L;
		private ThongKeBUS bus = new ThongKeBUS();
		private JDateChooser dcFrom;
		private JDateChooser dcTo;
		private JPanel pnInfo;
		private JFreeChart chart;
		private ChartPanel panelDoanhThu;
		private JTable table;
		private DefaultTableModel model = new DefaultTableModel(
			new Object[][] {}, new String[] {"Thời gian", "Doanh thu", "Chi phí", "Chênh lệch"}
		);
		
		ThongKeDoanhThu() {
			setBounds(0,0,1000,600);
			setBorder(new EmptyBorder(10, 10, 10, 10));
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[] {0};
			gridBagLayout.rowHeights = new int[] {200};
			gridBagLayout.columnWeights = new double[]{1.0};
			gridBagLayout.rowWeights = new double[]{0.0, 1.0};
			setLayout(gridBagLayout);
			
			pnInfo = new JPanel();
			GridBagConstraints gbc_pnInfo = new GridBagConstraints();
			gbc_pnInfo.fill = GridBagConstraints.VERTICAL;
			gbc_pnInfo.insets = new Insets(0, 0, 20, 0);
			gbc_pnInfo.gridx = 0;
			gbc_pnInfo.gridy = 0;
			add(pnInfo, gbc_pnInfo);
			GridBagLayout gbl_pnInfo = new GridBagLayout();
			gbl_pnInfo.columnWidths = new int[] {60, 130, 60, 130};
			gbl_pnInfo.rowHeights = new int[] {40, 55, 40};
			gbl_pnInfo.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
			gbl_pnInfo.rowWeights = new double[]{0.0, 0.0, 0.0};
			pnInfo.setLayout(gbl_pnInfo);
			
			JLabel lblTitle = new JLabel("THỐNG KÊ DOANH THU");
			GridBagConstraints gbc_lblTitle = new GridBagConstraints();
			gbc_lblTitle.gridwidth = 4;
			gbc_lblTitle.fill = GridBagConstraints.VERTICAL;
			gbc_lblTitle.insets = new Insets(0, 0, 20, 0);
			gbc_lblTitle.gridx = 0;
			gbc_lblTitle.gridy = 0;
			pnInfo.add(lblTitle, gbc_lblTitle);
			lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
			
			JLabel lblDateFrom = new JLabel("Ngày bắt đầu:");
			GridBagConstraints gbc_lblDateFrom = new GridBagConstraints();
			gbc_lblDateFrom.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblDateFrom.insets = new Insets(0, 0, 25, 5);
			gbc_lblDateFrom.gridx = 0;
			gbc_lblDateFrom.gridy = 1;
			pnInfo.add(lblDateFrom, gbc_lblDateFrom);
			lblDateFrom.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			dcFrom = new JDateChooser(THANG_DAU_TIEN, "yyyy-MM");
			GridBagConstraints gbc_dcFrom = new GridBagConstraints();
			gbc_dcFrom.fill = GridBagConstraints.BOTH;
			gbc_dcFrom.insets = new Insets(0, 0, 25, 20);
			gbc_dcFrom.gridx = 1;
			gbc_dcFrom.gridy = 1;
			pnInfo.add(dcFrom, gbc_dcFrom);
			dcFrom.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			JLabel lblDateTo = new JLabel("Ngày kết thúc:");
			GridBagConstraints gbc_lblDateTo = new GridBagConstraints();
			gbc_lblDateTo.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblDateTo.insets = new Insets(0, 0, 25, 5);
			gbc_lblDateTo.gridx = 2;
			gbc_lblDateTo.gridy = 1;
			pnInfo.add(lblDateTo, gbc_lblDateTo);
			lblDateTo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			dcTo = new JDateChooser(Time.parseDate(LocalDate.now().plusMonths(1).toString()), "yyyy-MM");
			GridBagConstraints gbc_dcTo = new GridBagConstraints();
			gbc_dcTo.fill = GridBagConstraints.BOTH;
			gbc_dcTo.insets = new Insets(0, 0, 25, 0);
			gbc_dcTo.gridx = 3;
			gbc_dcTo.gridy = 1;
			pnInfo.add(dcTo, gbc_dcTo);
			dcTo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			JButton btnThongKe = new JButton("Thống kê");
			GridBagConstraints gbc_btnThongKe = new GridBagConstraints();
			gbc_btnThongKe.fill = GridBagConstraints.VERTICAL;
			gbc_btnThongKe.gridwidth = 4;
			gbc_btnThongKe.gridx = 0;
			gbc_btnThongKe.gridy = 2;
			pnInfo.add(btnThongKe, gbc_btnThongKe);
			btnThongKe.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					LocalDate dateFrom, dateTo;
					try {
						dateFrom = Time.toLocalDate(dcFrom.getDate());
					}
					catch (Exception ex) {
						ThongBao.baoLoi("Vui lòng nhập ngày bắt đầu hợp lệ");
						return;
					}
					
					try {
						dateTo = Time.toLocalDate(dcTo.getDate());
						if (dateFrom.isAfter(dateTo))
							ThongBao.baoLoi("Vui lòng nhập ngày bắt đầu sớm hơn ngày kết thúc");
					}
					catch (Exception ex) {
						ThongBao.baoLoi("Vui lòng nhập ngày kết thúc hợp lệ");
						return;
					}
									
					try {
						panelDoanhThu.setChart(load(bus.doanhThu(dateFrom, dateTo), dateFrom, dateTo));
					}
					catch (Exception ex) {
						ex.printStackTrace();
						ThongBao.baoLoi("Vui lòng kiểm tra lại nhập liệu");
					}
				}
			});
			btnThongKe.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			JPanel pnResult = new JPanel();
			pnResult.setOpaque(false);
			GridBagConstraints gbc_pnResult = new GridBagConstraints();
			gbc_pnResult.fill = GridBagConstraints.BOTH;
			gbc_pnResult.gridx = 0;
			gbc_pnResult.gridy = 1;
			add(pnResult, gbc_pnResult);
			pnResult.setLayout(new GridLayout());
			
			GridBagConstraints gbc_panelDoanhThu = new GridBagConstraints();
			gbc_panelDoanhThu.fill = GridBagConstraints.BOTH;
			gbc_panelDoanhThu.weightx = 0.4; // 40% of the width
			gbc_panelDoanhThu.weighty = 1.0;
			gbc_panelDoanhThu.gridx = 0;
			gbc_panelDoanhThu.gridy = 0;
			gbc_panelDoanhThu.insets = new Insets(0, 0, 0, 10); // Add some padding
			panelDoanhThu = new ChartPanel(null);
			panelDoanhThu.setBackground(Color.white);
			panelDoanhThu.setBorder(new LineBorder(new Color(200, 200, 200), 1));
			pnResult.add(panelDoanhThu, gbc_panelDoanhThu);

	        // Table constraints
	        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
	        gbc_scrollPane.fill = GridBagConstraints.BOTH;
	        gbc_scrollPane.weightx = 0.6; // 60% of the width
	        gbc_scrollPane.weighty = 1.0;
	        gbc_scrollPane.gridx = 1;
	        gbc_scrollPane.gridy = 0;
			
			JScrollPane scrollPane = new JScrollPane();
			pnResult.add(scrollPane, gbc_scrollPane);
			
			table = new JTable(model);
			table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 16));
			scrollPane.setViewportView(table);
			
			btnThongKe.doClick();
		}
		
		private JFreeChart load(HashMap<String, long[]> spNhap, LocalDate dateFrom, LocalDate dateTo) {
			DefaultCategoryDataset spDataset = new DefaultCategoryDataset();
			model.setRowCount(0);
			
			for (String date: spNhap.keySet()) {
				long[] soLuong = spNhap.get(date);
				spDataset.setValue(soLuong[1], "Doanh thu", date.toString());
				spDataset.setValue(soLuong[0], "Chi phí", date.toString());
				model.addRow(new Object[] {date, soLuong[1], soLuong[0], soLuong[1] - soLuong[0]});
			}
			
			chart = ChartFactory.createBarChart("DOANH THU GIAI ĐOẠN " + dateFrom.format(DateTimeFormatter.ofPattern("MM/yyyy")) + " - " + dateTo.format(DateTimeFormatter.ofPattern("MM/yyyy")),
					"Thời gian", "Tiền (VND)", spDataset, PlotOrientation.VERTICAL, true, true, false);
			paintChart(chart);
			return chart;
		}
		
		void repainting() {
			setBackground(Theme.LIGHT);
			pnInfo.setBackground(Theme.LIGHT);
			Time.filledDateEditor(dcFrom);
			Time.filledDateEditor(dcTo);
			paintChart(chart);
		}
	}
	
	private class ThongKeLoiNhuan extends JPanel {
		private static final long serialVersionUID = 1L;
		private ThongKeBUS bus = new ThongKeBUS();
		private JDateChooser dcFrom;
		private JDateChooser dcTo;
		private JPanel pnInfo;
		private JFreeChart chart;
		private ChartPanel panelDoanhThu;
		private JTable table;
		private DefaultTableModel model = new DefaultTableModel(
			new Object[][] {}, new String[] {"Thời gian", "Lợi nhuận"}
		);
		
		ThongKeLoiNhuan() {
			setBounds(0,0,1000,600);
			setBorder(new EmptyBorder(10, 10, 10, 10));
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[] {0};
			gridBagLayout.rowHeights = new int[] {200};
			gridBagLayout.columnWeights = new double[]{1.0};
			gridBagLayout.rowWeights = new double[]{0.0, 1.0};
			setLayout(gridBagLayout);
			
			pnInfo = new JPanel();
			GridBagConstraints gbc_pnInfo = new GridBagConstraints();
			gbc_pnInfo.fill = GridBagConstraints.VERTICAL;
			gbc_pnInfo.insets = new Insets(0, 0, 20, 0);
			gbc_pnInfo.gridx = 0;
			gbc_pnInfo.gridy = 0;
			add(pnInfo, gbc_pnInfo);
			GridBagLayout gbl_pnInfo = new GridBagLayout();
			gbl_pnInfo.columnWidths = new int[] {60, 130, 60, 130};
			gbl_pnInfo.rowHeights = new int[] {40, 55, 40};
			gbl_pnInfo.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
			gbl_pnInfo.rowWeights = new double[]{0.0, 0.0, 0.0};
			pnInfo.setLayout(gbl_pnInfo);
			
			JLabel lblTitle = new JLabel("THỐNG KÊ LỢI NHUẬN");
			GridBagConstraints gbc_lblTitle = new GridBagConstraints();
			gbc_lblTitle.gridwidth = 4;
			gbc_lblTitle.fill = GridBagConstraints.VERTICAL;
			gbc_lblTitle.insets = new Insets(0, 0, 20, 0);
			gbc_lblTitle.gridx = 0;
			gbc_lblTitle.gridy = 0;
			pnInfo.add(lblTitle, gbc_lblTitle);
			lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
			
			JLabel lblDateFrom = new JLabel("Ngày bắt đầu:");
			GridBagConstraints gbc_lblDateFrom = new GridBagConstraints();
			gbc_lblDateFrom.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblDateFrom.insets = new Insets(0, 0, 25, 5);
			gbc_lblDateFrom.gridx = 0;
			gbc_lblDateFrom.gridy = 1;
			pnInfo.add(lblDateFrom, gbc_lblDateFrom);
			lblDateFrom.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			dcFrom = new JDateChooser(THANG_DAU_TIEN, "yyyy-MM");
			GridBagConstraints gbc_dcFrom = new GridBagConstraints();
			gbc_dcFrom.fill = GridBagConstraints.BOTH;
			gbc_dcFrom.insets = new Insets(0, 0, 25, 20);
			gbc_dcFrom.gridx = 1;
			gbc_dcFrom.gridy = 1;
			pnInfo.add(dcFrom, gbc_dcFrom);
			dcFrom.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			JLabel lblDateTo = new JLabel("Ngày kết thúc:");
			GridBagConstraints gbc_lblDateTo = new GridBagConstraints();
			gbc_lblDateTo.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblDateTo.insets = new Insets(0, 0, 25, 5);
			gbc_lblDateTo.gridx = 2;
			gbc_lblDateTo.gridy = 1;
			pnInfo.add(lblDateTo, gbc_lblDateTo);
			lblDateTo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			dcTo = new JDateChooser(Time.parseDate(LocalDate.now().plusMonths(1).toString()), "yyyy-MM");
			GridBagConstraints gbc_dcTo = new GridBagConstraints();
			gbc_dcTo.fill = GridBagConstraints.BOTH;
			gbc_dcTo.insets = new Insets(0, 0, 25, 0);
			gbc_dcTo.gridx = 3;
			gbc_dcTo.gridy = 1;
			pnInfo.add(dcTo, gbc_dcTo);
			dcTo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			JButton btnThongKe = new JButton("Thống kê");
			GridBagConstraints gbc_btnThongKe = new GridBagConstraints();
			gbc_btnThongKe.fill = GridBagConstraints.VERTICAL;
			gbc_btnThongKe.gridwidth = 4;
			gbc_btnThongKe.gridx =0;
			gbc_btnThongKe.gridy = 2;
			pnInfo.add(btnThongKe, gbc_btnThongKe);
			btnThongKe.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					LocalDate dateFrom, dateTo;
					try {
						dateFrom = Time.toLocalDate(dcFrom.getDate());
					}
					catch (Exception ex) {
						ThongBao.baoLoi("Vui lòng nhập ngày bắt đầu hợp lệ");
						return;
					}
					
					try {
						dateTo = Time.toLocalDate(dcTo.getDate());
						if (dateFrom.isAfter(dateTo))
							ThongBao.baoLoi("Vui lòng nhập ngày bắt đầu sớm hơn ngày kết thúc");
					}
					catch (Exception ex) {
						ThongBao.baoLoi("Vui lòng nhập ngày kết thúc hợp lệ");
						return;
					}
									
					try {
						panelDoanhThu.setChart(load(bus.doanhThu(dateFrom, dateTo), dateFrom, dateTo));
					}
					catch (Exception ex) {
						ex.printStackTrace();
						ThongBao.baoLoi("Vui lòng kiểm tra lại nhập liệu");
					}
				}
			});
			btnThongKe.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			
			JPanel pnResult = new JPanel();
			pnResult.setOpaque(false);
			GridBagConstraints gbc_pnResult = new GridBagConstraints();
			gbc_pnResult.fill = GridBagConstraints.BOTH;
			gbc_pnResult.gridx = 0;
			gbc_pnResult.gridy = 1;
			add(pnResult, gbc_pnResult);
			pnResult.setLayout(new GridLayout());
			
			GridBagConstraints gbc_panelDoanhThu = new GridBagConstraints();
			gbc_panelDoanhThu.fill = GridBagConstraints.BOTH;
			gbc_panelDoanhThu.weightx = 0.7; // 40% of the width
			gbc_panelDoanhThu.weighty = 1.0;
			gbc_panelDoanhThu.gridx = 0;
			gbc_panelDoanhThu.gridy = 0;
			gbc_panelDoanhThu.insets = new Insets(0, 0, 0, 10); // Add some padding
			panelDoanhThu = new ChartPanel(null);
			panelDoanhThu.setBackground(Color.red);
			panelDoanhThu.setBorder(new LineBorder(new Color(200, 200, 200), 1));
			pnResult.add(panelDoanhThu, gbc_panelDoanhThu);

	        // Table constraints
	        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
	        gbc_scrollPane.fill = GridBagConstraints.BOTH;
	        gbc_scrollPane.weightx = 0.3; // 60% of the width
	        gbc_scrollPane.weighty = 1.0;
	        gbc_scrollPane.gridx = 1;
	        gbc_scrollPane.gridy = 0;
			
			JScrollPane scrollPane = new JScrollPane();
			pnResult.add(scrollPane, gbc_scrollPane);
			
			table = new JTable(model);
			table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 16));
			scrollPane.setViewportView(table);
			
			btnThongKe.doClick();
		}
		
//		private JFreeChart load(HashMap<String, long[]> spNhap, LocalDate dateFrom, LocalDate dateTo) {
//			DefaultCategoryDataset spDataset = new DefaultCategoryDataset();
//			model.setRowCount(0);
//			
//			for (String date: spNhap.keySet()) {
//				long[] soLuong = spNhap.get(date);
//				spDataset.setValue(soLuong[1], "Doanh thu", date.toString());
//				spDataset.setValue(soLuong[0], "Chi phí", date.toString());
//				model.addRow(new Object[] {date, soLuong[1] - soLuong[0]});
//			}
//			
//			chart = ChartFactory.createBarChart("LỢI NHUẬN GIAI ĐOẠN " + dateFrom.format(DateTimeFormatter.ofPattern("MM/yyyy")) + " - " + dateTo.format(DateTimeFormatter.ofPattern("MM/yyyy")),
//					"Thời gian", "Tiền (VND)", spDataset, PlotOrientation.VERTICAL, true, true, false);
//			paintChart(chart);
//			return chart;
//		}
		
		private JFreeChart load(HashMap<String, long[]> spNhap, LocalDate dateFrom, LocalDate dateTo) {
		    DefaultCategoryDataset spDataset = new DefaultCategoryDataset();
		    model.setRowCount(0);
		    
		    for (String date : spNhap.keySet()) {
		        long[] soLuong = spNhap.get(date);
		        long profit = soLuong[1] - soLuong[0];
		        spDataset.setValue(profit, "Lợi nhuận", date.toString());
		        model.addRow(new Object[] {date, profit});
		    }

		    chart = ChartFactory.createBarChart(
		        "LỢI NHUẬN GIAI ĐOẠN " + dateFrom.format(DateTimeFormatter.ofPattern("MM/yyyy")) + " - " + dateTo.format(DateTimeFormatter.ofPattern("MM/yyyy")),
		        "Thời gian", "Lợi nhuận (VND)", spDataset, PlotOrientation.VERTICAL, true, true, false
		    );
		    paintChart(chart);
		    return chart;
		}
		void repainting() {
			setBackground(Theme.LIGHT);
			pnInfo.setBackground(Theme.LIGHT);
			Time.filledDateEditor(dcFrom);
			Time.filledDateEditor(dcTo);
			paintChart(chart);
		}
	}
	public class ThongKeSPBC extends JPanel {
	    private static final long serialVersionUID = 1L;
	    private JPanel pnInfo;
	    private ThongKeBUS bus = new ThongKeBUS();
	    private JDateChooser dateChooser;
	    private JFreeChart chart;

	    private final ButtonGroup buttonGroup = new ButtonGroup();
	    private ChartPanel panelSP;
	    private JTable table;
	    private DefaultTableModel model = new DefaultTableModel(
	        new Object[][] {}, new String[] {"Tuần", "Tên SP", "Mã SP", "Số lượng bán ra", "Doanh thu"}
	    );

	    ThongKeSPBC() {
	        setBounds(0, 0, 1000, 600);
	        setBorder(new EmptyBorder(10, 10, 10, 10));
	        GridBagLayout gridBagLayout = new GridBagLayout();
	        gridBagLayout.columnWidths = new int[] {0};
	        gridBagLayout.rowHeights = new int[] {271, 0};
	        gridBagLayout.columnWeights = new double[]{1.0};
	        gridBagLayout.rowWeights = new double[]{0.0, 1.0};
	        setLayout(gridBagLayout);

	        pnInfo = new JPanel();
	        GridBagConstraints gbc_pnInfo = new GridBagConstraints();
	        gbc_pnInfo.fill = GridBagConstraints.VERTICAL;
	        gbc_pnInfo.insets = new Insets(0, 0, 20, 0);
	        gbc_pnInfo.gridx = 0;
	        gbc_pnInfo.gridy = 0;
	        add(pnInfo, gbc_pnInfo);
	        GridBagLayout gbl_pnInfo = new GridBagLayout();
	        gbl_pnInfo.columnWidths = new int[] {60, 75, 60, 15, 110};
	        gbl_pnInfo.rowHeights = new int[] {40, 30, 30, 30, 40};
	        gbl_pnInfo.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
	        gbl_pnInfo.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
	        pnInfo.setLayout(gbl_pnInfo);

	        JLabel lblTitle = new JLabel("THỐNG KÊ SẢN PHẨM BÁN");
	        GridBagConstraints gbc_lblTitle = new GridBagConstraints();
	        gbc_lblTitle.gridwidth = 5;
	        gbc_lblTitle.fill = GridBagConstraints.VERTICAL;
	        gbc_lblTitle.insets = new Insets(0, 0, 20, 0);
	        gbc_lblTitle.gridx = 0;
	        gbc_lblTitle.gridy = 0;
	        pnInfo.add(lblTitle, gbc_lblTitle);
	        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));


	        JLabel lblDate = new JLabel("Chọn tháng và năm:");
	        GridBagConstraints gbc_lblDate = new GridBagConstraints();
	        gbc_lblDate.fill = GridBagConstraints.HORIZONTAL;
	        gbc_lblDate.insets = new Insets(0, 0, 25, 5);
	        gbc_lblDate.gridx = 0;
	        gbc_lblDate.gridy = 1;
	        pnInfo.add(lblDate, gbc_lblDate);
	        lblDate.setFont(new Font("Segoe UI", Font.PLAIN, 16));

	        dateChooser = new JDateChooser();
	        dateChooser.setDateFormatString("MM/yyyy"); 
	        GridBagConstraints gbc_dateChooser = new GridBagConstraints();
	        gbc_dateChooser.gridwidth = 4;
	        gbc_dateChooser.fill = GridBagConstraints.BOTH;
	        gbc_dateChooser.insets = new Insets(0, 0, 25, 0);
	        gbc_dateChooser.gridx = 1;
	        gbc_dateChooser.gridy = 1;
	        pnInfo.add(dateChooser, gbc_dateChooser);
	        dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 16));

	        JButton btnThongKe = new JButton("Thống kê");
	        GridBagConstraints gbc_btnThongKe = new GridBagConstraints();
	        gbc_btnThongKe.fill = GridBagConstraints.VERTICAL;
	        gbc_btnThongKe.gridwidth = 5;
	        gbc_btnThongKe.gridx = 0;
	        gbc_btnThongKe.gridy = 4;
	        pnInfo.add(btnThongKe, gbc_btnThongKe);
	        btnThongKe.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                if (dateChooser.getDate() == null) {
	                    JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }

	                SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
	                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

	                int thang = Integer.parseInt(dateFormat.format(dateChooser.getDate())); 
	                int nam = Integer.parseInt(yearFormat.format(dateChooser.getDate())); 

	                try {
	                    ArrayList<Object[]> dataThang = bus.sanPhamBanTheoThang(thang, nam);

	                    if (dataThang.isEmpty()) {
	                        JOptionPane.showMessageDialog(null, "Không có dữ liệu cho tháng " + thang + " năm " + nam, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	                        return;
	                    }
	                    model.setRowCount(0);
	                    for (Object[] row : dataThang) {
	                        model.addRow(row);
	                    }

	                    DefaultCategoryDataset dataset = createDatasetWithMultipleSeries(dataThang);

	                    chart = ChartFactory.createStackedBarChart(
	                        "Lượng bán sản phẩm theo tuần - Tháng " + thang + " Năm " + nam,
	                        "Tuần",
	                        "Số lượng bán",
	                        dataset,
	                        PlotOrientation.VERTICAL,
	                        true,
	                        true,
	                        false
	                    );

	                    panelSP.setChart(chart);

	                } catch (Exception ex) {
	                    JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi lấy dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	                    ex.printStackTrace();
	                }
	            }

	            private DefaultCategoryDataset createDatasetWithMultipleSeries(ArrayList<Object[]> dataThang) {
	                DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	                for (int i = 0; i < dataThang.size(); i++) {
	                    Object[] row = dataThang.get(i);
	                    String productName = (String) row[2]; 
	                    int soThuTu = (int) row[0]; 
	                    int soLuongBan = (int) row[3];

	                    dataset.addValue(soLuongBan, productName, "Tuần " + soThuTu);
	                }

	                return dataset;
	            }
	        });










	        btnThongKe.setFont(new Font("Segoe UI", Font.PLAIN, 16));

	        JPanel pnResult = new JPanel();
	        pnResult.setOpaque(false);
	        GridBagConstraints gbc_pnResult = new GridBagConstraints();
	        gbc_pnResult.fill = GridBagConstraints.BOTH;
	        gbc_pnResult.gridx = 0;
	        gbc_pnResult.gridy = 1;
	        add(pnResult, gbc_pnResult);
	        pnResult.setLayout(new GridLayout(0, 2, 10, 0));

	        panelSP = new ChartPanel(null);
	        panelSP.setBackground(Color.white);
	        panelSP.setBorder(new LineBorder(new Color(200,200,200), 1));
	        pnResult.add(panelSP);

	        JScrollPane scrollPane = new JScrollPane();
	        pnResult.add(scrollPane);

	        table = new JTable(model);
	        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
	        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 16));
	        scrollPane.setViewportView(table);
	    }

	    private JFreeChart spNhap(HashMap<String, Object> spNhap) {
	        DefaultCategoryDataset spDataset = new DefaultCategoryDataset();
	        model.setRowCount(0);

	        for (String date: spNhap.keySet()) {
	            Object soLuong = spNhap.get(date);
	            spDataset.setValue((int)soLuong, "Số lượng", date.toString());
	            model.addRow(new Object[] {date, soLuong});
	        }

	        chart = ChartFactory.createBarChart(
	                "SẢN LƯỢNG BÁN CỦA SẢN PHẨM",
	                "Thời gian", "Tiền (VND)", spDataset,
	                PlotOrientation.VERTICAL, true, true, false);
	        paintChart(chart);

	        return chart;
	    }

	    void repainting() {
	        setBackground(Theme.LIGHT);
	        panelSP.setBackground(Theme.TABLE);
	        pnInfo.setBackground(Theme.LIGHT);
	        paintChart(chart);
	    }
	}
}

