package com.example.smartfarmtool;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "CurrentCrops")
public class CropDetail {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private int area = 0;

    private double lattitude;

    private double longitude;

    private String date;

    private int irrigationCost = 0;

    private int laborCost = 0;

    private int chemicalCost = 0;

    public int getChemicalCost() {
        return chemicalCost;
    }

    public void setChemicalCost(int chemicalCost) {
        this.chemicalCost = chemicalCost;
    }

    private int totalCost = 0;



    public CropDetail(String name, int area, double lattitude, double longitude, String date){
        this.name = name;
        this.area = area;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.date = date;
    }
    @Ignore
    public CropDetail(int id, String name, String date, int irrigationCost, int laborCost, int chemicalCost) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.irrigationCost = irrigationCost;
        this.laborCost = laborCost;
        this.chemicalCost = chemicalCost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(float lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIrrigationCost() {
        return irrigationCost;
    }

    public void setIrrigationCost(int irrigationCost) {
        this.irrigationCost = irrigationCost;
    }

    public int getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(int laborCost) {
        this.laborCost = laborCost;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
}
