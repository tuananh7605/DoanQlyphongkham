package com.example.doanphongkham.KeToan;


import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.doanphongkham.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.HoaDonViewHolder> {

    private Context context;
    private List<HoaDon> hoaDonList;
    private Database db;

    public HoaDonAdapter(Context context, List<HoaDon> hoaDonList) {
        this.context = context;
        this.hoaDonList = hoaDonList;
        this.db = new Database(context);
    }

    @NonNull
    @Override
    public HoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hoadon, parent, false);
        return new HoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonViewHolder holder, int position) {
        HoaDon hd = hoaDonList.get(position);

        holder.tvTen.setText("Bệnh nhân: " + hd.getTenBenhNhan());
        holder.tvNgaySinh.setText("Ngày sinh: " + hd.getNgaySinh());
        holder.tvSdt.setText("SĐT: " + hd.getSdt());
        holder.tvKhoa.setText("Khoa: " + hd.getKhoaKham());
        holder.tvTinhTrang.setText("Tình trạng: " + hd.getTinhTrang());
        holder.tvNgayTao.setText("Ngày tạo: " + hd.getNgayTao());

        String tongTienFormatted = NumberFormat.getNumberInstance(new Locale("vi", "VN"))
                .format(hd.getTongTien()) + " VNĐ";
        holder.tvTongTien.setText("Tổng tiền: " + tongTienFormatted);

        // Xử lý xóa
        holder.btnXoa.setOnClickListener(v -> {
            db.deleteHoaDon(hd.getId());
            hoaDonList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, hoaDonList.size());
            Toast.makeText(context, "Đã xóa hóa đơn", Toast.LENGTH_SHORT).show();
        });

        // Xử lý in
        holder.btnIn.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("In hóa đơn")
                    .setMessage("Bạn muốn in hóa đơn này?")
                    .setPositiveButton("OK", (dialog, which) -> {
                        // Giả lập hành động in
                        Toast.makeText(context, "Đang in hóa đơn...", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return hoaDonList.size();
    }

    public static class HoaDonViewHolder extends RecyclerView.ViewHolder {
        TextView tvTen, tvNgaySinh, tvSdt, tvKhoa, tvTinhTrang, tvNgayTao, tvTongTien;
        Button btnXoa, btnIn;

        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tvTenBenhNhan);
            tvNgaySinh = itemView.findViewById(R.id.tvNgaySinhHD);
            tvSdt = itemView.findViewById(R.id.tvSDT);
            tvKhoa = itemView.findViewById(R.id.tvKhoa);
            tvTinhTrang = itemView.findViewById(R.id.tvTinhTrang);
            tvNgayTao = itemView.findViewById(R.id.tvNgayTao);
            tvTongTien = itemView.findViewById(R.id.tvTongTien);
            btnXoa = itemView.findViewById(R.id.btnXoa);
            btnIn = itemView.findViewById(R.id.btnIn);
        }
    }
}

