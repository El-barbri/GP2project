package com.example.gp2project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class AdapterLocation extends RecyclerView.Adapter<AdapterLocation.Holder> {


    Context context;

    public AdapterLocation(Context context) {
        this.context = context;

    }

    public Holder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new Holder(LayoutInflater.from(this.context).inflate(R.layout.item_location, viewGroup, false));
    }

    public void onBindViewHolder(final Holder holder, final int position) {

        // handle action click on adapter

        holder.Name.setText(MapLocation.devList.get(position).getName());
        holder.det.setText(MapLocation.devList.get(position).getDistance());


    }

    public int getItemCount() {
        return MapLocation.devList.size();
    }


    class Holder extends RecyclerView.ViewHolder {
        TextView Name, det;

        public Holder(View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.textView6);
            det = itemView.findViewById(R.id.textView8);

        }

    }

}