/*QUẢN LÍ CỬA HÀNG BÁN THUỐC*/
/*KHÔNG ĐƯỢC XÓA DÒNG 280 281 nha các đại ka
-- /*3 CÂU LỆNH DƯỚI ĐÂY, CHẠY LẦN LƯỢT TỪNG CÂU LỆNH MỘT*/
-- drop database if exists quanlynhathuoc
-- create database quanlynhathuoc
-- use quanlynhathuoc	
-- /*SAU ĐÓ CÓ THỂ CHẠY TOÀN BỘ CÁC CÂU LỆNH DƯỚI ĐÂY*/
-- Tạo bảng countries
CREATE TABLE countries (
    id INT AUTO_INCREMENT PRIMARY KEY,
    country_name NVARCHAR(40) NOT NULL UNIQUE
);

-- Tạo bảng loaisanpham (Loại sản phẩm)
CREATE TABLE loaisanpham (
    maloai VARCHAR(50) PRIMARY KEY,
    tenloai NVARCHAR(50),
    trangthai BOOLEAN DEFAULT TRUE
);

-- Tạo bảng nhasanxuat (Nhà sản xuất)
CREATE TABLE nhasanxuat (
    mansx VARCHAR(50) PRIMARY KEY,
    tennsx NVARCHAR(100),
    trangthai BOOLEAN DEFAULT TRUE
);

-- Tạo bảng sanpham (Sản phẩm)
CREATE TABLE sanpham (
    masp VARCHAR(50) PRIMARY KEY,
    tensp NVARCHAR(200),
    loaisp VARCHAR(50),
    nhasanxuat VARCHAR(50),
    quycach NVARCHAR(20),
    xuatxu NVARCHAR(40),
    canketoa BOOLEAN,
    trangthai BOOLEAN DEFAULT TRUE,
    CONSTRAINT FOREIGN KEY (loaisp) REFERENCES loaisanpham(maloai),
    CONSTRAINT FOREIGN KEY (nhasanxuat) REFERENCES nhasanxuat(mansx),
    CONSTRAINT fk_xuatxu FOREIGN KEY (xuatxu) REFERENCES countries(country_name)
);

-- Tạo bảng dieutri (Điều trị)
CREATE TABLE dieutri (
    mathuoc VARCHAR(50) NOT NULL,
    benhdieutri NVARCHAR(100) NOT NULL,
    CONSTRAINT FOREIGN KEY (mathuoc) REFERENCES sanpham(masp),
    PRIMARY KEY (mathuoc, benhdieutri)
);

-- Tạo bảng anh (Ảnh sản phẩm)
CREATE TABLE anh (
    mathuoc VARCHAR(50) NOT NULL,
    anh NVARCHAR(100),
    PRIMARY KEY (mathuoc, anh),
    CONSTRAINT FOREIGN KEY (mathuoc) REFERENCES sanpham(masp)
);

-- Tạo bảng chuy (Công dụng)
CREATE TABLE chuy (
    mathuoc VARCHAR(50) NOT NULL,
    chuy NVARCHAR(500),
    PRIMARY KEY (mathuoc, chuy),
    CONSTRAINT FOREIGN KEY (mathuoc) REFERENCES sanpham(masp)
);

-- Tạo bảng thanhphan (Thành phần)
CREATE TABLE thanhphan (
    mathuoc VARCHAR(50) NOT NULL,
    thanhphan NVARCHAR(100) NOT NULL,
    PRIMARY KEY (mathuoc, thanhphan),
    CONSTRAINT FOREIGN KEY (mathuoc) REFERENCES sanpham(masp)
);

-- Tạo bảng duocsi (Dược sĩ)
CREATE TABLE duocsi (
    mads VARCHAR(50) PRIMARY KEY,
    hoten NVARCHAR(50),
    sodt CHAR(10),
    email VARCHAR(100),
    trangthai BOOLEAN DEFAULT TRUE
);

-- Tạo bảng anhavt (Ảnh avatar)
CREATE TABLE anhavt (
    mads VARCHAR(50) NOT NULL,
    anhavt NVARCHAR(100),
    PRIMARY KEY (mads, anhavt),
    CONSTRAINT FOREIGN KEY (mads) REFERENCES duocsi(mads)
);

-- Tạo bảng taikhoan (Tài khoản)
CREATE TABLE taikhoan (
    matk VARCHAR(50) PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(200),
    quyen INT,
    CONSTRAINT FOREIGN KEY (matk) REFERENCES duocsi(mads)
);

-- Tạo bảng nhacungcap (Nhà cung cấp)
CREATE TABLE nhacungcap (
    mancc VARCHAR(50) PRIMARY KEY,
    tenncc NVARCHAR(200),
    diachi NVARCHAR(200),
    sodt VARCHAR(15),
    email VARCHAR(100),
    trangthai BOOLEAN DEFAULT TRUE
);

-- Tạo bảng phieunhap (Phiếu nhập)
CREATE TABLE phieunhap (
    mapn VARCHAR(50) PRIMARY KEY,
    ngaylap DATE,
    nguoilap VARCHAR(50),
    nhacungcap VARCHAR(50),
    tongtien DECIMAL(10, 0),
    CONSTRAINT FOREIGN KEY (nguoilap) REFERENCES duocsi(mads),
    CONSTRAINT FOREIGN KEY (nhacungcap) REFERENCES nhacungcap(mancc)
);

-- Tạo bảng khachhang (Khách hàng)
CREATE TABLE khachhang (
    makh INT PRIMARY KEY AUTO_INCREMENT,
    hoten NVARCHAR(50),
    ngaysinh DATE,
    gioitinh NVARCHAR(3),
    sodt CHAR(10),
    diem INT DEFAULT 0
);

-- Tạo bảng hoadon (Hóa đơn)
CREATE TABLE hoadon (
    maHD VARCHAR(50) PRIMARY KEY,
    ngaylap DATE,
    nguoilap VARCHAR(50),
    khachhang INT,
    toathuoc VARCHAR(512), -- chứa đường dẫn của hình ảnh toa thuốc
    diemtichluy INT,
    tongtien DECIMAL(10, 0),
    CONSTRAINT FOREIGN KEY (nguoilap) REFERENCES duocsi(mads),
    CONSTRAINT FOREIGN KEY (khachhang) REFERENCES khachhang(makh)
);

-- Tạo bảng chitietsanpham (Chi tiết sản phẩm)
CREATE TABLE chitietsanpham (
    mavach VARCHAR(20) NOT NULL,
    masp VARCHAR(50) NOT NULL,
    losx VARCHAR(50),
    ngaysx DATE,
    hansd DATE,
    phantram DECIMAL(10, 0), -- Thay đổi giá bán thành phần trăm
    soluong INT,
    CONSTRAINT FOREIGN KEY (masp) REFERENCES sanpham(masp),
    PRIMARY KEY (mavach, masp) -- Khóa chính kết hợp từ mavach và masp
);

-- Tạo bảng chitietphieunhap (Chi tiết phiếu nhập)
CREATE TABLE chitietphieunhap (
    mapn VARCHAR(50) NOT NULL,
    mavach VARCHAR(20) NOT NULL,
    losx VARCHAR(50),
    gianhap DECIMAL(10, 0),
    soluong INT,
    CONSTRAINT FOREIGN KEY (mapn) REFERENCES phieunhap(mapn),
    CONSTRAINT FOREIGN KEY (mavach) REFERENCES chitietsanpham(mavach),
    PRIMARY KEY (mapn, mavach)
);

-- Tạo bảng chitiethoadon (Chi tiết hóa đơn)
CREATE TABLE chitiethoadon (
    mahd VARCHAR(50) NOT NULL,
    mavach VARCHAR(20),
    giaban DECIMAL(10, 0),
    soLuong INT,
    CONSTRAINT FOREIGN KEY (mahd) REFERENCES hoadon(mahd),
    CONSTRAINT FOREIGN KEY (mavach) REFERENCES chitietsanpham(mavach),
    PRIMARY KEY (mahd, mavach)
);

-- Tạo bảng tieuhuy (Tiêu hủy sản phẩm)
CREATE TABLE tieuhuy (
    mavach VARCHAR(20) PRIMARY KEY,
    ngaytieuhuy DATE NOT NULL,
    nguoilap VARCHAR(50) NOT NULL,
    lydo NVARCHAR(512),
    thiethai DECIMAL(10, 0),
    CONSTRAINT FOREIGN KEY (mavach) REFERENCES chitietsanpham(mavach)
);

-- Tạo stored procedure insertTH
DELIMITER $$
DROP PROCEDURE IF EXISTS `insertTH`$$
CREATE PROCEDURE `insertTH`(
    IN in_maSP VARCHAR(50),
    IN in_loSX VARCHAR(50),
    IN in_ngayTieuHuy DATE,
    IN in_nguoilap VARCHAR(50),
    IN in_lyDo NVARCHAR(512),
    IN in_thietHai DECIMAL(10, 2)
)
BEGIN
    DECLARE in_mavach VARCHAR(50);

    -- Tìm mã vạch từ bảng ChiTietSanPham
    SELECT mavach INTO in_mavach
    FROM chitietsanpham
    WHERE masp = in_maSP AND losx = in_loSX;

    -- Đặt số lượng thành 0 cho mã vạch
    UPDATE chitietsanpham
    SET soLuong = 0
    WHERE mavach = in_mavach;

    -- Thêm thông tin vào bảng tieuhuy
    INSERT INTO tieuhuy (mavach, ngaytieuhuy, nguoilap, lydo, thiethai)
    VALUES (in_mavach, in_ngayTieuHuy, in_nguoilap, in_lyDo, in_thietHai);
END$$
DELIMITER ;

-- Tạo stored procedure insertPN
DELIMITER $$
DROP PROCEDURE IF EXISTS `insertPN`$$
CREATE PROCEDURE `insertPN`(
    IN in_maPN VARCHAR(50),
    IN in_maSP VARCHAR(50),
    IN in_mavach VARCHAR(20),
    IN in_loSX VARCHAR(50),
    IN in_ngaySX DATE,
    IN in_hanSD DATE,
    IN in_giaNhap DECIMAL(10, 2),
    IN in_giaBan DECIMAL(10, 2),
    IN in_soLuong INT
)
BEGIN
    DECLARE old_soLuong INT;

    -- Kiểm tra số lượng sản phẩm có sẵn
    SELECT soLuong INTO old_soLuong
    FROM chitietsanpham
    WHERE masp = in_maSP AND losx = in_loSX AND mavach = in_mavach;

    IF old_soLuong IS NOT NULL THEN
        -- Cập nhật số lượng nếu sản phẩm đã tồn tại
        UPDATE chitietsanpham
        SET soLuong = old_soLuong + in_soLuong
        WHERE masp = in_maSP AND losx = in_loSX AND mavach = in_mavach;
    ELSE
        -- Thêm sản phẩm mới nếu chưa tồn tại
        INSERT INTO chitietsanpham (mavach, masp, losx, ngaysx, hansd, phantram, soLuong)
        VALUES (in_mavach, in_maSP, in_loSX, in_ngaySX, in_hanSD, in_giaBan, in_soLuong);
    END IF;

    -- Thêm chi tiết phiếu nhập
    INSERT INTO chitietphieunhap (mapn, mavach, losx, gianhap, soluong)
    VALUES (in_maPN, in_mavach, in_loSX, in_giaNhap, in_soLuong);
END$$
DELIMITER ;

-- Tạo stored procedure updateDiem
DELIMITER $$
DROP PROCEDURE IF EXISTS `updateDiem`$$
CREATE PROCEDURE `updateDiem`(
    IN in_maKH INT,
    IN in_diem INT
)
BEGIN
    UPDATE khachhang
    SET diem = diem + in_diem
    WHERE makh = in_maKH;
END$$
DELIMITER ;

-- Tạo stored procedure insertCTHD
DELIMITER $$

DROP PROCEDURE IF EXISTS `insertCTHD`$$

CREATE PROCEDURE `insertCTHD`(
    IN in_maHD VARCHAR(50),
    IN in_mavach VARCHAR(20),
    IN in_giaBan DECIMAL(10,2),
    IN in_soLuong INT
)
BEGIN

    -- Biến tách các phần của mã vạch
    DECLARE prefix VARCHAR(10);
    DECLARE suffix INT;

    -- Tách mã vạch thành hai phần: phần trước dấu '-' và phần sau dấu '-'
    SET prefix = SUBSTRING_INDEX(in_mavach, '-', 1);
    SET suffix = CAST(SUBSTRING_INDEX(in_mavach, '-', -1) AS UNSIGNED);

    -- Thêm thông tin vào bảng chitiethoadon
    INSERT INTO chitiethoadon (maHD, mavach, giaBan, soLuong)
    VALUES (in_maHD, in_mavach, in_giaBan, in_soLuong);

    -- Lược bỏ phần cập nhật số lượng trong chitietsanpham
    -- Đã xóa câu lệnh UPDATE ở đây

END$$
DELIMITER ;





insert into loaisanpham values
('LSP0001', 'Thuốc', 1),
('LSP0002', 'Thực phẩm chức năng', 1),
('LSP0003', 'Thuốc tiêu hoá', 1),
('LSP0004', 'Thuốc dạ dày', 1),
('LSP0005', 'Thuốc say tàu xe', 1),
('LSP0006', 'Siro tiêu hoá', 1),
('LSP0007', 'Thuốc chống dị ứng', 1),
('LSP0008', 'Thuốc thần kinh', 1),
('LSP0009', 'Các dụng cụ và sản phẩm khác', 1),
('LSP0010', 'Miếng dán say tàu xe', 1),
('LSP0011', 'Thuốc gan mật', 1),
('LSP0012', 'Thuốc trị đau nửa đầu', 1),
('LSP0013', 'Thuốc tăng cường tuần hoàn não', 1);
insert into nhasanxuat values
('NSX0001', 'Sanofi', 1),
('NSX0002', 'Tâm Bình', 1),
('NSX0003', 'Công ty cổ phần công nghệ cao Traphaco', 1);
INSERT INTO countries (country_name) VALUES
('Việt Nam'),
('Hoa Kỳ'),
('Trung Quốc'),
('Nhật Bản'),
('Đức'),
('Pháp'),
('Anh'),
('Ý'),
('Nga'),
('Ấn Độ'),
('Brazil'),
('Canada'),
('Mexico'),
('Hàn Quốc'),
('Thụy Điển'),
('Thái Lan'),
('Australia'),
('Nam Phi'),
('Thổ Nhĩ Kỳ'),
('Indonesia'),
('Pakistan'),
('Saudi Arabia'),
('Argentina'),
('Tây Ban Nha'),
('Bồ Đào Nha'),
('New Zealand'),
('Malaysia'),
('Ai Cập'),
('Ireland'),
('Philippines'),
('Na Uy'),
('Ba Lan'),
('Ukraina'),
('Hà Lan'),
('Bỉ'),
('Đan Mạch'),
('Singapore'),
('Phần Lan'),
('Hy Lạp'),
('Thụy Sĩ'),
('Colombia'),
('Chile'),
('Venezuela'),
('Israel'),
('Iran'),
('Iraq'),
('Afghanistan'),
('Cuba'),
('Bangladesh'),
('Sri Lanka'),
('Nepal');
insert into sanpham values
('SP0001', 'Thuốc Boganic Traphaco bổ gan, hỗ trợ điều trị suy giảm chức năng gan (5 vỉ x 20 viên)', 'LSP0002', 'NSX0003', 'Hộp 5 Vỉ x 20 Viên', 'Việt Nam', 0, 1),
('SP0002', 'Viên nén Methyldopa 250mg Traphaco điều trị tăng huyết áp (10 vỉ x 10 viên)', 'LSP0001', 'NSX0003', 'Hộp 10 Vỉ x 10 Viên', 'Việt Nam', 1, 1),
('SP0003', 'Viên nhuận tràng Ovalax 5mg Traphaco điều trị táo bón, làm sạch ruột (1 vỉ x 10 viên)', 'LSP0001', 'NSX0003', 'Hộp 1 vỉ x 10 viên', 'Việt Nam', 0, 1),
('SP0004', 'Viên ngậm Cagu Traphaco điều trị viêm họng, ho do lạnh, viêm thanh quản (2 vỉ x 10 viên)', 'LSP0002', 'NSX0003', 'Hộp 2 vỉ x 10 viên', 'Việt Nam', 0, 1),
('SP0005', 'Thuốc Solupred 5mg Sanofi dùng chống viêm (30 viên)', 'LSP0001', 'NSX0001', 'Hộp 30 viên', 'Pháp', 1, 1),
('SP0006', 'Thuốc Fumafer B9 Corbière Sanofi điều trị thiếu máu do thiếu sắt (8 vỉ x 15 viên)', 'LSP0001', 'NSX0001', 'Hộp 8 vỉ x 15 viên', 'Pháp', 0, 1),
('SP0007', 'Hỗn dịch uống Phosphalugel Sanofi giảm độ axit của dạ dày (26 gói x 20g)', 'LSP0001', 'NSX0001', 'Hộp 26 Gói x 20g', 'Pháp', 0, 1),
('SP0008', 'Thuốc Telfast HD 180mg Sanofi điều trị viêm mũi dị ứng, mày đay (1 vỉ x 10 viên)', 'LSP0001', 'NSX0001', 'Hộp 1 Vỉ x 10 Viên', 'Pháp', 0, 1),
('SP0009', 'Viên khớp Tâm Bình hỗ trợ giảm các triệu chứng của thoái hóa khớp, viêm khớp (60 viên)', 'LSP0002', 'NSX0002', 'Hộp 60 viên', 'Việt Nam', 0, 1),
('SP0010', 'Viên uống Bổ gan Tâm Bình tăng cường chức năng gan, thanh nhiệt, giải độc (60 viên)', 'LSP0002', 'NSX0002', 'Hộp 60 viên', 'Việt Nam', 0, 1),
('SP0011', 'Viên uống Đại Tràng Tâm Bình hỗ trợ giảm các triệu chứng của viêm đại tràng cấp và mãn tính (5 vỉ x 12 viên)', 'LSP0002', 'NSX0002', 'Hộp 5 vỉ x 12 viên', 'Việt Nam', 0, 1),
('SP0012', 'Thuốc Dưỡng Tâm An Thần Danapha điều trị mất ngủ do lo âu, làm việc quá sức (100 viên)', 'LSP0002', 'NSX0002', 'Hộp 100 viên', 'Việt Nam', 1, 1),
('SP0013', 'Thuốc Vitamin B12 Traphaco hỗ trợ điều trị thiếu máu, mệt mỏi (2 vỉ x 10 viên)', 'LSP0001', 'NSX0003', 'Hộp 2 Vỉ x 10 Viên', 'Việt Nam', 0, 1),
('SP0014', 'Viên uống Vitamin E 400 IU điều trị thiếu Vitamin E, hỗ trợ làm đẹp da (30 viên)', 'LSP0001', 'NSX0003', 'Hộp 30 Viên', 'Việt Nam', 0, 1),
('SP0015', 'Thuốc Omeprazol 20mg Traphaco điều trị loét dạ dày, trào ngược axit (5 vỉ x 10 viên)', 'LSP0001', 'NSX0003', 'Hộp 5 Vỉ x 10 Viên', 'Việt Nam', 1, 1),
('SP0016', 'Viên uống Canxi Corbière Sanofi bổ sung canxi, hỗ trợ xương chắc khỏe (6 ống x 10ml)', 'LSP0001', 'NSX0001', 'Hộp 6 Ống x 10ml', 'Pháp', 0, 1),
('SP0017', 'Thuốc Tăng Cường Trí Não Ginkgo Biloba 60mg (30 viên)', 'LSP0001', 'NSX0003', 'Hộp 30 Viên', 'Việt Nam', 0, 1),
('SP0018', 'Thuốc Bổ Mắt Traphaco hỗ trợ điều trị thoái hóa điểm vàng (10 viên)', 'LSP0001', 'NSX0003', 'Hộp 10 Viên', 'Việt Nam', 0, 1),
('SP0019', 'Thuốc Trợ Tim Digoxin 0.25mg điều trị suy tim (5 vỉ x 10 viên)', 'LSP0001', 'NSX0003', 'Hộp 5 Vỉ x 10 Viên', 'Việt Nam', 1, 1),
('SP0020', 'Thuốc Hạ Sốt Paracetamol 500mg (2 vỉ x 10 viên)', 'LSP0001', 'NSX0003', 'Hộp 2 Vỉ x 10 Viên', 'Việt Nam', 0, 1),
('SP0021', 'Viên uống Bổ Khớp Glucosamine (60 viên)', 'LSP0001', 'NSX0002', 'Hộp 60 Viên', 'Việt Nam', 0, 1),
('SP0022', 'Thuốc Giảm Đau Aspirin 81mg (10 viên)', 'LSP0001', 'NSX0003', 'Hộp 10 Viên', 'Việt Nam', 0, 1),
('SP0023', 'Thuốc An Thần Diazepam 5mg điều trị lo âu, mất ngủ (2 vỉ x 10 viên)', 'LSP0001', 'NSX0003', 'Hộp 2 Vỉ x 10 Viên', 'Việt Nam', 1, 1),
('SP0024', 'Viên uống Tăng Đề Kháng Echinacea (30 viên)', 'LSP0001', 'NSX0003', 'Hộp 30 Viên', 'Việt Nam', 0, 1),
('SP0025', 'Viên uống Chống Dị Ứng Loratadine 10mg (2 vỉ x 10 viên)', 'LSP0001', 'NSX0003', 'Hộp 2 Vỉ x 10 Viên', 'Việt Nam', 0, 1),
('SP0026', 'Viên uống Bổ Sung Vitamin C 500mg (30 viên)', 'LSP0001', 'NSX0003', 'Hộp 30 Viên', 'Việt Nam', 0, 1),
('SP0027', 'Thuốc Giảm Cân Orlistat 120mg (2 vỉ x 10 viên)', 'LSP0001', 'NSX0003', 'Hộp 2 Vỉ x 10 Viên', 'Việt Nam', 1, 1),
('SP0028', 'Thuốc Hạ Mỡ Máu Simvastatin 20mg (5 vỉ x 10 viên)', 'LSP0001', 'NSX0003', 'Hộp 5 Vỉ x 10 Viên', 'Việt Nam', 0, 1),
('SP0029', 'Viên uống Giảm Đường Huyết Metformin 500mg (10 vỉ x 10 viên)', 'LSP0001', 'NSX0003', 'Hộp 10 Vỉ x 10 Viên', 'Việt Nam', 1, 1),
('SP0030', 'Viên uống Sáng Da Vitamin C & E (30 viên)', 'LSP0001', 'NSX0003', 'Hộp 30 Viên', 'Việt Nam', 0, 1),
('SP0031', 'Viên uống Hỗ Trợ Tim Mạch CoQ10 (30 viên)', 'LSP0001', 'NSX0003', 'Hộp 30 Viên', 'Việt Nam', 0, 1),
('SP0032', 'Thuốc Giảm Axit Dạ Dày Omeprazol 40mg (2 vỉ x 10 viên)', 'LSP0001', 'NSX0003', 'Hộp 2 Vỉ x 10 Viên', 'Việt Nam', 0, 1),
('SP0033', 'Thuốc Tăng Cường Đề Kháng Immune Plus (30 viên)', 'LSP0001', 'NSX0003', 'Hộp 30 Viên', 'Việt Nam', 0, 1),
('SP0034', 'Viên uống Tăng Sinh Lý Nam Maca (60 viên)', 'LSP0001', 'NSX0003', 'Hộp 60 Viên', 'Việt Nam', 0, 1),
('SP0035', 'Thuốc Giảm Cân CLA 1000mg (30 viên)', 'LSP0001', 'NSX0003', 'Hộp 30 Viên', 'Việt Nam', 0, 1),
('SP0036', 'Thuốc Tăng Cường Xương Khớp Chondroitin (30 viên)', 'LSP0001', 'NSX0003', 'Hộp 30 Viên', 'Việt Nam', 0, 1),
('SP0037', 'Viên uống Giảm Mỡ Bụng CLA 2000mg (30 viên)', 'LSP0001', 'NSX0003', 'Hộp 30 Viên', 'Việt Nam', 0, 1),
('SP0038', 'Thuốc Trị Mụn Acnotin 10mg (3 vỉ x 10 viên)', 'LSP0001', 'NSX0003', 'Hộp 3 Vỉ x 10 Viên', 'Việt Nam', 1, 1),
('SP0039', 'Viên uống Omega 3 1000mg (30 viên)', 'LSP0001', 'NSX0003', 'Hộp 30 Viên', 'Việt Nam', 0, 1),
('SP0040', 'Viên uống Hỗ Trợ Giảm Đường Huyết Alpha Lipoic Acid (30 viên)', 'LSP0001', 'NSX0003', 'Hộp 30 Viên', 'Việt Nam', 0, 1),
('SP0041', 'Thuốc Điều Trị Mụn Differin 0.1% (1 tuýp x 30g)', 'LSP0001', 'NSX0003', 'Tuýp 30g', 'Việt Nam', 1, 1),
('SP0042', 'Thuốc Trị Ho Dextromethorphan (2 vỉ x 10 viên)', 'LSP0001', 'NSX0003', 'Hộp 2 Vỉ x 10 Viên', 'Việt Nam', 0, 1),
('SP0043', 'Viên uống Chống Lão Hóa Astaxanthin (30 viên)', 'LSP0001', 'NSX0003', 'Hộp 30 Viên', 'Việt Nam', 0, 1),
('SP0044', 'Thuốc Hỗ Trợ Điều Trị Bệnh Tiểu Đường Glibenclamide (2 vỉ x 10 viên)', 'LSP0001', 'NSX0003', 'Hộp 2 Vỉ x 10 Viên', 'Việt Nam', 1, 1),
('SP0045', 'Viên uống Tăng Miễn Dịch Beta Glucan (30 viên)', 'LSP0001', 'NSX0003', 'Hộp 30 Viên', 'Việt Nam', 0, 1),
('SP0046', 'Thuốc Điều Trị Tiểu Đường Metformin 850mg (10 vỉ x 10 viên)', 'LSP0001', 'NSX0003', 'Hộp 10 Vỉ x 10 Viên', 'Việt Nam', 1, 1),
('SP0047', 'Viên uống Bổ Khớp MSM 1000mg (60 viên)', 'LSP0001', 'NSX0003', 'Hộp 60 Viên', 'Việt Nam', 0, 1),
('SP0048', 'Viên uống Chống Viêm Bromelain 500mg (30 viên)', 'LSP0001', 'NSX0003', 'Hộp 30 Viên', 'Việt Nam', 0, 1),
('SP0049', 'Thuốc Điều Trị Loét Dạ Dày Pantoprazol 40mg (2 vỉ x 10 viên)', 'LSP0001', 'NSX0003', 'Hộp 2 Vỉ x 10 Viên', 'Việt Nam', 0, 1),
('SP0050', 'Viên uống Hỗ Trợ Giảm Cân Green Tea Extract (60 viên)', 'LSP0001', 'NSX0003', 'Hộp 60 Viên', 'Việt Nam', 0, 1);
insert into thanhphan values
('SP0001', 'Bìm Bìm'),
('SP0001', 'Rau đắng đất'),
('SP0001', 'Atisô'),
('SP0002', 'Methyldopa'),
('SP0003', 'Bisacodyl'),
('SP0004', 'Xạ can'),
('SP0004', 'Bột quế'),
('SP0004', 'Bột gừng'),
('SP0004', 'Cam thảo'),
('SP0005', 'Prednisolone'),
('SP0006', 'Acid folic'),
('SP0006', 'Sắt'),
('SP0007', 'Aluminium phosphate'),
('SP0008', 'Fexofenadine'),
('SP0009', 'Độc hoạt'),
('SP0009', 'Bột mã tiền chế'),
('SP0009', 'Bìm Bìm'),
('SP0009', 'Cẩu tích'),
('SP0009', 'Hy thiêm'),
('SP0009', 'Ba Kich'),
('SP0009', 'Bột thương truật'),
('SP0010', 'Novasol Curcumin'),
('SP0010', 'Cao khô actiso'),
('SP0010', 'Mật nhân'),
('SP0010', ' Sài Hồ'),
('SP0010', 'Bạch thược'),
('SP0010', 'Khúng khéng'),
('SP0010', 'Cao kế sữa'),
('SP0010', 'Cao hỗn hợp dược liệu '),
('SP0011', 'Bạch linh'),
('SP0011', 'Đảng sâm'),
('SP0011', 'Trần bì'),
('SP0011', 'Mộc hương bắc'),
('SP0011', 'Nhục đậu khấu'),
('SP0011', 'Mạch nha'),
('SP0011', 'Sơn tra'),
('SP0011', 'Sa nhân'),
('SP0011', 'Hoàng liên'),
('SP0011', 'Tá dược vừa đủ'),
('SP0011', 'Liên nhục'),
('SP0011', 'Hoài sơn'),
('SP0012', 'Long nhãn'),
('SP0012', 'Lá vông'),
('SP0012', 'Lá dâu'),
('SP0012', 'Hắc táo nhân'),
('SP0012', 'Bá tử nhân'),
('SP0012', 'Liên tâm'),
('SP0012', 'Liên nhục'),
('SP0012', 'Hoài sơn'),
('SP0013', 'Vitamin B12'),
('SP0014', 'Tá dược vừa đủ'),
('SP0014', 'Vitamin E'),
('SP0015', 'Omeprazol'),
('SP0016', 'Calcium glubionate'),
('SP0016', 'Calcium lactobionate'),
('SP0017', 'Ginkgo biloba'),
('SP0018', 'Hạ khô thảo'),
('SP0018', 'Đương quy'),
('SP0018', 'Thảo quyết minh'),
('SP0018', 'Hà thủ ô đỏ'),
('SP0018', 'Cúc hoa'),
('SP0018', 'Trạch tả'),
('SP0018', 'Hoài Sơn'),
('SP0018', 'Thục địa'),
('SP0020', 'Digoxin 0.25mg'),
('SP0020', 'Paracetamol'),
('SP0021', 'Elastin'),
('SP0021', 'Vitamin B1'),
('SP0021', 'Vitamin B12'),
('SP0021', 'Hyaluronic acid'),
('SP0021', 'Collagen type II'),
('SP0021', 'Chondroitin'),
('SP0021', 'Collagen'),
('SP0021', 'Glucosamine'),
('SP0021', 'Canxi từ vỏ sò'),
('SP0022', 'Acetylsalicylic acid'),
('SP0023', 'diazepam'),
('SP0024', 'Tá dược vừa đủ'),
('SP0024', 'Cao cúc nhím'),
('SP0025', 'Loratadine'),
('SP0026', 'Vitamin C'),
('SP0027', 'Orlistat'),
('SP0028', 'Simvastatin'),
('SP0029', 'Metformine'),
('SP0030', 'Tá dược đầy đủ'),
('SP0030', 'Vitamin E'),
('SP0031', 'Sáp ong vàng'),
('SP0031', 'Dầu lecithin đậu nành '), 
('SP0031', 'Coenzyme Q10'),
('SP0031', 'Tá dược vừa đủ'),
('SP0032', 'Omeprazol'),
('SP0033', 'Betaglucan'),
('SP0033', 'Hoàng kỳ'),
('SP0033', 'Echinacea'),
('SP0033', 'Kẽm Gluconat'),
('SP0033', 'Sắt fumarate'),
('SP0033', 'Calcium lactate'),
('SP0033', 'Magnesium gluconate'),
('SP0033', 'Vitamin B3'),
('SP0033', 'Vitamin B6'),
('SP0033', 'Vitamin B2'),
('SP0033', 'Vitamin B1'),
('SP0033', 'Vitamin B5'),
('SP0033', 'Vitamin B12'),
('SP0033', 'Vitamin B9'),
('SP0033', 'Vitamin C'),
('SP0034', 'Radix Dipsaci'),
('SP0034', 'Rhizoma Ligustici Chuanxiong'),
('SP0034', 'Fructus Amomi'),
('SP0034', 'Radix Achryanthis Bidentatae'),
('SP0034', 'Cortex Eucommiae'),
('SP0034', 'Fructus Alpiniae Oxyphyllae'),
('SP0034', 'Radix Polygalae-root'),
('SP0034', 'Angelica Root'),
('SP0034', 'American Ginseng'),
('SP0034', 'maca root extract'),
('SP0035', 'Conjugated Linoleic Acid'),
('SP0036', 'Chondroitin'),
('SP0037', 'Conjugated Linoleic Acid'),
('SP0038', 'Isotretinoin'),
('SP0039', 'Dầu hạt lanh'),
('SP0039', 'Omega 3'),
('SP0039', 'Hạt Tía Tô'),
('SP0039', 'Dầu nhuyễn thể'),
('SP0039', 'Vitamin E'),
('SP0039', 'Chiết xuất vi tảo lục'),
('SP0039', 'Nattokinase'),
('SP0040', 'Acid alpha lipoic'),
('SP0041', 'Adapalene'),
('SP0042', 'Dextromethorphan hydrobromid'),
('SP0043', 'Olive oil'),
('SP0043', 'Hematococcus algal pigment'),
('SP0043', 'gelatin'),
('SP0043', 'glycerin'),
('SP0043', 'vitamin E'),
('SP0044', 'Glibenclamid'),
('SP0045', 'Vỏ sò'),
('SP0045', 'Nấm Phellinus'),
('SP0045', 'Nấm Chaga'),
('SP0045', 'Nấm thông'),
('SP0045', 'Nấm linh chi'),
('SP0045', 'Đông trùng hạ thảo'),
('SP0045', 'Nấm lim xanh'),
('SP0045', 'Mật ong'),
('SP0045', 'Men bia tươi'),
('SP0046', 'Metformin'),
('SP0047', 'MSM'),
('SP0048', 'Bromelain'),
('SP0049', 'Pantoprazole'),
('SP0050', 'Chiết xuất trà xanh Caffeine'),
('SP0050', 'Cây Thường xanh Nam'),
('SP0050', 'Mỹ Bột tảo biển nâu'),
('SP0050', 'Chiết xuất củ gừng (5%)');
insert into dieutri values
('SP0001', 'Bổ gan'),
('SP0001', 'Suy giảm chức năng gan'),
('SP0001', 'Giảm triệu chứng của viêm gan'),
('SP0001', 'Xơ vữa động mạch'),
('SP0001', 'Mỡ trong máu cao'),
('SP0002', 'Điều trị tăng huyết áp'),
('SP0003', 'Điều trị táo bón'),
('SP0003', 'Đi ngoài khó'),
('SP0004', 'Viêm họng'),
('SP0004', 'Viêm thanh quản'),
('SP0004', 'Khàn tiếng'),
('SP0004', 'Tắt tiếng'),
('SP0005', 'Khàn tiếng'),
('SP0005', 'Viêm thanh quản'),
('SP0005', 'Viêm họng'),
('SP0006', 'Viêm loét đại tràng'),
('SP0006', 'Hen phế quản'),
('SP0006', 'Bệnh dị ứng'),
('SP0006', 'Lupus ban đỏ'),
('SP0006', 'Thiếu máu huyết tán'),
('SP0006', 'Viêm mạch'),
('SP0006', 'Hội chứng thận hư'),
('SP0007', 'Thiếu sắt'),
('SP0006', 'Thiếu máu'),
('SP0007', ' đau, bỏng rát do axit gây ra trong các bệnh lý viêm loét thực quản'),
('SP0007', ' Đau, bỏng rát do axit gây ra trong các bệnh lý viêm loét dạ dày '),
('SP0008', 'Nổi mề đai'),
('SP0008', 'Viêm mũi dị ứng'),
('SP0009', 'Viêm khớp'),
('SP0009', 'Đau khớp gối'),
('SP0009', 'Thoái hóa khớp'),
('SP0010', 'Tăng cường chức năng gan'),
('SP0010', 'Bổ gan'),
('SP0011', 'Viêm đại tràng cấp'),
('SP0011', 'Viêm đại tràng mãn tính'),
('SP0012', 'Mất ngủ lâu ngày'),
('SP0013', 'Thiếu máu mệt mỏi'),
('SP0014', 'Thiếu vitaminE'),
('SP0015', 'Trào ngược dạ dày'),
('SP0016', 'Hỗ trợ xương chắc khỏe'),
('SP0017', 'Tăng cường trí nhớ'),
('SP0018', 'Thoái hóa điểm vàng'),
('SP0019', 'Suy tim'),
('SP0020', 'Hạ sốt'),
('SP0021', 'Đau khớp'),
('SP0022', 'Giảm đau'),
('SP0023', 'Mất ngủ lo âu'),
('SP0024', 'Tăng cường sức đề kháng'),
('SP0025', 'Dị ứng'),
('SP0025', 'Nổi mề đai'),
('SP0026', 'Bổ sung vitamin'),
('SP0027', 'Hỗ trợ điều trị bệnh nhân béo phì'),
('SP0028', 'Mỡ máu'),
('SP0028', 'Cholesterol máu cao'),
('SP0029', 'Điều trị đái tháo đường không phụ thuộc insulin (túyp II)'),
('SP0030', 'Cải thiện sức khỏe da'),
('SP0030', 'Phục hồi da'),
('SP0031', 'Bệnh tim mạch'),
('SP0031', 'Cholesterol máu cao'),
('SP0032', 'Trào ngược dạ dày'),
('SP0032', 'Ợ chua'),
('SP0033', 'Tăng cường vitamin '),
('SP0034', 'Đau lưng'),
('SP0034', 'Yếu sinh lý'),
('SP0034', 'Mỏi gối'),
('SP0034', 'Đi tiểu nhiểu'),
('SP0035', 'Giảm cân'),
('SP0036', 'Giảm đau xương khớp'),
('SP0037', 'Giảm mỡ'),
('SP0038', 'Mụn trứng cá'),
('SP0039', 'Hỗ trợ tốt cho não bộ và thị lực'),
('SP0039', 'Hỗ trợ cải thiện sức khỏe tim mạch'),
('SP0040', 'Giảm cân'),
('SP0040', 'Điều trị đau dây thần kinh do tiểu đường'),
('SP0040', 'Chữa lành vết thương'),
('SP0040', 'Giảm lượng đường trong máu'),
('SP0040', 'Cải thiện sử đổi màu da do bệnh bạch biến'),
('SP0040', 'Giảm biến chứng của phẩu thuật ghép mạch vành (CABG)'),
('SP0041', 'Mụn trứng cá'),
('SP0041', 'Mụn nhân'),
('SP0041', 'Mụn li ti '),
('SP0041', 'Mụn mủ ở các vị trí mặt,ngực,lưng'),
('SP0042', 'Ho do họng và phế quản bị kích thích khi cảm lạnh thông thường hoặc khi hít phải chất kích thích '),
('SP0042', 'Ho không có đờm'),
('SP0042', 'Ho mạn tính'),
('SP0043', 'Lão hóa từ 35 đến 40 tuổi'),
('SP0044', 'Tiểu đường type 2'),
('SP0045', 'Suy nhược cơ thể'),
('SP0045', 'Suy dinh dưỡng'),
('SP0045', 'Suy giảm hệ miễn dịch'),
('SP0046', 'Tiểu đường type 2'),
('SP0047', 'Thoái hóa khớp'),
('SP0047', 'Viêm đau khớp'),
('SP0047', 'Loãng xương'),
('SP0047', 'Đau cột sống lưng'),
('SP0047', 'Thấp khớp'),
('SP0048', 'Viêm xoang'),
('SP0048', 'Đau dạ dày'),
('SP0048', 'Viêm phế quản cấp tính'),
('SP0048', 'Khó tiêu'),
('SP0048', 'Viêm phế quản mạn tính'),
('SP0048', 'Viêm phổi'),
('SP0049', 'Trào ngược dạ dày'),
('SP0050', 'Giảm cân'),
('SP0050', 'Béo phì');
insert into duocsi values
('ADMIN', 'Admin', NULL, NULL, 1),
('DS0001', 'Lý Văn Công', '0903001001', 'cong@gmail.com', 1),
('DS0002', 'Quách Hồng Linh', '0903001002', 'linh@gmail.com', 1),
('DS0003', 'Dương Thanh Luận', '0903001003', 'luan@gmail.com', 1),
('DS0004', 'Trần Tuấn Sang', '0903001004', 'sang@gmail.com', 1),
('DS0005', 'Nguyễn Hoàng Nhật', '0903001005', 'nhat@gmail.com', 1),
('DS0006', 'Bùi Thành Công', '0903001006', 'cong@gmail.com', 1),
('DS0007', 'Trương Xuân Cảnh', '0903001007', 'canh@gmail.com', 1),
('DS0008', 'Nguyễn Tấn Đạt', '0903001008', 'dat@gmail.com', 1),
('DS0009', 'Võ Thị Yến Thùy', '0903001009', 'thuy@gmail.com', 1),
('DS0010', 'Vũ Lê Đức Anh', '0903001010', 'anh@gmail.com', 1),
('DS0011', 'Nguyễn Minh Tuấn', '0903001011', 'tuan@gmail.com', 1),
('DS0012', 'Lê Thị Thu Hà', '0903001012', 'ha@gmail.com', 1),
('DS0013', 'Vũ Minh Quân', '0903001013', 'quan@gmail.com', 1),
('DS0014', 'Trần Thị Mai', '0903001014', 'mai@gmail.com', 1),
('DS0015', 'Phan Văn Lộc', '0903001015', 'loc@gmail.com', 1),
('DS0016', 'Bùi Thị Như', '0903001016', 'nhu@gmail.com', 1),
('DS0017', 'Nguyễn Văn Bình', '0903001017', 'binh@gmail.com', 1),
('DS0018', 'Trần Thị Lan', '0903001018', 'lan@gmail.com', 1),
('DS0019', 'Đỗ Văn Long', '0903001019', 'long@gmail.com', 1),
('DS0020', 'Nguyễn Thị Thanh', '0903001020', 'thanh@gmail.com', 1);
insert into taikhoan values
('ADMIN', 'Admin', '123456', 15),
('DS0001', 'DS0001', '123456', 2),
('DS0002', 'DS0002', '123456', 4),
('DS0003', 'DS0003', '123456', 4),
('DS0004', 'DS0004', '123456', 4),
('DS0005', 'DS0005', '123456', 4),
('DS0006', 'DS0006', '123456', 4),
('DS0007', 'DS0007', '123456', 4),
('DS0008', 'DS0008', '123456', 4),
('DS0009', 'DS0009', '123456', 4),
('DS0010', 'DS0010', '123456', 4),
('DS0011', 'DS0011', '123456', 4),
('DS0012', 'DS0012', '123456', 4),
('DS0013', 'DS0013', '123456', 8),
('DS0014', 'DS0014', '123456', 8),
('DS0015', 'DS0015', '123456', 8),
('DS0016', 'DS0016', '123456', 8),
('DS0017', 'DS0017', '123456', 8),
('DS0018', 'DS0018', '123456', 8),
('DS0019', 'DS0019', '123456', 8),
('DS0020', 'DS0020', '123456', 8);
insert into nhacungcap values
('NCC0001', 'Công ty Cổ phần Dược phẩm và Sinh học Y tế (MEBIPHAR JSC)', '18, đường số 13, KCN Tân Bình, quận Tân Phú, TP.HCM', '02838156187', 'contact@mebiphar.vn', 1),
('NCC0002', 'Công ty TNHH Dược phẩm FPT', '123, đường Phạm Ngũ Lão, quận 1, TP.HCM', '02838156188', 'info@fptpharma.vn', 1),
('NCC0003', 'Công ty TNHH Dược phẩm Minh Châu', '456, đường Cách Mạng Tháng 8, quận 3, TP.HCM', '02838156189', 'support@minhchaupharma.vn', 1),
('NCC0004', 'Công ty Cổ phần Dược phẩm Sao Mai', '789, đường Hai Bà Trưng, quận 1, TP.HCM', '02838156190', 'sales@saomaipharma.vn', 1),
('NCC0005', 'Công ty TNHH Dược phẩm Đông Á', '101, đường Võ Văn Tần, quận 3, TP.HCM', '02838156191', 'info@donga.vn', 1),
('NCC0006', 'Công ty Cổ phần Dược phẩm Bình Minh', '202, đường Lê Văn Sỹ, quận Phú Nhuận, TP.HCM', '02838156192', 'contact@binhminhpharma.vn', 1),
('NCC0007', 'Công ty TNHH Dược phẩm An Khang', '303, đường Nguyễn Trãi, quận 5, TP.HCM', '02838156193', 'support@ankhangpharma.vn', 1),
('NCC0008', 'Công ty Cổ phần Dược phẩm Thăng Long', '404, đường Nguyễn Văn Trỗi, quận Phú Nhuận, TP.HCM', '02838156194', 'info@thanglongpharma.vn', 1),
('NCC0009', 'Công ty TNHH Dược phẩm Hùng Vương', '505, đường Trần Hưng Đạo, quận 1, TP.HCM', '02838156195', 'contact@hungvuongpharma.vn', 1),
('NCC0010', 'Công ty TNHH Dược phẩm Thiên Bình', '606, đường Nguyễn Oanh, quận Gò Vấp, TP.HCM', '02838156196', 'sales@thienbinhpharma.vn', 1),
('NCC0011', 'Công ty TNHH Dược phẩm Đại Phát', '707, đường Phan Văn Trị, quận Bình Thạnh, TP.HCM', '02838156197', 'info@daiphatpharma.vn', 1),
('NCC0012', 'Công ty TNHH Dược phẩm Nam Việt', '808, đường Nguyễn Đình Chiểu, quận 3, TP.HCM', '02838156198', 'support@namvietpharma.vn', 1),
('NCC0013', 'Công ty Cổ phần Dược phẩm Hoàng Anh', '909, đường Điện Biên Phủ, quận Bình Thạnh, TP.HCM', '02838156199', 'contact@hoanganhpharma.vn', 1),
('NCC0014', 'Công ty TNHH Dược phẩm Trường An', '100, đường Hồng Bàng, quận 5, TP.HCM', '02838156200', 'info@truonganpharma.vn', 1),
('NCC0015', 'Công ty Cổ phần Dược phẩm Hòa Bình', '111, đường Nguyễn Văn Đậu, quận Bình Thạnh, TP.HCM', '02838156201', 'contact@hoabinhpharma.vn', 1),
('NCC0016', 'Công ty TNHH Dược phẩm Quốc Tế', '222, đường Lê Hồng Phong, quận 10, TP.HCM', '02838156202', 'info@quoctepharma.vn', 1),
('NCC0017', 'Công ty Cổ phần Dược phẩm Phúc An', '333, đường Trần Quang Khải, quận 1, TP.HCM', '02838156203', 'sales@phucanpharma.vn', 1),
('NCC0018', 'Công ty TNHH Dược phẩm Á Châu', '444, đường Nguyễn Văn Linh, quận 7, TP.HCM', '02838156204', 'info@achaupharma.vn', 1),
('NCC0019', 'Công ty Cổ phần Dược phẩm Long Vân', '555, đường Trường Chinh, quận Tân Bình, TP.HCM', '02838156205', 'support@longvanpharma.vn', 1),
('NCC0020', 'Công ty TNHH Dược phẩm Vĩnh Phát', '666, đường Xô Viết Nghệ Tĩnh, quận Bình Thạnh, TP.HCM', '02838156206', 'contact@vinhphatpharma.vn', 1);
insert into khachhang (hoten, ngaysinh, gioitinh, sodt, diem) values
('Lê Ngọc Bích Thảo', '2003-09-18', 'Nữ', '0903123456', 320),
('Nguyễn Trọng Nhân', '2002-01-18', 'Nam', '0903123123', 80),
('Nguyễn Hoàng Kiều Ngân', '2003-03-25', 'Nữ', '0903456456', 104),
('Trần Minh Quân', '1999-07-22', 'Nam', '0903999123', 150),
('Phạm Thị Thu Hà', '2001-08-15', 'Nữ', '0903123987', 200),
('Nguyễn Hoàng Phúc', '1998-10-10', 'Nam', '0903222333', 75),
('Lê Thị Mỹ Linh', '2003-05-19', 'Nữ', '0903124890', 140),
('Trần Văn Hải', '2000-06-05', 'Nam', '0903999456', 215),
('Đặng Thanh Tùng', '2002-12-14', 'Nam', '0903771234', 90),
('Nguyễn Thị Bích Ngọc', '2003-04-27', 'Nữ', '0903129789', 195),
('Lý Đức Thịnh', '1997-11-13', 'Nam', '0903128654', 85),
('Đoàn Minh Hòa', '2001-03-29', 'Nam', '0903124678', 230),
('Nguyễn Thu Thủy', '2002-09-12', 'Nữ', '0903554321', 170),
('Trần Thị Kim Yến', '2003-07-23', 'Nữ', '0903125879', 135),
('Nguyễn Phúc Duy', '1998-12-02', 'Nam', '0903999001', 160),
('Phan Văn Nam', '1996-04-18', 'Nam', '0903999333', 210),
('Nguyễn Hoàng Anh', '2000-11-25', 'Nam', '0903556543', 120),
('Đỗ Thị Thu Trang', '2002-06-16', 'Nữ', '0903999888', 145),
('Võ Thành Công', '2003-02-11', 'Nam', '0903772222', 130),
('Nguyễn Thị Ngọc Ánh', '2003-08-09', 'Nữ', '0903773333', 175),
('Phạm Hoàng Long', '1999-10-19', 'Nam', '0903445566', 155),
('Nguyễn Thị Phương Thảo', '2001-01-30', 'Nữ', '0903446677', 180),
('Trần Văn Tài', '1995-09-21', 'Nam', '0903223344', 140),
('Lê Thanh Hòa', '1997-05-17', 'Nam', '0903666777', 160),
('Phạm Thị Lan', '2000-07-08', 'Nữ', '0903888999', 185),
('Võ Minh Tuấn', '2003-04-12', 'Nam', '0903999555', 90),
('Nguyễn Quốc Hưng', '2002-03-18', 'Nam', '0903111222', 115),
('Lê Thị Kim Chi', '2003-11-28', 'Nữ', '0903111333', 170),
('Đặng Hoàng Phúc', '1996-08-22', 'Nam', '0903999222', 130),
('Nguyễn Văn Toàn', '2001-02-14', 'Nam', '0903773444', 195),
('Trần Thị Hồng Nhung', '2003-05-05', 'Nữ', '0903111456', 210),
('Võ Ngọc Hân', '2003-09-02', 'Nữ', '0903999666', 150),
('Nguyễn Thanh Bình', '2002-10-06', 'Nam', '0903111677', 120),
('Lê Văn Hải', '1998-01-01', 'Nam', '0903111788', 200),
('Trần Thị Mỹ Duyên', '2003-03-19', 'Nữ', '0903556677', 85),
('Đoàn Văn Đức', '2001-08-22', 'Nam', '0903999111', 95),
('Nguyễn Thị Hồng Ngọc', '2002-07-16', 'Nữ', '0903222332', 140),
('Phạm Minh Hoàng', '2000-09-09', 'Nam', '0903999444', 190),
('Trần Thị Lan Anh', '2002-05-15', 'Nữ', '0903111567', 170),
('Võ Thành Trung', '1999-12-18', 'Nam', '0903999777', 130),
('Nguyễn Thị Thu Hương', '2003-10-25', 'Nữ', '0903111789', 195),
('Phạm Hoàng Sơn', '2001-03-13', 'Nam', '0903556676', 160),
('Đỗ Văn Hậu', '2003-06-26', 'Nam', '0903111901', 110),
('Trần Thị Thùy Linh', '2002-08-05', 'Nữ', '0903446789', 155),
('Lê Thị Thanh Hương', '2001-11-09', 'Nữ', '0903667889', 165),
('Nguyễn Văn Phú', '2000-07-12', 'Nam', '0903223334', 115),
('Vũ Thị Thanh Mai', '1998-09-15', 'Nữ', '0903999223', 190),
('Nguyễn Thành Đạt', '1997-11-23', 'Nam', '0903667890', 145),
('Lê Văn Khoa', '1999-03-29', 'Nam', '0903111334', 105);
insert into anh values 
('SP0001','BoganicTraphaco.png'),
('SP0002', 'Methyldopa.png'),
('SP0003','ovalax.png'),
('SP0004','CaguTraphaco.png'),
('SP0005','solupred.png'),
('SP0006','fumafer.png'),
('SP0007','PhosphalugelSanofi.png'),
('SP0008','Telfast.png'),
('SP0009','vienkhoptambinh.png'),
('SP0010','bogantambinh.png'),
('SP0011','daitrangtambinh.png'),
('SP0012','duongtamanthan.png'),
('SP0013','vtaminB12.png'),
('SP0014','naturalvitamin.png'),
('SP0015','omegaprazol.png'),
('SP0016','caliumextra.png'),
('SP0017','GinkgoBiloba.png'),
('SP0018','Traphaco.png'),
('SP0019','dioxinequaly.png'),
('SP0020','ParacetamolStada.png'),
('SP0021','Glucosamine.png'),
('SP0022','AspirinStella.png'),
('SP0023','Mimosa.png'),
('SP0024','EchinaKingphar.png'),
('SP0025','Loratadin.png'),
('SP0026','NaturesBounty.png'),
('SP0027','Orlistat Stada.png'),
('SP0028','Simvastatin Stella.png'),
('SP0029','Natural Vitamin.png'),
('SP0030','Blackmores.png'),
('SP0031','Omeprazol TVP.png'),
('SP0032','Immune++.png'),
('SP0033','Ironmen Ocavill.png'),
('SP0034','Slim Body.png'),
('SP0035','Glucosamine And Chondroitin.png'),
('SP0036','Lipitas Jpanwell.png'),
('SP0037','Acnotin.png'),
('SP0038','OMEGA 3.png'),
('SP0039','Hộ Tạng Đường Hồng Bàng.png'),
('SP0040','Differin Galderma.png'),
('SP0041','Dextromethorphan.png'),
('SP0042','Dextromethorphan.png'),
('SP0043','Thymo-Glucan.png'),
('SP0044','Metformin.png'),
('SP0045','msm.png'),
('SP0046','Metformin.png'),
('SP0047','msm.png'),
('SP0048','Metformin.png'),
('SP0049','msm.png'),
('SP0050','msm.png');
insert into chuy  values 
('SP0001', ' Mẫn cảm với bất cứ thành phần nào của thuốc. Bị viêm tắc mật. Người tỳ vị hư hàn (đi phân lỏng nát hoặc tiêu chảy).'),
('SP0002', ' Quá mẫn với thuốc. Bệnh gan đang hoạt động như viêm gan cấp và xơ gan đang tiến triển. Rối loạn chức năng gan liên quan đến điều trị bằng Methyldopa trước đây. U tế bào ưa Crôm.Người đang dùng thuốc ức chế MAO.'),
('SP0003', ' Tắc ruột, đau bụng cấp tính bao gồm viêm ruột thừa, viêm ruột cấp tính. Tình trạng mất nước nặng. Mẫn cảm với bisacodyl hoặc bất cứ thành phần nào của thuốc.'),
('SP0004', ' Thận trọng khi dùng cho người tăng huyết áp, người đang bị phù, suy thận, viêm tắc mật, người đang bị sốt cao.'),
('SP0005', ' Phần lớn trường hợp nhiễm trùng. Một số bệnh do nhiễm siêu vi trùng đang tiến triển (viêm gan siêu vi, herpes, thủy đậu, zona). Tiêm vaccin sống.'),
('SP0006', ' Cơ thể thừa sắt. Tiền căn dị ứng với một trong các thành phần của thuốc.Bệnh mô nhiễm sắt (hematochromatosis). Thiếu máu tán huyết.'),
('SP0007', ' Không sử dụng cho người mẫn cảm với bất kỳ thành phần nào của sản phẩm.'),
('SP0008', ' Cần thận trọng và điều chỉnh liều thích hợp khi dùng thuốc cho người có chức năng thận suy giảm vì nồng độ thuốc trong huyết tương tăng do thời gian bán thải kéo dài. Cần thận trọng khi dùng thuốc cho người cao tuổi (trên 65 tuổi) thường có suy giảm sinh lý chức năng thận.'),
('SP0009', ' Không dùng cho phụ nữ có thai. Không sử dụng cho người mẫn cảm với bất cứ thành phần nào của sản phẩm. Không dùng quá liều khuyến cáo.'),
('SP0010', ' Người đang sử dụng thuốc, điều trị bệnh tham khảo ý kiến bác sĩ trước khi sử dụng. Không dùng cho phụ nữ có thai, không uống quá liều chỉ định. Không dùng cho người mẫn cảm, kiêng kỵ với bất kỳ thành phần nào của sản phẩm.'),
('SP0011', ' Không sử dụng cho người mẫn cảm với bất cứ thành phần nào của sản phẩm. Không dùng quá liều khuyến cáo. Sản phẩm này không phải là thuốc và không có tác dụng thay thế thuốc chữa bệnh.'),
('SP0012', ' Người bị mẫn cảm với thành phần của thuốc. Người lái xe, vận hành máy móc. Người bị trầm cảm.'),
('SP0013', ' Người mẫn cảm với bất cứ thành phần nào của thuốc. Người có tiền sử dị ứng với Cobalamin và chất liên quan. U ác tính (nguy cơ làm khối u phát triển do Vitamin B12 làm tăng trưởng các mô có khả năng sinh trưởng cao). Điều trị bệnh Leber’s hoặc thị lực suy giảm do hút thuốc lá.'),
('SP0014', ' Không sử dụng cho người mẫn cảm với bất cứ thành phần nào của sản phẩm. Không dùng quá liều khuyến cáo.'),
('SP0015', ' Omeprazol có thể che giấu triệu chứng và làm chậm chẩn đoán ở bệnh nhân bị tổn thương ác tính ở dạ dày.Với người cao tuổi, không cần thiết phải điều chỉnh liều.Ở người suy thận, sinh khả dụng của omeprazol thay đổi không đáng kể.'),
('SP0016', ' Không sử dụng cho người mẫn cảm với bất kỳ thành phần nào của sản phẩm.'),
('SP0017', ' Không sử dụng cho người mẫn cảm với bất cứ thành phần nào của sản phẩm. Không dùng quá liều khuyến cáo.'),
('SP0018', ' Dầu đậu nành: Không nên sử dụng nếu bị dị ứng với lạc hoặc đậu nành. Sorbitol: Nên liên hệ bác sĩ trước khi dùng thuốc nếu mắc chứng không dung nạp đường.'),
('SP0019', ' Đã biết quá mẫn với các thuốc digitalis, hoặc với bất kỳ thành phần nào của viên thuốc.Loạn nhịp thất trầm trọng (rung thất hoặc nhịp nhanh thất).Nhịp tim chậm trầm trọng.Phì đại tắc nghẽn cơ tim (HOCM).'),
('SP0020', ' Quá mẫn với paracetamol hoặc bất kỳ thành phần nào của thuốc'),
('SP0021', ' Không sử dụng cho người có mẫn cảm với bất kỳ thành phần nào của sản phẩm.Không dùng cho phụ nữ có thai, phụ nữ cho con bú, trẻ em, trẻ vị thành niên dưới 18 tuổi.'),
('SP0022', 'Do nguy cơ dị ứng chéo, không dùng aspirin cho người đã có triệu chứng hen, viêm mũi hoặc mày đay khi dùng aspirin hoặc những thuốc chống viêm không steroid khác trước đây.Người có tiền sử bệnh hen không được dùng aspirin, do nguy cơ gây hen thông qua tương tác với cân bằng prostaglandin và thromboxan'),
('SP0023', ' Không sử dụng cho người mẫn cảm với bất kỳ thành phần nào của sản phẩm.'),
('SP0024', ' Không dùng cho người mẫn cảm với bất kỳ thành phần nào của sản phẩm.Không dùng quá liều khuyến cáo.Sản phẩm này không phải là thuốc và không có tác dụng thay thế thuốc chữa bệnh.'),
('SP0025', ' Cần thận trọng với bệnh nhân suy gan.Khi dùng Loratadin, có nguy cơ khô miệng, đặc biệt ở người cao tuổi, và tăng nguy cơ sâu răng.Do đó, cần phải vệ sinh răng miệng sạch sẽ khi dùng Loratadin.'),
('SP0026', ' Thực phẩm này không phải là thuốc, không có tác dụng thay thế thuốc chữa bệnh.Nếu bạn đang mang thai, đang cho con bú, sử dụng bất kỳ loại thuốc nào, lên kế hoạch cho bất kỳ thủ tục y tế nào hoặc có bất kỳ tình trạng y tế nào, hãy tham khảo ý kiến bác sĩ trước khi sử dụng.'),
('SP0027', ' Mẫn cảm với orlistat hay bất cứ thành phần nào của thuốc.Hội chứng kém hấp thu mạn tính.Bệnh ứ mật.Phụ nữ cho con bú.'),
('SP0028', ' Quá mẫn với Simvastatin hay bất cứ thành phần nào của thuốc. Bệnh gan hoạt động hoặc transaminase huyết thanh tăng dai dẳng mà không giải thích được. Phụ nữ có thai và cho con bú. '),
('SP0029', ' Mẫn cảm với thuốc.Người bệnh có trạng thái dị hóa cấp tính, nhiễm khuẩn, chấn thương.Suy thận, bệnh lý cấp tính có nguy cơ gây thoái hóa chức năng thận.Suy tim sung huyết, trụy tim mạch, nhồi máu cơ tim cấp tính.'),
('SP0030', ' Không sử dụng cho người mẫn cảm với bất cứ thành phần nào của sản phẩm. Không dùng quá liều khuyến cáo.Sản phẩm này không phải là thuốc và không có tác dụng thay thế thuốc chữa bệnh.'),
('SP0031', ' Người đang ăn kiêng.Phụ nữ mang thai hoặc đang cho con bú.Người mẫn cảm với bất cứ thành phần nào của sản phẩm.'),
('SP0032', ' Mẫn cảm với bất kỳ thành phần nào của thuốc.Sử dụng đồng thời với neltlnavir.'),
('SP0033', ' Trẻ em và người lớn kém ăn, gầy yếu, trẻ biếng ăn, hay bị viêm đường hô hấp do sức đề kháng kém.Người cần bổ sung vitamin và khoáng chất do chế độ ăn không đủ.'),
('SP0034', ' Người dưới 18 tuổi.Người mẫn cảm với bất kỳ thành phần nào của sản phẩm.'),
('SP0035', ' Đọc kĩ hướng dẫn sử dụng trước khi dùng.'),
('SP0036', ' Đọc kĩ hướng dẫn sử dụng trước khi dùng.'),
('SP0037', ' Đọc kĩ hướng dẫn sử dụng trước khi dùng.'),
('SP0038', ' Suy gan.Lipid huyết tăng đáng kể.Thừa vitamin A.Quá mẫn với isotretinoin, dầu đậu nành hoặc với bất kỳ thành phần nào của thuốc.'),
('SP0039', ' Không sử dụng cho người mẫn cảm với bất cứ thành phần nào của sản phẩm. Không dùng quá liều khuyến cáo.'),
('SP0040', ' Không sử dụng cho người mẫn cảm với bất cứ thành phần nào của sản phẩm.Không dùng quá liều khuyến cáo.'),
('SP0041', ' Quá mẫn với bất kỳ thành phần nào của thuốc này.'),
('SP0042', ' Quá mẫn cảm với dextromethorphan và các thành phần khác của thuốc.Người bệnh đang điều trị các thuốc ức chế monoamin oxydase (IMAO) vì có thể gây những phản ứng nặng như sốt cao, chóng mặt, tăng huyết áp, chảy máu não, thậm chí tử vong.Trẻ em dưới 2 tuổi.'),
('SP0043', ' Không sử dụng cho người mẫn cảm với bất cứ thành phần nào của sản phẩm.'),
('SP0044', ' Quá mẫn với glibenclamid hoặc bất kỳ thành phần nào của thuốc.Quá mẫn với sulfonylure khác hoặc các thuốc liên quan.Đái tháo đường phụ thuộc insulin (typ 1), đái tháo đường thiếu niên hoặc không ổn định.Hôn mê do đái tháo đường.'),
('SP0045', ' Không sử dụng cho người mẫn cảm với bất kỳ thành phần nào của sản phẩm.Không dùng quá liều khuyến cáo.Sản phẩm này không phải là thuốc và không có tác dụng thay thế thuốc chữa bệnh.'),
('SP0046', ' Mẫn cảm với thuốc.Người bệnh có trạng thái dị hóa cấp tính, nhiễm khuẩn, chấn thương.Suy thận, bệnh lý cấp tính có nguy cơ gây thoái hóa chức năng thận.'),
('SP0047', ' Không sử dụng cho người mẫn cảm với bất kỳ thành phần nào của sản phẩm.'),
('SP0048', ' Sử dụng đồng thời với thuốc chống đông máu hoặc thuốc chống kết tập tiểu cầu.Người suy gan, suy thận.Phụ nữ có thai và cho con bú.'),
('SP0049', ' Khi dừng thuốc đột ngột, có thể gây hội chứng tăng tiết acid trở lại (rebound). Trước khi dùng pantoprazol, phải loại trừ khả năng ung thư dạ dày vì thuốc có thể che lấp triệu chứng hoặc làm chậm chẩn đoán ung thư.Thận trọng ở người suy thận, người cao tuổi.'),
('SP0050', ' Không sử dụng cho người mẫn cảm với bất kỳ thành phần nào của sản phẩm.');
insert into anhavt values 
('DS0001','avt1.png'),
('DS0002','avt2.png'),
('DS0003','avt3.png'),
('DS0004','avt4.png'),
('DS0005','avt5.png'),
('DS0006','avt6.png'),
('DS0007','avt7.png'),
('DS0008','avt8.png'),
('DS0009','avt9.png'),
('DS0010','avt10.png'),
('DS0011','avt11.png'),
('DS0012','avt12.png'),
('DS0013','avt13.png'),
('DS0014','avt14.png'),
('DS0015','avt15.png'),
('DS0016','avt16.png'),
('DS0017','avt17.png'),
('DS0018','avt18.png'),
('DS0019','avt19.png'),
('DS0020','avt20.png'),
('ADMIN','admin.png');
