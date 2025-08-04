package com.example.doanphongkham.Model;

public class TaiKHoan {
    private String maTk;
    private String tenTk;
    private String matKhau;
    private String loaiTK;
    private String TinhTrang;
    private String mail;
    public TaiKHoan(String maTk, String tenTk, String matKhau, String loaiTK, String tinhTrang, String mail) {
        this.maTk = maTk;
        this.tenTk = tenTk;
        this.matKhau = matKhau;
        this.loaiTK = loaiTK;
        TinhTrang = tinhTrang;
        this.mail = mail;
    }
    public String getTenTk() {
        return tenTk;
    }

    public void setTenTk(String tenTk) {
        this.tenTk = tenTk;
    }

    public String getMaTk() {return maTk;}

    public void setMaTk(String maTk) {this.maTk = maTk;}

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getLoaiTK() {
        return loaiTK;
    }

    public void setLoaiTK(String loaiTK) {
        this.loaiTK = loaiTK;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        TinhTrang = tinhTrang;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


}
