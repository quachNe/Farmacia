package GUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import BUS.AnhBUS;
import BUS.ChuyBUS;
import BUS.DieuTriBUS;
import BUS.ThanhPhanBUS;

public class HienThiThanhPhan extends JDialog {

	private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private static JLabel lbl_masp;
    private static String masp;
    private static ThanhPhanBUS tpBUS = new ThanhPhanBUS();
    private static DieuTriBUS dtBUS = new DieuTriBUS();
    private static JTable table;
    private static JTable table_1;
    private static JTextArea txtChu;
    private static JLabel lbAnh = new JLabel("");
    private static AnhBUS anhBus = new AnhBUS();
    private static ChuyBUS chuyBus = new ChuyBUS();
    private static    JTextArea txt_TenSanPham = new JTextArea();
    public HienThiThanhPhan() {
    	setResizable(false);
    	setModalityType(DEFAULT_MODALITY_TYPE);
        setTitle("Thành phần và điều trị của thuốc");
        setIconImage(new ImageIcon("img/icon.png").getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 700, 546);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lbl_masp = new JLabel();
        lbl_masp.setBounds(10, 10, 300, 30);
        lbl_masp.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(lbl_masp);

        JLabel lblDieuTri = new JLabel("Điều trị");
        lblDieuTri.setBounds(355, 173, 321, 22);
        lblDieuTri.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        contentPane.add(lblDieuTri);

        JLabel lblThanhPhan = new JLabel("Thành phần");
        lblThanhPhan.setBounds(10, 173, 297, 22);
        lblThanhPhan.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        contentPane.add(lblThanhPhan);

        table = new JTable();
        table.setModel(new DefaultTableModel(new Object[][] { {null, null}, },new String[] { "STT", "Thành Phần" }) {
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] { String.class, Object.class };
            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        
        TableColumn sttColumn = table.getColumnModel().getColumn(0); 
        sttColumn.setPreferredWidth(20);
        TableColumn thanhPhanColumn = table.getColumnModel().getColumn(1);
        thanhPhanColumn.setPreferredWidth(280);

        JScrollPane scrollPane1 = new JScrollPane(table);
        scrollPane1.setBounds(10, 205, 335, 217);
        contentPane.add(scrollPane1);

        table_1 = new JTable();
        table_1.setModel(new DefaultTableModel(new Object[][] { {null, null}, },new String[] { "STT", "Điều Trị" }) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        TableColumn sttColumn1 = table_1.getColumnModel().getColumn(0); 
        sttColumn1.setPreferredWidth(20);
        TableColumn dieuTriColumn = table_1.getColumnModel().getColumn(1);
        dieuTriColumn.setPreferredWidth(280);

        JScrollPane scrollPane2 = new JScrollPane(table_1);
        scrollPane2.setBounds(355, 205, 321, 217);
        contentPane.add(scrollPane2);

        lbAnh.setBounds(341, 10, 335, 153);
        contentPane.add(lbAnh);

        txtChu = new JTextArea();
        txtChu.setBounds(10, 432, 666, 67);
        contentPane.add(txtChu);
        txtChu.setColumns(10);
        txtChu.setLineWrap(true);
        txtChu.setWrapStyleWord(true);
        
     
        txt_TenSanPham.setBounds(10, 53, 300, 110);
        contentPane.add(txt_TenSanPham);
        txt_TenSanPham.setColumns(30);
        txt_TenSanPham.setWrapStyleWord(true);
        txt_TenSanPham.setLineWrap(true);
        

        
        
        repainting();
    }
    
    public static void taiAnh(String anh) {
        String thuMucChuaAnh = getFolderPath() + "/img/HinhSanPham/";
        String duongdananh = thuMucChuaAnh + anh;
        System.out.print(duongdananh);
        changeImage(duongdananh);
    }

    public static String getFolderPath() {
        String folderPath = "";
        try {
            String appFolderPath = System.getProperty("user.dir");
            if (appFolderPath.endsWith("bin") || appFolderPath.endsWith("dist")) {
                folderPath = new File(appFolderPath).getParent();
            } else {
                folderPath = appFolderPath;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return folderPath;
    }

    public static void changeImage(String duongdananh) {
        try {
            ImageIcon resizedImage = ResizeImage(duongdananh);
            lbAnh.setIcon(resizedImage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static ImageIcon ResizeImage(String imagePath) {
        ImageIcon resizedImage = null;
        try {
            File file = new File(imagePath);
            if (!file.exists()) {
                return null;
            }
            ImageIcon originalImage = new ImageIcon(imagePath);
            Image img = originalImage.getImage();
            Image newImg = img.getScaledInstance(lbAnh.getWidth(), lbAnh.getHeight(), Image.SCALE_SMOOTH);
            resizedImage = new ImageIcon(newImg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resizedImage;
    }

    public static void setData(String ma, String tensp) {
        masp = ma;
        lbl_masp.setText("Mã sản phẩm: " + ma);
        txt_TenSanPham.setText("Tên sản phẩm: " + tensp);
        ArrayList<String> arrSP = tpBUS.getThanhPhanByMaThuoc(masp);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        while (model.getRowCount() != 0) {
            model.removeRow(0);
        }

        int no = 1;
        for (String sp : arrSP) {
            model.addRow(new Object[] { no++, sp });
        }

        ArrayList<String> arrSP1 = dtBUS.getBenhDieuTriByMaThuoc(masp);

        DefaultTableModel model1 = (DefaultTableModel) table_1.getModel();
        while (model1.getRowCount() != 0) {
            model1.removeRow(0);
        }

        int no1 = 1;
        for (String sp : arrSP1) {
            model1.addRow(new Object[] { no1++, sp });
        }
        String anh = anhBus.getAnhByMaThuoc(masp);
        taiAnh(anh);
        String chuy = " Chú ý :" + chuyBus.getTenchuyByMaChuy(masp);
        txtChu.setText(chuy);
    }

    void repainting() {
        Theme.setTheme(this);
        getRootPane().putClientProperty("JRootPane.titleBarBackground", Theme.DARK);
        getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.WHITE);
        contentPane.setBackground(Theme.LIGHT);
    }
}
