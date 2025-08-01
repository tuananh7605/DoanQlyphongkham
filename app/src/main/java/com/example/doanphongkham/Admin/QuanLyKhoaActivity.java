package com.example.doanphongkham.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanphongkham.Adapter.KhoaAdapter;
import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.Model.Khoa; // Import lớp Khoa
import com.example.doanphongkham.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class QuanLyKhoaActivity extends AppCompatActivity {

    private EditText edtTimKiem;
    private Button btnTim;
    private ListView lvKhoa;
    private FloatingActionButton btnThem;
    private DatabaseHelper dbHelper;
    private KhoaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_khoa);

        // Initialize views
        edtTimKiem = findViewById(R.id.quanly_trangchinh_quanlyKhoa_textbox_tim);
        btnTim = findViewById(R.id.quanly_trangchinh_quanlykhoa_button_tim);
        lvKhoa = findViewById(R.id.quanly_trangchinh_quanlyKhoa_listview_Khoa);
        btnThem = findViewById(R.id.quanly_trangchinh_quanlyKhoa_button_them);

        dbHelper = new DatabaseHelper(this);

        // Load data
        loadDanhSachKhoa();

        // Set up listeners
        btnTim.setOnClickListener(v -> timKiemKhoa());
        btnThem.setOnClickListener(v -> {
            Intent intent = new Intent(QuanLyKhoaActivity.this, QuanLyKhoa_ThemKhoaActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDanhSachKhoa(); // Tải lại danh sách khi quay lại activity
    }

    private void loadDanhSachKhoa() {
        List<Khoa> listKhoa = dbHelper.getAllKhoa(); // Lấy List<Khoa>
        if (adapter == null) {
            adapter = new KhoaAdapter(this, listKhoa);
            lvKhoa.setAdapter(adapter);
            setupItemClickListener();
        } else {
            adapter.updateData(listKhoa); // Sử dụng updateData thay vì tạo adapter mới
        }
    }

    private void setupItemClickListener() {
        lvKhoa.setOnItemClickListener((parent, view, position, id) -> {
            Khoa khoa = (Khoa) parent.getItemAtPosition(position); // Ép kiểu về Khoa
            Intent intent = new Intent(QuanLyKhoaActivity.this, QuanLyKhoa_SuaActivity.class);
            intent.putExtra("MA_KHOA_ID", khoa.getId()); // Truyền ID là int
            intent.putExtra("TEN_KHOA", khoa.getTenKhoa());
            intent.putExtra("MO_TA", khoa.getMoTa());
            intent.putExtra("GIA_TIEN", khoa.getGiaTien()); // Truyền giá tiền
            startActivity(intent);
        });
    }

    private void timKiemKhoa() {
        String keyword = edtTimKiem.getText().toString().trim();
        List<Khoa> ketQua;
        if (keyword.isEmpty()) {
            ketQua = dbHelper.getAllKhoa(); // Nếu rỗng thì hiển thị tất cả
        } else {
            ketQua = dbHelper.timKiemKhoa(keyword); // Tìm kiếm theo từ khóa
        }

        if (ketQua.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy kết quả", Toast.LENGTH_SHORT).show();
            adapter.updateData(new ArrayList<>()); // Xóa dữ liệu cũ trên ListView
        } else {
            adapter.updateData(ketQua);
        }
    }
}
