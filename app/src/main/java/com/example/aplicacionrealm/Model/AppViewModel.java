package com.example.aplicacionrealm.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;


import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class AppViewModel extends AndroidViewModel {
    public AppViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> modificar = new MutableLiveData<>(false);
    public MutableLiveData<Integer> idSeleccion = new MutableLiveData<>();
    public MutableLiveData<Empleat> empleatABuscar = new MutableLiveData<>();

    public LiveData<RealmResults<Empleat>> cercarEmpleats = Transformations.switchMap(empleatABuscar, new Function<Empleat, LiveData<RealmResults<Empleat>>>() {
        @Override
        public LiveData<RealmResults<Empleat>> apply(Empleat empleat) {

            RealmQuery<Empleat> empleatRealmQuery = Realm.getDefaultInstance().where(Empleat.class);

            if(empleat.getId() != -1)
                empleatRealmQuery.equalTo("id", empleat.getId());

            if(!empleat.getNom().isEmpty())
                empleatRealmQuery.equalTo("nom", empleat.getNom());

            if(!empleat.getCognoms().isEmpty())
                empleatRealmQuery.equalTo("cognoms", empleat.getCognoms());

            if(!empleat.getCategoria().isEmpty())
                empleatRealmQuery.equalTo("categoria", empleat.getCategoria());

            if(empleat.getEdad() != -1 )
                empleatRealmQuery.equalTo("edad", empleat.getEdad());

            if(empleat.getAntiguetat() != -1 )
                empleatRealmQuery.equalTo("antiguetat", empleat.getAntiguetat());


            return new MutableLiveData<>(empleatRealmQuery.findAll());
        }
    });
}
