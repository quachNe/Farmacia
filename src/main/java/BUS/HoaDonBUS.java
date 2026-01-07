package BUS;

import DTO.*;
import DAO.HoaDonDAO;

import static org.thymeleaf.templatemode.TemplateMode.HTML;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.sql.Date;
import java.time.LocalDateTime;
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
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class HoaDonBUS {
	private HoaDonDAO hdDAO = new HoaDonDAO();
	private int soDong;

	public ArrayList<KhachHang> getKhachHang() {
		return hdDAO.getKhachHang();
	}
	public ArrayList<HoaDon> getHoaDon(){
		ArrayList<HoaDon> hd = hdDAO.getHoaDon();
		soDong = hd.size();
		return hd;
	}
	
	public HoaDon getHoaDon(String maHD){
		return hdDAO.getHoaDon(maHD);
	}

	public ArrayList<HoaDon> searchHoaDon(String option, String key) {
		return hdDAO.searchHoaDon(option, key);
	}
	
	public ArrayList<String> getLoSX(String maSP) {
		return hdDAO.getLoSX(maSP);
	}
	
	public ChiTietSanPham getChiTietSP(String maSP, String loSX) {
		return hdDAO.getChiTietSP(maSP, loSX);
	}
	public boolean updateDiem(int makh, int diem) {
		return hdDAO.updateDiem(makh, diem);
	}
	public int getDiem(int makh) {
	    return hdDAO.getDiem(makh);
	}
	public ArrayList<ChiTietHoaDon> getChiTietHD(String maHD) {
		return hdDAO.getChiTietHD(maHD);
	}
	
//	public boolean updateSoLuong(String mact,String losx, int soLuongBanRa) {
//		return hdDAO.updateSoLuong(mact,losx, soLuongBanRa);
//    }
	private String taoMa() {
		String ma = Integer.toString(soDong + 1);
		while (ma.length() < 4) ma = "0" + ma;
		return "HD" + ma;
	}
	
	public boolean addHoaDon(HoaDon hd, ArrayList<ChiTietHoaDon> spList, KhachHang kh, boolean useDiem,int diemConLai) {
		hd.setMa(taoMa());
		return hdDAO.addHoaDon(hd, spList, kh, useDiem,diemConLai);
	}
	
	public boolean addKhachHang(String hoten,Date ngaysinh,String gioitinh,String sdt, int diem) {
		return hdDAO.addKhachHang(hoten, ngaysinh, gioitinh, sdt, diem);
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
					cell.setCellValue("DANH SÁCH HÓA ĐƠN");
					cell.setCellStyle(cellStyle);
					sheet.addMergedRegion(new CellRangeAddress(0,0,0,colNum - 1));
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
	
	public ArrayList<KhachHang> xuatAllKhachHang(){
		ArrayList<KhachHang> kh = new ArrayList();
		kh = hdDAO.getAllKhachHang();
		return kh;
		
		
		
	}
	public boolean xuatPDF(HoaDon hoaDon, ArrayList<ChiTietHoaDon> cthd, KhachHang kh, String path) {
	    try {
	        // Cấu hình Template Resolver cho Thymeleaf
	        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
	        templateResolver.setPrefix("/");
	        templateResolver.setSuffix(".html");
	        templateResolver.setTemplateMode(HTML);
	        templateResolver.setCharacterEncoding("UTF-8");

	        TemplateEngine templateEngine = new TemplateEngine();
	        templateEngine.setTemplateResolver(templateResolver);

	        Context context = new Context();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	        String formattedDate = LocalDateTime.now().format(formatter);
	        context.setVariable("thoiGian", formattedDate);
	        context.setVariable("maHD", hoaDon.getMa());
	        
	        // Lấy thông tin người bán
	        DuocSi duocSi = new DuocSiBUS().getDuocSi().stream()
	            .filter(ds -> ds.getMa().equals(hoaDon.getNguoiLap()))
	            .findFirst()
	            .orElse(null);
	        
	        if (duocSi != null) {
	            context.setVariable("nguoiBan", duocSi.getTen());
	        } else {
	            context.setVariable("nguoiBan", "Nhân viên không xác định");
	        }

	        // Kiểm tra đối tượng khách hàng
	        if (kh != null && kh.getMa() != 0) {
	            context.setVariable("tenKH", kh.getHoTen());

	            // Lấy số điện thoại và ẩn các chữ số, chỉ hiển thị 3 số cuối
	            String soDienThoai = kh.getSoDT();
	            String soDienThoaiAn = soDienThoai.replaceAll(".(?=.{3})", "*");

	            context.setVariable("soDienThoai", soDienThoaiAn);
	            
	        } else {
	            context.setVariable("tenKH", "Vãng lai");
	            context.setVariable("soDienThoai", "");
	        }


	        // Truyền chi tiết hóa đơn vào context
	        context.setVariable("cthd", cthd);
	        context.setVariable("tongTien", hoaDon.getTongTien());
	        context.setVariable("diemTichLuy", hoaDon.getDiemTichLuy());

	        // Xử lý chi tiết hóa đơn
	        SanPhamBUS spBus = new SanPhamBUS();
	        ArrayList<SanPham> spList = spBus.getAllSp();
	        for (ChiTietHoaDon chiTiet : cthd) {
	            for (SanPham sp : spList) {
	                if (chiTiet.getChiTiet() != null && chiTiet.getChiTiet().getMaSanPham() != null) {
	                    if (chiTiet.getChiTiet().getMaSanPham().equals(sp.getMa())) {
	                        chiTiet.getChiTiet().setSanPham(sp.getMa(), sp.getTen());
	                    }
	                } else {
	                    System.out.println("Chi tiết hoặc sản phẩm null");
	                }
	            }
	        }

	        // Render HTML từ template "hoadon.html"
	        String renderedHtmlContent = templateEngine.process("hoadon", context);

	        // Cấu hình iText để tạo PDF
	        FontFactory.defaultEmbedding = true;
	        ITextRenderer renderer = new ITextRenderer();
	        renderer.getFontResolver().addFont("/segoeui.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

	        // Thiết lập base URL cho tài nguyên (hình ảnh, css)
	        String baseUrl = FileSystems.getDefault().getPath("src", "main", "resources").toUri().toURL().toString();
	        renderer.setDocumentFromString(renderedHtmlContent, baseUrl);
	        renderer.layout();

	        // Ghi file PDF
	        try (OutputStream outputStream = new FileOutputStream(path)) {
	            renderer.createPDF(outputStream);
	        }

	        return true;
	    } catch (Exception ex) {
	        // Log lỗi nếu có ngoại lệ xảy ra
	        ex.printStackTrace();
	        System.err.println("Lỗi khi xuất PDF: " + ex.getMessage());
	        return false;
	    }
	}

		 
	
	
public boolean xuatPDF1(HoaDon hoaDon, ArrayList<ChiTietHoaDon> cthd, String path) {
    try {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(HTML);
        templateResolver.setCharacterEncoding("UTF-8");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("thoiGian", LocalDateTime.now().toString());
        context.setVariable("maHD", hoaDon.getMa());
        for (DuocSi ds: new DuocSiBUS().getDuocSi())
            if (ds.getMa().equals(hoaDon.getNguoiLap())) {
                context.setVariable("nguoiBan", ds.getTen());
                break;
            }
        if (hoaDon.getKhachHang() !=0) {
        	ArrayList<KhachHang> kh= new ArrayList();
        	HoaDonDAO hdDao= new HoaDonDAO();
        	kh=hdDao.getAllKhachHang();
        	for(KhachHang kh1 :kh)
        	{
        		if(kh1.getMa()==hoaDon.getKhachHang()) {
        		context.setVariable("tenKH", kh1.getHoTen());
	            context.setVariable("soDienThoai", kh1.getSoDT());
	            break;
        		}
        	
        	}
            	        }
        else {
            context.setVariable("tenKH", "Vãng lai");
            context.setVariable("soDienThoai", "");
        }

        // Cập nhật để truyền phần trăm vào ngữ cảnh
        context.setVariable("cthd", cthd);
        context.setVariable("tongTien", hoaDon.getTongTien());
        context.setVariable("diemTichLuy", hoaDon.getDiemTichLuy());

        String renderedHtmlContent = templateEngine.process("hoadon", context);

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
    } catch (Exception ex) {
        ex.printStackTrace();
        return false;
    }


}}
