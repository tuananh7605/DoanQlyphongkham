package com.example.doanphongkham.KeToan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.doanphongkham.R;

public class MainKeToanActivity extends AppCompatActivity {
    Button btnHoaDon, btnDoanhThu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_ke_toan);


        btnHoaDon = findViewById(R.id.btnHoaDon);
        btnDoanhThu = findViewById(R.id.btnDoanhThu);

        btnHoaDon.setOnClickListener(v -> {
            startActivity(new Intent(MainKeToanActivity.this, DanhSachHoaDonActivity.class));
        });

        btnDoanhThu.setOnClickListener(v -> {
            startActivity(new Intent(MainKeToanActivity.this, DoanhThuActivity.class));
        });

    }
}