package com.example.doanphongkham.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.doanphongkham.Model.Khoa; // Import lớp Khoa
import com.example.doanphongkham.R;

import java.util.List;

public class KhoaAdapter extends ArrayAdapter<Khoa> { // Sử dụng Khoa thay vì KhoaWithBacSi
    private Context context;
    private List<Khoa> khoaList;

    public KhoaAdapter(Context context, List<Khoa> khoaList) {
        super(context, 0, khoaList);
        this.context = context;
        this.khoaList = khoaList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_khoa, parent, false); // Giả định layout item_khoa
        }

        Khoa currentKhoa = khoaList.get(position);

        TextView tvTenKhoa = convertView.findViewById(R.id.tv_ten_khoa); // Giả định ID TextView
        TextView tvMoTa = convertView.findViewById(R.id.tv_mo_ta); // Giả định ID TextView
        TextView tvGiaTien = convertView.findViewById(R.id.tv_gia_tien); // Giả định ID TextView

        // Thiết lập dữ liệu cho các TextView
        if (tvTenKhoa != null) {
            tvTenKhoa.setText(currentKhoa.getTenKhoa());
        }
        if (tvMoTa != null) {
            tvMoTa.setText(currentKhoa.getMoTa());
        }
        if (tvGiaTien != null) {
            tvGiaTien.setText(String.format("Giá: %.0f VNĐ", currentKhoa.getGiaTien()));
        }

        return convertView;
    }

    // Phương thức cập nhật dữ liệu
    public void updateData(List<Khoa> newList) {
        khoaList.clear();
        khoaList.addAll(newList);
        notifyDataSetChanged();
    }
}
