package com.bawp.navigationbar.Reservation.data;

public class Reservation {
    private String numReserve;
    private String nomVoyageurReserve;
    private String designReserve;
    private String dateReserve;
    private String frais;

    public Reservation(String numReserve, String nomVoyageurReserve, String designReserve, String dateReserve, String frais) {
        this.numReserve = numReserve;
        this.nomVoyageurReserve = nomVoyageurReserve;
        this.designReserve = designReserve;
        this.dateReserve = dateReserve;
        this.frais = frais;
    }

    public String getNumReserve() {
        return numReserve;
    }

    public void setNumReserve(String numReserve) {
        this.numReserve = numReserve;
    }

    public String getNomVoyageurReserve() {
        return nomVoyageurReserve;
    }

    public void setNomVoyageurReserve(String nomVoyageurReserve) {
        this.nomVoyageurReserve = nomVoyageurReserve;
    }

    public String getDesignReserve() {
        return designReserve;
    }

    public void setDesignReserve(String designReserve) {
        this.designReserve = designReserve;
    }

    public String getDateReserve() {
        return dateReserve;
    }

    public void setDateReserve(String dateReserve) {
        this.dateReserve = dateReserve;
    }

    public String getFrais() {
        return frais;
    }

    public void setFrais(String frais) {
        this.frais = frais;
    }

    @Override
    public String toString() {
        return "ReservationDto{" +
                "numReserve=" + numReserve +
                ", nomVoyageurReserve='" + nomVoyageurReserve + '\'' +
                ", designReserve='" + designReserve + '\'' +
                ", dateReserve='" + dateReserve + '\'' +
                ", frais=" + frais +
                '}';
    }
}
