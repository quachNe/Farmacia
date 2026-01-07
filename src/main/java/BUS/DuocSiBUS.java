package BUS;

import DTO.DuocSi;
import DTO.TaiKhoan;
import DAO.DuocSiDAO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.swing.table.TableModel;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DuocSiBUS {
	private DuocSiDAO dao = new DuocSiDAO();
	
	public boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^0\\d{9}$";
        return phoneNumber.matches(regex);
    }
	
	public boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(regex);
    }
	public ArrayList<DuocSi> getDuocSi() {
		return dao.getDuocSi();
	}
	public boolean check_trungDT(String num) {
	    for (DuocSi ds : getDuocSi()) {
	        if (ds.getSoDT() != null && ds.getSoDT().equalsIgnoreCase(num))
	            return true;
	    }
	    return false;
	}
	
	public boolean check_trungEmail(String Email) {
	    for (DuocSi ds : getDuocSi()) {
	        if (ds.getEmail() != null &&  ds.getEmail().equalsIgnoreCase(Email))
	            return true;
	    }
	    return false;
	}
	public boolean addDuocSi(DuocSi ds) {
		return dao.addDuocSi(ds);
	}
	
	public boolean updateDuocSi(DuocSi ds) {
		return dao.updateDuocSi(ds);
	}
	
	public boolean lockDuocSi(String ma) {
		return dao.lockDuocSi(ma);
	}
	
	
	public boolean unlockDuocSi(String ma) {
		return dao.unlockDuocSi(ma);
	}
	
	public ArrayList<DuocSi> findDuocSi(String option, String key){
		return dao.findDuocSi(option, key);
	}
	
	public int countDS(String DS) {
		return dao.countDS(DS);
	}
	
	public int countQL(String QL) {
		return dao.countQL(QL);
	}
	
	public String generateMaDS() {
	    String ma = "DS";
	    int count = countDS(ma);
	    String nextma = String.format("%s%04d", ma, count+1);
	    return nextma;
	}
	
	public String generateMaQL() {
	    String ma = "QL";
	    int count = countDS(ma);
	    String nextma = String.format("%s%04d", ma, count+1);
	    return nextma;
	}
	
	public ArrayList<DuocSi> nhapExcel(String filename) {
	    try {
	        FileInputStream fileIn = new FileInputStream(filename);
	        Workbook workbook = (filename.endsWith(".xls")) ? new HSSFWorkbook(fileIn) : ((filename.endsWith(".xlsx")) ? new XSSFWorkbook(fileIn) : null);
	        ArrayList<DuocSi> importList = new ArrayList<>();

	        Sheet sheet = workbook.getSheet("Sheet1");
	        int row = sheet.getPhysicalNumberOfRows();
	        if (row < 2) return null;
	        int col = sheet.getRow(0).getPhysicalNumberOfCells();
	        int count_1 = 0;
	        int count_2 = 0;
	        for (DuocSi ds : getDuocSi())
	            if (ds.getMa().startsWith("DS")) {
	            	count_1++;
	            	count_2++;
	            }
	        for (short i = 1; i < row; i++) {
	            String[] arg = new String[col];
	            for (short j = 0; j < col; j++) {
	                Cell cell = sheet.getRow(i).getCell(j);
	                arg[j] = (cell != null && cell.getCellType() != CellType.BLANK) ? cell.toString() : "";
	            }
	            String ma = String.format("DS%04d", ++count_1);
	            TaiKhoan taiKhoan = new TaiKhoan(ma);
	            DuocSi ds = new DuocSi(String.format("DS%04d", ++count_2), arg[0], arg[1], arg[2], taiKhoan, true);
	            importList.add(ds);
	        }

	        fileIn.close();
	        workbook.close();
	        return importList;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	public boolean xuatExcel(TableModel tbModel, String filename) {
		Workbook workbook = (filename.endsWith(".xls")) ? new HSSFWorkbook() : new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Sheet1");
		
		int rowNum = tbModel.getRowCount();
		int colNum = tbModel.getColumnCount();
		
		for (short i=0; i<=rowNum; i++) {						
			Row row = sheet.createRow(i);
			for (short j=0; j<colNum; j++) {
				String value;
				Cell cell = row.createCell(j);
				Font font;
				if (workbook instanceof XSSFWorkbook) font = (XSSFFont)sheet.getWorkbook().createFont();
				else font = (HSSFFont)sheet.getWorkbook().createFont();
				
				CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
				cellStyle.setBorderBottom(BorderStyle.THIN);
				cellStyle.setBorderTop(BorderStyle.THIN);
				cellStyle.setBorderLeft(BorderStyle.THIN);
				cellStyle.setBorderRight(BorderStyle.THIN);
				
				if (i>0) value = (tbModel.getValueAt(i - 1, j) != null) ? tbModel.getValueAt(i - 1, j).toString() : "";

				else {
					value = tbModel.getColumnName(j);
					if (workbook instanceof XSSFWorkbook) {
						((XSSFFont)font).setColor(new XSSFColor(new java.awt.Color(255,255,255), null));
						cellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(0,100,0), null));
					}
					else {
						font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
						cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.DARK_GREEN.getIndex());
					}					
					font.setBold(true);
					font.setFontHeightInPoints((short)14);
					cellStyle.setAlignment(HorizontalAlignment.CENTER);
					cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				}
				
				cellStyle.setFont(font);
				cell.setCellValue(value);
				cell.setCellStyle(cellStyle);
			}
		}
		
		for (short j=0; j<colNum; j++) sheet.autoSizeColumn(j);
		
		try {
			FileOutputStream fileOut = new FileOutputStream(filename);
		    workbook.write(fileOut);
		    fileOut.close();
		    workbook.close();
		    return true;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
}
