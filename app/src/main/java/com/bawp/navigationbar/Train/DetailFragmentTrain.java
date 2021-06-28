package com.bawp.navigationbar.Train;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawp.navigationbar.R;


public class DetailFragmentTrain extends Fragment {

    // TODO: Rename parameter arguments, choose names that match

    public DetailFragmentTrain() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_train, container, false);
    }
}