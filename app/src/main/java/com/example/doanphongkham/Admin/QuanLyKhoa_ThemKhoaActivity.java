package com.example.doanphongkham.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.Model.Khoa;
import com.example.doanphongkham.R;

import java.util.List;

public class QuanLyKhoa_ThemKhoaActivity extends AppCompatActivity {

    private EditText edtMaKhoa, edtTenKhoa, edtMoTa;
    private Spinner spinnerBacSi;
    private Button btnLuu, btnHuy;
    private DatabaseHelper dbHelper;
    private boolean isEditMode = false;
    private String currentMaKhoa = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_khoa_them_khoa);

        // Ánh xạ view
        edtMaKhoa = findViewById(R.id.themlophocphan_textbox_maKhoa);
        edtTenKhoa = findViewById(R.id.themlophocphan_textbox_tenKhoa);
        edtMoTa = findViewById(R.id.themKhoa_textbox_MoTa);
        spinnerBacSi = findViewById(R.id.themlophocphan_spinner_giangvien);
        btnLuu = findViewById(R.id.themlophocphan_button_luu);
        btnHuy = findViewById(R.id.themlophocphan_button_huy);

        dbHelper = new DatabaseHelper(this);

        // Kiểm tra có phải chế độ chỉnh sửa không
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("MAKHOA")) {
            isEditMode = true;
            currentMaKhoa = extras.getString("MAKHOA");
            loadKhoaInfo(currentMaKhoa);
        } else {
            initNewKhoa();
        }

        // Xử lý sự kiện
        btnLuu.setOnClickListener(v -> saveKhoa());
        btnHuy.setOnClickListener(v -> finish());
    }

    private void initNewKhoa() {
        // Tạo mã khoa tự động
        String maKhoa = dbHelper.generateMaKhoa();
        edtMaKhoa.setText(maKhoa);
        edtMaKhoa.setEnabled(false);

        // Load danh sách bác sĩ vào spinner
        loadBacSiSpinner(null);
    }

    private void loadKhoaInfo(String maKhoa) {
        Khoa khoa = dbHelper.getKhoaByMa(maKhoa);
        if (khoa != null) {
            edtMaKhoa.setText(khoa.getMaKhoa());
            edtMaKhoa.setEnabled(false);
            edtTenKhoa.setText(khoa.getTenKhoa());
            edtMoTa.setText(khoa.getMoTa());

            // Load spinner và chọn bác sĩ hiện tại
            loadBacSiSpinner(khoa.getMaBS());
        }
    }

    private void loadBacSiSpinner(String selectedMaBS) {
        List<DatabaseHelper.BacSiSpinner> listBacSi = dbHelper.getAllBacSiForSpinner();

        ArrayAdapter<DatabaseHelper.BacSiSpinner> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, listBacSi);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBacSi.setAdapter(adapter);

        // Chọn bác sĩ nếu có
        if (selectedMaBS != null && !selectedMaBS.isEmpty()) {
            for (int i = 0; i < listBacSi.size(); i++) {
                if (listBacSi.get(i).getMaBS().equals(selectedMaBS)) {
                    spinnerBacSi.setSelection(i);
                    break;
                }
            }
        }
    }

    private void saveKhoa() {
        String maKhoa = edtMaKhoa.getText().toString().trim();
        String tenKhoa = edtTenKhoa.getText().toString().trim();
        String moTa = edtMoTa.getText().toString().trim();

        // Validate dữ liệu
        if (tenKhoa.isEmpty()) {
            edtTenKhoa.setError("Vui lòng nhập tên khoa");
            edtTenKhoa.requestFocus();
            return;
        }

        // Lấy mã bác sĩ từ spinner
        DatabaseHelper.BacSiSpinner selectedBacSi = (DatabaseHelper.BacSiSpinner) spinnerBacSi.getSelectedItem();
        String maBS = selectedBacSi != null ? selectedBacSi.getMaBS() : null;

        boolean result;
        if (isEditMode) {
            // Cập nhật khoa
            result = dbHelper.updateKhoa(maKhoa, tenKhoa, maBS, moTa);
        } else {
            // Thêm khoa mới
            result = dbHelper.insertKhoa(maKhoa, tenKhoa, maBS, moTa);
        }

        if (result) {
            Toast.makeText(this,
                    isEditMode ? "Cập nhật khoa thành công" : "Thêm khoa thành công",
                    Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this,
                    isEditMode ? "Cập nhật khoa thất bại" : "Thêm khoa thất bại",
                    Toast.LENGTH_SHORT).show();
        }
    }

}