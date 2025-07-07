package com.example.doanphongkham.fragmentBacsi;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doanphongkham.ActivityBacsi.ThemLichKhamActivity;
import com.example.doanphongkham.ActivityBacsi.ThemThongtinBenhNhanActivity;
import com.example.doanphongkham.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LichkhamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LichkhamFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LichkhamFragment() {
        // Required empty public constructor
    }


    public static LichkhamFragment newInstance(String param1, String param2) {
        LichkhamFragment fragment = new LichkhamFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lichkham, container, false);
        FloatingActionButton fabAddHoso = view.findViewById(R.id.fabAddlichhen);
        fabAddHoso.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ThemLichKhamActivity.class);
            startActivity(intent);
        });

        return view;
    }
}