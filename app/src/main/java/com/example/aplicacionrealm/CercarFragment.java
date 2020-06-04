package com.example.aplicacionrealm;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplicacionrealm.Model.Empleat;



/**
 * A simple {@link Fragment} subclass.
 */
public class CercarFragment extends MyFragment {
    Button  btCercar;
    EditText id, nom, cognoms, categoria, edad, antiguetat;
    public CercarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cercar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        id = view.findViewById(R.id.idCerca);
        nom = view.findViewById(R.id.nomCerca);
        //cognoms = view.findViewById(R.id.cognomsCerca);
        categoria = view.findViewById(R.id.categoriaCerca);
        edad = view.findViewById(R.id.edadCerca);
        antiguetat = view.findViewById(R.id.antiguetatCerca);
        btCercar = view.findViewById(R.id.btnCerca);

        btCercar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateForm()){
                    Toast.makeText(requireActivity(),"Has d'introduir alguna dade !", Toast.LENGTH_SHORT).show();
                    return;
                }

                appViewModel.empleatAModifcarOBuscar.setValue(new Empleat(
                        id.getText().toString().isEmpty() ? -1 : Integer.parseInt(id.getText().toString()),
                        categoria.getText().toString(),
                        edad.getText().toString().isEmpty() ? -1 : Integer.parseInt(edad.getText().toString()),
                        antiguetat.getText().toString().isEmpty() ? -1 : Integer.parseInt(antiguetat.getText().toString()),
                        nom.getText().toString()));

                navController.navigate(R.id.empleat_List_Fragment);
            }
        });
    }

    private boolean validateForm() {
        boolean valid = true;
        int contador = 0;

        String idt = id.getText().toString();
        if (!TextUtils.isEmpty(idt) ) {
            try{
                int num = Integer.parseInt(idt);
            }catch (Exception e){
                edad.setError("Solo números");
                valid = false;
            }
        }else contador ++;

        String edadt = edad.getText().toString();
        if (!TextUtils.isEmpty(edadt) ) {
            try{
                int num = Integer.parseInt(edadt);
            }catch (Exception e){
                edad.setError("Solo números");
                valid = false;
            }
        }else contador ++;

        String antiguetatt = antiguetat.getText().toString();
        if (!TextUtils.isEmpty(antiguetatt)) {
            try{
                int num = Integer.parseInt(antiguetatt);
            }catch (Exception e){
                antiguetat.setError("Solo números");
                valid = false;
            }
        }else contador ++;

        String nomIcognom = nom.getText().toString();
        if (TextUtils.isEmpty(nomIcognom)) {
            contador ++;
        }

        String categ = categoria.getText().toString();
        if (TextUtils.isEmpty(categ)) {
            contador ++;
        }

        if (contador == 5) valid = false;

        return valid;
    }
}