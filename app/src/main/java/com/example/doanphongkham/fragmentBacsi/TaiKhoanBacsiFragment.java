package com.example.doanphongkham.fragmentBacsi;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doanphongkham.ActivityBacsi.ThemThuocActivity;
import com.example.doanphongkham.Admin.QuanLyTrangChinhActivity;
import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.LoginActivity;
import com.example.doanphongkham.R;

public class TaiKhoanBacsiFragment extends Fragment {
    EditText edtHoTenBs, edtGioiTinhBs, edtSdtBs, edtNgaySinhBs, edtDiaChiBs, edtEmailBs;
    DatabaseHelper dbHelper;

    Button btnThemThuoc, btnCapNhatThongTin, btnDangXuat;

    String maBS; // Mã bác sĩ hiện tại

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tai_khoan_bacsi, container, false);

        // Khởi tạo DB
        dbHelper = new DatabaseHelper(getContext());

        // Ánh xạ view
        edtHoTenBs = view.findViewById(R.id.edtHoTenBs);
        edtGioiTinhBs = view.findViewById(R.id.edtGioiTinhBs);
        edtSdtBs = view.findViewById(R.id.edtSdtBs);
        edtNgaySinhBs = view.findViewById(R.id.edtNgaySinhBs);
        edtDiaChiBs = view.findViewById(R.id.edtDiaChiBs);
        edtEmailBs = view.findViewById(R.id.edtEmailBs);
        btnThemThuoc = view.findViewById(R.id.btnThemThuoc);
        btnDangXuat = view.findViewById(R.id.btnDangXuatBacSi);
        btnCapNhatThongTin = view.findViewById(R.id.btnCapNhatThongTin);

        // Lấy maBS từ SharedPreferences (được lưu khi đăng nhập)
        SharedPreferences prefs = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        maBS = prefs.getString("maBS", null);

        if (maBS != null) {
            loadBacSiInfo();
        } else {
            Toast.makeText(getContext(), "Không tìm thấy thông tin bác sĩ", Toast.LENGTH_SHORT).show();
        }

        // Nút thêm thuốc
        btnThemThuoc.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ThemThuocActivity.class);
            startActivity(intent);
        });

        // Nút đăng xuất
        btnDangXuat.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        // Nút cập nhật thông tin
        btnCapNhatThongTin.setOnClickListener(v -> updateBacSiInfo());

        return view;
    }

// Hàm load dữ liệu bác sĩ từ DB
private void loadBacSiInfo() {
    SQLiteDatabase db = dbHelper.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM BacSi WHERE maBS = ?", new String[]{maBS});

    if (cursor.moveToFirst()) {
        edtHoTenBs.setText(cursor.getString(cursor.getColumnIndexOrThrow("tenBS")));
        edtGioiTinhBs.setText(cursor.getString(cursor.getColumnIndexOrThrow("gioitinh")));
        edtSdtBs.setText(cursor.getString(cursor.getColumnIndexOrThrow("sdt")));
        edtNgaySinhBs.setText(cursor.getString(cursor.getColumnIndexOrThrow("NgaySinh")));
        edtDiaChiBs.setText(cursor.getString(cursor.getColumnIndexOrThrow("diachi")));
        edtEmailBs.setText(cursor.getString(cursor.getColumnIndexOrThrow("email")));
    }
    cursor.close();
}

    // Hàm cập nhật thông tin bác sĩ
    private void updateBacSiInfo() {
        String tenBS = edtHoTenBs.getText().toString().trim();
        String gioiTinh = edtGioiTinhBs.getText().toString().trim();
        String sdt = edtSdtBs.getText().toString().trim();
        String ngaySinh = edtNgaySinhBs.getText().toString().trim();
        String diaChi = edtDiaChiBs.getText().toString().trim();
        String email = edtEmailBs.getText().toString().trim();

        if (tenBS.isEmpty() || email.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ họ tên và email", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenBS", tenBS);
        values.put("gioitinh", gioiTinh);
        values.put("sdt", sdt);
        values.put("NgaySinh", ngaySinh);
        values.put("diachi", diaChi);
        values.put("email", email);

        int rows = db.update("BacSi", values, "maBS = ?", new String[]{maBS});

        if (rows > 0) {
            Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}
