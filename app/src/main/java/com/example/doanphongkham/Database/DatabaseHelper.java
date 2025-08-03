package com.example.doanphongkham.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.doanphongkham.KeToan.RevenueItem;
import com.example.doanphongkham.Model.DaKhamXong;
import com.example.doanphongkham.Model.KhachHang;
import com.example.doanphongkham.Model.Khoa;
import com.example.doanphongkham.Model.PhieuKhamBenh;
import com.example.doanphongkham.Model.Thuoc;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PhongkhamDB";
    private static final int DATABASE_VERSION =34;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //bacsi
        String CREATE_TABLE_PHIEU_KHAM = "CREATE TABLE PhieuKhamBenh (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenBenhNhan TEXT NOT NULL, " +
                "SDT TEXT, " +
                "NgaySinh DATE, " +
                "NgayKham TEXT NOT NULL, " +
                "GioKham TEXT NOT NULL, " +
                "TienSuBenh TEXT, " +
                "PhongKham TEXT, " +
                "TrangThai INTEGER DEFAULT 0" +
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

        // Tạo bảng Thuoc
        String CREATE_TABLE_THUOC = "CREATE TABLE IF NOT EXISTS Thuoc (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenThuoc TEXT NOT NULL, " +
                "MoTa TEXT, " +
                "giaTien REAL" +
                ");";

        String CREATE_TABLE_DA_KHAM_XONG = "CREATE TABLE DaKhamXong (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenBenhNhan TEXT NOT NULL, " +
                "SDT TEXT, " +
                "NgaySinh DATE, " +
                "NgayKham TEXT NOT NULL, " +
                "TienSuBenh TEXT, " +
                "PhongKham TEXT, " +
                "TongTien REAL DEFAULT 0" +
                ");";
        String CREATE_TABLE_DOANH_THU = "CREATE TABLE IF NOT EXISTS DoanhThu (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date TEXT, " +
                "amount REAL)";
        db.execSQL(CREATE_TABLE_DOANH_THU);





        //admin
        String CREATE_TABLE_Tai_Khoan = "CREATE TABLE TaiKhoan (" +
                "maTk  TEXT PRIMARY KEY, " +
                "tenTk TEXT NOT NULL, " +
                "mk TEXT, " +
                "loai_TK TEXT, " +
                "mail TEXT, " +
                "tinh_trang TEXT" +
                ");";
        String CREATE_TABLE_BAC_SI = "CREATE TABLE BacSi (" +
                "maBS  TEXT PRIMARY KEY, " +
                "tenBS  TEXT NOT NULL, " +
                "gioitinh TEXT, " +
                "sdt TEXT, " +
                "NgaySinh DATE, " +
                "diachi TEXT," +
                "email TEXT UNIQUE" +
                ");";

        String CREATE_TABLE_KE_TOAN = "CREATE TABLE KeToan (" +
                "maKT  TEXT PRIMARY KEY, " +
                "tenKT  TEXT NOT NULL, " +
                "gioitinh TEXT, " +
                "sdt TEXT, " +
                "NgaySinh DATE, " +
                "diachi TEXT," +
                "email TEXT UNIQUE" +
                ");";

        String CREATE_TABLE_NHAN_VIEN = "CREATE TABLE NhanVien (" +
                "maNV TEXT PRIMARY KEY, " +
                "tenNV TEXT NOT NULL, " +
                "gioiTinh TEXT, " +
                "sdt TEXT, " +
                "NgaySinh DATE, " +
                "diachi TEXT, " +
                "email TEXT UNIQUE" +
                ");";
        String CREATE_TABLE_KHOA = "CREATE TABLE Khoa (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenKhoa TEXT NOT NULL, " +
                "giatien REAL, " +
                "moTa TEXT " +
                ");";
        db.execSQL(CREATE_TABLE_KE_TOAN);
        db.execSQL(CREATE_TABLE_PHIEU_KHAM);
        db.execSQL(CREATE_TABLE_KHACH_HANG);
        db.execSQL(CREATE_TABLE_THUOC);
        db.execSQL(CREATE_TABLE_DA_KHAM_XONG);
        db.execSQL(CREATE_TABLE_Tai_Khoan);
        db.execSQL(CREATE_TABLE_BAC_SI);
        db.execSQL(CREATE_TABLE_NHAN_VIEN);
        db.execSQL(CREATE_TABLE_KHOA);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PhieuKhamBenh");
        db.execSQL("DROP TABLE IF EXISTS KhachHang");
        db.execSQL("DROP TABLE IF EXISTS Thuoc");
        db.execSQL("DROP TABLE IF EXISTS DaKhamXong");
        db.execSQL("DROP TABLE IF EXISTS TaiKhoan");
        db.execSQL("DROP TABLE IF EXISTS BacSi");
        db.execSQL("DROP TABLE IF EXISTS NhanVien");
        db.execSQL("DROP TABLE IF EXISTS Khoa");
db.execSQL("DROP TABLE IF EXISTS KeToan");
        onCreate(db);
    }
    //bacsi
    //LICH KHAM
    //THEM LICH KHAM
    public boolean insertLichKham(String tenBN, String sdt, String ngaySinh, String ngay, String gio, String tienSu, String phong) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenBenhNhan", tenBN);
        values.put("SDT", sdt);
        values.put("NgaySinh", ngaySinh);
        values.put("NgayKham", ngay);
        values.put("GioKham", gio);
        values.put("TienSuBenh", tienSu);
        values.put("PhongKham", phong);

        long result = db.insert("PhieuKhamBenh", null, values);
        db.close();
        return result != -1;
    }

    //CARDVIEW
    // Trả về các lịch CHƯA khám (TrangThai = 0)
    public List<PhieuKhamBenh> getAllLichKham() {
        List<PhieuKhamBenh> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PhieuKhamBenh WHERE TrangThai = 0", null);
        while (cursor.moveToNext()) {
            list.add(createPhieuKhamFromCursor(cursor));
        }
        cursor.close();
        return list;
    }

    // Trả về các lịch ĐÃ khám (TrangThai = 1)
    public List<PhieuKhamBenh> getLichDaKham() {
        List<PhieuKhamBenh> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PhieuKhamBenh WHERE TrangThai = 1", null);
        while (cursor.moveToNext()) {
            list.add(createPhieuKhamFromCursor(cursor));
        }
        cursor.close();
        return list;
    }

    // Hàm tiện ích dùng chung cho cả 2 hàm trên
    private PhieuKhamBenh createPhieuKhamFromCursor(Cursor cursor) {
        return new PhieuKhamBenh(
                cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("TenBenhNhan")),
                cursor.getString(cursor.getColumnIndexOrThrow("SDT")),
                cursor.getString(cursor.getColumnIndexOrThrow("NgaySinh")),
                cursor.getString(cursor.getColumnIndexOrThrow("NgayKham")),
                cursor.getString(cursor.getColumnIndexOrThrow("GioKham")),
                cursor.getString(cursor.getColumnIndexOrThrow("TienSuBenh")),
                cursor.getString(cursor.getColumnIndexOrThrow("PhongKham"))
        );
    }

    //XOA LICH KHAM
    public boolean deleteLichKham(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("PhieuKhamBenh", "id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }
    //SUA LICH KHAM
    public boolean updateLichKham(int id, String tenBN, String sdt, String ngaySinh, String ngay, String gio, String tienSu, String phong) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenBenhNhan", tenBN);
        values.put("SDT", sdt);
        values.put("NgaySinh", ngaySinh);
        values.put("NgayKham", ngay);
        values.put("GioKham", gio);
        values.put("TienSuBenh", tienSu);
        values.put("PhongKham", phong);
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
    //lay thuoc
    public List<Thuoc> getAllThuoc() {
        List<Thuoc> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, TenThuoc, MoTa, giaTien FROM Thuoc", null);

        if (cursor.moveToFirst()) {
            do {
                list.add(new Thuoc(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getDouble(3)
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }
    public boolean updateTrangThaiDaKham(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TrangThai", 1); // 1 = đã khám
        int rows = db.update("PhieuKhamBenh", values, "id=?", new String[]{String.valueOf(id)});
        return rows > 0;
    }

    public boolean insertThuoc(String ten, String moTa, double giaTien) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenThuoc", ten);
        values.put("MoTa", moTa);
        values.put("giaTien", giaTien);
        long result = db.insert("Thuoc", null, values);
        return result != -1;
    }
    public List<String> getAllTenKhoa() {
        List<String> khoaList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT tenKhoa FROM Khoa", null);

        if (cursor.moveToFirst()) {
            do {
                khoaList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return khoaList;
    }
    public double getGiaPhongKham(String tenKhoa) {
        double gia = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT giatien FROM Khoa WHERE tenKhoa = ?", new String[]{tenKhoa});
        if (cursor.moveToFirst()) {
            gia = cursor.getDouble(0);
        }
        cursor.close();
        db.close();
        return gia;
    }





    //
    public List<DaKhamXong> getAllDaKhamXongList() {
        List<DaKhamXong> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM DaKhamXong", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String tenBenhNhan = cursor.getString(cursor.getColumnIndexOrThrow("TenBenhNhan"));
                String ngaySinh = cursor.getString(cursor.getColumnIndexOrThrow("NgaySinh"));
                String sdt = cursor.getString(cursor.getColumnIndexOrThrow("SDT"));
                String phongKham = cursor.getString(cursor.getColumnIndexOrThrow("PhongKham"));
                String tienSuBenh = cursor.getString(cursor.getColumnIndexOrThrow("TienSuBenh"));
                String ngayKham = cursor.getString(cursor.getColumnIndexOrThrow("NgayKham"));
                double tongTien = cursor.getDouble(cursor.getColumnIndexOrThrow("TongTien"));

                DaKhamXong daKham = new DaKhamXong(id, tenBenhNhan, ngaySinh, sdt, phongKham, tienSuBenh,ngayKham, tongTien);
                list.add(daKham);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    //Ke Toan
    public boolean insertDaKhamXong(String tenBenhNhan, String sdt, String ngaySinh, String ngayKham,
                                    String tienSuBenh, String phongKham, double tongTien) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenBenhNhan", tenBenhNhan);
        values.put("SDT", sdt);
        values.put("NgaySinh", ngaySinh);
        values.put("NgayKham", ngayKham);
        values.put("TienSuBenh", tienSuBenh);
        values.put("PhongKham", phongKham);
        values.put("TongTien", tongTien);

        long result = db.insert("DaKhamXong", null, values);

        if (result != -1) {
            // Tự động cộng vào bảng DoanhThu
            insertOrUpdateDoanhThu(ngayKham, tongTien);
        }

        db.close();
        return result != -1;
    }
    private void insertOrUpdateDoanhThu(String date, double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, amount FROM DoanhThu WHERE date = ?", new String[]{date});

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            double currentAmount = cursor.getDouble(1);
            ContentValues values = new ContentValues();
            values.put("amount", currentAmount + amount);
            db.update("DoanhThu", values, "id = ?", new String[]{String.valueOf(id)});
        } else {
            ContentValues values = new ContentValues();
            values.put("date", date);
            values.put("amount", amount);
            db.insert("DoanhThu", null, values);
        }

        cursor.close();
    }
    public List<RevenueItem> getAllDoanhThu() {
        List<RevenueItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM DoanhThu", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String date = cursor.getString(1);
                double amount = cursor.getDouble(2);
                list.add(new RevenueItem(id, date, amount));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }
    // ✅ Thêm vào DatabaseHelper.java
    public List<DaKhamXong> getDaKhamXongTheoNgay(String ngayKham) {
        List<DaKhamXong> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM DaKhamXong WHERE NgayKham = ?",
                new String[]{ngayKham}
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String tenBenhNhan = cursor.getString(cursor.getColumnIndexOrThrow("TenBenhNhan"));
                String ngaySinh = cursor.getString(cursor.getColumnIndexOrThrow("NgaySinh"));
                String sdt = cursor.getString(cursor.getColumnIndexOrThrow("SDT"));
                String phongKham = cursor.getString(cursor.getColumnIndexOrThrow("PhongKham"));
                String tienSuBenh = cursor.getString(cursor.getColumnIndexOrThrow("TienSuBenh"));
                String ngay = cursor.getString(cursor.getColumnIndexOrThrow("NgayKham"));
                double tongTien = cursor.getDouble(cursor.getColumnIndexOrThrow("TongTien"));

                list.add(new DaKhamXong(id, tenBenhNhan, ngaySinh, sdt, phongKham, tienSuBenh, ngay, tongTien));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }







    public boolean deleteDaKhamXong(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("DaKhamXong", "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }



















    //admin
    //Tai Khoan
    //THEM TaiKhoan
    // Thêm tài khoản
    public boolean insertTaiKhoan(String maTk, String ten, String mk, String loaiTK, String mail, String tinhTrang) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maTk", maTk);
        values.put("tenTk", ten);
        values.put("mk", mk);
        values.put("loai_TK", loaiTK);
        values.put("mail", mail);
        values.put("tinh_trang", tinhTrang);

        long result = db.insert("TaiKhoan", null, values);
        db.close();
        return result != -1; // trả về true nếu thêm thành công
    }
    //CARDVIEW
    // Lấy tất cả tài khoản
    public List<String> getAllTaiKhoan() {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT maTk || ' - ' || tenTk FROM TaiKhoan", null);

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    // Xoá tài khoản theo id
    public boolean deleteTaiKhoan(String maBS) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("BacSi", "maBS = ?", new String[]{maBS});
        db.close();
        return result > 0;
    }
    // Cap Nhat Tai Khoan
    public boolean updateTaiKhoan(String maTk, String ten, String mk, String loaiTK, String mail, String tinhTrang) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maTK", maTk);
        values.put("tenTk", ten);
        values.put("mk", mk);
        values.put("loai_TK", loaiTK);
        values.put("mail", mail);
        values.put("tinh_trang", tinhTrang);
        long result = db.insert("TaiKhoan", null, values);
        db.close();
        return result != -1;
    }
    //Bac Sĩ
    //Thêm Bác Sĩ
    public boolean insertBacSi(String maBS, String tenBS, String gioiTinh,
                               String sdt, String ngaySinh, String diaChi, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("maBS", maBS);
        values.put("tenBS", tenBS);
        values.put("gioiTinh", gioiTinh);
        values.put("sdt", sdt);
        values.put("ngaySinh", ngaySinh);
        values.put("diaChi", diaChi);
        values.put("email", email);

        long result = db.insert("BacSi", null, values);
        db.close();
        return result != -1; // Trả về true nếu insert thành công
    }
    //Lấy danh sách Bác sĩ
    public List<String> getAllBacSi() {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT maBS || ' - ' || tenBS FROM BacSi", null);

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    // Delete Bác Sĩ
    public boolean deleteBacSi(String maBS) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            // Lấy email (không đóng db trong getEmailFromMaBS)
            String email = getEmailFromMaBS(maBS);

            // Xóa bác sĩ
            int deletedRows = db.delete("BacSi", "maBS = ?", new String[]{maBS});
            boolean success = deletedRows > 0;

            // Xóa tài khoản nếu cần
            if (success && email != null) {
                db.delete("TaiKhoan", "mail = ?", new String[]{email});
            }

            db.setTransactionSuccessful();
            return success;
        } catch (Exception e) {
            Log.e("Database", "Error deleting doctor", e);
            return false;
        } finally {
            try {
                db.endTransaction();
            } finally {
                db.close(); // Đóng db ở đây sau khi kết thúc mọi thao tác
            }
        }
    }
    //Cập nhật bác sĩ
    public boolean updateBacSi(String maBS, String tenBS,String diachi, String ngaySinh,String sdt, String email,String gioitinh) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenBS", tenBS);
        values.put("sdt", sdt);
        values.put("gioitinh", gioitinh);
        values.put("ngaySinh", ngaySinh);
        values.put("email", email);
        values.put("diachi", diachi);
        int result = db.update("BacSi", values, "maBS = ?", new String[]{maBS});
        db.close();
        return result > 0;
    }
    // Nhân Viên
    //Thêm Nhân Viên
    public boolean insertNhanVien(String maNV, String tenNV, String gioiTinh,
                                  String sdt, String ngaySinh, String diaChi, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("maNV", maNV);
        values.put("tenNV", tenNV);
        values.put("gioiTinh ", gioiTinh);
        values.put("sdt", sdt);
        values.put("ngaySinh", ngaySinh);
        values.put("diaChi", diaChi);
        values.put("email", email);

        long result = db.insert("NhanVien", null, values);
        db.close();
        return result != -1;
    }
    // Danh Sách Nhân Viên
    public List<String> getAllNhanVien() {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT maNV || ' - ' || tenNV FROM NhanVien", null);

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    // Xóa Nhân Viên
    public boolean deleteNhanVien(String maNV) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = false;

        try {
            db.beginTransaction();

            // Sử dụng db connection đã mở
            String email = getEmailFromMaNV(db, maNV);

            // Xóa nhân viên
            int result = db.delete("NhanVien", "maNV=?", new String[]{maNV});

            if (result > 0) {
                // Xóa tài khoản nếu có email
                if (email != null) {
                    db.delete("TaiKhoan", "mail=?", new String[]{email});
                }
                success = true;
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.e("Database", "Error deleting employee", e);
        } finally {
            try {
                db.endTransaction();
            } finally {
                db.close();
            }
        }
        return success;
    }
    // Cập Nhật Nhân Viên
    public boolean updateNhanVien(String maNV, String tenNV,String diachi, String ngaySinh,String gioitinh, String sdt, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenNV", tenNV);
        values.put("sdt", sdt);
        values.put("gioitinh", gioitinh);
        values.put("ngaySinh", ngaySinh);
        values.put("email", email);
        values.put("diachi", diachi);

        int result = db.update("NhanVien", values, "maNV = ?", new String[]{maNV});
        db.close();
        return result > 0;
    }

    //Thêm Ke Toan
    public boolean insertKeToan(String maKT, String tenKT, String gioiTinh,
                               String sdt, String ngaySinh, String diaChi, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("maKT", maKT);
        values.put("tenKT", tenKT);
        values.put("gioiTinh", gioiTinh);
        values.put("sdt", sdt);
        values.put("ngaySinh", ngaySinh);
        values.put("diaChi", diaChi);
        values.put("email", email);

        long result = db.insert("keToan", null, values);
        db.close();
        return result != -1; // Trả về true nếu insert thành công
    }
    //Lấy danh sách Kế Toán
    public List<String> getAllKeToan() {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT maKT || ' - ' || tenKT FROM keToan", null);

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    // Delete Bác Sĩ
    public boolean deleteKeToan(String maKT) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            // Lấy email (không đóng db trong getEmailFromMaBS)
            String email = getEmailFromMaBS(maKT);

            // Xóa bác sĩ
            int deletedRows = db.delete("keToan", "maKT = ?", new String[]{maKT});
            boolean success = deletedRows > 0;

            // Xóa tài khoản nếu cần
            if (success && email != null) {
                db.delete("TaiKhoan", "mail = ?", new String[]{email});
            }

            db.setTransactionSuccessful();
            return success;
        } catch (Exception e) {
            Log.e("Database", "Error deleting doctor", e);
            return false;
        } finally {
            try {
                db.endTransaction();
            } finally {
                db.close(); // Đóng db ở đây sau khi kết thúc mọi thao tác
            }
        }
    }
    //Cập nhật bác sĩ
    public boolean updateKeToan(String maKT, String tenKT,String diachi, String ngaySinh,String sdt, String email,String gioitinh) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenKT", tenKT);
        values.put("sdt", sdt);
        values.put("gioitinh", gioitinh);
        values.put("ngaySinh", ngaySinh);
        values.put("email", email);
        values.put("diachi", diachi);
        int result = db.update("keToan", values, "maKT = ?", new String[]{maKT});
        db.close();
        return result > 0;
    }
// Trong DatabaseHelper.java

    // Lấy thông tin nhân viên từ mã tài khoản
    public String getMaNVFromTaiKhoan(String maTaikhoan) {
        SQLiteDatabase db = this.getReadableDatabase();
        String maNV = null;

        Cursor cursor = db.rawQuery("SELECT maNV FROM NhanVien WHERE email = ?",
                new String[]{maTaikhoan});

        if (cursor.moveToFirst()) {
            maNV = cursor.getString(0);
        }

        cursor.close();
        db.close();
        return maNV;
    }

    // Lấy thông tin bác sĩ từ mã tài khoản
    public String getMaBSFromTaiKhoan(String maTaikhoan) {
        SQLiteDatabase db = this.getReadableDatabase();
        String maBS = null;

        Cursor cursor = db.rawQuery("SELECT maBS FROM BacSi WHERE email = ?",
                new String[]{maTaikhoan});

        if (cursor.moveToFirst()) {
            maBS = cursor.getString(0);
        }

        cursor.close();
        db.close();
        return maBS;
    }
// Thêm vào DatabaseHelper.java

    // Model cho Spinner bác sĩ
    public static class BacSiSpinner {
        private String maBS;
        private String tenBS;

        public BacSiSpinner(String maBS, String tenBS) {
            this.maBS = maBS;
            this.tenBS = tenBS;
        }

        public String getMaBS() {
            return maBS;
        }

        public String getTenBS() {
            return tenBS;
        }

        @Override
        public String toString() {
            return maBS + " - " + tenBS;
        }
    }

    // Thêm khoa mới
    public boolean insertKhoa(String tenKhoa, String moTa, double giaTien) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenKhoa", tenKhoa);
        values.put("moTa", moTa);
        values.put("giatien", giaTien);  // Lưu ý: tên cột trong bảng là "giatien"
        long result = db.insert("Khoa", null, values);
        db.close();
        return result != -1;
    }
    // Lấy thông tin 1 khoa
    public Khoa getKhoaById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Khoa khoa = null;
        Cursor cursor = db.query("Khoa",
                new String[]{"id", "tenKhoa", "moTa", "giatien"}, // Lấy tất cả các cột cần thiết
                "id = ?", new String[]{String.valueOf(id)}, // Tìm kiếm theo ID
                null, null, null);
        if (cursor.moveToFirst()) {
            khoa = new Khoa(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")), // Lấy ID
                    cursor.getString(cursor.getColumnIndexOrThrow("tenKhoa")),
                    cursor.getString(cursor.getColumnIndexOrThrow("moTa")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("giatien"))
            );
        }
        cursor.close();
        db.close();
        return khoa;
    }
    // Cập nhật thông tin khoa
    public boolean updateKhoa(int id, String tenKhoa, String moTa, double giaTien) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenKhoa", tenKhoa);
        values.put("moTa", moTa);
        values.put("giatien", giaTien);
        int result = db.update("Khoa", values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }
    // Xóa khoa
    public boolean deleteKhoa(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("Khoa", "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    // Lấy danh sách tất cả các khoa
    public List<Khoa> getAllKhoa() {
        List<Khoa> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT id, tenKhoa, moTa, giatien FROM Khoa ORDER BY id";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            list.add(new Khoa(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("tenKhoa")),
                    cursor.getString(cursor.getColumnIndexOrThrow("moTa")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("giatien"))
            ));
        }
        cursor.close();
        db.close();
        return list;
    }
    // Tìm kiếm khoa
    public List<Khoa> timKiemKhoa(String keyword) {
        List<Khoa> ketQua = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT id, tenKhoa, moTa, giatien FROM Khoa " +
                "WHERE tenKhoa LIKE ? OR moTa LIKE ? " +
                "ORDER BY id";
        Cursor cursor = db.rawQuery(query, new String[]{
                "%" + keyword + "%",
                "%" + keyword + "%"
        });
        while (cursor.moveToNext()) {
            ketQua.add(new Khoa(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("tenKhoa")),
                    cursor.getString(cursor.getColumnIndexOrThrow("moTa")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("giatien"))
            ));
        }
        cursor.close();
        db.close();
        return ketQua;
    }
    // Lấy email bác sĩ và nhân viên
    public String getEmailFromMaBS(String maBS) {
        SQLiteDatabase db = this.getReadableDatabase();
        String email = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT email FROM BacSi WHERE maBS = ?", new String[]{maBS});
            if (cursor.moveToFirst()) {
                email = cursor.getString(0);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            // KHÔNG đóng db ở đây
        }
        return email;
    }

    public String getEmailFromMaNV(SQLiteDatabase db, String maNV) {
        String email = null;
        Cursor cursor = null;
        try {
            cursor = db.query("NhanVien",
                    new String[]{"email"},
                    "maNV=?",
                    new String[]{maNV},
                    null, null, null);
            if (cursor.moveToFirst()) {
                email = cursor.getString(0);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            // KHÔNG đóng db ở đây
        }
        return email;
    }

}
