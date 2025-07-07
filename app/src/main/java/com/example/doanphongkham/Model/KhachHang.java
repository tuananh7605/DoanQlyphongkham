package com.example.doanphongkham.Model;

public class KhachHang {
    private int id;
    private String tenKH;
    private String sdt;
    private String gioiTinh;
    private String ngaySinh;
    private String diaChi;
    private String tienSuBenh;

    public KhachHang(int id, String tenKH, String sdt, String gioiTinh, String ngaySinh, String diaChi, String tienSuBenh) {
        this.id = id;
        this.tenKH = tenKH;
        this.sdt = sdt;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.tienSuBenh = tienSuBenh;
    }

    // Getter
    public int getId() { return id; }
    public String getTenKH() { return tenKH; }
    public String getSdt() { return sdt; }
    public String getGioiTinh() { return gioiTinh; }
    public String getNgaySinh() { return ngaySinh; }
    public String getDiaChi() { return diaChi; }
    public String getTienSuBenh() { return tienSuBenh; }
}

