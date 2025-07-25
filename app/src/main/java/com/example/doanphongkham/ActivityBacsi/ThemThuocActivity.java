package com.example.doanphongkham.ActivityBacsi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.R;

public class ThemThuocActivity extends AppCompatActivity {

    EditText edtTenThuoc, edtMoTaThuoc, edtGiaTienThuoc;
    Button btnLuuThuoc;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_thuoc);

        edtTenThuoc = findViewById(R.id.edtTenThuoc);
        edtMoTaThuoc = findViewById(R.id.edtMoTaThuoc);
        edtGiaTienThuoc = findViewById(R.id.edtGiaTienThuoc);
        btnLuuThuoc = findViewById(R.id.btnLuuThuoc);
        db = new DatabaseHelper(this);

        btnLuuThuoc.setOnClickListener(v -> {
            String ten = edtTenThuoc.getText().toString().trim();
            String moTa = edtMoTaThuoc.getText().toString().trim();
            String giaStr = edtGiaTienThuoc.getText().toString().trim();

            if (ten.isEmpty() || giaStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            double gia;
            try {
                gia = Double.parseDouble(giaStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Giá tiền không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean result = db.insertThuoc(ten, moTa, gia);
            if (result) {
                Toast.makeText(this, "Thêm thuốc thành công", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
