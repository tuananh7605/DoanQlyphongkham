package com.example.doanphongkham.Manager;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.doanphongkham.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ManagerActivity extends AppCompatActivity {

    private TextView tvTotalRevenue;
    private Spinner spinnerMonth, spinnerYear;
    private RecyclerView recyclerRevenue;
    private FloatingActionButton btnAddRevenue;

    private RevenueAdapter revenueAdapter;
    private ArrayList<RevenueItem> revenueList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manager);

        // Áp dụng inset cho layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ view
        tvTotalRevenue = findViewById(R.id.tvTotalRevenue);
        spinnerMonth = findViewById(R.id.spinnerMonth);
        spinnerYear = findViewById(R.id.spinnerYear);
        recyclerRevenue = findViewById(R.id.recyclerRevenue);
        btnAddRevenue = findViewById(R.id.btnAddRevenue);

        // Khởi tạo danh sách doanh thu giả lập
        revenueList = new ArrayList<>();
        revenueList.add(new RevenueItem("01/07/2025", "Bán thuốc", 500000));
        revenueList.add(new RevenueItem("02/07/2025", "Dịch vụ khám", 800000));
        revenueList.add(new RevenueItem("03/07/2025", "Xét nghiệm", 300000));

        // Cập nhật tổng doanh thu
        updateTotalRevenue();

        // Set RecyclerView
        revenueAdapter = new RevenueAdapter(revenueList);
        recyclerRevenue.setLayoutManager(new LinearLayoutManager(this));
        recyclerRevenue.setAdapter(revenueAdapter);

        // Xử lý khi ấn nút thêm doanh thu
        btnAddRevenue.setOnClickListener(view -> {
            Toast.makeText(this, "Chức năng thêm doanh thu đang được phát triển", Toast.LENGTH_SHORT).show();
            // TODO: Mở dialog hoặc activity để thêm doanh thu mới
        });
    }

    private void updateTotalRevenue() {
        int total = 0;
        for (RevenueItem item : revenueList) {
            total += item.getAmount();
        }
        tvTotalRevenue.setText("Tổng doanh thu: " + String.format("%,d", total) + "đ");
    }
}
