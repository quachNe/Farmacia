package GUI;
import java.util.Date;

import com.toedter.calendar.*;
import java.awt.Color;
import java.time.LocalDate;
import java.time.ZoneId;

abstract class Time {
	static String toString(Date date) {
		try {
			return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();
		}
		catch (Exception e) {
			return null;
		}
	}
	
	static Date parseDate(String string) {
		try {
			return new Date(LocalDate.parse(string).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
		}
		catch (Exception e) {
			return null;
		}
	}
	
	static LocalDate toLocalDate(Date date) {
		try {
			return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
		catch (Exception e) {
			return null;
		}
	}
	
	static void filledDateEditor(JDateChooser dateChooser) {
		dateChooser.getComponent(1).setForeground(Theme.TITLE);
		IDateEditor dateEditor = dateChooser.getDateEditor();
        if (dateEditor instanceof JTextFieldDateEditor) {
            JTextFieldDateEditor txtFld = (JTextFieldDateEditor) dateEditor;
            txtFld.addPropertyChangeListener("foreground", event -> {
                if (Color.BLACK.equals(event.getNewValue())) {
                    txtFld.setForeground(Theme.TITLE);
                }
            });
        }
	}
}
