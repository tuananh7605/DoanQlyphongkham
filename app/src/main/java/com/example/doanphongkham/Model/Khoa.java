package com.example.doanphongkham.Model;

public class Khoa {
    private int id;
    private String tenKhoa;
    private String moTa;
    private double giaTien;

    // Constructors
    public Khoa() {}

    public Khoa(int id, String tenKhoa, String moTa, double giaTien) {
        this.id = id;
        this.tenKhoa = tenKhoa;
        this.moTa = moTa;
        this.giaTien = giaTien;
    }

    public Khoa(String tenKhoa, String moTa, double giaTien) {
        this.tenKhoa = tenKhoa;
        this.moTa = moTa;
        this.giaTien = giaTien;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenKhoa() {
        return tenKhoa;
    }

    public void setTenKhoa(String tenKhoa) {
        this.tenKhoa = tenKhoa;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }

    @Override
    public String toString() {
        return tenKhoa + " (Giá: " + giaTien + ")";
    }
}
