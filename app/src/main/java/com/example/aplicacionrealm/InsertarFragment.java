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
        cognomsEditText = view.findViewById(R.id.cognomsEditText);
        categoriaEditText = view.findViewById(R.id.categoriaEditText);
        edadEditText = view.findViewById(R.id.edadEditText);
        antiguetatEditText = view.findViewById(R.id.antiguetatEditText);
        btnInsertar = view.findViewById(R.id.btnInsertar);
        appViewModel.idSeleccion.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer id) {
                idSelecion = id;
            }
        });
        appViewModel.modificar.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                if (b) {
                    idEditText.setVisibility(EditText.INVISIBLE);
                    btnInsertar.setText("Modificar");
                    titol.setText("Modificar dades");
                }
                modificar = b;
            }
        });

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateForm(modificar)){
                    Toast toast1 =
                            Toast.makeText(requireActivity(),
                                    "Faltan datos por introducir !", Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.CENTER | Gravity.LEFT, 250, 0);
                    toast1.show();
                    return;
                }
                if ( ! modificar && verificarExistencia(Integer.parseInt(idEditText.getText().toString()))){
                    Toast toast1 =
                            Toast.makeText(requireActivity(),
                                    "El id introduit ja existeix !", Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.CENTER | Gravity.LEFT, 250, 0);
                    toast1.show();
                    return;
                }else{
                    writerDades(modificar, idSelecion);
                    navController.navigate(R.id.homeFragment);
                }

            }
        });

    }
    boolean verificarExistencia(int idSelecion){

        RealmResults<Empleat> resultat = realm.where(Empleat.class)
                .equalTo("id", idSelecion)
                .findAll();

        if (resultat.isEmpty()) return false;
        else return true;

    }
    private void writerDades(boolean modificar, int idSelecion) {
        int id;
        if (modificar ) id = idSelecion;
        else id = Integer.parseInt(idEditText.getText().toString());

        empleat = new Empleat();
        if (modificar ) empleat.setId(idSelecion);
        else empleat.setId(Integer.parseInt(idEditText.getText().toString()));
        empleat.setNom(nomEditText.getText().toString());
        empleat.setCognoms(cognomsEditText.getText().toString());
        empleat.setCategoria(categoriaEditText.getText().toString());
        empleat.setEdad(Integer.parseInt(edadEditText.getText().toString()));
        empleat.setAntiguetat(Integer.parseInt(antiguetatEditText.getText().toString()));

        realm.beginTransaction();
        ;
        Empleat registrarEmpleat = realm.copyToRealmOrUpdate(empleat);
        realm.commitTransaction();
    }

    private boolean validateForm(boolean modificar) {
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
        String cognoms = cognomsEditText.getText().toString();
        if (TextUtils.isEmpty(cognoms)) {
            cognomsEditText.setError("Required.");
            valid = false;
        }
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
