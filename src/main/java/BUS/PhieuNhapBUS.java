package BUS;

import DTO.*;
import DAO.PhieuNhapDAO;

import java.io.FileInputStream;
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

import java.io.*;
import java.nio.file.FileSystems;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;
import static org.thymeleaf.templatemode.TemplateMode.HTML;

public class PhieuNhapBUS {
	private PhieuNhapDAO dao = new PhieuNhapDAO();
	private int soDong = 0;
	
	private String taoMa() {
		String ma = Integer.toString(soDong + 1);
		while (ma.length() < 4) ma = "0" + ma;
		return "PN" + ma;
	}
	
	public boolean kiemTraSP(String maSP) {
		return dao.kiemTraSP(maSP);
	}
	
	public boolean check_losp(String masp, String losx) {
		return dao.check_losp(masp, losx);
	} 
	
	public ArrayList<PhieuNhap> getPhieuNhap() {
		ArrayList<PhieuNhap> pn = dao.getPhieuNhap();
		soDong = pn.size();
		return pn;
	}
	
	public PhieuNhap timPhieuNhap(String ma) {
		return dao.timPhieuNhap(ma);
	}
	
	public ArrayList<PhieuNhap> timPhieuNhap(ArrayList<String> ncc, ArrayList<String> sp, LocalDate dateFrom, LocalDate dateTo) {
		return dao.timPhieuNhap(ncc, sp, dateFrom, dateTo);
	}

	public ArrayList<ChiTietPhieuNhap> getSanPhamNhap(String maPN) {
		return dao.getSanPhamNhap(maPN);
	}
	
	public boolean addPhieuNhap(PhieuNhap pn, ArrayList<ChiTietPhieuNhap> spList) {
		pn.setMa(taoMa());
		return dao.addPhieuNhap(pn, spList);
	}
	
	/**
	 * @param filename
	 * @return
	 */
	public ArrayList<ChiTietPhieuNhap> nhapSPExcel(String filename) {
	    ArrayList<ChiTietPhieuNhap> importList = new ArrayList<>(); // Khởi tạo danh sách ở đầu
	    try {
	        FileInputStream fileIn = new FileInputStream(filename);
	        Workbook workbook = (filename.endsWith(".xls")) ? new HSSFWorkbook(fileIn) : new XSSFWorkbook(fileIn);
	        
	        Sheet sheet = workbook.getSheet("Sheet1");
	        
	        // Duyệt từ hàng đầu tiên đến hàng cuối cùng
	        for (int i = sheet.getFirstRowNum()+3; i <= sheet.getLastRowNum(); i++) {
	            Row currentRow = sheet.getRow(i);
	            if (currentRow == null) continue; // Kiểm tra nếu hàng không tồn tại
	            
	            int col = sheet.getRow(0).getPhysicalNumberOfCells();
	            String[] arg = new String[col];
	            for (int j = 0; j < col; j++) {
	                Cell cell = currentRow.getCell(j);
	                if (cell == null) continue; // Kiểm tra nếu ô không tồn tại
	                arg[j] = cell.toString();
	            }

	            ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
	            
	            if (arg.length >= 8) {
	                // Tạo đối tượng ChiTietSanPham và xử lý dữ liệu
	                ChiTietSanPham sp = new ChiTietSanPham(
	                    arg[0],                                   // maSanPham
	                    arg[7],                                   // mavach
	                    arg[1],                                   // loSanXuat
	                    LocalDate.parse(arg[2]),                  // ngaySanXuat
	                    LocalDate.parse(arg[3]),                  // hanSuDung
	                    Integer.parseInt(arg[5]),                 // phanTram
	                    Integer.parseInt(arg[6])                  // soLuong
	                );
	                
	                ctpn.setGiaNhap(Long.parseLong(arg[4]));      // giaNhap
	                ctpn.setChiTiet(sp);                          // Đặt ChiTietSanPham cho ChiTietPhieuNhap
	                importList.add(ctpn);                         // Thêm vào danh sách
	            }
	        }
	        
	        fileIn.close();
	        workbook.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return importList;		
	}


	public boolean xuatMauSPNhap(TableModel tbModel, String filename) {
		Workbook workbook = (filename.endsWith(".xls")) ? new HSSFWorkbook() : new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Sheet1");
		
		int rowNum = 4; //1 dòng tiêu đề, 1 dòng để trống, 1 dòng tiêu đề thông tin, 2 dòng thông tin rỗng (dòng bắt đầu bằng 0)
		int colNum = tbModel.getColumnCount();
		
		//Lay data
		for (short i=0; i<=rowNum; i++) {						
			Row row = sheet.createRow(i);
			if (i == 1) continue;
			for (short j=0; j<colNum; j++) {
				String value = "";
				Cell cell = row.createCell(j);
				Font font;
				if (workbook instanceof XSSFWorkbook) font = (XSSFFont)sheet.getWorkbook().createFont();
				else font = (HSSFFont)sheet.getWorkbook().createFont();
				
				CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
				cellStyle.setDataFormat(workbook.createDataFormat().getFormat("@"));
				
				if (i == 0) {		
					font.setBold(true);
					font.setFontHeightInPoints((short)20);
					cellStyle.setAlignment(HorizontalAlignment.CENTER);
					cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
					cellStyle.setFont(font);
					cell.setCellValue("DANH SÁCH SẢN PHẨM NHẬP");
					cell.setCellStyle(cellStyle);
					sheet.addMergedRegion(new CellRangeAddress(0,0,0,colNum - 1));
					break;
				}
				else {
					cellStyle.setBorderBottom(BorderStyle.THIN);
					cellStyle.setBorderTop(BorderStyle.THIN);
					cellStyle.setBorderLeft(BorderStyle.THIN);
					cellStyle.setBorderRight(BorderStyle.THIN);
					
					if (i == 2) {					
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
	
	public boolean xuatChiTietExcel(TableModel tbModel, String filename) {
	    Workbook workbook = (filename.endsWith(".xls")) ? new HSSFWorkbook() : new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Sheet1");
	    
	    int rowNum = tbModel.getRowCount() + 2;
	    int colNum = tbModel.getColumnCount();    
	    
	    //Lay data
	    for (short i=0; i<=rowNum; i++) {                        
	        Row row = sheet.createRow(i);
	        if (i == 1) continue; // Bỏ qua dòng thứ 1
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
	                cell.setCellValue("DANH SÁCH PHIẾU NHẬP");
	                cell.setCellStyle(cellStyle);
	                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, colNum - 1));
	                break;
	            }
	            else {
	                cellStyle.setBorderBottom(BorderStyle.THIN);
	                cellStyle.setBorderTop(BorderStyle.THIN);
	                cellStyle.setBorderLeft(BorderStyle.THIN);
	                cellStyle.setBorderRight(BorderStyle.THIN);
	                
	                if (i > 2) {
	                    value = tbModel.getValueAt(i - 3, j).toString();
	                    
	                    // Kiểm tra nếu cột là cột giaban (ví dụ ở vị trí thứ 5)
	                    if (j == 5) { // Cột thứ 5 (giá bán)
	                        double phantram = Double.parseDouble(value);
	                        // Bạn có thể thực hiện các thay đổi tại đây (ví dụ: định dạng, tính toán lại giá)
	                        value = String.format("%.2f", phantram); // Định dạng giá bán với 2 chữ số thập phân
	                    }
	                }
	                else {                    
	                    value = tbModel.getColumnName(j);
	                    if (workbook instanceof XSSFWorkbook) {
	                        ((XSSFFont)font).setColor(new XSSFColor(new java.awt.Color(255, 255, 255), null));
	                        cellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(0, 100, 0), null));
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
	    
	    for (short j = 0; j < colNum; j++) sheet.autoSizeColumn(j);
	    
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

    public ChiTietPhieuNhap timChiTietPhieuNhapTheoMaVach(String mavach) {
        if (mavach == null || mavach.isEmpty()) {
            System.out.println("Mã vạch không được để trống.");
            return null;
        }

        // Gọi DAO để lấy chi tiết phiếu nhập từ database
        ChiTietPhieuNhap chiTietPhieuNhap = PhieuNhapDAO.timChiTietPhieuNhap(mavach);

        if (chiTietPhieuNhap != null) {
            System.out.println("Đã tìm thấy chi tiết phiếu nhập cho mã vạch: " + mavach);
        } else {
            System.out.println("Không tìm thấy chi tiết phiếu nhập cho mã vạch: " + mavach);
        }

        return chiTietPhieuNhap;
    }
	public boolean xuatPDF(PhieuNhap phieuNhap, ArrayList<ChiTietPhieuNhap> ctsp, String path) {
		try {
		    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		    templateResolver.setPrefix("/");
		    templateResolver.setSuffix(".html");
		    templateResolver.setTemplateMode(HTML);
		    templateResolver.setCharacterEncoding("UTF-8");
	
		    TemplateEngine templateEngine = new TemplateEngine();
		    templateEngine.setTemplateResolver(templateResolver);
	
		    Context context = new Context();
		    context.setVariable("soPhieu", phieuNhap.getMa());
		
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		    String formattedDate = LocalDateTime.now().format(formatter);
		    context.setVariable("thoiGian", formattedDate);
		    
		    context.setVariable("ctsp", ctsp);
		    context.setVariable("tongTien", phieuNhap.getTongTien());
		    
		    for (DuocSi ds: new DuocSiBUS().getDuocSi())
		    	if (ds.getMa().equals(phieuNhap.getNguoiLap())) {
		    		context.setVariable("nguoiLap", ds.getTen());
		    		break;
		    	}
	
		    String renderedHtmlContent = templateEngine.process("phieunhap", context);
		    
		    FontFactory.defaultEmbedding = true;
		    ITextRenderer renderer = new ITextRenderer();
		    renderer.getFontResolver().addFont("/segoeui.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		    String baseUrl = FileSystems.getDefault().getPath("src", "main", "resources").toUri().toURL().toString();
		    
		    renderer.setDocumentFromString(renderedHtmlContent, baseUrl);
		    renderer.layout();
		  
		    OutputStream outputStream = new FileOutputStream(path);
		    renderer.createPDF(outputStream);
		    outputStream.close();
		    return true;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		    return false;
		}
	}
}
