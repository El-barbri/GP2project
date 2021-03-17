package com.example.gp2project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupFragment extends Fragment {

    ImageButton plus;
    RecyclerView rec;
    ArrayList<GroupModel> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_group, container, false);

        plus = root.findViewById(R.id.addbutton);
        rec = root.findViewById(R.id.rec);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CreateGroup.class));
            }
        });


        Init();

        return root;
    }

    public void Init() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("Groups");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    GroupModel dev = new GroupModel();
                    dev.setKey(snapshot.getKey());
                    dev.setName(snapshot.child("Name").getValue(String.class));
                    dev.setUserAdminKey(snapshot.child("UserAdmin").getValue(String.class));
                    list.add(dev);
                    //snapshot.child("City").getValue(String.class)
                }

                AdapterGroups adapterHproducts = new AdapterGroups(getContext(), list);
                rec.setLayoutManager(new GridLayoutManager(getContext(), 2));
                rec.setAdapter(adapterHproducts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}

