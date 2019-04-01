package com.example.smartfarmtool;

public class Buy {
    private String cname;
    private String date;
    private String price;
    private String quantity;

    public Buy() {
    }

    public Buy(String cname, String date, String price, String quantity) {
        this.cname = cname;
        this.date = date;
        this.price = price;
        this.quantity = quantity;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}

