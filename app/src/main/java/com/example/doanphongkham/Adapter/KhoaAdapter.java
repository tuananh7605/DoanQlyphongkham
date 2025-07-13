package com.example.doanphongkham.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.R;

import java.util.List;

public class KhoaAdapter extends ArrayAdapter<DatabaseHelper.KhoaWithBacSi> {
    private Context context;
    private List<DatabaseHelper.KhoaWithBacSi> listKhoa;

    public KhoaAdapter(@NonNull Context context, List<DatabaseHelper.KhoaWithBacSi> listKhoa) {
        super(context, R.layout.item_khoa, listKhoa);
        this.context = context;
        this.listKhoa = listKhoa;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_khoa, parent, false);
        }

        DatabaseHelper.KhoaWithBacSi khoa = listKhoa.get(position);

        TextView tvMaKhoa = view.findViewById(R.id.tvMaKhoa);
        TextView tvTenKhoa = view.findViewById(R.id.tvTenKhoa);
        TextView tvBacSi = view.findViewById(R.id.tvBacSi);
        TextView tvMoTa = view.findViewById(R.id.tvMoTa);

        tvMaKhoa.setText("Mã khoa: " + khoa.getMaKhoa());
        tvTenKhoa.setText("Tên khoa: " + khoa.getTenKhoa());
        tvBacSi.setText("Bác sĩ: " + (khoa.getTenBS() != null ? khoa.getTenBS() : "Chưa có"));
        tvMoTa.setText("Mô tả: " + khoa.getMoTa());

        return view;
    }

    public void updateData(List<DatabaseHelper.KhoaWithBacSi> newList) {
        this.listKhoa.clear();
        this.listKhoa.addAll(newList);
        notifyDataSetChanged();
    }
}
