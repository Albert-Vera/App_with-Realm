package com.example.aplicacionrealm.Model;

import io.realm.annotations.Required;

public class Directiu {
    @Required
    private String nomIcognoms, categoria, sexe;
    @Required int edad;

    public Directiu(String nomIcognoms, String categoria, String sexe, int edad) {
        this.nomIcognoms = nomIcognoms;
        this.categoria = categoria;
        this.sexe = sexe;
        this.edad = edad;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
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
}
