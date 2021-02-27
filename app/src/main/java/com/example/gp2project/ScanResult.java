package com.example.gp2project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.animation.AnimationUtils;

public class ScanResult extends AppCompatActivity {

    RecyclerView rec;
    public static AdapterItems adapterHproducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);


        rec                     = findViewById(R.id.rec);

        // init
        Init();

    }

    public void Init()
    {
         adapterHproducts = new AdapterItems(this);
        rec.setLayoutManager(new LinearLayoutManager(this));
        rec.setAdapter(adapterHproducts);
      //  recycle_animation(rec);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.devList.clear();
    }
}