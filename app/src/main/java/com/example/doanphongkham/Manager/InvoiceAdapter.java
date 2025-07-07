package com.example.doanphongkham.Manager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.doanphongkham.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {

    public interface OnInvoiceClickListener {
        void onClick(Invoice invoice);
    }

    private final ArrayList<Invoice> originalList; // Danh sách gốc
    private final ArrayList<Invoice> filteredList; // Danh sách đang hiển thị
    private final OnInvoiceClickListener listener;

    public InvoiceAdapter(ArrayList<Invoice> list, OnInvoiceClickListener listener) {
        this.originalList = list;
        this.filteredList = new ArrayList<>(list); // Khởi tạo bản sao
        this.listener = listener;
    }

    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invoice, parent, false);
        return new InvoiceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceViewHolder holder, int position) {
        Invoice invoice = filteredList.get(position);
        holder.tvName.setText(invoice.name);
        holder.tvPhone.setText(invoice.phone);
        holder.tvDate.setText(invoice.date); // Thêm ngày nếu cần

        holder.itemView.setOnClickListener(v -> listener.onClick(invoice));
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    // Lọc theo từ khóa (tên hoặc số điện thoại)
    public void filterByKeyword(String keyword) {
        String lowerKeyword = keyword.toLowerCase(Locale.getDefault());
        filteredList.clear();

        if (lowerKeyword.isEmpty()) {
            filteredList.addAll(originalList);
        } else {
            for (Invoice invoice : originalList) {
                if (invoice.name.toLowerCase(Locale.getDefault()).contains(lowerKeyword)
                        || invoice.phone.toLowerCase(Locale.getDefault()).contains(lowerKeyword)) {
                    filteredList.add(invoice);
                }
            }
        }
        notifyDataSetChanged();
    }

    // Lọc theo ngày
    public void filterByDate(String date) {
        filteredList.clear();
        for (Invoice invoice : originalList) {
            if (invoice.date.equals(date)) {
                filteredList.add(invoice);
            }
        }
        notifyDataSetChanged();
    }

    // Cập nhật khi thêm mới
    public void notifyDataChanged() {
        filteredList.clear();
        filteredList.addAll(originalList);
        notifyDataSetChanged();
    }

    public static class InvoiceViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone, tvDate;

        public InvoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvDate = itemView.findViewById(R.id.tvDate); // Thêm trường này vào layout item_invoice.xml
        }
    }
}
