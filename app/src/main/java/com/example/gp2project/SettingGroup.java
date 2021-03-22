package com.example.gp2project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.LayoutInflater;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageButton;
import com.google.rpc.context.AttributeContext.Auth;

import android.widget.TextView;
import  android.widget.Toast;
public class SettingGroup extends AppCompatActivity {

    Intent intent;
    String name;
    String key;
    String AdminKey;
    ImageButton GroupName;
    EditText newGroupName;
    DatabaseReference reference;
    Button deletGroup,addmeMember;
    TextView deletG,gName;
    Button deletMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_sitting_page);

        intent = getIntent();
        GroupName = findViewById(R.id.editGrouptName);
        deletGroup= findViewById(R.id.deletGroup);
        deletMember=findViewById(R.id.deletMember);
        gName=findViewById(R.id.gName);
        addmeMember=findViewById(R.id.addmeMember);
        //newGroupN=findViewById(R.id.newGroupName);
        reference = FirebaseDatabase.getInstance().getReference("Groups");

        if (intent.hasExtra("Name")) {
            name = intent.getStringExtra("Name");
            key = intent.getStringExtra("key");
            AdminKey = intent.getStringExtra("AdminKey");
            gName.setText(name);
        }

        GroupName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.edit_group_name_page, null);
                newGroupName=(EditText)view.findViewById(R.id.newGroupName);
                final AlertDialog.Builder groupNameRestDialog = new AlertDialog.Builder(v.getContext());
                groupNameRestDialog.setView(view);
                groupNameRestDialog.setPositiveButton("تغيير", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String changeGroupName = newGroupName.getText().toString();

                       if (TextUtils.isEmpty(newGroupName.getText().toString())) {
                           groupEmptyName();

                        }
                        else if(newGroupName.getText().toString().length()>25){
                            groupLongName();
                        }/*
                        else if(true){
                            exist(newGroupName);
                        } */
                        else{
                            reference.child(key).child("Name").setValue(newGroupName.getText().toString());
                           gName.setText(changeGroupName);
                        }
                    }

                });
                groupNameRestDialog.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "تم الإلغاء بنجاح",Toast.LENGTH_SHORT).show();

                    }
                });

                groupNameRestDialog.create().show();

            }
        });



    }

    public void deletMember(View view){
        startActivity(new Intent(this, SelectUser.class).putExtra("Name", name)
                .putExtra("key", key).putExtra("type" , "deleteMember")
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

    }

    public void deletGItem (View view){
        startActivity(new Intent(this, SelectUser.class).putExtra("Name", name)
                .putExtra("key", key).putExtra("type" , "deleteGItem")
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }



    public void deleteGroup(View view) {
        DatabaseReference group= FirebaseDatabase.getInstance().getReference("Groups").child(key);
        View vieww = LayoutInflater.from(getApplicationContext()).inflate(R.layout.deletgroup_page, null);

        final AlertDialog.Builder deleteItem = new AlertDialog.Builder(this);
        deletG=vieww.findViewById(R.id.deleteGroup);
        name = intent.getStringExtra("Name");
        deletG.setText("هل أنت متأكد من حذف مجموعة "+name);
        deleteItem.setView(vieww);

        deleteItem.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                group.removeValue();
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
        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
       // this.finish();

    }

    public void groupLongName() {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.edit_group_name_page, null);
        newGroupName=(EditText)view.findViewById(R.id.newGroupName);
        newGroupName.setError("من فضلك الاسم يجب أن لايتجاوز 25 حرفاً");
        final AlertDialog.Builder groupNameRestDialog = new AlertDialog.Builder(this);
        groupNameRestDialog.setView(view);
        groupNameRestDialog.setPositiveButton("تغيير", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String changeGroupName = newGroupName.getText().toString();

                if (TextUtils.isEmpty(newGroupName.getText().toString())) {
                    groupEmptyName();

                }
                else if(newGroupName.getText().toString().length()>20){
                    groupLongName();
                }/*
                else if(true){
                     exist(newGroupName);
                } */
                else{
                    reference.child(key).child("Name").setValue(newGroupName.getText().toString());
                    gName.setText(changeGroupName);
                }
            }

        });
        groupNameRestDialog.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "تم الإلغاء بنجاح",Toast.LENGTH_SHORT).show();

            }
        });

        groupNameRestDialog.create().show();

    }

    public void groupEmptyName() {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.edit_group_name_page, null);
        newGroupName=(EditText)view.findViewById(R.id.newGroupName);
        newGroupName.setError("ادخل اسم من فضلك");
        final AlertDialog.Builder groupNameRestDialog = new AlertDialog.Builder(this);
        groupNameRestDialog.setView(view);
        groupNameRestDialog.setPositiveButton("تغيير", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String changeGroupName = newGroupName.getText().toString();

                if (TextUtils.isEmpty(newGroupName.getText().toString())) {
                    groupEmptyName();

                }
                else if(newGroupName.getText().toString().length()>20){
                    groupLongName();
                }/*
                else if(true){
                     exist(newGroupName);
                } */
                else{
                    reference.child(key).child("Name").setValue(newGroupName.getText().toString());
                    gName.setText(changeGroupName);
                }
            }

        });
        groupNameRestDialog.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "تم الإلغاء بنجاح",Toast.LENGTH_SHORT).show();

            }
        });

        groupNameRestDialog.create().show();

    }

    public void AddMember(View view) {

        startActivity(new Intent(this, addGroupUser.class).putExtra("key", key)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

    }

    public void AddItem(View view) {
        startActivity(new Intent(this, SelectUser.class).putExtra("Key", key)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

    }

}