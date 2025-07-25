package com.example.doanphongkham.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanphongkham.ActivityBacsi.KhamBenhActivity;
import com.example.doanphongkham.ActivityBacsi.ThemLichKhamActivity;
import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.Model.PhieuKhamBenh;
import com.example.doanphongkham.R;

import java.util.List;

public class LichKhamAdapter extends RecyclerView.Adapter<LichKhamAdapter.LichKhamViewHolder> {

    private Context context;
    private List<PhieuKhamBenh> list;
    private DatabaseHelper databaseHelper;
    private boolean isDaKham;

    public LichKhamAdapter(Context context, List<PhieuKhamBenh> list, DatabaseHelper db, boolean isDaKham) {
        this.context = context;
        this.list = list;
        this.databaseHelper = db;
        this.isDaKham = isDaKham;
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
        holder.tvPhongKham.setText("Phòng khám: " + item.getPhongKham());
        holder.tvNgayKham.setText("Ngày khám: " + item.getNgayKham());
        holder.tvGioKham.setText("Giờ khám: " + item.getGioKham());
        holder.tvTienSuBenh.setText("Tiền sử bệnh: " + item.getTienSuBenh());
        holder.tvSdt.setText("SĐT: " + item.getSdt());
        holder.tvNgaySinh.setText("Ngày sinh: " + item.getNgaySinh());

        if (isDaKham) {
            holder.btnXoa.setVisibility(View.GONE);
            holder.btnSua.setVisibility(View.GONE);
        } else {
            holder.btnXoa.setVisibility(View.VISIBLE);
            holder.btnSua.setVisibility(View.VISIBLE);
        }

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

        holder.btnSua.setOnClickListener(v -> {
            Intent intent = new Intent(context, ThemLichKhamActivity.class);
            intent.putExtra("isUpdate", true);
            intent.putExtra("id", item.getId());
            intent.putExtra("ten", item.getTenBenhNhan());
            intent.putExtra("sdt", item.getSdt());
            intent.putExtra("ngaySinh", item.getNgaySinh());
            intent.putExtra("ngay", item.getNgayKham());
            intent.putExtra("gio", item.getGioKham());
            intent.putExtra("tiensu", item.getTienSuBenh());
            intent.putExtra("phong", item.getPhongKham());
            context.startActivity(intent);
        });

        holder.itemView.setOnClickListener(v -> {
            if (isDaKham) {
                // Hiện tất cả thông tin tương tự KhamBenhActivity
                String info = "Bệnh nhân: " + item.getTenBenhNhan() + "\n"
                        + "SĐT: " + item.getSdt() + "\n"
                        + "Ngày sinh: " + item.getNgaySinh() + "\n"
                        + "Ngày khám: " + item.getNgayKham() + "\n"
                        + "Giờ khám: " + item.getGioKham() + "\n"
                        + "Tiền sử bệnh: " + item.getTienSuBenh() + "\n"
                        + "Phòng khám: " + item.getPhongKham() + "\n"
                        + "Ghi chú: Đã hoàn thành khám";

                new AlertDialog.Builder(context)
                        .setTitle("Chi tiết lịch đã khám")
                        .setMessage(info)
                        .setPositiveButton("Đóng", null)
                        .show();
            } else {
                // Nếu chưa khám thì chuyển sang KhamBenhActivity
                Intent intent = new Intent(context, KhamBenhActivity.class);
                intent.putExtra("id", item.getId());
                intent.putExtra("ten", item.getTenBenhNhan());
                intent.putExtra("sdt", item.getSdt());
                intent.putExtra("ngaySinh", item.getNgaySinh());
                intent.putExtra("ngay", item.getNgayKham());
                intent.putExtra("gio", item.getGioKham());
                intent.putExtra("tiensu", item.getTienSuBenh());
                intent.putExtra("phong", item.getPhongKham());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LichKhamViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenBenhNhan, tvNgayKham, tvGioKham, tvTienSuBenh, tvPhongKham;
        TextView tvSdt, tvNgaySinh;
        ImageButton btnXoa;
        ImageButton btnSua;

        public LichKhamViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenBenhNhan = itemView.findViewById(R.id.textViewTenbenhnhan);
            tvNgayKham = itemView.findViewById(R.id.textViewNgaykham);
            tvGioKham = itemView.findViewById(R.id.textViewGiokham);
            tvTienSuBenh = itemView.findViewById(R.id.textViewTiensubenh);
            tvPhongKham = itemView.findViewById(R.id.textViewPhongKham);
            btnXoa = itemView.findViewById(R.id.btnXoalichkham);
            btnSua = itemView.findViewById(R.id.btnSualichkham);
            tvSdt = itemView.findViewById(R.id.textViewSdt);
            tvNgaySinh = itemView.findViewById(R.id.textViewNgaySinh);


        }
    }

    public void updateList(List<PhieuKhamBenh> newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }
}
