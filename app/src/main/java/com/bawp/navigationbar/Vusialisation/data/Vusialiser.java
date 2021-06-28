package com.bawp.navigationbar.Vusialisation.data;

public class Vusialiser {
    private String  numVoyageur;
    private String 	nomVoyageur;
    private String  numTrain;
    private String  nomTrain;

    public Vusialiser (String numVoyageur, String nomVoyageur, String numTrain, String nomTrain) {
        this.numVoyageur = numVoyageur;
        this.nomVoyageur = nomVoyageur;
        this.numTrain = numTrain;
        this.nomTrain = nomTrain;
    }

    public Vusialiser() {
    }

    public Vusialiser(String numVoyageur, String nomVoyageur) {
        this.numVoyageur = numVoyageur;
        this.nomVoyageur = nomVoyageur;
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

    public String getNumTrain() {
        return numTrain;
    }

    public void setNumTrain(String numTrain) {
        this.numTrain = numTrain;
    }

    public String getNomTrain() {
        return nomTrain;
    }

    public void setNomTrain(String nomTrain) {
        this.nomTrain = nomTrain;
    }

    @Override
    public String toString() {
        return "Voyageur{" +
                "numVoyageur='" + numVoyageur + '\'' +
                ", nomVoyageur='" + nomVoyageur + '\'' +
                '}';
    }
}
