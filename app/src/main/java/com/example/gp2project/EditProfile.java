package com.example.gp2project;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.app.PendingIntent.getActivity;

public class EditProfile  extends AppCompatActivity {

    public static final String TAG = "TAG";
    private EditText usernameText, phoneText, emailText ,currentpass,newpass;
    private ImageButton logout;
    private FirebaseAuth Auth;
    private DatabaseReference reference;
    private String profileusername, profileemail, profilephonNum ;
    private boolean flag;
    private boolean flag1;
    private String username, email, phonNum;
    private Button changepass ,changeToNew;
    private FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editinfo_page);
        //access the data that is send
        Intent data = getIntent();
        profileusername = data.getStringExtra("username");
        profileemail = data.getStringExtra("email");
        profilephonNum = data.getStringExtra("phonNum");



        Log.d(TAG, "تم الوصول الى صفحة تعديل المعلومات مع " + profileusername + " " + profileemail + " " + profilephonNum);

        //pointer
        usernameText = findViewById(R.id.username);
        phoneText = findViewById(R.id.phonenum);
        emailText = findViewById(R.id.email);
        logout = findViewById(R.id.logout);
        changepass = findViewById(R.id.passwordset);




        //declared authentication
        Auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("user");

        //set edit text
        emailText.setText(profileemail);
        phoneText.setText(profilephonNum);


        //logout button
        logout.setOnClickListener(v -> {
            if (Auth.getCurrentUser() != null) {
                Log.d("TAG", "log out and start the activity login");
                Auth.signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));


            }
        });

        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.password_page , null);
                currentpass =view.findViewById(R.id.curentpass);
                newpass =view.findViewById(R.id.newpass);


                AlertDialog.Builder PasswordRestDialog = new AlertDialog.Builder(v.getContext());
                //   PasswordRestDialog.setTitle("آعادة ضبط كلمة المرور ");
                //  PasswordRestDialog.setMessage("ادخل البريد الالكتروني الخاص بك ليتم ارسال رابط اعادة ضبط كلمة المرور");
                PasswordRestDialog.setView(view);
                PasswordRestDialog.setPositiveButton("تغيير", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String old = currentpass.getText().toString();
                        String neww = newpass.getText().toString();

                        if(TextUtils.isEmpty(currentpass.getText().toString())){
                            currentpass.setError("ادخل كلمة المرور السابقة من فضلك");
                            return;
                        }
                        if (currentpass.getText().toString().length() <8){
                            currentpass.setError("يجب  إدخال كلمة المرور أكثر من 8 خانات  ");
                            return;
                        }
                        if(TextUtils.isEmpty(newpass.getText().toString())){
                            newpass.setError("ادخل كلمة المرور الجديدة من فضلك");
                            return;
                        }
                        if (newpass.getText().toString().length() <8){
                            newpass.setError("يجب  إدخال كلمة المرور أكثر من 8 خانات  ");
                            return;
                        }
                        user=Auth.getCurrentUser();
                        AuthCredential authCredential= EmailAuthProvider.getCredential(user.getEmail(),old);
                        user.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //successfully authenticated ,begin update
                                user.updatePassword(neww).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //Password update
                                        Toast.makeText(getApplicationContext(), "تم تعديل كلمة المرور",Toast.LENGTH_SHORT).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //failed updating password! ,show reason
                                        Toast.makeText(getApplicationContext(), ""+e.getMessage(),Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }
                        });

                    }
                });

                PasswordRestDialog.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                PasswordRestDialog.create().show();

            }
        });


    }



    public void update(View view) {
        if (isEmailexist()){
            if (isEmailchange()){
                Auth.getCurrentUser().updateEmail(emailText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        reference.child(Auth.getCurrentUser().getUid()).child("email").setValue(emailText.getText().toString());
                        Toast.makeText(EditProfile.this, "تم حفظ التعديلات للبريد الالكتروني  ", Toast.LENGTH_SHORT).show();


                    }

                });

            }}
        else {
            Toast.makeText(EditProfile.this, "لم يتم حفظ التعديلات للبريد الالكتروني  ", Toast.LENGTH_SHORT).show();
        }
        if( isPhonechange()) {

            reference.child(Auth.getCurrentUser().getUid()).child("phonNum").setValue(phoneText.getText().toString());
            Toast.makeText(EditProfile.this, "تم حفظ التعديلات لرقم الهاتف ", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(EditProfile.this, "لم يتم حفظ التعديلات لرقم الهاتف ", Toast.LENGTH_SHORT).show();
        }

    }
/*
    private boolean isPhoneexist() {
        flag1= true;
        if (! profilephonNum.equals(phoneText.getText().toString())) {
            reference.orderByChild("phonNum").equalTo(phoneText.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        Log.w("TAG", "createUserWithphone:failure" + snapshot);
                        phoneText.setError("رقم الهاتف موجود مسبقًا");
                        phoneText.requestFocus();
                        flag1=false;
                        return;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else {
            flag1=false;
        }
        return flag1;
    }
*/

    private boolean isEmailexist() {
        flag= true;
        if (! profileemail.equals(emailText.getText().toString())) {
            reference.orderByChild("email").equalTo(emailText.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        Log.w("TAG", "createUserWithUsernam:failure" + snapshot);
                        emailText.setError("البريد الالكتروني موجود مسبقًا");
                        emailText.requestFocus();
                        flag=false;

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else {
            flag=false;
        }
        return flag;
    }

    private boolean isEmailchange() {
        if (!profileemail.equals(emailText.getText().toString())) {
            if (TextUtils.isEmpty(emailText.getText().toString())) {
                emailText.setError("الرجاء إدخال البريد الإلكتروني ");
                return false;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(emailText.getText().toString()).matches()){
                emailText.setError("الرجاء إدخال بريد إلكتروني صالح ");
                return false;
            }
        }
        else{
            return false;
        }

        return true;
    }

    private boolean isPhonechange() {
        if (! profilephonNum.equals(phoneText.getText().toString())) {

            if(TextUtils.isEmpty(phoneText.getText().toString())){
                phoneText.setError("الرجاء إدخال رقم الهاتف ");
                return false;
            }
            if(phoneText.length()<10){
                phoneText.setError("الرجاء إدخال رقم هاتف أكثر من 10 خانات ");
                return false;
            }

        }
        else{
            return false;
        }

        return true;
    }



}