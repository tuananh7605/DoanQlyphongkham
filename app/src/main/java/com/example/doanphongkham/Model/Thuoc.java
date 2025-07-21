package com.example.doanphongkham.Model;

public class Thuoc {
    private int id;
    private String tenThuoc;
    private String moTa;
    private double giaTien;

    public Thuoc(int id, String tenThuoc, String moTa, double giaTien) {
        this.id = id;
        this.tenThuoc = tenThuoc;
        this.moTa = moTa;
        this.giaTien = giaTien;
    }

    public Thuoc(int id, String tenThuoc, String moTa) {
        this.id = id;
        this.tenThuoc = tenThuoc;
        this.moTa = moTa;
    }

    public int getId() {
        return id;
    }

    public String getTenThuoc() {
        return tenThuoc;
    }

    public String getMoTa() {
        return moTa;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }

    @Override
    public String toString() {
        return tenThuoc;
    }
}
