package com.example.aplicacionrealm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.aplicacionrealm.Model.Empleat;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class AppViewModel extends AndroidViewModel {

    private final Realm realm;

    public enum EstatGestioEmpleats {
        ESTAT_INICIAL, EMPLEAT_JA_EXISTENT, EMPLEAT_INSERTAT, EMPLEAT_ELIMINAT, EMPLEAT_MODIFICAT
    }

    public AppViewModel(@NonNull Application application) {
        super(application);
        realm = Realm.getDefaultInstance();
    }

    // Aquesta variable conté l'empleat que volem modificar en el InsertarFragment, si es null significa que es vol insertar un nou empleat.
    // També pot contenir les dades de l'empleat a buscar, i si es null, es que es desitja llistar tots els empleats
    public MutableLiveData<Empleat> empleatAModifcarOBuscar = new MutableLiveData<>();

    public MutableLiveData<EstatGestioEmpleats> estatGestioEmpleats = new MutableLiveData<>();

    public void iniciarGestioEmpleats(){
        estatGestioEmpleats.setValue(EstatGestioEmpleats.ESTAT_INICIAL);
    }

    public void insertarEmpleat(Empleat empleat){
        if(verificarExistencia(empleat.getId())){
            estatGestioEmpleats.setValue(EstatGestioEmpleats.EMPLEAT_JA_EXISTENT);
            return;
        }

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(empleat);
        realm.commitTransaction();

        estatGestioEmpleats.setValue(EstatGestioEmpleats.EMPLEAT_INSERTAT);
    }

    public void modificarEmpleat(Empleat empleat){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(empleat);
        realm.commitTransaction();

        estatGestioEmpleats.setValue(EstatGestioEmpleats.EMPLEAT_MODIFICAT);
    }

    boolean verificarExistencia(int idEmpleat){
        return !realm.where(Empleat.class)
                .equalTo("id", idEmpleat)
                .findAll().isEmpty();
    }

    public LiveData<RealmResults<Empleat>> cercarEmpleats = Transformations.switchMap(empleatAModifcarOBuscar, new Function<Empleat, LiveData<RealmResults<Empleat>>>() {
        @Override
        public LiveData<RealmResults<Empleat>> apply(Empleat empleat) {

            RealmQuery<Empleat> empleatRealmQuery = realm.where(Empleat.class);

            if(empleat != null) {
                if (empleat.getId() != -1)
                    empleatRealmQuery.equalTo("id", empleat.getId());

                if (!empleat.getNomIcognoms().isEmpty())
                    empleatRealmQuery.equalTo("nomIcognoms", empleat.getNomIcognoms());

//                if (!empleat.getCognoms().isEmpty())
//                    empleatRealmQuery.equalTo("cognoms", empleat.getCognoms());

                if (!empleat.getCategoria().isEmpty())
                    empleatRealmQuery.equalTo("categoria", empleat.getCategoria());

                if (empleat.getEdad() != -1)
                    empleatRealmQuery.equalTo("edad", empleat.getEdad());

                if (empleat.getAntiguetat() != -1)
                    empleatRealmQuery.equalTo("antiguetat", empleat.getAntiguetat());
            }

            return new MutableLiveData<>(empleatRealmQuery.findAll());
        }
    });

    public void eliminarEmpleat(Empleat empleat){
        realm.beginTransaction();
        empleat.deleteFromRealm();
        realm.commitTransaction();

        estatGestioEmpleats.setValue(EstatGestioEmpleats.EMPLEAT_ELIMINAT);
    }
}
