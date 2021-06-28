package com.bawp.navigationbar.retrofit.models;

public class Trains {
    private int numTrain;
    private String design;
    private String itineraire;

    public Trains(/*int numTrain, */String design, String itineraire) {
        //this.numTrain = numTrain;
        this.design = design;
        this.itineraire = itineraire;
    }

    public Trains() {

    }

    public int getNumTrain() {
        return numTrain;
    }

    public void setNumTrain(int numTrain) {
        this.numTrain = numTrain;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getItineraire() {
        return itineraire;
    }

    public void setItineraire(String itineraire) {
        this.itineraire = itineraire;
    }

    @Override
    public String toString() {
        return "Trains{" +
                "numTrain=" + numTrain +
                ", design='" + design + '\'' +
                ", itineraire='" + itineraire + '\'' +
                '}';
    }
}
