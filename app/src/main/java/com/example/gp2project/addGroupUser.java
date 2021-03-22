package com.example.gp2project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class addGroupUser extends AppCompatActivity {

    EditText addUser;
    DatabaseReference db,db2;
    Intent intent;
    String GroupKey,addUserName,username,email,userKey;
    FirebaseAuth auth;
    boolean isExist,isMember,isAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_member_page);




        addUser=findViewById(R.id.groupNameTextBox);
        intent=getIntent();
        auth = FirebaseAuth.getInstance();
        isExist=false;
        isMember=false;
        isAdded=true;

        if (intent.hasExtra("key")) {
            GroupKey = intent.getStringExtra("key");

        }

    }


    public void validateUser(View view){

        addUserName = addUser.getText().toString();

        if(addUserName.isEmpty()){
            addUser.setError("الرجاء ادخال اسم مستخدم");
        }
        else if(addUserName.length()>25){
            addUser.setError("اسم المستخدم لايتعدى 25 حرف ");
        }
        else {
            isExistInDB();
        }

        isExist=false;
        isMember=false;
        isAdded=true;
    }


    public void add(String username,String email,String userKey){

        db = FirebaseDatabase.getInstance().getReference().child("Groups").child(GroupKey).child("Members");
        HashMap<String, String> map = new HashMap<>();
        map.put("Name",username );
        map.put("Mail", email);
        map.put("role","عضو");
        db.child(userKey).setValue(map);
        Toast.makeText(getApplicationContext(), "تمت الاضافة بنجاح ", Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(this,SettingGroup.class));
        this.finish();
    }

    public void isExistInDB() {


        db = FirebaseDatabase.getInstance().getReference().child("user");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(isAdded) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.child("username").getValue().equals(addUser.getText().toString())) {
                            isExist = true;
                            username = snapshot.child("username").getValue().toString();
                            email = snapshot.child("email").getValue().toString();
                            userKey = snapshot.getKey();
                            isMember();
                        }
                    }
                    if (!isExist) {

                        addUser.setError("اسم مستخدم غير موجود ");
                    }
                }}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });



    }

    public void isMember(){

        db2 = FirebaseDatabase.getInstance().getReference().child("Groups").child(GroupKey).child("Members");
        db2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(isAdded){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        if (snapshot.child("Name").getValue().equals(addUser.getText().toString())) {
                            Log.w("s", "On data change");
                            isMember = true;
                        }
                    }
                    iff();
                }}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


    public void iff(){

        if(isMember){

            addUser.setError("اسم مستخدم مضاف مسبقاً");

        }
        else{
            add(username, email, userKey);
            isAdded=false;
            isMember=false;

        }
    }

}