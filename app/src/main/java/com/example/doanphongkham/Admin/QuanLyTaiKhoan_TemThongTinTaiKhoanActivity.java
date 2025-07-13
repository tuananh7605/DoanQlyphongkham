package com.example.doanphongkham.Admin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class QuanLyTaiKhoan_TemThongTinTaiKhoanActivity extends AppCompatActivity {
    // Sử dụng cùng constants với Activity trước
    private static final String KEY_ACCOUNT_TYPE = QuanLyTaiKhoan_TaoTaiKhoanActivity.KEY_ACCOUNT_TYPE;
    private static final String KEY_ACCOUNT_CODE = QuanLyTaiKhoan_TaoTaiKhoanActivity.KEY_ACCOUNT_CODE;
    private static final String KEY_ACCOUNT_NAME = QuanLyTaiKhoan_TaoTaiKhoanActivity.KEY_ACCOUNT_NAME;
    private static final String KEY_ACCOUNT_EMAIL = QuanLyTaiKhoan_TaoTaiKhoanActivity.KEY_ACCOUNT_EMAIL;
    private static final String KEY_ACCOUNT_PASSWORD = QuanLyTaiKhoan_TaoTaiKhoanActivity.KEY_ACCOUNT_PASSWORD;

    private EditText edtTenNV, edtMaNV, edtSDT, edtNgaySinh, edtDiaChi;
    private Spinner spinnerGioiTinh;
    private FloatingActionButton btnSave;
    private Calendar calendar;
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
            // Khởi tạo các thành phần cơ bản
            databaseHelper = new DatabaseHelper(this);
            calendar = Calendar.getInstance();

            // Khởi tạo view và thiết lập các sự kiện
            initViews();
            setupDatePicker();
            setupGenderSpinner();
            setupSaveButton();

            // Xử lý dữ liệu từ Intent
            processIntentData();

        } catch (Exception e) {
            Log.e("ActivityError", "Lỗi khởi tạo Activity: " + e.getMessage(), e);
            Toast.makeText(this, "Lỗi khởi tạo ứng dụng", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initViews() {
        try {
            edtTenNV = findViewById(R.id.thongtintaikhoan_textbox_tennhanvien);
            edtMaNV = findViewById(R.id.thongtintaikhoan_textbox_manhanvien);
            edtSDT = findViewById(R.id.taotaikhoan_themthongtinnhanvien_textbox_sodienthoai);
            edtNgaySinh = findViewById(R.id.taotaikhoan_themthongtinnhanvien_textbox_ngaysinh);
            edtDiaChi = findViewById(R.id.taotaikhoan_themthongtinnhanvien_textbox_diachi);
            spinnerGioiTinh = findViewById(R.id.taotaikhoan_themthongtinnhanvien_spinner_gioitinh);
            btnSave = findViewById(R.id.taotaikhoan_themthongtinnhanvien_button_save);

            // Vô hiệu hóa bàn phím cho trường ngày sinh
            edtNgaySinh.setKeyListener(null);

            // Kiểm tra null cho các view quan trọng
            if (edtMaNV == null || edtTenNV == null || edtNgaySinh == null || spinnerGioiTinh == null) {
                throw new IllegalStateException("Một hoặc nhiều view không được tìm thấy");
            }
        } catch (Exception e) {
            Log.e("ViewInitError", "Lỗi khởi tạo view: " + e.getMessage());
            throw e;
        }
    }

    private void processIntentData() {
        try {
            Intent intent = getIntent();
            if (intent == null) {
                Toast.makeText(this, "Không có dữ liệu từ Intent", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            // Lấy dữ liệu từ Intent với giá trị mặc định
            loaiTaiKhoan = intent.getStringExtra(KEY_ACCOUNT_TYPE) != null ?
                    intent.getStringExtra(KEY_ACCOUNT_TYPE) : "";
            ma = intent.getStringExtra(KEY_ACCOUNT_CODE) != null ?
                    intent.getStringExtra(KEY_ACCOUNT_CODE) : "";
            ten = intent.getStringExtra(KEY_ACCOUNT_NAME) != null ?
                    intent.getStringExtra(KEY_ACCOUNT_NAME) : "";
            email = intent.getStringExtra(KEY_ACCOUNT_EMAIL) != null ?
                    intent.getStringExtra(KEY_ACCOUNT_EMAIL) : "";

            // Hiển thị dữ liệu lên các trường
            edtMaNV.setText(ma);
            edtTenNV.setText(ten);

            // Disable các trường mã và tên nếu cần
            edtMaNV.setEnabled(false);
            edtTenNV.setEnabled(false);

        } catch (Exception e) {
            Log.e("IntentError", "Lỗi xử lý Intent: " + e.getMessage());
            Toast.makeText(this, "Lỗi dữ liệu", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setupDatePicker() {
        try {
            DatePickerDialog.OnDateSetListener dateListener = (view, year, month, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            };

            edtNgaySinh.setOnClickListener(v -> {
                try {
                    new DatePickerDialog(
                            this,
                            dateListener,
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                    ).show();
                } catch (Exception e) {
                    Log.e("DatePickerError", "Lỗi hiển thị DatePicker: " + e.getMessage());
                    Toast.makeText(this, "Lỗi hiển thị lịch", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e("DateSetupError", "Lỗi thiết lập DatePicker: " + e.getMessage());
            Toast.makeText(this, "Lỗi thiết lập ngày sinh", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateDateLabel() {
        try {
            String dateFormat = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
            edtNgaySinh.setText(sdf.format(calendar.getTime()));
        } catch (Exception e) {
            Log.e("DateError", "Lỗi cập nhật ngày: " + e.getMessage());
        }
    }

    private void setupGenderSpinner() {
        try {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.gender_array,
                    android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerGioiTinh.setAdapter(adapter);

            // Thiết lập giá trị mặc định nếu cần
            spinnerGioiTinh.setSelection(0);
        } catch (Exception e) {
            Log.e("SpinnerError", "Lỗi thiết lập giới tính: " + e.getMessage());
            Toast.makeText(this, "Lỗi thiết lập giới tính", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupSaveButton() {
        btnSave.setOnClickListener(v -> {
            try {
                if (saveEmployeeInfo()) {
                    // Quay lại màn hình trước đó nếu lưu thành công
                    setResult(RESULT_OK);
                    finish();
                }
            } catch (Exception e) {
                Log.e("SaveError", "Lỗi khi lưu thông tin: " + e.getMessage());
                Toast.makeText(this, "Lỗi khi lưu thông tin", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean saveEmployeeInfo() {
        try {
            String maNV = edtMaNV.getText().toString().trim();
            String tenNV = edtTenNV.getText().toString().trim();
            String sdt = edtSDT.getText().toString().trim();
            String ngaySinh = edtNgaySinh.getText().toString().trim();
            String diaChi = edtDiaChi.getText().toString().trim();
            String gioiTinh = spinnerGioiTinh.getSelectedItem() != null ?
                    spinnerGioiTinh.getSelectedItem().toString() : "";

            // Validate dữ liệu
            if (!validateInput(maNV, tenNV, sdt, ngaySinh, diaChi)) {
                return false;
            }

            boolean isInserted;
            if (loaiTaiKhoan.equalsIgnoreCase("Bác Sĩ")) {
                isInserted = databaseHelper.insertBacSi(
                        maNV, tenNV, gioiTinh, sdt, ngaySinh, diaChi, email);
            } else {
                isInserted = databaseHelper.insertNhanVien(
                        maNV, tenNV, gioiTinh, sdt, ngaySinh, diaChi, email);
            }

            if (isInserted) {
                Toast.makeText(this, "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(this, "Lưu thông tin thất bại", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            Log.e("SaveError", "Lỗi khi lưu thông tin: " + e.getMessage());
            Toast.makeText(this, "Lỗi hệ thống khi lưu thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean validateInput(String maNV, String tenNV, String sdt,
                                  String ngaySinh, String diaChi) {
        if (maNV.isEmpty() || tenNV.isEmpty() || sdt.isEmpty() ||
                ngaySinh.isEmpty() || diaChi.isEmpty()) {
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
        try {
            if (databaseHelper != null) {
                databaseHelper.close();
            }
        } catch (Exception e) {
            Log.e("ActivityError", "Lỗi khi đóng database: " + e.getMessage());
        }
    }
}