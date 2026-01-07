package DTO;
import com.barcodelib.barcode.Linear;
import com.github.sarxos.webcam.Webcam;
import com.google.zxing.LuminanceSource;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JTextField;

import java.io.File;

public class BarCode {

    // Khai báo webcam
    private Webcam webcam;
    private boolean scanning = true; // Biến kiểm tra việc quét có đang hoạt động
    private Thread scanningThread; 
    private JFrame frame;
    
    JTextField txtSeri;

    public void ScanBarCode(JTextField txtSeri) {
        // Tạo JFrame để hiển thị camera
        JFrame frame = new JFrame("Quét Mã Vạch");
        frame.setSize(640, 480); // Kích thước cửa sổ camera
        frame.setLayout(new BorderLayout());

        // Tạo JPanel để hiển thị hình ảnh từ webcam
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (webcam != null && webcam.isOpen()) {
                    BufferedImage image = webcam.getImage();
                    if (image != null) {
                        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
                    }
                }
            }
        };

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

        try {
            // Khởi tạo webcam
            webcam = Webcam.getDefault();
            if (webcam == null) {
                throw new RuntimeException("Không tìm thấy webcam.");
            }
            webcam.setViewSize(WebcamResolution.VGA.getSize());
            // Bắt đầu webcam
            webcam.open();

            // Thêm WindowListener để đóng webcam khi frame được đóng
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    // Đóng webcam khi frame đóng
                    if (webcam != null && webcam.isOpen()) {
                        webcam.close();
                    }
                    // Đóng frame
                    frame.dispose();
                }
            });

            // Tạo một Thread để quét mã vạch
            new Thread(() -> {
                while (scanning) {
                    BufferedImage image = webcam.getImage();
                    if (image != null) {
                        // Chuyển đổi BufferedImage thành LuminanceSource
                        LuminanceSource source = new BufferedImageLuminanceSource(image);
                        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                        // Khởi tạo mã vạch Reader
                        MultiFormatReader reader = new MultiFormatReader();
                        try {
                            Result result = reader.decode(bitmap);
                            // Nếu quét thành công, cập nhật vào txtSeri
                            if (result != null) {
                                String barcodeText = result.getText();	
                                SwingUtilities.invokeLater(() -> txtSeri.setText(barcodeText));
                                scanning = false; // Dừng quét
                                // Đóng webcam và frame sau khi quét thành công
                                webcam.close();
                                frame.dispose();
                                if (!txtSeri.getText().trim().isEmpty()) {  // Kiểm tra nếu txtSeri có dữ liệu
                                    KeyEvent enterKeyEvent = new KeyEvent(
                                        txtSeri, 
                                        KeyEvent.KEY_PRESSED, 
                                        System.currentTimeMillis(), 
                                        0, 
                                        KeyEvent.VK_ENTER, 
                                        '\n'
                                    );
                                    txtSeri.dispatchEvent(enterKeyEvent);  // Gửi sự kiện Enter
                                }

                            }
                        } catch (NotFoundException e) {
                            // Không tìm thấy mã vạch
                        }
                    }
                    // Vẽ lại panel để cập nhật hình ảnh
                    panel.repaint();

                    // Để tránh việc sử dụng CPU quá mức
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phương thức để dừng quét và đóng webcam
    private void stopScanning() {
        scanning = false; // Đặt biến kiểm soát thành false
        if (webcam != null && webcam.isOpen()) {
            webcam.close(); // Đóng webcam
        }
        // Đợi cho thread quét hoàn thành
        if (scanningThread != null && scanningThread.isAlive()) {
            try {
                scanningThread.join(); // Chờ cho thread quét kết thúc
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Đặt lại trạng thái ngắt
            }
        }
        // Đóng frame và xuất thông báo nếu quét thất bại
        if (!scanning) {
            JOptionPane.showMessageDialog(frame, "Quét thất bại. Vui lòng thử lại.");
            frame.dispose();
        }
    }
    public void createBarcodes(int SeriBD, int SeriKT) {
        try {
            Linear barcode = new Linear();
            barcode.setType(Linear.CODE128B);
            barcode.setI(11.0f);

            // Create a directory to save barcodes if it doesn't exist
            String outputPath = "C:\\Barcodes\\";
            new File(outputPath).mkdirs(); // Create the directory

            for (int i = SeriBD; i <= SeriKT; i++) {
                // Set the data for the barcode
                barcode.setData(String.valueOf(i));

                // Save the barcode to a file
                String filePath = outputPath + i + ".png"; // Full path to save the barcode
                barcode.renderBarcode(filePath); // Save barcode to file
            }
         //   System.out.println("In thành công!"); // Print success message

        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions
        }
    }

}
