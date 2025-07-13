package com.example.doanphongkham.ActivityBacsi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.R;

public class SuaThongtinBenhNhanActivity extends AppCompatActivity {

    EditText editHoTen, editSDT, editGioiTinh, editNgaySinh, editDiaChi, editTienSu;
    Button btnLuu;
    DatabaseHelper db;
    int id; // id bệnh nhân cần sửa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_thongtin_benh_nhan);

        // Ánh xạ
        editHoTen = findViewById(R.id.editHoTen);
        editSDT = findViewById(R.id.editSoDienThoai);
        editGioiTinh = findViewById(R.id.editGioiTinh);
        editNgaySinh = findViewById(R.id.editNgaySinh);
        editDiaChi = findViewById(R.id.editDiaChi);
        editTienSu = findViewById(R.id.editTienSuBenh);
        btnLuu = findViewById(R.id.btnLuu);

        db = new DatabaseHelper(this);

        // Nhận dữ liệu từ intent
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        String ten = intent.getStringExtra("ten");
        String sdt = intent.getStringExtra("sdt");
        String gioiTinh = intent.getStringExtra("gioiTinh");
        String ngaySinh = intent.getStringExtra("ngaySinh");
        String diaChi = intent.getStringExtra("diaChi");
        String tienSu = intent.getStringExtra("tienSu");

        // Set dữ liệu lên form
        editHoTen.setText(ten);
        editSDT.setText(sdt);
        editGioiTinh.setText(gioiTinh);
        editNgaySinh.setText(ngaySinh);
        editDiaChi.setText(diaChi);
        editTienSu.setText(tienSu);

        // Xử lý lưu
        btnLuu.setOnClickListener(v -> {
            boolean updated = db.updateKhachHang(
                    id,
                    editHoTen.getText().toString(),
                    editSDT.getText().toString(),
                    editGioiTinh.getText().toString(),
                    editNgaySinh.getText().toString(),
                    editDiaChi.getText().toString(),
                    editTienSu.getText().toString()
            );

            if (updated) {
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        //back
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            finish();
        });

    }
}
