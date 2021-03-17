package com.example.gp2project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.LayoutInflater;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import androidx.appcompat.app.AppCompatActivity;
import com.google.rpc.context.AttributeContext.Auth;
import  android.widget.Toast;
public class SettingGroup extends AppCompatActivity {

    Intent intent;
    String name;
    String key;
    String AdminKey;
    Button GroupName;
    EditText newGroupName;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_sitting_page);

        intent = getIntent();
        GroupName = findViewById(R.id.editGrouptName);
        newGroupName=findViewById(R.id.newGroupName);
        reference = FirebaseDatabase.getInstance().getReference("Groups");

        if (intent.hasExtra("Name")) {
            name = intent.getStringExtra("Name");
            key = intent.getStringExtra("key");
            AdminKey = intent.getStringExtra("AdminKey");

            GroupName.setText(name);
        }


/*
        GroupName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.edit_group_name_page, null);
                final AlertDialog.Builder groupNameRestDialog = new AlertDialog.Builder(v.getContext());
                groupNameRestDialog.setView(view);
                groupNameRestDialog.setPositiveButton("تغيير", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String changeGroupName = newGroupName.getText().toString();

                     /*   if (TextUtils.isEmpty(newGroupName.getText().toString())) {
                            EmptyName();

                        }
                        else if(newGroupName.getText().toString().length()>20){
                            longName();
                        }
                        else if(true){
                            exist(newGroupName);
                        } */
/*
                            reference.child("-MVuiTT3WICiqAOz4dEz").child("Name").setValue(newGroupName.getText().toString());
                          // editGroupName.setText(changeGroupName);
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
        }); */

    }

    public void EmptyName() {

    }


    public void AddMember(View view) {
        startActivity(new Intent(this, SelectUser.class).putExtra("key", key)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

    }

    public void AddItem(View view) {
        startActivity(new Intent(this, SelectUser.class).putExtra("Key", key)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

    }

    public void editGroupName(){


    }
}