package com.example.gp2project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddItem extends AppCompatActivity {

    EditText name;
    FirebaseAuth auth;
    String mac;
    private DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);


        name = findViewById(R.id.editTextTextPersonName);
        auth = FirebaseAuth.getInstance();
        Intent intent = getIntent();

        if (intent.hasExtra("mac")) {
            mac = intent.getStringExtra("mac");
        }
    }

    public void Add(View view) {

        mData = FirebaseDatabase.getInstance().getReference().child("Item").child(auth.getUid());
        HashMap<String, String> map = new HashMap<>();
        map.put("Name", name.getText().toString());
        map.put("Mac", mac);
        mData.push().setValue(map);
        Toast.makeText(this, "تم اضافة الممتلك بنجاح", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this,MainActivity.class));
    }
}