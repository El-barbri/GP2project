package com.example.gp2project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class AdapterGroups extends RecyclerView.Adapter<AdapterGroups.Holder> {
    Context context;
    ArrayList<GroupModel> list;

    public AdapterGroups(Context context, ArrayList<GroupModel> list) {
        this.context = context;
        this.list = list;
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
                context.startActivity(new Intent(context, InfoGroup.class)
                        .putExtra("Name", list.get(position).getName())
                        .putExtra("key", list.get(position).getKey())
                        .putExtra("AdminKey", list.get(position).getUserAdminKey()).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

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