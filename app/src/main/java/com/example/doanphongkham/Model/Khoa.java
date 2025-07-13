package com.example.doanphongkham.Model;

public class Khoa {
    private String maKhoa;
    private String tenKhoa;
    private String maBS;
    private String moTa;

    public Khoa(String maKhoa, String tenKhoa, String maBS, String moTa) {
        this.maKhoa = maKhoa;
        this.tenKhoa = tenKhoa;
        this.maBS = maBS;
        this.moTa = moTa;
    }

    // Getter methods
    public String getMaKhoa() {
        return maKhoa;
    }

    public String getTenKhoa() {
        return tenKhoa;
    }

    public String getMaBS() {
        return maBS;
    }

    public String getMoTa() {
        return moTa;
    }
}
