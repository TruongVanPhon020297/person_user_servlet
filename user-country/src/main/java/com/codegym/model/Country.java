package com.codegym.model;

public class Country {
    protected int idCountry;
    protected String nameCountry;

    public Country(){

    }

    public Country(String nameCountry) {
        this.nameCountry = nameCountry;
    }

    public Country(int idCountry, String nameCountry) {
        this.idCountry = idCountry;
        this.nameCountry = nameCountry;
    }

    public int getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(int idCountry) {
        this.idCountry = idCountry;
    }

    public String getNameCountry() {
        return nameCountry;
    }

    public void setNameCountry(String nameCountry) {
        this.nameCountry = nameCountry;
    }
}
