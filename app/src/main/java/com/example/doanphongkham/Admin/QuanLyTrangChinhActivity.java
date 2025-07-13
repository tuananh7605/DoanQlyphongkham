package com.example.doanphongkham.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.doanphongkham.LoginActivity;
import com.example.doanphongkham.R;

public class QuanLyTrangChinhActivity extends AppCompatActivity {
    Button quanly_trangchinh_button_quanlytaikhoan, quanly_trangchinh_button_quanlyphongkham,quanly_trangchinh_button_dangxuat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_trang_chinh);
        quanlytrangchinh_anh_xa();
        quanly_trangchinh_button_quanlytaikhoan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyTrangChinhActivity.this, QuanLyNhanVienActivity.class);
                startActivity(intent);
            }
        });

        quanly_trangchinh_button_quanlyphongkham.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyTrangChinhActivity.this, QuanLyKhoaActivity.class);
                startActivity(intent);
            }
        });
        quanly_trangchinh_button_dangxuat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        //----------------------------------------------------//
    }
    public void quanlytrangchinh_anh_xa ()
    {
        quanly_trangchinh_button_quanlytaikhoan = (Button) findViewById(R.id.quanly_trangchinh_button_quanlytaikhoan);
        quanly_trangchinh_button_quanlyphongkham=(Button) findViewById(R.id.quanly_trangchinh_button_quanlyKhoa);
        quanly_trangchinh_button_dangxuat=(Button) findViewById(R.id.quanly_trangchinh_button_dangxuat);

    }
}