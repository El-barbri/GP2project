package com.example.gp2project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;


public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.Holder> {


    Context context;
    ArrayList<user> list;
    String key;
    private DatabaseReference mData;

    public AdapterUsers(Context context, ArrayList<user> list, String key) {
        this.context = context;
        this.list = list;
        this.key = key;

    }

    public Holder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new Holder(LayoutInflater.from(this.context).inflate(R.layout.item_result, viewGroup, false));
    }

    public void onBindViewHolder(final Holder holder, final int position) {

        // handle action click on adapter

        holder.Name.setText(list.get(position).getUsername());
        holder.det.setText(list.get(position).getEmail());

        holder.det.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddMember(position);
            }
        });

        holder.Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddMember(position);

            }
        });

    }


    public void AddMember(int position) {
        if (!key.equals("")) {
            mData = FirebaseDatabase.getInstance().getReference().child("Groups").child(key).child("Members");
            HashMap<String, String> map = new HashMap<>();
            map.put("Name", list.get(position).getUsername());
            map.put("Mail", list.get(position).getEmail());
            mData.child(list.get(position).getKey()).setValue(map);
            Toast.makeText(context, "تمت الاضافة بنجاح ", Toast.LENGTH_SHORT).show();
        }
    }

    public int getItemCount() {
        return list.size();
    }


    class Holder extends RecyclerView.ViewHolder {
        TextView Name, det;

        public Holder(View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.textView6);
            det = itemView.findViewById(R.id.textView7);

        }

    }

}