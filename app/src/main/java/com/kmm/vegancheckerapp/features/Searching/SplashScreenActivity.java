package com.kmm.vegancheckerapp.features.Searching;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.Loader;

import com.kmm.vegancheckerapp.R;
import com.kmm.vegancheckerapp.features.LoaderManager;
import com.kmm.vegancheckerapp.features.NetworkReceiver;
import com.kmm.vegancheckerapp.features.NetworkService;
import com.kmm.vegancheckerapp.features.Scanning.NavigationActivity;
import com.kmm.vegancheckerapp.features.ShowToast;
import com.kmm.vegancheckerapp.utils.IConstants;

import java.util.ArrayList;

public class SplashScreenActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<ArrayList<String>> {
NetworkReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        receiver = new NetworkReceiver();
        if(checkInternet(this)){
            androidx.loader.app.LoaderManager.getInstance(this).initLoader(IConstants.ALCOHOLTYPELOADERID, null, this);
            androidx.loader.app.LoaderManager.getInstance(this).initLoader(IConstants.FOODTYPELOADERID, null, this);
            androidx.loader.app.LoaderManager.getInstance(this).initLoader(IConstants.ALLBARCODELOADERID, null, this);

        } else{
            ShowToast toast = new ShowToast();
            toast.makeImageToast(this, R.drawable.ic_wifi_off, R.string.no_wifi, Toast.LENGTH_LONG);

        }
    }

    @NonNull
    @Override
    public Loader<ArrayList<String>> onCreateLoader(int id, @Nullable Bundle args) {
        if(id == IConstants.ALLBARCODELOADERID){
            return new LoaderManager.AllBarcodeLoaders(this);
        }  else if(id == IConstants.FOODTYPELOADERID){
            return new LoaderManager.TypeLoader(this, "Food");
        } else{
            return new LoaderManager.TypeLoader(this, "Alcohol");
        }
    }



    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<String>> loader, ArrayList<String> data) {
       int id = loader.getId();
       if(data != null){
           switch (id){
               case IConstants.ALLBARCODELOADERID:
                   IConstants.PRODUCT_BARCODES = new ArrayList<>(data);
                   break;
               case IConstants.FOODTYPELOADERID:
                   IConstants.FOOD_TYPES =new ArrayList<>(data);
                   IConstants.FOOD_TYPES.add("Other");


                   return;

               case IConstants.ALCOHOLTYPELOADERID:

                   IConstants.ALCOHOL_TYPES = new ArrayList<>(data);
                   IConstants.ALCOHOL_TYPES.add("Other");

                   return;


      }} else{

               Toast.makeText(this, "DB Error", Toast.LENGTH_LONG).show();
       }


        Intent intent = new Intent(this, NavigationActivity.class);
        startActivity(intent);
        finish();

      }








    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<String>> loader) {
        new LoaderManager.TypeLoader(this, "Food");
    }


    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, intentFilter);

    }


    @Override
    public void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    boolean checkInternet(Context context) {
        NetworkService serviceManager = new NetworkService(context);
        return serviceManager.isNetworkAvailable();
    }

}