package com.bawp.navigationbar.retrofit.models;

public class Recette {
    private  int numTrain;
    private  int total;
    private Trains train;

    public Recette(int numTrain, int total, Trains trains) {
        this.numTrain = numTrain;
        this.total = total;
        this.train = trains;
    }

    public Recette( int total, Trains trains) {

        this.total = total;
        this.train = trains;
    }

    public Recette() {
    }

    public int getNumTrain() {
        return numTrain;
    }

    public void setNumTrain(int numTrain) {
        this.numTrain = numTrain;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Trains getTrains() {
        return train;
    }

    public void setTrains(Trains trains) {
        this.train = trains;
    }

    @Override
    public String toString() {
        return "Recette{" +
                "numTrain=" + numTrain +
                ", total=" + total +
                ", trains=" + train +
                '}';
    }
}
