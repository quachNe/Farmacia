package DTO;

import java.awt.Color;
import java.awt.Component;

import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.Border;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;
import javax.swing.border.AbstractBorder;

public class RoundedBorder extends AbstractBorder {
    private final int cornerRadiusPercent;  // Phần trăm bo góc

    // Constructor nhận vào phần trăm bo góc
    public RoundedBorder(int cornerRadiusPercent) {
        this.cornerRadiusPercent = cornerRadiusPercent;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        // Chuyển Graphics thành Graphics2D để dùng các thuộc tính mượt hơn
        Graphics2D g2 = (Graphics2D) g.create();

        // Bật khử răng cưa cho đường bo tròn mượt mà
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Tính toán bán kính góc bo tròn dựa trên phần trăm
        int radius = Math.min(width, height) * cornerRadiusPercent / 100;

        // Vẽ hình chữ nhật với góc bo tròn
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);

        // Giải phóng Graphics2D
        g2.dispose();
    }


    public static void setPlaceholder(JTextField textField, String placeholder) {
        // Thiết lập màu cho placeholder
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);

        // Listener để kiểm soát khi người dùng nhập và rời khỏi JTextField
     	  textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
    }
    

    
    public boolean isBorderOpaque() {
        return false;
    }
}