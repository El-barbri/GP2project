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

public class CreateGroup extends AppCompatActivity {

    EditText GroupName;
    FirebaseAuth auth;
    private DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_group_name_page);

        GroupName = findViewById(R.id.groupNameTextBox);
        auth = FirebaseAuth.getInstance();

    }

    public void CreateGroup(View view) {

        mData = FirebaseDatabase.getInstance().getReference().child("Groups");
        HashMap<String, String> map = new HashMap<>();
        map.put("Name", GroupName.getText().toString());
        map.put("UserAdmin", auth.getUid());
        mData.push().setValue(map);
        Toast.makeText(this, "تم اضافة المجموعة بنجاح", Toast.LENGTH_SHORT).show();

    }
}