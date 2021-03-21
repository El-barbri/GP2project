package com.example.gp2project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

public class ItemPage extends AppCompatActivity {

    String Mac;
    String name;
    String key;
    TextView itemName ,deleteItem;
    EditText newName;
    TextView deletItem;
    ImageButton editName;
    private String userID;
    private DatabaseReference reference;
    private FirebaseAuth Auth;
    private Intent intent;
    ArrayList<DevModel> list = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_page);

        //declared authentication
        Auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Item");

        itemName = findViewById(R.id.itemName);
        deleteItem = findViewById(R.id.deleteItem);
        editName = findViewById(R.id.editName);

        intent = getIntent();

        if (intent.hasExtra("Mac")) {
            Mac = intent.getStringExtra("Mac");
            key= intent.getStringExtra("key");
            // Toast.makeText(this, Mac, Toast.LENGTH_SHORT).show();
            if (intent.hasExtra("item")) {
                name = intent.getStringExtra("item");
                itemName.setText(name);
                deleteItem.setText("حذف " + name);
            }

        }

        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem();
              //  startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getItemList();
                Log.w("تتتتتتتتتتت","ddd");
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.edititemname_page, null);
                newName = view.findViewById(R.id.newName);
                final AlertDialog.Builder NameRestDialog = new AlertDialog.Builder(v.getContext());
                NameRestDialog.setView(view);

                NameRestDialog.setPositiveButton("تغيير", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String neww = newName.getText().toString();

                        if (TextUtils.isEmpty(newName.getText().toString())) {
                            EmptyName();
                        }
                        else if(newName.getText().toString().length()>20){
                            longName();
                        }
                        else if(true){
                            exist(neww);
                        }
                        else {
                            reference.child(Auth.getCurrentUser().getUid()).child(key).child("Name").setValue(newName.getText().toString());
                            itemName.setText(neww);
                            deleteItem.setText("حذف " + neww);
                        }

                    }
                });
                NameRestDialog.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "تم الإلغاء بنجاح",Toast.LENGTH_SHORT).show();

                    }
                });
                NameRestDialog.create().show();
            }



        });
    }


    public void EmptyName()
    {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.edititemname_page, null);
        newName = view.findViewById(R.id.newName);
        newName.setError("ادخل اسم من فضلك");
        final AlertDialog.Builder NameRestDialog = new AlertDialog.Builder(this);
        NameRestDialog.setView(view);

        NameRestDialog.setPositiveButton("تغيير", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String neww = newName.getText().toString();

                if (TextUtils.isEmpty(newName.getText().toString())) {
                    EmptyName();
                  //  Toast.makeText(getApplicationContext(), "ادخل اسم من فضلك",Toast.LENGTH_SHORT).show();
                }
                else if(newName.getText().toString().length()>20){
                    longName();
                  //  Toast.makeText(getApplicationContext(), "من فضلك الاسم يجب أن لايتجاوز 20 حرفاً",Toast.LENGTH_SHORT).show();
                }
                else if(true){
                  exist(neww);
                }
                else {
                    reference.child(Auth.getCurrentUser().getUid()).child(key).child("Name").setValue(newName.getText().toString());
                    itemName.setText(neww);
                    deleteItem.setText("حذف " + neww);
                }
            }
        });
        NameRestDialog.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "تم الإلغاء بنجاح",Toast.LENGTH_SHORT).show();

            }
        });
        NameRestDialog.create().show();

    }

    public void longName() {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.edititemname_page, null);
        newName = view.findViewById(R.id.newName);
        newName.setError("من فضلك الاسم يجب أن لايتجاوز 20 حرفاً");
        final AlertDialog.Builder NameRestDialog = new AlertDialog.Builder(this);
        NameRestDialog.setView(view);

        NameRestDialog.setPositiveButton("تغيير", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String neww = newName.getText().toString();

                if (TextUtils.isEmpty(newName.getText().toString())) {
                    EmptyName();
                    //Toast.makeText(getApplicationContext(), "ادخل اسم من فضلك",Toast.LENGTH_SHORT).show();
                }
                else if(newName.getText().toString().length()>20){
                    longName();
                   // Toast.makeText(getApplicationContext(), "من فضلك الاسم يجب أن لايتجاوز 20 حرفاً",Toast.LENGTH_SHORT).show();
                }
                else if(true){
                    exist(neww);
                }
                else {
                    reference.child(Auth.getCurrentUser().getUid()).child(key).child("Name").setValue(newName.getText().toString());
                    itemName.setText(neww);
                    deleteItem.setText("حذف " + neww);
                }
            }
        });
        NameRestDialog.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "تم الإلغاء بنجاح",Toast.LENGTH_SHORT).show();

            }
        });
        NameRestDialog.create().show();

    }

    public void exist(String newNamee){

        DevModel dev = new DevModel();
        String devName="";
        int i;
        for(i=0; i<list.size(); i++){
            dev= list.get(i);
            devName= dev.getName();
            if(devName.equals(newNamee)){
                againView();
                return;
            }

        }

        String neww = newName.getText().toString();
        reference.child(Auth.getCurrentUser().getUid()).child(key).child("Name").setValue(newName.getText().toString());
        itemName.setText(neww);
        deleteItem.setText("حذف " + neww);

    }

    public void againView(){

        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.edititemname_page, null);
        newName = view.findViewById(R.id.newName);
        newName.setError("الإسم موجود مسبقاَ");
        final AlertDialog.Builder NameRestDialog = new AlertDialog.Builder(this);
        NameRestDialog.setView(view);

        NameRestDialog.setPositiveButton("تغيير", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String neww = newName.getText().toString();

                if (TextUtils.isEmpty(newName.getText().toString())) {
                    EmptyName();
                    //Toast.makeText(getApplicationContext(), "ادخل اسم من فضلك",Toast.LENGTH_SHORT).show();
                }
                else if(newName.getText().toString().length()>20){
                    longName();
                    // Toast.makeText(getApplicationContext(), "من فضلك الاسم يجب أن لايتجاوز 20 حرفاً",Toast.LENGTH_SHORT).show();
                }
                else if(true){
                    exist(neww);
                }
                else {

                    reference.child(Auth.getCurrentUser().getUid()).child(key).child("Name").setValue(newName.getText().toString());
                    itemName.setText(neww);
                    deleteItem.setText("حذف " + neww);
                }

            }
        });
        NameRestDialog.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "تم الإلغاء بنجاح",Toast.LENGTH_SHORT).show();

            }
        });
        NameRestDialog.create().show();
    }

    public void deleteItem(){
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference item= FirebaseDatabase.getInstance().getReference("Item").child(userID).child(key);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.deleteitem_page, null);

        final AlertDialog.Builder deleteItem = new AlertDialog.Builder(this);
        deletItem=view.findViewById(R.id.delete);
        name = intent.getStringExtra("item");
        deletItem.setText("هل أنت متأكد من حذف "+name);
        deleteItem.setView(view);

        deleteItem.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                item.removeValue();
                Toast.makeText(getApplicationContext(), "تم الحذف ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        deleteItem.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "تم الإلغاء بنجاح",Toast.LENGTH_SHORT).show();
            }
        });
        deleteItem.create().show();



    }

    public void getItemList(){


            FirebaseDatabase database = FirebaseDatabase.getInstance();
            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference reference = database.getReference().child("Item").child(userID);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        DevModel dev = new DevModel();
                        dev.setKey(snapshot.getKey());
                        dev.setName(snapshot.child("Name").getValue(String.class));
                        dev.setMac(snapshot.child("Mac").getValue(String.class));
                        list.add(dev);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        }

    public void Track(View view) {

        startActivity(new Intent(this, ScanResult.class).putExtra("Mac", Mac)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }


}