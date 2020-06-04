package com.example.aplicacionrealm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacionrealm.Model.Empleat;

import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class InsertarFragment extends MyFragment {

    private EditText idEditText, nomEditText, cognomsEditText, categoriaEditText, edadEditText, antiguetatEditText;
    private Button btnInsertar;
    TextView titol;
    private int idSelecion;
    private boolean modificar ;
    public InsertarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insertar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titol = view.findViewById(R.id.titleText);
        idEditText = view.findViewById(R.id.idEditText);
        nomEditText = view.findViewById(R.id.nomEditText);
        // cognomsEditText = view.findViewById(R.id.cognomsEditText);
        categoriaEditText = view.findViewById(R.id.categoriaEditText);
        edadEditText = view.findViewById(R.id.edadEditText);
        antiguetatEditText = view.findViewById(R.id.antiguetatEditText);
        btnInsertar = view.findViewById(R.id.btnInsertar);
        appViewModel.iniciarGestioEmpleats();

        appViewModel.empleatAModifcarOBuscar.observe(getViewLifecycleOwner(), new Observer<Empleat>() {
            @Override
            public void onChanged(Empleat empleat) {
                if (empleat != null) {
                    emplenarDadesEmpleat(empleat);
                    idEditText.setVisibility(EditText.INVISIBLE);
                    btnInsertar.setText("Modificar");
                    titol.setText("Modificar dades");
                    modificar = true;
                }
            }
        });

        appViewModel.estatGestioEmpleats.observe(getViewLifecycleOwner(), new Observer<AppViewModel.EstatGestioEmpleats>() {
            @Override
            public void onChanged(AppViewModel.EstatGestioEmpleats estatGestioEmpleats) {
                switch (estatGestioEmpleats){
                    case EMPLEAT_INSERTAT:
                        navController.navigate(R.id.homeFragment);
                        return;
                    case EMPLEAT_MODIFICAT:
                        Toast.makeText(requireActivity(),"Empleat modificat!", Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.homeFragment);
                        return;
                    case EMPLEAT_JA_EXISTENT:
                        Toast.makeText(requireActivity(),"El id introduit ja existeix!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateForm()){
                    Toast.makeText(requireActivity(),"Falten dades per introduir!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Empleat empleat = new Empleat(
                        Integer.parseInt(idEditText.getText().toString()),
//                        cognomsEditText.getText().toString(),
                        categoriaEditText.getText().toString(),
                        edadEditText.getText().toString().isEmpty() ? -1 : Integer.parseInt(edadEditText.getText().toString()),
                        antiguetatEditText.getText().toString().isEmpty() ? -1 : Integer.parseInt(antiguetatEditText.getText().toString()),
                        nomEditText.getText().toString());

                if (modificar){
                    appViewModel.modificarEmpleat(empleat);
                } else {
                    appViewModel.insertarEmpleat(empleat);
                }
            }
        });
    }

    private void emplenarDadesEmpleat(Empleat empleat){
        idEditText.setText(String.valueOf(empleat.getId()));
       // cognomsEditText.setText(empleat.getCognoms());
        categoriaEditText.setText(empleat.getCategoria());
        nomEditText.setText(empleat.getNomIcognoms());
        edadEditText.setText(String.valueOf(empleat.getEdad()));
        antiguetatEditText.setText(String.valueOf(empleat.getEdad()));
    }


    private boolean validateForm() {
        boolean valid = true;

        String id = idEditText.getText().toString();
        if (TextUtils.isEmpty(id) && !modificar) {
            idEditText.setError("Required.");
            valid = false;
        }
        String nom = nomEditText.getText().toString();
        if (TextUtils.isEmpty(nom)) {
            nomEditText.setError("Required.");
            valid = false;
        }
//        String cognoms = cognomsEditText.getText().toString();
//        if (TextUtils.isEmpty(cognoms)) {
//            cognomsEditText.setError("Required.");
//            valid = false;
//        }
        String categoria = categoriaEditText.getText().toString();
        if (TextUtils.isEmpty(categoria)) {
            categoriaEditText.setError("Required.");
            valid = false;
        }
        String edad = edadEditText.getText().toString();
        if (TextUtils.isEmpty(edad) ) {
            edadEditText.setError("Required.");
            valid = false;
        }else{
            try{
                int num = Integer.parseInt(edad);
            }catch (Exception e){
                edadEditText.setError("Solo números");
                valid = false;
            }
        }
        String antiguetat = antiguetatEditText.getText().toString();
        if (TextUtils.isEmpty(antiguetat)) {
            antiguetatEditText.setError("Required.");
            valid = false;
        }else{
            try{
                int num = Integer.parseInt(antiguetat);
            }catch (Exception e){
                antiguetatEditText.setError("Solo números");
                valid = false;
            }
        }
        return valid;
    }
}
