package com.example.aplicacionrealm.Model;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Empleat extends RealmObject {


    @PrimaryKey
    private int id;
    @Index
    private String categoria;
    private int edad, antiguetat;
    @Required
    private String nomIcognoms;

    public Empleat() {
    }

    public Empleat(int id, String categoria, int edad, int antiguetat, String nomIcognoms) {
        this.id = id;
        this.categoria = categoria;
        this.edad = edad;
        this.antiguetat = antiguetat;
        this.nomIcognoms = nomIcognoms;
    }

    public String getCountString() {
        return Integer.toString(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomIcognoms() {
        return nomIcognoms;
    }

    public void setNomIcognoms(String nomIcognoms) {
        this.nomIcognoms = nomIcognoms;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getAntiguetat() {
        return antiguetat;
    }

    public void setAntiguetat(int antiguetat) {
        this.antiguetat = antiguetat;
    }
}
