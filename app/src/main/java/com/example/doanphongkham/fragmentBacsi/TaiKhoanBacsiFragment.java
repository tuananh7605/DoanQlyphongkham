package com.example.doanphongkham.fragmentBacsi;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.doanphongkham.ActivityBacsi.ThemThuocActivity;
import com.example.doanphongkham.KeToan.MainKeToanActivity;
import com.example.doanphongkham.R;

public class TaiKhoanBacsiFragment extends Fragment {

    Button btnThemThuoc;
    Button btnDangXuat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tai_khoan_bacsi, container, false);

        btnThemThuoc = view.findViewById(R.id.btnThemThuoc);
        btnDangXuat = view.findViewById(R.id.btnDangXuatBacSi);

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
