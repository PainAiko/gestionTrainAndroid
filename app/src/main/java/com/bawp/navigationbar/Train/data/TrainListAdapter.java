package com.bawp.navigationbar.Train.data;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.navigationbar.R;
import com.bawp.navigationbar.DetailActivity;
import com.bawp.navigationbar.retrofit.builder.BuilderRetrofit;
import com.bawp.navigationbar.retrofit.controller.UseService;
import com.bawp.navigationbar.retrofit.models.Trains;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainListAdapter  extends RecyclerView.Adapter<TrainListAdapter.ViewHolder> {

    private Context context;
    private List<Train> trainList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;



    public TrainListAdapter(Context context , List<Train> trainList ) {
        this.context =context;
        this.trainList= trainList;
    }

    public void  updateData(List<Train> trainList ){
         this.trainList = trainList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.train_listitem,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Train train = trainList.get(position);
        holder.numTrain.setText(train.getNumTrain());
        holder.design.setText(train.getDesign());
        holder.itineraire.setText(train.getItineraire());

    }


    @Override
    public int getItemCount() {
        return trainList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView numTrain;
        public TextView design;
        public TextView itineraire;
        public Button editButton;
        public Button saveEditButton;
        private TextView designEdit;
        private TextView itineraireEdit;
        private UseService useService;
        private RecyclerView recyclerView;
        //private AlertDialog.Builder builder;
       // private  AlertDialog  dialog;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            numTrain = itemView.findViewById(R.id.numTrain);
            design = itemView.findViewById(R.id.design);
            itineraire = itemView.findViewById(R.id.itineraire);
            editButton = itemView.findViewById(R.id.editBtnTrain);
            editButton.setOnClickListener(this);
            recyclerView = itemView.findViewById(R.id.recyclerViewtrain);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.editBtnTrain:
                  createPopupDialog();


                    break;
                default:
                    int position = getAbsoluteAdapterPosition();
                    Train train =trainList.get(position);
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("numTrain",Integer.parseInt(train.getNumTrain()));
                    intent.putExtra("design",train.getDesign());
                    intent.putExtra("itineraire",train.getItineraire());
                    intent.putExtra("isTrain",true);
                    context.startActivity(intent);
                    Log.d("TAG","on click"+ train.toString());
            }
        }

        public void  createPopupDialog( ){
            builder = new AlertDialog.Builder(itemView.getContext());
           // builder = new AlertDialog.Builder(context);
            int position = getAbsoluteAdapterPosition();
            Train train =trainList.get(position);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.add_train_modal, null);
            designEdit = view.findViewById(R.id.designEdit);
            itineraireEdit = view.findViewById(R.id.itineraireEdit);
            saveEditButton = view.findViewById(R.id.saveTrainbtn);
            designEdit.setText(train.getDesign());
            itineraireEdit.setText(train.getItineraire());

            saveEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!designEdit.getText().toString().isEmpty() && !itineraireEdit.getText().toString().isEmpty()){
                        saveTrain(v,train.getNumTrain(),position);
                    }
                    else {
                        Toast.makeText(context,"Veuillez remplir les champs",Toast.LENGTH_LONG).show();
                    }

                }
            });
            builder.setView(view);
            //creation objet dialog
            dialog = builder.create();
            dialog.show();
        }
        public void saveTrain(View view,String numTrain,int position){
            useService = BuilderRetrofit.getRetrofit().create(UseService.class);
            Trains train = new Trains(designEdit.getText().toString(),itineraireEdit.getText().toString());
            int id = Integer.parseInt(numTrain);
            Call<Trains> trainsCall = useService.updateTrains(id,train);
            trainsCall.enqueue(new Callback<Trains>() {
                @Override
                public void onResponse(Call<Trains> call, Response<Trains> response) {
                   // trainList.remove(position);
                    Call<Trains> getTrain = useService.getTrains(id);
                    getTrain.enqueue(new Callback<Trains>() {
                        @Override
                        public void onResponse(Call<Trains> call, Response<Trains> response) {

                            Train train1 =new Train(String.valueOf(response.body().getNumTrain()),response.body().getDesign(),
                                    response.body().getItineraire());
                            trainList.get(position).setDesign(train1.getDesign());
                            trainList.get(position).setItineraire(train1.getItineraire());
                            Toast.makeText(context.getApplicationContext(),""+ train1.getItineraire(),Toast.LENGTH_LONG).show();
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                        @Override
                        public void onFailure(Call<Trains> call, Throwable t) {

                        }
                    });
                  //
                }

                @Override
                public void onFailure(Call<Trains> call, Throwable t) {
                            dialog.dismiss();
                }
            });
        }
    }
}
