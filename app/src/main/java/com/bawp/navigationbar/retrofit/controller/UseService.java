package com.bawp.navigationbar.retrofit.controller;

import com.bawp.navigationbar.retrofit.models.Recette;
import com.bawp.navigationbar.retrofit.models.Reservations;
import com.bawp.navigationbar.retrofit.models.Trains;
import com.bawp.navigationbar.retrofit.models.Visualisation;
import com.bawp.navigationbar.retrofit.models.Voyageurs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UseService {
    @GET("voyageur/{id}")
    Call<Voyageurs> getVoyageur(@Path("id") int id);
    @GET("voyageur")
    Call<List<Voyageurs>> getAllVoyageur();
    @POST("voyageur")
    Call<Voyageurs> addVoyageurs(@Body Voyageurs voyageur);
    @DELETE("voyageur/{id}")
    Call<Integer> deleteVoyageurs(@Path("id") int id);
    @PUT("voyageur/{id}")
    Call<Voyageurs> updateVoyageurs(@Body Voyageurs voyageur, @Path("id") int id);

    /****train**/
    @GET("train")
    Call<List<Trains>> getAllTrain();
    @GET("train/{numTrain}")
    Call<Trains> getTrains(@Path("numTrain") int numTrain);
    @POST("train")
    Call<Trains> addTrains(@Body Trains train);
    @DELETE("train/{numTrain}")
    Call<Trains> deleteTrains(@Path("numTrain") int numTrain);
    @PUT("train/{numTrain}")
    Call<Trains> updateTrains(@Path("numTrain") int numTrain , @Body Trains train);

    /**reservation**/
    @GET("reserve")
    Call<List<Reservations>>  getAllReservation();
    @GET("reserve/{numReserve}")
    Call<Reservations> getReservation(@Path("numReserve") int numReserve);
    @POST("reserve")
    Call<Reservations> addReservations(@Body Reservations reservations);
    @DELETE("reserve/{numReserve}")
    Call<Reservations> deleteReservations(@Path("numReserve") int numReserve);
    @PUT("reserve/{numReserve}")
    Call<Reservations> updateReservations(@Path("numReserve") int numReserve , @Body Reservations reservations);

    /**visualisation**/
    @GET("visualiser/{numTrain}")
    Call<List<Visualisation>> visualiserTrain(@Path("numTrain") int numTrain);
    /****recette***/
    @GET("recettes/{numTrain}/{annee}")
    Call<Recette> getRecette(@Path("numTrain") int numTrain, @Path("annee") String annee);
}
