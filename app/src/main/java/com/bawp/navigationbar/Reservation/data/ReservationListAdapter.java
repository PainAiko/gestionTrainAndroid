package com.bawp.navigationbar.Reservation.data;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.navigationbar.R;
import com.bawp.navigationbar.Train.data.Train;
import com.bawp.navigationbar.Voyageur.data.Voyageur;
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


public class ReservationListAdapter extends RecyclerView.Adapter<ReservationListAdapter.ViewHolder> {

    private Context context;
    private List<Reservation> reservationList;

    public ReservationListAdapter(Context context , List<Reservation> reservationList) {
        this.context =context;

        this.reservationList =reservationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reserve_listitem,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reservation reservation = reservationList.get(position);
        holder.numReserve.setText(reservation.getNumReserve());
        holder.train.setText(reservation.getDesignReserve());
        holder.voyageur.setText(reservation.getNomVoyageurReserve());
        holder.frais.setText(reservation.getFrais());
        holder.date.setText(reservation.getDateReserve());
    }


    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView numReserve;
        public TextView train;
        public TextView voyageur;
        public TextView date;
        public TextView frais;
        private AlertDialog.Builder builder;
        private AlertDialog dialog;
        private LayoutInflater inflater;
        private UseService useService;
        private Button delReserveBtn;
        private Button editReserveBtn;
        private Button isDeleteButton;
        private Button nonDeleteButton;
        private Button saveReservationBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            useService = BuilderRetrofit.getRetrofit().create(UseService.class);

           numReserve = itemView.findViewById(R.id.numReservation);
           train = itemView.findViewById(R.id.train);
           voyageur = itemView.findViewById(R.id.voyageur);
           date= itemView.findViewById(R.id.date);
           frais = itemView.findViewById(R.id.frais);
           delReserveBtn =itemView.findViewById(R.id.delBtnReserve);
           editReserveBtn = itemView.findViewById(R.id.editBtnReserve);
           delReserveBtn.setOnClickListener(this);
           editReserveBtn.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {
              switch (v.getId()){
                  case R.id.delBtnReserve:
                     openModalDelete();
                      break;
                  case R.id.editBtnReserve:
                      //List<Trains> trainsList = new ArrayList<Trains>();
                      builder = new AlertDialog.Builder(itemView.getContext());
                      Trains[] trains = new Trains[1];
                      Voyageurs[] voyageur1 = new Voyageurs[1];
                      int  position = getAbsoluteAdapterPosition();
                      Reservation reservation = reservationList.get(position);
                     // Toast.makeText(context, ""+reservation.getNumReserve(), Toast.LENGTH_SHORT).show();
                      inflater = LayoutInflater.from(context);
                      View view = inflater.inflate(R.layout.add_reserve_modal,null);
                      saveReservationBtn = view.findViewById(R.id.saveReservebtn);
                      Spinner trainSpinner = view.findViewById(R.id.trainSpin);
                      Spinner voyageurSpinner = view.findViewById(R.id.voyageurSpin);
                      EditText dateEdit =view.findViewById(R.id.dateEdit);
                      EditText fraisEdit = view.findViewById(R.id.fraisEdit);
                      dateEdit.setText(reservation.getDateReserve());
                      fraisEdit.setText(reservation.getFrais());

                      Call<List<Trains>> trainListCall = useService.getAllTrain();
                      trainListCall.enqueue(new Callback<List<Trains>>() {
                          @Override
                          public void onResponse(Call<List<Trains>> call, Response<List<Trains>> response) {
                              List<Trains> trainsList = new ArrayList<Trains>();
                              trainsList = response.body();
                              ArrayList<String> train = new ArrayList<String>();
                              for(int i =0 ; i < trainsList.size();i++){
                                  train.add(trainsList.get(i).getDesign());
                              }
                              ArrayAdapter<String> adapter = new ArrayAdapter<String>(itemView.getContext(), android.R.layout.simple_spinner_item, train);
                              trainSpinner.setAdapter(adapter);

                              List<Trains> finalTrainsList = trainsList;
                              trainSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                  @Override
                                  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                      trains[0] = finalTrainsList.get(position);

                                  }

                                  @Override
                                  public void onNothingSelected(AdapterView<?> parent) {

                                  }
                              });

                              Call<List<Voyageurs>> voyageurListCall = useService.getAllVoyageur();
                              voyageurListCall.enqueue(new Callback<List<Voyageurs>>() {
                                  @Override
                                  public void onResponse(Call<List<Voyageurs>> call, Response<List<Voyageurs>> response) {
                                      List<Voyageurs> voyageuList = new ArrayList<Voyageurs>();
                                      voyageuList = response.body();
                                      ArrayList<String> voyageur = new ArrayList<String>();
                                      for(int i =0 ; i < voyageuList.size();i++){
                                          voyageur.add(voyageuList.get(i).getNomVoyageur());
                                      }
                                      ArrayAdapter<String> adapterVoy = new ArrayAdapter<String>(itemView.getContext(), android.R.layout.simple_list_item_1, voyageur);
                                      voyageurSpinner.setAdapter(adapterVoy);

                                      List<Voyageurs> finalVoyageurList = voyageuList;
                                      voyageurSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                          @Override
                                          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                             voyageur1[0] = finalVoyageurList.get(position);
                                             // Toast.makeText(itemView.getContext(), ""+voyageur1[0].getNumVoyageur(), Toast.LENGTH_SHORT).show();
                                          }

                                          @Override
                                          public void onNothingSelected(AdapterView<?> parent) {

                                          }
                                      });

                                      /**save**/
                                      saveReservationBtn.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              if (!dateEdit.getText().toString().isEmpty() && !fraisEdit.getText().toString().isEmpty()){
                                                /* Reservations reservation =new Reservations(trains[0].getNumTrain(),voyageur1[0].getNumVoyageur(),
                                                          dateEdit.getText().toString(),Integer.parseInt(fraisEdit.getText().toString()));
                                                  //addReservation(reservation);*/
                                                  Reservations reservations = new Reservations();
                                                  reservations.setTrain(trains[0]);
                                                  reservations.setVoyageur(voyageur1[0]);
                                                  reservations.setTrainNumTrain(trains[0].getNumTrain());
                                                  reservations.setVoyageurNumVoyageur(voyageur1[0].getNumVoyageur());
                                                  reservations.setDateReserve(dateEdit.getText().toString());
                                                  reservations.setFrais(Integer.parseInt(fraisEdit.getText().toString()));
                                                  reservations.setNumReserve(Integer.parseInt(reservation.getNumReserve()));
                                                //  Log.d("0TAG", "onClick: "+ reservations);
                                                  Call<Reservations> updateReservationsCall = useService.updateReservations(reservations.getNumReserve(),reservations);
                                                  updateReservationsCall.enqueue(new Callback<Reservations>() {
                                                      @Override
                                                      public void onResponse(Call<Reservations> call, Response<Reservations> response) {
                                                          Call<Reservations> getReservationsCall = useService.getReservation(reservations.getNumReserve());
                                                          getReservationsCall.enqueue(new Callback<Reservations>() {
                                                              @Override
                                                              public void onResponse(Call<Reservations> call, Response<Reservations> response) {

                                                                  Reservation reservation12 =new Reservation(String.valueOf(response.body().getNumReserve()),
                                                                          response.body().getVoyageur().getNomVoyageur(),
                                                                          response.body().getTrain().getDesign(),response.body().getDateReserve(),
                                                                          String.valueOf(response.body().getFrais()));
                                                                  reservationList.get(position).setNumReserve(reservation12.getNumReserve());
                                                                  reservationList.get(position).setNomVoyageurReserve(reservation12.getNomVoyageurReserve());
                                                                  reservationList.get(position).setDateReserve(reservation12.getDateReserve());
                                                                  reservationList.get(position).setFrais(reservation12.getFrais());
                                                                 // Toast.makeText(context.getApplicationContext(),""+ train1.getItineraire(),Toast.LENGTH_LONG).show();
                                                                  notifyDataSetChanged();
                                                                  dialog.dismiss();
                                                              }
                                                              @Override
                                                              public void onFailure(Call<Reservations> call, Throwable t) {
 
                                                              }
                                                          });
                                                      }

                                                      @Override
                                                      public void onFailure(Call<Reservations> call, Throwable t) {

                                                      }
                                                  });
                                              }
                                              else {
                                                  Toast.makeText(itemView.getContext(), "Remplir les champs", Toast.LENGTH_SHORT).show();
                                              }
                                          }

                                      });
                                  }

                                  @Override
                                  public void onFailure(Call<List<Voyageurs>> call, Throwable t) {

                                  }
                              });
                             // Log.d("max",response.body().toString());
                             // Toast.makeText(context, "edit"+trainsList.toString(), Toast.LENGTH_SHORT).show();
                          }

                          @Override
                          public void onFailure(Call<List<Trains>> call, Throwable t) {

                          }
                      });
                      builder.setView(view);
                      //creation objet dialog
                      dialog = builder.create();
                      dialog.show();
                      break;
              }
        }
        private void openPopupAddReserve() {

            builder = new AlertDialog.Builder(itemView.getContext());
            int position = getAbsoluteAdapterPosition();
            inflater =LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.add_reserve_modal,null);
            saveReservationBtn = view.findViewById(R.id.saveReservebtn);
            Spinner trainSpinner = view.findViewById(R.id.trainSpin);
            Spinner voyageurSpinner = view.findViewById(R.id.voyageurSpin);
            EditText dateEdit =view.findViewById(R.id.dateEdit);
            EditText fraisEdit = view.findViewById(R.id.fraisEdit);
            //String[] country = { "India", "USA", "China", "Japan", "Other"};
           getAllTrain();


           /* ArrayList<String> train = new ArrayList<String>();
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
            dialog.show();*/
        }
        private  void openModalDelete(){
            builder = new AlertDialog.Builder(itemView.getContext());
            int position = getAbsoluteAdapterPosition();
            Reservation reservation = reservationList.get(position);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.suppression_modal,null);
            isDeleteButton = view.findViewById(R.id.Okbtn);
            nonDeleteButton = view.findViewById(R.id.Nobtn);
            isDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.Okbtn:
                            deleteReservation(v,reservation,position);
                            break;
                    }
                }
            });
            nonDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.Nobtn:
                            Toast.makeText(context, "No", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            break;
                    }
                }
            });
            builder.setView(view);
            dialog = builder.create();
            dialog.show();
        }

        private void deleteReservation(View view, Reservation reserve,int position){
            useService = BuilderRetrofit.getRetrofit().create(UseService.class);
            int id = Integer.parseInt(reserve.getNumReserve());
            Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
            Call<Integer> deleteVoyageur = useService.deleteVoyageurs(id);
            deleteVoyageur.enqueue(new Callback<Integer>() {
                                       @Override
                                       public void onResponse(Call<Integer> call, Response<Integer> response) {
                                           Log.d("TAG", "onResponse: " +response.body());
                                           reservationList.remove(position);
                                           notifyDataSetChanged();
                                           Toast.makeText(context, "Suppression avec succes", Toast.LENGTH_SHORT).show();
                                           dialog.dismiss();
                                       }

                                       @Override
                                       public void onFailure(Call<Integer> call, Throwable t) {
                                           Log.d("TAG", "onResponse: " +t.getLocalizedMessage());
                                           //voyageurs.remove(position);
                                           // notifyDataSetChanged();
                                           // Toast.makeText(context, "Suppression avec succes", Toast.LENGTH_SHORT).show();
                                           dialog.dismiss();
                                       }
                                   }
            );
        }

        public void getAllTrain(){
            Call<List<Trains>> trainListCall = useService.getAllTrain();
            trainListCall.enqueue(new Callback<List<Trains>>() {
                @Override
                public void onResponse(Call<List<Trains>> call, Response<List<Trains>> response) {

                    //trainsList = response.body();
                    Log.d("max",response.body().toString());

                }

                @Override
                public void onFailure(Call<List<Trains>> call, Throwable t) {

                }
            });

        }

    }
}
