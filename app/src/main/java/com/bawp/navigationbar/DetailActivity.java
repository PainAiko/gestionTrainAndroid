package com.bawp.navigationbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bawp.navigationbar.retrofit.builder.BuilderRetrofit;
import com.bawp.navigationbar.retrofit.controller.UseService;
import com.bawp.navigationbar.retrofit.models.Trains;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    UseService   useService;
    Toolbar toolbar;
    private TextView numTrain,design,itineraire;
    private Button editButton;
    private Button deleteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_train);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.train);
        setSupportActionBar(toolbar);
        useService = BuilderRetrofit.getRetrofit().create(UseService.class);
        numTrain = findViewById(R.id.numTrainValue);
        design = findViewById(R.id.designValue);
        itineraire = findViewById(R.id.itineraireValue);
        editButton =(Button) findViewById(R.id.editTrainBtn);
        deleteButton =(Button) findViewById(R.id.deleteTrainBtn);
        editButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        getTrain();
    }

    public  void  getTrain(){
        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("numTrain");
        Call<Trains> train = useService.getTrains(id);
        train.enqueue(new Callback<Trains>() {
            @Override
            public void onResponse(Call<Trains> call, Response<Trains> response) {
                if (response.code() != 200){
                    Log.d("warn" , "echec");
                    return;
                }
             //   Log.d("succes" ,  response.body().toString());
                Trains data = response.body();
                numTrain.setText(String.valueOf(data.getNumTrain()));
                design.setText(data.getDesign());
                itineraire.setText(data.getItineraire());
            }

            @Override
            public void onFailure(Call<Trains> call, Throwable t) {
                Log.d("Error" , t.getLocalizedMessage());
            }
        });

    }

    public void deleteTrain(String num){
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        // startActivity(intent);
        int id = Integer.parseInt(num);

        Log.d("id", ""+ id);
        Call<Trains> delete = useService.deleteTrains(id);
        delete.enqueue(new Callback<Trains>() {
            @Override
            public void onResponse(Call<Trains> call, Response<Trains> response) {
                if (response.code() != 200){
                    Log.d("warn" , "echec");
                    return;
                }
                Log.d("succes" ,  response.body().toString());
                startActivity(intent);
                //setResult(RESULT_OK);
                //finish();
            }

            @Override
            public void onFailure(Call<Trains> call, Throwable t) {
                Log.d("Error" , t.getLocalizedMessage());
                startActivity(intent);
            }
        });
        //  startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editTrainBtn:
                //startActivity(new Intent(detailTrainActivity.this, AddTrainActivity.class));
                Toast.makeText(getApplicationContext(),"bouton edit zay", Toast.LENGTH_LONG).show();
                break;
            case R.id.deleteTrainBtn:
                deleteTrain((String) numTrain.getText());
                Toast.makeText(getApplicationContext(),"Suppression avec succes",Toast.LENGTH_LONG).show();
                break;
        }
    }
}