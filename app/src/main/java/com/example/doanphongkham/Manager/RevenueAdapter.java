package com.example.doanphongkham.Manager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanphongkham.R;

import java.util.ArrayList;

public class RevenueAdapter extends RecyclerView.Adapter<RevenueAdapter.RevenueViewHolder> {

    private ArrayList<RevenueItem> revenueList;

    public RevenueAdapter(ArrayList<RevenueItem> revenueList) {
        this.revenueList = revenueList;
    }

    @NonNull
    @Override
    public RevenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_revenue_item, parent, false);
        return new RevenueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RevenueViewHolder holder, int position) {
        RevenueItem item = revenueList.get(position);
        holder.tvDate.setText(item.getDate());
        holder.tvType.setText(item.getType());
        holder.tvAmount.setText(String.format("%,d đ", item.getAmount()));
    }

    @Override
    public int getItemCount() {
        return revenueList.size();
    }

    public static class RevenueViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvType, tvAmount;

        public RevenueViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvType = itemView.findViewById(R.id.tvType);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }
}
