package com.example.doanphongkham.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class QuanLyTaiKhoan_TemThongTinTaiKhoanActivity extends AppCompatActivity {

    private static final String KEY_ACCOUNT_TYPE = QuanLyTaiKhoan_TaoTaiKhoanActivity.KEY_ACCOUNT_TYPE;
    private static final String KEY_ACCOUNT_CODE = QuanLyTaiKhoan_TaoTaiKhoanActivity.KEY_ACCOUNT_CODE;
    private static final String KEY_ACCOUNT_NAME = QuanLyTaiKhoan_TaoTaiKhoanActivity.KEY_ACCOUNT_NAME;
    private static final String KEY_ACCOUNT_EMAIL = QuanLyTaiKhoan_TaoTaiKhoanActivity.KEY_ACCOUNT_EMAIL;

    private EditText edtTenNV, edtMaNV, edtSDT, edtNgaySinh, edtDiaChi;
    private Spinner spinnerGioiTinh;
    private FloatingActionButton btnSave;
    private DatabaseHelper databaseHelper;

    private String email = "";
    private String ma = "";
    private String ten = "";
    private String loaiTaiKhoan = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_tai_khoan_tem_thong_tin_tai_khoan);

        try {
            databaseHelper = new DatabaseHelper(this);
            initViews();
            setupGenderSpinner();
            setupSaveButton();
            processIntentData();
        } catch (Exception e) {
            Log.e("ActivityError", "Lỗi khởi tạo Activity: " + e.getMessage(), e);
            Toast.makeText(this, "Lỗi khởi tạo ứng dụng", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initViews() {
        edtTenNV = findViewById(R.id.thongtintaikhoan_textbox_tennhanvien);
        edtMaNV = findViewById(R.id.thongtintaikhoan_textbox_manhanvien);
        edtSDT = findViewById(R.id.taotaikhoan_themthongtinnhanvien_textbox_sodienthoai);
        edtNgaySinh = findViewById(R.id.taotaikhoan_themthongtinnhanvien_textbox_ngaysinh);
        edtDiaChi = findViewById(R.id.taotaikhoan_themthongtinnhanvien_textbox_diachi);
        spinnerGioiTinh = findViewById(R.id.taotaikhoan_themthongtinnhanvien_spinner_gioitinh);
        btnSave = findViewById(R.id.taotaikhoan_themthongtinnhanvien_button_save);

        // Cho phép người dùng nhập ngày sinh bằng tay
        // Không cần setKeyListener(null) và không thiết lập DatePicker
    }

    private void processIntentData() {
        Intent intent = getIntent();
        if (intent == null) {
            Toast.makeText(this, "Không có dữ liệu từ Intent", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loaiTaiKhoan = intent.getStringExtra(KEY_ACCOUNT_TYPE) != null ? intent.getStringExtra(KEY_ACCOUNT_TYPE) : "";
        ma = intent.getStringExtra(KEY_ACCOUNT_CODE) != null ? intent.getStringExtra(KEY_ACCOUNT_CODE) : "";
        ten = intent.getStringExtra(KEY_ACCOUNT_NAME) != null ? intent.getStringExtra(KEY_ACCOUNT_NAME) : "";
        email = intent.getStringExtra(KEY_ACCOUNT_EMAIL) != null ? intent.getStringExtra(KEY_ACCOUNT_EMAIL) : "";

        edtMaNV.setText(ma);
        edtTenNV.setText(ten);
        edtMaNV.setEnabled(false);
        edtTenNV.setEnabled(false);
    }

    private void setupGenderSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.gender_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGioiTinh.setAdapter(adapter);
        spinnerGioiTinh.setSelection(0);
    }

    private void setupSaveButton() {
        btnSave.setOnClickListener(v -> {
            if (saveEmployeeInfo()) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private boolean saveEmployeeInfo() {
        String maNV = edtMaNV.getText().toString().trim();
        String tenNV = edtTenNV.getText().toString().trim();
        String sdt = edtSDT.getText().toString().trim();
        String ngaySinh = edtNgaySinh.getText().toString().trim();
        String diaChi = edtDiaChi.getText().toString().trim();
        String gioiTinh = spinnerGioiTinh.getSelectedItem() != null ?
                spinnerGioiTinh.getSelectedItem().toString() : "";

        if (!validateInput(maNV, tenNV, sdt, ngaySinh, diaChi)) {
            return false;
        }

        boolean isInserted = loaiTaiKhoan.equalsIgnoreCase("Bác Sĩ") ?
                databaseHelper.insertBacSi(maNV, tenNV, gioiTinh, sdt, ngaySinh, diaChi, email) :
                databaseHelper.insertNhanVien(maNV, tenNV, gioiTinh, sdt, ngaySinh, diaChi, email);

        if (isInserted) {
            Toast.makeText(this, "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Lưu thông tin thất bại", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean validateInput(String maNV, String tenNV, String sdt, String ngaySinh, String diaChi) {
        if (maNV.isEmpty() || tenNV.isEmpty() || sdt.isEmpty() || ngaySinh.isEmpty() || diaChi.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!sdt.matches("\\d{10,11}")) {
            Toast.makeText(this, "Số điện thoại phải có 10-11 chữ số", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!ngaySinh.matches("\\d{2}/\\d{2}/\\d{4}")) {
            Toast.makeText(this, "Ngày sinh phải có định dạng dd/MM/yyyy", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}
