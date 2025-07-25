package com.example.doanphongkham.KeToan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.doanphongkham.R;

import java.util.Calendar;

public class CreateInvoiceActivity extends AppCompatActivity {

    EditText edtTenBenhNhan, edtNgaySinh, edtSDT, edtKhoaKham, edtTinhTrang, edtNgayTao, edtTongTien;
    Button btnLuu, btnBack;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_invoice);

        // Ánh xạ view
        edtTenBenhNhan = findViewById(R.id.edtTenBenhNhan);
        edtNgaySinh = findViewById(R.id.edtNgaySinh);
        edtSDT = findViewById(R.id.edtSDTBenhNhan);
        edtKhoaKham = findViewById(R.id.edtKhoaKham);
        edtTinhTrang = findViewById(R.id.edtTinhTrang);
        edtNgayTao = findViewById(R.id.edtNgayTao);
        edtTongTien = findViewById(R.id.edtTongTien);
        btnLuu = findViewById(R.id.btnLuuHoaDon);
        btnBack = findViewById(R.id.btnBack);

        db = new Database(this);

        // Hiển thị DatePicker khi nhấn vào edtNgayTao
        edtNgayTao.setOnClickListener(v -> showDatePicker());

        // Xử lý lưu hóa đơn
        btnLuu.setOnClickListener(v -> {
            String ten = edtTenBenhNhan.getText().toString().trim();
            String ns = edtNgaySinh.getText().toString().trim();
            String sdt = edtSDT.getText().toString().trim();
            String khoa = edtKhoaKham.getText().toString().trim();
            String tt = edtTinhTrang.getText().toString().trim();
            String ngayTao = edtNgayTao.getText().toString().trim();
            String tong = edtTongTien.getText().toString().trim();

            if (ten.isEmpty() || ngayTao.isEmpty() || tong.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ các trường bắt buộc!", Toast.LENGTH_SHORT).show();
                return;
            }

            double tongTien;
            try {
                tongTien = Double.parseDouble(tong);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Tổng tiền không hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lưu vào database
            db.insertHoaDon(ten, ns, sdt, khoa, tt, ngayTao, tongTien);
            db.insertDoanhThu(ngayTao, tongTien);

            Toast.makeText(this, "Đã lưu hóa đơn và doanh thu!", Toast.LENGTH_SHORT).show();

            // Quay lại danh sách hóa đơn
            startActivity(new Intent(this, DanhSachHoaDonActivity.class));
            finish();
        });

        // Xử lý nút Quay lại
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(CreateInvoiceActivity.this, DanhSachHoaDonActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, (DatePicker view, int y, int m, int d) -> {
            String formattedDate = String.format("%04d-%02d-%02d", y, m + 1, d);
            edtNgayTao.setText(formattedDate);
        }, year, month, day);

        dialog.show();
    }
}
