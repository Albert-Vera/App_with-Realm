package com.example.aplicacionrealm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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

        final EmpleatListAdapter empleatListAdapter = new EmpleatListAdapter(null, true);
        empleatsRecyclerView.setAdapter(empleatListAdapter);

        appViewModel.iniciarGestioEmpleats();

        appViewModel.cercarEmpleats.observe(getViewLifecycleOwner(), new Observer<RealmResults<Empleat>>() {
            @Override
            public void onChanged(RealmResults<Empleat> empleats) {
                if (empleats.isEmpty())
                    Toast.makeText(requireActivity(),"No hi ha cap coincidència amb la cerca", Toast.LENGTH_SHORT).show();
                empleatListAdapter.updateData(empleats);
            }
        });

        appViewModel.estatGestioEmpleats.observe(getViewLifecycleOwner(), new Observer<AppViewModel.EstatGestioEmpleats>() {
            @Override
            public void onChanged(AppViewModel.EstatGestioEmpleats estatGestioEmpleats) {
                if(estatGestioEmpleats == AppViewModel.EstatGestioEmpleats.EMPLEAT_ELIMINAT){
                    Toast.makeText(requireActivity(),"Empleat eliminat!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class EmpleatListAdapter extends RealmRecyclerViewAdapter<Empleat, EmpleatListAdapter.MyViewHolder> {

        public EmpleatListAdapter(@Nullable OrderedRealmCollection<Empleat> data, boolean autoUpdate) {
            super(data, autoUpdate);
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_empleats, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
            final Empleat empleat = getItem(position);

            holder.id.setText(String.valueOf(getData().get(position).getId()));
            holder.nom.setText(getData().get(position).getNomIcognoms());
            //holder.cognoms.setText(getData().get(position).getCognoms());
            holder.categoria.setText(getData().get(position).getCategoria());
            holder.edad.setText(String.valueOf(getData().get(position).getEdad()));
            holder.antiguetat.setText(String.valueOf(getData().get(position).getAntiguetat()));

            holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new AlertDialog.Builder(requireContext()).setTitle("Eliminar empleat?")
                            .setMessage("S'eliminarà l'empleat amb id: " + empleat.getId())
                            .setCancelable(true)
                            .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    appViewModel.eliminarEmpleat(empleat);
                                }
                            })
                            .setNegativeButton("Cancel·lar", null)
                            .create()
                            .show();
                }
            });

            holder.btnModificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appViewModel.empleatAModifcarOBuscar.setValue(empleat);
                    navController.navigate(R.id.insertarFragment);
                }
            });
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView id, nom, categoria, edad, antiguetat;
            Button btnEliminar, btnModificar;

            public MyViewHolder(@NonNull View view) {
                super(view);
                id = view.findViewById(R.id.textid);
                nom = view.findViewById(R.id.textnom);
                //cognoms = view.findViewById(R.id.textcognoms);
                categoria = view.findViewById(R.id.textcategoria);
                edad = view.findViewById(R.id.textedad);
                antiguetat = view.findViewById(R.id.textantiguetat);
                btnEliminar = view.findViewById(R.id.eliminarButton);
                btnModificar = view.findViewById(R.id.modificarButton);
            }
        }
    }
}
