package com.example.aplicacionrealm;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionrealm.Model.Empleat;

import java.util.HashSet;
import java.util.Set;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class Empleat_List_Fragment extends MyFragment {
    private boolean inDeletionMode = false;
    private Set<Integer> countersToDelete = new HashSet<>();

    public Empleat_List_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_empleat_list, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView empleatsRecyclerView = view.findViewById(R.id.itemList);

        RealmResults<Empleat> listEmpleat = realm.where(Empleat.class)
                .sort("id")
                .findAllAsync();

        empleatsRecyclerView.setAdapter(new EmpleatAdapter(listEmpleat, true));
    }


    public class EmpleatAdapter extends RealmRecyclerViewAdapter<Empleat, EmpleatAdapter.MyViewHolder> {

        public EmpleatAdapter(@Nullable OrderedRealmCollection<Empleat> data, boolean autoUpdate) {
            super(data, autoUpdate);
        }
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_empleats, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

            holder.id.setText(String.valueOf(getData().get(position).getId()));
            holder.nom.setText(getData().get(position).getNom());
            holder.cognoms.setText(getData().get(position).getCognoms());
            holder.categoria.setText(getData().get(position).getCategoria());
            holder.edad.setText(String.valueOf(getData().get(position).getEdad()));
            holder.antiguetat.setText(String.valueOf(getData().get(position).getAntiguetat()));

            holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    eliminarDades(position);
                    new AlertDialog.Builder(requireContext()).setTitle("\t\tEl·liminat")
                            .setMessage("\t      ")
                            .setMessage("\t      ")
                            .setCancelable(true)
                            .create()
                            .show();
                }
            });
            appViewModel.modificar.setValue(false);
            holder.btnModificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appViewModel.idSeleccion.postValue(getData().get(position).getId());
                    appViewModel.modificar.setValue(true);
                    navController.navigate(R.id.insertarFragment);
                }
            });
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView id, nom, cognoms, categoria, edad, antiguetat;
            Button btnEliminar, btnModificar;

            public MyViewHolder(@NonNull View view) {
                super(view);
                id = view.findViewById(R.id.textid);
                nom = view.findViewById(R.id.textnom);
                cognoms = view.findViewById(R.id.textcognoms);
                categoria = view.findViewById(R.id.textcategoria);
                edad = view.findViewById(R.id.textedad);
                antiguetat = view.findViewById(R.id.textantiguetat);
                btnEliminar = view.findViewById(R.id.eliminarButton);
                btnModificar = view.findViewById(R.id.modificarButton);
            }
        }
        void eliminarDades(Integer pos){
            RealmResults<Empleat> empleat = realm.where(Empleat.class).findAll();
            realm.beginTransaction();
            empleat.get(pos).deleteFromRealm();
            realm.commitTransaction();
        }
    }
}
