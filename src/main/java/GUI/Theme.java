package GUI;
import java.awt.Color;

import javax.swing.*;
import com.formdev.flatlaf.*;

abstract class Theme {
	static Color TABLE;
	static Color LIGHT;
	static Color MID;
	static Color DARK;
	static Color DARK_CUR;
	static Color TITLE;
	static String THEME;
	
	static int MAU;
	final static int TOTAL = 3;
	
	static void setTheme(int num, JFrame frame) {
		MAU = num;
		setTheme(frame);
	}
	
	static void setTheme(JFrame frame) {
		switch (MAU) {
			case 1:
				TABLE = Color.white;
				LIGHT = new Color(239, 249, 249);
				MID = new Color (183, 228, 228);
				DARK = new Color(23, 107, 135);
				DARK_CUR = new Color(27, 124, 157);
				TITLE = Color.black;
				try {
					UIManager.setLookAndFeel(new FlatLightLaf());
					SwingUtilities.updateComponentTreeUI(frame);
				}
				catch (Exception e) {}
				THEME = "light";
				break;
			case 2:
				TABLE = Color.white;
				LIGHT = Color.white;
				MID = new Color(230, 230, 230);
				DARK = new Color(127, 127, 127);
				DARK_CUR = new Color(150, 150, 150);
				TITLE = Color.black;
				THEME = "light";
				break;
			case 3:
				TABLE = new Color(64, 67, 69);
				LIGHT = new Color(60, 63, 65);
				MID = new Color(60, 63, 65);
				DARK = new Color(23, 107, 135);
				DARK_CUR = new Color(94, 99, 102);
				try {
					FlatLaf.registerCustomDefaultsSource("themes");
					UIManager.setLookAndFeel(new FlatDarkLaf());
					SwingUtilities.updateComponentTreeUI(frame);
				}
				catch (Exception e) {}				
				TITLE = Color.white;
				THEME = "dark";
				break;
		}
	}
	
	static void setTheme(JDialog dialog) {
		switch (MAU) {
			case 1:
				TABLE = Color.white;
				LIGHT = new Color(239, 249, 249);
				MID = new Color (183, 228, 228);
				DARK = new Color(23, 107, 135);
				DARK_CUR = new Color(27, 124, 157);
				TITLE = Color.black;
				try {
					UIManager.setLookAndFeel(new FlatLightLaf());
					SwingUtilities.updateComponentTreeUI(dialog);
				}
				catch (Exception e) {}
				THEME = "light";
				break;
			case 2:
				TABLE = Color.white;
				LIGHT = Color.white;
				MID = new Color(230, 230, 230);
				DARK = new Color(127, 127, 127);
				DARK_CUR = new Color(150, 150, 150);
				TITLE = Color.black;
				try {
					UIManager.setLookAndFeel(new FlatLightLaf());
					SwingUtilities.updateComponentTreeUI(dialog);
				}
				catch (Exception e) {}
				THEME = "light";
				break;
			case 3:
				TABLE = new Color(64, 67, 69);
				LIGHT = new Color(60, 63, 65);
				MID = new Color(60, 63, 65);
				DARK = new Color(23, 107, 135);
				DARK_CUR = new Color(94, 99, 102);
				try {
					FlatLaf.registerCustomDefaultsSource("themes");
					UIManager.setLookAndFeel(new FlatDarkLaf());
					SwingUtilities.updateComponentTreeUI(dialog);
				}
				catch (Exception e) {}				
				TITLE = Color.white;
				THEME = "dark";
				break;
		}
	}
}