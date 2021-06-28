package com.bawp.navigationbar.Voyageur;

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
import com.bawp.navigationbar.Voyageur.data.Voyageur;
import com.bawp.navigationbar.Voyageur.data.VoyageurListAdapter;
import com.bawp.navigationbar.retrofit.builder.BuilderRetrofit;
import com.bawp.navigationbar.retrofit.controller.UseService;
import com.bawp.navigationbar.retrofit.models.Trains;
import com.bawp.navigationbar.retrofit.models.Voyageurs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_voyageur extends Fragment implements View.OnClickListener{

    private List<Voyageur> voyageurList;
    private UseService useService;
    private View listvView;
    private RecyclerView recyclerViewVoyageur;
    private   Button addFormBtn;
    private  AlertDialog dialog;
    private  AlertDialog.Builder builder;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useService = BuilderRetrofit.getRetrofit().create(UseService.class);
        getAllVoyageur();
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerViewVoyageur = listvView.findViewById(R.id.recyclerViewVoyageur);
     //   recyclerViewVoyageur.setHasFixedSize(true);
        recyclerViewVoyageur.setLayoutManager(new LinearLayoutManager(getContext()));
        voyageurList =new ArrayList<Voyageur>();

    }

    public void getAllVoyageur(){
        Call<List<Voyageurs>> voyageurs = useService.getAllVoyageur();
        voyageurs.enqueue(new Callback<List<Voyageurs>>() {
            @Override
            public void onResponse(Call<List<Voyageurs>> call, Response<List<Voyageurs>> response) {
                if(response.code() != 200){
                    Log.d("msg  onja", "echec de connection ");
                    return;
                }
                List<Voyageurs> datas = response.body();
                setVoyageurInfos(datas);
            }

            @Override
            public void onFailure(Call<List<Voyageurs>> call, Throwable t) {
                Log.d("msg  onja", "fail "+t.getLocalizedMessage());
            }
        });
      /*  for (int i =0; i<10; i++){
            VoyageurDto voyageur = new VoyageurDto("num"+(i+1),"onjaniaina");
            voyageurs.add(voyageur);
        }*/

    }


    public  void  setVoyageurInfos(List<Voyageurs> voyageurInfos){
        for (Voyageurs voyageur:   voyageurInfos){
            String numVoyageur = String.valueOf(voyageur.getNumVoyageur());
            this.voyageurList.add(new Voyageur(numVoyageur, voyageur.getNomVoyageur()));
        }
        setAdapter();
    }

    public  void  setAdapter(){
        VoyageurListAdapter adapter = new VoyageurListAdapter(getActivity(),voyageurList);
        recyclerViewVoyageur.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listvView= inflater.inflate(R.layout.fragment_voyageur,container,false);
        addFormBtn = listvView.findViewById(R.id.addVoyageurBtn);
        addFormBtn.setOnClickListener(this);
        return listvView;
    }


    @Override
    public void onClick(View v) {
           switch (v.getId()){
               case  R.id.addVoyageurBtn:
                   showPopupVoyageur();
                   break;

           }
    }

    private void showPopupVoyageur() {
        builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.add_voyageur_modal,null);


        EditText nomVoyageurEdit = view.findViewById(R.id.nomVoyageurValue);
        Button saveBtn =view.findViewById(R.id.saveVoyageurbtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nomVoyageurEdit.getText().toString().trim().isEmpty()) {

                   String nom = nomVoyageurEdit.getText().toString().trim();
                   Voyageurs   voyageurs =new Voyageurs(nom);
                   addVoyageur(v,voyageurs);
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

    private void addVoyageur(View view, Voyageurs voyageurs){
        Call<Voyageurs> voyageursCall= useService.addVoyageurs(voyageurs);
        voyageursCall.enqueue(new Callback<Voyageurs>() {
            @Override
            public void onResponse(Call<Voyageurs> call, Response<Voyageurs> response) {
                Toast.makeText(getContext(),"Voyageur enregistrer",Toast.LENGTH_LONG).show();
                //recyclerViewVoyageur.notifyAll();
                voyageurList.add(new Voyageur(String.valueOf(response.body().getNumVoyageur()),response.body().getNomVoyageur()));
                Log.d("TAG", "onResponse: "+voyageurList.toString());
               /* for (int i=0; i<voyageurList.size();i++){
                    voyageurList.
                }*/

                voyageurList.clear();

                getAllVoyageur();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                },1500);
            }

            @Override
            public void onFailure(Call<Voyageurs> call, Throwable t) {
                getAllVoyageur();
                dialog.dismiss();
            }
        });

    }
}
