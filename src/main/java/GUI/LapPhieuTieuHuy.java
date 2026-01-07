package GUI;

import java.awt.BorderLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BUS.TieuHuyBUS;
import DTO.TieuHuy;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class LapPhieuTieuHuy extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtNguoiLap;
	private JTextField txtNgayLap;
	private long tongTien = 0;
	private TieuHuyBUS bus = new TieuHuyBUS();
	private JComboBox<String > cbbLyDo;
	private JComboBox<String> cbbLoSX;
	private JComboBox<String> cbbMaSP;
	private JTextArea txaLyDoKhac;
	private DefaultComboBoxModel<String> modelLoSX = new DefaultComboBoxModel<>();
//	private DefaultComboBoxModel<String> modelMaSP = new DefaultComboBoxModel<>();
	
	public LapPhieuTieuHuy(String maDS) {
		setBounds(100, 100, 490, 570);
		setModal(true);
		setResizable(false);
		setIconImage(new ImageIcon("img/icon.png").getImage());
		setTitle("Lập phiếu tiêu hủy");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		JLabel lblNewLabel = new JLabel("LẬP PHIẾU TIÊU HỦY");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 10, 456, 30);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Ngày lập:");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(10, 100, 80, 25);
		contentPanel.add(lblNewLabel_1);
		txtNguoiLap = new JTextField();
		txtNguoiLap.setEnabled(false);
		txtNguoiLap.setText(maDS);
		txtNguoiLap.setBounds(140, 50, 326, 25);
		contentPanel.add(txtNguoiLap);
		txtNguoiLap.setColumns(10);
	
		txtNgayLap = new JTextField();
		txtNgayLap.setColumns(10);
		txtNgayLap.setBounds(140, 100, 326, 25);
		txtNgayLap.setText(LocalDate.now().toString());
		contentPanel.add(txtNgayLap);
		txtNgayLap.setEnabled(false);
		
		JLabel lblNewLabel_1_1 = new JLabel("Sản phẩm:");
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblNewLabel_1_1.setBounds(10, 150, 120, 25);
		contentPanel.add(lblNewLabel_1_1);
		
		cbbMaSP = new JComboBox<>();
		cbbMaSP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadSX(cbbMaSP.getSelectedItem() + "");
			}
		});
		cbbMaSP.setBounds(140, 150, 326, 25);
		contentPanel.add(cbbMaSP);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Lô sản xuất:");
		lblNewLabel_1_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblNewLabel_1_1_1.setBounds(10, 200, 120, 25);
		contentPanel.add(lblNewLabel_1_1_1);
		
		JLabel lblThietHai = new JLabel("");
		lblThietHai.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblThietHai.setHorizontalAlignment(SwingConstants.RIGHT);
		lblThietHai.setBounds(286, 444, 180, 20);
		contentPanel.add(lblThietHai);
		
		cbbLoSX = new JComboBox<>();
		cbbLoSX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tongTien = bus.getThietHai(cbbMaSP.getSelectedItem() + "", cbbLoSX.getSelectedItem() + "");
				
				lblThietHai.setText("THIỆT HẠI: " + tongTien);
			}
		});
		cbbLoSX.setBounds(140, 200, 326, 25);
		contentPanel.add(cbbLoSX);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Lý do:");
		lblNewLabel_1_1_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblNewLabel_1_1_1_1.setBounds(10, 250, 80, 25);
		contentPanel.add(lblNewLabel_1_1_1_1);
		
		txaLyDoKhac = new JTextArea();
		txaLyDoKhac.setBounds(140, 300, 326, 134);
		txaLyDoKhac.setEnabled(false);
		contentPanel.add(txaLyDoKhac);
		
		cbbLyDo = new JComboBox<>();
		cbbLyDo.addItem("Sản phẩm bị hết hạn sử dụng");
		cbbLyDo.addItem("Sản phẩm bị lỗi");
		cbbLyDo.addItem("Sản phẩm có chất không được cấp phép");
		cbbLyDo.addItem("Lý do khác");
		cbbLyDo.setBounds(140, 250, 326, 25);
		contentPanel.add(cbbLyDo);
		cbbLyDo.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        if (cbbLyDo.getSelectedItem().equals("Lý do khác")) {
		        	txaLyDoKhac.setEnabled(true);  // Enable the text area
		        } else {
		        	txaLyDoKhac.setEnabled(false); // Disable the text area
		            txaLyDoKhac.setText("");       // Clear the text when disabled
		        }
		    }
		});
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Lý do khác:");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblNewLabel_1_1_1_1_1.setBounds(10, 300, 120, 25);
		contentPanel.add(lblNewLabel_1_1_1_1_1);
		
		
		JButton btnLuu = new JButton("Lưu\r\n");
		btnLuu.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	if (cbbMaSP == null || cbbMaSP.getItemCount() == 0) {
		    	    ThongBao.baoLoi("Chưa có sản phẩm để tiêu hủy");
		    	    return;
		    	}
		        if ((cbbLyDo.getSelectedItem() + "").equals("Lý do khác") && txaLyDoKhac.getText().isEmpty()) {
		            ThongBao.baoLoi("Vui lòng nhập lý do tiêu hủy");
		            return;
		        }
		        if (ThongBao.cauHoiNghiemTrong("Bạn có chắc chắn muốn tiêu hủy đơn này? Một khi tiêu hủy thì hệ thống không thể khôi phục.") == JOptionPane.YES_OPTION) {
		            String lyDo = (cbbLyDo.getSelectedItem() + "").equals("Lý do khác") ? txaLyDoKhac.getText() : cbbLyDo.getSelectedItem() + "";
		            if (lyDo.equals("Sản phẩm bị lỗi")) {
		                tongTien = (long) (tongTien-tongTien * 0.10);
		            }

		            TieuHuy th = new TieuHuy(cbbMaSP.getSelectedItem() + "", cbbLoSX.getSelectedItem() + "", LocalDate.now(), maDS, lyDo, tongTien);
		            if (bus.addTieuHuy(th)) {
		                ThongBao.thongBao("Lập đơn tiêu hủy thành công");
//		                TieuHuyGUI(maDS)).loadTH(bus.getTieuHuy());
		                TieuHuyGUI.loadTH(bus.getTieuHuy());
		                dispose();
		            } else {
		                ThongBao.baoLoi("Lập đơn tiêu hủy thất bại");
		            }
		        }
		    }

			private Object TieuHuyGUI(String maDS) {
				// TODO Auto-generated method stub
				return null;
			}
		});

		btnLuu.setForeground(Color.WHITE);
		btnLuu.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLuu.setBackground(new Color(11, 101, 140));
		btnLuu.setBorderPainted(false);
		btnLuu.setFocusPainted(false);
		btnLuu.setIcon(new ImageIcon(new ImageIcon("img/Icon/Save.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		btnLuu.setBounds(140, 493, 100, 30);
		contentPanel.add(btnLuu);
		
		JButton btnHuy = new JButton("Hủy");
		btnHuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnHuy.setForeground(Color.WHITE);
		btnHuy.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnHuy.setBorderPainted(false);
		btnHuy.setFocusPainted(false);
		btnHuy.setBackground(new Color(11, 101, 140));
		btnHuy.setIcon(new ImageIcon(new ImageIcon("img/Icon/Cancel.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		btnHuy.setBounds(269, 493, 100, 30);
		contentPanel.add(btnHuy);
		
		JLabel lblNewLabel_3 = new JLabel("Người lập:");
		lblNewLabel_3.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(10, 50, 120, 25);
		
		contentPanel.add(lblNewLabel_3);
		if (cbbMaSP.getSelectedIndex() != -1 && cbbLoSX.getSelectedIndex() != -1) {
	        tongTien = bus.getThietHai(cbbMaSP.getSelectedItem() + "", cbbLoSX.getSelectedItem() + "");
	        lblThietHai.setText("THIỆT HẠI: " + tongTien);
	    }
//	    cbbLyDo.setSelectedIndex(0);
//	    txaLyDoKhac.setEditable(false);
//	    txaLyDoKhac.setText("");
	    repainting();
	    populateProductCodes();
	}
	void repainting() {
		Theme.setTheme(this);
		getRootPane().putClientProperty("JRootPane.titleBarBackground", Theme.DARK);
		getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.WHITE);
		contentPanel.setBackground(Theme.LIGHT);		
	}
	private void loadSX(String maSP) {
		ArrayList<String> listSX = bus.getSanXuat(maSP);
		modelLoSX = new DefaultComboBoxModel<>();
		if (listSX != null) {
			modelLoSX.addAll(listSX);
			cbbLoSX.setModel(modelLoSX);
			cbbLoSX.setSelectedIndex(0);
		}
	}
	private void populateProductCodes() {
	    ArrayList<String> productCodes = bus.getSanPham();
	    DefaultComboBoxModel<String> modelMaSP = new DefaultComboBoxModel<>();
	    if (productCodes != null) {
	        modelMaSP.addAll(productCodes);
	        cbbMaSP.setModel(modelMaSP);
	        if (modelMaSP.getSize() > 0) {
	            cbbMaSP.setSelectedIndex(0);
	        }
	    }
	}
}