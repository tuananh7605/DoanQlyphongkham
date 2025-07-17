package com.example.doanphongkham.KeToan;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanphongkham.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DanhSachHoaDonActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    HoaDonAdapter adapter;
    Database db;
    FloatingActionButton btnThem;
    MaterialButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_hoa_don);

        recyclerView = findViewById(R.id.recyclerViewHoaDon);
        btnThem = findViewById(R.id.btnThemHoaDon);
        btnBack = findViewById(R.id.btnBack);
        db = new Database(this);


        btnThem.setOnClickListener(view -> {
            Intent intent = new Intent(DanhSachHoaDonActivity.this, CreateInvoiceActivity.class);
            startActivity(intent);
        });


        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(DanhSachHoaDonActivity.this, MainKeToanActivity.class);
            startActivity(intent);
            finish();
        });

        loadData();
    }


    private void loadData() {
        List<HoaDon> list = db.getAllHoaDon();
        adapter = new HoaDonAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}
