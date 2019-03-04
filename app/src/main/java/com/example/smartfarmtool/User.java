package com.example.smartfarmtool;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class User {

    public User(String cropname, String quantity, String rate) {
        this.cropname = cropname;
        this.quantity = quantity;
        this.rate = rate;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "cropname")
    private String cropname;
    @ColumnInfo(name = "quantity")
    private String quantity;
    @ColumnInfo(name = "rate")
    private String rate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCropname() {
        return cropname;
    }

    public void setCropname(String cropname) {
        this.cropname = cropname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
