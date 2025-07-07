package com.example.doanphongkham.ActivityBacsi;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.doanphongkham.R;
import com.example.doanphongkham.fragmentBacsi.HoSoBenhNhanFragment;
import com.example.doanphongkham.fragmentBacsi.LichdakhamFragment;
import com.example.doanphongkham.fragmentBacsi.LichkhamFragment;
import com.example.doanphongkham.fragmentBacsi.TaiKhoanBacsiFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);



        //anh xa
        bottomNavigationView = findViewById(R.id.bottomNavigationMain);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.menuitemLichkham)
                {
                    setFragment(new LichkhamFragment());
                    return true;
                }
                if(item.getItemId() == R.id.menuitemLichdakham)
                {
                    setFragment(new LichdakhamFragment());
                    return true;
                }
                if(item.getItemId() == R.id.menuitemHosobenhnhan)
                {
                    setFragment(new HoSoBenhNhanFragment());
                    return true;
                }
                if(item.getItemId() == R.id.menuitemProfile)
                {
                    setFragment(new TaiKhoanBacsiFragment());
                    return true;
                }
                return false;
            }
        });
        setFragment(new LichkhamFragment());
        bottomNavigationView.setSelectedItemId(R.id.menuitemLichkham);
    }

    void setFragment(Fragment newFragment){
        // Create new Fragment and transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //Replace whatever is in the fragment_content view with fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.contentLayoutMain, newFragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }
}