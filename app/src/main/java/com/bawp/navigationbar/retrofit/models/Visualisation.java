package com.bawp.navigationbar.retrofit.models;

public class Visualisation {
    private  int numReserve;
    private  int trainNumTrain;
    private  int voyageurNumVoyageur;
    private String dateReserve;
    private int frais;
    private Trains train;
    private Voyageurs voyageur;

    public Visualisation(int numReserve, int trainNumTrain, int voyageurNumVoyageur, String dateReserve, int frais, Trains trains, Voyageurs voyageurs) {
        this.numReserve = numReserve;
        this.trainNumTrain = trainNumTrain;
        this.voyageurNumVoyageur = voyageurNumVoyageur;
        this.dateReserve = dateReserve;
        this.frais = frais;
        this.train= trains;
        this.voyageur = voyageurs;
    }

    public int getNumReserve() {
        return numReserve;
    }

    public void setNumReserve(int numReserve) {
        this.numReserve = numReserve;
    }

    public int getTrainNumTrain() {
        return trainNumTrain;
    }

    public void setTrainNumTrain(int trainNumTrain) {
        this.trainNumTrain = trainNumTrain;
    }

    public int getVoyageurNumVoyageur() {
        return voyageurNumVoyageur;
    }

    public void setVoyageurNumVoyageur(int voyageurNumVoyageur) {
        this.voyageurNumVoyageur = voyageurNumVoyageur;
    }

    public String getDateReserve() {
        return dateReserve;
    }

    public void setDateReserve(String dateReserve) {
        this.dateReserve = dateReserve;
    }

    public int getFrais() {
        return frais;
    }

    public void setFrais(int frais) {
        this.frais = frais;
    }

    public Trains getTrains() {
        return train;
    }

    public void setTrains(Trains trains) {
        this.train = trains;
    }

    public Voyageurs getVoyageurs() {
        return voyageur;
    }

    public void setVoyageurs(Voyageurs voyageurs) {
        this.voyageur = voyageurs;
    }

    @Override
    public String toString() {
        return "Visualisation{" +
                "numReserve=" + numReserve +
                ", trainNumTrain=" + trainNumTrain +
                ", voyageurNumVoyageur=" + voyageurNumVoyageur +
                ", dateReserve='" + dateReserve + '\'' +
                ", frais=" + frais +
                ", trains=" + train +
                ", voyageurs=" + voyageur+
                '}';
    }
}
