package com.example.doanphongkham.fragmentBacsi;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.doanphongkham.ActivityBacsi.SuaThongtinBenhNhanActivity;
import com.example.doanphongkham.ActivityBacsi.ThemThongtinBenhNhanActivity;
import com.example.doanphongkham.Adapter.KhachHangAdapter;
import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.Model.KhachHang;
import com.example.doanphongkham.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class HoSoBenhNhanFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView rcv;
    KhachHangAdapter adapter;
    List<KhachHang> khachHangList;
    DatabaseHelper db;
    FloatingActionButton fabAdd;
    FloatingActionButton fabAddHosobenhnhan;
    public HoSoBenhNhanFragment() {
        // Required empty public constructor
    }


    public static HoSoBenhNhanFragment newInstance(String param1, String param2) {
        HoSoBenhNhanFragment fragment = new HoSoBenhNhanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ho_so_benh_nhan, container, false);

        rcv = view.findViewById(R.id.rcvHosobenhnhan);
        fabAdd = view.findViewById(R.id.fabAddHosobenhnhan);
        db = new DatabaseHelper(getContext());

        khachHangList = db.getAllKhachHang();
        adapter = new KhachHangAdapter(khachHangList);

        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv.setAdapter(adapter);

        // DELETE
        adapter.setOnDeleteClickListener(khachHang -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Xác nhận xoá")
                    .setMessage("Bạn có chắc muốn xoá bệnh nhân \"" + khachHang.getTenKH() + "\"?")
                    .setPositiveButton("Xoá", (dialog, which) -> {
                        boolean deleted = db.deleteKhachHang(khachHang.getId());
                        if (deleted) {
                            // Xóa theo id để chắc chắn
                            for (int i = 0; i < khachHangList.size(); i++) {
                                if (khachHangList.get(i).getId() == khachHang.getId()) {
                                    khachHangList.remove(i);
                                    adapter.notifyItemRemoved(i);
                                    break;
                                }
                            }
                            Toast.makeText(getContext(), "Đã xoá", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Xoá thất bại", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Huỷ", null)
                    .show();
        });

        //UPDATE
        adapter.setOnEditClickListener(khachHang -> {
            Intent intent = new Intent(getContext(), SuaThongtinBenhNhanActivity.class);
            intent.putExtra("id", khachHang.getId());
            intent.putExtra("ten", khachHang.getTenKH());
            intent.putExtra("sdt", khachHang.getSdt());
            intent.putExtra("gioiTinh", khachHang.getGioiTinh());
            intent.putExtra("ngaySinh", khachHang.getNgaySinh());
            intent.putExtra("diaChi", khachHang.getDiaChi());
            intent.putExtra("tienSu", khachHang.getTienSuBenh());
            startActivity(intent);
        });
        //xem thong tin benh nhan
        adapter.setOnItemClickListener(khachHang -> {
            String message = "Họ tên: " + khachHang.getTenKH() + "\n"
                    + "SĐT: " + khachHang.getSdt() + "\n"
                    + "Giới tính: " + khachHang.getGioiTinh() + "\n"
                    + "Ngày sinh: " + khachHang.getNgaySinh() + "\n"
                    + "Địa chỉ: " + khachHang.getDiaChi() + "\n"
                    + "Tiền sử bệnh: " + khachHang.getTienSuBenh();

            new AlertDialog.Builder(getContext())
                    .setTitle("Thông tin bệnh nhân")
                    .setMessage(message)
                    .setPositiveButton("Đóng", null)
                    .show();
        });

        // Nút thêm hồ sơ bệnh nhân
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ThemThongtinBenhNhanActivity.class);
            startActivity(intent);
        });

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        // Refresh danh sách sau khi thêm
        khachHangList.clear();
        khachHangList.addAll(db.getAllKhachHang());
        adapter.notifyDataSetChanged();
    }
}