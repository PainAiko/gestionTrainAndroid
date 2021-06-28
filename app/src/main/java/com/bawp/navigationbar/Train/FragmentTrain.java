package com.bawp.navigationbar.Train;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.navigationbar.R;
import com.bawp.navigationbar.Reservation.data.Reservation;
import com.bawp.navigationbar.Reservation.data.ReservationListAdapter;
import com.bawp.navigationbar.Train.data.Train;
import com.bawp.navigationbar.Train.data.TrainListAdapter;
import com.bawp.navigationbar.retrofit.builder.BuilderRetrofit;
import com.bawp.navigationbar.retrofit.controller.UseService;
import com.bawp.navigationbar.retrofit.models.Reservations;
import com.bawp.navigationbar.retrofit.models.Trains;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTrain extends Fragment/*implements View.OnClickListener*/{
        private AlertDialog.Builder builder;
        private  AlertDialog  dialog;
        private  Button saveTrain;
        private EditText designItem;
        private  EditText itineraireItem;
        private List<Train> trainList;
        private UseService useService;
        private RecyclerView recyclerViewTrain;
        private Button buttonAdd;
        private Button save_button;
        private  View listView ;
        public  FragmentTrain(){

        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            useService = BuilderRetrofit.getRetrofit().create(UseService.class);
            getAllTrain();
           // getAllTrain();

          /*  */
        }

        @Override
        public void onStart() {
            super.onStart();
            recyclerViewTrain= listView.findViewById(R.id.recyclerViewtrain);
            //recyclerViewTrain.setHasFixedSize(true);
            recyclerViewTrain.setLayoutManager(new LinearLayoutManager(getContext()));
            trainList = new ArrayList<Train>();
           // getAllTrain();

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
                    setTrainInfos(datas);
                }

                @Override
                public void onFailure(Call<List<Trains>> call, Throwable t) {
                    Log.d("Error",t.getLocalizedMessage());
                }
            });


        }
        public  void  setTrainInfos(List<Trains> trainInfos){
            for (Trains trains : trainInfos){
                String numTrain = String.valueOf(trains.getNumTrain());
                this.trainList.add(new Train(numTrain,trains.getDesign(),trains.getItineraire()));
            }
            this.setAdapter();
        }

    private void setAdapter() {
        TrainListAdapter trainListAdapter = new TrainListAdapter(getContext(),trainList);
        recyclerViewTrain.setAdapter(trainListAdapter);
    }

   @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       listView = inflater.inflate(R.layout.fragmenttrains,container,false);
       buttonAdd = listView.findViewById(R.id.addTrainBtn);


       buttonAdd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               createPopupDialog();
           }

       });

       return listView;
    }


    private void createPopupDialog() {

        builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.add_train_modal,null);
        designItem = view.findViewById(R.id.designEdit);
        itineraireItem = view.findViewById(R.id.itineraireEdit);
        save_button = view.findViewById(R.id.saveTrainbtn);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!designItem.getText().toString().isEmpty() && !itineraireItem.getText().toString().isEmpty()){
                    saveTrain(v);
                }
                else {
                    Toast.makeText(getContext(),"Veuillez remplir les champs",Toast.LENGTH_LONG).show();
                }

            }
        });

        builder.setView(view);
        //creation objet dialog
        dialog = builder.create();
        dialog.show();
    }

    private void saveTrain(View view) {
            String design = designItem.getText().toString().trim();
            String itineraire = itineraireItem.getText().toString().trim();
            Trains train = new Trains(design,itineraire);
            System.out.println(train.toString());
            Call<Trains> addTrainCall = useService.addTrains(train);
            addTrainCall.enqueue(new Callback<Trains>() {
                @Override
                public void onResponse(Call<Trains> call, Response<Trains> response) {
                    String numTrain = String.valueOf(response.body().getNumTrain());
                    trainList.add(new Train(numTrain,response.body().getDesign(),response.body().getItineraire()));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                        }
                    },1500);


                }

                @Override
                public void onFailure(Call<Trains> call, Throwable t) {
                    getAllTrain();
                    dialog.dismiss();
                }
            });

    }

   /* @Override
    public void onClick(View v) {

    }*/
}
