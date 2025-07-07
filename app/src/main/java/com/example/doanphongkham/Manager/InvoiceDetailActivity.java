package com.example.doanphongkham.Manager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanphongkham.R;

public class InvoiceDetailActivity extends AppCompatActivity {

    private TextView tvDetailName, tvDetailBirthDate, tvDetailPhone, tvDetailGender, tvDetailDepartment,
            tvDetailCondition, tvDetailExamNumber, tvDetailTotal;
    private Button btnDeleteInvoice, btnPrintInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_detail);

        tvDetailName = findViewById(R.id.tvDetailName);
        tvDetailBirthDate = findViewById(R.id.tvDetailBirthDate);
        tvDetailPhone = findViewById(R.id.tvDetailPhone);
        tvDetailGender = findViewById(R.id.tvDetailGender);
        tvDetailDepartment = findViewById(R.id.tvDetailDepartment);
        tvDetailCondition = findViewById(R.id.tvDetailCondition);
        tvDetailExamNumber = findViewById(R.id.tvDetailExamNumber);
        tvDetailTotal = findViewById(R.id.tvDetailTotal);

        btnDeleteInvoice = findViewById(R.id.btnDeleteInvoice);
        btnPrintInvoice = findViewById(R.id.btnPrintInvoice);

        Invoice invoice = (Invoice) getIntent().getSerializableExtra("invoice");
        if (invoice != null) {
            tvDetailName.setText("Tên: " + invoice.name);
            tvDetailBirthDate.setText("Ngày sinh: " + invoice.birthDate);
            tvDetailPhone.setText("SĐT: " + invoice.phone);
            tvDetailGender.setText("Giới tính: " + invoice.gender);
            tvDetailDepartment.setText("Khoa khám: " + invoice.department);
            tvDetailCondition.setText("Tình trạng: " + invoice.condition);
            tvDetailExamNumber.setText("Số khám: " + invoice.examNumber);
            tvDetailTotal.setText("Tổng tiền: " + invoice.total + "đ");
        }

        btnDeleteInvoice.setOnClickListener(v -> {
            Toast.makeText(this, "Xóa hóa đơn (đang phát triển)", Toast.LENGTH_SHORT).show();
        });

        btnPrintInvoice.setOnClickListener(v -> {
            Toast.makeText(this, "In hóa đơn (đang phát triển)", Toast.LENGTH_SHORT).show();
        });
    }
}