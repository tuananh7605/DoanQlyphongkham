package com.example.doanphongkham.KeToan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanphongkham.Adapter.DaKhamXongAdapter;
import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.Model.DaKhamXong;
import com.example.doanphongkham.R;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;
import java.util.List;

public class DoanhThuActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView tvTongDoanhThu, tvChonNgay;
    MaterialButton btnBack;
    DatabaseHelper db;
    List<DaKhamXong> hoaDonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu);

        recyclerView = findViewById(R.id.recyclerViewDoanhThu);
        tvTongDoanhThu = findViewById(R.id.tvTongDoanhThu);
        tvChonNgay = findViewById(R.id.tvChonNgay);
        btnBack = findViewById(R.id.btnBack);

        db = new DatabaseHelper(this);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(DoanhThuActivity.this, MainKeToanActivity.class);
            startActivity(intent);
            finish();
        });

        String today = getTodayDate();
        tvChonNgay.setText("Ngày đã chọn: " + today);
        loadHoaDonTheoNgay(today);

        tvChonNgay.setOnClickListener(v -> showDatePicker());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(this, (view, y, m, d) -> {
            String selectedDate = String.format("%04d/%02d/%02d", y, m + 1, d);
            tvChonNgay.setText("Ngày đã chọn: " + selectedDate);
            loadHoaDonTheoNgay(selectedDate);
        }, year, month, day);

        datePicker.show();
    }

    private void loadHoaDonTheoNgay(String ngay) {
        hoaDonList = db.getDaKhamXongTheoNgay(ngay);

        double tong = 0;
        for (DaKhamXong hd : hoaDonList) {
            tong += hd.getTongTien();
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new DaKhamXongAdapter(this, hoaDonList));
        tvTongDoanhThu.setText("Tổng doanh thu ngày " + ngay + ": " + tong + " VNĐ");
    }

    private String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        return String.format("%04d/%02d/%02d",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
    }
}
