package com.example.gp2project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.Holder> {


    Context context;
    ArrayList<user> list;
    String key,role;
    private DatabaseReference mData;
    FirebaseAuth auth;

    public AdapterUsers(Context context, ArrayList<user> list, String key) {
        this.context = context;
        this.list = list;
        this.key = key;
        //this.role =role;

    }

    public Holder onCreateViewHolder(ViewGroup viewGroup, int position) {
        auth = FirebaseAuth.getInstance();

        return new Holder(LayoutInflater.from(context).inflate(R.layout.user_shape, viewGroup, false));
    }

    public void onBindViewHolder(final Holder holder, final int position) {

        // handle action click on adapter
user user=list.get(position);
String name= user.getUsername();

        holder.Name.setText(name);
        // holder.role.setText(list.get(position).getEmail());
        mData=FirebaseDatabase.getInstance().getReference("Groups");
        mData.child(key).child("Members").child(user.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                String r=""+snapshot.child("role").getValue();
                holder.role.setText(r);
            }}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });









    }


    public void  AddMember(int position) {
        if (!key.equals("")) {
            mData = FirebaseDatabase.getInstance().getReference().child("Groups").child(key).child("Members");
            HashMap<String, String> map = new HashMap<>();
            map.put("Name", list.get(position).getUsername());
            map.put("Mail", list.get(position).getEmail());
            map.put("role","عضو");
            mData.child(list.get(position).getKey()).setValue(map);
            Toast.makeText(context, "تمت الاضافة بنجاح ", Toast.LENGTH_SHORT).show();
        }
    }

    public int getItemCount() {
        return list.size();
    }


    class Holder extends RecyclerView.ViewHolder {
        TextView Name, role;

        public Holder(View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.userName);
            role = itemView.findViewById(R.id.user_role);

        }

    }

}