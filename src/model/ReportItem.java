package model;

import java.time.ZonedDateTime;

public class ReportItem {
    private String month;
    private String type;
    private Integer quantity;

    private String userName;
    private String customerName;
    private ZonedDateTime dateTime;

    public ReportItem() {
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
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

    public String getUserName() {
        return userName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }
}