package com.example.aplicacionrealm;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;

public class MyApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("realAlbert.realm")
                .schemaVersion(3)
                .migration(new MyMigration())
                .build();
        Realm.setDefaultConfiguration(config);
    }
}



// .migration(new MyMigration())
//            .deleteRealmIfMigrationNeeded()