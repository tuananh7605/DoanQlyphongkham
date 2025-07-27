package com.example.doanphongkham.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class QuanLyKhoa_SuaActivity extends AppCompatActivity {

    private EditText edtgiatienKhoa, edtTenKhoa, edtMoTa;
    private FloatingActionButton btnLuu, btnXoa;
    private DatabaseHelper dbHelper;
    private int maKhoaId; // Thay đổi từ String sang int để phù hợp với ID của Khoa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_khoa_sua);

        // Ánh xạ view
        edtTenKhoa = findViewById(R.id.themlophocphan_textbox_tenKhoa);
        edtgiatienKhoa = findViewById(R.id.themlophocphan_textbox_giatienKhoa);
        edtMoTa = findViewById(R.id.themKhoa_textbox_MoTa);
        btnLuu = findViewById(R.id.thongtinlophocphan_button_luu);
        btnXoa = findViewById(R.id.thongtinlophocphan_button_xoa);

        dbHelper = new DatabaseHelper(this);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        maKhoaId = intent.getIntExtra("MA_KHOA_ID", -1); // Lấy ID là int
        String tenKhoa = intent.getStringExtra("TEN_KHOA");
        String moTa = intent.getStringExtra("MO_TA");
        double giaTien = intent.getDoubleExtra("GIA_TIEN", 0.0); // Lấy giá tiền

        // Hiển thị dữ liệu lên EditText
        if (maKhoaId != -1) {
            edtTenKhoa.setText(tenKhoa);
            edtMoTa.setText(moTa);
            edtgiatienKhoa.setText(String.valueOf(giaTien)); // Chuyển double sang String
        } else {
            Toast.makeText(this, "Không tìm thấy thông tin khoa", Toast.LENGTH_SHORT).show();
            finish(); // Đóng activity nếu không có ID hợp lệ
        }

        // Xử lý sự kiện
        btnLuu.setOnClickListener(v -> luuThongTin());
        btnXoa.setOnClickListener(v -> xoaKhoa());
    }

    private void luuThongTin() {
        String tenKhoa = edtTenKhoa.getText().toString().trim();
        String moTa = edtMoTa.getText().toString().trim();
        double giaTien;

        if (tenKhoa.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên khoa", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            giaTien = Double.parseDouble(edtgiatienKhoa.getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Giá tiền không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gọi phương thức updateKhoa từ DatabaseHelper
        boolean result = dbHelper.updateKhoa(maKhoaId, tenKhoa, moTa, giaTien);

        if (result) {
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void xoaKhoa() {
        // Gọi phương thức deleteKhoa từ DatabaseHelper
        boolean result = dbHelper.deleteKhoa(maKhoaId);
        if (result) {
            Toast.makeText(this, "Xóa khoa thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Xóa khoa thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}
