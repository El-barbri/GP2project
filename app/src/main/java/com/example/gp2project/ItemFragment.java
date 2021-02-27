
package com.example.gp2project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gp2project.R;

import java.util.ArrayList;

public class ItemFragment extends Fragment {

    ImageButton add;
    ArrayList<DeviceData> data = MainActivity.devListfilter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_item,container,false);

        add                 = root.findViewById(R.id.addbutton);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(),ScanResult.class));
            }
        });

        return root;
    }


}
