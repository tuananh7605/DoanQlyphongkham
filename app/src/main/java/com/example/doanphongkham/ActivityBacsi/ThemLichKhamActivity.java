package com.example.doanphongkham.ActivityBacsi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.R;

public class ThemLichKhamActivity extends AppCompatActivity {

    EditText edtTenBenhNhan, edtNgayKham, edtGioKham, edtTienSuBenh, edtPhongKham;
    Button btnThemLichKham;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_lich_kham);

        // Ánh xạ view
        edtTenBenhNhan = findViewById(R.id.edtTenBenhNhan);
        edtNgayKham = findViewById(R.id.edtNgayKham);
        edtGioKham = findViewById(R.id.edtGioKham);
        edtTienSuBenh = findViewById(R.id.edtTienSuBenh);
        edtPhongKham = findViewById(R.id.edtPhongKham);
        btnThemLichKham = findViewById(R.id.btnThemLichKham);
        ImageButton btnBack = findViewById(R.id.btnBack);



        // nút back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Khởi tạo database
        databaseHelper = new DatabaseHelper(this);

        // Xử lý khi ấn nút
        btnThemLichKham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edtTenBenhNhan.getText().toString().trim();
                String ngay = edtNgayKham.getText().toString().trim();
                String gio = edtGioKham.getText().toString().trim();
                String tienSu = edtTienSuBenh.getText().toString().trim();
                String phong = edtPhongKham.getText().toString().trim();

                if (ten.isEmpty() || ngay.isEmpty() || gio.isEmpty()) {
                    Toast.makeText(ThemLichKhamActivity.this, "Vui lòng nhập đầy đủ tên, ngày và giờ khám!", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isInserted = databaseHelper.insertLichKham(ten, ngay, gio, tienSu, phong);
                if (isInserted) {
                    Toast.makeText(ThemLichKhamActivity.this, "Thêm lịch khám thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                    // Xóa form sau khi thêm
                    edtTenBenhNhan.setText("");
                    edtNgayKham.setText("");
                    edtGioKham.setText("");
                    edtTienSuBenh.setText("");
                    edtPhongKham.setText("");
                } else {
                    Toast.makeText(ThemLichKhamActivity.this, "Lỗi! Không thể thêm.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
