package com.example.doanphongkham.ActivityBacsi;

import android.os.Bundle;
import android.view.View;
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

public class ThemThongtinBenhNhanActivity extends AppCompatActivity {

    ImageButton btnback;
    Button btnLuu;
    EditText editHoTen, editSoDienThoai, editGioiTinh, editNgaySinh, editDiaChi, editTienSuBenh;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_them_thongtin_benh_nhan);


        // Ánh xạ view
        editHoTen = findViewById(R.id.editHoTen);
        editSoDienThoai = findViewById(R.id.editSoDienThoai);
        editGioiTinh = findViewById(R.id.editGioiTinh);
        editNgaySinh = findViewById(R.id.editNgaySinh);
        editDiaChi = findViewById(R.id.editDiaChi);
        editTienSuBenh = findViewById(R.id.editTienSuBenh);
        btnLuu = findViewById(R.id.btnLuu);
        btnback = findViewById(R.id.btnBack);






         //end anh xa
        // Khởi tạo database
        db = new DatabaseHelper(this);
         //nut back
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Xử lý nút lưu
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTen = editHoTen.getText().toString().trim();
                String sdt = editSoDienThoai.getText().toString().trim();
                String gioiTinh = editGioiTinh.getText().toString().trim();
                String ngaySinh = editNgaySinh.getText().toString().trim();
                String diaChi = editDiaChi.getText().toString().trim();
                String tienSu = editTienSuBenh.getText().toString().trim();

                if (hoTen.isEmpty()) {
                    Toast.makeText(ThemThongtinBenhNhanActivity.this, "Vui lòng nhập họ tên", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean result = db.insertKhachHang(hoTen, sdt, gioiTinh, ngaySinh, diaChi, tienSu);
                if (result) {
                    Toast.makeText(ThemThongtinBenhNhanActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ThemThongtinBenhNhanActivity.this, "Lưu thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });







        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}