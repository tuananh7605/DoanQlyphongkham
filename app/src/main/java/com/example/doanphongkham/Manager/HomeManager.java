package com.example.doanphongkham.Manager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanphongkham.R;

public class HomeManager extends AppCompatActivity {

    private Button btnRevenue, btnInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_home);

        // Ánh xạ view
        btnRevenue = findViewById(R.id.btnRevenue);
        btnInvoice = findViewById(R.id.btnInvoice);

        // Mở màn hình quản lý doanh thu
        btnRevenue.setOnClickListener(v -> {
            Intent intent = new Intent(HomeManager.this, ManagerActivity.class);
            startActivity(intent);
        });

        // Mở màn hình tạo hóa đơn
        btnInvoice.setOnClickListener(v -> {
            Intent intent = new Intent(HomeManager.this, InvoiceListActivity.class);
            startActivity(intent);
        });
    }
}
