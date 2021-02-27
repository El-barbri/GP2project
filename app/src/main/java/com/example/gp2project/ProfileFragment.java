package com.example.gp2project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.Executor;


public class ProfileFragment extends Fragment {

    private TextView  phone,email,username;
    private FirebaseAuth Auth;
    private FirebaseDatabase db;
    private DatabaseReference ref;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View profile  =inflater.inflate(R.layout.fragment_profile,container,false);
        username=(TextView)profile.findViewById(R.id.username);
        email=(TextView)profile.findViewById(R.id.username2);
        phone=(TextView)profile.findViewById(R.id.username3);


        Auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        ref=db.getReference("user");



        Query q=ref.orderByKey().equalTo(Auth.getCurrentUser().getUid());
        q.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren() ){
                    String username1=""+dataSnapshot.child("username").getValue();
                    String email1=""+dataSnapshot.child("email").getValue();
                    String phone1=""+dataSnapshot.child("phonNum").getValue();
                    username.setText(username1);
                    email.setText(email1);
                    phone.setText(phone1);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });






        return profile;

    }}
