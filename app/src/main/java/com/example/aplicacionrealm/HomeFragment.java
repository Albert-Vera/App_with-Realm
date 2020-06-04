package com.example.aplicacionrealm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends MyFragment {
    private Button insertar, cercar, visualitzar;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        insertar = view.findViewById(R.id.insertarText);
        cercar = view.findViewById(R.id.cercarText);
        visualitzar = view.findViewById(R.id.visualitzarText);

        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appViewModel.empleatAModifcarOBuscar.setValue(null);
                navController.navigate(R.id.insertarFragment);
            }
        });

        cercar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.cercarFragment);
            }
        });

        visualitzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appViewModel.empleatAModifcarOBuscar.setValue(null);
                navController.navigate(R.id.empleat_List_Fragment);
            }
        });
    }
}
