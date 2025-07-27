package com.example.doanphongkham.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.R;

import java.util.ArrayList;
import java.util.List;

public class DanhSachTaiKhoanActivity extends AppCompatActivity {

    private EditText edtTimKiem;
    private Button btnTimKiem;
    private Spinner spnLoaiTaiKhoan;
    private ListView lvDanhSach;

    // Database
    private DatabaseHelper databaseHelper;

    // Data
    private ArrayList<String> danhSachTaiKhoan = new ArrayList<>();
    private String[] loaiTaiKhoan = {"Tất cả", "Bác sĩ", "Nhân viên", "Kế Toán"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_tai_khoan); // Thêm dòng này

        // Xử lý nút back đúng cách
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(DanhSachTaiKhoanActivity.this, QuanLyNhanVienActivity.class));
                finish();
            }

        });

        // Initialize database
        databaseHelper = new DatabaseHelper(this);

        // Map UI components
        anhXa();

        // Setup spinner
        setupSpinner();

        // Load all accounts initially
        taiDanhSachTaiKhoan("Tất cả");

        // Setup search button
        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timKiemTaiKhoan();
            }
        });
        // Setup list view item click
        setupListViewClick();
    }
    private void anhXa() {
        edtTimKiem = findViewById(R.id.danhsachtaikhoan_textbox_tim);
        btnTimKiem = findViewById(R.id.danhsachtaikhoan_button_tim);
        spnLoaiTaiKhoan = findViewById(R.id.danhsachtaikhoan_spinner_loaitaikhoan);
        lvDanhSach = findViewById(R.id.danhsachtaikhoan_listview_danhsachtaikhoan);
    }

    private void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, loaiTaiKhoan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLoaiTaiKhoan.setAdapter(adapter);

        spnLoaiTaiKhoan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edtTimKiem.setText("");
                String loai = spnLoaiTaiKhoan.getSelectedItem().toString();
                taiDanhSachTaiKhoan(loai);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void taiDanhSachTaiKhoan(String loai) {
        danhSachTaiKhoan.clear(); // Xóa danh sách cũ

        if (loai.equals("Tất cả")) {
            // Chỉ lấy bác sĩ và nhân viên, không lấy tài khoản thông thường
            danhSachTaiKhoan.addAll(databaseHelper.getAllBacSi());
            danhSachTaiKhoan.addAll(databaseHelper.getAllNhanVien());
            danhSachTaiKhoan.addAll(databaseHelper.getAllKeToan());
        }
        else if (loai.equals("Bác sĩ")) {
            // Chỉ lấy bác sĩ
            danhSachTaiKhoan.addAll(databaseHelper.getAllBacSi());
        }
        else if (loai.equals("Nhân viên")) {
            // Chỉ lấy nhân viên
            danhSachTaiKhoan.addAll(databaseHelper.getAllNhanVien());
        }
        else if (loai.equals("Kế Toán")) {
            // Chỉ lấy nhân viên
            danhSachTaiKhoan.addAll(databaseHelper.getAllKeToan());
        }
        // Cập nhật ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, danhSachTaiKhoan);
        lvDanhSach.setAdapter(adapter);
    }

    private void timKiemTaiKhoan() {
        String keyword = edtTimKiem.getText().toString().trim();
        if (keyword.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
            return;
        }

        String loai = spnLoaiTaiKhoan.getSelectedItem().toString();
        danhSachTaiKhoan.clear();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        if (loai.equals("Tất cả")) {
            // Tìm trong cả bác sĩ và nhân viên
            searchInTable(db, "BacSi", "maBS", "tenBS", keyword);
            searchInTable(db, "NhanVien", "maNV", "tenNV", keyword);
            searchInTable(db, "keToan", "maKT", "tenKT", keyword);
        }
        else if (loai.equals("Bác sĩ")) {
            searchInTable(db, "BacSi", "maBS", "tenBS", keyword);
        }
        else if (loai.equals("Nhân viên")) {
            searchInTable(db, "NhanVien", "maNV", "tenNV", keyword);
        }
        else if (loai.equals("Kế Toán")) {
            searchInTable(db, "keToan", "maKT", "tenKT", keyword);
        }
        db.close();

        // Cập nhật ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, danhSachTaiKhoan);
        lvDanhSach.setAdapter(adapter);
    }

    private void searchInTable(SQLiteDatabase db, String tableName, String codeColumn, String nameColumn, String keyword) {
        String query = "SELECT " + codeColumn + ", " + nameColumn +
                " FROM " + tableName +
                " WHERE " + codeColumn + " LIKE ? OR " + nameColumn + " LIKE ?";

        // Thêm % vào keyword để tìm kiếm phần tử chứa keyword
        String searchPattern = "%" + keyword + "%";

        Cursor cursor = db.rawQuery(query, new String[]{searchPattern, searchPattern});

        while (cursor.moveToNext()) {
            String code = cursor.getString(0);
            String name = cursor.getString(1);
            danhSachTaiKhoan.add(code + " - " + name);
        }

        cursor.close();
    }
    private void searchAccounts(List<String> accounts, String keyword) {
        for (String account : accounts) {
            // Split the account string into code and name
            String[] parts = account.split(" - ");
            if (parts.length >= 2) {
                String code = parts[0];
                String name = parts[1];

                // Check if code matches exactly or name contains the keyword (case insensitive)
                if (code.equals(keyword) ||
                        name.toLowerCase().contains(keyword.toLowerCase())) {
                    danhSachTaiKhoan.add(account);
                }
            }
        }
    }

    private void setupListViewClick() {
        lvDanhSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = danhSachTaiKhoan.get(position);
                String[] parts = selectedItem.split(" - ");
                String ma = parts[0];
                String ten = parts[1];
                // Determine account type
                String loai = spnLoaiTaiKhoan.getSelectedItem().toString();
                if (loai.equals("Tất cả")) {
                    // Need to check if it's doctor or staff
                    for (String doctor : databaseHelper.getAllBacSi()) {
                        if (doctor.startsWith(ma)) {
                            loai = "Bác sĩ";
                            break;
                        }
                    }
                    if (!loai.equals("Bác sĩ")) {
                        for (String staff : databaseHelper.getAllNhanVien()) {
                            if (staff.startsWith(ma)) {
                                loai = "Nhân viên";
                                break;
                            }
                        }
                    }
                }
                if (loai.equals("Bác sĩ")) {
                    hienThiThongTinBacSi(ma, ten);
                } else if (loai.equals("Nhân viên")) {
                    hienThiThongTinNhanVien(ma, ten);
                } else if (loai.equals("Kế Toán")) {
                    hienThiThongTinKeToan(ma, ten); // Thêm hiển thị thông tin kế toán
                }
            }
        });
    }

    private void hienThiThongTinBacSi(String maBS,String tenBS) {
        // Show doctor info dialog with options
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông tin bác sĩ");
        builder.setMessage("Mã: " + maBS +"\nTênBS: " + tenBS + "\nChuyên Khoa:");

        builder.setPositiveButton("Xem chi tiết", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Open detail activity
                Intent intent = new Intent(DanhSachTaiKhoanActivity.this, DanhSachTaiKhoan_ThongTinActivity.class);
                intent.putExtra("MA_BS", maBS);
                startActivity(intent);
            }
        });
        builder.show();
    }

    private void hienThiThongTinNhanVien(String maNV,String tenNV) {
        // Show staff info dialog with options
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông tin nhân viên");
        builder.setMessage("Mã: " + maNV +"\nTênNV: " + tenNV );

        builder.setPositiveButton("Xem chi tiết", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Open detail activity
                Intent intent = new Intent(DanhSachTaiKhoanActivity.this, DanhSachTaiKhoan_ThongTinActivity.class);
                intent.putExtra("MA_NV", maNV);
                startActivity(intent);
            }
        });
        builder.show();
    }
    private void hienThiThongTinKeToan(String maKT, String tenKT) {
        // Show accountant info dialog with options
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông tin kế toán");
        builder.setMessage("Mã: " + maKT + "\nTên: " + tenKT);
        builder.setPositiveButton("Xem chi tiết", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Open detail activity
                Intent intent = new Intent(DanhSachTaiKhoanActivity.this, DanhSachTaiKhoan_ThongTinActivity.class);
                intent.putExtra("MA_KT", maKT);
                startActivity(intent);
            }
        });
        builder.show();
    }
    private void taiLaiDanhSach() {
        String loai = spnLoaiTaiKhoan.getSelectedItem().toString();
        taiDanhSachTaiKhoan(loai);
    }

    @Override
    protected void onResume() {
        super.onResume();
        taiLaiDanhSach();
    }


}