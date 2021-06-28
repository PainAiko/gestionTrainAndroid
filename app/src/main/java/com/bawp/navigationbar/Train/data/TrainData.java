package com.bawp.navigationbar.Train.data;

import android.util.Log;

import com.bawp.navigationbar.retrofit.builder.BuilderRetrofit;
import com.bawp.navigationbar.retrofit.controller.UseService;
import com.bawp.navigationbar.retrofit.models.Trains;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainData {
    private List<Train> trains;
    private UseService useService;

    public TrainData() {
        useService = BuilderRetrofit.getRetrofit().create(UseService.class);
    }

    public void getAllTrain(){
        Call<List<Trains>> trainList =this.useService.getAllTrain();
        trainList.enqueue(new Callback<List<Trains>>() {
            @Override
            public void onResponse(Call<List<Trains>> call, Response<List<Trains>> response) {
                if (response.code() != 200){
                    Log.d("Warning" ,"Echec de connection");
                }
                List<Trains> datas = response.body();
                Log.d("succes" ,datas.toString());
                //setTrainInfos(datas);
            }

            @Override
            public void onFailure(Call<List<Trains>> call, Throwable t) {
                Log.d("Error",t.getLocalizedMessage());
            }
        });


    }
}
