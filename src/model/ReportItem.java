package model;

public class ReportItem {
    private String month;
    private String type;
    private Integer quantity;

    public void setMonth(String month) {
        this.month = month;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getMonth() {
        return month;
    }

    public String getType() {
        return type;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
