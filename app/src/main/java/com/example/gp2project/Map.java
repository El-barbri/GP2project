package com.example.gp2project;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class Map extends AppCompatActivity {

    TextView item_name , distance;
    ImageView phone_position , item_position;
    RecyclerView rec;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_item_on_map);

        item_name = findViewById(R.id.editTextTextItemName);
        distance = findViewById(R.id.editTextTextItemDis);
        phone_position = findViewById(R.id.phoneposition);
        item_position = findViewById(R.id.itemposition);
    }


}
