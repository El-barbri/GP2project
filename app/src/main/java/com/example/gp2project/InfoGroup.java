package com.example.gp2project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InfoGroup extends AppCompatActivity {

    Intent intent;
    String name;
    String key;
    String AdminKey;
    RecyclerView recItems, recUsers;
    ArrayList<user> list = new ArrayList<>();
    ArrayList<DevModel> list2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_group_page);

        intent = getIntent();
        recUsers = findViewById(R.id.recyclerView2);
        recItems = findViewById(R.id.recyclerView);

        if (intent.hasExtra("Name")) {
            name = intent.getStringExtra("Name");
            key = intent.getStringExtra("key");
            AdminKey = intent.getStringExtra("AdminKey");
        }

        initUsers();
        InitItems();

    }

    public void Settings(View view) {
        startActivity(new Intent(this, SettingGroup.class).putExtra("Name", name)
                .putExtra("key", key)
                .putExtra("AdminKey", AdminKey).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }


    public void initUsers() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("Groups").child(key).child("Members");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    user dev = new user();
                    dev.setKey(snapshot.getKey());
                    dev.setEmail(snapshot.child("Mail").getValue(String.class));
                    dev.setPhonNum(snapshot.child("phonNum").getValue(String.class));
                    dev.setUsername(snapshot.child("Name").getValue(String.class));
                    list.add(dev);
                    //snapshot.child("City").getValue(String.class)
                }

                AdapterUsers adapterHproducts = new AdapterUsers(getBaseContext(), list, "");
                recUsers.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                recUsers.setAdapter(adapterHproducts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void InitItems() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("Groups").child(key).child("Items");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list2.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DevModel dev = new DevModel();
                    dev.setKey(snapshot.getKey());
                    dev.setName(snapshot.child("Name").getValue(String.class));
                    dev.setMac(snapshot.child("Mac").getValue(String.class));
                    list2.add(dev);
                    //snapshot.child("City").getValue(String.class)
                }

                AdapterHomeItemsGroup adapterHproducts = new AdapterHomeItemsGroup(getBaseContext(), list2, "");
                recItems.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));
                recItems.setAdapter(adapterHproducts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}