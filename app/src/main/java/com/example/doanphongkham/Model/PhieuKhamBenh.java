package com.example.doanphongkham.Model;

public class PhieuKhamBenh {
    private int id;
    private String tenBenhNhan;
    private String sdt;
    private String ngaySinh;
    private String ngayKham;
    private String gioKham;
    private String tienSuBenh;
    private String phongKham;

    public PhieuKhamBenh(int id, String tenBenhNhan, String sdt, String ngaySinh, String ngayKham, String gioKham, String tienSuBenh, String phongKham) {
        this.id = id;
        this.tenBenhNhan = tenBenhNhan;
        this.sdt = sdt;
        this.ngaySinh = ngaySinh;
        this.ngayKham = ngayKham;
        this.gioKham = gioKham;
        this.tienSuBenh = tienSuBenh;
        this.phongKham = phongKham;
    }

    public int getId() {
        return id;
    }

    public String getTenBenhNhan() {
        return tenBenhNhan;
    }

    public String getSdt() {
        return sdt;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public String getNgayKham() {
        return ngayKham;
    }

    public String getGioKham() {
        return gioKham;
    }

    public String getTienSuBenh() {
        return tienSuBenh;
    }

    public String getPhongKham() {
        return phongKham;
    }
}
