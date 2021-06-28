package com.bawp.navigationbar.Vusialisation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.navigationbar.R;
import com.bawp.navigationbar.Voyageur.data.Voyageur;
import com.bawp.navigationbar.Voyageur.data.VoyageurListAdapter;
import com.bawp.navigationbar.Vusialisation.data.VusialisationListAdapter;
import com.bawp.navigationbar.Vusialisation.data.Vusialiser;
import com.bawp.navigationbar.retrofit.builder.BuilderRetrofit;
import com.bawp.navigationbar.retrofit.controller.UseService;
import com.bawp.navigationbar.retrofit.models.Visualisation;
import com.bawp.navigationbar.retrofit.models.Voyageurs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_vusialiser extends Fragment {
    private EditText numTrainValue;
    private List<Vusialiser> voyageurList;
    private UseService useService;
    private View listvView;
    private RecyclerView recyclerViewVoyageur;
    private Button chercherBtn;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useService = BuilderRetrofit.getRetrofit().create(UseService.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        voyageurList = new ArrayList<Vusialiser>();
        recyclerViewVoyageur = listvView.findViewById(R.id.recyclerViewVisualiser);
        recyclerViewVoyageur.setHasFixedSize(true);
        recyclerViewVoyageur.setLayoutManager(new LinearLayoutManager(getContext()));
        numTrainValue = listvView.findViewById(R.id.numTrainEditVusialiser);
        chercherBtn = listvView.findViewById((R.id.chercheVoyageurBtn));
        chercherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numTrainValue.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Champ Obligatoire" , Toast.LENGTH_SHORT).show();
                }
                else {
                   int id = Integer.parseInt(numTrainValue.getText().toString());
                   getAllVoyageur(id);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listvView = inflater.inflate(R.layout.fragment_vusialiser,container,false);
        return listvView;
    }

    public void getAllVoyageur(int idTrain){
        Call<List<Visualisation>> voyageursVisual = useService.visualiserTrain(idTrain);
        voyageursVisual.enqueue(new Callback<List<Visualisation>>() {
            @Override
            public void onResponse(Call<List<Visualisation>> call, Response<List<Visualisation>> response) {
                if(response.code() != 200){
                    Log.d("msg  onja", "echec de connection ");
                    return;
                }
                List<Visualisation> datas = response.body();
                setVoyageurInfos(datas);
            }

            @Override
            public void onFailure(Call<List<Visualisation>> call, Throwable t) {
                Log.d("msg  onja", "fail "+t.getLocalizedMessage());
            }
        });
      /*  for (int i =0; i<10; i++){
            VoyageurDto voyageur = new VoyageurDto("num"+(i+1),"onjaniaina");
            voyageurs.add(voyageur);
        }*/

    }


    public  void  setVoyageurInfos(List<Visualisation> voyageurInfos){
        Log.d("TAG", "setVoyageurInfos: "+voyageurInfos.toString());
        for (Visualisation voyageur:   voyageurInfos){

            String numVoyageur = String.valueOf(voyageur.getVoyageurNumVoyageur());
            Toast.makeText(getContext(), ""+numVoyageur, Toast.LENGTH_SHORT).show();
            this.voyageurList.add(new Vusialiser(voyageur.getTrains().getDesign(), voyageur.getVoyageurs().getNomVoyageur()));
        }
        setAdapter();
    }

    public  void  setAdapter(){
        VusialisationListAdapter adapter = new VusialisationListAdapter(getActivity(),voyageurList);
        recyclerViewVoyageur.setAdapter(adapter);
    }

}
