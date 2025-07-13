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

import com.example.doanphongkham.R;

public class QuanLyNhanVienActivity extends AppCompatActivity {

    Button quanly_trangchinh_quanlytaikhoan_button_taotaikhoan,
            quanly_trangchinh_quanlytaikhoan_button_danhsachtaikhoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quan_ly_nhan_vien);
        quanlynhanvien_anhxa();
        //----------------------------------//
        quanly_trangchinh_quanlytaikhoan_button_taotaikhoan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyNhanVienActivity.this, QuanLyTaiKhoan_TaoTaiKhoanActivity.class);
                startActivity(intent);
                finish();
            }
        });

        quanly_trangchinh_quanlytaikhoan_button_danhsachtaikhoan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyNhanVienActivity.this, DanhSachTaiKhoanActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public void quanlynhanvien_anhxa ()
    {
        quanly_trangchinh_quanlytaikhoan_button_taotaikhoan = (Button) findViewById(R.id.quanly_trangchinh_quanlytaikhoan_button_taotaikhoan);
        quanly_trangchinh_quanlytaikhoan_button_danhsachtaikhoan = (Button) findViewById(R.id.quanly_trangchinh_quanlytaikhoan_button_danhsachtaikhoan);
    }
}