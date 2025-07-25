package com.example.doanphongkham.Model;

public class DaKhamXong {
    private int id;
    private String tenBenhNhan;
    private String ngaySinh;
    private String sdt;
    private String phongKham;
    private String tienSuBenh;
    private String ngayKham;
    private double tongTien;

    public DaKhamXong(int id, String tenBenhNhan, String ngaySinh, String sdt, String phongKham, String tienSuBenh, String ngayKham, double tongTien) {
        this.id = id;
        this.tenBenhNhan = tenBenhNhan;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.phongKham = phongKham;
        this.tienSuBenh = tienSuBenh;
        this.ngayKham = ngayKham;
        this.tongTien = tongTien;
    }

    // Getters
    public int getId() { return id; }
    public String getTenBenhNhan() { return tenBenhNhan; }
    public String getNgaySinh() { return ngaySinh; }
    public String getSdt() { return sdt; }
    public String getPhongKham() { return phongKham; }
    public String getTienSuBenh() { return tienSuBenh; }
    public String getNgayKham() { return ngayKham; }
    public double getTongTien() { return tongTien; }
}

