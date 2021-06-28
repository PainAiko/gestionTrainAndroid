package com.bawp.navigationbar.retrofit.models;

public class Voyageurs {
    private int numVoyageur;
    private String 	nomVoyageur;

    public int getNumVoyageur() {
        return numVoyageur;
    }

    public void setNumVoyageur(int numVoyageur) {
        this.numVoyageur = numVoyageur;
    }

    public String getNomVoyageur() {
        return nomVoyageur;
    }

    public void setNomVoyageur(String nomVoyageur) {
        this.nomVoyageur = nomVoyageur;
    }

    public Voyageurs(/*int numVoyageur,*/ String nomVoyageur) {
      //  this.numVoyageur = numVoyageur;
        this.nomVoyageur = nomVoyageur;
    }

    @Override
    public String toString() {
        return "Voyageurs{" +
                "numVoyageur=" + numVoyageur +
                ", nomVoyageur='" + nomVoyageur + '\'' +
                '}';
    }
}
