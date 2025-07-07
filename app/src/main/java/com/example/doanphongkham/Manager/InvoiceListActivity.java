package com.example.doanphongkham.Manager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanphongkham.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class InvoiceListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAddInvoice;
    private EditText edtSearchInvoice;
    private Button btnSelectDate;

    private InvoiceAdapter adapter;
    private ArrayList<Invoice> invoiceList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_list);

        recyclerView = findViewById(R.id.recyclerInvoice);
        fabAddInvoice = findViewById(R.id.fabAddInvoice);
        edtSearchInvoice = findViewById(R.id.edtSearchInvoice);
        btnSelectDate = findViewById(R.id.btnSelectDate);

        invoiceList = new ArrayList<>();

        // Adapter với danh sách đầy đủ
        adapter = new InvoiceAdapter(invoiceList, this::onInvoiceClick);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fabAddInvoice.setOnClickListener(view -> {
            Intent intent = new Intent(this, CreateInvoiceActivity.class);
            startActivityForResult(intent, 1); // nhận kết quả tạo hóa đơn
        });

        // Tìm kiếm khi nhập văn bản
        edtSearchInvoice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filterByKeyword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        // Chọn ngày lọc hóa đơn
        btnSelectDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, year, month, dayOfMonth) -> {
                        String selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
                        adapter.filterByDate(selectedDate);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });
    }

    private void onInvoiceClick(Invoice invoice) {
        Intent intent = new Intent(this, InvoiceDetailActivity.class);
        intent.putExtra("invoice", invoice);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Invoice newInvoice = (Invoice) data.getSerializableExtra("invoice");
            if (newInvoice != null) {
                invoiceList.add(newInvoice);
                adapter.notifyDataSetChanged(); // Cập nhật cả danh sách và filtered
                Toast.makeText(this, "Đã thêm hóa đơn mới", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
