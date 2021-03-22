package com.example.gp2project;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;



import static android.view.LayoutInflater.*;




public class AdabterMemberGroup  extends RecyclerView.Adapter<AdabterMemberGroup.Holder> {
    Context context ;
    ArrayList<user> list;
    String Key;
    Activity app;
    private DatabaseReference mData;




    public AdabterMemberGroup(Context context, ArrayList<user> list, String key , Activity app) {
        this.context = context;
        this.list = list;
        this.Key = key;
        this.app=app;


    }



    @NonNull
    @Override
    public AdabterMemberGroup.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new Holder(from(this.context).inflate(R.layout.delete_user_shape ,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdabterMemberGroup.Holder holder, int position) {

        String username=list.get(position).getUsername();
        holder.name.setText(username);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference group= FirebaseDatabase.getInstance().getReference("Groups").child(Key).child("Members").child(list.get(position).getKey());

                View v = LayoutInflater.from(app).inflate(R.layout.delete_member_massage, null);
                final AlertDialog.Builder deletemember = new AlertDialog.Builder(app);
                deletemember.setView(v);
               // deletemember.setView(LayoutInflater.from(app).inflate(R.layout.delete_member_massage, null));

                deletemember.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        group.removeValue();
                        Toast.makeText(app, "تم حذف العضو بنجاح ", Toast.LENGTH_SHORT).show();
                        app.startActivity(new Intent(app, MainActivity.class));
                    }
                });
                deletemember.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(app, "تم الإلغاء بنجاح",Toast.LENGTH_SHORT).show();
                    }
                });


               deletemember.create().show();



            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView Name, det;
        Button name;

        public Holder(View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.deleteMember);
        }

    }


}

