 package com.bawp.navigationbar.retrofit.builder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuilderRetrofit {
    private static final String URL ="http:10.0.2.2:8082/";
    private  static Retrofit retrofit = null;
    public static Retrofit getRetrofit(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
