package com.example.gp2project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class AdapterItems extends RecyclerView.Adapter<AdapterItems.Holder> {


    Context context;

    public AdapterItems(Context context) {
        this.context = context;

    }
    public Holder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new Holder(LayoutInflater.from(this.context).inflate(R.layout.item_result, viewGroup, false));
    }

    public void onBindViewHolder(final Holder holder, final int position) {

        // handle action click on adapter

        holder.Name.setText(MainActivity.devList.get(position).getName());
        holder.det.setText(MainActivity.devList.get(position).getMac());

        holder.det.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, AddItem.class)
                        .putExtra("mac", MainActivity.devList.get(position).getMac()));
            }
        });

        holder.Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, AddItem.class).putExtra("mac", MainActivity.devList.get(position).getMac()));
            }
        });

    }

    public int getItemCount() {
        return MainActivity.devList.size();
    }




    class Holder extends RecyclerView.ViewHolder {
        TextView Name , det;

        public Holder(View itemView) {
            super(itemView);

            Name            = itemView.findViewById(R.id.textView6);
            det             = itemView.findViewById(R.id.textView7);

        }

    }

}