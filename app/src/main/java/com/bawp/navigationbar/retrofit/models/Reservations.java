package com.bawp.navigationbar.retrofit.models;

public class Reservations {
    private int numReserve;
    private int trainNumTrain;
    private int voyageurNumVoyageur;
    private String dateReserve;
    private int frais;
    private Trains train;
    private Voyageurs voyageur;

    public Reservations(int numReserve, int trainNumTrain, int voyageurNumVoyageur, String dateReserve, int frais) {
        this.numReserve = numReserve;
        this.trainNumTrain = trainNumTrain;
        this.voyageurNumVoyageur = voyageurNumVoyageur;
        this.dateReserve = dateReserve;
        this.frais = frais;
    }

    public Reservations( int trainNumTrain, int voyageurNumVoyageur, String dateReserve, int frais) {
        this.trainNumTrain = trainNumTrain;
        this.voyageurNumVoyageur = voyageurNumVoyageur;
        this.dateReserve = dateReserve;
        this.frais = frais;
    }

    public Reservations() {

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

    public Trains getTrain() {
        return train;
    }

    public void setTrain(Trains train) {
        this.train = train;
    }

    public Voyageurs getVoyageur() {
        return voyageur;
    }

    public void setVoyageur(Voyageurs voyageur) {
        this.voyageur = voyageur;
    }

    @Override
    public String toString() {
        return "Reservations{" +
                "numReserve=" + numReserve +
                ", trainNumTrain=" + trainNumTrain +
                ", voyageurNumVoyageur=" + voyageurNumVoyageur +
                ", dateReserve='" + dateReserve + '\'' +
                ", frais=" + frais +
               ", train=" + train.toString() +
                ", voyageur=" + voyageur.toString() +
                '}';
    }
}
