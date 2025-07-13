package com.example.doanphongkham.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class QuanLyKhoa_SuaActivity extends AppCompatActivity {

    private EditText edtMaKhoa, edtTenKhoa, edtMoTa;
    private Spinner spinnerBacSi;
    private FloatingActionButton btnLuu, btnXoa;
    private DatabaseHelper dbHelper;
    private String maKhoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_khoa_sua);

        // Ánh xạ view
        edtMaKhoa = findViewById(R.id.themlophocphan_textbox_maKhoa);
        edtTenKhoa = findViewById(R.id.themlophocphan_textbox_tenKhoa);
        spinnerBacSi = findViewById(R.id.themlophocphan_spinner_giangvien);
        edtMoTa = findViewById(R.id.themKhoa_textbox_MoTa);
        btnLuu = findViewById(R.id.thongtinlophocphan_button_luu);
        btnXoa = findViewById(R.id.thongtinlophocphan_button_xoa);

        dbHelper = new DatabaseHelper(this);

        // Load danh sách bác sĩ vào Spinner
        loadDanhSachBacSi();

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        maKhoa = intent.getStringExtra("MA_KHOA");
        edtMaKhoa.setText(maKhoa);
        edtTenKhoa.setText(intent.getStringExtra("TEN_KHOA"));
        edtMoTa.setText(intent.getStringExtra("MO_TA"));

        // Chọn bác sĩ hiện tại trong Spinner
        String currentMaBS = intent.getStringExtra("MA_BS");
        if (currentMaBS != null) {
            setSpinnerSelection(spinnerBacSi, currentMaBS);
        }

        // Xử lý sự kiện
        btnLuu.setOnClickListener(v -> luuThongTin());
        btnXoa.setOnClickListener(v -> xoaKhoa());
    }

    private void loadDanhSachBacSi() {
        List<DatabaseHelper.BacSiSpinner> danhSachBacSi = dbHelper.getAllBacSiForSpinner();
        ArrayAdapter<DatabaseHelper.BacSiSpinner> adapter = new ArrayAdapter<DatabaseHelper.BacSiSpinner>(
                this,
                android.R.layout.simple_spinner_item,
                danhSachBacSi);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBacSi.setAdapter(adapter);
    }

    private void setSpinnerSelection(Spinner spinner, String maBS) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<DatabaseHelper.BacSiSpinner> adapter = (ArrayAdapter<DatabaseHelper.BacSiSpinner>) spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            DatabaseHelper.BacSiSpinner item = adapter.getItem(i);
            if (item != null && item.getMaBS().equals(maBS)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private void luuThongTin() {
        String tenKhoa = edtTenKhoa.getText().toString().trim();

        @SuppressWarnings("unchecked")
        DatabaseHelper.BacSiSpinner selectedBacSi = (DatabaseHelper.BacSiSpinner) spinnerBacSi.getSelectedItem();
        String maBS = selectedBacSi != null ? selectedBacSi.getMaBS() : null;
        String moTa = edtMoTa.getText().toString().trim();

        if (tenKhoa.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên khoa", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean result = dbHelper.updateKhoa(
                maKhoa,
                tenKhoa,
                maBS,
                moTa
        );

        if (result) {
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void xoaKhoa() {
        boolean result = dbHelper.deleteKhoa(maKhoa);
        if (result) {
            Toast.makeText(this, "Xóa khoa thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Xóa khoa thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}