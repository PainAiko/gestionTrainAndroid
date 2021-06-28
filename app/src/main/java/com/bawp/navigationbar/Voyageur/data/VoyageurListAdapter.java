package com.bawp.navigationbar.Voyageur.data;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.recyclerview.widget.RecyclerView;


import com.bawp.navigationbar.R;
import com.bawp.navigationbar.Reservation.data.Reservation;
import com.bawp.navigationbar.Reservation.data.ReservationListAdapter;
import com.bawp.navigationbar.retrofit.builder.BuilderRetrofit;
import com.bawp.navigationbar.retrofit.controller.UseService;
import com.bawp.navigationbar.retrofit.models.Voyageurs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoyageurListAdapter extends RecyclerView.Adapter<VoyageurListAdapter.ViewHolder> {

    private Context context;
    private List<Voyageur> voyageurs;

    public VoyageurListAdapter(Context context , List<Voyageur> voyageurList) {
        this.context =context;
        this.voyageurs = voyageurList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.voyageur_listitem,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Voyageur voyageur = voyageurs.get(position);
        holder.numVoyageur.setText(voyageur.getNumVoyageur());
        holder.nomVoyageur.setText(voyageur.getNomVoyageur());

    }


    @Override
    public int getItemCount() {
        return voyageurs.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView numVoyageur;
        public TextView nomVoyageur;
        private Button editVoyageurTrain;
        private Button delVoyageurTrain;
        private Button saveEditBtn;
        private Button isDeleteButton;
        private Button nonDeleteButton;
        private EditText nomVoyageurValue;
        private AlertDialog.Builder builder;
        private AlertDialog dialog;
        private LayoutInflater inflater;
        private UseService useService;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            numVoyageur = itemView.findViewById(R.id.numVoyageur);
            nomVoyageur = itemView.findViewById(R.id.nomVoyageur);
            editVoyageurTrain = itemView.findViewById(R.id.editBtnVoyageur);
            editVoyageurTrain.setOnClickListener(this);
            delVoyageurTrain =itemView.findViewById(R.id.delBtnVoyageur);

            delVoyageurTrain.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
               switch (v.getId()){
                   case R.id.editBtnVoyageur:
                       openPopupEdit();
                       break;
                   case R.id.delBtnVoyageur:
                       //Toast.makeText(context, "delette", Toast.LENGTH_SHORT).show();
                        openModalDelete();
                       break;
                   default:
                       Toast.makeText(context, "item", Toast.LENGTH_SHORT).show();
                       break;
               }

        }

        public void openPopupEdit(){
              builder = new AlertDialog.Builder(itemView.getContext());
              int position = getAbsoluteAdapterPosition();
              Voyageur voyageur = voyageurs.get(position);
              inflater = LayoutInflater.from(context);
              View view = inflater.inflate(R.layout.add_voyageur_modal,null);
              nomVoyageurValue = view.findViewById(R.id.nomVoyageurValue);
              nomVoyageurValue.setText(voyageur.getNomVoyageur());
              saveEditBtn =view.findViewById(R.id.saveVoyageurbtn);
              saveEditBtn.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if(nomVoyageurValue.getText().toString().isEmpty()){
                          Toast.makeText(context, "Remplir le champ ", Toast.LENGTH_SHORT).show();
                      }
                      else {
                          voyageur.setNomVoyageur(nomVoyageurValue.getText().toString());
                          updateVoyageur(v,voyageur,position);
                          Toast.makeText(context,"Modification effectuer", Toast.LENGTH_LONG).show();
                      }

                  }
              });
              builder.setView(view);
              dialog =builder.create();
              dialog.show();
        }

        private void updateVoyageur(View view, Voyageur voyageur , int position){
            useService = BuilderRetrofit.getRetrofit().create(UseService.class);
            Voyageurs voyageurs1 =new Voyageurs(voyageur.getNomVoyageur());
            voyageurs1.setNumVoyageur(Integer.parseInt(voyageur.getNumVoyageur()));
            Call<Voyageurs> updateVoyageur = useService.updateVoyageurs(voyageurs1,Integer.parseInt(voyageur.getNumVoyageur()));
            updateVoyageur.enqueue(new Callback<Voyageurs>() {
                @Override
                public void onResponse(Call<Voyageurs> call, Response<Voyageurs> response) {
                    Log.d("success",response.body().toString());
                    voyageurs.get(position).setNomVoyageur(response.body().getNomVoyageur());
                    notifyDataSetChanged();
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<Voyageurs> call, Throwable t) {
                    Log.d("fail",t.getLocalizedMessage());
                    dialog.dismiss();
                }
            });
        }

        private  void openModalDelete(){
            builder = new AlertDialog.Builder(itemView.getContext());
            int position = getAbsoluteAdapterPosition();
            Voyageur voyageur = voyageurs.get(position);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.suppression_modal,null);
            isDeleteButton = view.findViewById(R.id.Okbtn);
            nonDeleteButton = view.findViewById(R.id.Nobtn);
            isDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.Okbtn:
                           // Toast.makeText(context, voyageur.toString(), Toast.LENGTH_SHORT).show();
                            deleteVoyageur(v,voyageur,position);
                            break;
                    }
                }
            });
            nonDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.Nobtn:
                            //Toast.makeText(context, "No", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            break;
                    }
                }
            });
            builder.setView(view);
            dialog = builder.create();
            dialog.show();
        }

        private void deleteVoyageur(View view, Voyageur voyageur,int position){
            useService = BuilderRetrofit.getRetrofit().create(UseService.class);
            int id = Integer.parseInt(voyageur.getNumVoyageur());
            Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
            Call<Integer> deleteVoyageur = useService.deleteVoyageurs(id);
            deleteVoyageur.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    Log.d("TAG", "onResponse: " +response.body());
                    voyageurs.remove(position);
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
    }
}
