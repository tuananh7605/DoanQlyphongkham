package com.example.doanphongkham.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanphongkham.Model.KhachHang;
import com.example.doanphongkham.R;

import java.util.List;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.KhachHangViewHolder> {

    private List<KhachHang> khachHangList;

    public KhachHangAdapter(List<KhachHang> khachHangList) {
        this.khachHangList = khachHangList;
    }

    @NonNull
    @Override
    public KhachHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_hosobenhnhan, parent, false);
        return new KhachHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhachHangViewHolder holder, int position) {
        KhachHang kh = khachHangList.get(position);
        holder.tvTen.setText(kh.getTenKH());
        holder.tvSDT.setText("SĐT: " + kh.getSdt());
        holder.tvTienSu.setText("Tiền sử: " + kh.getTienSuBenh());

        holder.btnDelete.setOnClickListener(v -> {
            if (deleteClickListener != null) {
                deleteClickListener.onDeleteClick(kh);
            }
        });

        holder.btnEdit.setOnClickListener(v -> {
            if (editClickListener != null) {
                editClickListener.onEditClick(kh);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(kh);
            }
        });


    }

    @Override
    public int getItemCount() {
        return khachHangList.size();
    }

    public static class KhachHangViewHolder extends RecyclerView.ViewHolder {
        TextView tvTen, tvSDT, tvTienSu;
        ImageButton btnDelete;
        ImageButton btnEdit;
        public KhachHangViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.textViewTenbenhnhan);
            tvSDT = itemView.findViewById(R.id.textViewSDT);
            tvTienSu = itemView.findViewById(R.id.textViewTiensubenh);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
    public interface OnDeleteClickListener {
        void onDeleteClick(KhachHang khachHang);
    }

    private OnDeleteClickListener deleteClickListener;

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteClickListener = listener;
    }

    public interface OnEditClickListener {
        void onEditClick(KhachHang khachHang);
    }

    private OnEditClickListener editClickListener;

    public void setOnEditClickListener(OnEditClickListener listener) {
        this.editClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(KhachHang khachHang);
    }

    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }



}

