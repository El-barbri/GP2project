package com.example.gp2project;

import android.app.Activity;
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
    RecyclerView recItems, recUsers;
    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;



        setContentView(R.layout.activity_select_user);
        rec = findViewById(R.id.rec);


        intent = getIntent();
        auth = FirebaseAuth.getInstance();
        Key = intent.getStringExtra("key");



        if (intent.hasExtra("key")) {
            setContentView(R.layout.activity_select_user);
            rec = findViewById(R.id.rec);}

        if (intent.hasExtra("key")) {
            Key = intent.getStringExtra("key");

          //  Init();
            //Init();

        } else {
            Key = intent.getStringExtra("Key");
            setContentView(R.layout.activity_select_user);
            rec = findViewById(R.id.rec);
            InitItems();
        }


        if(intent.hasExtra("type")) {
            if (intent.getStringExtra("type").equals("deleteMember")) {
                setContentView(R.layout.delete_memmber_page);
                recUsers = findViewById(R.id.deleterec);
                Deleteuser();

            }
        }

        if(intent.hasExtra("type")) {
            if (intent.getStringExtra("type").equals("deleteGItem")) {
                setContentView(R.layout.delete_item_page);
                recItems = findViewById(R.id.deleteItemrec);
                DeleteGitem();


            }
        }


    }

    private void DeleteGitem() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("Groups").child(Key).child("Items");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DevModel dev = new DevModel();
                    dev.setKey(dataSnapshot.getKey());
                    dev.setName(dataSnapshot.child("Name").getValue(String.class));
                    dev.setMac(dataSnapshot.child("Mac").getValue(String.class));

                    list2.add(dev);
                }

                AdabterItemGroup adapterHproducts = new AdabterItemGroup(getBaseContext(), list2, Key ,activity);
                recItems.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                recItems.setAdapter(adapterHproducts);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


            private void Deleteuser() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("Groups").child(Key).child("Members");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                  if(dataSnapshot.child("role").getValue(String.class).equals("عضو") ){
                    user dev = new user();
                    dev.setKey(dataSnapshot.getKey());
                    dev.setUsername(dataSnapshot.child("Name").getValue(String.class));
                    list.add(dev);
                }}

                AdabterMemberGroup adapterHproducts = new AdabterMemberGroup(getBaseContext(), list, Key ,activity);
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