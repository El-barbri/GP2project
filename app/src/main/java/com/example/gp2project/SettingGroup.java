package com.example.gp2project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SettingGroup extends AppCompatActivity {

    Intent intent;
    String name;
    String key;
    String AdminKey;
    Button GroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_sitting_page);

        intent = getIntent();
        GroupName = findViewById(R.id.reprttext);
        if (intent.hasExtra("Name")) {
            name = intent.getStringExtra("Name");
            key = intent.getStringExtra("key");
            AdminKey = intent.getStringExtra("AdminKey");

            GroupName.setText(name);
        }

    }

    public void AddMember(View view) {
        startActivity(new Intent(this, SelectUser.class).putExtra("key", key)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public void AddItem(View view) {
        startActivity(new Intent(this, SelectUser.class).putExtra("Key", key)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}