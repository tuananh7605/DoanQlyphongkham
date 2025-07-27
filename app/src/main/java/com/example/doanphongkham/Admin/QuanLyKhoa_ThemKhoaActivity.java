package com.example.doanphongkham.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.Model.Khoa;
import com.example.doanphongkham.R;

public class QuanLyKhoa_ThemKhoaActivity extends AppCompatActivity {
    private EditText edtTenKhoa, edtMoTa, edtGiaTien;
    private Button btnLuu, btnHuy;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_khoa_them_khoa);

        dbHelper = new DatabaseHelper(this);

        // Ánh xạ view
        edtTenKhoa = findViewById(R.id.themlophocphan_textbox_tenKhoa);
        edtMoTa = findViewById(R.id.themKhoa_textbox_MoTa);
        edtGiaTien = findViewById(R.id.themlophocphan_textbox_giatienKhoa);
        btnLuu = findViewById(R.id.themlophocphan_button_luu);
        btnHuy = findViewById(R.id.themlophocphan_button_huy);

        // Xử lý sự kiện
        btnHuy.setOnClickListener(v -> finish());

        btnLuu.setOnClickListener(v -> {
            try {
                String ten = edtTenKhoa.getText().toString().trim();
                String moTa = edtMoTa.getText().toString().trim();
                double giaTien = Double.parseDouble(edtGiaTien.getText().toString().trim());

                if (ten.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập tên khoa", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Sửa lỗi: Truyền các tham số riêng lẻ thay vì đối tượng Khoa
                boolean result = dbHelper.insertKhoa(ten, moTa, giaTien);

                if (result) { // Phương thức insertKhoa trả về boolean
                    Toast.makeText(this, "Thêm khoa thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(QuanLyKhoa_ThemKhoaActivity.this, QuanLyKhoaActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Giá tiền không hợp lệ", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
