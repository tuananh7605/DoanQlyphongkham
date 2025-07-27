package com.example.doanphongkham.fragmentBacsi;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.doanphongkham.ActivityBacsi.ThemThuocActivity;
import com.example.doanphongkham.Admin.QuanLyTrangChinhActivity;
import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.KeToan.MainKeToanActivity;

import com.example.doanphongkham.R;

public class TaiKhoanBacsiFragment extends Fragment {
    EditText edtHoTenBs, edtGioiTinhBs, edtSdtBs, edtNgaySinhBs, edtDiaChiBs, edtEmailBs;
    DatabaseHelper dbHelper;

    Button btnThemThuoc;
    Button btnCapNhatThongTin;


    Button btnDangXuat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tai_khoan_bacsi, container, false);

        // Khởi tạo
        dbHelper = new DatabaseHelper(getContext());
        // Ánh xạ
        edtHoTenBs = view.findViewById(R.id.edtHoTenBs);
        edtGioiTinhBs = view.findViewById(R.id.edtGioiTinhBs);
        edtSdtBs = view.findViewById(R.id.edtSdtBs);
        edtNgaySinhBs = view.findViewById(R.id.edtNgaySinhBs);
        edtDiaChiBs = view.findViewById(R.id.edtDiaChiBs);
        edtEmailBs = view.findViewById(R.id.edtEmailBs);
        btnThemThuoc = view.findViewById(R.id.btnThemThuoc);
        btnDangXuat = view.findViewById(R.id.btnDangXuatBacSi);
        btnCapNhatThongTin = view.findViewById(R.id.btnCapNhatThongTin);






        btnThemThuoc.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ThemThuocActivity.class);
            startActivity(intent);
        });
        btnDangXuat.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainKeToanActivity.class);
            startActivity(intent);
        });


        return view;
    }
}
