package com.bawp.navigationbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.bawp.navigationbar.Reservation.Fragment_reservation;
import com.bawp.navigationbar.Train.FragmentTrain;
import com.bawp.navigationbar.Voyageur.Fragment_voyageur;
import com.bawp.navigationbar.Vusialisation.Fragment_vusialiser;
import com.bawp.navigationbar.recette.RecetteFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView= findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();//mampiseho anle icone drawer
        if (savedInstanceState == null){
            FragmentManager fragmentManager =getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_train()).commit();
            if (fragment == null){
                toolbar.setTitle(R.string.train);
                fragment =  new FragmentTrain();
                fragmentManager.beginTransaction().add(R.id.fragment_container,fragment).commit();
                navigationView.setCheckedItem(R.id.nav_train);
            }

       }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawers();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_train:
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentTrain()).commit();
                navigationView.setCheckedItem(R.id.nav_train);
                toolbar.setTitle(R.string.train);
                break;
            case R.id.nav_voyageur:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_voyageur()).commit();
                navigationView.setCheckedItem(R.id.nav_voyageur);
                toolbar.setTitle(R.string.voyageur);
                break;
            case R.id.nav_reservation:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_reservation()).commit();
                navigationView.setCheckedItem(R.id.nav_reservation);
                toolbar.setTitle(R.string.reservation);
                break;
            case R.id.nav_vusialiserVoyageurTrain:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_vusialiser()).commit();
                navigationView.setCheckedItem(R.id.nav_vusialiserVoyageurTrain);
                toolbar.setTitle(R.string.vusialisation);
                break;
            case R.id.nav_recette:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new RecetteFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_recette);
                toolbar.setTitle(R.string.recette);

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}