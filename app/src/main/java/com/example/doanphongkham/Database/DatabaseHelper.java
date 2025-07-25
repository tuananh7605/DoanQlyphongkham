package com.example.doanphongkham.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.doanphongkham.Model.KhachHang;
import com.example.doanphongkham.Model.Khoa;
import com.example.doanphongkham.Model.PhieuKhamBenh;
import com.example.doanphongkham.Model.Thuoc;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PhongkhamDB";
    private static final int DATABASE_VERSION =26;

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
                "GioKham TEXT NOT NULL, " +
                "TienSuBenh TEXT, " +
                "PhongKham TEXT, " +
                "TongTien REAL DEFAULT 0" +
                ");";








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
                "maKhoa TEXT PRIMARY KEY, " +
                "tenKhoa TEXT NOT NULL, " +
                "maBS TEXT, " +
                "moTa TEXT, " +
                "FOREIGN KEY (maBS) REFERENCES BacSi(maBS) ON DELETE SET NULL" +
                ");";
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
                cursor.getString(cursor.getColumnIndexOrThrow("NgayKham")),
                cursor.getString(cursor.getColumnIndexOrThrow("SDT")),
                cursor.getString(cursor.getColumnIndexOrThrow("NgaySinh")),
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
    public boolean updateLichKham(int id, String tenBN, String ngay, String gio, String tienSu, String phong, String sdt, String ngaySinh) {
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
    public boolean insertDaKhamXong(String ten, String sdt, String ngaySinh, String ngay, String gio, String tienSu, String phong, double tongTien) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("TenBenhNhan", ten);
        values.put("SDT", sdt);
        values.put("NgaySinh", ngaySinh);
        values.put("NgayKham", ngay);
        values.put("GioKham", gio);
        values.put("TienSuBenh", tienSu);
        values.put("PhongKham", phong);
        values.put("TongTien", tongTien);

        long result = db.insert("DaKhamXong", null, values);
        return result != -1;
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

    // Lấy danh sách bác sĩ cho Spinner
    public List<BacSiSpinner> getAllBacSiForSpinner() {
        List<BacSiSpinner> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT maBS, tenBS FROM BacSi", null);

        if (cursor.moveToFirst()) {
            do {
                list.add(new BacSiSpinner(
                        cursor.getString(0), // maBS
                        cursor.getString(1)  // tenBS
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    // Tạo mã khoa tự động
    public String generateMaKhoa() {
        SQLiteDatabase db = this.getReadableDatabase();
        String maKhoa = "KH001"; // Mã mặc định

        Cursor cursor = db.rawQuery("SELECT maKhoa FROM Khoa ORDER BY maKhoa DESC LIMIT 1", null);
        if (cursor.moveToFirst()) {
            String lastMaKhoa = cursor.getString(0);
            int number = Integer.parseInt(lastMaKhoa.substring(2)) + 1;
            maKhoa = "KH" + String.format("%03d", number);
        }
        cursor.close();
        db.close();
        return maKhoa;
    }

    // Thêm khoa mới
    public boolean insertKhoa(String maKhoa, String tenKhoa, String maBS, String moTa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("maKhoa", maKhoa);
        values.put("tenKhoa", tenKhoa);
        values.put("maBS", maBS);
        values.put("moTa", moTa);

        long result = db.insert("Khoa", null, values);
        db.close();
        return result != -1;
    }

    // Lấy thông tin 1 khoa
    public Khoa getKhoaByMa(String maKhoa) {
        SQLiteDatabase db = this.getReadableDatabase();
        Khoa khoa = null;

        Cursor cursor = db.query("Khoa",
                new String[]{"maKhoa", "tenKhoa", "maBS", "moTa"},
                "maKhoa = ?", new String[]{maKhoa},
                null, null, null);

        if (cursor.moveToFirst()) {
            khoa = new Khoa(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
        }

        cursor.close();
        db.close();
        return khoa;
    }

    // Cập nhật thông tin khoa
    public boolean updateKhoa(String maKhoa, String tenKhoa, String maBS, String moTa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("tenKhoa", tenKhoa);
        values.put("maBS", maBS);
        values.put("moTa", moTa);

        int result = db.update("Khoa", values, "maKhoa = ?", new String[]{maKhoa});
        db.close();
        return result > 0;
    }

    // Xóa khoa
    public boolean deleteKhoa(String maKhoa) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("Khoa", "maKhoa = ?", new String[]{maKhoa});
        db.close();
        return result > 0;
    }

    // Lấy danh sách khoa với thông tin bác sĩ
    public List<KhoaWithBacSi> getAllKhoaWithBacSi() {
        List<KhoaWithBacSi> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT K.maKhoa, K.tenKhoa, K.maBS, B.tenBS, K.moTa " +
                "FROM Khoa K LEFT JOIN BacSi B ON K.maBS = B.maBS " +
                "ORDER BY K.maKhoa";

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            KhoaWithBacSi info = new KhoaWithBacSi(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
            list.add(info);
        }

        cursor.close();
        db.close();
        return list;
    }

    // Model cho thông tin khoa kèm tên bác sĩ
    public static class KhoaWithBacSi {
        private String maKhoa;
        private String tenKhoa;
        private String maBS;
        private String tenBS;
        private String moTa;

        public KhoaWithBacSi(String maKhoa, String tenKhoa, String maBS, String tenBS, String moTa) {
            this.maKhoa = maKhoa;
            this.tenKhoa = tenKhoa;
            this.maBS = maBS;
            this.tenBS = tenBS;
            this.moTa = moTa;
        }

        // Getter methods
        public String getMaKhoa() { return maKhoa; }
        public String getTenKhoa() { return tenKhoa; }
        public String getMaBS() { return maBS; }
        public String getTenBS() { return tenBS; }
        public String getMoTa() { return moTa; }
    }
// Thêm vào DatabaseHelper.java

    // Tìm kiếm khoa
    public List<KhoaWithBacSi> timKiemKhoa(String keyword) {
        List<KhoaWithBacSi> ketQua = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT K.maKhoa, K.tenKhoa, K.maBS, B.tenBS, K.moTa " +
                "FROM Khoa K LEFT JOIN BacSi B ON K.maBS = B.maBS " +
                "WHERE K.maKhoa LIKE ? OR K.tenKhoa LIKE ? OR B.tenBS LIKE ? " +
                "ORDER BY K.maKhoa";

        Cursor cursor = db.rawQuery(query, new String[]{
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%"
        });

        while (cursor.moveToNext()) {
            ketQua.add(new KhoaWithBacSi(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
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
