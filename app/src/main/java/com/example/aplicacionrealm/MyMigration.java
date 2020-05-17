package com.example.aplicacionrealm;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class MyMigration extends MyFragment implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();

        // Versió 0

        // aqui juntaré el nom i cognoms

        if (oldVersion == 0) {
            RealmObjectSchema empleatSchema = schema.get("Empleat");

            // Combine 'firstName' and 'lastName' in a new field called 'fullName'
            empleatSchema
                    .addField("nomIcognoms", String.class, FieldAttribute.REQUIRED)
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            obj.set("nomIcognoms", obj.getString("nom") + " " + obj.getString("cognoms"));
                        }
                    })
                    .removeField("nom")
                    .removeField("cognoms");
            oldVersion++;
        }
        if (oldVersion == 1) {

            // Create a new class
            RealmObjectSchema directiuSchema = schema.create("Directiu")
                    .addField("nomIcognoms", String.class, FieldAttribute.REQUIRED)
                    .addField("categoria", String.class, FieldAttribute.REQUIRED);

            oldVersion++;
        }
        if (oldVersion == 2) {

            // Create a new class
            RealmObjectSchema direcSchema = schema.get("Directiu");
            direcSchema
                    .addField("edad", String.class, FieldAttribute.REQUIRED)
                    .addField("sexe", String.class, FieldAttribute.REQUIRED);

            oldVersion++;
        }
    }
}
