package com.bawp.navigationbar.Reservation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.bawp.navigationbar.retrofit.builder.BuilderRetrofit;
import com.bawp.navigationbar.retrofit.controller.UseService;
import com.bawp.navigationbar.retrofit.models.Reservations;
import com.bawp.navigationbar.retrofit.models.Trains;
import com.bawp.navigationbar.retrofit.models.Voyageurs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_reservation extends Fragment implements View.OnClickListener{

    private Button addReservationBtn;
    private Button saveReservationBtn;
    private UseService useService;
    private List<Reservation> reservationList;
    private List<Trains> trainsList;
    private List<Voyageurs> voyageursList;
    private RecyclerView recyclerViewReserve;
    private View listView;
    private AlertDialog dialog;
    private  AlertDialog.Builder builder;
    public Fragment_reservation() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useService = BuilderRetrofit.getRetrofit().create(UseService.class);

    }

    @Override
    public void onStart() {
        super.onStart();
        reservationList = new ArrayList<Reservation>();
        trainsList = new ArrayList<Trains>();
        voyageursList = new ArrayList<Voyageurs>();
        recyclerViewReserve = listView.findViewById(R.id.recyclerViewReservation);
        recyclerViewReserve.setHasFixedSize(true);
        recyclerViewReserve.setLayoutManager(new LinearLayoutManager(getContext()));
        getAllReservation();
        getAllTrain();
        getAllVoyageur();
    }

    public void getAllReservation(){
        Call<List<Reservations>> reservations = useService.getAllReservation();
        reservations.enqueue(new Callback<List<Reservations>>() {
            @Override
            public void onResponse(Call<List<Reservations>> call, Response<List<Reservations>> response) {
                if (response.code() != 200){
                    Log.d("Warning" ,"Echec de connection");
                }
                List<Reservations> datas = response.body();
                Log.d("succes" ,datas.toString());
                setReservationInfos(datas);
            }

            @Override
            public void onFailure(Call<List<Reservations>> call, Throwable t) {
                Log.d("Error",t.getLocalizedMessage());
            }
        });
    }

    public  void  setReservationInfos(List<Reservations> datas){
        for (Reservations data : datas){
            String numReserve = String.valueOf(data.getNumReserve());
            String frais = String.valueOf(data.getFrais());
            System.out.println(frais);
            this.reservationList.add(new Reservation(
                    numReserve,data.getVoyageur().getNomVoyageur(),data.getTrain().getDesign(),data.getDateReserve(),frais));
        }
       this.setAdapter();

    }

    public  void  setAdapter(){ //recyclerViewTrain
        ReservationListAdapter adapter = new ReservationListAdapter(getContext(),reservationList);
        recyclerViewReserve.setAdapter(adapter);
    }


    /****get All train ***/
    public void getAllTrain(){
        Call<List<Trains>> trainListCall = useService.getAllTrain();
        trainListCall.enqueue(new Callback<List<Trains>>() {
            @Override
            public void onResponse(Call<List<Trains>> call, Response<List<Trains>> response) {

                trainsList = response.body();
                Log.d("max",response.body().toString());

            }

            @Override
            public void onFailure(Call<List<Trains>> call, Throwable t) {

            }
        });

    }

    /*** get All voyageur**/
    public void getAllVoyageur(){
        Call<List<Voyageurs>> voyageursCall = useService.getAllVoyageur();
        voyageursCall.enqueue(new Callback<List<Voyageurs>>() {
            @Override
            public void onResponse(Call<List<Voyageurs>> call, Response<List<Voyageurs>> response) {
                voyageursList =response.body();
                Log.d("voyageur",voyageursList.toString());
            }

            @Override
            public void onFailure(Call<List<Voyageurs>> call, Throwable t) {

            }
        });
    }

   @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listView = inflater.inflate(R.layout.fragment_reservation,container,false);
        addReservationBtn = listView.findViewById(R.id.addReservationBtn);
        addReservationBtn.setOnClickListener(this);
       return listView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addReservationBtn:

                openPopupAddReserve();
                break;
        }
    }

    private void openPopupAddReserve() {

        builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.add_reserve_modal,null);
        saveReservationBtn = view.findViewById(R.id.saveReservebtn);
        Spinner trainSpinner = view.findViewById(R.id.trainSpin);
        Spinner voyageurSpinner = view.findViewById(R.id.voyageurSpin);
        EditText dateEdit =view.findViewById(R.id.dateEdit);
        EditText fraisEdit = view.findViewById(R.id.fraisEdit);
        //String[] country = { "India", "USA", "China", "Japan", "Other"};
        getAllTrain();

        ArrayList<String> train = new ArrayList<String>();
        for(int i =0 ; i < trainsList.size();i++){
            train.add(trainsList.get(i).getDesign());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, train);
        trainSpinner.setAdapter(adapter);
        Trains[] trains = new Trains[1];
        trainSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                trains[0] =trainsList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        getAllVoyageur();
        ArrayList<String> voyageur =new ArrayList<String>() ;
        System.out.println(voyageursList.size());
        for(int j =0 ; j < voyageursList.size();j++){
            voyageur.add(voyageursList.get(j).getNomVoyageur());

        }
        ArrayAdapter<String> adapterVoy = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,voyageur);
        voyageurSpinner.setAdapter(adapterVoy);
        Voyageurs[] voyageurs = new Voyageurs[1];
       voyageurSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                voyageurs[0] =voyageursList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       saveReservationBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (!dateEdit.getText().toString().isEmpty() && !fraisEdit.getText().toString().isEmpty()){
                   Reservations reservation =new Reservations(trains[0].getNumTrain(),voyageurs[0].getNumVoyageur(),
                           dateEdit.getText().toString(),Integer.parseInt(fraisEdit.getText().toString()));
                   addReservation(reservation);
               }
               else {
                   Toast.makeText(getContext(), "Remplir les champs", Toast.LENGTH_SHORT).show();
               }
           }
       });

        builder.setView(view);
        //creation objet dialog
        dialog = builder.create();
        dialog.show();
    }

    private void addReservation(Reservations reservations){
        Call<Reservations> saveReservationsCall = useService.addReservations(reservations);
        saveReservationsCall.enqueue(new Callback<Reservations>() {
            @Override
            public void onResponse(Call<Reservations> call, Response<Reservations> response) {
                Log.d("TAG", String.valueOf(response.body().getNumReserve()));
                for(int i=0;i<reservationList.size();i++){
                    reservationList.remove(i);
                }
                getAllReservation();
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<Reservations> call, Throwable t) {

            }
        });

    }
  /*  private void  changeRecycler(int id){
        Call<Reservations> getReseve = useService.getReservation(id);
        getReseve.enqueue(new Callback<Reservations>() {
            @Override
            public void onResponse(Call<Reservations> call, Response<Reservations> res) {
                Reservations reserve = res.body();
                Log.d("dd",reserve.toString());
                reservationList.add(new Reservation(String.valueOf(reserve.getNumReserve()),reserve.getVoyageur().getNomVoyageur()
                        ,reserve.getTrain().getDesign(),reserve.getDateReserve(),String.valueOf(reserve.getFrais())));

            }

            @Override
            public void onFailure(Call<Reservations> call, Throwable t) {

            }
        });
    }*/
}
