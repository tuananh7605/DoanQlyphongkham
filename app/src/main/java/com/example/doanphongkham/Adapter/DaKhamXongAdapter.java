package com.example.doanphongkham.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.Model.DaKhamXong;
import com.example.doanphongkham.R;

import java.util.List;

public class DaKhamXongAdapter extends RecyclerView.Adapter<DaKhamXongAdapter.ViewHolder> {
    private Context context;
    private List<DaKhamXong> hoaDonList;


    private DatabaseHelper db;

    public DaKhamXongAdapter(Context context, List<DaKhamXong> hoaDonList) {
        this.context = context;
        this.hoaDonList = hoaDonList;
        this.db = new DatabaseHelper(context);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenBNHD, tvNgaySinhHD, tvSDTHD, tvPhongKhamHD, tvTienSuBenhHD, tvNgayKhamHD, tvTongTienHD;
        Button btnIn, btnXoa;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTenBNHD = itemView.findViewById(R.id.tvTenBNHD);
            tvNgaySinhHD = itemView.findViewById(R.id.tvNgaySinhHD);
            tvSDTHD = itemView.findViewById(R.id.tvSDTHD);
            tvPhongKhamHD = itemView.findViewById(R.id.tvPhongKhamHD);
            tvTienSuBenhHD = itemView.findViewById(R.id.tvTienSuBenhHD);
            tvNgayKhamHD = itemView.findViewById(R.id.tvNgayKhamHD);
            tvTongTienHD = itemView.findViewById(R.id.tvTongTienHD);
            btnIn = itemView.findViewById(R.id.btnIn);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }

    @NonNull
    @Override
    public DaKhamXongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hoadon2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DaKhamXongAdapter.ViewHolder holder, int position) {
        DaKhamXong hoaDon = hoaDonList.get(position);
        holder.tvTenBNHD.setText("Bệnh nhân: " + hoaDon.getTenBenhNhan());
        holder.tvNgaySinhHD.setText("Ngày sinh: " + hoaDon.getNgaySinh());
        holder.tvSDTHD.setText("SĐT: " + hoaDon.getSdt());
        holder.tvPhongKhamHD.setText("Phòng khám: " + hoaDon.getPhongKham());
        holder.tvTienSuBenhHD.setText("Tiền sử bệnh: " + hoaDon.getTienSuBenh());
        holder.tvNgayKhamHD.setText("Ngày khám: " + hoaDon.getNgayKham());
        holder.tvTongTienHD.setText("Tổng tiền: " + hoaDon.getTongTien() + " VNĐ");
        //btn Xoa
        holder.btnXoa.setOnClickListener(v -> {
            DaKhamXong item = hoaDonList.get(holder.getAdapterPosition());

            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa hóa đơn này?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        DatabaseHelper db = new DatabaseHelper(context);
                        boolean isDeleted = db.deleteDaKhamXong(item.getId());
                        if (isDeleted) {
                            int pos = holder.getAdapterPosition();
                            hoaDonList.remove(pos);
                            notifyItemRemoved(pos);
                            notifyItemRangeChanged(pos, hoaDonList.size());
                            Toast.makeText(context, "Đã xóa hóa đơn", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });



    }


    @Override
    public int getItemCount() {
        return hoaDonList.size();
    }
}
