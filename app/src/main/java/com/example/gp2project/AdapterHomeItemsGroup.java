package com.example.gp2project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;


public class AdapterHomeItemsGroup extends RecyclerView.Adapter<AdapterHomeItemsGroup.Holder> {
    Context context;
    ArrayList<DevModel> list;
    String Key;
    private DatabaseReference mData;

    public AdapterHomeItemsGroup(Context context, ArrayList<DevModel> list, String key) {
        this.context = context;
        this.list = list;
        this.Key = key;

    }

    public Holder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new Holder(LayoutInflater.from(this.context).inflate(R.layout.items, viewGroup, false));
    }

    public void onBindViewHolder(final Holder holder, final int position) {

        // handle action click on adapter

        holder.name.setText(list.get(position).getName());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddItem(position);

            }
        });

    }


    public void AddItem(int position) {
        if (!Key.equals("")) {
            mData = FirebaseDatabase.getInstance().getReference().child("Groups").child(Key).child("Items");
            HashMap<String, String> map = new HashMap<>();
            map.put("Name", list.get(position).getName());
            map.put("Mac", list.get(position).getMac());
            mData.child(list.get(position).getKey()).setValue(map);
            Toast.makeText(context, "تمت الاضافة بنجاح ", Toast.LENGTH_SHORT).show();
        }
    }

    public int getItemCount() {
        return list.size();
    }


    class Holder extends RecyclerView.ViewHolder {
        TextView Name, det;
        Button name;

        public Holder(View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.useritem);
        }

    }

}