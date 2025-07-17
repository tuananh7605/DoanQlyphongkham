package com.example.doanphongkham.KeToan;
public class HoaDon {
    private int id;
    private String tenBenhNhan;
    private String ngaySinh;
    private String sdt;
    private String khoaKham;
    private String tinhTrang;
    private String ngayTao;
    private double tongTien;

    public HoaDon(int id, String tenBenhNhan, String ngaySinh, String sdt, String khoaKham, String tinhTrang, String ngayTao, double tongTien) {
        this.id = id;
        this.tenBenhNhan = tenBenhNhan;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.khoaKham = khoaKham;
        this.tinhTrang = tinhTrang;
        this.ngayTao = ngayTao;
        this.tongTien = tongTien;
    }

    // Getters
    public int getId() { return id; }
    public String getTenBenhNhan() { return tenBenhNhan; }
    public String getNgaySinh() { return ngaySinh; }
    public String getSdt() { return sdt; }
    public String getKhoaKham() { return khoaKham; }
    public String getTinhTrang() { return tinhTrang; }
    public String getNgayTao() { return ngayTao; }
    public double getTongTien() { return tongTien; }
}

