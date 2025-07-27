package com.example.doanphongkham.KeToan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanphongkham.Adapter.DaKhamXongAdapter;
import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.Model.DaKhamXong;
import com.example.doanphongkham.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DanhSachHoaDonActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private DaKhamXongAdapter adapter1;
    private List<DaKhamXong> hoaDonList;
    private DatabaseHelper dbHelper;
    HoaDonAdapter adapter;
    Database db;
    FloatingActionButton btnThem;
    MaterialButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_hoa_don);

        recyclerView = findViewById(R.id.recyclerViewHoaDon);
        //dakhamxong


        btnThem = findViewById(R.id.btnThemHoaDon);
        btnBack = findViewById(R.id.btnBack);
        db = new Database(this);
        dbHelper = new DatabaseHelper(this);


        btnThem.setOnClickListener(view -> {
            Intent intent = new Intent(DanhSachHoaDonActivity.this, CreateInvoiceActivity.class);
            startActivity(intent);
        });
        //ẩn btn them
        FloatingActionButton btnThemHoaDon = findViewById(R.id.btnThemHoaDon);
        btnThemHoaDon.setVisibility(View.GONE); // Ẩn nút "Thêm"



        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(DanhSachHoaDonActivity.this, MainKeToanActivity.class);
            startActivity(intent);
            finish();
        });

        loadData();
    }


    private void loadData() {
        hoaDonList = dbHelper.getAllDaKhamXongList();
        adapter1 = new DaKhamXongAdapter(this, hoaDonList);

        List<HoaDon> list = db.getAllHoaDon();
        adapter = new HoaDonAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter1);
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}
