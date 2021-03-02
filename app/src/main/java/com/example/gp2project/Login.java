package com.example.gp2project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText  email  , password;
    private Button loginbutton;
    private ProgressBar progressBar;
    private TextView registertext , forgetPassword;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        mAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBarLogin);
        loginbutton=findViewById(R.id.loginButton);
        registertext =findViewById(R.id.new_user);
        forgetPassword=findViewById(R.id.forgetPassword);


        //startActivity(new Intent(getApplicationContext(), MainActivity.class));


        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String umail =email.getText().toString().trim();
                String upass =password.getText().toString().trim();
                database=FirebaseDatabase.getInstance();
                ref=FirebaseDatabase.getInstance().getReference().child("user");


                if (TextUtils.isEmpty(umail)) {
                    email.setError("الرجاء إدخال البريد الإلكتروني ");
                    return;
                }

                if(TextUtils.isEmpty(upass)) {
                    password.setError("الرجاء إدخال كلمة المرور ");
                    return;
                }

                if(upass.length() < 8){
                    password.setError("يجب  إدخال كلمة المرور أكثر من 8 خانات  ");
                    return;
                }
                ref.orderByChild("email").equalTo(umail).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()){
                            Toast.makeText(Login.this, " خطأ في البريد الإلكتروني أو كلمة المرور ",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else {
                            ref.orderByChild("password").equalTo(upass).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (!snapshot.exists()){
                                        Toast.makeText(Login.this, " خطأ في البريد الإلكتروني أو كلمة المرور ",Toast.LENGTH_SHORT).show();
                                        return;
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

                mAuth.signInWithEmailAndPassword(umail,upass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {

                            Log.d("TAG", "signinWithEmail:success");
                            Toast.makeText(Login.this, "تم التسجيل بنجاح ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else {
                            Toast.makeText(Login.this, "حدث خطأ ", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
        registertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
        forgetPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText enterEmail=new EditText(v.getContext());
                AlertDialog.Builder PasswordRestDialog = new AlertDialog.Builder(v.getContext());
                PasswordRestDialog.setTitle("آعادة ضبط كلمة المرور ");
                PasswordRestDialog.setMessage("ادخل البريد الالكتروني الخاص بك ليتم ارسال رابط اعادة ضبط كلمة المرور");
                PasswordRestDialog.setView(enterEmail);
                PasswordRestDialog.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email = enterEmail.getText().toString();
                        mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this, " تم ارسال الرابط الى بريدك الالكتروني ",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this,"لم يتم ارسال الرابط بنجاح" + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                PasswordRestDialog.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                PasswordRestDialog.create().show();
            }
        });


    }

    //public void Login(View view)
    // {
    // startActivity(new Intent(this, MainActivity.class));
    //}

    // public void Register(View view)
    // {
    // startActivity(new Intent(this, Register.class));
    // }
}
