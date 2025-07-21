package com.example.doanphongkham.fragmentBacsi;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doanphongkham.Adapter.LichKhamAdapter;
import com.example.doanphongkham.Database.DatabaseHelper;
import com.example.doanphongkham.Model.PhieuKhamBenh;
import com.example.doanphongkham.R;

import java.util.List;

public class LichdakhamFragment extends Fragment {

    RecyclerView rcvLichDaKham;
    DatabaseHelper databaseHelper;
    LichKhamAdapter adapter;

    public LichdakhamFragment() {
        // Required empty public constructor
    }

    public static LichdakhamFragment newInstance(String param1, String param2) {
        LichdakhamFragment fragment = new LichdakhamFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lichdakham, container, false);

        rcvLichDaKham = view.findViewById(R.id.rcvLichdakham);
        rcvLichDaKham.setLayoutManager(new LinearLayoutManager(getContext()));
        databaseHelper = new DatabaseHelper(getContext());

        loadData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(); // Load lại mỗi lần quay lại
    }

    private void loadData() {
        List<PhieuKhamBenh> list = databaseHelper.getLichDaKham();
        if (adapter == null) {
            adapter = new LichKhamAdapter(getContext(), list, databaseHelper,true);
            rcvLichDaKham.setAdapter(adapter);
        } else {
            adapter.updateList(list);
        }
    }
}
