package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PhieuNhapGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private JPanel pnButton;
	private JButton btnXem;
	private JButton btnLap;
	private JButton btnLamMoi;
	private JButton btnFormer;
	private JPanel pnForm;
	
	public PhieuNhapGUI(String maDS) {		
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
		
		btnXem = new JButton("Xem phiếu nhập");
		btnXem.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnXem.setFocusable(false);
		btnXem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrent(btnXem);
				pnForm.removeAll();
				pnForm.add(new PhieuNhapXemGUI());
				pnForm.validate();
				pnForm.repaint();
			}
		});
		btnXem.setFont(new Font("Segoe UI", Font.ITALIC, 16));
		btnXem.setPreferredSize(new Dimension(145,35));
		pnButton.add(btnXem);
		
		btnLap = new JButton("Lập phiếu nhập");
		btnLap.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnLap.setFocusable(false);
		btnLap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrent(btnLap);
				pnForm.removeAll();
				pnForm.add(new PhieuNhapLapGUI(maDS));
				pnForm.validate();
				pnForm.repaint();
			}
		});
		btnLap.setFont(new Font("Segoe UI", Font.ITALIC, 16));
		btnLap.setPreferredSize(new Dimension(145,35));
		pnButton.add(btnLap);
		
		btnLamMoi = new JButton("");
		btnLamMoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnForm.removeAll();
				pnForm.add(btnFormer == btnLap ? new PhieuNhapXemGUI() : new PhieuNhapLapGUI(maDS));
				pnForm.validate();
				pnForm.repaint();
			}
		});
		btnLamMoi.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnLamMoi.setFocusable(false);		
		btnLamMoi.setBackground(new Color(183, 228, 228));
		btnLamMoi.setPreferredSize(new Dimension(40,35));
		pnButton.add(btnLamMoi);

		btnXem.doClick();
		repainting();
	}
	
	private void setCurrent(JButton btnCurrent) {
		btnFormer = (btnCurrent == btnLap) ? btnXem : btnLap;
		btnFormer.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnCurrent.setBorder(new MatteBorder(0, 0, 2, 0, Theme.DARK));
	}
	
	void repainting() {
		pnButton.setBackground(Theme.MID);
		btnXem.setBackground(Theme.MID);
		btnLap.setBackground(Theme.MID);
		btnLamMoi.setBackground(Theme.MID);
		btnLamMoi.setIcon(new ImageIcon("img/reload_" + Theme.THEME + ".png"));
		
		if (btnFormer == btnLap) ((PhieuNhapXemGUI)pnForm.getComponent(0)).repainting();
		else ((PhieuNhapLapGUI)pnForm.getComponent(0)).repainting();
	}
}
