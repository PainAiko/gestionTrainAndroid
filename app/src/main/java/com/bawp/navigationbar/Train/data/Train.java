package com.bawp.navigationbar.Train.data;

public class Train {
    private String numTrain;
    private String design;
    private String itineraire;

    public Train(String numTrain, String design, String itineraire) {
        this.numTrain = numTrain;
        this.design = design;
        this.itineraire = itineraire;
    }

    public String getNumTrain() {
        return numTrain;
    }

    public void setNumTrain(String numTrain) {
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
        return "Train{" +
                "numTrain='" + numTrain + '\'' +
                ", design='" + design + '\'' +
                ", itineraire='" + itineraire + '\'' +
                '}';
    }
}
