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


public class AdapterHomeItems extends RecyclerView.Adapter<AdapterHomeItems.Holder> {
    Context context;
    ArrayList<DevModel> list;

    public AdapterHomeItems(Context context, ArrayList<DevModel> list) {
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
                Intent intent=new Intent(context, ItemPage.class).putExtra("Mac", list.get(position).getMac())
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("item", list.get(position).getName());
                context.startActivity(intent);
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