package com.example.doanphongkham.Admin;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DanhSachTaiKhoan_ThongTinActivity extends AppCompatActivity {

    private EditText edtTenNV, edtMaNV, edtEmail, edtSDT, edtNgaySinh, edtDiaChi;
    private Spinner spinnerGioiTinh;
    private FloatingActionButton btnSave, btnDelete;
    private Calendar calendar;
    private DatabaseHelper databaseHelper;
    private String ma, loaiTaiKhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_tai_khoan_thong_tin);

        // Initialize views
        initViews();

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Initialize calendar for date picker
        calendar = Calendar.getInstance();

        // Setup gender spinner
        setupGenderSpinner();

        // Setup date picker
        setupDatePicker();

        // Get data from intent
        processIntentData();

        // Setup buttons
        setupButtons();
    }

    private void initViews() {
        edtTenNV = findViewById(R.id.taotaikhoan_themthongtinnhanvien_textbox_tennhanvien);
        edtMaNV = findViewById(R.id.taotaikhoan_themthongtinnhanvien_textbox_manhanvien);
        edtEmail = findViewById(R.id.thongtintaikhoan_textbox_email);
        edtSDT = findViewById(R.id.thongtintaikhoan_textbox_sodienthoai);
        edtNgaySinh = findViewById(R.id.thongtintaikhoan_textbox_ngaysinh);
        edtDiaChi = findViewById(R.id.thongtintaikhoan_textbox_diachi);
        spinnerGioiTinh = findViewById(R.id.thongtintaikhoan_spinner_gioitinh);
        btnSave = findViewById(R.id.thongtintaikhoan_button_save);
        btnDelete = findViewById(R.id.thongtintaikhoan_button_xoa);
    }

    private void setupGenderSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.gender_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGioiTinh.setAdapter(adapter);
    }

    private void setupDatePicker() {
        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            }
        };

        edtNgaySinh.setOnClickListener(v -> {
            new DatePickerDialog(
                    DanhSachTaiKhoan_ThongTinActivity.this,
                    dateListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });
    }

    private void updateDateLabel() {
        String dateFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
        edtNgaySinh.setText(sdf.format(calendar.getTime()));
    }

    private void processIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("MA_BS")) {
                ma = intent.getStringExtra("MA_BS");
                loaiTaiKhoan = "Bác sĩ";
                loadBacSiInfo();
            } else if (intent.hasExtra("MA_NV")) {
                ma = intent.getStringExtra("MA_NV");
                loaiTaiKhoan = "Nhân viên";
                loadNhanVienInfo();
            }
        }
    }

    private void loadBacSiInfo() {
        Cursor cursor = databaseHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM BacSi WHERE maBS = ?", new String[]{ma});

        if (cursor.moveToFirst()) {
            edtMaNV.setText(cursor.getString(0));
            edtTenNV.setText(cursor.getString(1));

            // Set gender spinner
            String gioiTinh = cursor.getString(2);
            if (gioiTinh != null) {
                int spinnerPosition = ((ArrayAdapter)spinnerGioiTinh.getAdapter()).getPosition(gioiTinh);
                spinnerGioiTinh.setSelection(spinnerPosition);
            }

            edtSDT.setText(cursor.getString(3));
            edtNgaySinh.setText(cursor.getString(4));
            edtDiaChi.setText(cursor.getString(5));
            edtEmail.setText(cursor.getString(6));
        }
        cursor.close();
    }

    private void loadNhanVienInfo() {
        Cursor cursor = databaseHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM NhanVien WHERE maNV = ?", new String[]{ma});

        if (cursor.moveToFirst()) {
            edtMaNV.setText(cursor.getString(0));
            edtTenNV.setText(cursor.getString(1));

            // Set gender spinner
            String gioiTinh = cursor.getString(2);
            if (gioiTinh != null) {
                int spinnerPosition = ((ArrayAdapter)spinnerGioiTinh.getAdapter()).getPosition(gioiTinh);
                spinnerGioiTinh.setSelection(spinnerPosition);
            }

            edtSDT.setText(cursor.getString(3));
            edtNgaySinh.setText(cursor.getString(4));
            edtDiaChi.setText(cursor.getString(5));
            edtEmail.setText(cursor.getString(6));
        }
        cursor.close();
    }

    private void setupButtons() {
        btnSave.setOnClickListener(v -> {
            if (saveInfo()) {
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnDelete.setOnClickListener(v -> {
            showDeleteConfirmationDialog();
        });
    }

    private boolean saveInfo() {
        String ten = edtTenNV.getText().toString().trim();
        String sdt = edtSDT.getText().toString().trim();
        String ngaySinh = edtNgaySinh.getText().toString().trim();
        String diaChi = edtDiaChi.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String gioiTinh = spinnerGioiTinh.getSelectedItem().toString();

        // Validate input
        if (ten.isEmpty() || sdt.isEmpty() || ngaySinh.isEmpty() || diaChi.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!sdt.matches("\\d{10,11}")) {
            Toast.makeText(this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean result;
        if (loaiTaiKhoan.equals("Bác sĩ")) {
            result = databaseHelper.updateBacSi(
                    ma,
                    ten,
                    diaChi,
                    ngaySinh,
                    sdt,
                    email,
                    gioiTinh
            );
        } else {
            result = databaseHelper.updateNhanVien(
                    ma,
                    ten,
                    diaChi,
                    ngaySinh,
                    gioiTinh,
                    sdt,
                    email
            );
        }

        if (!result) {
            Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa " + loaiTaiKhoan + " này và tài khoản liên quan?");

        builder.setPositiveButton("Xóa", (dialog, which) -> {
            boolean result;
            if (loaiTaiKhoan.equals("Bác sĩ")) {
                result = databaseHelper.deleteBacSi(ma);
            } else {
                result = databaseHelper.deleteNhanVien(ma);
            }

            if (result) {
                Toast.makeText(this, "Đã xóa " + loaiTaiKhoan + " và tài khoản liên quan", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }
}