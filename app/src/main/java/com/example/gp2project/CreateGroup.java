package com.example.gp2project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Random;

public class CreateGroup extends AppCompatActivity {

    EditText GroupName;
    FirebaseAuth auth;
    private DatabaseReference mData,mData2;
    String email,username;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_group_name_page);

        GroupName = findViewById(R.id.groupNameTextBox);
        auth = FirebaseAuth.getInstance();
        getAdminInfo();


    }

        public void CreateGroup (View view) {

        Random ran = new Random();
        int x=ran.nextInt(1000);
        key=""+x;

            mData = FirebaseDatabase.getInstance().getReference().child("Groups").child(key);

            HashMap<String, String> map = new HashMap<>();
            map.put("Name", GroupName.getText().toString());
            map.put("UserAdmin", auth.getUid());

            mData.setValue(map);
            addAdminToGroup();


            Toast.makeText(this, "تم اضافة المجموعة بنجاح", Toast.LENGTH_SHORT).show();
            this.finish();
            // Log.w("هناااااااااااا"," "+key);

    }

    public void getAdminInfo(){
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference reference = database.getReference("user");


    Query q=reference.orderByKey().equalTo(auth.getCurrentUser().getUid());
    q.addValueEventListener(new ValueEventListener(){

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            for(DataSnapshot dataSnapshot: snapshot.getChildren() ){
                username=""+dataSnapshot.child("username").getValue();
                email=""+dataSnapshot.child("email").getValue();
                // Log.w("هناااااااااااا"," "+username);
                // Log.w("هناااااااااااا"," "+email);
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    });


}

   public void addAdminToGroup(){

       mData2 = FirebaseDatabase.getInstance().getReference().child("Groups").child(key).child("Members").child(auth.getUid());
       HashMap<String, String> map1 = new HashMap<>();
       map1.put("Mail",email);
       map1.put("Name",username);
       map1.put("role","مشرف");
       mData2.setValue(map1);
   }
}