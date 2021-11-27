package com.example.krishworkscodinground;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.krishworkscodinground.Fragments.AddData;
import com.example.krishworkscodinground.Fragments.ReadData;
import com.example.krishworkscodinground.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentTransaction readTrans = getSupportFragmentManager().beginTransaction();
        readTrans.replace(R.id.frame , new ReadData());
        binding.bottomNav.getMenu().findItem(R.id.read).setChecked(true);
        readTrans.commit();





        binding.bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.add :
                        FragmentTransaction addTrans = getSupportFragmentManager().beginTransaction();
                        addTrans.replace(R.id.frame , new AddData());
                        binding.bottomNav.getMenu().findItem(R.id.add).setChecked(true);
                        addTrans.commit();
                        break;

                    case R.id.read :
                        FragmentTransaction readTrans = getSupportFragmentManager().beginTransaction();
                        readTrans.replace(R.id.frame , new ReadData());
                        binding.bottomNav.getMenu().findItem(R.id.read).setChecked(true);
                        readTrans.commit();
                        break;
                }

                return false;
            }
        });
    }
}
/*
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".AddData"
    >
    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/list"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_above="@id/trigger"/>
        <Button
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:layout_alignParentBottom="true"
            android:id="@+id/trigger"
            android:text="Trigger"
            android:layout_marginLeft="30dp"
            android:layout_marginRight="30dp"/>

    </RelativeLayout>
</FrameLayout>
 */