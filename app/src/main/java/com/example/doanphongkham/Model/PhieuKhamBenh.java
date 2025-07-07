package com.example.doanphongkham.Model;

public class PhieuKhamBenh {
    private int id;
    private String tenBenhNhan, ngayKham, gioKham, tienSuBenh, phongKham;

    public PhieuKhamBenh(int id, String tenBenhNhan, String ngayKham, String gioKham, String tienSuBenh, String phongKham) {
        this.id = id;
        this.tenBenhNhan = tenBenhNhan;
        this.ngayKham = ngayKham;
        this.gioKham = gioKham;
        this.tienSuBenh = tienSuBenh;
        this.phongKham = phongKham;
    }

    public int getId() { return id; }
    public String getTenBenhNhan() { return tenBenhNhan; }
    public String getNgayKham() { return ngayKham; }
    public String getGioKham() { return gioKham; }
    public String getTienSuBenh() { return tienSuBenh; }
    public String getPhongKham() { return phongKham; }
}

