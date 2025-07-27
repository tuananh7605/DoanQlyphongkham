package com.example.doanphongkham.Admin;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class QuanLyTaiKhoan_TaoTaiKhoanActivity extends AppCompatActivity {
    // Constants for intent keys
    public static final String KEY_ACCOUNT_TYPE = "loai_TK";
    public static final String KEY_ACCOUNT_CODE = "ma";
    public static final String KEY_ACCOUNT_NAME = "ten";
    public static final String KEY_ACCOUNT_EMAIL = "mail";
    public static final String KEY_ACCOUNT_PASSWORD = "matkhau";

    private DatabaseHelper dbHelper;
    private Spinner taotaikhoan_spinner_loaitaikhoan;
    private EditText taotaikhoan_textbox_tentaikhoan, taotaikhoan_textbox_mataikhoan,
            taotaikhoan_textbox_matkhau, taotaikhoan_textbox_mail;
    private Button taotaikhoan_button_taomail;
    private FloatingActionButton taotaikhoan_button_next;

    private final String[] ds_loai = {"Chưa chọn", "Bác Sĩ", "Nhân Viên", "Kế Toán"};
    private final ArrayList<String> maList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_tai_khoan_tao_tai_khoan);
        dbHelper = new DatabaseHelper(this);
        initViews();
        setupSpinner();
        setupListeners();
    }

    private void initViews() {
        taotaikhoan_spinner_loaitaikhoan = findViewById(R.id.taotaikhoan_spinner_loaitaikhoan);
        taotaikhoan_textbox_tentaikhoan = findViewById(R.id.taotaikhoan_textbox_tentaikhoan);
        taotaikhoan_textbox_mataikhoan = findViewById(R.id.taotaikhoan_textbox_mataikhoan);
        taotaikhoan_textbox_matkhau = findViewById(R.id.taotaikhoan_textbox_matkhau);
        taotaikhoan_textbox_mail = findViewById(R.id.taotaikhoan_textbox_mail);
        taotaikhoan_button_taomail = findViewById(R.id.taotaikhoan_button_taomail);
        taotaikhoan_button_next = findViewById(R.id.taotaikhoan_button_next);
    }

    private void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, ds_loai);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taotaikhoan_spinner_loaitaikhoan.setAdapter(adapter);
        taotaikhoan_spinner_loaitaikhoan.setSelection(0);

        taotaikhoan_spinner_loaitaikhoan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resetFormFields();
                String selectedType = taotaikhoan_spinner_loaitaikhoan.getSelectedItem().toString();

                if (selectedType.equals("Nhân Viên") || selectedType.equals("Bác Sĩ")|| selectedType.equals("Kế Toán")) {
                    taotaikhoan_textbox_mataikhoan.setText(generateAccountCode(selectedType));
                    taotaikhoan_button_next.setImageResource(R.drawable.baseline_navigate_next_24);
                    taotaikhoan_textbox_mataikhoan.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupListeners() {
        View.OnFocusChangeListener focusListener = (v, hasFocus) -> {
            if (hasFocus) {
                taotaikhoan_textbox_mail.setText("");
            }
        };
        taotaikhoan_textbox_tentaikhoan.setOnFocusChangeListener(focusListener);
        taotaikhoan_textbox_mataikhoan.setOnFocusChangeListener(focusListener);

        taotaikhoan_button_taomail.setOnClickListener(v -> {
            if (validateBasicInfo()) {
                generateEmail();
            }
        });

        taotaikhoan_button_next.setOnClickListener(v -> {
            if (validateForm()) {
                processAccountCreation();
            }
        });
    }

    private boolean validateBasicInfo() {
        String loai = taotaikhoan_spinner_loaitaikhoan.getSelectedItem().toString();
        String ten = taotaikhoan_textbox_tentaikhoan.getText().toString().trim();
        String ma = taotaikhoan_textbox_mataikhoan.getText().toString().trim();

        if (loai.equals("Chưa chọn")) {
            showToast("Chưa chọn loại tài khoản");
            return false;
        } else if (ten.isEmpty()) {
            showToast("Chưa nhập Họ Tên đầy đủ");
            return false;
        } else if (ma.isEmpty()) {
            showToast("Chưa nhập mã số");
            return false;
        } else if (ma.length() != 7) {
            showToast("Mã số phải có độ dài bằng 7");
            return false;
        }
        return true;
    }

    private boolean validateForm() {
        String loai = taotaikhoan_spinner_loaitaikhoan.getSelectedItem().toString();
        String ten = taotaikhoan_textbox_tentaikhoan.getText().toString().trim();
        String ma = taotaikhoan_textbox_mataikhoan.getText().toString().trim();
        String mk = taotaikhoan_textbox_matkhau.getText().toString().trim();
        String mail = taotaikhoan_textbox_mail.getText().toString().trim();

        if (!validateBasicInfo()) return false;

        if (mk.isEmpty()) {
            showToast("Chưa nhập mật khẩu");
            return false;
        } else if (mail.isEmpty()) {
            showToast("Chưa nhập mail");
            return false;
        }
        return true;
    }

    private void generateEmail() {
        String loai = taotaikhoan_spinner_loaitaikhoan.getSelectedItem().toString();
        String ten = VNCharacterUtils.removeAccent(taotaikhoan_textbox_tentaikhoan.getText().toString())
                .replaceAll(" ", "");
        String ma = taotaikhoan_textbox_mataikhoan.getText().toString();

        if (loai.equals("Nhân Viên")) {
            taotaikhoan_textbox_mail.setText(ten + ma + "@nv.gmail.com");
        } else if (loai.equals("Bác Sĩ")) {
            taotaikhoan_textbox_mail.setText(ten + ma + "@bs.gmail.com");
        }else if (loai.equals("Kế Toán")) {
            taotaikhoan_textbox_mail.setText(ten + ma + "@kt.gmail.com");
        }
    }

    private String generateAccountCode(String accountType) {
        String prefix = accountType.equals("Bác Sĩ") ? "BS" : accountType.equals("Kế Toán") ? "KT" : "NV";
        String columnName = accountType.equals("Bác Sĩ") ? "maBS" : accountType.equals("Kế Toán") ? "maKT" : "maNV";
        String tableName = accountType.equals("Bác Sĩ") ? "BacSi" : accountType.equals("Kế Toán") ? "KeToan" : "NhanVien";
        int maxNumber = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + columnName + " FROM " + tableName, null);
        while (cursor.moveToNext()) {
            String existingCode = cursor.getString(0);
            if (existingCode.startsWith(prefix)) {
                try {
                    int currentNum = Integer.parseInt(existingCode.substring(prefix.length()));
                    if (currentNum > maxNumber) maxNumber = currentNum;
                } catch (NumberFormatException ignored) {}
            }
        }
        cursor.close();
        db.close();
        return prefix + String.format("%05d", maxNumber + 1);
    }
    private void processAccountCreation() {
        String loai = taotaikhoan_spinner_loaitaikhoan.getSelectedItem().toString();
        String ten = taotaikhoan_textbox_tentaikhoan.getText().toString().trim();
        String ma = taotaikhoan_textbox_mataikhoan.getText().toString().trim();
        String mk = taotaikhoan_textbox_matkhau.getText().toString().trim();
        String mail = taotaikhoan_textbox_mail.getText().toString().trim();

        if (loai.equals("Nhân Viên") && !isNumeric(ma.substring(2))) {
            showToast("Mã nhân viên phải là ký tự số");
            return;
        }

        if (isCodeExists(ma, loai)) {
            showToast("Mã " + loai.toLowerCase() + " đã tồn tại");
            return;
        }

        if (!dbHelper.insertTaiKhoan(ma,ten, hashMD5(mk), loai, mail, "Active")) {
            showToast("Lỗi khi tạo tài khoản");
            return;
        }

        navigateToDetailActivity(loai, ma, ten, mail, mk);
    }

    private boolean isCodeExists(String code, String accountType) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String tableName = accountType.equals("Bác Sĩ") ? "BacSi" : accountType.equals("Kế Toán") ? "KeToan" : "NhanVien";
        String columnName = accountType.equals("Bác Sĩ") ? "maBS" : accountType.equals("Kế Toán") ? "maKT" : "maNV";
        Cursor cursor = db.rawQuery("SELECT " + columnName + " FROM " + tableName + " WHERE " + columnName + " = ?",
                new String[]{code});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }
    private void navigateToDetailActivity(String accountType, String code, String name, String email, String password) {
        // Thêm kiểm tra null trước khi truyền dữ liệu
        if (code == null || name == null || email == null || password == null) {
            Toast.makeText(this, "Dữ liệu không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, QuanLyTaiKhoan_TemThongTinTaiKhoanActivity.class);
        intent.putExtra(KEY_ACCOUNT_TYPE, accountType != null ? accountType : "");
        intent.putExtra(KEY_ACCOUNT_CODE, code);
        intent.putExtra(KEY_ACCOUNT_NAME, name);
        intent.putExtra(KEY_ACCOUNT_EMAIL, email);
        intent.putExtra(KEY_ACCOUNT_PASSWORD, password);
        startActivity(intent);
        finish();
    }

    private void resetFormFields() {
        taotaikhoan_textbox_mail.setText("");
        taotaikhoan_textbox_tentaikhoan.setText("");
        taotaikhoan_textbox_mataikhoan.setText("");
        taotaikhoan_textbox_matkhau.setText("");
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String hashMD5(String input) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}