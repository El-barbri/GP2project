package com.example.gp2project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ItemPage extends AppCompatActivity {

    String Mac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_page);

        Intent intent = getIntent();
        if (intent.hasExtra("Mac")) {
            Mac = intent.getStringExtra("Mac");
            // Toast.makeText(this, Mac, Toast.LENGTH_SHORT).show();
        }


    }


    public void Track(View view) {

        startActivity(new Intent(this, ScanResult.class).putExtra("Mac", Mac)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}