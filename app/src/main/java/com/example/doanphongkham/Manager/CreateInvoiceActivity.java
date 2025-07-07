package com.example.doanphongkham.Manager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.doanphongkham.R;

import androidx.appcompat.app.AppCompatActivity;

public class CreateInvoiceActivity extends AppCompatActivity {

    private EditText edtName, edtBirthDate, edtPhone, edtDepartment, edtCondition, edtExamNumber, edtTotal;
    private RadioGroup rgGender;
    private Button btnSaveInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_invoice);

        // Ánh xạ view
        edtName = findViewById(R.id.edtName);
        edtBirthDate = findViewById(R.id.edtBirthDate);
        edtPhone = findViewById(R.id.edtPhone);
        edtDepartment = findViewById(R.id.edtDepartment);
        edtCondition = findViewById(R.id.edtCondition);
        edtExamNumber = findViewById(R.id.edtExamNumber);
        edtTotal = findViewById(R.id.edtTotal);
        rgGender = findViewById(R.id.rgGender);
        btnSaveInvoice = findViewById(R.id.btnSaveInvoice);

        btnSaveInvoice.setOnClickListener(v -> {
            // Lấy dữ liệu
            String name = edtName.getText().toString().trim();
            String birthDate = edtBirthDate.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String department = edtDepartment.getText().toString().trim();
            String condition = edtCondition.getText().toString().trim();
            String examNumber = edtExamNumber.getText().toString().trim();
            String totalStr = edtTotal.getText().toString().trim();

            int genderId = rgGender.getCheckedRadioButtonId();

            // Kiểm tra dữ liệu hợp lệ
            if (name.isEmpty() || birthDate.isEmpty() || phone.isEmpty() || department.isEmpty()
                    || condition.isEmpty() || examNumber.isEmpty() || totalStr.isEmpty() || genderId == -1) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            int total;
            try {
                total = Integer.parseInt(totalStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Số tiền không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lấy giới tính
            RadioButton genderBtn = findViewById(genderId);
            String gender = genderBtn.getText().toString();

            // Tạo đối tượng hóa đơn
            Invoice invoice = new Invoice(name, birthDate, phone, gender, department, condition, examNumber, total);

            // Trả kết quả về activity trước đó
            Intent resultIntent = new Intent();
            resultIntent.putExtra("invoice", invoice);
            setResult(RESULT_OK, resultIntent);

            Toast.makeText(this, "Đã lưu hóa đơn", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
