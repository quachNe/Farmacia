package DTO;

public class AnhAvt {
    private String mathuoc;  // Mã thuốc
    private String avt;      // Đường dẫn hoặc tên ảnh đại diện (avatar)

    // Constructor có tham số
    public AnhAvt(String mathuoc, String avt) {
        this.mathuoc = mathuoc;
        this.avt = avt;
    }

    // Getter và setter cho thuộc tính mathuoc
    public String getMathuoc() {
        return mathuoc;
    }

    public void setMathuoc(String mathuoc) {
        this.mathuoc = mathuoc;
    }

    // Getter và setter cho thuộc tính avt
    public String getAvt() {
        return avt;
    }

    public void setAvt(String avt) {
        this.avt = avt;
    }
}
