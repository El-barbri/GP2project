package com.example.gp2project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private EditText username , email , phone , password;
    private Button registerbutton;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private FirebaseAuth mAuth;
    private static final String USER="user";
    private static final String TAG="Register";
    private user user;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        username=(EditText)findViewById(R.id.username);
        email=(EditText)findViewById(R.id.emailfiled);
        phone=(EditText)findViewById(R.id.edittextPhone);
        password=(EditText)findViewById(R.id.password);
        registerbutton=(Button)findViewById(R.id.registerbutton);

        database=FirebaseDatabase.getInstance();
        ref=FirebaseDatabase.getInstance().getReference().child("user");
        mAuth=FirebaseAuth.getInstance();


        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //register  the user in firebase
                String usernameAfter=username.getText().toString();
                String emailAfter=email.getText().toString();
                String phoneAfter=phone.getText().toString();
                String passwordAfter=password.getText().toString();
                //valedate the user
                if (TextUtils.isEmpty(usernameAfter)) {
                    username.setError("الرجاء إدخال اسم مستخدم ");
                    return;
                }
                if(usernameAfter.contains(" ")){
                    username.setError("الرجاء إدخال اسم مستخدم لا يحتوي على مسافة ");
                    return;
                }


                ref.orderByChild("username").equalTo(usernameAfter).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Log.w("TAG","createUserWithUsernam:failure"+ snapshot);
                            username.setError("اسم المسخدم موجود مسبقًا");
                            username.requestFocus();
                            return;
                        }
                        else{
                            ref.orderByChild("phonNum").equalTo(phoneAfter).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()) {
                                        Log.w("TAG", "createUserWithUsernam:failure" + snapshot);
                                        phone.setError("رقم الهاتف موجود مسبقًا");
                                        phone.requestFocus();
                                        return;
                                    }
                                    else {
                                        //valdate the email
                                        if (TextUtils.isEmpty(emailAfter)) {
                                            email.setError("الرجاء إدخال البريد الإلكتروني ");
                                            return;
                                        }
                                        if (!Patterns.EMAIL_ADDRESS.matcher(emailAfter).matches()){
                                            email.setError("الرجاء إدخال بريد إلكتروني صالح ");
                                            return;
                                        }
                                        if(TextUtils.isEmpty(phoneAfter)){
                                            phone.setError("الرجاء إدخال رقم الهاتف ");
                                            return;
                                        }
                                        if (passwordAfter.length() <8){
                                            password.setError("يجب  إدخال كلمة المرور أكثر من 8 خانات  ");
                                            return;
                                        }
                                        if(phoneAfter.length()<10){
                                            phone.setError("الرجاء إدخال رقم هاتف أكثر من 10 خانات ");
                                            return;
                                        }

                                        user=new user(usernameAfter,emailAfter,phoneAfter,passwordAfter);
                                        registerUser(emailAfter,passwordAfter);

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }



        });
    }



    public void registerUser(String e,String p){
        mAuth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.d("TAG","createUserWithEmail:success");
                    FirebaseUser userr=mAuth.getCurrentUser();
                    updateUI(userr);
                }
                else{
                    Log.w("TAG","createUserWithEmail:failure",task.getException());
                    Toast.makeText(Register.this, "عذراً هذا البريد الإلكتروني موجود مسبقا" , Toast.LENGTH_SHORT).show();
                    //progressBar.setVisibility(View.GONE);
                }

            }
        });
    }



    public void updateUI(FirebaseUser currentUser){
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("user");
        String keyId=mAuth.getCurrentUser().getUid();
        ref.child(keyId).setValue(user);
        startActivity(new Intent(getApplicationContext(), Login.class));
    }

}