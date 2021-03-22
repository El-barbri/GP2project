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



public class AdabterItemGroup extends RecyclerView.Adapter<AdabterItemGroup.Holder> {

    Context context ;
    ArrayList<DevModel> list;
    String Key;
    Activity app;


    public AdabterItemGroup(Context context, ArrayList<DevModel> list, String key , Activity app) {
        this.context = context;
        this.list = list;
        this.Key = key;
        this.app=app;


    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(this.context).inflate(R.layout.delete_group_item_shape ,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        //String name=list.get(position).getName();
        holder.name.setText(list.get(position).getName());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference item = FirebaseDatabase.getInstance().getReference("Groups").child(Key).child("Items").child(list.get(position).getKey());
                final AlertDialog.Builder deleteItem =new AlertDialog.Builder(app);

                View view = LayoutInflater.from(app).inflate(R.layout.delete_item_massage, null);
                deleteItem.setView(view);
               //deleteItem.setMessage("هل أنت متأكد أنك تريد حذف هذا الممتلك ؟");

                deleteItem.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        item.removeValue();
                        Toast.makeText(app, "تم حذف الممتلك بنجاح ", Toast.LENGTH_SHORT).show();
                        app.startActivity(new Intent(app, MainActivity.class));
                    }
                });
                deleteItem.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(app, "تم الإلغاء بنجاح",Toast.LENGTH_SHORT).show();
                    }
                });


                deleteItem.create().show();

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


        name = itemView.findViewById(R.id.deletitemG);
    }

}
}