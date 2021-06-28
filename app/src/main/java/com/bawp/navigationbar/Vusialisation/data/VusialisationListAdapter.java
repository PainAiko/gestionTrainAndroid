package com.bawp.navigationbar.Vusialisation.data;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.navigationbar.R;
import com.bawp.navigationbar.retrofit.builder.BuilderRetrofit;
import com.bawp.navigationbar.retrofit.controller.UseService;
import com.bawp.navigationbar.retrofit.models.Voyageurs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VusialisationListAdapter extends RecyclerView.Adapter<VusialisationListAdapter.ViewHolder> {

    private Context context;
    private List<Vusialiser> vusialiserList;

    public VusialisationListAdapter(Context context , List<Vusialiser> vusialiserList) {
        this.context =context;
        this.vusialiserList = vusialiserList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visualiser_listitem,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vusialiser voyageur = vusialiserList.get(position);
        holder.numVoyageur.setText(voyageur.getNumVoyageur());
        holder.nomVoyageur.setText(voyageur.getNomVoyageur());

    }


    @Override
    public int getItemCount() {
        return vusialiserList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        public TextView numVoyageur;
        public TextView nomVoyageur;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            numVoyageur = itemView.findViewById(R.id.numVoyageurVisualiser);
            nomVoyageur = itemView.findViewById(R.id.nomVoyageurVisualise);
        }
    }
}
