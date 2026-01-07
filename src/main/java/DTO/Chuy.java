package DTO;

public class Chuy {
    private String machuy;  // Mã chuy
    private String tenchuy; // Tên chuy

    // Constructor có tham số
    public Chuy(String machuy, String tenchuy) {
        this.machuy = machuy;
        this.tenchuy = tenchuy;
    }

    // Constructor không tham số
    public Chuy() {
        super();
    }

    // Getter và setter cho thuộc tính machuy
    public String getMachuy() {
        return machuy;
    }

    public void setMachuy(String machuy) {
        this.machuy = machuy;
    }

    // Getter và setter cho thuộc tính tenchuy
    public String getTenchuy() {
        return tenchuy;
    }

    public void setTenchuy(String tenchuy) {
        this.tenchuy = tenchuy;
    }
}
