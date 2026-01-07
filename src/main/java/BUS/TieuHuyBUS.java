package BUS;
import DTO.TieuHuy;
import DAO.TieuHuyDAO;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.table.TableModel;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TieuHuyBUS {
	private TieuHuyDAO dao = new TieuHuyDAO();
	
	public ArrayList<TieuHuy> getTieuHuy() {
		return dao.getTieuHuy();
	}
	
	public ArrayList<String> getSanPham() {
		return dao.getSanPham(); 
	}
	
	public ArrayList<String> getSanXuat(String maSP) {
		return dao.getSanXuat(maSP); 
	}
	
	public long getThietHai(String maSP, String loSX) {
		return dao.getThietHai(maSP, loSX);
	}
	
	public boolean addTieuHuy(TieuHuy th) {
		return dao.addTieuHuy(th);
	}
	
	public ArrayList<TieuHuy> timTieuHuy(LocalDate dateFrom, LocalDate dateTo) {
		return dao.timTieuHuy(dateFrom, dateTo);
	}
	
	public boolean xuatChiTietExcel(TableModel tbModel, String filename) {
		Workbook workbook = (filename.endsWith(".xls")) ? new HSSFWorkbook() : new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Sheet1");
		
		int rowNum = tbModel.getRowCount() + 2;
		int colNum = tbModel.getColumnCount();	
		
		//Lay data
		for (short i=0; i<=rowNum; i++) {						
			Row row = sheet.createRow(i);
			if (i == 1) continue;
			for (short j=0; j<colNum; j++) {
				String value;
				Cell cell = row.createCell(j);
				Font font;
				if (workbook instanceof XSSFWorkbook) font = (XSSFFont)sheet.getWorkbook().createFont();
				else font = (HSSFFont)sheet.getWorkbook().createFont();
				
				CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
				
				if (i == 0) {		
					font.setBold(true);
					font.setFontHeightInPoints((short)20);
					cellStyle.setAlignment(HorizontalAlignment.CENTER);
					cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
					cellStyle.setFont(font);
					cell.setCellValue("DANH SÁCH SẢN PHẨM BỊ TIÊU HỦY");
					cell.setCellStyle(cellStyle);
					sheet.addMergedRegion(new CellRangeAddress(0,0,0,4));
					break;
				}
				else {
					cellStyle.setBorderBottom(BorderStyle.THIN);
					cellStyle.setBorderTop(BorderStyle.THIN);
					cellStyle.setBorderLeft(BorderStyle.THIN);
					cellStyle.setBorderRight(BorderStyle.THIN);
					
					if (i>2) value = tbModel.getValueAt(i-3, j).toString();
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