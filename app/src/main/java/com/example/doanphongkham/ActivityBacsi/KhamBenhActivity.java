package com.example.doanphongkham.ActivityBacsi;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.Model.Thuoc;
import com.example.doanphongkham.R;

import java.util.ArrayList;
import java.util.List;

public class KhamBenhActivity extends AppCompatActivity {
    TextView tvTenBN, tvNgayKham, tvGioKham, tvTienSuBenh, tvPhongKham;
    TextView tvSdt, tvNgaySinh;

    ImageButton btnBack;
    TextView tvTienKhamBenh;
    Button btnKhamBenh;

    List<Thuoc> thuocList;
    DatabaseHelper dbHelper;
    private boolean isUpdating = false;
    private double giaPhongKham = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kham_benh);

        // Ánh xạ
        tvTenBN = findViewById(R.id.tvTenBNHD);
        tvNgayKham = findViewById(R.id.tvNgayKham);
        tvGioKham = findViewById(R.id.tvGioKham);
        tvTienSuBenh = findViewById(R.id.tvTeinsubenh);
        tvPhongKham = findViewById(R.id.tvPhongKham);
        btnBack = findViewById(R.id.btnBackk);
        tvTienKhamBenh = findViewById(R.id.tvTienKhamBenh);
        tvSdt = findViewById(R.id.tvSdt);
        tvNgaySinh = findViewById(R.id.tvNgaySinhHD);
        btnKhamBenh = findViewById(R.id.btnKhamBenh);

        // Nhận dữ liệu
        int phieuKhamId = getIntent().getIntExtra("id", -1);
        String ten = getIntent().getStringExtra("ten");
        String sdt = getIntent().getStringExtra("sdt");
        String ngaySinh = getIntent().getStringExtra("ngaySinh");
        String ngay = getIntent().getStringExtra("ngay");
        String gio = getIntent().getStringExtra("gio");
        String tiensu = getIntent().getStringExtra("tiensu");
        String phong = getIntent().getStringExtra("phong");

        // Hiển thị dữ liệu
        tvTenBN.setText("Bệnh nhân: " + ten);
        tvSdt.setText("SĐT: " + sdt);
        tvNgaySinh.setText("Ngày sinh: " + ngaySinh);
        tvNgayKham.setText("Ngày khám: " + ngay);
        tvGioKham.setText("Giờ khám: " + gio);
        tvTienSuBenh.setText("Tiền sử bệnh: " + tiensu);
        tvPhongKham.setText("Phòng khám: " + phong);
        tvPhongKham.setText("Phòng khám: " + phong);



        btnBack.setOnClickListener(v -> finish());

        dbHelper = new DatabaseHelper(this);



        // Lấy giá phòng khám
        if (phong != null && !phong.trim().isEmpty()) {
            giaPhongKham = dbHelper.getGiaPhongKham(phong);
        }
        TextView tvGiaPhongKham = findViewById(R.id.tvGiaPhongKham);
        tvGiaPhongKham.setText(String.format("(%,.0f đ)", giaPhongKham));

        // Danh sách thuốc có mục chọn đầu tiên
        thuocList = new ArrayList<>();
        thuocList.add(new Thuoc(-1, "-- Chọn thuốc --", "", 0));
        thuocList.addAll(dbHelper.getAllThuoc());

        setupThuocRow(findViewById(R.id.rowThuoc1));
        setupThuocRow(findViewById(R.id.rowThuoc2));
        setupThuocRow(findViewById(R.id.rowThuoc3));

        btnKhamBenh.setOnClickListener(v -> {

            double tongTien = giaPhongKham; // ✅ cộng luôn giá phòng
            tongTien += getTienThuoc(findViewById(R.id.rowThuoc1));
            tongTien += getTienThuoc(findViewById(R.id.rowThuoc2));
            tongTien += getTienThuoc(findViewById(R.id.rowThuoc3));
            boolean inserted = dbHelper.insertDaKhamXong(ten, sdt, ngaySinh, ngay,  tiensu, phong, tongTien);
            if (inserted) {
                dbHelper.updateTrangThaiDaKham(phieuKhamId);
                Toast.makeText(this, "Khám Thành Công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupThuocRow(View rowView) {
        Spinner spinner = rowView.findViewById(R.id.spinnerThuoc);
        EditText edtSoLuong = rowView.findViewById(R.id.edtSoLuong);
        TextView tvMoTa = rowView.findViewById(R.id.tvMoTaThuoc);
        TextView tvGia = rowView.findViewById(R.id.tvGiaThuoc);

        ArrayAdapter<Thuoc> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, thuocList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Thuoc selected = thuocList.get(position);
                if (selected.getId() == -1) {
                    tvMoTa.setText("");
                    tvGia.setText("Giá: 0 đ");
                } else {
                    tvMoTa.setText(selected.getMoTa());
                    tvGia.setText("Giá: " + (int) selected.getGiaTien() + " đ");
                }
                updateTongTien();
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {
                tvMoTa.setText("");
                tvGia.setText("Giá: 0 đ");
                updateTongTien();
            }
        });

        edtSoLuong.addTextChangedListener(new android.text.TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateTongTien();
            }

            @Override public void afterTextChanged(android.text.Editable s) {}
        });
    }

    private void updateTongTien() {
        if (isUpdating) return;
        isUpdating = true;

        double tong = giaPhongKham; // ✅ Bắt đầu từ giá phòng khám
        tong += getTienThuoc(findViewById(R.id.rowThuoc1));
        tong += getTienThuoc(findViewById(R.id.rowThuoc2));
        tong += getTienThuoc(findViewById(R.id.rowThuoc3));

        tvTienKhamBenh.setText(String.format("%,.0f đ", tong));
        isUpdating = false;
    }


    private double getTienThuoc(View row) {
        Spinner spinner = row.findViewById(R.id.spinnerThuoc);
        EditText edtSoLuong = row.findViewById(R.id.edtSoLuong);

        int pos = spinner.getSelectedItemPosition();
        if (pos <= 0 || pos >= thuocList.size()) return 0;

        Thuoc thuoc = thuocList.get(pos);
        String slStr = edtSoLuong.getText().toString().trim();
        int sl = 0;
        try {
            sl = Integer.parseInt(slStr);
        } catch (NumberFormatException e) {
            sl = 0;
        }
        return thuoc.getGiaTien() * sl;
    }
}
