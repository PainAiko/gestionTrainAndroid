package com.bawp.navigationbar.recette;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bawp.navigationbar.R;
import com.bawp.navigationbar.Vusialisation.data.Vusialiser;
import com.bawp.navigationbar.retrofit.builder.BuilderRetrofit;
import com.bawp.navigationbar.retrofit.controller.UseService;
import com.bawp.navigationbar.retrofit.models.Recette;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecetteFragment extends Fragment {
    private EditText numTrainValue;
    private EditText dateValue;
    private TextView messRecette;
    private UseService useService;
    private Button chercherBtn;
    private View view;
    public RecetteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useService = BuilderRetrofit.getRetrofit().create(UseService.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        numTrainValue = view.findViewById(R.id.numTrainEditRecette);
        dateValue = view.findViewById(R.id.annee);
        messRecette = view.findViewById(R.id.messRecette);
        chercherBtn = view.findViewById(R.id.chercheRecetteBtn);
        chercherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dateValue.getText().toString().isEmpty() && !numTrainValue.getText().toString().isEmpty()){
                    int id = Integer.parseInt(numTrainValue.getText().toString());
                    Call<Recette> recetteCall = useService.getRecette(id, dateValue.getText().toString());
                    recetteCall.enqueue(new Callback<Recette>() {
                        @Override
                        public void onResponse(Call<Recette> call, Response<Recette> response) {
                            Recette recette =response.body();
                            if (recette == null) {
                                messRecette.setText("aucun");
                                return;
                            }
                            messRecette.setText("les recettes de train numero  "+numTrainValue.getText()+"( "+recette.getTrains().getDesign()+")"+" en "+ dateValue.getText()+" est "+ recette.getTotal());
                            Log.d("TAG", "onResponse: "+recette.getTotal());
                        }

                        @Override
                        public void onFailure(Call<Recette> call, Throwable t) {
                            Log.d("claude sy doda", "fail "+t.getLocalizedMessage());
                        }
                    });
                }
                else {
                    Toast.makeText(getActivity(), "Remplir les champs", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_recette, container, false);
        return  view;
    }
}