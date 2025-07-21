package com.example.doanphongkham.Model;

public class ChiTietKhamBenh {
    private int id;
    private int phieuKhamId;
    private int thuocId;
    private int soLuong;

    public ChiTietKhamBenh(int id, int phieuKhamId, int thuocId, int soLuong) {
        this.id = id;
        this.phieuKhamId = phieuKhamId;
        this.thuocId = thuocId;
        this.soLuong = soLuong;
    }

    public int getId() { return id; }
    public int getPhieuKhamId() { return phieuKhamId; }
    public int getThuocId() { return thuocId; }
    public int getSoLuong() { return soLuong; }
}

