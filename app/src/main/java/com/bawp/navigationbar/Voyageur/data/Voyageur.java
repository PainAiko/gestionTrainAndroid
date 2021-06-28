package com.bawp.navigationbar.Voyageur.data;

public class Voyageur {
    private String  numVoyageur;
    private String 	nomVoyageur;

    public Voyageur(String numVoyageur, String nomVoyageur) {
        this.numVoyageur = numVoyageur;
        this.nomVoyageur = nomVoyageur;
    }

    public Voyageur() {
    }

    public String getNumVoyageur() {
        return numVoyageur;
    }

    public void setNumVoyageur(String numVoyageur) {
        this.numVoyageur = numVoyageur;
    }

    public String getNomVoyageur() {
        return nomVoyageur;
    }

    public void setNomVoyageur(String nomVoyageur) {
        this.nomVoyageur = nomVoyageur;
    }

    @Override
    public String toString() {
        return "Voyageur{" +
                "numVoyageur='" + numVoyageur + '\'' +
                ", nomVoyageur='" + nomVoyageur + '\'' +
                '}';
    }
}
