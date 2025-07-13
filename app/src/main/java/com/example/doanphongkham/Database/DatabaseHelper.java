package com.example.doanphongkham.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.doanphongkham.Model.KhachHang;
import com.example.doanphongkham.Model.PhieuKhamBenh;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PhongkhamDB";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_PHIEU_KHAM = "CREATE TABLE PhieuKhamBenh (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenBenhNhan TEXT NOT NULL, " +
                "NgayKham TEXT NOT NULL, " +
                "GioKham TEXT NOT NULL, " +
                "TienSuBenh TEXT, " +
                "PhongKham TEXT" +
                ");";

        String CREATE_TABLE_KHACH_HANG = "CREATE TABLE KhachHang (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenKH TEXT NOT NULL, " +
                "SDT TEXT, " +
                "GioiTinh TEXT, " +
                "NgaySinh DATE, " +
                "DiaChi TEXT, " +
                "TienSuBenh TEXT" +
                ");";
        db.execSQL(CREATE_TABLE_PHIEU_KHAM);
        db.execSQL(CREATE_TABLE_KHACH_HANG);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PhieuKhamBenh");
        db.execSQL("DROP TABLE IF EXISTS KhachHang");
        onCreate(db);
    }

    //LICH KHAM
    //THEM LICH KHAM
    public boolean insertLichKham(String tenBN, String ngay, String gio, String tienSu, String phong) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenBenhNhan", tenBN);
        values.put("NgayKham", ngay);
        values.put("GioKham", gio);
        values.put("TienSuBenh", tienSu);
        values.put("PhongKham", phong);

        long result = db.insert("PhieuKhamBenh", null, values);
        db.close();
        return result != -1; // trả về true nếu thêm thành công
    }
    //CARDVIEW
    public List<PhieuKhamBenh> getAllLichKham() {
        List<PhieuKhamBenh> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PhieuKhamBenh", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String ten = cursor.getString(1);
                String ngay = cursor.getString(2);
                String gio = cursor.getString(3);
                String tiensu = cursor.getString(4);
                String phong = cursor.getString(5);

                list.add(new PhieuKhamBenh(id, ten, ngay, gio, tiensu, phong));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    //XOA LICH KHAM
    public boolean deleteLichKham(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("PhieuKhamBenh", "id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }
    //SUA LICH KHAM
    public boolean updateLichKham(int id, String tenBenhNhan, String ngayKham, String gioKham, String tienSuBenh, String phongKham) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenBenhNhan", tenBenhNhan);
        values.put("NgayKham", ngayKham);
        values.put("GioKham", gioKham);
        values.put("TienSuBenh", tienSuBenh);
        values.put("PhongKham", phongKham);

        int result = db.update("PhieuKhamBenh", values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }






    //KHACH HANG
    //THEM KHACH HANG
    public boolean insertKhachHang(String tenKH, String sdt, String gioiTinh, String ngaySinh, String diaChi, String tienSuBenh) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenKH", tenKH);
        values.put("SDT", sdt);
        values.put("GioiTinh", gioiTinh);
        values.put("NgaySinh", ngaySinh);
        values.put("DiaChi", diaChi);
        values.put("TienSuBenh", tienSuBenh);

        long result = db.insert("KhachHang", null, values);
        db.close();
        return result != -1;
    }
    //cardview
    public List<KhachHang> getAllKhachHang() {
        List<KhachHang> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM KhachHang", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String ten = cursor.getString(1);
                String sdt = cursor.getString(2);
                String gioiTinh = cursor.getString(3);
                String ngaySinh = cursor.getString(4);
                String diaChi = cursor.getString(5);
                String tienSu = cursor.getString(6);
                list.add(new KhachHang(id, ten, sdt, gioiTinh, ngaySinh, diaChi, tienSu));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }
    //XOA KHACH HANG
    public boolean deleteKhachHang(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("KhachHang", "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }
    //SUA KHACH HANG
    public boolean updateKhachHang(int id, String ten, String sdt, String gioiTinh, String ngaySinh, String diaChi, String tienSuBenh) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenKH", ten);
        values.put("SDT", sdt);
        values.put("GioiTinh", gioiTinh);
        values.put("NgaySinh", ngaySinh);
        values.put("DiaChi", diaChi);
        values.put("TienSuBenh", tienSuBenh);

        int result = db.update("KhachHang", values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }



}
