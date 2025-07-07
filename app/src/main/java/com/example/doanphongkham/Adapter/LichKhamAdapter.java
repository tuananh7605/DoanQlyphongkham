package com.example.doanphongkham.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.Model.PhieuKhamBenh;
import com.example.doanphongkham.R;

import java.util.List;

public class LichKhamAdapter extends RecyclerView.Adapter<LichKhamAdapter.LichKhamViewHolder> {

    private Context context;
    private List<PhieuKhamBenh> list;
    private DatabaseHelper databaseHelper;
    public LichKhamAdapter(Context context, List<PhieuKhamBenh> list,DatabaseHelper db) {
        this.context = context;
        this.list = list;
        this.databaseHelper = db;
    }

    @NonNull
    @Override
    public LichKhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_lichkham, parent, false);
        return new LichKhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LichKhamViewHolder holder, int position) {
        PhieuKhamBenh item = list.get(position);

        holder.tvTenBenhNhan.setText(item.getTenBenhNhan());
        holder.tvNgayKham.setText("Ngày khám: " + item.getNgayKham());
        holder.tvGioKham.setText("Giờ khám: " + item.getGioKham());
        holder.tvTienSuBenh.setText("Tiền sử bệnh: " + item.getTienSuBenh());

        holder.btnXoa.setOnClickListener(v -> {
            boolean isDeleted = databaseHelper.deleteLichKham(item.getId());

            if (isDeleted) {
                list.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(context, "Đã xoá lịch khám", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LichKhamViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenBenhNhan, tvNgayKham, tvGioKham, tvTienSuBenh;
        ImageButton btnXoa;

        public LichKhamViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenBenhNhan = itemView.findViewById(R.id.textViewTenbenhnhan);
            tvNgayKham = itemView.findViewById(R.id.textViewNgaykham);
            tvGioKham = itemView.findViewById(R.id.textViewGiokham);
            tvTienSuBenh = itemView.findViewById(R.id.textViewTiensubenh);
            btnXoa = itemView.findViewById(R.id.btnXoalichkham);
        }
    }
    public void updateList(List<PhieuKhamBenh> newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }

}

