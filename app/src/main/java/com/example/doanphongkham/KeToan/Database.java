package com.example.doanphongkham.KeToan;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "PhongKhamDB";
    private static final int DB_VERSION = 3;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createHoaDonTable = "CREATE TABLE hoadon (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ten TEXT, " +
                "ngaySinh TEXT, " +
                "sdt TEXT, " +
                "khoa TEXT, " +
                "tinhTrang TEXT, " +
                "ngayTao TEXT, " +
                "tongTien REAL)";
        db.execSQL(createHoaDonTable);

        String createDoanhThuTable = "CREATE TABLE doanhthu (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date TEXT, " +
                "amount REAL)";
        db.execSQL(createDoanhThuTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS hoadon");
        db.execSQL("DROP TABLE IF EXISTS doanhthu");
        onCreate(db);
    }

    // ✅ Thêm hóa đơn và tự động ghi vào doanh thu
    public void insertHoaDon(String ten, String ngaySinh, String sdt, String khoa, String tinhTrang, String ngayTao, double tongTien) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ten", ten);
        values.put("ngaySinh", ngaySinh);
        values.put("sdt", sdt);
        values.put("khoa", khoa);
        values.put("tinhTrang", tinhTrang);
        values.put("ngayTao", ngayTao);
        values.put("tongTien", tongTien);
        db.insert("hoadon", null, values);

        // ✅ Tự động cập nhật doanh thu theo ngày tạo hóa đơn
        insertDoanhThu(ngayTao, tongTien);

        db.close();
    }

    public List<HoaDon> getAllHoaDon() {
        List<HoaDon> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM hoadon", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String ten = cursor.getString(1);
                String ngaySinh = cursor.getString(2);
                String sdt = cursor.getString(3);
                String khoa = cursor.getString(4);
                String tinhTrang = cursor.getString(5);
                String ngayTao = cursor.getString(6);
                double tongTien = cursor.getDouble(7);
                list.add(new HoaDon(id, ten, ngaySinh, sdt, khoa, tinhTrang, ngayTao, tongTien));
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return list;
    }

    public void deleteHoaDon(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("hoadon", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // ✅ Cập nhật doanh thu nếu đã có ngày, ngược lại thì thêm mới
    public void insertDoanhThu(String date, double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, amount FROM doanhthu WHERE date = ?", new String[]{date});

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            double currentAmount = cursor.getDouble(1);
            ContentValues values = new ContentValues();
            values.put("amount", currentAmount + amount);
            db.update("doanhthu", values, "id = ?", new String[]{String.valueOf(id)});
        } else {
            ContentValues values = new ContentValues();
            values.put("date", date);
            values.put("amount", amount);
            db.insert("doanhthu", null, values);
        }

        cursor.close();
        db.close();
    }

    public List<RevenueItem> getAllDoanhThu() {
        List<RevenueItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM doanhthu", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String date = cursor.getString(1);
                double amount = cursor.getDouble(2);
                list.add(new RevenueItem(id, date, amount));
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return list;
    }

    // ✅ Hàm mới: Lấy danh sách hóa đơn theo ngày
    public List<HoaDon> getHoaDonTheoNgay(String ngayTao) {
        List<HoaDon> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM hoadon WHERE ngayTao = ?", new String[]{ngayTao});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String ten = cursor.getString(1);
                String ngaySinh = cursor.getString(2);
                String sdt = cursor.getString(3);
                String khoa = cursor.getString(4);
                String tinhTrang = cursor.getString(5);
                String ngay = cursor.getString(6);
                double tongTien = cursor.getDouble(7);
                list.add(new HoaDon(id, ten, ngaySinh, sdt, khoa, tinhTrang, ngay, tongTien));
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return list;
    }
}

