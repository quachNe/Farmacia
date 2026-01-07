package DTO;

public class Anh {
    private String mathuoc;  // Mã thuốc
    private String anh;      // Đường dẫn hoặc tên ảnh

    // Constructor có tham số
    public Anh(String mathuoc, String anh) {
        this.mathuoc = mathuoc;
        this.anh = anh;
    }

   
    // Getter và setter cho thuộc tính mathuoc
    public String getMathuoc() {
        return mathuoc;
    }

    public void setMathuoc(String mathuoc) {
        this.mathuoc = mathuoc;
    }

    // Getter và setter cho thuộc tính anh
    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}
