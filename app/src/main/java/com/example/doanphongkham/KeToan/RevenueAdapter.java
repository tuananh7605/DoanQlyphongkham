package com.example.doanphongkham.KeToan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanphongkham.R;

import java.util.List;

public class RevenueAdapter extends RecyclerView.Adapter<RevenueAdapter.ViewHolder> {

    private List<RevenueItem> list;

    public RevenueAdapter(List<RevenueItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_doanh_thu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RevenueItem item = list.get(position);
        holder.tvDate.setText("Ngày: " + item.getDate());
        holder.tvAmount.setText("Số tiền: " + item.getAmount() + " VNĐ");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvNgayDoanhThu);
            tvAmount = itemView.findViewById(R.id.tvSoTien);
        }
    }
}
