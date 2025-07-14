package com.example.doanphongkham.ActivityBacsi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.doanphongkham.R;

public class KhamBenhActivity extends AppCompatActivity {
    TextView tvTenBN, tvNgayKham, tvGioKham, tvTienSuBenh, tvPhongKham;
    ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kham_benh);
        //anh xa
        tvTenBN = findViewById(R.id.tvTenBN);
        tvNgayKham = findViewById(R.id.tvNgayKham);
        tvGioKham = findViewById(R.id.tvGioKham);
        tvTienSuBenh = findViewById(R.id.tvTeinsubenh);
        tvPhongKham = findViewById(R.id.tvPhongKham);
        btnBack = findViewById(R.id.btnBackk);

        // Nhận dữ liệu từ intent
        String ten = getIntent().getStringExtra("ten");
        String ngay = getIntent().getStringExtra("ngay");
        String gio = getIntent().getStringExtra("gio");
        String tiensu = getIntent().getStringExtra("tiensu");
        String phong = getIntent().getStringExtra("phong");

        // Hiển thị lên TextView
        tvTenBN.setText("Bệnh nhân: " + ten);
        tvNgayKham.setText("Ngày khám: " + ngay);
        tvGioKham.setText("Giờ khám: " + gio);
        tvTienSuBenh.setText("Tiền sử bệnh: " + tiensu);
        tvPhongKham.setText("Phòng khám: " + phong);

        btnBack.setOnClickListener(v -> finish());





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}