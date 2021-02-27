package com.example.pro_desa.ui.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.pro_desa.R;
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.ui.activity.LoginActivity;
import com.example.pro_desa.ui.user.fragment.HistorySuratUserFragment;
import com.example.pro_desa.ui.user.fragment.HomeUserFragment;
import com.example.pro_desa.ui.user.fragment.PengumumanUserFragment;
import com.example.pro_desa.ui.user.fragment.PesanUserFragment;
import com.example.pro_desa.utils.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeUserActivity extends AppCompatActivity {
    public SharedPrefManager sharedPrefManager;
    public ApiInterface apiInterface;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private AppBarConfiguration mAppBarConfiguration;
    private ActionBarDrawerToggle mToggle;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);

        sharedPrefManager = new SharedPrefManager(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        //Load Fragment
        loadFragment(new HomeUserFragment());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_home :
                        loadFragment(new HomeUserFragment());
                        return true;

                    case R.id.navigation_history_surat :
                        loadFragment(new HistorySuratUserFragment());
                        return true;

                    case R.id.navigation_pengumuman :
                        loadFragment(new PengumumanUserFragment());
                        return true;

                    case R.id.navigation_pesan :
                        loadFragment(new PesanUserFragment());
                        return true;

                    default:
                        return false;
                }
            }
        });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home)
//                .setDrawerLayout(drawer)
//                .build();
//
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(HomeUserActivity.this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onSupportNavigateUp()
//    {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//
//    }

    private void loadFragment (Fragment fragment){
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed(){
//        finish();
        new AlertDialog.Builder(this)
                .setMessage("Apakah anda mau keluar dari Aplikasi Pro Desa?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HomeUserActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("Tidak", null)
                .show();
    }
}