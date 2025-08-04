package com.example.doanphongkham;


import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import android.content.Intent;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.view.WindowInsetsCompat;

import com.example.doanphongkham.ActivityBacsi.MainActivity;
import com.example.doanphongkham.Admin.QuanLyTrangChinhActivity;
import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.KeToan.MainKeToanActivity;



public class LoginActivity extends AppCompatActivity {

    private EditText maTkEditText;
    private EditText passwordEditText;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        maTkEditText = findViewById(R.id.maTk);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maTk = maTkEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();


                if (maTk.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this,
                            "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseHelper dbHelper = new DatabaseHelper(LoginActivity.this);
                String userType = dbHelper.checkUser(maTk, password);
                if (userType != null) {
                    userType = userType.trim(); // Trim any leading/trailing spaces
                }
                Log.d("LoginActivity", "User Type: " + userType);

                if (userType != null) {
                    if (userType.equals("Bác Sĩ")) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công với vai trò " + userType + "!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Close LoginActivity
                    } else if (userType.equals("Kế Toán")) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công với vai trò " + userType + "!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainKeToanActivity.class);
                        startActivity(intent);
                        finish(); // Close LoginActivity
                    } else if (userType.equals("Admin")) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công với vai trò " + userType + "!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, QuanLyTrangChinhActivity.class);
                        startActivity(intent);
                        finish(); // Close LoginActivity
                    } else {
                        Toast.makeText(LoginActivity.this, "Loại tài khoản không hợp lệ.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
