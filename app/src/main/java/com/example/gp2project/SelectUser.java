package com.example.gp2project;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectUser extends AppCompatActivity {

    RecyclerView rec;
    ArrayList<user> list = new ArrayList<>();
    ArrayList<DevModel> list2 = new ArrayList<>();
    FirebaseAuth auth;
    String Key;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_user);
        rec = findViewById(R.id.rec);


        intent = getIntent();
        auth = FirebaseAuth.getInstance();

        if (intent.hasExtra("key")) {
            Key = intent.getStringExtra("key");
            Init();
        } else {
            Key = intent.getStringExtra("Key");
            InitItems();
        }






    }

    public void Init() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("user");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    user dev = new user();
                    dev.setKey(snapshot.getKey());
                    //dev.setEmail(snapshot.child("email").getValue(String.class));
                   // dev.setPhonNum(snapshot.child("phonNum").getValue(String.class));
                    dev.setUsername(snapshot.child("username").getValue(String.class));
                    list.add(dev);
                    //snapshot.child("City").getValue(String.class)
                }

                AdapterUsers adapterHproducts = new AdapterUsers(getBaseContext(), list, Key);
                rec.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                rec.setAdapter(adapterHproducts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void InitItems() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("Item").child(auth.getUid());
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

                AdapterHomeItemsGroup adapterHproducts = new AdapterHomeItemsGroup(getBaseContext(), list2, Key);
                rec.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));
                rec.setAdapter(adapterHproducts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}