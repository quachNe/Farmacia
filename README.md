Source code của ứng dụng được phát triển bằng ngôn ngữ Java theo mô hình 3 lớp (Three-tier Architecture), bao gồm DTO – BUS – DAO, kết hợp với tầng giao diện (GUI) xây dựng bằng Java Swing cho ứng dụng desktop.

DTO (Data Transfer Object):
Chứa các lớp đối tượng dùng để mô tả dữ liệu, ánh xạ trực tiếp với các bảng trong cơ sở dữ liệu. DTO giúp đóng gói dữ liệu và truyền dữ liệu giữa các tầng trong hệ thống.

DAO (Data Access Object):
Đảm nhiệm việc truy cập và thao tác với cơ sở dữ liệu MySQL thông qua JDBC, thực hiện các chức năng CRUD (thêm, sửa, xóa, truy vấn dữ liệu).

BUS (Business Layer):
Thực hiện xử lý các nghiệp vụ chính của hệ thống, kiểm tra và ràng buộc dữ liệu, đồng thời đóng vai trò trung gian giữa tầng giao diện và tầng DAO.

GUI (Graphical User Interface):
Được xây dựng bằng Java Swing, cung cấp các form và màn hình chức năng để người dùng tương tác trực tiếp với hệ thống trên nền tảng desktop.